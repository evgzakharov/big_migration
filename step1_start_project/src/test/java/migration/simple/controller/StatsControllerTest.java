package migration.simple.controller;

import migration.simple.responses.StatsResponse;
import migration.simple.service.StatsService;
import migration.simple.types.Stats;
import migration.simple.types.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.*;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StatsService statsServiceMock;

    @Test
    public void statsControllerShouldReturnValidResult() {
        Stats expectedStats = new Stats(
                2,
                new User(1L, "name1", "surname1", 25),
                new User(2L, "name2", "surname2", 30)
        );
        given(this.statsServiceMock.getStats()).willReturn(expectedStats);
        StatsResponse expectedResponse = new StatsResponse(true, "user stats", expectedStats);

        StatsResponse actualResponse = restTemplate.getForObject("/stats", StatsResponse.class);

        assertEquals("invalid stats response", expectedResponse, actualResponse);
    }
}
