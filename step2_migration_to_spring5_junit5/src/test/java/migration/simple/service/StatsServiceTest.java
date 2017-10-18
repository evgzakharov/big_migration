package migration.simple.service;

import migration.simple.repository.UserRepository;
import migration.simple.types.Stats;
import migration.simple.types.User;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//for ide which not support JUnit5
//@RunWith(JUnitPlatform.class)
@DisplayName("Service test with mockito")
public class StatsServiceTest {

    @Test
    @DisplayName("stats service should return right data")
    public void statsServiceShouldReturnRightData() {
        UserRepository userRepositoryMock = mock(UserRepository.class);

        User youngestUser = new User(1L, "UserName1", "Sr1", 21);
        User someOtherUser = new User(2L, "UserName2", "Sr2", 25);
        User oldestUser = new User(3L, "UserName3", "Sr3", 30);

        when(userRepositoryMock.findAllUsers()).thenReturn(Arrays.asList(
                youngestUser,
                someOtherUser,
                oldestUser
        ));

        StatsService statsService = new StatsService(userRepositoryMock);

        Stats actualStats = statsService.getStats();

        Stats expectedStats = new Stats(
                3,
                oldestUser,
                youngestUser
        );

        Assert.assertEquals("invalid stats", expectedStats, actualStats);
    }
}


