package com.jaw.communication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarListDAO;
import com.jaw.core.dao.IAcademicCalendarListDAO;
import com.jaw.framework.dao.BaseDao;
@Repository
public class NoticeBoardListDAO extends BaseDao implements
INoticeBoardListDAO {
Logger logger = Logger.getLogger(NoticeBoardListDAO.class);

@Override
public List<NoticeBoard> getNoticeBoardList(
		NoticeBoardListKey noticeBoardListKey) throws NoDataFoundException {
	logger.debug("DAO :Inside Notice Board List select method");
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			 .append("NOTICE_SER_NO,")
				.append("AC_TERM,")
				.append("NOTICE_TYPE,")
				.append("NOTICE_NAME,")
				.append("NOTICE_DESC,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("INFORM_PARENT,")
				.append("AS_IMPORTANT")
			.append(" from ncbd ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");
		data.add(noticeBoardListKey.getInstId());
		data.add(noticeBoardListKey.getBranchId());
		data.add("N");
		
		if ((noticeBoardListKey.getAcTerm() !=null)&&(!noticeBoardListKey.getAcTerm().equals(""))
				) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC Term Value :" + noticeBoardListKey.getAcTerm());
			data.add(noticeBoardListKey.getAcTerm());
		}
		if ((noticeBoardListKey.getNoticeType() !=null)&&(!noticeBoardListKey.getNoticeType().equals(""))
				) {
			sql.append(" and NOTICE_TYPE=?  ");
			logger.debug("NOTICE_TYPE Value :" + noticeBoardListKey.getNoticeType());
			data.add(noticeBoardListKey.getNoticeType());
		}
		
		if ((noticeBoardListKey.getFromDate() !=null)&&(!noticeBoardListKey.getFromDate().equals("")
				&&(noticeBoardListKey.getToDate()!=null)&&(!noticeBoardListKey.getToDate().equals(""))))
				{			
			sql.append(" and (FROM_DATE>=? or TO_DATE>=?)");
			logger.debug("FROM_DATE :" + noticeBoardListKey.getFromDate()+"   TO_DATE :"+noticeBoardListKey.getToDate());
			data.add(noticeBoardListKey.getFromDate());
			data.add(noticeBoardListKey.getToDate());
		}
		 sql.append(" order by FROM_DATE ");
		 
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<NoticeBoard> selectedListNoticeBoard = getJdbcTemplate()
			.query(sql.toString(), array, new NoticeBoardSelectRowMapper());
	if (selectedListNoticeBoard.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicCalendar List value"+selectedListNoticeBoard.size() );
	return selectedListNoticeBoard;
	}

@Override
public List<NoticeBoard> getNoticeBoardListForDashBoard(
		NoticeBoardListKey noticeBoardListKey,String profileGroup) throws NoDataFoundException {
	logger.debug("DAO :Inside Notice Board List select method");
	System.out.println("profile groupppppppppppppppp"+profileGroup);


	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			 .append("NOTICE_NAME,")
				.append("NOTICE_DESC,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("INFORM_PARENT,")
				.append("AS_IMPORTANT,")
				.append("TO_DATE")			
			.append(" from ncbd")
			.append(" where")			
			.append("   INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?")
		 .append(" and  AC_TERM=?")
		  .append(" and  NOTICE_TYPE=?");
	    
		 
			if(profileGroup.equals(ApplicationConstant.PG_PARENT)){
				sql .append(" and  INFORM_PARENT='"+ApplicationConstant.INFORM_PARENT+"'");	
			}
	
	    sql.append(" order by FROM_DATE, TO_DATE");
		data.add(noticeBoardListKey.getInstId());
		data.add(noticeBoardListKey.getBranchId());
		data.add("N");
		data.add(noticeBoardListKey.getAcTerm());
		data.add(noticeBoardListKey.getNoticeType());
		/*data.add(noticeBoardListKey.getFromDate());		
		data.add(noticeBoardListKey.getToDate());
		data.add(noticeBoardListKey.getFromDate());		
		data.add(noticeBoardListKey.getToDate());*/
		System.out.println("queryyyyyyyyyyyyyyyyyyyyyyyy"+sql.toString());
		
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<NoticeBoard> selectedListNoticeBoard = getJdbcTemplate()
			.query(sql.toString(), array, new NoticeBoardDetailsRowMapper());
	if (selectedListNoticeBoard.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicCalendar List value"+selectedListNoticeBoard.size() );
	return selectedListNoticeBoard;
	}

}






class NoticeBoardSelectRowMapper implements RowMapper<NoticeBoard> {

	@Override
	public NoticeBoard mapRow(ResultSet rs, int arg1) throws SQLException {
		NoticeBoard noticeBoard = new NoticeBoard();	
	noticeBoard.setNoticeSerialNo(rs.getString("NOTICE_SER_NO"));
	noticeBoard.setAcTerm(rs.getString("AC_TERM"));
	noticeBoard.setNoticeType(rs.getString("NOTICE_TYPE"));
	noticeBoard.setNoticeName(rs.getString("NOTICE_NAME"));
	noticeBoard.setNoticeDesc(rs.getString("NOTICE_DESC"));
	noticeBoard.setFromDate(rs.getString("FROM_DATE"));
	noticeBoard.setToDate(rs.getString("TO_DATE"));
	noticeBoard.setInformParent(rs.getString("INFORM_PARENT"));
	noticeBoard.setIsImportant(rs.getString("AS_IMPORTANT"));
System.out.println("notice descriptionnnnnnnnnnnnnnnnnnnn"+rs.getString("NOTICE_DESC"));
	return noticeBoard;
	}
	}
class NoticeBoardDetailsRowMapper implements RowMapper<NoticeBoard> {

	@Override
	public NoticeBoard mapRow(ResultSet rs, int arg1) throws SQLException {
		NoticeBoard noticeBoard = new NoticeBoard();
	
	noticeBoard.setNoticeName(rs.getString("NOTICE_NAME"));
	noticeBoard.setNoticeDesc(rs.getString("NOTICE_DESC"));
	noticeBoard.setFromDate(rs.getString("FROM_DATE"));
	noticeBoard.setToDate(rs.getString("TO_DATE"));
	noticeBoard.setInformParent(rs.getString("INFORM_PARENT"));
	noticeBoard.setIsImportant(rs.getString("AS_IMPORTANT"));

	return noticeBoard;
	}
	}
