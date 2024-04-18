package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for checking if a value is required (not null).
 *
 * @param T The type of value to validate.
 * @param message The error message to use for null values (default: "Field is required").
 */
class RequiredValidator<T>(
    private val message: String = "Field is required"
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        if (value == null) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }

}