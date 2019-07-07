package com.vidyo.services;

import static com.vidyo.TytoConstants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.vidyo.bo.Member;
import com.vidyo.db.endpoints.EndpointDao;
import com.vidyo.dto.GetVisitResponse;
import com.vidyo.dto.VisitRequest;
import com.vidyo.dto.VisitReviewRequest;
import com.vidyo.dto.VisitReviewTytoRequest;
import com.vidyo.dto.tyto.ClinicianResponse;
import com.vidyo.dto.tyto.TytoCreateClinicianRq;
import com.vidyo.dto.tyto.TytoCreateVisitRequest;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IUserService;

import com.vidyo.validators.TytoIdValidator;

public class VidyoTytoBridge {

	protected static final Logger logger = LoggerFactory.getLogger(VidyoTytoBridge.class.getName());

	private final EndpointDao endpointDao;

	private final IUserService userService;

	private final IMemberService memberService;

	public VidyoTytoBridge(EndpointDao endpointDao, IMemberService memberService, IUserService userService) {
		this.endpointDao = endpointDao;
		this.memberService = memberService;
		this.userService = userService;
	}

	TytoCreateVisitRequest createVisit(int tenantId, VisitRequest vr, String remoteAddress) throws TytoProcessingException {
		logger.debug(" trying to find endpoint GUID {}", vr.getClinicianIdentifier());
		List<Map<String, Object>> results = findPublicIpAndMemberId(tenantId,
				Arrays.asList(vr.getClinicianIdentifier(), vr.getStationIdentifier()));
		
		Stream<EndpointDetails> endpointDetails = results.stream()
				.map(mapObject -> new EndpointDetails((String) mapObject.get("endpointGUID"),
						(String) mapObject.get("endpointPublicIPAddress"), (Long) mapObject.get("memberID"),
						(String) mapObject.get("memberType")));
		Map<String, EndpointDetails> endpointData = new HashMap<String, EndpointDetails>();
		endpointDetails.forEach(endpointDetail -> endpointData.put(endpointDetail.endpointGUID, endpointDetail));

		if (null == endpointData.get(vr.getClinicianIdentifier())) {
			logger.error("endpoint GUID: {} not found in database, returning 404 error", vr.getClinicianIdentifier() );
			throw new TytoProcessingException(CLINICIAN_GUID_NOT_FOUND, NOT_FOUND.value());
		} else if (null == endpointData.get(vr.getStationIdentifier())) {
			logger.error("endpoint GUID: {} not found in database, returning 404 error", vr.getStationIdentifier() );
			throw new TytoProcessingException(STATION_GUID_NOT_FOUND, NOT_FOUND.value());
		} else {
			String tytoClinicianId = preparaTytoId(tenantId, vr.getClinicianIdentifier(), endpointData);
			String stationIdentifier = TytoIdValidator.truncateStationGUID(vr.getStationIdentifier());
			// Update the Clinician's Endpoint public IP Address in Endpoints properties table,
			// if it has changed after login or disconnect/reconnect
			if (endpointData.get(vr.getClinicianIdentifier()) != null
					&& !remoteAddress.equalsIgnoreCase(endpointData.get(vr.getClinicianIdentifier()).publicIPAddress)) {
				int updatedCount = this.endpointDao.updatePublicIPAddress(vr.getClinicianIdentifier(), remoteAddress);
				logger.info("Updated row count {} while updating PublicIpAddress of Clinician EndpointGUID {}", updatedCount,
						vr.getClinicianIdentifier());
			} else {
				logger.error(
						"Unexpected Error - Clinician Endpoint Data for EndpointGUID {} is not available in Portal",
						vr.getClinicianIdentifier());
			}
			//Station Identifier gets updated based on polling interval by getStationStatus API
			TytoCreateVisitRequest tytoRequest = new TytoCreateVisitRequest(vr.getIdentifier(),
					tytoClinicianId,
					stationIdentifier,
					remoteAddress,
					endpointData.get(vr.getStationIdentifier()).publicIPAddress);
			logger.info("Returned TytoCreateVisit request: {} ," + tytoRequest.toString());
			return tytoRequest;
		}
	}

	public VisitReviewTytoRequest createVisitReview(int tenantId, VisitReviewRequest visitRequest, String remoteAddress)
			throws TytoProcessingException {
		String endpointGuid = visitRequest.getReviewerId();
		logger.debug(" trying to find endpoint GUID {}", endpointGuid);
		Map<String, Object> results = findPublicIpAndMemberId(tenantId, endpointGuid);

		Long memberID;
		if (null == results.get("memberID")) {
			throw new TytoProcessingException(ENDPOINT_GUID_NOT_FOUND, HttpStatus.NOT_FOUND.value());
		} else {
			memberID = (Long) results.get("memberID");
		}
		String memberType = (String) results.get("memberType");
		String tytoUserId = translateVidyoToTytoId(tenantId, memberID, memberType);

		VisitReviewTytoRequest tytoRequest = new VisitReviewTytoRequest();
		tytoRequest.setClinicianIdentifier(tytoUserId);
		tytoRequest.setClinicianRemoteAddress(remoteAddress);
		logger.info("VidyoBackend endpointID {} is mapped to TytoID: {} with publicIPAddress: {}", endpointGuid,
				tytoUserId, remoteAddress);
		return tytoRequest;
	}

    Map<String, Object> findPublicIpAndMemberId(int tenantId, String endpointGuid) {
         List<Map<String, Object>> results = findPublicIpAndMemberId(tenantId,
                 Collections.singletonList(endpointGuid));
         if (results.size() == 0){
         	logger.error(" could not find member id and public ip address for tenant {} and endpointGUID: {}", tenantId, endpointGuid);
             throw new TytoProcessingException(ENDPOINT_GUID_NOT_FOUND, NOT_FOUND.value());
         } else {
             return results.get(0);
         }
    }

	List<Map<String, Object>> findPublicIpAndMemberId(int tenantId, List<String> endpointGuid) {
		try {
			return endpointDao.findPublicIpAndMemberId(tenantId, endpointGuid);
		} catch (EmptyResultDataAccessException ae) {
			String givenEndpoints = String.join(",", endpointGuid);
			logger.error("could not find  any member and public ip addresses for tenant {},  endpoints: {}. Reason: {}",
					tenantId, givenEndpoints,  ae.getMessage(), ae);
			throw new TytoProcessingException(ENDPOINT_GUID_NOT_FOUND, NOT_FOUND.value());
		}
	}

	GetVisitResponse cleanUpGetVisitResponse(GetVisitResponse rawTytoResponse) {
		String clinicianEndpoint = memberIdToEndpointGuid(rawTytoResponse.getClinicianIdentifier());
		return new GetVisitResponse(rawTytoResponse, clinicianEndpoint);
	}

	public static String translateVidyoToTytoId(int tenantId, Long memberId, String memberType) {
		String tytoId = memberId + "@" + tenantId;
		// This below code will provide the format MemberId@TenantId_G/D. G- guests, D -
		// Regular user
		if (org.apache.commons.lang.StringUtils.isNotBlank(memberType)) {
			tytoId = tytoId.concat("_").concat(memberType);
		}
		// G/D is appended as MemberId and GuestId may collide.
		return tytoId;
	}

	TytoCreateClinicianRq createClinicianRq(int tenantId, Long memberId, String tytoClinicianId) {
		Member member = memberService.getMember(tenantId, memberId.intValue());
		ClinicianDetails clinicianDetails = deriveClinicianDetailsFromMember(member);
		return new TytoCreateClinicianRq(clinicianDetails.firstName, clinicianDetails.lastName,
				clinicianDetails.emailAddress, tytoClinicianId);
	}
	
	/**
	 * Returns ClinicianDetails derived from the Member object.
	 * Rules: Replace multiple whitespace by single whitespace
	 * 1. Firstname shall be first part of User.memberName property till the first occurrence of whitespace
       2. Lastname shall be part of User.memberName till the end of string 
       3. In case if User.memberName contains multiple whitespaces: firstname will be treated till the first whitespace, lastname: from the first whitespace till the end of second string
       4. Firstname and Lastname shall be trimmed before further processing
       5. In case if string doesn't contain whitespace firstname shall be equal to the last name  
       6. In case if firstname exceeds 20 character it should be trimmed  to 20 characters
       7. In case if lastname exceeds 30 characters it should be trimmed to 30 characters 
       8. In case if Firstname or Lastname contain any special characters they should be replaced with empty space
       9. Order of applying this rules are following: spliting, replacing special characters, trimming.
       10. Emails should be truncated to the max lenth of 70 characters
       11. In case if email after truncation doesn't contain @ character -> last 6 characters will be replaced with @a.com
	 * @param member
	 * @return {@link ClinicianDetails}
	 */
	private static ClinicianDetails deriveClinicianDetailsFromMember(Member member) {
		// Member name cannot be null, as empty/null member name is not allowed
		// Replace multiple whitespaces by single whitespace
		String memberName = member.getMemberName().trim().replaceAll("\\s{2,}", " "); 
		String[] memberNameSplitted = memberName.split(" ");
		// The minimum length of array should be 1 after the split
		String firstName = memberNameSplitted[0].trim();
		String lastName = memberNameSplitted[0].trim();
		if(memberNameSplitted.length >= 2) {
			//Assign the 2nd one to be the last name
			lastName = memberNameSplitted[1].trim();
		}
		//Replace all special characters with space
		firstName = firstName.replaceAll("[^a-zA-Z0-9' ]", "");
		lastName = lastName.replaceAll("[^a-zA-Z0-9' ]", "");
		//Trim the firstname to max 20 characters
		firstName = firstName.length() > 20 ? firstName.substring(0, 20) : firstName;
		//Trim the lastname to max 30 characters
		lastName = lastName.length() > 30 ? lastName.substring(0, 30) : lastName;
		//After all these processing if the FN and LN are empty (due to all special characters instead of alpha numeric names)
		if(org.apache.commons.lang.StringUtils.isBlank(firstName)) {
			firstName = "Clinician-FirstName"; //19 chars
		}
		if(org.apache.commons.lang.StringUtils.isBlank(lastName)) {
			lastName = "Clinician-LastName-Generated"; //28 chars
		}
		//local + @ + domain parts of an email address must not exceed 254 characters - RFC
		//Truncate email address if length is more than 70 chars
		String emailAddress = member.getEmailAddress();
		if(emailAddress.length() > 70) {
			emailAddress = "email_unknown@dummy-domain.net";
		}
		ClinicianDetails clinicianDetails = new ClinicianDetails(firstName, lastName, emailAddress);
		return clinicianDetails;
	}

	boolean isClinicianModified(int tenantId, Long memberId, ClinicianResponse response) {
		Member m = memberService.getMember(tenantId, memberId.intValue());
		ClinicianDetails cd = deriveClinicianDetailsFromMember(m);
		if (!cd.firstName.equals(response.getFirstName()) || (!cd.lastName.equals(response.getLastName()))
				|| (!cd.emailAddress.equals(response.getEmail()))) {
			return true;
		} else {
			return false;
		}
	}

	private static String preparaTytoId(int tenantId, String endpointGUID, Map<String, EndpointDetails> endpointData) {
		Long memberId = endpointData.get(endpointGUID).memberID;
		String memberType = endpointData.get(endpointGUID).memberType;
		return translateVidyoToTytoId(tenantId, memberId, memberType);
	}

	private String memberIdToEndpointGuid(String identifier) {
		// member_id@tenant_id combination should be translated to endpoint guid
		if (!StringUtils.isEmpty(identifier) && (identifier.split("@").length > 1)) {
			try {
				int memberId = Integer.parseInt(identifier.split("@")[0]);
				return userService.getLinkedEndpointGUID(memberId);
			} catch (NumberFormatException e) {
				logger.error("error while translating memberID to endpointID, "
						+ "returning received value from Tyto as is," + "memberID: " + identifier, e);
			} catch (DataAccessException de) {
				logger.error("error mapping identifier {} to endpointGUID", identifier, de);
				throw new TytoProcessingException(ENDPOINT_GUID_NOT_FOUND, NOT_FOUND.value());
			}
		}
		return identifier;
	}

	private static class EndpointDetails {
		private final String publicIPAddress;
		private final Long memberID;
		private final String memberType;
		private String endpointGUID;

		EndpointDetails(String endpointGUID, String publicIPAddress, Long memberID, String memberType) {
			this.endpointGUID = endpointGUID;
			this.publicIPAddress = publicIPAddress;
			this.memberID = memberID;
			this.memberType = memberType;
		}
	}
	
	/**
	 * Static inner class to hold Clinician details derived
	 * from the Member object
	 * @author ganesh
	 *
	 */
	private static class ClinicianDetails {
		
		/**
		 * FirstName derived from the MemberName
		 */
		private String firstName;
		
		/**
		 * LastName derived from the MemberName
		 */
		private String lastName;
		
		/**
		 * Email address obtained from the Member object
		 */
		private String emailAddress;
		
		ClinicianDetails(String firstName, String lastName, String emailAddress) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.emailAddress = emailAddress;
		}
		
	}
}