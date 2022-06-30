package com.jaw.mark.controller;

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

import com.jaw.attendance.service.IAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.AttendanceExistException;
import com.jaw.common.exceptions.batch.StudentNotFoundForMarkException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.service.IAddMarksService;
import com.jaw.mark.service.IMarkMasterService;

@Controller
public class AddMarksController {
	// Logging
	Logger logger = Logger.getLogger(AddMarksController.class);
	@Autowired
	IAddMarksService addMarkService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IAttendanceService attendanceService;

	// Academic Calendar List Get method

	@RequestMapping(value = "/addMarks", method = RequestMethod.GET)
	public ModelAndView addMarksPage(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Add Marks List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl_AddMark", null);
		WebUtils.setSessionAttribute(httpRequest, "seletedAction", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus",
				null);
		WebUtils.setSessionAttribute(httpRequest, "addMarkHighSearch",
				null);
		WebUtils.setSessionAttribute(httpRequest, "markSubLinkVO",
				null);
		WebUtils.setSessionAttribute(httpRequest, "rowIdMarks",
				null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;
		

	}

	@RequestMapping(value = "/addMarks", method = RequestMethod.GET, params = { "Get" })
	public String viewMarkMasterList(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			HttpSession session, HttpServletRequest httpRequest,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
		/*
		 * WebUtils.setSessionAttribute(httpRequest, "searchVo",
		 * specialClassMasterVO);
		 */
		WebUtils.setSessionAttribute(httpRequest, "display_tbl_AddMark", null);
		WebUtils.setSessionAttribute(httpRequest, "seletedAction", null);
		WebUtils.setSessionAttribute(httpRequest, "markSubLinkVO",
				null);
					WebUtils.setSessionAttribute(httpRequest, "rowIdMarks",
				null);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");

		if ((addMarksMasterVO.getAddMarkSearch().getCrslId() != "")
				&& (addMarksMasterVO.getAddMarkSearch().getCrslId() != null)
				&& (!addMarksMasterVO.getAddMarkSearch().getCrslId()
						.equals(","))) {
			if ((addMarksMasterVO.getAddMarkSearch().getCrslId().contains(","))) {
				String parts[] = addMarksMasterVO.getAddMarkSearch()
						.getCrslId().split(",");
				if (parts.length != 0) {
					if ((parts[0] != null) && (parts[0] != "")) {
						addMarksMasterVO.getAddMarkSearch().setCrslId(parts[0]);
					} else {
						addMarksMasterVO.getAddMarkSearch().setCrslId(parts[1]);
					}
				} else {
					addMarksMasterVO.getAddMarkSearch().setCrslId("");
				}

			}
		}
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					true);
		}
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		WebUtils.setSessionAttribute(httpRequest, "addMarkHighSearch",
				addMarksMasterVO.getAddMarkSearch());
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database
			addMarkService.getMarkSubjectListForAdd(addMarksMasterVO,
					sessionCache.getUserSessionDetails());
			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					addMarksMasterVO.getMarkSubListVo());
			addMarksMasterVO.setPageSize(addMarksMasterVO.getPageSize());
			addMarksMasterVO.setPageSize1(addMarksMasterVO.getPageSize1());
		}
		redirectAttributes.addFlashAttribute("addMarkMaster", addMarksMasterVO);
		return "redirect:/addMarks.htm?data";

	}

	@RequestMapping(value = "/addMarks", method = RequestMethod.GET, params = "data")
	public ModelAndView markMasterBack(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			HttpServletRequest httpServletRequest) {
			addMarksMasterVO.setPageSize(addMarksMasterVO.getPageSize());
			addMarksMasterVO.setPageSize1(addMarksMasterVO.getPageSize1());
	if(httpServletRequest.getParameter("edit")!=null){
	WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				false);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
	}
	String markgrade=null;
		AddMarksSearchVO adMarksSearchVO = (AddMarksSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "addMarkHighSearch");
		MarkSubjectLinkListForAddMarksVO markSubLinkVO=(MarkSubjectLinkListForAddMarksVO)WebUtils.getSessionAttribute(
				httpServletRequest, "markSubLinkVO");
		addMarksMasterVO.setAddMarkSearch(adMarksSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		
		String[] subremarks = httpServletRequest
				.getParameterValues("subRemarks");
		String[] marks = httpServletRequest.getParameterValues("marksText");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");
		String[] attendanceValue = httpServletRequest
				.getParameterValues("attendance");
		String[] grades = httpServletRequest.getParameterValues("grades");
		/*String markgrade = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "markOrGrade");*/
		if(markSubLinkVO!=null){
		 markgrade=markSubLinkVO.getMarkGrade();
		}
		List<StudentListForAddMarksVO> stuMrkList = (List<StudentListForAddMarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl_AddMark");

		if (stuMrkList != null) {
			if ((subremarks != null)) {
				for (int j = 0; j < subremarks.length; j++) {
					int value = Integer.parseInt(rowIds[j]);
					stuMrkList.get(value).setSubRemarks(subremarks[j]);
				}

			}
			if (markgrade.equals(ApplicationConstant.MARK)) {
				if ((marks != null)) {
					for (int j = 0; j < marks.length; j++) {
						int value = Integer.parseInt(rowIds[j]);
						stuMrkList.get(value).setMarksObtd(marks[j]);
					}

				}
			} else {
				if ((grades != null)) {
					for (int j = 0; j < grades.length; j++) {
						int value = Integer.parseInt(rowIds[j]);
						stuMrkList.get(value).setGradeObtd(grades[j]);
					}

				}
			}
			if ((attendanceValue != null)) {
				for (int j = 0; j < attendanceValue.length; j++) {
					int absentValue = Integer.parseInt(attendanceValue[j]);
					stuMrkList.get(absentValue).setAttendance("A");
				}

			}
		}
		
		return modelAndView;
	}

	// Add Marks Student List
	@RequestMapping(value = "/addMarks", method = RequestMethod.GET, params = { "addMarksParam" })
	public String addMarks(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException,
			 StudentNotFoundForMarkException {
		logger.debug("inside Add Marks method");
		addMarksMasterVO.setPageSize(addMarksMasterVO.getPageSize());
			addMarksMasterVO.setPageSize1(addMarksMasterVO.getPageSize1());
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		httpServletRequest.setAttribute("errors", "erroradd");
		List<MarkSubjectLinkListForAddMarksVO> addMarksVOs = (List<MarkSubjectLinkListForAddMarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowid");
		AddMarksSearchVO adMarksSearchVO = (AddMarksSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "addMarkHighSearch");
		addMarksMasterVO.setAddMarkSearch(adMarksSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		MarkSubjectLinkListForAddMarksVO mSubAddLink = addMarksVOs.get(Integer
				.parseInt(id));
		String add = "ADD";
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				id);
		addMarkService.getStudentListForAddMarks(addMarksMasterVO,
				sessionCache.getUserSessionDetails(), mSubAddLink,
				add);		
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl_AddMark",
				addMarksMasterVO.getStuListForMark());
		
		WebUtils.setSessionAttribute(httpServletRequest, "markSubLinkVO",
				mSubAddLink);
		WebUtils.setSessionAttribute(httpServletRequest, "seletedAction", "ADD");
		redirectAttribute.addFlashAttribute("addMarkMaster", addMarksMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				true);
		return "redirect:/addMarks.htm?data";
		//return modelAndView;
	}

	// Edit Marks Student List
	@RequestMapping(value = "/updateMarks", method = RequestMethod.GET, params = { "editMarksParam" })
	public String editMarksList(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException,
			AcadCalendarDeleteFailedException, StudentNotFoundForMarkException {
		logger.debug("inside edit Marks method");
		addMarksMasterVO.setPageSize(addMarksMasterVO.getPageSize());
			addMarksMasterVO.setPageSize1(addMarksMasterVO.getPageSize1());
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				null);
		List<MarkSubjectLinkListForAddMarksVO> addMarksVOs = (List<MarkSubjectLinkListForAddMarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowid");
		AddMarksSearchVO adMarksSearchVO = (AddMarksSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "addMarkHighSearch");
		addMarksMasterVO.setAddMarkSearch(adMarksSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		MarkSubjectLinkListForAddMarksVO mSubAddLink = addMarksVOs.get(Integer
				.parseInt(id));
		String add = "EDIT";
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				id);
		addMarkService.getStudentListForAddMarks(addMarksMasterVO,
				sessionCache.getUserSessionDetails(), mSubAddLink,
				add);

		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl_AddMark",
				addMarksMasterVO.getStuListForMark());
		
		WebUtils.setSessionAttribute(httpServletRequest, "markSubLinkVO",
				mSubAddLink);
		WebUtils.setSessionAttribute(httpServletRequest, "seletedAction",
				"EDIT");	
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		redirectAttribute.addFlashAttribute("addMarkMaster", addMarksMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				true);
		return "redirect:/addMarks.htm?data";
		//return modelAndView;
	}

	// View Marks Student List
	@RequestMapping(value = "/addMarks", method = RequestMethod.GET, params = { "viewMarksParam" })
	public String viewMarksList(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException,
			AcadCalendarDeleteFailedException, StudentNotFoundForMarkException {
		logger.debug("inside view Marks method");
		addMarksMasterVO.setPageSize(addMarksMasterVO.getPageSize());
			addMarksMasterVO.setPageSize1(addMarksMasterVO.getPageSize1());
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				null);
		List<MarkSubjectLinkListForAddMarksVO> addMarksVOs = (List<MarkSubjectLinkListForAddMarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowid");
		AddMarksSearchVO adMarksSearchVO = (AddMarksSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "addMarkHighSearch");
		addMarksMasterVO.setAddMarkSearch(adMarksSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		MarkSubjectLinkListForAddMarksVO mSubAddLink = addMarksVOs.get(Integer
				.parseInt(id));
		String add = "EDIT";
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				id);
		addMarkService.getStudentListForAddMarks(addMarksMasterVO,
				sessionCache.getUserSessionDetails(), mSubAddLink,
				add);
	
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl_AddMark",
				addMarksMasterVO.getStuListForMark());
		
		WebUtils.setSessionAttribute(httpServletRequest, "markSubLinkVO",
				mSubAddLink);
		WebUtils.setSessionAttribute(httpServletRequest, "seletedAction",
				"VIEW");
		redirectAttribute.addFlashAttribute("addMarkMaster", addMarksMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				true);
		return "redirect:/addMarks.htm?data";
		//return modelAndView;
	}

	// Save Method
	@RequestMapping(value = "/addMarks", method = RequestMethod.GET, params = {
			"data", "actionSave" })
	public String saveAddMarkDetails(
			@ModelAttribute("addMarkMaster") AddMarksMasterVO addMarksMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException {
		logger.debug("inside save Add Marksr method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ADD_MARKS, "addMarkMaster",
				addMarksMasterVO);
		AddMarksSearchVO adMarksSearchVO = (AddMarksSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "addMarkHighSearch");
		addMarksMasterVO.setAddMarkSearch(adMarksSearchVO);
		httpServletRequest.setAttribute("page", modelAndView);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		String[] subremarks = httpServletRequest
				.getParameterValues("subRemarks");
		String[] marks = httpServletRequest.getParameterValues("marksText");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");
		String[] attendanceValue = httpServletRequest
				.getParameterValues("attendance");
		String[] grades = httpServletRequest.getParameterValues("grades");
		
		MarkSubjectLinkListForAddMarksVO markSubLinkVO=(MarkSubjectLinkListForAddMarksVO)WebUtils.getSessionAttribute(
				httpServletRequest, "markSubLinkVO");
		List<StudentListForAddMarksVO> stuMrkList = (List<StudentListForAddMarksVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl_AddMark");
		/*String markgrade = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "markOrGrade");
		String mkSlId = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "mkslid");*/
		String markgrade=markSubLinkVO.getMarkGrade();
				String mkSlId=markSubLinkVO.getMkslId();
		
		if (stuMrkList != null) {
			if ((subremarks != null)) {
				for (int j = 0; j < subremarks.length; j++) {
					int value = Integer.parseInt(rowIds[j]);
					stuMrkList.get(value).setSubRemarks(subremarks[j]);
				}

			}
			if (markgrade.equals(ApplicationConstant.MARK)) {
				if ((marks != null)) {
					for (int j = 0; j < marks.length; j++) {
						int value = Integer.parseInt(rowIds[j]);
						stuMrkList.get(value).setMarksObtd(marks[j]);
					}

				}
			} else {
				if ((grades != null)) {
					for (int j = 0; j < grades.length; j++) {
						int value = Integer.parseInt(rowIds[j]);
						stuMrkList.get(value).setGradeObtd(grades[j]);
					}

				}
			}
			if ((attendanceValue != null)) {
				for (int j = 0; j < attendanceValue.length; j++) {
					int absentValue = Integer.parseInt(attendanceValue[j]);
					stuMrkList.get(absentValue).setAttendance("A");
				}

			}
		}		
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String subject=markSubLinkVO.getSubject();
		String examId=markSubLinkVO.getExamId();
		addMarkService.saveMarkdetails(addMarksMasterVO,
				sessionCache.getUserSessionDetails(), mkSlId, markgrade,
				stuMrkList,subject,examId,applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl_AddMark",
				null);
		//sdd
		WebUtils.setSessionAttribute(httpServletRequest, "markSubLinkVO",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "rowIdMarks",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.MARK_SAVE_SUCCESS);
		System.out.println("mark save successs"+ErrorCodeConstant.MARK_SAVE_SUCCESS);			
		redirectAttribute.addFlashAttribute("addMarkMaster", addMarksMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/addMarks.htm?Get";
	}
	
	// Ajax Update Marks
	@RequestMapping(value = "/updateMarks", method = RequestMethod.GET, params = { "updateMark" })
	public String updateMarks(HttpSession session, HttpServletRequest request)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		logger.debug("inside  Update Marksmethod");
		String res = "dddd";
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
	System.out.println("ajaxxxxxxxxxxx updateeeeeeeeeeeeee"+request
			.getParameter("updateMarkss"));
		List<StudentListForAddMarksVO> addMarksVOs = (List<StudentListForAddMarksVO>) WebUtils
				.getSessionAttribute(request, "display_tbl_AddMark");
		String id = request.getParameter("updateRow");
		StudentListForAddMarksVO studentListForAddMarksVO = addMarksVOs
				.get(Integer.parseInt(id));
		MarkSubjectLinkListForAddMarksVO markSubLinkVO=(MarkSubjectLinkListForAddMarksVO)WebUtils.getSessionAttribute(
				request, "markSubLinkVO");
		/*String mkSlId = (String) WebUtils
				.getSessionAttribute(request, "mkslid");*/
		String mkSlId =markSubLinkVO.getMkslId();
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		StudentMarksVO studentMarksVO = new StudentMarksVO();
		if((request.getParameter("updateMarkss")==null)||(request.getParameter("updateMarkss").isEmpty())){
			studentMarksVO.setMarksObtd(0);
		}else{
		studentMarksVO.setMarksObtd(Integer.parseInt(request
				.getParameter("updateMarkss")));
		}
		System.out.println("update reasonnnnnnnnnnnn"+request.getParameter("updateReason"));
		studentMarksVO.setUpdateReason(request.getParameter("updateReason"));
		studentMarksVO.setGradeObtd(request.getParameter("updateGrades"));
		studentMarksVO.setSubRemarks(request.getParameter("updateRemark"));
		studentMarksVO.setAttendance(request.getParameter("updateAbsent"));
		studentMarksVO.setRollNumber(studentListForAddMarksVO.getRollNumber());
		studentMarksVO.setMkslSeqId(mkSlId);
		studentMarksVO.setStudentAdmisNo(studentListForAddMarksVO
				.getStudentAdmisNo());
		if(request.getParameter("updateAbsent").equals("A")){
			studentMarksVO.setGradeObtd("");
			studentMarksVO.setMarksObtd(0);
		}
		addMarkService.updateStudentMarks(studentMarksVO,
				sessionCache.getUserSessionDetails(),applicationCache);
		addMarksVOs.get(Integer.parseInt(id)).setMarksObtd(Integer.toString(studentMarksVO.getMarksObtd()));
		addMarksVOs.get(Integer.parseInt(id)).setGradeObtd(studentMarksVO.getGradeObtd());
		addMarksVOs.get(Integer.parseInt(id)).setAttendance(studentMarksVO.getAttendance());
		addMarksVOs.get(Integer.parseInt(id)).setSubRemarks(studentMarksVO.getSubRemarks());
		
		WebUtils.setSessionAttribute(request, "success",
				ErrorCodeConstant.MARK_UPDATE_SUCCESS);
		WebUtils.setSessionAttribute(request, "keepStatus", "status");
		return "redirect:/addMarks.htm?data";
	}

	// For dropDown

	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getAcademicTermListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}

	
	@RequestMapping(value = "/examListAjax", method = RequestMethod.GET, params = { "examList" })
	public @ResponseBody
	Map<String, String> getExamListBasedOnStudentGrpId(
			HttpServletRequest httpServletRequest, HttpSession session,
			HttpServletResponse response) throws PropertyNotFoundException,
			ErrorDescriptionNotFoundException

	{	
		System.out.println("exam list :");
		Map<String, String> map = null;
		String studentGrpId=(String) httpServletRequest.getParameter("studentGroupId");
		System.out.println("exam list : student "+studentGrpId);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.selectExamNameList(sessionCache
					.getUserSessionDetails(),studentGrpId);
		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Student Group Dropdown :"
					+ e.getMessage());
		}
		
		return map;

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			CommonCodeNotFoundException.class,
			DatabaseException.class ,UpdateFailedException.class,
			 TableNotSpecifiedForAuditException.class})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);

		return modelAndView;
	}
	
	@ExceptionHandler({ 
		NoDataFoundException.class  })
public ModelAndView handleNoDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());	
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);

	WebUtils.setSessionAttribute(request, "display_tbl", null);
	WebUtils.setSessionAttribute(request, "display_tbl_AddMark", null);
	return mav;
}
	@ExceptionHandler({ 
		StudentNotFoundForMarkException.class  })
public ModelAndView handleNoStudentDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());	
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);

	WebUtils.setSessionAttribute(request, "display_tbl_AddMark", null);
	return mav;
}
}
