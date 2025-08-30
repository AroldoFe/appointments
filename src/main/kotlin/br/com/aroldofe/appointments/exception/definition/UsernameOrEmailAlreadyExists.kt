package br.com.aroldofe.appointments.exception.definition

class UsernameOrEmailAlreadyExists(
    override val message: String = "Username or email already exists",
): ApiException(message) {
    override val errorResponseList = ErrorResponse(
        ErrorMessage(
            error = "invalid_data",
            description = message,
        )
    )
}