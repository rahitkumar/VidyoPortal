/**
 * 
 */
package com.vidyo.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.vidyo.bo.EndpointBehavior;
import com.vidyo.db.repository.EndpointBehaviorRepository;

/**
 * @author ysakurikar
 *
 */
@Service(value = "EndpointBehaviorDao")
public class EndpointBehaviorDaoJdbcImpl implements EndpointBehaviorDao {

	@Autowired
	private EndpointBehaviorRepository endpointBehaviorRepository;

	/**
	 * Fetch the EndpointBehavior using endpointBehaviorKey. 
	 * @param endpointBehaviorKey
	 * @return
	 */
	@Override
	@Cacheable(cacheName = "endpointBehaviorCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public EndpointBehavior getEndpointBehaviorByKey(String endpointBehaviorKey) {
		return endpointBehaviorRepository.findByEndpointBehaviorKey(endpointBehaviorKey);
	}

	/**
	 * Get the EndpointBehavior by tenant
	 * @param tenantId
	 * @return
	 */
	@Override
	@Cacheable(cacheName = "endpointBehaviorCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public List<EndpointBehavior> getEndpointBehaviorByTenant(int tenantID) {
		return endpointBehaviorRepository.findByTenantTenantID(tenantID);
	}

	/**
	 * Create/Update the EndpointBehavior in the database. 
	 * @param endpointBehavior
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName="endpointBehaviorCache", removeAll=true)
	public EndpointBehavior saveEndpointBehavior(EndpointBehavior endpointBehavior) {
		endpointBehavior = endpointBehaviorRepository.save(endpointBehavior);
		
		return endpointBehavior;
	}

	/**
	 * Delete the EndpointBehavior identified by EndpointBehaviorKey
	 * @param endpointBehaviorKey
	 */
	@Override
	@TriggersRemove(cacheName="endpointBehaviorCache", removeAll=true)
	public boolean deleteEndpointBehaviorByKey(@PartialCacheKey String endpointBehaviorKey) {
		return endpointBehaviorRepository.deleteByEndpointBehaviorKey(endpointBehaviorKey) > 0;
	}

	/**
	 * Delete the EndpointBehavior identified by tenantId
	 * @param tenantID
	 * @return
	 */
	@Override
	@TriggersRemove(cacheName="endpointBehaviorCache", removeAll=true)
	public boolean deleteEndpointBehaviorByTenantID(@PartialCacheKey int tenantID) {
		return endpointBehaviorRepository.deleteByTenantTenantID(tenantID) > 0;
	}
}
