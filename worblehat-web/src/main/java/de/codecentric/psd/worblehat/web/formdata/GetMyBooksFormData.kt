package de.codecentric.psd.worblehat.web.formdata

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * This class represent the form data of the getting the list of books form.
 */
class GetMyBooksFormData {

    @NotEmpty(message = "{empty.getAllBooksFormData.emailAddress}")
    @Email(message = "{notvalid.getAllBooksFormData.emailAddress}")
    var emailAddress: String = ""
        get() = field.trim()

}
