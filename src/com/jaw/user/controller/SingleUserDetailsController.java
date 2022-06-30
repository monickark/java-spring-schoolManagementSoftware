package com.jaw.user.controller;

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
import org.springframework.web.util.WebUtils;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.ProfileGroup;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mgmtUser.controller.MgmtUserVO;
import com.jaw.prodAdm.service.ICommonCodeService;
import com.jaw.staff.controller.StaffVo;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.user.service.ISingleUserDetailsService;

@Controller
public class SingleUserDetailsController {
	Logger logger = Logger.getLogger(SingleUserDetailsController.class);
	@Autowired
	ICommonCodeService commonCodeService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	ISingleUserDetailsService singleUserDetailsService;
	String error = "";
	
	@RequestMapping(value = "/userDetails", method = RequestMethod.GET)
	public ModelAndView singleUserDetails(
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map) throws NoDataFoundException, InvalidUserIdException,
			PropertyNotFoundException {
		ApplicationCache applicationCache = (ApplicationCache) session.getServletContext()
				.getAttribute(
						ApplicationConstant
						.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.
				SESSION_CACHE_KEY);
		
		String userId = httpServletRequest.getParameter("userId");
		
		MgmtUserVO managementVo = new MgmtUserVO();
		BranchAdminVO branchAdminVO = new BranchAdminVO();
		StaffVo staffAdmissionVo = new StaffVo();
		AdmissionDetailsVO admissionDetailsVO = new AdmissionDetailsVO();
		SingleParentDetailsVO parentDetailsVO = new SingleParentDetailsVO();
		ModelAndView modelAndView = null;
		String profileGroup = new String();
		profileGroup = singleUserDetailsService
				.getSingleUser(applicationCache, userId, sessionCache.getUserSessionDetails(),
						managementVo, branchAdminVO,
						staffAdmissionVo, admissionDetailsVO, parentDetailsVO, profileGroup);
		
		logger.debug("Profile group :" + profileGroup);
		logger.debug("Student data :" + admissionDetailsVO.getStudentAdmisNo());
		logger.debug("Non Staff data :" + admissionDetailsVO.getStudentAdmisNo());
		logger.debug("Parent data :" + admissionDetailsVO.getStudentAdmisNo());
		
		ProfileGroup pg = ProfileGroup.valueOf(profileGroup);
		switch (pg) {
			case NSF: {
				System.out.println("branch admin :" + branchAdminVO.getStaffId());
				modelAndView = new ModelAndView(".jaw.NonStaffDetails", "singleUser", branchAdminVO);
				break;
			}
			case PAR: {
				
				modelAndView = new ModelAndView(".jaw.ViewSingleParent", "singleUser",
						parentDetailsVO);
				break;
			}
			
			case MGT: {
				logger.debug("management id:" + managementVo.getUserId());
				modelAndView = new ModelAndView(".jaw.ViewSingleManagement", "singleUser",
						managementVo);
				break;
			}
			case STF: {
				logger.debug("branch admin id:" +
						branchAdminVO.getUserId());
				modelAndView = new
						ModelAndView(".jaw.StaffSingleUser", "staff", staffAdmissionVo);
				break;
			}
			case STU: {
				modelAndView = new
						ModelAndView(".jaw.ViewSingleStudent", "singleUser", admissionDetailsVO);
				break;
			}
			
			default:
				break;
		
		}
		httpServletRequest.setAttribute("page"
				, modelAndView);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/singleUser", method = RequestMethod.POST, params = "Search=Search")
	public ModelAndView userManagementBack(
			@ModelAttribute("userManagement") UserManagementVO userManagement) {
		return new ModelAndView(".jaw.userManagement");
		
	}
	
	@ExceptionHandler({
			InvalidUserIdException.class,
			NoDataFoundException.class,
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "request", null);
		ModelAndView page = (ModelAndView) request.getAttribute("page");
		logger.debug("Page is :" + page.getViewName());
		String type = "error";
		String message = ex.getMessage();
		page.getModelMap().addAttribute("message", message)
				.addAttribute("type", type);
		return page;
		
	}
}
