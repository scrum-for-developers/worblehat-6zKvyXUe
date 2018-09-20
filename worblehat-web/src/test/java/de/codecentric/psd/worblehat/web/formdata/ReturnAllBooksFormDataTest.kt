package de.codecentric.psd.worblehat.web.formdata

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class ReturnAllBooksFormDataTest {

    private var formData: ReturnAllBooksFormData? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        formData = ReturnAllBooksFormData()
    }

    @Test
    fun getTitleWithWhitespaces() {
        formData!!.title = " mit "
        assertThat(formData!!.title, `is`("mit"))
    }

    @Test
    fun getTitleWithoutWhitespaces() {
        formData!!.title = "ohne"
        assertThat(formData!!.title, `is`("ohne"))
    }

    @Test
    fun getEmailAddressWithWhitespaces() {
        formData!!.emailAddress = " mit "
        assertThat(formData!!.emailAddress, `is`("mit"))
    }

    @Test
    fun getEmailAddressWithoutWhitespaces() {
        formData!!.emailAddress = "ohne"
        assertThat(formData!!.emailAddress, `is`("ohne"))
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
    fun getRadioButtonValues() {
        formData!!.radioButtonValues = arrayOf("Test1", "Test2")
        assertThat(formData!!.radioButtonValues, `is`(arrayOf("Test1", "Test2")))
    }

    @Test
    fun getRadioButtonSelectionWithWhitespaces() {
        formData!!.radioButtonSelection = " mit "
        assertThat(formData!!.radioButtonSelection, `is`("mit"))
    }

    @Test
    fun getRadioButtonSelectionWithoutWhitespaces() {
        formData!!.radioButtonSelection = "ohne"
        assertThat(formData!!.radioButtonSelection, `is`("ohne"))
    }

}