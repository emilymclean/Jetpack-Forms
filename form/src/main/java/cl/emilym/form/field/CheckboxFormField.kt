package cl.emilym.form.field

import cl.emilym.form.FormField
import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField

class CheckboxFormField(
    override val name: String,
    override val validators: List<Validator<Boolean>>
): InputFormField<Boolean>()