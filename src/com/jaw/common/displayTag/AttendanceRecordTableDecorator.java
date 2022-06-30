package com.jaw.common.displayTag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;

import com.jaw.attendance.controller.AttendanceDetailsListVO;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

public class AttendanceRecordTableDecorator extends TableDecorator {
	private String id = "id";
	String key = null;
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	HttpSession httpSession;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void init(PageContext pageContext, Object decorated,
			TableModel tableModel) {
		super.init(pageContext, decorated, tableModel);
		key = pageContext.getRequest().getParameter("rowId1");
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpSession=pageContext.getSession();
		
	}

	public Integer getSerialno() {

		return getListIndex() + 1;
	}
	@Override
	public String finishRow() {
		return addRowClass();
		
	}
	
	private String getDescriptionByTypeAndCode(String type, String code)
			throws CommonCodeNotFoundException {
		return commonCodeUtil.getDescriptionByTypeAndCode(applicationCache,
				type, code, sessionCache.getUserSessionDetails().getInstId(),
				sessionCache.getUserSessionDetails().getBranchId());
	}
@Override
	public String addRowClass() {
		AttendanceDetailsListVO attendanceListVO = (AttendanceDetailsListVO) getCurrentRowObject();
		System.out.println("view index :" + getViewIndex());
		System.out.println("row Id :" + attendanceListVO.getRowid());
		System.out.println("key :" + key);
		if(key==null) {
			System.out.println("inside session get"+httpSession);
			Object value=httpSession.getAttribute("rowIdpers");
			System.out.println("Object :"+value);
			if(value!=null) {
				key=String.valueOf(value.toString());
				System.out.println("inside key :"+key);
			}
			
		}  else {
			System.out.println("inside session set");
			httpSession.setAttribute("rowIdpers", key);
		}
		System.out.println("row Id :" + attendanceListVO.getRowid());
		if ((key != null)
				&& (attendanceListVO.getRowid() == Integer.parseInt(key))) {
			System.out.println("Inside highlight");
	
			return "highlight";
		} else {
			System.out.println("no highlight");
			return null;
		}
	}

public String getLabBatch() throws CommonCodeNotFoundException {

	AttendanceDetailsListVO attendanceListVO = (AttendanceDetailsListVO) getCurrentRowObject();
	StringBuffer buffer = new StringBuffer();
	
		System.out.println("getLabBatch :"+attendanceListVO.getLabBatch());
		if ((attendanceListVO.getLabBatch() == null)||(attendanceListVO.getLabBatch().equals(""))) {
			System.out.println("Inside null");
			buffer.append("Not Applicable");
		} else {
			buffer.append(getDescriptionByTypeAndCode(
					CommonCodeConstant.LAB_BATCH,
					attendanceListVO.getLabBatch()));
		}
		
	
	return buffer.toString();
}


}