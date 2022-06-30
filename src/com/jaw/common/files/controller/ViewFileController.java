package com.jaw.common.files.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Common Controller class for Viewing a file
@Controller
public class ViewFileController {

	// Logging
	Logger logger = Logger.getLogger(ViewFileController.class);

	@Autowired
	IFileMasterService fileMasterService;
	private static final int BUFFER_SIZE = 4096;

	// method to view the Images
	@RequestMapping(value = "image", method = RequestMethod.GET, params = {
			"instId", "branchId", "photo", "type" })
	public String displayImageIB(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String instId = httpSevletRequest.getParameter("instId");
		String branchid = httpSevletRequest.getParameter("branchId");
		String photo = httpSevletRequest.getParameter("photo");
		String type = httpSevletRequest.getParameter("type");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		UserSessionDetails userSessionDetails = sessionCache
				.getUserSessionDetails();
		userSessionDetails.setInstId(instId);
		userSessionDetails.setBranchId(branchid);
		return getImage(session,httpSevletRequest, response, model,
				sessionCache.getUserSessionDetails(), photo, type,ApplicationConstant.DEFAULT_FILE_SRL_NO);
	}

	// method to view the Images
	@RequestMapping(value = "image", method = RequestMethod.GET, params = {
			"photo", "type" })
	public String displayImage(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String photo = httpSevletRequest.getParameter("photo");
		if(photo==null||photo.equals("")){
			photo=sessionCache.getUserSessionDetails().getLinkId();		
		}
		String type = httpSevletRequest.getParameter("type");
		
		return getImage(session,httpSevletRequest, response, model,
				sessionCache.getUserSessionDetails(), photo, type,ApplicationConstant.DEFAULT_FILE_SRL_NO);
	}
	
	
	
	
	// newly added
		@RequestMapping(value = "image", method = RequestMethod.GET, params = {
				"photo", "type" ,"srlno"})
		public String displayImageBySrlNo(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			String photo = httpSevletRequest.getParameter("photo");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			if(photo==null||photo.equals("")){
				photo=sessionCache.getUserSessionDetails().getLinkId();			
			}
			String type = httpSevletRequest.getParameter("type");
			String srlNo = httpSevletRequest.getParameter("srlno");						
			return getImage(session,httpSevletRequest, response, model,
					sessionCache.getUserSessionDetails(), photo, type,srlNo);
		}

	private String getImage(HttpSession session,HttpServletRequest httpSevletRequest,
			HttpServletResponse response, ModelMap model,
			UserSessionDetails userSessionDetails, String id, String type,String srlNo)
			throws IOException {
		logger.info("inside the method to retrieve the image");
		InputStream img = null;
		OutputStream out;
		FileMaster file = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String realPath = httpSevletRequest.getSession().getServletContext()
				.getRealPath("/images/noimage.gif");
		try {
			file = fileMasterService.getFile(userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), id, type,srlNo,applicationCache,session.getServletContext());			
			img = file.getInputStream();
		} catch (FileNotFoundInDatabase e) {		
			img = new FileInputStream(realPath);
			
		}
		out = response.getOutputStream();
		response.setContentType("image/gif");
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		// write bytes read from the input stream into the output stream
		while ((bytesRead = img.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
		}
		img.close();
		out.close();
		return null;

	}

	// From user Controller
	@RequestMapping(value = "/userimage", method = RequestMethod.GET)
	public String userImage(HttpServletResponse response,
			HttpServletRequest request, HttpSession session)
			throws FileNotFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		InputStream img;
		byte[] userpho = sessionCache.getUserSessionDetails().getUserPhoto();
		String path = request.getSession().getServletContext()
				.getRealPath("/images/admin.jpg");

		try {
			if (userpho != null) {
				img = new ByteArrayInputStream(userpho);
			} else {
				img = new FileInputStream(path);
			}
			OutputStream out = response.getOutputStream();
			response.setContentType("image/jpeg");
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			// write bytes read from the input stream into the output stream
			while ((bytesRead = img.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			img.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
