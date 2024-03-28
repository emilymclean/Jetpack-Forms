package cl.emilym.form.extract

import cl.emilym.form.FormExtractor
import cl.emilym.form.FormField
import cl.emilym.form.MappedOutputFormField
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

class PokoFormExtractor<T: Any>(
    val target: KClass<T>
): FormExtractor<T> {

    override fun extract(fields: List<FormField<*>>): T {
        val map = fields.associate {
            it.name.lowercase() to
            (if (it is MappedOutputFormField<*>) it.currentOutputValue else it.currentValue)
        }
        val constructor = target.constructors.last()
        val args: Map<KParameter, Any?> = constructor.parameters.associateWith { map[it.name?.lowercase()] }
        return constructor.callBy(args)
    }

}