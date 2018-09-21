package de.codecentric.psd.worblehat.web.validation

import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import javax.validation.ConstraintValidatorContext

class NumericConstraintValidatorTest {

    private var numericConstraintValidator = NumericConstraintValidator()
    private var constraintValidatorContext: ConstraintValidatorContext = mock()

    @Test
    @Throws(Exception::class)
    fun initializeShouldTakeNumeric() {
        val numeric = mock(Numeric::class.java)
        numericConstraintValidator.initialize(numeric)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTrueIfBlank() {
        val actual = numericConstraintValidator.isValid("", constraintValidatorContext)
        assertTrue(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnTrueIfNumeric() {
        val actual = numericConstraintValidator.isValid("1", constraintValidatorContext)
        assertTrue(actual)
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnFalseIfNotNumeric() {
        val actual = numericConstraintValidator.isValid("x", constraintValidatorContext)
        assertFalse(actual)
    }
}