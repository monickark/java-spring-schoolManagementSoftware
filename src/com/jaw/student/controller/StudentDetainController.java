package com.jaw.student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.DuplicatesInList;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.PreSportParticipationDetailsVO;
import com.jaw.student.admission.controller.SiblingDetailsVO;
import com.jaw.student.admission.dao.StudentDetain;
import com.jaw.student.service.IStudentDetainService;

@Controller
public class StudentDetainController {
	Logger logger = Logger.getLogger(StudentDetainController.class);
	
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IStudentDetainService studentPromotionService;
			
	//Method to render the Student Detain Page
		@RequestMapping(value = "/stuDetain", method = RequestMethod.GET)
		public ModelAndView stuDetainPage(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException {	
			ModelAndView mav = new ModelAndView(".jaw.stuDetain");	
			WebUtils.setSessionAttribute(request, "success", null);
			WebUtils.setSessionAttribute(request, "display_tbl", null);
			WebUtils.setSessionAttribute(request, "display_tbl1", null);
			WebUtils.setSessionAttribute(request, "search", null);
			return mav;

		}
		
		@RequestMapping(value = "/stuDetain", method = RequestMethod.GET, params = {"Data","!batchSave","!addRow"})
		public ModelAndView stuDetainPageData(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException, DataIntegrityException, RuntimeExceptionForBatch, DuplicatesInList {	
			ModelAndView mav = new ModelAndView(".jaw.stuDetain");	
			if((request.getParameter("batchSave")!=null)&&(request.getParameter("batchSave").equals("Save"))){				
				
				String[] checkBoxes = request.getParameterValues("_chk");
				
				List<StudentDetainListVO> stuList = (List<StudentDetainListVO>) WebUtils
						.getSessionAttribute(request, "display_tbl");
				SessionCache sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				
				if(checkBoxes!=null){	
					List<StudentDetainListVO> studentListForSave = new ArrayList<StudentDetainListVO>();
					for(String checkBox:checkBoxes){				
						studentListForSave.add(stuList.get((Integer.valueOf(checkBox))-1));
					}	
					studentDetainMasterVO.setStuDetList(studentListForSave);	
					studentPromotionService.insertDetainedStudents(sessionCache.getUserSessionDetails(), studentListForSave);				
					studentDetainMasterVO = (StudentDetainMasterVO) WebUtils
							.getSessionAttribute(request, "search");
					WebUtils.setSessionAttribute(request, "success",
							ErrorCodeConstant.STUU_SUCCESS);
				
			

						
			}
			}
			return mav;

		}

	//Method to render the Student Detain Page
				@RequestMapping(value = "/stuDetain", method = RequestMethod.GET, params = "stgGrpList")
				public ModelAndView getStudentListForDetain(
						@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
						HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException {
					request.setAttribute("stuDetain", studentDetainMasterVO);
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ModelAndView mav = new ModelAndView(".jaw.stuDetain");					
					
					if ((request.getParameter("stgGrpList")!=null)&&(request.getParameter("stgGrpList").equals("Get")) ){			
						WebUtils.setSessionAttribute(request, "display_tbl", null);
						WebUtils.setSessionAttribute(request, "success", null);
					}
					else {								
						StudentDetainSearchVO studentDetainSearchVO = (StudentDetainSearchVO)WebUtils.getSessionAttribute(request, "search");
						studentDetainMasterVO.setStuSearchVO(studentDetainSearchVO);
						WebUtils.setSessionAttribute(request, "keepStatus",
								"status");
					}
					
					//Get the Student List									
					Map<String,String> mapOfValues = studentPromotionService.getStuList(studentDetainMasterVO.getStuSearchVO().getStuGrpId(),
							studentDetainMasterVO.getStuSearchVO().getAcademicYear(),sessionCache.getUserSessionDetails());
					WebUtils.setSessionAttribute(request, "display_tbl1",
							mapOfValues);
					mav.getModelMap().addAttribute("StuNameMap", mapOfValues);
					
					
					
					//Get the detained students
				
					List<StudentDetainListVO> stuDetainedList = studentPromotionService.getDetainedStudents(studentDetainMasterVO.getStuSearchVO().getAcademicYear(),
							studentDetainMasterVO.getStuSearchVO().getStuGrpId());
					
					List<StudentDetainListVO> listOfStudentDetain = new ArrayList<StudentDetainListVO>();
					
					listOfStudentDetain.addAll(stuDetainedList);					
			
					WebUtils.setSessionAttribute(request, "display_tbl",
							listOfStudentDetain);
				
					WebUtils.setSessionAttribute(request, "search",
							studentDetainMasterVO.getStuSearchVO());
						
				
					return mav;

				}
						
				
		@RequestMapping(value = "/stuDetain", method = RequestMethod.GET, params = "Edit")
		public String updateStudentDetainRec(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException, UpdateFailedException, TableNotSpecifiedForAuditException {
		
			String rowId = request.getParameter("rowId");
			List<StudentDetainListVO> listOfStudentDetain = (List<StudentDetainListVO>) WebUtils.getSessionAttribute(request, "display_tbl");
			StudentDetainListVO studentDetainListVO = listOfStudentDetain.get(Integer.valueOf(rowId)-1);
		
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			
			 String remarks = request.getParameter("remark");
		
			 studentDetainListVO.setDetainRemarks(remarks);
			String admisNo = studentDetainListVO.getStuAdmisNo();
			String acTerm = studentDetainListVO.getAcademicYear();		
		
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
												
				studentPromotionService.updateStudentDetainRec(applicationCache,sessionCache.getUserSessionDetails(), studentDetainListVO);				
				WebUtils.setSessionAttribute(request, "success",
						ErrorCodeConstant.UPDATE_SUCCESS_MESS);
				logger.debug("Data's Updated successfully!");
				WebUtils.setSessionAttribute(request, "keepStatus", "status");
				return "redirect:/stuDetain.htm?stgGrpList";
									
					
		}
		
		
		
		@RequestMapping(value = "/stuDetain", method = RequestMethod.GET, params = "Delete")
		public String deleteStudentDetainRec(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException, UpdateFailedException, DeleteFailedException, TableNotSpecifiedForAuditException {		
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			
			String rowId = request.getParameter("rowId");
			List<StudentDetainListVO> listOfStudentDetain = (List<StudentDetainListVO>) WebUtils.getSessionAttribute(request, "display_tbl");
			StudentDetainListVO studentDetainListVO = listOfStudentDetain.get(Integer.valueOf(rowId)-1);		
					
			String admisNo = studentDetainListVO.getStuAdmisNo();
			String acTerm = studentDetainListVO.getAcademicYear();				
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				studentPromotionService.deleteStudentDetainRec(applicationCache,sessionCache.getUserSessionDetails(), admisNo, acTerm);
				
				WebUtils.setSessionAttribute(request, "success",
						ErrorCodeConstant.DELETE_SUCCESS_MESS);
				logger.debug("Data's deleted successfully!");
				WebUtils.setSessionAttribute(request, "keepStatus", "status");
				return "redirect:/stuDetain.htm?stgGrpList";
									
					
		}
				
		@RequestMapping(value = "/stuDetain", method = RequestMethod.POST, params = {"batchSave"})
		public String stuDetainBatchSave(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException, DataIntegrityException, RuntimeExceptionForBatch, NumberFormatException, DuplicatesInList {			
			ModelAndView mav = new ModelAndView(".jaw.stuDetain");	
		
			StudentDetainSearchVO studentDetainSearchVO = (StudentDetainSearchVO) WebUtils
					.getSessionAttribute(request, "search");
			studentDetainMasterVO.setStuSearchVO(studentDetainSearchVO);
			request.setAttribute("stuDetain", studentDetainMasterVO);
			setRecordIntoSessionList(request, studentDetainSearchVO);
			
			List<StudentDetainListVO> stuList = (List<StudentDetainListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");		
			
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					
				List<StudentDetainListVO> studentListForSave = new ArrayList<StudentDetainListVO>();
				for(StudentDetainListVO studentDetainListVO:stuList){	
					studentDetainListVO.setAcademicYear(studentDetainSearchVO.getAcademicYear());
					if(studentDetainListVO.getOldRec().equals("N")){
						studentListForSave.add(studentDetainListVO);
					}
					
				}	
				studentDetainMasterVO.setStuDetList(studentListForSave);				
				studentPromotionService.insertDetainedStudents(sessionCache.getUserSessionDetails(), studentListForSave);				
				
				studentDetainMasterVO.setStuSearchVO(studentDetainSearchVO);
				WebUtils.setSessionAttribute(request, "success",
						ErrorCodeConstant.STUU_SUCCESS);
			
		
				
			return "redirect:/stuDetain.htm?stgGrpList";
		}
		
		
		
		@RequestMapping(value = "/stuDetain", method = RequestMethod.POST, params = {"Data","addRow","!batchSave"})
		public ModelAndView addNewRecInList(
				@ModelAttribute("stuDetain") StudentDetainMasterVO studentDetainMasterVO,
				HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException, DataIntegrityException, RuntimeExceptionForBatch {
		
			ModelAndView mav = new ModelAndView(".jaw.stuDetain");	
			
			
			StudentDetainSearchVO searchObj = (StudentDetainSearchVO) WebUtils
					.getSessionAttribute(request, "search");
	
			setRecordIntoSessionList(request, searchObj);
			
			return mav;

		}
		
		
		
		public void setRecordIntoSessionList(HttpServletRequest request,StudentDetainSearchVO searchObj){
			String[] nameValues  = request.getParameterValues("nameDropList");			
			
			String[] remarkValues  = request.getParameterValues("remarkForStuDetain");
			StudentDetainListVO stuDetainList = new StudentDetainListVO();
			List<StudentDetainListVO> stuList = (List<StudentDetainListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");						
			if((nameValues!=null)&&(remarkValues!=null)){
				stuDetainList.setAcademicYear(searchObj.getAcademicYear());
				stuDetainList.setStuGrpId(searchObj.getStuGrpId());
				stuDetainList.setStuAdmisNo(nameValues[nameValues.length-1]);
				stuDetainList.setDetainRemarks(remarkValues[remarkValues.length-1]);
				stuDetainList.setNewRecMoved("Y");			
				if((stuList==null)){					
					stuList= new ArrayList<StudentDetainListVO>();
					stuDetainList.setRowid(1);
					
				}else if (stuList!=null){
									
					stuDetainList.setRowid(stuList.size()+1);
					
				}
				stuList.add(stuDetainList);
				WebUtils
				.setSessionAttribute(request, "display_tbl",stuList);
			}
			
			if((request.getParameter("addRow")!=null)&&(request.getParameter("addRow").trim().equals("Add"))){				
				WebUtils.setSessionAttribute(request, "addAction",
						"AddAction");
				Map<String,String> mapOfNames = (Map<String,String>) WebUtils.getSessionAttribute(request, "display_tbl1");				
				if(stuList!=null){
					WebUtils.setSessionAttribute(request, "listSize",
							stuList.size());	
				}else{
					WebUtils.setSessionAttribute(request, "listSize",
							0);	
				}
								
			}else{																			
				WebUtils.setSessionAttribute(request, "addAction",
						null);
				WebUtils.setSessionAttribute(request, "listSize",
						null);
			}
		}
						
		
		@ModelAttribute("studentGrpList")
		public Map<String, String> gerStuGrpList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			Map<String, String> map = new LinkedHashMap<String,String>();
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			try {
				
				List<StudentGroupMaster> listOfStuGrpMas = dropDownListService.getStudentGroupListTag(sessionCache);
				for(StudentGroupMaster  stuGrpMas:listOfStuGrpMas){
					map.put(stuGrpMas.getStudentGrpId(), stuGrpMas.getStudentGrp());
				
				}			
					
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Student Group Dropdown :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("studentGrpList", map);
			return map;

		}
		
		
		@ExceptionHandler({ NoDataFoundException.class,
			DatabaseException.class, DuplicateEntryException.class, NoDataFoundException.class, BatchUpdateFailedException.class, 
			SectionNotAllocatedException.class, UpdateFailedException.class, DeleteFailedException.class, DuplicatesInList.class
			})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

			StudentDetainMasterVO stuDetailsVO = (StudentDetainMasterVO) request
				.getAttribute("stuDetain");		
			WebUtils
			.setSessionAttribute(request, "display_tbl",null);
		ModelAndView mav = new ModelAndView(".jaw.stuDetain",
				"stuDetain", stuDetailsVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
		
			
}

