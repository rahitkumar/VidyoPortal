/**
 * 
 */
package com.vidyo.service.endpointbehavior;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vidyo.bo.EndpointBehavior;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.EndpointBehaviorDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.utils.SecureRandomUtils;

/**
 * This class will be responsible to handle all request for EndpointBehavior.
 * @author ysakurikar
 *
 */

@Service ("endpointBehaviorService")
public class EndpointBehaviorServiceImpl implements EndpointBehaviorService {
	
	@Autowired
	private EndpointBehaviorDao endpointBehaviorDao;
	
	@Autowired
	private TenantConfigurationDao tenantConfigDao;
	
	protected static final Logger logger = LoggerFactory.getLogger(EndpointBehaviorServiceImpl.class);
	
	/**
	 * Fetch the EndpointBehavior using endpointBehaviorKey. 
	 * @param endpointBehaviorKey
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public EndpointBehavior getEndpointBehaviorByKey(String endpointBehaviorKey) throws ServiceException {
		logger.debug("fetching EndpointBehavior for Key="+endpointBehaviorKey);
		EndpointBehavior endpointBehavior = endpointBehaviorDao.getEndpointBehaviorByKey(endpointBehaviorKey);
		return endpointBehavior;
	}
	
	/**
	 * Get the EndpointBehavior by tenant
	 * @param tenantId
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<EndpointBehavior> getEndpointBehaviorByTenant(int tenantID) throws ServiceException {
		logger.debug("fetching EndpointBehavior for tenantId="+tenantID);
		return endpointBehaviorDao.getEndpointBehaviorByTenant(tenantID);
	}
	
	/**
	 * Create/Update the EndpointBehavior in the database. 
	 * @param endpointBehavior
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	@Override
	public EndpointBehavior saveEndpointBehavior (EndpointBehavior endpointBehavior) throws ServiceException {
		logger.debug("Saving EndpointBehavior");
		endpointBehavior = endpointBehaviorDao.saveEndpointBehavior(endpointBehavior);
		
		return endpointBehavior;
	}
	
	/**
	 * Delete the EndpointBehavior identified by EndpointBehaviorKey
	 * @param endpointBehaviorKey
	 * @throws ServiceException
	 */
	@Transactional
	@Override
	public boolean deleteEndpointBehavior(String endpointBehaviorKey) throws ServiceException {
		logger.debug("deleting EndpointBehavior");
		
		return endpointBehaviorDao.deleteEndpointBehaviorByKey(endpointBehaviorKey);
	}
	
	/**
	 * Delete the EndpointBehavior identified by tenantId
	 * @param tenantID
	 * @return
	 */
	@Transactional
	@Override
	public boolean deleteEndpointBehaviorByTenantID(int tenantID) {
		return endpointBehaviorDao.deleteEndpointBehaviorByTenantID(tenantID);
	}
	
	/**
	 * To generate a unique EndpointBehavior Key
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public String generateEndpointBehaviorKey() throws ServiceException {
		return SecureRandomUtils.generateRoomKey(64);
	}
	
	/**
	 * Check if the EndpointBehavior is enabled for the Tenant
	 * @param tenantId
	 * @return
	 */
	@Override
	public boolean isEndpointBehaviorForTenant(int tenantId){
		TenantConfiguration tenantConfiguration = tenantConfigDao.getTenantConfiguration(tenantId);
		return (tenantConfiguration.getEndpointBehavior() == 1 ? true:false);
	}
	
	/**
	 * Create the copy of the endpointBehaviorDataType into the corresponding endpointBehavior object and return it.
	 * @param endpointBehaviorDataType
	 * @return
	 */
	@Override
    public EndpointBehavior makeEndpointBehaviorFromDataType(Object endpointBehaviorDataType, EndpointBehavior endpointBehavior) {
	   	 BeanUtils.copyProperties(endpointBehaviorDataType, endpointBehavior);
	   	 return endpointBehavior;
    }
    
	/**
	 * Create the copy of the endpointBehavior into the corresponding endpointBehaviorDataType object and return it.
	 * @param endpointBehavior
	 * @param endpointBehaviorDataType
	 * @return
	 */
    @Override
    public Object makeEndpointBehavioDataType(EndpointBehavior endpointBehavior, Object endpointBehaviorDataType) {
	   	BeanUtils.copyProperties(endpointBehavior, endpointBehaviorDataType);
		return endpointBehaviorDataType;
   }
    
	
}
