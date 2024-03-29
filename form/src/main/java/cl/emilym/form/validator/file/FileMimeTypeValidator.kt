package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo

class FileMimeTypeValidator<T: FileInfo>(
    val acceptableMimeTypes: List<String>,
    val message: String = "File type is not allowed"
): Validator<T> {

    override fun validate(value: T?): ValidationResult {
        value ?: return ValidationResult.Valid
        return when (acceptableMimeTypes.contains(value.mimeType)) {
            false -> ValidationResult.Valid
            true -> ValidationResult.Invalid(message)
        }
    }
}