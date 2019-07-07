package com.vidyo.service.idp;

import static org.junit.Assert.*;
import static org.unitils.mock.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.mock.core.MockObject;
import org.unitils.mock.core.PartialMockObject;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.MemberServiceImpl;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class IdpUserToMemberAttributesMapperImplTest {
	
	@SpringBeanByType
	private IdpUserToMemberAttributesMapper idpUserToMemberAttributesMapper;
	
	private Mock<IGroupService> mockGroupService;
	private Mock<ISystemService> mockSystemService;
	private Mock<IServiceService> mockServiceService;
	private Mock<IMemberService> mockMemberService;
	private Mock<TenantIdpAttributesMapping> mockTenantIdpAttributes;
	private Mock<IRoomService> mockRoomService;
	
	@Before
	public void initialize() {
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setGroupService(mockGroupService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setMemberService(mockMemberService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setRoomService(mockRoomService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setServiceService(mockServiceService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setSystemService(mockSystemService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setTenantIdpAttributes(mockTenantIdpAttributes.getMock());
	}
	
	@Test
	public void getMemberFromAttributesTest() {
		PartialMockObject<IMemberService> partialmockMemberService = new PartialMockObject<IMemberService>(MemberServiceImpl.class, this);
		((MemberServiceImpl)partialmockMemberService.getMock()).setGroupService(mockGroupService.getMock());
		((IdpUserToMemberAttributesMapperImpl)idpUserToMemberAttributesMapper).setMemberService(partialmockMemberService.getMock());
		
		List<Attribute> attributes = new ArrayList<>();
		
		Mock<Attribute> attribute = new MockObject<>(Attribute.class, this);
		List<XMLObject> attributeValues = new ArrayList<>();
		Mock<XSString> userName = new MockObject<>(XSString.class, this);;
		userName.returns("UserName").getValue();
		attributeValues.add(userName.getMock());
		attribute.returns("IdpUserName").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> userType = new MockObject<>(XSString.class, this);;
		userType.returns("Normal").getValue();
		attributeValues.add(userType.getMock());
		attribute.returns("IdpUserType").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> displayName = new MockObject<>(XSString.class, this);;
		displayName.returns("Display name").getValue();
		attributeValues.add(displayName.getMock());
		attribute.returns("IdpDisplayName").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> emailAddress = new MockObject<>(XSString.class, this);;
		emailAddress.returns("email@company.com").getValue();
		attributeValues.add(emailAddress.getMock());
		attribute.returns("IdpEmailAddress").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> extension = new MockObject<>(XSString.class, this);;
		extension.returns("111458").getValue();
		attributeValues.add(extension.getMock());
		attribute.returns("IdpExtension").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> groupAttr = new MockObject<>(XSString.class, this);;
		groupAttr.returns("Default").getValue();
		attributeValues.add(groupAttr.getMock());
		attribute.returns("IdpGroup").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		attribute = new MockObject<>(Attribute.class, this);
		attributeValues = new ArrayList<>();
		Mock<XSString> proxyAttr = new MockObject<>(XSString.class, this);;
		proxyAttr.returns("VP").getValue();
		attributeValues.add(proxyAttr.getMock());
		attribute.returns("IdpProxy").getName();
		attribute.returns(attributeValues).getAttributeValues();
		attributes.add(attribute.getMock());
		
		partialmockMemberService.returns("111").getTenantPrefix();
		
		Group group = new Group();
		group.setGroupID(1);
		group.setGroupName("Default");
		
		mockGroupService.returns(group).getDefaultGroup();
		
		Location location = new Location();
		location.setLocationID(1);
		location.setLocationTag("Default");
		mockSystemService.returns(1).getTenantDefaultLocationTagID();
		mockServiceService.returns(location).getLocation(1);
		
		List<TenantIdpAttributeMapping> attributeMappings = new ArrayList<>();
		TenantIdpAttributeMapping attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("UserName");
		attributeMapping.setIdpAttributeName("IdpUserName");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("UserType");
		attributeMapping.setIdpAttributeName("IdpUserType");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("DisplayName");
		attributeMapping.setIdpAttributeName("IdpDisplayName");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("EmailAddress");
		attributeMapping.setIdpAttributeName("IdpEmailAddress");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("Extension");
		attributeMapping.setIdpAttributeName("IdpExtension");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("Group");
		attributeMapping.setIdpAttributeName("IdpGroup");
		attributeMappings.add(attributeMapping);
		
		attributeMapping = new TenantIdpAttributeMapping();
		attributeMapping.setVidyoAttributeName("Proxy");
		attributeMapping.setIdpAttributeName("IdpProxy");
		attributeMappings.add(attributeMapping);
		
		mockTenantIdpAttributes.returns(attributeMappings).getTenantIdpAttributeMapping(1);
		
		MemberRoles memberRole = new MemberRoles();
		memberRole.setRoleID(3);
		memberRole.setRoleName("Normal");
		
		Proxy proxy = new Proxy();
		proxy.setProxyID(4);
		proxy.setProxyName("VP");
		
		partialmockMemberService.returns(memberRole).getMemberRoleByName(any(String.class));
		partialmockMemberService.returns(false).isMemberExistForUserName(any(String.class), 0);
		mockGroupService.returns(group).getGroupByName(any(String.class));
		partialmockMemberService.returns(proxy).getProxyByName(any(String.class));
		
		
		Member member = idpUserToMemberAttributesMapper.getMemberFromAttributes(1, attributes);
		
		assertNotNull(member);
		assertEquals("UserName", member.getUsername());
		assertEquals("Normal", member.getRoleName());
		assertEquals(3, member.getRoleID());
		assertEquals("Display name", member.getMemberName());
		assertEquals("email@company.com", member.getEmailAddress());
		assertEquals("111111458", member.getRoomExtNumber());
		assertEquals(1, member.getGroupID());
		assertEquals("Default", member.getGroupName());
		assertEquals(4, member.getProxyID());
		assertEquals("VP", member.getProxyName());
		
	}

}
