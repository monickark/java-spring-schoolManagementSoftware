package com.jaw.batch.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
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
import com.jaw.batch.service.IDownloadFileService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class DownLoadFileController {
	Logger logger = Logger.getLogger(DownLoadFileController.class);
	@Autowired
	IDownloadFileService downLoadService;
	@Autowired
	IDropDownListService dropDownListService;

	@RequestMapping(value = "/downLoadFile", method = RequestMethod.GET)
	public ModelAndView renderDownloadFilePage(
			@ModelAttribute("fileDownLoad") DownLoadFileVO fileDownLoadVO,HttpServletResponse response,HttpServletRequest request,HttpSession session) throws NoDataFoundException, IOException {
		logger.info("Going to Render Export Files Page");
		ModelAndView model = new ModelAndView(".jaw.exportFiles");
		if ((request.getParameter("ImportFileBttn") != null)
				&& (request.getParameter("ImportFileBttn").equals("Download"))) {
			logger.info("Going to get the files");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			if (fileDownLoadVO.getBranchId().equals("Institute Specified")) {
				fileDownLoadVO.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
			}
			
			request.setAttribute("fileDownLoad", fileDownLoadVO);
			//Have to change the Profile grp..
			List<DownLoadFileVO> fil = downLoadService.downloadFile(fileDownLoadVO,
					sessionCache.getUserSessionDetails(),applicationCache,session.getServletContext(),"STU");
			ServletOutputStream sos = response.getOutputStream();
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition",
					"attachment; filename=\"DOWNLOAD_FILE(S).ZIP\"");
			createZipFile(fil,sos);			
		}
		return model;
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

	public void createZipFile(List<DownLoadFileVO> fil,ServletOutputStream sos) throws IOException{
		byte[] buf = new byte[8192];
		// Create the ZIP file
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(baos);
		// Compress the files
		for (int i = 0; i < fil.size(); i++) {
			BufferedInputStream bis = new BufferedInputStream(fil.get(i)
					.getInputStream());
			// Add ZIP entry to output stream.
			String contentType = fil.get(i).getContentType();
			String[] extn = contentType.split("/");
			String entryName = fil.get(i).getLinkId().toString() + "."
					+ extn[1];
			entryName = entryName.replace("/", "^");
			FileType fileType  = FileType.valueOf(fil.get(i).getFileType());
			String fileName = null;
			switch(fileType){
			case FL_BIRT:{
				fileName = "BIRTH_CERTIFICATE";
				break;
			}
			case FL_CAST:{
				fileName = "CASTE_CERTIFICATE";
				break;
			}
			case FL_MARK:{
				fileName = "MARK_CERTIFICATE";
				break;
			}
			case FL_MEDI:{
				fileName = "MEDICAL_CERTIFICATE";
				break;
			}
			case FL_SPCR:{
				fileName = "SPORTS_CERTIFICATE";
				break;
			}
			case FL_STBD:{
				fileName = "STAFF_BIODATA";
				break;
			}
			case FL_STCR:{
				fileName = "STAFF_CERTIFICATE";
				break;
			}
			case FL_TRAN:{
				fileName = "TRANSFER_CERTIFICATE";
				break;
			}
			case PH_APHO:{
				fileName = "TR_ASSISTANT_PHOTO";
				break;	
			}				
			case PH_FPHO:{
				fileName = "FATHER_PHOTO";
				break;
			}
			case PH_GPHO:{
				fileName = "GUARDIAN_PHOTO";
				break;
			}
			case PH_LOGO:{
				fileName = "LOGO";
				break;
			}
			case PH_MGMT:{
				fileName = "MGMT_PHOTO";
				break;
			}
			case PH_MPHO:{
				fileName = "MOTHER_PHOTO";
				break;
			}
			case PH_NSTA:{
				fileName = "NON_STF_PHOTO";
				break;
			}
			case PH_SPHO:{
				fileName = "STU_PHOTO";
				break;
			}
			case PH_STA:{
				fileName = "STF_PHOTO";
				break;
			}
			default:
				break;
			
			}
			
			out.putNextEntry(new ZipEntry(fileName+"/"+entryName));
			int bytesRead;
			while ((bytesRead = bis.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
			out.closeEntry();
			bis.close();
			
			out.flush();
			baos.flush();
			out.close();
			baos.close();				
			
			sos.write(baos.toByteArray());
			out.flush();
			out.close();
			sos.flush();
			fil.get(i).getInputStream().close();
		}
	
		//return null;

		
	}
	
	enum FileType{
		PH_LOGO,PH_SPHO,FL_BIRT,FL_CAST,PH_FPHO,PH_MPHO,
		PH_GPHO, FL_TRAN,FL_MARK,PH_APHO,PH_MGMT,PH_NSTA,		
		PH_STA,FL_MEDI,FL_SPCR,FL_STCR,FL_STBD;
	}
	
	@ExceptionHandler({ NoDataFoundException.class,IOException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		DownLoadFileVO fileDownLoadVO = (DownLoadFileVO) request
				.getAttribute("fileDownLoad");
		ModelAndView mav = new ModelAndView(".jaw.exportFiles", "fileDownLoad",
				fileDownLoadVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;
	}

}
