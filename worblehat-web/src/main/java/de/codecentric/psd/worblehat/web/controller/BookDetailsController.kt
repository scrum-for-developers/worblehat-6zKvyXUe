package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
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
@RequestMapping("/bookDetails")
open class BookDetailsController @Autowired constructor(private val bookService: BookService) {

    @RequestMapping(method = [RequestMethod.GET])
    fun setupForm(modelMap: ModelMap, @RequestParam id: Long): String {
        val book = bookService.findBookById(id)
        modelMap.addAttribute("book", book)
        return "bookDetails"
    }

}
