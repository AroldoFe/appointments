package br.com.aroldofe.appointments.controller

import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.request.UserUpdateRequest
import br.com.aroldofe.appointments.dto.response.UserResponse
import br.com.aroldofe.appointments.service.impl.UserServiceImpl
import br.com.aroldofe.appointments.utils.ValidPatterns.USER_PUB_ID_REGEX
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PutMapping("/{id}")
    suspend fun updateUser(
        @Valid
        @Pattern(regexp = USER_PUB_ID_REGEX, message = "Invalid user ID format")
        @PathVariable id: String,
        @Valid @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserResponse> {
        val user = service.update(id, request)
        return ResponseEntity.ok(user.toResponse())
    }

}
