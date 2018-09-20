package de.codecentric.psd.worblehat.domain;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Borrowing Entity
 */
@Entity
public class Borrowing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // NOSONAR

	private String borrowerEmailAddress;

	@Temporal(TemporalType.DATE)
	private Date borrowDate;

	@OneToOne()
	private Book borrowedBook;

	/**
	 * @param book
	 * The borrowed book
	 * @param borrowerEmailAddress
	 * The borrowers e-mail Address
	 * @param borrowDate
	 * The borrow date
	 */
	public Borrowing(Book book, String borrowerEmailAddress, DateTime borrowDate) {
		super();
		this.borrowedBook = book;
		this.borrowerEmailAddress = borrowerEmailAddress;
		this.borrowDate = borrowDate.toDate();
	}

	public Borrowing(Book book, String borrowerEmailAddress) {
		this(book, borrowerEmailAddress, DateTime.now());
	}

	private Borrowing() {
		// for JPA
	}

	public String getBorrowerEmailAddress() {
		return borrowerEmailAddress;
	}

	public String getReturnDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(borrowDate);
		cal.add(Calendar.DAY_OF_MONTH, 21);
		return new SimpleDateFormat("dd.MM.yyyy").format(cal.getTime());
	}

	public Book getBorrowedBook() {
		return borrowedBook;
	}
}
