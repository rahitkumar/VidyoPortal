/**
 * 
 */
package com.vidyo.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.TenantDialInCountry;
import com.vidyo.bo.tenant.Tenant;
import com.vidyo.db.repository.TenantDialInRepository;
import com.vidyo.db.repository.tenant.TenantRepository;

/**
 * @author ysakurikar
 *
 */
@Component(value = "tenantDialInDao")
public class TenantDialInDaoImpl implements TenantDialInDao {
	
	protected static final Logger logger = LoggerFactory.getLogger(TenantDialInDaoImpl.class);
	
	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private TenantDialInRepository tenantDialInRepository;
	
	/**
	 * Get Tenant dial in country list
	 * @param tenantId
	 * @return
	 */
	@Override
	@Cacheable(cacheName = "tenantDialInCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public List<TenantDialInCountry> getTenantDialInCounties (int tenantId){
		List<TenantDialInCountry> tenantDialInCounties = tenantDialInRepository.findByTenantTenantIdOrderByCountryNameAsc(tenantId);
		if (tenantDialInCounties.isEmpty()) {
			logger.error("Cannot retrieve tenant dial in data, empty tenant dial in data.");
			// Throw exception to avoid caching null data
			throw new RuntimeException("Cannot retrieve tenant dial in data, empty tenant dial in data.");
		}
		return tenantDialInCounties;
	}
	
	/**
	 * Create/Update the TenantDialInCountries in the database. 
	 * @param tenantDialInCountries
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName="tenantDialInCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public List<TenantDialInCountry> saveAllTenantDialInCountries(@PartialCacheKey int tenantId, List<TenantDialInCountry> tenantDialInCountries) {
		tenantDialInCountries = tenantDialInRepository.save(tenantDialInCountries);
		
		return tenantDialInCountries;
	}

	/**
	 * Delete the TenantDialInCountry
	 * @param tenantId
	 */
	@Override
	@TriggersRemove(cacheName="tenantDialInCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public boolean deleteTenantDialInCountryForTenant(int tenantId) {
		return tenantDialInRepository.deleteByTenantTenantId(tenantId) > 0;
	}

	/**
	 * Returns Tenant by tenant id
	 *
	 */
	@Override
	public Tenant getTenantDetails(int tenantId) {
		return tenantRepository.findByTenantId(tenantId);
	}

	
}
