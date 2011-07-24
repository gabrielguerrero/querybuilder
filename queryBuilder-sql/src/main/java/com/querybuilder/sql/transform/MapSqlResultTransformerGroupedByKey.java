/*
 * Copyright (c) 2011 Gabriel Guerrero.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.querybuilder.sql.transform;

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

	public MapSqlResultTransformerGroupedByKey() {
		this.resultMap = new HashMap();
	}
	
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

	public void processRow(ResultSet rs) throws SQLException{
		K rowId = getRowId(rs);
		List<V> list = resultMap.get(rowId);
		if (list==null){
			list = new ArrayList<V>();
			resultMap.put(rowId,list);
		}
		list.add(getBeanForRow(rs));
	}
	
	public abstract V getBeanForRow(ResultSet rs) throws SQLException;
	
	public abstract K getRowId(ResultSet rs) throws SQLException;
	
	public Map<K,List<V>> getResult() {
		return resultMap;
	}

}
