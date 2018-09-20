package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

public class MyBookListControllerTest {

    private MyBookListController myBookListController;

    @Before
    public void setUp() {
        BookService bookService = mock(BookService.class);
        this.myBookListController = new MyBookListController(bookService);
    }

    @Test
    public void shouldSetupForm() {
        ModelMap modelMap = new ModelMap();

        String navigateTo = myBookListController.setupForm(modelMap, "me@me.de");

        assertThat(modelMap.get("borrowings"), is(not(nullValue())));
        assertThat(navigateTo, is("myBooksList"));
    }

}