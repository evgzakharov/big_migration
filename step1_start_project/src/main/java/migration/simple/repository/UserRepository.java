package migration.simple.repository;

import migration.simple.types.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    public List<User> findAllUsers() {
        return Arrays.asList(
                new User("Aleg", "BigMan", 21),
                new User("Lesia", "Listova", 25),
                new User("Bin", "Bigbanovich", 30)
        );
    }
}
