package cl.emilym.form.compose.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import cl.emilym.form.FormField
import cl.emilym.form.compose.R
import cl.emilym.form.field.TextFormField

@Composable
fun TextFormFieldWidget(
    textFormField: FormField<String>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    characterLimitDisplay: @Composable ((limit: Int, count: Int) -> Unit) = { limit, count ->
        val characters = limit - count
        Text(pluralStringResource(R.plurals.form_character_remaining, characters, characters))
    }
) {
    val value by textFormField.liveValue.collectAsState(null)
    val characterLimit = (textFormField as? TextFormField)?.characterLimit ?: 0

    FormFieldWrapper(
        textFormField,
        modifier,
        bottomLabel = {
            if (characterLimit > 0) {
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    characterLimitDisplay(characterLimit, value?.length ?: 0)
                }
            }
        }
    ) { error ->
        TextField(
            value = value ?: "",
            onValueChange = { textFormField.currentValue = it },
            modifier = Modifier.fillMaxWidth(),
            enabled,
            readOnly,
            textStyle,
            label,
            placeholder,
            leadingIcon,
            trailingIcon,
            prefix,
            suffix,
            supportingText,
            error,
            visualTransformation,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            minLines,
            interactionSource,
            shape,
            colors
        )
    }
}