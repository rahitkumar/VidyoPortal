package com.vidyo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Guest;
import com.vidyo.bo.InviteList;
import com.vidyo.bo.InviteListFilter;
import com.vidyo.bo.Language;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.RoomIdConferenceType;
import com.vidyo.bo.SpeedDial;
import com.vidyo.bo.SpeedDialFilter;
import com.vidyo.bo.User;
import com.vidyo.bo.member.MemberMini;
import com.vidyo.service.exceptions.AccessRestrictedException;

public interface IMemberService {
    public boolean isSuperExistForUserName(String userName);
    public int updateSuperPassword(Member member);
    public String getMemberPassword(int memberID);
    public boolean isValidMemberPassword(String password, int memberID);
    public List<Member> getMembers(MemberFilter filter);
    public Long getCountMembers(MemberFilter filter);
    public Member getMember(int memberID);
    public Member getMember(int tenantID, int memberID);
    public Member getMemberByRoom(int roomID);
    public Member getMemberNoRoom(int memberID);
    public int updateSuper(Member member) throws AccessRestrictedException;
    public Member getMemberByName(String userName);
    public Member getMemberByName(String userName, String tenantName);
    public Member getMemberByName(String userName, int tenantID);
    public Language getMemberLang(int memberID);
    public int updateMember(int memberID, Member member) throws AccessRestrictedException;
    public int updateMember(int tenantID, int memberID, Member member) throws AccessRestrictedException;
    public int enableUser(int tenantID, int memberID, String userName, int enable);
    public int insertMember(Member member);
    public int deleteMember(int tenantID, int memberID) throws AccessRestrictedException;
    public List<MemberRoles> getMemberRoles();
    public List<MemberRoles> getSamlMemberRoles();
    public MemberRoles getMemberRoleByRoleId(int roleID) ;
    public Long getCountMemberRoles();
    public Long getCountSamlMemberRoles();
    public MemberRoles getMemberRoleByName(String roleName);
    public boolean isMemberExistForUserName(String userName, int memberID);
    public List<SpeedDial> getSpeedDial(int memberID, SpeedDialFilter filter);
    public Long getCountSpeedDial(int memberID);
    public int addSpeedDialEntry(int memberID, int roomID);
    public int removeSpeedDialEntry(int memberID, int roomID);
    public boolean isInSpeedDialEntry(int memberID, int roomID);
    public int updateMemberPassword(Member member);
    public int updateMemberLanguage(Member member);
    public int updateMemberDefaultEndpointGUID(Member member);
    public int updateLegacy(int tenantId, int memberID, Member member);
    public int updateMemberMode(int memberID, int modeID);

    public List<Entity> getContacts(int memberID, EntityFilter filter);
    public int getCountContacts(int memberID, EntityFilter filter);
    public Entity getContact(int memberID);
    public int getJoinedConferenceRoomId(int memberID);
    public RoomIdConferenceType getJoinedConferenceRoomIdConferenceType(int memberID);

    public Guest getGuest(int guestID);
    public String getTenantPrefix();
    public int getLicensedSeats();
    public int getLicensedSeats(int tenant);
    public int getLicensedPorts();
    public int getLicensedPorts(int tenantId);
    public int getLicensedInstalls();
    public int getLicensedInstalls(int tenantId);
    public Long getCountSeats();
    public Long getCountSeats(int tenant);
    public Long getCountAllSeats();
    public Long getCountAllInstalls();
    public Long getCountInstalls();
    public Long getCountInstalls(int tenantId);
    public List<Proxy> getProxies();
    public Long getCountProxies();
    public Proxy getProxyByName(String proxyName);

    public List<InviteList> getOnlineMembers(InviteListFilter filter);
    public Long getCountOnlineMembers();
    
    public boolean isMemberActive(int memberID);
    public boolean isMemberAllowedToParticipate(int memberID);    

    public List<Location> getLocationTags();
    public Long getCountLocationTags();
    public Location getLocationInTenantByTag(String locationTag);

    public int getLicensedExecutive();
    public int getLicensedExecutive(int tenant);
    public Long getCountExecutives();
    public Long getCountExecutives(int tenant);
    public Long getCountAllExecutives();

	public int getLicensedPanorama();
	public int getLicensedPanorama(int tenant);
	public Long getCountPanoramas();
	public Long getCountPanoramas(int tenant);
	public Long getCountAllPanoramas();

    public int addLegacy(Member member);
    public String getLocationTag(int locationTagID);
    //public void removeUserFromCache(int memberID);
    
	
	/**
	 * Returns MemberId and EndpointId of the Invited User
	 * 
	 * @param inviteeRoomId
	 * @return
	 */
	public Member getInviteeDetails(int inviteeRoomId);
	
	/**
	 * Returns MemberName and EndpointGUID of the Inviter
	 * 
	 * @param inviterMemberId
	 * @param tenantId
	 * @return
	 */
	public Member getInviterDetails(int inviterMemberId);
	
    public Entity getOwnRoomDetails(int memberID);

    //Not used
    //public Member getMemberByNameAndGatewayController(String username, String gatewayControllerDns);
    
    /**
	 * Utility to set proxy to updating Member
	 * @param updatingMember
	 * @param proxyName
	 * @return Returns <code>true</code> - if proxyName is valid and set to updating member, otherwise returns <code>false</code>
	 */
	public boolean setProxyOfMember(Member updatingMember, String proxyName);
	
    /**
	 * Utility to set group to updating Member
	 * @param updatingMember
	 * @param groupName
	 * @return Returns <code>true</code> - if groupName is valid and set to updating member, otherwise returns <code>false</code>
	 */
	public boolean setGroupOfMember(Member updatingMember, String groupName);
	
	/**
	 * Utility to set tenant location tag to updating Member
	 * @param updatingMember
	 * @param locationTag
	 * @return Returns <code>true</code> - if locationTag is valid and set to updating member, otherwise returns <code>false</code>
	 */
	public boolean setTenantLocationTagOfMember(Member updatingMember, String locationTag);
	
	/**
	 * Utility to set MemberRole to updating Member
	 * @param updatingMember
	 * @param memberRole
	 * @return Returns <code>true</code> - if memberRole is valid and set to updating member, otherwise returns <code>false</code>
	 */
	public boolean setMemberRoleOfMember(Member updatingMember, String memberRole);

    // LOGIN HISTORY
    public int resetLoginFailureCount(int memberID);
	public int incrementLoginFailureCount(int memberID);
	public int getMemberID(String userName, int tenantID);
	public int getMemberLoginFailureCount(String userName, int tenantID);
    public int updateUserAsLocked(int memberID, int tenantID, String userName );
	public int markMembersAsInactive(int inactiveDaysLimit);
	public long getMemberCreationTime(int memberID, int tenantID);
	public int lockUserPasswordExpiry(int passwordExpiryDaysCount);
	public String getUsername(int memberID);
	public void deleteMemberPasswordHistory(int memberID);
	
	public Map<Integer, Integer> getPersonalRoomIds(List<Integer> memberIds);
    public Integer getPersonalRoomId(Integer memberId);
	
	/**
	 * Security role check: Tenant Admins and Operators Can Not Create Higher Privileged User Types
	 * @param user
	 * @param member
	 * @return
	 */
    public boolean isUserEligibleToCreateUpdateMember(User user, Member member);

    public int findMembersCount(String query, String queryField, List<Integer> memberTypes);

    public List<MemberMini> findMembers(int thisUserMemberID, String query, String queryField, List<Integer> memberTypes, int start, int limit, String sortBy, String sortDir);
    
    public List<Member> searchMembers(int tenantId, MemberFilter memberFilter);
    
    public int countMembers(int tenantId, MemberFilter memberFilter);
	public String getThumbNailImage(Integer tenantId, int memberID);
	//public void moveFile(Path source, int memberID, int tenantId) throws IOException;
	public void deleteUserImage(Integer tenantId, int memberID) throws IOException;
	String getDefaultThumbNailImage(Integer tenantId, int memberID);
	void uploadUserImage(byte[] thumbNail, int memberID, int tenantId) throws IOException;
	
	/**
	 * Update the field userImageAllowed in the member table.0-false, 1=true
	 * @param tenantID
	 * @param memberID
	 * @param userName
	 * @param userImageAllowed
	 * @return
	 */
	public int updateUserImageAlwdFlag(int tenantID, int memberID, String userName, int userImageAllowed);
	/**
	 * * Update the field userImageUploaded and lastModifiedDateExternal(making null)  in the member table.0-false, 1=true
	 * @param tenantID
	 * @param memberID
	 * @param userName
	 * @param userImageAllowed
	 * @return
	 */
	public int updateUserImageUploadedFlags(int tenantID, int memberID, String userName, int userImageAllowed);
	
	
	/**
	 * Update the LastModifiedDateExternal field for all members. Pass null in order to reset the flag. This will cause updating ldap attributes during authentication. No cache cleared during this operation as member table not cached,userdatacache is not impacted with this change
	 *  
	 * @param tenantID
	 * @param dateStr
	 * @return
	 */
	 public int updateAllMembersLastModifiedDateExt(int tenantID,String dateStr);
	void deleteImageFolder(Integer tenantId) ;
	public int updateMemberThumbnailTimeStamp(int memberID, Date thumbnailTimeStamp);


	public int deleteByCreationTimeBefore(Date deletionTime);
	
}
