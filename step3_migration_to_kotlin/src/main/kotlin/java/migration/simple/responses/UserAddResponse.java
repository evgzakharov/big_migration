package migration.simple.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import migration.simple.types.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAddResponse extends Response {
    private Long userId;

    public UserAddResponse(Boolean success, String description, Long userId) {
        super(success, description);
        this.userId = userId;
    }
}


