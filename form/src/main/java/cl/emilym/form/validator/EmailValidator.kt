package cl.emilym.form.validator

import android.util.Patterns
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

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