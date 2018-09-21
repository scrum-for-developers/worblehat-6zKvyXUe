package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Component("BookDetails")
public class BookDetails {

    private SeleniumAdapter seleniumAdapter;
    private BookService bookService;

    @Autowired
    public BookDetails(ApplicationContext applicationContex,
                       SeleniumAdapter seleniumAdapter){
        this.seleniumAdapter = seleniumAdapter;
        this.bookService = applicationContex.getBean(BookService.class);
    }

    @Then("the librarian can see the details of the book with isbn $isbn")
    public void theLibarianCanSeeTheDetails(String isbn){
        Set<Book> books = bookService.findBooksByIsbn(isbn);
        for (Book book: books) {
            seleniumAdapter.gotoPage(Page.BOOKDETAILS, book.getId() + "");

            String title = seleniumAdapter.getTextFromElement(PageElement.DETAILS_TITLE);
            assertThat(title, is(book.getTitle()));
        }
    }


}
