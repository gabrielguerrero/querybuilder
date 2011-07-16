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
package com.querybuilder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.querybuilder.Field;
import com.querybuilder.FieldsCollection;
import com.querybuilder.QueryBuilder;

public class FieldsCollectionTest {

	QueryBuilder queryBuilder;
	private FieldsCollection<QueryBuilder> fieldList;
	

	@Before
	public void init(){
		queryBuilder = mock(QueryBuilder.class);
		fieldList = new FieldsCollection<QueryBuilder>(queryBuilder);
	}
	
	@Test
	public void testAddField() {
		fieldList.add(new Field("0"));
		fieldList.add(new Field("1"));
		fieldList.add(new Field("2"));
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testAddIntField() {
		fieldList.add("0");
		fieldList.add(1,new Field("1"));
		fieldList.add("2");
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testAddString() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testAddAfter() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
		
		fieldList.addAfter(new Field("x"),new Field("0"));
		assertTrue(fieldList.getFieldsList().indexOf(new Field("x"))==1);
	}

	@Test
	public void testAddAll() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		FieldsCollection<QueryBuilder> newStament = new FieldsCollection<QueryBuilder>(queryBuilder);
		newStament.add("3");
		newStament.add("4");
		newStament.add("5");
		fieldList.addAll(newStament);
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
		assertTrue(fieldList.getFieldsList().contains(new Field("3")));
		assertTrue(fieldList.getFieldsList().contains(new Field("4")));
		assertTrue(fieldList.getFieldsList().contains(new Field("5")));
	}
	
	@Test
	public void testAddBefore() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
		
		fieldList.addBefore(new Field("x"),new Field("1"));
		assertTrue(fieldList.getFieldsList().indexOf(new Field("x"))==1);
	}

	@Test
	public void testAddLast() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
		
		fieldList.addLast(new Field("x"));
		assertTrue(fieldList.getFieldsList().indexOf(new Field("x"))==3);
	}

	@Test
	public void testClear() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		fieldList.clear();
		assertFalse(fieldList.getFieldsList().contains(new Field("0")));
		assertFalse(fieldList.getFieldsList().contains(new Field("1")));
		assertFalse(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testEnd() {
		assertEquals(queryBuilder, fieldList.end());
	}

	@Test
	public void testGetFieldsList() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertNotNull(fieldList.getFieldsList());
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertTrue(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testJoinBy() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		StringBuilder builder = new StringBuilder();
		fieldList.joinBy(',', builder);
		assertEquals("0,1,2", builder.toString());
	}

	@Test
	public void testRemoveField() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		fieldList.remove(new Field("1"));
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertFalse(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testRemoveInt() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		fieldList.remove(1);
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertFalse(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
	}

	@Test
	public void testReplace() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		fieldList.replace(new Field("x"),new Field("1"));
		assertTrue(fieldList.getFieldsList().contains(new Field("0")));
		assertFalse(fieldList.getFieldsList().contains(new Field("1")));
		assertTrue(fieldList.getFieldsList().contains(new Field("2")));
		assertTrue(fieldList.getFieldsList().contains(new Field("x")));
		assertTrue(fieldList.getFieldsList().indexOf(new Field("x"))==1);
	}

	@Test
	public void testSize() {
		fieldList.add("0");
		fieldList.add("1");
		fieldList.add("2");
		assertTrue(fieldList.size()==3);
	}

}
