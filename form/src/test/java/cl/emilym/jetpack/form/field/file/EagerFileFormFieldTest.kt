package cl.emilym.jetpack.form.field.file

import android.net.Uri
import cl.emilym.form.field.file.EagerFileFormField
import cl.emilym.form.field.file.EagerFileFormFieldCallback
import cl.emilym.form.field.file.EagerFileFormFieldController
import cl.emilym.form.field.file.FileState
import cl.emilym.form.field.file.LazyFileFormField
import cl.emilym.form.field.file.LocalFileInfo
import cl.emilym.form.field.file.RemoteFileInfo
import cl.emilym.form.validator.file.FileCountValidator
import cl.emilym.form.validator.file.FileMimeTypeValidator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EagerFileFormFieldTest {

    private lateinit var uri: Uri
    private lateinit var uri2: Uri

    @Before
    fun setUp() {
        uri = Mockito.mock(Uri::class.java)
        uri2 = Mockito.mock(Uri::class.java)
    }

    @Test
    fun `addFile with valid file should update state to Waiting`() {
        val validator = EagerFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg", "image/png"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            object : EagerFileFormFieldController<RemoteFileInfo> {
                override fun upload(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    callback(FileState.Waiting(file))
                }

                override fun delete(file: RemoteFileInfo) {
                    // Do nothing for testing
                }

                override fun retry(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }
            },
            true
        )

        val file = RemoteFileInfo(uri, "image.jpg", "image/jpeg", 100L, null)
        validator.addFile(file)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Waiting)
    }

    @Test
    fun `addFile with invalid file should update state to Invalid`() {
        val validator = EagerFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg", "image/png"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            object : EagerFileFormFieldController<RemoteFileInfo> {
                override fun upload(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    callback(FileState.Waiting(file))
                }

                override fun delete(file: RemoteFileInfo) {
                    // Do nothing for testing
                }

                override fun retry(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }
            },
            true
        )

        val file = RemoteFileInfo(uri, "image.gif", "image/gif", 100L, null)
        validator.addFile(file)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Invalid)
    }

    @Test
    fun `removeFile should update state by removing the specified file`() {
        val file1 = RemoteFileInfo(uri, "file1.jpg", "image/jpeg", 200L, null)
        val file2 = RemoteFileInfo(uri2, "file2.jpg", "image/jpeg", 300L, null)
        val validator = EagerFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            object : EagerFileFormFieldController<RemoteFileInfo> {
                override fun upload(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }

                override fun delete(file: RemoteFileInfo) {
                    // Do nothing for testing
                }

                override fun retry(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }
            },
            true
        )
        validator.addFile(file1)
        validator.addFile(file2)

        validator.removeFile(file1)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Waiting)
        assertEquals(file2, currentState.first().file)
    }

    @Test
    fun `retryFile with valid file should update state to Complete`() {
        val validator = EagerFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            object : EagerFileFormFieldController<RemoteFileInfo> {
                override fun upload(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }

                override fun delete(file: RemoteFileInfo) {
                    // Do nothing for testing
                }

                override fun retry(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    callback(FileState.Complete(file))
                }
            },
            true
        )

        val file = RemoteFileInfo(uri, "image.jpg", "image/jpeg", 100L, null)
        validator.addFile(file)
        validator.retryFile(file)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Complete)
    }

    @Test
    fun `retryFile with invalid file should not update state`() {
        val validator = EagerFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            object : EagerFileFormFieldController<RemoteFileInfo> {
                override fun upload(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    // Do nothing for testing
                }

                override fun delete(file: RemoteFileInfo) {
                    // Do nothing for testing
                }

                override fun retry(file: RemoteFileInfo, callback: EagerFileFormFieldCallback<RemoteFileInfo>) {
                    callback(FileState.Invalid(file, "File is invalid"))
                }
            },
            true
        )

        val file = RemoteFileInfo(uri, "image.jpg", "image/jpeg", 100L, null)
        validator.retryFile(file)

        val currentState = validator.currentState
        assertEquals(0, currentState.size) // Should not update state if retry failed
    }
}
