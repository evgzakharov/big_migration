package migration.simple.controller;

import migration.simple.repository.UserRepository;
import migration.simple.responses.Response;
import migration.simple.responses.UserResponse;
import migration.simple.types.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users")
    public UserResponse users() {
        List<User> users = userRepository.findAllUsers();

        return new UserResponse(true, "all ok", users);
    }

    @GetMapping("user/{id}")
    public UserResponse users(@PathVariable("id") Long userId) {
        Optional<User> user = userRepository.findUser(userId);

        return user
                .map(user1 -> new UserResponse(true, "find user with requested id", Arrays.asList(user1)))
                .orElseGet(() -> new UserResponse(false, "user not found", Collections.emptyList()));
    }

    @PutMapping("user")
    public Response addUser(@RequestParam("user") User user) {
        boolean status = userRepository.addUser(user);

        return new Response(status, "user add response");
    }

    @DeleteMapping("user/{id}")
    public Response deleteUser(@PathVariable("id") Long id) {
        boolean status = userRepository.deleteUser(id);

        return new Response(status, "user delete response");
    }
}
