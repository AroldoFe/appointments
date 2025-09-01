package br.com.aroldofe.appointments.validation.impl

import br.com.aroldofe.appointments.dto.request.UserUpdateRequest
import br.com.aroldofe.appointments.validation.AtLeastOneNotNull
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class AtLeastOneNotNullValidator : ConstraintValidator<AtLeastOneNotNull, UserUpdateRequest> {

    override fun isValid(value: UserUpdateRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true // Null validation is handled separately
        }

        return value.name != null ||
               value.email != null ||
               value.username != null ||
               value.password != null
    }
}

