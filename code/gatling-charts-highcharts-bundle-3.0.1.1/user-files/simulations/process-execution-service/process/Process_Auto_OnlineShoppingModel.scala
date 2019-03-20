// 这是带有rtl信息的process 流程定义
// 表示一个自动化流程

// 先定义onlineshopping流程模型的rlt级别
// 
//  task-name        rtl-level
//    start             / 
// choose-goods         0
//     pay              0
//  send-goods          0
// receive-goods        0
//      end             /

package activitiadmissiondatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_Auto_OnlineShoppingModel {
    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")
    var workflow = exec { session =>
            session.set("processDefinitionId","online-shopping:1:37")
        }
        .exec(http("startProcessInstanceById")
            .post("/startProcessInstanceById/${processDefinitionId}")
            .headers(contentType)
            .check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(2) //模拟外部服务的任务处理时间
        .exec(http("completeTask")   //choose-goods task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "0"))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1) //模拟外部服务的任务处理时间
        .exec(http("completeTask")   //pay task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "0"))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(3) //模拟外部服务的任务处理时间
        .exec(http("completeTask")    //send goods
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "0"))
        .exec(http("getCurrentSingleTask")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1) //模拟外部服务的任务处理时间
        .exec(http("completeTask")  //receive goods 
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType)
            .formParam("rtl", "0"))
}

