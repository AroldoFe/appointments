package br.com.aroldofe.appointments.validation

import br.com.aroldofe.appointments.validation.impl.AtLeastOneNotNullValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [AtLeastOneNotNullValidator::class])
annotation class AtLeastOneNotNull(
    val message: String = "At least one field must be provided for update",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

