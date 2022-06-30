package com.jaw.mark.controller;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.mark.dao.MarkSubjectLinkKey;
import com.jaw.mark.dao.MarkSubjectLinkList;

public interface IMarkSubjectLinkListDAO {
	
	List<MarkSubjectLinkList> getMarkSubjectLinkList(MarkSubjectLinkKey MarkSubjectLinkKey)
			throws NoDataFoundException;
	
}
