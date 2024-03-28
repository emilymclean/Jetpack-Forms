package cl.emilym.form.validator

import android.util.Patterns
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

class PhoneNumberValidator(
    private val message: String = "Phone number is invalid"
): Validator<String> {

    override fun validate(value: String?): ValidationResult {
        if (value.isNullOrBlank()) return ValidationResult.Valid
        if (!Patterns.PHONE.matcher(value).matches()) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }

}