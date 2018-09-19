package de.codecentric.psd.worblehat.web.formdata;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represent the form data of the getting the list of books form.
 */
public class GetMyBooksFormData {

	@NotEmpty(message = "{empty.getAllBooksFormData.emailAddress}")
	@Email(message = "{notvalid.getAllBooksFormData.emailAddress}")
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
