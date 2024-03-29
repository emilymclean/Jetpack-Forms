package cl.emilym.jetpack.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.validator.file.FileMimeTypeValidator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FileMimeTypeValidatorTest {

    @Test
    fun `validate with acceptable mime type returns ValidationResult_Valid`() {
        val acceptableMimeTypes = listOf("image/jpeg", "image/png")
        val fileInfo = createMockFileInfo("image/jpeg")
        val validator = FileMimeTypeValidator<FileInfo>(acceptableMimeTypes, message = "Invalid file type")
        val result = validator.validate(fileInfo)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with unacceptable mime type returns ValidationResult_Invalid`() {
        val acceptableMimeTypes = listOf("image/jpeg", "image/png")
        val fileInfo = createMockFileInfo("application/pdf")
        val validator = FileMimeTypeValidator<FileInfo>(acceptableMimeTypes, message = "Invalid file type")
        val result = validator.validate(fileInfo)
        assert(result is ValidationResult.Invalid)
    }

    @Test
    fun `validate with null value returns ValidationResult_Valid`() {
        val acceptableMimeTypes = listOf("image/jpeg", "image/png")
        val validator = FileMimeTypeValidator<FileInfo>(acceptableMimeTypes, message = "Invalid file type")
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    private fun createMockFileInfo(mimeType: String): FileInfo {
        return mock(FileInfo::class.java).apply {
            `when`(this.mimeType).thenReturn(mimeType)
        }
    }
}
