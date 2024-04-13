package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField
import cl.emilym.form.validator.*

/**
 * Represents a form field for entering text.
 *
 * @property name The name of the form field.
 * @property validators List of validators for the text value.
 */
class TextFormField(
    override val name: String,
    override val validators: List<Validator<String>>,
): InputFormField<String>() {

    val characterLimit by lazy {
        validators.filterIsInstance(CharacterMaximumValidator::class.java).firstOrNull()?.maximum
    }

    companion object {

        fun phone(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(PhoneNumberValidator()))
        }

        fun email(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(EmailValidator()))
        }

        fun website(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(URLValidator()))
        }

        fun abn(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(ABNValidator()))
        }

        fun basic(name: String, required: Boolean): TextFormField {
            return createField(name, required, emptyList())
        }

        private fun createField(
            name: String,
            required: Boolean,
            validators: List<Validator<String>>
        ): TextFormField {
            return TextFormField(
                name,
                validators + if (required) listOf(RequiredValidator(), BlankValidator()) else listOf()
            )
        }

    }

}