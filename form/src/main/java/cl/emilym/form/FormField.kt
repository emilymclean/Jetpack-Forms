package cl.emilym.form

import kotlinx.coroutines.flow.Flow

/**
 * An interface representing a form field that can hold a nullable value of type T.
 *
 * @param T The type of value this form field can hold.
 */
interface FormField<T>: Verifiable {

    /**
     * The name of the form field.
     */
    val name: String

    /**
     * The current value of the form field.
     */
    var currentValue: T?
    /**
     * A flow representing the current value of the form field.
     */
    val liveValue: Flow<T?>

    /**
     * A flow representing the error message associated with the form field, if any.
     */
    val errorMessage: Flow<String?>

}