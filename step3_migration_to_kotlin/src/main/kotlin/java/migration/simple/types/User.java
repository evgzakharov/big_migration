package migration.simple.types;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class User {
    private Long id;
    private String name;
    private String surname;
    private Integer age;

    public User copy(Long id) {
        return new User(id, this.name, this.surname, this.age);
    }
}

