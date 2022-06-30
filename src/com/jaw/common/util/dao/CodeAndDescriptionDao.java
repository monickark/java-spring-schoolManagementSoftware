package com.jaw.common.util.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jaw.framework.dao.BaseDao;

@Repository
public class CodeAndDescriptionDao extends BaseDao implements
		ICodeAndDescriptionDao {
	Logger logger = Logger.getLogger(CodeAndDescriptionDao.class);

	@Override
	public String getStudentGrpName(final String instId, final String branchId,
			final String stGroup) {
		String sql = "SELECT student_grp FROM studentgrpmaster where inst_id='"
				+ instId + "' and branch_id='" + branchId
				+ "' and del_flg='N' and studentgrp_id='" + stGroup + "'";
		String stGroupname = getJdbcTemplate().queryForObject(sql.toString(),
				String.class);
		System.out.println("stGroupname:" + stGroupname);
		logger.info("In Dao Layer,CommonCodeColumnRec has been successfully retrieved");
		return stGroupname;
	}

}
