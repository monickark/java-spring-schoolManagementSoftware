package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IDataLogListDao {
public List<DataLog> getDataLogList(DataLogKey dataLogKey) throws NoDataFoundException;
}
