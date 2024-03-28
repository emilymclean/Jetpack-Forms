package cl.emilym.form.compose.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import cl.emilym.component.compose.rdp
import cl.emilym.form.FormField
import cl.emilym.form.compose.helper.CheckboxText
import cl.emilym.form.compose.helper.RadioText
import cl.emilym.form.compose.model.SelectionOption

@Composable
fun <T> RadioFormFieldWidget(
    field: FormField<T>,
    options: List<SelectionOption<T>>,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    enabled: Boolean = true,
    content: @Composable (String) -> Unit = { Text(it, style = MaterialTheme.typography.bodyMedium) },
) {
    val value by field.liveValue.collectAsState(null)
    val errorMessage by field.errorMessage.collectAsState(null)

    Column(verticalArrangement = Arrangement.spacedBy(0.5.rdp)) {
        for (option in options) {
            RadioText(
                value == option.value,
                {
                    field.currentValue = option.value
                },
                modifier = modifier,
                colors = colors,
                enabled = enabled,
                isError = errorMessage != null,
            ) {
                content(option.label)
            }
        }
    }
}