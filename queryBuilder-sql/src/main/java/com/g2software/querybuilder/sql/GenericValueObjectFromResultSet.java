package com.g2software.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class GenericValueObjectFromResultSet implements ValueObjectFromResultSet{

	public Object getValueObject(ResultSet rs) throws SQLException {
		int cols = rs.getMetaData().getColumnCount();
		if (cols == 1) {
			Object value = rs.getObject(1);
			if (value instanceof BigInteger)
				value = Long.valueOf(((BigInteger) value).intValue() + "");
			else if (value instanceof BigDecimal)
				value = Float.valueOf(((BigDecimal) value).floatValue()
						+ "");
			return value;
		} else {
			Map mapFromResultSet = SQLExecutor.getMapFromResultSet(rs, rs.getMetaData(), 1);
			return mapFromResultSet;
		}
	}

}
