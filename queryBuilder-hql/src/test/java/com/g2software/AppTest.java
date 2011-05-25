package com.g2software;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.g2software.querybuilder.hql.HqlBuilder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	public void test() {
		// SelectQueryBuilder query =HqlSelectQueryBuilder
		// .select()
		// .setFields("vol.name,vol.gender,vol.job")
		// .setFrom("Volunteer vol")
		// .addJoin("inner join vol.person per")
		// .addJoin("inner join vol.person per")
		// .addAndCondition("vol.id = :volId")
		// .setParameter("volId",new Long(34));
		// .execute()
		// .list();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver")
				.newInstance();

			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:oracle", "vc_man", "vc_man");
			PreparedStatement select = connection.prepareStatement("Select a.ACCEPTED aCffDa from signupoa_person a");
			ResultSet rs = select.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			for (int i = 1; i <= md.getColumnCount(); i++) {
				System.out.println(md.getColumnLabel(i) + ' '
						+ md.getColumnTypeName(i) + ' ' + md.getScale(i) + ' '
						+ md.getPrecision(i) + ' ' + md.getColumnClassName(i));
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HqlBuilder.select()
			.add("field1")
			.add("field1")
			.end()
			.from()
			.add("tabla1")
			.add("inner join table2 t2")
			.end()
			.where()
			.addAnd("asdasd")
			.addAnd("asdasd")
			.addAnd("asdasd")
			.end();

		/**
		 * 
		 * 
		 * 
		 * List vols = HQLBuilder .select()
		 * .setFields("vol.name,vol.gender,vol.job") .setFrom("Volunteer vol")
		 * .getJoins() .add("inner join vol.person per") .add("inner join
		 * vol.person per") .endJoins() .getConditions() .addAnd("vol.id =
		 * :volId") .addAnd("vol.id = :volId") .addOr("vol.id = :volId")
		 * .endConditions() .setParameter("volId",new Long(34)); .build()
		 * .getResultList();
		 * 
		 * List vols = HQLBuilder.select()
		 * .setFields("vol.name,vol.gender,vol.job") .setFrom("Volunteer vol")
		 * .addJoin("inner join vol.person per") .addJoin("inner join vol.person
		 * per") .addAndCondition("vol.id = :volId") .setParameter("volId",new
		 * Long(34));
		 * 
		 * 
		 * List vols = HQLBuilder.select()
		 * .fields("vol.name,vol.gender,vol.job") .from("Volunteer vol")
		 * .joins() .add("inner join vol.person per") .add("inner join
		 * vol.person per") .endJoins() .where() .and("vol.id = :volId")
		 * .or("vol.id = :volId") .nd("vol.id = :volId") .addOr("vol.id =
		 * :volId") .addOr("vol.id = :volId") .or("vol.id = :volId")
		 * .and("vol.id = :volId") .endWhere()
		 * 
		 * query.joins().add("vol1 = vol3"); query.joins().remove("vol1 =
		 * vol3"); query.joins().replace("vol1 = vol3");
		 * query.where().addAnd("vol1 = vol3");
		 * 
		 * 
		 * 
		 * List vols = HQLBuilder.select()
		 * .fields("vol.name,vol.gender,vol.job") .from("Volunteer vol")
		 * .add("inner join vol.person per") .add("inner join vol.person per")
		 * .where() .addAnd("vol.id = :volId") .addOr("vol.id = :volId")
		 * .orderBy(vol.name.first) .groupBy(vol.gender) .build() .list()
		 * 
		 * List vols = HQLBuilder .select("vol.name,vol.gender,vol.job") .from()
		 * .add("Volunteer vol") .add("inner join vol.person per") .add("inner
		 * join vol.person per") .end() .where() .addAnd("vol.id = :volId")
		 * .addOr("vol.id = :volId") .end() .orderBy(vol.name.first)
		 * .groupBy(vol.gender) .build() .list()
		 * 
		 * List vols = HQLBuilder .select("vol.name,vol.gender,vol.job")
		 * .beginFrom() .add("Volunteer vol") .add("inner join vol.person per")
		 * .add("inner join vol.person per") .end() .beginWhere()
		 * .addAnd("vol.id = :volId") .addOr("vol.id = :volId") .end()
		 * .orderBy(vol.name.first) .groupBy(vol.gender) .build() .list()
		 * 
		 * select() from() where() groupBy() orderBy() build()
		 * 
		 * select("t1.field1,t1.field2") from("Table1 as t1") where("t1.field1=
		 * 1") groupBy("t1.field1") orderBy("t2.field2") build()
		 * 
		 */
		// query.addJoin("inner join vol.interstAreas ia")
		// .addAndCondition("ia.id = :31");
		//    
		// query.getJoins().remove("inner join vol.interstAreas ia");
		// query.setFields("count(vol)")
		// .build()
		// .getResultList();
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
}
