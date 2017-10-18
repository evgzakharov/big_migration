package migration.simple.service;

import migration.simple.repository.UserRepository;
import migration.simple.types.Stats;
import migration.simple.types.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class StatsService {

    private UserRepository userRepository;

    public StatsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Stats getStats() {
        List<User> allUsers = userRepository.findAllUsers();

        User oldestUser = allUsers.stream()
                .max(Comparator.comparingInt(User::getAge))
                .get();

        User youngestUser = allUsers.stream()
                .min(Comparator.comparingInt(User::getAge))
                .get();

        return new Stats(
                allUsers.size(),
                oldestUser,
                youngestUser
        );
    }
}
