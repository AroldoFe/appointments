package br.com.aroldofe.appointments.validation.impl

import br.com.aroldofe.appointments.validation.TrimmedNotBlank
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class TrimmedNotBlankValidator : ConstraintValidator<TrimmedNotBlank, String?> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        // Null values are handled by @NotNull if needed
        if (value == null) {
            return true
        }

        return value.trim().isNotBlank()
    }
}