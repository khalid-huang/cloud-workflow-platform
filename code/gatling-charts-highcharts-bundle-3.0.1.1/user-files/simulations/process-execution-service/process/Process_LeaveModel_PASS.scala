//先定义leave process流程模型每个任务的rtl级别
// 
//  task-name          rtl-level
//     start                 /
//    提交申请               2
//    经理审核               1
//    修改申请               2
//     end                   /

package activitiadmissiondatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_LeaveModel_PASS {
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow =  exec { session =>
            session.set("processDefinitionId", "leave:1:42")
        } 
		.exec(http("startProcessInstanceById")
			.post("/startProcessInstanceById/${processDefinitionId}")
			.headers(contentType)
			.formParam("apply", "zhangsan")
			.formParam("approve", "lisi")
			.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
		.pause(2)
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第一个任务“提交申请”
		.pause(1)
		// .exec {session => 
		// 	println(session)
		// 	session
		// }
		.exec(http("completeTask")
			.post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
			.headers(contentType)
            .formParam("rtl", "2"))
		.pause(2)
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第二个任务 “经理审批”
		.pause(1)
		.exec(http("completeTask")
			.post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
			.headers(contentType)
			.formParam("pass", "1")
            .formParam("rtl", "1"))		
		.pause(1) //流程结束
}