package cl.emilym.form.field.base

interface LabeledFormField<T> {

    fun getLabel(value: T?): String?

}