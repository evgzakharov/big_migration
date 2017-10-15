package migration.simple.service;

import migration.simple.repository.UserRepository;
import migration.simple.types.Stats;
import migration.simple.types.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StatsServiceTest {

    @Test
    public void statsServiceShouldReturnRightData() {
        UserRepository userRepositoryMock = mock(UserRepository.class);

        User oldestUser = new User(3L, "UserName3", "Sr3", 30);
        User youngestUser = new User(1L, "UserName1", "Sr1", 21);

        when(userRepositoryMock.findAllUsers()).thenReturn(Arrays.asList(
                youngestUser,
                new User(2L, "UserName2", "Sr2", 25),
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
