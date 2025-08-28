package br.com.aroldofe.apointments.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequest (
    @field:NotBlank
    @field:Size(min = 3, max = 256)
    val name: String,
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    @field:Size(min = 3, max = 100)
    // TODO validar se username so tem letras e numeros e caracteres como _ ou .
    val username: String,
    @field:NotBlank
    @field:Size(min = 7, max = 256)
    // TODO criar validator para senha forte
    val password: String,
)