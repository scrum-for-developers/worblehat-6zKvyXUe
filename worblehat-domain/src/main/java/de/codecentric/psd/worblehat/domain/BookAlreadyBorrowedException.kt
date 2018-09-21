package de.codecentric.psd.worblehat.domain

/**
 * Exception for borrowing a book
 */
class BookAlreadyBorrowedException(message: String) : Exception(message) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
