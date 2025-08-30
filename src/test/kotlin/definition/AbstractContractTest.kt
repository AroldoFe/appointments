package definition

import br.com.aroldofe.apointments.exception.DefaultExceptionHandler
import java.time.Duration
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import utils.anotationTestCategory.UnitTest

@UnitTest
@Execution(ExecutionMode.CONCURRENT)
@WebFluxTest
@Import(
    DefaultExceptionHandler::class,
)
abstract class AbstractContractTest {
    @Autowired
    lateinit var client: WebTestClient

    fun setUp() {
        client = client.mutate()
            .responseTimeout(Duration.ofSeconds(60))
            .build()
    }
}