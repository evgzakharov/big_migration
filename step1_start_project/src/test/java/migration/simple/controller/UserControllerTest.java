package migration.simple.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import migration.simple.repository.UserRepository;
import migration.simple.responses.Response;
import migration.simple.responses.UserAddResponse;
import migration.simple.responses.UserResponse;
import migration.simple.types.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepositoryMock;

    @Test
    public void allUsersShouldBeReturnCorrectly() {
        List<User> users = Arrays.asList(
                new User(1L, "name1", "surname1", 25),
                new User(2L, "name2", "surname2", 30)
        );

        when(this.userRepositoryMock.findAllUsers()).thenReturn(users);
        UserResponse expectedResponse = new UserResponse(true, "return users", users);

        UserResponse actualResponse = restTemplate.getForObject("/users", UserResponse.class);

        assertEquals("invalid users response", expectedResponse, actualResponse);
    }

    @Test
    public void userShouldBeReturnCorrectly() {
        User user = new User(1L, "name1", "surname1", 25);
        when(this.userRepositoryMock.findUser(1L)).thenReturn(Optional.of(user));
        when(this.userRepositoryMock.findUser(2L)).thenReturn(Optional.empty());

        UserResponse expectedResponse = new UserResponse(true, "find user with requested id", Collections.singletonList(user));
        UserResponse actualResponse = restTemplate.getForObject("/user/{id}", UserResponse.class, 1L);
        assertEquals("invalid user find response", expectedResponse, actualResponse);

        UserResponse expectedMissedResponse = new UserResponse(false, "user not found", Collections.emptyList());
        UserResponse actualMissedResponse = restTemplate.getForObject("/user/{id}", UserResponse.class, 2L);
        assertEquals("invalid user not find response", expectedMissedResponse, actualMissedResponse);
    }

    @Test
    public void userShouldBeAddedCorrectly() throws JsonProcessingException {
        User newUser1 = new User(null, "name", "surname", 15);
        User newUser2 = new User(null, "name2", "surname2", 18);

        when(userRepositoryMock.addUser(newUser1)).thenReturn(Optional.of(15L));
        when(userRepositoryMock.addUser(newUser2)).thenReturn(Optional.empty());

        String request = objectMapper.writeValueAsString(newUser1);
        UserAddResponse expectedResponse = new UserAddResponse(true, "user add successfully", 15L);
        UserAddResponse actualResponse = putForObject("/user", request, UserAddResponse.class);
        assertEquals("invalid user add response", expectedResponse, actualResponse);
        verify(userRepositoryMock).addUser(eq(newUser1));

        String errorRequest = objectMapper.writeValueAsString(newUser2);
        UserAddResponse expectedErrorResponse = new UserAddResponse(false, "user not added", -1L);
        UserAddResponse actualErrorResponse = putForObject("/user", errorRequest, UserAddResponse.class);
        assertEquals("invalid user error add response", expectedErrorResponse, actualErrorResponse);
        verify(userRepositoryMock).addUser(eq(newUser2));
    }

    @Test
    public void userShouldBeDeletedCorrectly() {
        when(userRepositoryMock.deleteUser(1L)).thenReturn(true);
        when(userRepositoryMock.deleteUser(2L)).thenReturn(false);

        Response expectedResponse = new Response(true, "user has been deleted");
        Response actualResponse = deleteForObject("/user/{id}", Response.class, 1L);
        assertEquals("invalid delete response", expectedResponse, actualResponse);
        verify(userRepositoryMock).deleteUser(eq(1L));

        Response expectedErrorResponse = new Response(false, "user not been deleted");
        Response actualErrorResponse = deleteForObject("/user/{id}", Response.class, 2L);
        assertEquals("invalid delete error response", expectedErrorResponse, actualErrorResponse);
        verify(userRepositoryMock).deleteUser(eq(1L));
    }

    private <T> T putForObject(String url, String requestJson, Class<T> responseType, Object... uriVariables) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, headers);
        final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, responseType, uriVariables);
        final T result = response.getBody();
        return result;
    }

    private <T> T deleteForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        final HttpEntity<String> httpEntity = new HttpEntity<>("");
        final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, responseType, uriVariables);
        final T result = response.getBody();
        return result;
    }
}