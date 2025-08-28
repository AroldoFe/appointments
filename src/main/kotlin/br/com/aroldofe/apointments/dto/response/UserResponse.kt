package br.com.aroldofe.apointments.dto.response

data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val active: Boolean,
    val createdAt: String,
)
