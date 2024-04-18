package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo

/**
 * Validator implementation for validating file sizes.
 *
 * @param T The type of file to validate (must be FileInfo).
 * @param maximumSize The maximum allowed size of the file.
 * @param message The error message to use for files exceeding the maximum size.
 */
class FileSizeValidator<T: FileInfo>(
    val maximumSize: Long,
    val message: String = "File must be less than $maximumSize bytes"
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        value ?: return ValidationResult.Valid
        return when (value.size > maximumSize) {
            false -> ValidationResult.Valid
            true -> ValidationResult.Invalid(message)
        }
    }
}