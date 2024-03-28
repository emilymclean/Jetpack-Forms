package cl.emilym.form.compose.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.input.KeyboardType
import cl.emilym.form.FormField
import cl.emilym.form.compose.R
import cl.emilym.form.field.TextFormField

@Composable
fun TextFormFieldWidget(
    textFormField: FormField<String>,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
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
                    val characters = characterLimit - (value?.length ?: 0)
                    Text(pluralStringResource(R.plurals.form_character_remaining, characters, characters))
                }
            }
        }
    ) { error ->
        TextField(
            value = value ?: "",
            onValueChange = { textFormField.currentValue = it },
            placeholder = placeholder,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
            isError = error,
            enabled = enabled
        )
    }
}