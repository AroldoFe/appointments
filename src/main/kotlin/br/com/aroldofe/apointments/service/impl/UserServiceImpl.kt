package br.com.aroldofe.apointments.service.impl

import br.com.aroldofe.apointments.domain.User
import br.com.aroldofe.apointments.dto.bo.UserBO
import br.com.aroldofe.apointments.dto.converter.toBO
import br.com.aroldofe.apointments.dto.request.UserRequest
import br.com.aroldofe.apointments.repository.UserRepository
import br.com.aroldofe.apointments.repository.specification.UserSpecification
import br.com.aroldofe.apointments.service.NonAuthenticatedCreation
import br.com.aroldofe.apointments.utils.toSHA256
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