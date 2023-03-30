package cl.emilym.jetpack.form

interface FormExtractor<T> {

    fun extract(fields: List<FormField<*>>): T

}