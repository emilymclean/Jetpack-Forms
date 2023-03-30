package cl.emilym.jetpack.form.validator

import cl.emilym.jetpack.form.ValidationResult
import cl.emilym.jetpack.form.Validator

class EmptyListValidator<T>(
    private val message: String = "Field is required"
): Validator<List<T>> {

    override fun validate(value: List<T>?): ValidationResult {
        if (value?.isEmpty() != true) return ValidationResult.Valid
        return ValidationResult.Invalid(message)
    }
}