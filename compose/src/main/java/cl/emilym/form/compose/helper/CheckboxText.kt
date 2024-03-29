package cl.emilym.form.compose.helper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import cl.emilym.component.compose.rdp

@Composable
fun CheckboxText(
    value: Boolean,
    onValueChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    enabled: Boolean = true,
    isError: Boolean = false,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value,
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Checkbox,
                onValueChange = onValueChanged
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.rdp)
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = null,
            enabled = enabled,
            colors = if (!isError) colors else CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.error,
                uncheckedColor = MaterialTheme.colorScheme.error
            )
        )
        Row {
            content()
        }
    }
}

@Composable
fun RadioText(
    value: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    enabled: Boolean = true,
    isError: Boolean = false,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.RadioButton,
                onClick = onClick
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.rdp)
    ) {
        RadioButton(
            value,
            onClick = null,
            enabled = enabled,
            colors = if (!isError) colors else RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.error,
                unselectedColor = MaterialTheme.colorScheme.error
            )
        )
        Row {
            content()
        }
    }
}