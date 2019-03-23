package activitidatabase 

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import activitidatabase.process._

class ExperimentOneSimulation extends 
Simulation {
	// val httpConf = http
	// 	.baseUrl("http://localhost:8781/")

	val httpConf = http
		.baseUrl("http://localhost:8791/")

	var t1 = scenario("T1").exec(Process_T1_OnlineShopping.workflow)
	var t2 = scenario("T2").exec(Process_T2_OnlineShopping.workflow)

	//测试各个请求的执行时间
	def experiment_base_request_responseTime() {
		setUp(
			t1.inject(
				constantUsersPerSec(6) during (3 minutes))
		).throttle(
			reachRps(30) in (10 seconds),
			holdFor(1 minutes),
			reachRps(50) in (10 seconds),
			holdFor(5 minutes)
		).protocols(httpConf)
		
		// setUp(
		// 	t1.inject(
		// 		rampUsers(500) during(2 minutes)
		// 	).throttle(
		// 		reachRps(10) in (5 seconds),
		// 		holdFor(30 seconds),
		// 		reachRps(20) in (5 seconds),
		// 		holdFor(30 seconds),
		// 		reachRps(30) in (5 seconds),
		// 		holdFor(30 seconds)
		// 	)
		// ).protocols(httpConf)
		// setUp(
		// 	t1.inject(
		// 		atOnceUsers(2)
		// 	)
		// ).protocols(httpConf)
	}

//再用分开在两个gatling下模拟两个租户试试；可以不用精确控制负载
	def process_auto_onlineShoppingModel() {
		setUp(
			t1.inject(
				rampUsers(150) during (1 minutes)
			).throttle(
				reachRps(20) in (5 seconds),
				holdFor(3 minutes)
			),
			t2.inject(
				rampUsers(300) during(1 minutes)
			).throttle(
				reachRps(20) in (5 seconds),
				holdFor(20 seconds)
			)
		).protocols(httpConf)
	}


	// process_auto_onlineShoppingModel()
	experiment_base_request_responseTime()

}
