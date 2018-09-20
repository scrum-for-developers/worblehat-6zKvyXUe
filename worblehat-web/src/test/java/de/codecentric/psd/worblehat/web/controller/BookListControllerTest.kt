package de.codecentric.psd.worblehat.web.controller

import com.nhaarman.mockitokotlin2.mock
import de.codecentric.psd.worblehat.domain.Book
import de.codecentric.psd.worblehat.domain.BookService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.ui.ModelMap

class BookListControllerTest {

    private var bookService: BookService = mock()
    private lateinit var bookListController: BookListController
    private lateinit var modelMap: ModelMap

    @Before
    fun setUp() {
        bookService = mock(BookService::class.java)
        bookListController = BookListController(bookService)
        modelMap = ModelMap()
    }

    @Test
    fun shouldNavigateToBookList() {
        val url = bookListController.setupForm(modelMap)
        assertThat(url, `is`("bookList"))
    }

    @Test
    fun shouldContainBooks() {
        val bookList = listOf(TEST_BOOK)
        `when`(bookService.findAllBooks()).thenReturn(bookList)
        bookListController.setupForm(modelMap)
        val actualBooks = modelMap["books"] as List<Book>
        assertThat(actualBooks, `is`<List<Book>>(bookList))
    }

    companion object {
        private val TEST_BOOK = Book("title", "author", "edition", "isbn", 2016)
    }

}
