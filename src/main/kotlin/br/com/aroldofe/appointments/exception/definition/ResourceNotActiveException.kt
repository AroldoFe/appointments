package br.com.aroldofe.appointments.exception.definition

class ResourceNotActiveException : ApiException(message = "Resource not active") {
    override val message: String = "Resource not active"
    override val errorResponseList: ErrorResponse = ErrorResponse(
        listOf(
            ErrorMessage(
                error = "resource_not_active",
                description = message
            )
        )
    )
}