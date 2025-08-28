package br.com.aroldofe.apointments.dto.bo

import br.com.aroldofe.apointments.domain.Procedure
import br.com.aroldofe.apointments.dto.response.ProcedureResponse
import java.time.LocalDateTime

data class ProcedureBO(
    val id: Long,
    val pubId: String,
    val name: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val createdBy: UserBO,
) {
    constructor(procedure: Procedure) : this(
        id = procedure.id!!,
        pubId = procedure.pubId,
        name = procedure.name,
        active = procedure.active,
        createdAt = procedure.creationTimestamp,
        createdBy = UserBO(procedure.creationUser!!),
    )

    fun toResponse() = ProcedureResponse(
        id = this.pubId,
        name = this.name,
        active = this.active,
        createdAt = this.createdAt.toString(),
        createdBy = this.createdBy.toResponse(),
    )
}
