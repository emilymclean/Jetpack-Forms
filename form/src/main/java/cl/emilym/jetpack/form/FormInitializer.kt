package cl.emilym.jetpack.form

interface FormInitializer {

    suspend fun <T> init(field: FormField<T>)

}