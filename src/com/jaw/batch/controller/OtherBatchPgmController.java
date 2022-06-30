package com.jaw.batch.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jaw.batch.service.IOtherBatchService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
@Controller
public class OtherBatchPgmController {
	
	Logger logger = Logger.getLogger(OtherBatchPgmController.class);
	@Autowired
	IOtherBatchService otherBatchService;
	
	
	
	@RequestMapping(value = "/batchRun", method = RequestMethod.GET)
	public String runBatch() {
		logger.info("Going to render Batch File Upload Page");
		return ".jaw.runBatch";
	}
	
	

	@RequestMapping(value = "/batchRun", method = RequestMethod.GET, params = {
			"UpdateDB"
		})
	public ModelAndView updateTheColumn(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException, BatchUpdateFailedException {
				
			otherBatchService.updatePsdeTableColumns();
	
			return new ModelAndView(".jaw.runBatch");
	}

	
		



}
