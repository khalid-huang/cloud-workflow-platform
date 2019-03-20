package activitidatabase 

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import activitidatabase.process._

class SchedulerDataCollectionSimulation extends 
Simulation {
	val httpConf = http
		.baseUrl("http://192.168.0.99:8763/")

	def process_auto_onlineShoppingModel() {
		var auto_onlineShopping = scenario("auto online shopping").exec(Process_AddPD_OnlineShoppingModel.workflow)

//实验组一的负载
		// setUp(
		// 	auto_onlineShopping.inject(
		// 		constantUsersPerSec(12) during (3 minutes))
		// ).throttle(
		// 	reachRps(20) in (10 seconds),
		// 	holdFor(1 minutes),
		// 	reachRps(60) in (10 seconds),
		// 	holdFor(1 minutes),
		// 	reachRps(80) in (10 seconds),
		// 	holdFor(1 minutes),
		// 	reachRps(100) in (10 seconds),
		// 	holdFor(1 minutes),
		// 	reachRps(80) in (10 seconds),   //这组负载是为了保证运行完后续的所有请求而设置的
		// 	holdFor(5 minutes)
		// ).protocols(httpConf)

		setUp(
			auto_onlineShopping.inject(
				constantUsersPerSec(6) during (2 minutes))
                                //rampUsers(1000) during (3 minutes))
		).throttle(
			reachRps(20) in (10 seconds),
			holdFor(30 seconds),
			reachRps(60) in (10 seconds),
			holdFor(4 minutes),
		 	//reachRps(20) in (10 seconds),   //这组负载是为了保证运行完后续的所有请求而设置的
		 	//holdFor(5 minutes)			
		).protocols(httpConf)

/**		setUp(
			auto_onlineShopping.inject(
				constantUsersPerSec(4) during (6 minutes))
		).protocols(httpConf)
*/

		// setUp(auto_onlineShopping.inject(atOnceUsers(1)).protocols(httpConf))
	}


	process_auto_onlineShoppingModel()

}
