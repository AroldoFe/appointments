package utils.assertions

import br.com.aroldofe.appointments.exception.definition.ErrorMessage
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import kotlin.test.assertNotNull
import org.assertj.core.api.Assertions.assertThat

object ErrorResponseAsserts {
    fun assertErrorResponse(result: ErrorResponse?, vararg expectedErrors: ErrorMessage) {
        assertNotNull(result)
        assertThat(result.errorMessages)
            .hasSize(expectedErrors.size)
            .containsExactlyInAnyOrder(*expectedErrors)
    }
}
