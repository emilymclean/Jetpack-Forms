package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.LabeledFormField
import cl.emilym.form.field.base.SelectionFormField

/**
 * A class representing a form field that conceptually allows the selection of
 * multiple values from a set list of choices.
 *
 * @param T The type of value this form field can hold.
 * @property name The name of the form field.
 * @property validators List of validators for the value.
 */
class MultipleSelectionFormField<T>(
    override val name: String,
    override val validators: List<Validator<List<T>>>
): SelectionFormField<List<T>>() {

    override val initialValue: List<T> = listOf()

}