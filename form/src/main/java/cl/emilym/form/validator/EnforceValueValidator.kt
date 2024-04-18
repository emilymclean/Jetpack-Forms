package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for enforcing a specific value.
 *
 * @param T The type of value to validate.
 * @param message The error message to use for invalid values.
 * @param requiredValue The value that must be enforced.
 */
class EnforceValueValidator<T>(
    private val message: String,
    private val requiredValue: T?
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        if (value == requiredValue) return ValidationResult.Valid
        return ValidationResult.Invalid(message)
    }

}