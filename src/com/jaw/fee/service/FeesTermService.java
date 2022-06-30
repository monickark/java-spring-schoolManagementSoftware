package com.jaw.fee.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.fee.controller.FeeTermVO;
import com.jaw.fee.dao.FeeTerms;
import com.jaw.fee.dao.FeeTermsKey;
import com.jaw.fee.dao.ITermFeeDAO;
import com.jaw.fee.dao.ITermFeeListDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
@Service
public class FeesTermService implements IFeesTermService {
	// Logging
	Logger logger = Logger.getLogger(FeesTermService.class);
	@Autowired
	DoAudit doAudit;
    @Autowired
    ITermFeeListDAO termFeesListDAO;
    @Autowired
    ITermFeeDAO termFeesDAO;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired 
	CommonCodeUtil commonCodeUtil;
	@Override
	public Map<String, String> getFeeTermList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeeTermsKey feeTermsKey=new FeeTermsKey();
		feeTermsKey.setInstId(userSessionDetails.getInstId());
		feeTermsKey.setBranchId(userSessionDetails.getBranchId());
		List<FeeTerms> feeTermList=termFeesListDAO.selectFeeTermList(feeTermsKey);
		Map<String,String> feeTrmMaps=new LinkedHashMap<String,String>();
		for(int i=0;i<feeTermList.size();i++){
			feeTrmMaps.put(feeTermList.get(i).getFeeTerm(), feeTermList.get(i).getFeeTermValue());
		}
		return feeTrmMaps;
	}
	@Override
	public Map<String, String> getFeePaymentTermList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException {
		FeeTermsKey feeTermsKey=new FeeTermsKey();
		feeTermsKey.setInstId(userSessionDetails.getInstId());
		feeTermsKey.setBranchId(userSessionDetails.getBranchId());
		List<FeeTerms> feePayTrmList=termFeesListDAO.selectFeePaymentTermList(feeTermsKey);
		Map<String,String> feeTrmMaps=new LinkedHashMap<String,String>();
		for(int i=0;i<feePayTrmList.size();i++){
			feeTrmMaps.put(feePayTrmList.get(i).getFeePaymentTerm(), feePayTrmList.get(i).getFeePaymentTermValue());
		}
		return feeTrmMaps;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void inserttermFeesBatchRec(String[] feeTerm, String feePaymentTerm,
			UserSessionDetails userSessionDetails)
			throws  
			DatabaseException, NoDataFoundException, UpdateFailedException, DuplicateEntryException {
		List<FeeTerms> feeTrmList=new ArrayList<FeeTerms>();
		for(int i=0;i<feeTerm.length;i++){
			FeeTerms feeTerms=new FeeTerms();
			feeTerms.setInstId(userSessionDetails.getInstId());
			feeTerms.setBranchId(userSessionDetails.getBranchId());
			feeTerms.setFeePaymentTerm(feePaymentTerm);
			feeTerms.setFeeTerm(feeTerm[i]);
			feeTerms.setDbTs(1);
			feeTerms.setDelFlag("N");
			feeTerms.setrCreId(userSessionDetails.getUserId());
			feeTerms.setrModId(userSessionDetails.getUserId());
			feeTrmList.add(feeTerms);
		}
		try {
			termFeesListDAO.insertFeeTermListRecs(feeTrmList);
			// functional audit
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.FEES_TERM_INSERT, "");
			logger.debug("Completed Functional Auditing");
		} catch (DuplicateEntryException e) {
			System.out.println("duplicate entry catch");
			List<FeeTerms> feesTrmList=new ArrayList<FeeTerms>();
			List<FeeTermsKey> feesTrmKeyList=new ArrayList<FeeTermsKey>();
			FeeTermsKey feeTermsKey=new FeeTermsKey();
			feeTermsKey.setInstId(userSessionDetails.getInstId());
			feeTermsKey.setBranchId(userSessionDetails.getBranchId());
			feeTermsKey.setFeePaymentTerm(feePaymentTerm);
			System.out.println("fee reeee length"+feeTerm.length);
			for(int i=0;i<feeTerm.length;i++){

				feeTermsKey.setFeesTerm(feeTerm[i]);
				FeeTerms selectFeeTrm=termFeesDAO.selectFeesTermRecDelFlgY(feeTermsKey);
				System.out.println("fee reeee"+selectFeeTrm);
				if(selectFeeTrm!=null){
				FeeTerms feeTerms=new FeeTerms();
				feeTerms.setrModId(userSessionDetails.getUserId());
				feeTerms.setDbTs(selectFeeTrm.getDbTs());
				FeeTermsKey feeTermsKeyU=new FeeTermsKey();
				feeTermsKeyU.setDbTs(selectFeeTrm.getDbTs());
				feeTermsKeyU.setInstId(userSessionDetails.getInstId());
				feeTermsKeyU.setBranchId(userSessionDetails.getBranchId());
				feeTermsKeyU.setFeesTerm(selectFeeTrm.getFeeTerm());
				feeTermsKeyU.setFeePaymentTerm(selectFeeTrm.getFeePaymentTerm());
				feesTrmList.add(feeTerms);
				feesTrmKeyList.add(feeTermsKeyU);
				}
			}
			if(feesTrmList.size()==0){
				throw new DuplicateEntryException();
			}
			termFeesListDAO.updateTermFeesRecDelFlgYesToNo(feesTrmList, feesTrmKeyList);
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.FEES_TERM_INSERT, "Fee Term inserted from delflg Y to N");
			logger.debug("Completed Functional Auditing");
		}
	}
	@Override
	public List<FeeTermVO> getFeeTermsList(
			ApplicationCache applicationCache,SessionCache sessionCache) throws NoDataFoundException {
		FeeTermsKey feeTermsKey=new FeeTermsKey();
		feeTermsKey.setInstId(sessionCache.getUserSessionDetails().getInstId());
		feeTermsKey.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
		List<FeeTermVO> feeTrmVos=new ArrayList<FeeTermVO>();
		List<FeeTerms> feeTrmList=termFeesListDAO.selectTermFeesListRecs(feeTermsKey);
		LinkedHashMap<String,List<FeeTerms>> objectsByFeePayTrm =
		        new LinkedHashMap<String,List<FeeTerms>>();
		    for (FeeTerms obj : feeTrmList) {
		        List<FeeTerms> feeTrList = objectsByFeePayTrm.get(obj.getFeePaymentTerm());
		        if (feeTrList == null) {
		            objectsByFeePayTrm.put(obj.getFeePaymentTerm(), feeTrList = new ArrayList<FeeTerms>());
		        }
		        feeTrList.add(obj);          
		    }
		    
		  
		    
		    Iterator it1 = objectsByFeePayTrm.entrySet().iterator();
		    while (it1.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it1.next();
		        FeeTermVO feeTrmVo=new FeeTermVO();
		        feeTrmVo.setFeePaymentTerm((String)pairs.getKey());
		        feeTrmVo.setFeePaymentTermValue(commonCodeUtil.getDescriptionByTypeAndCode(applicationCache,
			    		CommonCodeConstant.FEE_PAYMENT_TERM,
			    		(String)pairs.getKey(), sessionCache.getUserSessionDetails().getInstId(),
						sessionCache.getUserSessionDetails().getBranchId()));
		        System.out.println("Map"+pairs.getKey() + " = " + pairs.getValue());
		        List<FeeTerms> feeTerm=(List<FeeTerms>) pairs.getValue();
		        List<String> feeTrma=new ArrayList<String>();
		        List<String> feeTrmaValue=new ArrayList<String>();
		        for(int as=0;as<feeTerm.size();as++){
		        	feeTrma.add(feeTerm.get(as).getFeeTerm());
		        	feeTrmaValue.add(commonCodeUtil.getDescriptionByTypeAndCode(applicationCache,
		    		CommonCodeConstant.FEE_TERM,
		    		feeTerm.get(as).getFeeTerm(), sessionCache.getUserSessionDetails().getInstId(),
					sessionCache.getUserSessionDetails().getBranchId()));
		        }
		        feeTrmVo.setFeeTerm(feeTrma.toArray(new String[feeTrma.size()]));
		        feeTrmVo.setFeeTermValue(feeTrmaValue.toArray(new String[feeTrmaValue.size()]));
		        System.out.println("fee term vo : "+feeTrmVo.toString());
		        feeTrmVos.add(feeTrmVo);
		    }
		return feeTrmVos;
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteFeeTermRec(String[] feeTerm, String feePaymentTerm,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException, DuplicateEntryException, DatabaseException  {
		
		
		FeeTermsKey feeTermsKey = new FeeTermsKey();
		feeTermsKey.setBranchId(userSessionDetails.getBranchId());
		feeTermsKey.setInstId(userSessionDetails.getInstId());
		feeTermsKey.setFeePaymentTerm(feePaymentTerm);
		feeTermsKey.setFeeTerm(feeTerm);
		FeeTerms feeTerms=new FeeTerms();
		feeTerms.setrModId(userSessionDetails.getUserId());
		termFeesDAO.deleteTermFeesRec(feeTerms, feeTermsKey);
		// functional audit
				doAudit.doFunctionalAudit(userSessionDetails,
						AuditConstant.FEES_TERM_DELETE, " ");
				logger.debug("Completed Functional Auditing");
				
				

	}

}
