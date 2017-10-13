package migration.simple.controller;

import migration.simple.repository.UserRepository;
import migration.simple.responses.UserResponse;
import migration.simple.types.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public UserResponse users() {
        List<User> users = userRepository.findAllUsers();

        return new UserResponse(true, "all ok", users);
    }
}
