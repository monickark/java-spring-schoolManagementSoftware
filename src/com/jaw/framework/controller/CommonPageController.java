package com.jaw.framework.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonPageController {
	Logger logger = Logger.getLogger(CommonPageController.class);

	// To show login page
	@RequestMapping(value = "/sessionTimeout", method = RequestMethod.GET)
	public String sessionTimeout() {
		return "common/session_timeout";
	}

	@RequestMapping(value = "/invalidUrl", method = RequestMethod.GET)
	public String invalidUrl() {
		return "common/invalid_url";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request) {
		logger.info("Ip address remote address:" + request.getRemoteAddr());
		logger.info("Ip address X-FORWARDED-FOR:"
				+ request.getHeader("X-FORWARDED-FOR"));
		ModelAndView modelAndView = new ModelAndView(".jaw.welcome");
		return modelAndView;
	}
}

