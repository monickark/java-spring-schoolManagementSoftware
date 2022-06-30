package com.jaw.batch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.batch.controller.BatchDataUploadVO;
import com.jaw.common.batch.util.ExcelValidation.ClassNames;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.ITransportDetailsListDao;
import com.jaw.core.dao.TransportDetails;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.CommunicationDetails;
import com.jaw.student.admission.dao.ICommunicationDetailsListDao;
import com.jaw.student.admission.dao.IParentDetailsListDao;
import com.jaw.student.admission.dao.IPrevAcademicDetailsList;
import com.jaw.student.admission.dao.ISiblingDetailsListDao;
import com.jaw.student.admission.dao.IStudentInfoListDao;
import com.jaw.student.admission.dao.IStudentMasterListDAO;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.PrevAcademicDetails;
import com.jaw.student.admission.dao.SiblingDetails;
import com.jaw.student.admission.dao.StudentInfo;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.user.dao.IUserLinkListDao;
import com.jaw.user.dao.IUserListDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
@Component
public class BatchInsertHelper {
	Logger logger = Logger.getLogger(BatchInsertHelper.class);
	@Autowired
	CommonBusiness commonBusiness;	
	@Autowired
	IStudentInfoListDao studentInfoListDao;
	@Autowired
	IPrevAcademicDetailsList prevAcademicDetailsListDao;
	@Autowired
	IParentDetailsListDao parentDetailsListDao;
	@Autowired
	ISiblingDetailsListDao siblingDetailsListDao;
	@Autowired
	ICommunicationDetailsListDao communicationDetailsListDao;
	@Autowired
	ITransportDetailsListDao transportDetailsListDao;
	@Autowired
	ISiblingDetailsListDao siblingListDao;
	@Autowired
	DoAudit doAudit;
	@Autowired
	IStudentMasterListDAO studentMasListDao;	
	@Autowired
	IUserLinkListDao userLinkListDao;
	@Autowired
	IUserListDao userListDao;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	DateUtil dateUtil;

	
	@Transactional(rollbackFor = Exception.class)
	public void dataBaseInsertCall(
			List<Map<String, ArrayList<Object>>> listOfObjectMaps,			
			BatchDataUploadVO importDataVO,
			UserSessionDetails userSessionDetails,
			String batchSerialNo,ApplicationCache applicationCache) throws RuntimeExceptionForBatch, DataIntegrityException, DuplicateEntryException, DatabaseException, NumberFormatException, PropertyNotFoundException {
		
		String remarks = null;	
		String auditConstant = null;	
			
		for (int index = 0; index < listOfObjectMaps.size(); index++) {
			Map<String, ArrayList<Object>> objectMap = listOfObjectMaps
					.get(index);		
			for(Object classNameFromMap :objectMap.keySet()){			
				ClassNames className = ClassNames
						.valueOf(classNameFromMap.toString());				
				switch (className) {
				
				case StudentMasterVO: {
					List<StudentMaster> studentMasterListVo = new ArrayList<StudentMaster>();
					for (Object studentMas : objectMap
							.get(className.toString())) {

						StudentMaster studentmasterNew = new StudentMaster();
						commonBusiness.changeObject(studentmasterNew,
								studentMas);
						studentmasterNew.setDbTs(1);
						studentmasterNew
								.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						studentmasterNew
								.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
							studentmasterNew
									.setInstId(importDataVO.getInstId());						
							
							studentmasterNew.setBranchId(importDataVO
									.getBranchId());						
						studentMasterListVo.add(studentmasterNew);
					}
					studentMasListDao
							.insertStudentMasterList(studentMasterListVo);
					break;
				}
				case StudentInfoVO: {
					List<StudentInfo> studentInfoListVO = new ArrayList<StudentInfo>();
					for (Object studentInfo : objectMap.get(className
							.toString())) {
						StudentInfo studentInfoNew = new StudentInfo();
						commonBusiness
								.changeObject(studentInfoNew, studentInfo);
						studentInfoNew.setDbTs(1);
						studentInfoNew
								.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						studentInfoNew
								.setrModId(BatchConstants.BATCH_UPLD_USER_ID);					
							studentInfoNew.setInstId(importDataVO.getInstId());						
							studentInfoNew.setBranchId(importDataVO
									.getBranchId());						
						studentInfoListVO.add(studentInfoNew);
					}
					studentInfoListDao.insertStudentInfoList(studentInfoListVO);
					break;
				}
				case PrevAcademicDetailsVO: {
					List<PrevAcademicDetails> prevAcademicDetails = new ArrayList<PrevAcademicDetails>();
					for (Object prevAcaVO : objectMap.get(className.toString())) {
						PrevAcademicDetails prevAca = new PrevAcademicDetails();
						commonBusiness.changeObject(prevAca, prevAcaVO);
						prevAca.setDbTs(1);
						prevAca.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						prevAca.setrModId(BatchConstants.BATCH_UPLD_USER_ID);					
							prevAca.setInstId(importDataVO.getInstId());											
							prevAca.setBranchId(importDataVO.getBranchId());						
						prevAcademicDetails.add(prevAca);
					}
					prevAcademicDetailsListDao
							.insertPrevAcademicDetailsList(prevAcademicDetails);

					break;
				}
				case ParentDetailsVO: {
					List<ParentDetails> parentDetails = new ArrayList<ParentDetails>();
					for (Object parentVO : objectMap.get(className.toString())) {
						ParentDetails parentDetail = new ParentDetails();
						commonBusiness.changeObject(parentDetail, parentVO);
						parentDetail.setDbTs(1);
						parentDetail
								.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						parentDetail
								.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
							parentDetail.setInstId(importDataVO.getInstId());						
						
							parentDetail
									.setBranchId(importDataVO.getBranchId());						
						parentDetails.add(parentDetail);
					}
					parentDetailsListDao.insertParentDetailsList(parentDetails);
					break;
				}
				case CommunicationDetailsVO: {
					List<CommunicationDetails> coomunicationDetails = new ArrayList<CommunicationDetails>();
					for (Object commVO : objectMap.get(className.toString())) {
						CommunicationDetails commDetail = new CommunicationDetails();
						commonBusiness.changeObject(commDetail, commVO);
						commDetail.setDbTs(1);
						commDetail.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						commDetail.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
							commDetail.setInstId(importDataVO.getInstId());											
							commDetail.setBranchId(importDataVO.getBranchId());						
						coomunicationDetails.add(commDetail);
					}
					communicationDetailsListDao
							.insertCommunicationDetailsList(coomunicationDetails);
					break;
				}
				case SiblingDetailsVO: {
					List<SiblingDetails> siblingDetails = new ArrayList<SiblingDetails>();
					for (Object sibVO : objectMap.get(className.toString())) {
						SiblingDetails sibDetail = new SiblingDetails();
						commonBusiness.changeObject(sibDetail, sibVO);
						sibDetail.setDbTs(1);
						sibDetail.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						sibDetail.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
						sibDetail.setInstId(importDataVO.getInstId());											
						sibDetail.setBranchId(importDataVO.getBranchId());						
						siblingDetails.add(sibDetail);
					}
					siblingListDao.insertSiblingDetailsList(siblingDetails);						
					break;
				}
				case TransportDetailsVO: {
					List<TransportDetails> transportDetails = new ArrayList<TransportDetails>();
					for (Object transVO : objectMap.get(className.toString())) {
						TransportDetails transDetail = new TransportDetails();
						commonBusiness.changeObject(transDetail, transVO);
						transDetail.setDbTs(1);
						transDetail.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						transDetail.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
						transDetail.setInstId(importDataVO.getInstId());											
						transDetail.setBranchId(importDataVO.getBranchId());						
						transportDetails.add(transDetail);
					}
					transportDetailsListDao.insertTransportDetailsList(transportDetails);						
					break;
				}
				
				case UserLink: {
					
					
					List<UserLink> userLinkList = new ArrayList<UserLink>();				
					logger.info("Start inserting into UserLinking table");															
					for (Object userLink : objectMap.get(className.toString())) {
						UserLink userLinkObj = new UserLink();
						commonBusiness.changeObject(userLinkObj, userLink);
						userLinkObj.setDb_ts(1);
						userLinkObj.setDeleteFlag("N");
						userLinkObj.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						userLinkObj.setrModId(BatchConstants.BATCH_UPLD_USER_ID);						
						userLinkObj.setInstId(importDataVO.getInstId());											
						userLinkObj.setBranchId(importDataVO.getBranchId());		
						userLinkList.add(userLinkObj);
					}
					userLinkListDao.insertListOfUserLinkRec(userLinkList);
					
					break;
				}
				case UserVO: {
					List<User> userList = new ArrayList<User>();
					for (Object usrObj : objectMap.get(className.toString())) {
						User user = new User();
						commonBusiness.changeObject(user, usrObj);
						String[] userAndPassword = new String[2];
						userAndPassword[0] = user.getUserId();
						userAndPassword[1] = user.getPassword();
						user.setDb_ts(1);
						user.setInstId(importDataVO.getInstId());
						user.setBranchId(importDataVO.getBranchId());
						user.setPassword(commonBusiness
								.createPasswordForUser(userAndPassword[1]));
						int days = Integer.parseInt(propertyManagementUtil
								.getPropertyValue(applicationCache,
										userSessionDetails.getInstId(),
										userSessionDetails.getBranchId(),
										PropertyManagementConstant.PWD_EXPIRY_PRD)
								.toString());
						String expiryDate = dateUtil.addDaysToCurrentDate(days);						
						user.setPasswordExpiryDate(expiryDate);					
						user.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						user.setrModId(BatchConstants.BATCH_UPLD_USER_ID);
						user.setLastLogoutTime(ApplicationConstant.LAST_LOGOUT_TIME);
						user.setTotalNoOfLogin("0");
						user.setUserEnableFlag("Y");
						user.setDeleteFlag("N");
						user.setrModId(BatchConstants.BATCH_UPLD_USER_ID);
						user.setrCreId(BatchConstants.BATCH_UPLD_USER_ID);
						user.setPasswordResetFlag("Y");					
							userList.add(user);
					}
					userListDao.insertUserList(userList);
					break;
				}
				
				

				}
																		

			}
		}		
			 remarks = "Batch Program Name :"
						+ importDataVO.getBatchName() + "," + "Data type :"
						+ importDataVO.getDataType() + ","
						+ "Batch Serial No:" + batchSerialNo;	
			 auditConstant = AuditConstant.BATCH_PROGRAM_SUCCESS;
																	
		
			doAudit.doFunctionalAudit(userSessionDetails,auditConstant
					, remarks);
			logger.info("Completed Functional Auditing..");
					
		
	}
	
	
}
