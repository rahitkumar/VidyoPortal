package com.vidyo.service;

import static org.junit.Assert.*; 
import static org.unitils.mock.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.ForgotPassword;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.db.ILoginDao;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.exceptions.EmailAddressNotFoundException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.NotificationEmailAddressNotConfiguredException;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class LoginServiceImplTest {
	
	@SpringBeanByType
	private ILoginService loginService;
	
	@SpringBeanByType
	private ReloadableResourceBundleMessageSource ms;
	
	private Mock<ISystemService> mockSystemService;
	private Mock<ILoginDao> mockLoginDao;
	private Mock<EmailService> mockEmailService;
	
	@Before
	public void initialize() {
		((LoginServiceImpl)loginService).setSystem(mockSystemService.getMock());
		((LoginServiceImpl)loginService).setDao(mockLoginDao.getMock());
		((LoginServiceImpl)loginService).setEmailService(mockEmailService.getMock());
		
		((LoginServiceImpl)loginService).setMs(ms);
	}
	
	@Test
	public void forgotAPIPasswordSuccess() {
		String notificationFromEmail = "user@notific.com";
		mockSystemService.returns(notificationFromEmail).getNotificationFromEmailAddress();
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com"; 
		
		ForgotPassword member = new ForgotPassword();
		member.setEmailAddress(forgotPasswordEmail);
		member.setLangID(1);
		member.setMemberID(5);
		member.setMemberName("forgotPasswordMember");
		member.setUsername("forgotPasswordUsername");
		
		List<ForgotPassword> members = new ArrayList<>();
		members.add(member);
		
		List<String> userTypes = new ArrayList<String>();
        userTypes.add(MemberRoleEnum.ADMIN.getMemberRole());
        userTypes.add(MemberRoleEnum.EXECUTIVE.getMemberRole());
        userTypes.add(MemberRoleEnum.NORMAL.getMemberRole());
        userTypes.add(MemberRoleEnum.OPERATOR.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_PANORAMA.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_ROOM.getMemberRole());
		
		mockLoginDao.returns(members).getMembersForEmail(forgotPasswordEmail, 1, userTypes);
		
		try {
			loginService.forgotAPIPassword(1, forgotPasswordEmail, "http", "host.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException
				| EmailAddressNotFoundException | GeneralException e) {
			fail("forgotAPIPassword should not fail.");
		}
		
		mockSystemService.assertInvoked().getNotificationFromEmailAddress();
		mockLoginDao.assertInvoked().getMembersForEmail(forgotPasswordEmail, 1, userTypes);
		mockLoginDao.assertInvoked().updateMemberPassKey(5, any(String.class));
		mockEmailService.assertInvoked().sendEmailAsynchronous(any(SimpleMailMessage.class));
	}
	
	@Test
	public void forgotAPIPasswordFailNotificationEmailAddressNotConfiguredException() {
		String notificationFromEmail = null;
		mockSystemService.returns(notificationFromEmail).getNotificationFromEmailAddress();
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com"; 
		
		try {
			loginService.forgotAPIPassword(1, forgotPasswordEmail, "http", "host.com", "/");
		} catch (NotificationEmailAddressNotConfiguredException e) {
			// OK
		}
		catch (EmailAddressNotFoundException | GeneralException e) {
			fail("forgotAPIPassword should not fail here.");
		}
		
		mockSystemService.assertInvoked().getNotificationFromEmailAddress();
		mockLoginDao.assertNotInvoked().getMembersForEmail(any(String.class), 0, null);
		mockLoginDao.assertNotInvoked().updateMemberPassKey(5, any(String.class));
		mockEmailService.assertNotInvoked().sendEmailAsynchronous(any(SimpleMailMessage.class));
	}
	
	@Test
	public void forgotAPIPasswordFailEmailAddressNotFoundException() {
		String notificationFromEmail = "user@notific.com";
		mockSystemService.returns(notificationFromEmail).getNotificationFromEmailAddress();
		
		String forgotPasswordEmail = "forgotPasswordEmail@mail.com"; 
		
		List<ForgotPassword> members = new ArrayList<>();
		
		List<String> userTypes = new ArrayList<String>();
        userTypes.add(MemberRoleEnum.ADMIN.getMemberRole());
        userTypes.add(MemberRoleEnum.EXECUTIVE.getMemberRole());
        userTypes.add(MemberRoleEnum.NORMAL.getMemberRole());
        userTypes.add(MemberRoleEnum.OPERATOR.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_PANORAMA.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_ROOM.getMemberRole());
		
		mockLoginDao.returns(members).getMembersForEmail(forgotPasswordEmail, 1, userTypes);
		
		try {
			loginService.forgotAPIPassword(1, forgotPasswordEmail, "http", "host.com", "/");
		} catch (EmailAddressNotFoundException e) {
			// OK
		} catch (NotificationEmailAddressNotConfiguredException | GeneralException e) {
			fail("forgotAPIPassword should not fail here.");
		}
		
		mockSystemService.assertInvoked().getNotificationFromEmailAddress();
		mockLoginDao.assertInvoked().getMembersForEmail(forgotPasswordEmail, 1, userTypes);
		mockLoginDao.assertNotInvoked().updateMemberPassKey(5, any(String.class));
		mockEmailService.assertNotInvoked().sendEmailAsynchronous(any(SimpleMailMessage.class));
	}

}
