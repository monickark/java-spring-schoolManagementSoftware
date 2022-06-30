package com.jaw.batch.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jaw.batch.service.IBatchFileUploadService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Controller
public class BatchFileUploadController {
	Logger logger = Logger.getLogger(BatchFileUploadController.class);
	@Autowired
	IBatchFileUploadService batchFileUploadService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IDropDownListService dropDownListService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String displayForm(
			@ModelAttribute("fileUploadForm") BatchFileUploadVO fileUploadForm) {
		logger.info("Going to render Batch File Upload Page");
		return ".jaw.importFiles";
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

	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String save(
			@ModelAttribute("fileUploadForm") BatchFileUploadVO fileUploadForm,
			HttpSession session, RedirectAttributes redirect)
			throws DatabaseException, ErrorDescriptionNotFoundException, IllegalStateException, IOException, FileNotSaveException {
		logger.info("In Controller,going to upload the file(s)");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String batchSerialNo = null;
		fileUploadForm.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		CommonsMultipartFile[] files = fileUploadForm.getFiles();
		if (files != null && files.length != 0) {
			batchSerialNo = batchFileUploadService.batchFileInsert(
					fileUploadForm, sessionCache.getUserSessionDetails(),applicationCache,"STU",session.getServletContext());						
		}
		redirect.addFlashAttribute(
				"success",
				ErrorCodeConstant.BATCH_INITIATE
						+ ": "
						+ "Batch Id :"
						+ batchSerialNo
						+ " "
						+ errorCodeUtil.getErrorDescription(applicationCache,
								ErrorCodeConstant.BATCH_INITIATE));
		return "redirect:/upload.htm";
	}

}
