package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.BaseFormField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Represents a form field that holds a value but it not actually visible to the user.
 *
 * @param T The type of the invisible value.
 * @property name The name of the form field.
 * @property value The invisible value.
 */
class InvisibleFormField<T>(
    override val name: String,
    private val value: T?
): BaseFormField<T>() {

    override val validators: List<Validator<T>> = listOf()
    override var currentValue: T?
        get() = value
        set(_) { throw UnsupportedOperationException() }
    override val liveValue: Flow<T?>
        get() = flowOf(value)

    init {
        isValid.tryEmit(true)
    }

}