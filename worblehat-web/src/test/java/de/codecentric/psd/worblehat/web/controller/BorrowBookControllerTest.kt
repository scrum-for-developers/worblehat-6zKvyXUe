package de.codecentric.psd.worblehat.web.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class BorrowBookControllerTest {

    private val bookService: BookService = mock()
    private lateinit var borrowBookController: BorrowBookController
    private val bindingResult = MapBindingResult(HashMap<Any, Any>(), "")
    private val bookBorrowFormData = BookBorrowFormData()

    @Before
    fun setUp() {
        borrowBookController = BorrowBookController(bookService)
    }

    @Test
    fun shouldSetupForm() {
        val modelMap = ModelMap()

        borrowBookController.setupForm(modelMap)

        assertThat<Any>(modelMap["borrowFormData"], `is`(not(nullValue())))
    }

    @Test
    fun shouldNavigateToBorrowWhenResultHasErrors() {
        bindingResult.addError(ObjectError("", ""))

        val navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult)

        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldRejectBorrowingIfBookDoesNotExist() {
        whenever(bookService.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(null)

        val navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult)

        assertThat(bindingResult.hasFieldErrors("isbn"), `is`(true))
        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldRejectAlreadyBorrowedBooks() {
        bookBorrowFormData.email = BORROWER_EMAIL
        bookBorrowFormData.isbn = TEST_BOOK.isbn
        whenever(bookService.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(setOf(TEST_BOOK))
        val navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult)

        assertThat(bindingResult.hasFieldErrors("isbn"), `is`(true))
        assertThat(bindingResult.getFieldError("isbn")?.code, `is`("noBorrowableBooks"))
        assertThat(navigateTo, `is`("borrow"))
    }

    @Test
    fun shouldNavigateHomeOnSuccess() {
        bookBorrowFormData.email = BORROWER_EMAIL
        bookBorrowFormData.isbn = TEST_BOOK.isbn
        whenever(bookService.findBooksByIsbn(TEST_BOOK.isbn)).thenReturn(setOf(TEST_BOOK))
        whenever(bookService.borrowBook(any(), any())).thenReturn(Optional.of(Borrowing(TEST_BOOK, BORROWER_EMAIL)))

        val navigateTo = borrowBookController.processSubmit(bookBorrowFormData, bindingResult)
        verify(bookService).borrowBook(TEST_BOOK.isbn, BORROWER_EMAIL)
        assertThat(navigateTo, `is`("redirect:/bookList"))
    }

    @Test
    fun shouldNavigateToHomeOnErrors() {
        val navigateTo = borrowBookController.handleErrors(Exception(), MockHttpServletRequest())

        assertThat(navigateTo, `is`("redirect:/borrow"))
    }

    companion object {

        private val TEST_BOOK = Book("title", "author", "edition", "isbn", 2016)

        const val BORROWER_EMAIL = "someone@codecentric.de"
    }
}
