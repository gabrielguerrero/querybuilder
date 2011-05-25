package com.g2software.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
class GenericListSqlResultTransformer extends ListSqlResultTransformer {

	@Override
	public Object getValueObject(ResultSet rs) throws SQLException {
		int cols = getColumnCount();
		Map object = new HashMap(cols);
		for (int i = 1; i <= cols; i++) {
			Object value = rs.getObject(i);
			String name = getResultSetMetaData().getColumnLabel(i);
			if (value instanceof BigInteger)
				value = Long.valueOf(((BigInteger) value).intValue() + "");
			else if (value instanceof BigDecimal)
				value = Float.valueOf(((BigDecimal) value).floatValue() + "");
			if (name == null)
				name = getResultSetMetaData().getCatalogName(i);
			object.put(name, value);
		}
		return object;
	}

}
