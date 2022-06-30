package com.jaw.student.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.IParentDetails;
import com.jaw.student.admission.dao.IParentDetailsListDao;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.IStudentTransferDao;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.ParentDetailsKey;
import com.jaw.student.admission.dao.StuTranKey;

import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.controller.StuTranSearchVO;
import com.jaw.student.controller.StuTranVO;
import com.jaw.student.controller.StudentTransferController;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.IUserLinkListDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
import com.jaw.user.dao.UserLinkKey;
@Service
public class StuTranService implements IStuTranService {
	
	// LoggingIParentDetails
			Logger logger = Logger.getLogger(StuTranService.class);
			@Autowired 
			IUserLinkDao userLinkDao;
			@Autowired
			IUserDao userDao;
			@Autowired
			IParentDetailsListDao parentDetails;
			@Autowired
			IUserLinkListDao userLinkListDao;
	
	@Autowired
	IStudentTransferDao studentTransferDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentMasterListDAO studentMasListDao;
	
	@Override
	public String getStuForTransfer(UserSessionDetails userSession,StuTranSearchVO stuTranSearchVO) {
		StuTranKey stuTranKey = new StuTranKey();
		commonBusiness.changeObject(stuTranKey, stuTranSearchVO);
		stuTranKey.setInstId(userSession.getInstId());
		stuTranKey.setBranchId(userSession.getBranchId());
		String feeDue = "";
		try {
			feeDue = studentTransferDao.feeDueCheckForStuTan(stuTranKey);			
		} catch (NoDataFoundException e) {
			feeDue = "";
		}
		
		return feeDue;
	}

	
	@Transactional
	@Override
	public void studentTransfer(StuTranSearchVO stuTranSearchVO,
			StuTranVO stuTranVO,UserSessionDetails userSession,ApplicationCache applicationCache) throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		StuTranKey stuTranKey = new StuTranKey();
		commonBusiness.changeObject(stuTranKey, stuTranSearchVO);
		stuTranKey.setInstId(userSession.getInstId());
		stuTranKey.setBranchId(userSession.getBranchId());
		StudentMaster stuMaster = studentTransferDao.getStuTransferRec(stuTranKey);
		StudentMaster oldRec = new StudentMaster();
		commonBusiness.changeObject(oldRec, stuMaster);
		stuMaster.setTransfered(ApplicationConstant.STU_TRANSFER_CONST);
		stuMaster.setTransferDate(stuTranVO.getTransferDate());
		stuMaster.setDelFlg(ApplicationConstant.STU_TRANSFER_DEL_FLG);
		stuMaster.setrModId(userSession.getUserId());
		
		studentTransferDao.stuTransfer(stuTranKey, stuMaster);
		
		ParentDetailsKey parentDetailsKey = new ParentDetailsKey();
		parentDetailsKey.setInstId(userSession.getInstId());
		parentDetailsKey.setBranchId(userSession.getBranchId());
		parentDetailsKey.setStudentAdmisNo(stuMaster.getStudentAdmisNo());
		
		List<ParentDetails> listOfParentDetails = parentDetails.retriveParentDetailsListFromStuAdmisNo(parentDetailsKey);
		
		if(listOfParentDetails.size()>1){
			UserLinkKey userLinkKey = new UserLinkKey();
			userLinkKey.setInstId(userSession.getInstId());
			userLinkKey.setLinkId(stuMaster.getStudentAdmisNo());
			List<UserLink> userLinkList = userLinkListDao.getListOfUserLinkRecords(userLinkKey);
			
		}else{
			disablingUserDetails( stuTranSearchVO,
					 stuTranVO, userSession, applicationCache);
		}
		
	
		
		
		// functional audit
				doAudit.doFunctionalAudit(userSession,
						AuditConstant.STUDENT_TRAN_SUCCESS,"Transfer Date :"+stuTranVO.getTransferDate()+",Admis No :"+stuTranSearchVO.getStuAdmisNo());
				logger.debug("Completed Functional Auditing");
			
		
		
	}
	
	public void disablingUserDetails(StuTranSearchVO stuTranSearchVO,
			StuTranVO stuTranVO,UserSessionDetails userSession,ApplicationCache applicationCache) throws UpdateFailedException{
		//Disabling the users
		UserLink link;
		try {
			link = userLinkDao.getUserDetailsByLinkId(stuTranSearchVO.getStuAdmisNo(), userSession
					.getInstId(), userSession.getBranchId());
			link.setDeleteFlag("Y");
			link.setrModId(userSession.getUserId());
			userLinkDao.updateUserLinkRec(link);
			User user = userDao.validateUserId(link.getUserId());
			user.setDeleteFlag("Y");
			user.setrModId(userSession.getUserId());
			userDao.updateUser(user);
		} catch (InvalidUserIdException e) {
		}
	}

	@Override
	public Map<String, String> getStudentNameMap(
			UserSessionDetails userSession, String acy, String stuGrpId) throws NoDataFoundException {
		Map<String,String> mapOfStuNames = studentMasListDao.getStuListForDetain(stuGrpId,acy,userSession.getInstId(),userSession.getBranchId());
		return mapOfStuNames;
	}
	
	

}
