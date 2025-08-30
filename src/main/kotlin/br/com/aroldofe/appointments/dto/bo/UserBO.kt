package br.com.aroldofe.appointments.dto.bo

import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.dto.response.UserResponse
import java.time.LocalDateTime

data class UserBO(
    val id: Long,
    val pubId: String,
    val name: String,
    val username: String,
    val password: String,
    val email: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
) {
    constructor(user: User): this(
        id = user.id!!,
        pubId = user.pubId,
        name = user.name,
        username = user.username,
        password = user.password,
        email = user.email,
        active = user.active,
        createdAt = user.creationTimestamp,
    )

    fun toResponse() = UserResponse(
        id = this.pubId,
        name = this.name,
        email = this.email,
        active = this.active,
        createdAt = this.createdAt.toString(),
    )
}
