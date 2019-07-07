/**
 * 
 */
package com.vidyo.db;

import java.util.List;

import com.vidyo.bo.Country;

/**
 * @author ysakurikar
 *
 */
public interface CountryDao {

	/**
	 * Get list of all countries
	 * @return
	 */
	public List<Country> getAllCountries();


}
