package com.jaw.common.files.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.service.IIdGeneratorService;

@Repository
public class FileHistoryDao extends BaseDao implements IFileHistoryDao {

	// Logging
	Logger logger = Logger.getLogger(FileHistoryDao.class);

	@Autowired
	IIdGeneratorService simpleIdGenerator;

	@Override
	public void insertSingleFile(final FileHistory fileHistory,
			final Integer dbts) throws DuplicateEntryException {
		logger.info("Going to insert into fileHistroy table");
		StringBuffer sql = new StringBuffer("insert into flht(")
				.append("FILE_HT_SRL_NO, ").append("DB_TS, ")
				.append("INST_ID, ").append("BRANCH_ID, ").append("FILE_SRL_NO, ").append("LINK_ID, ")
				.append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ")
				.append("FILE_PATH, ")
				.append("FILE_TYPE, ")
				.append("CONTENT_TYPE,").append("DEL_FLG, ")
				.append("FLMT_R_CRE_ID, ").append("FLMT_R_CRE_TIME, ")
				.append("R_CRE_ID, ").append("R_CRE_TIME,").append("FILE_SIZE")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?)");
try{
		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, fileHistory.getFileHtSrlNo());
				ps.setInt(2, dbts);
				ps.setString(3, fileHistory.getInstId().trim());
				ps.setString(4, fileHistory.getBranchId().trim());
				ps.setString(5, fileHistory.getFileSrlno().trim());
				ps.setString(6, fileHistory.getLinkId().trim());
				ps.setString(7, fileHistory.getFileRefno());
				ps.setBinaryStream(8, fileHistory.getInputStream(),
						fileHistory.getSize());
				ps.setString(9, fileHistory.getFileName().trim());
				ps.setString(10, fileHistory.getFilepath().trim());
				ps.setString(11, fileHistory.getFileType().trim());
				ps.setString(12, fileHistory.getContentType().trim());
				ps.setString(13, fileHistory.getDelFlg().trim());
				ps.setString(14, fileHistory.getFlmtRCreId().trim());
				ps.setString(15, fileHistory.getFlmtRCreTime().trim());
				ps.setString(16, fileHistory.getrCreId().trim());
				ps.setString(17, fileHistory.getSize().toString());
			}

		});
			} catch (org.springframework.dao.DuplicateKeyException e) {
				logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
				throw new DuplicateEntryException();
			}

		logger.debug("insert query :" + sql.toString());
	}
	
	@Override
	public FileHistory getSingleFileForFileTransfer(final String instId, final String branchid,
			final String linkId, final String fileType, final String srlNo)
			throws FileNotFoundInDatabase {
		logger.debug("inst id :" + instId + ",branchid:" + branchid
				+ ",linkId :" + linkId + ",fileType :" + fileType + ",srlNo :"
				+ srlNo);
		FileHistory filemaster = null;
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("FILE_SRL_NO, ").append("LINK_ID, ")
				.append("FILE_REFNO, ").append("FILE, ").append("FILE_NAME, ")
				.append("FILE_PATH, ").append("FILE_TYPE, ")
				.append("FILE_SIZE, ").append("CONTENT_TYPE,")
				.append("DEL_FLG, ")/*.append("R_MOD_ID, ")
				.append("R_MOD_TIME, ")*/.append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from flht ")
				.append("where ").append("INST_ID = ?")
				.append(" AND BRANCH_ID= ?").append(" AND FILE_SRL_NO= ?")
				.append(" AND LINK_ID= ?")
				.append(" AND FILE_TYPE = ?").append(" order by FILE_SRL_NO ;");
		logger.debug("select query :" + sql.toString());

		filemaster = (FileHistory) getJdbcTemplate().query(sql.toString(),
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

				}, new ResultSetExtractor<FileHistory>() {

					@Override
					public FileHistory extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						FileHistory files = new FileHistory();
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
						/*	files.setrModId(rs.getString("R_MOD_ID"));
							files.setrModTime(rs.getString("R_MOD_TIME"));*/
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
	
	@Override
	public void updateFlhtForFileTransfer(final FileHistory fileHis) throws UpdateFailedException {
		logger.debug("Inside update method for file transfer in Dao");
		logger.info("File Master Object :"+fileHis);
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE flht SET")
				.append(" DB_TS= ?")
				.append(", FILE= ?")
				.append(", FILE_NAME= ?")
				.append(", FILE_PATH= ?")				
				/*.append(", R_MOD_ID= ?")*/
				/*.append(", R_MOD_TIME=now()")*/
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
					
						ps.setInt(1,fileHis.getDbTs()+1);
						if(fileHis.getInputStream()!=null){
							ps.setBinaryStream(2, fileHis.getInputStream());
						}else{
							ps.setBinaryStream(2, null);
						}
					
						ps.setString(3, fileHis.getFileName().trim());
						ps.setString(4, fileHis.getFilepath().trim());
					//	ps.setString(5, fileHis.getrModId().trim());
						ps.setString(5, fileHis.getInstId().trim());
						ps.setString(6, fileHis.getBranchId().trim());
						ps.setString(7, fileHis.getFileSrlno().trim());
						ps.setString(8, fileHis.getLinkId().trim());
						ps.setString(9, fileHis.getFileType().trim());
						ps.setInt(10, fileHis.getDbTs());
												
					}

				});
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

		logger.debug("update query :" + sql.toString());
		
	}


}
