package de.codecentric.psd.worblehat.web.formdata;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookBorrowFormDataTest {

    private BookBorrowFormData formData;

    @Before
    public void setUp() throws Exception {
        formData = new BookBorrowFormData();
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
    public void getEmailWithWhitespaces() {
        formData.setEmail(" mit ");
        assertThat(formData.getEmail(), is("mit"));
    }

    @Test
    public void getEmailWithoutWhitespaces() {
        formData.setEmail("ohne");
        assertThat(formData.getEmail(), is("ohne"));
    }
}