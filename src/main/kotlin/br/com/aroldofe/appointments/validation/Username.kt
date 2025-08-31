package br.com.aroldofe.appointments.validation

import br.com.aroldofe.appointments.validation.impl.UsernameValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UsernameValidator::class])
annotation class Username(
    val message: String = "Username must contain only letters, numbers, underscore, and dots. It cannot start with a number or dot.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

