package migration.simple.controller;

import migration.simple.repository.UserRepository;
import migration.simple.responses.Response;
import migration.simple.responses.UserAddResponse;
import migration.simple.responses.UserResponse;
import migration.simple.types.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

        return new UserResponse(true, "return users", users);
    }

    @GetMapping("user/{id}")
    public UserResponse users(@PathVariable("id") Long userId) {
        Optional<User> user = userRepository.findUser(userId);

        return user
                .map(findUser -> new UserResponse(true, "find user with requested id", Collections.singletonList(findUser)))
                .orElseGet(() -> new UserResponse(false, "user not found", Collections.emptyList()));
    }

    @PutMapping(value = "user")
    public Response addUser(@RequestBody User user) {
        Optional<Long> addIndex = userRepository.addUser(user);

        return addIndex
                .map(index -> new UserAddResponse(true, "user add successfully", index))
                .orElseGet(() -> new UserAddResponse(false, "user not added", -1L));
    }

    @DeleteMapping("user/{id}")
    public Response deleteUser(@PathVariable("id") Long id) {
        boolean status = userRepository.deleteUser(id);

        if (status) {
            return new Response(true, "user has been deleted");
        } else {
            return new Response(false, "user not been deleted");
        }
    }
}
