package com.jaw.attendance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.dao.StudentAttendance;

public class ViewAttendanceHelper {
public List<ViewAttendance> getAttendanceDeatils(List<StudentAttendance> studentAttendanceDetails){
	List<ViewAttendance> viewAttendanceList=new ArrayList<ViewAttendance>();
	Map<String,ViewAttendance> result=new HashMap<String,ViewAttendance>();
	String prevCrsl=null;
	String prevClsType=null;
	
	for(StudentAttendance st:studentAttendanceDetails){		
		
		System.out.println("previvvvvvvvvv crslllllll"+prevCrsl);
		System.out.println("previvvvvvvvvv class typeeeeeeeeee"+prevClsType);
		if(st.getClsType()!=null){
			String newCrsl=st.getCrslId();	
			String newClasstype=st.getClsType();
			System.out.println("new crslllllll"+newCrsl);
			System.out.println("new class typeeeeeeeeee"+newClasstype);
			if(newCrsl.equals(prevCrsl)&&(newClasstype.equals(prevClsType))){	
				System.out.println("if service helperrrrrrrrrrrrrrr");
				if(st.getIsPresent().equals("A")){
					result.get(newCrsl).setNoOfAbsent(st.getNoOfSessions());
				}else if(st.getIsPresent().equals("P")){
					result.get(newCrsl).setNoOfPresent(st.getNoOfSessions());
				}			
			}else{	
				System.out.println("elseeeeeeeeeeeeeeeee service helperrrrrrrrrrrrrrr");
				ViewAttendance viewAttendance=new ViewAttendance();
				if(st.getIsPresent().equals("A")){
					viewAttendance.setNoOfAbsent(st.getNoOfSessions());
				}else if(st.getIsPresent().equals("P")){
					viewAttendance.setNoOfPresent(st.getNoOfSessions());
				}
				viewAttendance.setSubject(st.getAbsenteeRmks());
				viewAttendance.setClsType(st.getClsType());
				result.put(st.getCrslId(), viewAttendance);			
			}
			if((prevCrsl!=null)&&(prevCrsl.equals(st.getCrslId()))){
				prevClsType=st.getClsType();
			}else{
				prevClsType=null;
			}
			prevCrsl=st.getCrslId();
			
			
			
		}else{
		String newCrsl=st.getCrslId();		
		if(newCrsl.equals(prevCrsl)){			
			if(st.getIsPresent().equals("A")){
				result.get(newCrsl).setNoOfAbsent(st.getNoOfSessions());
			}else if(st.getIsPresent().equals("P")){
				result.get(newCrsl).setNoOfPresent(st.getNoOfSessions());
			}			
		}else{			
			ViewAttendance viewAttendance=new ViewAttendance();
			if(st.getIsPresent().equals("A")){
				viewAttendance.setNoOfAbsent(st.getNoOfSessions());
			}else if(st.getIsPresent().equals("P")){
				viewAttendance.setNoOfPresent(st.getNoOfSessions());
			}
			viewAttendance.setSubject(st.getAbsenteeRmks());
			result.put(st.getCrslId(), viewAttendance);			
		}
		prevCrsl=st.getCrslId();
		}
	}
	 Iterator it = result.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        viewAttendanceList.add((ViewAttendance)pairs.getValue());			       
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    for(ViewAttendance viewAtt:viewAttendanceList){
	    	System.out.println("objecttttttttttttttttttttttt"+viewAtt);
	    }
	    return viewAttendanceList;
}
}
