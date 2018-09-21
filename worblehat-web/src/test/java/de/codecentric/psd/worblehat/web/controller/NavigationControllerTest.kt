package de.codecentric.psd.worblehat.web.controller

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class NavigationControllerTest {

    @Test
    @Throws(Exception::class)
    fun shouldNavigateToHome() {
        val navigateTo = NavigationController().home()

        assertThat(navigateTo, `is`("home"))
    }
}