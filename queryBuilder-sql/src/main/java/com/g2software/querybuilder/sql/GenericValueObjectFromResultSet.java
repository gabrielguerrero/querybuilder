package com.g2software.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("rawtypes")
public class GenericValueObjectFromResultSet implements ValueObjectFromResultSet{

	public Object getValueObject(ResultSet rs) throws SQLException {
		 Object value = rs.getObject(1);
		if (value instanceof BigInteger)
			value = Long.valueOf(((BigInteger) value).intValue() + "");
		else if (value instanceof BigDecimal)
			value = Float.valueOf(((BigDecimal) value).floatValue()
					+ "");
		return value;
	}

}
