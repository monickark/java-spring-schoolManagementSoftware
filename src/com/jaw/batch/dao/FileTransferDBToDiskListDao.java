package com.jaw.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.framework.dao.BaseDao;
@Repository
public class FileTransferDBToDiskListDao extends BaseDao implements IFileTransferDBToDiskListDao {
	Logger logger = Logger.getLogger(BatchFileUploadDao.class);	
	@Override
	public List<FileMaster> getFileObjectForTransfer(final String instId,
			final  String branchId,final String typeOfOpt) {		
		logger.info("Going to rerieve the key values from flmt for file transfer");
		List<FileMaster> fileMasterList = new ArrayList<FileMaster>();	
				FileMaster filemaster = null;
				StringBuffer sql = new StringBuffer().append("select ")					
						.append("INST_ID, ")
						.append("BRANCH_ID, ")
						.append("LINK_ID, ")
						.append("FILE_REFNO, ")			
						.append("FILE_SRL_NO, ")			
						.append("FILE_TYPE")						
						.append(" FROM flmt ")						
						.append("WHERE ")
						.append("INST_ID = ?")
						.append(" AND BRANCH_ID= ?");
						if(typeOfOpt.equals(ApplicationConstant.DB_TO_DISK_CONSTANT)){
							sql.append(" AND FILE IS NOT NULL");
						}else if(typeOfOpt.equals(ApplicationConstant.DISK_TO_DB_CONSTANT)){
							sql.append(" AND FILE IS NULL");
						}
						
						sql.append(" AND FILE_TYPE NOT LIKE '%PH%'");
				logger.debug("select query in getFileObjectForTransfer method :" + sql.toString());				
				fileMasterList =  getJdbcTemplate().query(
						sql.toString(), new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement pss)
									throws SQLException {
								pss.setString(1, instId.trim());									
								pss.setString(2, branchId.trim());										
																	
							}

						}, new RowMapper<FileMaster>() {							

							@Override
							public FileMaster mapRow(ResultSet rs, int arg1)
									throws SQLException {

								FileMaster files = new FileMaster();
												
									files.setInstId(rs
											.getString("INST_ID"));
									files.setBranchId(rs
											.getString("BRANCH_ID"));
									files.setLinkId(rs
											.getString("LINK_ID"));
									files.setFileRefno(rs
											.getString("FILE_REFNO"));									
									files.setFileSrlno(rs
											.getString("FILE_SRL_NO"));			
									files.setFileType(rs
											.getString("FILE_TYPE"));									
								return files;
							}

						});			
				
			
									
			return fileMasterList;		
	}

	
	@Override
	public List<FileHistory> getFileObjectForTransferFlht(final String instId,
			final  String branchId,final String typeOfOpt) {
		logger.info("Going to rerieve the key values from flht for file transfer");
		List<FileHistory> fileMasterList = new ArrayList<FileHistory>();	
				FileHistory filehist = null;
				StringBuffer sql = new StringBuffer().append("select ")					
						.append("INST_ID, ")
						.append("BRANCH_ID, ")
						.append("LINK_ID, ")
						.append("FILE_REFNO, ")			
						.append("FILE_SRL_NO, ")			
						.append("FILE_TYPE")						
						.append(" FROM flht ")						
						.append("WHERE ")
						.append("INST_ID = ?")
						.append(" AND BRANCH_ID= ?");
						if(typeOfOpt.equals(ApplicationConstant.DB_TO_DISK_CONSTANT)){
							sql.append(" AND FILE IS NOT NULL");
						}
					
						sql.append(" AND FILE_TYPE NOT LIKE '%PH%'");
				logger.debug("select query :" + sql.toString());				
				fileMasterList =  getJdbcTemplate().query(
						sql.toString(), new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement pss)
									throws SQLException {
								pss.setString(1, instId.trim());									
								pss.setString(2, branchId.trim());										
																	
							}

						}, new RowMapper<FileHistory>() {						

							@Override
							public FileHistory mapRow(ResultSet rs, int arg1)
									throws SQLException {

								FileHistory files = new FileHistory();
										
									files.setInstId(rs
											.getString("INST_ID"));
									files.setBranchId(rs
											.getString("BRANCH_ID"));
									files.setLinkId(rs
											.getString("LINK_ID"));
									files.setFileRefno(rs
											.getString("FILE_REFNO"));									
									files.setFileSrlno(rs
											.getString("FILE_SRL_NO"));			
									files.setFileType(rs
											.getString("FILE_TYPE"));									
								return files;
							}

						});			
							
									
			return fileMasterList;		
	}

	
}
