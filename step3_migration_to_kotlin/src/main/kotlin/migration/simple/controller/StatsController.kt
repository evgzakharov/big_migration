package migration.simple.controller

import migration.simple.responses.StatsResponse
import migration.simple.service.StatsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("stats")
open class StatsController(private val statsService: StatsService) {

    @GetMapping
    open fun stats(): StatsResponse {
        val stats = statsService.getStats()

        return StatsResponse(true, "user stats", stats)
    }
}
