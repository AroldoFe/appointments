package br.com.aroldofe.appointments.service

interface AuthenticatedCreation<C, R> {
    suspend fun create(data: C, userPubId: String): R
}

interface NonAuthenticatedCreation<C, R> {
    suspend fun create(data: C): R
}

interface AuthenticatedUpdate<C, R> {
    suspend fun update(id: String, data: C, userPubId: String): R
}

interface NonAuthenticatedUpdate<C, R> {
    suspend fun update(id: String, data: C): R
}

interface NonAuthenticatedGet<R> {
    suspend fun get(id: String): R
}

interface NonAuthenticatedInactivate<R> {
    suspend fun inactivate(id: String): R
}