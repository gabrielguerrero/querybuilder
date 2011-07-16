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

public class Field {
//	public static enum Type {
//		LONG,INTEGER, DECIMAL, CHAR, STRING, BOOLEAN, DATE, TIMESTAMP
//	}

	private static final String AS = " as ";;

	private String name;
	private String alias;
//	private Type type;
//	private Object value;
	
	public Field(String name) {
		super();
		this.setName(name);
	}

	public Field(String name, String alias) {
		super();
		this.setName(name);
		this.setAlias(alias);
	}

//	public Field(String name, String alias, Type type) {
//		super();
//		this.setName(name);
//		this.setAlias(alias);
//		this.setType(type);
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Field other = (Field) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	protected String getAlias() {
		return alias;
	}

	public String getName() {
		return name;
	}

//	protected String getStatement() {
//		return getName();
//	}

//	protected Type getType() {
//		return type;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	protected Field setAlias(String alias) {
		this.alias = alias.trim();
		return this;
	}

	private Field setName(String name) {
		this.name = name.trim();
		return this;
	}

//	protected Field setType(Type type) {
//		this.type = type;
//		return this;
//	}
	
//	public void setValue(Object value) {
//		this.value = value;
//	}
	
//	public Object getValue() {
//		return value;
//	}
	
	@Override
	public String toString() {
		return (getAlias() == null) ? getName() : getName() + AS + getAlias();
	}
}
