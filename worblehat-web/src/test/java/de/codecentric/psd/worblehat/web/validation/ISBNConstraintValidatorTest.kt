package de.codecentric.psd.worblehat.web.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import javax.validation.ConstraintValidatorContext

class ISBNConstraintValidatorTest {

    private var isbnConstraintValidator: ISBNConstraintValidator? = null

    private var constraintValidatorContext: ConstraintValidatorContext? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        isbnConstraintValidator = ISBNConstraintValidator()
        constraintValidatorContext = mock(ConstraintValidatorContext::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun initializeShouldTakeIsbn() {
        val isbn = mock(ISBN::class.java)
        isbnConstraintValidator!!.initialize(isbn)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTrueIfBlank() {
        val actual = isbnConstraintValidator!!.isValid("", constraintValidatorContext!!)
        assertTrue(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTrueIfValidISBN10() {
        val actual = isbnConstraintValidator!!.isValid("0132350882", constraintValidatorContext!!)
        assertTrue(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTrueIfValidISBN13() {
        val actual = isbnConstraintValidator!!.isValid("978-3-16-148410-0", constraintValidatorContext!!)
        assertTrue(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFalseIfISBNBetween10And13() {
        val actual = isbnConstraintValidator!!.isValid("978-3-16-148410", constraintValidatorContext!!)
        assertFalse(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFalseIfInvalidISBN() {
        val actual = isbnConstraintValidator!!.isValid("0123459789", constraintValidatorContext!!)
        assertFalse(actual)
    }

}