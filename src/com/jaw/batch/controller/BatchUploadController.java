package com.jaw.batch.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.jaw.batch.service.IFileUploadService;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.ExcelRejectException;
import com.jaw.common.exceptions.batch.ExtensionNotMatchingException;
import com.jaw.common.exceptions.batch.InValidCellValue;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.ErrorCodeUtil;

@Controller
// Controller class for BatchUpload
public class BatchUploadController {
	Logger logger = Logger.getLogger(BatchUploadController.class);
	@Autowired
	private IFileUploadService fileUploadService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IDropDownListService dropDownListService;

	// Method to render the Export Data Page
	@RequestMapping(value = "/batchImport", method = RequestMethod.GET)
	public ModelAndView loadUploadPage(
			@ModelAttribute("importData") BatchDataUploadVO exportDataVO)
			throws ParserConfigurationException, SAXException, IOException {
		
		logger.info("Excel file update page is opening");
		ModelAndView mav = new ModelAndView(".jaw.importData");
		return mav;
	}

	// Method to upload the the Excel data
	@RequestMapping(value = "/batchImport", method = RequestMethod.POST)
	public ModelAndView fileInsert(
			@ModelAttribute("importData") BatchDataUploadVO importtDataVO,
			HttpSession session,HttpServletRequest request) throws ParserConfigurationException,
			SAXException, IOException, IllegalAccessException,
			InvocationTargetException, DataIntegrityException,
			DuplicateEntryException, DatabaseException, NoRecordFoundException,
			ExcelRejectException, NoDataFoundException, ExtensionNotMatchingException, ErrorDescriptionNotFoundException, FileNotSaveException {	
		
		logger.info("Inside the method to upload excel data");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);		
		String serialNo = null;
		request.setAttribute( "importData",importtDataVO);
		
		try {
			
			String ext = FilenameUtils.getExtension(importtDataVO
					.getUploadFile().getOriginalFilename());			
			if(ext.equalsIgnoreCase(BatchConstants.BATCH_EXCEL_FORMATS_1)||ext.equalsIgnoreCase(BatchConstants.BATCH_EXCEL_FORMATS_2)){
				serialNo = fileUploadService.fileupload(ext, importtDataVO,
						applicationCache, session.getServletContext(),
						sessionCache.getUserSessionDetails(),
						importtDataVO.getDataType());
			}else{
				throw new ExtensionNotMatchingException();
			}
			
		} catch (InValidCellValue e) {
			e.printStackTrace();
		}
		return new ModelAndView(".jaw.importData").addObject(
				"success",
				ErrorCodeConstant.BATCH_INITIATE
						+ ": "
						+ "Batch Id :"
						+ serialNo
						+ " "
						+ errorCodeUtil.getErrorDescription(applicationCache,
								ErrorCodeConstant.BATCH_INITIATE));
	}
	
	
	@ModelAttribute("branchList")
	public Map<String, String> gerBranchList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
			SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService.getBranchListTag(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("branchList", map);
		return map;

	}


	// Method to handle the user-defined exceptions
	@ExceptionHandler({ DataIntegrityException.class,
			DuplicateEntryException.class, NoRecordFoundException.class,ExtensionNotMatchingException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		
		logger.info("Inside handle exception method to handle the user-defined exceptions");		
		BatchDataUploadVO importData = (BatchDataUploadVO) request.getAttribute("importData");
		ModelAndView mav = new ModelAndView(".jaw.importData", "importData",
				importData);		
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
