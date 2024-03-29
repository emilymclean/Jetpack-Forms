package cl.emilym.form.field.file

import android.content.ContentResolver
import android.net.Uri
import cl.emilym.form.Validator

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

typealias EagerFileFormFieldCallback<T> = (FileState<T>) -> Unit

interface EagerFileFormFieldController<T: FileInfo> {

    fun upload(file: T, callback: EagerFileFormFieldCallback<T>)
    fun delete(file: T)
    fun retry(file: T, callback: EagerFileFormFieldCallback<T>)

}

fun <E> Iterable<E>.replace(new: E, match: (E) -> Boolean) = map { if (match(it)) new else it }