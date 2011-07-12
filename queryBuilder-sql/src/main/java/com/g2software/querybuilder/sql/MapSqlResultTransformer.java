package com.g2software.querybuilder.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapSqlResultTransformer<K,V> implements SqlResultTransformer<Map<K,V>>, ValueObjectFromResultSet<V> {

	protected Map<K,V> resultMap;
	private int cols;
	private ResultSetMetaData resultSetMetaData;

	public MapSqlResultTransformer(int initialMapCapacity) {
		this.resultMap = new HashMap(initialMapCapacity);
	}
	
	public MapSqlResultTransformer() {
		this.resultMap = new HashMap();
	}

	public MapSqlResultTransformer(int mapInitialCapacity, float mapLoadFactor) {
		this.resultMap = new HashMap(mapInitialCapacity, mapLoadFactor);
	}

	public MapSqlResultTransformer(Map mapResult) {
		this.resultMap = mapResult;
	}
	
	public void init(ResultSet rs) throws SQLException {
		resultSetMetaData = rs.getMetaData();
		cols = resultSetMetaData.getColumnCount();
	}

	public void processRow(ResultSet rs) throws SQLException {
		resultMap.put(getIdObject(rs), getValueObject(rs));
	}
	
	/* (non-Javadoc)
	 * @see com.g2software.querybuilder.sql.ValueObjectFromResultSet#getValueObject(java.sql.ResultSet)
	 */
	public abstract V getValueObject(ResultSet rs)throws SQLException;
	
	public abstract K getIdObject(ResultSet rs) throws SQLException;
	
	public Map<K, V> getResult() {
		return resultMap;
	}

	protected int getColumnCount(){
		return cols;
	}
	
	protected ResultSetMetaData getResultSetMetaData() {
		return resultSetMetaData;
	}
}
