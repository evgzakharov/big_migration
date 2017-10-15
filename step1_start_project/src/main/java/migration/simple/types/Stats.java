package migration.simple.types;

import lombok.Value;

@Value
public class Stats {
    private Integer userCount;
    private User oldestUser;
    private User youngestUser;
}
