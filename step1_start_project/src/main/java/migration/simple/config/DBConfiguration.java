package migration.simple.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBConfiguration {
    private DbConfig db;

    @Bean
    public DbConfig configureDb() {
        return new DbConfig(db.getUrl(), db.getUser(), unSecure(db.getPassword()));
    }

    private String unSecure(String password) {
        int secretIndex = password.indexOf("Secret");

        return password.substring(0, secretIndex);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DbConfig {
        private String url;
        private String user;
        private String password;
    }
}
