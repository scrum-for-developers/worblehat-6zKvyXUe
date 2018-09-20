package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.Book
import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.any
import org.mockito.Matchers.anyInt
import org.mockito.Mockito.*
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class InsertBookControllerTest {

    private var insertBookController: InsertBookController? = null

    private var bookService: BookService? = null

    private var bookDataFormData: BookDataFormData? = null

    private var bindingResult: BindingResult? = null

    @Before
    fun setUp() {
        bookService = mock(BookService::class.java)
        insertBookController = InsertBookController(bookService!!)
        bookDataFormData = BookDataFormData()
        bindingResult = MapBindingResult(HashMap<Any, Any>(), "")
    }

    @Test
    fun shouldSetupForm() {
        val modelMap = ModelMap()

        insertBookController!!.setupForm(modelMap)

        assertThat<Any>(modelMap["bookDataFormData"], `is`(not(nullValue())))
    }

    @Test
    fun shouldRejectErrors() {
        bindingResult!!.addError(ObjectError("", ""))

        val navigateTo = insertBookController!!.processSubmit(bookDataFormData!!, bindingResult!!)

        assertThat(navigateTo, `is`("insertBooks"))
    }

    @Test
    fun shouldCreateNewCopyOfExistingBook() {
        setupFormData()
        `when`(bookService!!.bookExists(TEST_BOOK.isbn)).thenReturn(true)
        `when`(bookService!!.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK))

        val navigateTo = insertBookController!!.processSubmit(bookDataFormData!!, bindingResult!!)

        verifyBookIsCreated()
        assertThat(navigateTo, `is`("redirect:bookList"))
    }

    @Test
    fun shouldCreateBookAndNavigateToBookList() {
        setupFormData()
        `when`(bookService!!.bookExists(TEST_BOOK.isbn)).thenReturn(false)
        `when`(bookService!!.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK))

        val navigateTo = insertBookController!!.processSubmit(bookDataFormData!!, bindingResult!!)

        verifyBookIsCreated()
        assertThat(navigateTo, `is`("redirect:bookList"))
    }

    private fun verifyBookIsCreated() {
        verify<BookService>(bookService).createBook(TEST_BOOK.title, TEST_BOOK.author,
                TEST_BOOK.edition, TEST_BOOK.isbn, TEST_BOOK.yearOfPublication, TEST_BOOK.description)
    }

    private fun setupFormData() {
        bookDataFormData!!.title = TEST_BOOK.title
        bookDataFormData!!.author = TEST_BOOK.author
        bookDataFormData!!.edition = TEST_BOOK.edition
        bookDataFormData!!.isbn = TEST_BOOK.isbn
        bookDataFormData!!.yearOfPublication = TEST_BOOK.yearOfPublication.toString()
        bookDataFormData!!.description = TEST_BOOK.description
    }

    companion object {

        private val TEST_BOOK = Book("title", "author", "edition", "isbn", 2016, "desc")
    }
}
