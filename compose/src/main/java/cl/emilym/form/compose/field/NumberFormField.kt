package cl.emilym.form.compose.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import cl.emilym.form.FormField
import cl.emilym.form.field.base.LabeledFormField

@Composable
inline fun <reified T: Number> NumberFormFieldWidget(
    field: FormField<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    noinline label: @Composable (() -> Unit)? = null,
    noinline placeholder: (@Composable () -> Unit)? = null,
    noinline trailingIcon: (@Composable () -> Unit)? = null,
    noinline leadingIcon: (@Composable () -> Unit)? = null,
    noinline prefix: @Composable (() -> Unit)? = null,
    noinline suffix: @Composable (() -> Unit)? = null,
    noinline supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType =
                if (decimal) KeyboardType.Decimal else KeyboardType.Number
            ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            shape = shape,
            colors = colors,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            isError = error,
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