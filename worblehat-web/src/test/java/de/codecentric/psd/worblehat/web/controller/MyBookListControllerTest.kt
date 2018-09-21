package de.codecentric.psd.worblehat.web.controller

import com.nhaarman.mockitokotlin2.mock
import de.codecentric.psd.worblehat.domain.BookService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.springframework.ui.ModelMap

class MyBookListControllerTest {

    private lateinit var myBookListController: MyBookListController
    private val bookService: BookService = mock()

    @Before
    fun setUp() {
        myBookListController = MyBookListController(bookService)
    }

    @Test
    fun shouldSetupForm() {
        val modelMap = ModelMap()

        val navigateTo = myBookListController.setupForm(modelMap, "me@me.de")

        assertThat(modelMap["borrowings"], `is`(not(nullValue())))
        assertThat(navigateTo, `is`("myBooksList"))
    }

}