package br.com.aroldofe.apointments.dto.response

data class ProcedureResponse (
    val id: String,
    val name: String,
    val active: Boolean,
    val createdAt: String,
    val createdBy: UserResponse,
)