package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.web.formdata.GetMyBooksFormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controller class for the
 */
@Controller
@RequestMapping("/getMyBooks")
public class GetMyBooksController {

	public GetMyBooksController() {}

	@RequestMapping(method = RequestMethod.GET)
	public void prepareView(ModelMap modelMap) {
		modelMap.put("getMyBooksFormData", new GetMyBooksFormData());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getMyBooks(
			@ModelAttribute("getMyBooksFormData") @Valid GetMyBooksFormData formData,
			BindingResult result) {
		if (result.hasErrors()) {
			return "getMyBooks";
		} else {
			return "redirect:myBooksList?email=" + formData.getEmailAddress();
		}
	}

}
