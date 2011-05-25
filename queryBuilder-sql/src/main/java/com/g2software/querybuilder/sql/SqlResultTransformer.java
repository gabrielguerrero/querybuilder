package com.g2software.querybuilder.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlResultTransformer<T> {

	public void init(ResultSet result) throws SQLException;
	
	public void processRow(ResultSet rs) throws SQLException;
	
	public T getResult();
	
}
