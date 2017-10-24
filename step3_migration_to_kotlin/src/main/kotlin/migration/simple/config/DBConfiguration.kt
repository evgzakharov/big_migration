package migration.simple.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties
open class DBConfiguration {
    var db: DbConfig = DbConfig()

    @Bean
    open fun configureDb(): DbConfig {
        return DbConfig(db.url, db.user, unSecure(db.password))
    }

    private fun unSecure(password: String): String {
        return password.substringBefore("Secret")
    }

    data class DbConfig(
            var url: String = "",
            var user: String = "",
            var password: String = ""
    )
}
