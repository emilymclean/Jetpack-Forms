package cl.emilym.jetpack.form.form

import cl.emilym.jetpack.form.Form
import cl.emilym.jetpack.form.FormExtractor
import cl.emilym.jetpack.form.FormField
import cl.emilym.jetpack.form.FormInitializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

abstract class BaseForm: Form {

    abstract val fields: List<FormField<*>>

    override suspend fun initialize(initializer: FormInitializer) {
        fields.forEach { initializer.init(it) }
    }

    override fun <T> extract(extractor: FormExtractor<T>): T {
        return extractor.extract(fields)
    }

    override fun hasField(name: String): Boolean {
        return fields.any { it.name == name }
    }

    override fun <T> getField(name: String): FormField<T> {
        return fields.first { it.name == name } as FormField<T>
    }

    override val isValid: Flow<Boolean>
        get() = combine(fields.map { it.isValid }) { it.fold(true) { acc, c -> acc && c } }

    override suspend fun validate(silent: Boolean): Boolean {
        return fields.map { it.validate(silent) }.all { it }
    }

}