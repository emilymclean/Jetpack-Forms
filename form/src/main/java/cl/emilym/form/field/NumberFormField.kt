package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField
import cl.emilym.form.field.base.LabeledFormField

/**
 * Represents a form field for entering numeric values.
 *
 * @param T The type of the numeric value (e.g., Int, Double, Float, etc.).
 * @property name The name of the form field.
 * @property validators List of validators for the numeric value.
 */
class NumberFormField<T: Number>(
    override val name: String,
    override val validators: List<Validator<T>>
): InputFormField<T>(), LabeledFormField<T> {

    override fun getLabel(value: T?): String? {
        return value?.let { "$value" }
    }
}