package migration.simple.repository;

import migration.simple.config.DBConfiguration;
import migration.simple.types.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private DBConfiguration.DbConfig dbConfig;

    public UserRepository(DBConfiguration.DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    private Long index = 3L;

    private List<User> users = Arrays.asList(
            new User(1L, "Oleg", "BigMan", 21),
            new User(2L, "Lesia", "Listova", 25),
            new User(3L, "Bin", "Bigbanovich", 30)
    );

    public List<User> findAllUsers() {
        return new ArrayList<>(users);
    }

    public synchronized boolean addUser(User newUser) {
        return users.add(newUser.copy(nextIndex()));
    }

    public Optional<User> findUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public synchronized boolean deleteUser(Long id) {
        Optional<User> findUser = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();

        Boolean status = false;
        if (findUser.isPresent()) {
            users.remove(findUser.get());
            status = true;
        }

        return status;
    }

    private Long nextIndex() {
        return index++;
    }
}
