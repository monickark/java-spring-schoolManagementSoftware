package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class TeacherSubjectLinkListDAO extends BaseDao implements
		ITeacherSubjectLinkListDAO {
	Logger logger = Logger.getLogger(TeacherSubjectLinkListDAO.class);

	@Override
	public List<TeacherSubjectLink> selectTeacherSubLinkList(
			TeacherSubjectLinkListKey teacherSubLinkListKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside Teacher Subject Link List select method");
		logger.debug("Tsrl key :" + teacherSubLinkListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" TRSL_ID,").append("STAFF_ID,")
				.append("SUB_ID").append(" from trsl ").append(" where")
				.append("  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?");
		data.add(teacherSubLinkListKey.getInstId());
		data.add(teacherSubLinkListKey.getBranchId());
		data.add("N");
		if ((teacherSubLinkListKey.getStaffId() != null)
				&& (teacherSubLinkListKey.getStaffId() != "")) {
			sql.append(" and STAFF_ID=?  ");
			logger.debug("STAFF_ID  :" + teacherSubLinkListKey.getStaffId());
			data.add(teacherSubLinkListKey.getStaffId());
		}
		if ((teacherSubLinkListKey.getSubId() != null)
				&& (teacherSubLinkListKey.getSubId() != "")) {
			sql.append(" and SUB_ID=?  ");
			logger.debug("SUB_ID  :" + teacherSubLinkListKey.getSubId());
			data.add(teacherSubLinkListKey.getSubId());
		}

		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<TeacherSubjectLink> selectedListTrSubLink = getJdbcTemplate()
				.query(sql.toString(), array,
						new TeacherSubjectLinkSelectRowMapper());

		if (selectedListTrSubLink.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Teacher Subject Link List value"
				+ selectedListTrSubLink.size());
		return selectedListTrSubLink;
	}

	@Override
	public Map<String, String> selectStaffList(
			TeacherSubjectLink teacherSubjectLink) throws NoDataFoundException {
		logger.debug("DAO :Inside Teacher Subject Link List select method");
		logger.debug("SUB_ID  :" + teacherSubjectLink.getSubId());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("t.STAFF_ID, ").append("s.staff_name ")
				.append("from trsl t,stfm s ").append("where ")
				.append("t.staff_id=s.staff_id and ")
				.append("t.inst_id=s.inst_id and ")
				.append("t.branch_id=s.branch_id and ")
				.append("t.del_flg=s.del_flg and  ").append("  t.inst_id=?")
				.append("  and t.branch_id=?").append(" and  t.DEL_FLG=?");
		data.add(teacherSubjectLink.getInstId());
		data.add(teacherSubjectLink.getBranchId());
		data.add("N");

		if ((teacherSubjectLink.getSubId() != null)
				&& (teacherSubjectLink.getSubId() != "")) {
			sql.append(" and SUB_ID=?  ");
			logger.debug("SUB_ID  :" + teacherSubjectLink.getSubId());
			data.add(teacherSubjectLink.getSubId());
		}
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		Map<String, String> staffList = getJdbcTemplate().query(sql.toString(),
				array, new StaffSelectExtractor());

		if (staffList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : staff map size :" + staffList.size());
		return staffList;
	}

	@Override
	public List<TeacherSubjectLinkList> getStaffListForTransferProcess(
			final TeacherSubjectLinkList teacherSubjectLinkList)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getCourseSubjectLinkList"
				+ teacherSubjectLinkList.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("TRSL_ID, ").append("STAFF_ID ")
				.append("from trsl   ").append("where ").append("  inst_id=?")
				.append(" and branch_id=?").append(" and  DEL_FLG=?")
				.append(" and staff_id=? ");

		List<TeacherSubjectLinkList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, teacherSubjectLinkList.getInstId());
						pss.setString(2, teacherSubjectLinkList.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, teacherSubjectLinkList.getStaffId());

					}
				}, new TeacherSubjectListStaffTransfer());
		logger.debug("list size returned :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public void updateStaffDataOnTransfer(
			final List<TeacherSubjectLinkList> teacherSubjectLinkLists)
			throws UpdateFailedException {
		logger.debug("Inside Holiday List update Record");
		logger.debug("Holiday List Size   " + teacherSubjectLinkLists.size());
		StringBuffer query = new StringBuffer();

		query.append("update trsl set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'T',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  TRSL_ID= ?")
				.append(" and  DEL_FLG='N'");

		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						TeacherSubjectLinkList teacherSubjectLinkList = teacherSubjectLinkLists.get(i);
						System.out.println("update records :"
								+ teacherSubjectLinkList.toString());
						pss.setInt(1, teacherSubjectLinkList.getDbTs()+1);
						pss.setString(2, teacherSubjectLinkList.getrModId().trim());

						pss.setInt(3, teacherSubjectLinkList.getDbTs());
						pss.setString(4, teacherSubjectLinkList.getInstId().trim());
						pss.setString(5, teacherSubjectLinkList.getBranchId().trim());
						pss.setString(6, teacherSubjectLinkList.getTrslId().trim());
					}

					@Override
					public int getBatchSize() {
						return teacherSubjectLinkLists.size();
					}
				}

		);
		if (a.length == 0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}

	}

}

class TeacherSubjectLinkSelectRowMapper implements
		RowMapper<TeacherSubjectLink> {

	@Override
	public TeacherSubjectLink mapRow(ResultSet rs, int arg1)
			throws SQLException {
		TeacherSubjectLink strSubLinkMtr = new TeacherSubjectLink();
		strSubLinkMtr.setTrslId(rs.getString("TRSL_ID"));
		strSubLinkMtr.setStaffId(rs.getString("STAFF_ID"));
		strSubLinkMtr.setSubId(rs.getString("SUB_ID"));
		return strSubLinkMtr;
	}
}

class TeacherSubjectListStaffTransfer implements
		RowMapper<TeacherSubjectLinkList> {

	@Override
	public TeacherSubjectLinkList mapRow(ResultSet rs, int arg1)
			throws SQLException {
		TeacherSubjectLinkList teacherSubjectLinkList = new TeacherSubjectLinkList();
		teacherSubjectLinkList.setDbTs(rs.getInt("DB_TS"));
		teacherSubjectLinkList.setInstId(rs.getString("INST_ID"));
		teacherSubjectLinkList.setBranchId(rs.getString("BRANCH_ID"));
		teacherSubjectLinkList.setTrslId(rs.getString("TRSL_ID"));
		teacherSubjectLinkList.setStaffId(rs.getString("STAFF_ID"));
		return teacherSubjectLinkList;
	}
}

class StaffSelectExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("STAFF_ID");
			String value = rs.getString("STAFF_NAME");
			System.out.println("Key :" + key + "  value:" + value);
			map.put(key, value);
		}
		System.out.println("Map size before result set :" + map.size());
		return map;
	}
}
