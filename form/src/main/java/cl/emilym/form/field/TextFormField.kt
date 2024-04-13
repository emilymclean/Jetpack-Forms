package cl.emilym.form.field

import cl.emilym.form.Validator
import cl.emilym.form.field.base.InputFormField
import cl.emilym.form.validator.*
import cl.emilym.form.validator.file.FileCountValidator

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

    /**
     * The character limit of the field, if one is provided
     */
    val characterLimit by lazy {
        validators.filterIsInstance(CharacterCountPeriodValidator::class.java).firstOrNull()?.maximum ?:
            validators.filterIsInstance(CharacterMaximumValidator::class.java).firstOrNull()?.maximum
    }

    /**
     * The minimum and maximum character count of the field, if one is provided
     */
    val characterLengthRange by lazy {
        val validator = validators.filterIsInstance(CharacterCountPeriodValidator::class.java).firstOrNull()
        validator ?: return@lazy null
        return@lazy IntRange(validator.minimum ?: 0, (validator.maximum?.minus(1) ?: Int.MAX_VALUE))
    }

    companion object {

        /**
         * Creates a phone number text field.
         *
         * @param name The name of the form field.
         * @param required Indicates if the field is required.
         * @return A TextFormField for phone numbers.
         */
        fun phone(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(PhoneNumberValidator()))
        }

        /**
         * Creates an email text field.
         *
         * @param name The name of the form field.
         * @param required Indicates if the field is required.
         * @return A TextFormField for email addresses.
         */
        fun email(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(EmailValidator()))
        }

        /**
         * Creates a website URL text field.
         *
         * @param name The name of the form field.
         * @param required Indicates if the field is required.
         * @return A TextFormField for website URLs.
         */
        fun website(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(URLValidator()))
        }

        /**
         * Creates an Australian Business Number (ABN) text field.
         *
         * @param name The name of the form field.
         * @param required Indicates if the field is required.
         * @return A TextFormField for ABNs.
         */
        fun abn(name: String, required: Boolean): TextFormField {
            return createField(name, required, listOf(ABNValidator()))
        }

        /**
         * Creates a basic text field.
         *
         * @param name The name of the form field.
         * @param required Indicates if the field is required.
         * @return A basic TextFormField.
         */
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