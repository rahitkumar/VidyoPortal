package com.vidyo.service.ldap;

import com.vidyo.bo.*;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.framework.context.TenantContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TenantLdapAttributesMappingImpl implements ITenantLdapAttributesMapping {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(TenantLdapAttributesMappingImpl.class.getName());

	private ITenantLdapAttributesMappingDao dao;
	private IMemberDao memberDao;
	private IGroupDao groupDao;
	private IServiceDao serviceDao;

	public void setDao(ITenantLdapAttributesMappingDao dao) {
		this.dao = dao;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping() {
		return getTenantLdapAttributeMapping(TenantContext.getTenantId());
	}
	
	@Override
	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping(int tenantID) {
		List<TenantLdapAttributeMapping> rc = this.dao.getTenantLdapAttributeMapping(tenantID);
		if (rc.size() == 0) {
			saveTenantLdapAttributeMappingRecord(tenantID, "UserName", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "UserType", "", "Normal");
			saveTenantLdapAttributeMappingRecord(tenantID, "DisplayName", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "EmailAddress", "", "yourcompany.com");
			saveTenantLdapAttributeMappingRecord(tenantID, "Extension", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Group", "", "Default");
			saveTenantLdapAttributeMappingRecord(tenantID, "Description", "", "LDAP Provisioned User");
			saveTenantLdapAttributeMappingRecord(tenantID, "Proxy", "", "No Proxy");
			saveTenantLdapAttributeMappingRecord(tenantID, "LocationTag", "", "Default");
			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber1", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber2", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber3", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Thumbnail Photo", "","");
			saveTenantLdapAttributeMappingRecord(tenantID, "Department", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Title", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "IM", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Location", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "User Groups", "","");
			return this.dao.getTenantLdapAttributeMapping(tenantID);
		}else  if(rc.size() == 9){
			//since we added new fields in 3.4.3, for existing tenant with ldap settings should get those fields when 

			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber1", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber2", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "PhoneNumber3", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Thumbnail Photo", "","");
			saveTenantLdapAttributeMappingRecord(tenantID, "Department", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Title", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "IM", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "Location", "", "");
			saveTenantLdapAttributeMappingRecord(tenantID, "User Groups", "","");
			return this.dao.getTenantLdapAttributeMapping(tenantID);
		}else if(rc.size() == 16){
			//this is for upgrade from 3.4.3 to 3.4.4 since we removed Thumbnail Photo from 3.4.3 
			saveTenantLdapAttributeMappingRecord(tenantID, "Thumbnail Photo", "","");
			saveTenantLdapAttributeMappingRecord(tenantID, "User Groups", "","");
			return this.dao.getTenantLdapAttributeMapping(tenantID);
		} else if(rc.size() == 17){
			// Since we added the User Groups fields in 17.2.0 
			saveTenantLdapAttributeMappingRecord(tenantID, "User Groups", "","");
			return this.dao.getTenantLdapAttributeMapping(tenantID);
		} else
			return rc;
		
	}

	public TenantLdapAttributeMapping getTenantLdapAttributeMappingRecord(int tenantID, int mappingID) {
		TenantLdapAttributeMapping rc = this.dao.getTenantLdapAttributeMappingRecord(tenantID, mappingID);
		return rc;
	}

	
	@Deprecated
	@Override
	public int saveTenantLdapAttributeMappingRecord(String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue) {
		int rc = this.dao.saveTenantLdapAttributeMappingRecord(TenantContext.getTenantId(), vidyoAttributeName, ldapAttributeName, defaultAttributeValue);
		return rc;
	}
	
	@Override
	public int saveTenantLdapAttributeMappingRecord(int tenantID, String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue) {
		int rc = this.dao.saveTenantLdapAttributeMappingRecord(tenantID, vidyoAttributeName, ldapAttributeName, defaultAttributeValue);
		return rc;
	}
	
	
	public int updateTenantLdapAttributeMappingRecord(int tenantID, TenantLdapAttributeMapping record) {
		int rc = this.dao.updateTenantLdapAttributeMappingRecord(tenantID, record);
		memberDao.updateAllMembersLastModifiedDateExt(tenantID, null);
		return rc;
	}

	public List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMapping(int tenantID, int mappingID) {
		List<TenantLdapAttributeValueMapping> rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
		return rc;
	}

	public List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMappingForEdit(int tenantID, int mappingID) {
		List<TenantLdapAttributeValueMapping> rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
		TenantLdapAttributeMapping attr = getTenantLdapAttributeMappingRecord(tenantID, mappingID);
		if (attr != null) {
			// User Type
			if (attr.getVidyoAttributeName().equalsIgnoreCase("UserType")) {
				List<MemberRoles> usertypes = this.memberDao.getMemberRoles();
				for (MemberRoles type : usertypes) {
					TenantLdapAttributeValueMapping av = new TenantLdapAttributeValueMapping(mappingID, type.getRoleName());
					if (!rc.contains(av)) {
						saveTenantLdapAttributeValueMappingRecord(tenantID, 0, mappingID, type.getRoleName(), "");
					}
				}
				rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
			}
			// Group
			if (attr.getVidyoAttributeName().equalsIgnoreCase("Group")) {
				List<Group> tenantGroups = this.groupDao.getGroups(tenantID, null);
				for (Group group : tenantGroups) {
					TenantLdapAttributeValueMapping av = new TenantLdapAttributeValueMapping(mappingID, group.getGroupName());
					if (!rc.contains(av)) {
						saveTenantLdapAttributeValueMappingRecord(tenantID, 0, mappingID, group.getGroupName(), "");
					}
				}
				rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
			}
			// Proxy
			if (attr.getVidyoAttributeName().equalsIgnoreCase("Proxy")) {
				List<Proxy> proxies = serviceDao.getProxies(tenantID);
				for (Proxy proxy : proxies) {
					TenantLdapAttributeValueMapping av = new TenantLdapAttributeValueMapping(mappingID, proxy.getProxyName());
					if (!rc.contains(av)) {
						saveTenantLdapAttributeValueMappingRecord(tenantID, 0, mappingID, proxy.getProxyName(), "");
					}
				}
				rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
			}
			// LocationTag
			if (attr.getVidyoAttributeName().equalsIgnoreCase("LocationTag")) {
				List<Location> locationTags = this.memberDao.getLocationTags(tenantID);
				for (Location location : locationTags) {
					TenantLdapAttributeValueMapping av = new TenantLdapAttributeValueMapping(mappingID, location.getLocationTag());
					if (!rc.contains(av)) {
						saveTenantLdapAttributeValueMappingRecord(tenantID, 0, mappingID, location.getLocationTag(), "");
					}
				}
				rc = this.dao.getTenantLdapAttributeValueMapping(tenantID, mappingID);
			}

		}
		return rc;
	}

	public int saveTenantLdapAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String ldapValueName) {
		int rc = this.dao.saveTenantLdapAttributeValueMappingRecord(tenantID, valueID, mappingID, vidyoValueName, ldapValueName);
		return rc;
	}

	public int saveTenantLdapAttributeValueMappingRecord(int tenantID, TenantLdapAttributeValueMapping mapping) {
		int rc = this.dao.saveTenantLdapAttributeValueMappingRecord(tenantID, mapping.getValueID(), mapping.getMappingID(), mapping.getVidyoValueName(), mapping.getLdapValueName());
		//this will cause to update the user during ldap import.
		memberDao.updateAllMembersLastModifiedDateExt(tenantID,null);
		
		return rc;
	}

	public int removeTenantLdapAttributeValueMappingRecord(int tenantID, int valueID) {
		int rc = this.dao.removeTenantLdapAttributeValueMappingRecord(tenantID, valueID);
		return rc;
	}

	/**
	 * @param serviceDao the serviceDao to set
	 */
	public void setServiceDao(IServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}
}