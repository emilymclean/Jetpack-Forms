package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

/**
 * Validator implementation for checking if a string value less than a specified number of characters.
 *
 * @param maximum The maximum allowable number of characters.
 */
@Deprecated("Use CharacterCountPeriodValidator")
class CharacterMaximumValidator(
    val maximum: Int
): Validator<String> {

    override fun validate(value: String?): ValidationResult {
        if (value.isNullOrBlank()) return ValidationResult.Valid
        return when (value.length > maximum) {
            false -> ValidationResult.Valid
            true -> ValidationResult.Invalid("Only ${maximum} characters allowed")
        }
    }

}