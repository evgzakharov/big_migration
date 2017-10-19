package migration.simple.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import migration.simple.Application
import migration.simple.beans
import migration.simple.repository.UserRepository
import migration.simple.responses.DeleteResponse
import migration.simple.responses.UserAddResponse
import migration.simple.responses.UserResponse
import migration.simple.types.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

@DisplayName("UserController test")
class UserControllerTest {
    private val userRepositoryMock = mock<UserRepository>()

    private val port = 8181
    private val configuration = beans().apply {
        bean { userRepositoryMock }
    }
    private var application = Application(port, configuration)

    private lateinit var client: WebClient

    private val objectMapper: ObjectMapper = ObjectMapper()

    @BeforeEach
    fun before() {
        reset(userRepositoryMock)
        application.start()
        client = WebClient.create("http://localhost:$port")
    }

    @AfterEach
    fun after() {
        application.stop()
    }

    @Test
    fun `all users should be return correctly`() {
        val users = listOf(
                User(1L, "name1", "surname1", 25),
                User(2L, "name2", "surname2", 30)
        )

        whenever(this.userRepositoryMock.findAllUsers()).thenReturn(users)
        val expectedResponse = UserResponse(true, "return users", users)

        client.get()
                .uri("/users")
                .retrieveResultForTest<UserResponse>()
                .expectNextMatches { it == expectedResponse }
                .verifyComplete()
    }

    @Test
    fun `user should be return correctly`() {
        val user = User(1L, "name1", "surname1", 25)
        whenever(this.userRepositoryMock.findUser(1L)).thenReturn(user)
        whenever(this.userRepositoryMock.findUser(2L)).thenReturn(null)

        val expectedResponse = UserResponse(true, "find user with requested id", listOf(user))

        client.get()
                .uri("/user/{id}", 1)
                .retrieveResultForTest<UserResponse>()
                .expectNextMatches { it == expectedResponse }
                .verifyComplete()

        val expectedMissedResponse = UserResponse(false, "user not found", emptyList())

        client.get()
                .uri("/user/{id}", 2)
                .retrieveResultForTest<UserResponse>()
                .expectNextMatches { it == expectedMissedResponse }
                .verifyComplete()
    }

    @Test
    fun `user should be added correctly`() {
        val newUser1 = User(null, "name", "surname", 15)
        val newUser2 = User(null, "name2", "surname2", 18)

        whenever(userRepositoryMock.addUser(newUser1)).thenReturn(15L)
        whenever(userRepositoryMock.addUser(newUser2)).thenReturn(null)

        val request = objectMapper.writeValueAsString(newUser1)
        val expectedResponse = UserAddResponse(true, "user add successfully", 15L)

        client.put()
                .uri("/user")
                .postBody(request)
                .retrieveResultForTest<UserAddResponse>()
                .expectNextMatches { it == expectedResponse }
                .verifyComplete()

        val errorRequest = objectMapper.writeValueAsString(newUser2)
        val expectedErrorResponse = UserAddResponse(false, "user not added", -1L)

        client.put()
                .uri("/user")
                .postBody(errorRequest)
                .retrieveResultForTest<UserAddResponse>()
                .expectNextMatches { it == expectedErrorResponse }
                .verifyComplete()
    }

    @Test
    fun `user should be deleted correctly`() {
        whenever(userRepositoryMock.deleteUser(1L)).thenReturn(true)
        whenever(userRepositoryMock.deleteUser(2L)).thenReturn(false)

        val expectedResponse = DeleteResponse(true, "user has been deleted")

        client.delete()
                .uri("/user/{id}", 1L)
                .retrieveResultForTest<DeleteResponse>()
                .expectNextMatches { it == expectedResponse }
                .verifyComplete()

        val expectedErrorResponse = DeleteResponse(false, "user not been deleted")

        client.delete()
                .uri("/user/{id}", 2L)
                .retrieveResultForTest<DeleteResponse>()
                .expectNextMatches { it == expectedErrorResponse }
                .verifyComplete()
    }


}