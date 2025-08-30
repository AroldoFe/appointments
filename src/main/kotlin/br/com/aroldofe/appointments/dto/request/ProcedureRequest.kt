package br.com.aroldofe.appointments.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ProcedureRequest(
    @field:NotBlank
    @field:Size(max = 256)
    val name: String
)