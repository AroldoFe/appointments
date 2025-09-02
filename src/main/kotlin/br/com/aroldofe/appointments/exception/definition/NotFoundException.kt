package br.com.aroldofe.appointments.exception.definition

open class NotFoundException : ApiException(message = "Resource not found") {
    override val message: String = "Resource not found"
    override val errorResponseList: ErrorResponse by lazy {
        ErrorResponse(
            listOf(
                ErrorMessage(
                    error = "not_found",
                    description = message
                )
            )
        )
    }
}