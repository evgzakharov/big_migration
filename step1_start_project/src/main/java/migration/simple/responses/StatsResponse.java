package migration.simple.responses;

import lombok.Getter;
import migration.simple.types.Stats;

@Getter
public class StatsResponse extends Response {
    private Stats stats;

    public StatsResponse(Boolean success, String description, Stats stats) {
        super(success, description);
        this.stats = stats;
    }
}
