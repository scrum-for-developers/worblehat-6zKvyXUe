package de.codecentric.psd.worblehat.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * Controller for Navigation
 */
@Controller
open class NavigationController {

    @RequestMapping(value = ["/"], method = [RequestMethod.GET])
    fun home(): String {
        return HOME_NAVIGATION
    }

    companion object {
        const val HOME_NAVIGATION = "home"
    }

}
