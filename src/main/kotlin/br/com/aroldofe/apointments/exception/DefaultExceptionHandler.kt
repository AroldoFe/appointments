package br.com.aroldofe.apointments.exception

import br.com.aroldofe.apointments.exception.definition.ApiException
import br.com.aroldofe.apointments.exception.definition.EntityAlreadyExistsException
import br.com.aroldofe.apointments.exception.definition.ErrorMessage
import br.com.aroldofe.apointments.exception.definition.ErrorResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException

@Component
@ControllerAdvice
class DefaultExceptionHandler(
    private val logger: Logger = LoggerFactory.getLogger(DefaultExceptionHandler::class.java)
) {
    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
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

    @ExceptionHandler(WebExchangeBindException::class)
    fun handle(exception: WebExchangeBindException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    exception.bindingResult.fieldErrors.map {
                        ErrorMessage(
                            error = "invalid_field",
                            parameterName = it.field,
                            description = it.defaultMessage!!
                        )
                    }
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