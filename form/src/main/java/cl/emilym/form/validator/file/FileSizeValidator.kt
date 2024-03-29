package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo

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