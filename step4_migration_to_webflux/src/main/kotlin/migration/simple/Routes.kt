package migration.simple

import migration.simple.controller.StatsController
import migration.simple.controller.UserController
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

open class Routes(
        private val userController: UserController,
        private val statsController: StatsController
) {
    fun router() = router {
        accept(APPLICATION_JSON).nest(userController.nest())

        accept(APPLICATION_JSON).nest(statsController.nest())

        GET("/") { ok().render("index") }
    }
}


