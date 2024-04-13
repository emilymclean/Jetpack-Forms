package cl.emilym.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.field.file.FileState

/**
 * Validator implementation for validating the states of files.
 *
 * Files must be in the state [cl.emilym.form.field.file.FileState.Complete][FileState.Complete] to pass validation.
 *
 * @param T The type of file to validate (must be FileInfo).
 * @param message The error message to use for invalid file states (default: "Files must be valid and uploaded").
 */
class FileStateValidator<T: FileInfo>(
    val message: String = "Files must be valid and uploaded"
): Validator<List<FileState<T>>> {

    override fun validate(value: List<FileState<T>>?): ValidationResult {
        value ?: return ValidationResult.Valid
        for (state in value) {
            if (state !is FileState.Complete) return ValidationResult.Invalid(message)
        }
        return ValidationResult.Valid
    }
}