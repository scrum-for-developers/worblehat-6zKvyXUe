package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.PathVariable
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

        if (book.isPresent) {
            modelMap.addAttribute("book", book.get())
            return "bookDetails"
        }

        return "redirect:/bookList"
    }

    @RequestMapping("/delete/{id}", method = [RequestMethod.GET])
    fun deleteBook(@PathVariable("id") id: Long, modelMap: ModelMap): String {
        bookService.deleteBookById(id)
        val books = bookService.findAllBooks()
        modelMap.addAttribute("books", books)
        return "redirect:/bookList"
    }

}
