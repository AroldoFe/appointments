package br.com.aroldofe.apointments.repository.impl

import br.com.aroldofe.apointments.domain.User
import br.com.aroldofe.apointments.repository.UserRepository
import br.com.aroldofe.apointments.repository.jpa.UserJpaRepository
import br.com.aroldofe.apointments.repository.specification.UserSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val repository: UserJpaRepository
): UserRepository {
    override suspend fun save(user: User): User {
        return this.repository.save(user)
    }

    override suspend fun findById(id: Long): User? {
        return this.repository.findById(id).orElse(null)
    }

    override suspend fun findByPubId(pubId: String): User? {
        return this.findOne(UserSpecification.findByPubId(pubId))
    }

    override suspend fun exists(specification: Specification<User>): Boolean {
        return this.repository.exists(specification)
    }

    override suspend fun findAll(
        specification: Specification<User>,
        pageable: Pageable
    ): Page<User> {
        return this.repository.findAll(specification, pageable)
    }

    override suspend fun findOne(specification: Specification<User>): User? {
        return this.repository.findOne(specification).orElse(null)
    }
}