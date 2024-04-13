package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for checking if a list is empty.
 *
 * @param T The type of elements in the list.
 * @param message The error message to use for empty lists (default: "Field is required").
 */
class EmptyListValidator<T>(
    private val message: String = "Field is required"
): Validator<List<T>> {

    override fun validate(value: List<T>?): ValidationResult {
        if (value?.isEmpty() != true) return ValidationResult.Valid
        return ValidationResult.Invalid(message)
    }
}