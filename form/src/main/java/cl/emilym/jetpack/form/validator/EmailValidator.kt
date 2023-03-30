package cl.emilym.jetpack.form.validator

import android.util.Patterns
import cl.emilym.jetpack.form.ValidationResult
import cl.emilym.jetpack.form.Validator

class EmailValidator(
    private val message: String = "Email is invalid"
): Validator<String> {

    override fun validate(value: String?): ValidationResult {
        if (value.isNullOrBlank()) return ValidationResult.Valid
        return when (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            true -> ValidationResult.Valid
            false -> ValidationResult.Invalid(message)
        }
    }

}