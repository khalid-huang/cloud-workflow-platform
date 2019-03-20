package activitidatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_T1_OnlineShopping {


    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")

    var feeder1 = csv("act_re_procdef_1.csv").circular  //包含1个话中实例定义
    var feeder20 = csv("act_re_procdef_20.csv").circular //包含20个流程实例定义 
    var feeder30 = csv("act_re_procdef_30.csv").circular //包含20个流程实例定义 
    var feeder40 = csv("act_re_procdef_40.csv").circular 
    var feeder1000 = csv("act_re_procdef_0.csv").circular

    var workflow = feed(feeder1)
		.exec(http("t1-startProcessInstanceById")
            .post("/startProcessInstanceById/${processDefinitionId}")
            .headers(contentType)
			.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
 		.exec(http("t1-getAllocatedWorkitem")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(2) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")   //choose-goods task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
        .exec(http("t1-getAllocatedWorkitem")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")   //pay task
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
        .exec(http("t1-getAllocatedWorkitem")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(2) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")    //send goods
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
       .exec(http("t1-getAllocatedWorkitem")
			.get("getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId")))
        .pause(1) //模拟外部服务的任务处理时间
        .exec(http("t1-getAllocatedWorkitem")  //receive goods 
            .post("completeTask/${processDefinitionId}/${processInstanceId}/${taskId}")
            .headers(contentType))
}
