package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/myBooksList")
open class MyBookListController @Autowired constructor(private val bookService: BookService) {

    private val logger = LoggerFactory.getLogger(MyBookListController::class.java)

    @RequestMapping(method = [RequestMethod.GET])
    fun setupForm(modelMap: ModelMap, @RequestParam(value = "email") email: String): String {
        modelMap.addAttribute("borrowings", bookService.findBooksByEmail(email))

        logger.info("email: $email")
        return "myBooksList"
    }

}
