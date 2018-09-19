package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represent the form data of the return book form.
 */
public class ReturnAllBooksFormData {

	@NotEmpty(message = "{empty.returnAllBookFormData.emailAddress}") 
	@Email(message = "{notvalid.returnAllBookFormData.emailAddress}")
	private String emailAddress;

	@ISBN(message = "{notvalid.returnOneBook.isbn}")
	private String isbn;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
