package de.codecentric.psd.worblehat.domain

import java.util.*

/**
 * The interface of the domain service for books.
 */
interface BookService {

    fun returnAllBooksByBorrower(borrowerEmailAddress: String)

    fun returnBookByBorrowerAndIsbn(borrower: String, isbn: String)

    fun returnBookByBorrowerAndTitle(borrower: String, title: String)

    fun borrowBook(isbn: String, borrower: String): Optional<Borrowing>

    fun findBooksByIsbn(isbn: String): Set<Book>

    fun findAllBooks(): List<Book>

    fun findBooksByEmail(email: String): List<Borrowing>

    fun createBook(title: String, author: String, edition: String, isbn: String, yearOfPublication: Int): Optional<Book>
    fun createBook(title: String, author: String, edition: String, isbn: String, yearOfPublication: Int, description: String): Optional<Book>

    fun bookExists(isbn: String): Boolean

    fun deleteAllBooks()
}
