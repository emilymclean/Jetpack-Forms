package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

class NumberPeriodValidator<T>(
    private val message: String,
    private val maximum: T? = null,
    private val minimum: T? = null
): Validator<T> where T: Number, T: Comparable<T> {

    override fun validate(value: T?): ValidationResult {
        if (value == null) return ValidationResult.Valid
        if (minimum != null && value < minimum) return ValidationResult.Invalid(message)
        if (maximum != null && value >= maximum) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }
}