package cl.emilym.form.compose.field

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import cl.emilym.form.FormField
import cl.emilym.form.compose.LocalFormConfig
import cl.emilym.form.field.base.LabeledFormField
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateFormFieldWidget(
    field: FormField<Date>,
    hint: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val value = field.liveValue.collectAsState(null).value?.let {
        (field as? LabeledFormField<Date>)?.getLabel(it) ?: SimpleDateFormat.getDateInstance().format(it)
    }
    val context = LocalContext.current

    FormFieldWrapper(field, modifier) { error ->
        TextField(
            value = value ?: "",
            onValueChange = {},
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledTextColor = LocalContentColor.current,
                disabledLabelColor =  MaterialTheme.colorScheme.onSurface,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            placeholder = {
                Text(
                    hint,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Button
                ) {
                    if (!enabled) return@clickable
                    showDatePicker(context.getActivity()!!, field.currentValue ?: Date()) {
                        field.currentValue = it
                    }
                },
            trailingIcon = LocalFormConfig.current.datePickerTrailingIcon,
            isError = error,
        )
    }
}

private fun showDatePicker(activity: AppCompatActivity, current: Date, callback: (Date) -> Unit) {
    MaterialDatePicker.Builder.datePicker()
        .setSelection(current.time)
        .build().apply {
            show(activity.supportFragmentManager, this.toString())
            addOnPositiveButtonClickListener {
                callback(Date(it))
            }
        }
}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}