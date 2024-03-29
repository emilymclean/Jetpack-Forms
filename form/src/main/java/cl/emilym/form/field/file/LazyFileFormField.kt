package cl.emilym.form.field.file

import android.net.Uri
import cl.emilym.form.Validator
import kotlinx.coroutines.sync.Mutex

class LazyFileFormField(
    override val name: String,
    override val fileValidators: List<Validator<LocalFileInfo>>,
    override val filesValidators: List<Validator<List<LocalFileInfo>>>
): ConcurrentBaseFileFormField<LocalFileInfo>() {

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
            it.filter { it.file == file }
        }
    }

    override fun toUri(file: LocalFileInfo): Uri {
        return file.uri
    }
    
}