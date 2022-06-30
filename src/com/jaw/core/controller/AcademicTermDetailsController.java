package com.jaw.core.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.exceptions.AcadTermUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.ExistPresentTermException;
import com.jaw.common.exceptions.ExistPreviousTermException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.service.IAcademicTermDetailsService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Class
@Controller
@SuppressWarnings("unchecked")
public class AcademicTermDetailsController {
	// Logging
	Logger logger = Logger.getLogger(AcademicTermDetailsController.class);
	@Autowired
	IAcademicTermDetailsService academicTermDetailsService;
	
	@RequestMapping(value = "/academicTermDetailsList", method = RequestMethod.GET)
	public ModelAndView viewAcademicTermDetailsList(
			@ModelAttribute("acadtrmdetail") AcademicTermMasterVO academicTermMasterVO,
			HttpServletRequest httpRequest, HttpSession session) throws NoDataFoundException {
		
		// Getting the display tag parameter
		
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
		
		ModelAndView modelAndView = new ModelAndView(ModelAndViewConstant.ACAD_TRM_DET_LIST,
				"acadtrmdetail", academicTermMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
		
		if (stockParamMap.size() == 0) {
			
			logger.info("Table operation has been triggered");
			
			// Get list from database
			
			try {
				academicTermDetailsService.selectAcademicTermList(academicTermMasterVO,
						sessionCache.getUserSessionDetails());
			}
			catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}
			
			// Set the list to session to access in jsp
			
			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					academicTermMasterVO.getAcademicTermDetailsVOs());
			academicTermMasterVO.setPageSize(academicTermMasterVO.getPageSize());
			
		}
		
		
		return modelAndView;
	}
	

	@RequestMapping(value = "/academicTermDetailsList", method = RequestMethod.POST)
	public String saveAcademicTermDetails(
			@ModelAttribute("acadtrmdetail") AcademicTermMasterVO academicTermMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, ExistPresentTermException, DatabaseException, NoDataFoundException, UpdateFailedException, ExistPreviousTermException
	{
		
		logger.debug("inside save AcademicTerm Details method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		if (academicTermMasterVO.getAcademicTermDetailsVO().getAcTerm().contains(",")) {
			String parts[] = academicTermMasterVO.getAcademicTermDetailsVO().getAcTerm()
					.split(",");
			academicTermMasterVO.getAcademicTermDetailsVO().setAcTerm(parts[0]); 
		}
		if (academicTermMasterVO.getAcademicTermDetailsVO().getAcYear().contains(",")) {
			String parts[] = academicTermMasterVO.getAcademicTermDetailsVO().getAcYear()
					.split(",");
			academicTermMasterVO.getAcademicTermDetailsVO().setAcYear(parts[0]); 
		}
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.ACAD_TRM_DET_LIST,
				"acadtrmdetail", academicTermMasterVO);
		httpServletRequest.setAttribute("page", mav);
		httpServletRequest.setAttribute("errors", "erroradd");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		academicTermDetailsService.insertAcademicTermDetailsRec(
				academicTermMasterVO.getAcademicTermDetailsVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		academicTermMasterVO.setAcademicTermDetailsVO(null);
		redirectAttribute.addFlashAttribute("acadtrmdetail",
				academicTermMasterVO);
	
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS_ATDTA);
		logger.debug("Data's inserted successfully!");
		return "redirect:/academicTermDetailsList.htm";
	}
	
	@RequestMapping(value = "/academicTermDetailsList", method = RequestMethod.GET, params = {
			"actionGet"
	})
	public String getAcademicTermDetails(
			@ModelAttribute("acadtrmdetail") AcademicTermMasterVO academicTermMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
	{
		
		logger.debug("inside Update AcademicTerm Details method");
		
		List<AcademicTermDetailsVO> academicTermListVOs = (List<AcademicTermDetailsVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		
		String id = httpServletRequest.getParameter("rowId");
		academicTermMasterVO
				.setAcademicTermDetailsVO(academicTermListVOs.get(Integer.parseInt(id)));
		
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("acadtrmdetail", academicTermMasterVO);
		return "redirect:/academicTermDetailsList.htm";
	}
	
	@RequestMapping(value = "/academicTermDetailsList", method = RequestMethod.POST, params = {
			"actionUpdate"
	})
	public String updateAcademicTerDetails(
			@ModelAttribute("acadtrmdetail") AcademicTermMasterVO acdtrmdVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) throws UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadTermUpdateFailedException, ExistPresentTermException, ExistPreviousTermException
	{
		
		logger.debug("inside Update AcademicTerm Details method");
		httpServletRequest.setAttribute("updateAction", "update");	
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("errors", "erroradd");	
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.ACAD_TRM_DET_LIST,
				"acadtrmdetail", acdtrmdVO);
		httpServletRequest.setAttribute("page", mav);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		System.out.println("acterm vo "+acdtrmdVO.getAcademicTermDetailsVO().toString());
		academicTermDetailsService.updateAcademicTermDetailsRec(
				acdtrmdVO.getAcademicTermDetailsVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		System.out.println("after exception");
		return "redirect:/academicTermDetailsList.htm";
	}
	
	@RequestMapping(value = "/academicTermDetailsList", method = RequestMethod.GET, params = {
			"actionDelete"
	})
	public String deleteAcademicTerDetails(
			@ModelAttribute("acadtrmdetail") AcademicTermMasterVO acdtrmdVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException
	{
		
		logger.debug("inside Delete AcademicTerm Details method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<AcademicTermDetailsVO> academicTermListVOs = (List<AcademicTermDetailsVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		
		String id = httpServletRequest.getParameter("rowId");
		acdtrmdVO.setAcademicTermDetailsVO(academicTermListVOs.get(Integer.parseInt(id)));
		httpServletRequest.setAttribute("errors", "erroradd");	
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.ACAD_TRM_DET_LIST,
				"acadtrmdetail", acdtrmdVO);
		httpServletRequest.setAttribute("page", mav);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		academicTermDetailsService.deleteAcademicTermDetailsRec(
				acdtrmdVO.getAcademicTermDetailsVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		
		return "redirect:/academicTermDetailsList.htm";
	}
	
	

	
	@ExceptionHandler({
			DuplicateEntryException.class, ExistPresentTermException.class,
			DeleteFailedException.class, UpdateFailedException.class,  
			DatabaseException.class, TableNotSpecifiedForAuditException.class,AcadTermUpdateFailedException.class,ExistPreviousTermException.class
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
	
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		modelAndView.getModelMap().addAttribute(error, ex.getMessage());
		if(request.getAttribute("updateAction")!=null){
		String action=(String)request.getAttribute("updateAction");
		System.out.println("actions in exception handler "+action);
		if(action.equals("update")){
			modelAndView.getModelMap().addAttribute("popup", "update");
		}
		}
		return modelAndView;
		
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
