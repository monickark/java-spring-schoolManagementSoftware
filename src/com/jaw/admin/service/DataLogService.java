package com.jaw.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaw.admin.controller.DataLogDetailsVO;
import com.jaw.admin.controller.DataLogListVO;
import com.jaw.admin.controller.DataLogSearchVO;
import com.jaw.admin.dao.DataLog;
import com.jaw.admin.dao.DataLogKey;
import com.jaw.admin.dao.IDataLogListDao;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.dao.ComCodeColumn;
import com.jaw.common.util.dao.ComCodeColumnSearch;
import com.jaw.common.util.dao.IComCodeColumnListDao;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Service
public class DataLogService implements IDataLogService {
	Logger logger = Logger.getLogger(DataLogService.class);
	@Autowired
	IDataLogListDao dataLogListDao;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DataLogUtil dataLogUtil;
	@Autowired
	IAuditDao auditDao;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	IComCodeColumnListDao comCodeColumnList;
	
	
	@Override
	public List<DataLogListVO> selectListOfAuditRecords(
			DataLogSearchVO dataLogSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.info("In Service,Going to select List of Data Log Records");
		if (dataLogSearchVO.getBranchId().equals(ApplicationConstant.INSP)) {
			dataLogSearchVO.setBranchId(userSessionDetails.getBranchId());
		}
		DataLogKey dataKey = new DataLogKey();
		commonBusiness.changeObject(dataKey, dataLogSearchVO);
		dataKey.setInstId(userSessionDetails.getInstId());
		dataKey.setAuditFlg(ApplicationConstant.DATA_LOG_CONSTANT);
		List<DataLog> listOfDataLog = dataLogListDao.getDataLogList(dataKey);
		List<DataLogListVO> dataLogListRec = new ArrayList<DataLogListVO>();
		Integer i = 0;
		for (DataLog dataLog : listOfDataLog) {
			DataLogListVO dataLogListVO = new DataLogListVO();
			commonBusiness.changeObject(dataLogListVO, dataLog);
			dataLogListVO.setRowid(i.toString());
			dataLogListRec.add(dataLogListVO);
			i++;
		}		
		
		return dataLogListRec;
	}
	
	@Override
	public Map<String, ArrayList<String>> getDataLogRecFromListRec(
			DataLogListVO dataLogListVO, DataLogDetailsVO dataLogVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException {
		logger.info("In Service,Going to Split the New Record/Old Record values into Column Name and Value");
		Map<String, ArrayList<String>> columnAndRecords = null;
		DataLogKey dataLogKey = new DataLogKey();
		commonBusiness.changeObject(dataLogKey, dataLogListVO);
		dataLogKey.setAuditFlg(ApplicationConstant.DATA_LOG_CONSTANT);
		DataLog dataLog = auditDao.getAuditRecord(dataLogKey);
		
		String typeOfOper = CommonCodeUtil.getDescriptionByCode(
				applicationCache, dataLog.getTypeOfOperation());
		dataLog.setTypeOfOperation(typeOfOper);
		
		ComCodeColumnSearch comCodeColKey = new ComCodeColumnSearch();
		commonBusiness.changeObject(comCodeColKey, dataLogListVO);
		comCodeColKey.setInstId(userSessionDetails.getInstId());
		List<ComCodeColumn> comCodeColList = null;
		try{
			 comCodeColList = comCodeColumnList
					.getCommonCodeColumnList(comCodeColKey);	
		}catch(NoDataFoundException e){
			logger.info("No Enteries in CCCL Table");
			}		
		
		columnAndRecords = DataLogUtil.getDataLogMessage(dataLog,
				comCodeColList,applicationCache);
		commonBusiness.changeObject(dataLogVO, dataLog);	
		return columnAndRecords;
	}
	
	@Override
	public Map<String,ArrayList<String>> getTableKey(DataLogDetailsVO dataLog,ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.info("In Service,Going to Split the table key value into Column Name and Value");
		ComCodeColumnSearch comCodeColKey = new ComCodeColumnSearch();
		commonBusiness.changeObject(comCodeColKey, dataLog);
		comCodeColKey.setInstId(userSessionDetails.getInstId());
		List<ComCodeColumn> comCodeColList = null;
		try{
			 comCodeColList = comCodeColumnList
					.getCommonCodeColumnList(comCodeColKey);	
		}catch(NoDataFoundException e){
			logger.info("No Enteries in CCCL Table");
			}	
		/*  comCodeColumnList
				.getCommonCodeColumnList(comCodeColKey);*/
		Map<String,ArrayList<String>> tableColAndValue = DataLogUtil.splitTableKey(dataLog.getTableKey(), comCodeColList,applicationCache,userSessionDetails);		
		return tableColAndValue;
	}
	
}
