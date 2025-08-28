package br.com.aroldofe.apointments.controller

import br.com.aroldofe.apointments.dto.request.ProcedureRequest
import br.com.aroldofe.apointments.dto.response.ProcedureResponse
import br.com.aroldofe.apointments.service.impl.ProcedureServiceImpl
import br.com.aroldofe.apointments.utils.Constants
import br.com.aroldofe.apointments.utils.ValidPatterns
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/procedure")
@Validated
class ProcedureRestController(
    private val procedureService: ProcedureServiceImpl
) {
    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun create(
        @Valid
        @RequestBody
        request: ProcedureRequest,
        @RequestHeader(name = Constants.X_USER_ID, required = true)
        @Pattern(
            regexp = ValidPatterns.USER_PUB_ID_REGEX,
            message = "Invalid user id format"
        )
        userPubId: String,
    ): ResponseEntity<ProcedureResponse> {
        val procedure = this.procedureService.create(request, userPubId)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(procedure.toResponse())
    }
}