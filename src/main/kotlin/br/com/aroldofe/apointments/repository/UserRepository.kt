package br.com.aroldofe.apointments.repository

import br.com.aroldofe.apointments.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface UserRepository {
    suspend fun save(user: User): User
    suspend fun findById(id: Long): User?
    suspend fun findByPubId(pubId: String): User?

    suspend fun exists(specification: Specification<User>): Boolean
    suspend fun findAll(specification: Specification<User>, pageable: Pageable): Page<User>
    suspend fun findOne(specification: Specification<User>): User?

}