package migration.simple.controller

import migration.simple.responses.StatsResponse
import migration.simple.service.StatsService
import migration.simple.utils.Controller
import org.springframework.web.reactive.function.server.RouterFunctionDsl
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

open class StatsController(private val statsService: StatsService) : Controller {
    override fun nest(): RouterFunctionDsl.() -> Unit = {
        GET("/stats") { ok().body(stats()) }
    }

    open fun stats(): Mono<StatsResponse> {
        val stats = statsService.getStats()

        return Mono.just(StatsResponse(true, "user stats", stats))
    }
}
