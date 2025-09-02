package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.dto.response.UserResponse
import br.com.aroldofe.appointments.enums.EntityName
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import br.com.aroldofe.appointments.exception.definition.ResourceNotActiveException
import br.com.aroldofe.appointments.exception.definition.UsernameOrEmailAlreadyExists
import br.com.aroldofe.appointments.repository.EntityHistoryRepository
import br.com.aroldofe.appointments.repository.UserRepository
import br.com.aroldofe.appointments.utils.toSHA256
import com.ninjasquad.springmockk.SpykBean
import definition.AbstractIntegrationTest
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import mock.dsl.createUserRequest
import mock.dsl.updateUserRequest
import org.junit.jupiter.api.Test
import utils.assertions.ErrorResponseAsserts.assertErrorResponse
import utils.assertions.UserResponseAsserts.assertUserResponse
import utils.extensions.postBadRequest
import utils.extensions.postCreated
import utils.extensions.putBadRequest
import utils.extensions.putOk

class UserRestControllerIntegratedTest : AbstractIntegrationTest() {

    @SpykBean
    lateinit var userRepository: UserRepository

    @SpykBean
    lateinit var entityHistoryRepository: EntityHistoryRepository

    @Test
    fun `should create user successfully`() {
        val userRequest = createUserRequest {}

        val result = webTestClient.postCreated<UserResponse>("/user", userRequest)

        assertUserResponse(result, userRequest)
    }

    @Test
    fun `should not create two users with the same email`() = runTest {
        userRepository.save(
            User(
                name = "John Doe",
                email = "email@email.com",
                username = "john_doe",
                password = "StrongP@ssw0rd".toSHA256(),
            )
        )

        val userRequest = createUserRequest {
            username("anotherusername")
        }

        val result = webTestClient.postBadRequest<ErrorResponse>("/user", userRequest)

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

    @Test
    fun `should edit user successfully`() = runTest {
        val user = userRepository.save(
            User(
                name = "John Doe",
                email = "email@email.com",
                username = "john_doe",
                password = "StrongP@ssw0rd".toSHA256(),
            )
        )

        val updateUserRequest = updateUserRequest {
            name("New Name")
            username("new_username")
            email("newemail@email.com")
            password("newStr0ngP@ssword")
        }

        val userEdited = webTestClient.putOk<UserResponse>("/user/${user.pubId}", updateUserRequest)

        assertUserResponse(userEdited, updateUserRequest)
        coVerify(exactly = 1) {
            entityHistoryRepository.save(match {
                it.entityId == user.id && it.entityName == EntityName.USER
            })
        }
    }

    @Test
    fun `should not edit inactive user`() = runTest {
        val user = userRepository.save(
            User(
                name = "John Doe",
                email = "email@email.com",
                username = "john_doe",
                password = "StrongP@ssw0rd".toSHA256(),
                active = false
            )
        )

        val updateUserRequest = updateUserRequest {
            name("New Name")
        }

        val result = webTestClient.putBadRequest<ErrorResponse>("/user/${user.pubId}", updateUserRequest)

        assertErrorResponse(result, *ResourceNotActiveException().errorResponseList.errorMessages.toTypedArray())
        coVerify(exactly = 0) { entityHistoryRepository.save(any()) }
    }

    @Test
    fun `should not edit username to one that already exists`() = runTest {
        val firstUser = userRepository.save(
            User(
                name = "Mary Jane",
                email = "mary_jane@email.com",
                username = "mary_jane",
                password = "StrongP@ssw0rd".toSHA256()
            )
        )

        val user = userRepository.save(
            User(
                name = "John Doe",
                email = "email@email.com",
                username = "john_doe",
                password = "StrongP@ssw0rd".toSHA256()
            )
        )

        val updateUserRequest = updateUserRequest {
            allNull()
            username(firstUser.username)
        }

        val result = webTestClient.putBadRequest<ErrorResponse>("/user/${user.pubId}", updateUserRequest)

        assertErrorResponse(result, *UsernameOrEmailAlreadyExists().errorResponseList.errorMessages.toTypedArray())
        coVerify(exactly = 1) {
            entityHistoryRepository.save(match {
                it.entityId == user.id && it.entityName == EntityName.USER
            })
        }
    }

    @Test
    fun `should not edit email to one that already exists`() = runTest {
        val firstUser = userRepository.save(
            User(
                name = "Mary Jane",
                email = "mary_jane@email.com",
                username = "mary_jane",
                password = "StrongP@ssw0rd".toSHA256()
            )
        )

        val user = userRepository.save(
            User(
                name = "John Doe",
                email = "email@email.com",
                username = "john_doe",
                password = "StrongP@ssw0rd".toSHA256()
            )
        )

        val updateUserRequest = updateUserRequest {
            allNull()
            email(firstUser.email)
        }

        val result = webTestClient.putBadRequest<ErrorResponse>("/user/${user.pubId}", updateUserRequest)

        assertErrorResponse(result, *UsernameOrEmailAlreadyExists().errorResponseList.errorMessages.toTypedArray())
        coVerify(exactly = 1) {
            entityHistoryRepository.save(match {
                it.entityId == user.id && it.entityName == EntityName.USER
            })
        }
    }

}