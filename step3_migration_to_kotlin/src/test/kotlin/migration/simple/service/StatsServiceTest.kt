package migration.simple.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import migration.simple.repository.UserRepository
import migration.simple.types.Stats
import migration.simple.types.User
import org.junit.Assert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Service test with mockito")
class StatsServiceTest {

    @Test
    @DisplayName("stats service should return right data")
    fun statsServiceShouldReturnRightData() {
        val userRepositoryMock = mock<UserRepository>()

        val youngestUser = User(1L, "UserName1", "Sr1", 21)
        val someOtherUser = User(2L, "UserName2", "Sr2", 25)
        val oldestUser = User(3L, "UserName3", "Sr3", 30)

        whenever(userRepositoryMock.findAllUsers()).thenReturn(Arrays.asList(
                youngestUser,
                someOtherUser,
                oldestUser
        ))

        val statsService = StatsService(userRepositoryMock)

        val actualStats = statsService.getStats()

        val expectedStats = Stats(
                3,
                oldestUser,
                youngestUser
        )

        Assert.assertEquals("invalid stats", expectedStats, actualStats)
    }
}


