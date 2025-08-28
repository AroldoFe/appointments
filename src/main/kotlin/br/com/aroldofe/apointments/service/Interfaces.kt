package br.com.aroldofe.apointments.service

interface AuthenticatedCreation<C, R> {
    suspend fun create(data: C, userPubId: String?): R
}

interface NonAuthenticatedCreation<C, R> {
    suspend fun create(data: C): R
}