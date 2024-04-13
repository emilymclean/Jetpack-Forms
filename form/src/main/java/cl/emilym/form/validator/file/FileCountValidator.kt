package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.field.file.FileState

/**
 * Validator implementation for validating the count of files in a list.
 *
 * @param T The type of files to validate (must be FileInfo).
 * @param minimum The minimum allowed file count (optional, defaults to null).
 * @param maximum The maximum allowed file count (optional, defaults to null).
 * @param message The error message to use for invalid file counts.
 * @throws IllegalArgumentException if both minimum and maximum are null.
 */
class FileCountValidator<T: FileInfo>(
    val minimum: Int?,
    val maximum: Int?,
    val message: String
): Validator<List<T>> {

    init {
        if (minimum == null && maximum == null) throw java.lang.IllegalArgumentException("Must set either/or minimum and maximum")
    }

    override fun validate(value: List<T>?): ValidationResult {
        value ?: return ValidationResult.Valid
        if (minimum != null && value.size < minimum) return ValidationResult.Invalid(message)
        if (maximum != null && value.size > maximum) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }
}