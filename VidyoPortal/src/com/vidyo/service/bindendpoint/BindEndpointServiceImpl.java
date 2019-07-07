/**
 * 
 */
package com.vidyo.service.bindendpoint;

import javax.activation.DataHandler;

import com.vidyo.service.ISystemService;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.vcap20.*;
import org.apache.axis2.AxisFault;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.member.MemberEntity;
import com.vidyo.service.IUserService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.system.SystemService;
import com.vidyo.ws.manager.EndpointGUID_type0;
import com.vidyo.ws.manager.InfoForEndpointRequest;
import com.vidyo.ws.manager.InfoForEndpointResponse;
import com.vidyo.ws.manager.VidyoManagerServiceStub;

/**
 * @author Ganesh
 * 
 */
public class BindEndpointServiceImpl implements BindEndpointService {

	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(BindEndpointServiceImpl.class);

	/**
     * 
     */
	private SystemService systemService;

	/**
     * 
     */
	private MemberService memberService;

	/**
	 * 
	 */
	private String vidyoRoomRoleName;

	/**
	 *
	 */
	private String vidyoPanoramaRoleName;

	/**
	 * 
	 */
	protected IUserService userService;
	
	/**
	 * 
	 */
	private ISystemService system;
	
	/**
	 * 
	 */
	private EndpointService endpointService;

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param vidyoRoomRoleName
	 *            the vidyoRoomRoleName to set
	 */
	public void setVidyoRoomRoleName(String vidyoRoomRoleName) {
		this.vidyoRoomRoleName = vidyoRoomRoleName;
	}

	/**
	 * @param vidyoPanoramaRoleName
	 *            the vidyoPanoramaRoleName to set
	 */
	public void setVidyoPanoramaRoleName(String vidyoPanoramaRoleName) {
		this.vidyoPanoramaRoleName = vidyoPanoramaRoleName;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

    public void setSystem(ISystemService system) {
        this.system = system;
    }
    
    public void setEndpointService(EndpointService endpointService) {
    	this.endpointService = endpointService;
    }
	/**
	 * Sends Challenge to the Endpoint through VidyoManager. IMPORTANT: There
	 * are AOP interceptors working on this method flow. Please refer to the
	 * spring interceptors xml before changing any business logic in this
	 * method.
	 * 
	 * @param memberID
	 * @param GUID
	 */
	@Override
	public void bindUserToEndpoint(int memberID, String GUID) {
		logger.debug("Executing bindUserToEndpoint memberID - {}, GUID - {} ", memberID, GUID);
		MemberEntity memberEntity = null;
		memberEntity = memberService.getMemberEntity(memberID);
		if (memberEntity != null) {
			// send VCAP message BindUser
			RootDocument doc = RootDocument.Factory.newInstance();
			Message message = doc.addNewRoot();
			RequestMessage req_message = message.addNewRequest();
			// Unique RequestID
			long bindUserRequestID = Long.valueOf(PortalUtils.generateNumericKey(9));
			req_message.setRequestID(bindUserRequestID);

			BindUserRequest bind_user_req = req_message.addNewBindUser();

			bind_user_req.setUsername(memberEntity.getUsername());
			UserProfileType user_prof_type = bind_user_req.addNewUserProfile();

			memberEntity.setMemberName(memberEntity.getMemberName());

			user_prof_type.setDisplayName(memberEntity.getMemberName());

			user_prof_type.setLanguage(memberEntity.getLangCode());

			String challenge = null;
			boolean usePak2 = false;
			if(memberEntity.getPak() == null) {
				// If pak2 use secure random and delete the pak1 generated during login
				challenge = SecureRandomUtils.generateHashKey(47);
				usePak2 = true;
			} else {
				challenge = PortalUtils.generateKey(16);
			}
			
			String clientType = null;
			Endpoint endpoint = endpointService.getEndpointDetails(GUID);
			if(endpoint != null) {
				clientType = endpoint.getEndpointUploadType();
			}
			if ((memberEntity.getRoleName().equalsIgnoreCase(vidyoRoomRoleName) || memberEntity.getRoleName().equalsIgnoreCase(vidyoPanoramaRoleName))
					&& 
					(clientType == null
					|| clientType != null && !clientType.equalsIgnoreCase("B") && !clientType.equalsIgnoreCase("C") && !clientType.equalsIgnoreCase("D"))
					
					||
					
					memberEntity.getRoleName().equalsIgnoreCase(MemberRoleEnum.EXECUTIVE.getMemberRole()) && clientType != null
					&& (clientType.equalsIgnoreCase("B") || clientType.equalsIgnoreCase("C") || clientType.equalsIgnoreCase("D"))) {
					String reverseChallenge = new StringBuffer(challenge).reverse().toString();
					userService.generateSAKforMember(memberEntity.getTenantID(), memberID, reverseChallenge, bindUserRequestID, usePak2);
				} else {
					userService.generateSAKforMember(memberEntity.getTenantID(), memberID, challenge, bindUserRequestID, usePak2);
				}
			bind_user_req.setChallenge(challenge);

			BirrateLimitSet bit_rate_limits = user_prof_type.addNewBitrateLimits();

			BitrateLimitType bit_rate_limit_up = bit_rate_limits.addNewBitrateLimit();
			MediaChannelType bit_rate_channel_up = bit_rate_limit_up.addNewMediaChannel();
			bit_rate_channel_up.setMediaType(MediaType.NONE);
			bit_rate_channel_up.setMediaSource(MediaSourceType.LOCAL);
			bit_rate_limit_up.setBitrate(1000 * memberEntity.getUserMaxBandWidthOut());

			BitrateLimitType bit_rate_limit_down = bit_rate_limits.addNewBitrateLimit();
			MediaChannelType bit_rate_channel_down = bit_rate_limit_down.addNewMediaChannel();
			bit_rate_channel_down.setMediaType(MediaType.NONE);
			bit_rate_channel_down.setMediaSource(MediaSourceType.REMOTE);
			bit_rate_limit_down.setBitrate(1000 * memberEntity.getUserMaxBandWidthIn());

            // Add DSCP values
            try{
                DSCPValueSet dscpValueSet = user_prof_type.addNewDSCPValues();
                DSCPValueSet dscpValueSetFromConfig = system.getDSCPConfiguration(memberEntity.getMemberName(), memberEntity.getTenantID());
                dscpValueSet.setMediaAudio(dscpValueSetFromConfig.getMediaAudio());
                dscpValueSet.setMediaVideo(dscpValueSetFromConfig.getMediaVideo());
                dscpValueSet.setMediaData(dscpValueSetFromConfig.getMediaData());
                dscpValueSet.setSignaling(dscpValueSetFromConfig.getSignaling());
                dscpValueSet.setOAM(dscpValueSetFromConfig.getOAM());
            } catch (Exception e) {
				logger.error("Error while setting DSCP values  ", e);
			}

			XmlOptions opts = new XmlOptions();
			opts.setCharacterEncoding("UTF-8");

			String info = doc.xmlText(opts);

			DataHandler binduser = new DataHandler(info, "text/plain; charset=UTF-8");

			try {
				sendMessageToEndpoint(GUID, binduser, memberEntity.getTenantID());
			} catch (Exception e) {
				logger.error("Error while sending message to Endpoint tenantID - {}, memberID - {}, GUID - {} ",
						memberEntity.getTenantID(), memberID, GUID);
				logger.error("Exception invoking sendMessageToEndpoint ", e);
			}
		}

	}
	
	/**
	 * Empty method implementation for interception.
	 * 
	 * @param guestID
	 * @param GUID
	 */
	public void bindGuestToEndpoint(int guestID, String GUID) {
		logger.debug("Guest ID - {}, GUID - {}", guestID, GUID);
	}

	@Override
	public void sendBindSuccessMessage(String endpointGUID, int tenantID) {
			RootDocument doc = RootDocument.Factory.newInstance();
			Message message = doc.addNewRoot();
			RequestMessage req = message.addNewRequest();
			long bindUserRequestID = Long.valueOf(PortalUtils.generateNumericKey(9));
			req.setRequestID(bindUserRequestID);
			BindUserAckRequest userAckRequest = req.addNewBindUserAck();
			userAckRequest.setBindSuccess(true); // never false, otherwise unbind VCAP msg was sent
			XmlOptions opts = new XmlOptions();
			opts.setCharacterEncoding("UTF-8");
			String info = doc.xmlText(opts);

			DataHandler dataHandler = new DataHandler(info, "text/plain; charset=UTF-8");

			try {
				sendMessageToEndpoint(endpointGUID, dataHandler, tenantID);
			} catch (Exception e) {
				logger.error("Error while sending bindAck message to Endpoint tenantID - {}, GUID - {} ",
						tenantID, endpointGUID);
				logger.error("Exception invoking sendMessageToEndpoint for bindAck", e);
			}
	}

	/**
	 * 
	 * @param endpointGUID
	 * @param content
	 * @param tenantID
	 * @throws Exception
	 */
	protected void sendMessageToEndpoint(String endpointGUID, DataHandler content, int tenantID) throws Exception {
		VidyoManagerServiceStub stub = null;
		try {
			stub = systemService.getVidyoManagerServiceStubWithAuth(tenantID);

			// Info for endpoint - go thru VM
			InfoForEndpointRequest info_req = new InfoForEndpointRequest();

			EndpointGUID_type0 to_guid = new EndpointGUID_type0();
			to_guid.setEndpointGUID_type0(endpointGUID);
			info_req.setEndpointGUID(to_guid);

			info_req.setContent(content);

			InfoForEndpointResponse info_resp = stub.infoForEndpoint(info_req);
			// logger.error("InfoForEndpoint endpointGUID ->" + endpointGUID);
			if (info_resp != null) {
				DataHandler resp = info_resp.getContent();
			}

		} catch (Exception e) {
			logger.error("InfoForEndpoint request failed for endpointGUID ->" + endpointGUID + " Error ->" + e.getMessage());
			throw e;
		} finally {
			if (stub != null) {
				try {
					stub.cleanup();
				} catch (AxisFault af) {
					// ignore
				}
			}
		}
	}

}
