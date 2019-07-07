package com.vidyo.service.ldap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Group;
import com.vidyo.bo.Member;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;

public class LdapUserToMemberAttributesMapperImplTest {

	@InjectMocks
	private LdapUserToMemberAttributesMapperImpl ldapUserToMemberAttributesMapperImpl;

	@Mock
	private ITenantLdapAttributesMapping tenantLdapAttributesMapping;

	@Mock
	private IMemberService memberService;

	@Mock
	private IGroupService groupService;

	@Mock
	private ISystemService systemService;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);

		TenantContext.setTenantId(1);

		when(memberService.getTenantPrefix()).thenReturn("121");

		Group group = new Group();
		group.setGroupID(1);
		group.setGroupName("Default");
		when(groupService.getDefaultGroup()).thenReturn(group);

		when(systemService.getTenantDefaultLocationTagID()).thenReturn(1);

		Configuration config = new Configuration();
		config.setConfigurationName("MAX_USER_GROUPS_IMPORTED_PER_USER");
		config.setConfigurationValue("100");
		when(systemService.getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER")).thenReturn(config);
	}

	@AfterMethod
	private void destroy() {
		verify(memberService, times(1)).getTenantPrefix();
		verify(groupService, times(2)).getDefaultGroup();
		verify(systemService, times(1)).getTenantDefaultLocationTagID();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMapFromAttributesWith3Groups() throws NamingException {
		NamingEnumeration attributeValues = mock(NamingEnumeration.class);
		when(attributeValues.hasMore()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(attributeValues.next()).thenReturn("cn=portalusers,ou=groups,dc=localhost")
				.thenReturn("cn=blrusers,ou=groups,dc=localhost").thenReturn("cn=allusers,ou=groups,dc=localhost");

		Attribute userGroupAttribute = mock(Attribute.class);
		when(userGroupAttribute.getAll()).thenReturn(attributeValues);

		Attributes attributes = mock(Attributes.class);
		when(attributes.get("memberOf")).thenReturn(userGroupAttribute);

		TenantLdapAttributeMapping userGroupMapping = new TenantLdapAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setLdapAttributeName("memberOf");
		userGroupMapping.setTenantID(1);
		List<TenantLdapAttributeMapping> attributeMappings = new ArrayList<>();
		attributeMappings.add(userGroupMapping);
		when(tenantLdapAttributesMapping.getTenantLdapAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = ldapUserToMemberAttributesMapperImpl.mapFromAttributes(attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().size() == 3);
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("portalusers"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("blrusers"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("allusers"));

		verify(tenantLdapAttributesMapping, times(1)).getTenantLdapAttributeMapping(1);
		verify(userGroupAttribute, times(1)).getAll();
		verify(attributeValues, times(4)).hasMore();
		verify(attributeValues, times(3)).next();
		verify(systemService, times(1)).getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMapFromAttributesWithNoGroups() throws NamingException {
		NamingEnumeration attributeValues = mock(NamingEnumeration.class);
		when(attributeValues.hasMore()).thenReturn(false);

		Attribute userGroupAttribute = mock(Attribute.class);
		when(userGroupAttribute.getAll()).thenReturn(attributeValues);

		Attributes attributes = mock(Attributes.class);
		when(attributes.get("memberOf")).thenReturn(userGroupAttribute);

		TenantLdapAttributeMapping userGroupMapping = new TenantLdapAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setLdapAttributeName("memberOf");
		userGroupMapping.setTenantID(1);
		List<TenantLdapAttributeMapping> attributeMappings = new ArrayList<>();
		attributeMappings.add(userGroupMapping);
		when(tenantLdapAttributesMapping.getTenantLdapAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = ldapUserToMemberAttributesMapperImpl.mapFromAttributes(attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantLdapAttributesMapping, times(1)).getTenantLdapAttributeMapping(1);
		verify(userGroupAttribute, times(1)).getAll();
		verify(attributeValues, times(1)).hasMore();
		verify(systemService, times(1)).getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMapFromAttributesWithGroupsMoreThanLimit() throws NamingException {
		NamingEnumeration attributeValues = mock(NamingEnumeration.class);
		when(attributeValues.hasMore()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(attributeValues.next()).thenReturn("cn=portalusers,ou=groups,dc=localhost")
				.thenReturn("cn=blrusers,ou=groups,dc=localhost").thenReturn("cn=allusers,ou=groups,dc=localhost");

		Attribute userGroupAttribute = mock(Attribute.class);
		when(userGroupAttribute.getAll()).thenReturn(attributeValues);

		Attributes attributes = mock(Attributes.class);
		when(attributes.get("memberOf")).thenReturn(userGroupAttribute);

		TenantLdapAttributeMapping userGroupMapping = new TenantLdapAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setLdapAttributeName("memberOf");
		userGroupMapping.setTenantID(1);
		List<TenantLdapAttributeMapping> attributeMappings = new ArrayList<>();
		attributeMappings.add(userGroupMapping);
		when(tenantLdapAttributesMapping.getTenantLdapAttributeMapping(1)).thenReturn(attributeMappings);

		Configuration config = new Configuration();
		config.setConfigurationName("MAX_USER_GROUPS_IMPORTED_PER_USER");
		config.setConfigurationValue("2");
		when(systemService.getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER")).thenReturn(config);

		Member member = ldapUserToMemberAttributesMapperImpl.mapFromAttributes(attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().size() == 2);
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("portalusers"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("blrusers"));

		verify(tenantLdapAttributesMapping, times(1)).getTenantLdapAttributeMapping(1);
		verify(userGroupAttribute, times(1)).getAll();
		verify(attributeValues, times(2)).hasMore();
		verify(attributeValues, times(2)).next();
		verify(systemService, times(1)).getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
	}

	@Test
	public void testMapFromAttributesWithNoUserGroupMapping() throws NamingException {
		Attributes attributes = mock(Attributes.class);

		TenantLdapAttributeMapping userGroupMapping = new TenantLdapAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setLdapAttributeName("");
		userGroupMapping.setTenantID(1);
		List<TenantLdapAttributeMapping> attributeMappings = new ArrayList<>();
		attributeMappings.add(userGroupMapping);
		when(tenantLdapAttributesMapping.getTenantLdapAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = ldapUserToMemberAttributesMapperImpl.mapFromAttributes(attributes);
		Assert.assertNotNull(member);
		Assert.assertFalse(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantLdapAttributesMapping, times(1)).getTenantLdapAttributeMapping(1);
	}

	@Test
	public void testMapFromAttributesWithUserGroupMappingValueAsNull() throws NamingException {
		Attributes attributes = mock(Attributes.class);

		TenantLdapAttributeMapping userGroupMapping = new TenantLdapAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setLdapAttributeName(null);
		userGroupMapping.setTenantID(1);
		List<TenantLdapAttributeMapping> attributeMappings = new ArrayList<>();
		attributeMappings.add(userGroupMapping);
		when(tenantLdapAttributesMapping.getTenantLdapAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = ldapUserToMemberAttributesMapperImpl.mapFromAttributes(attributes);
		Assert.assertNotNull(member);
		Assert.assertFalse(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantLdapAttributesMapping, times(1)).getTenantLdapAttributeMapping(1);
	}
}
