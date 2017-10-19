package migration.simple.controller

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import migration.simple.Application
import migration.simple.beans
import migration.simple.responses.StatsResponse
import migration.simple.service.StatsService
import migration.simple.types.Stats
import migration.simple.types.User
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

@DisplayName("StatsController test")
open class StatsControllerTest {
    private val statsServiceMock = mock<StatsService>()

    private val port = 8181
    private val configuration = beans().apply {
        bean { statsServiceMock }
    }
    private val application = Application(port, configuration)

    private lateinit var client: WebClient

    @BeforeEach
    fun before() {
        reset(statsServiceMock)
        application.start()
        client = WebClient.create("http://localhost:$port")
    }

    @AfterEach
    fun after() {
        application.stop()
    }

    @Test
    fun `stats controller should return valid result`() {
        val expectedStats = Stats(
                2,
                User(1L, "name1", "surname1", 25),
                User(2L, "name2", "surname2", 30)
        )
        whenever(statsServiceMock.getStats()).thenReturn(expectedStats)

        val expectedResponse = StatsResponse(true, "user stats", expectedStats)
        val response: StatsResponse = "http://localhost:$port/stats".GET()

        assertEquals(expectedResponse, response, "invalid response")
    }
}
