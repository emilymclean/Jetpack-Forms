package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.CharacterCountPeriodValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterCountPeriodValidatorTest {

    @Test
    fun `validate with non-null value within range should return ValidationResult Valid`() {
        val validator = CharacterCountPeriodValidator("Length must be between 0 and 10", minimum = 0, maximum = 10)
        val result = validator.validate("o".repeat(5))
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = CharacterCountPeriodValidator("Length must be between 0 and 10", minimum = 0, maximum = 10)
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with value less than minimum should return ValidationResult Invalid`() {
        val validator = CharacterCountPeriodValidator("Length must be at least 10", minimum = 10)
        val result = validator.validate("o".repeat(5))
        assert(result is ValidationResult.Invalid)
        assertEquals("Length must be at least 10", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with value equal to maximum should return ValidationResult Valid`() {
        val validator = CharacterCountPeriodValidator("Length must be less than 10", maximum = 10)
        val result = validator.validate("o".repeat(10))
        assert(result is ValidationResult.Valid)
    }

    @Test
    fun `validate with value beyond maximum should return ValidationResult Invalid`() {
        val validator = CharacterCountPeriodValidator("Length must be less than 50", maximum = 50)
        val result = validator.validate("o".repeat(100))
        assert(result is ValidationResult.Invalid)
        assertEquals("Length must be less than 50", (result as ValidationResult.Invalid).message)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validate without minimum or maximum should throw IllegalArgumentException`() {
        CharacterCountPeriodValidator("Value must be within range")
    }
}
