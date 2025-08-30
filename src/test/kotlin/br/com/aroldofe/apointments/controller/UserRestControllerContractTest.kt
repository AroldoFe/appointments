package br.com.aroldofe.apointments.controller

import br.com.aroldofe.apointments.exception.definition.ErrorMessage
import br.com.aroldofe.apointments.exception.definition.ErrorResponse
import br.com.aroldofe.apointments.service.impl.UserServiceImpl
import com.ninjasquad.springmockk.MockkBean
import definition.AbstractContractTest
import mock.dsl.createUserRequest
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import utils.assertions.ErrorResponseAsserts.assertErrorResponse
import utils.extensions.postBadRequest

@WebFluxTest(controllers = [UserRestController::class])
class UserRestControllerContractTest : AbstractContractTest() {

    @MockkBean
    lateinit var userServiceImpl: UserServiceImpl

    @ParameterizedTest
    @ValueSource(strings = ["", "  "])
    fun `should return bad request when name is blank`(invalidName: String) {
        val request = createUserRequest {
            name(invalidName)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        val expectedErrorMessageBlank = ErrorMessage(
            error = "invalid_field",
            parameterName = "name",
            description = "Name must not be empty"
        )

        val expectedErrorMessageSize = ErrorMessage(
            error = "invalid_field",
            parameterName = "name",
            description = "Name must be between 3 and 256 characters"
        )
        assertErrorResponse(result, expectedErrorMessageBlank, expectedErrorMessageSize)
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidNames")
    fun `should return bad request when name is invalid`(invalidName: String, expectedMessage: String) {
        val request = createUserRequest {
            name(invalidName)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        val expectedErrorMessage = ErrorMessage(
            error = "invalid_field",
            parameterName = "name",
            description = expectedMessage
        )
        assertErrorResponse(result, expectedErrorMessage)
    }

}