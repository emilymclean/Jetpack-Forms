package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import cl.emilym.form.Validator
import kotlinx.coroutines.sync.Mutex

/**
 * Represents a form field for "lazy" file upload. In this context, lazy means the form will maintain
 * a local reference to the file, but the responsibility for uploading/handling it lies with the
 * consumer. This is in contrast to the "eager" file upload, which immediately uploads/handles the file.
 *
 * @property name The name of the form field.
 * @property fileValidators List of validators for individual files.
 * @property filesValidators List of validators for the entire file selection.
 * @property singleThread Indicates if file operations should be performed on a single thread.
 */
class LazyFileFormField(
    override val name: String,
    override val fileValidators: List<Validator<LocalFileInfo>>,
    override val filesValidators: List<Validator<List<LocalFileInfo>>>,
    override val singleThread: Boolean = false
): ConcurrentBaseFileFormField<LocalFileInfo>() {

    override fun addFile(uri: Uri, contentResolver: ContentResolver) {
        addFile(LocalFileInfo.fromUri(uri, contentResolver) ?: return)
    }

    override fun addFile(file: LocalFileInfo) {
        val message = fileValid(file)
        val state = if (message == null) {
            FileState.Complete(file)
        } else {
            FileState.Invalid(file, message)
        }
        updateState {
            it + state
        }
    }

    override fun removeFile(file: LocalFileInfo) {
        updateState {
            it.filterNot { it.file == file }
        }
    }

    override fun toUri(file: LocalFileInfo): Uri {
        return file.uri
    }

}