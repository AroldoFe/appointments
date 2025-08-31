package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.exception.definition.ErrorMessage
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import br.com.aroldofe.appointments.service.impl.UserServiceImpl
import com.ninjasquad.springmockk.MockkBean
import definition.AbstractContractTest
import mock.dsl.createUserRequest
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import utils.assertions.ErrorResponseAsserts.assertErrorResponse
import utils.extensions.postBadRequest

@WebFluxTest(controllers = [UserRestController::class])
class UserRestControllerContractTest : AbstractContractTest() {

    @MockkBean
    lateinit var userServiceImpl: UserServiceImpl

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidNames")
    fun `should return bad request when name is invalid`(invalidName: String, expectedMessages: List<ErrorMessage>) {
        val request = createUserRequest {
            name(invalidName)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidEmails")
    fun `should return bad request when email is invalid`(invalidEmail: String, expectedMessages: List<ErrorMessage>) {
        val request = createUserRequest {
            email(invalidEmail)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidUsernames")
    fun `should return bad request when username is invalid`(invalidUsername: String, expectedMessages: List<ErrorMessage>) {
        val request = createUserRequest {
            username(invalidUsername)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }
}