package com.vidyo.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.ModelAndView;
import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberRoles;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;

public class MemberControllerTest {

	@InjectMocks
	private MemberController memberController;

	@Mock
	private IMemberService service;

	@Mock
	private IUserService user;

	@Mock
	private IRoomService room;

	@Mock
	private IGroupService group;

	@Mock
	private ISystemService system;

	@Mock
	private LicensingService license;

	private ReloadableResourceBundleMessageSource ms;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
		ms = new ReloadableResourceBundleMessageSource();
		ms.setUseCodeAsDefaultMessage(true);
		ReflectionTestUtils.setField(memberController, "ms", ms);

		when(license.getAllowedSeats()).thenReturn(5L);
	}

	private Map<String, Object> getModel(ModelAndView result) {
		return (Map<String, Object>) result.getModel().get("model");
	}

	@Test
	public void testImportMembers() throws Exception {
		List<MemberRoles> roles = new ArrayList<MemberRoles>();
		MemberRoles role = new MemberRoles();
		role.setRoleName("normal");
		roles.add(role);
		when(service.getMemberRoles()).thenReturn(roles);
		when(service.getTenantPrefix()).thenReturn("020");
		
		Group groupBean = new Group();
		when(group.getGroupByName(any())).thenReturn(groupBean);
		
		when(service.getLocationInTenantByTag(any())).thenReturn(new Location());
		when(service.insertMember(any())).thenReturn(1);
		when(room.insertRoom(any())).thenReturn(1);
		
		HttpServletResponse response = mock(HttpServletResponse.class);
		MockMultipartFile file = new MockMultipartFile("client-path", "test.csv", "text/plain",
				("UserType,Username,Password,Fullname,Email,Extension,Group,Language,Description,Proxy,LocationTag,"
						+ "PhoneNumber1,PhoneNumber2,PhoneNumber3,Department,Title,IM,Location\r\n"
						+ "Normal,user1,1,user1,m@m.com,0200200011,Default,en,,No Proxy,Default,,,,,,,").getBytes());
		MockMultipartHttpServletRequest multipartRequest = new MockMultipartHttpServletRequest();
		multipartRequest.addFile(file);
		
		ModelAndView result = memberController.doImportMembersAjax(multipartRequest, response);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(getModel(result).get("success"));
		Assert.assertTrue((Boolean) getModel(result).get("success"));
		Assert.assertEquals((Integer) 1, (Integer) getModel(result).get("userCreated"));
	}
	
	@Test
	public void testExportMembers() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		when(response.getOutputStream()).thenReturn(new DelegatingServletOutputStream(output));
		
		List<Member> members = new ArrayList<Member>();
		Member member = new Member();
		member.setUsername("user1");
		member.setPassword("1");
		member.setMemberName("user1");
		member.setActive(1);
		member.setAllowedToParticipate(1);
		member.setDescription("");
		member.setEmailAddress("m@m.com");
		member.setRoomExtNumber("0200200011");
		member.setGroupID(1);
		member.setLocation("");
		member.setProfileID(1);
		member.setLangID(1);
		member.setRoleID(6);
		members.add(member);
		when(service.getMembers(any())).thenReturn(members);
		
		memberController.doExportMembersAjax(request, response);
		String renderedContent = output.toString();
		
		Assert.assertNotNull(renderedContent);
		Assert.assertTrue(renderedContent.contains("0200200011"));
		Assert.assertTrue(renderedContent.contains("user1"));
		Assert.assertTrue(renderedContent.contains("1"));
		Assert.assertTrue(renderedContent.contains("m@m.com"));
	}
}
