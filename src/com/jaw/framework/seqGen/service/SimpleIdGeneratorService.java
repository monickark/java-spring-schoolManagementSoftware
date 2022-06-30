package com.jaw.framework.seqGen.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.dao.IIdGeneratorDao;

//Service Class for Sinmple Sequnece Generation
@Service("simpleIdGenerator")
public class SimpleIdGeneratorService extends BaseDao implements
		IIdGeneratorService {

	// Logging
	Logger logger = Logger.getLogger(SimpleIdGeneratorService.class);

	@Autowired
	@Qualifier(value = "simpleIdGenDao")
	IIdGeneratorDao simpleIdGendao;

	private static Object acquireLockForIDGeneration = new Object();
	Integer currentSeqValue = 0;

	// Method to get Simple sequence
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	// sequence id generation is kept outside the originating transaction scope.
	public Integer getNextId(String key, int granularity)
			throws DatabaseException {

		synchronized (acquireLockForIDGeneration) {
			for (int count = 1; count <= granularity; count++) {
				currentSeqValue = simpleIdGendao.getIdForSequence(key);
			}
		}

		return currentSeqValue - (granularity - 1);
	}

}
