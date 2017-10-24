package migration.simple.controller

import com.nhaarman.mockito_kotlin.whenever
import migration.simple.responses.StatsResponse
import migration.simple.service.StatsService
import migration.simple.types.Stats
import migration.simple.types.User
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("StatsController test")
class StatsControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @MockBean
    private lateinit var statsServiceMock: StatsService

    @Test
    fun `stats controller should return valid result`() {
        val expectedStats = Stats(
                2,
                User(1L, "name1", "surname1", 25),
                User(2L, "name2", "surname2", 30)
        )
        whenever(this.statsServiceMock.getStats()).thenReturn(expectedStats)
        val expectedResponse = StatsResponse(true, "user stats", expectedStats)

        val actualResponse = restTemplate.getForObject("/stats", StatsResponse::class.java)

        assertEquals("invalid stats response", expectedResponse, actualResponse)
    }
}
