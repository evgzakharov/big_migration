package migration.simple.types

data class User(
        val id: Long? = null,
        val name: String,
        val surname: String,
        val age: Int
)

data class Stats(
        val userCount: Int,
        val oldestUser: User,
        val youngestUser: User
)

