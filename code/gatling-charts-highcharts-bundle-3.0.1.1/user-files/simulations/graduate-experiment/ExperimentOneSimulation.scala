package activitidatabase 

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import activitidatabase.process._

class ExperimentOneSimulation extends 
Simulation {
	val httpConf = http
		.baseUrl("http://192.168.0.39:8763/")

	var t1 = scenario("T1").exec(Process_T1_OnlineShopping.workflow)
	var t2 = scenario("T2").exec(Process_T2_OnlineShopping.workflow)

//再用分开在两个gatling下模拟两个租户试试；可以不用精确控制负载
	def process_auto_onlineShoppingModel() {
		setup(
			t1.inject(
				ramperUsers(150) during (1 minutes)
			).throttle(
				reachPrs(20) in (5 seconds),
				holdFor(3 minutes)
			),
			t2.inject(
				ramperUsers(300) during(1 minutes)
			).throttle(
				reachPrs(20) in (5 seconds),
				holdFor(20 seconds),
				reachPrs()
			)
		).protocols(httpConf)
	}


	process_auto_onlineShoppingModel()

}
