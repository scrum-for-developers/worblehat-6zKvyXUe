package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import javax.validation.Valid

/**
 * Controller class for the
 */
@Controller
@RequestMapping("/returnAllBooks")
open class ReturnAllBooksController @Autowired constructor(private val bookService: BookService) {

    @RequestMapping(method = [RequestMethod.GET])
    fun prepareView(modelMap: ModelMap) {
        modelMap["returnAllBookFormData"] = ReturnAllBooksFormData()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun returnAllBooks(
            @ModelAttribute("returnAllBookFormData") @Valid formData: ReturnAllBooksFormData,
            result: BindingResult): String {
        return if (result.hasErrors()) {
            "returnAllBooks"
        } else {
            if (formData.isbn.isEmpty() && formData.title.isEmpty()) {
                bookService.returnAllBooksByBorrower(formData.emailAddress)
            } else if ("ISBN" == formData.radioButtonSelection) {
                bookService.returnBookByBorrowerAndIsbn(formData.emailAddress, formData.isbn)
            } else if ("Title" == formData.radioButtonSelection) {
                bookService.returnBookByBorrowerAndTitle(formData.emailAddress, formData.title)
            }
            "redirect:/bookList"
        }
    }

}
