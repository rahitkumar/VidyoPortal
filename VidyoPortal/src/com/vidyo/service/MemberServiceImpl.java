package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.bo.member.MemberMini;
import com.vidyo.bo.passwordhistory.MemberPasswordHistory;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.repository.memberbak.MemberBAKRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.service.room.request.RoomUpdateRequest;
import com.vidyo.utils.ImageUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MemberServiceImpl implements IMemberService {

    protected static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
    private IMemberDao dao;
    private IRoomService room;
    private ITenantService tenantService;
    private IConferenceService conference;
    private IUserService user;
    private IGroupService groupService;
    private ISystemService systemService;

    private IServiceDao serviceDao;

    private String thumbNailLocation;

    @Autowired
    private MemberBAKRepository memberBAKRepository;

    public String getThumbNailLocation() {
        return thumbNailLocation;
    }

    public void setThumbNailLocation(String thumbNailLocation) {
        this.thumbNailLocation = thumbNailLocation;
    }

    /**
     * Regex to allow spaces between words and special characters [period, underscore and hyphen]
     */
    private static final String LEGACY_REGEX_USERNAME = "^[[^\\p{Punct}]&&[^\\t\\r\\n\\v\\f]][[[^\\p{Punct}]&&[^\\t\\r\\n\\v\\f]][_\\-\\.\\@]]*$";

    public void setDao(IMemberDao dao) {
        this.dao = dao;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

    public void setConference(IConferenceService conference) {
        this.conference = conference;
    }

    public void setUser(IUserService user) {
        this.user = user;
    }

    public void setGroupService(IGroupService groupService) {
        this.groupService = groupService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

    @Deprecated
    public List<Member> getMembers(MemberFilter filter) {
        List<Member> list = this.dao.getMembers(TenantContext.getTenantId(), filter);
        return list;
    }

    public Long getCountMembers(MemberFilter filter) {
        Long number = this.dao.getCountMembers(TenantContext.getTenantId(), filter);
        return number;
    }

    public int updateSuper(Member member) throws AccessRestrictedException{
        User loggedInUser = user.getLoginUser();

        if(loggedInUser.getMemberID() == member.getMemberID() && member.getActive() != 1) {
            String errMsg = "Logged in Super cannot inactivate himself. Logged in super memberID = " + member.getMemberID();
            logger.error(errMsg);
            return -5;
        }

        // Empty method invocation to clear cache
        dao.updateMember(member.getTenantID(),
                member.getUsername() != null ? member.getUsername()
                        .toLowerCase(Locale.ENGLISH) : null);
    /*
     * When the user is loaded, tenant context tenantid + username
     * combination is cached
     */
        List<Integer> tenantIds = tenantService.getAllTenantIds();
    /*
     *  Clear the cache with all tenantId + username combination - as we
     *  don't know which tenanturl has been used by the super
     */
        for (Integer tenantId : tenantIds) {
            dao.updateMember(tenantId, member.getUsername() != null ? member
                    .getUsername().toLowerCase(Locale.ENGLISH) : null);
        }
        if (member.getPassword() != null) {
            this.dao.saveMemberPasswordHistory(member.getMemberID());
        }

        int updatedRecCount = 0;
        try {
            updatedRecCount = this.dao.updateSuper(member);
        } catch(DataAccessException e) {
            logger.error("Super for memberID = " + member.getMemberID() + " is not updated. The error message is : " + e.getMessage());
        }
        return updatedRecCount;

    }

    public Member getMember(int memberID) {
        Member member = null;
        int tenantId = TenantContext.getTenantId();
        try {
            member = this.dao.getMember(tenantId, memberID);
        } catch(Exception e) {
            logger.warn("Member is not found for tenantId = " + tenantId + " and member = " + memberID);
        }
        return member;
    }

    public Member getMember(int tenantID, int memberID) {
        Member member = null;
        try {
            member = this.dao.getMember(tenantID, memberID);
        } catch(Exception e) {
            logger.warn("Member is not found for tenantId = " + tenantID + " and member = " + memberID);
        }
        return member;
    }

    public Member getMemberByRoom(int roomID) {
        Member member = this.dao.getMemberByRoom(TenantContext.getTenantId(), roomID);
        return member;
    }

    public Member getMemberNoRoom(int memberID) {
        Member member = this.dao.getMemberNoRoom(TenantContext.getTenantId(), memberID);
        return member;
    }

    public Member getMemberByName(String userName) {
        Member member = this.dao.getMemberByName(TenantContext.getTenantId(), userName);
        return member;
    }

    public Language getMemberLang(int memberID) {
        Language lang = this.dao.getMemberLang(TenantContext.getTenantId(), memberID);
        return lang;
    }

    public Member getMemberByName(String userName, String tenantName) {
        Tenant local_tenant = this.tenantService.getTenant(tenantName);
        Member member = this.dao.getMemberByName(local_tenant.getTenantID(), userName);
        return member;
    }

    public Member getMemberByName(String userName, int tenantID) {
        Member member = null;
        try {
            member = this.dao.getMemberByName(tenantID, userName);
        } catch (Exception e) {
            logger.warn("Member is not found for tenantId = " + tenantID + " and userName = " + userName);
        }
        return member;
    }

    @Deprecated
    public int updateMember(int memberID, Member member) throws AccessRestrictedException {
        return updateMember(TenantContext.getTenantId(), memberID, member);
    }

    public int updateMember(int tenantID, int memberID, Member member) throws AccessRestrictedException {
        int rc = 0;
        updateMemberPreProcessing(tenantID, memberID, member);
        if (member.getPassword() != null) {
            this.dao.saveMemberPasswordHistory(memberID);
        }
        try {
            rc = this.dao.updateMember(tenantID, memberID, member);
        } catch (DataAccessException e) {
            logger.error("Update Member failed {}, Error - {}", memberID, e.getMessage());
        }
        updateMemberPostProcessing(tenantID, memberID, member);

        return rc;
    }
    public int updateUserImageAlwdFlag(int tenantID, int memberID, String userName, int userImageAllowed) {
        int rc = 0;
        try {
            rc = this.dao.updateUserImageAlwdFlag(tenantID, memberID, userImageAllowed);
            // Dummy method invocation for cache clearing interceptors to work on
            this.dao.updateMember(tenantID, userName != null ? userName.toLowerCase(Locale.ENGLISH) : null);
        } catch (DataAccessException e) {
            logger.error("updateUserImageAlwdFlag User failed {}, {}, Error - {}", memberID, userImageAllowed, e.getMessage());
        }

        return rc;
    }
    public int updateUserImageUploadedFlags(int tenantID, int memberID, String userName, int userImageUploaded) {
        int rc = 0;
        try {
            rc = this.dao.updateUserImageUploadedFlags(tenantID, memberID, userImageUploaded);
            // Dummy method invocation for cache clearing interceptors to work on
            this.dao.updateMember(tenantID, userName != null ? userName.toLowerCase(Locale.ENGLISH) : null);
        } catch (DataAccessException e) {
            logger.error("updateUserImageUploadedFlags User failed {}, {}, Error - {}", memberID, userImageUploaded, e.getMessage());
        }

        return rc;
    }
    public int updateMemberUserImageUploaded(int tenantID, int memberID, String userName, int userImageUploaded) {
        int rc = 0;
        try {
            rc = this.dao.updateMemberUserImageUploaded(tenantID, memberID, userImageUploaded);
            // Dummy method invocation for cache clearing interceptors to work on
            this.dao.updateMember(tenantID, userName != null ? userName.toLowerCase(Locale.ENGLISH) : null);
        } catch (DataAccessException e) {
            logger.error("updateMemberUserImageUploaded User failed {}, {}, Error - {}", memberID, userImageUploaded, e.getMessage());
        }

        return rc;
    }
    public int updateAllMembersLastModifiedDateExt(int tenantID, String emptyDateStr) {
        int rc = 0;
        try {
            rc = this.dao.updateAllMembersLastModifiedDateExt(tenantID, emptyDateStr);

        } catch (DataAccessException e) {
            logger.error("updateAllMembersLastModifiedDateExt User failed {}, {}, Error - {}",  emptyDateStr, e.getMessage());
        }

        return rc;
    }


    public int enableUser(int tenantID, int memberID, String userName, int enable) {
        int rc = 0;
        try {
            rc = this.dao.enableUser(tenantID, memberID, enable);
            // Dummy method invocation for cache clearing interceptors to work on
            this.dao.updateMember(tenantID, userName != null ? userName.toLowerCase(Locale.ENGLISH) : null);
        } catch (DataAccessException e) {
            logger.error("Enable User failed {}, {}, Error - {}", memberID, enable, e.getMessage());
        }

        return rc;
    }

    /**
     * Clears the member cache and updates the scheduled room group<br>
     * if the group of the member has been changed.
     *
     * @param tenantId
     * @param memberId
     */
    private void updateMemberPreProcessing(int tenantId, int memberId, Member modifiedMember) throws AccessRestrictedException {
        Member oldMember = dao.getMember(tenantId, memberId);

        if (oldMember != null && (oldMember.getTenantID() != tenantId || oldMember.getRoleID() == MemberRoleEnum.SUPER.getMemberRoleID())) {
            String errMsg = "Trying to access Unauthorized Member - tenantID = " + tenantId + " memberID = " + memberId;
            logger.error(errMsg);
            throw new AccessRestrictedException(errMsg);
        }

        if(oldMember != null) {
            // Dummy method invocation for cache clearing interceptors to work on
            dao.updateMember(tenantId, oldMember.getUsername() != null ? oldMember.getUsername().toLowerCase(Locale.ENGLISH) : null);
            // Update the scheduled room group if changed
            if(oldMember.getGroupID() != modifiedMember.getGroupID()) {
                RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
                roomUpdateRequest.setMemberId(memberId);
                roomUpdateRequest.setRoomType("Scheduled");
                roomUpdateRequest.setNewGroupId(modifiedMember.getGroupID());
                // no need to validate the return count here
                room.updateGroupByMember(roomUpdateRequest);
            }
        }
    }

    private void updateMemberPostProcessing(int tenantID, int memberID, Member member) {

        if (member != null) {
            // unbind user from endpoint if password was changed
            if (member.getPassword() != null) {
                int endpointID = this.conference.getEndpointIDForMemberID(memberID);
                if (endpointID != 0) {
                    String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
                    if (endpointGUID != null && !endpointGUID.equalsIgnoreCase("")) {
                        this.conference.unbindUserFromEndpoint(endpointGUID, UserUnbindCode.PASSWORD_CHANGED);
                    }
                }
                // change PAK for user in DB
                this.user.generatePAKforMember(member.getMemberID());
            } else if (member.getActive() == 0) {
                int endpointID = this.conference.getEndpointIDForMemberID(memberID);
                if (endpointID != 0) {
                    String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
                    if (endpointGUID != null && !endpointGUID.equalsIgnoreCase("")) {
                        this.conference.unbindUserFromEndpoint(endpointGUID, UserUnbindCode.ACCOUNT_DISABLED);
                    }
                }
                // change PAK for user in DB
                this.user.generatePAKforMember(member.getMemberID());
            } else if (member.getAllowedToParticipate() == 0) {
                int endpointID = this.conference.getEndpointIDForMemberID(memberID);
                if (endpointID != 0) {
                    String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
                    if (endpointGUID != null && !endpointGUID.equalsIgnoreCase("")) {
                        this.conference.unbindUserFromEndpoint(endpointGUID, UserUnbindCode.PARTICIPATION_DISABLED);
                    }
                }
                // change PAK for user in DB
                this.user.generatePAKforMember(member.getMemberID());
            }
        }

    }

    public int insertMember(Member member) {
        int rc = this.dao.insertMember(TenantContext.getTenantId(), member);
        dao.updateMember(TenantContext.getTenantId(), member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);
        return rc;
    }

    @Transactional
    public int deleteMember(int tenantID, int memberID) throws AccessRestrictedException {
        Member member = this.getMember(memberID);
        if (member == null || member.getTenantID() != tenantID || member.getRoleID() == MemberRoleEnum.SUPER.getMemberRoleID()) {
            String errMsg = "Trying to access Unauthorized Member - tenantID = " + tenantID + " memberID = " + memberID;
            logger.error(errMsg);
            throw new AccessRestrictedException(errMsg);
        }

        // unbind user from endpoint
        int endpointID = this.conference.getEndpointIDForMemberID(memberID);
        if (endpointID != 0) {
            String endpointGUID = this.conference.getGUIDForEndpointID(endpointID, "D");
            if (endpointGUID != null && !endpointGUID.equalsIgnoreCase("")) {
                this.conference.unbindUserFromEndpoint(endpointGUID, UserUnbindCode.ACCOUNT_DELETED);
            }
        }
        dao.deleteMember(tenantID, member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);
        room.deleteRoomsByMember(memberID);

        //VPTL-8008 - Removing the below inefficient code

        // delete all rooms where this member is Owner before delete member
        /*List<Long> listOfroomID = this.room.getRoomsForOwnerID(memberID);
        for (Long aLong : listOfroomID) {
            this.room.deleteRoom(aLong.intValue());
        }*/
        memberBAKRepository.deleteByMemberId(memberID);

        int rc = this.dao.deleteMember(tenantID, memberID);
        try {
            deleteImage(thumbNailLocation, tenantID, memberID);
        } catch (IOException e) {
            //No need to throw error as failure of this operation shouldnt hinder user experience. Also no impact.
            logger.error("Error occurred while deleting image",e);

        }
        return rc;
    }

    private void deleteImage(String thumbNailLocation2, int tenantID, int memberID) throws IOException {
        Path imageFile = Paths.get(thumbNailLocation + "/" + tenantID + "/" + memberID);
        ImageUtils.deleteImage(imageFile);

    }

    public List<MemberRoles> getMemberRoles() {
        List<MemberRoles> list = this.dao.getMemberRoles();
        return list;
    }

    public List<MemberRoles> getSamlMemberRoles() {
        List<MemberRoles> list = this.dao.getSamlMemberRoles();
        return list;
    }


    public MemberRoles getMemberRoleByRoleId(int roleID) {
        List<MemberRoles> list = this.dao.getAllMemberRoles();
        MemberRoles thisRole = null;
        for(MemberRoles role: list){
            if(role.getRoleID() == roleID)  {
                thisRole = role;
                break;
            }
        }
        return thisRole;
    }
    public Long getCountMemberRoles() {
        Long number = this.dao.getCountMemberRoles();
        return number;
    }

    public Long getCountSamlMemberRoles() {
        Long number = this.dao.getCountSamlMemberRoles();
        return number;
    }

    public MemberRoles getMemberRoleByName(String roleName) {
        int tenantId = TenantContext.getTenantId();
        MemberRoles role = null;
        try {
            role = this.dao.getMemberRoleByName(tenantId, roleName);
        } catch (Exception e) {
            logger.warn("Member role is not found for tenantId = " + tenantId + " and role = " + roleName);
        }
        return role;
    }

    public boolean isMemberExistForUserName(String userName, int memberID) {
        boolean rc = this.dao.isMemberExistForUserName(TenantContext.getTenantId(), userName, memberID);
        return rc;
    }

    public boolean isSuperExistForUserName(String userName) {
        boolean rc = this.dao.isMemberExistForUserName(1, userName, 0);
        return rc;
    }

    public boolean isMemberActive(int memberID) {
        boolean rc = this.dao.isMemberActive(memberID);
        return rc;
    }

    public boolean isMemberAllowedToParticipate(int memberID) {
        boolean rc = this.dao.isMemberAllowedToParticipate(memberID);
        return rc;
    }

    public List<SpeedDial> getSpeedDial(int memberID, SpeedDialFilter filter) {
        List<SpeedDial> rc = this.dao.getSpeedDial(TenantContext.getTenantId(), memberID, filter);
        return rc;
    }

    public Long getCountSpeedDial(int memberID) {
        Long number = this.dao.getCountSpeedDial(TenantContext.getTenantId(), memberID);
        return number;
    }

    public int addSpeedDialEntry(int memberID, int roomID) {
        int rc = this.dao.addSpeedDialEntry(memberID, roomID);
        return rc;
    }

    public int removeSpeedDialEntry(int memberID, int roomID) {
        int rc = this.dao.removeSpeedDialEntry(memberID, roomID);
        return rc;
    }

    public boolean isInSpeedDialEntry(int memberID, int roomID) {
        boolean rc = this.dao.isInSpeedDialEntry(memberID, roomID);
        return rc;
    }

    /*
     *Returns -1: Password does not meet requirements
     *Returns -2: The password of imported user cannot be updated
     *Returns -3: Exception while encoding the member Password
     * or else returns number of Rows affected (should be 1)
     */
    public int updateMemberPassword(Member member){
        if(member.getImportedUsed() == 1) {
            logger.debug("The password of imported user cannot be updated. UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED;
        }
        if(!isValidMemberPassword(member.getPassword(), member.getMemberID())){
            logger.debug("Password does not meet requirements. UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS;
        }
        try{
            member.setPassword(PasswordHash.createHash(member.getPassword()));
        }catch (Exception ignored){
            logger.error("Exception while encoding the member Password: UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_ENCODING_EXCEPTION;
        }
        if (member != null) {
            dao.updateMember(TenantContext.getTenantId(), member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);
        }
        this.saveMemberPasswordHistory( member.getMemberID());
        int rc = this.dao.updateMemberPassword(TenantContext.getTenantId(), member);
        updateMemberPostProcessing(member.getTenantID(), member.getMemberID(), member);
        return rc;
    }

    public int updateSuperPassword(Member member){
        if(member.getImportedUsed() == 1) {
            logger.debug("The password of imported user cannot be updated. UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED;
        }
        if(!isValidMemberPassword(member.getPassword(), member.getMemberID())){
            logger.debug("Password does not meet requirements. UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS;
        }
        try{
            member.setPassword(PasswordHash.createHash(member.getPassword()));
        }catch (Exception ignored){
            logger.error("Exception while encoding the member Password: UserName=" + member.getUsername());
            return MemberManagementResponse.PASSWORD_ENCODING_EXCEPTION;
        }
        if (member != null) {
            List<Integer> tenantIds = tenantService.getAllTenantIds();
        /*
         *  Clear the cache with all tenantId + username combination - as we
         *  don't know which tenanturl has been used by the super
         */
            for (Integer tenantId : tenantIds) {
                dao.updateMember(tenantId, member.getUsername() != null ? member
                        .getUsername().toLowerCase(Locale.ENGLISH) : null);
            }
        }

        this.saveMemberPasswordHistory( member.getMemberID());
        int rc = this.dao.updateMemberPassword(1, member);
        return rc;
    }

    public int updateMemberLanguage(Member member) {
        int rc = this.dao.updateMemberLanguage(TenantContext.getTenantId(), member);
        return rc;
    }

    public int updateMemberDefaultEndpointGUID(Member member) {
        int rc = this.dao.updateMemberDefaultEndpointGUID(TenantContext.getTenantId(), member);
        return rc;
    }

    public int updateLegacy(int tenantId, int memberID, Member member) {
        int rc = 0;
        try {
            rc = this.dao.updateLegacy(TenantContext.getTenantId(), memberID, member);
        } catch(DataAccessException e) {
            logger.warn("Legacy is not updated for tenantId = " + tenantId + " and member = " + memberID);
        }
        return rc;
    }

    public int updateMemberMode(int memberID, int modeID) {
        int rc = this.dao.updateMemberMode(memberID, modeID);
        return rc;
    }

    public List<Entity> getContacts(int memberID, EntityFilter filter) {
        List<Integer> canCallToTenantIds = tenantService
                .canCallToTenantIds(TenantContext.getTenantId());
        List<Entity> rc = this.dao.getContacts(TenantContext.getTenantId(), memberID, filter, canCallToTenantIds);
        return rc;
    }

    public int getCountContacts(int memberID, EntityFilter filter) {
        List<Integer> canCallToTenantIds = tenantService
                .canCallToTenantIds(TenantContext.getTenantId());
        int rc = this.dao.getCountContacts(TenantContext.getTenantId(), memberID, filter, canCallToTenantIds);
        return rc;
    }

    public Entity getContact(int memberID) {
        Entity rc = null;
        try {
            rc = this.dao.getContact(TenantContext.getTenantId(), memberID);
        } catch (Exception ignored) {
            logger.error("error while retrieving getContact", ignored);
            throw new BadCredentialsException("Bad credentials", null);
        }
        return rc;
    }

    /*
    * Get the conference room ID, member has joined
     */
    public int getJoinedConferenceRoomId(int memberID) {
        int roomID = 0;
        try {
            roomID = this.dao.getJoinedConferenceRoomId(memberID);
        } catch (Exception ignored) {
            throw new BadCredentialsException("Bad credentials", null);
        }
        return roomID;
    }

    public RoomIdConferenceType getJoinedConferenceRoomIdConferenceType(int memberID){

        RoomIdConferenceType roomConfType = null;
        try {
            roomConfType = this.dao.getJoinedConferenceRoomIdConferenceType(memberID);
        } catch (Exception ignored) {
            throw new BadCredentialsException("Bad credentials", null);
        }
        return roomConfType;
    }

    public Guest getGuest(int guestID) {
        Guest rc = this.dao.getGuest(TenantContext.getTenantId(), guestID);
        return rc;
    }

    public String getTenantPrefix() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getTenantPrefix();
    }

    public int getLicensedSeats() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getSeats();
    }

    public int getLicensedSeats(int tenant) {
        Tenant rc = this.tenantService.getTenant(tenant);
        return rc.getSeats();
    }

    public Long getCountSeats() {
        Long number = this.dao.getCountSeats(TenantContext.getTenantId());
        return number;
    }

    public Long getCountSeats(int tenant) {
        Long number = this.dao.getCountSeats(tenant);
        return number;
    }

    /**
     * Use the API from MemberServiceImpl
     */
    @Deprecated
    public Long getCountAllSeats() {
        Long number = this.dao.getCountAllSeats();
        return number;
    }

    public Long getCountAllInstalls() {
        Long number = this.dao.getCountAllInstalls();
        return number;
    }

    public int getLicensedPorts() {
        return this.tenantService.getTenant(TenantContext.getTenantId()).getPorts();
    }

    public int getLicensedPorts(int tenantId) {
        return this.tenantService.getTenant(tenantId).getPorts();
    }

    public int getLicensedInstalls(){
        return this.tenantService.getTenant(TenantContext.getTenantId()).getInstalls();
    }

    public int getLicensedInstalls(int tenantId){
        return this.tenantService.getTenant(tenantId).getInstalls();
    }

    public Long getCountInstalls() {
        Long number = this.dao.getCountInstalls(this.tenantService.getTenant(TenantContext.getTenantId()).getTenantName());
        return number;
    }

    public Long getCountInstalls(int tenantId) {
        Long number = 0L;
        Tenant searchingTenant = this.tenantService.getTenant(tenantId);
        if(searchingTenant != null) {
            number = this.dao.getCountInstalls(searchingTenant.getTenantName());
        }
        return number;
    }

    public List<Proxy> getProxies() {
        List<Proxy> list = serviceDao.getProxies(TenantContext.getTenantId());
        return list;
    }

    public Long getCountProxies() {
        Long number = serviceDao.getCountProxies(TenantContext.getTenantId());
        return number;
    }

    public Proxy getProxyByName(String proxyName) {
        Proxy proxy = serviceDao.getProxyByName(TenantContext.getTenantId(), proxyName);
        return proxy;
    }

    @Deprecated
    //TODO - remove unused API
    public List<InviteList> getOnlineMembers(InviteListFilter filter){
        List<InviteList> list = this.dao.getOnlineMembers(TenantContext.getTenantId(), filter);
        return list;
    }

    public Long getCountOnlineMembers(){
        Long number = this.dao.getCountOnlineMembers(TenantContext.getTenantId());
        return number;
    }

    public List<Location> getLocationTags() {
        List<Location> list = this.dao.getLocationTags(TenantContext.getTenantId());
        return list;
    }

    public Long getCountLocationTags() {
        Long number = this.dao.getCountLocationTags(TenantContext.getTenantId());
        return number;
    }

    public Location getLocationInTenantByTag(String locationTag) {
        Location loc = this.dao.getLocationInTenantByTag(TenantContext.getTenantId(), locationTag);
        return loc;
    }

    public int getLicensedExecutive() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getExecutives();
    }

    public int getLicensedExecutive(int tenant) {
        Tenant rc = this.tenantService.getTenant(tenant);
        return rc.getExecutives();
    }

    public Long getCountExecutives() {
        Long number = this.dao.getCountExecutives(TenantContext.getTenantId());
        return number;
    }

    public Long getCountExecutives(int tenant) {
        Long number = this.dao.getCountExecutives(tenant);
        return number;
    }

    public Long getCountAllExecutives() {
        Long number = this.dao.getCountAllExecutives();
        return number;
    }

    public int getLicensedPanorama() {
        Tenant rc = this.tenantService.getTenant(TenantContext.getTenantId());
        return rc.getPanoramas();
    }

    public int getLicensedPanorama(int tenant) {
        Tenant rc = this.tenantService.getTenant(tenant);
        return rc.getPanoramas();
    }

    public Long getCountPanoramas() {
        Long number = this.dao.getCountPanoramas(TenantContext.getTenantId());
        return number;
    }

    public Long getCountPanoramas(int tenant) {
        Long number = this.dao.getCountPanoramas(tenant);
        return number;
    }

    public Long getCountAllPanoramas() {
        Long number = this.dao.getCountAllPanoramas();
        return number;
    }


    public int addLegacy(Member member) {
        int id = this.dao.insertMember(TenantContext.getTenantId(), member);
        return id;
    }


    public String getLocationTag(int locationTagID) {
        String tag = this.dao.getLocationTag(locationTagID);
        return tag;
    }

    /**
     * Returns MemberId and EndpointId of the Invited User
     *
     * @param inviteeRoomId
     * @return
     */
    public Member getInviteeDetails(int inviteeRoomId) {
        Member member = dao.getInviteeDetails(inviteeRoomId, TenantContext.getTenantId());
        return member;
    }

    /**
     * Returns MemberName and EndpointGUID of the Inviter
     *
     * @param inviterMemberId
     * @paramtenantId
     * @return
     */
    public Member getInviterDetails(int inviterMemberId) {
        Member member = dao.getInviterDetails(inviterMemberId, TenantContext.getTenantId());
        return member;
    }

    public Entity getOwnRoomDetails(int memberID) {
        return dao.getOwnRoomDetails(TenantContext.getTenantId(), memberID);
    }

    /* Not Used
    public Member getMemberByNameAndGatewayController(String username, String gatewayControllerDns) {
      return dao.getMemberByNameAndGatewayController(username, gatewayControllerDns);
    }*/
    public String getMemberPassword(int memberID) {
        return dao.getMemberPassword(memberID);
    }

    /**
     *
     * @param memberID
     * @return
     */
    @Override
    public int resetLoginFailureCount(int memberID) {
        return dao.resetLoginFailureCount(memberID);
    }

    /**
     *
     * @param memberID
     * @return
     */
    @Override
    public int incrementLoginFailureCount(int memberID) {
        return dao.incrementLoginFailureCount(memberID);
    }

    /**
     *
     * @param userName
     * @param tenantID
     * @return
     */
    @Override
    public int getMemberID(String userName, int tenantID) {
        int id = 0;
        try {
            id = dao.getMemberID(userName, tenantID);
        } catch (DataAccessException dae) {
            logger.warn("User not found in the system {}, {}", userName, tenantID);
        }
        return id;
    }

    /**
     *
     * @param userName
     * @param tenantID
     * @return
     */
    @Override
    public int getMemberLoginFailureCount(String userName, int tenantID) {
        int failureCount = -1;
        try {
            failureCount = dao.getMemberLoginFailureCount(userName, tenantID);
        } catch (DataAccessException dae) {
            logger.warn("User not found in the system {}, {}", userName, tenantID);
            try {
                failureCount = dao.getMemberLoginFailureCount(userName, 1);
            } catch (DataAccessException e) {
                logger.warn("User not found in the system {}, {}", userName, tenantID);
            }
        }
        return failureCount;
    }



    @Override
    public int updateUserAsLocked(int memberID, int tenantID, String userName ) {
        dao.updateMember(tenantID, userName != null ? userName.toLowerCase(Locale.ENGLISH) : null);

        return dao.updateUserAsLocked(memberID);
    }

    /**
     *
     * @param memberID
     */
    private void saveMemberPasswordHistory(int memberID) {
        dao.saveMemberPasswordHistory(memberID);
    }

    /**
     * Updates the Users as Inactive if the last login time has passed beyond
     * the inactivity limit
     *
     * @param inactiveDaysLimit
     * @return
     */
    @Override
    public int markMembersAsInactive(int inactiveDaysLimit) {
        return dao.markMembersAsInactive(inactiveDaysLimit);
    }

    /**
     *
     * @param memberID
     * @param tenantID
     * @return
     */
    @Override
    public long getMemberCreationTime(int memberID, int tenantID) {
        return dao.getMemberCreationTime(memberID, tenantID);
    }

    /**
     *
     * @param passwordExpiryDaysCount
     * @return
     */
    @Override
    public int lockUserPasswordExpiry(int passwordExpiryDaysCount) {
        return dao.lockUserPasswordExpiry(passwordExpiryDaysCount);
    }

    /**
     * Returns the username for the member based on memberId.
     * This doesn't restrict the access to member based on Tenant.
     * Returns null if the member is not found.
     * @param memberID
     * @return username String
     */
    @Override
    public String getUsername(int memberID) {
        String username = null;
        try {
            username = dao.getUsername(memberID);
        } catch (DataAccessException dae) {
            logger.warn("Retrieve username for the member failed , Cannot find member with id {}", memberID);
        }
        return username;
    }

    /**
     *
     * @param memberID
     */
    @Override
    public void deleteMemberPasswordHistory(int memberID) {
        dao.deleteMemberAllPasswordHistory(memberID);
    }


    /*
   -at least 15 characters
   -at least 2 uppercase alphabetic character
   -at least 2 lowercase alphabetic character
   -at least 2 numeric characters
   -at least 2 non-alphanumeric (special) character
   -no consecutive repeating characters. 2 is OK, 3 is not
   -password can only be changed once within 24 hours.
   Also they must be different from the last 10 passwords used by that account.
   */
    public boolean isValidDisaMemberPassword(String password, int memberID) {
        // -at least 15 characters
        if (!password.matches(".{15,}")) {
            return false;
        }

        if (!password.matches(".*[\\w].*[\\w].*")) {
            return false;
        }

        // -at least 2 uppercase alphabetic character
        if (!password.matches(".*[A-Z].*[A-Z].*")) {
            return false;
        }
        // -at least 2 lowercase alphabetic character
        if (!password.matches(".*[a-z].*[a-z].*")) {
            return false;
        }
        // -at least 2 numeric characters
        if (!password.matches(".*[0-9].*[0-9].*")) {
            return false;
        }

        // -at least 2 non-alphanumeric (special) character
        if (!password.matches(".*[\\W].*[\\W].*")) {
            return false;
        }
        // -no consecutive repeating characters. 2 is OK, 3 is not
        if (!password.matches("(?:(?!(.)\\1\\1.*).)*")) {
            return false;
        }
        // if it's a new member do not go farther
        if(memberID == 0) {
            return true;
        }
        // Also they must be different from the last 10 passwords used by that account.
        String encriptedPassword=null;
        try{
            encriptedPassword = SHA1.enc(password);
        }catch (java.lang.Exception ignored) {
            logger.error("Exception while encoding the member Password memberID=" + memberID);
            return false;
        }

        String currentPassword = dao.getMemberPassword(memberID);
        // Check the current password
        try {
            if (encriptedPassword.equalsIgnoreCase(currentPassword)
                    || currentPassword.contains(":") && PasswordHash.validatePassword(password, currentPassword)) {
                logger.warn("New password same as the old password");
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error while validating password hash ", e);
        }

        List<MemberPasswordHistory> passwordHistories = this.dao.getMemberPasswordHistory(memberID);
        //Check the last password change time
        if(passwordHistories != null && passwordHistories.size() > 0) {
            long lastChangeTime = passwordHistories.get(0).getChangeTime().getTime();
            long currentTime = Calendar.getInstance().getTimeInMillis();
            long timeDiffHrs = (currentTime - lastChangeTime)/(1000 * 60 * 60);
            if(timeDiffHrs < 24) {
                return false;
            }
        }

        // Check the password histories
        for (MemberPasswordHistory passwordHistory : passwordHistories) {
            try {
                if (encriptedPassword.equalsIgnoreCase(passwordHistory
                        .getPassword())
                        || ((passwordHistory.getPassword().contains(":") && PasswordHash.validatePassword(password,
                        passwordHistory.getPassword())))) {
                    logger.warn("New password same as the old password");
                    return false;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.error("Error while validating password hash ", e);
            }
        }

        return true;

    }

    public boolean isValidMemberPassword(String password, int memberID){
        String passwordRuleName = null;
        Configuration conf = null;
        try {
            conf = systemService.getUserPasswordRule();
        } catch (Exception ignored) {
        }
        if(conf != null) {
            passwordRuleName = conf.getConfigurationValue();
        }
        boolean retVal = true;
        if(passwordRuleName != null && passwordRuleName.equalsIgnoreCase("DISA")){
            retVal = isValidDisaMemberPassword(password, memberID);
        }

        return retVal;
    }

    /**
     * Utility to validate if the legacy username conforms to the standards
     *
     * @param legacyUsername
     * @return
     */
    public static boolean isValidLegacyUsername(String legacyUsername) {
        return legacyUsername.matches(LEGACY_REGEX_USERNAME);
    }

    /**
     * Utility to set proxy to updating Member
     * @param updatingMember
     * @param proxyName
     * @return Returns <code>true</code> - if proxyName is valid and set to updating member, otherwise returns <code>false</code>
     */
    @Override
    public boolean setProxyOfMember(Member updatingMember, String proxyName) {
        boolean retVal = true;
        try {
            Proxy tenantProxy = getProxyByName(proxyName);
            if (tenantProxy != null) {
                updatingMember.setProxyID(tenantProxy.getProxyID());
                updatingMember.setProxyName(tenantProxy.getProxyName());
            } else {
                retVal = false;
            }
        } catch (Exception ignored) {
            logger.error("Proxy " + proxyName + " is not found");
            retVal = false;
        }

        return retVal;
    }

    /**
     * Utility to set group to updating Member
     * @param updatingMember
     * @param groupName
     * @return Returns <code>true</code> - if groupName is valid and set to updating member, otherwise returns <code>false</code>
     */
    @Override
    public boolean setGroupOfMember(Member updatingMember, String groupName) {
        boolean retVal = true;
        try {
            Group tenantGroup = groupService.getGroupByName(groupName);
            updatingMember.setGroupID(tenantGroup.getGroupID());
            updatingMember.setGroupName(tenantGroup.getGroupName());
        } catch (Exception ignored) {
            logger.error("Group " + groupName + " is not found");
            retVal = false;
        }

        return retVal;
    }

    /**
     * Utility to set tenant location tag to updating Member
     * @param updatingMember
     * @param locationTag
     * @return Returns <code>true</code> - if locationTag is valid and set to updating member, otherwise returns <code>false</code>
     */
    @Override
    public boolean setTenantLocationTagOfMember(Member updatingMember, String locationTag) {
        boolean retVal = true;
        try {
            Location tenantLocationTag = getLocationInTenantByTag(locationTag);
            if (tenantLocationTag != null) {
                updatingMember.setLocationID(tenantLocationTag.getLocationID());
                updatingMember.setLocationTag(tenantLocationTag.getLocationTag());
            } else {
                retVal = false;
            }
        } catch(Exception ignored) {
            logger.error("LocationTag " + locationTag + " is not found");
            retVal = false;
        }

        return retVal;
    }

    /**
     * Utility to set MemberRole to updating Member
     * @param updatingMember
     * @param memberRole
     * @return Returns <code>true</code> - if memberRole is valid and set to updating member, otherwise returns <code>false</code>
     */
    @Override
    public boolean setMemberRoleOfMember(Member updatingMember, String memberRole) {
        boolean retVal = true;
        try {
            MemberRoles role = getMemberRoleByName(memberRole);
            if (role != null) {
                updatingMember.setRoleID(role.getRoleID());
                updatingMember.setRoleName(role.getRoleName());
            } else {
                retVal = false;
            }
        } catch(Exception ignored) {
            logger.error("MemberRole " + memberRole + " is not found");
            retVal = false;
        }

        return retVal;
    }

    public Map<Integer, Integer> getPersonalRoomIds(List<Integer> memberIds) {
        Map<Integer, Integer> memberRoomMap = null;
        try {
            memberRoomMap = dao.getPersonalRoomIds(memberIds);
        } catch (Exception e) {
            logger.warn("Exception while getting memberRoomMap");
        }

        return memberRoomMap;
    }

    public Integer getPersonalRoomId(Integer memberId) {
        try {
            return dao.getPersonalRoomId(memberId);
        } catch (Exception e) {
            logger.warn("Exception while getting getPersonalRoomId");
        }

        return null;
    }

    /**
     * Security role check: Tenant Admins and Operators Can Not Create Higher Privileged User Types
     * @param user
     * @param member
     * @return
     */
    @Override
    public boolean isUserEligibleToCreateUpdateMember(User user, Member member) {
        /*
         *  START Security role check: Tenant Admins and Operators Can Not Create Higher Privileged User Types
        */

        boolean retVal = true;

        if(user.getUserRole().equalsIgnoreCase("ROLE_SUPER") ){
            // Super can create/Edit all types of User
        } else if(user.getUserRole().equalsIgnoreCase("ROLE_ADMIN") ){
            // Admin can not create/edit Super User
            MemberRoles role = getMemberRoleByRoleId(member.getRoleID());
            if(role.getRoleName().equalsIgnoreCase("Super"))  {
                retVal = false;
            }

        } else if(user.getUserRole().equalsIgnoreCase("ROLE_OPERATOR") ){
            // Operator can not create/edit Super or Admin User
            MemberRoles role = getMemberRoleByRoleId(member.getRoleID());
            if(role.getRoleName().equalsIgnoreCase("Admin") || role.getRoleName().equalsIgnoreCase("Super"))  {
                retVal = false;
            }

        } else {
            // Except Super, Admin and Operator no one can create User/member
            retVal = false;
        }

        return retVal;
    }



    @Override
    public int findMembersCount(String query, String queryField, List<Integer> memberTypes) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        return this.dao.findMembersCount(query, queryField, memberTypes, allowedTenantIds);
    }

    @Override
    public List<MemberMini> findMembers(int thisUserMemberID, String query, String queryField, List<Integer> memberTypes, int start, int limit, String sortBy, String sortDir) {
        List<Integer> allowedTenantIds = tenantService.canCallToTenantIds(TenantContext.getTenantId());
        return this.dao.findMembers(thisUserMemberID, query, queryField, memberTypes, allowedTenantIds, start, limit, sortBy, sortDir);
    }

    public List<Member> searchMembers(int tenantId, MemberFilter memberFilter) {
        List<Member> members = null;
        // Fix for backward compatibility where query field is being used for member display name/username in search filter
        if(StringUtils.isNotBlank(memberFilter.getQuery()) && StringUtils.isBlank(memberFilter.getMemberName())) {
            memberFilter.setMemberName(memberFilter.getQuery());
        }
        try {
            members = dao.searchMembers(tenantId, memberFilter);
        } catch (DataAccessException e) {
            logger.error("Error while searching members");
        }
        return members;
    }

    public int countMembers(int tenantId, MemberFilter memberFilter) {
        int totalCount = 0;
        // Fix for backward compatibility where query field is being used for member display name/username in search filter
        if(StringUtils.isNotBlank(memberFilter.getQuery()) && StringUtils.isBlank(memberFilter.getMemberName())) {
            memberFilter.setMemberName(memberFilter.getQuery());
        }
        try {
            totalCount = dao.getMembersCount(tenantId, memberFilter);
        } catch (DataAccessException e) {
            logger.error("Error while searching members");
        }
        return totalCount;
    }

    /**
     * @param serviceDao the serviceDao to set
     */
    public void setServiceDao(IServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public String getThumbNailImage(Integer tenantId, int memberID) {


        Path theExpectedFileName = Paths.get(thumbNailLocation + "/" + tenantId + "/" +memberID);

        if (Files.exists(theExpectedFileName)) {
            byte[] thumbNailImage;
            try {
                thumbNailImage = Files.readAllBytes(theExpectedFileName);


                return new String(Base64.getEncoder().encode(thumbNailImage));

            } catch (Exception e) {
                logger.error("Error occurred while getting thumbnail",e);
            }
        }



        return null;
    }
    @Override
    public String getDefaultThumbNailImage(Integer tenantId, int memberID) {


        Path theExpectedFileName = Paths.get(thumbNailLocation + "/default_thumbnail.base64");

        if (Files.exists(theExpectedFileName)) {
            List<String> thumbNailImage;
            try {
                thumbNailImage = Files.readAllLines(theExpectedFileName);
                if(thumbNailImage!=null && !thumbNailImage.isEmpty() && thumbNailImage.size()>0){
                    return thumbNailImage.get(0);
                }else{
                    logger.error("Error occurred while getting deafult thumbnail image "+thumbNailImage);
                }




            } catch (Exception e) {
                logger.error("Error occurred while getting thumbnail",e);
            }
        }



        return null;
    }

    @Override
    public void uploadUserImage(byte[] thumbNail, int memberID, int tenantId) throws IOException {
        Path imageFile = Paths.get(thumbNailLocation + "/" + tenantId + "/" + memberID);
        Path thumbNailFolder = Paths.get(thumbNailLocation + "/" + tenantId);
        Path backUpFile = Paths
                .get(thumbNailLocation + "/" + tenantId + "/" + memberID + RandomStringUtils.randomAlphabetic(16));
        Member member = this.getMember(memberID);
        if (member == null) {
            throw new RuntimeException("Member not found for the ID " + memberID);
        }
        if (ImageUtils.writeImageOperationWithBackUp(thumbNailLocation, thumbNail, thumbNailFolder, imageFile, backUpFile)) {
            int status = updateMemberUserImageUploaded(tenantId, memberID, member.getUsername(), 1);
            if (status == 0) {
                try {
                    logger.error(
                            "Error occurred durring database update. Doing restore rolling back the image changes");
                    ImageUtils.deleteImage(imageFile);
                    ImageUtils.restoreBackUp(backUpFile, imageFile);
                } catch (Exception e) {
                    logger.error("Error occurred durring backup Operation ");
                }
                throw new RuntimeException("Error occurred.Updating member table failed for the member id " + memberID);
            } else {
                // removing back up file
                if (backUpFile != null) {
                    ImageUtils.deleteImage(backUpFile);
                }

                //Update the timestamp for the thumbnail photo
                updateMemberThumbnailTimeStamp(memberID, Calendar.getInstance().getTime());
            }
        } else {
            throw new RuntimeException("Image couldnt be written to the File System");
        }
    }

    @Override
    public int updateMemberThumbnailTimeStamp(int memberID, Date thumbnailTimeStamp){
        return dao.updateMemberThumbnailTimeStamp(memberID, thumbnailTimeStamp);
    }

    @Override
    public int deleteByCreationTimeBefore(Date deletionTime) {
        return memberBAKRepository.deleteByCreationTimeBefore(deletionTime);
    }

    @Override
    public void deleteImageFolder(Integer tenantId) {
        Path directory = Paths.get(thumbNailLocation + "/" + tenantId );
        ImageUtils.deleteImageFolder(directory);
    }


    @Override
    public void deleteUserImage(Integer tenantId, int memberID) throws IOException {
        Member member = this.getMember(memberID);
        if (member == null) {
            throw new RuntimeException("Member not found for the ID " + memberID);
        }
        Path backUpFile = Paths.get(thumbNailLocation + "/" + tenantId + "/" + memberID + RandomStringUtils.randomAlphabetic(16));
        Path imageFile = Paths.get(thumbNailLocation + "/" + tenantId + "/" + memberID);
        if (Files.exists(imageFile)) {
            backUpFile = ImageUtils.takeBackUp(backUpFile, imageFile);
            int status = updateUserImageUploadedFlags(tenantId, memberID, member.getUsername(), 0);
            if (status == 0) {
                ImageUtils.restoreBackUp(backUpFile, imageFile);
                logger.error("Error occurred durring database update. Rolling back the image changes");
                throw new RuntimeException("Error occurred durring database update.Rolling back the image changes");
            }else{
                logger.debug("Deleted user image successfully. Doing clean up now");
                //Update the timestamp for the thumbnail photo
                updateMemberThumbnailTimeStamp(memberID, Calendar.getInstance().getTime());
                try {
                    if (backUpFile != null) {
                        ImageUtils.deleteImage(backUpFile);

                    }
                } catch (Exception e) {
                    logger.error("Restore failed",e);
                }
            }

        } else {
            logger.error("File not found for tenantid " + tenantId + "and member id " + memberID);
            throw new RuntimeException("File not found for tenantid " + tenantId + "and member id " + memberID);
        }

    }
}
