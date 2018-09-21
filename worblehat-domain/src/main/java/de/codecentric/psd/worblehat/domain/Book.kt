package de.codecentric.psd.worblehat.domain

import java.io.Serializable
import javax.persistence.*

/**
 * Entity implementation class for Entity: Book
 */
@Entity
class Book() : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0

    var title: String = ""
    var author: String = ""
    var edition: String = ""

    // TODO: convert String to an ISBN class, that ensures a valid ISBN
    var isbn: String = ""
    var yearOfPublication: Int = 0
    @Lob
    var description: String = ""

    @OneToOne(mappedBy = "borrowedBook", orphanRemoval = true)
    var borrowing: Borrowing? = null
        private set

    /**
     * Creates a new book instance.
     *
     * @param title
     * the title
     * @param author
     * the author
     * @param edition
     * the edition
     * @param isbn
     * the isbn
     * @param yearOfPublication
     * the yearOfPublication
     */
    constructor(title: String,
                author: String,
                edition: String,
                isbn: String,
                yearOfPublication: Int) : this() {
        this.title = title
        this.author = author
        this.edition = edition
        this.isbn = isbn
        this.yearOfPublication = yearOfPublication
    }

    constructor(title: String,
                author: String,
                edition: String,
                isbn: String,
                yearOfPublication: Int, description: String) : this() {
        this.title = title
        this.author = author
        this.edition = edition
        this.isbn = isbn
        this.yearOfPublication = yearOfPublication
        this.description = description
    }

    fun isSameCopy(book: Book): Boolean {
        return title == book.title && author == book.author && isbn == book.isbn
    }

    fun isSameEdition(book: Book): Boolean {
        return edition == book.edition
    }

    fun borrowNowByBorrower(borrowerEmailAddress: String) {
        if (borrowing == null) {
            this.borrowing = Borrowing(this, borrowerEmailAddress)
        }
    }

    override fun toString(): String {
        return "Book{" +
                "title='" + title + '\''.toString() +
                ", author='" + author + '\''.toString() +
                ", edition='" + edition + '\''.toString() +
                ", isbn='" + isbn + '\''.toString() +
                ", yearOfPublication=" + yearOfPublication + '\''.toString() +
                ", description='" + description +
                '}'.toString()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
