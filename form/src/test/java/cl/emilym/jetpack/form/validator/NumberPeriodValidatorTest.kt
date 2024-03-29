package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.DatePeriodValidator
import cl.emilym.form.validator.NumberPeriodValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class NumberPeriodValidatorTest {

    @Test
    fun `validate with non-null value within range should return ValidationResult Valid`() {
        val validator = NumberPeriodValidator<Int>("Value must be between 0 and 10", minimum = 0, maximum = 10)
        val result = validator.validate(5)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = NumberPeriodValidator<Int>("Value must be between 0 and 10", minimum = 0, maximum = 10)
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with value less than minimum should return ValidationResult Invalid`() {
        val validator = NumberPeriodValidator<Double>("Value must be at least 10.0", minimum = 10.0)
        val result = validator.validate(5.0)
        assert(result is ValidationResult.Invalid)
        assertEquals("Value must be at least 10.0", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with value equal to maximum should return ValidationResult Invalid`() {
        val validator = NumberPeriodValidator<Long>("Value must be less than 100", maximum = 100L)
        val result = validator.validate(100L)
        assert(result is ValidationResult.Invalid)
        assertEquals("Value must be less than 100", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with value beyond maximum should return ValidationResult Invalid`() {
        val validator = NumberPeriodValidator<Float>("Value must be less than 50.0", maximum = 50.0f)
        val result = validator.validate(55.0f)
        assert(result is ValidationResult.Invalid)
        assertEquals("Value must be less than 50.0", (result as ValidationResult.Invalid).message)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validate without minimum or maximum should throw IllegalArgumentException`() {
        NumberPeriodValidator<Int>("Value must be within range")
    }
}
