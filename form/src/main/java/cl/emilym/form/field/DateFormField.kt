package cl.emilym.form.field

import cl.emilym.form.FormField
import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField
import cl.emilym.form.field.base.LabeledFormField
import java.text.DateFormat
import java.util.*

/**
 * Represents a form field for selecting dates.
 *
 * @property name The name of the form field.
 * @property validators List of validators for the date value.
 * @property dateFormat The format for displaying dates.
 */
class DateFormField(
    override val name: String,
    override val validators: List<Validator<Date>>,
    private val dateFormat: DateFormat
): InputFormField<Date>(), LabeledFormField<Date> {

    /**
     * Retrieves the label for a date value based on the specified date format.
     *
     * @param value The date value to get the label for.
     * @return The formatted date label.
     */
    override fun getLabel(value: Date?): String? {
        return value?.let { dateFormat.format(it) }
    }
}