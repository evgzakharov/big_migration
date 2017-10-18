package migration.simple.types

data class User(
        val id: Long? = null,
        val name: String,
        val surname: String,
        val age: Int
)

