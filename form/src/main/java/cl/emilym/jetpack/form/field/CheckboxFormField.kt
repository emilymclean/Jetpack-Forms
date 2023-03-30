package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.FormField
import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.InputFormField

class CheckboxFormField(
    override val name: String,
    override val validators: List<Validator<Boolean>>
): InputFormField<Boolean>()