package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This class represent the form data of the return book form.
 */
public class ReturnAllBooksFormData {

    @NotEmpty(message = "{empty.returnAllBookFormData.emailAddress}")
    @Email(message = "{notvalid.returnAllBookFormData.emailAddress}")
    private String emailAddress;

    @ISBN(message = "{notvalid.returnOneBook.isbn}")
    private String isbn;

    private String title;

    private String[] radioButtonValues = new String[]{"ISBN", "Title"};

    private String radioButtonSelection = "ISBN";

    public String getTitle() {
        return title == null ? null : title.trim();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmailAddress() {
        return emailAddress == null ? null : emailAddress.trim();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIsbn() {
        return isbn == null ? null : isbn.trim();
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String[] getRadioButtonValues() {
        return radioButtonValues;
    }

    public void setRadioButtonValues(String[] radioButtonValues) {
        this.radioButtonValues = radioButtonValues;
    }

    public String getRadioButtonSelection() {
        return radioButtonSelection == null ? null : radioButtonSelection.trim();
    }

    public void setRadioButtonSelection(String radioButtonSelection) {
        this.radioButtonSelection = radioButtonSelection;
    }
}
