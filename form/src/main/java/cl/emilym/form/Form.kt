package cl.emilym.form

import cl.emilym.form.form.SimpleForm
import kotlinx.coroutines.flow.Flow

/**
 * Represents a form.
 */
interface Form: Verifiable {

    /**
     * Initializes the form using the provided initializer.
     *
     * @param initializer The initializer to use for form initialization.
     */
    suspend fun initialize(initializer: FormInitializer)

    /**
     * Extracts form data using the provided extractor.
     *
     * @param extractor The extractor to use for form data extraction.
     * @return The extracted data.
     */
    fun <T> extract(extractor: FormExtractor<T>): T

    /**
     * Checks if the form has a field with the given name.
     *
     * @param name The name of the field to check.
     * @return True if the field exists, false otherwise.
     */
    fun hasField(name: String): Boolean

    /**
     * Retrieves the form field with the specified name.
     *
     * @param T The type of the form field
     * @param name The name of the field to retrieve.
     * @return The form field associated with the name.
     */
    fun <T> getField(name: String): FormField<T>

    companion object {

        /**
         * Creates a new basic form with the provided list of form fields.
         *
         * @param fields The list of form fields for the new form.
         * @return The created form instance.
         */
        fun create(fields: List<FormField<*>>): Form {
            return SimpleForm(fields)
        }

    }

}