package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for checking if a boolean value is set to the required value.
 *
 * @param message The error message to use for blank values (default: "Field must be checked").
 * @param requiredState The state the field must be in to be valid.
 */
class CheckedValidator(
    private val message: String = "Field must be checked",
    private val requiredState: Boolean = true
): Validator<Boolean> {

    override fun validate(value: Boolean?): ValidationResult {
        return when (value == requiredState) {
            true -> ValidationResult.Valid
            false -> ValidationResult.Invalid(message)
        }
    }

}