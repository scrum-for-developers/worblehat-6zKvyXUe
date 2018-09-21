package de.codecentric.psd.worblehat.domain

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class BookTest {

    private var BOOK = Book("Titel", "Author", "2", "1", 1234)

    @Test
    fun shouldReturnFalseWhenAuthorisDifferent() {
        val anotherCopy = Book(BOOK.title, BOOK.author, BOOK.edition, BOOK.isbn, BOOK.yearOfPublication)
        anotherCopy.author = "Bene"
        assertThat(BOOK.isSameCopy(anotherCopy), `is`(false))
    }

    @Test
    fun shouldReturnFalseWhenTitleisDifferent() {
        val anotherCopy = Book(BOOK.title, BOOK.author, BOOK.edition, BOOK.isbn, BOOK.yearOfPublication)
        anotherCopy.title = "Lord of the Rings"
        assertThat(BOOK.isSameCopy(anotherCopy), `is`(false))
    }

    @Test
    fun shouldReturnTrueWhenAllButTitleAndAuthorAndIsbnAreDifferent() {
        val anotherCopy = Book(BOOK.title, BOOK.author, BOOK.edition, BOOK.isbn, BOOK.yearOfPublication)
        anotherCopy.edition = "2000"
        anotherCopy.yearOfPublication = 2010
        assertThat(BOOK.isSameCopy(anotherCopy), `is`(true))
    }

    @Test
    fun shouldReturnFalseWhenSameIsbnAndDifferentEdition() {
        val anotherCopy = Book(BOOK.title, BOOK.author, BOOK.edition, BOOK.isbn, BOOK.yearOfPublication)
        anotherCopy.edition = "5"
        assertThat(BOOK.isSameCopy(anotherCopy) && BOOK.isSameEdition(anotherCopy), `is`(false))
    }

    @Test
    fun shouldBeBorrowable() {
        BOOK.borrowNowByBorrower("a@bc.de")
        assertThat(BOOK.borrowing?.borrowerEmailAddress, `is`("a@bc.de"))
    }

    @Test
    fun shouldIgnoreNewBorrowWhenBorrowed() {
        BOOK.borrowNowByBorrower("a@bc.de")
        BOOK.borrowNowByBorrower("a@bc.ru")
        assertThat(BOOK.borrowing?.borrowerEmailAddress, `is`("a@bc.de"))
    }
}