package com.jaw.common.sms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.dao.ISMSConfigurationListDAO;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.SMSPropertyManagementUtil;
import com.jaw.communication.dao.ISMSDetailsDAO;
import com.jaw.communication.dao.ISMSListDAO;
import com.jaw.communication.dao.ISMSRequestDAO;
import com.jaw.communication.dao.SMSDetails;
import com.jaw.communication.dao.SMSDetailsKey;
import com.jaw.communication.dao.SMSListKey;
import com.jaw.communication.dao.SMSMemberList;
import com.jaw.communication.dao.SMSRequest;
import com.jaw.communication.dao.SMSRequestKey;
import com.jaw.custom.SMSGroupIdCustomClass;
import com.jaw.custom.SMSResponseCustomClass;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

@Component
public class SMSHelper {
	@Autowired
	SMSGroupIdCustomClass smsGroupIdCustomClass;
	@Autowired
	SMSResponseCustomClass smsResponseCustomClass;
	@Autowired
	SendURL sendURL;

	// Enum for Generic group
	public enum GENERICGROUP {
		PAR, STU, STA, MGT;
	}

	// Logging
	Logger logger = Logger.getLogger(SMSHelper.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	ISMSListDAO smsListDAO;
	@Autowired
	ISMSDetailsDAO smsDetailsDAO;
	@Autowired
	ISMSRequestDAO smsRqstDAO;
	@Autowired
	ISMSConfigurationListDAO smsConfigurationListDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	@Autowired
	SMSPropertyManagementUtil smsPropertyManagementUtil;
	Map<String, String> urlMaps = new LinkedHashMap<String, String>();

	/*1.Gee the SMS Request record
	2.Create the thread to send SMS
	3.Get the count size based on generic and specific Group Size
	4.Update the SMS Request status as API Called
	5.Get total mobile number  for Student group or department
	6.write for loop to get the mobile number list by 70 
	7.Get the mobile numbers and save into smsd table
	8.Form the url to send SMS and get response
	9.thread sleep for 3 minutes
	10.update the message groupid and Status(Api called) to SMS details table
	11.Form the url for getting deliverd status 
	12.Update the SMS delivered details to SMS Details table
	13 Update the SMS Request table status as closed*/
	final ArrayList<String> errorList = new ArrayList<String>();
	@Transactional(rollbackFor = Exception.class)
	public void batchInsertSMSDetailsRecord(final SMSRequest smsRequest,final UserSessionDetails userSessiondetails,
			final ApplicationCache applicationCache)throws NoDataFoundException, DatabaseException,
			DuplicateEntryException {
		
		logger.debug("Inside Send SMS Method");
		logger.debug("Inside Send SMS Method SMS Request Values"+ smsRequest.toString());

		// get SMS Request object
		SMSRequestKey smsRequestKey = new SMSRequestKey();
		commonBusiness.changeObject(smsRequestKey, smsRequest);
		final SMSRequest smsRequestObject = smsRqstDAO.selectSMSRequestRec(smsRequestKey);
		logger.debug("SMS Request object :" + smsRequestObject.toString());		
		// Finished SMS Request object		
		
		// Create thread to Send SMS
		Thread t = new Thread(new Runnable() {
			public void run() {
				
				logger.debug("Starting thread ");
				int forCount = 0;
				int totalMobileNum = 0;
				String genGrp = "";
				
				// Get Generic Group count and List
				int genericCount = 1;
				ArrayList<String> genGrpList = new ArrayList<String>();
				ArrayList<String> speGrpList = new ArrayList<String>();
				if (smsRequestObject.getGeneralGrpList().contains(ApplicationConstant.COMMA_SEPERATOR)) {
					genericCount = smsRequestObject.getGeneralGrpList().split(ApplicationConstant.COMMA_SEPERATOR).length;
					for (int gc = 0; gc < smsRequestObject.getGeneralGrpList().split(ApplicationConstant.COMMA_SEPERATOR).length; gc++) {
						genGrpList.add(smsRequestObject.getGeneralGrpList()
										.split(ApplicationConstant.COMMA_SEPERATOR)[gc]);
					}
				} else {
					genGrpList.add(smsRequestObject.getGeneralGrpList());
				}
				forCount = genericCount;

				// Get Specific Group count and List
				int specificCount = 0;
				if ((!smsRequestObject.getSpecificGrpList().equals(""))) {
					if (smsRequestObject.getSpecificGrpList().contains(ApplicationConstant.COMMA_SEPERATOR)) {
						specificCount = smsRequestObject.getSpecificGrpList()
								.split(ApplicationConstant.COMMA_SEPERATOR).length;
						for (int sc = 0; sc < smsRequestObject.getSpecificGrpList().split(
										ApplicationConstant.COMMA_SEPERATOR).length; sc++) {
							speGrpList.add(smsRequestObject.getSpecificGrpList()
											.split(ApplicationConstant.COMMA_SEPERATOR)[sc]);
						}
					} else {
						speGrpList.add(smsRequestObject.getSpecificGrpList());
						specificCount = 1;
					}
				forCount = specificCount;
				}

				// Update the SMS Request Status
				smsRequestObject.setReqstStatus(ApplicationConstant.IN_PROGRESS);
				try {
					updateSMSRequestStatus(smsRequestObject,userSessiondetails, applicationCache);
				} catch (NoDataFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UpdateFailedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

				// Get total Count for Loop
				SMSListKey smsListKey = new SMSListKey();
				commonBusiness.changeObject(smsListKey, smsRequestObject);
				smsListKey.setGenCategory(smsRequestObject.getReqstCategory());
				logger.debug("Count for Each Student Group or Staff "+ forCount);
				int serialNo = 0;
				for (int i = 0; i < forCount; i++) {
					System.out.println("inside for loopppppppppppp" + i);

					// Set the Genearal and specific group to list key for
					// getting
					// mobile numbers
					if (smsRequestObject.getReqstCategory().equals(
							ApplicationConstant.GENERIC_GROUPS)) {						
						genGrp = genGrpList.get(i);
						smsListKey.setSpecificGrp(genGrpList.get(i));
					} else {
						genGrp = genGrpList.get(0);
						smsListKey.setSpecificGrp(genGrpList.get(0));
						if (smsListKey.getSpecificGrp().equals(
								ApplicationConstant.GENERIC_GRP_STAFF)) {
							smsListKey.setDepartmentId(speGrpList.get(i));
						} else if (!smsListKey.getSpecificGrp().equals(
								ApplicationConstant.GENERIC_GRP_MGMT)) {
							smsListKey.setStudentGrpId(speGrpList.get(i));
						}
					}

					// get the total number of mobile list
					// ENUM For Generic groups
					GENERICGROUP genericGrp = GENERICGROUP.valueOf(smsListKey
							.getSpecificGrp());

					switch (genericGrp) {
					case STA: {
						totalMobileNum = smsListDAO.getMobileNumCountForStaff(smsListKey);
						break;
					}
					case STU: {
						totalMobileNum = smsListDAO.getMobileNumCountForStudent(smsListKey);
						break;
					}
					case PAR: {
						totalMobileNum = smsListDAO.getMobileNumCountForParent(smsListKey);
						break;
					}
					case MGT: {
						totalMobileNum = 0;
						break;
					}
					default: {
						break;
					}
					}

					logger.debug("Total mobile numbers to be insert in one Student grp or staff id"+ totalMobileNum);
					
					// Initialize the offset value
					int offset = 0;

					if (totalMobileNum != offset) {
						List<SMSDetails> smsDetailsList = new ArrayList<SMSDetails>();
						for (int count = 0; count < totalMobileNum; count = count+ Integer
										.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS)) {		
							
							if (count% Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS) > 0) {
								offset = 0;
							} else {
								offset = offset;
							}

							List<SMSMemberList> smsList = null;
							switch (genericGrp) {
							case STA: {
								try {
									smsList = smsListDAO.getMemberListForStaff(smsListKey, offset);
								} catch (NoDataFoundException e) {
									errorList.add("No Mobile Number List found");
								}
								break;
							}
							case STU: {
								try {
									smsList = smsListDAO.getMemberListForStudent(smsListKey, offset);
								} catch (NoDataFoundException e) {
									errorList.add("No Mobile Number List found");
								}
								break;
							}
							case PAR: {
								try {
									smsList = smsListDAO.getMemberListForParent(smsListKey,offset);
								} catch (NoDataFoundException e) {
									errorList.add("No Mobile Number List found");
								}
								break;
							}
							case MGT: {
								// smsList
								break;
							}
							default: {
								break;
							}
							}
							
							//Check the error list and proceed
							if(errorList.size()==0){
                            //convert the list mobilenumber to String using comma seperator
							String mobileNumbersToSend = getConcatenateMobileNumbers(smsList);
							serialNo = serialNo + 1;
							logger.debug("Serial Num" + serialNo);
							try {
								insertSMSDetailsRecord(smsRequestObject,smsList, userSessiondetails, serialNo,applicationCache);
							}  catch (DuplicateEntryException e) {
								errorList.add("Duplicate Entry Exception");
							} catch (NoDataFoundException e) {
								errorList.add("No data Exception");
							} 

							logger.debug("Offset " + offset);
							offset = offset+ Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS);

							// Form SMS Details list to send sms
							SMSDetails smsDetails = new SMSDetails();
							smsDetails.setInstId(userSessiondetails.getInstId());
							smsDetails.setBranchId(userSessiondetails.getBranchId());
							smsDetails.setSmsSrlNo(serialNo);
							smsDetails.setSmsReqstId(smsRequestObject.getSmsReqstId());
							smsDetails.setAcTerm(smsRequestObject.getAcTerm());
							smsDetails.setMobileNumList(mobileNumbersToSend);
							smsDetailsList.add(smsDetails);

						}
						// Finished forloop insert
						// To send Urls
							//Check the error list and proceed
							if(errorList.size()==0){
						logger.debug("Call the send SMS method");
						try {
							sendSMS(smsRequestObject, userSessiondetails,
									applicationCache, smsDetailsList);
						} catch (NoDataFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (PropertyNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
						//Check the error list and proceed
						if(errorList.size()==0){
				// Update SMS Request
				logger.debug("Update SMS Request Status as closed");
				smsRequestObject.setReqstStatus(ApplicationConstant.CLOSED);
				try {
					updateSMSRequestStatus(smsRequestObject,
							userSessiondetails, applicationCache);
				} catch (NoDataFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UpdateFailedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

			}
					}
			}
			}
		});
		t.start();
	}

	/*Method to insert SMS details record,this method is common for batch and single insert(Specific mobile number).
	if its single(Specific mobile number) we check the category and send SMS*/	
	public void insertSMSDetailsRecord(SMSRequest smsRequest,List<SMSMemberList> smsMobileList,
			final UserSessionDetails userSessiondetails, int serialNo,final ApplicationCache applicationCache) throws DuplicateEntryException, NoDataFoundException {
		
		final SMSDetails smsDetails = new SMSDetails();
		
		commonBusiness.changeObject(smsDetails, smsRequest);
		String mobileList = getConcatenateMobileNumbers(smsMobileList);
		smsDetails.setMobileNumCnt(smsMobileList.size());
		smsDetails.setMobileNumList(mobileList);
		smsDetails.setSmsSrlNo(serialNo);
		smsDetails.setDetailsStatus(ApplicationConstant.RQST_ENTERED);
		smsDetailsDAO.saveSMSDetailsRecord(smsDetails);
		/*// functional audit
		doAudit.doFunctionalAudit(userSessiondetails,
				AuditConstant.SMS_DETAILS_INSERT, "Mobile Number Count "
						+ smsMobileList.size());
		logger.debug("Completed Functional Auditing");*/

		//Check whether the category is Specific member and then send sms
		if (smsRequest.getReqstCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)) {
			logger.debug("Specific member list ");
			// get SMS Request object
			SMSRequestKey smsRequestKey = new SMSRequestKey();
			commonBusiness.changeObject(smsRequestKey, smsRequest);
			final SMSRequest smsRequestObject = smsRqstDAO.selectSMSRequestRec(smsRequestKey);
			
			//Create the thread to send SMS
			Thread t = new Thread(new Runnable() {
				public void run() {
					// Update the SMS Request Status
					smsRequestObject.setReqstStatus(ApplicationConstant.IN_PROGRESS);
					try {
						updateSMSRequestStatus(smsRequestObject,
								userSessiondetails, applicationCache);
					} catch (NoDataFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UpdateFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 

					List<SMSDetails> smsDetailsList = new ArrayList<SMSDetails>();
					smsDetailsList.add(smsDetails);

					try {
						sendSMS(smsRequestObject, userSessiondetails,
								applicationCache, smsDetailsList);
					} catch (NoDataFoundException e1) {
						errorList.add("No data found exception");
					} catch (PropertyNotFoundException e1) {
						errorList.add("property not found exception");
					}
					//Check the error list and proceed
					if(errorList.size()==0){
					// Update SMS Request to CLosed
					smsRequestObject.setReqstStatus(ApplicationConstant.CLOSED);
					try {
						updateSMSRequestStatus(smsRequestObject,
								userSessiondetails, applicationCache);
					} catch (NoDataFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UpdateFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					}
				}
			});
			t.start();
		}

	}

	//method is used to convert list of objects to string
	public String getConcatenateMobileNumbers(List<SMSMemberList> smsMobileList) {
		String mobileList = "";

		for (int sd = 0; sd < smsMobileList.size(); sd++) {
			if (sd == 0) {
				mobileList = smsMobileList.get(sd).getMobileNum();
			} else {
				mobileList += ApplicationConstant.COMMA_SEPERATOR
						+ smsMobileList.get(sd).getMobileNum();
			}
		}
		return mobileList;
	}

	//Update SMS Request 
	public void updateSMSRequestStatus(SMSRequest smsRequest,
			UserSessionDetails userSessiondetails,
			ApplicationCache applicationCache) throws NoDataFoundException, UpdateFailedException  {
		
		logger.debug("inside update Alert method");
		SMSRequestKey smsRequestKey = new SMSRequestKey();

		smsRequest.setInstId(userSessiondetails.getInstId());
		smsRequest.setBranchId(userSessiondetails.getBranchId());
		commonBusiness.changeObject(smsRequestKey, smsRequest);
		smsRequestKey.setDbTs(0);
		SMSRequest smsRequestNew = smsRqstDAO.selectSMSRequestRec(smsRequestKey);
		SMSRequest updateSMSRequest = new SMSRequest();
		commonBusiness.changeObject(updateSMSRequest, smsRequestNew);
		smsRequestKey.setDbTs(updateSMSRequest.getDbTs());

		updateSMSRequest.setReqstStatus(smsRequest.getReqstStatus());
		updateSMSRequest.setrModId(userSessiondetails.getUserId());

		smsRqstDAO.updateSMSRequestStatus(updateSMSRequest, smsRequestKey);

		/*// functional audit
		doAudit.doFunctionalAudit(userSessiondetails,
				AuditConstant.SMS_RQST_UPDATE, " ");
		logger.debug("Completed Functional Auditing");

		// Database audit
		String oldRecString = smsRequestNew.toStringForAuditSMSRequestRecord();
		SMSRequest SMSRequestForAudit = null;
		smsRequestKey.setDbTs(0);
		try {
			SMSRequestForAudit = smsRqstDAO.selectSMSRequestRec(smsRequestKey);
		} catch (NoDataFoundException e) {
			logger.error("No data found  Exception occured in update student group master:");
			e.printStackTrace();
		}
		smsRequestKey.setDbTs(SMSRequestForAudit.getDbTs());
		String newRecString = SMSRequestForAudit
				.toStringForAuditSMSRequestRecord();
		doAudit.doDatabaseAudit(applicationCache, userSessiondetails,
				TableNameConstant.SMS_REQUEST,
				smsRequestKey.toStringForAuditAlertKey(), oldRecString,
				AuditConstant.TYPE_OF_OPER_UPDATE, newRecString, "");

		logger.debug("Completed Database Auditing");*/

	}

	//Update SMS Details
	public void updateSMSDetails(SMSDetails smsDetails,
			UserSessionDetails userSessiondetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			UpdateFailedException {
		logger.debug("inside update Alert method");

		SMSDetailsKey smsDetailsKey = new SMSDetailsKey();

		smsDetailsKey.setInstId(userSessiondetails.getInstId());
		smsDetailsKey.setBranchId(userSessiondetails.getBranchId());
		commonBusiness.changeObject(smsDetailsKey, smsDetails);
		smsDetailsKey.setDbTs(0);
		SMSDetails smsDetailsNew = smsDetailsDAO
				.selectSMSDetailsRec(smsDetailsKey);
		SMSDetails updateSMSDetails = new SMSDetails();
		commonBusiness.changeObject(updateSMSDetails, smsDetailsNew);
		smsDetailsKey.setDbTs(updateSMSDetails.getDbTs());

		updateSMSDetails.setDetailsStatus(smsDetails.getDetailsStatus());
		updateSMSDetails.setMsgGrpId(smsDetails.getMsgGrpId());
		updateSMSDetails.setDeliveredNumCnt(smsDetails.getDeliveredNumCnt());
		updateSMSDetails
				.setUnDeliveredNumCnt(smsDetails.getUnDeliveredNumCnt());
		updateSMSDetails.setUnDeliveredNumList(smsDetails
				.getUnDeliveredNumList());
		updateSMSDetails.setrModId(userSessiondetails.getUserId());

		smsDetailsDAO.updateSMSDetailsRecord(updateSMSDetails, smsDetailsKey);

	}

	//Send SMS
	void sendSMS(SMSRequest smsRequest, UserSessionDetails userSessiondetails,
			ApplicationCache applicationCache, List<SMSDetails> smsDetailsList) throws NoDataFoundException, PropertyNotFoundException
			{
		logger.debug("Send SMS Method");
		
		
		String vendorName=smsPropertyManagementUtil
				.getPropertyValue(
						applicationCache,
						userSessiondetails.getInstId(),
						userSessiondetails.getBranchId(),
						PropertyManagementConstant.SMS_VENDOR_NAME)
				.toString();
		// get SMS Request object
		SMSRequestKey smsRequestKey = new SMSRequestKey();
		commonBusiness.changeObject(smsRequestKey, smsRequest);
		smsRequestKey.setDbTs(0);
		final SMSRequest smsRequestObject = smsRqstDAO
				.selectSMSRequestRec(smsRequestKey);

		for (int url = 0; url < smsDetailsList.size(); url++) {
			String baseUrl = smsPropertyManagementUtil.getPropertyValue(
					applicationCache, userSessiondetails.getInstId(),
					userSessiondetails.getBranchId(),
					PropertyManagementConstant.SMS_CONFIG_BASE_URL).toString();
			StringBuffer sendUrl = new StringBuffer();
			System.out.println("urllllllllllllllll" + baseUrl);
			if (baseUrl.contains("http://")) {
				sendUrl.append(baseUrl + "/");
			} else {
				sendUrl.append("http://" + baseUrl + "/");
			}

			sendUrl.append(
					smsPropertyManagementUtil.getPropertyValue(
							applicationCache, userSessiondetails.getInstId(),
							userSessiondetails.getBranchId(),
							PropertyManagementConstant.SMS_CONFIG_SEND_URL)
							.toString())
					.append("?"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											userSessiondetails.getInstId(),
											userSessiondetails.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_AUTH_PARAM)
									.toString())
					.append("&"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											userSessiondetails.getInstId(),
											userSessiondetails.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_MOBNO_LIST)
									.toString())
					.append("=" + smsDetailsList.get(url).getMobileNumList())
					.append("&"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											userSessiondetails.getInstId(),
											userSessiondetails.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_MSG_PARAM)
									.toString())
					.append("=" + smsRequestObject.getSmsMessage());

			System.out.println("urls" + sendUrl.toString());
			logger.debug("Send Url   : " + sendUrl.toString());
              //Send the Url
				String result = sendURL.sendURLAndGetResponse(sendUrl.toString());
			try {
				Thread.sleep(180000);
			} catch (InterruptedException e1) {
              errorList.add("Interupt Exception");
			}
			// System.out.println("resultttttttttttttt  :  "+result.toString());
			//Check the error list and proceed
			if(errorList.size()==0){
			smsDetailsList.get(url).setDetailsStatus(ApplicationConstant.API_CALLED);
			String msgId = smsGroupIdCustomClass.getResponseMsgGrpId(result.toString(), userSessiondetails,vendorName);
			System.out.println("msg grp iddddddddddd" + msgId);
			logger.debug("Send Url   Messege group Id : " + msgId);
			logger.debug("Update Message Group id to sms details");
			smsDetailsList.get(url).setMsgGrpId(msgId);
			try {
				updateSMSDetails(smsDetailsList.get(url), userSessiondetails,
						applicationCache);
			} catch (NoDataFoundException e) {
				errorList.add("No Dat found Exception");
			} catch (UpdateFailedException e) {
				errorList.add("Update failed Exception");
			}

			// Get Respone Url (Include mag Grp id)
			if(errorList.size()==0){
			String baseUrl1 = smsPropertyManagementUtil.getPropertyValue(
					applicationCache, userSessiondetails.getInstId(),
					userSessiondetails.getBranchId(),
					PropertyManagementConstant.SMS_CONFIG_BASE_URL).toString();
			StringBuffer resUrl = new StringBuffer();
			System.out.println("urllllllllllllllll" + baseUrl1);
			if (baseUrl1.contains("http://")) {
				resUrl.append(baseUrl1 + "/");
			} else {
				resUrl.append("http://" + baseUrl1 + "/");
			}
			resUrl.append(smsPropertyManagementUtil.getPropertyValue(
					applicationCache, userSessiondetails.getInstId(),
					userSessiondetails.getBranchId(),
					PropertyManagementConstant.SMS_CONFIG_RESPONSE_URL)
					.toString());
			String responseAuthParam = smsPropertyManagementUtil
					.getPropertyValue(
							applicationCache,
							userSessiondetails.getInstId(),
							userSessiondetails.getBranchId(),
							PropertyManagementConstant.SMS_CONFIG_RES_AUTH_PARAM)
					.toString();
			if ((!responseAuthParam.equals("")) && (responseAuthParam != null)) {
				resUrl.append("?" + responseAuthParam)
						.append("&"
								+ smsPropertyManagementUtil
										.getPropertyValue(
												applicationCache,
												userSessiondetails.getInstId(),
												userSessiondetails
														.getBranchId(),
												PropertyManagementConstant.SMS_CONFIG_RES_MSGID_PARAM)
										.toString());
			} else {
				resUrl.append("?"
						+ smsPropertyManagementUtil
								.getPropertyValue(
										applicationCache,
										userSessiondetails.getInstId(),
										userSessiondetails.getBranchId(),
										PropertyManagementConstant.SMS_CONFIG_RES_MSGID_PARAM)
								.toString());
			}

			resUrl.append("=" + msgId);

			System.out.println("response  url" + resUrl.toString());
			logger.debug("Response url : " + resUrl.toString());
			// Send url method call
			String responseResult = sendURL.sendURLAndGetResponse(resUrl.toString());

			logger.debug("Response url return text");
			SMSDetails smsDet = smsResponseCustomClass.getSMSResponse(
					responseResult, userSessiondetails,vendorName);
			logger.debug("Response modified : " + smsDet.toString());
			// Update SMS Details Delivered and un delivered cnt and Status
			smsDetailsList.get(url).setDeliveredNumCnt(smsDet.getDeliveredNumCnt());
			smsDetailsList.get(url).setUnDeliveredNumCnt(smsDet.getUnDeliveredNumCnt());
			smsDetailsList.get(url).setUnDeliveredNumList(smsDet.getUnDeliveredNumList());
			if ((smsDet.getUnDeliveredNumCnt() == 0)
					&& (smsDet.getDeliveredNumCnt() == 0)) {
				smsDetailsList.get(url).setDetailsStatus(
						ApplicationConstant.FAILED);
			} else if (smsDet.getUnDeliveredNumCnt() == 0) {
				smsDetailsList.get(url).setDetailsStatus(
						ApplicationConstant.DELIVERED);
			} else {
				smsDetailsList.get(url).setDetailsStatus(
						ApplicationConstant.UNDELIVERED);
			}
			logger.debug("Update SMS details record :delivered and undelivered details");
			try {
				updateSMSDetails(smsDetailsList.get(url), userSessiondetails,
						applicationCache);
			} catch (NoDataFoundException e) {
                errorList.add("no data found exception");
			} catch (UpdateFailedException e) {
				errorList.add("update failed exception");
			}

		}
		}
		}

	}

}
