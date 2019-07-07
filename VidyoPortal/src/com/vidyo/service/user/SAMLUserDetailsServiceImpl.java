package com.vidyo.service.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.Member;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.db.IMemberDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.UserDetailsService;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.idp.IdpUserToMemberAttributesMapper;
import com.vidyo.service.idp.TenantIdpAttributesMapping;
import com.vidyo.service.ldap.LdapService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.service.usergroup.UserGroupService;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.utils.saml.XSTypeUtils;

public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

	protected static final Logger logger = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class.getName());
	
	@Autowired LdapService ldapService;
	
	private UserDetailsService userDetailsService;
	
	private ITenantService tenantService;
	
	private MemberService memberService1;
	
	private ReloadableResourceBundleMessageSource ms;
	
	private IdpUserToMemberAttributesMapper idpUserToMemberAttributesMapper;
	
	private TenantIdpAttributesMapping tenantIdpAttributes;
	
	private IRoomService roomService;
	
	private SamlAuthenticationService samlAuthenticationService;
	
	private IMemberDao memberDao;

	@Autowired
	private UserGroupService userGroupService;

	/**
	 * @param memberDao the memberDao to set
	 */
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	//loc is a non IoC field
	private Locale loc = Locale.getDefault();
	
	private static final String UPDATE_PASSWORD_SCHEME = "update Member set password = ? where memberId = ?";
	
	/**
	 * JdbcTemplate is injected directly so that none of the
	 * other classed will have access to update password scheme
	 */
	private JdbcTemplate jdbcTemplate;	
	
	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}
	
	public void setMemberService1(MemberService memberService1) {
		this.memberService1 = memberService1;
	}
	
	public void setMs(ReloadableResourceBundleMessageSource msgSrc){
		this.ms = msgSrc;
	}

	public void setIdpUserToMemberAttributesMapper(
			IdpUserToMemberAttributesMapper idpUserToMemberAttributesMapper) {
		this.idpUserToMemberAttributesMapper = idpUserToMemberAttributesMapper;
	}

	public void setTenantIdpAttributes(TenantIdpAttributesMapping tenantIdpAttributes) {
		this.tenantIdpAttributes = tenantIdpAttributes;
	}
	
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}
	
	public void setSamlAuthenticationService(SamlAuthenticationService samlAuthenticationService) {
		this.samlAuthenticationService = samlAuthenticationService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	@Override
	public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
		
		logCredentialAttributes(credential.getAttributes());
		logAuthenticationAssertion(credential.getAuthenticationAssertion());
		UserDetails userDetails = null;
		TenantIdpAttributeMapping attributeMapping = null;
		Integer tenantID = TenantContext.getTenantId();
		SamlAuthentication samlAuth =  samlAuthenticationService.getSamlAuthenticationForTenant(tenantID);		
		if(samlAuth.getAuthProvisionType() == SamlProvisionType.LOCAL) {
			String idpAttributeForUsername = samlAuth.getIdpAttributeForUsername();
			if(idpAttributeForUsername != null && !idpAttributeForUsername.isEmpty()) {
				Attribute attr = null;
				for(Attribute attribute : credential.getAttributes()) {
					if(attribute.getName().equalsIgnoreCase(idpAttributeForUsername)) {
						attr = attribute;
						break;
					}
				}
				
				if (attr != null) {
					String attrVal = null;
					if(attr.getAttributeValues().size() > 0) {
						attrVal = XSTypeUtils.getValue(attr.getAttributeValues().get(0));
					}
					
					if(attrVal == null || attrVal.isEmpty()) {
						throw new UsernameNotFoundException(ms.getMessage("idp.attribute.mapping.not.found.for.username", null, "", loc));
					}
					
					try {
						userDetails = userDetailsService.loadUserByUsername(attrVal, tenantID);
					} catch(Exception e){
						throw new UsernameNotFoundException(ms.getMessage("account.is.not.provisioned", null, "", loc));
					}
					
					if(userDetails == null) {
						throw new UsernameNotFoundException(ms.getMessage("account.is.not.provisioned", null, "", loc));
					}
				}
			} else {
				throw new UsernameNotFoundException(ms.getMessage("idp.attribute.mapping.not.found.for.username", null, "", loc));
			}
		} else {
			List<TenantIdpAttributeMapping> tenantIdpAttributeMappings = tenantIdpAttributes.getTenantIdpAttributeMapping(tenantID);
			for(TenantIdpAttributeMapping tenantIdpAttributeMapping : tenantIdpAttributeMappings) {
				if(tenantIdpAttributeMapping.getVidyoAttributeName().equalsIgnoreCase("UserName")) {
					attributeMapping = tenantIdpAttributeMapping;
					break;
				}
			}
			
			if(attributeMapping == null) {
				throw new UsernameNotFoundException(ms.getMessage("idp.attribute.mapping.not.found.for.username", null, "", loc));
			}
			
			String userName = idpUserToMemberAttributesMapper.getIdpAttributeValue(credential.getAttributes(), attributeMapping);
			try {
				userDetails = userDetailsService.loadUserByUsername(userName, tenantID);
			} catch(Exception e){
				// It is OK. Member is not found. The new one will be created.
			}
			
			Member memberObj = idpUserToMemberAttributesMapper.getMemberFromAttributes(tenantID, credential.getAttributes());
			if (userDetails == null) {
				Tenant tenantObj = tenantService.getTenant(tenantID);
				
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				MemberManagementResponse memberResponse = memberService1.addMember(tenantID, memberObj, 
						request.getScheme(), tenantObj.getTenantURL());
				
				if(memberResponse.getStatus() != MemberManagementResponse.SUCCESS &&
						memberResponse.getStatus() != MemberManagementResponse.USER_CREATED_BUT_EMAIL_SEND_FAILED) {
					String errMsg;
					switch (memberResponse.getStatus()) {
					case MemberManagementResponse.DUPLICATE_USER_NAME:
						errMsg = MessageFormat.format(ms.getMessage(memberResponse.getMessage(), null, "", loc), memberObj.getUsername());
						break;
					case MemberManagementResponse.DUPLICATE_ROOM_NUMBER:
						errMsg = MessageFormat.format(ms.getMessage(memberResponse.getMessage(), null, "", loc), memberObj.getRoomExtNumber());
						break;
					case MemberManagementResponse.EXTN_MATCHES_SCHEDULEROOM_PREFIX:
						errMsg = MessageFormat.format(ms.getMessage(memberResponse.getMessage(), null, "", loc), memberObj.getRoomExtNumber());
						break;
					case MemberManagementResponse.INVALID_USERNAME_MATCH:
						errMsg = MessageFormat.format(ms.getMessage(memberResponse.getMessage(), null, "", loc), memberObj.getUsername());
						break;
					case MemberManagementResponse.INVALID_EMAIL_MATCH:
						errMsg = MessageFormat.format(ms.getMessage(memberResponse.getMessage(), null, "", loc), memberObj.getEmailAddress());
						break;
					default:
						errMsg = ms.getMessage(memberResponse.getMessage(), null, "", loc);
						break;
					}
					throw new UsernameNotFoundException(errMsg);
				}
			} else if(((VidyoUserDetails)userDetails).isImportedUser()){
				Member existingMember = memberService1.getMemberByName(userName, tenantID);
				
				if (!existingMember.getMemberName().equalsIgnoreCase(memberObj.getMemberName())
						|| !StringUtils.equalsIgnoreCase(existingMember.getEmailAddress(), memberObj.getEmailAddress())
						|| existingMember.getGroupID() != memberObj.getGroupID()
						|| !StringUtils.equalsIgnoreCase(existingMember.getDescription(), memberObj.getDescription())
						|| existingMember.getProxyID() != memberObj.getProxyID()
						|| !StringUtils.equalsIgnoreCase(existingMember.getRoomExtNumber(), memberObj.getRoomExtNumber())
						|| existingMember.getLocationID() != memberObj.getLocationID()
						|| existingMember.getRoleID() != memberObj.getRoleID()
						|| !StringUtils.equalsIgnoreCase(existingMember.getPhone1(), memberObj.getPhone1())
						|| !StringUtils.equalsIgnoreCase(existingMember.getPhone2(), memberObj.getPhone2())
						|| !StringUtils.equalsIgnoreCase(existingMember.getPhone3(), memberObj.getPhone3())
						|| !StringUtils.equalsIgnoreCase(existingMember.getDepartment(), memberObj.getDepartment())
						|| !StringUtils.equalsIgnoreCase(existingMember.getTitle(), memberObj.getTitle())
						|| !StringUtils.equalsIgnoreCase(existingMember.getInstantMessagerID(), memberObj.getInstantMessagerID())
						|| !StringUtils.equalsIgnoreCase(existingMember.getLocation(), memberObj.getLocation())
						)
					{
						if (this.roomService.isRoomExistForRoomExtNumber(memberObj.getRoomExtNumber(), existingMember.getRoomID())) {
							logger.warn("Extension conflict. Extension will not be updated.");
							memberObj.setRoomExtNumber(existingMember.getRoomExtNumber());
						}
						try {
							memberService1.updateMember(tenantID, existingMember.getMemberID(), memberObj);
						} catch (AccessRestrictedException e) {
							throw new UsernameNotFoundException(ms.getMessage("invalid.member", null, "", loc));
						}
					}
			}
			
			userDetails = userDetailsService.loadUserByUsername(memberObj.getUsername(), tenantID);
			if (userDetails.getPassword() != null && !userDetails.getPassword().contains(":")) {
				try {
					String newPassword = PasswordHash.createHash(PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)));
					int updateCount = jdbcTemplate.update(UPDATE_PASSWORD_SCHEME, new Object[] {newPassword, ((VidyoUserDetails)userDetails).getMemberId()});
					memberDao.updateMember(TenantContext.getTenantId(), userDetails.getUsername() != null ? userDetails
							.getUsername().toLowerCase(Locale.ENGLISH) : null);						
					logger.error("Update Count for Password Scheme Change {}", updateCount);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					logger.error("Updating to new password scheme failed ", e);
				}			
			}
			
			if (memberObj.getThumbNailImage() != null && memberObj.getThumbNailImage().length > 0) {
				ldapService.writeImagetoFileSystemAsync(String.valueOf(((VidyoUserDetails)userDetails).getMemberId()),
						TenantContext.getTenantId(), memberObj);
				logger.debug(memberObj.getUsername()
						+ "-member ,image is available to write a file. THe size of the image is "
						+ memberObj.getThumbNailImage().length + "background process is being called");
			}

			// Manage user groups
			// 1. Create groups if not already present
			// 2. Assign the groups to the member if not already done
			if (memberObj.isUserGroupsUpdated()) {
				Member member = memberService1.getMemberByName(userName, tenantID);
				if (member != null) {
					userGroupService.setGroupsForMember(member.getMemberID(),
							memberObj.getUserGroupsFromAuthProvider());
				}
			}
		}

		return userDetails;
	}

	private void logCredentialAttributes(List<Attribute> attributes) {
		if(logger.isDebugEnabled()) {
			logger.debug("List of assertion attributes ================");
			StringBuffer attrValue = new StringBuffer();
			for(Attribute attribute : attributes) {
				logger.debug("Attribute name = " + attribute.getName());
				logger.debug("Attribute name format = " + attribute.getNameFormat());
				logger.debug("Attribute friendly name = " + attribute.getFriendlyName());
				attrValue.delete(0, attrValue.length());
				for(XMLObject val : attribute.getAttributeValues()) {
//					attrValue.append(((XSString)val).getValue() + " ");
					attrValue.append(XSTypeUtils.getValue(val) + " ");
				}
				logger.debug("Attribute values = " + attrValue);
			}
			
			logger.debug("End of list of assertion attributes ================");
		}
	}
	
	private void logAuthenticationAssertion(Assertion assertion) {
		if(logger.isDebugEnabled()) {
			logger.debug("Assertion ================");

			logger.debug("Authn statements");
			for(AuthnStatement authnStatement : assertion.getAuthnStatements()) {
				if(authnStatement.getAuthnContext() != null && authnStatement.getAuthnContext().getAuthContextDecl() != null) {
					logger.debug("Auth Context Declaration" + authnStatement.getAuthnContext().getAuthContextDecl().getTextContent());
				}
				if(authnStatement.getAuthnContext() != null && authnStatement.getAuthnContext().getAuthnContextDeclRef() != null) {
					logger.debug("Auth Context Declaration Reference" + authnStatement.getAuthnContext().getAuthnContextDeclRef().getAuthnContextDeclRef());
				}
				logger.debug("The authentication took place at " + authnStatement.getAuthnInstant());
				logger.debug("The session between the principal and the SAML authority ends at " + authnStatement.getSessionNotOnOrAfter());
				logger.debug("Session index: " + authnStatement.getSessionIndex() + "\n");
			}
			logger.debug("End of Authn statements");
			
			for(AuthzDecisionStatement authzDecisionStatement : assertion.getAuthzDecisionStatements()) {
				logger.debug("Decision of the authorization request : " + authzDecisionStatement.getDecision());
			}
			
			if(assertion.getConditions() != null) {
				logger.debug("Conditions");
				logger.debug("The date/time before which the assertion is invalid : " + assertion.getConditions().getNotBefore());
				logger.debug("The date/time on, or after, which the assertion is invalid : " + assertion.getConditions().getNotOnOrAfter());
				logger.debug("End of conditions");
			}
			
			logger.debug("The issue instance of this assertion " + assertion.getIssueInstant());
			if(assertion.getIssuer() != null) {
				logger.debug("Issuer : " + assertion.getIssuer().getValue());
			}
			logger.debug("The ID of assertion : " + assertion.getID());
			logger.debug("SAML version : " + assertion.getVersion().getMajorVersion() + "." + assertion.getVersion().getMinorVersion());
			
			
			logger.debug("End of Assertion ================");
		}
	}

}
