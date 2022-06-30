package com.jaw.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.files.dao.FileMaster;
import com.jaw.framework.dao.BaseDao;
@Repository
public class DownloadBatchFileDao extends BaseDao implements IDownloadBatchFileDao {
	//Logging
		Logger logger = Logger.getLogger(DownloadBatchFileDao.class);
	@Override
	public List<FileMaster> downloadFile(final DownloadFileKey downloadFileKey) throws NoDataFoundException  {		
		logger.info("Going to get the files from database");
		List<String> optList=new ArrayList<String>();
		StringBuffer sql=new StringBuffer()
		.append("select ")	
		.append("LINK_ID, ")
		.append("FILE, ")
		.append("FILE_TYPE, ")
		.append("FILE_SIZE, ")
		.append("FILE_NAME, ")
		.append("FILE_PATH, ")
		.append("CONTENT_TYPE")	
		.append(" from flmt ")
		.append("where ")		
		.append("INST_ID = ?")
		.append(" AND BRANCH_ID= ?")	
		.append(" AND DEL_FLG= ?");		
		optList.add(downloadFileKey.getInstId().trim());
		optList.add(downloadFileKey.getBranchId().trim());
		optList.add("N");	
		if(!downloadFileKey.getFileType().equals("All")){
			sql.append( " and FILE_TYPE = ?");	
			optList.add(downloadFileKey.getFileType().trim());
		}
		
		if(!downloadFileKey.getLinkId().equals("")){
			sql.append( " and LINK_ID = ?");	
			optList.add(downloadFileKey.getLinkId().trim());
		}
		logger.debug("select query :"+sql.toString());		
		String[] array = optList.toArray(new String[optList.size()]);
		List<FileMaster> filemaster =  getJdbcTemplate().query(sql.toString(),array,new DownloadFileRowMapper());		
			 
		if(filemaster.size()==0){					
			throw new NoDataFoundException();
		}		
		logger.info("Files has been retrieved successfully from database");
		return filemaster;
	
	}

}
class DownloadFileRowMapper implements RowMapper<FileMaster>{
	@Override
	public FileMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		FileMaster files = new FileMaster();
		files.setLinkId(rs.getString("LINK_ID"));
		files.setInputStream(rs.getBinaryStream("FILE"));
		files.setFileType(rs.getString("FILE_TYPE"));
		files.setFileName(rs.getString("FILE_NAME"));
		files.setFilepath(rs.getString("FILE_PATH"));
		files.setSize(Long.valueOf(rs.getString("FILE_SIZE")));
		files.setContentType(rs.getString("CONTENT_TYPE"));					
		return files;
	}
}
