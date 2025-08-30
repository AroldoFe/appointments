package br.com.aroldofe.appointments.exception.definition

open class ApiException(
    override val message: String
): RuntimeException(message){
    open val errorResponseList: ErrorResponse = ErrorResponse()
}