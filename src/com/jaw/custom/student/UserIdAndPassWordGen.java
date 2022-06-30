package com.jaw.custom.student;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;

@Component
public class UserIdAndPassWordGen {
	
	Logger logger = Logger.getLogger(UserIdAndPassWordGen.class);
	
	public String getStudentUserId(String studentAdmisNo) throws UnableCreateStudentUserId{
		logger.debug("Inside the method to create Student User Id");	
		String studentUserId= null;
		
		if((studentAdmisNo!=null)&&(!studentAdmisNo.equals(""))){
		
		 studentUserId= ApplicationConstant.PREFIX_STUDENT_USERID+studentAdmisNo;
		}else{
			throw new UnableCreateStudentUserId();
		}
		return studentUserId;
		
	}

	public String getStudentPassword(String studentDOB) throws UnableCreateStudentPassword{
		logger.debug("Inside the method to create Student Password");
		String studentPass = "";				
		if((studentDOB==null)||(studentDOB.equals(""))){
			throw new UnableCreateStudentPassword();
		}else{
			String[] splittedDOB = studentDOB.split(ApplicationConstant.CONST_SPLIT_DOB);		
			for(Integer index=(splittedDOB.length)-1;index>-1;index-- ){				
				if(index==0){					
					studentPass = studentPass.concat(splittedDOB[index].substring(2, 4));	
				}else if(index==1){
					if(splittedDOB[index].length()==1){
						studentPass = studentPass.concat("0").concat(splittedDOB[index]);
					}else{
						studentPass = studentPass.concat(splittedDOB[index]);
					}
				
				}
				else{
					studentPass = studentPass.concat(splittedDOB[index]);	
				}			
			}
		}	
		return studentPass;
		
	}
	public String getParentUserId(String studentAdmisNo) throws UnableCreateParentUserId{
		
		logger.debug("Inside the method to create Parent User Id");

		String parentUserId="";
		
		if((studentAdmisNo!=null)&&(!studentAdmisNo.equals(""))){
		
			parentUserId= ApplicationConstant.PREFIX_PARENT_USERID+studentAdmisNo;
		}else{
			throw new UnableCreateParentUserId();
		}
		return parentUserId;
		
	
		
	}
	public String getParentPassword(String studentDOB) throws UnableCreateParentPassword{
		logger.debug("Inside the method to create Parent Password");
		
		String parentPass = "";
		if((studentDOB!=null)&&(!studentDOB.equals(""))){
			String[] splittedDOB = studentDOB.split(ApplicationConstant.CONST_SPLIT_DOB);
			
			for(Integer index=(splittedDOB.length)-1;index>-1;index-- ){			
				if(index==0){				
					parentPass = parentPass.concat(splittedDOB[index].substring(2, 4));	
				}else if(index==1){
					if(splittedDOB[index].length()==1){
						parentPass = parentPass.concat("0").concat(splittedDOB[index]);
					}else{
						parentPass = parentPass.concat(splittedDOB[index]);
					}
				
				}else{
					parentPass = parentPass.concat(splittedDOB[index]);	
				}				
			}			
		}else{
			throw new UnableCreateParentPassword();
		}	
		return parentPass;
		
	}
}
