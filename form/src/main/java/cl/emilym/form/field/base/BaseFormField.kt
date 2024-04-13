package cl.emilym.form.field.base

import cl.emilym.form.FormField
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * An abstract class representing a base form field that implements the FormField interface.
 *
 * @param T The type of value this form field can hold.
 */
abstract class BaseFormField<T>: FormField<T> {

    override val isValid = MutableStateFlow(false)
    override val errorMessage = MutableStateFlow<String?>(null)

    protected abstract val validators: List<Validator<T>>

    final override suspend fun validate(silent: Boolean): Boolean {
        return doValidation(silent)
    }

    /**
     * Performs the actual validation using the defined validators.
     *
     * @param silent If true, validation will be performed without emitting validation errors.
     * @return True if the form field is valid, false otherwise.
     */
    protected open fun doValidation(silent: Boolean): Boolean {
        val failed = validators
            .map { it.validate(currentValue) }
            .filterIsInstance<ValidationResult.Invalid>()
        return presentValidation(failed, silent)
    }

    /**
     * Handles presenting validation results.
     *
     * @param failed List of failed validation results.
     * @param silent If true, validation errors will not be emitted.
     * @return True if the form field is valid, false otherwise.
     */
    protected fun presentValidation(
        failed: List<ValidationResult.Invalid>,
        silent: Boolean
    ): Boolean {
        val valid = failed.isEmpty()
        isValid.tryEmit(valid)

        if (!silent) {
            errorMessage.tryEmit(failed.firstOrNull()?.message)
        }

        return valid
    }

}