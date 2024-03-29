package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.DatePeriodValidator
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DatePeriodValidatorTest {

    private val testDate = Date(1648704000000) // April 1, 2022 00:00:00 UTC

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = DatePeriodValidator("Date must be within range", maximum = Date(1735728000000), minimum = Date(1648704000000))
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with date within range should return ValidationResult Valid`() {
        val validator = DatePeriodValidator("Date must be within range", maximum = Date(1735728000000), minimum = Date(1648704000000))
        val result = validator.validate(testDate)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with date before minimum should return ValidationResult Invalid`() {
        val validator = DatePeriodValidator("Date must be within range", minimum = Date(1735728000000))
        val result = validator.validate(testDate)
        assert(result is ValidationResult.Invalid)
        assertEquals("Date must be within range", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with date equal to maximum should return ValidationResult Invalid`() {
        val validator = DatePeriodValidator("Date must be within range", maximum = Date(1648704000000))
        val result = validator.validate(testDate)
        assert(result is ValidationResult.Invalid)
        assertEquals("Date must be within range", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with date beyond maximum should return ValidationResult Invalid`() {
        val validator = DatePeriodValidator("Date must be within range", maximum = Date(1648704000000))
        val result = validator.validate(Date(1735728000000))
        assert(result is ValidationResult.Invalid)
        assertEquals("Date must be within range", (result as ValidationResult.Invalid).message)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `validate without minimum or maximum should throw IllegalArgumentException`() {
        DatePeriodValidator("Date must be within range")
    }
}
