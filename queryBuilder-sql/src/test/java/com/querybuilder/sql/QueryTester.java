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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import com.querybuilder.QueryBuilderException;
import com.querybuilder.test.CustomerProto;
import com.querybuilder.test.CustomerProto.CustomerList;

public class QueryTester extends DBTestBase{

//	CREATE TABLE Customer(ID INTEGER PRIMARY KEY,FirstName VARCHAR(20),LastName VARCHAR(30),Street VARCHAR(50),City VARCHAR(25));
//	CREATE TABLE Product(ID INTEGER PRIMARY KEY,Name VARCHAR(30),Price DECIMAL);
//	CREATE TABLE Invoice(ID INTEGER PRIMARY KEY,CustomerID INTEGER,Total DECIMAL, FOREIGN KEY (CustomerId) REFERENCES Customer(ID) ON DELETE CASCADE);
//	CREATE TABLE Item(InvoiceID INTEGER,Item INTEGER,ProductID INTEGER,Quantity INTEGER,Cost DECIMAL,PRIMARY KEY(InvoiceID,Item), FOREIGN KEY (InvoiceId) REFERENCES Invoice (ID) ON DELETE CASCADE, FOREIGN KEY (ProductId) REFERENCES Product(ID) ON DELETE CASCADE);

	ConnectionProvider connectionProvider = new ConnectionProvider() {
		
		public Connection get() {
			try {
				return getConnection();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};
	private SqlQueryFactory queryFactory;
	
	@Before
	public void beforeTest(){
		queryFactory = new SqlQueryFactory(connectionProvider);
	}
	
	@Test
	public void testListOfMapResult() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		List<Map<String,?>> resultList = queryBuilder.execute().getResultList();
		
		for (Map<String, ?> map : resultList) {
			System.out.println(map);
		}
			
		assertThat(resultList.size(), greaterThan(1));
		Map<String, ?> map = resultList.iterator().next();
		assertNotNull(map.get("ID"));
		assertTrue(map.get("ID") instanceof Integer);
		assertNotNull(map.get("FIRSTNAME"));
		assertNotNull(map.get("CITY"));
		assertNotNull(map.get("STREET"));
		assertNotNull(map.get("LASTNAME"));
	}
	@Test
	public void testCachedRowSetResult() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		CachedRowSet resultList = queryBuilder.execute().getResult(new CachedRowSetTransformer());
		resultList.next();
		assertThat(resultList.size(), greaterThan(1));
		assertNotNull(resultList.getString("ID"));
		assertNotNull(resultList.getString("FIRSTNAME"));
		assertNotNull(resultList.getString("CITY"));
		assertNotNull(resultList.getString("STREET"));
		assertNotNull(resultList.getString("LASTNAME"));
	}
	@Test
	public void testMapResult() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		Map<Long,Map> result = queryBuilder.execute().getResultMap();
		
		for (Map.Entry<Long,Map> entry : result.entrySet()) {
			System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
		}
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, Map> entry = result.entrySet().iterator().next();
		assertTrue(entry.getKey() instanceof Long);
		assertNotNull(entry.getValue().get("FIRSTNAME"));
		assertNotNull(entry.getValue().get("CITY"));
		assertNotNull(entry.getValue().get("STREET"));
		assertNotNull(entry.getValue().get("LASTNAME"));
	}
	
	@Test
	public void testMapResult2() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("c.id").from("Customer c");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		Map<Long,Map> result = queryBuilder.execute().getResultMap();
		
		for (Map.Entry<Long,Map> entry : result.entrySet()) {
			System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
		}
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, Map> entry = result.entrySet().iterator().next();
		assertTrue(entry.getKey() instanceof Long);
		assertEquals(Boolean.TRUE,entry.getValue());
	}
	@Test
	public void testMapResult3() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("i.customerId,sum(it.cost)").from("Invoice i","inner join Item it on it.invoiceId=i.id").groupBy("i.customerId");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		Map<Long,Map> result = queryBuilder.execute().getResultMap();
		
		for (Map.Entry<Long,Map> entry : result.entrySet()) {
			System.out.println("key:"+entry.getKey()+" value:"+entry.getValue());
		}
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, ?> entry = result.entrySet().iterator().next();
		assertTrue(entry.getKey() instanceof Long);
		assertTrue(entry.getValue() instanceof Float);
		assertNotNull(entry.getValue());
	}
	
	@Test
	public void testSqlResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		List<Customer> result = queryBuilder.execute().getResult(new SqlResultTransformer<List<Customer>>() {

			List<Customer> list = new ArrayList<Customer>();
			public void init(ResultSet result) throws SQLException {
			}

			public void processRow(ResultSet rs) throws SQLException {
				Customer customer = new Customer();
				list.add(customer);
				customer.setId(rs.getLong("ID"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setCity(rs.getString("CITY"));
				customer.setStreet(rs.getString("STREET"));
			}

			public List<Customer> getResult() {
				return list;
			}
		});
		
		
		assertThat(result.size(), greaterThan(1));
		Customer entry = result.iterator().next();
		assertNotNull(entry.getCity());
		assertNotNull(entry.getFirstName());
		assertNotNull(entry.getId());
		assertNotNull(entry.getLastName());
		assertNotNull(entry.getStreet());
	}
	@Test
	public void testSqlFileResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		final FileOutputStream fileOutputStream = new FileOutputStream(new File("target//temp.cvs"));
		queryBuilder.execute().getResult(new SqlResultTransformer<Object>() {
			
			private PrintWriter writer;

			public void init(ResultSet rs) throws SQLException {
					writer = new PrintWriter(fileOutputStream); 
					writer.write("ID");
					writer.write(',');
					writer.write("FIRSTNAME");
					writer.write(',');
					writer.write("LASTNAME");
					writer.write(',');
					writer.write("CITY");
					writer.write(',');
					writer.write("STREET");
					writer.write('\n');
			}
			
			public void processRow(ResultSet rs) throws SQLException {
				writer.write(rs.getString("ID"));
				writer.write(',');
				writer.write(rs.getString("FIRSTNAME"));
				writer.write(',');
				writer.write(rs.getString("LASTNAME"));
				writer.write(',');
				writer.write(rs.getString("CITY"));
				writer.write(',');
				writer.write(rs.getString("STREET"));
				writer.write('\n');
			}
			
			public Object getResult() {
				writer.flush();
				return null;
			}
		});
		fileOutputStream.close();
		
		File inFile = new File("temp.cvs");
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
	    String headers = br.readLine();
	     String readLine = br.readLine();
	     String[] fields = readLine.split("\\,");
		assertThat(fields.length, equalTo(5));
		assertNotNull(fields[0]);
		assertNotNull(fields[1]);
		assertNotNull(fields[2]);
		assertNotNull(fields[3]);
		assertNotNull(fields[4]);
	}
	
	@Test
	public void testSqlCvsResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		FileOutputStream fileOutputStream = new FileOutputStream(new File("target//temp2.cvs"));
		queryBuilder.execute().getResult(new CvsResultTransfomer(fileOutputStream));
		fileOutputStream.flush();
		fileOutputStream.close();
		
		File inFile = new File("temp2.cvs");
		System.out.println(inFile.getAbsolutePath());
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
	    String headers = br.readLine();
	     String readLine = br.readLine();
	     String[] fields = readLine.split("\\,");
		assertThat(fields.length, equalTo(5));
		assertNotNull(fields[0]);
		assertNotNull(fields[1]);
		assertNotNull(fields[2]);
		assertNotNull(fields[3]);
		assertNotNull(fields[4]);
	}
	@Test
	public void testSqlJsonResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		FileOutputStream fileOutputStream = new FileOutputStream(new File("target//temp.json"));
		queryBuilder.execute().getResult(new JsonResultTransformer(fileOutputStream));
		fileOutputStream.flush();
		fileOutputStream.close();
		
		File inFile = new File("target//temp.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory f = mapper.getJsonFactory();
		JsonParser jsonParser = f.createJsonParser(inFile);
		JsonNode readValueAsTree = jsonParser.readValueAsTree();
		JsonNode jsonNode = readValueAsTree.get(0);
		System.out.println(inFile.getAbsolutePath());
		
		assertFalse(jsonNode.path("ID").isNull());
		assertFalse(jsonNode.path("FIRSTNAME").isNull());
		assertFalse(jsonNode.path("STREET").isNull());
	}
	@Test
	public void testSqlCustomeJsonResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		final FileOutputStream outputStream = new FileOutputStream(new File("target//temp2.json"));
		queryBuilder.execute().getResult(new SqlResultTransformer<Object>() {

			public void init(ResultSet rs) throws SQLException {
				JsonFactory f = new JsonFactory();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				try {
					JsonGenerator g = f.createJsonGenerator(outputStream,JsonEncoding.UTF8);
					g.writeStartArray();
					while(rs.next()){
						g.writeStartObject();
						g.writeFieldName("id");
						g.writeObject(rs.getLong("ID"));
						g.writeFieldName("firstName");
						g.writeObject(rs.getString("FIRSTNAME"));
						g.writeFieldName("street");
						g.writeObject(rs.getString("STREET"));
						g.writeEndObject();
					}
					g.writeEndArray();
					g.flush();
				} catch (IOException e) {
					throw new QueryBuilderException("Exception building Json Result",e);
				}
			}

			public void processRow(ResultSet rs) throws SQLException {}

			public Object getResult() {return null;}
		});
		outputStream.flush();
		outputStream.close();
		
		File inFile = new File("target//temp2.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory f = mapper.getJsonFactory();
		JsonParser jsonParser = f.createJsonParser(inFile);
		JsonNode readValueAsTree = jsonParser.readValueAsTree();
		JsonNode jsonNode = readValueAsTree.get(0);
		System.out.println(inFile.getAbsolutePath());
		
		assertFalse(jsonNode.path("id").isNull());
		assertFalse(jsonNode.path("firstName").isNull());
		assertFalse(jsonNode.path("street").isNull());
	}
	@Test
	public void testSqlProtobufResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		final FileOutputStream outputStream = new FileOutputStream(new File("target//temp3.proto"));
		queryBuilder.execute().getResult(new SqlResultTransformer<Object>() {
			
			public void init(ResultSet rs) throws SQLException {}
			
			public void processRow(ResultSet rs) throws SQLException {
				try {
					CustomerProto.Customer.Builder customer =  CustomerProto.Customer.newBuilder();
					customer.setId(rs.getLong("ID"));
					customer.setFirstName(rs.getString("FIRSTNAME"));
					customer.setLastName(rs.getString("LASTNAME"));
					customer.setStreet(rs.getString("STREET"));
					
					byte[] byteArray = customer.build().toByteArray();
					outputStream.write(byteArray.length);
					outputStream.write(byteArray);
				} catch (IOException e) {
					throw new QueryBuilderException("Exception building protobuf result",e);
				}
			}
			
			public Object getResult() {return null;}
		});
		outputStream.flush();
		outputStream.close();
		
		FileInputStream in = new FileInputStream(new File("target//temp3.proto"));
		int size;
		byte[] byteArray;

//        while ((size = in.read()) != -1) {
//        	byteArray = new byte[size];
//        	in.read(byteArray);
//        	CustomerProto.Customer customer = CustomerProto.Customer.parseFrom(byteArray);
//        	System.out.println(customer);
//        }
		size = in.read();
		byteArray = new byte[size];
		in.read(byteArray);
		CustomerProto.Customer customer = CustomerProto.Customer.parseFrom(byteArray);
		size = in.read();
		byteArray = new byte[size];
		in.read(byteArray);
		com.querybuilder.test.CustomerProto.Customer customer2 = CustomerProto.Customer.parseFrom(byteArray);
		size = in.read();
		byteArray = new byte[size];
		in.read(byteArray);
		com.querybuilder.test.CustomerProto.Customer customer3 = CustomerProto.Customer.parseFrom(byteArray);
		
		assertThat(customer.getId(),equalTo(0L));
		assertThat(customer.getFirstName(),equalTo("Laura"));
		assertThat(customer.getLastName(),equalTo("Steel"));
	}
	@Test
	public void testSqlProtobuf2ResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		CustomerProto.CustomerList list = queryBuilder.execute().getResult(new SqlResultTransformer<CustomerProto.CustomerList>() {
			CustomerProto.CustomerList.Builder list;
			
			public void init(ResultSet rs) throws SQLException {
				list = CustomerProto.CustomerList.newBuilder();
			}
			
			public void processRow(ResultSet rs) throws SQLException {
					CustomerProto.Customer.Builder customer =  CustomerProto.Customer.newBuilder();
					customer.setId(rs.getLong("ID"));
					customer.setFirstName(rs.getString("FIRSTNAME"));
					customer.setLastName(rs.getString("LASTNAME"));
					customer.setStreet(rs.getString("STREET"));
					
					list.addCustomers(customer.build());
			}
			
			public CustomerProto.CustomerList getResult() {
				return list.build();
			}
		});
		final FileOutputStream outputStream = new FileOutputStream(new File("target//temp4.proto"));
		outputStream.write(list.toByteArray());
		outputStream.flush();
		outputStream.close();
		FileInputStream fileInputStream = new FileInputStream(new File("target//temp4.proto"));
		
		CustomerList customerList = CustomerProto.CustomerList.parseFrom(fileInputStream);
		com.querybuilder.test.CustomerProto.Customer customer = customerList.getCustomers(0);
		
		assertThat(customer.getId(),equalTo(0L));
		assertThat(customer.getFirstName(),equalTo("Laura"));
		assertThat(customer.getLastName(),equalTo("Steel"));
	}
	@Test
	public void testListSqlResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		
		List<Customer> result = queryBuilder.execute().getResult(new ListSqlResultTransformer<Customer>(){

			@Override
			public Customer getValueObject(ResultSet rs) throws SQLException {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setCity(rs.getString("CITY"));
				customer.setStreet(rs.getString("STREET"));
				return customer;
			}});
		
		
		assertThat(result.size(), greaterThan(1));
		Customer entry = result.iterator().next();
		assertNotNull(entry.getCity());
		assertNotNull(entry.getFirstName());
		assertNotNull(entry.getId());
		assertNotNull(entry.getLastName());
		assertNotNull(entry.getStreet());
	}
	
	@Test
	public void testMapSqlResultTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		
		Map<Long, Boolean> result = queryBuilder.execute().getResult(new MapSqlResultTransformer<Long, Boolean>() {

			@Override
			public Boolean getValueObject(ResultSet rs) throws SQLException {
				return Boolean.TRUE;
			}

			@Override
			public Long getIdObject(ResultSet rs) throws SQLException {
				return rs.getLong("ID");
			}
		});
		
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, Boolean> entry = result.entrySet().iterator().next();
		assertNotNull(entry.getKey());
		assertNotNull(entry.getValue());
		assertTrue(entry.getValue());
	}
	@Test
	public void testMapSqlResultToBeanTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		Map<Long, Customer> result = queryBuilder.execute().getResult(new MapSqlResultTransformer<Long, Customer>() {
			
			@Override
			public Customer getValueObject(ResultSet rs) throws SQLException {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setCity(rs.getString("CITY"));
				customer.setStreet(rs.getString("STREET"));
				return customer;
			}
			
			@Override
			public Long getIdObject(ResultSet rs) throws SQLException {
				return rs.getLong("ID");
			}
		});
		
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, Customer> entry = result.entrySet().iterator().next();
		assertNotNull(entry.getKey());
		assertNotNull(entry.getValue());
		assertNotNull(entry.getValue().getCity());
		assertNotNull(entry.getValue().getFirstName());
		assertNotNull(entry.getValue().getId());
		assertNotNull(entry.getValue().getLastName());
		assertNotNull(entry.getValue().getStreet());
	}
	@Test
	public void testUniqueBeanSqlResultToBeanTransformer() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Customer");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		Customer customer = queryBuilder.execute().getUniqueResult(new ValueObjectFromResultSet<Customer>() {

			public Customer getValueObject(ResultSet rs) throws SQLException {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setFirstName(rs.getString("FIRSTNAME"));
				customer.setLastName(rs.getString("LASTNAME"));
				customer.setCity(rs.getString("CITY"));
				customer.setStreet(rs.getString("STREET"));
				return customer;
			}
			
		});
		
		
		
		assertNotNull(customer);
		assertNotNull(customer.getCity());
		assertNotNull(customer.getFirstName());
		assertNotNull(customer.getId());
		assertNotNull(customer.getLastName());
		assertNotNull(customer.getStreet());
	}
	@Test
	public void testMapSqlResultTransformerGroupedByKey() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("*").from("Invoice as i");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		
		Map<Long, List<Invoice>> result = queryBuilder.execute().getResult(new MapSqlResultTransformerGroupedByKey<Long, Invoice>() {

			@Override
			public Invoice getBeanForRow(ResultSet rs) throws SQLException{
				Invoice invoice = new Invoice();
				invoice.setId(rs.getLong("ID"));
				invoice.setCustomerId(rs.getLong("CUSTOMERID"));
				invoice.setTotal(rs.getLong("TOTAL"));
				return invoice;
			}

			@Override
			public Long getRowId(ResultSet rs) throws SQLException{
				return rs.getLong("CUSTOMERID");
			}
		});
		
		
		assertThat(result.size(), greaterThan(1));
		Entry<Long, List<Invoice>> entry = result.entrySet().iterator().next();
		assertNotNull(entry.getKey());
		assertNotNull(entry.getValue());
	}
	
	
	@Test
	public void testSqlJoins() throws Exception{
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		queryBuilder.select("it.*").from("Customer as c",
				                         "inner join Invoice as i on i.customerId = c.id",
				                         "inner join Item it on it.invoiceId = i.id")
									.where().addAnd("c.firstName like :name").end().setParameter("name","Andrew");
		queryBuilder.build();
		System.out.println(queryBuilder.getBuiltQuery());
		List<Map<String,?>> resultList = queryBuilder.execute().getResultList();
		
		for (Map<String, ?> map : resultList) {
			System.out.println(map);
		}
			
		assertThat(resultList.size(), greaterThan(1));
		Map<String, ?> map = resultList.iterator().next();
		assertNotNull(map.get("COST"));
		assertNotNull(map.get("INVOICEID"));
		assertNotNull(map.get("ITEM"));
		assertNotNull(map.get("QUANTITY"));
	}
	
	@Test
	public void testInsert() throws Exception{
		
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		Integer count = (Integer) queryBuilder.select("count(*)").from("Customer c").execute().getUniqueResult();
		
		SqlInsertQueryBuilder newInsertQueryBuilder = queryFactory.newInsertQueryBuilder();
		newInsertQueryBuilder.insertInto("Customer")
		.setFieldValue("ID", 55)
		.setFieldValue("FIRSTNAME", "Gab")
		.setFieldValue("LASTNAME", "Gue").build();
		System.out.println("sql "+ newInsertQueryBuilder.getBuiltQuery());
		int updateCount = newInsertQueryBuilder.execute().getUpdateCount();
		
		assertEquals(1,updateCount);
		
		Integer count2 = (Integer) queryBuilder.execute().getUniqueResult();
		assertEquals(count.intValue() +1,count2.intValue());
		
		Map map = (Map) queryBuilder.select("*").where("c.id = :id").setParameter("id", 55).execute().getUniqueResult();
		
		assertEquals(map.get("ID"),55);
		assertEquals(map.get("FIRSTNAME"),"Gab");
		assertEquals(map.get("LASTNAME"),"Gue");
	}
	
	@Test
	public void testInsert2() throws Exception{
		
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		Integer count = (Integer) queryBuilder.select("count(*)").from("Customer c").execute().getUniqueResult();
		
		SqlInsertQueryBuilder insertQueryBuilder = queryFactory.newInsertQueryBuilder().insertInto("Customer").fields("ID","FIRSTNAME","LASTNAME")
												.setValuesFrom(queryBuilder.select("c.id+500,c.firstName,c.lastName"));
		insertQueryBuilder.build();
		System.out.println("sql "+ insertQueryBuilder.getBuiltQuery());
		int updateCount = insertQueryBuilder.execute().getUpdateCount();
		
		assertThat(updateCount, equalTo(count));
		Integer count2 = (Integer) queryBuilder.select("count(*)").build().execute().getUniqueResult();
		assertThat(count.intValue() *2,equalTo(count2.intValue()));
	}
	
	@Test
	public void testUpdate() throws Exception{
		
		SqlSelectQueryBuilder selectQuery = queryFactory.newSelectQueryBuilder();
		
		SqlUpdateQueryBuilder updateQuery = queryFactory.newUpdateQueryBuilder();
		updateQuery.update("Customer c")
							 .setFieldValue("FIRSTNAME", "Gabriel")
							 .setFieldValue("LASTNAME", "Guerrero")
							 .where("c.firstName like :name")
							 .setParameter("name", "Andrew");
		updateQuery.build();
		System.out.println("sql: "+ updateQuery.getBuiltQuery());
		
		int updateCount = updateQuery.execute().getUpdateCount();
		
		assertThat(updateCount,greaterThan(1));
		
		//execute the query again to ensure none more andrews are left
		selectQuery.select("*").from("Customer c").where("c.firstName like :name").setParameter("name", "Andrew").execute().getUniqueResult();
		assertNull(selectQuery.execute().getUniqueResult());
	}

	
	@Test
	public void testUpdate2() throws Exception{
		
		SqlSelectQueryBuilder selectQuery = queryFactory.newSelectQueryBuilder();
		SqlUpdateQueryBuilder updateQuery = queryFactory.newUpdateQueryBuilder();
		
		selectQuery.select("c.id").from("Customer c").where("c.firstName like :name").setParameter("name", "James");
		
		updateQuery.update("Customer c2")
				.setFieldValue("FIRSTNAME", "Gabriel")
				.setFieldValue("LASTNAME", "Guerrero")
				.where("c2.id in  :ids")
				.setParameter("ids", selectQuery).build();
		
		System.out.println("sql: "+updateQuery.getBuiltQuery());
		int updateCount = updateQuery.setParameter("ids", selectQuery).execute().getUpdateCount();
		assertThat(updateCount,greaterThan(1));
		
		//execute the query again to ensure none more andrews are left
		assertNull(selectQuery.execute().getUniqueResult());
	}
	@Test
	public void testDelete() throws Exception{
		
		SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
		SqlDeleteQueryBuilder deleteQuery = queryFactory.newDeleteQueryBuilder();
		
		Integer count = (Integer) queryBuilder.select("count(*)").from("Customer c").where("c.firstName like :name").setParameter("name", "Bob").build().execute().getUniqueResult();

		queryBuilder.select("c.id").from("Customer c").where("c.firstName like :name").setParameter("name", "James");
		
		deleteQuery.deleteFrom("Customer c2").where("c2.id in :ids").setParameter("ids", queryBuilder).build();
		
		int updateCount = deleteQuery.execute().getUpdateCount();
		
		assertThat(updateCount,lessThan(count));
		Integer count2 = (Integer) queryBuilder.select("count(*)").build().execute().getUniqueResult();
		assertThat(count2,lessThan(count));
	}
	
//	public PagedResult search(QueryParameters queryParameters) {
//		  SqlSelectQueryBuilder queryBuilder = queryFactory.newSelectQueryBuilder();
//		  //basic query
//		  queryBuilder.select("p.id, p.name, p.salary, j.jobDescription")
//		                                .from("Person p","inner join Job j on p.jobId=j.id");
//		  
//		  if (queryParameters.getDepartmentNameSearch()!=null){
//		        // if we have a department search add a join to department and a extra where condition 
//		        queryBuilder.from().add("inner join Department d on p.departmentId=d.id");
//		        queryBuilder.where().addAnd("d.name like :departmentName");
//		        queryBuilder.setParameter("departmentName",queryParameters.getDepartmentNameSearch());
//		  }
//		  if (queryParameters.getSalaryGreaterThan()!=null){
//		        // in this case no extra join is needed, just add an extra where condition
//		        queryBuilder.where().addAnd("p.salary >= :salary");
//		        queryBuilder.setParameter("salary",queryParameters.getSalaryGreaterThan());
//		  }
//		  //set the pagination limits
//		  queryBuilder.setFirstResults(queryParameters.getStartRow());
//		  queryBuilder.setMaxResults(queryParameters.getNumberOfRows());
//		  //execute query
//		  queryBuilder.build();
//		  if (log.isDebugEnabled()){
//		        log.debug("sql:"+queryBuilder.getBuiltQuery()+" parameters:"+queryBuilder.getParameters());
//		  }
//		  List<Map<String,?>> result = queryBuilder.execute().getResultList();
//		  
//		  //calculate total count
//		  queryBuilder.select("count(p.id)");// this replaces select("p.id, p.name, p.salary, j.jobDescription")
//		  queryBuilder.clearFirstAndMaxResultLimits();
//		  // we can remove the inner join to the Job table because is not needed in the count
//		  queryBuilder.from().remove("inner join Job j on p.jobId=j.id");//be sure to use the exact same string
//		  //queryBuilder.from().remove(1);//we could also remove by position
//		  
//		  //execute query
//		  queryBuilder.build();
//		  Integer totalCount = (Integer) queryBuilder.execute().getUniqueResult();
//		  
//		  //now return your paged result
//		  PagedResult pagedResult = new PagedResult(result,totalCount);
//		  return pagedResult;
//		 }
}