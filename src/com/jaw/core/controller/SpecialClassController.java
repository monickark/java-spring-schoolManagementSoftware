package com.jaw.core.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SpecialClsDeleteFailedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.service.ISpecialClassService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Special Class Controller Class
@Controller
public class SpecialClassController {
	// Logging
	Logger logger = Logger.getLogger(SpecialClassController.class);
	@Autowired
	ISpecialClassService splClassService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET)
	public ModelAndView specialClasss(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Special Class List page");
		
		
		logger.info("Rendering Student Group List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus",
				null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;
		
		

	}
	
	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET, params = { "Get" })
	public String viewSpecialClassList(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,HttpSession session,
			HttpServletRequest httpRequest,RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
			
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");

		// Setting model and view for exception handler
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					"status");
		}
		//System.out.println("sssssssssssssssssssss"+specialClassMasterVO.getSpecialClassSearchVO().toString());
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					specialClassMasterVO.getSpecialClassSearchVO());
			// Get list from database
			splClassService.selectSpecialClassList(specialClassMasterVO,sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					specialClassMasterVO.getSplClsVOList());
			
			specialClassMasterVO.setPageSize(specialClassMasterVO.getPageSize());

		}
		redirectAttributes.addFlashAttribute("splClsM",
				specialClassMasterVO);
		return "redirect:/specialClassList.htm?data";

	}

	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET, params = "data")
	public ModelAndView specialClassListBack(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			HttpServletRequest httpServletRequest) {
	
		SpecialClassSearchVO specialClassSearchVo= (SpecialClassSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		specialClassMasterVO.setSpecialClassSearchVO(specialClassSearchVo);		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		
		return modelAndView;
		
		
	}
	
	// Save Method
	@RequestMapping(value = "/specialClassList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveSpecialClass(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			DuplicateEntryForHolGenException {

		logger.debug("inside save Special Class method");
		
		if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){			
		SpecialClassSearchVO specialClsSearchVo=
				(SpecialClassSearchVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");
		specialClassMasterVO.setSpecialClassSearchVO(specialClsSearchVo);
		
		}/*else{
			specialClassMasterVO.getSpecialClassSearchVO().setAcTerm(specialClassMasterVO.getSplClsVO().getAcTerm());
		}*/
		redirectAttribute.addFlashAttribute("splClsM", specialClassMasterVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		System.out.println("ssssssssssss"+specialClassMasterVO.getSplClsVO().toString());
		
		if (specialClassMasterVO.getSplClsVO().getStudentGrpId().contains(",")) {
			String parts[] = specialClassMasterVO.getSplClsVO().getStudentGrpId()
					.split(",");
			specialClassMasterVO.getSplClsVO().setStudentGrpId(parts[0]); 
		}
		if ((specialClassMasterVO.getSplClsVO().getCrslId()!=null)&&(specialClassMasterVO.getSplClsVO().getCrslId().contains(","))) {
			String parts[] = specialClassMasterVO.getSplClsVO().getCrslId()
					.split(",");
			specialClassMasterVO.getSplClsVO().setCrslId(parts[0]); 
		}
		httpServletRequest.setAttribute("errors", "erroradd");
		
		splClassService.insertSpecialClassRec(
				specialClassMasterVO.getSplClsVO(),
				sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		logger.debug("Data's inserted successfully!");
		return "redirect:/specialClassList.htm?Get";
	}

	// Get Values from list for popup
	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET, params = { "actionGet" })
	public String getAcademicCalendar(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get Special Class details from list method");

		List<SpecialClassVO> specialClassVOVOs = (List<SpecialClassVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		specialClassMasterVO.setSplClsVO(specialClassVOVOs.get(Integer
				.parseInt(id)));		
		
		
		String keepstat = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "keepStatus");
		System.out.println("Keep status  :" + keepstat);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST);
		if (keepstat != null) {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		redirectAttribute.addFlashAttribute("selectedSub",specialClassMasterVO.getSplClsVO().getCrslId());
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("splClsM", specialClassMasterVO);
		return "redirect:/specialClassList.htm?data";	
	}

	// Update method
	@RequestMapping(value = "/specialClassList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateAcademicCalendar(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside update Special Class method");

		if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){			
		SpecialClassSearchVO specialClsSearchVo=
				(SpecialClassSearchVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");
		specialClassMasterVO.setSpecialClassSearchVO(specialClsSearchVo);
		
		}
		httpServletRequest.setAttribute("errors", "error");
		redirectAttribute.addFlashAttribute("splClsM", specialClassMasterVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		splClassService.updateSpecialClassRec(
				specialClassMasterVO.getSplClsVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
	
		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/specialClassList.htm?Get";
	}

	// Delete Method
	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteStudentGroup(
			@ModelAttribute("splClsM") SpecialClassMasterVO specialClassMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, SpecialClsDeleteFailedException {
		logger.debug("inside Deleted Special Class method");

		if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){			
		SpecialClassSearchVO specialClsSearchVo=
				(SpecialClassSearchVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");
		specialClassMasterVO.setSpecialClassSearchVO(specialClsSearchVo);
		
		}
		httpServletRequest.setAttribute("errors", "error");
		redirectAttribute.addFlashAttribute("splClsM", specialClassMasterVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SPECIAL_CLASS_LIST, "splClsM",
				specialClassMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		List<SpecialClassVO> specialClassVOVOs = (List<SpecialClassVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		specialClassMasterVO.setSplClsVO(specialClassVOVOs.get(Integer
				.parseInt(id)));
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		splClassService.deleteSpecialClassRec(
				specialClassMasterVO.getSplClsVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/specialClassList.htm?Get";
	}

	// Ajax For getting Sub Id,Opt Sub Id
	@RequestMapping(value = "/specialClassList", method = RequestMethod.GET, params = { "stdGrpID" })
	public @ResponseBody
	List<String> retriveList(HttpSession session, HttpServletRequest request) {
		String res = "result";

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		System.out.println("student grpo idddd"
				+ request.getParameter("studentGrpId"));
		List<String> lts = null;
		
			lts = splClassService.selectSubjectListForSplCls(request
					.getParameter("studentGrpId"),sessionCache.getUserSessionDetails());
		System.out.println("list size in controller"+lts.size());
		
		return lts;
	}

	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService
					.getAcademicTermListTag(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}
	@ModelAttribute("presentAcTermList")
	public Map<String, String> gerCurrentAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService
					.getPresentAcademicTermTag(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("presentAcTermList", map);
		return map;

	}

	
	
	
	
	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,			
			UpdateFailedException.class, DeleteFailedException.class , TableNotSpecifiedForAuditException.class,SpecialClsDeleteFailedException.class})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		mav.getModelMap().addAttribute(error, ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return mav;

	}

	@ExceptionHandler({ 
		NoDataFoundException.class  })
public ModelAndView handleNoDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {
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
