package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/myBooksList")
public class MyBookListController {

	private static final Logger LOG = LoggerFactory
			.getLogger(InsertBookController.class);

	private BookService bookService;

	@Autowired
	public MyBookListController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(ModelMap modelMap, @RequestParam(value = "email") String email) {
		modelMap.addAttribute("borrowings", bookService.findBooksByEmail(email));

		LOG.info("email: " + email);
		return "myBooksList";
	}

}
