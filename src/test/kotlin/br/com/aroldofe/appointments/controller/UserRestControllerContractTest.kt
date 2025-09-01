package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.exception.definition.ErrorMessage
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import br.com.aroldofe.appointments.service.impl.UserServiceImpl
import br.com.aroldofe.appointments.utils.EntityType
import com.ninjasquad.springmockk.MockkBean
import definition.AbstractContractTest
import mock.RandomID
import mock.dsl.createUserRequest
import mock.dsl.updateUserRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import utils.assertions.ErrorResponseAsserts.assertErrorResponse
import utils.extensions.postBadRequest
import utils.extensions.putBadRequest

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
    fun `should return bad request when username is invalid`(
        invalidUsername: String,
        expectedMessages: List<ErrorMessage>
    ) {
        val request = createUserRequest {
            username(invalidUsername)
        }

        val result = this.client.postBadRequest<ErrorResponse>("/user", request)

        assertNotNull(result)
        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }

    @Test
    fun `should return bad request when id is invalid in update request`() {
        val invalidId = "invalid-id"
        val request = updateUserRequest {
            name("Valid Name")
        }

        val result = this.client.putBadRequest<ErrorResponse>(
            "/user/$invalidId",
            request
        )

        val expectedErrorMessage = ErrorMessage(
            error = "invalid_parameter",
            description = "Invalid user ID format"
        )

        assertNotNull(result)
        assertErrorResponse(result, expectedErrorMessage)
    }

    @Test
    fun `should return bad request when all fields are null in update request`() {
        val request = updateUserRequest {
            allNull()
        }

        val result = this.client.putBadRequest<ErrorResponse>(
            "/user/${RandomID.pubId(EntityType.USER)}",
            request
        )

        val expectedErrorMessage = ErrorMessage(
            error = "invalid_object",
            description = "At least one field must be provided for update"
        )

        assertNotNull(result)
        assertErrorResponse(result, expectedErrorMessage)
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidNames")
    fun `should return bad request when name is invalid in update request`(
        name: String,
        expectedMessages: List<ErrorMessage>
    ) {
        val request = updateUserRequest {
            allNull()
            name(name)
        }

        val result = this.client.putBadRequest<ErrorResponse>(
            "/user/${RandomID.pubId(EntityType.USER)}",
            request
        )

        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidUsernames")
    fun `should return bad request when username is invalid in update request`(
        username: String,
        expectedMessages: List<ErrorMessage>
    ) {
        val request = updateUserRequest {
            allNull()
            username(username)
        }

        val result = this.client.putBadRequest<ErrorResponse>(
            "/user/${RandomID.pubId(EntityType.USER)}",
            request
        )

        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }

    @ParameterizedTest
    @MethodSource("mock.methodSource.TestMethodSource#invalidEmails")
    fun `should return bad request when email is invalid in update request`(
        email: String,
        expectedMessages: List<ErrorMessage>
    ) {
        val request = updateUserRequest {
            allNull()
            email(email)
        }

        val result = this.client.putBadRequest<ErrorResponse>(
            "/user/${RandomID.pubId(EntityType.USER)}",
            request
        )

        assertErrorResponse(result, *expectedMessages.toTypedArray())
    }
}