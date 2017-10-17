package migration.simple.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import migration.simple.types.Stats;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StatsResponse extends Response {
    private Stats stats;

    public StatsResponse(Boolean success, String description, Stats stats) {
        super(success, description);
        this.stats = stats;
    }
}
