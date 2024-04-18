package cl.emilym.form.compose.field

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.emilym.bytes.HumanReadableBytes
import cl.emilym.compose.units.rdp
import cl.emilym.form.compose.R
import cl.emilym.form.field.file.FileFormField
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.field.file.FileState
import cl.emilym.form.field.file.RetryableFileFormField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T: FileInfo> FileFormFieldWidget(
    field: FileFormField<T>,
    modifier: Modifier = Modifier,
    uploadInstructionTitle: String? = null,
    uploadInstructionContent: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    contentVariantColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    errorColor: Color = MaterialTheme.colorScheme.error,
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
    shape: Shape = RoundedCornerShape(1.rdp),
) {
    val value by field.liveState.collectAsState(listOf())
    val context = LocalContext.current

    val fileLauncher = rememberLauncherForActivityResult(
        object: ActivityResultContract<String, Uri?>() {
            @CallSuper
            override fun createIntent(context: Context, input: String): Intent {
                return getFileIntent(
                    field.acceptableMimeTypes,
                    field.fileCountRequired?.last ?: Int.MAX_VALUE
                )
            }

            override fun getSynchronousResult(
                context: Context,
                input: String
            ): SynchronousResult<Uri?>? = null

            override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
                return intent.takeIf { resultCode == Activity.RESULT_OK }?.data
            }
        }
    ) {
        field.addFile(it ?: return@rememberLauncherForActivityResult, context.contentResolver)
    }

    FormFieldWrapper(
        field,
        modifier,
    ) { error ->
        Box(
            modifier = Modifier
                .clip(shape)
                .background(backgroundColor)
                .then(
                    when {
                        border != null && error -> Modifier.border(
                            border.copy(
                                brush = SolidColor(errorColor)
                            ), shape
                        )

                        border != null -> Modifier.border(border, shape)
                        else -> Modifier
                    }
                )
                .defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = TextFieldDefaults.MinHeight
                )
                .then(modifier)
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Column(
                    Modifier.fillMaxSize(),
                ) {
                    if (field.fileCountRequired == null || value.size < field.fileCountRequired!!.last) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    fileLauncher.launch("")
                                }
                                .padding(TextFieldDefaults.textFieldWithoutLabelPadding())
                        ) {
                            Icon(painterResource(R.drawable.ic_file_upload), contentDescription = "File upload icon", tint = accentColor)
                            Spacer(Modifier.width(TextFieldDefaults.textFieldWithoutLabelPadding().calculateEndPadding(LocalLayoutDirection.current)))
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(0.25.rdp)) {
                                Text(
                                    uploadInstructionTitle ?: stringResource(R.string.form_file_upload_title),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = accentColor
                                )
                                uploadInstructionContent?.let { Text(uploadInstructionContent) }
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (value.isNotEmpty()) {
                            Box(
                                Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(contentColor))
                        }

                        for (fileState in value) {
                            val arrangement = Arrangement.spacedBy(TextFieldDefaults.textFieldWithoutLabelPadding().calculateEndPadding(LocalLayoutDirection.current))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(TextFieldDefaults.textFieldWithoutLabelPadding()),
                                horizontalArrangement = arrangement
                            ) {
                                Column(Modifier.weight(1f)) {
                                    FlowRow(horizontalArrangement = Arrangement.spacedBy(0.5.rdp)) {
                                        Text(
                                            fileState.file.name, color = when (fileState) {
                                                is FileState.Failed, is FileState.Invalid -> errorColor
                                                else -> contentColor
                                            },
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            stringResource(
                                                R.string.form_file_upload_file_metadata,
                                                MimeTypeMap.getSingleton()
                                                    .getExtensionFromMimeType(fileState.file.mimeType) ?: "",
                                                HumanReadableBytes.si(fileState.file.size)
                                            ),
                                            color = contentVariantColor,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                Row {
                                    when (fileState) {
                                        is FileState.Complete, is FileState.Invalid -> IconButton(onClick = {
                                            field.removeFile(fileState.file)
                                        }) {
                                            if (fileState is FileState.Complete) {
                                                Icon(painterResource(R.drawable.ic_delete), contentDescription = "Delete icon", tint = accentColor)
                                            } else {
                                                Icon(painterResource(R.drawable.ic_close), contentDescription = "Invalid icon", tint = errorColor)
                                            }
                                        }
                                        is FileState.Waiting -> IconButton(onClick = { field.removeFile(fileState.file) }) {
                                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                        }
                                        is FileState.Progress -> CircularProgressIndicator(progress = fileState.progress.toFloat(), modifier = Modifier.size(24.dp))
                                        is FileState.Failed -> {
                                            IconButton(onClick = {
                                                field.removeFile(fileState.file)
                                            }) {
                                                Icon(painterResource(R.drawable.ic_close), contentDescription = "Cancel upload icon", tint = errorColor)
                                            }
                                            if (field is RetryableFileFormField<*>) {
                                                IconButton(onClick = { field.retryFile(fileState.file) }) {
                                                    Icon(painterResource(R.drawable.ic_retry), "Retry icon", tint = errorColor)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            Box(Modifier.height(1.dp).fillMaxWidth().background(contentColor))
                        }
                    }
                }
            }
        }
    }
}

private fun getFileIntent(acceptableMimeTypes: List<String>?, maximumFiles: Int): Intent {
    return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        if (acceptableMimeTypes != null) {
            if (acceptableMimeTypes.size == 1) {
                type = acceptableMimeTypes[0]
            } else {
                type = "*/*"
                putStringArrayListExtra(
                    Intent.EXTRA_MIME_TYPES,
                    ArrayList(acceptableMimeTypes)
                )
            }
        }

        // TODO
//        if (maximumFiles > 1) {
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        }

        addCategory(Intent.CATEGORY_OPENABLE)
    }
}