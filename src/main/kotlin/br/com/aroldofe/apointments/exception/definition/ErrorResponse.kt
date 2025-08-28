package br.com.aroldofe.apointments.exception.definition

data class ErrorMessage(
    val error: String,
    val description: String,
    val parameterName: String? = null,
)

data class ErrorResponse(
    val errorMessages: List<ErrorMessage> = mutableListOf()
) {
    constructor(errorMessage: ErrorMessage) : this(listOf(errorMessage))
}