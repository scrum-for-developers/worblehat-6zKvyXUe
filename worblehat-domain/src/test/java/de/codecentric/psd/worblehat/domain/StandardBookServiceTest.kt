package de.codecentric.psd.worblehat.domain

import com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.joda.time.DateTime
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.any
import org.mockito.Mockito.*
import java.util.*

class StandardBookServiceTest {

    private var borrowingRepository: BorrowingRepository = mock()
    private var bookRepository: BookRepository = mock()
    private lateinit var bookService: BookService

    private var aBook = Book("title", "author", "edition", "isbn", 2016)
    private var aCopyofBook = Book("title", "author", "edition", "isbn", 2016)
    private var anotherBook = Book(TITLE, "author2", "edition2", "isbn2", 2016)

    private var aBorrowedBook = Book("title", "author", "edition", "isbn", 2016)
    private var aCopyofBorrowedBook = Book("title", "author", "edition", "isbn", 2016)
    private var anotherBorrowedBook = Book("title2", "author2", "edition2", ISBN, 2016)
    private var aBorrowing = Borrowing(aBorrowedBook, BORROWER_EMAIL, NOW)
    private var aBorrowingOfCopy = Borrowing(aCopyofBorrowedBook, BORROWER_EMAIL, NOW)
    private var anotherBorrowing = Borrowing(anotherBorrowedBook, BORROWER_EMAIL, NOW)

    private val bookArgumentCaptor: KArgumentCaptor<Book> = argumentCaptor()

    @Before
    fun setup() {
        aBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL)
        aCopyofBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL)
        anotherBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL)

        whenever(borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
                .thenReturn(Arrays.asList<Borrowing>(aBorrowing, anotherBorrowing))

        whenever(borrowingRepository.findBorrowingForBook(aBook)).thenReturn(null)

        bookService = StandardBookService(borrowingRepository, bookRepository)
        whenever(bookService.findBooksByEmail(BORROWER_EMAIL))
                .thenReturn(Arrays.asList<Borrowing>(aBorrowing, anotherBorrowing))

    }

    private fun givenALibraryWith(vararg books: Book) {
        val bookCopies = HashMap<String, MutableSet<Book>>()
        for (book in books) {
            if (!bookCopies.containsKey(book.isbn)) {
                bookCopies[book.isbn] = mutableSetOf()
            }
            bookCopies[book.isbn]?.add(book)
        }
        for ((key, value) in bookCopies) {
            whenever(bookRepository.findByIsbn(key)).thenReturn(value)
            whenever(bookRepository.findTopByIsbn(key)).thenReturn(Optional.of(value.iterator().next()))
        }
    }

    @Test
    fun shouldReturnAllBooksOfOnePerson() {
        bookService.returnAllBooksByBorrower(BORROWER_EMAIL)
        verify<BorrowingRepository>(borrowingRepository).delete(anotherBorrowing)
    }

    @Test
    fun shouldReturnAllBooksOfOnePersonWithISBN() {
        bookService.returnBookByBorrowerAndIsbn(BORROWER_EMAIL, ISBN)
        verify<BorrowingRepository>(borrowingRepository).delete(anotherBorrowing)
    }

    @Test
    fun shouldReturnAllBooksOfOnePersonWithTitle() {
        bookService.returnBookByBorrowerAndTitle(BORROWER_EMAIL, TITLE)
        verify<BorrowingRepository>(borrowingRepository).delete(anotherBorrowing)
    }

    @Test
    fun shouldSaveBorrowingWithBorrowerEmail() {
        givenALibraryWith(aBook)
        val borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing::class.java)
        bookService.borrowBook(aBook.isbn, BORROWER_EMAIL)
        verify<BorrowingRepository>(borrowingRepository).save(borrowingArgumentCaptor.capture())
        assertThat(borrowingArgumentCaptor.value.borrowerEmailAddress, equalTo(BORROWER_EMAIL))
    }

    @Test
    fun shouldNotBorrowWhenBookAlreadyBorrowed() {
        givenALibraryWith(aBorrowedBook)
        val borrowing = bookService.borrowBook(aBorrowedBook.isbn, BORROWER_EMAIL)
        assertTrue(!borrowing.isPresent)
    }

    @Test
    fun shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() {
        givenALibraryWith(aBook, aCopyofBook)
        val borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing::class.java)
        bookService.borrowBook(aBook.isbn, BORROWER_EMAIL)
        verify<BorrowingRepository>(borrowingRepository).save(borrowingArgumentCaptor.capture())
        assertThat(borrowingArgumentCaptor.value.borrowerEmailAddress, `is`(BORROWER_EMAIL))
        assertThat<Book>(borrowingArgumentCaptor.value.borrowedBook, either(`is`<Book>(aBook)).or(`is`<Book>(aCopyofBook)))
    }

    @Test
    fun shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() {
        givenALibraryWith(aBook, aBorrowedBook)
        val borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing::class.java)
        bookService.borrowBook(aBook.isbn, BORROWER_EMAIL)
        verify<BorrowingRepository>(borrowingRepository).save(borrowingArgumentCaptor.capture())
        assertThat(borrowingArgumentCaptor.value.borrowerEmailAddress, `is`(BORROWER_EMAIL))
        assertThat<Book>(borrowingArgumentCaptor.value.borrowedBook, `is`<Book>(aBook))
    }

    @Test
    fun shouldThrowExceptionWhenAllBooksAreBorrowedRightNow() {
        givenALibraryWith(aBorrowedBook, aCopyofBorrowedBook)
        val borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing::class.java)
        val borrowing = bookService.borrowBook(aBorrowedBook.isbn, BORROWER_EMAIL)
        assertThat(borrowing, isEmpty())
        verify<BorrowingRepository>(borrowingRepository, never()).save<Borrowing>(any(Borrowing::class.java))
    }

    @Test
    fun shouldCreateBook() {
        whenever(bookRepository.save<Book>(any(Book::class.java))).thenReturn(aBook)
        bookService.createBook(aBook.title, aBook.author, aBook.edition,
                aBook.isbn, aBook.yearOfPublication)

        assertBookSavedToRepositoryAndInfoPassedCorrectly()
    }

    @Test
    fun shouldCreateBookWithDescription() {
        whenever(bookRepository.save<Book>(any(Book::class.java))).thenReturn(aBook)
        bookService.createBook(aBook.title, aBook.author, aBook.edition,
                aBook.isbn, aBook.yearOfPublication, aBook.description)
        assertBookSavedToRepositoryAndInfoPassedCorrectly()
        assertThat(bookArgumentCaptor.firstValue.description, `is`(aBook.description))

    }

    private fun assertBookSavedToRepositoryAndInfoPassedCorrectly() {
        // assert that book was saved to repository
        verify<BookRepository>(bookRepository).save(bookArgumentCaptor.capture())

        // assert that the information was passed correctly to create the book
        assertThat(bookArgumentCaptor.firstValue.title, `is`(aBook.title))
        assertThat(bookArgumentCaptor.firstValue.author, `is`(aBook.author))
        assertThat(bookArgumentCaptor.firstValue.edition, `is`(aBook.edition))
        assertThat(bookArgumentCaptor.firstValue.isbn, `is`(aBook.isbn))
        assertThat(bookArgumentCaptor.firstValue.yearOfPublication, `is`(aBook.yearOfPublication))
    }

    @Test
    fun shouldCreateAnotherCopyOfExistingBook() {
        whenever(bookRepository.save<Book>(any(Book::class.java))).thenReturn(aBook)
        bookService.createBook(aBook.title, aBook.author, aBook.edition,
                aBook.isbn, aBook.yearOfPublication)
        verify<BookRepository>(bookRepository, times(1)).save<Book>(any(Book::class.java))
    }

    @Test
    fun shouldCreateAnotherCopyOfExistingBookWithDescription() {
        whenever(bookRepository.save<Book>(any(Book::class.java))).thenReturn(aBook)
        bookService.createBook(aBook.title, aBook.author, aBook.edition,
                aBook.isbn, aBook.yearOfPublication, aBook.description)
        verify<BookRepository>(bookRepository, times(1)).save<Book>(any(Book::class.java))
    }

    @Test
    fun shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
        givenALibraryWith(aBook)
        bookService.createBook(aBook.title + "X", aBook.author, aBook.edition,
                aBook.isbn, aBook.yearOfPublication)
        verify<BookRepository>(bookRepository, times(0)).save<Book>(any(Book::class.java))
    }

    @Test
    fun shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
        givenALibraryWith(aBook)
        bookService.createBook(aBook.title, aBook.author + "X", aBook.edition,
                aBook.isbn, aBook.yearOfPublication)
        verify<BookRepository>(bookRepository, times(0)).save<Book>(any(Book::class.java))
    }

    @Test
    fun shouldFindAllBooks() {
        val expectedBooks = ArrayList<Book>()
        expectedBooks.add(aBook)
        whenever(bookRepository.findAllByOrderByTitle()).thenReturn(expectedBooks)
        val actualBooks = bookService.findAllBooks()
        assertThat(actualBooks, `is`<List<Book>>(expectedBooks))
    }

    @Test
    fun shouldVerifyExistingBooks() {
        whenever(bookService.findBooksByIsbn(aBook.isbn)).thenReturn(setOf<Book>(aBook))
        //when(bookRepository.findByIsbn(aBook.getIsbn())).thenReturn(Collections.singleton(aBook));
        val bookExists = bookService.bookExists(aBook.isbn)
        assertTrue(bookExists)
    }

    @Test
    fun shouldVerifyNonexistingBooks() {
        whenever(bookRepository.findByIsbn(aBook.isbn)).thenReturn(emptySet())
        val bookExists = bookService.bookExists(aBook.isbn)
        assertThat(bookExists, `is`(false))
    }

    @Test
    fun shouldDeleteAllBooksAndBorrowings() {
        bookService.deleteAllBooks()
        verify<BookRepository>(bookRepository).deleteAll()
        verify<BorrowingRepository>(borrowingRepository).deleteAll()
    }

    companion object {
        private const val BORROWER_EMAIL = "someone@codecentric.de"
        private const val ISBN = "123456789X"
        private const val TITLE = "title2"
        private val NOW = DateTime.now()
    }
}
