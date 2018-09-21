package de.codecentric.psd.worblehat.web.formdata

import de.codecentric.psd.worblehat.web.validation.ISBN
import de.codecentric.psd.worblehat.web.validation.Numeric
import org.hibernate.validator.constraints.Length

import javax.validation.constraints.NotEmpty

/**
 * This class represent the form data of the add book form.
 */
class BookDataFormData {

    @NotEmpty(message = "{empty.bookDataFormData.title}")
    var title: String = ""
        set(title) {
            field = title.trim()
        }

    @NotEmpty(message = "{empty.bookDataFormData.edition}")
    @Numeric(message = "{notvalid.bookDataFormData.edition}")
    var edition: String = ""
        set(edition) {
            field = edition.trim()
        }

    @NotEmpty(message = "{empty.bookDataFormData.yearOfPublication}")
    @Numeric(message = "{notvalid.bookDataFormDxata.yearOfPublication}")
    @Length(message = "{invalid.length.bookDataFormData.yearOfPublication}", min = 4, max = 4)
    var yearOfPublication: String = ""
        set(yearOfPublication) {
            field = yearOfPublication.trim()
        }

    @NotEmpty(message = "{empty.bookDataFormData.isbn}")
    @ISBN(message = "{notvalid.bookDataFormData.isbn}")
    var isbn: String = ""
        set(isbn) {
            field = isbn.trim()
        }

    @NotEmpty(message = "{empty.bookDataFormData.author}")
    var author: String = ""
        set(author) {
            field = author.trim()
        }

    var description: String = ""

    override fun toString(): String {
        return ("BookDataFormData [title=" + this.title + ", edition=" + this.edition
                + ", yearOfPublication=" + this.yearOfPublication + ", isbn=" + this.isbn + ", author=" + this.author
                + "]")
    }

}
