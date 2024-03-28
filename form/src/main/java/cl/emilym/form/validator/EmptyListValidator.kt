package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

class EmptyListValidator<T>(
    private val message: String = "Field is required"
): Validator<List<T>> {

    override fun validate(value: List<T>?): ValidationResult {
        if (value?.isEmpty() != true) return ValidationResult.Valid
        return ValidationResult.Invalid(message)
    }
}