package br.com.aroldofe.appointments.repository

import br.com.aroldofe.appointments.domain.EntityHistory

interface EntityHistoryRepository {
    suspend fun save(entityHistory: EntityHistory): EntityHistory
}