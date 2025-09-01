package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.EntityHistory
import org.springframework.data.jpa.repository.JpaRepository

interface EntityHistoryJpaRepository: JpaRepository<EntityHistory, Long>