package activitidatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_LeaveModel_NOTPASS_N {
    var N = 5 //表示5次审批失败
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow = exec(http("startProcess")
			.post("/startProcess/leave")
			.headers(contentType)
			.formParam("apply", "zhangsan")
			.formParam("approve", "lisi")
			.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
		.pause(1)
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第一个任务“提交申请”
		.pause(1)
		// .exec {session => 
		// 	println(session)
		// 	session
		// }
		.exec(http("completeTask")
			.post("completeTask/${processInstanceId}/${taskId}")
			.headers(contentType))
		.pause(3)
        .repeat(N) {
		    exec(http("getCurrentSingleTask")
			    .get("getCurrentSingleTask/${processInstanceId}")
			    .check(jsonPath("$..taskId").saveAs("taskId"))) //获取第二个任务 “经理审批”
		    .pause(1)
		    .exec(http("completeTask")
			    .post("completeTask/${processInstanceId}/${taskId}")
			    .headers(contentType)
			    .formParam("pass", "0"))    
            .pause(1)
            .exec(http("getCurrentSingleTask")
                .get("getCurrentSingleTask/${processInstanceId}")
                .check(jsonPath("$..taskId").saveAs("taskId"))) //获取"修改申请"task
            .pause(1)
            .exec(http("completeTask")
                .post("completeTask/${processInstanceId}/${taskId}")
                .headers(contentType))
            .pause(1)
        }
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第二个任务 “经理审批”
		.pause(1)
		.exec(http("completeTask")
			.post("completeTask/${processInstanceId}/${taskId}")
			.headers(contentType)
			.formParam("pass", "1"))		
		.pause(1) //流程结束
}