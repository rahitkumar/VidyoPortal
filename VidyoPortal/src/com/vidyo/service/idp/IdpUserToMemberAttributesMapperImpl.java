package com.vidyo.service.idp;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.saml.XSTypeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdpUserToMemberAttributesMapperImpl implements IdpUserToMemberAttributesMapper {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(IdpUserToMemberAttributesMapperImpl.class.getName());

	private IGroupService groupService;
	private ISystemService systemService;
	private IServiceService serviceService;
	private IMemberService memberService;
	private TenantIdpAttributesMapping tenantIdpAttributes;
	private IRoomService roomService;

	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public void setTenantIdpAttributes(TenantIdpAttributesMapping tenantIdpAttributes) {
		this.tenantIdpAttributes = tenantIdpAttributes;
	}
	
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	@Override
	public Member getMemberFromAttributes(int tenantID, List<Attribute> attributes) {
		List<TenantIdpAttributeMapping> attributeMappings = tenantIdpAttributes.getTenantIdpAttributeMapping(tenantID);

		Member member = new Member();
		member.setImportedUsed(1); // this is imported user (from Idp or etc.)
		member.setEnable("on");
		member.setAllowedToParticipateHtml("on");
		member.setTenantPrefix(memberService.getTenantPrefix());
		member.setLangID(10); // Language 10 = Default System Language
		
		member.setRoomTypeID(1); // Personal room
		member.setMemberCreated((int) (new Date().getTime() * .001));
		
		try {
			member.setPassword(PasswordHash.createHash(SecureRandomUtils.generateHashKey(10))); // encode the password
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.warn(e.getMessage());
			// This should not happen. But sets some default password in case of error.
			member.setPassword("HayastanYndmisht");
		}
		
		// by default - will be overwritten if value mapping match portal DB
		member.setGroupID(groupService.getDefaultGroup().getGroupID());
		member.setGroupName(groupService.getDefaultGroup().getGroupName());

		member.setProxyID(0);
		member.setProxyName("No Proxy");

		int defaultLocationtagId = systemService.getTenantDefaultLocationTagID(); 
		member.setLocationID(defaultLocationtagId);
		member.setLocationTag(serviceService.getLocation(defaultLocationtagId).getLocationTag());
		
		member.setDescription("Idp Provisioned User");
		
		Member existingUser = null;
		
		// As there are attributes which depend on UserName, this attribute processes separately
		for (TenantIdpAttributeMapping mapping : attributeMappings) {
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("UserName")) {
				String userName = getIdpAttributeValue(attributes, mapping);
				
				int i = 0;
				while((i < userName.length()) && !CharUtils.isAsciiAlphanumeric(userName.charAt(i))) {
					i++;
				}
				if(i > 0) {
					userName = userName.substring(i);
				}
				
				member.setUsername(userName);
				
				if(memberService.isMemberExistForUserName(userName, 0)) {
					existingUser = memberService.getMemberByName(userName);
				}
				break;
			}
		}

		for (TenantIdpAttributeMapping mapping : attributeMappings) {
			// The usertype being passed in is used to assign the member role.
			// Super and Legacy roles cannot be assigned via this workflow.
			// The setMemberRoleOfMember method, fetches the user role from the
			// database and assigns it to the user. If the user role doesn't
			// exist, the user is assigned the default user role - Normal
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("UserType")) {
				String userType = getIdpAttributeValue(attributes, mapping);
				if (userType != null) {					
					if(!memberService.setMemberRoleOfMember(member, userType)) {
						memberService.setMemberRoleOfMember(member, mapping.getDefaultAttributeValue());
					}
				} else {
					memberService.setMemberRoleOfMember(member, mapping.getDefaultAttributeValue());
				}
			}
			
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("DisplayName")) {
				String memberName = getIdpAttributeValue(attributes, mapping);
				if (memberName != null && !memberName.equalsIgnoreCase("")) {
					member.setMemberName(memberName);
				} else {
					member.setMemberName(member.getUsername());
				}
			}

			if (mapping.getVidyoAttributeName().equalsIgnoreCase("EmailAddress")) {
				String emailAddress = this.getIdpAttributeValue(attributes, mapping);
				if (emailAddress != null && !emailAddress.equalsIgnoreCase("")) {					
					if (!emailAddress.matches(ValidationUtils.EMAIL_REGEX)) {
						if (member.getUsername().contains("@")) {
							// in case username is email address - use it
							emailAddress = member.getUsername();
						} else {
							emailAddress = member.getUsername() + "@" + mapping.getDefaultAttributeValue();
						}
					}
					member.setEmailAddress(emailAddress);
				}
			}
			
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Extension")) {
				String extNum = getIdpAttributeValue(attributes, mapping);

				if (extNum != null) {
					extNum = extNum.replaceAll("\\D", "");
				}
				if (extNum == null || extNum.equalsIgnoreCase("") || extNum.equalsIgnoreCase(member.getTenantPrefix())
						|| !extNum.matches("^[0-9]+$") || (extNum.length() > 16)) { // will add tenant prefix
					if (existingUser != null) {
						logger.warn("extension num from IDP doesnt conform to the supported extension format ->" + extNum);
						member.setRoomExtNumber(existingUser.getRoomExtNumber());
					} else {
						extNum = this.roomService.generateRoomExt(member.getTenantPrefix());
						logger.warn("generating a new extension number for the user ->" + extNum);
						member.setRoomExtNumber(extNum);
					}
				} else {
					logger.warn("updating the member object with extension num from IDP ->" + extNum);
					member.setRoomExtNumber(member.getTenantPrefix() + extNum);
				}
			}

			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Group")) {
				String group = getIdpAttributeValue(attributes, mapping);
				if (group != null) {					
					if(!memberService.setGroupOfMember(member, group)) {
						memberService.setGroupOfMember(member, mapping.getDefaultAttributeValue());
					}
				} else {
					memberService.setGroupOfMember(member, mapping.getDefaultAttributeValue());
				}
			}
			
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Description")) {
				String description = getIdpAttributeValue(attributes, mapping);
				if (description != null) {
					member.setDescription(description);
				}
			}
			
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Proxy")) {
				String proxy = getIdpAttributeValue(attributes, mapping);
				if (proxy != null && !proxy.equalsIgnoreCase("No Proxy")) {
					if(!memberService.setProxyOfMember(member, proxy)) {
						memberService.setProxyOfMember(member, mapping.getDefaultAttributeValue());
					}
				} else if(proxy == null) {
					memberService.setProxyOfMember(member, mapping.getDefaultAttributeValue());
				}
			}
			
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("LocationTag")) {
				String locationTag = getIdpAttributeValue(attributes, mapping);
				if (locationTag != null) {
					if(!memberService.setTenantLocationTagOfMember(member, locationTag)) {
						memberService.setTenantLocationTagOfMember(member, mapping.getDefaultAttributeValue());
					}
				} else {
					memberService.setTenantLocationTagOfMember(member, mapping.getDefaultAttributeValue());
				}
			}

			if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber1")) {
				String ph1 = this.getIdpAttributeValue(attributes, mapping);
				if (ph1 != null) {
					member.setPhone1(ph1);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber2")) {
				String ph2 = this.getIdpAttributeValue(attributes, mapping);
				if (ph2 != null) {
					member.setPhone2(ph2);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("PhoneNumber3")) {
				String ph3 = this.getIdpAttributeValue(attributes, mapping);
				if (ph3 != null) {
					member.setPhone3(ph3);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Department")) {
				String department = this.getIdpAttributeValue(attributes, mapping);
				if (department != null) {
					member.setDepartment(department);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Title")) {
				String title = this.getIdpAttributeValue(attributes, mapping);
				if (title != null) {
					member.setTitle(title);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("IM")) {
				String im = this.getIdpAttributeValue(attributes, mapping);
				if (im != null) {
					member.setInstantMessagerID(im);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Location")) {
				String location = this.getIdpAttributeValue(attributes, mapping);
				if (location != null) {
					member.setLocation(location);
				}
			}
			if (mapping.getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo")) {
				
				String thumbnailImageBS64 = this.getIdpAttributeValue(attributes, mapping);
				if (thumbnailImageBS64 != null) {
					try{
						
						byte[]thumbNailImage=Base64.getDecoder().decode(thumbnailImageBS64);
						member.setThumbNailImage(thumbNailImage);
					}catch(Exception e){
						logger.error("Cant decode the thumbnail value  "+thumbnailImageBS64 ,e);
					}
					
				}else{
					logger.error("No image value found.Enable the debug mode and see the values coming from saml");
				}
			}

			if (mapping.getVidyoAttributeName().equalsIgnoreCase("User Groups")
					&& StringUtils.isNotBlank(mapping.getIdpAttributeName())) {
				Attribute userGroupsAttribute = null;
				for (Attribute attribute : attributes) {
					if (attribute.getName().equalsIgnoreCase(mapping.getIdpAttributeName())) {
						userGroupsAttribute = attribute;
						break;
					}
				}

				Set<String> userGroups = new HashSet<String>();
				if (userGroupsAttribute != null
						&& CollectionUtils.isNotEmpty(userGroupsAttribute.getAttributeValues())) {
					Configuration configuration = systemService.getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
					int maxGroupsToBeImported = (configuration != null)
							? Integer.valueOf(configuration.getConfigurationValue()).intValue() : 100;
					for (XMLObject attributeValue : userGroupsAttribute.getAttributeValues()) {
						userGroups.add(XSTypeUtils.getValue(attributeValue));
						maxGroupsToBeImported--;

						if (maxGroupsToBeImported == 0) {
							break;
						}
					}
				}
				if (CollectionUtils.isNotEmpty(userGroups)) {
					member.setUserGroupsFromAuthProvider(userGroups);
				}
				member.setUserGroupsUpdated(true);
			}
		}

		return member;
	}

	@Override
	public String getIdpAttributeValue(List<Attribute> attributes, TenantIdpAttributeMapping attributeMapping) {
		String rc = attributeMapping.getDefaultAttributeValue();
		Attribute attr = null;
		for(Attribute attribute : attributes) {
			if(attribute.getName().equalsIgnoreCase(attributeMapping.getIdpAttributeName())) {
				attr = attribute;
				break;
			}
		}
		
		if (attr != null) {
			String attrVal = null;
			if(attr.getAttributeValues().size() > 0) {
//				attrVal = ((XSString)attr.getAttributeValues().get(0)).getValue();
				attrVal = XSTypeUtils.getValue(attr.getAttributeValues().get(0));
			}
			if (attrVal != null) {
				boolean isValueMappingDone = false;
				// check attrValue mapping
				List<TenantIdpAttributeValueMapping> values = tenantIdpAttributes.
						getTenantIdpAttributeValueMapping(attributeMapping.getTenantID(), attributeMapping.getMappingID());
				for (TenantIdpAttributeValueMapping v: values) {
					if (!v.getIdpValueName().isEmpty() && v.getIdpValueName().equalsIgnoreCase(attrVal)) {
						rc = v.getVidyoValueName();
						isValueMappingDone = true;
						break;
					}
				}
				if (!isValueMappingDone && !attrVal.equalsIgnoreCase("")) {
					rc = attrVal;
				}
			}
		}
		return rc;
	}
}