package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.web.formdata.GetMyBooksFormData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class GetMyBooksControllerTest {

    private val getMyBooksController = GetMyBooksController()
    private val formData: GetMyBooksFormData = GetMyBooksFormData()
    private val bindingResult = MapBindingResult(HashMap<Any, Any>(), "")

    @Test
    fun shouldPrepareView() {
        val modelMap = ModelMap()

        getMyBooksController.prepareView(modelMap)

        assertThat<Any>(modelMap["getMyBooksFormData"], `is`(not(nullValue())))
    }

    @Test
    fun shouldRejectError() {
        bindingResult.addError(ObjectError("", ""))
        val navigateTo = getMyBooksController.getMyBooks(formData, bindingResult)
        assertThat(navigateTo, `is`("getMyBooks"))
    }

    @Test
    fun shouldRedirectToMyBooks() {
        formData.emailAddress = "me@me.me"
        val navigateTo = getMyBooksController.getMyBooks(formData, bindingResult)
        assertThat(navigateTo, `is`("redirect:myBooksList?email=me@me.me"))
    }
}