package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.Book
import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.domain.Borrowing
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Mockito.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class BorrowBookControllerTest {

    private var bookService: BookService? = null

    private var borrowBookController: BorrowBookController? = null

    private var bindingResult: BindingResult? = null

    private var bookBorrowFormData: BookBorrowFormData? = null

    @Before
    fun setUp() {
        bookService = mock(BookService::class.java)
        bindingResult = MapBindingResult(HashMap<Any, Any>(), "")
        bookBorrowFormData = BookBorrowFormData()
        borrowBookController = BorrowBookController(bookService!!)
    }

    @Test
    fun shouldSetupForm() {
        val modelMap = ModelMap()

        borrowBookController!!.setupForm(modelMap)

        assertThat<Any>(modelMap["borrowFormData"], `is`(not(nullValue())))
    }

    @Test
    fun shouldNavigateToBorrowWhenResultHasErrors() {
        bindingResult!!.addError(ObjectError("", ""))

        val navigateTo = borrowBookController!!.processSubmit(bookBorrowFormData!!, bindingResult!!)

        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldRejectBorrowingIfBookDoesNotExist() {
        `when`(bookService!!.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(null)

        val navigateTo = borrowBookController!!.processSubmit(bookBorrowFormData!!, bindingResult!!)

        assertThat(bindingResult!!.hasFieldErrors("isbn"), `is`(true))
        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldRejectAlreadyBorrowedBooks() {
        bookBorrowFormData!!.email = BORROWER_EMAIL
        bookBorrowFormData!!.isbn = TEST_BOOK.isbn
        `when`(bookService!!.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(setOf(TEST_BOOK))
        val navigateTo = borrowBookController!!.processSubmit(bookBorrowFormData!!, bindingResult!!)

        assertThat(bindingResult!!.hasFieldErrors("isbn"), `is`(true))
        assertThat(bindingResult!!.getFieldError("isbn")!!.code, `is`("noBorrowableBooks"))
        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldNavigateHomeOnSuccess() {
        bookBorrowFormData!!.email = BORROWER_EMAIL
        bookBorrowFormData!!.isbn = TEST_BOOK.isbn
        `when`(bookService!!.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(setOf(TEST_BOOK))
        `when`(bookService!!.borrowBook(any(), any())).thenReturn(Optional.of(Borrowing(TEST_BOOK, BORROWER_EMAIL)))

        val navigateTo = borrowBookController!!.processSubmit(bookBorrowFormData!!, bindingResult!!)
        verify<BookService>(bookService).borrowBook(TEST_BOOK.isbn, BORROWER_EMAIL)
        assertThat(navigateTo, `is`("home"))
    }

    @Test
    fun shouldNavigateToHomeOnErrors() {
        val navigateTo = borrowBookController!!.handleErrors(Exception(), MockHttpServletRequest())

        assertThat(navigateTo, `is`("home"))
    }

    companion object {

        private val TEST_BOOK = Book("title", "author", "edition", "isbn", 2016)

        val BORROWER_EMAIL = "someone@codecentric.de"
    }
}
