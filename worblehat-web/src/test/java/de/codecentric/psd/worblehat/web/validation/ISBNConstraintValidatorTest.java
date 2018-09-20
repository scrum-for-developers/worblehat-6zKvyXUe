package de.codecentric.psd.worblehat.web.validation;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ISBNConstraintValidatorTest {

    private ISBNConstraintValidator isbnConstraintValidator;

    private ConstraintValidatorContext constraintValidatorContext;


    @Before
    public void setUp() throws Exception {
        isbnConstraintValidator = new ISBNConstraintValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void initializeShouldTakeIsbn() throws Exception {
        ISBN isbn= mock(ISBN.class);
        isbnConstraintValidator.initialize(isbn);
    }

    @Test
    public void shouldReturnTrueIfBlank() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnTrueIfValidISBN10() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("0132350882", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnTrueIfValidISBN13() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("978-3-16-148410-0", constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalseIfISBNBetween10And13() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("978-3-16-148410", constraintValidatorContext);
        assertFalse(actual);
    }

    @Test
    public void shouldReturnFalseIfInvalidISBN() throws Exception {
        boolean actual = isbnConstraintValidator.isValid("0123459789", constraintValidatorContext);
        assertFalse(actual);
    }

}