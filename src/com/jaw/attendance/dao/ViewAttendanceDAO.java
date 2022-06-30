package com.jaw.attendance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class ViewAttendanceDAO extends BaseDao implements IViewAttendanceDAO {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceDAO.class);
	
	@Override
	public List<ViewAttendance> consolidateAttendance(StudentAttendance studentAttendance)
			throws NoDataFoundException {
		logger.debug("DAO :Inside Get attendance for consolidated select method");
		logger.debug("DAO :Inside Get attendance for consolidated select method studentAttendance"
				+ studentAttendance);
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("(select")
				.append(" is_present,")
				.append(" count(distinct(att_date)) as noOfSessions ")
				.append("from stat ")
				.append("where ")
				.append(" inst_id = ?")
				.append(" and branch_id = ?")
				.append(" and ac_term = ?")
				.append(" and student_admis_no = ?")
				.append(" and del_flg = 'N'")
				.append(" and is_present = 'A')")
				.append("union")
				.append("(select")
				.append(" is_present,")
				.append(" count(distinct(att_date)) as noOfSessions ")
				.append("from stat ")
				.append("where ")
				.append(" inst_id = ?")
				.append(" and branch_id = ?")
				.append(" and ac_term = ?")
				.append(" and student_admis_no = ?")
				.append(" and del_flg = 'N'")
				.append(" and is_present = 'P'")
				.append(" and ")
				.append(" att_date not in ")
				.append("(select distinct(att_date)")
				.append("from stat ")
				.append("where ")
				.append(" inst_id = ?")
				.append(" and branch_id = ?")
				.append(" and ac_term = ?")
				.append(" and student_admis_no = ?")
				.append(" and del_flg = 'N'")
				.append(" and is_present = 'A'))");
		
		data.add(studentAttendance.getInstId());
		data.add(studentAttendance.getBranchId());
		data.add(studentAttendance.getAcTerm());
		data.add(studentAttendance.getStudentAdmisNo());
		data.add(studentAttendance.getInstId());
		data.add(studentAttendance.getBranchId());
		data.add(studentAttendance.getAcTerm());
		data.add(studentAttendance.getStudentAdmisNo());
		data.add(studentAttendance.getInstId());
		data.add(studentAttendance.getBranchId());
		data.add(studentAttendance.getAcTerm());
		data.add(studentAttendance.getStudentAdmisNo());
		
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<ViewAttendance> selectedAttendanceDetails = getJdbcTemplate()
				.query(sql.toString(), array, new ViewConsolidatedAttendanceViewSelectRowMapper());
		if (selectedAttendanceDetails.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedAttendanceDetails.size());
		
		return selectedAttendanceDetails;
		
	}
	
	@Override
	public List<StudentAttendance> attendanceWithLabSeperatly(StudentAttendance studentAttendance)
			throws NoDataFoundException {
		
		logger.debug("DAO :Inside Get attendance with lab seperatly select method");
		logger.debug("DAO :Inside Get attendance with lab seperatly select method studentAttendance"
				+ studentAttendance);
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select")
				.append(" stat.crsl_id,code_Desc,cls_type,")
				.append("is_present, sum(no_of_sessions) as noOfSessions ")
				.append("from stat,crsl, cocd ")
				.append("where ")
				.append(" stat.inst_id = crsl.inst_id and")
				.append(" stat.branch_id = crsl.branch_id and")
				.append(" stat.crsl_id = crsl.crsl_id and")
				.append(" stat.del_flg = crsl.del_flg and")
				.append(" stat.inst_id = cocd.inst_id and")
				.append(" stat.branch_id = cocd.branch_id and")
				.append(" stat.del_flg = cocd.del_flg and")
				.append(" crsl.sub_id = cocd.cm_code and")
				.append(" stat.inst_id = ?")
				.append(" and stat.branch_id = ?")
				.append(" and ac_term = ?")
				.append(" and student_admis_no = ?")
				.append(" and stat.del_flg = ?")
				.append(" and code_type in")
				.append(" ('CSUB', 'LANG2', 'LANG3', 'OSUB')")
				.append(" group by ")
				.append("crsl_id, cls_type, is_present ")
				.append(" order by ")
				.append(" crsl_id,cls_type,is_present ");
		data.add(studentAttendance.getInstId());
		data.add(studentAttendance.getBranchId());
		data.add(studentAttendance.getAcTerm());
		data.add(studentAttendance.getStudentAdmisNo());
		data.add("N");
		
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<StudentAttendance> selectedStudentList = getJdbcTemplate()
				.query(sql.toString(), array,
						new ViewSubAttListWithLabSepearteViewSelectRowMapper());
		if (selectedStudentList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedStudentList.size());
		
		return selectedStudentList;
		
	}
	
	@Override
	public List<StudentAttendance> attendanceWithoutLab(StudentAttendance studentAttendance)
			throws NoDataFoundException {
		
		logger.debug("DAO :Inside Get attendance without lab select method");
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select")
				.append(" stat.crsl_id,code_Desc,")
				.append("is_present, sum(no_of_sessions) as noOfSessions ")
				.append("from stat,crsl, cocd ")
				.append("where ")
				.append(" stat.inst_id = crsl.inst_id and")
				.append(" stat.branch_id = crsl.branch_id and")
				.append(" stat.crsl_id = crsl.crsl_id and")
				.append(" stat.del_flg = crsl.del_flg and")
				.append(" stat.inst_id = cocd.inst_id and")
				.append(" stat.branch_id = cocd.branch_id and")
				.append(" stat.del_flg = cocd.del_flg and")
				.append(" crsl.sub_id = cocd.cm_code and")
				.append(" stat.inst_id = ?")
				.append(" and stat.branch_id = ?");
		
		sql.append(" and ac_term = ?")
				.append(" and student_admis_no = ?")
				.append(" and stat.del_flg = ?");
		if ((studentAttendance.getClsType() != "") && (studentAttendance.getClsType() != null)) {
			sql.append(" and cls_type = ?");
		}
		sql.append(" and code_type in")
				.append(" ('CSUB', 'LANG2', 'LANG3', 'OSUB')")
				.append(" group by ")
				.append(" crsl_id, is_present ")
				.append(" order by ")
				.append(" crsl_id, is_present ");
		data.add(studentAttendance.getInstId());
		data.add(studentAttendance.getBranchId());
		data.add(studentAttendance.getAcTerm());
		data.add(studentAttendance.getStudentAdmisNo());
		data.add("N");
		if ((studentAttendance.getClsType() != "") && (studentAttendance.getClsType() != null)) {
			data.add(studentAttendance.getClsType());
		}
		
		if ((studentAttendance.getClsType() != "") && (studentAttendance.getClsType() != null)) {
			
		}
		
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<StudentAttendance> selectedStudentList = getJdbcTemplate()
				.query(sql.toString(), array, new ViewSubjectAttendanceListViewSelectRowMapper());
		if (selectedStudentList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedStudentList.size());
		
		return selectedStudentList;
		
	}

	@Override
	public List<ViewAttendance> getConsolidateAttendance(
			ViewAttendanceKey viewAttendanceKey) throws NoDataFoundException {
		logger.debug("DAO :Inside Get attendance for consolidated select method");
		logger.debug("DAO :Inside Get attendance for consolidated select method View Attendance"
				+ viewAttendanceKey.toString());
		List<Object> data = new ArrayList<Object>();			
		
		StringBuffer sql = new StringBuffer();
		sql.append("select")
		.append(" 'A' is_present,")
		.append("count(distinct(stam.att_date)) as noOfSessions")
		.append(" from stad, stam")
		.append(" where")
		.append(" stad.inst_id = stam.inst_id")
		.append(" and stad.branch_id = stam.branch_id")
		.append(" and stad.stam_id = stam.stam_id")
		.append(" and stad.del_flg = stam.del_flg")
		.append(" and stad.inst_id=?")
		.append(" and stad.branch_id=?")
		.append(" and stam.ac_term=?")
		.append(" and student_admis_no = ?")
		.append(" and stad.del_flg = 'N'")
		.append(" union ");		
		sql.append("select")
		.append("'P' as is_present,")
		.append("count(adate) as noOfSessions")
		.append(" from")
		.append("(select")
		.append(" distinct(att_date) as adate")
		.append(" from stam ")
		.append(" where ")
		.append(" stam.inst_id = ?")
		.append(" and stam.branch_id = ?")
		.append(" and stam.ac_term =?")
		.append(" and stam.studentgrp_id = ?")
		.append(" and stam.del_flg = 'N'")
		.append(" and crsl_id in (")
		.append(" select crsl_id")
		.append(" from stum,stgm,crsl")
		.append(" where")		
		.append(" stum.inst_id = stgm.inst_id")
		.append(" and stum.branch_id = stgm.branch_id")
		.append(" and stum.del_flg = stgm.del_flg")
		.append(" and stum.studentgrp_id = stgm.studentgrp_id")
		.append(" and stgm.inst_id = crsl.inst_id")
		.append(" and stgm.branch_id = crsl.branch_id")
		.append(" and  stgm.del_flg = crsl.del_flg")
		.append(" and stgm.coursemaster_id = crsl.coursemaster_id")
		.append(" and stum.inst_id = ?")
		.append(" and stum.branch_id = ?")
		.append(" and stum.del_flg = 'N'")
		.append(" and stum.student_admis_no = ?")
		.append(" and stum.academic_year = ?")
		.append(" and ((sub_type = 'C')")
		.append(" or (sub_type = 'L2' and stum.second_lang = crsl.sub_id)")
		.append(" or (sub_type = 'L3' and stum.third_lang = crsl.sub_id)")		
		.append(" or (sub_type = 'E1' and stum.elective_1 = crsl.sub_id)")
		.append(" or (sub_type = 'E2' and stum.elective_2 = crsl.sub_id)))")
		.append(" order by att_date")
		.append(" ) as a left outer join")
		.append(" (select distinct(att_date) as bdate ")
		.append(" from stad, stam")
		.append(" where ")		
		.append(" stad.inst_id = stam.inst_id ")
		.append(" and  stad.branch_id = stam.branch_id ")
		.append(" and  stad.stam_id = stam.stam_id ")
		.append(" and  stad.del_flg = stam.del_flg ")
		.append("  and stad.inst_id = ? ")
		.append(" and stad.branch_id = ?")
		.append(" and stam.ac_term = ? ")		
		.append(" and student_admis_no = ? ")
		.append(" and stad.del_flg = 'N' ")
		.append(" and cls_type = 'CLST' ")
		.append(" order by att_date ) as b on adate = bdate ")
		.append(" where bdate is null")	;
		
		System.out.println("sql queryyyyyyyyyyyyyyyyyyyyyyyyyyyy  : "+sql.toString());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentGrpId());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<ViewAttendance> selectedAttendanceDetails = getJdbcTemplate()
				.query(sql.toString(), array, new ViewConsolidatedAttendance1ViewSelectRowMapper());
		if (selectedAttendanceDetails.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedAttendanceDetails.size());
		
		return selectedAttendanceDetails;
	}

	@Override
	public List<ViewAttendance> subjectAttendanceWithCheckLab(
			ViewAttendanceKey viewAttendanceKey) throws NoDataFoundException {
		
		logger.debug("DAO :Inside Get attendance for Subject select method");
		logger.debug("DAO :Inside Get attendance for Subject select method View Attendance"
				+ viewAttendanceKey.toString());
		List<Object> data = new ArrayList<Object>();		
	
		StringBuffer sql = new StringBuffer();
		sql.append("select")
		.append(" sub_name, abrecs.crsl_id,")
		.append(" abrecs.is_present, abrecs.no_of_sessions ")
		.append("from sbjm, crsl, ")
		.append(" (select ")
		.append(" crsl_id as crsl_id,")
		.append(" is_present as is_present,")
		.append(" sum(no_of_sessions) as no_of_sessions ")
		.append(" from stad, stam where ")
		.append(" stad.inst_id = stam.inst_id and ")
		.append(" stad.branch_id = stam.branch_id and ")
		.append(" stad.stam_id = stam.stam_id and ")
		.append(" stad.del_flg = stam.del_flg and ")
		.append(" stad.inst_id = ? ")
		.append(" and stad.branch_id = ? ")
		.append(" and stam.ac_term = ? ")
		.append(" and studentgrp_id =? ")
		.append(" and student_admis_no = ? ")
		.append(" and stad.del_flg = 'N' ")
		.append(" and is_present = 'A' ");		
		
		if ((viewAttendanceKey.getClassType() != "") && (viewAttendanceKey.getClassType() != null)) {
			sql.append(" and cls_type ='"+viewAttendanceKey.getClassType()+"'");
		}
		sql.append(" group by crsl_id, is_present ")
		.append(" order by crsl_id, is_present) ")		
		.append(" as abrecs where ")
		.append(" sbjm.inst_id = crsl.inst_id and ")
		.append(" sbjm.branch_id = crsl.branch_id and ")
		.append(" sbjm.del_flg = crsl.del_flg and ")
		.append(" sbjm.sub_id = crsl.sub_id and ")
		.append(" abrecs.crsl_id = crsl.crsl_id  ");
		if ((viewAttendanceKey.getCrslId() != "") && (viewAttendanceKey.getCrslId() != null)) {
			sql.append(" and abrecs.crsl_id='"+viewAttendanceKey.getCrslId()+"'");
		}
		sql.append(" union ")
		.append(" select ")
		.append(" sub_name, prrecs.crsl_id, ")
		.append(" prrecs.is_present, prrecs.no_of_sessions ")
		.append(" from sbjm, crsl, ")
		.append(" (select ")
		.append(" crsl_id as crsl_id, ")
		.append(" 'P' as is_present, ")
		.append(" sum(no_of_sessions) as no_of_sessions ")
		.append(" from stam ")
		.append(" left join stad on ")
		.append(" stam.inst_id = stad.inst_id and ")
		.append(" stam.branch_id = stad.branch_id and ")
		.append(" stam.stam_id = stad.stam_id and  ")
		.append(" stam.del_flg = stad.del_flg and  ")
		.append(" stad.student_admis_no =? ")	
		.append(" where ")
		.append(" stam.inst_id = ? and ")
		.append(" stam.branch_id = ? and ")
		.append(" stam.ac_term = ? and ")
		.append(" stam.studentgrp_id = ? and ")
		.append(" stam.del_flg = 'N'  ");
		if ((viewAttendanceKey.getClassType() != "") && (viewAttendanceKey.getClassType() != null)) {
			sql.append("and cls_type = '"+viewAttendanceKey.getClassType()+"'");
		}
		sql.append(" and crsl_id in ( ")
		.append(" select crsl_id ")
		.append(" from stum,stgm,crsl ")		
		.append(" where ")
		.append(" stum.inst_id = stgm.inst_id and ")
		.append(" stum.branch_id = stgm.branch_id and ")
		.append(" stum.del_flg = stgm.del_flg and ")
		.append(" stum.studentgrp_id = stgm.studentgrp_id and ")
		.append(" stgm.inst_id = crsl.inst_id and ")
		.append(" stgm.branch_id = crsl.branch_id and ")
		.append(" stgm.del_flg = crsl.del_flg and ")		
		.append(" stgm.coursemaster_id = crsl.coursemaster_id and ")
		.append(" stgm.term_id = crsl.term_id and ")
		.append(" stum.inst_id = ? and ")
		.append(" stum.branch_id = ? and ")
		.append(" stum.del_flg = 'N' and ")
		.append(" stum.student_admis_no = ? and ")
		.append(" stum.academic_year = ? and ")				
		.append(" ((sub_type = 'C') ")
		.append(" or (sub_type = 'L2' and stum.second_lang = crsl.sub_id) ")
		.append(" or (sub_type = 'L3' and stum.third_lang = crsl.sub_id) ")
		.append(" or (sub_type = 'E1' and stum.elective_1 = crsl.sub_id) ")
		.append(" or (sub_type = 'E2' and stum.elective_2 = crsl.sub_id))) and ")
		.append(" stad.stam_id is null ")		
		.append(" group by crsl_id, is_present ")
		.append(" order by crsl_id, is_present) as prrecs ")
		.append(" where ")
		.append(" sbjm.inst_id = crsl.inst_id and ")
		.append(" sbjm.branch_id = crsl.branch_id and ")
		.append(" sbjm.del_flg = crsl.del_flg and ")
		.append(" sbjm.sub_id = crsl.sub_id and ")
		.append(" prrecs.crsl_id = crsl.crsl_id ");
		if ((viewAttendanceKey.getCrslId() != "") && (viewAttendanceKey.getCrslId() != null)) {
			sql.append(" and prrecs.crsl_id='"+viewAttendanceKey.getCrslId()+"'");
		}
		System.out.println("sql queryyyyyyyyyyyyyyy : "+sql.toString());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentGrpId());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentGrpId());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getAcTerm());
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<ViewAttendance> selectedStudentList = getJdbcTemplate()
				.query(sql.toString(), array, new SubjectAttendanceListViewSelectRowMapper());
		if (selectedStudentList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedStudentList.size());
		
		return selectedStudentList;
		
		
	}

	@Override
	public List<ViewAttendance> subjectAttendanceWithLabSeperatly(
			ViewAttendanceKey viewAttendanceKey) throws NoDataFoundException {
		
		logger.debug("DAO :Inside Get attendance for Subject with Lab Seperately select method");
		logger.debug("DAO :Inside Get attendance for Subject with Lab Seperately select method View Attendance"
				+ viewAttendanceKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();	
		sql.append("select")
		.append(" sub_name,")
		.append("abrecs.cls_type,")
		.append("abrecs.crsl_id,")
		.append("abrecs.is_present,")
		.append("abrecs.no_of_sessions")
		.append(" from sbjm, crsl,")
		.append(" (select crsl_id as crsl_id,")
		.append("cls_type as cls_type, ")
		.append("is_present as is_present,")
		.append("sum(no_of_sessions) as no_of_sessions ")
		.append(" from stad, stam where ")
		.append(" stad.inst_id = stam.inst_id and")
		.append(" stad.branch_id = stam.branch_id and")		
		.append(" stad.stam_id = stam.stam_id and")
		.append(" stad.del_flg = stam.del_flg and")
		.append(" stad.inst_id = ?")
		.append(" and stad.branch_id = ?")
		.append(" and stam.ac_term = ?")
		.append(" and studentgrp_id = ?")
		.append(" and student_admis_no = ?")
		.append(" and stad.del_flg = 'N'")		
		.append(" and is_present = 'A'")		
		.append(" group by crsl_id, cls_type, is_present")
		.append(" order by crsl_id, cls_type, is_present) as abrecs")
		.append(" where")
		.append(" sbjm.inst_id = crsl.inst_id and")
		.append(" sbjm.branch_id = crsl.branch_id and")
		.append(" sbjm.del_flg = crsl.del_flg and")
		.append(" sbjm.sub_id = crsl.sub_id and")
		.append(" abrecs.crsl_id = crsl.crsl_id ");
		if ((viewAttendanceKey.getCrslId() != "") && (viewAttendanceKey.getCrslId() != null)) {
			sql.append(" and abrecs.crsl_id='"+viewAttendanceKey.getCrslId()+"'");
		}
		sql.append(" union ")
		.append(" select sub_name,")
		.append(" prrecs.cls_type,")
		.append(" prrecs.crsl_id,")
		.append("  prrecs.is_present,")
		.append(" prrecs.no_of_sessions")		   				
						.append(" from sbjm, crsl,")
						.append("  (select crsl_id as crsl_id,")
						.append(" cls_type as cls_type,")
						.append(" 'P' as is_present,")
						.append(" sum(no_of_sessions) as no_of_sessions")
						.append(" from stam left join stad on ")
						.append("  stam.inst_id = stad.inst_id and")
						.append(" stam.branch_id = stad.branch_id and")						
						.append("  stam.stam_id = stad.stam_id and ")
						.append(" stam.del_flg = stad.del_flg and")
						.append(" stad.student_admis_no = ?")
						.append(" where")						
						.append(" stam.inst_id = ? and")
						.append(" stam.branch_id = ? and")
						.append(" stam.ac_term = ? and")
						.append(" stam.studentgrp_id = ? and")
						.append(" stam.del_flg = 'N' and crsl_id in (")
						.append(" select crsl_id ")
						.append(" from stum,stgm,crsl  ")
						.append(" where")
						.append(" stum.inst_id = stgm.inst_id and")
						.append(" stum.branch_id = stgm.branch_id and")
						.append(" stum.del_flg = stgm.del_flg and")
						.append(" stum.studentgrp_id = stgm.studentgrp_id and")
						.append(" stgm.inst_id = crsl.inst_id and")						
						.append(" stgm.branch_id = crsl.branch_id and")
						.append(" stgm.del_flg = crsl.del_flg and")
						.append(" stgm.coursemaster_id = crsl.coursemaster_id and")
						.append(" stgm.term_id = crsl.term_id and")
						.append(" stum.inst_id = ? and")
						.append(" stum.branch_id = ? and")						
							.append(" stum.del_flg = 'N' and")
							.append(" stum.student_admis_no = ? and")
							.append(" stum.academic_year = ? and")							
							.append(" ((sub_type = 'C') ")
							.append(" or (sub_type = 'L2' and stum.second_lang = crsl.sub_id)")
							.append(" or (sub_type = 'L3' and stum.third_lang = crsl.sub_id)")
							.append(" or (sub_type = 'E1' and stum.elective_1 = crsl.sub_id)")
							.append(" or (sub_type = 'E2' and stum.elective_2 = crsl.sub_id))) and")
							.append(" stad.stam_id is null ")
							
							.append(" group by crsl_id, cls_type, is_present ")
							.append(" order by crsl_id,cls_type, is_present) as prrecs ")
							.append(" where ")
							.append(" sbjm.inst_id = crsl.inst_id and ")
							.append(" sbjm.branch_id = crsl.branch_id and ")							
							.append(" sbjm.del_flg = crsl.del_flg and ")
							.append(" sbjm.sub_id = crsl.sub_id and ")
							.append(" prrecs.crsl_id = crsl.crsl_id ");
		if ((viewAttendanceKey.getCrslId() != "") && (viewAttendanceKey.getCrslId() != null)) {
			sql.append(" and prrecs.crsl_id='"+viewAttendanceKey.getCrslId()+"'");
		}
		System.out.println("sql queryyyyyyyyyyyyyyy : "+sql.toString());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentGrpId());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getInstId());		
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getAcTerm());
		data.add(viewAttendanceKey.getStudentGrpId());
		data.add(viewAttendanceKey.getInstId());
		data.add(viewAttendanceKey.getBranchId());
		data.add(viewAttendanceKey.getStudentAdmisNo());
		data.add(viewAttendanceKey.getAcTerm());
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		List<ViewAttendance> selectedStudentList = getJdbcTemplate()
				.query(sql.toString(), array, new SubjectAttendanceListWithLabSeperateViewSelectRowMapper());
		if (selectedStudentList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Student Attendance List value" + selectedStudentList.size());
		
		return selectedStudentList;		
						
						
		
		
	}
	
}

class SubjectAttendanceListWithLabSeperateViewSelectRowMapper implements RowMapper<ViewAttendance> {
	
	@Override
	public ViewAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewAttendance viewAttendance = new ViewAttendance();
		viewAttendance.setCrslId(rs.getString("crsl_id"));
		
		if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("A"))) {
			viewAttendance.setAttType("A");
			viewAttendance.setNoOfAbsent(rs.getInt("no_of_sessions"));
		}
		else if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("P"))) {
			viewAttendance.setAttType("P");
			viewAttendance.setNoOfPresent(rs.getInt("no_of_sessions"));
		}
	
		viewAttendance.setSubject(rs.getString("SUB_NAME"));
		viewAttendance.setClsType(rs.getString("cls_type"));
		return viewAttendance;
	}
}
class SubjectAttendanceListViewSelectRowMapper implements RowMapper<ViewAttendance> {
	
	@Override
	public ViewAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewAttendance viewAttendance = new ViewAttendance();
		viewAttendance.setCrslId(rs.getString("crsl_id"));
		
		if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("A"))) {
			viewAttendance.setAttType("A");
			viewAttendance.setNoOfAbsent(rs.getInt("no_of_sessions"));
		}
		else if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("P"))) {
			viewAttendance.setAttType("P");
			viewAttendance.setNoOfPresent(rs.getInt("no_of_sessions"));
		}
	
		viewAttendance.setSubject(rs.getString("SUB_NAME"));
		
		return viewAttendance;
	}
}
class ViewSubjectAttendanceListViewSelectRowMapper implements RowMapper<StudentAttendance> {
	
	@Override
	public StudentAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setCrslId(rs.getString("crsl_id"));
		studentAttendance.setIsPresent(rs.getString("is_present"));
		studentAttendance.setNoOfSessions(rs.getInt("noOfSessions"));
		studentAttendance.setAbsenteeRmks(rs.getString("code_desc"));
		
		return studentAttendance;
	}
}

class ViewSubAttListWithLabSepearteViewSelectRowMapper implements RowMapper<StudentAttendance> {
	
	@Override
	public StudentAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentAttendance studentAttendance = new StudentAttendance();
		studentAttendance.setCrslId(rs.getString("crsl_id"));
		studentAttendance.setIsPresent(rs.getString("is_present"));
		studentAttendance.setNoOfSessions(rs.getInt("noOfSessions"));
		studentAttendance.setAbsenteeRmks(rs.getString("code_desc"));
		studentAttendance.setClsType(rs.getString("cls_type"));
		
		return studentAttendance;
	}
}

class ViewConsolidatedAttendanceViewSelectRowMapper implements RowMapper<ViewAttendance> {
	
	@Override
	public ViewAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewAttendance viewAttendance = new ViewAttendance();
		if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("A"))) {
			viewAttendance.setAttType("A");
			viewAttendance.setNoOfAbsent(rs.getInt("noOfSessions"));
		}
		else if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("P"))) {
			viewAttendance.setAttType("P");
			viewAttendance.setNoOfPresent(rs.getInt("noOfSessions"));
		}
		
		return viewAttendance;
	}
}
class ViewConsolidatedAttendance1ViewSelectRowMapper implements RowMapper<ViewAttendance> {
	
	@Override
	public ViewAttendance mapRow(ResultSet rs, int arg1) throws SQLException {
		ViewAttendance viewAttendance = new ViewAttendance();
		System.out.println("view Attendance String : "+rs.getString("is_present"));
		System.out.println("view Attendance String : "+rs.getInt("noOfSessions"));
		if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("A"))) {
			viewAttendance.setAttType("A");
			viewAttendance.setNoOfAbsent(rs.getInt("noOfSessions"));
		}
		 if ((rs.getString("is_present") != null) && (rs.getString("is_present").equals("P"))) {
			viewAttendance.setAttType("P");
			viewAttendance.setNoOfPresent(rs.getInt("noOfSessions"));
		}
		System.out.println("view Attendance to string  : "+viewAttendance.toString());
		return viewAttendance;
	}
}