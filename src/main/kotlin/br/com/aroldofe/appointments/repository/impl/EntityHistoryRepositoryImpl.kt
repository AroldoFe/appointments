package br.com.aroldofe.appointments.repository.impl

import br.com.aroldofe.appointments.domain.EntityHistory
import br.com.aroldofe.appointments.repository.EntityHistoryRepository
import br.com.aroldofe.appointments.repository.jpa.EntityHistoryJpaRepository
import org.springframework.stereotype.Repository

@Repository
class EntityHistoryRepositoryImpl(
    private val repository: EntityHistoryJpaRepository
) : EntityHistoryRepository {
    override suspend fun save(entityHistory: EntityHistory): EntityHistory {
        return this.repository.save(entityHistory)
    }
}