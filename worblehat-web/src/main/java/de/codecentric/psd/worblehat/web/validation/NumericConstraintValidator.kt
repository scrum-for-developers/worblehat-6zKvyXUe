package de.codecentric.psd.worblehat.web.validation

import org.apache.commons.lang.StringUtils

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NumericConstraintValidator : ConstraintValidator<Numeric, String> {

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        // Don't validate null, empty and blank strings, since these are validated by @NotNull, @NotEmpty and @NotBlank
        return if (value.isNotBlank()) {
            StringUtils.isNumeric(value)
        } else true
    }

}
