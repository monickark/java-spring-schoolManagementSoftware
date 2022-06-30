package com.jaw.common.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.sms.SMSHelper.GENERICGROUP;
import com.jaw.common.util.SMSPropertyManagementUtil;
import com.jaw.communication.dao.ISMSAlertDAO;
import com.jaw.communication.dao.ISMSListDAO;
import com.jaw.communication.dao.SMSAlert;
import com.jaw.communication.dao.SMSAlertKey;
import com.jaw.communication.dao.SMSDetails;
import com.jaw.communication.dao.SMSDetailsKey;
import com.jaw.communication.dao.SMSListKey;
import com.jaw.communication.dao.SMSMemberList;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;

@Component
public class SendAlertSMS {
	// Enum for Alert Type
	public enum ALERTTYPE {
		ABST, NAM, FEES, PWRST;
	}

	// Logging
	Logger logger = Logger.getLogger(SendAlertSMS.class);
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ISMSListDAO smsListDAO;
	@Autowired
	ISMSAlertDAO smsAlertDAO;
	@Autowired
	SMSPropertyManagementUtil smsPropertyManagementUtil;
	@Autowired
	SendURL sendURL;

	public void sendAlertSMS(final ApplicationCache applicationCache,
			SMSAlert smsAlert, List<String> ids,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, PropertyNotFoundException,
			NoDataFoundException, UpdateFailedException {
		List<SMSMemberList> smsMobileList = null;

		// ENUM For Generic groups
		ALERTTYPE alertType = ALERTTYPE.valueOf(smsAlert.getAlertType());

		switch (alertType) {
		case ABST: {
			smsMobileList = getParentMobileList(smsAlert, ids);
			break;
		}
		case NAM: {
			smsMobileList = getParentMobileList(smsAlert, ids);
			break;
		}
		case FEES: {
			smsMobileList = getParentMobileList(smsAlert, ids);
			break;
		}
		case PWRST: {
			// totalMobileNum = 0;
			break;
		}
		default: {
			break;
		}
		}

		// Iterate the mobile number and send sms
		for (int i = 0; i < smsMobileList.size(); i++) {
			SMSAlert smsAlrt = new SMSAlert();
			commonBusiness.changeObject(smsAlrt, smsAlert);
			smsAlrt.setMobileNum(smsMobileList.get(i).getMobileNum());
			smsAlrt.setDelFlag("N");
			smsAlrt.setLinkId(smsMobileList.get(i).getAdmissionNum());
			if (smsAlert.getAlertType().equals(
					ApplicationConstant.ATTENDANCE_ALERT)) {
				smsAlrt.setSmsMessage(smsAlrt.getSmsMessage().replace("NAME",
						smsMobileList.get(i).getStudentName()));
			}
			System.out.println("sms Alert to string: " + smsAlrt.toString());
			// Insert SMS Alert
			smsAlertDAO.insertSMSAlertRec(smsAlrt);

			// Forming the url
			String baseUrl = smsPropertyManagementUtil.getPropertyValue(
					applicationCache, smsAlert.getInstId(),
					smsAlert.getBranchId(),
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
							applicationCache, smsAlert.getInstId(),
							smsAlert.getBranchId(),
							PropertyManagementConstant.SMS_CONFIG_SEND_URL)
							.toString())
					.append("?"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											smsAlert.getInstId(),
											smsAlert.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_AUTH_PARAM)
									.toString())
					.append("&"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											smsAlert.getInstId(),
											smsAlert.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_MOBNO_LIST)
									.toString())
					.append("=" + smsAlrt.getMobileNum())
					.append("&"
							+ smsPropertyManagementUtil
									.getPropertyValue(
											applicationCache,
											smsAlert.getInstId(),
											smsAlert.getBranchId(),
											PropertyManagementConstant.SMS_CONFIG_SEND_MSG_PARAM)
									.toString())
					.append("=" + smsAlrt.getSmsMessage());

			System.out.println("urls" + sendUrl.toString());
			logger.debug("Send Url   : " + sendUrl.toString());
			// Send URL
			//String response = sendURL.sendURLAndGetResponse(sendUrl.toString());
			//logger.debug("Send Url  Response : " + response);
			//System.out.println("responseeeeeeeeeeeee" + response);

			// Update Message groupid to sms alert
			smsAlrt.setMsgGrpId("response");
			updateSMSAlertRecord(smsAlrt, userSessionDetails);
		}

	}

	// Get mobile number list for parent
	List<SMSMemberList> getParentMobileList(SMSAlert smsAlrt, List<String> ids) {
		SMSListKey smsListKey = new SMSListKey();
		commonBusiness.changeObject(smsListKey, smsAlrt);
		List<SMSMemberList> smsMobileList = null;
		try {
			smsMobileList = smsListDAO.selectParenMobileListForAlert(
					smsListKey, ids);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smsMobileList;
	}

	void updateSMSAlertRecord(SMSAlert smsAlert,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			UpdateFailedException {

		logger.debug("inside update Alert method");
		SMSAlertKey smsAlertKey = new SMSAlertKey();

		commonBusiness.changeObject(smsAlertKey, smsAlert);
		smsAlertKey.setDbTs(0);
		SMSAlert smsAlertNew = smsAlertDAO.selectSMSAlertRec(smsAlertKey);
		System.out.println("Selected SMS ALert"+ smsAlertNew.toString());
		SMSAlert updateSMSAlert = new SMSAlert();
		commonBusiness.changeObject(updateSMSAlert, smsAlertNew);
		smsAlertKey.setDbTs(updateSMSAlert.getDbTs());
		smsAlertKey.setAlertDate(updateSMSAlert.getAlertDate());
		updateSMSAlert.setDeliveryStatus(smsAlert.getDeliveryStatus());
		updateSMSAlert.setMsgGrpId(smsAlert.getMsgGrpId());
		updateSMSAlert.setrModId(userSessionDetails.getUserId());	
		smsAlertDAO.updateSMSAlertRec(updateSMSAlert, smsAlertKey);

	}
}
