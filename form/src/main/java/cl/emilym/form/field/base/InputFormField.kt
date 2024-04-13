package cl.emilym.form.field.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

/**
 * An abstract class representing an input form field that extends BaseFormField.
 *
 * @param T The type of value this form field can hold.
 */
abstract class InputFormField<T>: BaseFormField<T>() {

    /**
     * The initial value of the form field.
     */
    protected open val initialValue: T? = null

    override var currentValue: T? = initialValue
        set(value) {
            field = value
            _liveValue.tryEmit(field)
            doValidation(errorMessage.value == null)
        }

    private val _liveValue = MutableStateFlow<T?>(null)
    final override val liveValue: Flow<T?> = _liveValue

}