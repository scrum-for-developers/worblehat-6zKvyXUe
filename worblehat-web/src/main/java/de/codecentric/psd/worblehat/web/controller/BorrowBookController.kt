package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.domain.BookService
import de.codecentric.psd.worblehat.web.formdata.BookBorrowFormData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

/**
 * Controller for BorrowingBook
 */
@RequestMapping("/borrow")
@Controller
open class BorrowBookController @Autowired constructor(private val bookService: BookService) {

    @RequestMapping(method = [RequestMethod.GET])
    fun setupForm(model: ModelMap) {
        model["borrowFormData"] = BookBorrowFormData()
    }

    @Transactional
    @RequestMapping(method = [RequestMethod.POST])
    open fun processSubmit(@ModelAttribute("borrowFormData") @Valid borrowFormData: BookBorrowFormData,
                      result: BindingResult): String {
        if (result.hasErrors()) {
            return TARGET_BORROW_PAGE
        }
        val books = bookService.findBooksByIsbn(borrowFormData.isbn)
        if (books.isEmpty()) {
            result.rejectValue("isbn", "noBookExists")
            return TARGET_BORROW_PAGE
        }
        val borrowing = bookService.borrowBook(borrowFormData.isbn, borrowFormData.email)

        return borrowing
                .map { "redirect:/bookList" }
                .orElseGet {
                    result.rejectValue("isbn", "noBorrowableBooks")
                    TARGET_BORROW_PAGE
                }
    }

    @ExceptionHandler(Exception::class)
    fun handleErrors(ex: Exception, request: HttpServletRequest): String {
        return ERROR_HANDLING_REDIRECT
    }

    companion object {
        const val TARGET_BORROW_PAGE = "borrow"
        const val ERROR_HANDLING_REDIRECT = "redirect:/borrow"
    }
}
