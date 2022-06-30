package com.jaw.common.files.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.service.IIdGeneratorService;

@Repository
public class FileMasterListDao extends BaseDao implements IFileMasterListDao {
	// Logging
	Logger logger = Logger.getLogger(FileMasterDao.class);

	@Autowired
	IIdGeneratorService simpleIdGenerator;
	@Autowired
	DateUtil dateUtil;

	@Override
	public void deleteFile(List<FileMaster> files)
			throws FileNotFoundInDatabase {

		logger.debug("Delete file from filemaster table:"
				+ files.get(0).getLinkId());
		for (final FileMaster fileMaster : files) {
			StringBuffer sql = new StringBuffer().append("Delete  from")
					.append(" flmt ").append(" where ")
					.append(" INST_ID =? ").append(" AND")
					.append(" BRANCH_ID =?").append(" AND")
					.append(" FILE_TYPE =?").append(" AND")
					.append(" LINK_ID=? ");

			logger.debug("Delete query :" + sql.toString());
			try {
				getJdbcTemplate().update(sql.toString(),
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								if (fileMaster.getInstId() != null) {
									ps.setString(1, fileMaster.getInstId()
											.trim());
								}
								ps.setString(2, fileMaster.getBranchId().trim());
								ps.setString(3, fileMaster.getFileType().trim());
								ps.setString(4, fileMaster.getLinkId().trim());

							}

						});
			} catch (EmptyResultDataAccessException e) {
				throw new FileNotFoundInDatabase();
			}
		}

	}

	// inserting batch of files
	@Override
	public void fileBatch(final List<FileMaster> files,
			final Integer startingSeq) throws DuplicateEntryException {			
		StringBuffer sql = new StringBuffer("insert into flmt(")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ").append("FILE_SRL_NO, ")
				.append("LINK_ID, ").append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ").append("FILE_PATH, ")
				.append("FILE_TYPE, ").append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME,").append("CONTENT_TYPE,")
				.append("FILE_SIZE")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?)");
		try{
		getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						FileMaster filemaster = files.get(i);
						Integer studentInfoID = 0;
						studentInfoID = startingSeq + i;						
						filemaster.setFileRefno(ApplicationConstant.STRING_FILE_MASTER_SEQ+studentInfoID.toString());

						ps.setInt(1, 1);
						ps.setString(2, filemaster.getInstId().trim());						
						ps.setString(3, filemaster.getBranchId().trim());
						ps.setString(4, filemaster.getFileSrlno().trim());
						ps.setString(5, filemaster.getLinkId().trim());
						ps.setString(6, filemaster.getFileRefno());
						if(filemaster.getInputStream()!=null){
							ps.setBinaryStream(7, filemaster.getInputStream(),
									filemaster.getSize());
						}else{
							ps.setBinaryStream(7, null);
						}
						ps.setString(8, filemaster.getFileName().trim());
						ps.setString(9, filemaster.getFilepath().trim());
						ps.setString(10, filemaster.getFileType().trim());
						ps.setString(11, filemaster.getDelFlg().trim());
						ps.setString(12, filemaster.getrModId().trim());					
						ps.setString(13, filemaster.getrCreId().trim());						
						ps.setString(14, filemaster.getContentType().trim());
						ps.setLong(15, filemaster.getSize());
					}

					@Override
					public int getBatchSize() {
						return files.size();
					}
				});
		
	} catch (org.springframework.dao.DuplicateKeyException e) {
		logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
		throw new DuplicateEntryException();
	}
		logger.debug("sql query :" + sql.toString());

	}

	@Override
	public List<FileMaster> fileTypeList(final String instId,
			final String branchId, final String studentAdmisNo)  {

		logger.debug("retrive FileMaster");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("FILE_TYPE,")
				.append("FILE_SRL_NO ")
				.append(" from flmt ")
				.append("where ").append("LINK_ID=?").append(" and ")
				.append("INST_ID=?").append(" and ").append("BRANCH_ID=?")
				.append(" and ").append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		List<FileMaster> fileType = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentAdmisNo);
						pss.setString(2, instId);
						pss.setString(3, branchId);
						pss.setString(4, "N");
					}
				}, new RowMapper<FileMaster>() {

					@Override
					public FileMaster mapRow(ResultSet rs, int arg1)
							throws SQLException {
						FileMaster fileMaster = new FileMaster();
						fileMaster.setFileType(rs.getString("FILE_TYPE"));
						fileMaster.setFileSrlno(rs.getString("FILE_SRL_NO"));
						return fileMaster;
						//return rs.getString("FILE_TYPE");
					}

				});
		
		return fileType;
	}

	// method to select all the files
	@Override
	public List<FileMaster> retriveFileMaster(final String studentAdmisNo) throws NoDataFoundException {

		logger.debug("retrive FileMaster Details");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("LINK_ID, ").append("FILE_REFNO, ").append("FILE, ")
				.append("FILE_TYPE, ").append("FILE_SIZE, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from flmt ")
				.append("where ").append("LINK_ID=?;");
		logger.debug("select query :" + sql.toString());
		List<FileMaster> commDetailsResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentAdmisNo.trim());

					}

				}, new FileMasterRowmapper());
		if(commDetailsResult.size()==0){
			throw new NoDataFoundException();
			
		}

		return commDetailsResult;
	}

	// Inner class to map selected columns into File master object
	class FileMasterRowmapper implements RowMapper<FileMaster> {

		@Override
		public FileMaster mapRow(ResultSet rs, int arg1) throws SQLException {
			FileMaster files = new FileMaster();
			files.setDbTs(rs.getInt("DB_TS"));
			files.setInstId(rs.getString("INST_ID"));
			files.setBranchId(rs.getString("BRANCH_ID"));
			files.setLinkId(rs.getString("LINK_ID"));
			files.setFileRefno(rs.getString("FILE_REFNO"));
			files.setInputStream(rs.getBinaryStream("FILE"));
			files.setFileType(rs.getString("FILE_TYPE"));
			files.setSize(Long.valueOf(rs.getString("FILE_SIZE")));
			files.setDelFlg(rs.getString("DEL_FLG"));
			files.setrModId(rs.getString("R_MOD_ID"));
			files.setrModTime(rs.getString("R_MOD_TIME"));
			files.setrCreId(rs.getString("R_CRE_ID"));
			files.setrCreTime(rs.getString("R_CRE_TIME"));
			return files;
		}

	}
}
