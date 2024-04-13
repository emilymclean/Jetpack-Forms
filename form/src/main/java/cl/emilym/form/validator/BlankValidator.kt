package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for checking if a string value is null or blank.
 *
 * @param message The error message to use for blank values (default: "Field is required").
 */
class BlankValidator(
    private val message: String = "Field is required"
): Validator<String> {

    override fun validate(value: String?): ValidationResult {
        return when (value.isNullOrBlank()) {
            false -> ValidationResult.Valid
            true -> ValidationResult.Invalid(message)
        }
    }
}