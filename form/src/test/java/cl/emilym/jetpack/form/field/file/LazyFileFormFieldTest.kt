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
class LazyFileFormFieldTest {

    private lateinit var uri: Uri
    private lateinit var uri2: Uri

    @Before
    fun setUp() {
        uri = Mockito.mock(Uri::class.java)
        uri2 = Mockito.mock(Uri::class.java)
    }

    @Test
    fun `addFile with valid file should update state to Complete`() {
        val validator = LazyFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg", "image/png"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            true
        )

        val file = LocalFileInfo(uri, "image.jpg", "image/jpeg", 100L)
        validator.addFile(file)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Complete)
    }

    @Test
    fun `removeFile should update state by removing the specified file`() {
        val file1 = LocalFileInfo(uri, "file1.jpg", "image/jpeg", 200L)
        val file2 = LocalFileInfo(uri2, "file2.jpg", "image/jpeg", 300L)
        val validator = LazyFileFormField(
            "files",
            listOf(FileMimeTypeValidator(listOf("image/jpeg"))),
            listOf(FileCountValidator(1, 5, "Must have between 1 and 5 files")),
            true
        )
        validator.addFile(file1)
        validator.addFile(file2)

        validator.removeFile(file1)

        val currentState = validator.currentState
        assertEquals(1, currentState.size)
        assert(currentState.first() is FileState.Complete)
        assertEquals(file2, currentState.first().file)
    }
}
