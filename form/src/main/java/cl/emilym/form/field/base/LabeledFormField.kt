package cl.emilym.form.field.base

/**
 * An interface representing a form field with a label for its value.
 *
 * @param T The type of value this form field can hold.
 */
interface LabeledFormField<T> {

    /**
     * Gets the label corresponding to the provided value.
     *
     * @param value The value for which the label is required.
     * @return The label associated with the provided value, or null if no label is available.
     */
    fun getLabel(value: T?): String?

}