package cl.emilym.jetpack.form.validator

import cl.emilym.form.validator.CheckedValidator

import cl.emilym.form.ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckedValidatorTest {

    @Test
    fun `validate with true value and default message should return ValidationResult Valid`() {
        val validator = CheckedValidator()
        val result = validator.validate(true)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with false value and default message should return ValidationResult Invalid`() {
        val validator = CheckedValidator()
        val result = validator.validate(false)
        assert(result is ValidationResult.Invalid)
        assertEquals("Field must be checked", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with custom message should return ValidationResult Invalid with custom message`() {
        val customMessage = "Please accept the terms"
        val validator = CheckedValidator(message = customMessage)
        val result = validator.validate(false)
        assert(result is ValidationResult.Invalid)
        assertEquals("Please accept the terms", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with requiredState true should return ValidationResult Valid for true value`() {
        val validator = CheckedValidator(requiredState = true)
        val result = validator.validate(true)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with requiredState false should return ValidationResult Valid for false value`() {
        val validator = CheckedValidator(requiredState = false)
        val result = validator.validate(false)
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with requiredState true should return ValidationResult Invalid for false value`() {
        val validator = CheckedValidator(requiredState = true)
        val result = validator.validate(false)
        assert(result is ValidationResult.Invalid)
        assertEquals("Field must be checked", (result as ValidationResult.Invalid).message)
    }

    @Test
    fun `validate with requiredState false should return ValidationResult Invalid for true value`() {
        val validator = CheckedValidator(requiredState = false)
        val result = validator.validate(true)
        assert(result is ValidationResult.Invalid)
        assertEquals("Field must be checked", (result as ValidationResult.Invalid).message)
    }
}
