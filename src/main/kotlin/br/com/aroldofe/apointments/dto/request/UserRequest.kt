package br.com.aroldofe.apointments.dto.request

import br.com.aroldofe.apointments.validation.TrimmedNotBlank
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserRequest (
    @field:TrimmedNotBlank(
        message = "Name must not be empty"
    )
    @field:Size(min = 3, max = 256, message = "Name must be between 3 and 256 characters")
    val name: String,
    
    @field:TrimmedNotBlank(
        message = "Email must not be empty"
    )
    @field:Email(message = "Email must be valid")
    val email: String,
    
    @field:TrimmedNotBlank(
        message = "Username must not be empty"
    )
    @field:Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    // TODO validar se username so tem letras e numeros e caracteres como _ ou .
    val username: String,
    
    @field:TrimmedNotBlank(
        message = "Password must not be empty"
    )
    @field:Size(min = 7, max = 256, message = "Password must be between 7 and 256 characters")
    // TODO criar validator para senha forte
    val password: String,
)