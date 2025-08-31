package br.com.aroldofe.appointments.validation.impl

import br.com.aroldofe.appointments.validation.StrongPassword
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class StrongPasswordValidator : ConstraintValidator<StrongPassword, String?> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        // Null values are handled by other validators
        if (value == null) {
            return true
        }

        // Empty strings are handled by TrimmedNotBlank
        if (value.isEmpty()) {
            return true
        }

        // Check for whitespace - not allowed
        if (value.contains(" ")) {
            return false
        }

        // Check for at least 1 uppercase letter
        if (!value.any { it.isUpperCase() }) {
            return false
        }

        // Check for at least 1 lowercase letter
        if (!value.any { it.isLowerCase() }) {
            return false
        }

        // Check for at least 1 digit
        if (!value.any { it.isDigit() }) {
            return false
        }

        // Check for at least 1 special character
        val specialCharacters = "!@#$%^&*()_-+=<>?/[]{}|~."
        return value.any { it in specialCharacters }
    }
}

