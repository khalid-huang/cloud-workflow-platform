// 这是带有rtl信息的process 流程定义

// 先定义onlineshopping流程模型的rlt级别
// 
//  task-name        rtl-level
//    start             / 
// choose-goods         1
//     pay              2
//  send-goods          2
// receive-goods        1
//      end             /

package activitiadmissiondatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_OnlineShoppingModel {
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow = exec { session =>
            session.set("processDefinitionId", "online-shopping:1:37")
        }
        .exec(http("startProcessInstanceById")
            .post("/startProcessInstanceById/${processDefinitionId}")
            .headers(contentType)
            .check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
        .pause(1)
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1)
        .exec(http("completeTask")   //choose-goods task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "1"))
        .pause(1)
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1)
        .exec(http("completeTask")   //pay task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "2"))
        .pause(1)        
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1)
        .exec(http("completeTask")    //send goods
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "2"))
        .pause(1)
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1)
        .exec(http("completeTask")  //receive goods 
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "1"))
        .pause(1)   //流程结束
}

