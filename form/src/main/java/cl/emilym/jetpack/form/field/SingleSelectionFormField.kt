package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.LabeledFormField
import cl.emilym.jetpack.form.field.base.SelectionFormField

class SingleSelectionFormField<T>(
    override val name: String,
    override val validators: List<Validator<T>>,
    private val label: (T?) -> String?
): SelectionFormField<T>(), LabeledFormField<T> {

    override fun getLabel(value: T?): String? {
        return label(value)
    }

}