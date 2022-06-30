package com.jaw.common.displayTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.TableModel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.dropdown.dao.IStudentGroupListTagDAO;
import com.jaw.common.dropdown.dao.StudentGroupListTagDAO;
import com.jaw.common.dropdown.service.DropDownListService;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.dao.IStudentGroupListUtilDAO;
import com.jaw.common.util.dao.StudentGroupListUtilDAO;
import com.jaw.communication.controller.AlertVO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class AlertTableDecorator extends TableDecorator {
	private String id = "id";
	private String fieldName = "_chk";
	String key = null;
	CommonCodeUtil commonCodeUtil = new CommonCodeUtil();
	IStudentGroupListUtilDAO studntGrpListdao;
	IDropDownListService dropDownListService=new DropDownListService();
	IStudentGroupListTagDAO studentGroupListTag=new StudentGroupListTagDAO();
	ApplicationCache applicationCache;
	SessionCache sessionCache;
	private List checkedIds;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
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
		key = pageContext.getRequest().getParameter("rowId");
		applicationCache = (ApplicationCache) pageContext
				.findAttribute(ApplicationConstant.APPLICATION_CACHE);
		sessionCache = (SessionCache) pageContext
				.findAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ServletContext servletContext=pageContext.getServletContext();
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		studntGrpListdao = (StudentGroupListUtilDAO) applicationContext
				.getBean("studntGrpListdao");
		System.out.println("servlet context : "+servletContext);
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);
		
	}
	
	@Override
	public void finish() {
		
		if (!checkedIds.isEmpty()) {
			JspWriter writer = getPageContext().getOut();
			for (Iterator it = checkedIds.iterator(); it.hasNext();) {
				
				String name = (String) it.next();
				StringBuffer buffer = new StringBuffer();
				buffer.append("<input type=\"hidden\" name=\"");
				buffer.append(fieldName);
				buffer.append("\" value=\"");
				buffer.append(name);
				buffer.append("\">");
				try {
					writer.write(buffer.toString());
				}
				catch (IOException e) {
					// should never happen
				}
			}
		}
		
		super.finish();
		
	}
	
	public Integer getSerialno() {
		
		return getListIndex() + 1;
	}
	private List<CommonCode> getcommonCodeList(String key)
			throws CommonCodeNotFoundException {
		return commonCodeUtil.getCommonCodeListByType(applicationCache, key,
				sessionCache.getUserSessionDetails().getInstId(), sessionCache
						.getUserSessionDetails().getBranchId());
	}

	private String getDescriptionByTypeAndCode(String type, String code)
			throws CommonCodeNotFoundException {
		return commonCodeUtil.getDescriptionByTypeAndCode(applicationCache,
				type, code, sessionCache.getUserSessionDetails().getInstId(),
				sessionCache.getUserSessionDetails().getBranchId());
	}
public String getGenericGroups() {
		
	AlertVO alertVO = (AlertVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();
		String targetGrp="";
		for(int i=0;i<alertVO.getGeneralGrpListArray().length;i++){
			if(i!=0){
				targetGrp=targetGrp+",";
			}
			if(alertVO.getGeneralGrpListArray()[i].equals(ApplicationConstant.GENERIC_GRP_STAFF)){
			targetGrp=targetGrp+"Staff";
			}else if(alertVO.getGeneralGrpListArray()[i].equals(ApplicationConstant.GENERIC_GRP_STUDENT)){
				targetGrp=targetGrp+"Student";
			}else if(alertVO.getGeneralGrpListArray()[i].equals(ApplicationConstant.GENERIC_GRP_PARENT)){
				targetGrp=targetGrp+"Parent";
			}else if(alertVO.getGeneralGrpListArray()[i].equals(ApplicationConstant.GENERIC_GRP_MGMT)){
				targetGrp=targetGrp+"Management";
			}
		}
		
			buffer.append(targetGrp);
		
		return buffer.toString();
	}
public String getSpecificGroups() throws CommonCodeNotFoundException, NoDataFoundException{
	AlertVO alertVO = (AlertVO) getCurrentRowObject();
	List<CommonCode> departmentCodes = getcommonCodeList(CommonCodeConstant.DEPARTMENT);
	
	StringBuffer buffer = new StringBuffer();	
	String codeCnst;
	if((alertVO.getReqstCategory().equals(ApplicationConstant.SPECIFIC_GROUPS))&&
			(!alertVO.getSpecificGrpList().equals(""))&&(alertVO.getSpecificGrpList()!=null)){
	System.out.println(alertVO.getGeneralGrpList());
		if(alertVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_STAFF)){
			String specificGrp="";

			if(alertVO.getSpecificGrpList().contains(",")){
				for(int i=0;i<alertVO.getSpecificGrpList().split(",").length;i++){
					if(i==0){
						specificGrp=getDescriptionByTypeAndCode(
								CommonCodeConstant.DEPARTMENT,
								alertVO.getSpecificGrpList());
					}else{
					specificGrp+=ApplicationConstant.COMMA_SEPERATOR+getDescriptionByTypeAndCode(
							CommonCodeConstant.DEPARTMENT,
							alertVO.getSpecificGrpList());
					}
				}
			}else{
				specificGrp=getDescriptionByTypeAndCode(
						CommonCodeConstant.DEPARTMENT,
						alertVO.getSpecificGrpList());
			}
			buffer.append(specificGrp);
			
			}else if((alertVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_STUDENT))||
					(alertVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_PARENT))){
				List<StudentGroupMaster> studentGrpMat=studntGrpListdao.selectStudentGroupList(sessionCache.getUserSessionDetails());
				System.out.println("list size : "+studentGrpMat.size());
				Map<String,String> stuGrpList=new LinkedHashMap<String,String>();
				for(int j=0;j<studentGrpMat.size();j++){
					stuGrpList.put(studentGrpMat.get(j).getStudentGrpId(), studentGrpMat.get(j).getStudentGrp());
				}
				String specificGrp="";
				
				if(alertVO.getSpecificGrpList().contains(",")){
					for(int i=0;i<alertVO.getSpecificGrpList().split(",").length;i++){
						if(i==0){
							specificGrp=stuGrpList.get(alertVO.getSpecificGrpList().split(",")[i]);
						}else{
						specificGrp+=ApplicationConstant.COMMA_SEPERATOR+stuGrpList.get(alertVO.getSpecificGrpList().split(",")[i]);
						}
						}
				}else{
					specificGrp=stuGrpList.get(alertVO.getSpecificGrpList());
				}
				buffer.append(specificGrp);
	
	
			}
	}
	System.out.println("buffer tostring : "+buffer.toString());
	return buffer.toString();
}
}
