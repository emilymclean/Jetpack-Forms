package cl.emilym.jetpack.form

interface Validator<in T> {

    fun validate(value: T?): ValidationResult

}

sealed interface ValidationResult {
    object Valid: ValidationResult
    class Invalid(
        val message: String
    ): ValidationResult
}