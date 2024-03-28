package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.LabeledFormField
import cl.emilym.form.field.base.SelectionFormField

class SingleSelectionFormField<T>(
    override val name: String,
    override val validators: List<Validator<T>>,
    private val label: (T?) -> String?
): SelectionFormField<T>(), LabeledFormField<T> {

    override fun getLabel(value: T?): String? {
        return label(value)
    }

}