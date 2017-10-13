package migration.simple.responses;

import migration.simple.types.User;

import java.util.List;

public class UserResponse extends Response {
    private List<User> users;

    public UserResponse(Boolean success, String description, List<User> users) {
        super(success, description);
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
