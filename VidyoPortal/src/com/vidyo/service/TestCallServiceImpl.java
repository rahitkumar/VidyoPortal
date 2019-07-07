package com.vidyo.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.databinding.types.URI;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.model.CallDetails;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.exceptions.TestCallException;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.ScheduledRoomResponse;

@Service("TestCallService")
public class TestCallServiceImpl implements TestCallService {

    protected final Logger logger = LoggerFactory.getLogger(TestCallServiceImpl.class.getName());

	private static final String REQUEST_PATH = "/vidyotestcall";

	@Autowired
	private ITenantService tenantService;
	@Autowired
    private RestTemplate restTemplate;
	@Autowired
    private IUserService userService;
	@Autowired
    private ISystemService systemService;
	@Autowired
    private IRoomService roomService;
	@Autowired
    private IMemberService memberService;
	
	private int tenantLevelWebRTCPort = 443;

	public void setSystemLevelWebRTCPort(int systemLevelWebRTCPort) {this.systemLevelWebRTCPort = systemLevelWebRTCPort;}

	private int systemLevelWebRTCPort = 443;

	public void setTenantLevelWebRTCPort(int tenantLevelWebRTCPort) { this.tenantLevelWebRTCPort = tenantLevelWebRTCPort;}

	public int getTenantLevelWebRTCPort() {return tenantLevelWebRTCPort;}

    public IMemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(IMemberService memberService) {
        this.memberService = memberService;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public ISystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }

    public IRoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(IRoomService roomService) {
        this.roomService = roomService;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public void testCall(CallDetails callDetails, String webRTCURL, PortConfig portConfig) throws TestCallException {
        // set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CallDetails> entity = new HttpEntity<>(callDetails, headers);
        ResponseEntity<String> response = null;
		int port = portConfig == PortConfig.SYSTEM ? systemLevelWebRTCPort: tenantLevelWebRTCPort;
		webRTCURL = webRTCURL+":"+ port + REQUEST_PATH;
		logger.info("WebRTC URL constructed for making Rest call :: "+webRTCURL);
        try {
            response = restTemplate.exchange(webRTCURL, HttpMethod.POST, entity, String.class);
        }catch(Exception exception){
            logger.error("Exception occured when invoking the rest call with Testcall server ");
            logger.error(exception.getMessage());
            throw new TestCallException("Connection with Testcall server could not be established. Kindly check the WebRTC server and port configuration.");
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK ){
            logger.info("Test call service executed succesfully");
        } else {
            logger.error("Invalid response recieved from Testcall server. "+response.getBody());
            throw new TestCallException("Exception while communicating with Testcall Server.");
        }

	}

	// Method to fetch a member at random for a specific member type
	private Member fetchMemberAtRandom(MemberRoleEnum memberRole, Long userCount) {
		int randomNum = ThreadLocalRandom.current().nextInt(userCount.intValue());
		MemberFilter filter = new MemberFilter();
		filter.setType(memberRole.getMemberRole());
		filter.setStart(randomNum);
		filter.setLimit(1);
		List<Member> members = memberService.getMembers(filter);
		if (CollectionUtils.isNotEmpty(members)) {
			return members.get(0);
		}
		return null;
	}

	// Method to fetch an existing admin or normal member
	private Member fetchExistingMemberForGuest() throws ScheduledRoomCreationException {
		MemberRoleEnum memberRole = MemberRoleEnum.ADMIN;
		Long userCount = 0l;

		MemberFilter filter = new MemberFilter();
		filter.setType(memberRole.getMemberRole());
		userCount = memberService.getCountMembers(filter);

		if (userCount.equals(0l)) {
			memberRole = MemberRoleEnum.NORMAL;
			filter.setType(memberRole.getMemberRole());
			userCount = memberService.getCountMembers(filter);

			if (userCount.equals(0l)) {
				String message = "No admins or normal members available while creating a scheduled room";
				logger.error(message);
				ScheduledRoomCreationException exception = new ScheduledRoomCreationException(message);
				throw exception;
			}
		}

		return fetchMemberAtRandom(memberRole, userCount);
	}

	public ScheduledRoomResponse createScheduledRoomForTestCall(Member member) throws ScheduledRoomCreationException {
		return createScheduledRoomForTestCall(member, null);
	}
	
	public ScheduledRoomResponse createScheduledRoomForTestCallOneAttempt(Member member, String externalRoomId) 
			throws ScheduledRoomCreationException {
		return createScheduledRoomForTestCall(member, externalRoomId);
	}
	
	protected ScheduledRoomResponse createScheduledRoomForTestCall(Member member, String externalRoomId) 
			throws ScheduledRoomCreationException {

		boolean isGuestUser = (member == null) ? true : false;
		int count = 0;
		boolean isRoomCreated = false;
		RoomCreationResponse roomCreationResponse = null;

		do {
			try {
				// For a guest user, fetch a existing admin or normal member to pass on to the Scheduled Room Creation
				member = (member != null) ? member : fetchExistingMemberForGuest();
				if (member == null) {
					count++;
					continue;
				}
				
				SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
				schRoomCreationRequest.setTenantId(member.getTenantID());
				schRoomCreationRequest.setMemberId(member.getMemberID());
				schRoomCreationRequest.setMemberName(member.getMemberName());
				schRoomCreationRequest.setGroupId(member.getGroupID());
				schRoomCreationRequest.setRecurring(1); //VPTL-7755 - Set to 1 which is the minimum value for the batch job to clean up the record.
				schRoomCreationRequest.setPinRequired(false);
				
				if (externalRoomId != null) { // only for epic integration
					schRoomCreationRequest.setExternalRoomId(externalRoomId);
					int roomId = roomService.getRoomIdForExternalRoomId(externalRoomId);
					
					if (roomId > 0) {
						isRoomCreated = true;
						roomCreationResponse = prepareRoomCreationResponse(roomId);
						break;
					}
				}

				// Create a scheduled room for test call.
				roomCreationResponse = roomService.createScheduledRoom(schRoomCreationRequest);
				if (roomCreationResponse.getStatus() == 0) {
					isRoomCreated = true;
				}
			} catch (Exception e) {
				logger.error("Error while creating a scheduled room for a test call : ", e);
				// Resetting the member if it was randomly picked for the guest flow
				if (isGuestUser) {
					member = null;
				}
				
				if (externalRoomId != null) { // only for epic integration
					int roomId = roomService.getRoomIdForExternalRoomId(externalRoomId);
					
					if (roomId > 0) {
						isRoomCreated = true;
						roomCreationResponse = prepareRoomCreationResponse(roomId);
						break;
					}
				}
			}

			count++;

			// Retry logic is applied only for guest users. For regular users
			// there is retry logic in the createScheduledRoom method in RoomService.
		} while (!isRoomCreated && isGuestUser && count < 10);

		if (!isRoomCreated) {
			String message = "Scheduled Room Creation for Test Call failed after attempts - " + count;
			logger.error(message);
			message = (roomCreationResponse != null) ? roomCreationResponse.getMessage() : message;
			ScheduledRoomCreationException exception = new ScheduledRoomCreationException(message);
			throw exception;
		}

		roomCreationResponse.getRoom().setRoomName(roomCreationResponse.getExtensionValue());

		URI uri = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String requestScheme = request.getScheme();
			String joinURL = roomService.getRoomURL(systemService, requestScheme,
					roomCreationResponse.getTenant().getTenantURL(), roomCreationResponse.getRoom().getRoomKey());

			uri = new URI(joinURL);
		} catch (URI.MalformedURIException e) {
			logger.error("Exception while creating scheduled Room ", e);
			throw new ScheduledRoomCreationException("Exception while creating a scheduled Room.");
		}

		ScheduledRoomResponse createScheduledRoomResponse = new ScheduledRoomResponse();
		createScheduledRoomResponse.setRoom(roomCreationResponse.getRoom());
		createScheduledRoomResponse.getRoom().setRoomURL(uri.toString());
		createScheduledRoomResponse.setRoomExtn(roomCreationResponse.getExtensionValue());
		createScheduledRoomResponse.setMemberId(member.getMemberID());
		createScheduledRoomResponse.setMemberName(member.getMemberName());

		return createScheduledRoomResponse;
	}

	private RoomCreationResponse prepareRoomCreationResponse(int roomId) {
		RoomCreationResponse roomCreationResponse = new RoomCreationResponse();
		Room room = roomService.getRoom(roomId);
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		
		roomCreationResponse.setRoom(room);
		roomCreationResponse.setExtensionValue(room.getRoomExtNumber());
		roomCreationResponse.setTenant(tenant);
		
		return roomCreationResponse;
	}
}
