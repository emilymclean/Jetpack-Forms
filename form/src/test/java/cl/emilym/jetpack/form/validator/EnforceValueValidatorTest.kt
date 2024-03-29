package cl.emilym.jetpack.form.validator

import cl.emilym.form.validator.EnforceValueValidator

import cl.emilym.form.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Test

class EnforceValueValidatorTest {

    @Test
    fun `validate with matching value should return ValidationResult Valid`() {
        val validator = EnforceValueValidator<Int>("Value must be 5", 5)
        val result = validator.validate(5)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with non-matching value should return ValidationResult Invalid`() {
        val validator = EnforceValueValidator<String>("Value must be 'hello'", "hello")
        val result = validator.validate("world")
        assert(result is ValidationResult.Invalid)
        assertEquals("Value must be 'hello'", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with null value should return ValidationResult Invalid`() {
        val validator = EnforceValueValidator<Double>("Value must be null", null)
        val result = validator.validate(3.14)
        assert(result is ValidationResult.Invalid)
        assertEquals("Value must be null", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with custom message should return ValidationResult Invalid with custom message`() {
        val customMessage = "Value must be a positive number"
        val validator = EnforceValueValidator<Double>(message = customMessage, requiredValue = 10.0)
        val result = validator.validate(-5.0)
        assert(result is ValidationResult.Invalid)
        assertEquals(customMessage, (result as ValidationResult.Invalid).message)
    }
}
