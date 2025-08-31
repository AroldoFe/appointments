package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.dto.response.UserResponse
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import br.com.aroldofe.appointments.exception.definition.UsernameOrEmailAlreadyExists
import definition.AbstractIntegrationTest
import mock.dsl.createUserRequest
import org.junit.jupiter.api.Test
import utils.assertions.ErrorResponseAsserts.assertErrorResponse
import utils.assertions.UserResponseAsserts
import utils.extensions.postBadRequest
import utils.extensions.postCreated

class UserRestControllerIntegratedTest : AbstractIntegrationTest() {

    @Test
    fun `should create user successfully`() {
        val userRequest = createUserRequest {}

        val result = webTestClient.postCreated<UserResponse>("/user", userRequest)

        UserResponseAsserts.assertUserResponse(result, userRequest)
    }

    @Test
    fun `should not create two users with the same email`() {
        val userRequest = createUserRequest { }
        val anotherUserRequest = userRequest.copy(
            username = "anotherusername"
        )

        webTestClient.postCreated<UserResponse>("/user", userRequest)
        val result = webTestClient.postBadRequest<ErrorResponse>("/user", anotherUserRequest)

        assertErrorResponse(result, UsernameOrEmailAlreadyExists().errorResponseList.errorMessages.first())
    }

    @Test
    fun `should not create two users with the same username`() {
        val userRequest = createUserRequest { }
        val anotherUserRequest = userRequest.copy(
            email = "another@email.com"
        )

        webTestClient.postCreated<UserResponse>("/user", userRequest)
        val result = webTestClient.postBadRequest<ErrorResponse>("/user", anotherUserRequest)

        assertErrorResponse(result, UsernameOrEmailAlreadyExists().errorResponseList.errorMessages.first())
    }

}