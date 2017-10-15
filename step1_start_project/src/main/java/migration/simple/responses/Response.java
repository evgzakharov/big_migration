package migration.simple.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {
    private Boolean success;
    private String description;
}
