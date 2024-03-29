package cl.emilym.jetpack.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.validator.ABNValidator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ABNValidatorTest {

    private lateinit var abnValidator: ABNValidator

    @Before
    fun setUp() {
        abnValidator = ABNValidator()
    }

    @Test
    fun testValidate_ValidABN() {
        // Valid ABN: 12345678901
        val validationResult = abnValidator.validate("42002780289")
        assertEquals(ValidationResult.Valid, validationResult)
    }

    @Test
    fun testValidate_ValidABNWhitespace() {
        // Valid ABN: 12345678901
        val validationResult = abnValidator.validate("42 002 780 289")
        assertEquals(ValidationResult.Valid, validationResult)
    }

    @Test
    fun testValidate_InvalidLength() {
        // Invalid ABN length: 1234567890 (10 digits)
        val validationResult = abnValidator.validate("1234567890")
        assert(validationResult is ValidationResult.Invalid)
        assertEquals("Invalid ABN", (validationResult as ValidationResult.Invalid).message)
    }

    @Test
    fun testValidate_NonDigitCharacters() {
        // ABN with non-digit characters: 12-345-678-901
        val validationResult = abnValidator.validate("12-345-678-901")
        assert(validationResult is ValidationResult.Invalid)
        assertEquals("Invalid ABN", (validationResult as ValidationResult.Invalid).message)
    }

    @Test
    fun testValidate_InvalidChecksum() {
        // ABN with invalid checksum: 12345678902
        val validationResult = abnValidator.validate("12345678902")
        assert(validationResult is ValidationResult.Invalid)
        assertEquals("Invalid ABN", (validationResult as ValidationResult.Invalid).message)
    }

    @Test
    fun testValidate_NullOrBlankValue() {
        // Null value
        val validationResultNull = abnValidator.validate(null)
        assertEquals(ValidationResult.Valid, validationResultNull)

        // Blank value
        val validationResultBlank = abnValidator.validate("")
        assertEquals(ValidationResult.Valid, validationResultBlank)
    }

    @Test
    fun testValidate_CustomMessage() {
        // Custom error message for invalid ABN
        abnValidator = ABNValidator("Custom message")
        val validationResult = abnValidator.validate("1234567890")
        assert(validationResult is ValidationResult.Invalid)
        assertEquals("Custom message", (validationResult as ValidationResult.Invalid).message)
    }
}