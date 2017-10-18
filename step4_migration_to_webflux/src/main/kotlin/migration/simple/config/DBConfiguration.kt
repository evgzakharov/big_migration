package migration.simple.config

import org.springframework.context.annotation.Bean

open class DBConfiguration {
    var db: DbConfig = DbConfig()

    @Bean
    open fun configureDb(): DbConfig {
        return DbConfig(db.url, db.user, unSecure(db.password))
    }

    private fun unSecure(password: String): String {
        val secretIndex = password.indexOf("Secret")

        return password.substring(0, secretIndex)
    }

    data class DbConfig(
            var url: String = "",
            var user: String = "",
            var password: String = ""
    )
}
