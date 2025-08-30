package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.response.UserResponse
import br.com.aroldofe.appointments.service.impl.UserServiceImpl
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Validated
class UserRestController(
    private val service: UserServiceImpl
) {
    @PostMapping
    suspend fun createUser(
        @Valid
        @RequestBody request: UserRequest,
    ): ResponseEntity<UserResponse> {
        val user = service.create(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user.toResponse())
    }
}
