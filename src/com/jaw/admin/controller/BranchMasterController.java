package com.jaw.admin.controller;

import java.io.IOException;
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
import org.springframework.web.util.WebUtils;

import com.jaw.admin.service.IBranchMasterService;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class BranchMasterController {
	@Autowired
	IBranchMasterService branchMasterService;
	@Autowired
	CommonBusiness commonBusiness;

	Logger logger = Logger.getLogger(BranchMasterController.class);

	@RequestMapping(value = "/branchMaster", method = RequestMethod.GET)
	public ModelAndView viewInstMaster(
			@ModelAttribute("branch") BranchMasterListVO branchVO,
			HttpSession session, HttpServletRequest request, ModelMap model)
			throws NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		logger.info("Opening Branch Master Page");
		ModelAndView mav = new ModelAndView(".jaw.branchMaster");
		List<BranchMasterListVO> listOfBranchMaster = null;
		if (request.getParameter("stat") != null) {
			mav.getModelMap().addAttribute("status", "false");
		} else {
			mav.getModelMap().addAttribute("status", "true");
		}

		if ((request.getParameter("action1") != null)
				&& request.getParameter("action1").equals("Back")) {
			listOfBranchMaster = (List<BranchMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");
		} else {
			String instId = sessionCache.getUserSessionDetails().getInstId();

			request.setAttribute("listOfBranchMaster", null);
			Map stockParamMap = WebUtils.getParametersStartingWith(request,
					"d-");
			if (stockParamMap.size() == 0) {
				listOfBranchMaster = branchMasterService
						.selectBranchMasterList(instId);
				WebUtils.setSessionAttribute(request, "display_tbl",
						listOfBranchMaster);
			}
			listOfBranchMaster = (List<BranchMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");

			String success = request.getParameter("success");
			if (success != null) {
				mav.getModelMap().addAttribute("success", success);
			}

		}
		return mav;

	}

	// method to extract the list which is already in session
	private List<BranchMasterListVO> extracted(HttpServletRequest request) {
		List<BranchMasterListVO> sessionAttribute = (List<BranchMasterListVO>) WebUtils
				.getSessionAttribute(request, "display_tbl");
		return sessionAttribute;
	}

	@RequestMapping(value = "/viewBranchMaster", method = RequestMethod.GET)
	public ModelAndView viewBranchDetails(
			@ModelAttribute("branchVO") BranchMasterVO branchVO,
			HttpSession session, HttpServletRequest request, ModelMap model)
			throws NoDataFoundException {
		logger.info("Branch Master Details page is opening");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mv = new ModelAndView(".jaw.branchDetails");
		if ((request.getParameter("action1") != null)
				&& request.getParameter("action1").equals("Back")) {
			ModelAndView mav = new ModelAndView("redirect:/branchMaster.htm");
			mav.addObject("stat", "clear");
			mav.addObject("action1", "Back");
			return mav;

		} else {
			String rowid = request.getParameter("rowid");
			List<BranchMasterListVO> branchList = null;
			branchList = extracted(request);
			BranchMasterListVO branchMasterRec = null;
			branchMasterRec = branchList.get(Integer.valueOf(rowid));
			branchMasterService.selectBranchMasterRecord(branchMasterRec,
					branchVO, sessionCache);
		}

		return mv;
	}

	@RequestMapping(value = "/editBranchMasterView", method = RequestMethod.GET)
	public ModelAndView editBranchDetails(
			@ModelAttribute("branchVo") BranchMasterVO branchVO,
			HttpSession session, HttpServletRequest request, ModelMap model)
			throws NoDataFoundException, UpdateFailedException,
			DuplicateEntryException, FileNotFoundInDatabase {

		logger.info("Branch Master Details page is opening");
		ModelAndView mav = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String rowid = request.getParameter("rowid");
		mav = new ModelAndView(".jaw.branchEdit");
		List<BranchMasterListVO> branchList = null;
		branchList = extracted(request);
		BranchMasterListVO branchMasterRec = null;
		branchMasterRec = branchList.get(Integer.valueOf(rowid));
		branchMasterService.selectBranchMasterRecord(branchMasterRec, branchVO,
				sessionCache);
		branchVO.setRowid(Integer.valueOf(rowid));
		return mav;

	}

	@RequestMapping(value = "/editBranchMasterView", method = RequestMethod.POST)
	public ModelAndView editBranchDetailsUpdate(
			@ModelAttribute("branchVo") BranchMasterVO branchVO,
			HttpSession session, HttpServletRequest request, ModelMap model)
			throws NoDataFoundException, UpdateFailedException,
			DuplicateEntryException, FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		request.setAttribute("branch", branchVO);
		String rowid = request.getParameter("rowid");
		List<BranchMasterListVO> branchList = null;
		branchList = extracted(request);
		BranchMasterListVO branchMasterOldRec = null;
		branchMasterOldRec = branchList.get(Integer.valueOf(rowid));
		ModelAndView mav = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		if ((request.getParameter("editPageBttn") != null)
				&& request.getParameter("editPageBttn").equals("Update")) {
			String dbts = request.getParameter("dbTs");
			branchVO.setDbTs(Integer.valueOf(dbts));
			branchMasterService.updateBranchMaster(branchMasterOldRec,
					applicationCache, branchVO, sessionCache,session.getServletContext());
			branchVO.setDbTs(branchVO.getDbTs() + 1);
			branchVO.setRowid(Integer.valueOf(rowid));
			WebUtils.setSessionAttribute(request, "display_tbl",
					branchMasterService
					.selectBranchMasterList(sessionCache.getUserSessionDetails().getInstId()));			
			// editBranchMasterView
			mav = new ModelAndView("redirect:branchMaster.htm").addObject(
					"stat", "clear").addObject("success",
					ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		} else if ((request.getParameter("editPageBttn") != null)
				&& request.getParameter("editPageBttn").equals("Reset")) {
			mav = new ModelAndView("redirect:editBranchMasterView.htm")
					.addObject("rowid", rowid);
		}

		return mav;

	}

	@RequestMapping(value = "/deleteBranchMaster", method = RequestMethod.GET)
	public ModelAndView deleteBranchMaster(
			@ModelAttribute("branch") BranchMasterListVO branchVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException, DeleteFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		logger.info("Called deleteBranchMaster Function");
		String rowid = httpServletRequest.getParameter("rowid");
		List<BranchMasterListVO> branchList = null;
		branchList = extracted(httpServletRequest);
		BranchMasterListVO branchMasterRec = null;
		branchMasterRec = branchList.get(Integer.valueOf(rowid));
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		branchMasterService.deleteBranchMaster(applicationCache,
				branchMasterRec, sessionCache);
		return new ModelAndView("redirect:branchMaster.htm").addObject(
				"success", ErrorCodeConstant.DELETE_SUCCESS_MESS);
	}

	@RequestMapping(value = "/branchMasterAdd", method = RequestMethod.GET)
	public ModelAndView viewAddBranchMaster(
			@ModelAttribute("branchVO") BranchMasterVO branchVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			ModelMap model) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		branchVO.setInstId(sessionCache.getUserSessionDetails().getInstId());
		ModelAndView mav = new ModelAndView(".jaw.branchMasterAdd");
		return mav;

	}

	@RequestMapping(value = "/branchMasterAdd", method = RequestMethod.POST)
	public ModelAndView insertBranchMaster(
			@ModelAttribute("branchVO") BranchMasterVO branchVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			ModelMap model) throws DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		logger.info("inside insertBranchMaster");

		ModelAndView mav = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		String action = httpServletRequest.getParameter("addPageBttn");
		if (action.equals("Add")) {
			mav = new ModelAndView(".jaw.branchMasterAdd").addObject("success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);
			branchMasterService.insertIntoBranchMaster(applicationCache,
					branchVO, sessionCache,session.getServletContext());
			logger.info("Data's inserted successfully!");

		}
		return mav;
	}

	@ExceptionHandler({ NoDataFoundException.class,
			DeleteFailedException.class,
			TableNotSpecifiedForAuditException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		BranchMasterVO branchVO = (BranchMasterVO) request
				.getAttribute("branch");

		ModelAndView mav = new ModelAndView(".jaw.branchMaster", "branch",
				branchVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}

}
