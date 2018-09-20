package de.codecentric.psd.worblehat.web.formdata

import de.codecentric.psd.worblehat.web.validation.ISBN

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * This class represent the form data of the return book form.
 */
class ReturnAllBooksFormData {

    @NotEmpty(message = "{empty.returnAllBookFormData.emailAddress}")
    @Email(message = "{notvalid.returnAllBookFormData.emailAddress}")
    var emailAddress: String = ""
        get() = field.trim()

    @ISBN(message = "{notvalid.returnOneBook.isbn}")
    var isbn: String = ""
        get() = field.trim()

    var title: String = ""
        get() = field.trim()

    var radioButtonValues = arrayOf("ISBN", "Title")

    var radioButtonSelection: String = "ISBN"
        get() = field.trim()
}
