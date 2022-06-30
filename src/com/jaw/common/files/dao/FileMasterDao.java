package com.jaw.common.files.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.service.IIdGeneratorService;

//Dao class for files
@Repository
public class FileMasterDao extends BaseDao implements IFileMasterDao {

	// Logging
	Logger logger = Logger.getLogger(FileMasterDao.class);

	@Autowired
	IIdGeneratorService simpleIdGenerator;
	@Autowired
	DateUtil dateUtil;

	// Method to get a single file
	@Override
	public void insertSingleFile(final FileMaster fileMaster, final Integer dbts) {

		StringBuffer sql = new StringBuffer("insert into flmt(")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("FILE_SRL_NO, ").append("LINK_ID, ")
				.append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ")
				.append("FILE_PATH, ").append("FILE_TYPE, ")
				.append("CONTENT_TYPE,").append("DEL_FLG, ")
				.append("R_MOD_ID, ").append("R_MOD_TIME, ")
				.append("R_CRE_ID, ").append("R_CRE_TIME,").append("FILE_SIZE")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?)");

		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, dbts);
				ps.setString(2, fileMaster.getInstId().trim());
				ps.setString(3, fileMaster.getBranchId().trim());
				ps.setString(4, fileMaster.getFileSrlno().trim());
				ps.setString(5, fileMaster.getLinkId().trim());
				ps.setString(6, fileMaster.getFileRefno());
				ps.setBinaryStream(7, fileMaster.getInputStream(),
						fileMaster.getSize());
				ps.setString(8, fileMaster.getFileName().trim());
				ps.setString(9, fileMaster.getFilepath().trim());
				ps.setString(10, fileMaster.getFileType().trim());
				ps.setString(11, fileMaster.getContentType().trim());
				ps.setString(12, fileMaster.getDelFlg().trim());
				ps.setString(13, fileMaster.getrModId().trim());
				ps.setString(14, fileMaster.getrCreId().trim());
				ps.setString(15, fileMaster.getSize().toString());

			}

		});

		logger.debug("insert query :" + sql.toString());
	}

	// Method to get a file based on instId,branchId,fileType & delFalg for
	// Institute Logo
	@Override
	public FileMaster getSingleFile(final String instId, final String branchid,
			final String linkId, final String fileType, final String srlNo)
			throws FileNotFoundInDatabase {
		logger.debug("inst id :" + instId + ",branchid:" + branchid
				+ ",linkId :" + linkId + ",fileType :" + fileType + ",srlNo :"
				+ srlNo);
		FileMaster filemaster = null;
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("FILE_SRL_NO, ").append("LINK_ID, ")
				.append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ")
				.append("FILE_PATH, ").append("FILE_TYPE, ")
				.append("FILE_SIZE, ").append("CONTENT_TYPE,")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from flmt ")
				.append("where ").append("INST_ID = ?")
				.append(" AND BRANCH_ID= ?").append(" AND FILE_SRL_NO= ?")
				.append(" AND LINK_ID= ?").append(" AND DEL_FLG= ?")
				.append(" AND FILE_TYPE = ?").append(" order by FILE_SRL_NO ;");
		logger.debug("select query :" + sql.toString());

		filemaster = (FileMaster) getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid.trim());
						pss.setString(3, srlNo.trim());
						pss.setString(4, linkId.trim());
						pss.setString(5, "N");
						pss.setString(6, fileType.trim());
					}

				}, new ResultSetExtractor<FileMaster>() {

					@Override
					public FileMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						FileMaster files = new FileMaster();
						if (rs.next()) {
							files.setDbTs(rs.getInt("DB_TS"));
							files.setInstId(rs.getString("INST_ID"));
							files.setBranchId(rs.getString("BRANCH_ID"));
							files.setFileSrlno(rs.getString("FILE_SRL_NO"));
							files.setLinkId(rs.getString("LINK_ID"));
							files.setFileRefno(rs.getString("FILE_REFNO"));
							files.setInputStream(rs.getBinaryStream("FILE"));
							files.setFileName(rs.getString("FILE_NAME"));
							files.setFilepath(rs.getString("FILE_PATH"));
							files.setInputStream(rs.getBinaryStream("FILE"));
							files.setFileType(rs.getString("FILE_TYPE"));
							files.setSize(Long.valueOf(rs
									.getString("FILE_SIZE")));
							files.setContentType(rs.getString("CONTENT_TYPE"));
							files.setDelFlg(rs.getString("DEL_FLG"));
							files.setrModId(rs.getString("R_MOD_ID"));
							files.setrModTime(rs.getString("R_MOD_TIME"));
							files.setrCreId(rs.getString("R_CRE_ID"));
							files.setrCreTime(rs.getString("R_CRE_TIME"));
						}

						return files;
					}

				});
	
		if (((filemaster.getInputStream() == null) || (filemaster
				.getInputStream().equals("")))
				&& ((filemaster.getFilepath() == null) || (filemaster
						.getFilepath().equals("")))) {
			throw new FileNotFoundInDatabase();
		}

		return filemaster;

	}
	
	@Override
	public FileMaster getSingleFileForFileTransfer(final String instId, final String branchid,
			final String linkId, final String fileType, final String srlNo)
			throws FileNotFoundInDatabase {
		logger.debug("inst id :" + instId + ",branchid:" + branchid
				+ ",linkId :" + linkId + ",fileType :" + fileType + ",srlNo :"
				+ srlNo);
		FileMaster filemaster = null;
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("FILE_SRL_NO, ").append("LINK_ID, ")
				.append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ")
				.append("FILE_PATH, ").append("FILE_TYPE, ")
				.append("FILE_SIZE, ").append("CONTENT_TYPE,")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from flmt ")
				.append("where ").append("INST_ID = ?")
				.append(" AND BRANCH_ID= ?").append(" AND FILE_SRL_NO= ?")
				.append(" AND LINK_ID= ?")
				.append(" AND FILE_TYPE = ?")				
				.append(" order by FILE_SRL_NO ;");
		logger.debug("select query :" + sql.toString());

		filemaster = (FileMaster) getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid.trim());
						pss.setString(3, srlNo.trim());
						pss.setString(4, linkId.trim());					
						pss.setString(5, fileType.trim());
					}

				}, new ResultSetExtractor<FileMaster>() {

					@Override
					public FileMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						FileMaster files = new FileMaster();
						if (rs.next()) {
							files.setDbTs(rs.getInt("DB_TS"));
							files.setInstId(rs.getString("INST_ID"));
							files.setBranchId(rs.getString("BRANCH_ID"));
							files.setFileSrlno(rs.getString("FILE_SRL_NO"));
							files.setLinkId(rs.getString("LINK_ID"));
							files.setFileRefno(rs.getString("FILE_REFNO"));
						//	files.setFile( (MultipartFile) rs.getObject("FILE"));
							files.setInputStream(rs.getBinaryStream("FILE"));
							files.setFileName(rs.getString("FILE_NAME"));
							files.setFilepath(rs.getString("FILE_PATH"));							
							files.setFileType(rs.getString("FILE_TYPE"));
							files.setSize(Long.valueOf(rs
									.getString("FILE_SIZE")));
							files.setContentType(rs.getString("CONTENT_TYPE"));
							files.setDelFlg(rs.getString("DEL_FLG"));
							files.setrModId(rs.getString("R_MOD_ID"));
							files.setrModTime(rs.getString("R_MOD_TIME"));
							files.setrCreId(rs.getString("R_CRE_ID"));
							files.setrCreTime(rs.getString("R_CRE_TIME"));
						}

						return files;
					}

				});	
		if(filemaster==null){
			throw new FileNotFoundInDatabase();
		}

		return filemaster;

	}
	
	
	
	
	
	
	

	// Method to delete Institute Logo in filemaster
	@Override
	public void deleteFile(final String instId, final String branchId,
			final String studentAdmisNo, final String fileType,
			final String fileSrlNo) throws FileNotFoundInDatabase, DeleteFailedException {
		Integer deletedValue = null;
		logger.debug("Delete file from filemaster table");	
		StringBuffer sql = new StringBuffer().append("Delete  from")
				.append(" flmt ").append(" where ").append(" INST_ID =? ")
				.append(" AND").append(" BRANCH_ID =?").append(" AND")
				.append(" FILE_TYPE =?").append(" AND").append(" LINK_ID=? ")
				.append(" AND").append(" FILE_SRL_NO=? ");

		logger.debug("Delete query :" + sql.toString());
		try {
			deletedValue = getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							if (instId != null) {
								ps.setString(1, instId);
							}
							ps.setString(2, branchId.trim());
							ps.setString(3, fileType.trim());
							ps.setString(4, studentAdmisNo);
							ps.setString(5, fileSrlNo.trim());

						}

					});
		} catch (EmptyResultDataAccessException e) {
			throw new DeleteFailedException();
		}	
		if (deletedValue == null || deletedValue == 0) {
			throw new DeleteFailedException();
		}

	}

	@Override
	public void updateFlmtForFileTransfer(final FileMaster fileMaster) throws UpdateFailedException {
		logger.debug("Inside update method for file transfer in Dao");
		logger.info("File Master Object :"+fileMaster);
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE flmt SET")
				.append(" DB_TS= ?")
				.append(", FILE= ?")
				.append(", FILE_NAME= ?")
				.append(", FILE_PATH= ?")				
				.append(", R_MOD_ID= ?")
				.append(", R_MOD_TIME=now()")
				.append(" WHERE")
				.append(" INST_ID= ?")
				.append(" AND ")
				.append(" BRANCH_ID= ?")
				.append(" AND ")
				.append(" FILE_SRL_NO= ?")
				.append(" AND ")
				.append(" LINK_ID= ?")
				.append(" AND ")
				.append(" FILE_TYPE= ?")
				.append(" AND ")
				.append(" DB_TS= ?");				

		int updateStatus = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
					
						ps.setInt(1,fileMaster.getDbTs()+1);
						if(fileMaster.getInputStream()!=null){
							ps.setBinaryStream(2, fileMaster.getInputStream());
						}else{
							ps.setBinaryStream(2, null);
						}
					
						ps.setString(3, fileMaster.getFileName().trim());
						ps.setString(4, fileMaster.getFilepath().trim());
						ps.setString(5, fileMaster.getrModId().trim());
						ps.setString(6, fileMaster.getInstId().trim());
						ps.setString(7, fileMaster.getBranchId().trim());
						ps.setString(8, fileMaster.getFileSrlno().trim());
						ps.setString(9, fileMaster.getLinkId().trim());
						ps.setString(10, fileMaster.getFileType().trim());
						ps.setInt(11, fileMaster.getDbTs());
												
					}

				});
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

		logger.debug("update query :" + sql.toString());
		
	}

	/*
	 * //Method to check whether the Institute Logo record is in Filemaster
	 * 
	 * @Override public Boolean dataExistOrNot(String instId, String branchId,
	 * String studentAdmisNo, String filetype) {
	 * 
	 * logger.debug("Inside dataExistOrNot method");
	 * 
	 * Boolean flag = false; StringBuffer query = new StringBuffer()
	 * .append("SELECT INST_ID,") .append("BRANCH_ID,")
	 * .append("STUDENT_STAFF_ADMIS_NO,") .append("FILE_TYPE ")
	 * .append(" FROM filemaster ") .append("WHERE INST_ID =")
	 * .append("'"+instId.trim()+"'") .append(" AND BRANCH_ID =")
	 * .append("'"+branchId.trim()+"'") .append(" AND LINK_ID = ")
	 * .append("'"+studentAdmisNo.trim()+"'") .append(" AND FILE_TYPE =")
	 * .append("'"+filetype.trim()+"'");
	 * 
	 * logger.debug("Select query :"+query.toString());
	 * 
	 * try{ getJdbcTemplate().execute(query.toString()); flag = true ;
	 * 
	 * }catch(EmptyResultDataAccessException e){ flag = false ;
	 * 
	 * } return flag ; }
	 */
}
