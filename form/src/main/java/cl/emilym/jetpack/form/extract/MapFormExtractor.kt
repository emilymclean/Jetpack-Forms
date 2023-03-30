package cl.emilym.jetpack.form.extract

import cl.emilym.jetpack.form.FormExtractor
import cl.emilym.jetpack.form.FormField

class MapFormExtractor: FormExtractor<Map<String, Any?>> {

    override fun extract(fields: List<FormField<*>>): Map<String, Any?> {
        return fields.associate {
            val name = it.name
            name to it.currentValue
        }
    }
}