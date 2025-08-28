package br.com.aroldofe.apointments.repository.jpa

import br.com.aroldofe.apointments.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserJpaRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User>

