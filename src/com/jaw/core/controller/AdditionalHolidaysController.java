package com.jaw.core.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.service.IAdditionalHolidaysService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
@Controller
public class AdditionalHolidaysController {
		
	// Logging
				Logger logger = Logger.getLogger(AdditionalHolidaysController.class);
				@Autowired
				IDropDownListService dropDownListService;
				@Autowired
				IAdditionalHolidaysService additionalHolidaysService;
			
				
				@RequestMapping( value= "/addlHolidaysList",method=RequestMethod.GET)
				public ModelAndView viewAddlHolidays( @ModelAttribute("addHolidayMas") AddlHolidaysMasterVO aHVO,HttpServletRequest httpRequest,
						HttpSession httpSession){
					System.out.println("page render in addl Holidays");
					ModelAndView mav = new ModelAndView(ModelAndViewConstant.ADDL_HOLIDAYS_LIST,"addHolidayMas",aHVO );
					WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
					WebUtils.setSessionAttribute(httpRequest, "success", null);
					WebUtils.setSessionAttribute(httpRequest, "keepStatus",
							null);
					WebUtils.setSessionAttribute(httpRequest, "searchVo",
							null);
					mav.getModelMap().addAttribute("status", "true");
					return mav;
				}
				@RequestMapping( value= "/addlHolidaysList",method=RequestMethod.GET, params = { "Get" })
				public String viewAddlHolidaysList( @ModelAttribute("addHolidayMas") AddlHolidaysMasterVO aHVO,HttpSession session,
						HttpServletRequest httpRequest,RedirectAttributes redirectAttributes) throws NoDataFoundException{
					System.out.println("get methoddddddddddddddd");
					WebUtils.setSessionAttribute(httpRequest, "searchVo",
							aHVO.getAddlHolidaysSearchVo());
					ModelAndView mav = new ModelAndView(ModelAndViewConstant.ADDL_HOLIDAYS_LIST,"addHolidayMas",aHVO);
					Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
							"d-");															
					httpRequest.setAttribute("page", mav);
					httpRequest.setAttribute("errors", "error");
					// Check whether the list already get or have to fetch from data base
					if (httpRequest.getParameter("Get").equals("Get")) {
						WebUtils.setSessionAttribute(httpRequest, "keepStatus",
								null);
						WebUtils.setSessionAttribute(httpRequest, "success", null);
					}
					else {
						WebUtils.setSessionAttribute(httpRequest, "keepStatus",
								"status");
					}
					if (stockParamMap.size() == 0) {

						logger.info("Table operation has been triggered");
						SessionCache sessionCache = (SessionCache) session
								.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

						// Get list from database
						additionalHolidaysService.getListOfAddlHolidays(aHVO,sessionCache.getUserSessionDetails());											
						// Set the list to session to access in jsp
						WebUtils.setSessionAttribute(httpRequest, "display_tbl",
								aHVO.getAddlHolidaysVOList());
						aHVO.setPageSize(aHVO.getPageSize());

					}					
					redirectAttributes.addFlashAttribute("addHolidayMas",
							aHVO);
					return "redirect:/addlHolidaysList.htm?data";
					
				}
				
				
				
				@RequestMapping(value = "/addlHolidaysList", method = RequestMethod.GET, params = "data")
				public ModelAndView addlHolidaysListBack(
						@ModelAttribute("addHolidayMas") AddlHolidaysMasterVO aHVO,
						HttpServletRequest httpServletRequest) {
					System.out.println("data methoddddddddddddddd");
					AddlHolidaysSearchVO addlHolSearchVo= (AddlHolidaysSearchVO) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");		
					aHVO.setAddlHolidaysSearchVo(addlHolSearchVo)	;		
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ADDL_HOLIDAYS_LIST, "addHolidayMas",
							aHVO);
					
					return modelAndView;
				}

								
				@RequestMapping(value = "/addlHolidaysList", method = RequestMethod.POST, params = {
						"actionSave"
					})
				public String saveAdditionalHolidays(
						@ModelAttribute("addHolidayMas") AddlHolidaysMasterVO aHVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session,RedirectAttributes redirectAttribute) throws DuplicateEntryException, DatabaseException,DuplicateEntryForHolGenException,DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException
						{
				
					logger.debug("inside save Additional Holidays method");
					if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){	
					AddlHolidaysSearchVO addlHolSearchVo= (AddlHolidaysSearchVO) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");			
					aHVO.setAddlHolidaysSearchVo(addlHolSearchVo);
					}
					if (aHVO.getAddlHolidaysVO().getAcTerm().contains(",")) {
						String parts[] = aHVO.getAddlHolidaysVO().getAcTerm()
								.split(",");
						aHVO.getAddlHolidaysVO().setAcTerm(parts[0]); 
					}
					if (aHVO.getAddlHolidaysVO().getStudentGrpId().contains(",")) {
						String parts[] = aHVO.getAddlHolidaysVO().getStudentGrpId()
								.split(",");
						aHVO.getAddlHolidaysVO().setStudentGrpId(parts[0]); 
					}
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ADDL_HOLIDAYS_LIST, "addHolidayMas",
							aHVO);
			               httpServletRequest.setAttribute("errors", "erroradd");		
					redirectAttribute.addFlashAttribute("addHolidayMas", aHVO);
					logger.debug("inside save AcademicCalendar method");
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					
					httpServletRequest.setAttribute("page", modelAndView);
					
					
					additionalHolidaysService.insertAdditionalHolidaysRec(aHVO.getAddlHolidaysVO(), sessionCache.getUserSessionDetails());	
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.ADD_SUCCESS_MESS);
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/addlHolidaysList.htm?Get";
				}
								
				@RequestMapping(value = "/addlHolidaysList", method = RequestMethod.GET, params = {
						"delete"
					})
				public String deleteAddlHolidaysRec(
						@ModelAttribute("addHolidayMas") AddlHolidaysMasterVO aHVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session,RedirectAttributes redirectAttribute) throws DuplicateEntryException, DatabaseException, DeleteFailedException, NoDataFoundException, TableNotSpecifiedForAuditException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException
						{

					logger.debug("inside Delete Additional Holidays Rec method");
					if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){	
					AddlHolidaysSearchVO addlHolSearchVo= (AddlHolidaysSearchVO) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");			
					aHVO.setAddlHolidaysSearchVo(addlHolSearchVo)	;	
					}
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ADDL_HOLIDAYS_LIST, "addHolidayMas",
							aHVO);
			               httpServletRequest.setAttribute("errors", "erroradd");		
					redirectAttribute.addFlashAttribute("addHolidayMas", aHVO);
					logger.debug("inside Deleted Academic Calendae method");
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					
					httpServletRequest.setAttribute("page", modelAndView);
					List<AddlHolidaysVO> addlHolidaysVOs = (List<AddlHolidaysVO>) WebUtils
							.getSessionAttribute(httpServletRequest, "display_tbl");

					String id = httpServletRequest.getParameter("rowId");				
					aHVO.setAddlHolidaysVO(addlHolidaysVOs.get(Integer
							.parseInt(id)));
					additionalHolidaysService.removeAddHolidaysRec(
							aHVO.getAddlHolidaysVO(),
							sessionCache.getUserSessionDetails(),applicationCache);
					
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.DELETE_SUCCESS_MESS);
					logger.debug("Data's deleted successfully!");
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/addlHolidaysList.htm?Get";
				}
				
				// Get Values from list for popup
				@RequestMapping(value = "/addlHolidaysList", method = RequestMethod.GET, params = { "actionGet" })
				public String getAddlHolidays(
						@ModelAttribute("addHolidayMas")AddlHolidaysMasterVO aHVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute) {
					logger.debug("inside Get Addl holidays from List method");
					if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){	
						AddlHolidaysSearchVO addlHolSearchVo= (AddlHolidaysSearchVO) WebUtils
								.getSessionAttribute(httpServletRequest, "searchVo");			
						aHVO.setAddlHolidaysSearchVo(addlHolSearchVo)	;	
						}
						ModelAndView modelAndView = new ModelAndView(
								ModelAndViewConstant.ADDL_HOLIDAYS_LIST, "addHolidayMas",
								aHVO);
				               httpServletRequest.setAttribute("errors", "erroradd");		
						redirectAttribute.addFlashAttribute("addHolidayMas", aHVO);
						
					
						
						httpServletRequest.setAttribute("page", modelAndView);
						List<AddlHolidaysVO> addlHolidaysVOs = (List<AddlHolidaysVO>) WebUtils
								.getSessionAttribute(httpServletRequest, "display_tbl");

						String id = httpServletRequest.getParameter("rowId");				
						aHVO.setAddlHolidaysVO(addlHolidaysVOs.get(Integer
								.parseInt(id)));
					
				
					String keepstat = (String) WebUtils.getSessionAttribute(
							httpServletRequest, "keepStatus");
					System.out.println("Keep status  :" + keepstat);
					
					if (keepstat != null) {
						modelAndView.getModelMap().addAttribute("status", "false");
					} else {
						modelAndView.getModelMap().addAttribute("status", "true");
					}
					redirectAttribute.addFlashAttribute("popup", "update");
					redirectAttribute.addFlashAttribute("acadcal", aHVO);
					return "redirect:/addlHolidaysList.htm?data";
				}		
				
				
				// Update method
				@RequestMapping(value = "/addlHolidaysList", method = RequestMethod.POST, params = { "actionUpdate" })
				public String updateAcademicCalendar(
						@ModelAttribute("addHolidayMas")AddlHolidaysMasterVO aHVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute)
						throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
					logger.debug("inside update addl holidays method");
					if(WebUtils.getSessionAttribute(httpServletRequest, "searchVo")!=null){	
						AddlHolidaysSearchVO addlHolSearchVo= (AddlHolidaysSearchVO) WebUtils
								.getSessionAttribute(httpServletRequest, "searchVo");			
						aHVO.setAddlHolidaysSearchVo(addlHolSearchVo)	;	
						}
						ModelAndView modelAndView = new ModelAndView(
								ModelAndViewConstant.ADDL_HOLIDAYS_LIST, "addHolidayMas",
								aHVO);
				               httpServletRequest.setAttribute("errors", "erroradd");		
						redirectAttribute.addFlashAttribute("addHolidayMas", aHVO);
						
						ApplicationCache applicationCache = (ApplicationCache) session
								.getServletContext().getAttribute(
										ApplicationConstant.APPLICATION_CACHE);
						SessionCache sessionCache = (SessionCache) session
								.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);	
					
					httpServletRequest.setAttribute("page", modelAndView);
					additionalHolidaysService.updateAddlHolidaysRec(aHVO.getAddlHolidaysVO(), sessionCache.getUserSessionDetails(),applicationCache);					
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.UPDATE_SUCCESS_MESS);
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");					
					logger.debug("Data's updated successfully!");
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/addlHolidaysList.htm?Get";
				}
				
				
				@ExceptionHandler({ DatabaseException.class,DuplicateEntryException.class,DuplicateEntryForHolGenException.class,
DuplicateEntryForAcaTermHolGenException.class,TableNotSpecifiedForAuditException.class		})
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
