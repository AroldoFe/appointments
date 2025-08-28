package br.com.aroldofe.apointments.exception.definition

class EntityAlreadyExistsException(entityName: String, pubId: String): RuntimeException() {
    override val message: String = "$entityName with id $pubId already exists"

    val errorResponseList = ErrorResponse(
        ErrorMessage(
            error = "entity_already_exists",
            description = message,
        )
    )
}