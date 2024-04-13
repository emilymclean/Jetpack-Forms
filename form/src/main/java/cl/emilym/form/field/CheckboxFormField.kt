package cl.emilym.form.field

import cl.emilym.form.FormField
import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField

/**
 * Represents a form field for checkboxes.
 *
 * @property name The name of the form field.
 * @property validators List of validators for the checkbox value.
 */
class CheckboxFormField(
    override val name: String,
    override val validators: List<Validator<Boolean>>
): InputFormField<Boolean>() {

    override val initialValue: Boolean = true

}