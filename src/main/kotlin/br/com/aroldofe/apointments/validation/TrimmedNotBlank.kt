package br.com.aroldofe.apointments.validation

import br.com.aroldofe.apointments.validation.impl.TrimmedNotBlankValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [TrimmedNotBlankValidator::class])
annotation class TrimmedNotBlank(
    val message: String = "Field must not be blank after trimming",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

