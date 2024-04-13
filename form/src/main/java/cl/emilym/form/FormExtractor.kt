package cl.emilym.form

/**
 * An interface for extracting form data into objects of type T.
 *
 * @param T The type of object to extract form data into.
 */
interface FormExtractor<T> {

    /**
     * Extracts form data into an object of type T.
     *
     * @param fields The list of form fields containing data to be extracted.
     * @return An object of type T constructed using the extracted form data.
     */
    fun extract(fields: List<FormField<*>>): T

}