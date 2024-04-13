package cl.emilym.form.extract

import cl.emilym.form.FormExtractor
import cl.emilym.form.FormField
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

/**
 * A class that implements FormExtractor interface to extract form data into a specific type T.
 *
 * @param T The type of object to extract form data into.
 * @property target The target class to construct using form data.
 */
class PokoFormExtractor<T: Any>(
    val target: KClass<T>
): FormExtractor<T> {

    /**
     * Extracts form data into an object of type T.
     *
     * @param fields The list of form fields containing data to be extracted.
     * @return An object of type T constructed using the extracted form data.
     */
    override fun extract(fields: List<FormField<*>>): T {
        val map = fields.associate {
            it.name.lowercase() to it.currentValue
        }
        val constructor = target.constructors.last()
        val args: Map<KParameter, Any?> = constructor.parameters.associateWith { map[it.name?.lowercase()] }
        return constructor.callBy(args)
    }

}