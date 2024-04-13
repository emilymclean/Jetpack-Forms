package cl.emilym.form.validator

import android.util.Patterns
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import java.util.regex.Pattern

/**
 * Validator implementation for checking if a String is a valid email.
 *
 * @param message The error message to use for invalid values (default: "Email is invalid").
 */
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