package migration.simple.responses;

import migration.simple.types.Stats;

public class StatsResponse extends Response {
    private Stats stats;

    public StatsResponse(Boolean success, String description, Stats stats) {
        super(success, description);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
}
