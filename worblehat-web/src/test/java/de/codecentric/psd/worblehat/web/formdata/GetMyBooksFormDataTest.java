package de.codecentric.psd.worblehat.web.formdata;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class GetMyBooksFormDataTest {

    private GetMyBooksFormData formData;

    @Before
    public void setUp() throws Exception {
        formData = new GetMyBooksFormData();
    }

    @Test
    public void getEmailAddressWithWhitespaces() {
        formData.setEmailAddress(" mit ");
        assertThat(formData.getEmailAddress(), is("mit"));
    }

    @Test
    public void getEmailAddressWithoutWhitespaces() {
        formData.setEmailAddress("ohne");
        assertThat(formData.getEmailAddress(), is("ohne"));
    }

    @Test
    public void getNullEmail() {
        formData.setEmailAddress(null);
        assertThat(formData.getEmailAddress(), is(nullValue()));
    }
}