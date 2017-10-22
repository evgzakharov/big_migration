package migration.simple.controller

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import migration.simple.Application
import migration.simple.beansConfiguration
import migration.simple.repository.UserRepository
import migration.simple.responses.DeleteResponse
import migration.simple.responses.UserAddResponse
import migration.simple.responses.UserResponse
import migration.simple.types.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.support.BeanDefinitionDsl

@DisplayName("UserController test")
class UserControllerTest {
    private var port = 8181

    private lateinit var userRepositoryMock: UserRepository

    private lateinit var configuration: BeanDefinitionDsl

    private lateinit var application: Application

    @BeforeEach
    fun before() {
        userRepositoryMock = mock()

        configuration = beansConfiguration {
            bean { userRepositoryMock }
        }
        application = Application(port, configuration)
        application.start()
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

        whenever(userRepositoryMock.findAllUsers()).thenReturn(users)
        val expectedResponse = UserResponse(true, "return users", users)

        val response: UserResponse = "http://localhost:$port/users".GET()

        assertEquals(expectedResponse, response, "invalid response")
    }

    @Test
    fun `user should be return correctly`() {
        val user = User(1L, "name1", "surname1", 25)
        whenever(userRepositoryMock.findUser(1L)).thenReturn(user)
        whenever(userRepositoryMock.findUser(2L)).thenReturn(null)

        val expectedResponse = UserResponse(true, "find user with requested id", listOf(user))
        val response: UserResponse = "http://localhost:$port/user/1".GET()

        assertEquals(expectedResponse, response, "not find exists user")

        val expectedMissedResponse = UserResponse(false, "user not found", emptyList())
        val missingResponse: UserResponse = "http://localhost:$port/user/2".GET()

        assertEquals(expectedMissedResponse, missingResponse, "invalid user response")
    }

    @Test
    fun `user should be added correctly`() {
        val newUser1 = User(null, "name", "surname", 15)
        val newUser2 = User(null, "name2", "surname2", 18)

        whenever(userRepositoryMock.addUser(newUser1)).thenReturn(15L)
        whenever(userRepositoryMock.addUser(newUser2)).thenReturn(null)

        val expectedResponse = UserAddResponse(true, "user add successfully", 15L)
        val response: UserAddResponse = "http://localhost:$port/user".PUT(newUser1)

        assertEquals(expectedResponse, response, "invalid add response")

        val expectedErrorResponse = UserAddResponse(false, "user not added", -1L)
        val errorResponse: UserAddResponse = "http://localhost:$port/user".PUT(newUser2)

        assertEquals(expectedErrorResponse, errorResponse, "invalid add response")
    }

    @Test
    fun `user should be deleted correctly`() {
        whenever(userRepositoryMock.deleteUser(1L)).thenReturn(true)
        whenever(userRepositoryMock.deleteUser(2L)).thenReturn(false)

        val expectedResponse = DeleteResponse(true, "user has been deleted")
        val response: DeleteResponse = "http://localhost:$port/user/1".DELETE()

        assertEquals(expectedResponse, response, "invalid response")

        val expectedErrorResponse = DeleteResponse(false, "user not been deleted")
        val errorResponse: DeleteResponse = "http://localhost:$port/user/2".DELETE()

        assertEquals(expectedErrorResponse, errorResponse, "invalid response")
    }
}
