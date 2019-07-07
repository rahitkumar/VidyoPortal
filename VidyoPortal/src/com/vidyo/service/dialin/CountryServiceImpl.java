/**
 * 
 */
package com.vidyo.service.dialin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Country;
import com.vidyo.db.CountryDao;

/**
 * @author ysakurikar
 *
 */
@Service(value = "countryService")
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryDao countryDao;
	
	
	
	/**
	 * @param countryDao the countryDao to set
	 */
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	/**
	 * Get list of all countries
	 * @return
	 */
	public List<Country> getAllCountries() {
		List<Country> list = null; 
		try {
			list = countryDao.getAllCountries();
		} catch (RuntimeException re){
			// ignore it as the list is empty  
		}
		return list;
	}
	
	
}
