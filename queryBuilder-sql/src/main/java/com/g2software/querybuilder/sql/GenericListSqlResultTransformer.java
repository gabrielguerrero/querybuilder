package com.g2software.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
class GenericListSqlResultTransformer extends ListSqlResultTransformer {

	@Override
	public Object getValueObject(ResultSet rs) throws SQLException {
		Map object = SQLExecutor.getMapFromResultSet(rs,rs.getMetaData(),1);
		return object;
	}


}
