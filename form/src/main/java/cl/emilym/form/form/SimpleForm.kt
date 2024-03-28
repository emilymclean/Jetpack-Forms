package cl.emilym.form.form

import cl.emilym.form.FormField

class SimpleForm(
    override val fields: List<FormField<*>>
): BaseForm()