package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/bookList")
open class BookListController @Autowired constructor(private val bookService: BookService) {

    @RequestMapping(method = [RequestMethod.GET])
    fun setupForm(modelMap: ModelMap): String {
        val books = bookService.findAllBooks()
        modelMap.addAttribute("books", books)
        return "bookList"
    }

}
