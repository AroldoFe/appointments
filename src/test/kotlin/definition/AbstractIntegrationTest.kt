package definition

import java.time.Duration
import mock.container.PostgresContainerInitializer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.jdbc.JdbcTestUtils
import org.springframework.test.web.reactive.server.WebTestClient

@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [PostgresContainerInitializer.Initializer::class])
abstract class AbstractIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setConfig() {
        this.webTestClient = webTestClient.mutate()
            .responseTimeout(Duration.ofSeconds(60))
            .build()
    }

    @AfterEach
    fun tearDown() {
        this.clearAllTables()
    }

    fun clearAllTables() {
        val tables = listOf(
            "entity_history",
            "appointment",
            "procedure",
            "insurance",
            "patient_phone",
            "patient",
            "public.user",
        ).toTypedArray()

        JdbcTestUtils.deleteFromTables(jdbcTemplate, *tables)
    }
}