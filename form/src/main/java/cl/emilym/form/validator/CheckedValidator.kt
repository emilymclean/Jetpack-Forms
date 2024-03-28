package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

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