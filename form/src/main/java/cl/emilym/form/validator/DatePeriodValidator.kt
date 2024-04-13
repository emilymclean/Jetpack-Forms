package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import java.util.*

/**
 * Validator implementation for checking if date is before, after, or between specified dates.
 *
 * Either maximum and/or minimum must be set.
 *
 * @param message The error message to use for invalid dates.
 * @param maximum The latest date the value can be (optional, defaults to null).
 * @param minimum The earliest date the value can be (optional, defaults to null).
 * @throws IllegalArgumentException if both maximum and minimum are null.
 */
class DatePeriodValidator(
    private val message: String,
    private val maximum: Date? = null,
    private val minimum: Date? = null
): Validator<Date> {

    init {
        if (maximum == null && minimum == null)
            throw java.lang.IllegalArgumentException("Must provide either a minimum and/or a maximum date")
    }

    override fun validate(value: Date?): ValidationResult {
        if (value == null) return ValidationResult.Valid
        if (minimum != null && value < minimum) return ValidationResult.Invalid(message)
        if (maximum != null && value >= maximum) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }
}