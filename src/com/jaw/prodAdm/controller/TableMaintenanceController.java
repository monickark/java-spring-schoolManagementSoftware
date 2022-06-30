package com.jaw.prodAdm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * CommonCodeController is used for commoncode table actions and its binding
 * with cocd.jsp
 */

@Controller
public class TableMaintenanceController {

	@RequestMapping(value = "/tablemaintenance", method = RequestMethod.GET)
	public ModelAndView cocdGet() {
		return new ModelAndView(".jaw.tablemaintenance");
	}

}