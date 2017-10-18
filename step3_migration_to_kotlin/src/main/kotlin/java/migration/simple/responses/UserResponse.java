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
public class UserResponse extends Response {
    private List<User> users;

    public UserResponse(Boolean success, String description, List<User> users) {
        super(success, description);
        this.users = users;
    }
}


