package br.com.aroldofe.appointments.exception.definition

class UserNotFoundException : NotFoundException() {
    override val message: String = "User not found"
    override val errorResponseList: ErrorResponse by lazy {
        ErrorResponse(
            listOf(
                ErrorMessage(
                    error = "user_not_found",
                    description = message
                )
            )
        )
    }
}