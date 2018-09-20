package de.codecentric.psd.worblehat.web.formdata;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ReturnAllBooksFormDataTest {

    private ReturnAllBooksFormData formData;

    @Before
    public void setUp() throws Exception {
        formData = new ReturnAllBooksFormData();
    }

    @Test
    public void getTitleWithWhitespaces() {
        formData.setTitle(" mit ");
        assertThat(formData.getTitle(), is("mit"));
    }

    @Test
    public void getTitleWithoutWhitespaces() {
        formData.setTitle("ohne");
        assertThat(formData.getTitle(), is("ohne"));
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
    public void getIsbnWithWhitespaces() {
        formData.setIsbn(" mit ");
        assertThat(formData.getIsbn(), is("mit"));
    }

    @Test
    public void getIsbnWithoutWhitespaces() {
        formData.setIsbn("ohne");
        assertThat(formData.getIsbn(), is("ohne"));
    }

    @Test
    public void getRadioButtonValues() {
        formData.setRadioButtonValues(new String[]{"Test1", "Test2"});
        assertThat(formData.getRadioButtonValues(), is(new String[]{"Test1", "Test2"}));
    }

    @Test
    public void getRadioButtonSelectionWithWhitespaces() {
        formData.setRadioButtonSelection(" mit ");
        assertThat(formData.getRadioButtonSelection(), is("mit"));
    }

    @Test
    public void getRadioButtonSelectionWithoutWhitespaces() {
        formData.setRadioButtonSelection("ohne");
        assertThat(formData.getRadioButtonSelection(), is("ohne"));
    }

}