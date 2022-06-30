package com.jaw.custom;

import org.springframework.stereotype.Component;

import com.jaw.framework.sessCache.UserSessionDetails;
@Component
public class SMSGroupIdCustomClass {
	//Enum for Vendor
		public enum VENDOR {
			KAP,IISSMS;
			}
		
		public String getResponseMsgGrpId(String res,UserSessionDetails userSessionDetails,String vendorName){		
			System.out.println("branchsssssssssss"+userSessionDetails.getBranchId());
			
		//ENUM For Generic groups
			VENDOR vendor = VENDOR.valueOf(vendorName);
		String outputMsgId = null;
		switch (vendor)
		{
		case IISSMS:{
			System.out.println("for branch 1"+res);
		//	res="Message GID=436887270 ID=436887270-1, 436887270-2, 436887270-3Invalid/DND Numbers: 988649910";
			outputMsgId=getResponseIdForIISSMS(res);
			//outputMsgId=getResponseIdForB2(res);
			break;
		}
	    case KAP:{
	    	System.out.println("for branch 2"+res);
	    	//res="113509649-2014_02_21";
	    	outputMsgId=getResponseIdForKAP(res);
		    break;
	    }		
        default:{
         break;
         }
		}
		return outputMsgId;
	}
		
		//For ASC and Branch 1
		String getResponseIdForIISSMS(String response){
			String output="";
			
			String[] tokens = response.split("-|\\.");
			if(response.contains("=")){
				output= response.split(" ")[1].split("=")[1];
			}else{
				output=response;
			}
			
			
			//   int ind = response.indexOf("Message GID=");
			/*System.out.println("total lengthhhhhhhhhhhhhhh"+response.length());
			System.out.println("total lengthhhhhhhhhhhhhhh"+response.charAt(9));
			System.out.println("total lengthhhhhhhhhhhhhhh"+"Message GID=".length()+ 9);
			    output = response.substring("Message GID=".length(),"Message GID=".length()+ 9);*/
				System.out.println("values in split string"+output);
			
			return output;
		}
		//For  Branch2
		String getResponseIdForKAP(String response){
					String output="";
						output=response;
					
						System.out.println("values in split string"+output);
					
					return output;
				}
}
