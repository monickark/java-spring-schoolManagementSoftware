package com.jaw.common.util.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IComCodeColumnListDao {
public List<ComCodeColumn> getCommonCodeColumnList(ComCodeColumnSearch comCodeColumnKey) throws NoDataFoundException ;

}
