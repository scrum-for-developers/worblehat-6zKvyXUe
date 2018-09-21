package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import javax.validation.Valid

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/insertBooks")
open class InsertBookController @Autowired constructor(private val bookService: BookService) {

    private val logger = LoggerFactory .getLogger(InsertBookController::class.java)

    @RequestMapping(method = [RequestMethod.GET])
    fun setupForm(modelMap: ModelMap) {
        modelMap["bookDataFormData"] = BookDataFormData()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun processSubmit(@ModelAttribute("bookDataFormData") @Valid bookDataFormData: BookDataFormData,
                      result: BindingResult): String {

        return if (result.hasErrors()) {
            "insertBooks"
        } else {
            val book = bookService.createBook(
                    bookDataFormData.title, bookDataFormData.author,
                    bookDataFormData.edition, bookDataFormData.isbn,
                    bookDataFormData.yearOfPublication.toInt(), bookDataFormData.description)
            if (book.isPresent) {
                logger.info("new book instance is created: ${book.get()}")
            } else {
                logger.debug("failed to create new book with: $bookDataFormData")
            }
            "redirect:bookList"
        }
    }

}
