package migration.simple.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Response {
    private Boolean success;
    private String description;
}

