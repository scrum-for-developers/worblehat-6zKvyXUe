package de.codecentric.psd.worblehat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Navigation
 */
@Controller
public class NavigationController {

	public static final String HOME_NAVIGATION = "home";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return HOME_NAVIGATION;
	}

}
