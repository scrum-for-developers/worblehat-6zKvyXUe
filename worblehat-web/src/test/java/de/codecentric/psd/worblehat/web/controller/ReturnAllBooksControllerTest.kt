package de.codecentric.psd.worblehat.web.controller

import com.nhaarman.mockitokotlin2.mock
import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class ReturnAllBooksControllerTest {

    private lateinit var returnAllBooksController: ReturnAllBooksController
    private val bookService: BookService = mock()
    private val returnAllBooksFormData = ReturnAllBooksFormData()
    private val bindingResult = MapBindingResult(HashMap<Any, Any>(), "")

    @Before
    @Throws(Exception::class)
    fun setUp() {
        returnAllBooksController = ReturnAllBooksController(bookService)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSetupForm() {
        val modelMap = ModelMap()

        returnAllBooksController.prepareView(modelMap)

        assertThat<Any>(modelMap["returnAllBookFormData"], `is`(not(nullValue())))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRejectErrors() {
        bindingResult.addError(ObjectError("", ""))

        val navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult)

        assertThat(navigateTo, `is`("returnAllBooks"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnAllBooksAndNavigateHome() {
        val borrower = "someone@codecentric.de"
        returnAllBooksFormData.emailAddress = borrower

        val navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult)

        verify<BookService>(bookService).returnAllBooksByBorrower(borrower)
        assertThat(navigateTo, `is`("redirect:/bookList"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnBookByIsbnAndNavigateHome() {
        val borrower = "someone@codecentric.de"
        val isbn = "123456789X"
        returnAllBooksFormData.emailAddress = borrower
        returnAllBooksFormData.isbn = isbn
        returnAllBooksFormData.radioButtonSelection = "ISBN"

        val navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult)

        verify<BookService>(bookService).returnBookByBorrowerAndIsbn(borrower, isbn)
        assertThat(navigateTo, `is`("redirect:/bookList"))
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnBookByTitleAndNavigateHome() {
        val borrower = "someone@codecentric.de"
        val title = "A book"
        returnAllBooksFormData.emailAddress = borrower
        returnAllBooksFormData.title = title
        returnAllBooksFormData.radioButtonSelection = "Title"

        val navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult)

        verify<BookService>(bookService).returnBookByBorrowerAndTitle(borrower, title)
        assertThat(navigateTo, `is`("redirect:/bookList"))
    }
}
