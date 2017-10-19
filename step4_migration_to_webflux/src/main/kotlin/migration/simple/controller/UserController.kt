package migration.simple.controller

import migration.simple.repository.UserRepository
import migration.simple.responses.DeleteResponse
import migration.simple.responses.UserAddResponse
import migration.simple.responses.UserResponse
import migration.simple.types.User
import org.springframework.web.reactive.function.server.RouterFunctionDsl
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

open class UserController(private val userRepository: UserRepository) {
    fun nest(): RouterFunctionDsl.() -> Unit = {
        GET("/user") {
            ok().body(users())
        }
        GET("/user/{id}") {
            ok().body(user(it.pathVariable("id").toLong()))
        }
        PUT("/user") {
            ok().body(addUser(it.bodyToMono(User::class.java)))
        }
        DELETE("/user/{id}") {
            ok().body(deleteUser(it.pathVariable("id").toLong()))
        }
    }

    open fun users(): Mono<UserResponse> {
        val users = userRepository.findAllUsers()

        return Mono.just(UserResponse(true, "return users", users))
    }

    open fun user(userId: Long): Mono<UserResponse> {
        val user = userRepository.findUser(userId)

        val response = user
                ?.let { UserResponse(true, "find user with requested id", listOf(it)) }
                ?: UserResponse(false, "user not found", emptyList())

        return Mono.just(response)
    }

    open fun addUser(user: Mono<User>): Mono<UserAddResponse> = user.map {
        val addIndex = userRepository.addUser(it)

        addIndex
                ?.let { UserAddResponse(true, "user add successfully", it) }
                ?: UserAddResponse(false, "user not added", -1L)
    }

    open fun deleteUser(id: Long): Mono<DeleteResponse> {
        val status = userRepository.deleteUser(id)

        val result = if (status) {
            DeleteResponse(true, "user has been deleted")
        } else {
            DeleteResponse(false, "user not been deleted")
        }

        return Mono.just(result)
    }
}
