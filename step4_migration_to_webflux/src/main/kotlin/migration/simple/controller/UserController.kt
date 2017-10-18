package migration.simple.controller

import migration.simple.repository.UserRepository
import migration.simple.responses.DeleteResponse
import migration.simple.responses.UserAddResponse
import migration.simple.responses.UserResponse
import migration.simple.types.User
import org.springframework.web.bind.annotation.*

@RestController
open class UserController(private val userRepository: UserRepository) {

    @GetMapping("users")
    open fun users(): UserResponse {
        val users = userRepository.findAllUsers()

        return UserResponse(true, "return users", users)
    }

    @GetMapping("user/{id}")
    open fun users(@PathVariable("id") userId: Long): UserResponse {
        val user = userRepository.findUser(userId)

        return user
                ?.let { UserResponse(true, "find user with requested id", listOf(it)) }
                ?: UserResponse(false, "user not found", emptyList())
    }

    @PutMapping(value = "user")
    open fun addUser(@RequestBody user: User): UserAddResponse {
        val addIndex = userRepository.addUser(user)

        return addIndex
                ?.let { UserAddResponse(true, "user add successfully", it) }
                ?: UserAddResponse(false, "user not added", -1L)
    }

    @DeleteMapping("user/{id}")
    open fun deleteUser(@PathVariable("id") id: Long): DeleteResponse {
        val status = userRepository.deleteUser(id)

        return if (status) {
            DeleteResponse(true, "user has been deleted")
        } else {
            DeleteResponse(false, "user not been deleted")
        }
    }
}
