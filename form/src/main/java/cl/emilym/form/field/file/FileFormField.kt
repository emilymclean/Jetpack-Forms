package cl.emilym.form.field.file

import android.net.Uri
import cl.emilym.form.FormField
import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator
import cl.emilym.form.field.base.BaseFormField
import cl.emilym.form.validator.file.FileStateValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.lang.UnsupportedOperationException

interface FileFormField<T: FileInfo>: FormField<List<Uri>> {

    var currentState: List<FileState<T>>
    val liveState: Flow<List<FileState<T>>>

    fun addFile(file: T)
    fun removeFile(file: T)

}

abstract class BaseFileFormField<T: FileInfo>: BaseFormField<List<Uri>>(), FileFormField<T> {

    abstract val fileValidators: List<Validator<T>>
    abstract val filesValidators: List<Validator<List<T>>>
    open val stateValidators: List<Validator<List<FileState<T>>>> = listOf(FileStateValidator())

    final override val validators: List<Validator<List<Uri>>>
        get() = listOf()

    override var currentState: List<FileState<T>> = listOf()
        set(value) {
            field = value
            _liveState.tryEmit(field)
        }

    private val _liveState = MutableStateFlow<List<FileState<T>>>(listOf())
    final override val liveState: Flow<List<FileState<T>>> = _liveState

    override var currentValue: List<Uri>?
        get() = currentState.mapNotNull { toUri(it.file) }
        set(value) {
            throw UnsupportedOperationException("Cannot directly set file form field")
        }
    final override val liveValue: Flow<List<Uri>?> = liveState.map { it.mapNotNull { toUri(it.file) } }

    override suspend fun validate(silent: Boolean): Boolean {
        val fileValidationResults = currentState.map { fileState ->
            fileValidators.map { it.validate(fileState.file) }
        }.flatten().filterIsInstance<ValidationResult.Invalid>()
        val filesValidationResults = filesValidators.map { it.validate(currentState.map { it.file }) }
            .filterIsInstance<ValidationResult.Invalid>()
        val stateValidationResults = stateValidators.map { it.validate(currentState) }
            .filterIsInstance<ValidationResult.Invalid>()

        return presentValidation(fileValidationResults + filesValidationResults + stateValidationResults, silent)
    }

    protected abstract fun toUri(file: T): Uri?

}