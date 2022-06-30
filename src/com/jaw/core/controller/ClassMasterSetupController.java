package com.jaw.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * CommonCodeController is used for commoncode table actions and its binding
 * with cocd.jsp
 */

@Controller
public class ClassMasterSetupController {

	// To show the class master setup page

	@RequestMapping(value = "/classmastersetup", method = RequestMethod.GET)
	public ModelAndView cocdGet() {
		return new ModelAndView(".jaw.classMasterSetup");
	}

}