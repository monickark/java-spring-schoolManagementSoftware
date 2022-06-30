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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
import com.jaw.communication.controller.SMSRequestListVO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;

public class SMSHistoryTableDecorator extends TableDecorator {
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
		String[] params = pageContext.getRequest()
				.getParameterValues(fieldName);
		checkedIds = params != null ? new ArrayList(Arrays.asList(params))
				: new ArrayList(0);
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
	public String getRequestCategory() {
		
		SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();
			StringBuffer buffer = new StringBuffer();	
			
				if(smsRequestListVO.getReqstCategory().equals(ApplicationConstant.GENERIC_GROUPS)){
					buffer.append("Generic Group");
					}else if(smsRequestListVO.getReqstCategory().equals(ApplicationConstant.SPECIFIC_GROUPS)){
						buffer.append("Specific Group");
					}else if(smsRequestListVO.getReqstCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)){
						buffer.append("Specific Member Group");
					}
			
			return buffer.toString();
		}
	
public String getGeneralGroup() {
		
	SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();
		StringBuffer buffer = new StringBuffer();		
		List<String> generalgrp=new ArrayList<String>();
		if(smsRequestListVO.getGeneralGrpList().contains(",")){
			for(int i=0;i<smsRequestListVO.getGeneralGrpList().split(",").length;i++){
				generalgrp.add(smsRequestListVO.getGeneralGrpList().split(",")[i]);
			}			
		}else{
			generalgrp.add(smsRequestListVO.getGeneralGrpList());
		}
		String genGrp="";
		for(int i=0;i<generalgrp.size();i++){
			if(i!=0){
				genGrp=genGrp+",";
			}
			if(generalgrp.get(i).equals(ApplicationConstant.GENERIC_GRP_STAFF)){
				genGrp=genGrp+"Staff";
				}else if(generalgrp.get(i).equals(ApplicationConstant.GENERIC_GRP_STUDENT)){
					genGrp=genGrp+"Student";
				}else if(generalgrp.get(i).equals(ApplicationConstant.GENERIC_GRP_PARENT)){
					genGrp=genGrp+"Parent";
				}else if(generalgrp.get(i).equals(ApplicationConstant.GENERIC_GRP_MGMT)){
					genGrp=genGrp+"Management";
				}
		}
		
			buffer.append(genGrp);
		
		
		
		
		
		return buffer.toString();
	}
public String getSpecificGroup() throws CommonCodeNotFoundException, NoDataFoundException {
	
	SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();
	
	List<CommonCode> departmentCodes = getcommonCodeList(CommonCodeConstant.DEPARTMENT);
	
		StringBuffer buffer = new StringBuffer();	
		String codeCnst;
		if((smsRequestListVO.getReqstCategory().equals(ApplicationConstant.SPECIFIC_GROUPS))&&
				(!smsRequestListVO.getSpecificGrpList().equals(""))&&(smsRequestListVO.getSpecificGrpList()!=null)){
		
			if(smsRequestListVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_STAFF)){
				String specificGrp="";
	
				if(smsRequestListVO.getSpecificGrpList().contains(",")){
					for(int i=0;i<smsRequestListVO.getSpecificGrpList().split(",").length;i++){
						if(i==0){
							specificGrp=getDescriptionByTypeAndCode(
									CommonCodeConstant.DEPARTMENT,
									smsRequestListVO.getSpecificGrpList());
						}else{
						specificGrp+=ApplicationConstant.COMMA_SEPERATOR+getDescriptionByTypeAndCode(
								CommonCodeConstant.DEPARTMENT,
								smsRequestListVO.getSpecificGrpList());
						}
					}
				}else{
					specificGrp=getDescriptionByTypeAndCode(
							CommonCodeConstant.DEPARTMENT,
							smsRequestListVO.getSpecificGrpList());
				}
				buffer.append(specificGrp);
				
				}else if((smsRequestListVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_STUDENT))||
						(smsRequestListVO.getGeneralGrpList().equals(ApplicationConstant.GENERIC_GRP_PARENT))){
					List<StudentGroupMaster> studentGrpMat=studntGrpListdao.selectStudentGroupList(sessionCache.getUserSessionDetails());
					Map<String,String> stuGrpList=new LinkedHashMap<String,String>();
					for(int j=0;j<studentGrpMat.size();j++){
						stuGrpList.put(studentGrpMat.get(j).getStudentGrpId(), studentGrpMat.get(j).getStudentGrp());
					}
					String specificGrp="";
					
					if(smsRequestListVO.getSpecificGrpList().contains(",")){
						for(int i=0;i<smsRequestListVO.getSpecificGrpList().split(",").length;i++){
							if(i==0){
								specificGrp=stuGrpList.get(smsRequestListVO.getSpecificGrpList().split(",")[i]);
							}else{
							specificGrp+=ApplicationConstant.COMMA_SEPERATOR+stuGrpList.get(smsRequestListVO.getSpecificGrpList().split(",")[i]);
							}
							}
					}else{
						specificGrp=stuGrpList.get(smsRequestListVO.getSpecificGrpList());
					}
					buffer.append(specificGrp);
		
		
				}
		}
		return buffer.toString();
	}
    public String getSpecificMemberList(){
        SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();		
		StringBuffer buffer = new StringBuffer();
		if((smsRequestListVO.getSpecificMembrList()!=null)&&(!smsRequestListVO.getSpecificMembrList().equals(""))){
	
		
		buffer.append("<div style='align:left;'><i class='icon-info-sign smsRqstIconClick quick-info' title='Description' style='float: center;'></i>");
		buffer.append("<input type='hidden' class='smsRqstMobNumText' value='"+smsRequestListVO.getSpecificMembrList()+"'></div>");
	
		}
		return buffer.toString();
    }
    public String getRequestStatus(){
        SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();		
		StringBuffer buffer = new StringBuffer();
		if(smsRequestListVO.getReqstStatus().equals(ApplicationConstant.SMS_RQST_ENTERED)){
			buffer.append("Request Entered");
			}else if(smsRequestListVO.getReqstStatus().equals(ApplicationConstant.IN_PROGRESS)){
				buffer.append("In Progress");
			}else if(smsRequestListVO.getReqstStatus().equals(ApplicationConstant.CLOSED)){
				buffer.append("Closed");
			}
	
		return buffer.toString();
    }
    public String getSmsDetails(){
        SMSRequestListVO smsRequestListVO = (SMSRequestListVO) getCurrentRowObject();		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<a href=smsHistoryList.htm?smsDetails&rowId=")
			.append(smsRequestListVO.getRowId())
			.append(" class='icon-file details-info' title=' Details' id='smsDetailsId'></a>");
	
		return buffer.toString();
    }
}


