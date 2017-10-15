package migration.simple.responses;

import lombok.Getter;
import migration.simple.types.User;

import java.util.List;

@Getter
public class UserResponse extends Response {
    private List<User> users;

    public UserResponse(Boolean success, String description, List<User> users) {
        super(success, description);
        this.users = users;
    }
}
