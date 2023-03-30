package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.InputFormField
import cl.emilym.jetpack.form.field.base.LabeledFormField

class NumberFormField<T: Number>(
    override val name: String,
    override val validators: List<Validator<T>>
): InputFormField<T>(), LabeledFormField<T> {

    override fun getLabel(value: T?): String? {
        return value?.let { "$value" }
    }
}