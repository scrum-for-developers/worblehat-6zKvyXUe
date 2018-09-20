package de.codecentric.psd.worblehat.web.formdata;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This class represent the form data of the getting the list of books form.
 */
public class GetMyBooksFormData {

	@NotEmpty(message = "{empty.getAllBooksFormData.emailAddress}")
	@Email(message = "{notvalid.getAllBooksFormData.emailAddress}")
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress == null ? null : emailAddress.trim();
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
