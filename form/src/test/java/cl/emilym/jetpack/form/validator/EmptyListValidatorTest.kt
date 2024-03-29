package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.EmptyListValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class EmptyListValidatorTest {

    @Test
    fun `validate with non-empty list should return ValidationResult Valid`() {
        val validator = EmptyListValidator<Int>()
        val result = validator.validate(listOf(1, 2, 3))
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with empty list should return ValidationResult Invalid`() {
        val validator = EmptyListValidator<String>()
        val result = validator.validate(emptyList())
        assert(result is ValidationResult.Invalid)
        assertEquals("Field is required", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with null list should return ValidationResult Valid`() {
        val validator = EmptyListValidator<Double>()
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with custom message should return ValidationResult Invalid with custom message`() {
        val customMessage = "Please provide at least one item"
        val validator = EmptyListValidator<String>(message = customMessage)
        val result = validator.validate(emptyList())
        assert(result is ValidationResult.Invalid)
        assertEquals(customMessage, (result as ValidationResult.Invalid).message)
    }
}
