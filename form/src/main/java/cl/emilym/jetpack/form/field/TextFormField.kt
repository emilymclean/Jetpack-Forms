package cl.emilym.jetpack.form.field

import cl.emilym.jetpack.form.Validator
import cl.emilym.jetpack.form.field.base.InputFormField
import cl.emilym.jetpack.form.validator.*

class TextFormField(
    override val name: String,
    override val validators: List<Validator<String>>
): InputFormField<String>() {

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