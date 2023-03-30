package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.SelectionFormField

class MultipleSelectionFormField<T>(
    override val name: String,
    override val validators: List<Validator<List<T>>>
): SelectionFormField<List<T>>()