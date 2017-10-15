package migration.simple.controller;

import migration.simple.responses.StatsResponse;
import migration.simple.service.StatsService;
import migration.simple.types.Stats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("stats")
public class StatsController {

    private StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public StatsResponse stats() {
        Stats stats = statsService.getStats();

        return new StatsResponse(true, "user stats", stats);
    }
}
