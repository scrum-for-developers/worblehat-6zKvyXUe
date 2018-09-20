package de.codecentric.psd.worblehat.web.formdata

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class BookBorrowFormDataTest {

    private var formData: BookBorrowFormData? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        formData = BookBorrowFormData()
    }

    @Test
    fun getIsbnWithWhitespaces() {
        formData!!.isbn = " mit "
        assertThat(formData!!.isbn, `is`("mit"))
    }

    @Test
    fun getIsbnWithoutWhitespaces() {
        formData!!.isbn = "ohne"
        assertThat(formData!!.isbn, `is`("ohne"))
    }

    @Test
    fun getEmailWithWhitespaces() {
        formData!!.email = " mit "
        assertThat(formData!!.email, `is`("mit"))
    }

    @Test
    fun getEmailWithoutWhitespaces() {
        formData!!.email = "ohne"
        assertThat(formData!!.email, `is`("ohne"))
    }
}