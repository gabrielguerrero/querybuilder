package com.g2software.querybuilder.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListSqlResultTransformer<T> implements SqlResultTransformer<List<T>>,ValueObjectFromResultSet<T>{

	protected List<T> resultList; 
	private int cols;
	private ResultSetMetaData resultSetMetaData;
	
	public void init(ResultSet rs) throws SQLException {
		resultList= new ArrayList<T>();
		resultSetMetaData = rs.getMetaData();
		cols = resultSetMetaData.getColumnCount();
	}

	public final void processRow(ResultSet rs) throws SQLException {
		resultList.add(getValueObject(rs));
	}
	
	public abstract T getValueObject(ResultSet rs) throws SQLException;
	
	public List<T> getResult() {
		return resultList;
	}
	
	protected int getColumnCount(){
		return cols;
	}
	
	protected ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}
}
