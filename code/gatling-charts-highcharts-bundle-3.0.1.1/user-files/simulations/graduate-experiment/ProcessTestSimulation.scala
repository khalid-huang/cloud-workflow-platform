package activitidatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import activitidatabase.process._

class ProcessTestSimulation extends 
Simulation {
	val httpConf = http
		.baseUrl("http://localhost:8771/")
	
	def basictest() {
		var contentType = Map("Content-Type" -> "application/x-www-form-urlencoded")

		var scn = scenario("basic activiti scenario")
			.exec(http("startProcess")
				.post("/startProcess/leave")
				.headers(contentType)
				.formParam("apply", "zhangsan")
				.formParam("approve", "lisi")
				.check(jsonPath("$..processInstanceId").saveAs("processInstanceId")))
			.pause(3)
			.exec(http("getCurrentSingleTask")
				.get("getCurrentSingleTask/${processInstanceId}")
				.check(jsonPath("$..taskId").saveAs("taskId")))
			.pause(1)
			.exec {session => 
				println(session)
				session
			}
		setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
	}

	def process_leavemodel_pass() {
		var leavePass = scenario("leave pass").exec(Process_LeaveModel_PASS.workflow)
		setUp(leavePass.inject(atOnceUsers(1)).protocols(httpConf))
	}

	def process_leavemodel_notpass() {
		var leaveNotPass = scenario("leave no pass").exec(Process_LeaveModel_NOTPASS_N.workflow)
		setUp(leaveNotPass.inject(atOnceUsers(1)).protocols(httpConf))
	}

	def process_onlineshoppingmodel() {
		var onlineshopping = scenario("online shopping").exec(Process_OnlineShoppingModel.workflow)
		setUp(onlineshopping.inject(atOnceUsers(20)).protocols(httpConf))
	}

	// basictest()
	// process_leavemodel_pass()
	// process_leavemodel_notpass()
	process_onlineshoppingmodel()

}