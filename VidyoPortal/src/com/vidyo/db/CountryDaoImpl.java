package com.vidyo.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.vidyo.bo.Country;
import com.vidyo.db.repository.CountryRepository;

@Component(value = "countryDao")
public class CountryDaoImpl implements CountryDao {
	
	protected static final Logger logger = LoggerFactory.getLogger(CountryDaoImpl.class);

	@Autowired
	private CountryRepository countryRepository;
	
	/**
	 * Get list of all countries
	 * @return
	 */
	@Override
	@Cacheable(cacheName = "countryCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public List<Country> getAllCountries() {
		List<Country> country = countryRepository.findByActive(1);
		if (country.isEmpty()) {
			logger.error("Cannot retrieve country data, empty country data.");
			// Throw exception to avoid caching null data
			throw new RuntimeException("Cannot retrieve country data, empty country data.");
		}
		
		return country;
	}
	
}
