package com.g2software.querybuilder.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapSqlResultTransformerGroupedByKey<K,V> implements SqlResultTransformer<Map<K,List<V>>> {

	protected Map<K,List<V>> resultMap;
	private int cols;

	public MapSqlResultTransformerGroupedByKey(int initialMapCapacity) {
		this.resultMap = new HashMap(initialMapCapacity);
	}

	public MapSqlResultTransformerGroupedByKey(int mapInitialCapacity, float mapLoadFactor) {
		this.resultMap = new HashMap(mapInitialCapacity, mapLoadFactor);
	}

	public MapSqlResultTransformerGroupedByKey(Map mapResult) {
		this.resultMap = mapResult;
	}
	
	public void init(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		cols = meta.getColumnCount();
	}

	public void processRow(ResultSet rs) {
		K rowId = getRowId(rs);
		List<V> list = resultMap.get(rowId);
		if (list==null){
			list = new ArrayList<V>();
			resultMap.put(rowId,list);
		}
		list.add(getBeanForRow(rs));
	}
	
	public abstract V getBeanForRow(ResultSet rs);
	
	public abstract K getRowId(ResultSet result);
	
	public Map<K,List<V>> getResult() {
		return resultMap;
	}

}
