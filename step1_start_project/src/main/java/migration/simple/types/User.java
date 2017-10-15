package migration.simple.types;

public class User {
    private Long id;
    private String name;
    private String surname;
    private Integer age;

    public User(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public User(Long id, String name, String surname, Integer age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public User copy(Long id) {
        return new User(
                id,
                this.name,
                this.surname,
                this.age
        );
    }
}

