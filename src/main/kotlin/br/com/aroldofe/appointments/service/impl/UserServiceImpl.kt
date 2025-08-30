package br.com.aroldofe.appointments.service.impl

import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.dto.bo.UserBO
import br.com.aroldofe.appointments.dto.converter.toBO
import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.repository.UserRepository
import br.com.aroldofe.appointments.repository.specification.UserSpecification
import br.com.aroldofe.appointments.service.NonAuthenticatedCreation
import br.com.aroldofe.appointments.utils.toSHA256
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val repository: UserRepository
): NonAuthenticatedCreation<UserRequest, UserBO> {

    override suspend fun create(
        data: UserRequest,
    ): UserBO {
        val user = User(
            name = data.name,
            email = data.email.lowercase(),
            username = data.username.lowercase(),
            password = data.password.toSHA256()
        )

        val existsSimilarUser = this.repository.exists(UserSpecification.findByUsernameOrEmail(user.username, user.email))
        if (existsSimilarUser) {
            throw RuntimeException("bla bla bla")
        }

        return this.repository.save(user).toBO()
    }
}