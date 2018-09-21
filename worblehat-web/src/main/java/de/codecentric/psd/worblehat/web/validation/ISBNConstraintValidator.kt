package de.codecentric.psd.worblehat.web.validation

import org.apache.commons.validator.routines.ISBNValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ISBNConstraintValidator : ConstraintValidator<ISBN, String> {

    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        // Don't validate null, empty and blank strings, since these are validated by @NotNull, @NotEmpty and @NotBlank
        return if (value.isNotBlank()) {
            ISBNValidator.getInstance().isValidISBN10(value) || ISBNValidator.getInstance().isValidISBN13(value)
        } else true
    }

}
