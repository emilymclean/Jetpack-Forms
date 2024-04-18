package cl.emilym.form

/**
 * Interface for initializing form fields.
 */
interface FormInitializer {

    /**
     * Initializes a form field.
     *
     * @param field The form field to initialize.
     * @param T The type of the form field.
     */
    suspend fun <T> init(field: FormField<T>)

}