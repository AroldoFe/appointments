package br.com.aroldofe.appointments.validation.impl

import br.com.aroldofe.appointments.validation.Username
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class UsernameValidator : ConstraintValidator<Username, String?> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        // Null values are handled by other validators
        if (value == null) {
            return true
        }

        // Cannot start with a dot
        if (value.startsWith(".")) {
            return false
        }

        // Cannot start with a number
        if (value.trim().isNotBlank() && value[0].isDigit()) {
            return false
        }

        // Must contain only letters, numbers, underscores, and dots
        val regex = Regex("^[a-zA-Z][a-zA-Z0-9_.]*$")
        return regex.matches(value)
    }
}

