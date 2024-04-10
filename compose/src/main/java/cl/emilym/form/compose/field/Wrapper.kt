package cl.emilym.form.compose.field

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cl.emilym.component.compose.rdp
import cl.emilym.form.FormField
import cl.emilym.form.compose.LocalFormConfig

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> FormFieldWrapper(
    field: FormField<T>,
    modifier: Modifier = Modifier,
    bottomLabel: @Composable () -> Unit = {},
    delegate: @Composable (error: Boolean) -> Unit,
) {
    val errorMessage by field.errorMessage.collectAsState(null)
    Column(
        Modifier
            .animateContentSize()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(0.5.rdp)
    ) {
        delegate(errorMessage != null)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(
                0.5.rdp,
                Alignment.CenterVertically
            ),
        ) {
            errorMessage?.let {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                    Row(horizontalArrangement = Arrangement.spacedBy(0.25.rdp)) {
                        LocalFormConfig.current.errorMessage(this, it)
                    }
                }
            }
            Box(Modifier.width(1.rdp))
            bottomLabel()
        }
    }
}