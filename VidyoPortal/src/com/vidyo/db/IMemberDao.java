package com.vidyo.db;

import com.vidyo.bo.*;
import com.vidyo.bo.member.MemberEntity;
import com.vidyo.bo.member.MemberMini;
import com.vidyo.bo.passwordhistory.MemberPasswordHistory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMemberDao {
    public String getMemberPassword(int memberID);
    public List<Member> getMembers(int tenant, MemberFilter filter);
    public Long getCountMembers(int tenant, MemberFilter filter);
    public Long getCountSeats(int tenant);
    public Long getCountAllSeats();
    public Long getCountAllInstalls();
    public Long getCountInstalls(String tenant);
    public int updateSuper(Member superMember);
    public Member getMember(int tenant, int memberID);
    public Member getMemberByRoom(int tenant,int roomID);
    public Member getMemberNoRoom(int tenant, int memberID);
    public Member getMemberByName(int tenant, String userName);
    public Language getMemberLang(int tenant, int memberID);
    public int updateMember(int tenant, int memberID, Member member);
    public int enableUser(int tenantID, int memberID, int enable);
    public int insertMember(int tenant, Member member);
    public int deleteMember(int tenant, int memberID);
    public List<MemberRoles> getMemberRoles();
    public List<MemberRoles> getAllMemberRoles();
    public List<MemberRoles> getSamlMemberRoles();
    public Long getCountMemberRoles();
    public Long getCountSamlMemberRoles();
    public MemberRoles getMemberRoleByName(int tenant, String roleName);
    public boolean isMemberExistForUserName(int tenant, String userName, int memberID);
    public List<SpeedDial> getSpeedDial(int tenant, int memberID, SpeedDialFilter filter);
    public Long getCountSpeedDial(int tenant, int memberID);
    public int addSpeedDialEntry(int memberID, int roomID);
    public int removeSpeedDialEntry(int memberID, int roomID);
    public boolean isInSpeedDialEntry(int memberID, int roomID);
    public int updateMemberPassword(int tenant, Member member);
    public int updateMemberLanguage(int tenant, Member member);
    public int updateMemberDefaultEndpointGUID(int tenant, Member member);
    public int updateLegacy(int tenant, int memberID, Member member);
    public int updateMemberMode(int memberID, int modeID);

    public List<Entity> getContacts(int tenant, int memberID, EntityFilter filter, List<Integer> canCallTenantIds);
    public int getCountContacts(int tenant, int memberID, EntityFilter filter, List<Integer> canCallTenantIds);
    public Entity getContact(int tenant, int memberID);
    public int getJoinedConferenceRoomId(int memberID);
    public RoomIdConferenceType getJoinedConferenceRoomIdConferenceType(int memberID);
    public Guest getGuest(int tenant, int guestID);

    public List<InviteList> getOnlineMembers(int tenant, InviteListFilter filter);
    public Long getCountOnlineMembers(int tenant);
    
    public boolean isMemberActive(int memberID);
    public boolean isMemberAllowedToParticipate(int memberID);

    public List<Location> getLocationTags(int tenant);
    public Long getCountLocationTags(int tenant);
    public Location getLocationInTenantByTag(int tenant, String locationTag);

    public Long getCountExecutives(int tenant);
    public Long getCountAllExecutives();

	public Long getCountPanoramas(int tenant);
	public Long getCountAllPanoramas();

    public String getLocationTag(int locationTagID);

	/**
	 * Returns MemberId,EndpointId,EndpointGUID, RoleName of the Invited User
	 * 
	 * @param inviteeRoomId
	 * @param tenantId
	 * @return
	 */
	public Member getInviteeDetails(int inviteeRoomId, int tenantId);

	/**
	 * Returns MemberName and EndpointGUID of the Inviter
	 * 
	 * @param inviterMemberId
	 * @param tenantId
	 * @return
	 */
	public Member getInviterDetails(int inviterMemberId, int tenantId);
	
	/**
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberEntity getMemberEntity(int memberId);
	
	/**
	 * 
	 * @param tenant
	 * @param memberID
	 * @return
	 */
    public Entity getOwnRoomDetails(int tenant, int memberID);
    
    /**
     * Returns the list of super accounts based on the filter
     * @param filter
     * @return
     */
	public List<Member> getSupers(MemberFilter filter);
	
	/**
	 * Returns the count of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public Long getCountSupers(MemberFilter filter);
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
    public Member getSuper(int memberID);
    
    /**
     * 
     * @param member
     * @return
     */
	public int insertSuper(Member member);
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
	public int deleteSuper(int memberID);
	
	/**
	 * Empty method to clear userDataCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [MemberDaoJdbcImpl] else cache wont get cleared.
	 * 
	 * @param tenantId
	 * @param userName
	 * @return
	 */	
	public int deleteMember(int tenantId, String userName);
	
	/**
	 * Empty method to clear userDataCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [MemberDaoJdbcImpl] else cache wont get cleared.
	 * 
	 * @param tenantId
	 * @param userName
	 * @return
	 */	
	public int updateMember(int tenantId, String userName);

	//Not Used
	//public Member getMemberByNameAndGatewayController(String username, String gatewayControllerDns);
	
	/**
	 * Returns TenantId of the Member.
	 * 
	 * @param memberId
	 * @param tenantId
	 * @return
	 */
	public Member getMemberDetail(int memberId, int tenantId);
	
	/**
	 * Returns Member from only Member table based on MemberId and TenantId
	 * @param memberId
	 * @param tenantId
	 * @return
	 */
	public Member getMemberObj(int memberId, int tenantId);

    //****************************************** LOGIN HISTORY


    public int resetLoginFailureCount(int memberID);

    public int incrementLoginFailureCount(int memberID);


    public int getMemberID(String userName, int tenantID);

    public int getMemberLoginFailureCount(String userName, int tenantID);

    public int updateUserAsLocked(int memberID);

    public int markMembersAsInactive(int inactiveDaysLimit);

    public long getMemberCreationTime(int memberID, int tenantID);

	public int lockUserPasswordExpiry(int passwordExpiryDaysCount);

	public String getUsername(int memberID);

    public void saveMemberPasswordHistory(int memberID);
    public void deleteMemberAllPasswordHistory(int memberID);
    public List<MemberPasswordHistory> getMemberPasswordHistory(int memberID);

    public Map<Integer, Integer> getPersonalRoomIds(List<Integer> memberIds);
	public Integer getPersonalRoomId(Integer memberId);

    public int findMembersCount(String query, String queryField, List<Integer> memberTypes, List<Integer> allowedTenantIds);

    public List<MemberMini> findMembers(int thisUserMemberID, String query, String queryField, List<Integer> memberTypes, List<Integer> allowedTenantIds,
                                        int start, int limit, String sortBy, String sortDir);
    
    public List<Member> searchMembers(int tenantId, MemberFilter filter);
    
	public int getMembersCount(int tenantId, MemberFilter filter);
	public int updateUserImageAlwdFlag(int tenantID, int memberID, int userImageAllowed);
	public int updateUserImageUploadedFlags(int tenantID, int memberID, int userImageUploaded);
	public int updateMemberUserImageUploaded(int tenantID, int memberID, int userImageUploaded);
	public int updateAllMembersLastModifiedDateExt(int tenantID,  String dateStr);
	public int updateMemberThumbnailTimeStamp(int memberID, Date thumbnailUpdateTime);
	
}
