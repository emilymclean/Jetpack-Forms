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
import cl.emilym.form.compose.model.SelectionOption

@Composable
fun <T> MultipleCheckboxFormFieldWidget(
    field: FormField<List<T>>,
    options: List<SelectionOption<T>>,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    enabled: Boolean = true,
    content: @Composable (String) -> Unit = { Text(it, style = MaterialTheme.typography.bodyMedium) },
) {
    val value by field.liveValue.collectAsState(null)

    FormFieldWrapper(
        field,
        modifier = modifier
    ) { error ->
        Column(verticalArrangement = Arrangement.spacedBy(0.5.rdp)) {
            for (option in options) {
                CheckboxText(
                    value?.contains(option.value) ?: false,
                    {
                        field.currentValue = if (it) {
                            (field.currentValue ?: listOf())
                                .toMutableSet()
                                .apply { add(option.value) }
                                .toList()
                        } else {
                            (field.currentValue ?: listOf()).filterNot { it == option.value }
                        }
                    },
                    colors = colors,
                    enabled = enabled,
                    isError = error,
                ) {
                    content(option.label)
                }
            }
        }
    }
}