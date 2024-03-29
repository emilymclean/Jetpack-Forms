package cl.emilym.jetpack.form.validator

import cl.emilym.form.validator.URLValidator

import cl.emilym.form.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Test

class URLValidatorTest {

    @Test
    fun `validate with valid URL should return ValidationResult Valid`() {
        val validator = URLValidator("Invalid website")
        val result = validator.validate("https://www.example.com")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = URLValidator("Invalid website")
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with empty value should return ValidationResult Valid`() {
        val validator = URLValidator("Invalid website")
        val result = validator.validate("")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with invalid URL should return ValidationResult Invalid`() {
        val validator = URLValidator("Invalid website")
        val result = validator.validate("example.com")
        assert(result is ValidationResult.Invalid)
        assertEquals("Invalid website", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with custom message for invalid URL should return ValidationResult Invalid with custom message`() {
        val customMessage = "Please enter a valid URL"
        val validator = URLValidator(message = customMessage)
        val result = validator.validate("example.com")
        assert(result is ValidationResult.Invalid)
        assertEquals(customMessage, (result as ValidationResult.Invalid).message)
    }
}
