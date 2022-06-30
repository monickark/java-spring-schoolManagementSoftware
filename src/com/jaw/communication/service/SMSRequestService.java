package com.jaw.communication.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.sms.SMSHelper;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.communication.controller.SMSDetailsVO;
import com.jaw.communication.controller.SMSHistoryMasterVO;
import com.jaw.communication.controller.SMSRequestListVO;
import com.jaw.communication.controller.SMSRequestVO;
import com.jaw.communication.dao.Alert;
import com.jaw.communication.dao.IAlertDAO;
import com.jaw.communication.dao.IAlertListDAO;
import com.jaw.communication.dao.ISMSDetailsListDAO;
import com.jaw.communication.dao.ISMSListDAO;
import com.jaw.communication.dao.ISMSRequestDAO;
import com.jaw.communication.dao.ISMSRequestListDAO;
import com.jaw.communication.dao.SMSDetails;
import com.jaw.communication.dao.SMSDetailsKey;
import com.jaw.communication.dao.SMSListKey;
import com.jaw.communication.dao.SMSMemberList;
import com.jaw.communication.dao.SMSRequest;
import com.jaw.communication.dao.SMSRequestListKey;
import com.jaw.core.controller.SubjectMasterVO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.SubjectMaster;
import com.jaw.core.dao.SubjectMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class SMSRequestService implements ISMSRequestService {
	// Logging
	Logger logger = Logger.getLogger(SMSRequestService.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ISMSListDAO smsListDAO;
	@Autowired
	ISMSRequestDAO smsRqstDAO;
	@Autowired
	ISMSDetailsListDAO smsDetailsListDAO;
	@Autowired 
	IDropDownListService dropDownListService;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	SMSHelper smsRqstHelper;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	ISMSRequestListDAO smsRequestListDAO;
	@Override
	public Map<String, String> getStudentGroupList(SessionCache sessionCache) {
		List<StudentGroupMaster> stuGrpMtrList=null;
		try {
			stuGrpMtrList=dropDownListService.getStudentGroupListTag(sessionCache, "");
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,String> stuGrpList=new LinkedHashMap<String,String>();
		for(StudentGroupMaster studentGrpMtr:stuGrpMtrList){
			stuGrpList.put(studentGrpMtr.getStudentGrpId(), studentGrpMtr.getStudentGrp());
		}
		return stuGrpList;
	}
	@Override
	public Map<String, String> getDepartementList(
			UserSessionDetails userSessiondetails) {
		Map<String, String> depts=null;
		try {
			depts= dropDownListService.getDepartementList(userSessiondetails);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return depts;
	}
	@Override
	public List<SMSMemberList> getSpecificMemberList(
			UserSessionDetails userSessiondetails, String genGroup,
			String speGroup) throws NoDataFoundException {
		List<SMSMemberList> smsMebList=null;
		SMSListKey smsListKey=new SMSListKey();
		smsListKey.setInstId(userSessiondetails.getInstId());
		smsListKey.setBranchId(userSessiondetails.getBranchId());
		smsListKey.setAcTerm(userSessiondetails.getCurrAcTerm());		
		System.out.println("specccccccccccccc"+speGroup);
		System.out.println("specccccccccccccc"+genGroup);
		smsListKey.setGenCategory(ApplicationConstant.SPECIFIC_MEMBER_LIST);
		if(genGroup.equals(ApplicationConstant.GENERIC_GRP_STUDENT)){
			smsListKey.setStudentGrpId(speGroup);
			smsMebList=smsListDAO.getMemberListForStudent(smsListKey,0);
		}else if(genGroup.equals(ApplicationConstant.GENERIC_GRP_PARENT)){
			smsListKey.setStudentGrpId(speGroup);
			smsMebList=smsListDAO.getMemberListForParent(smsListKey,0);
		}else{
			smsListKey.setDepartmentId(speGroup);
			smsMebList=smsListDAO.getMemberListForStaff(smsListKey,0);
		}
		
		return smsMebList;
	}
	@Override
	public void saveSMSRequest(UserSessionDetails userSessiondetails,
			SMSRequestVO smsRequestVo, String[] specificMembList,ApplicationCache applicationCache)
			throws  DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException {
		logger.debug("inside insert SMS Request Service method");
		SMSRequest smsRequest=new SMSRequest();
		// map the UIObject to Pojo
		commonBusiness.changeObject(smsRequest, smsRequestVo);
		smsRequest.setDbTs(1);
		smsRequest.setInstId(userSessiondetails.getInstId());
		smsRequest.setBranchId(userSessiondetails.getBranchId());
		smsRequest.setrCreId(userSessiondetails.getUserId());
		smsRequest.setrModId(userSessiondetails.getUserId());
		smsRequest.setDelFlag("N");
		smsRequest.setReqstStatus(ApplicationConstant.SMS_RQST_ENTERED);
		smsRequest.setAcTerm(userSessiondetails.getCurrAcTerm());
		smsRequest.setSmsReqstId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_SMS_RQST_SEQ, simpleIdGenerator
				.getNextId(SequenceConstant.SMS_REQUEST_SEQUENCE,
						1)));
		smsRequest.setReqstType(CommonCodeConstant.SMS_RQST);
		if(smsRequest.getSpecificGrpList()==null){
			smsRequest.setSpecificGrpList("");
		}
		String spMemList="";
		
		if((smsRequest.getReqstCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST))&&(specificMembList!=null)){			
			for(int i=0;i<specificMembList.length;i++){
				
				if(i==0){
					spMemList=specificMembList[i];
				}else{
					spMemList+=ApplicationConstant.COMMA_SEPERATOR+specificMembList[i];
				}
			}
			smsRequest.setSpecificMembrList(spMemList);
		}	
		
		smsRqstDAO.saveSMSRequestRecord(smsRequest);
		// functional audit
		doAudit.doFunctionalAudit(userSessiondetails,
				AuditConstant.SMS_RQST_INSERT, " ");
		logger.debug("Completed Functional Auditing");
		if(smsRequest.getReqstCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)){
			 List<SMSMemberList> smsNumList=new ArrayList<SMSMemberList>();
			 for(int i=0;i<specificMembList.length;i++){
				 SMSMemberList smsMembrList=new SMSMemberList();
				 smsMembrList.setMobileNum(specificMembList[i]);
				 smsNumList.add(smsMembrList);
			 }
			 if((specificMembList.length>Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS))){
				 System.out.println("greater than 70 member");
			 }else{
		//	smsRqstHelper.insertSMSDetailsRecord(smsRequest, smsNumList,userSessiondetails,1, applicationCache);
			 }
			
		}else{
			/*try {
			smsRqstHelper.batchInsertSMSDetailsRecord(smsRequest, userSessiondetails,applicationCache);
		    } catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/}
	}
	@Override
	public SMSHistoryMasterVO selectSMSRequestList(
			SMSHistoryMasterVO smsHistoryMasterVO,
			UserSessionDetails usersessiondetails) throws NoDataFoundException {
		SMSRequestListKey smsRequestListKey = new SMSRequestListKey();
		// map the UIObject to Pojo		
		commonBusiness.changeObject(smsRequestListKey, smsHistoryMasterVO.getSmsHistorySearchVO());
		smsRequestListKey.setInstId(usersessiondetails.getInstId());
		smsRequestListKey.setBranchId(usersessiondetails.getBranchId());
		List<SMSRequest> smsRqstList = smsRequestListDAO.getSMSRequestList(smsRequestListKey);
		List<SMSRequestListVO> smsRqstListVOs = new ArrayList<SMSRequestListVO>();

		for (int i = 0; i < smsRqstList.size(); i++) {
			SMSRequest smsRequest = smsRqstList.get(i);
			SMSRequestListVO smsRequestListVO = new SMSRequestListVO();
			commonBusiness.changeObject(smsRequestListVO, smsRequest);
			smsRequestListVO.setRowId(i);
			smsRqstListVOs.add(smsRequestListVO);
		}
		smsHistoryMasterVO.setSmsRequestListVOs(smsRqstListVOs);
		return smsHistoryMasterVO;
	}
	@Override
	public SMSHistoryMasterVO selectSMSDetailsList(
			SMSHistoryMasterVO smsHistoryMasterVO,
			UserSessionDetails usersessiondetails) throws NoDataFoundException {
		SMSDetailsKey smsDetailsKey = new SMSDetailsKey();
		// map the UIObject to Pojo		
		commonBusiness.changeObject(smsDetailsKey,smsHistoryMasterVO.getSmsReqstListVO());
		smsDetailsKey.setInstId(usersessiondetails.getInstId());
		List<SMSDetails> smsDetailsList = smsDetailsListDAO.getSMSDetailsList(smsDetailsKey);
		List<SMSDetailsVO> smsDetailsVOList = new ArrayList<SMSDetailsVO>();

		for (int i = 0; i < smsDetailsList.size(); i++) {
			SMSDetails smsDetails = smsDetailsList.get(i);
			SMSDetailsVO smsDetailsVO = new SMSDetailsVO();
			commonBusiness.changeObject(smsDetailsVO, smsDetails);
			smsDetailsVO.setRowId(i);
			smsDetailsVOList.add(smsDetailsVO);
		}
		smsHistoryMasterVO.setSmsDetailsListVOs(smsDetailsVOList);
		return smsHistoryMasterVO;
	}

}
