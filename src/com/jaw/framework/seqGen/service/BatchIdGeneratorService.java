package com.jaw.framework.seqGen.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.dao.IIdGeneratorDao;

//Service Class for Batch Sequnece Generation
@Service("batchIdGen")
public class BatchIdGeneratorService extends BaseDao implements
		IIdGeneratorService {

	// Logging
	Logger logger = Logger.getLogger(BatchIdGeneratorService.class);

	private static Object acquireLockForIDGeneration = new Object();

	@Autowired
	@Qualifier("batchIdGenDao")
	private IIdGeneratorDao batchIdGeneratorDao;

	Integer currentSeq = 0;
	Integer maxSeq = 0;

	// Method to get the Batch of sequences
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	// sequence id generation is kept outside the originating transaction scope.
	public Integer getNextId(String key, int granularity)
			throws DatabaseException {

		synchronized (acquireLockForIDGeneration) {

			if ((currentSeq == 0) && (maxSeq == 0)) {
				currentSeq = batchIdGeneratorDao.getIdForSequence(key);
				maxSeq = currentSeq + ApplicationConstant.BATCH_ID_GEN_INC;
			} else {
				currentSeq = currentSeq + granularity;
			}
			if (currentSeq >= maxSeq) {
				currentSeq = batchIdGeneratorDao.getIdForSequence(key);
				maxSeq = currentSeq + ApplicationConstant.BATCH_ID_GEN_INC;
			}

		}
		return currentSeq;
	}

}
