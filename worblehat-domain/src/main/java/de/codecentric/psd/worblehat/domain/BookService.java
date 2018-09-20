package de.codecentric.psd.worblehat.domain;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface of the domain service for books.
 */
public interface BookService {

	void returnAllBooksByBorrower(String string);

	void returnBookByBorrowerAndIsbn(String borrower, String isbn);

	void returnBookByBorrowerAndTitle(String borrower, String title);

	Optional<Borrowing> borrowBook(String isbn, String borrower);

	Set<Book> findBooksByIsbn(String isbn);

	List<Book> findAllBooks();

	List<Borrowing> findBooksByEmail(String email);

	Optional<Book> createBook(String title, String author, String edition, String isbn, int yearOfPublication);

	boolean bookExists(String isbn);

	void deleteAllBooks();
}
