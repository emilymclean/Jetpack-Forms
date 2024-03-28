package cl.emilym.form

interface FormExtractor<T> {

    fun extract(fields: List<FormField<*>>): T

}