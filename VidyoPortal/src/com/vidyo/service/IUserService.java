package com.vidyo.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.User;
import com.vidyo.bo.UserUnbindCode;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.service.user.AccessKeyResponse;

public interface IUserService {
    public User getUserByUsername(String username);
    public User getUserByUsername(String username, int tenantID);
    public User getUserByUsernameWithOutAuth(String username, int tenantID) ;
    public void setLoginUser(Map<String, Object> model, HttpServletResponse response) throws Exception;
    public int addGuestUser(String guestName, String username);
	public int addGuestUser(String guestName, String username, int roomID);
	public User getLoginUser();
    public User getUserForGuestID(int guestID);
    public Language getUserLang();

    public void linkEndpointToUser(String GUID, String clientType, boolean usePak2);
    public void linkEndpointToGuest(int guestID, String GUID, boolean usePak2);
    public void linkEndpointToUser(int memberID, String GUID, String clientType, boolean usePak2);
    public void unlinkEndpointFromUser(int memberID, String GUID, UserUnbindCode reasonCode);
    public int updateEndpointIPaddress(String GUID, String ipAddress);
    public int updateEndpointExtData(String GUID, int extDataType, String extData);
	public int updateEndpointCDRInfo(String GUID, CDRinfo2 cdrInfo);
	public int updateEndpointReferenceNumber(String GUID, String referenceNumber);

    public int getMyStatus(int memberID, String GUID);
    public boolean isMemberLinkedToEndpoint(int memberID, String GUID);
    public String getLinkedEndpointGUID(int memberID);

    public PortalAccessKeys generatePAKforMember(int memberID);
    public PortalAccessKeys getPAKforMember(int memberID);
    public PortalAccessKeys generatePAKforGuest(int guestID);
    public PortalAccessKeys getPAKforGuest(int guestID);
    public int getMemberIDForPAK(String PAK);

    public String generateBAKforMember(int memberID);
    public String generateBAKforMember(int memberID, MemberBAKType bakType);
    public Integer getMemberIDForBAK(String BAK, MemberBAKType bakType);

    public void generateSAKforMember(int tenantId, int memberID, String challenge, long bindUserRequestID, boolean pak2Enabled);
    public String getSAKforMember(int memberID);
    public long getBindUserRequestIDforMember(int memberID);
    public void generateSAKforGuest(int guestID, String challenge, long bindUserRequestID, boolean usePak2);
    public String getSAKforGuest(int guestID);
    public long getBindUserRequestIDforGuest(int guestID);

	public boolean isUserLicenseRegistered(String EID);
    public int registerLicenseForUser(User user, String EID, String IPaddress, String hostname);
    public int updateRegisterLicenseForGuest(String userName, String guestName, Room room);
    public int getLicensedNumberOfInstallLeft();
    public void checkRemainingInstallsBalance(int remainingBalance);

    public int setAuthorizedFlagForEndpoint(Endpoint endpoint);
	public String getLoginUserRole();


	
	/**
	 * Returns the SAK and BindUserRequestID for bind user challenge
	 * 
	 * @param memberID
	 * @return
	 */
	public User getUserForBindChallengeResponse(int memberID);
	
	/**
	 * Returns the onetime access URI after generating browser access key
	 * 
	 * @param tenantId Tenant to which the member belongs to
	 * @param memberId Member for which the access is requested
	 * @param bakType BAK type
	 * @return onetime access URI
	 */
	public AccessKeyResponse getOnetimeAccessUri(int tenantId, int memberId, MemberBAKType bakType);
	
	/**
	 * Return the Member matching the bak (browser access key)
	 * @param bak
	 * @return
	 */
    public Member getMemberForBak(String bak, MemberBAKType bakType);

    public String getUserDefaultPassword();
    
    public void linkEndpointToUser(String endpointGUID, String clientType,
			boolean pak2, User user);

	public void deleteMemberBAK(String bak);

}
