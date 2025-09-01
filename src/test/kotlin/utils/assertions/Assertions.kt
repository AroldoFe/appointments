package utils.assertions

import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.request.UserUpdateRequest
import br.com.aroldofe.appointments.dto.response.UserResponse
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

object UserResponseAsserts {
    fun assertUserResponse(result: UserResponse?, request: UserRequest) {
        assertNotNull(result)
        assertThat(result.id).isNotNull()
        assertThat(result.name).isEqualTo(request.name)
        assertThat(result.email).isEqualTo(request.email.lowercase())
        assertThat(result.active).isTrue()
        assertThat(result.createdAt).isNotNull()
    }

    fun assertUserResponse(result: UserResponse?, request: UserUpdateRequest) {
        assertNotNull(result)
        assertThat(result.id).isNotNull()
        request.name?.let { assertThat(result.name).isEqualTo(it) }
        request.email?.let { assertThat(result.email).isEqualTo(it.lowercase()) }
        assertThat(result.active).isTrue()
        assertThat(result.createdAt).isNotNull()
    }
}