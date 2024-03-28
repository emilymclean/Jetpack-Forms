package cl.emilym.form.compose.field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import cl.emilym.form.FormField
import cl.emilym.form.field.base.LabeledFormField

@Composable
inline fun <reified T: Number> NumberFormFieldWidget(
    field: FormField<T>,
    modifier: Modifier = Modifier,
    noinline placeholder: (@Composable () -> Unit)? = null,
    noinline trailingIcon: (@Composable () -> Unit)? = null,
    noinline leadingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    val decimal = when(T::class) {
        Double::class, Float::class -> true
        else -> false
    }
    val value = field.liveValue.collectAsState(null).value?.let {
        (field as? LabeledFormField<T>)?.getLabel(it) ?: "$it"
    }
    FormFieldWrapper(field, modifier) { error ->
        TextField(
            value = value ?: "",
            onValueChange = {
                val v = if (decimal) it else it.replace(".", "")
                if (it.isEmpty()) {
                    field.currentValue = null
                } else {
                    val casted: T? = v.toNumberOrNull()
                    if (casted != null) {
                        field.currentValue = casted
                    }
                }
            },
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType =
                if (decimal) KeyboardType.Decimal else KeyboardType.Number
            ),
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            isError = error,
            enabled = true,
        )
    }
}

inline fun <reified T: Number> String.toNumber(): T {
    return when(T::class) {
        Int::class -> toInt() as T
        Double::class -> toDouble() as T
        Float::class -> toFloat() as T
        Long::class -> toLong() as T
        Short::class -> toShort() as T
        Char::class -> toCharArray()[0] as T
        Byte::class -> toByte() as T
        else -> throw IllegalArgumentException("Unable to map ${T::class}")
    }
}

inline fun <reified T: Number> String.toNumberOrNull(): T? {
    return try {
        toNumber()
    } catch(e: Exception) {
        null
    }
}