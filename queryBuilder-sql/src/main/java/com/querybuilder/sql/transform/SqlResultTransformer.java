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
package com.querybuilder.sql.transform;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlResultTransformer<T> {

	/**
	 * First method called to process the result, use to initialize the container that will hold the results
	 * @param rs
	 * @throws SQLException
	 */
	public void init(ResultSet rs) throws SQLException;
	
	/**
	 * This method is executed ones per row in the result, use to process the row and add it to the results container
	 * @param rs
	 * @throws SQLException
	 */
	public void processRow(ResultSet rs) throws SQLException;
	
	/**
	 * Final method use it to  return the results container 
	 * @return
	 */
	public T getResult();
	
}
