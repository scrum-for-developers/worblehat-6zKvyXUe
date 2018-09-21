package de.codecentric.psd.worblehat.web.formdata

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class GetMyBooksFormDataTest {

    private val formData = GetMyBooksFormData()

    @Test
    fun getEmailAddressWithWhitespaces() {
        formData.emailAddress = " mit "
        assertThat(formData.emailAddress, `is`("mit"))
    }

    @Test
    fun getEmailAddressWithoutWhitespaces() {
        formData.emailAddress = "ohne"
        assertThat(formData.emailAddress, `is`("ohne"))
    }

    @Test
    fun getNullEmail() {
        formData.emailAddress = ""
        assertThat(formData.emailAddress, `is`(""))
    }
}