package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

class RequiredValidator<T>(
    private val message: String = "Field is required"
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        if (value == null) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }

}