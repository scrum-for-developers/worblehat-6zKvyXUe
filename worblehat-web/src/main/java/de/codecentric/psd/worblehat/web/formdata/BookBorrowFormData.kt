package de.codecentric.psd.worblehat.web.formdata

import de.codecentric.psd.worblehat.web.validation.ISBN

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

/**
 * Form data object from the borrow view.
 */
class BookBorrowFormData {

    @NotEmpty(message = "{empty.borrowCmd.isbn}")
    @ISBN(message = "{notvalid.borrowCmd.isbn}")
    var isbn: String = ""
        get() = field.trim()

    @NotEmpty(message = "{empty.borrowCmd.email}")
    @Email(message = "{notvalid.borrowCmd.email}")
    var email: String = ""
        get() = field.trim()

}
