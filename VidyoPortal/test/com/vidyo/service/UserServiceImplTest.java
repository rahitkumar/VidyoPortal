package com.vidyo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.memberbak.MemberBAK;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.IUserDao;
import com.vidyo.db.repository.member.MemberRepository;
import com.vidyo.db.repository.memberbak.MemberBAKRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.user.AccessKeyResponse;

public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private MemberBAKRepository memberBAKRepository;

	@Mock
	private IUserDao userDao;

	@Mock
	private IMemberDao memberDao;

	@Mock
	private ISystemDao systemDao;

	@Mock
	private ITenantService tenantService;

	@Mock
	private MemberRepository memberRepository;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);
		TenantContext.setTenantId(3);
	}

	@Test
	public void testGenerateBAKforMember() {
		when(userDao.saveBAKforMember(anyInt(), anyString())).thenReturn(1);
		when(userDao.updateMember(3, "user1")).thenReturn(1);

		Member member = new Member();
		member.setUsername("user1");
		when(memberDao.getMember(3, 4)).thenReturn(member);

		String result = userServiceImpl.generateBAKforMember(4);

		Assert.assertNotNull(result);
		verify(userDao, times(1)).saveBAKforMember(anyInt(), anyString());
		verify(userDao, times(1)).updateMember(3, "user1");
		verify(memberDao, times(1)).getMember(3, 4);
	}

	@Test
	public void testGenerateBAKforMemberWithBAKSaveException() {
		boolean status = false;
		when(userDao.saveBAKforMember(anyInt(), anyString()))
				.thenThrow(new RuntimeException("Excpetion while save the BAK"));

		try {
			userServiceImpl.generateBAKforMember(4);
		} catch (Exception e) {
			if (e instanceof RuntimeException && e.getMessage().contains("Excpetion while save the BAK")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(userDao, times(1)).saveBAKforMember(anyInt(), anyString());
	}

	@Test
	public void testGenerateBAKforMemberWithSuperUser() {
		when(userDao.saveBAKforMember(anyInt(), anyString())).thenReturn(1);
		when(userDao.updateMember(3, "superuser")).thenReturn(1);

		when(memberDao.getMember(3, 4)).thenReturn(null);
		Member member = new Member();
		member.setUsername("superuser");
		when(memberDao.getSuper(4)).thenReturn(member);

		String result = userServiceImpl.generateBAKforMember(4);

		Assert.assertNotNull(result);
		verify(userDao, times(1)).saveBAKforMember(anyInt(), anyString());
		verify(userDao, times(1)).updateMember(3, "superuser");
		verify(memberDao, times(1)).getMember(3, 4);
		verify(memberDao, times(1)).getSuper(4);
	}

	@Test
	public void testGenerateBAKforMemberWithInvalidUser() {
		when(userDao.saveBAKforMember(anyInt(), anyString())).thenReturn(1);
		when(userDao.updateMember(3, null)).thenReturn(1);

		when(memberDao.getMember(3, 4)).thenReturn(null);
		when(memberDao.getSuper(4)).thenReturn(null);

		String result = userServiceImpl.generateBAKforMember(4);

		Assert.assertNotNull(result);
		verify(userDao, times(1)).saveBAKforMember(anyInt(), anyString());
		verify(userDao, times(1)).updateMember(3, null);
		verify(memberDao, times(1)).getMember(3, 4);
		verify(memberDao, times(1)).getSuper(4);
	}

	@Test
	public void testNewGenerateBAKforMember() {
		when(memberBAKRepository.save(any(MemberBAK.class))).thenReturn(new MemberBAK());

		String result = userServiceImpl.generateBAKforMember(4, MemberBAKType.MorderatorURL);

		Assert.assertNotNull(result);
		verify(memberBAKRepository, times(1)).save(any(MemberBAK.class));
	}

	@Test
	public void testNewGenerateBAKforMemberWithSaveException() {
		boolean status = false;
		when(memberBAKRepository.save(any(MemberBAK.class)))
				.thenThrow(new RuntimeException("Excpetion while save the BAK"));

		try {
			userServiceImpl.generateBAKforMember(4, MemberBAKType.MorderatorURL);
		} catch (Exception e) {
			if (e instanceof RuntimeException && e.getMessage().contains("Excpetion while save the BAK")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(memberBAKRepository, times(1)).save(any(MemberBAK.class));
	}

	@Test
	public void testGetMemberIDForBAK() {
		MemberBAK memberBAK = new MemberBAK();
		memberBAK.setMemberId(4);
		memberBAK.setCreationTime(new Date());
		when(memberBAKRepository.findByBakAndBakType(anyString(), anyString())).thenReturn(memberBAK);

		Configuration timeoutConfig = new Configuration();
		timeoutConfig.setConfigurationValue("360");
		when(systemDao.getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS")).thenReturn(timeoutConfig);

		Integer result = userServiceImpl.getMemberIDForBAK("skjdfhksdjf", MemberBAKType.VidyoReplayLibrary);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.intValue(), 4);
		verify(memberBAKRepository, times(1)).findByBakAndBakType(anyString(), anyString());
		verify(systemDao, times(1)).getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS");
	}

	@Test
	public void testGetMemberIDForBAKWithInvalidBAK() {
		when(memberBAKRepository.findByBakAndBakType(anyString(), anyString())).thenReturn(null);

		Integer result = userServiceImpl.getMemberIDForBAK("skjdfhksdjf", MemberBAKType.VidyoReplayLibrary);

		Assert.assertNull(result);
		verify(memberBAKRepository, times(1)).findByBakAndBakType(anyString(), anyString());
	}

	@Test
	public void testGetMemberIDForBAKWithExpiredBAK() {
		MemberBAK memberBAK = new MemberBAK();
		memberBAK.setMemberId(4);
		// One day old token
		memberBAK.setCreationTime(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000)));
		when(memberBAKRepository.findByBakAndBakType(anyString(), anyString())).thenReturn(memberBAK);

		Configuration timeoutConfig = new Configuration();
		timeoutConfig.setConfigurationValue("360");
		when(systemDao.getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS")).thenReturn(timeoutConfig);

		Integer result = userServiceImpl.getMemberIDForBAK("skjdfhksdjf", MemberBAKType.VidyoReplayLibrary);

		Assert.assertNull(result);
		verify(memberBAKRepository, times(1)).findByBakAndBakType(anyString(), anyString());
		verify(systemDao, times(1)).getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS");
	}

	@Test
	public void testGetMemberIDForBAKWithExceptionWhileFetchingSystemConfig() {
		MemberBAK memberBAK = new MemberBAK();
		memberBAK.setMemberId(4);
		memberBAK.setCreationTime(new Date());
		when(memberBAKRepository.findByBakAndBakType(anyString(), anyString())).thenReturn(memberBAK);

		when(systemDao.getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS")).thenThrow(new RuntimeException());

		Integer result = userServiceImpl.getMemberIDForBAK("skjdfhksdjf", MemberBAKType.VidyoReplayLibrary);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.intValue(), 4);
		verify(memberBAKRepository, times(1)).findByBakAndBakType(anyString(), anyString());
		verify(systemDao, times(1)).getConfiguration(0, "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS");
	}

	@Test
	public void testGetOnetimeAccessUri() {
		Tenant tenant = new Tenant();
		when(tenantService.getTenant(3)).thenReturn(tenant);

		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);

		doReturn("sjdkhfsd").when(partiallyMockedUserServiceImpl).generateBAKforMember(4, MemberBAKType.ChangePassword);

		AccessKeyResponse result = partiallyMockedUserServiceImpl.getOnetimeAccessUri(3, 4,
				MemberBAKType.ChangePassword);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.getAccessKey(), "sjdkhfsd");
		verify(tenantService, times(1)).getTenant(3);
		verify(partiallyMockedUserServiceImpl, times(1)).generateBAKforMember(4, MemberBAKType.ChangePassword);
	}

	@Test
	public void testGetOnetimeAccessUriForInvalidTenant() {
		when(tenantService.getTenant(3)).thenReturn(null);

		AccessKeyResponse result = userServiceImpl.getOnetimeAccessUri(3, 4, MemberBAKType.ChangePassword);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.getStatus(), AccessKeyResponse.INVALID_TENANT);
		verify(tenantService, times(1)).getTenant(3);
	}

	@Test
	public void testGetOnetimeAccessUriForFailedBAKGeneration() {
		Tenant tenant = new Tenant();
		when(tenantService.getTenant(3)).thenReturn(tenant);

		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);

		doReturn(null).when(partiallyMockedUserServiceImpl).generateBAKforMember(4, MemberBAKType.ChangePassword);

		AccessKeyResponse result = partiallyMockedUserServiceImpl.getOnetimeAccessUri(3, 4,
				MemberBAKType.ChangePassword);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.getStatus(), AccessKeyResponse.KEY_GEN_FAILED);
		verify(tenantService, times(1)).getTenant(3);
		verify(partiallyMockedUserServiceImpl, times(1)).generateBAKforMember(4, MemberBAKType.ChangePassword);
	}

	@Test
	public void testGetOnetimeAccessUriWithBAKGenerationThrowingException() {
		Tenant tenant = new Tenant();
		when(tenantService.getTenant(3)).thenReturn(tenant);

		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);

		doThrow(new RuntimeException()).when(partiallyMockedUserServiceImpl).generateBAKforMember(4,
				MemberBAKType.ChangePassword);

		AccessKeyResponse result = partiallyMockedUserServiceImpl.getOnetimeAccessUri(3, 4,
				MemberBAKType.ChangePassword);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.getStatus(), AccessKeyResponse.KEY_GEN_FAILED);
		verify(tenantService, times(1)).getTenant(3);
		verify(partiallyMockedUserServiceImpl, times(1)).generateBAKforMember(4, MemberBAKType.ChangePassword);
	}

	@Test
	public void testGetMemberForBak() {
		when(memberRepository.findByMemberID(4)).thenReturn(new Member());

		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);
		doReturn(4).when(partiallyMockedUserServiceImpl).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);

		Member result = partiallyMockedUserServiceImpl.getMemberForBak("sdkhsdkf", MemberBAKType.MorderatorURL);

		Assert.assertNotNull(result);
		verify(memberRepository, times(1)).findByMemberID(4);
		verify(partiallyMockedUserServiceImpl, times(1)).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);
	}

	@Test
	public void testGetMemberForBakForInvalidBAK() {
		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);
		doReturn(null).when(partiallyMockedUserServiceImpl).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);

		Member result = partiallyMockedUserServiceImpl.getMemberForBak("sdkhsdkf", MemberBAKType.MorderatorURL);

		Assert.assertNull(result);
		verify(partiallyMockedUserServiceImpl, times(1)).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);
	}

	@Test
	public void testGetMemberForBakForInvalidMemberId() {
		when(memberRepository.findByMemberID(4)).thenReturn(null);

		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);
		doReturn(4).when(partiallyMockedUserServiceImpl).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);

		Member result = partiallyMockedUserServiceImpl.getMemberForBak("sdkhsdkf", MemberBAKType.MorderatorURL);

		Assert.assertNull(result);
		verify(memberRepository, times(1)).findByMemberID(4);
		verify(partiallyMockedUserServiceImpl, times(1)).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);
	}

	@Test
	public void testGetMemberForBakForExceptionCase() {
		boolean status = false;
		UserServiceImpl partiallyMockedUserServiceImpl = spy(userServiceImpl);
		doThrow(new RuntimeException("Exception while retrieving member details for BAK"))
				.when(partiallyMockedUserServiceImpl).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);

		try {
			partiallyMockedUserServiceImpl.getMemberForBak("sdkhsdkf", MemberBAKType.MorderatorURL);
		} catch (Exception e) {
			if (e instanceof RuntimeException
					&& e.getMessage().contains("Exception while retrieving member details for BAK")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(partiallyMockedUserServiceImpl, times(1)).getMemberIDForBAK("sdkhsdkf", MemberBAKType.MorderatorURL);
	}

	@Test
	public void testDeleteMemberBAK() {
		doNothing().when(memberBAKRepository).deleteByBak(anyString());

		userServiceImpl.deleteMemberBAK("sdfjhgskjhuwh");

		verify(memberBAKRepository, times(1)).deleteByBak(anyString());
	}

	@Test
	public void testDeleteMemberBAKForExceptionCase() {
		boolean status = false;
		doThrow(new RuntimeException("Exception while deleting BAK")).when(memberBAKRepository)
				.deleteByBak(anyString());

		try {
			userServiceImpl.deleteMemberBAK("sdfjhgskjhuwh");
		} catch (Exception e) {
			if (e instanceof RuntimeException && e.getMessage().contains("Exception while deleting BAK")) {
				status = true;
			}
		}

		Assert.assertTrue(status);
		verify(memberBAKRepository, times(1)).deleteByBak(anyString());
	}

}
