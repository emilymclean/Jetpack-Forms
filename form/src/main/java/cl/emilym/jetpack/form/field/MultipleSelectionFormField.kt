package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.LabeledFormField
import cl.emilym.jetpack.form.field.base.SelectionFormField

class MultipleSelectionFormField<T>(
    override val name: String,
    override val validators: List<Validator<List<T>>>,
    private val label: (T?) -> String?
): SelectionFormField<List<T>>(), LabeledFormField<T> {

    override fun getLabel(value: T?): String? {
        return label(value)
    }

}