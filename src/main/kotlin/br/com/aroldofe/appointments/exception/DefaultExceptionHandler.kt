package br.com.aroldofe.appointments.exception

import br.com.aroldofe.appointments.exception.definition.ApiException
import br.com.aroldofe.appointments.exception.definition.EntityAlreadyExistsException
import br.com.aroldofe.appointments.exception.definition.ErrorMessage
import br.com.aroldofe.appointments.exception.definition.ErrorResponse
import br.com.aroldofe.appointments.exception.definition.NotFoundException
import jakarta.validation.ConstraintViolationException
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

    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(exception: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        val errorResponse = ErrorResponse(
            exception.constraintViolations.map {
                ErrorMessage(
                    error = "invalid_parameter",
                    description = it.message
                )
            }
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handle(exception: WebExchangeBindException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        lateinit var errorResponse: ErrorResponse
        if (!exception.bindingResult.fieldErrors.isEmpty()) {
            errorResponse = ErrorResponse(
                exception.bindingResult.fieldErrors.map {
                    ErrorMessage(
                        error = "invalid_field",
                        parameterName = it.field,
                        description = it.defaultMessage!!
                    )
                }
            )
        } else {
            errorResponse = ErrorResponse(
                exception.bindingResult.allErrors.map {
                    ErrorMessage(
                        error = "invalid_object",
                        description = it.defaultMessage!!
                    )
                }

            )
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorResponse)
    }

    @ExceptionHandler(ApiException::class)
    fun handle(exception: ApiException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.errorResponseList)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handle(exception: NotFoundException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.errorResponseList)
    }

    @ExceptionHandler(EntityAlreadyExistsException::class)
    fun handle(exception: EntityAlreadyExistsException): ResponseEntity<ErrorResponse> {
        this.logger.error(exception.message, exception)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(exception.errorResponseList)
    }
}