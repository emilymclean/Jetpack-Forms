package cl.emilym.form.form

import cl.emilym.form.FormField

/**
 * Represents a simple form implementation with a list of form fields.
 *
 * @param fields The list of form fields associated with this form.
 */
class SimpleForm(
    override val fields: List<FormField<*>>
): BaseForm()