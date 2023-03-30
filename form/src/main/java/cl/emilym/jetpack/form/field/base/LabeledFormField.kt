package cl.emilym.jetpack.form.field.base

interface LabeledFormField<T> {

    fun getLabel(value: T?): String?

}