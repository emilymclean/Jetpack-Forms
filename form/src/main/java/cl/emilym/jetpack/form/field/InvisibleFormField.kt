package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.BaseFormField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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