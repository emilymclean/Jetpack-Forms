package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import java.util.Date

/**
 * Validator implementation for checking if String is shorter, longer or between specified lengths.
 *
 * Either `maximum` and/or `minimum` must be set.
 *
 * @param message The error message to use for invalid dates.
 * @param maximum The longest length the value can be (optional, defaults to null).
 * @param minimum The shortest length the value can be (optional, defaults to null).
 * @throws IllegalArgumentException if both maximum and minimum are null, or minimum is less than zero.
 */
class CharacterCountPeriodValidator(
    private val message: String,
    val maximum: Int? = null,
    val minimum: Int? = null
): Validator<String> {

    init {
        if (maximum == null && minimum == null)
            throw java.lang.IllegalArgumentException("Must provide either a minimum and/or a maximum count")
        if (minimum != null && minimum < 0)
            throw java.lang.IllegalArgumentException("Minimum must be >= 0")
    }

    override fun validate(value: String?): ValidationResult {
        if (value == null) return ValidationResult.Valid
        val count = value.length
        if (minimum != null && count < minimum) return ValidationResult.Invalid(message)
        if (maximum != null && count > maximum) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }

}