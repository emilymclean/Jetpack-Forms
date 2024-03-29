package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.field.file.FileState

class FileCountValidator<T: FileInfo>(
    val minimum: Int?,
    val maximum: Int?,
    val message: String
): Validator<List<T>> {

    init {
        if (minimum == null && maximum == null) throw IllegalStateException("Must set either/or minimum and maximum")
    }

    override fun validate(value: List<T>?): ValidationResult {
        value ?: return ValidationResult.Valid
        if (minimum != null && value.size < minimum) return ValidationResult.Invalid(message)
        if (maximum != null && value.size > maximum) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }
}