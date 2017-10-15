package migration.simple.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties()
public class DBConfiguration {
    private DbConfig db;

    public DBConfiguration() {
    }

    public DBConfiguration(DbConfig db) {
        this.db = db;
    }

    public DbConfig getDb() {
        return db;
    }

    public void setDb(DbConfig db) {
        this.db = db;
    }

    @Bean
    public DbConfig configureDb() {
        return new DbConfig(db.getUrl(), db.getUser(), unSecure(db.getPassword()));
    }

    private String unSecure(String password) {
        int secretIndex = password.indexOf("Secret");

        return password.substring(0, secretIndex + 1);
    }

    public static class DbConfig {
        private String url;
        private String user;
        private String password;

        public DbConfig() {
        }

        public DbConfig(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
