package com.g2software.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

 class GenericMapSqlResultTransformer extends MapSqlResultTransformer{

	public GenericMapSqlResultTransformer() {
		super(1000);
	}

	@Override
	public Object getValueObject(ResultSet rs) throws SQLException {
		int cols = getColumnCount();
		if (cols == 1) {
			return Boolean.TRUE;
		} else if (cols == 2) {
			Object value = rs.getObject(2);
			if (value instanceof BigInteger)
				value = Long.valueOf(((BigInteger) value).intValue() + "");
			else if (value instanceof BigDecimal)
				value = Float.valueOf(((BigDecimal) value).floatValue()
						+ "");
			return value;
		} else {
			Map object = new HashMap(cols);
			for (int i = 2; i <= cols; i++) {
				Object value = rs.getObject(i);
				String name = getResultSetMetaData().getColumnLabel(i);
				if (value instanceof BigInteger)
					value = Long.valueOf(((BigInteger) value).intValue()
							+ "");
				
				else if (value instanceof BigDecimal)
					value = Float.valueOf(((BigDecimal) value).floatValue()
							+ "");
				if (name == null)
					name = getResultSetMetaData().getCatalogName(i);
				object.put(name, value);
			}
			return object;
		}
	}

	@Override
	public Object getIdObject(ResultSet rs) throws SQLException {
		int cols = getColumnCount();
			Object key = rs.getObject(1);
			if (key instanceof BigInteger)
				key = Long.valueOf(((BigInteger) key).intValue() + "");
			else if (key instanceof BigDecimal)
				key = Float.valueOf(((BigDecimal) key).floatValue() + "");
			else if (key instanceof Number)
				key = Long.valueOf(((Number) key).longValue() + "");
		return key;
	}

}
