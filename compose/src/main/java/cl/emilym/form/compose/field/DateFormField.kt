package cl.emilym.form.compose.field

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import cl.emilym.form.FormField
import cl.emilym.form.field.base.LabeledFormField
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DateFormFieldWidget(
    field: FormField<Date>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
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
    // Must set the disabled fields if replacing
    colors: TextFieldColors = TextFieldDefaults.colors(
        disabledTextColor = LocalContentColor.current,
    disabledLabelColor =  MaterialTheme.colorScheme.onSurface,
    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
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
            keyboardOptions = keyboardOptions
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