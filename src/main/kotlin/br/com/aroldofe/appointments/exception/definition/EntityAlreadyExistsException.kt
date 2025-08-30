package br.com.aroldofe.appointments.exception.definition

class EntityAlreadyExistsException(
    entityName: String, pubId: String,
    override val message: String = "$entityName with id $pubId already exists"
): ApiException(message) {

    override val errorResponseList = ErrorResponse(
        ErrorMessage(
            error = "entity_already_exists",
            description = message,
        )
    )
}