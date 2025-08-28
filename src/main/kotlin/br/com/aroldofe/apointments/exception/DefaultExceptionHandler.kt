package br.com.aroldofe.apointments.exception

import br.com.aroldofe.apointments.exception.definition.ApiException
import br.com.aroldofe.apointments.exception.definition.EntityAlreadyExistsException
import br.com.aroldofe.apointments.exception.definition.ErrorMessage
import br.com.aroldofe.apointments.exception.definition.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
@ControllerAdvice
class DefaultExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    ErrorMessage(
                        error = "internal_server_error",
                        description = "Internal Server Error"
                    )
                )
            )
    }

    @ExceptionHandler(ApiException::class)
    fun handle(exception: ApiException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.errorResponseList)
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handle(exception: EntityAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(exception.errorResponseList)
    }
}