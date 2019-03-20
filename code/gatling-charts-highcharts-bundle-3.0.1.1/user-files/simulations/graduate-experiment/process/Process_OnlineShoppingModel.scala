package activitidatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_OnlineShoppingModel {
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow = exec { session =>
            session.set("processDefinitionId", "online-shopping:100:400")
        }
        .exec(http("startProcessInstanceById")
            .post("/startProcessInstanceById/${processDefinitionId}")
            .headers(contentType)
            .check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .exec(http("completeTask")   //choose-goods task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .exec(http("completeTask")   //pay task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .exec(http("completeTask")    //send goods
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .exec(http("completeTask")  //receive goods 
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
}