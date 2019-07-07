package com.vidyo.db;

import java.util.List;

import com.vidyo.bo.*;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.security.PortalAccessKeys;

public interface IUserDao {
	
	public static final String INSERT_INTO_ENDPOINTS = "INSERT INTO Endpoints(memberID, memberType, endpointGUID, authorized, sequenceNum, status, consumesLine, endpointUploadType, lectureModeSupport) "
			+ "VALUES(:memberID, :memberType, :endpointGUID, :authorized, :sequenceNum, :status, :consumesLine, :endpointUploadType, :lectureModeSupport)";
	
    public User getUserByUsername(int tenant, String username);
    public Language getUserLang(int tenant, String username);
    public int linkEndpointToUser(int memberID, String GUID, String endpointUploadType);
    public int unlinkEndpointFromUser(int memberID, String GUID);
    public int updateEndpointIPaddress(String GUID, String ipAddress);
    public int updateEndpointExternalData(String GUID, int extDataType, String extData);
	public int updateEndpointCDRInfo(String GUID, CDRinfo2 cdrInfo);
	public int updateEndpointReferenceNumber(String GUID, String referenceNumber);
    public boolean isMemberLinkedToEndpoint(int memberID, String GUID);
    public int getMyStatus(int memberID, String GUID);
    public String getLinkedEndpointGUID(int memberID);
    public int addGuestUser(int tenant, String guestname, String username);
	public int addGuestUser(int tenant, String guestName, String username, int roomID);
    public User getUserForGuestID(int guestID);
    public int linkEndpointToGuest(int tenant, int guestID, String GUID);

    public int savePAKforMember(int memberID, String PAK, String pak2);
    public PortalAccessKeys getPAKforMember(int memberID);
    public int savePAKforGuest(int guestID, String PAK, String pak2);
    public PortalAccessKeys getPAKforGuest(int guestID);
    public int getMemberIDForPAK(String PAK);

    public int saveBAKforMember(int memberID, String BAK);
    public String getBAKforMember(int memberID);
    public int getMemberIDForBAK(String BAK);

    public int saveSAKforMember(int memberID, String SAK, long bindUserRequestID);
    public String getSAKforMember(int memberID);
    public long getBindUserRequestIDforMember(int memberID);
    public int saveSAKforGuest(int guestID, String SAK, long bindUserRequestID);
    public String getSAKforGuest(int guestID);
    public long getBindUserRequestIDforGuest(int guestID);

	public boolean isUserLicenseRegistered(String EID);
    public int registerLicenseForUser(String userName, String tenantName, String EID, String ipAddress, String hostname);
	public int registerLicenseForUser2(String userName, String displayName, String tenantName, String EID, String ipAddress, String hostname);

    public int updateRegisterLicenseForGuest(String userName, String guestName, Room room);
	public int updateRegisterLicenseForGuest2(String userName, String guestName, Room room);

    public int getNumOfConsumedLicenses(String tenantName);
	public int getNumOfConsumedLicenses2(String tenantName);

    public int setAuthorizedFlagForEndpoint(Endpoint endpoint);

    //VPTL-7615 - API to fetch the Admin User for the given tenant.
	public List<User> getUserByRole(int tenantId, int roleId);
    
	/**
	 * Returns the SAK and BindUserRequestID for bind user challenge
	 * 
	 * @param memberID
	 * @return
	 */
	public User getUserForBindChallengeResponse(int memberID);
	
	/**
	 * Returns Member, browser access key(bak) and bak creation time based on
	 * the browser access key (bak)
	 * 
	 * @param Bak
	 * @return
	 */
	public List<Member> getMemberForBak(String bak);
	
	/**
	 * Empty method to clear userDetailCache by EhCacheInterceptor. This method
	 * needs to be stand alone and should not be invoked from the same class
	 * [UserDaoJdbcImpl] else cache wont get cleared.
	 * 
	 * @param tenantId
	 * @param userName
	 * @return
	 */
	public int updateMember(int tenantId, String userName);
	
	/**
	 * Deletes pak value of the Member
	 * 
	 * @param tenantId
	 * @param memberId
	 * @return
	 */
	public int deletePak(int tenantId, int memberId);
	
	/**
	 * Deletes pak2 value of the Member
	 * 
	 * @param tenantId
	 * @param memberId
	 * @return
	 */
	public int deletePak2(int tenantId, int memberId);	
}
