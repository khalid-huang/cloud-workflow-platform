package activitidatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_LeaveModel_PASS {
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow =  exec { session =>
            session.set("processDefinitionId", "leave:1:207542")
        } 
		.exec(http("startProcessInstanceById")
			.post("/startProcessInstanceById/${processDefinitionId}")
			.headers(contentType)
			.formParam("apply", "zhangsan")
			.formParam("approve", "lisi")
			.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第一个任务“提交申请”
		// .exec {session => 
		// 	println(session)
		// 	session
		// }
		.exec(http("completeTask")
			.post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
			.headers(contentType))
		.exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId"))) //获取第二个任务 “经理审批”
		.exec(http("completeTask")
			.post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
			.headers(contentType))
}