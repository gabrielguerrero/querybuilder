package com.querybuilder.sql;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import com.querybuilder.QueryBuilderException;

public class JsonResultTransformer implements SqlResultTransformer<Object> {

	private OutputStream outputStream;
	
	public JsonResultTransformer(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public void init(ResultSet rs) throws SQLException {
		JsonFactory f = new JsonFactory();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		try {
			JsonGenerator g = f.createJsonGenerator(outputStream,JsonEncoding.UTF8);
			g.writeStartArray();
			while(rs.next()){
				g.writeStartObject();
				for (int i = 1; i <= columnCount; i++) {
					g.writeFieldName(metaData.getColumnLabel(i));
					g.writeObject(rs.getObject(i));
				}
				g.writeEndObject();
			}
			g.writeEndArray();
			g.flush();
		} catch (IOException e) {
			throw new QueryBuilderException("Exception building Json Result",e);
		}
	}

	public void processRow(ResultSet rs) throws SQLException {}

	public Object getResult() {
		return null;
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}
}
