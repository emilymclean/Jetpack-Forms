package cl.emilym.jetpack.form.validator.file

import android.net.Uri
import cl.emilym.form.ValidationResult
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.validator.file.FileCountValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.lang.IllegalArgumentException

class FileCountValidatorTest {

    @Test
    fun `validate with valid count returns ValidationResult_Valid`() {
        val fileInfoList = listOf(
            createMockFileInfo("uri1"),
            createMockFileInfo("uri2"),
            createMockFileInfo("uri3")
        )
        val validator = FileCountValidator<FileInfo>(minimum = 2, maximum = 5, message = "Invalid file count")
        val result = validator.validate(fileInfoList)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with count below minimum returns ValidationResult_Valid`() {
        val fileInfoList = listOf(
            createMockFileInfo("uri1")
        )
        val validator = FileCountValidator<FileInfo>(minimum = 2, maximum = 5, message = "Invalid file count")
        val result = validator.validate(fileInfoList)
        assert(result is ValidationResult.Invalid)
    }

    @Test
    fun `validate with count above maximum returns ValidationResult_Invalid`() {
        val fileInfoList = listOf(
            createMockFileInfo("uri1"),
            createMockFileInfo("uri2"),
            createMockFileInfo("uri3"),
            createMockFileInfo("uri4"),
            createMockFileInfo("uri5"),
            createMockFileInfo("uri6")
        )
        val validator = FileCountValidator<FileInfo>(minimum = 2, maximum = 5, message = "Invalid file count")
        val result = validator.validate(fileInfoList)
        assert(result is ValidationResult.Invalid)
    }

    @Test
    fun `validate with null value returns ValidationResult_Valid`() {
        val validator = FileCountValidator<FileInfo>(minimum = 2, maximum = 5, message = "Invalid file count")
        val result = validator.validate(null)
        assert(result is ValidationResult.Valid)
    }

    @Test
    fun `init with null minimum and maximum throws IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            FileCountValidator<FileInfo>(minimum = null, maximum = null, message = "Invalid file count")
        }
    }

    private fun createMockFileInfo(uriString: String): FileInfo {
        val uri = mock(Uri::class.java)
        `when`(uri.toString()).thenReturn(uriString)
        return mock(FileInfo::class.java).apply {
            `when`(this.uri).thenReturn(uri)
            `when`(this.name).thenReturn("Mock File")
            `when`(this.mimeType).thenReturn("image/jpeg")
            `when`(this.size).thenReturn(1024)
        }
    }
}
