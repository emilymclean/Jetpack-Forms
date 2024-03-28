package cl.emilym.form

interface FormInitializer {

    suspend fun <T> init(field: FormField<T>)

}