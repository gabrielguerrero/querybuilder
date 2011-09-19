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
package com.querybuilder.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.querybuilder.QueryBuilder;
import com.querybuilder.QueryBuilderException;
import com.querybuilder.QueryExecutor;
import com.querybuilder.SelectQueryBuilder;
import com.querybuilder.sql.transform.GenericListSqlResultTransformer;
import com.querybuilder.sql.transform.GenericMapSqlResultTransformer;
import com.querybuilder.sql.transform.GenericValueObjectFromResultSet;
import com.querybuilder.sql.transform.SqlResultTransformer;
import com.querybuilder.sql.transform.ValueObjectFromResultSet;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SQLExecutor extends QueryExecutor {

	Connection connection;
	int updateCount;
	private PreparedStatement statement;
	private ResultSet resultSet;
	private ConnectionProvider connectionProvider;

	public SQLExecutor(Connection connection) {
		this.connection = connection;
	}
	public SQLExecutor(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	protected ResultSet execute() {
		boolean isUpdateQuery = false;
		//ensure no results or statements were left open
		close();

		String sql = getQueryBuilder().getBuiltQuery();
		try {
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
			
			statement = (connection!=null)? connection.prepareStatement(sql) : connectionProvider.get().prepareStatement(sql);
			// now set the real values to the statements in the right order by setting them in the same order of the :variableName
			Pattern p = Pattern.compile("\\:(\\w+)");
			Matcher m = p.matcher(getQueryBuilder().getBuiltQuery());
			int i = 1;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			while (m.find()) {
				String variableName = m.group(1);
				Object value = getQueryBuilder().getParameters()
					.get(variableName);
				if (value instanceof SelectQueryBuilder){
					continue;
				}
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
			throw new QueryBuilderException("Error executing query sql:"+sql, e);
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
		closeConnection();
	}


	private void closeResultSet() {
		try {
			if (resultSet != null){
				resultSet.close();
				resultSet=null;
			}	
		} catch (SQLException e) {
			throw new QueryBuilderException("Error closing resultset", e);
		}
	}


	private void closeStatement() {
		try {
			if (statement != null){
				statement.close();
				statement = null;
			}	
		} catch (SQLException e) {
			throw new QueryBuilderException("Error closing statement", e);
		}
	}
	
	private void closeConnection() {
		try {
			if (connection != null){
				connection.close();
				connection = null;
			}	
		} catch (SQLException e) {
			throw new QueryBuilderException("Error closing statement", e);
		}
	}
	
	public Connection getConnection() {
		if (connection!=null)
			return connection;
		else
			return connectionProvider.get();
	}

	@Override
	public QueryBuilder getQueryBuilder() {
		return super.getQueryBuilder();
	}

	@Override
	protected void init(QueryBuilder queryBuilder) {
		super.init(queryBuilder);
		execute();
	}
	/**
	 * Transforms the ResultSet to the class of type T defined by <code>resultSetProcessor</code>
	 * e.g.: 
	 * <pre>
	 * List<Customer> result = queryBuilder.execute().getResult(new ListSqlResultTransformer<Customer>(){
	 * 
     *          public Customer getValueObject(ResultSet rs) throws SQLException {
     *                  Customer customer = new Customer();
     *                  customer.setId(rs.getLong("ID"));
     *                  customer.setFirstName(rs.getString("FIRSTNAME"));
     *                  customer.setLastName(rs.getString("LASTNAME"));
     *                  customer.setCity(rs.getString("CITY"));
     *                  customer.setStreet(rs.getString("STREET"));
     *                  return customer;
     *          }});
	 * 
	 * </pre>
	 * @param <T>
	 * @param resultSetProcessor
	 * @return
	 */
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
	
	/**
	 * Returns a list of maps, find the value of each column by using the column name as a key on the map, 
	 * if you use an alias in your select the alias will be the key on the map
	 */
	@Override
	public List getResultList() {
		if	(!(getQueryBuilder() instanceof SqlSelectQueryBuilder))
			throw new QueryBuilderException("This method can only be called for SqlSelectQueryBuilder");
		SqlSelectQueryBuilder queryBuilder = (SqlSelectQueryBuilder)getQueryBuilder();
	
		if (queryBuilder.getResultTransformer()!=null)
			return (List)getResult(queryBuilder.getResultTransformer());
		return (List)getResult(new GenericListSqlResultTransformer());
	}
	
	/**
	 * returns a map where the key is the first column, and the value depends on number of columns in the result set,
	 *  <ul>
	 *  <li> if the result has only one column the value is Boolean.TRUE,
	 *  <li> if it has 2 columns the second column is the value
	 *  <li> if it has more than 2 columns, the value is a map with the rest of the columns values
	 *  </ul>
	 */
	@Override
	public Map getResultMap() {
		if	(!(getQueryBuilder() instanceof SqlSelectQueryBuilder))
			throw new QueryBuilderException("This method can only be called for SqlSelectQueryBuilder");
		SqlSelectQueryBuilder queryBuilder = (SqlSelectQueryBuilder)getQueryBuilder();
		if (queryBuilder.getResultTransformer()!=null)
			return (Map)getResult(queryBuilder.getResultTransformer());
		return (Map)getResult(new GenericMapSqlResultTransformer());
	}

	/**
	 * Returns
	 * <ul>
	 * <li> if the column count is equal to 1 return the value of the first column of the first row
	 * <li> if the column count is greater than 1 returns a Map of the first row, with the values of all the columns using the colum names as keys
	 * </ul>
	 */
	@Override
	public Object getUniqueResult() {
		if	(!(getQueryBuilder() instanceof SqlSelectQueryBuilder))
			throw new QueryBuilderException("This method can only be called for SqlSelectQueryBuilder");

		SqlSelectQueryBuilder queryBuilder = (SqlSelectQueryBuilder)getQueryBuilder();
		if (queryBuilder.getResultTransformer() instanceof ValueObjectFromResultSet)
			return getUniqueResult((ValueObjectFromResultSet)queryBuilder.getResultTransformer());
		return getUniqueResult(new GenericValueObjectFromResultSet());
	}
	
	/**
	 * Transforms the first row of the ResultSet to the type defined in <code>getValueObject</code>
	 * e.g.:
	 * <pre>
	 * Customer customer = queryBuilder.execute().getUniqueResult(new ValueObjectFromResultSet<Customer>() {
     *      
	 * 	    public Customer getValueObject(ResultSet rs) throws SQLException {
	 * 	    	Customer customer = new Customer();
	 * 	    	customer.setId(rs.getLong("ID"));
	 * 	    	customer.setFirstName(rs.getString("FIRSTNAME"));
	 * 	    	customer.setLastName(rs.getString("LASTNAME"));
	 * 	    	customer.setCity(rs.getString("CITY"));
	 * 	    	customer.setStreet(rs.getString("STREET"));
	 * 	    	return customer;
	 * 	    }
	 * 	
	 *	});
	 * </pre>
	 * @param <V>
	 * @param getValueObject
	 * @return
	 */
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
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
		this.connection=null;
	}
	
	public static Map getMapFromResultSet(ResultSet rs,ResultSetMetaData resultSetMetaData,int startColumn) throws SQLException {
		int cols = resultSetMetaData.getColumnCount();
		Map object = new HashMap(cols);
		for (int i = startColumn; i <= cols; i++) {
			Object value = rs.getObject(i);
			String name = resultSetMetaData.getColumnLabel(i);
			if (value instanceof BigInteger)
				value = Long.valueOf(((BigInteger) value).intValue() + "");
			else if (value instanceof BigDecimal)
				value = Float.valueOf(((BigDecimal) value).floatValue() + "");
			if (name == null)
				name = resultSetMetaData.getCatalogName(i);
			object.put(name, value);
		}
		return object;
	}
}
