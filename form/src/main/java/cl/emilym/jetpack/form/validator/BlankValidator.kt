package cl.emilym.jetpack.form.validator

import cl.emilym.jetpack.form.ValidationResult
import cl.emilym.jetpack.form.Validator

class BlankValidator(
    private val message: String = "Field cannot be blank"
): Validator<String> {

    override fun validate(value: String?): ValidationResult {
        return when (value.isNullOrBlank()) {
            false -> ValidationResult.Valid
            true -> ValidationResult.Invalid(message)
        }
    }
}