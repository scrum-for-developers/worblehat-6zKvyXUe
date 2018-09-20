package de.codecentric.psd.worblehat.web.controller

import de.codecentric.psd.worblehat.web.formdata.GetMyBooksFormData
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
@RequestMapping("/getMyBooks")
open class GetMyBooksController {

    @RequestMapping(method = [RequestMethod.GET])
    fun prepareView(modelMap: ModelMap) {
        modelMap["getMyBooksFormData"] = GetMyBooksFormData()
    }

    @RequestMapping(method = [RequestMethod.POST])
    fun getMyBooks(@ModelAttribute("getMyBooksFormData") @Valid formData: GetMyBooksFormData,
                   result: BindingResult): String {
        return if (result.hasErrors()) {
            "getMyBooks"
        } else {
            "redirect:myBooksList?email=" + formData.emailAddress
        }
    }

}
