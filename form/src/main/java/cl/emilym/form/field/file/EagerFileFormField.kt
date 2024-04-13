package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import cl.emilym.form.Validator

/**
 * Represents a form field for "eager" file upload. In this context, eager means the form will immediately
 * upload the selected file to a server, through th provided controller. This is in contrast to the "lazy"
 * file upload, which waits until form submission.
 *
 * @property name The name of the form field.
 * @property fileValidators List of validators for individual files.
 * @property filesValidators List of validators for the entire file selection.
 * @property controller The controller responsible for file operations.
 * @property singleThread Indicates if file operations should be performed on a single thread.
 */
class EagerFileFormField(
    override val name: String,
    override val fileValidators: List<Validator<RemoteFileInfo>>,
    override val filesValidators: List<Validator<List<RemoteFileInfo>>>,
    private val controller: EagerFileFormFieldController<RemoteFileInfo>,
    override val singleThread: Boolean = false
): ConcurrentBaseFileFormField<RemoteFileInfo>(), RetryableFileFormField<RemoteFileInfo> {

    override fun addFile(uri: Uri, contentResolver: ContentResolver) {
        addFile(RemoteFileInfo.fromUri(uri, contentResolver) ?: return)
    }

    override fun addFile(file: RemoteFileInfo) {
        val message = fileValid(file)
        val state = if (message == null) {
            FileState.Waiting(file)
        } else {
            FileState.Invalid(file, message)
        }
        updateState(
            afterUpdate = {
                if (state !is FileState.Waiting) return@updateState
                controller.upload(file, ::controllerCallback)
            }
        ) {
            it + state
        }
    }

    override fun removeFile(file: RemoteFileInfo) {
        updateState {
            it.filterNot { it.file == file }.also {
                controller.delete(file)
            }
        }
    }

    override fun retryFile(file: FileInfo) {
        if (file !is RemoteFileInfo) return
        val message = fileValid(file)
        val state = if (message == null) {
            FileState.Waiting(file)
        } else {
            FileState.Invalid(file, message)
        }
        updateState(
            afterUpdate = {
                if (state !is FileState.Waiting) return@updateState
                controller.retry(file, ::controllerCallback)
            }
        ) {
            it.replace(state) {
                it.file == file
            }
        }
    }

    private fun controllerCallback(state: FileState<RemoteFileInfo>) {
        updateState {
            it.replace(state) {
                it.file == state.file
            }.also { print(it) }
        }
    }

    override fun toUri(file: RemoteFileInfo): Uri? {
        return file.remoteReference
    }

}

/**
 * Callback type for eager file form field operations.
 *
 * @param T The type of file being operated on.
 */
typealias EagerFileFormFieldCallback<T> = (FileState<T>) -> Unit

/**
 * Interface for the controller of eager file form field operations.
 *
 * @param T The type of file being operated on.
 */
interface EagerFileFormFieldController<T: FileInfo> {

    /**
     * Uploads a file.
     *
     * @param file The file to upload.
     * @param callback The callback function to handle upload state.
     */
    fun upload(file: T, callback: EagerFileFormFieldCallback<T>)

    /**
     * Deletes a file.
     *
     * @param file The file to delete.
     */
    fun delete(file: T)

    /**
     * Retries uploading a file.
     *
     * @param file The file to retry.
     * @param callback The callback function to handle retry state.
     */
    fun retry(file: T, callback: EagerFileFormFieldCallback<T>)

}

/**
 * Replaces elements in an iterable based on a matching condition.
 *
 * @param new The new element to replace matching elements with.
 * @param match The matching condition.
 * @return A new iterable with replaced elements.
 */
fun <E> Iterable<E>.replace(new: E, match: (E) -> Boolean) = map { if (match(it)) new else it }