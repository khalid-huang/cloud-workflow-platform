package activitidatabase.process

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

object Process_T1_OnlineShopping {


    var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")

    var feeder1 = csv("act_re_procdef_1.csv").circular  //包含1个话中实例定义
    // var feeder20 = csv("act_re_procdef_20.csv").circular //包含20个流程实例定义 
    // var feeder30 = csv("act_re_procdef_30.csv").circular //包含20个流程实例定义 
    // var feeder40 = csv("act_re_procdef_40.csv").circular 
    // var feeder1000 = csv("act_re_procdef_0.csv").circular

    var workflow = feed(feeder1)
		.exec(http("t1-startProcessInstanceById")
            .post("experiment/startProcessInstanceById/${processDefinitionId}")
            .headers(contentType)
			.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
        .pause(2)
 		.exec(http("t1-getAllocatedWorkitem")
			.get("experiment/getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId1")))
        .pause(2) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")   //choose-goods task
            .post("experiment/workitem/complete/${processDefinitionId}/${processInstanceId}/${taskId1}")
            .headers(contentType))
        .pause(1)
        .exec(http("t1-getOfferedWorkitem")
			.get("experiment/getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId2")))
        // .pause(1) //模拟外部服务的任务处理时间
        // .exec(http("t1-acceptWorkiem")
        //     .post("experiment/workitem/accept/${processInstanceId}/${taskId2}")
        //     .headers(contentType)
        //     .formParam("username", "mike"))
        .pause(1)
        .exec(http("t1-completeWorkitem")   //pay task
            .post("experiment/workitem/complete/${processDefinitionId}/${processInstanceId}/${taskId2}")
            .headers(contentType))
        .pause(1)
        .exec(http("t1-getOfferedWorkitem")
			.get("experiment/getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId3")))
        // .pause(1) //模拟外部服务的任务处理时间
        // .exec(http("t1-acceptWorkiem")
        //     .post("experiment/workitem/accept/${processInstanceId}/${taskId3}")
        //     .headers(contentType)
        //     .formParam("username", "mike"))
        .pause(2) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")    //send goods
            .post("experiment/workitem/complete/${processDefinitionId}/${processInstanceId}/${taskId3}")
            .headers(contentType))
        .pause(1)
       .exec(http("t1-getAllocatedWorkitem")
			.get("experiment/getCurrentSingleTask/${processInstanceId}")
			.check(jsonPath("$..taskId").saveAs("taskId4")))
        .pause(1) //模拟外部服务的任务处理时间
        .exec(http("t1-completeWorkitem")  //receive goods 
            .post("experiment/workitem/complete/${processDefinitionId}/${processInstanceId}/${taskId4}")
            .headers(contentType))
        .pause(1)
}
