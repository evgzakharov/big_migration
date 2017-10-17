package migration.simple.types;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class Stats {
    private Integer userCount;
    private User oldestUser;
    private User youngestUser;
}
