package cl.emilym.form.field.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class InputFormField<T>: BaseFormField<T>() {

    override var currentValue: T? = null
        set(value) {
            field = value
            _liveValue.tryEmit(field)
        }

    private val _liveValue = MutableStateFlow<T?>(null)
    final override val liveValue: Flow<T?> = _liveValue

}