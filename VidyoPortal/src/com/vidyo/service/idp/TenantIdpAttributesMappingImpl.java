package com.vidyo.service.idp;

import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.framework.context.TenantContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TenantIdpAttributesMappingImpl implements TenantIdpAttributesMapping {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(TenantIdpAttributesMappingImpl.class.getName());

	private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
	private IGroupDao groupDao;
	private IMemberDao memberDao;
	private IServiceDao serviceDao;

	public void setTenantIdpAttributesMappingDao(ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao) {
		this.tenantIdpAttributesMappingDao = tenantIdpAttributesMappingDao;
	}
	
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Deprecated
	@Override
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping() {
		return getTenantIdpAttributeMapping(TenantContext.getTenantId());
	}
	
	@Override
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping(int tenantID) {
		List<TenantIdpAttributeMapping> rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeMapping(tenantID);
		if (rc.size() == 0) {
			saveTenantIdpAttributeMappingRecord(tenantID, "UserName", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "UserType", "", "Normal");
			saveTenantIdpAttributeMappingRecord(tenantID, "DisplayName", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "EmailAddress", "", "yourcompany.com");
			saveTenantIdpAttributeMappingRecord(tenantID, "Extension", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Group", "", "Default");
			saveTenantIdpAttributeMappingRecord(tenantID, "Description", "", "Idp Provisioned User");
			saveTenantIdpAttributeMappingRecord(tenantID, "Proxy", "", "No Proxy");
			saveTenantIdpAttributeMappingRecord(tenantID, "LocationTag", "", "Default");
			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber1", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber2", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber3", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Thumbnail Photo", "","");
			saveTenantIdpAttributeMappingRecord(tenantID, "Department", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Title", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "IM", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Location", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "User Groups", "", "");
			return tenantIdpAttributesMappingDao.getTenantIdpAttributeMapping(tenantID);
		}else  if(rc.size() == 9){
			//since we added new fields in 3.4.3, for existing tenant with ldap settings should get those fields when 

			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber1", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber2", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "PhoneNumber3", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Thumbnail Photo", "","");
			saveTenantIdpAttributeMappingRecord(tenantID, "Department", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Title", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "IM", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "Location", "", "");
			saveTenantIdpAttributeMappingRecord(tenantID, "User Groups", "", "");
			return tenantIdpAttributesMappingDao.getTenantIdpAttributeMapping(tenantID);
		} else  if(rc.size() == 17) {
			// Since we added the User Groups fields in 17.2.0 
			saveTenantIdpAttributeMappingRecord(tenantID, "User Groups", "", "");
			return tenantIdpAttributesMappingDao.getTenantIdpAttributeMapping(tenantID);
		} else
			return rc;
	}

	@Override
	public TenantIdpAttributeMapping getTenantIdpAttributeMappingRecord(int tenantID, int mappingID) {
		TenantIdpAttributeMapping rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeMappingRecord(tenantID, mappingID);
		return rc;
	}

	@Override
	public int saveTenantIdpAttributeMappingRecord(int tenantID, String vidyoAttributeName, String idpAttributeName, String defaultAttributeValue) {
		int rc = tenantIdpAttributesMappingDao.saveTenantIdpAttributeMappingRecord(tenantID, vidyoAttributeName, idpAttributeName, defaultAttributeValue);
		return rc;
	}
	
	@Override
	public int updateTenantIdpAttributeMappingRecord(int tenantId, TenantIdpAttributeMapping record) {
		int rc = tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingRecord(tenantId, record);
		return rc;
	}
	
	@Override
	public List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMapping(int tenantID, int mappingID) {
		List<TenantIdpAttributeValueMapping> rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
		return rc;
	}
	
	@Override
	public List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMappingForEdit(int tenantID, int mappingID) {
		List<TenantIdpAttributeValueMapping> rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
		TenantIdpAttributeMapping attr = getTenantIdpAttributeMappingRecord(tenantID, mappingID);
		if (attr != null) {
			// User Type
			if (attr.getVidyoAttributeName().equalsIgnoreCase("UserType")) {
				List<MemberRoles> usertypes = memberDao.getSamlMemberRoles();
				for (MemberRoles type : usertypes) {
					TenantIdpAttributeValueMapping av = new TenantIdpAttributeValueMapping(mappingID, type.getRoleName());
					if (!rc.contains(av)) {
						saveTenantIdpAttributeValueMappingRecord(tenantID, 0, mappingID, type.getRoleName(), "");
					}
				}
				rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
			}
			// Group
			if (attr.getVidyoAttributeName().equalsIgnoreCase("Group")) {
				List<Group> tenantGroups = groupDao.getGroups(tenantID, null);
				for (Group group : tenantGroups) {
					TenantIdpAttributeValueMapping av = new TenantIdpAttributeValueMapping(mappingID, group.getGroupName());
					if (!rc.contains(av)) {
						saveTenantIdpAttributeValueMappingRecord(tenantID, 0, mappingID, group.getGroupName(), "");
					}
				}
				rc = tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
			}
			// Proxy
			if (attr.getVidyoAttributeName().equalsIgnoreCase("Proxy")) {
				List<Proxy> proxies = serviceDao.getProxies(tenantID);
				for (Proxy proxy : proxies) {
					TenantIdpAttributeValueMapping av = new TenantIdpAttributeValueMapping(mappingID, proxy.getProxyName());
					if (!rc.contains(av)) {
						saveTenantIdpAttributeValueMappingRecord(tenantID, 0, mappingID, proxy.getProxyName(), "");
					}
				}
				rc = this.tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
			}
			// LocationTag
			if (attr.getVidyoAttributeName().equalsIgnoreCase("LocationTag")) {
				List<Location> locationTags = this.memberDao.getLocationTags(tenantID);
				for (Location location : locationTags) {
					TenantIdpAttributeValueMapping av = new TenantIdpAttributeValueMapping(mappingID, location.getLocationTag());
					if (!rc.contains(av)) {
						saveTenantIdpAttributeValueMappingRecord(tenantID, 0, mappingID, location.getLocationTag(), "");
					}
				}
				rc = this.tenantIdpAttributesMappingDao.getTenantIdpAttributeValueMapping(tenantID, mappingID);
			}
		}
		return rc;
	}
	
	@Override
	public int saveTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName) {
		int rc = tenantIdpAttributesMappingDao.saveTenantIdpAttributeValueMappingRecord(tenantID, valueID, mappingID, vidyoValueName, idpValueName);
		return rc;
	}
	@Override
	public int updateTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName) {
		int rc = tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingRecord(tenantID, valueID, mappingID, vidyoValueName, idpValueName);
		return rc;
	}
	
	@Override
	public int saveTenantIdpAttributeValueMappingRecord(int tenantID, TenantIdpAttributeValueMapping mapping) {
		int rc = tenantIdpAttributesMappingDao.saveTenantIdpAttributeValueMappingRecord(tenantID, mapping.getValueID(), mapping.getMappingID(), 
				mapping.getVidyoValueName(), mapping.getIdpValueName());
		return rc;
	}

	@Override
	public int removeTenantIdpAttributeValueMappingRecord(int tenantID, int valueID) {
		int rc = tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecord(tenantID, valueID);
		return rc;
	}

	/**
	 * @param serviceDao the serviceDao to set
	 */
	public void setServiceDao(IServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}
}