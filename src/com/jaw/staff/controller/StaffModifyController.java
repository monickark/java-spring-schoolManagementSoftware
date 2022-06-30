package com.jaw.staff.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
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
public class StaffModifyController {

	Logger logger = Logger.getLogger(StaffModifyController.class);

	@Autowired
	private IStaffInformationService staffInformationService;
	@Autowired
	private IStaffInsertionService staffInsertionService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	private IDropDownListService dropDownListService;
	@Autowired
	DateUtil dateUtil;

	@RequestMapping(value = "/viewStaff", method = RequestMethod.GET)
	public ModelAndView staffDetails(
			@ModelAttribute("StaffDetails") StaffListVo listVo,
			HttpServletRequest httpServletRequest, HttpSession session)
			throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		List<StaffListVo> staffListVos = (List<StaffListVo>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String rowId = httpServletRequest.getParameter("rowId");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String staffId = staffListVos.get(Integer.parseInt(rowId)).getStaffId();
		StaffVo admissionVo = new StaffVo();
		staffInformationService.selectStaff(admissionVo, staffId,
				sessionCache.getUserSessionDetails());
		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsView", "staff", admissionVo);
		WebUtils.setSessionAttribute(httpServletRequest, "staffResetValue",
				admissionVo);
		return modelAndView;
	}

	@RequestMapping(value = "/viewStaff", method = RequestMethod.GET, params = "files")
	public ModelAndView staffFiles(
			@ModelAttribute("StaffDetails") StaffListVo listVo,
			HttpServletRequest httpServletRequest, HttpSession session)
			throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		List<StaffListVo> staffListVos = (List<StaffListVo>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String rowId = httpServletRequest.getParameter("rowId");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String staffId = staffListVos.get(Integer.parseInt(rowId)).getStaffId();
		StaffVo admissionVo = new StaffVo();
		FileTypeVO fileType = staffInformationService.getFileType(
				sessionCache.getUserSessionDetails(), staffId);
		admissionVo.getStaffMasterVo().setStaffId(staffId);
		ModelAndView modelAndView = new ModelAndView(".jaw.staffFilesView",
				"staff", admissionVo);
		if (fileType != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileType);
		}
		WebUtils.setSessionAttribute(httpServletRequest, "staffResetValue",
				admissionVo);
		return modelAndView;
	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.GET)
	public ModelAndView viewStaffProfileGet(
			@ModelAttribute("StaffDetails") StaffListVo listVo,
			HttpServletRequest httpServletRequest, HttpSession session)
			throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		List<StaffListVo> staffListVos = (List<StaffListVo>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String rowId = httpServletRequest.getParameter("rowId");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String staffId = staffListVos.get(Integer.parseInt(rowId)).getStaffId();
		StaffVo admissionVo = new StaffVo();
		staffInformationService.selectStaff(admissionVo, staffId,
				sessionCache.getUserSessionDetails());
		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsModify", "staff", admissionVo);
		WebUtils.setSessionAttribute(httpServletRequest, "staffResetValue",
				admissionVo);
		return modelAndView;
	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.POST, params = "UpdateStaff")
	public String updateStaff(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsModify", "staff", staffAdmissionVo);
		httpServletRequest.setAttribute("page", modelAndView);
		staffInformationService.updateStaffFullDetails(staffAdmissionVo,
				sessionCache.getUserSessionDetails(), applicationCache,session.getServletContext());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		StaffListSearchVo staffListSearchVo = (StaffListSearchVo) WebUtils
				.getSessionAttribute(httpServletRequest, "SearchVo");
		redirectAttributes.addFlashAttribute("staff", staffListSearchVo);
		return "redirect:staffList.htm?Get=Get";

	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.POST, params = "ResetStaff")
	public ModelAndView resetStaff(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException {

		staffAdmissionVo = (StaffVo) WebUtils.getSessionAttribute(
				httpServletRequest, "staffResetValue");
		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsModify", "staff", staffAdmissionVo);
		return modelAndView;

	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.POST, params = "DeleteStaff")
	public String deleteStaff(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, BatchUpdateFailedException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsModify", "staff", staffAdmissionVo);
		httpServletRequest.setAttribute("page", modelAndView);
		staffInformationService.deleteStaff(staffAdmissionVo,
				sessionCache.getUserSessionDetails(), applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.STAFF_TRANSFERED);
		StaffListSearchVo staffListSearchVo = (StaffListSearchVo) WebUtils
				.getSessionAttribute(httpServletRequest, "SearchVo");
		redirectAttributes.addFlashAttribute("staff", staffListSearchVo);
		return "redirect:staffList.htm?Get=Get";

	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.POST, params = "UpdateStaffProfile")
	public String updateStaffProfile(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		ModelAndView modelAndView = new ModelAndView(
				".jaw.StaffListDetailsModify", "staff", staffAdmissionVo);
		httpServletRequest.setAttribute("page", modelAndView);
		staffInformationService.updateStaff(staffAdmissionVo,
				sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return "redirect:userManagement.htm?Search=Get";

	}

	@RequestMapping(value = "/modifyStaff", method = RequestMethod.POST, params = "BackStaff")
	public String BackStaff(@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			FileNotFoundInDatabase, InvalidUserIdException,
			UpdateFailedException, DuplicateEntryException {
		StaffListSearchVo staffListSearchVo = (StaffListSearchVo) WebUtils
				.getSessionAttribute(httpServletRequest, "SearchVo");
		redirectAttributes.addFlashAttribute("staff", staffListSearchVo);
		return "redirect:staffList.htm?Get=Get";

	}

	@ModelAttribute("menuprofile")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.selectMenuProfileList(
					ApplicationConstant.PG_STAFF,
					sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {

		}
		httpSevletRequest.setAttribute("menuprofile", map);
		return map;

	}

	@ExceptionHandler({

	FileNotFoundInDatabase.class, InvalidUserIdException.class,
			UpdateFailedException.class, DuplicateEntryException.class,
			DatabaseException.class, TableNotSpecifiedForAuditException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {

		StaffVo staffAdmissionVo = (StaffVo) request.getAttribute("page");
		ModelAndView modelAndView = new ModelAndView(".jaw.staffProfileModify",
				"staff", staffAdmissionVo);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}

	@ExceptionHandler({ NoDataFoundException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleNoDataException(Exception ex,
			HttpSession session, HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String message = (String) WebUtils.getSessionAttribute(request,
				"success");
		if ((message != null)
				&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {
		} else {
			System.out.println("inside non  delete message");
			WebUtils.setSessionAttribute(request, "success", null);
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}

		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return mav;
	}
}
