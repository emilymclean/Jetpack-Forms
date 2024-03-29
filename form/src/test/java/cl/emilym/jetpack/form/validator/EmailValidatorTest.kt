package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.EmailValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun `validate with blank value should return ValidationResult Valid`() {
        val validator = EmailValidator()
        val result = validator.validate("")
        assertEquals(ValidationResult.Valid, result)
    }

    @Test
    fun `validate with null value should return ValidationResult Valid`() {
        val validator = EmailValidator()
        val result = validator.validate(null)
        assertEquals(ValidationResult.Valid, result)
    }

}
