package de.codecentric.psd.worblehat.domain

import org.joda.time.DateTime
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

/**
 * Borrowing Entity
 */
@Entity
class Borrowing() : Serializable {

    @JvmOverloads constructor(
            book: Book,
            borrowerEmailAddress: String,
            borrowDate: DateTime = DateTime.now()) : this() {
        this.borrowedBook = book
        this.borrowerEmailAddress = borrowerEmailAddress
        this.borrowDate = borrowDate.toDate()
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0 // NOSONAR

    @Temporal(TemporalType.DATE)
    private var borrowDate: Date = Date()

    var borrowerEmailAddress = ""

    @OneToOne
    var borrowedBook: Book? = null

    val returnDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = borrowDate
            cal.add(Calendar.DAY_OF_MONTH, 21)
            return SimpleDateFormat("dd.MM.yyyy").format(cal.time)
        }

    companion object {
        private const val serialVersionUID = 1L
    }
}
