package de.codecentric.psd.worblehat.domain

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
open class StandardBookService @Autowired constructor(
        private val borrowingRepository: BorrowingRepository,
        private val bookRepository: BookRepository) : BookService {
    override fun findBookById(id: Long): Book {
        return bookRepository.findById(id).get()
    }

    override fun returnAllBooksByBorrower(borrowerEmailAddress: String) {
        with(borrowingRepository) {
            findBorrowingsByBorrower(borrowerEmailAddress).forEach { delete(it) }
        }
    }

    override fun returnBookByBorrowerAndIsbn(borrower: String, isbn: String) {
        with(borrowingRepository) {
            findBorrowingsByBorrower(borrower).filter { it.borrowedBook?.isbn == isbn }.forEach { delete(it) }
        }
    }

    override fun returnBookByBorrowerAndTitle(borrower: String, title: String) {
        with(borrowingRepository) {
            findBorrowingsByBorrower(borrower).filter { it.borrowedBook?.title == title }.forEach { delete(it) }
        }
    }

    override fun borrowBook(isbn: String, borrower: String): Optional<Borrowing> {
        val books = bookRepository.findByIsbn(isbn)

        val unborrowedBook = books.stream()
                .filter { book -> book.borrowing == null }
                .findFirst()

        return unborrowedBook.map { book ->
            book.borrowNowByBorrower(borrower)
            borrowingRepository.save(book.borrowing!!)
            book.borrowing
        }
    }

    override fun findBooksByIsbn(isbn: String): Set<Book> {
        return bookRepository.findByIsbn(isbn) //null if not found
    }

    override fun findAllBooks(): List<Book> {
        return bookRepository.findAllByOrderByTitle()
    }

    override fun findBooksByEmail(email: String): List<Borrowing> {
        return borrowingRepository.findBorrowingsByBorrower(email)
    }

    override fun createBook(title: String,
                            author: String,
                            edition: String,
                            isbn: String,
                            yearOfPublication: Int): Optional<Book> {
        val book = Book(title, author, edition, isbn, yearOfPublication)

        val bookFromRepo = bookRepository.findTopByIsbn(isbn)

        return if (!bookFromRepo.isPresent || book.isSameCopy(bookFromRepo.get()) && book.isSameEdition(bookFromRepo.get())) {
            Optional.of(bookRepository.save(book))
        } else
            Optional.empty()
    }

    override fun createBook(title: String,
                            author: String,
                            edition: String,
                            isbn: String,
                            yearOfPublication: Int,
                            description: String): Optional<Book> {
        val book = Book(title, author, edition, isbn, yearOfPublication, description)

        val bookFromRepo = bookRepository.findTopByIsbn(isbn)

        return if (!bookFromRepo.isPresent || book.isSameCopy(bookFromRepo.get())) {
            Optional.of(bookRepository.save(book))
        } else
            Optional.empty()
    }

    override fun bookExists(isbn: String): Boolean {
        val books = bookRepository.findByIsbn(isbn)
        return !books.isEmpty()
    }

    override fun deleteAllBooks() {
        borrowingRepository.deleteAll()
        bookRepository.deleteAll()
    }


}
