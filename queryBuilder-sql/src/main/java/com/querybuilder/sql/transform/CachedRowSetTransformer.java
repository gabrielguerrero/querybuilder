package com.querybuilder.sql.transform;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;


public class CachedRowSetTransformer implements SqlResultTransformer<CachedRowSet>{

	CachedRowSet cachedRowSet;
	
	public CachedRowSetTransformer() {
	}
	
	public CachedRowSetTransformer(CachedRowSet cachedRowSet) {
		super();
		this.cachedRowSet = cachedRowSet;
	}

	public void init(ResultSet result) throws SQLException {
		if (cachedRowSet==null)
			cachedRowSet = new com.sun.rowset.CachedRowSetImpl();
		cachedRowSet.populate(result);
	}

	public void processRow(ResultSet rs) throws SQLException {
	}

	public CachedRowSet getResult() {
		return cachedRowSet;
	}

}
