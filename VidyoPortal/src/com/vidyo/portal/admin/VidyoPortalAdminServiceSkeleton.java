/**
 * VidyoPortalAdminServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.admin;

import com.vidyo.bo.*;
import com.vidyo.bo.Entity;
import com.vidyo.bo.Group;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.*;
import com.vidyo.service.exceptions.*;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;

import org.apache.axis2.context.MessageContext;
import org.apache.commons.lang.StringUtils;

import java.lang.Exception;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * VidyoPortalAdminServiceSkeleton java skeleton for the axisService
 */
public class VidyoPortalAdminServiceSkeleton implements VidyoPortalAdminServiceSkeletonInterface {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(VidyoPortalAdminServiceSkeleton.class.getName());

	protected final DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private IMemberService member;
	private IGroupService group;
	private IRoomService room;
	private IRouterService router;
	private IConferenceService conference;
	private ISystemService system;
	private LicensingService license;
	private IUserService user;
	private ITenantService tenantService;

	public void setUser(IUserService user) {
		this.user = user;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

	public void setGroup(IGroupService group) {
		this.group = group;
	}

	public void setRoom(IRoomService room) {
		this.room = room;
	}

	public void setRouter(IRouterService router) {
		this.router = router;
	}

	public void setConference(IConferenceService conference) {
		this.conference = conference;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setLicense(LicensingService license) {
		this.license = license;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	private com.vidyo.portal.admin.Member getWSMemberFromBOMember(com.vidyo.bo.Member member) {
		com.vidyo.portal.admin.Member ws_member = new com.vidyo.portal.admin.Member();

		EntityID id = new EntityID();

		id.setEntityID(member.getMemberID());
		ws_member.setMemberID(id);
		ws_member.setDisplayName(member.getMemberName());
		ws_member.setName(member.getUsername());
		ws_member.setDescription(member.getDescription());
		ws_member.setExtension(member.getRoomExtNumber());
		ws_member.setEmailAddress(member.getEmailAddress());
		ws_member.setProxyName(member.getProxyName());

		if (member.getGroupName().equals("")) {
			Group defaultGroup = this.group.getDefaultGroup();

			if (defaultGroup != null) {
				ws_member.setGroupName(defaultGroup.getGroupName());
			} else {
				ws_member.setGroupName("Default");
			}
		} else {
			ws_member.setGroupName(member.getGroupName());
		}

		if (member.getLangID() == 1) {
			ws_member.setLanguage(Language_type0.en);
		} else if (member.getLangID() == 2) {
			ws_member.setLanguage(Language_type0.fr);
		} else if (member.getLangID() == 3) {
			ws_member.setLanguage(Language_type0.ja);
		} else if (member.getLangID() == 4) {
			ws_member.setLanguage(Language_type0.zh_CN);
		} else if (member.getLangID() == 5) {
			ws_member.setLanguage(Language_type0.es);
		} else if (member.getLangID() == 6) {
			ws_member.setLanguage(Language_type0.it);
		} else if (member.getLangID() == 7) {
			ws_member.setLanguage(Language_type0.de);
		} else if (member.getLangID() == 8) {
			ws_member.setLanguage(Language_type0.ko);
		} else if (member.getLangID() == 9) {
			ws_member.setLanguage(Language_type0.pt);
		} else if (member.getLangID() == 10) {
			ws_member.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(member.getTenantID()).getLangCode()));
		} else if (member.getLangID() == 11) {
			ws_member.setLanguage(Language_type0.fi);
		} else if (member.getLangID() == 12) {
			ws_member.setLanguage(Language_type0.pl);
		} else if (member.getLangID() == 13) {
			ws_member.setLanguage(Language_type0.zh_TW);
		} else if (member.getLangID() == 14) {
			ws_member.setLanguage(Language_type0.th);
		} else if (member.getLangID() == 15) {
			ws_member.setLanguage(Language_type0.ru);
		} else if (member.getLangID() == 16) {
			ws_member.setLanguage(Language_type0.tr);
		}

		if (member.getRoleName().equalsIgnoreCase("Admin")) {
			ws_member.setRoleName(RoleName_type0.Admin);
		} else if (member.getRoleName().equalsIgnoreCase("Normal")) {
			ws_member.setRoleName(RoleName_type0.Normal);
		} else if (member.getRoleName().equalsIgnoreCase("Operator")) {
			ws_member.setRoleName(RoleName_type0.Operator);
		} else if (member.getRoleName().equalsIgnoreCase("VidyoRoom")) {
			ws_member.setRoleName(RoleName_type0.VidyoRoom);
		} else if (member.getRoleName().equalsIgnoreCase("Legacy")) {
			ws_member.setRoleName(RoleName_type0.Legacy);
		} else {
			ws_member.setRoleName(RoleName_type0.Normal);
		}

		ws_member.setCreated(new Date((long)member.getMemberCreated() * 1000));
		ws_member.setAllowCallDirect(true);

		if (member.getRoomType().equalsIgnoreCase("Legacy")) {
			ws_member.setAllowPersonalMeeting(false);
		} else {
			ws_member.setAllowPersonalMeeting(!member.getRoomName().equalsIgnoreCase(""));
		}

		return ws_member;
	}

	private com.vidyo.portal.admin.Room getWSRoomFromBORoom(com.vidyo.bo.Room room) {
		com.vidyo.portal.admin.Room ws_room = new com.vidyo.portal.admin.Room();

		EntityID id = new EntityID();

		id.setEntityID(room.getRoomID());
		ws_room.setRoomID(id);
		ws_room.setName(room.getRoomName());
		ws_room.setDescription(room.getRoomDescription());
		ws_room.setGroupName(room.getGroupName());
		ws_room.setExtension(room.getRoomExtNumber());
		ws_room.setOwnerName(room.getOwnerName());

		if (room.getRoomType().equalsIgnoreCase("Personal")) {
			ws_room.setRoomType(RoomType_type1.Personal);
		} else if (room.getRoomType().equalsIgnoreCase("Public")) {
			ws_room.setRoomType(RoomType_type1.Public);
		}

		RoomMode_type0 roomMode = new RoomMode_type0();
		roomMode.setIsLocked(room.getRoomLocked() == 1);

		if (room.getRoomKey() != null) {
			MessageContext context = MessageContext.getCurrentMessageContext();
			Tenant tenant = tenantService.getTenant(room.getTenantID());
			StringBuffer path = new StringBuffer();
			/*context.getTo().getAddress().substring(0, context.getTo().getAddress().indexOf("/ser")));
			path.append("/flex.html?roomdirect.html&key=");
			path.append(room.getRoomKey());*/

			String joinURL = this.room.getRoomURL(system, context.getIncomingTransportName(), 
					tenant.getTenantURL(), room.getRoomKey());

			path.append(joinURL);
			
			if(tenantService.isTenantNotAllowingGuests()){
				path.append("&noguest");
			}

			roomMode.setRoomURL(path.toString());
		}

		if (room.getRoomPIN() != null) {
			roomMode.setHasPin(true);
			roomMode.setRoomPIN(room.getRoomPIN());
		} else {
			roomMode.setHasPin(false);
		}

		ws_room.setRoomMode(roomMode);

		return ws_room;
	}

	private com.vidyo.portal.admin.Group getWSGroupFromBOGroup(com.vidyo.bo.Group group){
		com.vidyo.portal.admin.Group ws_group = new com.vidyo.portal.admin.Group();

		EntityID id = new EntityID();

		id.setEntityID(group.getGroupID());
		ws_group.setGroupID(id);
		ws_group.setName(group.getGroupName());
		ws_group.setDescription(group.getGroupDescription());
		ws_group.setRoomMaxUsers(String.valueOf(group.getRoomMaxUsers()));
		ws_group.setUserMaxBandWidthIn(String.valueOf(group.getUserMaxBandWidthIn()));
		ws_group.setUserMaxBandWidthOut(String.valueOf(group.getUserMaxBandWidthOut()));

		Router pr_router = this.router.getRouter(group.getRouterID());
		ws_group.setPrimaryVRPool(pr_router.getRouterName());

		Router sc_router = this.router.getRouter(group.getSecondaryRouterID());
		ws_group.setSecondaryVRPool(sc_router.getRouterName());

		return ws_group;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getMembersRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.GetMembersResponse getMembers
		(
			com.vidyo.portal.admin.GetMembersRequest getMembersRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		GetMembersResponse resp = new GetMembersResponse();

		MemberFilter filter = null;
		Filter_type0 input_param = getMembersRequest.getFilter();
		if(input_param != null) {
			filter = new MemberFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("memberID")) {
					filter.setSort("memberID");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSort("username");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("displayName")) {
					filter.setSort("memberName");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSort("roomExtNumber");
				}
				else {
					filter.setSort("");
				}
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());

				if (input_param.getQuery().equalsIgnoreCase("**")) {
					int limit = this.member.countMembers(TenantContext.getTenantId(), filter);
					filter.setLimit(limit);
				}
			}
		}

		List<Member> list = this.member.searchMembers(TenantContext.getTenantId(), filter);
		for (Member member : list) {
			com.vidyo.portal.admin.Member ws_member = getWSMemberFromBOMember(member);
			resp.addMember(ws_member);
		}

		resp.setTotal(list.size());
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getMemberRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws MemberNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.GetMemberResponse getMember
		(
			com.vidyo.portal.admin.GetMemberRequest getMemberRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, MemberNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		GetMemberResponse resp = new GetMemberResponse();
		int memberID = 0;
		Member member;

		try {
			memberID = getMemberRequest.getMemberID().getEntityID();
		} catch(java.lang.Exception e) {
			throw new InvalidArgumentFaultException("Bad memberID = " + memberID);
		}

		member = this.member.getMember(memberID);
		if(member == null) {
			throw new MemberNotFoundExceptionException("Invalid memberID = " + memberID);
		}

		if (TenantContext.getTenantId() != member.getTenantID()) {
			throw new InvalidArgumentFaultException("Invalid memberID = " + memberID);
		}

		com.vidyo.portal.admin.Member ws_member = getWSMemberFromBOMember(member);
		resp.setMember(ws_member);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param addMemberRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws MemberAlreadyExistsExceptionException
	 */
	public com.vidyo.portal.admin.AddMemberResponse addMember
		(
			com.vidyo.portal.admin.AddMemberRequest addMemberRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, MemberAlreadyExistsExceptionException
	{
		User user = this.user.getLoginUser();
		
		AddMemberResponse resp = new AddMemberResponse();
		com.vidyo.portal.admin.Member ws_member = addMemberRequest.getMember();

		String name = ws_member.getName();
		if(name.length() > 128 || !name.matches(ValidationUtils.USERNAME_REGEX)) {
			throw new InvalidArgumentFaultException("Invalid Name = " + name);
		}
		if (this.member.isMemberExistForUserName(name, 0)) {
			throw new  MemberAlreadyExistsExceptionException("Member exist for name = " + name);
		}

		String ext = ws_member.getExtension();
		if (this.room.isRoomExistForRoomExtNumber(ext, 0)) {
			throw new MemberAlreadyExistsExceptionException("Room exist for extension = " + ext);
		}
		if (ext.length() > 64) {
			throw new InvalidArgumentFaultException("Invalid Extension - must be 64 characters or less");
		}

		// Validate email address
		String email = ws_member.getEmailAddress();
		if (!email.matches("([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})")) {
			throw new InvalidArgumentFaultException("Invalid Email Address = " + email);
		}

		int memberID;
		String roleName = ws_member.getRoleName().getValue();

		String groupName = ws_member.getGroupName();
		Group group;
		try {
			group = this.group.getGroupByName(groupName);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Group not found for name = " + groupName);
		}

		if (roleName.equalsIgnoreCase("Legacy")) { // create a Legacy Device
			Member member = new Member();
			// Legacy Device
			member.setRoleID(6);
			member.setRoleName(roleName);
			member.setRoomTypeID(3);
			member.setActive(1); // active by default
			member.setAllowedToParticipate(1); // allowed by default
			member.setUsername(ws_member.getName());
			member.setPassword("");
			member.setMemberName(ws_member.getDisplayName());
			if(ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());
			member.setGroupID(group.getGroupID());
			member.setProfileID(1);
			member.setLangID(1);
			member.setLocation("");
			member.setMemberCreated((int)(new Date().getTime() * .001));
			
			if(!this.member.isUserEligibleToCreateUpdateMember(user, member)) {
				throw new InvalidArgumentFaultException("There are not enough privileges to create a member with role name = " + roleName);
			}

			memberID = this.member.insertMember(member);

			// create a Personal Room for the member
			Room room = new Room();
			room.setMemberID(memberID);
			room.setRoomDescription(member.getUsername()+"-Legacy Device");
			room.setGroupID(member.getGroupID());
			room.setRoomEnabled(member.getActive());
			room.setRoomExtNumber(member.getRoomExtNumber());
			room.setRoomName(member.getUsername());
			room.setRoomTypeID(member.getRoomTypeID());
			this.room.insertRoom(room);
		} else {
			Member member = new Member();
			member.setActive(1); // active by default
			member.setAllowedToParticipate(1); // allowed by default
			member.setRoomTypeID(1); // personal room
			member.setUsername(ws_member.getName());
			member.setMemberName(ws_member.getDisplayName());
			if(ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());

			String langCode = ws_member.getLanguage().getValue();
			if (langCode.equalsIgnoreCase(Language_type0._en)) {
				member.setLangID(1);
			} else if (langCode.equalsIgnoreCase(Language_type0._fr)) {
				member.setLangID(2);
			} else if (langCode.equalsIgnoreCase(Language_type0._ja)) {
				member.setLangID(3);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_CN)) {
				member.setLangID(4);
			} else if (langCode.equalsIgnoreCase(Language_type0._es)) {
				member.setLangID(5);
			} else if (langCode.equalsIgnoreCase(Language_type0._it)) {
				member.setLangID(6);
			} else if (langCode.equalsIgnoreCase(Language_type0._de)) {
				member.setLangID(7);
			} else if (langCode.equalsIgnoreCase(Language_type0._ko)) {
				member.setLangID(8);
			} else if (langCode.equalsIgnoreCase(Language_type0._pt)) {
				member.setLangID(9);
			} else if (langCode.equalsIgnoreCase(Language_type0._fi)) {
				member.setLangID(11);
			} else if (langCode.equalsIgnoreCase(Language_type0._pl)) {
				member.setLangID(12);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_TW)) {
				member.setLangID(13);
			} else if (langCode.equalsIgnoreCase(Language_type0._th)) {
				member.setLangID(14);
			} else if (langCode.equalsIgnoreCase(Language_type0._ru)) {
				member.setLangID(15);
			} else if (langCode.equalsIgnoreCase(Language_type0._tr)) {
				member.setLangID(16);
			}

			member.setMemberCreated((int)(new Date().getTime() * .001));
			try {
				member.setPassword(PasswordHash.createHash(ws_member.getPassword()));
			} catch (Exception ignored) {
			}

			member.setGroupID(group.getGroupID());

			MemberRoles role = null;

			role = this.member.getMemberRoleByName(roleName);
			if(role == null) {
				throw new InvalidArgumentFaultException("Role not found for name = " + roleName);
			}
			member.setRoleID(role.getRoleID());
			member.setRoleName(role.getRoleName());

			String proxyName = ws_member.getProxyName();
			if (proxyName != null && !(proxyName.trim().equalsIgnoreCase("") || proxyName.trim().equalsIgnoreCase("No Proxy"))) {
				Proxy proxy;
				try {
					proxy = this.member.getProxyByName(proxyName);
				} catch (Exception e) {
					throw new InvalidArgumentFaultException("Proxy not found for name = " + proxyName);
				}
				if (proxy != null) {
					member.setProxyID(proxy.getProxyID());
				} else {
					throw new InvalidArgumentFaultException("Proxy not found for name = " + proxyName);
				}
			} else {
				member.setProxyID(0);
			}
			
			if(!this.member.isUserEligibleToCreateUpdateMember(user, member)) {
				throw new InvalidArgumentFaultException("There are not enough privileges to create a member with role name = " + roleName);
			}

			memberID = this.member.insertMember(member);

			// create a Personal Room for the member
			Room room = new Room();
			room.setMemberID(memberID);
			room.setRoomDescription(member.getUsername()+"-Personal Room");
			room.setGroupID(member.getGroupID());
			room.setRoomEnabled(member.getActive());
			room.setRoomExtNumber(member.getRoomExtNumber());
			room.setRoomName(member.getUsername());
			room.setRoomTypeID(member.getRoomTypeID());
			room.setDisplayName(member.getMemberName());
			if (VendorUtils.isRoomsLockedByDefault()) {
				room.setRoomLocked(1);
			}
			this.room.insertRoom(room);
		}

		if (memberID > 0) {
			resp.setOK(OK_type1.OK);
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateMemberRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws MemberNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.UpdateMemberResponse updateMember
		(
			com.vidyo.portal.admin.UpdateMemberRequest updateMemberRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, MemberNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		UpdateMemberResponse resp = new UpdateMemberResponse();
		com.vidyo.portal.admin.Member ws_member = updateMemberRequest.getMember();

		int memberID;
		memberID = updateMemberRequest.getMemberID().getEntityID();
		if (memberID <= 0) {
			throw new InvalidArgumentFaultException("Member not exist for memberID = " + memberID);
		}

		Member member = this.member.getMember(memberID);
		if(member == null) {
			throw new MemberNotFoundExceptionException("Invalid memberID = " + memberID);
		}

		int tenantId = TenantContext.getTenantId();
		if (TenantContext.getTenantId() != member.getTenantID()) {
			throw new InvalidArgumentFaultException("Invalid memberID = " + memberID);
		}
		
		if(!this.member.isUserEligibleToCreateUpdateMember(user, member)) {
			throw new InvalidArgumentFaultException("There are not enough privileges to update a member with role name = " + member.getRoleName());
		}

		String roleName = ws_member.getRoleName().getValue();

		String groupName = ws_member.getGroupName();
		Group group;
		try {
			group = this.group.getGroupByName(groupName);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Group not found for name = " + groupName);
		}

		if(!member.getUsername().trim().equalsIgnoreCase(ws_member.getName().trim())){
			throw new InvalidArgumentFaultException("Forbidden to change name of existing account = " + member.getUsername().trim());
		}

		int rc;
		if (member.getRoleName().equalsIgnoreCase("Legacy")) { // create a Legacy Device
			// Legacy Device
			member.setRoleID(6);
			member.setRoomTypeID(3);
			member.setActive(1); // active by default
			member.setUsername(ws_member.getName());
			member.setMemberName(ws_member.getDisplayName());
			if(ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());
			member.setGroupID(group.getGroupID());
			member.setProfileID(1);
			member.setLangID(1);
			member.setMemberCreated((int)(new Date().getTime() * .001));

			rc = this.member.updateLegacy(tenantId, memberID, member);

		} else {
			member.setActive(1); // active by default
			member.setRoomTypeID(1); // personal room
			member.setUsername(ws_member.getName());
			member.setMemberName(ws_member.getDisplayName());
			if(ws_member.getDescription() != null) {
				member.setDescription(ws_member.getDescription());
			}
			member.setEmailAddress(ws_member.getEmailAddress());
			member.setRoomExtNumber(ws_member.getExtension());

			String langCode = ws_member.getLanguage().getValue();
			if (langCode.equalsIgnoreCase(Language_type0._en)) {
				member.setLangID(1);
			} else if (langCode.equalsIgnoreCase(Language_type0._fr)) {
				member.setLangID(2);
			} else if (langCode.equalsIgnoreCase(Language_type0._ja)) {
				member.setLangID(3);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_CN)) {
				member.setLangID(4);
			} else if (langCode.equalsIgnoreCase(Language_type0._es)) {
				member.setLangID(5);
			} else if (langCode.equalsIgnoreCase(Language_type0._it)) {
				member.setLangID(6);
			} else if (langCode.equalsIgnoreCase(Language_type0._de)) {
				member.setLangID(7);
			} else if (langCode.equalsIgnoreCase(Language_type0._ko)) {
				member.setLangID(8);
			} else if (langCode.equalsIgnoreCase(Language_type0._pt)) {
				member.setLangID(9);
			} else if (langCode.equalsIgnoreCase(Language_type0._fi)) {
				member.setLangID(11);
			} else if (langCode.equalsIgnoreCase(Language_type0._pl)) {
				member.setLangID(12);
			} else if (langCode.equalsIgnoreCase(Language_type0._zh_TW)) {
				member.setLangID(13);
			} else if (langCode.equalsIgnoreCase(Language_type0._th)) {
				member.setLangID(14);
			} else if (langCode.equalsIgnoreCase(Language_type0._ru)) {
				member.setLangID(15);
			} else if (langCode.equalsIgnoreCase(Language_type0._tr)) {
				member.setLangID(16);
			}

			member.setMemberCreated((int)(new Date().getTime() * .001));
			try {
				member.setPassword(PasswordHash.createHash(ws_member.getPassword()));
			} catch (Exception ignored) {
			}

			member.setGroupID(group.getGroupID());

			MemberRoles role = null;

			role = this.member.getMemberRoleByName(roleName);
			if(role == null) {
				throw new InvalidArgumentFaultException("Role not found for name = " + roleName);
			}
			member.setRoleID(role.getRoleID());
			member.setRoleName(role.getRoleName());
			
			if(!this.member.isUserEligibleToCreateUpdateMember(user, member)) {
				throw new InvalidArgumentFaultException("There are not enough privileges to update a member's role to " + roleName);
			}

			String proxyName = ws_member.getProxyName();
			if (proxyName != null && !(proxyName.trim().equalsIgnoreCase("") || proxyName.trim().equalsIgnoreCase("No Proxy"))) {
				Proxy proxy;
				try {
					proxy = this.member.getProxyByName(proxyName);
				} catch (Exception e) {
					throw new InvalidArgumentFaultException("Proxy not found for name = " + proxyName);
				}
				if (proxy != null) {
					member.setProxyID(proxy.getProxyID());
				} else {
					throw new InvalidArgumentFaultException("Proxy not found for name = " + proxyName);
				}
			} else {
				member.setProxyID(0);
			}

			try {
				rc = this.member.updateMember(memberID, member);
			} catch (AccessRestrictedException e) {
				throw new InvalidArgumentFaultException("Access restricted to memberID = " + memberID);
			}
		}

		if (rc > 0) {
			resp.setOK(OK_type1.OK);
		} else {
			throw new GeneralFaultException("Failed to update memberID = " + memberID);
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteMemberRequest
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws MemberNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.DeleteMemberResponse deleteMember
		(
			com.vidyo.portal.admin.DeleteMemberRequest deleteMemberRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, MemberNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		DeleteMemberResponse resp = new DeleteMemberResponse();
		int memberID = 0;

		try {
			memberID = deleteMemberRequest.getMemberID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad memberID = " + memberID);
		}

		try {
			this.member.deleteMember(TenantContext.getTenantId(), memberID);
		} catch (AccessRestrictedException e) {
			throw new InvalidArgumentFaultException("Invalid memberID = " + memberID);
		}

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomsRequest
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.GetRoomsResponse getRooms
		(
			com.vidyo.portal.admin.GetRoomsRequest getRoomsRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		GetRoomsResponse resp = new GetRoomsResponse();

		RoomFilter filter = null;
		Filter_type0 input_param = getRoomsRequest.getFilter();
		if(input_param != null) {
			filter = new RoomFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("roomID")) {
					filter.setSort("roomID");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSort("roomName");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSort("roomExtNumber");
				}
				else {
					filter.setSort("");
				}
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		}

		List<Room> list = this.room.getRooms(filter);
		for (Room room : list) {
			com.vidyo.portal.admin.Room ws_room = getWSRoomFromBORoom(room);
			resp.addRoom(ws_room);
		}

		resp.setTotal(list.size());

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getRoomRequest
	 * @throws InvalidArgumentFaultException  :
	 * @throws NotLicensedExceptionException  :
	 * @throws GeneralFaultException          :
	 * @throws RoomNotFoundExceptionException :
	 */
	public com.vidyo.portal.admin.GetRoomResponse getRoom
		(
			com.vidyo.portal.admin.GetRoomRequest getRoomRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, RoomNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		GetRoomResponse resp = new GetRoomResponse();
		int roomID = 0;
		Room room;

		try {
			roomID = getRoomRequest.getRoomID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		try {
			room = this.room.getRoom(roomID);
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Room not found for roomID = " + roomID);
		}

		if (room != null) {
			if (TenantContext.getTenantId() != room.getTenantID()) {
				throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
			}

			com.vidyo.portal.admin.Room ws_room = getWSRoomFromBORoom(room);
			resp.setRoom(ws_room);
			return resp;
		} else {
			throw new RoomNotFoundExceptionException("Room not found for roomID = " + roomID);
		}
	}

	/**
	 * Auto generated method signature
	 *
	 * @param addRoomRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws RoomAlreadyExistsExceptionException
	 */
	public com.vidyo.portal.admin.AddRoomResponse addRoom
		(
			com.vidyo.portal.admin.AddRoomRequest addRoomRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, RoomAlreadyExistsExceptionException
	{
		AddRoomResponse resp = new AddRoomResponse();
		com.vidyo.portal.admin.Room ws_room = addRoomRequest.getRoom();

		String name = ws_room.getName();
		if(StringUtils.isEmpty(name)) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"Invalid Room Name - " + name);
			exception.setFaultMessage(fault);
			throw exception;			
		}
		
		String ext = ws_room.getExtension();
		if(StringUtils.isEmpty(ext) || ext.length() > 64) {
			InvalidArgumentFault fault = new InvalidArgumentFault();
			InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
					"Invalid Room Extension - " + ext);
			exception.setFaultMessage(fault);
			throw exception;			
		}		
		
		if (this.room.isRoomExistForRoomName(name, 0)) {
			throw new RoomAlreadyExistsExceptionException("Room exist for name = " + name);
		}

		if (this.room.isRoomExistForRoomExtNumber(ext, 0)) {
			throw new RoomAlreadyExistsExceptionException("Room exist for extension = " + ext);
		}

		if (ws_room.getRoomType().getValue().equalsIgnoreCase(RoomType_type0._Personal)) {
			throw new InvalidArgumentFaultException("Use addMember to create a Personal room");
		}

		Room room = new Room();
		room.setRoomEnabled(1); // active by default
		room.setRoomTypeID(2); // public room
		room.setRoomName(ws_room.getName());
		room.setRoomDescription(ws_room.getDescription());
		room.setRoomExtNumber(ws_room.getExtension());

		if (ws_room.getRoomMode().getHasPin()) {
			String pin = ws_room.getRoomMode().getRoomPIN();
			if (pin == null || pin.equalsIgnoreCase("")) {
				throw new InvalidArgumentFaultException("roomPIN not provided");
			} else {
				if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), pin)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
							"PIN should be a " +
									system.getMinPINLengthForTenant(TenantContext.getTenantId()) +
									"-" + SystemServiceImpl.PIN_MAX + " digit number");
					exception.setFaultMessage(fault);
					throw exception;
				}				
				room.setPinSetting("enter");
				room.setRoomPIN(pin);
			}
		}

		String groupName = ws_room.getGroupName();
		Group group;
		try {
			group = this.group.getGroupByName(groupName);
			room.setGroupID(group.getGroupID());
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Group not found for name = " + groupName);
		}

		Member member;
		String ownerName = ws_room.getOwnerName();
		if (!this.member.isMemberExistForUserName(ownerName, 0)) {
			throw new InvalidArgumentFaultException("Member not found for ownerName = " + ownerName);
		}
		try {
			member = this.member.getMemberByName(ownerName);
			room.setMemberID(member.getMemberID());
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Member not found for ownerName = " + ownerName);
		}

		if (ws_room.getRoomMode().getIsLocked()) {
			room.setRoomLocked(1);
		}

		this.room.insertRoom(room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateRoomRequest:
	 * @throws InvalidArgumentFaultException  :
	 * @throws NotLicensedExceptionException  :
	 * @throws GeneralFaultException          :
	 * @throws RoomNotFoundExceptionException :
	 * @throws RoomAlreadyExistsExceptionException
	 */
	public com.vidyo.portal.admin.UpdateRoomResponse updateRoom
		(
			com.vidyo.portal.admin.UpdateRoomRequest updateRoomRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, RoomNotFoundExceptionException, RoomAlreadyExistsExceptionException
	{
		User user = this.user.getLoginUser();

		UpdateRoomResponse resp = new UpdateRoomResponse();
		com.vidyo.portal.admin.Room ws_room = updateRoomRequest.getRoom();

		int roomID;
		roomID = updateRoomRequest.getRoomID().getEntityID();
		if (roomID <= 0) {
			throw new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
		}

		String name = ws_room.getName();
		if (this.room.isRoomExistForRoomName(name, roomID)) {
			throw new RoomAlreadyExistsExceptionException("Room exist for name = " + name);
		}

		String ext = ws_room.getExtension();
		if (this.room.isRoomExistForRoomExtNumber(ext, roomID)) {
			throw new InvalidArgumentFaultException("Room exist for extension = " + ext);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new RoomNotFoundExceptionException("Room not exist for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		room.setRoomEnabled(1); // active by default
		room.setRoomName(ws_room.getName());
		room.setRoomDescription(ws_room.getDescription());
		room.setRoomExtNumber(ws_room.getExtension());

		if (ws_room.getRoomMode().getHasPin()) {
			String pin = ws_room.getRoomMode().getRoomPIN();
			if (pin == null || pin.equalsIgnoreCase("")) {
				throw new InvalidArgumentFaultException("roomPIN not provided");
			} else {
				if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), pin)) {
					InvalidArgumentFault fault = new InvalidArgumentFault();
					InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
							"PIN should be a " +
									system.getMinPINLengthForTenant(TenantContext.getTenantId()) +
									"-" + SystemServiceImpl.PIN_MAX + " digit number");
					exception.setFaultMessage(fault);
					throw exception;
				}				
				room.setPinSetting("enter");
				room.setRoomPIN(pin);
			}
		} else {
			room.setPinSetting("remove");
		}

		String groupName = ws_room.getGroupName();
		Group group;
		try {
			group = this.group.getGroupByName(groupName);
			room.setGroupID(group.getGroupID());
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Group not found for name = " + groupName);
		}

		Member member;
		String ownerName = ws_room.getOwnerName();
		if (!this.member.isMemberExistForUserName(ownerName, 0)) {
			throw new InvalidArgumentFaultException("Member not found for ownerName = " + ownerName);
		}
		try {
			member = this.member.getMemberByName(ownerName);
			room.setMemberID(member.getMemberID());
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Member not found for ownerName = " + ownerName);
		}

		if (ws_room.getRoomMode().getIsLocked()) {
			room.setRoomLocked(1);
		} else {
			room.setRoomLocked(0);
		}

		this.room.updateRoom(roomID, room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteRoomRequest:
	 * @throws InvalidArgumentFaultException  :
	 * @throws NotLicensedExceptionException  :
	 * @throws GeneralFaultException          :
	 * @throws RoomNotFoundExceptionException :
	 */
	public com.vidyo.portal.admin.DeleteRoomResponse deleteRoom
		(
			com.vidyo.portal.admin.DeleteRoomRequest deleteRoomRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, RoomNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		DeleteRoomResponse resp = new DeleteRoomResponse();

		int roomID = 0;
		try {
			roomID = deleteRoomRequest.getRoomID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch(Exception e) {
			throw new RoomNotFoundExceptionException("Room not found for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		if (room.getRoomType().equalsIgnoreCase("Personal")) {
			throw new InvalidArgumentFaultException("Cannot delete Personal type of Room for roomID = " + roomID);
		}

		this.room.deleteRoom(room.getRoomID());

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createRoomPINRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.CreateRoomPINResponse createRoomPIN
		(
			com.vidyo.portal.admin.CreateRoomPINRequest createRoomPINRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		CreateRoomPINResponse resp = new CreateRoomPINResponse();

		int roomID = 0;
		try {
			roomID = createRoomPINRequest.getRoomID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		String pin = createRoomPINRequest.getPIN();
		if (pin == null || pin.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("PIN not provided");
		} else {
			if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), pin)) {
				InvalidArgumentFault fault = new InvalidArgumentFault();
				InvalidArgumentFaultException exception = new InvalidArgumentFaultException(
						"PIN should be a " +
								system.getMinPINLengthForTenant(TenantContext.getTenantId()) +
								"-" + SystemServiceImpl.PIN_MAX + " digit number");
				exception.setFaultMessage(fault);
				throw exception;
			}			
			room.setPinSetting("enter");
			room.setRoomPIN(pin);
		}

		this.room.updateRoomPIN(room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeRoomPINRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.RemoveRoomPINResponse removeRoomPIN
		(
			com.vidyo.portal.admin.RemoveRoomPINRequest removeRoomPINRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		RemoveRoomPINResponse resp = new RemoveRoomPINResponse();

		int roomID = 0;
		try {
			roomID = removeRoomPINRequest.getRoomID().getEntityID();
		} catch(java.lang.Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		room.setPinSetting("remove");

		this.room.updateRoomPIN(room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param createRoomURLRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.CreateRoomURLResponse createRoomURL
		(
			com.vidyo.portal.admin.CreateRoomURLRequest createRoomURLRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		CreateRoomURLResponse resp = new CreateRoomURLResponse();

		int roomID = 0;
		try {
			roomID = createRoomURLRequest.getRoomID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		this.room.generateRoomKey(room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param removeRoomURLRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.RemoveRoomURLResponse removeRoomURL
		(
			com.vidyo.portal.admin.RemoveRoomURLRequest removeRoomURLRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		RemoveRoomURLResponse resp = new RemoveRoomURLResponse();

		int roomID = 0;
		try {
			roomID = removeRoomURLRequest.getRoomID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad roomID = " + roomID);
		}

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Room not exist for roomID = " + roomID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Room for roomID = " + roomID + " belongs to other tenant");
		}

		this.room.removeRoomKey(room);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getGroupsRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.GetGroupsResponse getGroups
		(
			com.vidyo.portal.admin.GetGroupsRequest getGroupsRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		GetGroupsResponse resp = new GetGroupsResponse();

		GroupFilter filter = null;
		Filter_type0 input_param = getGroupsRequest.getFilter();
		if(input_param != null) {
			filter = new GroupFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("groupID")) {
					filter.setSort("groupID");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSort("groupName");
				}
				else {
					filter.setSort("");
				}
			}
			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		}

		List<Group> list = this.group.getGroups(filter);
		for (Group group : list) {
			com.vidyo.portal.admin.Group ws_group = getWSGroupFromBOGroup(group);
			resp.addGroup(ws_group);
		}

		resp.setTotal(list.size());

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getGroupRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws GroupNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.GetGroupResponse getGroup
		(
			com.vidyo.portal.admin.GetGroupRequest getGroupRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, GroupNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		GetGroupResponse resp = new GetGroupResponse();

		int groupID = 0;
		try {
			groupID = getGroupRequest.getGroupID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad groupID = " + groupID);
		}

		Group group;
		try {
			group = this.group.getGroup(groupID);
		} catch(Exception e) {
			throw new GroupNotFoundExceptionException("Group not found for groupID = " + groupID);
		}

		if (group != null) {
			if (TenantContext.getTenantId() != group.getTenantID()) {
				throw new InvalidArgumentFaultException("Group for groupID = " + groupID + " belongs to other tenant");
			}

			com.vidyo.portal.admin.Group ws_group = getWSGroupFromBOGroup(group);
			resp.setGroup(ws_group);
		} else {
			throw new GroupNotFoundExceptionException("Group not found for groupID = " + groupID);
		}

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param addGroupRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws GroupAlreadyExistsExceptionException
	 */
	public com.vidyo.portal.admin.AddGroupResponse addGroup
		(
			com.vidyo.portal.admin.AddGroupRequest addGroupRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, GroupAlreadyExistsExceptionException
	{
		AddGroupResponse resp = new AddGroupResponse();
		com.vidyo.portal.admin.Group ws_group = addGroupRequest.getGroup();

		String name = ws_group.getName();
		if (this.group.isGroupExistForGroupName(name, 0)) {
			throw new GroupAlreadyExistsExceptionException("Group exist for name = " + name);
		}

		Group group = new Group();
		group.setDefaultFlag(1);
		group.setGroupName(ws_group.getName());
		group.setGroupDescription(ws_group.getDescription());
		group.setRoomMaxUsers(Integer.valueOf(ws_group.getRoomMaxUsers()));
		group.setUserMaxBandWidthIn(Integer.valueOf(ws_group.getUserMaxBandWidthIn()));
		group.setUserMaxBandWidthOut(Integer.valueOf(ws_group.getUserMaxBandWidthOut()));

/*		String pr_routerName = ws_group.getPrimaryVRPool();
		if (!this.router.isRouterExistForRouterName(pr_routerName, 0)) {
			throw new InvalidArgumentFaultException("Primary VidyoRouter Pool not exist for name = " + pr_routerName);
		}
		Router pr_router = this.router.getRouterByName(pr_routerName);
		group.setRouterID(pr_router.getRouterID());

		String sc_routerName = ws_group.getSecondaryVRPool();
		if (!this.router.isRouterExistForRouterName(sc_routerName, 0)) {
			throw new InvalidArgumentFaultException("Secondary VidyoRouter Pool not exist for name = " + sc_routerName);
		}
		Router sc_router = this.router.getRouterByName(sc_routerName);
		group.setSecondaryRouterID(sc_router.getRouterID());*/

		// Not default group
		group.setDefaultFlag(0);

		this.group.insertGroup(group);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param updateGroupRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws GroupNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.UpdateGroupResponse updateGroup
		(
			com.vidyo.portal.admin.UpdateGroupRequest updateGroupRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, GroupNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		UpdateGroupResponse resp = new UpdateGroupResponse();
		com.vidyo.portal.admin.Group ws_group = updateGroupRequest.getGroup();

		int groupID;
		groupID = updateGroupRequest.getGroupID().getEntityID();
		if (groupID <= 0) {
			throw new InvalidArgumentFaultException("Group not exist for groupID = " + groupID);
		}

		Group group;
		try {
			group = this.group.getGroup(groupID);
		} catch (Exception e) {
			throw new GroupNotFoundExceptionException("Group not exist for groupID = " + groupID);
		}

		String name = ws_group.getName();
		if (this.group.isGroupExistForGroupName(name, groupID)) {
			throw new InvalidArgumentFaultException("Group exist for name = " + name);
		}

		if (TenantContext.getTenantId() != group.getTenantID()) {
			throw new InvalidArgumentFaultException("Group for groupID = " + groupID + " belongs to other tenant");
		}

		if (ws_group.getName() != null && ws_group.getName().trim().length() > 0){
			group.setGroupName(ws_group.getName());
		}
		if (ws_group.getDescription() != null && ws_group.getDescription().trim().length() > 0) {
			group.setGroupDescription(ws_group.getDescription());
		}
		group.setRoomMaxUsers(Integer.valueOf(ws_group.getRoomMaxUsers()));
		group.setUserMaxBandWidthIn(Integer.valueOf(ws_group.getUserMaxBandWidthIn()));
		group.setUserMaxBandWidthOut(Integer.valueOf(ws_group.getUserMaxBandWidthOut()));

		/*String pr_routerName = ws_group.getPrimaryVRPool();
		if (!this.router.isRouterExistForRouterName(pr_routerName, 0)) {
			throw new InvalidArgumentFaultException("Primary VidyoRouter Pool not exist for name = " + pr_routerName);
		}
		Router pr_router = this.router.getRouterByName(pr_routerName);
		group.setRouterID(pr_router.getRouterID());

		String sc_routerName = ws_group.getSecondaryVRPool();
		if (!this.router.isRouterExistForRouterName(sc_routerName, 0)) {
			throw new InvalidArgumentFaultException("Secondary VidyoRouter Pool not exist for name = " + sc_routerName);
		}
		Router sc_router = this.router.getRouterByName(sc_routerName);
		group.setSecondaryRouterID(sc_router.getRouterID());*/

		this.group.updateGroup(groupID, group);

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param deleteGroupRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 * @throws GroupNotFoundExceptionException
	 */
	public com.vidyo.portal.admin.DeleteGroupResponse deleteGroup
		(
			com.vidyo.portal.admin.DeleteGroupRequest deleteGroupRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException, GroupNotFoundExceptionException
	{
		User user = this.user.getLoginUser();

		DeleteGroupResponse resp = new DeleteGroupResponse();

		int groupID = 0;
		try {
			groupID = deleteGroupRequest.getGroupID().getEntityID();
		} catch(Exception e) {
			throw new InvalidArgumentFaultException("Bad groupID = " + groupID);
		}

		Group defaultGroup = this.group.getDefaultGroup();
		if (defaultGroup != null && groupID == defaultGroup.getGroupID()) {
			throw new InvalidArgumentFaultException("Default group cannot be deleted");
		}

		Group group;
		try {
			group = this.group.getGroup(groupID);
		} catch(Exception e) {
			throw new GroupNotFoundExceptionException("Group not found for groupID = " + groupID);
		}

		if (TenantContext.getTenantId() != group.getTenantID()) {
			throw new InvalidArgumentFaultException("Group for groupID = " + groupID + " belongs to other tenant");
		}

		this.group.deleteGroup(group.getGroupID());

		resp.setOK(OK_type1.OK);

		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getParticipantsRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.GetParticipantsResponse getParticipants
		(
			com.vidyo.portal.admin.GetParticipantsRequest getParticipantsRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		GetParticipantsResponse resp = new GetParticipantsResponse();

		int confID = getParticipantsRequest.getConferenceID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(confID);
		} catch (java.lang.Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid " + confID);
		}

		if (room == null) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid " + confID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for ConferenceID = " + confID + " belongs to other tenant");
		}

		Filter_type0 input_param = getParticipantsRequest.getFilter();
		EntityFilter filter = null;
		// map ws_filter to entity filter
		if(input_param != null) {
			filter = new EntityFilter();
			if (input_param.getStart() >= 0) {
				filter.setStart(input_param.getStart());
			}
			if (input_param.getLimit() >= 0) {
				filter.setLimit(input_param.getLimit());
			}
			if (input_param.getDir() != null) {
				filter.setDir(input_param.getDir().getValue());
			}
			if (input_param.getSortBy() != null) {
				// mapping between WS and SQL
				if (input_param.getSortBy().equalsIgnoreCase("entityID")) {
					filter.setSortBy("endpointID");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("name")) {
					filter.setSortBy("name");
				}
				else if (input_param.getSortBy().equalsIgnoreCase("extension")) {
					filter.setSortBy("ext");
				}
				else {
					filter.setSortBy("");
				}
			}

			filter.setEntityType("All");

			if (input_param.getQuery() != null) {
				filter.setQuery(input_param.getQuery());
			}
		}

		List<Entity> list = this.conference.getParticipants(confID, filter, null);

		// map list of entities to ws_entities
		for (com.vidyo.bo.Entity entity : list) {
			Entity_type0 ws_entity = new Entity_type0();
			EntityID id = new EntityID();
			id.setEntityID(entity.getRoomID()); // roomID as EntityID
			ws_entity.setEntityID(id);

			EntityID p_id = new EntityID();
			p_id.setEntityID(entity.getEndpointID()); // endpointID as ParticipantID
			ws_entity.setParticipantID(p_id);

			if (entity.getRoomType() != null) {
				if (entity.getRoomType().equalsIgnoreCase("Personal")) {
					ws_entity.setEntityType(EntityType_type1.Member);
					ws_entity.setCanCallDirect(true);
					ws_entity.setCanJoinMeeting(true);
				} else if (entity.getRoomType().equalsIgnoreCase("Public")) {
					ws_entity.setEntityType(EntityType_type1.Room);
					ws_entity.setCanCallDirect(false);
					ws_entity.setCanJoinMeeting(true);
				} else if (entity.getRoomType().equalsIgnoreCase("Legacy")) {
					ws_entity.setEntityType(EntityType_type1.Legacy);
					ws_entity.setCanCallDirect(true);
					ws_entity.setCanJoinMeeting(false);
				}
			} else {
				ws_entity.setEntityType(EntityType_type1.Member);
				ws_entity.setCanCallDirect(false);
				ws_entity.setCanJoinMeeting(false);
			}

			if (entity.getMemberID() != 0) {
				Member member = this.member.getMember(entity.getMemberID());

				if (member.getLangID() == 1) {
					ws_entity.setLanguage(Language_type0.en);
				} else if (member.getLangID() == 2) {
					ws_entity.setLanguage(Language_type0.fr);
				} else if (member.getLangID() == 3) {
					ws_entity.setLanguage(Language_type0.ja);
				} else if (member.getLangID() == 4) {
					ws_entity.setLanguage(Language_type0.zh_CN);
				} else if (member.getLangID() == 5) {
					ws_entity.setLanguage(Language_type0.es);
				} else if (member.getLangID() == 6) {
					ws_entity.setLanguage(Language_type0.it);
				} else if (member.getLangID() == 7) {
					ws_entity.setLanguage(Language_type0.de);
				} else if (member.getLangID() == 8) {
					ws_entity.setLanguage(Language_type0.ko);
				} else if (member.getLangID() == 9) {
					ws_entity.setLanguage(Language_type0.pt);
				} else if (member.getLangID() == 10) {
					ws_entity.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(member.getTenantID()).getLangCode()));
				} else if (member.getLangID() == 11) {
					ws_entity.setLanguage(Language_type0.fi);
				} else if (member.getLangID() == 12) {
					ws_entity.setLanguage(Language_type0.pl);
				} else if (member.getLangID() == 13) {
					ws_entity.setLanguage(Language_type0.zh_TW);
				} else if (member.getLangID() == 14) {
					ws_entity.setLanguage(Language_type0.th);
				} else if (member.getLangID() == 15) {
					ws_entity.setLanguage(Language_type0.ru);
				} else if (member.getLangID() == 16) {
					ws_entity.setLanguage(Language_type0.tr);
				}

				if (entity.getModeID() == 1) {
					ws_entity.setMemberMode(MemberMode_type1.Available);
				} else if (entity.getModeID() == 2) {
					ws_entity.setMemberMode(MemberMode_type1.Away);
				} else if (entity.getModeID() == 3) {
					ws_entity.setMemberMode(MemberMode_type1.DoNotDisturb);
				}
			} else {
				ws_entity.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(entity.getTenantID()).getLangCode()));
				ws_entity.setMemberMode(MemberMode_type1.Available);
			}

			ws_entity.setDisplayName(entity.getName());
			ws_entity.setExtension(entity.getExt());
			ws_entity.setDescription(entity.getDescription());

			if (entity.getMemberStatus() == 0) {
				ws_entity.setMemberStatus(MemberStatus_type1.Offline);
			} else if (entity.getMemberStatus() == 1) {
				ws_entity.setMemberStatus(MemberStatus_type1.Online);
			} else if (entity.getMemberStatus() == 2) {
				ws_entity.setMemberStatus(MemberStatus_type1.Busy);
			} else if (entity.getMemberStatus() == 3) {
				ws_entity.setMemberStatus(MemberStatus_type1.Ringing);
			} else if (entity.getMemberStatus() == 4) {
				ws_entity.setMemberStatus(MemberStatus_type1.RingAccepted);
			} else if (entity.getMemberStatus() == 5) {
				ws_entity.setMemberStatus(MemberStatus_type1.RingRejected);
			} else if (entity.getMemberStatus() == 6) {
				ws_entity.setMemberStatus(MemberStatus_type1.RingNoAnswer);
			} else if (entity.getMemberStatus() == 7) {
				ws_entity.setMemberStatus(MemberStatus_type1.Alerting);
			} else if (entity.getMemberStatus() == 8) {
				ws_entity.setMemberStatus(MemberStatus_type1.AlertCancelled);
			} else if (entity.getMemberStatus() == 9) {
				ws_entity.setMemberStatus(MemberStatus_type1.BusyInOwnRoom);
			} else if (entity.getMemberStatus() == 12) {
				ws_entity.setMemberStatus(MemberStatus_type1.Busy); // WaitForConfirm
			}

			RoomMode_type0 roomMode = new RoomMode_type0();
			roomMode.setIsLocked(entity.getRoomLocked() == 1);
			roomMode.setHasPin(entity.getRoomPinned() == 1);
			if (entity.getRoomPIN() != null) {
				roomMode.setRoomPIN(entity.getRoomPIN());
			}
			if (entity.getRoomKey() != null) {
				StringBuffer path = new StringBuffer();
				Tenant tenant = this.tenantService.getTenant(entity.getTenantID());
				/*path.append("http://").append(tenant.getTenantURL());
				path.append("/flex.html?roomdirect.html&key=");
				path.append(entity.getRoomKey());*/

				String joinURL = this.room.getRoomURL(system, "http", 
						tenant.getTenantURL(), entity.getRoomKey());

				path.append(joinURL);
				if(tenantService.isTenantNotAllowingGuests()){
					path.append("&noguest");
				}

				roomMode.setRoomURL(path.toString());
			}
			ws_entity.setRoomMode(roomMode);

			if (entity.getRoomStatus() == 0) {
				ws_entity.setRoomStatus(RoomStatus_type0.Empty);
			} else if (entity.getRoomStatus() == 1) {
				ws_entity.setRoomStatus(RoomStatus_type0.Occupied);
			} else if (entity.getRoomStatus() == 2) {
				ws_entity.setRoomStatus(RoomStatus_type0.Full);
			}

			ws_entity.setVideo(entity.getVideo() == 1);
			ws_entity.setAudio(entity.getAudio() == 1);
			//ToDo - share?
			ws_entity.setAppshare(true);

			if (entity.getRoomOwner() == 1) {
				ws_entity.setCanControl(true);
			} else {
				ws_entity.setCanControl(false);
			}

			resp.addEntity(ws_entity);
		}

		// total records in DB
		Long total = this.conference.getCountParticipants(confID);
		resp.setTotal(total.intValue());
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param inviteToConferenceRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.InviteToConferenceResponse inviteToConference
		(
			com.vidyo.portal.admin.InviteToConferenceRequest inviteToConferenceRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		InviteToConferenceResponse resp = new InviteToConferenceResponse();

		Invite invite = new Invite();
		invite.setFromMemberID(user.getMemberID());

		int confID = inviteToConferenceRequest.getConferenceID().getEntityID();
		invite.setFromRoomID(confID);

		Room fromRoom;
		try {
			fromRoom = this.room.getRoom(confID);
		} catch (java.lang.Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid " + confID);
		}

		if (fromRoom == null) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid " + confID);
		}

		if (TenantContext.getTenantId() != fromRoom.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for ConferenceID = " + confID + " belongs to other tenant");
		}

		int toEntityID = 0;
		if(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getEntityID() != null){
			toEntityID = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getEntityID().getEntityID();
		}

		Room toRoom;
		int status = 1;
		boolean isLegacy = false;
		try {
			toRoom = this.room.getRoom(toEntityID);
			invite.setToMemberID(toRoom.getMemberID());

			Member invitee = this.member.getMember(toRoom.getMemberID());
			if(invitee.getRoleID() == 6){
				isLegacy = true;
			}

			int toEndpointID = 0;
			if(!isLegacy){
				toEndpointID = this.conference.getEndpointIDForMemberID(toRoom.getMemberID());

				invite.setToEndpointID(toEndpointID);

				// check if status of Endpoint is Online
				status = this.conference.getEndpointStatus(toEndpointID);
			}
		} catch (Exception e) {
			invite.setToMemberID(0);
			invite.setToEndpointID(0);
		}

		if (!isLegacy && status != 1) {
			throw new InvalidArgumentFaultException("Status of invited member is not Online.");
		}

		String search = "";
		if(inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getInvite() != null){
			search = inviteToConferenceRequest.getInviteToConferenceRequestChoice_type0().getInvite();
		}
		invite.setSearch(search);

		try {
			this.conference.inviteToConference(invite);
		} catch (OutOfPortsException e) {
			throw new GeneralFaultException(e.getMessage());
		} catch (EndpointNotExistException e) {
			throw new GeneralFaultException(e.getMessage());
		} catch (InviteConferenceException e) {
			throw new GeneralFaultException(e.getMessage());
		} catch (EndpointNotSupportedException e) {
            throw new GeneralFaultException(e.getMessage());
        }


		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param leaveConferenceRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.LeaveConferenceResponse leaveConference
		(
			com.vidyo.portal.admin.LeaveConferenceRequest leaveConferenceRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		LeaveConferenceResponse resp = new LeaveConferenceResponse();

		int confID = leaveConferenceRequest.getConferenceID().getEntityID();
		int endpointID = leaveConferenceRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(confID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid");
		}
		if (room.getMemberID() != user.getMemberID()) {
			// DO NOT CHECK FOR ADMIN
			//throw new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for ConferenceID = " + confID + " belongs to other tenant");
		}

		try {
			this.conference.leaveTheConference(endpointID, confID, CallCompletionCode.BY_SOMEONE_ELSE);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param muteAudioRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.MuteAudioResponse muteAudio
		(
			com.vidyo.portal.admin.MuteAudioRequest muteAudioRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		MuteAudioResponse resp = new MuteAudioResponse();

		int roomID = muteAudioRequest.getConferenceID().getEntityID();
		int endpointID = muteAudioRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid");
		}
		if (room.getMemberID() != user.getMemberID()) {
			// DO NOT CHECK FOR ADMIN
			//throw new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
		}
		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for conferenceID = " + roomID + " belongs to other tenant");
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			throw new InvalidArgumentFaultException("Participant not found in Conference");
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.muteAudio(endpointGUID);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param unmuteAudioRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.UnmuteAudioResponse unmuteAudio
		(
			com.vidyo.portal.admin.UnmuteAudioRequest unmuteAudioRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		UnmuteAudioResponse resp = new UnmuteAudioResponse();

		int roomID = unmuteAudioRequest.getConferenceID().getEntityID();
		int endpointID = unmuteAudioRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid");
		}
		if (room.getMemberID() != user.getMemberID()) {
			// DO NOT CHECK FOR ADMIN
			//throw new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for conferenceID = " + roomID + " belongs to other tenant");
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			throw new InvalidArgumentFaultException("Participant not found in Conference");
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.unmuteAudio(endpointGUID);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param stopVideoRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.StopVideoResponse stopVideo
		(
			com.vidyo.portal.admin.StopVideoRequest stopVideoRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		StopVideoResponse resp = new StopVideoResponse();

		int roomID = stopVideoRequest.getConferenceID().getEntityID();
		int endpointID = stopVideoRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid");
		}
		if (room.getMemberID() != user.getMemberID()) {
			// DO NOT CHECK FOR ADMIN
			//throw new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for conferenceID = " + roomID + " belongs to other tenant");
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			throw new InvalidArgumentFaultException("Participant not found in Conference");
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.stopVideo(endpointGUID);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param startVideoRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.StartVideoResponse startVideo
		(
			com.vidyo.portal.admin.StartVideoRequest startVideoRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		User user = this.user.getLoginUser();

		StartVideoResponse resp = new StartVideoResponse();

		int roomID = startVideoRequest.getConferenceID().getEntityID();
		int endpointID = startVideoRequest.getParticipantID().getEntityID();

		Room room;
		try {
			room = this.room.getRoom(roomID);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("ConferenceID is invalid");
		}
		if (room.getMemberID() != user.getMemberID()) {
			// DO NOT CHECK FOR ADMIN
			//throw new InvalidArgumentFaultException("You are not an owner of conference for conferenceID = " + confID);
		}

		if (TenantContext.getTenantId() != room.getTenantID()) {
			throw new InvalidArgumentFaultException("Conference for conferenceID = " + roomID + " belongs to other tenant");
		}

		if (!this.conference.isEndpointIDinRoomID(endpointID, roomID)) {
			throw new InvalidArgumentFaultException("Participant not found in Conference");
		}

		String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
		if (endpointGUID.equalsIgnoreCase("")) {
			endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "V");
		}

		try {
			this.conference.startVideo(endpointGUID);
		} catch (Exception e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setOK(OK_type1.OK);
		return resp;
	}

	/**
	 * Auto generated method signature
	 *
	 * @param getLicenseDataRequest:
	 * @throws InvalidArgumentFaultException :
	 * @throws NotLicensedExceptionException :
	 * @throws GeneralFaultException         :
	 */
	public com.vidyo.portal.admin.GetLicenseDataResponse getLicenseData
		(
			com.vidyo.portal.admin.GetLicenseDataRequest getLicenseDataRequest
		)
		throws InvalidArgumentFaultException, NotLicensedExceptionException, GeneralFaultException
	{
		GetLicenseDataResponse resp = new GetLicenseDataResponse();

		int tenantId  = TenantContext.getTenantId();

		List<SystemLicenseFeature> list = this.license.getTenantLicense(tenantId);

		List<String> listOfNames = new ArrayList<String>(10);
		listOfNames.add("Start Date");
		listOfNames.add("Expiry Date");
		listOfNames.add("SeatStartDate");
		listOfNames.add("SeatExpiry");
		listOfNames.add("Seats");
		listOfNames.add("Ports");
		listOfNames.add("Installs");
		listOfNames.add("LimitTypeExecutiveSystem");
		listOfNames.add("LimitTypePanoramaSystem");

		for (SystemLicenseFeature feature : list) {
			if (listOfNames.contains(feature.getName())) {
				LicenseFeatureData param = new LicenseFeatureData();
				param.setName(feature.getName());
				param.setMaxValue(feature.getLicensedValue());
				param.setCurrentValue(feature.getCurrentValue());

				resp.addLicenseFeature(param);
			}
		}

		return resp;
	}

	@Override
	public GetPortalVersionResponse getPortalVersion(
			GetPortalVersionRequest getPortalVersionRequest)
			throws InvalidArgumentFaultException,
			NotLicensedExceptionException, GeneralFaultException {
		logger.debug("Entering portalVersion() of VidyoPortalAdminServiceSkeleton");
		String portalVersion = system.getPortalVersion();
		GetPortalVersionResponse getPortalVersionResponse = new GetPortalVersionResponse();
		getPortalVersionResponse.setPortalVersion(portalVersion);
		logger.debug("Exiting portalVersion() of VidyoPortalAdminServiceSkeleton");
		return getPortalVersionResponse;
	}

}