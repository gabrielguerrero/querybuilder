package com.g2software.querybuilder.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.g2software.querybuilder.QueryBuilderException;
import com.g2software.querybuilder.QueryExecutor;

public class SQLExecutor extends QueryExecutor {

	Connection connection;
	int updateCount;
	private PreparedStatement statement;
	private ResultSet resultSet;

	protected SQLExecutor(Connection connection) {
		this.connection = connection;
	}


	protected ResultSet execute() {
		boolean isUpdateQuery = false;
		statement = null;
		resultSet = null;

		
		try {
			String sql = getQueryBuilder().getBuiltQuery();
			//Replace parameters for ?
			//First collection
			Set<Map.Entry> parameters2 = getQueryBuilder().getParameters().getParameters().entrySet();
			for (Map.Entry entry : parameters2) {
				if (entry.getValue() instanceof Collection){
					Collection c = (Collection) entry.getValue();
					StringBuilder builder = new StringBuilder("(");
					int i=0;
					for (Object object : c) {
						if (i>0)
							builder.append(',');
						builder.append('?');
						i++;
					}
					builder.append(")");
					sql.replaceAll("\\:"+entry.getKey(), builder.toString());
				}
			}
			//then the rest of the parameters
			sql = sql.replaceAll("\\:\\w+", "?");
			
			statement = connection.prepareStatement(sql);
			// now set the real values to the statements in the right order by setting them in the same order of the :variableName
			Pattern p = Pattern.compile("\\:(\\w+)");
			Matcher m = p.matcher(getQueryBuilder().getBuiltQuery());
			int i = 1;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			while (m.find()) {
				String variableName = m.group(1);
				Object value = getQueryBuilder().getParameters()
					.get(variableName);
				if (value instanceof Date){
					statement.setLong(i, Long.parseLong(dateFormat.format((Date) value)));
					i++;
				} else if (value instanceof Collection) {
					Collection c = (Collection) value;
					for (Object object : c) {
						statement.setObject(i, value);
						i++;
					}
				} else if (value instanceof Short) {
					statement.setShort(i, ((Short) value).shortValue());
					i++;
				} else{
					statement.setObject(i, value);
					i++;
				}
			}

			sql = sql.toLowerCase();

			if (sql.indexOf("update") != -1 || sql.indexOf("insert") != -1
					|| sql.indexOf("delete") != -1) {
				isUpdateQuery = true;
				updateCount = statement.executeUpdate();
			} else {
				resultSet = statement.executeQuery();
			}
		} catch (Exception e) {
			throw new QueryBuilderException(this.getQueryBuilder(), e);
		} finally {
			if (isUpdateQuery) {
				close();
			}
		}

		return resultSet;
	}
	
	@Override
	public void close() {
		closeResultSet();
		closeStatement();
	}


	private void closeResultSet() {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			throw new QueryBuilderException("Error closing resultset", e);
		}
	}


	private void closeStatement() {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			throw new QueryBuilderException("Error closing statement", e);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	@Override
	public SqlSelectQueryBuilder getQueryBuilder() {
		return (SqlSelectQueryBuilder) super.getQueryBuilder();
	}


	public <T> T getResult(SqlResultTransformer<T> resultSetProcessor){
		try {
			resultSetProcessor.init(resultSet);
			while(resultSet.next()){
				resultSetProcessor.processRow(resultSet);
			}
		} catch (SQLException e) {
			throw new QueryBuilderException("Error reading resultset",e);
		} finally{
			close();
		}
		return resultSetProcessor.getResult();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getResultList() {
		if (getQueryBuilder().getResultTransformer()!=null)
			return (List)getResult(getQueryBuilder().getResultTransformer());
		return (List)getResult(new GenericListSqlResultTransformer());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getResultMap() {
		if (getQueryBuilder().getResultTransformer()!=null)
			return (Map)getResult(getQueryBuilder().getResultTransformer());
		return (Map)getResult(new GenericMapSqlResultTransformer());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getUniqueResult() {
		if (getQueryBuilder().getResultTransformer() instanceof ValueObjectFromResultSet)
			return getUniqueResult((ValueObjectFromResultSet)getQueryBuilder().getResultTransformer());
		return getUniqueResult(new GenericValueObjectFromResultSet());
	}
	
	public <V> V getUniqueResult(ValueObjectFromResultSet<V> getValueObject) {
		try {
			while(resultSet.next()){
				V valueObject = getValueObject.getValueObject(resultSet);
				return valueObject;
			}
		} catch (SQLException e) {
			throw new QueryBuilderException("Error reading resultset",e);
		} finally{
			close();
		}
		return null;
	}

	protected int getUpdateCount() {
		return updateCount;
	}

	public SQLExecutor setConnection(Connection connection) {
		this.connection = connection;
		return this;
	}
}
