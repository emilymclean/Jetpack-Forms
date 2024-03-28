package cl.emilym.form.compose.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
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

@Composable
fun SingleCheckboxFormFieldWidget(
    field: FormField<Boolean>,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val value by field.liveValue.collectAsState(null)
    val errorMessage by field.errorMessage.collectAsState(null)
    CheckboxText(
        value ?: false,
        { field.currentValue = it },
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        isError = errorMessage != null,
    ) {
        content()
    }
}