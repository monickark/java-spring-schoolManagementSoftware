package com.jaw.attendance.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class ViewAttendance  implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendance.class);
private String subject;
private int noOfAbsent;
private int noOfPresent;
private String attType;
private String crslId;
private String clsType="";
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public int getNoOfAbsent() {
	return noOfAbsent;
}
public void setNoOfAbsent(int noOfAbsent) {
	this.noOfAbsent = noOfAbsent;
}
public int getNoOfPresent() {
	return noOfPresent;
}
public void setNoOfPresent(int noOfPresent) {
	this.noOfPresent = noOfPresent;
}

public String getCrslId() {
	return crslId;
}
public void setCrslId(String crslId) {
	this.crslId = crslId;
}

public String getAttType() {
	return attType;
}
public void setAttType(String attType) {
	this.attType = attType;
}
public String getClsType() {
	return clsType;
}
public void setClsType(String clsType) {
	this.clsType = clsType;
}

@Override
public String toString() {
	return "ViewAttendance [subject=" + subject + ", noOfAbsent=" + noOfAbsent
			+ ", noOfPresent=" + noOfPresent + ", attType=" + attType
			+ ", crslId=" + crslId + ", clsType=" + clsType + "]";
}
@Override
public int hashCode() {
    int hashCode = 0;

    hashCode = hashCode * 37 + this.crslId.hashCode();
  //  hashCode = hashCode * 37 + this.field2.hashCode();
	System.out.println("Hash code :"+hashCode);
    return hashCode;
}

public boolean equals( Object obj )
{
	/*boolean flag = false;
	ViewAttendance vA = ( ViewAttendance )obj;
	if( vA.crslId == crslId )
		flag = true;
	return flag;*/
	 if (!(obj instanceof ViewAttendance)) {
	        return false;
	    }

	 ViewAttendance that = (ViewAttendance) obj;
/*System.out.println("this .crslid : "+this.crslId);
System.out.println("that .crslid : "+that.crslId);*/
	    // Custom equality check here.
boolean flag=this.crslId.equals(that.crslId)&&this.clsType.equals(that.clsType);
/*System.out.println("flagggggggggggggg : "+flag);*/
	    return flag;
}

}
