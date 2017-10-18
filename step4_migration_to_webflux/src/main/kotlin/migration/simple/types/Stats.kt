package migration.simple.types

data class Stats(
    val userCount: Int,
    val oldestUser: User,
    val youngestUser: User
)
