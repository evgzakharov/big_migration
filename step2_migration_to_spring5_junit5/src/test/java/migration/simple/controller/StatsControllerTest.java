package migration.simple.controller;

import migration.simple.responses.StatsResponse;
import migration.simple.service.StatsService;
import migration.simple.types.Stats;
import migration.simple.types.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("StatsController test")
public class StatsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StatsService statsServiceMock;

    @Test
    @DisplayName("stats controller should return valid result")
    public void statsControllerShouldReturnValidResult() {
        Stats expectedStats = new Stats(
                2,
                new User(1L, "name1", "surname1", 25),
                new User(2L, "name2", "surname2", 30)
        );
        when(this.statsServiceMock.getStats()).thenReturn(expectedStats);
        StatsResponse expectedResponse = new StatsResponse(true, "user stats", expectedStats);

        StatsResponse actualResponse = restTemplate.getForObject("/stats", StatsResponse.class);

        assertEquals("invalid stats response", expectedResponse, actualResponse);
    }
}
