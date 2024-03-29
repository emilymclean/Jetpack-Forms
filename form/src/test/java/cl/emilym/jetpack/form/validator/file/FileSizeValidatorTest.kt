package cl.emilym.jetpack.form.validator.file

import cl.emilym.form.ValidationResult
import cl.emilym.form.field.file.FileInfo
import cl.emilym.form.validator.file.FileSizeValidator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FileSizeValidatorTest {

    @Test
    fun `validate with file size less than maximum returns ValidationResult_Valid`() {
        val maximumSize = 1024L
        val fileInfo = createMockFileInfo(size = 512L)
        val validator = FileSizeValidator<FileInfo>(maximumSize, message = "File size exceeds maximum")
        val result = validator.validate(fileInfo)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with file size equal to maximum returns ValidationResult_Valid`() {
        val maximumSize = 1024L
        val fileInfo = createMockFileInfo(size = 1024L)
        val validator = FileSizeValidator<FileInfo>(maximumSize, message = "File size exceeds maximum")
        val result = validator.validate(fileInfo)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with file size greater than maximum returns ValidationResult_Invalid`() {
        val maximumSize = 1024L
        val fileInfo = createMockFileInfo(size = 2048L)
        val validator = FileSizeValidator<FileInfo>(maximumSize, message = "File size exceeds maximum")
        val result = validator.validate(fileInfo)
        assert(result is ValidationResult.Invalid)
    }

    @Test
    fun `validate with null value returns ValidationResult_Valid`() {
        val maximumSize = 1024L
        val validator = FileSizeValidator<FileInfo>(maximumSize, message = "File size exceeds maximum")
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    private fun createMockFileInfo(size: Long): FileInfo {
        return mock(FileInfo::class.java).apply {
            `when`(this.size).thenReturn(size)
        }
    }
}
