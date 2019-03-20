package activitidatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

import activitidatabase.process._

class LoadSimulation extends Simulation {
    var httpConf = http.baseUrl("http://localhost:8771/")

    def admissionLayerWithThreeModel() {
        var onlineShopping = scenario("online shopping").exec(Process_OnlineShoppingModel.workflow)
        var leavePass = scenario("leave pass").exec(Process_LeaveModel_PASS.workflow)
		var leaveNotPass = scenario("leave no pass").exec(Process_LeaveModel_NOTPASS_N.workflow)

//adimissionlayersimulation-20181205031058757/index.html
// leavePass作为基本的，表示一直很稳定的，由leaveNotPass和onlineShopping来制造不稳定性
        setUp(
            leavePass.inject(
                constantUsersPerSec(5) during (60 seconds)
            ),
            onlineShopping.inject(
                nothingFor(5 seconds),
                rampUsers(20) during (5 seconds),
                nothingFor(2 seconds),
                atOnceUsers(40),
                rampUsers(20) during (5 seconds),
                nothingFor(2 seconds),
                atOnceUsers(30),
                rampUsers(40) during (5 seconds),
                nothingFor(5 seconds),
                rampUsers(20) during (5 seconds),
                nothingFor(5 seconds),
                atOnceUsers(60),
            )
        ).protocols(httpConf)
// adimissionlayersimulation-20181205023912758/index.html
        // setUp(
        //     leavePass.inject(
        //         constantUsersPerSec(10) during (60 seconds)
        //     )
        // ).protocols(httpConf)
    }

    admissionLayerWithThreeModel()

}