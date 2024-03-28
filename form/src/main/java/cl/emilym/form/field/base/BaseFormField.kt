package cl.emilym.form.field.base

import cl.emilym.form.FormField
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseFormField<T>: FormField<T> {

    override val isValid = MutableStateFlow(false)
    override val errorMessage = MutableStateFlow<String?>(null)

    protected abstract val validators: List<Validator<T>>

    override suspend fun validate(silent: Boolean): Boolean {
        val failed = validators
            .map { it.validate(currentValue) }
            .filterIsInstance<ValidationResult.Invalid>()
        return presentValidation(failed, silent)
    }

    protected suspend fun presentValidation(
        failed: List<ValidationResult.Invalid>,
        silent: Boolean
    ): Boolean {
        val valid = failed.isEmpty()
        isValid.emit(valid)

        if (!silent) {
            errorMessage.emit(failed.firstOrNull()?.message)
        }

        return valid
    }

}