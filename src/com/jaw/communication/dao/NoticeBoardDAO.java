package com.jaw.communication.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarDAO;
import com.jaw.framework.dao.BaseDao;
@Repository
public class NoticeBoardDAO extends BaseDao implements INoticeBoardDAO {
	// Logging
		Logger logger = Logger.getLogger(NoticeBoardDAO.class);
		
	@Override
	public void insertNoticeBoardRec(final NoticeBoard noticeBoardRecord)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("NoticeBoard object values :"
				+ noticeBoardRecord.toString());
		
		StringBuffer query = new StringBuffer();
		query = query.append("insert into ncbd ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("NOTICE_SER_NO,")
				.append("AC_TERM,")
				.append("NOTICE_TYPE,")
				.append("NOTICE_NAME,")
				.append("NOTICE_DESC,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("INFORM_PARENT,")
				.append("AS_IMPORTANT,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, noticeBoardRecord.getDbTs());
							pss.setString(2, noticeBoardRecord.getInstId());
							pss.setString(3, noticeBoardRecord.getBranchId());
							pss.setString(4, noticeBoardRecord.getNoticeSerialNo());
							pss.setString(5, noticeBoardRecord.getAcTerm().trim());
							pss.setString(6, noticeBoardRecord.getNoticeType().trim());
							pss.setString(7, noticeBoardRecord.getNoticeName().trim());
							pss.setString(8, noticeBoardRecord.getNoticeDesc().trim());
							pss.setString(9, noticeBoardRecord.getFromDate());
							pss.setString(10, noticeBoardRecord.getToDate());
							pss.setString(11, noticeBoardRecord.getInformParent());
							pss.setString(12, noticeBoardRecord.getIsImportant());
							pss.setString(13, noticeBoardRecord.getDelFlag().trim());
							pss.setString(14, noticeBoardRecord.getrModId().trim());
							pss.setString(15, noticeBoardRecord.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
	}

	@Override
	public void updateNoticeBoardRec(final NoticeBoard noticeBoardRecord,
			final NoticeBoardKey noticeBoardKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Notice Board object values :"+ noticeBoardRecord.toString());
		logger.debug("NoticeBoardKey object values :"+ noticeBoardKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update ncbd set")
		        .append(" DB_TS= ?,")
		        .append("NOTICE_TYPE= ?,")
				.append("NOTICE_NAME= ?,")
				.append("NOTICE_DESC= ?,")
				.append("FROM_DATE= ?,")
				.append("TO_DATE= ?,")
				.append("INFORM_PARENT= ?,")
				.append("AS_IMPORTANT= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  NOTICE_SER_NO= ?")				
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, noticeBoardRecord.getDbTs() + 1);
						ps.setString(2, noticeBoardRecord.getNoticeType().trim());
						ps.setString(3, noticeBoardRecord.getNoticeName().trim());
						ps.setString(4, noticeBoardRecord.getNoticeDesc().trim());
						ps.setString(5, noticeBoardRecord.getFromDate());
						ps.setString(6, noticeBoardRecord.getToDate());
						ps.setString(7, noticeBoardRecord.getInformParent());
						ps.setString(8, noticeBoardRecord.getIsImportant());
						ps.setString(9, noticeBoardRecord.getrModId().trim());
						ps.setInt(10, noticeBoardKey.getDbTs());
						ps.setString(11, noticeBoardKey.getInstId().trim());
						ps.setString(12, noticeBoardKey.getBranchId().trim());
						ps.setString(13, noticeBoardKey.getNoticeSerialNo());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteNoticeBoardRec(final NoticeBoard noticeBoardRecord,
			final NoticeBoardKey noticeBoardKey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Notice Board object values :"+ noticeBoardRecord.toString());
		logger.debug("Notice Board Key object values :"+ noticeBoardKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update ncbd set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  NOTICE_SER_NO= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, noticeBoardRecord.getDbTs());
						ps.setString(2, noticeBoardRecord.getrModId().trim());
						ps.setInt(3, noticeBoardKey.getDbTs());
						ps.setString(4, noticeBoardKey.getInstId().trim());
						ps.setString(5, noticeBoardKey.getBranchId().trim());
						ps.setString(6, noticeBoardKey.getNoticeSerialNo().trim());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception");
			throw new DeleteFailedException();

		}
	}

	@Override
	public NoticeBoard selectNoticeBoardRec(NoticeBoardKey noticeBoardKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("NoticeBoardKey object values :"+ noticeBoardKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
			     .append("NOTICE_SER_NO,")
				.append("AC_TERM,")
				.append("NOTICE_TYPE,")
				.append("NOTICE_NAME,")
				.append("NOTICE_DESC,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("INFORM_PARENT,")
				.append("AS_IMPORTANT,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from ncbd ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  NOTICE_SER_NO= ?")				
				.append(" and  DEL_FLG=?");
		    data.add(noticeBoardKey.getInstId());
			data.add(noticeBoardKey.getBranchId());
			data.add(noticeBoardKey.getNoticeSerialNo());
			data.add("N");
			
			if ((noticeBoardKey.getDbTs() !=null)&&(noticeBoardKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + noticeBoardKey.getDbTs());
				data.add(noticeBoardKey.getDbTs());
			}
			NoticeBoard selectedListNoticeBoard = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListNoticeBoard = (NoticeBoard) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<NoticeBoard>() {

					@Override
					public NoticeBoard extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						NoticeBoard noticeBoard = null;
						if (rs.next()) {						
							
							
							noticeBoard = new NoticeBoard();
							noticeBoard.setDbTs(rs.getInt("DB_TS"));
							noticeBoard.setInstId(rs.getString("INST_ID"));
							noticeBoard.setBranchId(rs.getString("BRANCH_ID"));
							noticeBoard.setNoticeSerialNo(rs.getString("NOTICE_SER_NO"));
							noticeBoard.setAcTerm(rs.getString("AC_TERM"));
							noticeBoard.setNoticeType(rs.getString("NOTICE_TYPE"));
							noticeBoard.setNoticeName(rs.getString("NOTICE_NAME"));
							noticeBoard.setNoticeDesc(rs.getString("NOTICE_DESC"));
							noticeBoard.setFromDate(rs.getString("FROM_DATE"));
							noticeBoard.setToDate(rs.getString("TO_DATE"));
							noticeBoard.setInformParent(rs.getString("INFORM_PARENT"));
							noticeBoard.setIsImportant(rs.getString("AS_IMPORTANT"));
							noticeBoard.setDelFlag((rs.getString("DEL_FLG")));
							noticeBoard.setrModId(rs.getString("R_MOD_ID"));
							noticeBoard.setrModTime(rs.getString("R_MOD_TIME"));
							noticeBoard.setrCreId(rs.getString("R_CRE_ID"));
							noticeBoard.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return noticeBoard;
					}

				});

		if (selectedListNoticeBoard == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListNoticeBoard;
	}

}
