/**
 * 
 */
package com.vidyo.service.member;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.utils.LangUtils;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.SimpleMailMessage;

import com.vidyo.bo.Group;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.member.MemberEntity;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.IGroupService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.member.response.MemberManagementResponse;

/**
 * @author Ganesh
 * 
 */
public class MemberServiceImpl implements MemberService {

	/** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class.getName());
    
    private final static String MEMBER_ROLE_EXECUTIVE = "Executive";
    private final static String MEMBER_ROLE_VIDYO_PANORAMA = "VidyoPanorama";
    
	/**
	 * 
	 */
	private IMemberDao memberDao;
	
	private IRoomService roomService;
	
	private LicensingService licenseService;
	
	private ISystemService systemService;
	
	private EmailService emailService;
	
	private IServiceService serviceService;
	
	private IGroupService groupService;
	
	private ITenantService tenantService;
	
	private IMemberService memberService;
	
	private IUserService userService;
	
	private ReloadableResourceBundleMessageSource ms;
	
	private IServiceDao serviceDao;
    
	/**
	 * @param memberDao
	 *            the memberDao to set
	 */
	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public void setLicenseService(LicensingService licenseService) {
		this.licenseService = licenseService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}
	
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}
	
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}
	
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public void setMs(ReloadableResourceBundleMessageSource msgSrc){
        this.ms = msgSrc;
    }

	/**
	 * 
	 */
	@Override
	public Member getMember(int tenantId, int memberId) {
		Member member = memberDao.getMemberDetail(memberId, tenantId);
		if (member == null) {
			logger.error("Trying to access unauthorized Member -{} from Tenant - {}",
					memberId, tenantId);
		}
		return member;
	}

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	@Override
	public Language getMemberLang(int memberID, int tenantID) {
		Language lang = memberDao.getMemberLang(tenantID, memberID);
		return lang;
	}

	/**
	 * 
	 * @param memberId
	 * @param tenantId
	 * @return
	 */
	@Override
	public MemberEntity getMemberEntity(int memberId) {
		MemberEntity memberEntity = memberDao.getMemberEntity(memberId);
		return memberEntity;
	}
	
	/**
	 * Returns the list of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public List<Member> getSupers(MemberFilter filter) {
		List<Member> list = memberDao.getSupers(filter);
		return list;
	}

	/**
	 * Returns the count of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public Long getCountSupers(MemberFilter filter) {
		Long number = memberDao.getCountSupers(filter);
		return number;
	}
	
	/**
	 * 
	 */
    public Member getSuper(int memberID) {
        Member member = null;
        try {
            member = memberDao.getSuper(memberID);
        } catch(Exception e) {
            logger.error("Super is not found for memberID = " + memberID);
        }
        return member;
    }
    
    /**
     * 
     * @param member
     * @return
     */
	public int insertSuper(Member member) {
		int rc = memberDao.insertSuper(member);
		return rc;
	}
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
	public int deleteSuper(int memberID) throws AccessRestrictedException {
		User user = userService.getLoginUser();
		if(user.getMemberID() == memberID) {
			String errMsg = "Attempt to delete yourself: username = " + user.getUsername() +", memberID = " + memberID;
			logger.error(errMsg);
			throw new AccessRestrictedException(errMsg); 
		}
		
		// The following check is done just in case somebody made wrong call of deleteSuper() who is not a super user
		MemberFilter filter = new MemberFilter();
		long superCount = memberDao.getCountSupers(filter);
		if(superCount <= 1) {
			String errMsg = "Attempt to delete last super account: memberID = " + memberID;
			logger.error(errMsg);
			throw new AccessRestrictedException(errMsg);
		}
		
		Member member = getSuper(memberID);
		if (member != null) {
			// Clear cache.
			memberDao.deleteMember(member.getTenantID(), member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);
		}
		int rc = 0;
		try {
			rc = memberDao.deleteSuper(memberID);
		} catch(DataAccessException e) {
			logger.error("Problem with deleting super memberID = " + memberID + ". " + e.getMessage());
		}

		
		try {
			memberDao.deleteMemberAllPasswordHistory(memberID);
		} catch(DataAccessException e) {
			logger.error("Problem with deleting all password history for super memberID = " + memberID + ". " + e.getMessage());
		}

		return rc;
	}
	
	@Override
	public MemberManagementResponse addMember(int tenantID, Member member, String requestScheme, String requestHost) {
		
		member.setMemberID(0);

        MemberManagementResponse response = validateMember(tenantID, member);

        if (response.getStatus() != 0) {
            return response;
        }

        // create a new member
        String password = member.getPassword();
        try {
            member.setPassword(PasswordHash.createHash(password));
        } catch (Exception e1) {
        	logger.error("Password encoding failed.");
            
            response.setStatus(MemberManagementResponse.PASSWORD_ENCODING_FAILED);
            response.setMessageId("PasswordEncodingFailed");
            response.setMessage("super.user.general.error");
            
            return response;
        }
        int memberID = memberDao.insertMember(tenantID, member);
        // Clear cache
        memberDao.updateMember(tenantID, member.getUsername().toLowerCase(Locale.ENGLISH));

        // create a Personal Room for the member
        Room room = new Room();
        room.setMemberID(memberID);
        if(member.getRoleName().equalsIgnoreCase("Legacy")) {
        	room.setRoomDescription(member.getUsername()+"-Legacy Device");
        } else {
            room.setRoomDescription(member.getUsername()+"-Personal Room");
            room.setDisplayName(member.getMemberName());
			if (VendorUtils.isRoomsLockedByDefault()) {
				room.setRoomLocked(1);
			}
        }
        room.setGroupID(member.getGroupID());
        room.setRoomEnabled(member.getActive());
        room.setRoomMuted(0);
        room.setRoomExtNumber(member.getRoomExtNumber());
        room.setRoomName(member.getUsername());
        room.setRoomTypeID(member.getRoomTypeID());
        
        roomService.insertRoom(room);

         com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(tenantID).toAuthentication();
         boolean isSamlAuth = authType instanceof SamlAuthentication;
        //Send New Account Notification email out
        if(systemService.getEnableNewAccountNotification() &&
                (this.emailService != null) &&
                this.systemService.isAuthenticationLocalForMemberRole(tenantID, member.getRoleID())) {
            try {
            sendNewAccountNotificationEmail(member.getEmailAddress(),
                                            member.getMemberName(),
                                            member.getUsername(),
                                            password,
                                            requestScheme,
                                            requestHost,
                                            member.getLangID());
            } catch (Exception e) {
                logger.error("User created successfully but failed to send email notification to this user");
                
                response.setStatus(MemberManagementResponse.USER_CREATED_BUT_EMAIL_SEND_FAILED);
                response.setMessageId("CannotSendEmail");
                response.setMessage("user.created.successfully.br.failed.to.send.email.notification.to.this.user");
                
                return response;
            }
        }
        
        response.setStatus(MemberManagementResponse.SUCCESS);
        return response;
	}
	
	public Long getCountMembers(int tenantID, MemberFilter filter) {
		Long number = memberDao.getCountMembers(tenantID, filter);
        return number;
	}
	
	public int getLicensedExecutive(int tenantID) {
        Tenant rc = tenantService.getTenant(tenantID);
        return rc.getExecutives();
    }
	
	public int getLicensedPanorama(int tenantID) {
		Tenant rc = tenantService.getTenant(tenantID);
		return rc.getPanoramas();
	}
	
	public List<Member> getMembers(int tenantID, MemberFilter filter) {
        List<Member> list = memberDao.getMembers(tenantID, filter);
        return list;
    }
	
	public int updateMember(int tenantID, int memberID, Member member) throws AccessRestrictedException {
		return memberService.updateMember(tenantID, memberID, member);
	}
	
	public Member getMemberByName(String userName, int tenantID) {
		return memberService.getMemberByName(userName, tenantID);
	}
	
	private MemberManagementResponse validateMember(int tenantID, Member member) {
        MemberManagementResponse response = new MemberManagementResponse();
		// validations
        if (memberDao.isMemberExistForUserName(tenantID, member.getUsername(), member.getMemberID())) {
            logger.error("Duplicate user name");
            
            response.setStatus(MemberManagementResponse.DUPLICATE_USER_NAME);
            response.setMessageId("username");
            response.setMessage("duplicate.user.name");
            
            return response;
        }
        if (roomService.isRoomExistForRoomExtNumber(member.getRoomExtNumber(), member.getRoomID())) {
            logger.error("Duplicate room number");
            
            response.setStatus(MemberManagementResponse.DUPLICATE_ROOM_NUMBER);
            response.setMessageId("roomExtNumber");
            response.setMessage("duplicate.room.number");
            
            return response;
        }
        
        int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(systemService, member.getRoomExtNumber());
		if (extExists > 0){
			logger.error("Extension matches Schedule Room prefix. Please choose a different extension and try again");
            
            response.setStatus(MemberManagementResponse.EXTN_MATCHES_SCHEDULEROOM_PREFIX);
            response.setMessageId("roomExtNumber");
            response.setMessage("extn.matches.scheduleroom.prefix");
            
            return response;
		}
        if(member.getUsername().length() > 80 ) {
            logger.error("Invalid username length");
            
            response.setStatus(MemberManagementResponse.INVALID_USERNAME_LENGTH);
            response.setMessageId("username");
            response.setMessage("invalid.username.length");
            
            return response;
        }
        if(!member.getUsername().matches(ValidationUtils.USERNAME_REGEX)){  
            logger.error("Invalid username match");
            response.setStatus(MemberManagementResponse.INVALID_USERNAME_MATCH);
            response.setMessageId("username");
            response.setMessage("invalid.username.match");
            
            return response;
        }
        if(!member.getEmailAddress().matches("^([\\w_\\-\\.])+@([\\w_\\-]+([\\.]))+([A-Za-z]{2,})$")){
            logger.error("Invalid email match");
            
            response.setStatus(MemberManagementResponse.INVALID_EMAIL_MATCH);
            response.setMessageId("email");
            response.setMessage("invalid.email.match");
            
            return response;
        }
        
        String roleName = member.getRoleName();
        MemberRoles role;
        try {
            role = memberDao.getMemberRoleByName(tenantID, roleName);
        } catch (Exception e) {
            logger.error("Invalid role tag.");
            
            response.setStatus(MemberManagementResponse.INVALID_ROLE_NAME);
            response.setMessageId("RoleName");
            response.setMessage("invalid.role.name");
            
            return response;
        }
        member.setRoleID(role.getRoleID());

        //check if seat license is still valid
        if(licenseService.lineLicenseExpired()){
            logger.error("Line license expired");
            
            response.setStatus(MemberManagementResponse.LINE_LICENSE_EXPIRED);
            response.setMessageId("AddMember");
            response.setMessage("line.license.expired");
            
            return response;
        }
        
        if(MEMBER_ROLE_EXECUTIVE.equalsIgnoreCase(member.getRoleName())) {
	        MemberFilter filter = new MemberFilter();
	        filter.setLimit(Integer.MAX_VALUE);
	        filter.setType(MEMBER_ROLE_EXECUTIVE);
	        Long numExecutive = getCountMembers(tenantID, filter);
	
	        if (numExecutive >= getLicensedExecutive(tenantID)) {
	            logger.error("Cannot add member executive license exceeded");
	            
	            response.setStatus(MemberManagementResponse.CANNOT_ADD_MEMBER_EXECUTIVE_LICENSE_EXPIRED);
	            response.setMessageId("AddMember");
	            response.setMessage("cannot.add.member.executive.license.exceeded");
	            
	            return response;
	        }
        } else if(MEMBER_ROLE_VIDYO_PANORAMA.equalsIgnoreCase(member.getRoleName())){
        	MemberFilter filter = new MemberFilter();
            filter.setLimit(Integer.MAX_VALUE);
            filter.setType(MEMBER_ROLE_VIDYO_PANORAMA);
            Long numPanorama = getCountMembers(tenantID, filter);

            if (numPanorama >= getLicensedPanorama(tenantID)) {
                logger.error("Cannot add member panorama license exceeded");
	            
	            response.setStatus(MemberManagementResponse.CANNOT_ADD_MEMBER_PANORAMA_LICENSE_EXPIRED);
	            response.setMessageId("AddMember");
	            response.setMessage("cannot.add.member.panorama.license.exceeded");
	            
	            return response;
            }
        } else {
        	long allowedSeats = licenseService.getAllowedSeats();
            
            if (allowedSeats < 1) {
                logger.error("Cannot add member seat license exceeded");
                
                response.setStatus(MemberManagementResponse.CANNOT_ADD_MEMBER_SEAT_LICENSE_EXPIRED);
                response.setMessageId("AddMember");
                response.setMessage("cannot.add.member.seat.license.exceeded");
                
                return response;
            }
        }

        if (member.getMemberID() != 0 && !memberDao.isMemberActive(member.getMemberID()) &&  member.getActive() == 1 && 
        		licenseService.getAllowedSeats() < 1) {
            logger.error("Cannot add member seat license exceeded");
            
            response.setStatus(MemberManagementResponse.CANNOT_ADD_MEMBER_SEAT_LICENSE_EXPIRED);
            response.setMessageId("enable");
            response.setMessage("cannot.add.member.seat.license.exceeded");
            
            return response;
        }

        if (member.getMemberID() != 0 &&  !memberDao.isMemberAllowedToParticipate(member.getMemberID()) && member.getAllowedToParticipate() == 1 && 
        		licenseService.getAllowedSeats() < 1) {
            logger.error("Cannot add member seat license exceeded");
            
            response.setStatus(MemberManagementResponse.CANNOT_ADD_MEMBER_SEAT_LICENSE_EXPIRED);
            response.setMessageId("allowedToParticipateHtml");
            response.setMessage("cannot.add.member.seat.license.exceeded");
            
            return response;
        }
        
        // Validate the Location Tag
        if(!roleName.equalsIgnoreCase("Legacy")) {
            int locationID;
            String locationTag = member.getLocationTag();
            if (locationTag == null || locationTag.equalsIgnoreCase("")) {
                logger.error("Invalid location tag.");
                
                response.setStatus(MemberManagementResponse.INVALID_LOCATION_TAG);
                response.setMessageId("LocationTag");
                response.setMessage("invalid.location.tag");
                
                return response;
            }
            locationID = this.serviceService.getLocationIdByLocationTag(locationTag);
            if (locationID == 0) {
                logger.error("Invalid location tag.");
                
                response.setStatus(MemberManagementResponse.INVALID_LOCATION_TAG);
                response.setMessageId("LocationTag");
                response.setMessage("invalid.location.tag");
                
                return response;
            }
            member.setLocationID(locationID);
            
            String proxyName = member.getProxyName();
            if (proxyName != null && !(proxyName.trim().equalsIgnoreCase("") || proxyName.trim().equalsIgnoreCase("No Proxy"))) {
                Proxy proxy;
                try {
                    proxy = serviceDao.getProxyByName(tenantID, proxyName);
                } catch (Exception e) {
                   logger.error("Invalid proxy name.");
                    
                    response.setStatus(MemberManagementResponse.INVALID_PROXY_NAME);
                    response.setMessageId("ProxyName");
                    response.setMessage("invalid.proxy.name");
                    
                    return response;
                }
                if (proxy != null) {
                    member.setProxyID(proxy.getProxyID());
                } else {
                    logger.error("Invalid proxy name.");
                    
                    response.setStatus(MemberManagementResponse.INVALID_PROXY_NAME);
                    response.setMessageId("ProxyName");
                    response.setMessage("invalid.proxy.name");
                    
                    return response;
                }
            } else {
                member.setProxyID(0);
            }
        }
        
        Group group;
        try {
            group = this.groupService.getGroupByName(member.getGroupName());
        } catch (Exception e) {
            logger.error("Invalid group name.");
            
            response.setStatus(MemberManagementResponse.INVALID_GROUP_NAME);
            response.setMessageId("GroupName");
            response.setMessage("invalid.group.name");
            
            return response;
        }
        member.setGroupID(group.getGroupID());

        return response;
	}
	
	//Attempt to send out notification email. There is no error returned if sending failed on whatever reason.
    // "void org.springframework.mail.MailSender.send()" method won't give any error if sending failed
    private void sendNewAccountNotificationEmail(String emailAddress, String fullName, String userName, String plainPassword, 
    		String requestScheme, String requestHost, int langID){
        Locale tmploc;
        if(langID == 10)
            tmploc = LangUtils.getLocaleByLangID(systemService.getSystemLang().getLangID());
        else 
            tmploc = LangUtils.getLocaleByLangID(langID);
        
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = ms.getMessage("NewAccount.Notification.Email.Subject", null, "", tmploc);
        String content = ms.getMessage("NewAccount.Notification.Email.Content", null, "", tmploc);
        content = content.replaceAll("\\[FULLNAME\\]", Matcher.quoteReplacement(fullName));
        content = content.replaceAll("\\[USERNAME\\]", Matcher.quoteReplacement(userName));
        content = content.replaceAll("\\[PASSWORD\\]", Matcher.quoteReplacement(plainPassword));
        content = content.replaceAll("\\[PORTAL\\]", requestScheme + "://" + requestHost);


        String fromAddr = this.systemService.getNotificationFromEmailAddress();
        message.setFrom(fromAddr);
        message.setTo(emailAddress);
        message.setSubject(subject);
        message.setText(content);

	    this.emailService.sendEmailAsynchronous(message);
    }
    
    /**
     * Duplicate method of IMemberService. All APIs from IMemberservice will be moved to this class
     * @return
     */
    public Long getCountAllSeats() {
        Long number = memberDao.getCountAllSeats();
        return number;
    }

	/**
	 * @param serviceDao the serviceDao to set
	 */
	public void setServiceDao(IServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}    

}
