package com.vidyo.service.idp;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;

public class IdpUserToMemberAttributesMapperImplTest {

	@InjectMocks
	private IdpUserToMemberAttributesMapperImpl idpUserToMemberAttributesMapperImpl;

	@Mock
	private TenantIdpAttributesMapping tenantIdpAttributesMapping;

	@Mock
	private IMemberService memberService;

	@Mock
	private IGroupService groupService;

	@Mock
	private ISystemService systemService;

	@Mock
	private IServiceService serviceService;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);

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

		when(serviceService.getLocation(1)).thenReturn(new Location());
	}

	@AfterMethod
	private void destroy() {
		verify(memberService, times(1)).getTenantPrefix();
		verify(groupService, times(2)).getDefaultGroup();
		verify(systemService, times(1)).getTenantDefaultLocationTagID();
		verify(serviceService, times(1)).getLocation(1);
	}

	@Test
	public void testGetMemberFromAttributesWith3Groups() {
		Attribute userGroupsAttribute = Mockito.mock(Attribute.class);
		when(userGroupsAttribute.getName()).thenReturn("groups");

		List<XMLObject> userGroupValues = new ArrayList<>();
		XSString userGroup1 = mock(XSString.class);
		when(userGroup1.getValue()).thenReturn("grp-1");
		userGroupValues.add(userGroup1);
		XSString userGroup2 = mock(XSString.class);
		when(userGroup2.getValue()).thenReturn("grp-2");
		userGroupValues.add(userGroup2);
		XSString userGroup3 = mock(XSString.class);
		when(userGroup3.getValue()).thenReturn("grp-3");
		userGroupValues.add(userGroup3);

		when(userGroupsAttribute.getAttributeValues()).thenReturn(userGroupValues);

		List<Attribute> attributes = new ArrayList<>();
		attributes.add(userGroupsAttribute);

		TenantIdpAttributeMapping userGroupMapping = new TenantIdpAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setIdpAttributeName("groups");
		userGroupMapping.setTenantID(1);

		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<TenantIdpAttributeMapping>();
		attributeMappings.add(userGroupMapping);
		when(tenantIdpAttributesMapping.getTenantIdpAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = idpUserToMemberAttributesMapperImpl.getMemberFromAttributes(1, attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().size() == 3);
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("grp-1"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("grp-2"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("grp-3"));

		verify(tenantIdpAttributesMapping, times(1)).getTenantIdpAttributeMapping(1);
		verify(systemService, times(1)).getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
	}
	
	@Test
	public void testGetMemberFromAttributesWithNoGroups() {
		Attribute userGroupsAttribute = Mockito.mock(Attribute.class);
		when(userGroupsAttribute.getName()).thenReturn("groups");

		when(userGroupsAttribute.getAttributeValues()).thenReturn(new ArrayList<>());

		List<Attribute> attributes = new ArrayList<>();
		attributes.add(userGroupsAttribute);

		TenantIdpAttributeMapping userGroupMapping = new TenantIdpAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setIdpAttributeName("groups");
		userGroupMapping.setTenantID(1);

		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<TenantIdpAttributeMapping>();
		attributeMappings.add(userGroupMapping);
		when(tenantIdpAttributesMapping.getTenantIdpAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = idpUserToMemberAttributesMapperImpl.getMemberFromAttributes(1, attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantIdpAttributesMapping, times(1)).getTenantIdpAttributeMapping(1);
	}
	
	@Test
	public void testGetMemberFromAttributesWithGroupsMoreThanLimit() {
		Attribute userGroupsAttribute = Mockito.mock(Attribute.class);
		when(userGroupsAttribute.getName()).thenReturn("groups");

		List<XMLObject> userGroupValues = new ArrayList<>();
		XSString userGroup1 = mock(XSString.class);
		when(userGroup1.getValue()).thenReturn("grp-1");
		userGroupValues.add(userGroup1);
		XSString userGroup2 = mock(XSString.class);
		when(userGroup2.getValue()).thenReturn("grp-2");
		userGroupValues.add(userGroup2);
		XSString userGroup3 = mock(XSString.class);
		when(userGroup3.getValue()).thenReturn("grp-3");
		userGroupValues.add(userGroup3);

		when(userGroupsAttribute.getAttributeValues()).thenReturn(userGroupValues);

		List<Attribute> attributes = new ArrayList<>();
		attributes.add(userGroupsAttribute);

		TenantIdpAttributeMapping userGroupMapping = new TenantIdpAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setIdpAttributeName("groups");
		userGroupMapping.setTenantID(1);

		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<TenantIdpAttributeMapping>();
		attributeMappings.add(userGroupMapping);
		when(tenantIdpAttributesMapping.getTenantIdpAttributeMapping(1)).thenReturn(attributeMappings);
		
		Configuration config = new Configuration();
		config.setConfigurationName("MAX_USER_GROUPS_IMPORTED_PER_USER");
		config.setConfigurationValue("2");
		when(systemService.getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER")).thenReturn(config);

		Member member = idpUserToMemberAttributesMapperImpl.getMemberFromAttributes(1, attributes);
		Assert.assertNotNull(member);
		Assert.assertTrue(member.isUserGroupsUpdated());
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().size() == 2);
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("grp-1"));
		Assert.assertTrue(member.getUserGroupsFromAuthProvider().contains("grp-2"));

		verify(tenantIdpAttributesMapping, times(1)).getTenantIdpAttributeMapping(1);
		verify(systemService, times(1)).getConfiguration("MAX_USER_GROUPS_IMPORTED_PER_USER");
	}

	@Test
	public void testGetMemberFromAttributesWithNoUserGroupsMapping() {
		Attribute userGroupsAttribute = Mockito.mock(Attribute.class);
		when(userGroupsAttribute.getName()).thenReturn("groups");

		List<XMLObject> userGroupValues = new ArrayList<>();
		XSString userGroup1 = mock(XSString.class);
		when(userGroup1.getValue()).thenReturn("grp-1");
		userGroupValues.add(userGroup1);
		XSString userGroup2 = mock(XSString.class);
		when(userGroup2.getValue()).thenReturn("grp-2");
		userGroupValues.add(userGroup2);
		XSString userGroup3 = mock(XSString.class);
		when(userGroup3.getValue()).thenReturn("grp-3");
		userGroupValues.add(userGroup3);

		when(userGroupsAttribute.getAttributeValues()).thenReturn(userGroupValues);

		List<Attribute> attributes = new ArrayList<>();
		attributes.add(userGroupsAttribute);

		TenantIdpAttributeMapping userGroupMapping = new TenantIdpAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setIdpAttributeName("");
		userGroupMapping.setTenantID(1);

		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<TenantIdpAttributeMapping>();
		attributeMappings.add(userGroupMapping);
		when(tenantIdpAttributesMapping.getTenantIdpAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = idpUserToMemberAttributesMapperImpl.getMemberFromAttributes(1, attributes);
		Assert.assertNotNull(member);
		Assert.assertFalse(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantIdpAttributesMapping, times(1)).getTenantIdpAttributeMapping(1);
	}

	@Test
	public void testGetMemberFromAttributesWithUserGroupsMappingValueAsNull() {
		Attribute userGroupsAttribute = Mockito.mock(Attribute.class);
		when(userGroupsAttribute.getName()).thenReturn("groups");

		List<XMLObject> userGroupValues = new ArrayList<>();
		XSString userGroup1 = mock(XSString.class);
		when(userGroup1.getValue()).thenReturn("grp-1");
		userGroupValues.add(userGroup1);
		XSString userGroup2 = mock(XSString.class);
		when(userGroup2.getValue()).thenReturn("grp-2");
		userGroupValues.add(userGroup2);
		XSString userGroup3 = mock(XSString.class);
		when(userGroup3.getValue()).thenReturn("grp-3");
		userGroupValues.add(userGroup3);

		when(userGroupsAttribute.getAttributeValues()).thenReturn(userGroupValues);

		List<Attribute> attributes = new ArrayList<>();
		attributes.add(userGroupsAttribute);

		TenantIdpAttributeMapping userGroupMapping = new TenantIdpAttributeMapping();
		userGroupMapping.setVidyoAttributeName("User Groups");
		userGroupMapping.setIdpAttributeName(null);
		userGroupMapping.setTenantID(1);

		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<TenantIdpAttributeMapping>();
		attributeMappings.add(userGroupMapping);
		when(tenantIdpAttributesMapping.getTenantIdpAttributeMapping(1)).thenReturn(attributeMappings);

		Member member = idpUserToMemberAttributesMapperImpl.getMemberFromAttributes(1, attributes);
		Assert.assertNotNull(member);
		Assert.assertFalse(member.isUserGroupsUpdated());
		Assert.assertNull(member.getUserGroupsFromAuthProvider());

		verify(tenantIdpAttributesMapping, times(1)).getTenantIdpAttributeMapping(1);
	}
}
