package com.jaw.common.batch.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
//Pojo class to read the XML for Excel Operations
@XmlRootElement(name="file")
public class XmlConfigurationForUpload implements Serializable{
	
	@XmlElement(name="recordFormat")
	 RecordFormat recordFormat;

	public RecordFormat getRecordFormat() {
		return recordFormat;
	}	

}
