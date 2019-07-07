/**
 * 
 */
package com.vidyo.db.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.vidyo.bo.Country;

/**
 * @author ysakurikar
 *
 */
public interface CountryRepository extends JpaRepository<Country, Integer> {

	/**
	 * Fetch the Country by countryID
	 * @param countryID
	 * @return
	 */
	public Country findByCountryID(@Param ("countryID") int countryID);
	
	/**
	 * Fetch all the active countries
	 * @param active
	 * @return
	 */
	public List<Country> findByActive(@Param ("active") int active);

	/**
	 * Fetch Country by country code iso
	 * @param active
	 * @param iso
	 * @return
	 */
	public Country findByActiveAndIso(@Param ("active") int active, @Param ("iso") String iso);
	
	/**
	 * Fetch Country by country name
	 * @param active
	 * @param name
	 * @return
	 */
	public Country findByActiveAndName(@Param ("active") int active, @Param ("name") String name);
	
	/**
	 * Fetch Country by country code long
	 * @param active
	 * @param iso3
	 * @return
	 */
	public Country findByActiveAndCountryCodeLong(@Param ("active") int active, @Param ("countryCodeLong") String countryCodeLong);

}
