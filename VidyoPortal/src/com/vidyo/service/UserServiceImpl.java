package com.vidyo.service;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.UserUnbindCode;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.memberbak.MemberBAK;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.bo.statusnotify.Alert;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.IUserDao;
import com.vidyo.db.repository.member.MemberRepository;
import com.vidyo.db.repository.memberbak.MemberBAKRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.authentication.VidyoUserGuestDetails;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.framework.security.utils.VidyoHash;
import com.vidyo.service.bindendpoint.BindEndpointService;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.guest.GuestService;
import com.vidyo.service.statusnotify.StatusNotificationService;
import com.vidyo.service.user.AccessKeyResponse;
import com.vidyo.utils.LangUtils;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecureRandomUtils;

public class UserServiceImpl implements IUserService {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    private static final String MEMBER_BAK_INACTIVE_LIMIT_IN_MINS = "MEMBER_BAK_INACTIVE_LIMIT_IN_MINS";

    private IUserDao dao;
    private ISystemDao system;
    private IConferenceService conference;
    private ITenantService tenantService;
    private EmailService emailService;
    private ReloadableResourceBundleMessageSource ms;
    private EndpointService endpointService;
    private BindEndpointService bindEndpointService;
    private String userPortalPath;
    private IMemberDao memberDao;
    private StatusNotificationService statusNotificationService;
    private GuestService guestService;
    @Autowired
    private MemberBAKRepository memberBAKRepository;
    @Autowired
    private MemberRepository memberRepository;

	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}

	public void setStatusNotificationService(StatusNotificationService statusNotificationService) {
		this.statusNotificationService = statusNotificationService;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public String getUserPortalPath() {
		return userPortalPath;
	}

	public void setUserPortalPath(String userPortalPath) {
		this.userPortalPath = userPortalPath;
	}

	public void setEndpointService(EndpointService endpointService) {
		this.endpointService = endpointService;
	}

	public void setMs(ReloadableResourceBundleMessageSource msgSrc){
        this.ms = msgSrc;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void setDao(IUserDao dao) {
        this.dao = dao;
    }

    public void setSystem(ISystemDao system) {
        this.system = system;
    }

    public void setConference(IConferenceService conference) {
        this.conference = conference;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

	public void setBindEndpointService(BindEndpointService bindEndpointService) {
		this.bindEndpointService = bindEndpointService;
	}

	public void setMemberBAKRepository(MemberBAKRepository memberBAKRepository) {
		this.memberBAKRepository = memberBAKRepository;
	}

	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public User getUserByUsernameWithOutAuth(String username, int tenantID) {
		User rc = null;
		try {
			rc = this.dao.getUserByUsername(tenantID, username);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return rc;
	}

	public User getUserByUsername(String username) {
        boolean superuser = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equalsIgnoreCase("ROLE_SUPER")) {
                superuser = true;
                break;
            }
        }

        User rc = null;
        try {
            if (superuser) {
                rc = this.dao.getUserByUsername(1, username);
            } else {
                rc = this.dao.getUserByUsername(TenantContext.getTenantId(), username);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return rc;
    }

    public User getUserByUsername(String username, int tenantID) {
        boolean superuser = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        for (GrantedAuthority role : roles) {
            if (role.getAuthority().equalsIgnoreCase("ROLE_SUPER")) {
                superuser = true;
                break;
            }
        }

        User rc = null;
        try {
            if (superuser) {
                rc = this.dao.getUserByUsername(1, username);
            } else {
                rc = this.dao.getUserByUsername(tenantID, username);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return rc;
    }

    public int addGuestUser(String guestName, String username) {
        int rc = this.dao.addGuestUser(TenantContext.getTenantId(), guestName, username);
        return rc;
    }

	public int addGuestUser(String guestName, String username, int roomID) {
		int rc = this.dao.addGuestUser(TenantContext.getTenantId(), guestName, username, roomID);
		return rc;
	}

    public void setLoginUser(Map<String, Object> model, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user;
            boolean isGuest = false;
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
            for (GrantedAuthority role : roles) {
                if (role.getAuthority().equalsIgnoreCase("ROLE_GUEST") || role.getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
                    isGuest = true;
                    break;
                }
            }

            if (isGuest) {
                user = new User();
                user.setMemberID(0);
                user.setRoomID(0);
                user.setUsername(auth.getName());
                user.setMemberName("Guest");
	            user.setUserRole("ROLE_GUEST");
            } else {
                user = getUserByUsername(auth.getName());
				if (user != null) {
					user.setMemberName(user.getMemberName());
					for (GrantedAuthority role : roles) {
						user.setUserRole(role.getAuthority());
					}
				}
            }

            model.put("user", user);
        } else {
            model.put("user", "Guest");
        }
    }

    public User getLoginUser() {
Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            User user = null;

            boolean isGuest = false;
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
            for (GrantedAuthority role : roles) {
                if (role.getAuthority().equalsIgnoreCase("ROLE_GUEST") || role.getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
                    isGuest = true;
                    break;
                }
            }

            if (isGuest) {
                user = new User();
                user.setMemberID(0);
                user.setRoomID(0);
                user.setUsername(auth.getName());
                user.setMemberName("Guest");
	            user.setUserRole("ROLE_GUEST");
            } else {
            	// TODO - Return the VidyoUserDetails instead of User BO - 256 places to be refactored
					user = new User();
					if(auth.getPrincipal() instanceof VidyoUserDetails) {
						user.setMemberName(((VidyoUserDetails)auth.getPrincipal()).getMemberName());
						user.setUsername(((VidyoUserDetails)auth.getPrincipal()).getUsername());
						user.setMemberID(((VidyoUserDetails)auth.getPrincipal()).getMemberId());
						user.setTenantID(((VidyoUserDetails)auth.getPrincipal()).getTenantID());
						user.setLangID(((VidyoUserDetails)auth.getPrincipal()).getLangId());
					} else if(auth.getPrincipal() instanceof VidyoUserGuestDetails) {
						user.setMemberName(((VidyoUserGuestDetails)auth.getPrincipal()).getUsername());
						user.setUsername(((VidyoUserGuestDetails)auth.getPrincipal()).getUsername());
						user.setMemberID(((VidyoUserGuestDetails)auth.getPrincipal()).getMemberId());
						user.setTenantID(((VidyoUserGuestDetails)auth.getPrincipal()).getTenantID());
						// LangId is not needed during License API call - TODO verify this
					}
					for (GrantedAuthority role : roles) {
						user.setUserRole(role.getAuthority());
					}
				}
            return user;
        } else {
            return null;
        }
    }

    public User getUserForGuestID(int guestID) {
        User rc = null;
		try {
			rc = this.dao.getUserForGuestID(guestID);
		} catch (Exception e) {
			logger.error("Invalid Guest Id " + guestID);
		}
        return rc;
    }

    public Language getUserLang() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Language userlang = null;

            boolean isGuest = false;
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
            for (GrantedAuthority role : roles) {
                if (role.getAuthority().equalsIgnoreCase("ROLE_GUEST") || role.getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
                    isGuest = true;
                    break;
                }
            }

            if (isGuest) {
                userlang = this.system.getSystemLang(TenantContext.getTenantId());
            } else {
				int tenantId = TenantContext.getTenantId();
                for (GrantedAuthority role : roles) {
                    if (role.getAuthority().equalsIgnoreCase("ROLE_SUPER")) {
                        tenantId = 1;
                        break;
                    }
                }
                userlang = this.dao.getUserLang(tenantId, auth.getName());
            }
            return userlang;
        } else {
            return this.system.getSystemLang(TenantContext.getTenantId());
        }
    }

    public void linkEndpointToUser(String GUID, String clientType, boolean pak2) {

		User user = getLoginUser();
		//calling the common method for CAC and normal operations.
		linkEndpointToUser( GUID,  clientType,  pak2,user);

    }
    public void linkEndpointToUser(String GUID, String clientType, boolean pak2,User user) {

		int memberID = user.getMemberID();
		if (memberID != 0) {
			Endpoint endpoint = endpointService.getEndpointDetail(GUID);
			int status = 0;
			int oldMemberID = 0;
			if(endpoint != null) {
				status = endpoint.getStatus();
				oldMemberID = endpoint.getMemberID();
			}

			// update CDR for GUID
			if (status > 1) {
				try {
					boolean leftConf = this.conference.leaveTheConference(GUID, CallCompletionCode.UNKNOWN);
				} catch (LeaveConferenceException lce) {
					logger.warn("Leave Conference Failed ->" + GUID);
				}
			} else {
				conference.cleanConferenceForLinkedUser(GUID);
			}
			if (oldMemberID != 0 && memberID != oldMemberID) { // new member want to use GUID
				logger.debug("FromLinkEndpoint - unlinkEndpointFromUser memberID - {}, GUID - {}", memberID, GUID);
				unlinkEndpointFromUser(oldMemberID, GUID, UserUnbindCode.MUST_UNBIND_BEFORE_BINDING_TO_DIFFERENT_USER);
			}

			// Step 2 - unlink current member from old GUID which he was linked
			String oldGUID = this.getLinkedEndpointGUID(memberID);
			if (!oldGUID.equalsIgnoreCase("")) {
				int oldstatus = this.conference.getEndpointStatus(oldGUID);
				// update CDR for oldGUID
				if (oldstatus > 1) {
					try {
						boolean leftConf = this.conference.leaveTheConference(oldGUID, CallCompletionCode.UNKNOWN);
					} catch (LeaveConferenceException lce) {
						logger.warn("Leave Conference Failed ->" + oldGUID);
					}
				}
				if (!GUID.equalsIgnoreCase(oldGUID)) { // new member want to switch GUID
					unlinkEndpointFromUser(memberID, oldGUID, UserUnbindCode.LOGGED_IN_ELSEWHERE);
				}
			}
			// Step 3 - Do in any case (new PAK will be generated for Endpoint
			logger.debug("Linkendpoint to user - memberID {}, to GUID {}", memberID, GUID);
			linkEndpointToUser(memberID, GUID, clientType, pak2);
		}else{
			logger.error("member id is 0");
		}
    }

    public void linkEndpointToUser(int memberID, String GUID, String clientType, boolean usePak2) {
        // Step 1 - update DB
        String endpointUploadType = null;
        if(clientType != null && !clientType.isEmpty()) {
        	endpointUploadType = clientType.toUpperCase();
        }
    	this.dao.linkEndpointToUser(memberID, GUID, endpointUploadType);

	    int status = this.conference.getEndpointStatus(GUID);

	    if(usePak2) {
	    	// Delete the pak1 generated during login
	    	dao.deletePak(TenantContext.getTenantId(), memberID);
	    } else {
	    	dao.deletePak2(TenantContext.getTenantId(), memberID);
	    }
	    if(status != 0) {
            // Step 2 - bind and add for VidyoLine license table
            try {
				bindEndpointService.bindUserToEndpoint(memberID, GUID);
			} catch (Exception e) {
				logger.error("Error during bind request",e);
				unlinkEndpointFromUser(memberID, GUID, UserUnbindCode.BIND_FAILURE);
				logger.warn("Unlink endpoint {} from user {}", GUID, memberID);
			}
        }

    }

	/**
	 * Updates the Endpoint table to remove the member info and sends unbind
	 * user from endpoint request to VidyoManager. Sending status notification
	 * for 'offline' might get duplicated if the EMCP connection is reset first
	 * and logout API is invoked later. Endpoint implementations should not
	 * perform EMCP reset and should rely on logout by the portal to take care
	 * of unbind.
	 *
	 * @param memberID
	 *            identifies the Member
	 * @param GUID
	 *            Identifies the Endpoint
	 */
	public void unlinkEndpointFromUser(int memberID, String GUID, UserUnbindCode reasonCode) {
		logger.debug("Entering unlinkEndpointFromUser memberID - {}, GUID - {}", memberID, GUID);
		User user = getLoginUser();
		Alert alert = new Alert(GUID, "Offline", memberID);
		alert.setUsername(user.getUsername());
		Tenant tenant = tenantService.getTenant(user.getTenantID());
		if(tenant != null) {
			alert.setTenantName(tenant.getTenantName());
		}
		NotificationInfo notificationInfo = new NotificationInfo();
		notificationInfo.setAlert(alert);
		statusNotificationService.sendStatusNotificationToQueue(notificationInfo);
		// Step 1 - update DB
		this.dao.unlinkEndpointFromUser(memberID, GUID);
		// Step 2 - unbind and remove for VidyoLine license table
		this.conference.unbindUserFromEndpoint(GUID, reasonCode);
		logger.debug("Exiting unlinkEndpointFromUser");
	}

    public int updateEndpointIPaddress(String GUID, String ipAddress) {
        int rc = this.dao.updateEndpointIPaddress(GUID, ipAddress);
        return rc;
    }
    
    public int updateEndpointExtData(String GUID, int extDataType, String extData) {
    	 int rc = this.dao.updateEndpointExternalData(GUID, extDataType, extData);
         return rc;
    }

	public int updateEndpointCDRInfo(String GUID, CDRinfo2 cdrInfo2) {
		int rc = this.dao.updateEndpointCDRInfo(GUID, cdrInfo2);
		return rc;
	}

	public int updateEndpointReferenceNumber(String GUID, String referenceNumber) {
		int rc = this.dao.updateEndpointReferenceNumber(GUID, referenceNumber);
		return rc;
	}


	public void linkEndpointToGuest(int guestID, String GUID, boolean usePak2) {
        int status = this.conference.getEndpointStatus(GUID);
        // update CDR for old GUID
        if (status > 1) {
            int endpointID = this.conference.getEndpointIDForGUID(GUID, "D");
            int roomID = this.conference.getRoomIDForEndpointID(endpointID);
            try {
                this.conference.leaveTheConference(endpointID, roomID, CallCompletionCode.UNKNOWN);
            } catch (LeaveConferenceException ignored) {
            }
            this.conference.updateEndpointStatus(GUID, "Online", "Portal", null, -1l, null, null);
        }

        this.dao.linkEndpointToGuest(TenantContext.getTenantId(), guestID, GUID);

        // Delete Pak or Pak2 depending upon the client support

        if(usePak2) {
        	guestService.deletePak(guestID, TenantContext.getTenantId());
        } else {
        	guestService.deletePak2(guestID, TenantContext.getTenantId());
        }

        if(status != 0) {
        	// send vcap message only when the status is not offline - also to prevent error messages in logs
        	this.conference.bindGuestToEndpoint(guestID, GUID);
        }

    }

    public int getMyStatus(int memberID, String GUID) {
        int rc = this.dao.getMyStatus(memberID, GUID);
        return rc;
    }

    @Deprecated
    /***
     * @deprecated  - this leads for executing query without checking to which tenant given members belongs.
     * As a consequence this might lead for some cross tenant attacks (user from one tenant try to perform attack
     * against a user from another tenant). Method should be refactored and  should take tenantID as a input param;
     * it should look to something like this:
     *     select endpointGUID from endpoints
     *        join user u on ...
     *        where u.userID = <searched_user>
     *         and u.tenantID = <user_tenant_id>
     */
    public String getLinkedEndpointGUID(int memberID) {
        String rc = this.dao.getLinkedEndpointGUID(memberID);
        return rc;
    }

    public boolean isMemberLinkedToEndpoint(int memberID, String GUID) {
        return this.dao.isMemberLinkedToEndpoint(memberID, GUID);
    }

    public PortalAccessKeys generatePAKforMember(int memberID) {
        String pak = PortalUtils.generateKey(16);
        String pak2 = SecureRandomUtils.generatePak2(47);
        this.dao.savePAKforMember(memberID, pak, pak2);
        PortalAccessKeys portalAccessKeys = new PortalAccessKeys();
        portalAccessKeys.setPak(pak);
        portalAccessKeys.setPak2(pak2);
        int tenantId = TenantContext.getTenantId();
        String username = null;
        try {
			username = memberDao.getMember(tenantId, memberID).getUsername();
		} catch (Exception e) {
			logger.error("Exception while getting username for memberId {}", memberID);
		}
        //Clear cache object
        dao.updateMember(tenantId, username != null ? username.toLowerCase(Locale.ENGLISH) : null);
        return portalAccessKeys;
    }

    public PortalAccessKeys generatePAKforGuest(int guestID) {
        String PAK = PortalUtils.generateKey(16);
        String pak2 = SecureRandomUtils.generatePak2(47);
        this.dao.savePAKforGuest(guestID, PAK, pak2);
        PortalAccessKeys portalAccessKeys = new PortalAccessKeys();
        portalAccessKeys.setPak(PAK);
        portalAccessKeys.setPak2(pak2);
        return portalAccessKeys;
    }

    public PortalAccessKeys getPAKforMember(int memberID) {
    	PortalAccessKeys portalAccessKeys = null;
    	try {
			portalAccessKeys = this.dao.getPAKforMember(memberID);
			return portalAccessKeys;
		} catch (DataAccessException dae) {
			logger.error("No PAK, PAK2 available for Member {}", memberID);
		}
        return portalAccessKeys;
    }

    public PortalAccessKeys getPAKforGuest(int guestID) {
        PortalAccessKeys portalAccessKeys = this.dao.getPAKforGuest(guestID);
        return portalAccessKeys;
    }

    public int getMemberIDForPAK(String PAK) {
        int rc = this.dao.getMemberIDForPAK(PAK);
        return rc;
    }

	public String generateBAKforMember(int memberID) {
		String BAK = SecureRandomUtils.generateHashKey(16).trim();
		logger.debug("BAK " + BAK + "generated for memberID " + memberID);
		try {
			this.dao.saveBAKforMember(memberID, SHA1.enc(BAK));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("generateBAKforMember = " + e.getMessage());
		}
		String username = null;
		try {
			username = memberDao.getMember(TenantContext.getTenantId(), memberID).getUsername();
		} catch (Exception e) {
			logger.error("Exception while getting username for memberId {}", memberID);
			try {
				username = memberDao.getSuper(memberID).getUsername();
			} catch (Exception e1) {
				logger.error("Exception while getting super's username for memberId {}", memberID);
			}
		}
		// Clear cached object
		dao.updateMember(TenantContext.getTenantId(), username != null ? username.toLowerCase(Locale.ENGLISH) : null);
		return BAK;
	}

	public String generateBAKforMember(int memberID, MemberBAKType bakType) {
		String BAK = SecureRandomUtils.generateHashKey(16).trim();
		logger.debug("BAK " + BAK + "generated for memberID " + memberID);
		try {
			MemberBAK memberBAK = new MemberBAK();
			memberBAK.setBak(SHA1.enc(BAK));
			memberBAK.setBakType(bakType.name());
			memberBAK.setMemberId(memberID);
			memberBAK.setCreationTime(Calendar.getInstance().getTime());
			memberBAKRepository.save(memberBAK);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("generateBAKforMember = " + e.getMessage());
		}
		return BAK;
	}

	public Integer getMemberIDForBAK(String BAK, MemberBAKType bakType) {
		MemberBAK memberBAK = null;
		try {
			memberBAK = memberBAKRepository.findByBakAndBakType(SHA1.enc(BAK), bakType.name());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("getMemberIDForBAK = " + e.getMessage());
		}

		if (memberBAK == null) {
			logger.error("Browser Access Key is invalid - {}", BAK);
			return null;
		}

		// Validate the creation time - invalid if more than the MemberBAK
		// timeout config
		long timeoutConfigValue = 360;
		try {
			Configuration timeoutConfig = system.getConfiguration(0, MEMBER_BAK_INACTIVE_LIMIT_IN_MINS);
			timeoutConfigValue = Long.valueOf(timeoutConfig.getConfigurationValue());
		} catch (Exception e) {
			logger.error("Error reading MemberBAK timeout config", e);
		}
		long bakCreationTime = memberBAK.getCreationTime().getTime();
		long currentTime = Calendar.getInstance().getTimeInMillis();
		long maxDuration = TimeUnit.MILLISECONDS.convert(timeoutConfigValue, TimeUnit.MINUTES);
		long duration = (currentTime - bakCreationTime);
		if (duration > maxDuration) {
			logger.error("Browser Access Key expired - {}, duration - {}", BAK, duration);
			return null;
		}

		return memberBAK.getMemberId();
	}

    /**
     *
     */
	public void generateSAKforMember(int tenantId, int memberID,
			String challenge, long bindUserRequestID, boolean usePak2) {
		// When link endpoint request is made, pak2Enabled key value is sent
		// from Client
		PortalAccessKeys portalAccessKeys = getPAKforMember(memberID);
		String sak = null;
		if (usePak2) {
			// if pak2 supported, use that to generate sak
			sak = VidyoHash.getBindUserChallengeResponse(challenge,
                    portalAccessKeys.getPak2());
		} else {
			String pakToUse = portalAccessKeys.getPak() != null ? portalAccessKeys
					.getPak() : portalAccessKeys.getPak2();
			if (pakToUse == null || pakToUse.isEmpty()) {
				throw new RuntimeException(
						"Both pak and pak2 are not available for Member, Terminating BindUser "
								+ memberID);
			}
			sak = VidyoHash.getBindUserChallengeResponse(challenge, pakToUse);
		}

		dao.saveSAKforMember(memberID, sak, bindUserRequestID);
		String username = null;
		try {
			username = memberDao.getMember(tenantId, memberID).getUsername();
		} catch (Exception e) {
			logger.error("Exception while getting username for memberId {}",
					memberID);
		}
		// Clear cache object
		dao.updateMember(tenantId,
				username != null ? username.toLowerCase(Locale.ENGLISH) : null);
	}

	public void generateSAKforGuest(int guestID, String challenge,
			long bindUserRequestID, boolean usePak2) {
		// When link endpoint request is made, pak2Enabled key value is sent
		// from Client
		PortalAccessKeys portalAccessKeys = getPAKforGuest(guestID);
		String sak = null;
		if (usePak2) {
			// if pak2 supported, use that to generate sak
			sak = VidyoHash.getBindUserChallengeResponse(challenge,
                    portalAccessKeys.getPak2());
		} else {
			String pakToUse = portalAccessKeys.getPak() != null ? portalAccessKeys
					.getPak() : portalAccessKeys.getPak2();
			if (pakToUse == null || pakToUse.isEmpty()) {
				throw new RuntimeException(
						"Both pak and pak2 are not available for Guest, Terminating BindUser "
								+ guestID);
			}
			sak = VidyoHash.getBindUserChallengeResponse(challenge, pakToUse);
		}
		this.dao.saveSAKforGuest(guestID, sak, bindUserRequestID);
	}

    public String getSAKforMember(int memberID) {
        String rc = this.dao.getSAKforMember(memberID);
        return rc;
    }

    public long getBindUserRequestIDforMember(int memberID) {
        long rc = this.dao.getBindUserRequestIDforMember(memberID);
        return rc;
    }

    public String getSAKforGuest(int guestID) {
        String rc = this.dao.getSAKforGuest(guestID);
        return rc;
    }

    public long getBindUserRequestIDforGuest(int guestID) {
        long rc = this.dao.getBindUserRequestIDforGuest(guestID);
        return rc;
    }

	public boolean isUserLicenseRegistered(String EID) {
		boolean rc = this.dao.isUserLicenseRegistered(EID);
		return rc;
	}

	public int registerLicenseForUser(User user, String EID, String ipAddress, String hostname) {
        int rc;
		Tenant t = this.tenantService.getTenant(TenantContext.getTenantId());
		// is a CDR format new? "0" - old, "1" - new
		String cdrFormat = getConfigValue(1, "CdrFormat");
		if (cdrFormat.equalsIgnoreCase("0")) {
			rc = this.dao.registerLicenseForUser(user.getMemberName() + "(" + user.getUsername() + ")", t.getTenantName(), EID, ipAddress, hostname);
		} else {
			rc = this.dao.registerLicenseForUser2(user.getUsername(), user.getMemberName(), t.getTenantName(), EID, ipAddress, hostname);
		}
        return rc;
    }

    public int updateRegisterLicenseForGuest(String userName, String guestName, Room room) {
        int rc;
		// is a CDR format new? "0" - old, "1" - new
        String cdrFormat = getConfigValue(1, "CdrFormat");
		if (cdrFormat.equalsIgnoreCase("0")) {
			rc = this.dao.updateRegisterLicenseForGuest(userName, guestName, room);
		} else {
			rc = this.dao.updateRegisterLicenseForGuest2(userName, guestName, room);
		}
        return rc;
    }

    public int getLicensedNumberOfInstallLeft() {
        Tenant t = this.tenantService.getTenant(TenantContext.getTenantId());
        int total = t.getInstalls();

        int consumed = this.dao.getNumOfConsumedLicenses(t.getTenantName()) + this.dao.getNumOfConsumedLicenses2(t.getTenantName());

		int rc = total - consumed;
        return rc;
    }

    public void checkRemainingInstallsBalance(int remainingBalance){
        // All business logic are put inside try-catch block. If anything failed, then let it be failed, log it only.
        //
        // Based on the discussion with Yuriy, we should not let any failure of this checking-remaining-balance process
        // affect registering-license process (in VidyoLicenseServiceSkeleton.java)
        // The best way is make balance-checking-process running in addition thread by using even-listener mechanism, I
        // will change it.
        try {
        	int langId = this.system.getSystemLang(TenantContext.getTenantId()).getLangID();

        	Locale loc = LangUtils.getLocaleByLangID(langId);

            if((remainingBalance == 5) || (remainingBalance == 15) || (remainingBalance == 25))
            {
                logger.info("Remaining Install Balance="+remainingBalance);
                String fromAddr = getConfigValue(TenantContext.getTenantId(), "NotificationEmailFrom");
                String toAddr = getConfigValue(TenantContext.getTenantId(), "NotificationEmailTo");
                String fromSuperAddr = getConfigValue(0, "SuperEmailFrom");
                String toSuperAddr = getConfigValue(0, "SuperEmailTo");

                if(fromAddr==null || fromAddr.trim().length()==0) {
                    if(fromSuperAddr==null || fromSuperAddr.trim().length()==0)
                        fromAddr = ms.getMessage("default.from.address", null, "", loc);
                    else
                        fromAddr = fromSuperAddr;
                }
                if(toAddr==null || toAddr.trim().length()==0){
                    logger.info("    - Admin To Email Address was not set.");
                }
                if(toSuperAddr==null || toSuperAddr.trim().length()==0){
                    logger.info("    - Super To Email Address was not set.");
                }

                Tenant t = this.tenantService.getTenant(TenantContext.getTenantId());

                String subject = ms.getMessage("vidyodesktop.install.license.notification", null, "", loc);
                String content = ms.getMessage("dear.vidyoportal.admin", null, "", loc) +
                		MessageFormat.format(ms.getMessage("you.have.x.remaining.install.licenses.remaining", null, "", loc), remainingBalance) +
                		ms.getMessage("contact.account.representative.for.more.install.licenses", null, "", loc) +
                		MessageFormat.format(ms.getMessage("tenant.name.is", null, "", loc), t.getTenantName()) +
                		MessageFormat.format(ms.getMessage("tenant.url.is", null, "", loc), t.getTenantURL()) +
                		MessageFormat.format(ms.getMessage("ip.address.is", null, "", loc), getLocalHostIP()) +
                		ms.getMessage("thank.you", null, "", loc);

                //send email to Tenant Admin
                if(toAddr!=null && toAddr.trim().length()>0){
                    emailService.sendEmailSynchronous(fromAddr, toAddr, subject, content);
                    logger.info("    - Send notification email from ["+fromAddr+"] to ["+toAddr+"].\n");
                }
                //send email to Super Admin
                if(toSuperAddr!=null && toSuperAddr.trim().length()>0){
                    emailService.sendEmailSynchronous(fromAddr, toSuperAddr, subject, content);
                    logger.info("    - Send notification email from ["+fromAddr+"] to ["+toSuperAddr+"].\n");
                }
            }
        }
        catch(Exception anyEx) {
            logger.info("Failed to send out notification email. remainingBalance="+remainingBalance);
        }
    }

    private String getLocalHostIP(){
        String ip="";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()){
                NetworkInterface etc = (NetworkInterface)interfaces.nextElement();
                if(etc.isLoopback())
                    continue;
                if(etc.getName().equals("eth0")) {
                    Enumeration<InetAddress> ips = etc.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        InetAddress thisComputer = (InetAddress)ips.nextElement();
                        if(thisComputer instanceof Inet4Address) {
                            byte[] address = thisComputer.getAddress();
                            for (int i = 0; i < address.length; i++) {
                                int unsignedByte = address[i] < 0 ? address[i] + 256 : address[i];
                                ip += unsignedByte ;
                                if(i<(address.length-1))
                                    ip += ".";
                            }
                            return ip;
                        }
                    }
                }
            }
        }
        catch(Exception ignored) {
        }
        return ip;
    }

    public int setAuthorizedFlagForEndpoint(Endpoint endpoint) {
        int rc = this.dao.setAuthorizedFlagForEndpoint(endpoint);
        return rc;
    }

	public String getLoginUserRole() {
		String rc = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		for (GrantedAuthority role : roles) {
			rc = role.getAuthority(); // hope user has only one role. in case of multiple - return last one
		}
		return rc;
	}

	/**
	 * Returns the SAK and BindUserRequestID for bind user challenge
	 *
	 * @param memberID
	 * @return
	 */
	@Override
	public User getUserForBindChallengeResponse(int memberID) {
		User user = dao.getUserForBindChallengeResponse(memberID);
		return user;
	}

	/**
	 * Returns the onetime access URI after generating browser access key
	 *
	 * @param tenantId Tenant to which the member belongs to
	 * @param memberId Member for which the access is requested
	 * @return access key and url
	 * @see AccessKeyResponse
	 */
	public AccessKeyResponse getOnetimeAccessUri(int tenantId, int memberId, MemberBAKType bakType) {
		Tenant tenant = tenantService.getTenant(tenantId);
		AccessKeyResponse accessKeyResponse = new AccessKeyResponse();
		if(tenant == null) {
			accessKeyResponse.setStatus(AccessKeyResponse.INVALID_TENANT);
			return accessKeyResponse;
		}

		String tenantUri = tenant.getTenantURL();
		String onetimeAccessKey = null;

		try {
			onetimeAccessKey = generateBAKforMember(memberId, bakType);
		} catch (Exception e) {
			logger.error("Error while generating/saving browser access key for member - {}", memberId);
		}

		if(onetimeAccessKey == null) {
			accessKeyResponse.setStatus(AccessKeyResponse.KEY_GEN_FAILED);
			return accessKeyResponse;
		}
		accessKeyResponse.setAccessKey(onetimeAccessKey);
		accessKeyResponse.setTenantUrl(tenantUri);
		accessKeyResponse.setPortalPath(getUserPortalPath());
		return accessKeyResponse;
	}

	/**
	 * Return the Member matching the bak (browser access key)
	 * @param bak
	 * @return
	 */
	public Member getMemberForBak(String bak, MemberBAKType bakType) {
		Integer memberId = getMemberIDForBAK(bak, bakType);

		if (memberId == null) {
			return null;
		}

		return memberRepository.findByMemberID(memberId);
	}

    @Override
    public String getUserDefaultPassword() {
        String defaultPassword = getConfigValue(1, "USER_DEFAULT_PASSWORD");
        if(defaultPassword == null || defaultPassword.isEmpty()) {
            defaultPassword = "password";
        }

        return defaultPassword;
    }

    private String getConfigValue(int tenantID, String configurationName){
		Configuration config = system.getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();

	}

	public void deleteMemberBAK(String bak) {
		try {
			memberBAKRepository.deleteByBak(SHA1.enc(bak));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error("deleteMemberBAK : " + bak + " : " + e.getMessage());
		}
	}

}