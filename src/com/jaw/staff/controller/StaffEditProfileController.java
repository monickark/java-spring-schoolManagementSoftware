package com.jaw.staff.controller;

import java.io.IOException;

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
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.staff.service.IStaffInformationService;
import com.jaw.staff.service.IStaffInsertionService;

//Controller class for Admission
@Controller
public class StaffEditProfileController {

	Logger logger = Logger.getLogger(StaffEditProfileController.class);

	@Autowired
	private IStaffInformationService staffInformationService;
	@Autowired
	private IStaffInsertionService staffInsertionService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	DateUtil dateUtil;

	@RequestMapping(value = "/edituser", method = RequestMethod.POST, params = "UpdateStaffEP")
	public String modifyUser(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		httpServletRequest.setAttribute("staff", staffAdmissionVo);
		ModelAndView modelAndView = new ModelAndView(".jaw.StaffEditProfile", "staff",
				staffAdmissionVo);
		httpServletRequest.setAttribute("page", modelAndView);
		staffInformationService.updateStaffEditProfile(applicationCache,
				staffAdmissionVo, sessionCache.getUserSessionDetails(),session.getServletContext());
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return "redirect:edituser.htm";

	}

	@RequestMapping(value = "edituser", method = RequestMethod.POST, params = "ResetStaffEP")
	public ModelAndView resetStaffEp(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) {
		staffAdmissionVo = (StaffVo) WebUtils.getSessionAttribute(
				httpServletRequest, "staffResetValue");

		ModelAndView modelAndView = new ModelAndView(".jaw.StaffEditProfile", "staff",
				staffAdmissionVo);
		httpServletRequest.setAttribute("page", modelAndView);
		return modelAndView;

	}

	@ExceptionHandler({ NoDataFoundException.class,
			FileNotFoundInDatabase.class, InvalidUserIdException.class,
			UpdateFailedException.class, DuplicateEntryException.class,
			TableNotSpecifiedForAuditException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}

}
