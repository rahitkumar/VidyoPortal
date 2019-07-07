/**
 * 
 */
package com.vidyo.db;

import javax.sql.DataSource;

import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;

/**
 * @author ganesh
 *
 */
public abstract class BaseDAOTest extends UnitilsJUnit4 {
	
	@TestDataSource
    private DataSource dataSource;
	
	protected DataSource getDataSource() {
		return dataSource;
	}
    
}
