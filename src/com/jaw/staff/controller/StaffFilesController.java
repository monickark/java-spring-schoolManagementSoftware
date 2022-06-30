package com.jaw.staff.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.staff.service.IStaffFilesService;

//Controller class for Admission
@Controller
public class StaffFilesController {

	Logger logger = Logger.getLogger(StaffFilesController.class);

	@Autowired
	IStaffFilesService staffFilesService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	DateUtil dateUtil;

	@RequestMapping(value = "/staffFiles", method = RequestMethod.GET)
	public ModelAndView staffDetails(
			@ModelAttribute("files") FileTypeVO fileTypeVO,
			HttpServletRequest httpServletRequest, HttpSession session)
			throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		staffFilesService.getMenuOption(fileTypeVO,
				sessionCache.getUserSessionDetails(), applicationCache,
				"staffFiles.htm");
		ModelAndView modelAndView = null;

		return modelAndView;
	}

	@RequestMapping(value = "/staffFiles", method = RequestMethod.GET, params = "Get")
	public ModelAndView staffFiles(
			@ModelAttribute("files") FileTypeVO fileTypeVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		httpServletRequest.setAttribute("fileTypeVO", fileTypeVO);
		staffFilesService.getFileType(fileTypeVO,
				sessionCache.getUserSessionDetails(), fileTypeVO.getStaffId(),
				applicationCache, "staffFiles.htm");
		ModelAndView modelAndView = null;
		if ((!sessionCache.getUserSessionDetails().getUserMenuProfile()
				.equals(ApplicationConstant.BRANCH_ADMIN))
				&& (sessionCache.getUserSessionDetails().getProfileGroup()
						.equals(ApplicationConstant.PG_STAFF))) {
			System.out.println("Inside staff");
			modelAndView = new ModelAndView(".jaw.staffFilesStaffView",
					"files", fileTypeVO);

		} else {
			System.out.println("Inside branch admin");

			modelAndView = new ModelAndView(".jaw.staffFilesView", "files",
					fileTypeVO);

		}
		if (fileTypeVO != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileTypeVO);
		}
		if (httpServletRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/staffFiles", method = RequestMethod.POST, params = "Upload")
	public String uploadFiles(@ModelAttribute("files") FileTypeVO fileTypeVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			NumberFormatException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			PropertyNotFoundException, IllegalStateException, FileNotSaveException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String staffId = httpServletRequest.getParameter("staffId");
		System.out.println("staff id :" + staffId);
		fileTypeVO.setStaffId(staffId);
		httpServletRequest.setAttribute("fileTypeVO", fileTypeVO);
		staffFilesService.uploadStaff(fileTypeVO,
				sessionCache.getUserSessionDetails(), applicationCache,session.getServletContext());

		ModelAndView modelAndView = new ModelAndView(".jaw.staffFilesView",
				"files", fileTypeVO);
		if (fileTypeVO != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileTypeVO);
		}
		redirectAttributes.addFlashAttribute("files", fileTypeVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/staffFiles.htm?Get";
	}

	@ExceptionHandler({ NoDataFoundException.class,
			FileNotFoundInDatabase.class, InvalidUserIdException.class,
			UpdateFailedException.class, DuplicateEntryException.class,
			DatabaseException.class, TableNotSpecifiedForAuditException.class,FileNotSaveException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		FileTypeVO fileTypeVO = (FileTypeVO) request.getAttribute("fileTypeVO");
		ModelAndView modelAndView = new ModelAndView(".jaw.staffFilesView",
				"files", fileTypeVO);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}

}
