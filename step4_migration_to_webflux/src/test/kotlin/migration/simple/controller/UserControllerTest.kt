//package migration.simple.controller
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.nhaarman.mockito_kotlin.eq
//import com.nhaarman.mockito_kotlin.verify
//import com.nhaarman.mockito_kotlin.whenever
//import migration.simple.repository.UserRepository
//import migration.simple.responses.DeleteResponse
//import migration.simple.responses.UserAddResponse
//import migration.simple.responses.UserResponse
//import migration.simple.types.User
//import org.junit.Assert.assertEquals
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import org.springframework.http.HttpMethod
//import org.springframework.http.MediaType
//
//@ExtendWith(SpringExtension::class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DisplayName("UserController test")
//class UserControllerTest {
//
//    @Autowired
//    private lateinit var restTemplate: TestRestTemplate
//
//    @Autowired
//    private lateinit var objectMapper: ObjectMapper
//
//    @MockBean
//    private lateinit var userRepositoryMock: UserRepository
//
//    @Test
//    fun `all users should be return correctly`() {
//        val users = listOf(
//                User(1L, "name1", "surname1", 25),
//                User(2L, "name2", "surname2", 30)
//        )
//
//        whenever(this.userRepositoryMock.findAllUsers()).thenReturn(users)
//        val expectedResponse = UserResponse(true, "return users", users)
//
//        val actualResponse = restTemplate.getForObject("/users", UserResponse::class.java)
//
//        assertEquals("invalid users response", expectedResponse, actualResponse)
//    }
//
//    @Test
//    fun `user should be return correctly`() {
//        val user = User(1L, "name1", "surname1", 25)
//        whenever(this.userRepositoryMock.findUser(1L)).thenReturn(user)
//        whenever(this.userRepositoryMock.findUser(2L)).thenReturn(null)
//
//        val expectedResponse = UserResponse(true, "find user with requested id", listOf(user))
//        val actualResponse = restTemplate.getForObject("/user/{id}", UserResponse::class.java, 1L)
//        assertEquals("invalid user find response", expectedResponse, actualResponse)
//
//        val expectedMissedResponse = UserResponse(false, "user not found", emptyList())
//        val actualMissedResponse = restTemplate.getForObject("/user/{id}", UserResponse::class.java, 2L)
//        assertEquals("invalid user not find response", expectedMissedResponse, actualMissedResponse)
//    }
//
//    @Test
//    fun `user should be added correctly`() {
//        val newUser1 = User(null, "name", "surname", 15)
//        val newUser2 = User(null, "name2", "surname2", 18)
//
//        whenever(userRepositoryMock.addUser(newUser1)).thenReturn(15L)
//        whenever(userRepositoryMock.addUser(newUser2)).thenReturn(null)
//
//        val request = objectMapper.writeValueAsString(newUser1)
//        val expectedResponse = UserAddResponse(true, "user add successfully", 15L)
//        val actualResponse = putForObject("/user", request, UserAddResponse::class.java)
//        assertEquals("invalid user add response", expectedResponse, actualResponse)
//        verify(userRepositoryMock).addUser(eq(newUser1))
//
//        val errorRequest = objectMapper.writeValueAsString(newUser2)
//        val expectedErrorResponse = UserAddResponse(false, "user not added", -1L)
//        val actualErrorResponse = putForObject("/user", errorRequest, UserAddResponse::class.java)
//        assertEquals("invalid user error add response", expectedErrorResponse, actualErrorResponse)
//        verify(userRepositoryMock).addUser(eq(newUser2))
//    }
//
//    @Test
//    fun `user should be deleted correctly`() {
//        whenever(userRepositoryMock.deleteUser(1L)).thenReturn(true)
//        whenever(userRepositoryMock.deleteUser(2L)).thenReturn(false)
//
//        val expectedResponse = DeleteResponse(true, "user has been deleted")
//        val actualResponse = deleteForObject("/user/{id}", DeleteResponse::class.java, 1L)
//        assertEquals("invalid delete response", expectedResponse, actualResponse)
//        verify(userRepositoryMock).deleteUser(eq(1L))
//
//        val expectedErrorResponse = DeleteResponse(false, "user not been deleted")
//        val actualErrorResponse = deleteForObject("/user/{id}", DeleteResponse::class.java, 2L)
//        assertEquals("invalid delete error response", expectedErrorResponse, actualErrorResponse)
//        verify(userRepositoryMock).deleteUser(eq(1L))
//    }
//
//    private fun <T> putForObject(url: String, requestJson: String, responseType: Class<T>, vararg uriVariables: Any): T? {
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_JSON
//
//        val httpEntity = HttpEntity(requestJson, headers)
//        val response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, responseType, *uriVariables)
//        return response.body
//    }
//
//    private fun <T> deleteForObject(url: String, responseType: Class<T>, vararg uriVariables: Any): T? {
//        val httpEntity = HttpEntity("")
//        val response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, responseType, *uriVariables)
//        return response.body
//    }
//}