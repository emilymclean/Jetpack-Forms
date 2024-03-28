package cl.emilym.form.extract

import cl.emilym.form.FormExtractor
import cl.emilym.form.FormField

class MapFormExtractor: FormExtractor<Map<String, Any?>> {

    override fun extract(fields: List<FormField<*>>): Map<String, Any?> {
        return fields.associate {
            val name = it.name
            name to it.currentValue
        }
    }
}