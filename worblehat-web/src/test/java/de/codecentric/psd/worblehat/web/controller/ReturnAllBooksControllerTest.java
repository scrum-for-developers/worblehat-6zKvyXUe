package de.codecentric.psd.worblehat.web.controller;

import java.util.HashMap;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ReturnAllBooksControllerTest {

    private ReturnAllBooksController returnAllBooksController;

    private BookService bookService;

    private ReturnAllBooksFormData returnAllBooksFormData;

    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        returnAllBooksController = new ReturnAllBooksController(bookService);
        returnAllBooksFormData = new ReturnAllBooksFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = new ModelMap();

        returnAllBooksController.prepareView(modelMap);

        assertThat(modelMap.get("returnAllBookFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() throws Exception {
        bindingResult.addError(new ObjectError("", ""));

        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

        assertThat(navigateTo, is("returnAllBooks"));
    }

    @Test
    public void shouldReturnAllBooksAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        returnAllBooksFormData.setEmailAddress(borrower);

        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

        verify(bookService).returnAllBooksByBorrower(borrower);
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldReturnBookByIsbnAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        String isbn = "123456789X";
        returnAllBooksFormData.setEmailAddress(borrower);
        returnAllBooksFormData.setIsbn(isbn);
        returnAllBooksFormData.setRadioButtonSelection("ISBN");

        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndIsbn(borrower, isbn);
        assertThat(navigateTo, is("home"));
    }

    @Test
    public void shouldReturnBookByTitleAndNavigateHome() throws Exception {
        String borrower = "someone@codecentric.de";
        String title = "A book";
        returnAllBooksFormData.setEmailAddress(borrower);
        returnAllBooksFormData.setTitle(title);
        returnAllBooksFormData.setRadioButtonSelection("Title");

        String navigateTo = returnAllBooksController.returnAllBooks(returnAllBooksFormData, bindingResult);

        verify(bookService).returnBookByBorrowerAndTitle(borrower, title);
        assertThat(navigateTo, is("home"));
    }
}
