package cl.emilym.jetpack.form

import kotlinx.coroutines.flow.Flow

interface Form {

    var isValid: Flow<Boolean>
    suspend fun validate(silent: Boolean): Boolean

    suspend fun initialize(initializer: FormInitializer)
    fun <T> extract(extractor: FormExtractor<T>)

    fun hasField(name: String): Boolean
    fun <T> getField(name: String): FormField<T>

}