package cl.emilym.form

import cl.emilym.form.form.SimpleForm
import kotlinx.coroutines.flow.Flow

interface Form: Verifiable {

    suspend fun initialize(initializer: FormInitializer)
    fun <T> extract(extractor: FormExtractor<T>): T

    fun hasField(name: String): Boolean
    fun <T> getField(name: String): FormField<T>

    companion object {

        fun create(fields: List<FormField<*>>): Form {
            return SimpleForm(fields)
        }

    }

}