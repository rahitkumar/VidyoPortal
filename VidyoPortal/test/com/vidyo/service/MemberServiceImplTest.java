package com.vidyo.service;


import com.vidyo.bo.*;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.exceptions.AccessRestrictedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;


public class MemberServiceImplTest {
	@InjectMocks
	private IMemberService memberService = new MemberServiceImpl();

	@Mock
	private IMemberDao memberDaoMock;
	@Mock
	private IUserService userServiceMock;
	@Mock
	private ITenantService tenantServiceMock;
	@Mock
	private IServiceDao serviceDaoMock;
	@Mock
	private IGroupService groupServiceMock;

	@BeforeMethod(alwaysRun = true)
	public void injectDoubles() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 *
	 * List<Member> getMembers(MemberFilter filter)
	 */
	@Test(groups = {"memberService"})
	public void getMembersWithValidMemberFilter(){
		MemberFilter filter = new MemberFilter();

		List<Member> list = new ArrayList<>();
		Member m1 = new Member();
		list.add(m1);
		Member m2 = new Member();
		list.add(m2);

		TenantContext.setTenantId(1);
		when(memberDaoMock.getMembers(1,filter)).thenReturn(list);

		assertEquals(memberService.getMembers(filter),list);
	}

	@Test(groups = {"memberService"})
	public void getMembersWithInvalidMemberFilter(){
		MemberFilter filter = new MemberFilter();

		TenantContext.setTenantId(1);
		when(memberDaoMock.getMembers(1,filter)).thenReturn(null);

		assertEquals(memberService.getMembers(filter),null);
	}

	/**
	 *
	 * Long getCountMembers(MemberFilter filter)
	 */
	@Test(groups = {"memberService"})
	public void getCountMembersWithValidMemberFilter(){
		MemberFilter filter = new MemberFilter();

		TenantContext.setTenantId(1);
		Long num = new Long(5);
		when(memberDaoMock.getCountMembers(1,filter)).thenReturn(num);

		assertEquals(memberService.getCountMembers(filter),num);
	}

	@Test(groups = {"memberService"})
	public void getCountMembersWithInvalidMemberFilter(){
		MemberFilter filter = new MemberFilter();

		TenantContext.setTenantId(1);
		Long num = new Long(0);
		when(memberDaoMock.getCountMembers(1,filter)).thenReturn(num);

		assertEquals(memberService.getCountMembers(filter),num);
	}

	/**
	 *
	 * int updateSuper(Member member)
	 */
	@Test(groups = {"memberService"})
	public void updateSuperWithValidMemberAndInactiveItself() throws AccessRestrictedException {
		Member member = new Member();

		User loggedInUser = new User();
		member.setActive(2);
		member.setMemberID(1);
		loggedInUser.setMemberID(1);
		when(userServiceMock.getLoginUser()).thenReturn(loggedInUser);

		assertEquals(memberService.updateSuper(member),-5);

	}

	@Test(groups = {"memberService"})
	public void updateSuperWithValidMemberAndEmptyPasswordAndEmptyUserName() throws AccessRestrictedException {
		Member member = new Member();

		User loggedInUser = new User();
		member.setActive(1);
		member.setMemberID(1);
		loggedInUser.setMemberID(2);
		when(userServiceMock.getLoginUser()).thenReturn(loggedInUser);

		List<Integer> tenantIds = new ArrayList<>();
		tenantIds.add(1);
		tenantIds.add(2);

		when(tenantServiceMock.getAllTenantIds()).thenReturn(tenantIds);

		when(memberDaoMock.updateSuper(member)).thenReturn(5);

		assertEquals(memberService.updateSuper(member),5);

	}

	@Test(groups = {"memberService"})
	public void updateSuperWithValidMemberAndEmptyPassword() throws AccessRestrictedException {
		Member member = new Member();

		User loggedInUser = new User();
		member.setActive(1);
		member.setMemberID(1);
		member.setTenantID(1);
		member.setUsername("user");
		loggedInUser.setMemberID(2);
		when(userServiceMock.getLoginUser()).thenReturn(loggedInUser);

		List<Integer> tenantIds = new ArrayList<>();
		tenantIds.add(1);
		tenantIds.add(2);

		when(memberDaoMock.updateMember(1,"user")).thenReturn(2);
		when(tenantServiceMock.getAllTenantIds()).thenReturn(tenantIds);
		when(memberDaoMock.updateSuper(member)).thenReturn(5);

		assertEquals(memberService.updateSuper(member),5);
	}

	@Test(groups = {"memberService"})
	public void updateSuperWithValidMember() throws AccessRestrictedException {
		Member member = new Member();

		User loggedInUser = new User();
		member.setActive(1);
		member.setMemberID(1);
		member.setTenantID(1);
		member.setUsername("user");
		member.setPassword("pwd");
		loggedInUser.setMemberID(2);
		when(userServiceMock.getLoginUser()).thenReturn(loggedInUser);

		List<Integer> tenantIds = new ArrayList<>();
		tenantIds.add(1);
		tenantIds.add(2);

		when(memberDaoMock.updateMember(1,"user")).thenReturn(2);
		when(tenantServiceMock.getAllTenantIds()).thenReturn(tenantIds);
		when(memberDaoMock.updateSuper(member)).thenReturn(5);

		assertEquals(memberService.updateSuper(member),5);
	}

	@Test(groups = {"memberService"})
	public void updateSuperWithValidMemberAndThrowDataAccessException() throws AccessRestrictedException {
		Member member = new Member();

		User loggedInUser = new User();
		member.setActive(1);
		member.setMemberID(1);
		member.setTenantID(1);
		member.setUsername("user");
		member.setPassword("pwd");
		loggedInUser.setMemberID(2);
		when(userServiceMock.getLoginUser()).thenReturn(loggedInUser);

		List<Integer> tenantIds = new ArrayList<>();
		tenantIds.add(1);
		tenantIds.add(2);

		when(memberDaoMock.updateMember(1,"user")).thenReturn(2);
		when(tenantServiceMock.getAllTenantIds()).thenReturn(tenantIds);

		DataAccessException dae = new DataAccessException("dae"){};
		when(memberDaoMock.updateSuper(member)).thenThrow(dae);

		assertEquals(memberService.updateSuper(member),0);
	}

	/**
	 *
	 * Member getMember(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberWithValidMemberID(){
		int memberID = 1;

		Member member = new Member();
		TenantContext.setTenantId(1);
		when(memberDaoMock.getMember(1,1)).thenReturn(member);

		assertEquals(memberService.getMember(memberID),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberWithInvalidMemberID(){
		int memberID = 1;

		TenantContext.setTenantId(1);

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getMember(1,1)).thenThrow(dae);

		assertEquals(memberService.getMember(memberID),null);
	}

	/**
	 *
	 * Member getMember(int tenantID, int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberWithValidTenantIdAndValidMemberId(){
		int memberID = 1;
		int tenantID = 1;
		Member member = new Member();

		when(memberDaoMock.getMember(1,1)).thenReturn(member);

		assertEquals(memberService.getMember(tenantID,memberID),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberWithInvalidTenantId(){
		int memberID = 1;
		int tenantID = 0;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getMember(0,1)).thenThrow(dae);

		assertEquals(memberService.getMember(tenantID,memberID),null);
	}

	@Test(groups = {"memberService"})
	public void getMemberWithInvalidMemberId(){
		int memberID = 0;
		int tenantID = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getMember(1,0)).thenThrow(dae);

		assertEquals(memberService.getMember(tenantID,memberID),null);
	}

	/**
	 *
	 * Member getMemberByRoom(int roomID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberByRoomWithValidRoomID(){
		int roomID = 1;

		Member member = new Member();
		TenantContext.setTenantId(1);

		when(memberDaoMock.getMemberByRoom(1,1)).thenReturn(member);

		assertEquals(memberService.getMemberByRoom(roomID),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberByRoomWithInvalidRoomID(){
		int roomID = 0;

		TenantContext.setTenantId(1);

		when(memberDaoMock.getMemberByRoom(1,1)).thenReturn(null);

		assertEquals(memberService.getMemberByRoom(roomID),null);
	}

	/**
	 *
	 * Member getMemberNoRoom(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberNoRoomWithValidMemberID(){
		int memberID = 1;

		TenantContext.setTenantId(1);
		Member member = new Member();

		when(memberDaoMock.getMemberNoRoom(1,memberID)).thenReturn(member);

		assertEquals(memberService.getMemberNoRoom(memberID),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberNoRoomWithInvalidMemberID(){
		int memberID = 1;

		TenantContext.setTenantId(1);
		Member member = new Member();

		when(memberDaoMock.getMemberNoRoom(1,memberID)).thenReturn(member);

		assertEquals(memberService.getMemberNoRoom(memberID),member);
	}

	/**
	 *
	 * Member getMemberByName(String userName)
	 */
	@Test(groups = {"memberService"})
	public void getMemberByNameWithValidUserName(){
		String userName = "userName";

		TenantContext.setTenantId(1);
		Member member = new Member();

		when(memberDaoMock.getMemberByName(1,userName)).thenReturn(member);

		assertEquals(memberService.getMemberByName(userName),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberByNameWithInvalidUserName(){
		String userName = "";

		TenantContext.setTenantId(1);
		Member member = new Member();

		when(memberDaoMock.getMemberByName(1,userName)).thenReturn(null);

		assertEquals(memberService.getMemberByName(userName),null);
	}

	/**
	 *
	 * Language getMemberLang(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberLangWithValidMemberID() {
		int memberID = 1;

		TenantContext.setTenantId(1);
		Language lang = new Language();

		when(memberDaoMock.getMemberLang(1,1)).thenReturn(lang);

		assertEquals(memberService.getMemberLang(memberID),lang);
	}

	@Test(groups = {"memberService"})
	public void getMemberLangWithInvalidMemberID() {
		int memberID = 0;

		TenantContext.setTenantId(1);

		when(memberDaoMock.getMemberLang(1,0)).thenReturn(null);

		assertEquals(memberService.getMemberLang(memberID),null);
	}

	/**
	 *
	 * Member getMemberByName(String userName, String tenantName)
	 */
	@Test(groups = {"memberService"})
	public void getMemberByNameWithValidUserNameAndValidTenantName(){
		String userName = "userName";
		String tenantName = "tenantName";

		Member member = new Member();
		Tenant local_tenant  = new Tenant();
		local_tenant.setTenantID(1);
		when(tenantServiceMock.getTenant(tenantName)).thenReturn(local_tenant);
		when(memberDaoMock.getMemberByName(1,"userName")).thenReturn(member);

		assertEquals(memberService.getMemberByName(userName,tenantName),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberByNameWithInvalidUserNameAndValidTenantName(){
		String userName = "";
		String tenantName = "tenantName";

		Tenant local_tenant  = new Tenant();
		local_tenant.setTenantID(1);
		when(tenantServiceMock.getTenant(tenantName)).thenReturn(local_tenant);
		when(memberDaoMock.getMemberByName(1,"")).thenReturn(null);

		assertEquals(memberService.getMemberByName(userName,tenantName),null);
	}

	@Test(groups = {"memberService"},expectedExceptions = NullPointerException.class)
	public void getMemberByNameWithInvalidTenantName(){
		String userName = "userName";
		String tenantName = "";

		Tenant local_tenant  = null;
		when(tenantServiceMock.getTenant(tenantName)).thenReturn(local_tenant);

		memberService.getMemberByName(userName,tenantName);
	}

	/**
	 *
	 * Member getMemberByName(String userName, int tenantID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberByNameWithValidUserNameAndValidTenantID(){
		String userName = "userName";
		int tenantID = 1;

		Member member = new Member();
		when(memberDaoMock.getMemberByName(tenantID,userName)).thenReturn(member);

		assertEquals(memberService.getMemberByName(userName,tenantID),member);
	}

	@Test(groups = {"memberService"})
	public void getMemberByNameWithValidUserNameAndInvalidTenantID(){
		String userName = "userName";
		int tenantID = 0;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getMemberByName(tenantID,userName)).thenThrow(dae);

		assertEquals(memberService.getMemberByName(userName,tenantID),null);
	}

	@Test(groups = {"memberService"})
	public void getMemberByNameWithInvalidUserNameAndValidTenantID(){
		String userName = "";
		int tenantID = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getMemberByName(tenantID,userName)).thenThrow(dae);

		assertEquals(memberService.getMemberByName(userName,tenantID),null);
	}

	/**
	 *
	 * int updateMember(int memberID, Member member)
	 */

	/**
	 *
	 * int updateMember(int tenantID, int memberID, Member member)
	 */

	/**
	 *
	 * updateUserImageAlwdFlag(int tenantID, int memberID, String userName, int userImageAllowed)
	 */
	@Test(groups = {"memberService"})
	public void updateUserImageAlwdFlagWithValidInput(){
		int tenantID = 1;
		int memberID = 1;
		String userName = "userName";
		int userImageAllowed = 1;

		when(memberDaoMock.updateUserImageAlwdFlag(tenantID,memberID,userImageAllowed)).thenReturn(5);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

		assertEquals(memberService.updateUserImageAlwdFlag(tenantID,memberID,userName,userImageAllowed),5);
	}

	@Test(groups = {"memberService"})
	public void updateUserImageAlwdFlagWithInvalidUserID(){
		int tenantID = 1;
		int memberID = 0;
		String userName = "userName";
		int userImageAllowed = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.updateUserImageAlwdFlag(tenantID,memberID,userImageAllowed)).thenThrow(dae);

		assertEquals(memberService.updateUserImageAlwdFlag(tenantID,memberID,userName,userImageAllowed),0);
	}

	@Test(groups = {"memberService"})
	public void updateUserImageAlwdFlagWithInvalidUserName(){
		int tenantID = 1;
		int memberID = 1;
		String userName = null;
		int userImageAllowed = 1;

		when(memberDaoMock.updateUserImageAlwdFlag(tenantID,memberID,userImageAllowed)).thenReturn(1);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

		assertEquals(memberService.updateUserImageAlwdFlag(tenantID,memberID,userName,userImageAllowed),1);
	}

	/**
	 *
	 * int updateUserImageUploadedFlags(int tenantID, int memberID, String userName, int userImageUploaded)
	 */
	@Test(groups = {"memberService"})
	public void updateUserImageUploadedFlagsWithValidInput(){
		int tenantID = 1;
		int memberID = 1;
		String userName = "userName";
		int userImageUploaded = 1;

		when(memberDaoMock.updateUserImageUploadedFlags(tenantID,memberID,userImageUploaded)).thenReturn(5);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

		assertEquals(memberService.updateUserImageUploadedFlags(tenantID,memberID,userName,userImageUploaded),5);
	}

	@Test(groups = {"memberService"})
	public void updateUserImageUploadedFlagsWithInvalidUserID(){
		int tenantID = 1;
		int memberID = 0;
		String userName = "userName";
		int userImageUploaded = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.updateUserImageUploadedFlags(tenantID,memberID,userImageUploaded)).thenThrow(dae);

		assertEquals(memberService.updateUserImageUploadedFlags(tenantID,memberID,userName,userImageUploaded),0);
	}

	@Test(groups = {"memberService"})
	public void updateUserImageUploadedFlagsWithInvalidUserName(){
		int tenantID = 1;
		int memberID = 1;
		String userName = null;
		int userImageUploaded = 1;

		when(memberDaoMock.updateUserImageUploadedFlags(tenantID,memberID,userImageUploaded)).thenReturn(1);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

		assertEquals(memberService.updateUserImageUploadedFlags(tenantID,memberID,userName,userImageUploaded),1);
	}

	/**
	 *
	 * int updateMemberUserImageUploaded(int tenantID, int memberID, String userName, int userImageUploaded)
	 */
	@Test(groups = {"memberService"})
	public void updateMemberUserImageUploadedWithValidInput(){
		int tenantID = 1;
		int memberID = 1;
		String userName = "userName";
		int userImageUploaded = 1;

		when(memberDaoMock.updateMemberUserImageUploaded(tenantID,memberID,userImageUploaded)).thenReturn(5);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

		assertEquals(((MemberServiceImpl)memberService).updateMemberUserImageUploaded(tenantID,memberID,userName,userImageUploaded),5);
	}

	@Test(groups = {"memberService"})
	public void updateMemberUserImageUploadedWithInvalidUserID(){
		int tenantID = 1;
		int memberID = 0;
		String userName = "userName";
		int userImageUploaded = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.updateMemberUserImageUploaded(tenantID,memberID,userImageUploaded)).thenThrow(dae);

		assertEquals(((MemberServiceImpl)memberService).updateMemberUserImageUploaded(tenantID,memberID,userName,userImageUploaded),0);
	}

	@Test(groups = {"memberService"})
	public void updateMemberUserImageUploadedWithInvalidUserName(){
		int tenantID = 1;
		int memberID = 1;
		String userName = null;
		int userImageUploaded = 1;

		when(memberDaoMock.updateMemberUserImageUploaded(tenantID,memberID,userImageUploaded)).thenReturn(1);
		when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

        assertEquals(((MemberServiceImpl)memberService).updateMemberUserImageUploaded(tenantID,memberID,userName,userImageUploaded),1);
	}

    /**
     *
     * int updateAllMembersLastModifiedDateExt(int tenantID, String emptyDateStr)
     */
    @Test(groups = {"memberService"})
    public void updateAllMembersLastModifiedDateExtWithValidInput(){
        int tenantID = 1;
        String emptyDateStr = "emptyDateStr";

        when(memberDaoMock.updateAllMembersLastModifiedDateExt(tenantID,emptyDateStr)).thenReturn(5);

        assertEquals(memberService.updateAllMembersLastModifiedDateExt(tenantID,emptyDateStr),5);
    }

    @Test(groups = {"memberService"})
    public void updateAllMembersLastModifiedDateExtWithInvalidInput(){
        int tenantID = 0;
        String emptyDateStr = "";

        DataAccessException dae = new DataAccessException("dae") {};
        when(memberDaoMock.updateAllMembersLastModifiedDateExt(tenantID,emptyDateStr)).thenThrow(dae);

        assertEquals(memberService.updateAllMembersLastModifiedDateExt(tenantID,emptyDateStr),0);
    }

    /**
     *
     * int enableUser(int tenantID, int memberID, String userName, int enable)
     */
    @Test(groups = {"memberService"})
    public void enableUserWithValidInput(){
        int tenantID = 1;
        int memberID = 1;
        String userName = "userName";
        int enable = 1;

        when(memberDaoMock.enableUser(tenantID,memberID,enable)).thenReturn(1);
        when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

        assertEquals(memberService.enableUser(tenantID,memberID,userName,enable),1);
    }

    @Test(groups = {"memberService"})
    public void enableUserWithValidInvalidTenantID(){
        int tenantID = 0;
        int memberID = 1;
        String userName = "userName";
        int enable = 1;

        DataAccessException dae = new DataAccessException("dae") {};
        when(memberDaoMock.enableUser(tenantID,memberID,enable)).thenThrow(dae);

        assertEquals(memberService.enableUser(tenantID,memberID,userName,enable),0);
    }

    @Test(groups = {"memberService"})
    public void enableUserWithEmptyUserName(){
        int tenantID = 1;
        int memberID = 1;
        String userName = "";
        int enable = 1;

        when(memberDaoMock.enableUser(tenantID,memberID,enable)).thenReturn(1);
        when(memberDaoMock.updateMember(tenantID,userName)).thenReturn(0);

        assertEquals(memberService.enableUser(tenantID,memberID,userName,enable),1);
    }

    /**
     *
     * void updateMemberPreProcessing(int tenantId, int memberId, Member modifiedMember)
     */

    /**
     *
     * void updateMemberPostProcessing(int tenantID, int memberID, Member member)
     */

    /**
     *
     * int insertMember(Member member)
     */
    @Test(groups = {"memberService"})
    public void insertMemberWithValidMember(){
        Member member = new Member();

        member.setUsername("member");
        TenantContext.setTenantId(1);
        when(memberDaoMock.insertMember(1,member)).thenReturn(1);
        when(memberDaoMock.updateMember(1,"member")).thenReturn(0);

        assertEquals(memberService.insertMember(member),1);
    }

    @Test(groups = {"memberService"})
    public void insertMemberWithInvalidMember(){
        Member member = new Member();

        member.setUsername("");
        TenantContext.setTenantId(1);
        when(memberDaoMock.insertMember(1,member)).thenReturn(0);
        when(memberDaoMock.updateMember(1,"member")).thenReturn(0);

        assertEquals(memberService.insertMember(member),0);
    }

    /**
     *
     * int deleteMember(int tenantID, int memberID)
     */
    @Test(groups = {"memberService"})
    public void deleteMemberWithInvalidMemberID(){}

    /**
     *
     * private void deleteImage(String thumbNailLocation2, int tenantID, int memberID)
     */

    /**
     *
     * List<MemberRoles> getMemberRoles()
     */
    @Test(groups = {"memberService"})
    public void getMemberRoles(){
        List<MemberRoles> list = new ArrayList<>();

        MemberRoles mr1 = new MemberRoles();
        list.add(mr1);
        when(memberDaoMock.getMemberRoles()).thenReturn(list);

        assertEquals(memberService.getMemberRoles().get(0),mr1);
    }

	/**
	 *
	 * List<MemberRoles> getSamlMemberRoles()
	 */
	@Test(groups = {"memberService"})
	public void getSamlMemberRoles(){
		List<MemberRoles> list = new ArrayList<>();

		MemberRoles mr1 = new MemberRoles();
		list.add(mr1);

		when(memberDaoMock.getSamlMemberRoles()).thenReturn(list);

		assertEquals(memberService.getSamlMemberRoles(),list);
	}

	/**
	 *
	 * MemberRoles getMemberRoleByRoleId(int roleID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberRoyeByRoleIdWithValidRoleID(){
		int roleID = 1;

		List<MemberRoles> list = new ArrayList<>();

		MemberRoles mr1 = new MemberRoles();
		MemberRoles mr2 = new MemberRoles();
		mr1.setRoleID(2);
		mr2.setRoleID(1);
		list.add(mr1);
		list.add(mr2);

		when(memberDaoMock.getAllMemberRoles()).thenReturn(list);

		assertEquals(memberService.getMemberRoleByRoleId(roleID),mr2);
	}

    @Test(groups = {"memberService"})
    public void getMemberRoyeByRoleIdWithInvalidRoleID(){
        int roleID = 3;

        List<MemberRoles> list = new ArrayList<>();

        MemberRoles mr1 = new MemberRoles();
        MemberRoles mr2 = new MemberRoles();
        mr1.setRoleID(2);
        mr2.setRoleID(1);
        list.add(mr1);
        list.add(mr2);

        when(memberDaoMock.getAllMemberRoles()).thenReturn(list);

        assertEquals(memberService.getMemberRoleByRoleId(roleID),null);
    }

    /**
     *
     * Long getCountMemberRoles()
     */
    @Test(groups = {"memberService"})
    public void getCountMemberRoles(){
        Long number = new Long(1);

        when(memberDaoMock.getCountMemberRoles()).thenReturn(number);

        assertEquals(memberService.getCountMemberRoles(),number);
    }

    /**
     *
     * Long getCountSamlMemberRoles()
     */
    @Test(groups = {"memberService"})
    public void getCountSamlMemberRoles(){
        Long number = new Long(1);

        when(memberDaoMock.getCountSamlMemberRoles()).thenReturn(number);

        assertEquals(memberService.getCountSamlMemberRoles(),number);
    }

    /**
     *
     * MemberRoles getMemberRoleByName(String roleName)
     */
    @Test(groups = {"memberService"})
    public void getMemberRoleByNameWithValidRoleName(){
        String roleName = "roleName";

        TenantContext.setTenantId(1);
        MemberRoles memberRoles = new MemberRoles();

        when(memberDaoMock.getMemberRoleByName(1,"roleName")).thenReturn(memberRoles);

        assertEquals(memberService.getMemberRoleByName(roleName),memberRoles);
    }

    @Test(groups = {"memberService"})
    public void getMemberRoleByNameWithInvalidRoleName(){
        String roleName = "";

        TenantContext.setTenantId(1);

        DataAccessException dae = new DataAccessException("dae") {};
        when(memberDaoMock.getMemberRoleByName(1,"")).thenThrow(dae);

        assertEquals(memberService.getMemberRoleByName(roleName),null);
    }

    /**
     *
     * boolean isMemberExistForUserName(String userName, int memberID)
     */
    @Test(groups = {"memberService"})
    public void isMemberExistForUserNameWithValidInput(){
        String userName = "userName";
        int memberID = 1;

        TenantContext.setTenantId(1);
        when(memberDaoMock.isMemberExistForUserName(1,"userName",1)).thenReturn(true);

        assertEquals(memberService.isMemberExistForUserName(userName,memberID),true);
    }

    @Test(groups = {"memberService"})
    public void isMemberExistForUserNameWithInvalidInput(){
        String userName = "";
        int memberID = 0;

        TenantContext.setTenantId(1);
        when(memberDaoMock.isMemberExistForUserName(1,"userName",1)).thenReturn(false);

        assertEquals(memberService.isMemberExistForUserName(userName,memberID),false);
    }

    /**
     *
     * boolean isSuperExistForUserName(String userName)
     */
    @Test(groups = {"memberService"})
    public void isSuperExistForUserNameWithValidInput(){
        String userName = "userName";

        TenantContext.setTenantId(1);
        when(memberDaoMock.isMemberExistForUserName(1,"userName",0)).thenReturn(true);

        assertEquals(memberService.isSuperExistForUserName(userName),true);
    }

    @Test(groups = {"memberService"})
    public void isSuperExistForUserNameWithInvalidInput(){
        String userName = "";

        TenantContext.setTenantId(1);
        when(memberDaoMock.isMemberExistForUserName(1,"userName",0)).thenReturn(false);

        assertEquals(memberService.isSuperExistForUserName(userName),false);
    }

    /**
     *
     * isMemberActive(int memberID)
     */
    @Test(groups = {"memberService"})
    public void isMemberActiveWithValidMemberID(){
        int memberID = 1;

        when(memberDaoMock.isMemberActive(memberID)).thenReturn(true);

        assertEquals(memberService.isMemberActive(memberID),true);
    }

    @Test(groups = {"memberService"})
    public void isMemberActiveWithInvalidMemberID(){
        int memberID = 0;

        when(memberDaoMock.isMemberActive(memberID)).thenReturn(false);

        assertEquals(memberService.isMemberActive(memberID),false);
    }

    /**
     *
     * isMemberAllowedToParticipate(int memberID)
     */
    @Test(groups = {"memberService"})
    public void isMemberAllowedToParticipateWithValidMemberID(){
        int memberID = 1;

        when(memberDaoMock.isMemberAllowedToParticipate(memberID)).thenReturn(true);

        assertEquals(memberService.isMemberAllowedToParticipate(memberID),true);
    }

    @Test(groups = {"memberService"})
    public void isMemberAllowedToParticipateWithInvalidMemberID(){
        int memberID = 0;

        when(memberDaoMock.isMemberAllowedToParticipate(memberID)).thenReturn(false);

        assertEquals(memberService.isMemberAllowedToParticipate(memberID),false);
    }

    /**
     *
     * List<SpeedDial> getSpeedDial(int memberID, SpeedDialFilter filter)
     */
    @Test(groups = {"memberService"})
    public void getSpeedDialWithValidInput(){
        int memberID = 1;
        SpeedDialFilter filter = new SpeedDialFilter();

        List<SpeedDial> rc = new ArrayList<>();
        SpeedDial speedDial = new SpeedDial();
        rc.add(speedDial);
        TenantContext.setTenantId(1);

        when(memberDaoMock.getSpeedDial(1,memberID,filter)).thenReturn(rc);

        assertEquals(memberService.getSpeedDial(memberID,filter),rc);
    }

    @Test(groups = {"memberService"})
    public void getSpeedDialWithInvalidInput(){
        int memberID = 0;
        SpeedDialFilter filter = new SpeedDialFilter();

        TenantContext.setTenantId(1);

        when(memberDaoMock.getSpeedDial(1,memberID,filter)).thenReturn(null);

        assertEquals(memberService.getSpeedDial(memberID,filter),null);
    }

    /**
     *
     * Long getCountSpeedDial(int memberID)
     */
    @Test(groups = {"memberService"})
    public void getCountSpeedDialWithValidMemberID(){
        int memberID = 1;

        TenantContext.setTenantId(1);
        Long num = new Long(5);

        when(memberDaoMock.getCountSpeedDial(1,1)).thenReturn(num);

        assertEquals(memberService.getCountSpeedDial(memberID),num);
    }

    @Test(groups = {"memberService"})
    public void getCountSpeedDialWithInvalidMemberID(){
        int memberID = 0;

        TenantContext.setTenantId(1);
        Long num = new Long(0);

        when(memberDaoMock.getCountSpeedDial(1,0)).thenReturn(num);

        assertEquals(memberService.getCountSpeedDial(memberID),num);
    }

    /**
     *
     * int addSpeedDialEntry(int memberID, int roomID)
     */
    @Test(groups = {"memberService"})
    public void addSpeedDialEntryWithValidInput(){
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.addSpeedDialEntry(memberID,roomID)).thenReturn(1);

        assertEquals(memberService.addSpeedDialEntry(memberID,roomID),1);
    }

    @Test(groups = {"memberService"})
    public void addSpeedDialEntryWithInvalidInput() {
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.addSpeedDialEntry(memberID,roomID)).thenReturn(0);

        assertEquals(memberService.addSpeedDialEntry(memberID,roomID),0);
    }

    /**
     *
     * int removeSpeedDialEntry(int memberID, int roomID)
     */
    @Test(groups = {"memberService"})
    public void removeSpeedDialEntryWithValidInput(){
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.removeSpeedDialEntry(memberID,roomID)).thenReturn(1);

        assertEquals(memberService.removeSpeedDialEntry(memberID,roomID),1);
    }

    @Test(groups = {"memberService"})
    public void removeSpeedDialEntryWithInvalidInput(){
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.removeSpeedDialEntry(memberID,roomID)).thenReturn(0);

        assertEquals(memberService.removeSpeedDialEntry(memberID,roomID),0);
    }

    /**
     *
     * boolean isInSpeedDialEntry(int memberID, int roomID)
     */
    @Test(groups = {"memberService"})
    public void isInSpeedDialEntryWithValidInput(){
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.isInSpeedDialEntry(memberID,roomID)).thenReturn(true);

        assertEquals(memberService.isInSpeedDialEntry(memberID,roomID),true);
    }

    @Test(groups = {"memberService"})
    public void isInSpeedDialEntryWithInvalidInput(){
        int memberID = 1;
        int roomID = 1;

        when(memberDaoMock.isInSpeedDialEntry(memberID,roomID)).thenReturn(false);

        assertEquals(memberService.isInSpeedDialEntry(memberID,roomID),false);
    }

    /**
     *
     * int updateMemberPassword(Member member)
     */
    @Test(groups = {"memberService"})
    public void updateMemberPasswordWithImportUsedEqualsOne(){
        Member member = new Member();

        member.setImportedUsed(1);

        assertEquals(memberService.updateMemberPassword(member),-2);
    }

    /**
     *
     * int updateSuperPassword(Member member)
     */
    @Test(groups = {"memberService"})
    public void updateSuperPasswordWithImportUsedEqualsOne(){
        Member member = new Member();

        member.setImportedUsed(1);

        assertEquals(memberService.updateSuperPassword(member),-2);
    }

    /**
     *
     * int updateMemberLanguage(Member member)
     */
    @Test(groups = {"memberService"})
    public void updateMemberLanguageWithValidMember(){
        Member member = new Member();

        TenantContext.setTenantId(1);
        when(memberDaoMock.updateMemberLanguage(1,member)).thenReturn(5);

        assertEquals(memberService.updateMemberLanguage(member),5);
    }

    @Test(groups = {"memberService"})
    public void updateMemberLanguageWithInvalidMember(){
        Member member = new Member();

        TenantContext.setTenantId(1);
        when(memberDaoMock.updateMemberLanguage(1,member)).thenReturn(0);

        assertEquals(memberService.updateMemberLanguage(member),0);
    }

    /**
     *
     * int updateMemberDefaultEndpointGUID(Member member)
     */
    @Test(groups = {"memberService"})
    public void updateMemberDefaultEndpointGUIDWithValidMember(){
        Member member = new Member();

        TenantContext.setTenantId(1);
        when(memberDaoMock.updateMemberLanguage(1,member)).thenReturn(1);

        assertEquals(memberService.updateMemberLanguage(member),1);
    }

    @Test(groups = {"memberService"})
    public void updateMemberDefaultEndpointGUIDWithInvalidMember(){
        Member member = new Member();

        TenantContext.setTenantId(1);
        when(memberDaoMock.updateMemberLanguage(1,member)).thenReturn(0);

        assertEquals(memberService.updateMemberLanguage(member),0);
    }

    /**
     *
     * int updateLegacy(int tenantId, int memberID, Member member)
     */
    @Test(groups = {"memberService"})
    public void updateLegacyWithValidInput(){
        int tenantId = 1;
        int memberId = 1;
        Member member = new Member();

        TenantContext.setTenantId(1);
        when(memberDaoMock.updateLegacy(1,memberId,member)).thenReturn(1);

        assertEquals(memberService.updateLegacy(tenantId,memberId,member),1);
    }

    @Test(groups = {"memberService"})
    public void updateLegacyWithInvalidInput(){
        int tenantId = 1;
        int memberId = 0;
        Member member = new Member();

        TenantContext.setTenantId(1);
        DataAccessException dae = new DataAccessException("dae") {};

        when(memberDaoMock.updateLegacy(1,memberId,member)).thenThrow(dae);

        assertEquals(memberService.updateLegacy(tenantId,memberId,member),0);
    }

    /**
     *
     * int updateMemberMode(int memberID, int modeID)
     */
    @Test(groups = {"memberService"})
    public void updateMemberModeWithValidInput(){
        int memberID = 1;
        int modeID = 1;

        when(memberDaoMock.updateMemberMode(memberID,modeID)).thenReturn(1);

        assertEquals(memberService.updateMemberMode(memberID,modeID),1);
    }

    @Test(groups = {"memberService"})
    public void updateMemberModeWithInvalidInput(){
        int memberID = 1;
        int modeID = 1;

        when(memberDaoMock.updateMemberMode(memberID,modeID)).thenReturn(0);

        assertEquals(memberService.updateMemberMode(memberID,modeID),0);
    }

	/**
	 *
	 * List<Entity> getContacts(int memberID, EntityFilter filter)
	 */
	@Test(groups = {"memberService"})
	public void getContactsWithValidInput(){
		int memberID = 1;
		EntityFilter filter = new EntityFilter();

		TenantContext.setTenantId(1);
		List<Entity> rc = new ArrayList<>();
		List<Integer> canCallTenantIds = new ArrayList<>();

		when(memberDaoMock.getContacts(1,memberID,filter, canCallTenantIds)).thenReturn(rc);

		assertEquals(memberService.getContacts(memberID,filter),rc);
	}

	@Test(groups = {"memberService"})
	public void getContactsWithInvalidInput(){
		int memberID = 0;
		EntityFilter filter = new EntityFilter();

		TenantContext.setTenantId(1);
		List<Integer> canCallTenantIds = new ArrayList<>();
		when(memberDaoMock.getContacts(1,memberID, filter, canCallTenantIds)).thenReturn(null);

		assertEquals(memberService.getContacts(memberID,filter),null);
	}

	/**
	 *
	 * Long getCountContacts(int memberID, EntityFilter filter)
	 */
	@Test(groups = {"memberService"})
	public void getCountContactsWithValidInput(){
		int memberID = 1;

		TenantContext.setTenantId(1);
		Entity rc = new Entity();

		when(memberDaoMock.getContact(1,memberID)).thenReturn(rc);

		assertEquals(memberService.getContact(memberID),rc);
	}

	@Test(groups = {"memberService"})
	public void getCountContactsWithInvalidInput(){
		int memberID = 0;

		TenantContext.setTenantId(1);

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getContact(0,memberID)).thenThrow(dae);

		assertEquals(memberService.getContact(memberID),null);
	}

	/**
	 *
	 * int getJoinedConferenceRoomId(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getJoinedConferenceRoomIdWithValidMemberID(){
		int memberID = 1;
		int roomID = 1;

		when(memberDaoMock.getJoinedConferenceRoomId(memberID)).thenReturn(roomID);

		assertEquals(memberService.getJoinedConferenceRoomId(memberID),1);
	}

	@Test(groups = {"memberService"},expectedExceptions = BadCredentialsException.class)
	public void getJoinedConferenceRoomIdWithInvalidMemberID(){
		int memberID = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getJoinedConferenceRoomId(memberID)).thenThrow(dae);

		memberService.getJoinedConferenceRoomId(memberID);
	}

	/**
	 *
	 * RoomIdConferenceType getJoinedConferenceRoomIdConferenceType(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getJoinedConferenceRoomIdConferenceTypeWithValidMemberID(){
		int memberID = 1;
		RoomIdConferenceType roomIdConferenceType = new RoomIdConferenceType();

		when(memberDaoMock.getJoinedConferenceRoomIdConferenceType(memberID)).thenReturn(roomIdConferenceType);

		assertEquals(memberService.getJoinedConferenceRoomIdConferenceType(memberID),roomIdConferenceType);

	}

	@Test(groups = {"memberService"},expectedExceptions = BadCredentialsException.class)
	public void getJoinedConferenceRoomIdConferenceTypeWithInvalidMemberID(){
		int memberID = 1;
		RoomIdConferenceType roomIdConferenceType = new RoomIdConferenceType();

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getJoinedConferenceRoomIdConferenceType(memberID)).thenThrow(dae);

		memberService.getJoinedConferenceRoomIdConferenceType(memberID);

	}

	/**
	 *
	 * Guest getGuest(int guestID)
	 */
	@Test(groups = {"memberService"})
	public void getGusetWithValidGuestID(){
		int guestID = 1;
		Guest rc = new Guest();

		TenantContext.setTenantId(1);

		when(memberDaoMock.getGuest(1,1)).thenReturn(rc);

		assertEquals(memberService.getGuest(guestID),rc);
	}

	@Test(groups = "memberService")
	public void getGusetWithInvalidGuestID(){
		int guestID = 0;

		TenantContext.setTenantId(1);

		when(memberDaoMock.getGuest(1,0)).thenReturn(null);

		assertEquals(memberService.getGuest(guestID),null);
	}

	/**
	 *
	 * String getTenantPrefix()
	 */

	@Test(groups = {"memberService"})
	public void getTenantPrefix(){
		TenantContext.setTenantId(1);
		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("prefix");

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(memberService.getTenantPrefix(),"prefix");
	}

	/**
	 *
	 * int getLicensedSeats()
	 */
	@Test(groups = {"memberService"})
	public void getLicensedSeats(){
		Tenant tenant = new Tenant();
		TenantContext.setTenantId(1);
		tenant.setSeats(1);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(memberService.getLicensedSeats(),1);
	}

	/**
	 *
	 * int getLicensedSeats(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getLicensedSeatsWithValidInput(){
		int tenantId = 2;
		Tenant tenant = new Tenant();
		TenantContext.setTenantId(1);
		tenant.setSeats(1);

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);

		assertEquals(memberService.getLicensedSeats(tenantId),1);
	}

	@Test(groups = {"memberService"})
	public void getLicensedSeatsWithInvalidInput(){
		int tenantId = 0;
		Tenant tenant = new Tenant();
		TenantContext.setTenantId(1);
		tenant.setSeats(1);

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);

		assertEquals(memberService.getLicensedSeats(tenantId),1);
	}

	/**
	 *
	 *  Long getCountSeats()
	 */
	@Test(groups = {"memberService"})
	public void getCountSeats(){
		Long count = new Long(5);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountSeats(1)).thenReturn(count);

		assertEquals(memberService.getCountSeats(),count);
	}

	/**
	 *
	 * Long getCountSeats(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getCountSeatsWithValidInput(){
		Long count = new Long(5);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountSeats(1)).thenReturn(count);

		assertEquals(memberService.getCountSeats(),count);
	}

	@Test(groups = {"memberService"})
	public void getCountSeatsWithInvalidInput(){
		Long count = new Long(0);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountSeats(1)).thenReturn(count);

		assertEquals(memberService.getCountSeats(),count);
	}

	/**
	 *
	 * Long getCountAllSeats()
	 */
	@Test(groups = {"memberService"})
	public void getCountAllSeats(){
		Long number = new Long(5);

		when(memberDaoMock.getCountAllSeats()).thenReturn(number);

		assertEquals(memberService.getCountAllSeats(),number);
	}

	/**
	 *
	 * Long getCountAllInstalls()
	 */
	@Test(groups = {"memberService"})
	public void getCountAllInstalls(){
		Long number = new Long(5);

		when(memberDaoMock.getCountAllInstalls()).thenReturn(number);

		assertEquals(memberService.getCountAllInstalls(),number);
	}

	/**
	 *
	 * int getLicensedPorts()
	 */
	@Test(groups = {"memberService"})
	public void getLicensedPorts(){
		TenantContext.setTenantId(1);
		Tenant tenant = new Tenant();
		tenant.setPorts(3);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(memberService.getLicensedPorts(),3);
	}


	/**
	 *
	 * getLicensedPorts(int tenantId)
	 */
	@Test(groups = {"memberService"})
	public void getLicensedPortsWithValidInput(){
		int tenantId = 1;

		Tenant tenant = new Tenant();
		tenant.setPorts(3);

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);

		assertEquals(memberService.getLicensedPorts(tenantId),3);
	}

	/**
	 *
	 * int getLicensedInstalls()
	 */
	@Test(groups = {"memberService"})
	public void getLicensedInstalls(){
		TenantContext.setTenantId(1);
		Tenant tenant = new Tenant();
		tenant.setInstalls(7);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(memberService.getLicensedInstalls(),7);
	}

	/**
	 *
	 * getLicensedInstalls(int tenantId)
	 */
	@Test(groups = {"memberService"})
	public void getLicensedInstallsWithValidInput(){
		int teantId = 1;

		TenantContext.setTenantId(1);
		Tenant tenant = new Tenant();
		tenant.setInstalls(7);

		when(tenantServiceMock.getTenant(teantId)).thenReturn(tenant);

		assertEquals(memberService.getLicensedInstalls(teantId),7);
	}

	/**
	 *
	 * Long getCountInstalls()
	 */
	@Test(groups = {"memberService"})
	public void getCountInstallsReturningLong(){
		TenantContext.setTenantId(1);
		Tenant tenant = new Tenant();
		tenant.setTenantName("abc");
		Long number = new Long(5);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);
		when(memberDaoMock.getCountInstalls("abc")).thenReturn(number);

		assertEquals(memberService.getCountInstalls(),number);
	}

	/**
	 *
	 * Long getCountInstalls(int tenantId)
	 */
	@Test(groups = {"memberService"})
	public void getCountInstallsWithValidTenantId(){
		int tenantId = 1;

		Long number = new Long(30);
		Tenant tenant = new Tenant();
		tenant.setTenantName("Name");

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);

		when(memberDaoMock.getCountInstalls("Name")).thenReturn(number);

		assertEquals(memberService.getCountInstalls(1),number);
	}

	@Test(groups = {"memberService"})
	public void getCountInstallsWithInvalidTenantId(){
		int tenantId = 0;

		Long number = 0L;

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(null);
		assertEquals(memberService.getCountInstalls(1),number);
	}

	/**
	 *
	 * List<Proxy> getProxies()
	 */
	@Test(groups = {"memberService"})
	public void getProxies(){
		TenantContext.setTenantId(1);
		List<Proxy> list = new ArrayList<>();

		when(serviceDaoMock.getProxies(1)).thenReturn(list);

		assertEquals(memberService.getProxies(),list);
	}

	/**
	 *
	 * Long getCountProxies()
	 */
	@Test(groups = {"memberService"})
	public void getCountProxies(){
		TenantContext.setTenantId(1);
		Long num = new Long(3);

		when(serviceDaoMock.getCountProxies(1)).thenReturn(num);

		assertEquals(memberService.getCountProxies(),num);
	}

	/**
	 *
	 * Proxy getProxyByName(String proxyName)
	 */
	@Test(groups = {"memberService"})
	public void getProxyByNameWithValidInput(){
		String proxyName = "proxyName";
		Proxy proxy = new Proxy();
		TenantContext.setTenantId(1);

		when(serviceDaoMock.getProxyByName(1,proxyName)).thenReturn(proxy);

		assertEquals(memberService.getProxyByName(proxyName),proxy);
	}

	@Test(groups = {"memberService"})
	public void getProxyByNameWithInvalidInput(){
		String proxyName = "proxyName";
		TenantContext.setTenantId(1);

		when(serviceDaoMock.getProxyByName(1,proxyName)).thenReturn(null);

		assertEquals(memberService.getProxyByName(proxyName),null);
	}

	/**
	 *
	 * List<InviteList> getOnlineMembers(InviteListFilter filter)
	 */
	@Test(groups = {"memberService"})
	public void getOnlineMembersWithValidInput(){
		InviteListFilter filter = new InviteListFilter();
		TenantContext.setTenantId(1);

		List<InviteList> list = new ArrayList<>();

		when(memberDaoMock.getOnlineMembers(1,filter)).thenReturn(list);

		assertEquals(memberService.getOnlineMembers(filter),list);
	}

	@Test(groups = {"memberService"})
	public void getOnlineMembersWithInvalidInput(){
		InviteListFilter filter = new InviteListFilter();
		TenantContext.setTenantId(1);

		when(memberDaoMock.getOnlineMembers(1,filter)).thenReturn(null);

		assertEquals(memberService.getOnlineMembers(filter),null);
	}

	/**
	 *
	 * Long getCountOnlineMembers()
	 */
	@Test(groups = {"memberService"})
	public void getCountOnlineMembers(){
		Long num = new Long(5);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountOnlineMembers(1)).thenReturn(num);

		assertEquals(memberService.getCountOnlineMembers(),num);
	}

	/**
	 *
	 * List<Location> getLocationTags()
	 */
	@Test(groups = {"memberService"})
	public void getLocationTags(){
		List<Location> list = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(memberDaoMock.getLocationTags(1)).thenReturn(list);

		assertEquals(memberService.getLocationTags(),list);
	}

	/**
	 *
	 * Long getCountLocationTags()
	 */
	@Test(groups = {"memberService"})
	public void getCountLocationTags(){
		Long number = new Long(5);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountLocationTags(1)).thenReturn(number);

		assertEquals(memberService.getCountLocationTags(),number);
	}

	/**
	 *
	 * Location getLocationInTenantByTag(String locationTag)
	 */
	@Test(groups = {"memberService"})
	public void getLocationInTenantByTagWithValidInput(){
		Location location = new Location();

		TenantContext.setTenantId(1);
		String locationTag = "LocationTag";

		when(memberDaoMock.getLocationInTenantByTag(1,locationTag)).thenReturn(location);

		assertEquals(memberService.getLocationInTenantByTag(locationTag),location);
	}

	@Test(groups = {"memberService"})
	public void getLocationInTenantByTagWithInvalidInput(){
		TenantContext.setTenantId(1);
		String locationTag = "LocationTag";

		when(memberDaoMock.getLocationInTenantByTag(1,locationTag)).thenReturn(null);

		assertEquals(memberService.getLocationInTenantByTag(locationTag),null);
	}

	/**
	 *
	 * getLicensedExecutive()
	 */
	@Test(groups = {"memberService"})
	public void getLicensedExecutive(){
		Tenant rc = new Tenant();
		rc.setExecutives(5);

		TenantContext.setTenantId(1);

		when(tenantServiceMock.getTenant(1)).thenReturn(rc);

		assertEquals(memberService.getLicensedExecutive(),5);
	}

	/**
	 *
	 * int getLicensedExecutive(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getLicensedExecutiveWithValidInput(){
		int tenant = 1;

		Tenant rc = new Tenant();
		rc.setExecutives(10);

		when(tenantServiceMock.getTenant(tenant)).thenReturn(rc);

		assertEquals(memberService.getLicensedExecutive(),10);
	}

	@Test(groups = {"memberService"},expectedExceptions = NullPointerException.class)
	public void getLicensedExecutiveWithInvalidInput(){
		int tenant = 1;

		when(tenantServiceMock.getTenant(1)).thenReturn(null);

		memberService.getLicensedExecutive(tenant);
	}

	/**
	 *
	 * Long getCountExecutives()
	 */
	@Test(groups = {"memberService"})
	public void getCountExcutive(){
		TenantContext.setTenantId(1);

		Long number = new Long(5);

		when(memberDaoMock.getCountExecutives(1)).thenReturn(number);

		assertEquals(memberService.getCountExecutives(),number);
	}

	/**
	 *
	 * Long getCountExecutives(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getCountExcutiveWithValidInput(){
		int tenant = 1;

		Long number = new Long(5);

		when(memberDaoMock.getCountExecutives(1)).thenReturn(number);

		assertEquals(memberService.getCountExecutives(),number);
	}

	@Test(groups = {"memberService"})
	public void getCountExcutiveWithInvalidInput(){
		int tenant = 0;

		when(memberDaoMock.getCountExecutives(tenant)).thenReturn(null);

		assertEquals(memberService.getCountExecutives(tenant),null);
	}

	/**
	 *
	 * Long getCountAllExecutives()
	 */
	@Test(groups = {"memberService"})
	public void getCountAllExcutives(){
		Long number = new Long(5);
		when(memberDaoMock.getCountAllExecutives()).thenReturn(number);

		assertEquals(memberService.getCountAllExecutives(),number);
	}

	/**
	 *
	 * int getLicensedPanorama()
	 */
	@Test(groups = {"memberService"})
	public void getLicensedPanorama(){
		Tenant rc = new Tenant();
		TenantContext.setTenantId(1);
		rc.setPanoramas(3);

		when(tenantServiceMock.getTenant(1)).thenReturn(rc);

		assertEquals(memberService.getLicensedPanorama(),3);
	}

	/**
	 *
	 * int getLicensedPanorama(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getLicensedPanoramaWithValidInput(){
		int tenant = 1;

		Tenant rc = new Tenant();
		rc.setPanoramas(10);

		when(tenantServiceMock.getTenant(tenant)).thenReturn(rc);

		assertEquals(memberService.getLicensedPanorama(tenant),10);
	}

	@Test(groups = {"memberService"},expectedExceptions = NullPointerException.class)
	public void getLicensedPanoramaWithInvalidInput() {
		int tenant = 1;

		Tenant rc = new Tenant();
		rc.setPanoramas(10);

		when(tenantServiceMock.getTenant(tenant)).thenReturn(null);

		memberService.getLicensedPanorama(tenant);
	}

	/**
	 *
	 * Long getCountPanoramas()
	 */
	@Test(groups = {"memberService"})
	public void getCountPanoramas(){
		Long number = new Long(5);
		TenantContext.setTenantId(1);

		when(memberDaoMock.getCountPanoramas(1)).thenReturn(number);

		assertEquals(memberService.getCountPanoramas(),number);
	}

	/**
	 *
	 * Long getCountPanoramas(int tenant)
	 */
	@Test(groups = {"memberService"})
	public void getCountPanoramasWithValidInput(){
		int tenant = 1;

		Long number = new Long(5);

		when(memberDaoMock.getCountPanoramas(tenant)).thenReturn(number);

		assertEquals(memberService.getCountPanoramas(tenant),number);
	}

	@Test(groups = {"memberService"})
	public void getCountPanoramasWithInvalidInput(){
		int tenant = 0;

		when(memberDaoMock.getCountPanoramas(tenant)).thenReturn(null);

		assertEquals(memberService.getCountPanoramas(tenant),null);
	}

	/**
	 *
	 * Long getCountAllPanoramas()
	 */
	@Test(groups = {"memberService"})
	public void getCountAllPanoramas(){
		Long number = new Long(5);

		when(memberDaoMock.getCountAllPanoramas()).thenReturn(number);

		assertEquals(memberService.getCountAllPanoramas(),number);
	}

	/**
	 *
	 * int addLegacy(Member member)
	 */
	@Test(groups = {"memberService"})
	public void addLegacyWithValidInput(){
		TenantContext.setTenantId(1);
		Member member = new Member();

		int id = 1;

		when(memberDaoMock.insertMember(1,member)).thenReturn(id);

		assertEquals(memberService.addLegacy(member),id);
	}

	@Test(groups = {"memberService"})
	public void addLegacyWithInvalidInput(){
		TenantContext.setTenantId(1);
		Member member = new Member();
		int id = 0;

		when(memberDaoMock.insertMember(1,member)).thenReturn(id);

		assertEquals(memberService.addLegacy(member),id);
	}

	/**
	 *
	 * String getLocationTag(int locationTagID)
	 */
	@Test(groups = {"memberService"})
	public void getLocationTagWithValidInput(){
		TenantContext.setTenantId(1);
		int locationTagID = 0;
		String tag = "tag";

		when(memberDaoMock.getLocationTag(locationTagID)).thenReturn(tag);

		assertEquals(memberService.getLocationTag(locationTagID),tag);
	}

	@Test(groups = {"memberService"})
	public void getLocationTagWithInvalidInput(){
		TenantContext.setTenantId(1);
		int locationTagID = 0;

		when(memberDaoMock.getLocationTag(locationTagID)).thenReturn(null);

		assertEquals(memberService.getLocationTag(locationTagID),null);
	}

	/**
	 *
	 *  Member getInviteeDetails(int inviteeRoomId)
	 */
	@Test(groups = {"memberService"})
	public void getInviteeDetailsWithValidInput(){
		int inviteeRoomId = 1;
		TenantContext.setTenantId(1);

		Member member = new Member();
		when(memberDaoMock.getInviteeDetails(inviteeRoomId,1)).thenReturn(member);

		assertEquals(memberService.getInviteeDetails(inviteeRoomId),member);
	}

	@Test(groups = {"memberService"})
	public void getInviteeDetailsWithInvalidInput(){
		int inviteeRoomId = 0;
		TenantContext.setTenantId(1);

		when(memberDaoMock.getInviteeDetails(inviteeRoomId,1)).thenReturn(null);

		assertEquals(memberService.getInviteeDetails(inviteeRoomId),null);
	}

	/**
	 *
	 * Member getInviterDetails(int inviterMemberId)
	 */
	@Test(groups = {"memberService"})
	public void getInviteeDetailsWithValidMemberId(){
		int inviterMemberId = 1;
		TenantContext.setTenantId(1);

		Member member = new Member();
		when(memberDaoMock.getInviteeDetails(inviterMemberId,1)).thenReturn(member);

		assertEquals(memberService.getInviteeDetails(inviterMemberId),member);
	}

	@Test(groups = {"memberService"})
	public void getInviteeDetailsWithInvalidMemberId(){
		int inviterMemberId = 0;
		TenantContext.setTenantId(1);

		when(memberDaoMock.getInviteeDetails(inviterMemberId,1)).thenReturn(null);

		assertEquals(memberService.getInviteeDetails(inviterMemberId),null);
	}

	/**
	 *
	 * Entity getOwnRoomDetails(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getOwnRoomDetailsWithValidMemberID(){
		int memberID = 5;

		TenantContext.setTenantId(1);
		Entity entity = new Entity();

		when(memberDaoMock.getOwnRoomDetails(1,memberID)).thenReturn(entity);

		assertEquals(memberService.getOwnRoomDetails(memberID),entity);
	}

	@Test(groups = {"memberService"})
	public void getOwnRoomDetailsWithInvalidMemberID(){
		int memberID = 0;

		TenantContext.setTenantId(1);
		Entity entity = new Entity();

		when(memberDaoMock.getOwnRoomDetails(1,memberID)).thenReturn(null);

		assertEquals(memberService.getOwnRoomDetails(memberID),null);
	}

	/**
	 *
	 * Member getMemberByNameAndGatewayController(String username, String gatewayControllerDns)
	 */
	/*
	@Test(groups = {"memberService"})
	public void getMemberByNameAndGatewayControllerWithValidUserNameAndValidGatewayControllerDNS(){
		String username = "username";
		String gatewayControllerDns = "gatewayControllerDns";
		Member member = new Member();

		when(memberDaoMock.getMemberByNameAndGatewayController(username,gatewayControllerDns)).thenReturn(member);

		assertEquals(memberService.getMemberByNameAndGatewayController(username,gatewayControllerDns),member);
	}*/

	/*@Test(groups = {"memberService"})
	public void getMemberByNameAndGatewayControllerWithInvalidInput(){
		String username = "username";
		String gatewayControllerDns = "gatewayControllerDns";

		when(memberDaoMock.getMemberByNameAndGatewayController(username,gatewayControllerDns)).thenReturn(null);

		assertEquals(memberService.getMemberByNameAndGatewayController(username,gatewayControllerDns),null);
	}*/

	/**
	 *
	 * String getMemberPassword(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberPasswordWithValidMemberID(){
		int memberID = 1;

		String pwd = "pwd";
		when(memberDaoMock.getMemberPassword(memberID)).thenReturn("pwd");

		assertEquals(memberService.getMemberPassword(memberID),"pwd");
	}

	@Test(groups = {"memberService"})
	public void getMemberPasswordWithInvalidMemberID(){
		int memberID = 0;

		when(memberDaoMock.getMemberPassword(memberID)).thenReturn(null);

		assertEquals(memberService.getMemberPassword(memberID),null);
	}

	/**
	 *
	 * int resetLoginFailureCount(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void resetLoginFailureCountWithValidInput() {
		int memberID = 1;

		when(memberDaoMock.resetLoginFailureCount(memberID)).thenReturn(5);

		assertEquals(memberService.resetLoginFailureCount(memberID),5);
	}

	@Test(groups = {"memberService"})
	public void resetLoginFailureCountWithInvalidInput() {
		int memberID = 1;

		when(memberDaoMock.resetLoginFailureCount(memberID)).thenReturn(0);

		assertEquals(memberService.resetLoginFailureCount(memberID),0);
	}

	/**
	 *
	 * int incrementLoginFailureCount(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void incrementLoginFailureCountWithValidInput(){
		int memberID = 1;

		when(memberDaoMock.incrementLoginFailureCount((memberID))).thenReturn(5);

		assertEquals(memberService.incrementLoginFailureCount(memberID),5);
	}

	@Test(groups = {"memberService"})
	public void incrementLoginFailureCountWithInvalidInput(){
		int memberID = 0;

		when(memberDaoMock.incrementLoginFailureCount((memberID))).thenReturn(0);

		assertEquals(memberService.incrementLoginFailureCount(memberID),0);
	}

	/**
	 *
	 * int getMemberID(String userName, int tenantID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberIDWithValidUserNameAndValidTenantID(){
		String userName = "userName";
		int tenantID = 1;

		when(memberDaoMock.getMemberID(userName,tenantID)).thenReturn(10);

		assertEquals(memberService.getMemberID(userName,tenantID),10);
	}

	@Test(groups = {"memberService"})
	public void getMemberIDWithInvalidInput(){
		String userName = "";
		int tenantID = 0;

		DataAccessException dataAccessException = new DataAccessException("dae") {};

		when(memberDaoMock.getMemberID(userName,tenantID)).thenThrow(dataAccessException);

		assertEquals(memberService.getMemberID(userName,tenantID),0);
	}

	/**
	 *
	 * int getMemberLoginFailureCount(String userName, int tenantID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberLoginFailureCountWithValidUserNameAndValidTenantID(){
		String userName = "userName";
		int tenantID = 1;

		when(memberDaoMock.getMemberLoginFailureCount(userName,tenantID)).thenReturn(1);

		assertEquals(memberService.getMemberLoginFailureCount(userName,tenantID),1);
	}

	@Test(groups = {"memberService"})
	public void getMemberLoginFailureCountWithValidUserNameAndInvalidTenantID(){
		String userName = "userName";
		int tenantID = 0;

		DataAccessException dataAccessException = new DataAccessException("dae") {};

		when(memberDaoMock.getMemberLoginFailureCount(userName,0)).thenThrow(dataAccessException);
		when(memberDaoMock.getMemberLoginFailureCount(userName,1)).thenReturn(-5);

		assertEquals(memberService.getMemberLoginFailureCount(userName,tenantID),-5);
	}

	@Test(groups = {"memberService"})
	public void getMemberLoginFailureCountWithInvalidUserName(){
		String userName = "";
		int tenantID = 2;

		DataAccessException dataAccessException = new DataAccessException("dae") {};

		when(memberDaoMock.getMemberLoginFailureCount(userName,2)).thenThrow(dataAccessException);
		when(memberDaoMock.getMemberLoginFailureCount(userName,1)).thenThrow(dataAccessException);

		assertEquals(memberService.getMemberLoginFailureCount(userName,tenantID),-1);
	}

	/**
	 *
	 *
	 * int updateUserAsLocked(int memberID, int tenantID, String userName )
	 */
	@Test(groups = {"memberService"})
	public void updateUserAsLockedWithValidInput(){
		int memberID = 1;
		int tenantID = 1;
		String userName = "userName";

		when(memberDaoMock.updateMember(tenantID,"username")).thenReturn(0);
		when(memberDaoMock.updateUserAsLocked(memberID)).thenReturn(5);

		assertEquals(memberService.updateUserAsLocked(memberID,tenantID,userName),5);
	}

	@Test(groups = {"memberService"})
	public void updateUserAsLockedWithEmptyUserName() {
		int memberID = 1;
		int tenantID = 1;
		String userName = "";

		when(memberDaoMock.updateMember(tenantID,null)).thenReturn(0);
		when(memberDaoMock.updateUserAsLocked(memberID)).thenReturn(1);

		assertEquals(memberService.updateUserAsLocked(memberID,tenantID,userName),1);
	}

	@Test(groups = {"memberService"})
	public void updateUserAsLockedWithInvalidInput() {
		int memberID = 0;
		int tenantID = 0;
		String userName = "";

		when(memberDaoMock.updateMember(tenantID,null)).thenReturn(0);
		when(memberDaoMock.updateUserAsLocked(memberID)).thenReturn(0);

		assertEquals(memberService.updateUserAsLocked(memberID,tenantID,userName),0);
	}

	/**
	 *
	 * int markMembersAsInactive(int inactiveDaysLimit)
	 */
	@Test(groups = {"memberService"})
	public void markMembersAsInactiveWithValidInactiveDaysLimit(){
		int inactiveDaysLimit  = 1;

		when(memberDaoMock.markMembersAsInactive(inactiveDaysLimit)).thenReturn(1);

		assertEquals(memberService.markMembersAsInactive(inactiveDaysLimit),1);
	}

	@Test(groups = {"memberService"})
	public void markMembersAsInactiveWithInvalidInactiveDaysLimit(){
		int inactiveDaysLimit  = 0;

		when(memberDaoMock.markMembersAsInactive(inactiveDaysLimit)).thenReturn(0);

		assertEquals(memberService.markMembersAsInactive(inactiveDaysLimit),0);
	}

	/**
	 *
	 * long getMemberCreationTime(int memberID, int tenantID)
	 */
	@Test(groups = {"memberService"})
	public void getMemberCreationTimeWithValidInput(){
		int memberID = 1;
		int tenantID = 1;

		long num = 6;
		when(memberDaoMock.getMemberCreationTime(memberID,tenantID)).thenReturn(num);

		assertEquals(memberService.getMemberCreationTime(tenantID,memberID),num);
	}

	@Test(groups = {"memberService"})
	public void getMemberCreationTimeWithInvalidInput(){
		int memberID = 0;
		int tenantID = 0;

		long num = 0;
		when(memberDaoMock.getMemberCreationTime(memberID,tenantID)).thenReturn(num);

		assertEquals(memberService.getMemberCreationTime(tenantID,memberID),num);
	}


	/**
	 *
	 * int lockUserPasswordExpiry(int passwordExpiryDaysCount)
	 */
	@Test(groups = {"memberService"})
	public void lockUserPasswordExpiryWithValidInput(){
		int passwordExiryDaysCount = 1;

		when(memberDaoMock.lockUserPasswordExpiry(passwordExiryDaysCount)).thenReturn(1);

		assertEquals(memberService.lockUserPasswordExpiry(passwordExiryDaysCount),1);
	}

	@Test(groups = {"memberService"})
	public void lockUserPasswordExpiryWithInvalidInput(){
		int passwordExiryDaysCount = 0;

		when(memberDaoMock.lockUserPasswordExpiry(passwordExiryDaysCount)).thenReturn(0);

		assertEquals(memberService.lockUserPasswordExpiry(passwordExiryDaysCount),0);
	}

	/**
	 *
	 * String getUsername(int memberID)
	 */
	@Test(groups = {"memberService"})
	public void getUsernameWithValidMemberID(){
		int memberId = 1;

		when(memberDaoMock.getUsername(memberId)).thenReturn("userName");

		assertEquals(memberService.getUsername(memberId),"userName");
	}

	@Test(groups = {"memberService"})
	public void getUsernameWithInvalidMemberID(){
		int memberId = 1;

		DataAccessException dataAccessException = new DataAccessException("dae") {};

		when(memberDaoMock.getUsername(memberId)).thenThrow(dataAccessException);

		assertEquals(memberService.getUsername(memberId),null);
	}

	/**
	 *
	 * boolean isValidDisaMemberPassword(String password, int memberID)
	 */
	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan15Leters(){
		String password = "password";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan2Leters(){
		String password = "@@@@@@@@@@@@@@@@@@";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan2UpperCharacters(){
		String password = "A222222222222222222222";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan2lowerCharacters(){
		String password = "A2222A2222222222222222";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan2NumCharacters(){
		String password = "A2AsAssdsdsaaAAAAaaaaaaaa";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordLessThan2NonCharacters(){
		String password = "A2AsAss2dsdsaaAAAAaaaaaaaa";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithPasswordMoreThan3NonCharacters(){
		String password = "A2AsAss2dsd@@aAAAAaaaaaaaa";
		int memberID = 1;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),false);
	}

	@Test(groups = {"memberService"})
	public void isValidDisaMemberPasswordWithValidPasswordAndMemeberIDIs0(){
		String password = "A2AsAss2dsd@@aAAaBCDaa";
		int memberID = 0;

		assertEquals(((MemberServiceImpl)memberService).isValidDisaMemberPassword(password,memberID),true);
	}
	/**
	 *
	 * static boolean isValidLegacyUsername(String legacyUsername)
	 */

	/**
	 *
	 * boolean setProxyOfMember(Member updatingMember, String proxyName)
	 */

	/**
	 *
	 * boolean setGroupOfMember(Member updatingMember, String groupName)
	 */
	@Test(groups = {"memberService"})
	public void setGroupOfMemberWithValipInput(){
		Member updatingMember = new Member();
		String groupName = "groupName";

		Group tenantGroup = new Group();
		tenantGroup.setGroupID(5);
		tenantGroup.setGroupName(groupName);

		when(groupServiceMock.getGroupByName(groupName)).thenReturn(tenantGroup);

		assertEquals(memberService.setGroupOfMember(updatingMember,groupName),true);
	}

	@Test(groups = {"memberService"})
	public void setGroupOfMemberWithInvalipInput(){
		Member updatingMember = new Member();
		String groupName = "groupName";

		Group tenantGroup = new Group();
		tenantGroup.setGroupID(5);
		tenantGroup.setGroupName(groupName);

		DataAccessException dae = new DataAccessException("dae") {};
		when(groupServiceMock.getGroupByName(groupName)).thenThrow(dae);

		assertEquals(memberService.setGroupOfMember(updatingMember,groupName),false);

	}

	/**
	 *
	 * boolean setGroupOfMember(Member updatingMember, String groupName)
	 */

	/**
	 *
	 * Map<Integer, Integer> getPersonalRoomIds(List<Integer> memberIds)
	 */
	@Test(groups = {"memberService"})
	public void getPersonalRoomIdsWithValidInput(){
		List<Integer> memberIds = new ArrayList<>();

		Map<Integer,Integer> memberRoomMap = new HashMap<>();
		memberRoomMap.put(1,1);
		memberRoomMap.put(2,2);

		when(memberDaoMock.getPersonalRoomIds(memberIds)).thenReturn(memberRoomMap);

		assertEquals(memberService.getPersonalRoomIds(memberIds).get(1),memberRoomMap.get(1));
	}

	@Test(groups = {"memberService"})
	public void getPersonalRoomIdsWithInvalidInput(){
		List<Integer> memberIds = new ArrayList<>();

		DataAccessException dae = new DataAccessException("dae") {};

		when(memberDaoMock.getPersonalRoomIds(memberIds)).thenThrow(dae);

		assertEquals(memberService.getPersonalRoomIds(memberIds),null);
	}

	/**
	 *
	 *  Integer getPersonalRoomId(Integer memberId)
	 */
	@Test(groups = {"memberService"})
	public void getPersonalRoomIdWithValidMemberId(){
		Integer memberId = 5;

		when(memberDaoMock.getPersonalRoomId(memberId)).thenReturn(10);

		assertEquals(memberService.getPersonalRoomId(memberId),(Integer)10);
	}

	@Test(groups = {"memberService"})
	public void getPersonalRoomIdWithInvalidMemberId(){
		Integer memberId = 5;

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.getPersonalRoomId(memberId)).thenThrow(dae);

		assertEquals(memberService.getPersonalRoomId(memberId),null);
	}

	/**
	 *
	 * boolean isUserEligibleToCreateUpdateMember(User user, Member member)
	 */

	/**
	 *
	 * int findMembersCount(String query, String queryField, List<Integer> memberTypes)
	 */
	@Test(groups = {"memberService"})
	public void findMembersCountWithValidInput(){
		String query = "query";
		String queryField = "queryField";
		List<Integer> memberTypes = new ArrayList<>();

		List<Integer> allowedTenantIds = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(memberDaoMock.findMembersCount(query,queryField,memberTypes,allowedTenantIds)).thenReturn(5);

		assertEquals(memberService.findMembersCount(query,queryField,memberTypes),5);

	}

	@Test(groups = {"memberService"})
	public void findMembersCountWithInvalidInput(){
		String query = "";
		String queryField = "";
		List<Integer> memberTypes = new ArrayList<>();

		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(null);
		when(memberDaoMock.findMembersCount(query,queryField,memberTypes,null)).thenReturn(0);

		assertEquals(memberService.findMembersCount(query,queryField,memberTypes),0);
	}

	/**
	 *
	 * List<Member> searchMembers(int tenantId, MemberFilter memberFilter)
	 */
	@Test(groups = {"memberService"})
	public void searchMembersWithValidInput(){
		int tenantId = 1;
		MemberFilter memberFilter = new MemberFilter();
		List<Member> members = new ArrayList<>();

		when(memberDaoMock.searchMembers(tenantId,memberFilter)).thenReturn(members);

		assertEquals(memberService.searchMembers(tenantId,memberFilter),members);
	}

	@Test(groups = {"memberService"})
	public void searchMembersWithInvalidInput(){
		int tenantId = 1;
		MemberFilter memberFilter = new MemberFilter();

		DataAccessException dae = new DataAccessException("dae") {};
		when(memberDaoMock.searchMembers(tenantId,memberFilter)).thenThrow(dae);

		assertEquals(memberService.searchMembers(tenantId,memberFilter),null);
	}

	/**
	 *
	 * int countMembers(int tenantId, MemberFilter memberFilter)
	 */
	@Test(groups = {"memberService"})
	public void countMembersWithValidInput(){
		int tenantId = 1;
		MemberFilter memberFilter = new MemberFilter();

		when(memberDaoMock.getMembersCount(tenantId,memberFilter)).thenReturn(5);

		assertEquals(memberService.countMembers(tenantId,memberFilter),5);
	}

	@Test(groups = {"memberService"})
	public void countMembersWithInvalidInput(){
		int tenantId = 1;
		MemberFilter memberFilter = new MemberFilter();

		DataAccessException dae = new DataAccessException("dae") {};

		when(memberDaoMock.getMembersCount(tenantId,memberFilter)).thenThrow(dae);

		assertEquals(memberService.countMembers(tenantId,memberFilter),0);

	}

	/**
	 *
	 * String getThumbNailImage(Integer tenantId, int memberID)
	 */

	/**
	 *
	 * String getDefaultThumbNailImage(Integer tenantId, int memberID)
	 */

	/**
	 *
	 * void uploadUserImage(byte[] thumbNail, int memberID, int tenantId) throws IOException
	 */

	/**
	 *
	 * int updateMemberThumbnailTimeStamp(int memberID, Date thumbnailTimeStamp)
	 */
	@Test(groups = {"memberService"})
	public void updateMemberThumbnailTimeStampWithValidInput(){
		int memberId = 1;
		Date thumbnailTimeStamp = new Date();

		when(memberDaoMock.updateMemberThumbnailTimeStamp(memberId,thumbnailTimeStamp)).thenReturn(1);

		assertEquals(memberService.updateMemberThumbnailTimeStamp(memberId,thumbnailTimeStamp),1);
	}

	@Test(groups = {"memberService"})
	public void updateMemberThumbnailTimeStampWithInvalidInput(){
		int memberId = 0;
		Date thumbnailTimeStamp = new Date();

		when(memberDaoMock.updateMemberThumbnailTimeStamp(memberId,thumbnailTimeStamp)).thenReturn(0);

		assertEquals(memberService.updateMemberThumbnailTimeStamp(memberId,thumbnailTimeStamp),0);
	}

}