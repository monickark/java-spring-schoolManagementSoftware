package com.jaw.custom;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.communication.dao.SMSDetails;
import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class SMSResponseCustomClass {

	//Enum for Vendor
			public enum VENDOR {
				KAP,IISSMS;
				}
		
		public SMSDetails getSMSResponse(String responseText,UserSessionDetails userSessionDetails,String vendorName){		
			System.out.println("branchsssssssssss"+userSessionDetails.getBranchId());
			
		//ENUM For Generic groups
			VENDOR vendor = VENDOR.valueOf(vendorName);
			SMSDetails smsDetails = null;
		switch (vendor)
		{
		 case IISSMS:{
			System.out.println("for branch 1"+responseText);
			//responseText="399552725-1,+919886499102,DELIVRD,2014-01-31 16:11:25 399552725-2,+919886499101,DELIVRD,2014-01-31 16:11:25";
			//responseText="436887270-1,+919916787292,DELIVRD,2014-02-27 16:50:01436887270-2,+918095087923,DELIVRD,2014-02-27 16:50:01436887270-3,+919916892844,DELIVRD,2014-02-27 16:50:01";
			smsDetails=getSMSResponseForIISSMS(responseText);
			//smsDetails=getSMSResponseIdForB2(responseText);
			break;
		}
	    case KAP:{
	    	System.out.println("for branch 2"+responseText);
	    //	responseText="918095087923 DELIVRD<br>919916892844 DELIVRD<br>";
	    	smsDetails=getSMSResponseIdForKAP(responseText);
		    break;
	    }		
        default:{
         break;
         }
		}
		return smsDetails;
	}

		private SMSDetails getSMSResponseIdForKAP(String responseText) {
			SMSDetails smsDetails=new SMSDetails();
			String lines[] = responseText.split("<br>");
			System.out.println("array length"+lines.length);
			int delCnt=0;
			int unDelCnt=0;
			String unDeliveredMobileNum="";
			for(int i=0;i<lines.length;i++){
				System.out.println("result values"+lines[i]);
				if(!lines[i].equals("")){
				String[] respone=lines[i].split("\\s+");
				if(respone[1].equals("DELIVRD")){
					delCnt=delCnt+1;
				}else{
					unDelCnt=unDelCnt+1;
					unDeliveredMobileNum+=ApplicationConstant.COMMA_SEPERATOR+respone[0];
				}
				}
			}
			smsDetails.setDeliveredNumCnt(delCnt);
			smsDetails.setUnDeliveredNumCnt(unDelCnt);
			smsDetails.setUnDeliveredNumList(unDeliveredMobileNum);
			return smsDetails;
		}

		private SMSDetails getSMSResponseForIISSMS(String responseText) {
			SMSDetails smsDetails=new SMSDetails();
			
			String arrayString[] = responseText.split("\\s+") ;			
			ArrayList<String> outputResponse=new ArrayList<String>();
			for(int i=0;i<arrayString.length;i++){
				//if(i%2==0){					
					outputResponse.add(arrayString[i]);
				//}
			}
			int delCnt=0;
			int unDelCnt=0;
			String unDeliveredMobileNum="";
			System.out.println("list sizeeeeeeeeeeee"+outputResponse.size());
			for(int or=0;or<outputResponse.size();or++){
				System.out.println("values"+outputResponse.get(or));
				if(outputResponse.get(or).contains(",")){
				String[] outputArray=outputResponse.get(or).split(",");
				System.out.println("values"+outputArray[2]+"output 1"+outputArray[1]);
				if(outputArray[2].equals("DELIVRD")){
					delCnt=delCnt+1;
				}else{
					unDelCnt=unDelCnt+1;
					if(unDeliveredMobileNum.equals("")){
						unDeliveredMobileNum=outputArray[1];
					}else{
						unDeliveredMobileNum+=ApplicationConstant.COMMA_SEPERATOR+outputArray[1];						
					}
					
				}
				}
			}
			smsDetails.setDeliveredNumCnt(delCnt);
			smsDetails.setUnDeliveredNumCnt(unDelCnt);
			smsDetails.setUnDeliveredNumList(unDeliveredMobileNum);
			System.out.println("deleived cnt "+delCnt);
			System.out.println("deleived cnt "+unDelCnt);
			System.out.println("un delivered mobile listttttttttt"+unDeliveredMobileNum);
			return smsDetails;
		}
}
