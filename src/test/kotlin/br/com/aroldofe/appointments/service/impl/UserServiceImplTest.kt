package br.com.aroldofe.appointments.service.impl

import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.exception.definition.ResourceNotActiveException
import br.com.aroldofe.appointments.exception.definition.UserNotFoundException
import br.com.aroldofe.appointments.exception.definition.UsernameOrEmailAlreadyExists
import br.com.aroldofe.appointments.repository.EntityHistoryRepository
import br.com.aroldofe.appointments.repository.UserRepository
import br.com.aroldofe.appointments.utils.toSHA256
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlin.test.assertFalse
import kotlinx.coroutines.test.runTest
import mock.dsl.createUserRequest
import mock.dsl.updateUserRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import utils.anotationTestCategory.UnitTest

@UnitTest
@ExtendWith(MockKExtension::class)
class UserServiceImplTest {
    @MockK
    lateinit var repository: UserRepository

    @MockK
    lateinit var entityHistoryRepository: EntityHistoryRepository

    @InjectMockKs
    lateinit var userServiceImpl: UserServiceImpl

    @Test
    fun `should not create user when email or username already exists`() = runTest {
        val userRequest = createUserRequest {}

        coEvery { repository.exists(any()) } returns true

        assertThrows<UsernameOrEmailAlreadyExists> {
            userServiceImpl.create(userRequest)
        }
    }

    @Test
    fun `should create user when email and username do not exist`() = runTest {
        val userRequest = createUserRequest {}

        val user = User(
            id = 1,
            name = userRequest.name,
            email = userRequest.email,
            username = userRequest.username,
            password = userRequest.password.toSHA256(),
        )

        coEvery { repository.exists(any()) } returns false
        coEvery { repository.save(any()) } answers { user }

        userServiceImpl.create(userRequest)
        assertEquals(userRequest.username, user.username)
        assertEquals(userRequest.email, user.email)
        assertEquals(userRequest.name, user.name)
        assertNotEquals(userRequest.password, user.password)
        assertEquals(userRequest.password.toSHA256(), user.password)
    }

    @Test
    fun `should return error when searching non-existing user`() {
        coEvery { repository.findByPubId(any()) } returns null

        assertThrows<UserNotFoundException> {
            runTest {
                userServiceImpl.get("non-existing-id")
            }
        }
    }

    @Test
    fun `should return user successfully when searching existing user`() = runTest {
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256(),
        )
        coEvery { repository.findByPubId(any()) } returns user

        val userBO = userServiceImpl.get(user.pubId)

        assertEquals(user.pubId, userBO.pubId)
        assertEquals(user.name, userBO.name)
        assertEquals(user.email, userBO.email)
        assertEquals(user.username, userBO.username)
        assertEquals(user.password, userBO.password)
    }

    @Test
    fun `should return error when editing non-existing user`() {
        val userUpdateRequest = updateUserRequest {}
        coEvery { repository.findByPubId(any()) } returns null

        assertThrows<UserNotFoundException> {
            runTest {
                userServiceImpl.update("non-existing-id", userUpdateRequest)
            }
        }
    }

    @Test
    fun `should return error when editing inactive user`() {
        val userUpdateRequest = updateUserRequest {}
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256(),
            active = false
        )
        coEvery { repository.findByPubId(any()) } returns user
        assertThrows<ResourceNotActiveException> {
            runTest {
                userServiceImpl.update(user.pubId, userUpdateRequest)
            }
        }
    }

    @Test
    fun `should return error when updating user with email or username that already exists`() {
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256()
        )

        val userUpdateRequest = updateUserRequest {}
        coEvery { repository.findByPubId(user.pubId) } returns user
        coEvery { repository.exists(any()) } returns true
        coEvery { entityHistoryRepository.save(any()) } answers { firstArg() }

        assertThrows<UsernameOrEmailAlreadyExists> {
            runTest {
                userServiceImpl.update(user.pubId, userUpdateRequest)
            }
        }
    }

    @Test
    fun `should update user successfully and save history`() = runTest {
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256()
        )
        val userUpdateRequest = updateUserRequest {}

        coEvery { repository.findByPubId(any()) } returns user
        coEvery { repository.exists(any()) } returns false
        coEvery { repository.save(any()) } answers { firstArg() }
        coEvery {
            entityHistoryRepository.save(match {
                it.entityId == user.id && it.entityName.name == "USER"
            })
        } answers { firstArg() }

        val userBO = userServiceImpl.update(user.pubId, userUpdateRequest)

        assertEquals(user.id, userBO.id)
        assertEquals(user.pubId, userBO.pubId)
        assertEquals(userUpdateRequest.name, userBO.name)
        assertEquals(userUpdateRequest.email, userBO.email)
        assertEquals(userUpdateRequest.username, userBO.username)
        assertEquals(userUpdateRequest.password!!.toSHA256(), userBO.password)
        assertEquals(user.active, userBO.active)

    }

    @Test
    fun `should return error when inactivating non-existing user`() {
        coEvery { repository.findByPubId(any()) } returns null

        assertThrows<UserNotFoundException> {
            runTest {
                userServiceImpl.inactivate("non-existing-id")
            }
        }
    }

    @Test
    fun `should return error when inactivating already inactive user`() {
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256(),
            active = false
        )
        coEvery { repository.findByPubId(any()) } returns user
        assertThrows<ResourceNotActiveException> {
            runTest {
                userServiceImpl.inactivate(user.pubId)
            }
        }
    }

    @Test
    fun `should inactivate user successfully and save history`() = runTest {
        val user = User(
            id = 1,
            name = "John Doe",
            email = "email@email.com",
            username = "john_doe",
            password = "StrongP@ssw0rd".toSHA256(),
        )
        coEvery { repository.findByPubId(any()) } returns user
        coEvery { repository.save(any()) } answers { firstArg() }
        coEvery {
            entityHistoryRepository.save(match {
                it.entityId == user.id && it.entityName.name == "USER"
            })
        } answers { firstArg() }

        val userBO = userServiceImpl.inactivate(user.pubId)

        assertEquals(user.id, userBO.id)
        assertEquals(user.pubId, userBO.pubId)
        assertEquals(user.name, userBO.name)
        assertEquals(user.email, userBO.email)
        assertEquals(user.username, userBO.username)
        assertEquals(user.password, userBO.password)
        assertFalse(userBO.active)
    }

}