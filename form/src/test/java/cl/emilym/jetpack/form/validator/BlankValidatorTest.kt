package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.BlankValidator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BlankValidatorTest {

    private lateinit var blankValidator: BlankValidator

    @Before
    fun setUp() {
        blankValidator = BlankValidator()
    }

    @Test
    fun `validate with non-null non-blank value should return valid`() {
        // Arrange
        val value = "Test Value"

        // Act
        val result = blankValidator.validate(value)

        // Assert
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return invalid with default message`() {
        // Arrange
        val value: String? = null

        // Act
        val result = blankValidator.validate(value)

        // Assert
        assert(result is ValidationResult.Invalid)
        assertEquals("Field cannot be blank", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with blank value should return invalid with custom message`() {
        // Arrange
        val customMessage = "Custom message"
        val validator = BlankValidator(customMessage)
        val value = ""

        // Act
        val result = validator.validate(value)

        // Assert
        assert(result is ValidationResult.Invalid)
        assertEquals(customMessage, (result as ValidationResult.Invalid).message)
    }
}