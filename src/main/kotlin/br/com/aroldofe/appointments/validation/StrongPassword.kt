package br.com.aroldofe.appointments.validation

import br.com.aroldofe.appointments.validation.impl.StrongPasswordValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [StrongPasswordValidator::class])
annotation class StrongPassword(
    val message: String = "Password must contain at least 1 uppercase letter, 1 lowercase letter, " +
            "1 number, 1 special character, and no whitespace",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

