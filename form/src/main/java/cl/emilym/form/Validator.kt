package cl.emilym.form

/**
 * Interface for defining validators for form fields.
 *
 * @param T The type of value that the validator can validate.
 */
interface Validator<in T> {

    /**
     * Validates the provided value.
     *
     * @param value The value to validate.
     * @return The result of the validation as a ValidationResult.
     */
    fun validate(value: T?): ValidationResult

}

/**
 * Sealed interface representing the result of a validation.
 */
sealed interface ValidationResult {
    /**
     * Represents a valid validation result.
     */
    data object Valid: ValidationResult

    /**
     * Represents an invalid validation result with an associated error message.
     *
     * @param message A message describing the validation failure.
     */
    class Invalid(
        val message: String
    ): ValidationResult
}