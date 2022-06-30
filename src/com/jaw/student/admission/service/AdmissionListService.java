package com.jaw.student.admission.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionCountListVO;
import com.jaw.student.admission.controller.AdmissionListVO;
import com.jaw.student.admission.controller.AdmissionSearchVO;
import com.jaw.student.admission.controller.NewAdmissionDetailsVO;
import com.jaw.student.admission.dao.AdmissionCountList;
import com.jaw.student.admission.dao.AdmissionKey;
import com.jaw.student.admission.dao.AdmissionList;
import com.jaw.student.admission.dao.IAdmissionListDao;

@Service
public class AdmissionListService implements IAdmissionListService{

	Logger logger = Logger.getLogger(AdmissionListService.class);
	@Autowired
	IAdmissionListDao admissionListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	
	//Select New Admission List
	public NewAdmissionDetailsVO selectAdmissionList(NewAdmissionDetailsVO newAdmissionDetailsVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.debug("inside selectAdmissionList method");

		AdmissionKey admissionKey=new AdmissionKey();
		commonBusiness.changeObject(admissionKey, newAdmissionDetailsVO.getAdmissionSearchVO());
		admissionKey.setInstId(userSessionDetails.getInstId());
		admissionKey.setBranchId(userSessionDetails.getBranchId());
		List<AdmissionList> admissionLists=admissionListDao.selectAdmissionListDetails(admissionKey);
		System.out.println("Admission LIst"+admissionLists);
		List<AdmissionListVO> admissionListsVO= new ArrayList<AdmissionListVO>();
		int rowId = 0;
		for(AdmissionList admissionList:admissionLists){
			AdmissionListVO admissionListVO= new AdmissionListVO();
			commonBusiness.changeObject(admissionListVO, admissionList);
			admissionListVO.setRowid(rowId);
			admissionListsVO.add(admissionListVO);
			rowId++;
		}
		System.out.println("Admission LIst1"+admissionListsVO);
		newAdmissionDetailsVO.setAdmissionList(admissionListsVO);
		return newAdmissionDetailsVO;
		
	}
	
	/*//Select New Admission List Count
	public Map<String,Integer> selectAdmissionCountList(AdmissionSearchVO admissionSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.debug("inside selectAdmissionList method");
		AdmissionKey admissionKey=new AdmissionKey();
		commonBusiness.changeObject(admissionKey, admissionSearchVO);
		admissionKey.setInstId(userSessionDetails.getInstId());
		admissionKey.setBranchId(userSessionDetails.getBranchId());
		List<AdmissionCountList> admissionCountLists=admissionListDao.selectAdmissionCountListDetails(admissionKey);
		System.out.println("Admission Count LIst"+admissionCountLists);
		Map<String,Integer> admissionCount =new HashMap<String,Integer>();
		//List<AdmissionCountListVO> admissionCountListsVO= new ArrayList<AdmissionCountListVO>();
		for(AdmissionCountList admissionCountList:admissionCountLists){
			admissionCount.put(admissionCountList.getCourse(), admissionCountList.getCount());
			AdmissionCountListVO admissionCountListVO= new AdmissionCountListVO();
			commonBusiness.changeObject(admissionCountListVO, admissionCountList);
			admissionCountListsVO.add(admissionCountListVO);
		}
		
		System.out.println("Admission Count Map"+admissionCount);
		return admissionCount;
	}*/
	
	//Select New Admission List Count
	public List<AdmissionCountListVO> selectAdmissionCount(AdmissionSearchVO admissionSearchVO,
			UserSessionDetails userSessionDetails, HttpSession session) throws NoDataFoundException {
		logger.debug("inside selectAdmissionList method");
		AdmissionKey admissionKey=new AdmissionKey();
		commonBusiness.changeObject(admissionKey, admissionSearchVO);
		admissionKey.setInstId(userSessionDetails.getInstId());
		admissionKey.setBranchId(userSessionDetails.getBranchId());
		List<AdmissionCountList> admissionCountLists=admissionListDao.selectAdmissionCountListDetails(admissionKey);
		System.out.println("Admission Count LIst"+admissionCountLists);
		/*ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);*/
		List<AdmissionCountListVO> admissionCountListsVO= new ArrayList<AdmissionCountListVO>();
		for(AdmissionCountList admissionCountList:admissionCountLists){
			AdmissionCountListVO admissionCountListVO= new AdmissionCountListVO();
			commonBusiness.changeObject(admissionCountListVO, admissionCountList);
			//System.out.println("Course before cocd:"+admissionCountListVO.getCourse());
			//String course = commonCodeUtil.getDescriptionByCode(applicationCache, admissionCountListVO.getCourse());
			//admissionCountListVO.setCourse(course);
			//System.out.println("Course Descc:"+admissionCountListVO.getCourse());
			admissionCountListsVO.add(admissionCountListVO);
		}
		System.out.println("Admission LIst1"+admissionCountListsVO);
		
		return admissionCountListsVO;
		
	}
}
