package cl.emilym.form.field.base

/**
 * An abstract class representing a form field that conceptually allows the selection of
 * a value(s) from a choice of multiple.
 *
 * @param T The type of value this form field can hold.
 */
abstract class SelectionFormField<T>: InputFormField<T>()