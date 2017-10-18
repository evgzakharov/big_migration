package migration.simple.controller

import migration.simple.repository.UserRepository
import migration.simple.responses.DeleteResponse
import migration.simple.responses.UserAddResponse
import migration.simple.responses.UserResponse
import migration.simple.types.User
import org.springframework.web.reactive.function.server.RouterFunctionDsl
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux

open class UserController(private val userRepository: UserRepository) {
    fun nest(): RouterFunctionDsl.() -> Unit = {
        GET("/users") { ok().body(users()) }
        GET("/user/{id}") { ok().body(users(it.pathVariable("id").toLong())) }
        PUT("/user") { ok().body(addUser(it.bodyToFlux(User::class.java))) }
        DELETE("/user/{id}") { ok().body(deleteUser(it.pathVariable("id").toLong())) }
    }

    open fun users(): Flux<UserResponse> {
        val users = userRepository.findAllUsers()

        return Flux.just(UserResponse(true, "return users", users))
    }

    open fun users(userId: Long): Flux<UserResponse> {
        val user = userRepository.findUser(userId)

        val response = user
                ?.let { UserResponse(true, "find user with requested id", listOf(it)) }
                ?: UserResponse(false, "user not found", emptyList())

        return Flux.just(response)
    }

    open fun addUser(user: Flux<User>): Flux<UserAddResponse> = user.map {
        val addIndex = userRepository.addUser(it)

        addIndex
                ?.let { UserAddResponse(true, "user add successfully", it) }
                ?: UserAddResponse(false, "user not added", -1L)
    }

    open fun deleteUser(id: Long): Flux<DeleteResponse> {
        val status = userRepository.deleteUser(id)

        val result = if (status) {
            DeleteResponse(true, "user has been deleted")
        } else {
            DeleteResponse(false, "user not been deleted")
        }

        return Flux.just(result)
    }
}
