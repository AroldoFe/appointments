package br.com.aroldofe.appointments.service.impl

import br.com.aroldofe.appointments.domain.EntityHistory
import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.dto.bo.UserBO
import br.com.aroldofe.appointments.dto.converter.toBO
import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.request.UserUpdateRequest
import br.com.aroldofe.appointments.enums.EntityName
import br.com.aroldofe.appointments.exception.definition.ResourceNotActiveException
import br.com.aroldofe.appointments.exception.definition.UserNotFoundException
import br.com.aroldofe.appointments.exception.definition.UsernameOrEmailAlreadyExists
import br.com.aroldofe.appointments.repository.EntityHistoryRepository
import br.com.aroldofe.appointments.repository.UserRepository
import br.com.aroldofe.appointments.repository.specification.UserSpecification
import br.com.aroldofe.appointments.service.UserService
import br.com.aroldofe.appointments.utils.toJsonString
import br.com.aroldofe.appointments.utils.toSHA256
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val repository: UserRepository,
    private val entityHistoryRepository: EntityHistoryRepository
) : UserService {

    @Transactional(readOnly = false)
    override suspend fun create(
        data: UserRequest,
    ): UserBO {
        val user = User(
            name = data.name,
            email = data.email.lowercase(),
            username = data.username.lowercase(),
            password = data.password.toSHA256()
        )

        val existsSimilarUser = this.repository.exists(
            UserSpecification.findByUsernameOrEmail(user.username, user.email)
        )
        if (existsSimilarUser) {
            throw UsernameOrEmailAlreadyExists()
        }

        return this.repository.save(user).toBO()
    }

    @Transactional(readOnly = false)
    override suspend fun update(
        id: String,
        data: UserUpdateRequest
    ): UserBO {
        val user = this.repository.findByPubId(id)
            ?: throw UserNotFoundException()

        if (!user.active) {
            throw ResourceNotActiveException()
        }

        this.saveHistory(user)

        data.name?.let { user.name = it }
        data.email?.let { user.email = it.lowercase() }
        data.username?.let { user.username = it.lowercase() }
        data.password?.let { user.password = it.toSHA256() }

        val existsSimilarUser =
            this.repository.exists(UserSpecification.findByUsernameOrEmail(user.username, user.email, id))
        if (existsSimilarUser) {
            throw UsernameOrEmailAlreadyExists()
        }

        return this.repository.save(user).toBO()
    }

    private suspend fun saveHistory(user: User) {
        val snapshot = user.toJsonString()
        val history = EntityHistory(
            entityName = EntityName.USER,
            entityId = user.id!!,
            history = snapshot,
            createdBy = user
        )
        this.entityHistoryRepository.save(history)
    }
}