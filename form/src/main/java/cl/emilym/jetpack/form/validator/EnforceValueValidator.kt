package cl.emilym.jetpack.form.validator

import cl.emilym.jetpack.form.ValidationResult
import cl.emilym.jetpack.form.Validator

class EnforceValueValidator<T>(
    private val message: String,
    private val requiredValue: T
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        if (value == requiredValue) return ValidationResult.Valid
        return ValidationResult.Invalid(message)
    }

}