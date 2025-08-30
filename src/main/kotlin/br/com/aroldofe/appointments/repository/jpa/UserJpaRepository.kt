package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserJpaRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User>

