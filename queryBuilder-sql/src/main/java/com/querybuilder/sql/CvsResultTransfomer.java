package com.querybuilder.sql;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CvsResultTransfomer implements SqlResultTransformer<Object>{

	private OutputStream outputStream;
	private PrintWriter writer;
	private int columnCount;
	
	
	public CvsResultTransfomer(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	public void init(ResultSet rs) throws SQLException {
		writer = new PrintWriter(outputStream); 
		ResultSetMetaData metaData = rs.getMetaData();
		columnCount = metaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			if (i>1)
				writer.write(',');
			writer.write(metaData.getColumnLabel(i));
		}
		writer.write('\n');
	}

	public void processRow(ResultSet rs) throws SQLException {
		for (int i = 1; i <= columnCount; i++) {
			if (i>1)
				writer.write(',');
			writer.write(rs.getString(i));
		}
		writer.write('\n');
	}

	public Object getResult() {
		writer.flush();
		return null;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	public PrintWriter getWriter() {
		return writer;
	}
}
