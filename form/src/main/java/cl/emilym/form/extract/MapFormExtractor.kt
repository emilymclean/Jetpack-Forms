package cl.emilym.form.extract

import cl.emilym.form.FormExtractor
import cl.emilym.form.FormField

/**
 * A class for extracting form fields into a map.
 */
class MapFormExtractor: FormExtractor<Map<String, Any?>> {

    /**
     * Extracts form fields into a map.
     *
     * @param fields The list of form fields containing data to be extracted.
     * @return a map containing the extracted form field names and values.
     */
    override fun extract(fields: List<FormField<*>>): Map<String, Any?> {
        return fields.associate {
            val name = it.name
            name to it.currentValue
        }
    }
}