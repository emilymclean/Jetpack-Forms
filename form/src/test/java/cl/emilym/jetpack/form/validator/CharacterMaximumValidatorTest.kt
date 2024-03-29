package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.CharacterMaximumValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMaximumValidatorTest {

    @Test
    fun `validate with valid value should return ValidationResult Valid`() {
        val validator = CharacterMaximumValidator(10)
        val result = validator.validate("Hello")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = CharacterMaximumValidator(10)
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with empty value should return ValidationResult Valid`() {
        val validator = CharacterMaximumValidator(10)
        val result = validator.validate("")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with invalid value should return ValidationResult Invalid`() {
        val validator = CharacterMaximumValidator(5)
        val result = validator.validate("Hello, World!")
        assert(result is ValidationResult.Invalid)
        assertEquals("Only 5 characters allowed", (result as ValidationResult.Invalid).message)
    }
}