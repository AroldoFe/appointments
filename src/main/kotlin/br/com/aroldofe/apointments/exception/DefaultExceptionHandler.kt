package br.com.aroldofe.apointments.exception

import br.com.aroldofe.apointments.exception.definition.EntityAlreadyExistsException
import br.com.aroldofe.apointments.exception.definition.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
@ControllerAdvice
class DefaultExceptionHandler {
    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handle(exception: EntityAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(exception.errorResponseList)
    }
}