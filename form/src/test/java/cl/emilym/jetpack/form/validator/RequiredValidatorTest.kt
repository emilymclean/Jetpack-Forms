package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.RequiredValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class RequiredValidatorTest {

    @Test
    fun `validate with non-null value should return ValidationResult Valid`() {
        val validator = RequiredValidator<String>("Field is required")
        val result = validator.validate("Test")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Invalid`() {
        val validator = RequiredValidator<Int>("Field is required")
        val result = validator.validate(null)
        assert(result is ValidationResult.Invalid)
        assertEquals("Field is required", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with custom message should return ValidationResult Invalid with custom message`() {
        val customMessage = "This field cannot be empty"
        val validator = RequiredValidator<Double>(message = customMessage)
        val result = validator.validate(null)
        assert(result is ValidationResult.Invalid)
        assertEquals("This field cannot be empty", (result as ValidationResult.Invalid).message)
    }
}
