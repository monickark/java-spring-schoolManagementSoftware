package com.jaw.staff.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.staff.service.IStaffInformationService;

//Controller class for Admission
@Controller
public class StaffInformationController {

	Logger logger = Logger.getLogger(StaffInformationController.class);

	@Autowired
	private IStaffInformationService staffInformationService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	DateUtil dateUtil;

	@RequestMapping(value = "/staffList", method = RequestMethod.GET)
	public ModelAndView insertMemDtls(
			@ModelAttribute("staff") StaffListSearchVo staffListSearchVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(".jaw.staffList");
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				null);
		return modelAndView;
	}

	@RequestMapping(value = "/staffList", method = RequestMethod.GET, params = "Get")
	public String insertContact(
			@ModelAttribute("staff") StaffListSearchVo staffListSearchVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		ModelAndView modelAndView = new ModelAndView(".jaw.staffList", "staff",
				staffListSearchVo);
		httpServletRequest.setAttribute("reqObjStaff", modelAndView);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<StaffListVo> requestList;
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		if (stockParamMap.size() == 0) {
			requestList = staffInformationService.selectStaff(
					staffListSearchVo, sessionCache.getUserSessionDetails());
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					requestList);
			WebUtils.setSessionAttribute(httpServletRequest, "SearchVo",
					staffListSearchVo);
		}
		redirectAttributes.addFlashAttribute("staff", staffListSearchVo);
		return "redirect:/staffList.htm?data";

	}

	@RequestMapping(value = "/staffList", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("staff") StaffListSearchVo staffListSearchVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(".jaw.staffList");
		return modelAndView;
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		ModelAndView page = (ModelAndView) request.getAttribute("reqObjStaff");
		page.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return page;

	}
}
