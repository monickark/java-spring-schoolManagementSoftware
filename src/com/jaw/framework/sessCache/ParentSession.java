package com.jaw.framework.sessCache;

import java.io.Serializable;
import java.util.List;

public class ParentSession implements Serializable {
	private String parentName;
	private String selectedStuIndex="0";
	private List<StudentSession> studentSession;

	public String getParentName() {
		return parentName;
	}

	public String getSelectedStuIndex() {
		return selectedStuIndex;
	}

	@Override
	public String toString() {
		return "ParentSession [parentName=" + parentName
				+ ", selectedStuIndex=" + selectedStuIndex
				+ ", studentSession=" + studentSession + "]";
	}

	public void setSelectedStuIndex(String selectedStuIndex) {
		this.selectedStuIndex = selectedStuIndex;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<StudentSession> getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(List<StudentSession> studentSession) {
		this.studentSession = studentSession;
	}

}
