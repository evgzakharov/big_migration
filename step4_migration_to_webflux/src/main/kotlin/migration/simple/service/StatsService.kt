package migration.simple.service

import migration.simple.repository.UserRepository
import migration.simple.types.Stats

open class StatsService(private val userRepository: UserRepository) {

    open fun getStats(): Stats {
        val allUsers = userRepository.findAllUsers()

        if (allUsers.isEmpty())
            throw RuntimeException("not find any user")

        val oldestUser = allUsers.maxBy { it.age }

        val youngestUser = allUsers.minBy { it.age }

        return Stats(
                allUsers.size,
                oldestUser!!,
                youngestUser!!
        )
    }
}
