package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.web.formdata.GetMyBooksFormData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class GetMyBooksControllerTest {

    private GetMyBooksController getMyBooksController;
    private GetMyBooksFormData formData;
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        this.getMyBooksController = new GetMyBooksController();
        this.formData = new GetMyBooksFormData();
        this.bindingResult = new MapBindingResult(new HashMap<>(), "");
    }

    @Test
    public void shouldPrepareView() {
        ModelMap modelMap = new ModelMap();

        getMyBooksController.prepareView(modelMap);

        assertThat(modelMap.get("getMyBooksFormData"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectError() {
        bindingResult.addError(new ObjectError("", ""));
        String navigateTo = getMyBooksController.getMyBooks(formData, bindingResult);
        assertThat(navigateTo, is("getMyBooks"));
    }

    @Test
    public void shouldRedirectToMyBooks() {
        formData.setEmailAddress("me@me.me");
        String navigateTo = getMyBooksController.getMyBooks(formData, bindingResult);
        assertThat(navigateTo, is("redirect:myBooksList?email=me@me.me"));
    }
}