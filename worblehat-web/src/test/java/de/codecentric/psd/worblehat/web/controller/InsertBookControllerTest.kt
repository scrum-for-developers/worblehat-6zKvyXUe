package de.codecentric.psd.worblehat.web.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import de.codecentric.psd.worblehat.domain.Book
import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.MapBindingResult
import org.springframework.validation.ObjectError
import java.util.*

class InsertBookControllerTest {

    private var insertBookController: InsertBookController? = null

    private var bookService: BookService = mock()

    private var bookDataFormData: BookDataFormData? = null

    private var bindingResult: BindingResult? = null

    @Before
    fun setUp() {
        insertBookController = InsertBookController(bookService)
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
        whenever(bookService.bookExists(TEST_BOOK.isbn)).thenReturn(true)
        whenever(bookService.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK))

        val navigateTo = insertBookController!!.processSubmit(bookDataFormData!!, bindingResult!!)

        verifyBookIsCreated()
        assertThat(navigateTo, `is`("redirect:bookList"))
    }

    @Test
    fun shouldCreateBookAndNavigateToBookList() {
        setupFormData()
        whenever(bookService.bookExists(TEST_BOOK.isbn)).thenReturn(false)
        whenever(bookService.createBook(any(), any(), any(), any(), anyInt(), any())).thenReturn(Optional.of(TEST_BOOK))

        val navigateTo = insertBookController!!.processSubmit(bookDataFormData!!, bindingResult!!)

        verifyBookIsCreated()
        assertThat(navigateTo, `is`("redirect:bookList"))
    }

    private fun verifyBookIsCreated() {
        verify(bookService).createBook(TEST_BOOK.title, TEST_BOOK.author,
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
