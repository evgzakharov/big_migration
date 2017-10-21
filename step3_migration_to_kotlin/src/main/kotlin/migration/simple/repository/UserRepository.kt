package migration.simple.repository

import migration.simple.config.DBConfiguration
import migration.simple.types.User
import org.springframework.stereotype.Repository

@Repository
open class UserRepository(dbConfig: DBConfiguration.DbConfig) {

    private var index: Long = 3L

    private val users = mutableListOf(
            User(1L, "Oleg", "BigMan", 21),
            User(2L, "Lesia", "Listova", 25),
            User(3L, "Bin", "Bigbanovich", 30)
    )

    init {
        println(dbConfig)
    }

    open fun findAllUsers(): List<User> {
        return users.toList()
    }

    @Synchronized
    open fun addUser(newUser: User): Long? {
        val newIndex = nextIndex()
        val addStatus = users.add(newUser.copy(id = newIndex))

        return if (addStatus) {
            newIndex
        } else {
            null
        }
    }

    open fun findUser(id: Long): User? {
        return users.firstOrNull { user -> user.id == id }
    }

    @Synchronized
    open fun deleteUser(id: Long?): Boolean {
        val findUser = users.find { user -> user.id == id }

        val addStatus: Boolean = if (findUser != null) {
            users.remove(findUser)
            true
        } else {
            false
        }

        return addStatus
    }

    private fun nextIndex(): Long {
        return index++
    }
}
