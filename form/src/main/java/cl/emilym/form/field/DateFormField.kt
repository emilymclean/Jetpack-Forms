package cl.emilym.form.field

import cl.emilym.form.FormField
import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField
import cl.emilym.form.field.base.LabeledFormField
import java.text.DateFormat
import java.util.*

class DateFormField(
    override val name: String,
    override val validators: List<Validator<Date>>,
    private val dateFormat: DateFormat
): InputFormField<Date>(), LabeledFormField<Date> {

    override fun getLabel(value: Date?): String? {
        return value?.let { dateFormat.format(it) }
    }
}