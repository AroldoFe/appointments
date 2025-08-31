package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.dto.response.UserResponse
import definition.AbstractIntegrationTest
import mock.dsl.createUserRequest
import org.junit.jupiter.api.Test
import utils.assertions.UserResponseAsserts
import utils.extensions.postCreated

class UserRestControllerIntegratedTest: AbstractIntegrationTest() {

    @Test
    fun `should create user successfully`() {
        val userRequest = createUserRequest { }

        val result = webTestClient.postCreated<UserResponse>("/user", userRequest)

        UserResponseAsserts.assertUserResponse(result, userRequest)
    }

}