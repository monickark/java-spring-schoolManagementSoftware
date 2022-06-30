package com.jaw.fee.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarController;
import com.jaw.fee.service.IFeesTermService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class TermFeeController {
	// Logging
		Logger logger = Logger.getLogger(AcademicCalendarController.class);
		
		@Autowired
		IFeesTermService feesTermService;

		// Academic Calendar List Get method

		@RequestMapping(value = "/termFeesList", method = RequestMethod.GET)
		public ModelAndView termFees(
				/*@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,*/
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Term Fees  List page");
			// Null the session display tag values
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.TERM_FEES_LIST/*, "acadcal",
					academicCalendarMasterVO*/);
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			mav.getModelMap().addAttribute("status", "true");
			return mav;

		}
		
		// Ajax For  Ac Term Status Validation
		@RequestMapping(value = "/termFeesList", method = RequestMethod.GET, params = { "allocate" })
		public @ResponseBody
		void saveFeeAndPaymentTerm(@RequestParam(value="feeTermArray[]",required=false) String[] feesTrmArray,
				
				HttpSession session, HttpServletRequest request,
				HttpServletResponse response)  {
			logger.debug("inside Fees Term Save method");
			System.out.println("fee payment term  : "+(String) request.getParameter("feePayTrm"));
			System.out.println("fee  term  : "+feesTrmArray.length);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			
			
			
					try {
						feesTermService.inserttermFeesBatchRec(feesTrmArray, (String) request.getParameter("feePayTrm"),
								sessionCache.getUserSessionDetails());
					}  catch (DuplicateEntryException e) {
						response.setStatus(400);
					} catch (DatabaseException e) {
						response.setStatus(400);
					} catch (NoDataFoundException e) {
						response.setStatus(400);
					} catch (UpdateFailedException e) {
						response.setStatus(400);
					}
				
		
		}
		
		// Ajax For  Ac Term Status Validation
		@RequestMapping(value = "/termFeesList", method = RequestMethod.GET, params = { "deallocate" })
		public @ResponseBody
		void deleteFeeAndPaymentTerm(@RequestParam(value="feeTermArrayDelete[]",required=false) String[] feesTrmArray,
				
				HttpSession session, HttpServletRequest request,
				HttpServletResponse response)  {
			logger.debug("inside Fees Term Save method");
			System.out.println("fee payment term  : "+(String) request.getParameter("feePayTrmDelete"));
			System.out.println("fee  term  : "+feesTrmArray.length);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			try {
				feesTermService.deleteFeeTermRec(feesTrmArray, (String) request.getParameter("feePayTrmDelete"),
						sessionCache.getUserSessionDetails(), applicationCache);
			} catch (DeleteFailedException e1) {
				response.setStatus(400);
			} catch (DuplicateEntryException e1) {
				response.setStatus(400);
			} catch (DatabaseException e1) {
				response.setStatus(400);
			} 
			
		
		}
		
		// Ajax For  Ac Term Status Validation
				@RequestMapping(value = "/termFeesList", method = RequestMethod.GET, params = { "feeTrmList" })
				public @ResponseBody
				List<FeeTermVO> getFeesTermList(					
						HttpSession session, HttpServletRequest request,
						HttpServletResponse response)  {
					logger.debug("inside Fees Term List method");
					
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					List<FeeTermVO> feeTrmVos=null;
					try {
						feeTrmVos = feesTermService.getFeeTermsList(applicationCache,sessionCache);
					} catch (NoDataFoundException e) {
						response.setStatus(400);
					}

				return feeTrmVos;
				}
		@ModelAttribute("feeTermList")
		public Map<String, String> gerFeeTermList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			Map<String, String> map = null;

			try {
				map = feesTermService.getFeeTermList(sessionCache
						.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Error occured in fee term :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("feeTermList", map);
			return map;

		}
		@ModelAttribute("payFeeTermList")
		public Map<String, String> gerPaymentFeeTrmList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model)  {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			Map<String, String> map = null;

			try {
				map = feesTermService.getFeePaymentTermList(sessionCache
						.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Error occured in payment fee Term Dropdown :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("payFeeTermList", map);
			return map;

		}

		@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
				UpdateFailedException.class, DeleteFailedException.class,
				TableNotSpecifiedForAuditException.class })
		public ModelAndView handleException(Exception ex, HttpSession session,
				HttpServletRequest request) {
			logger.error("Exception Handledddd :" + ex.getMessage());	
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
