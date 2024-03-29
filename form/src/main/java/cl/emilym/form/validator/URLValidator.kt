package cl.emilym.form.validator

import cl.emilym.form.ValidationResult
import cl.emilym.form.Validator

class URLValidator(
    private val message: String = "Invalid website"
): Validator<String> {

    companion object {
        // From https://gist.github.com/gruber/249502
        const val URL_REGEX = "(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))"
    }

    private val regex = Regex(URL_REGEX)

    override fun validate(value: String?): ValidationResult {
        if (value.isNullOrEmpty()) return ValidationResult.Valid
        if (!value.matches(regex)) return ValidationResult.Invalid(message)
        return ValidationResult.Valid
    }

}