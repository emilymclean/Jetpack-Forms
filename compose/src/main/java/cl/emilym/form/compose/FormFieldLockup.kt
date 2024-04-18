package cl.emilym.form.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import cl.emilym.compose.units.rdp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormFieldLockup(
    title: String?,
    description: String? = null,
    optional: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(0.5.rdp)) {
        if (title != null) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(0.5.rdp)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                if (optional) {
                    Text("(optional)", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        if (description != null) {
            Text(description, style = MaterialTheme.typography.bodySmall)
        }
        content()
    }
}