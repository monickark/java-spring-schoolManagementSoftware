package com.jaw.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.files.dao.FileHistory;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.framework.dao.BaseDao;

@Repository
public class BatchFileUploadDao extends BaseDao implements IBatchFileUploadDao {
	//Logging
			Logger logger = Logger.getLogger(BatchFileUploadDao.class);	

			@Override
			public void batchFileUpload(final BatchFileUpload batchFileUpload,
					final ArrayList<MulitpartFileObject> listOfFiles, final Integer startingSeq,final List<String> LISTOFLINKID,final List<String> LISTOFSRLNO) throws RuntimeExceptionForBatch {				
				logger.info("Going to insert the file(s) into flmt");
				StringBuffer sql = new StringBuffer("insert into filemaster(")
				.append("DB_TS, ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("LINK_ID, ")
				.append("FILE_REFNO, ")
				.append("FILE_SRL_NO, ")
				.append("FILE, ")
				.append("FILE_TYPE, ")
				.append("FILE_NAME, ")
				.append("FILE_PATH, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME,")
				.append("CONTENT_TYPE,")
				.append("FILE_SIZE")
				.append(") values(?,?,?,?,?,?,?,?,?,?,'N','UPLD',now(),'UPLD',now(),?,?)");
				try {
					getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
					 
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							MulitpartFileObject multipartFile = listOfFiles.get(i);
							Integer fileRefId = 0;
							fileRefId = startingSeq+i;												
							ps.setInt(1, 1);							
							ps.setString(2, batchFileUpload.getInstId().trim());
							
							ps.setString(3, batchFileUpload.getBranchId().trim());
							
							ps.setString(4, LISTOFLINKID.get(i));
							
							if(multipartFile!=null){
								
								ps.setBinaryStream(7, multipartFile.getInputStream(),multipartFile.getSize());
								ps.setString(11, multipartFile.getContentType().trim());
								
								ps.setLong(12, multipartFile.getSize());	
							}else{								
								ps.setBinaryStream(7, null);
								ps.setString(11, null);								
								ps.setLong(12, 0);	
							}
																				
							
							ps.setString(5, ApplicationConstant.STRING_FILE_MASTER_SEQ.concat(fileRefId.toString()));		
							
							ps.setString(6, LISTOFSRLNO.get(i));
							
										
							
							ps.setString(8, batchFileUpload.getFileType().trim());	
							
							ps.setString(9, batchFileUpload.getFileName().get(i).trim());	
							
							ps.setString(10, batchFileUpload.getFilePath().get(i).trim());	
							
												
						}
					 
						@Override
						public int getBatchSize() {
							return listOfFiles.size();
						}
					  });
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw new RuntimeExceptionForBatch();
				}
			
				  logger.debug("sql query :"+sql.toString());
				
			}

			@Override
			public List<String> getLinkId(final String instId, final String branchId,
					final String fileType) throws NoDataFoundException {
				logger.info("Going to retrieve the Link Id from flmt");
				StringBuffer sql=new StringBuffer();		
				sql.append("select ")
				.append("LINK_ID,")	
				.append("CONTENT_TYPE")	
				.append(" from flmt ")				
				.append("where ")			
				.append("DEL_FLG=? ")
				.append(" and")
				.append(" INST_ID = ?" )
				.append(" and")
				.append(" BRANCH_ID = ?" )
				.append(" and")
				.append(" FILE_TYPE = ?" );						
				
				List<String> linkIdList =  getJdbcTemplate().query(sql.toString(),new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss) throws SQLException {

						pss.setString(1, "N");
						pss.setString(2, instId.trim());
						pss.setString(3, branchId.trim());
						pss.setString(4, fileType.trim());

					}
				}, new LinkIdListRowMapper());
				
				if(linkIdList.size()==0){
					throw new NoDataFoundException();
				}
				return linkIdList;
			}

			@Override
			public void batchDeleteExistingFiles(final String instId,final
					String branchId,final String fileType, final List<String> linkId) throws RuntimeExceptionForBatch {		
				logger.info("Going to delete the file(s) into flmt");
				StringBuffer sql=new StringBuffer()
				.append("Delete  from  ")
				.append("flmt  ")				
				.append(" where ")
				.append(" INST_ID=?")
				.append(" AND BRANCH_ID =?")
				.append(" AND FILE_TYPE = ?")
				.append(" AND LINK_ID = ?")
				.append(" AND DEL_FLG ='N'");
				
				 try {
					getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
						 
							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {														
								ps.setString(1, instId.trim());
								ps.setString(2, branchId.trim());
								ps.setString(3, fileType.trim());
								ps.setString(4, linkId.get(i).trim());							
							}
						 
							@Override
							public int getBatchSize() {
								return linkId.size();
							}
						  });
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw new RuntimeExceptionForBatch();
				}
				logger.debug("update query :"+sql.toString());
				
			}

			@Override
			public List<FileMaster> getEntireExistingRec(final String instId,
					final String branchId, final String fileType, final List<String> linkId) throws DatabaseException {
				logger.info("Going to rerieve the whole file(s) object from flmt");
				List<FileMaster> fileMasterList = new ArrayList<FileMaster>();
				try {
					for (int index = 0; index < linkId.size(); index++) {
						FileMaster filemaster = null;
						StringBuffer sql = new StringBuffer().append("select ")
								.append("DB_TS, ")
								.append("INST_ID, ")
								.append("BRANCH_ID, ")
								.append("LINK_ID, ")
								.append("FILE_REFNO, ")
								.append("FILE, ")
								.append("FILE_TYPE, ")
								.append("FILE_SRL_NO, ")
								.append("FILE_NAME, ")
								.append("FILE_PATH, ")
								.append("FILE_SIZE, ")
								.append("CONTENT_TYPE,")
								.append("DEL_FLG, ")
								.append("R_MOD_ID, ")
								.append("R_MOD_TIME, ")
								.append("R_CRE_ID, ")
								.append("R_CRE_TIME")
								.append(" from flmt ")
								.append("where ")
								.append("INST_ID = ?")
								.append(" AND BRANCH_ID= ?")
								.append(" AND LINK_ID= ?")
								.append(" AND DEL_FLG= ?")
								.append(" AND FILE_TYPE = ?;");
						logger.debug("select query :" + sql.toString());
						final String linkID = linkId.get(index).trim();
						filemaster = (FileMaster) getJdbcTemplate().query(
								sql.toString(), new PreparedStatementSetter() {

									@Override
									public void setValues(PreparedStatement pss)
											throws SQLException {
										pss.setString(1, instId);									
										pss.setString(2, branchId.trim());										
										pss.setString(3, linkID);										
										pss.setString(4, "N");
										pss.setString(5, fileType.trim());										
									}

								}, new ResultSetExtractor<FileMaster>() {

									@Override
									public FileMaster extractData(ResultSet rs)
											throws SQLException,
											DataAccessException {

										FileMaster files = new FileMaster();
										if (rs.next()) {
											files.setDbTs(rs.getInt("DB_TS"));
											files.setInstId(rs
													.getString("INST_ID"));
											files.setBranchId(rs
													.getString("BRANCH_ID"));
											files.setLinkId(rs
													.getString("LINK_ID"));
											files.setFileRefno(rs
													.getString("FILE_REFNO"));
											files.setInputStream(rs
													.getBinaryStream("FILE"));
											files.setFileType(rs
													.getString("FILE_TYPE"));
											files.setFileSrlno(rs
													.getString("FILE_SRL_NO"));
											files.setFileName(rs
													.getString("FILE_NAME"));
											files.setFilepath(rs
													.getString("FILE_PATH"));
											files.setSize(Long.valueOf(rs
													.getString("FILE_SIZE")));
											files.setContentType(rs
													.getString("CONTENT_TYPE"));
											files.setDelFlg(rs
													.getString("DEL_FLG"));
											files.setrModId(rs
													.getString("R_MOD_ID"));
											files.setrModTime(rs
													.getString("R_MOD_TIME"));
											files.setrCreId(rs
													.getString("R_CRE_ID"));
											files.setrCreTime(rs
													.getString("R_CRE_TIME"));
										}
										return files;
									}

								});
						if (((filemaster.getInputStream() != null) && (!filemaster
								.getInputStream().equals("")))
								|| ((filemaster.getFilepath() != null) && (!filemaster
										.getFilepath().equals("")))) {
							fileMasterList.add(filemaster);
						}

						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
					return fileMasterList;
					}

			@Override
			public void insertIntoFlhtbeforeDelExistingRec(final List<FileHistory> files,final Integer startingSeq) throws RuntimeExceptionForBatch {
				logger.info("Going to rerieve the whole file(s) object from flmt");
				StringBuffer sql = new StringBuffer("insert into flht(")
				.append("FILE_HT_SRL_NO, ")
				.append("DB_TS, ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("LINK_ID, ")
				.append("FILE_REFNO, ")
				.append("FILE, ")
				.append("FILE_SRL_NO, ")
				.append("FILE_TYPE, ")
				.append("FILE_NAME, ")
				.append("FILE_PATH, ")
				.append("CONTENT_TYPE,")
				.append("DEL_FLG, ")
				.append("FLMT_R_CRE_ID, ")
				.append("FLMT_R_CRE_TIME, ")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME,")		
				.append("FILE_SIZE")				
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'UPLD',now(),?)");
				 
				  try {
					getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {
					 
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							FileHistory fileHistory = files.get(i);
							Integer fileHtSeq = 0;
							fileHtSeq = startingSeq+i;															
							ps.setString(1, ApplicationConstant.STRING_FILE_HISTORY_SEQ.concat(fileHtSeq.toString().trim()));	
							ps.setInt(2, 1);				
							ps.setString(3, fileHistory.getInstId().trim());			
							ps.setString(4, fileHistory.getBranchId().trim());				
							ps.setString(5, fileHistory.getLinkId().trim());				
							ps.setString(6, fileHistory.getFileRefno());	
							if( fileHistory.getInputStream()!=null){
								ps.setBinaryStream(7, fileHistory.getInputStream(),fileHistory.getSize());		
							}else{
								ps.setBinaryStream(7, null);		
							}
								
							ps.setString(8, "1");		
							ps.setString(9, fileHistory.getFileType().trim());
							ps.setString(10, fileHistory.getFileName().trim());
							ps.setString(11, fileHistory.getFilepath().trim());
							if(fileHistory.getContentType()!=null){
								ps.setString(12, fileHistory.getContentType().trim());
							}else{
								ps.setString(12,null);
							}
						
							ps.setString(13, fileHistory.getDelFlg().trim());				
							ps.setString(14, fileHistory.getFlmtRCreId().trim());	
							ps.setString(15, fileHistory.getFlmtRCreTime().trim());							
							ps.setString(16, fileHistory.getSize().toString());
						}
					 
						@Override
						public int getBatchSize() {
							return files.size();
						}
					  });
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw new RuntimeExceptionForBatch();
				}
				  logger.debug("sql query :"+sql.toString());
			}

}
class LinkIdListRowMapper implements RowMapper<String>{
	
	@Override
	public String mapRow(ResultSet rs, int arg1) throws SQLException {				
	 String linkId = rs.getString("LINK_ID");
	 String contentType = rs.getString("CONTENT_TYPE").split("/")[1];
	 return linkId+"."+contentType;
	}
}

