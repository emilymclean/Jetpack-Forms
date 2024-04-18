package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo

/**
 * Validator implementation for validating file MIME types.
 *
 * @param T The type of file to validate (must be FileInfo).
 * @param acceptableMimeTypes The list of acceptable MIME types.
 * @param message The error message to use for invalid MIME types (default: "File type is not allowed").
 */
class FileMimeTypeValidator<T: FileInfo>(
    val acceptableMimeTypes: List<String>,
    val message: String = "File type is not allowed"
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        value ?: return ValidationResult.Valid
        return when (acceptableMimeTypes.contains(value.mimeType)) {
            true -> ValidationResult.Valid
            false -> ValidationResult.Invalid(message)
        }
    }
}