package com.vidyo.web.portal;

import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.EPIC;
import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.VIDYO;
import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.validateExtDataType;
import static com.vidyo.service.configuration.enums.ExternalDataTypeEnum.isValidEpicExtDataType;
import static java.lang.String.format;
import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;
import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.UriBuilder;

import com.vidyo.service.conference.request.GuestJoinConfRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.bo.AppStoreConfig;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointSettings;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.Member;
import com.vidyo.bo.ProtocolHandlerParam;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlRelayStateParam;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.security.PortalAccessKeys;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.db.repository.security.samltoken.TempAuthToken;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.ILoginService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.PortalService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.configuration.enums.MobileAppParameters;
import com.vidyo.service.member.response.MemberManagementResponse;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.service.security.samltoken.TempAuthTokenService;
import com.vidyo.service.security.token.PersistentTokenService;
import com.vidyo.service.security.token.request.TokenCreationRequest;
import com.vidyo.service.security.token.response.TokenCreationResponse;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.HtmlUtils;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecureRandomUtils;
import com.vidyo.utils.TCAndPrivacyPolicyUtils;
import com.vidyo.utils.UserAgentUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.web.WebException;

@Controller
public class FlexUIController {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(FlexUIController.class.getName());

	//The below links are for Vidyo Mobile
	private static final String APPLE_STORE_VIDYOMOBILE = "itms-apps://itunes.apple.com/us/app/vidyomobile/id444062464?mt=8";
	private static final String APPLE_STORE_VIDYOSLATE =  "itms-apps://itunes.apple.com/us/app/vidyoslate/id660477058?ls=1&mt=8";
	private static final String GOOGLE_STORE_VIDYOMOBILE = "https://play.google.com/store/apps/details?id=com.vidyo.VidyoClient";
	private static final String GOOGLE_STORE_VIDYOSLATE =  "https://play.google.com/store/apps/details?id=com.vidyo.vidyoslate";

	@Autowired
	private IUserService user;

	@Autowired
	private IRoomService room;

    @Autowired
	private IMemberService member;

    @Autowired
	private ISystemService system;

    @Autowired
	@Qualifier("conferenceService")
    private IConferenceService conference;

    @Autowired
	private EndpointUploadService endpoint;

    @Value("${upload.path}")
    private String upload_path;

	@Autowired
	private IServiceService service;

	@Autowired
	private ILoginService login;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private ConferenceAppService conferenceAppService;

    @Autowired
    private TransactionService transactionService;

	@Autowired
	@Qualifier("authenticationManagerWithLicenseCheck")
    private AuthenticationManager authenticationManager;
    @Autowired
    private TempAuthTokenService tempAuthTokenService;

    @Autowired
    private PersistentTokenService persistentTokenService;

    @Autowired
    private AppStoreConfig appstoreConfig;

    @Autowired
	private PortalService portalService;

	@Autowired
	private ExtIntegrationService extIntegrationService;

	@Deprecated
	void setExtIntegrationService(ExtIntegrationService s) {
	    this.extIntegrationService = s;
    }
	
	void setConferenceService(IConferenceService conference) {
		this.conference = conference;
	}

	private static final String LOG_EPIC_INTEGRATION_NOT_ENABLED = "Epic integration is not enabled";
	private static final String LOG_EPIC_INTEGRATION_IS_INVALID = "Epic external data is invalid - ";
	private static final String LOG_EXTERNAL_INVALID_ROOM_ATTEMPT = "Invalid externalRoomId: ( %s + )";
	private static final String LOG_INVALID_ROOM_ID_ATTEMPT = "Invalid roomId: ( %s )";
	private static final String LOG_INVALID_ROOM_KEY_ATTEMPT = "Invalid key ( %s )";

	private int logFailRoomAccessAttenot(String paramName) {
		Integer tenantId = TenantContext.getTenantId();
		if (tenantId == null) {
			tenantId = 1;
		}
		String tenantName = tenantService.getTenant(tenantId).getTenantName();
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Guest/Room Link");
		transactionHistory.setTenantName(tenantName);
		transactionHistory.setTransactionResult("FAIL");
		transactionHistory.setTransactionParams(paramName);
		logger.info ("added to transaction history: category: Guest/Room link, result: FAIL, reason" + paramName);
		return transactionService.addTransactionHistoryWithUserLookup(transactionHistory);
	}

    // epic integration processing method
	private Room processExternalIntegration(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {

		if (!extIntegrationService.isTenantEpicEnabled()) {
			logFailRoomAccessAttenot(LOG_EPIC_INTEGRATION_NOT_ENABLED);
			response.sendError(404, "epicnotenabled");
			return null;
		}

		String externalData = getStringParameter(request, "extData");
		Map<String, String> parametersFromExternalData = new HashMap<String, String>();
		StringBuilder decryptedExternalData = new StringBuilder();
		boolean isExternalDataInvalid = extIntegrationService.validateExternalDataAndDecrypt(externalData,
				parametersFromExternalData, decryptedExternalData);

		String sessionId = parametersFromExternalData.get("SessionID");
		if ((sessionId == null) || sessionId.isEmpty())
			isExternalDataInvalid = true;

		if (isExternalDataInvalid || (parametersFromExternalData.size() == 0)) { // invalid external data
			logFailRoomAccessAttenot(LOG_EPIC_INTEGRATION_IS_INVALID + decryptedExternalData.toString());
			response.sendError(404, "externaldatainvalid");
			return null;
		}

		// Prepend session id with TenantId
		sessionId = TenantContext.getTenantId() + "_" + sessionId;

		// check is room for conference exists and create if not
		Room room = extIntegrationService.createRoomIfNotExist(sessionId);

		if (room == null) {
			logFailRoomAccessAttenot(format(LOG_EXTERNAL_INVALID_ROOM_ATTEMPT, sessionId));
			response.sendError(404, "invalidroom");
			return null;
		}
		// Room is available at this point
		model.put("key", room.getRoomKey());
		model.put("extData", getStringParameter(request, "extData"));
		model.put("extDataType", validateExtDataType(
				getStringParameter(request, "extDataType")).toString());
		model.put("dispName", parametersFromExternalData.get("FirstName") + " " + parametersFromExternalData.get("LastName"));
		model.put("firstName", StringUtils.trimAllWhitespace(parametersFromExternalData.get("FirstName")));
		model.put("lastName", StringUtils.trimAllWhitespace(parametersFromExternalData.get("LastName")));

		return room;
	}

	@RequestMapping(value = "/join/*", method = RequestMethod.GET)
	public ModelAndView getRoomJoin(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String requestURI = request.getRequestURI();
		String key = "none";
		try {
			key = requestURI.substring(requestURI.indexOf("/join/")+6);
		} catch (Exception e) {
			logger.error("Exception while getting room key using join link "+ e);
		}
		
		String directDialAsStringParam = "";
		String directDial = ServletRequestUtils.getStringParameter(request, "directDial", "");
		if (!directDial.isEmpty()) {
			directDialAsStringParam = directDialAsStringParam.concat("&directDial=").concat(directDial); 
		}
		return new ModelAndView("forward:/flex.html?roomdirect.html&key=" + key + directDialAsStringParam);
	}

	@RequestMapping(value = "/flex.html", method = RequestMethod.GET)
	public ModelAndView getFlexHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();
        // Fetch the requestMap with all the request parameters.
		Map<String, String[]> reqParamMap = request.getParameterMap();

		String forceNeoFlag = getStringParameter(request, "forceNeo", "false");
		model.put("forceNeoFlag", forceNeoFlag);
        String userAgent = request.getHeader("user-agent");
        if(VendorUtils.isDISA()){
            if(null != userAgent){
                if(!UserAgentUtils.isVidyoRoom(userAgent)){// Request not came from VidyoRoom
                    if(!request.getRequestURL().toString().contains("roomdirect.html")){ // Guest link
                        model.put("canotlogin", "true");
                    }
                } else {
                    model.put("vendor", "DISA");
                    if(system.showLoginBanner()){
                        model.put("showLoginBanner", "true");
                        String banner = "";
                        banner =  system.getLoginBanner();
                        banner =  HtmlUtils.stripBreakTags(HtmlUtils.stripNewlineCharacters(banner));
                        model.put("loginBannerText", banner );
                    }
                }
            }
        }

        prepareLogo(model);

        String passKey = getStringParameter(request, "pwd", "");
		if (!"".equalsIgnoreCase(passKey)) {
			String resetMsg = ms.getMessage("your.password.has.been.reset.and.e.mailed.to.you.please.check.your.e.mail.account.for.your.new.password", null, "", loc);

			boolean isPasswordReset = true;
			int resetVal = this.login.resetFlexPassword(passKey, request);
			if(resetVal == -100) {
				resetMsg = ms.getMessage("email.notifications.not.configured", null, "", loc);
				isPasswordReset = false;
			}else if(resetVal == -101) {
				resetMsg = ms.getMessage("there.was.a.problem.resetting.your.password.this.reset.key.may.have.expired.please.try.resetting.your.password.again", null, "", loc);
				isPasswordReset = false;
			}

			if (!UserAgentUtils.isVidyoRoom(userAgent)) {
				model.put("infoMsg", resetMsg);
				model.put("isInfoCheck", isPasswordReset);
				model.put("title", ms.getMessage("password.reset", null, "", loc));

				return new ModelAndView("closetab_html", "model", model);
			}
		}
		// If the URL contains room key, validate the key
		// Room key should not be initialized as empty string as this URL is a redirection now
		String roomKey = getStringParameter(request, "key");
		String directDial = getStringParameter(request, "directDial");
		Room room = null;

		if (StringUtils.isEmpty(roomKey)) {
			room = populateDirectDialParams(model, directDial);
			
			if (room == null) {
				String extDataType = getStringParameter(request, "extDataType");
				if (!isValidEpicExtDataType(extDataType)) {
					response.sendError(404, "externaldatatypeinvalid");
					return null;
				}
			
				room = processExternalIntegration(request, response, model);
			} 
			
			// If the integration is not available or there is some issue in decrypting, the response will be 404
			if (room == null) return null; // was error
			// Assignment is needed for roomkey
			roomKey = room.getRoomKey();
		} else if (roomKey != null) {
			room = this.room.getRoomDetailsByRoomKey(roomKey, TenantContext.getTenantId());
		}

		if (room == null) {
			// Check if it is a scheduled room
			String[] extPin = roomKey.split(":");
			if(extPin.length == 2){
				ScheduledRoomResponse scheduledRoomResponse = conferenceAppService.validateScheduledRoom(extPin[0], extPin[1]);
				if(scheduledRoomResponse.getStatus() == 0) {
					room = scheduledRoomResponse.getRoom();
					room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
				}
			}
		}

		if (room == null) {
			//if the room key is invalid, set invalidroom parameter in the URL
			//response.sendRedirect("flex.html?invalidroom");
			if(roomKey != null) {
				String logValue;
				if (roomKey.length() > 100) {
					logValue = roomKey.substring(0, 100) + "...";
				} else {
					logValue = roomKey;
				}
				logValue = StringUtils.trimAllWhitespace(logValue);
				logFailRoomAccessAttenot(format(LOG_INVALID_ROOM_KEY_ATTEMPT ,  logValue));
			}

			response.sendError(404, "invalidroom");
			return null;
		}

		if (!this.room.areGuestAllowedToThisRoom()) {
			AuthenticationConfig authConfig= this.system.getAuthenticationConfig(TenantContext.getTenantId());
			Authentication authType = authConfig.toAuthentication();
			// If guests are not allowed and auth type is not SAML, redirect to forbidden page
			if(!(authType instanceof SamlAuthentication)) {
				response.sendError(404, "guestnotallowed");
	            return null;
			} else {
				//If auth type is SAML, redirect to SAML login page
				String queryParamToRedirect;
				if (!StringUtils.isEmpty(directDial)) {
					queryParamToRedirect = "directDial=" + directDial;
				} else {
					queryParamToRedirect = "roomKey=" + roomKey;
				}
				String samlRedirectUrl = "/saml/login?" + queryParamToRedirect;
				logger.info(" user will be redirected to saml login url: " + samlRedirectUrl);
				response.sendRedirect(samlRedirectUrl); // Room key is alphanumeric, so safe to just append it
			}
		}

		if (userAgent != null) {
			if (UserAgentUtils.isSupportedTouchDevice(userAgent)) {
				UriBuilder uriBuilder = UriBuilder.fromPath("/mobile.html");
				uriBuilder.queryParam("tmst", (int)(new Date().getTime() * .001));
				if(model.get("extData") != null) {
					uriBuilder.queryParam("extData", model.get("extData"));
				}
				if(model.get("extDataType") != null) {
					uriBuilder.queryParam("extDataType",
							validateExtDataType(model.get("extDataType")).toString());
				}
				if(model.get("dispName") != null) {
					uriBuilder.queryParam("dispName", model.get("dispName"));
				}
				if (!StringUtils.isEmpty(directDial)) {
					uriBuilder.queryParam("directDial", directDial);
					uriBuilder.queryParam("ddDisplayName", model.get("ddDisplayName"));
				} else {
					uriBuilder.queryParam("key", roomKey);
				}
				response.sendRedirect(uriBuilder.build().toString());
                return null;
            }
        }

		boolean isUVD = prepareBaseModel(request, response, model, roomKey);
		Integer tenantId = TenantContext.getTenantId();
		boolean isNeo = VendorUtils.isForceNeoFlow() || !isUVD || system.isWebRTCEnabledForGuests(tenantId);

        prepareVidyoWeb(request, model, passKey, roomKey, tenantId);

		processUserAgent(model, userAgent);

		// Process custom protocol handler
		model.put("appProtocol",
				tenantService.getTenantConfiguration(TenantContext.getTenantId()).getDesktopProtocol());

        return processModelAndView(request, model, reqParamMap, userAgent,
				roomKey, room, tenantId, isNeo);
	}

	private void prepareLogo(Map<String, Object> model) {
		String logo = PortalUtils.prepareLogo(this.system);
		model.put("logoUrl", logo);
	}

	private void prepareVidyoWeb(HttpServletRequest request, Map<String, Object> model, String passKey, String roomKey,
			Integer tenantId) {
		String vidyoDesktopInstalled = getStringParameter(request, "vd", null);
        if (roomKey != null && "".equals(passKey) && vidyoDesktopInstalled == null) {
            if (system.isVidyoWebEnabled(tenantId)) {
                model.put("vidyoWebEnabled", true);
				model.put("zincUrl", getZincServer());
            }
        } else {
            model.put("vidyoWebEnabled", false);
        }

		model.put("forceNeoVidyoWeb", VendorUtils.isForceNeoVidyoWeb());
	}

	private boolean prepareBaseModel(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, String roomKey) {
		boolean isUVD = true;
		String portalVerison = system.getPortalVersion();
		model.put("portalVerison", portalVerison);

        // no caching as we need to check if vidyoDesktop installed/running everytime
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

		EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(request);
        model.put("key", roomKey);
        model.put("host", request.getScheme() + "://" + request.getServerName());


		if (endpoint != null) {
			String installerName = endpoint.getEndpointUploadFile();
			if (installerName != null && installerName.contains("TAG_VD_3")) {
				isUVD = true;
			} else {
				isUVD = false;
			}
			String installer = null;
			if (endpoint.getEndpointUploadVersion() == null) {
				UriBuilder uriBuilder = UriBuilder.fromPath("")
						.path(upload_path).path(endpoint.getEndpointUploadFile());
				if(!isUVD) {
					uriBuilder.queryParam("r", roomKey);
				}
				installer = uriBuilder.build().toString();
			} else {
				installer = this.endpoint.getCDNInstallerURLWithParam(endpoint.getEndpointUploadFile(), request.getScheme(), request.getHeader("host"), roomKey);
			}
			model.put("installer", installer);
		} else {
			model.put("installer", "no_installer");
		}
		return isUVD;
	}

	private void processUserAgent(Map<String, Object> model, String userAgent) {
		if (userAgent != null) {
			if (UserAgentUtils.isIphone(userAgent)) {
				model.put("os", "ios");
			} else if (UserAgentUtils.isIPad(userAgent)) {
				model.put("os", "ios");
			} else if (UserAgentUtils.isAndroid(userAgent) && UserAgentUtils.isMobile(userAgent)) {
				model.put("os", "android");
			} else if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) {
				model.put("os", "android");
			} else if (UserAgentUtils.isMac(userAgent)) {
				model.put("os", "mac");
			} else if (UserAgentUtils.isLinux(userAgent)) {
				model.put("os", "linux");
			} else if (UserAgentUtils.isWindows10(userAgent)) {
				model.put("os", "windows10");
			} else if (UserAgentUtils.isWindows(userAgent)) {
				model.put("os", "windows");
			}

			if (UserAgentUtils.isBrowserEdge(userAgent)) {
				model.put("browser", "edge"); // also reports itself as chrome so do this first
			} else if (UserAgentUtils.isBrowserChrome(userAgent)) {
				model.put("browser", "chrome");
			} else if (UserAgentUtils.isBrowserFirefox(userAgent)) {
				model.put("browser", "firefox");
			} else if (UserAgentUtils.isBrowserSafari(userAgent)) {
				model.put("browser", "safari");
			} else if (UserAgentUtils.isBrowserIE(userAgent)) {
				model.put("browser", "ie");
			}
		}
	}

	private ModelAndView processModelAndView(HttpServletRequest request, Map<String, Object> model,
			Map<String, String[]> reqParamMap, String userAgent, String roomKey, Room room,
			Integer tenantId, boolean isNeo) {
		EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(request);

		if (!UserAgentUtils.isVidyoRoom(userAgent) && roomKey != null){
			if (
					system.isWebRTCEnabledForGuests(tenantId) &&
							(UserAgentUtils.isLinux(userAgent) || UserAgentUtils.isChromeOS(userAgent))) {
                //Instead of just the host, now send the complete RequestURL without the request URI as part of request Param
                String portalURL = StringUtils.delete(request.getRequestURL().toString(),request.getRequestURI());
				return new ModelAndView("redirect:" + getNeoWebRTCUrl(portalURL, roomKey,reqParamMap, model));
			}

        	model.put("isInfoCheck", true);
			if (!isNeo) {
				return new ModelAndView("vd_test", "model", model);
			} else {
				model.put("portalFeatures", portalService.getEncodedPortalFeatures());
				model.put("pinned", (room != null && StringUtils.hasText(room.getRoomPIN())));
				// Support for Microsoft Edge browser https://jira.vidyo.com/browse/VPTL-7557 added in 17.1.1 release.
				if (
						system.isWebRTCEnabledForGuests(tenantId) &&
						(UserAgentUtils.isMac(userAgent) || UserAgentUtils.isWindows(userAgent)) &&
						(UserAgentUtils.isBrowserChrome(userAgent) || UserAgentUtils.isBrowserFirefox(userAgent)) //||
								//(UserAgentUtils.isBrowserEdge(userAgent) && UserAgentUtils.edgeSupportsWebRTC(userAgent.substring(userAgent.indexOf("Edge/")+5), this.baseEdgeVersion))
								) {
					model.put("neoWebRTCAvailable", true);
					//Instead of just the host, now send the complete RequestURL without the request URI as part of request Param
					String portalURL = StringUtils.delete(request.getRequestURL().toString(),request.getRequestURI());
					model.put("neoWebRTCUrl", getNeoWebRTCUrl(portalURL, roomKey, reqParamMap, model));

					if (endpoint == null) { // no installer
						return new ModelAndView("redirect:" + getNeoWebRTCUrl(portalURL, roomKey, reqParamMap, model));
					}
				} else {
					if (endpoint == null && !VendorUtils.isForceNeoFlow()) { // no installer, reuse vd page for this case
						return new ModelAndView("vd_test", "model", model);
					}
				}

				// https://jira.vidyo.com/browse/VPTL-7320 - changes for removing the scriplets from JSP.
				model.put("isVidyoCloud", VendorUtils.isVidyoCloud());

				model.put("privacyUrl", TCAndPrivacyPolicyUtils.getPrivacyPolicyUrl(system, tenantService));
				model.put("termsUrl", TCAndPrivacyPolicyUtils.getTermsAndConditionsUrl(system, tenantService));
				model.put("reqParamMap", reqParamMap);
				return new ModelAndView("neo_test", "model", model);
			}
        } else {
		    return new ModelAndView("vui_html", "model", model);
        }
	}

	@RequestMapping(value = "/mobile.html", method = RequestMethod.GET)
	public ModelAndView getMobileHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		// If the URL contains room key, validate the key
		// Room key should not be initialized as empty string as this URL is a redirection now
		String roomKey = getStringParameter(request, "key");
		String directDial = getStringParameter(request, "directDial");

		int tenantId = TenantContext.getTenantId();
		Tenant tenant = tenantService.getTenant(tenantId);
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);

		Room theRoom = null;
		if (roomKey != null && !roomKey.isEmpty()) {
			theRoom = this.room.getRoomDetailsByRoomKey(roomKey, tenantId);
			if (theRoom == null) {
				//if the room key is invalid, set invalidroom parameter in the URL
				response.sendError(404);
				return null;
			}
            model.put("roomName", theRoom.getRoomName() + "@" + request.getHeader("host"));
		} else {
			theRoom = populateDirectDialParams(model, directDial);
		}

		boolean epicFlag = false;
		// If the request is redirected from desktp page, check for extData and extDataType
		if (ServletRequestUtils.getStringParameter(request, "extData", null) != null
				&& ( validateExtDataType(getIntParameter(request, "extDataType", VIDYO.ordinal()))
				== EPIC.ordinal())) {
			theRoom = processExternalIntegration(request, response, model);
			roomKey = theRoom.getRoomKey();
			epicFlag = true;
			model.put("epicFlag", epicFlag);
		}

		StringBuffer hostUrl = new StringBuffer();
		hostUrl.append(request.getHeader("host"));
		hostUrl.append(request.getContextPath());
		hostUrl.append("/mobile.html");

		hostUrl.append(appendDirectDialOrRoomKey("?", roomKey, model));

		if (request.isSecure()) {
			hostUrl.append("&secure=yes");
		}

		if (tenantService.isTenantNotAllowingGuests()) {
			hostUrl.append("&noguest");
		}

		Locale locale = LocaleContextHolder.getLocale();
		String country = locale.getCountry();
		String htmlLang = locale.getLanguage();
		htmlLang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
		model.put("lang", htmlLang);

		model.put("hostUrl", hostUrl.toString()); // do not delete, used by customized jsp's e.g. turkcell/yahoo
        model.put("guestJoin", true);

		String portalVerison = system.getPortalVersion();
		model.put("portalVerison", portalVerison);


		model.put("vidyoMobileJoinLink", "vidyomobile://" + hostUrl.toString());
		model.put("vidyoSlateJoinLink", "vidyoslate://" + hostUrl.toString());
		prepareLogo(model);

		String moderatorKey = theRoom.getRoomModeratorKey();
		if (moderatorKey == null) {
			this.room.generateModeratorKey(theRoom);
			moderatorKey = theRoom.getRoomModeratorKey();
		}
		StringBuilder moderatorURL = new StringBuilder();
		moderatorURL.append("//").append(request.getHeader("host"));
		moderatorURL.append("/controlmeeting.html?key=");
		moderatorURL.append(moderatorKey);
		model.put("moderateLink", moderatorURL.toString());
		String page = "guest_phone_html";
		String userAgent = request.getHeader("user-agent");
		String portalUrl = request.getScheme() + "://" + request.getHeader("host");
		if (userAgent != null) {
			model.put("isAndroid", UserAgentUtils.isAndroid(userAgent));
			model.put("zincLink", getZincUrl(portalUrl, roomKey));
			// Put the tenant mode and display the ppropriate jsp file.
			int mode = tenant.getMobileLogin();
			model.put("roomKey", roomKey);
			//vidyoMobile
			if (mode == 1){
				if (UserAgentUtils.isIphone(userAgent)) {
					model.put("vidyoMobileDownloadLink", APPLE_STORE_VIDYOMOBILE);
					page = "guest_phone_html";
				} else if (UserAgentUtils.isIPad(userAgent)) {
					model.put("vidyoMobileDownloadLink", APPLE_STORE_VIDYOMOBILE);
					model.put("vidyoSlateDownloadLink", APPLE_STORE_VIDYOSLATE);
					page = "guest_tablet_html";
				} else if (UserAgentUtils.isAndroid(userAgent) && UserAgentUtils.isMobile(userAgent)) {
					model.put("vidyoMobileDownloadLink", GOOGLE_STORE_VIDYOMOBILE);
					page = "guest_phone_html";
				} else if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) {
					model.put("vidyoMobileDownloadLink", GOOGLE_STORE_VIDYOMOBILE);
					model.put("vidyoSlateDownloadLink", GOOGLE_STORE_VIDYOSLATE);
					page = "guest_tablet_html";
				} else if (UserAgentUtils.isMobile(userAgent)) {
					if (isZincEnabled()) {
						return new ModelAndView("redirect:" + getZincUrl(portalUrl, roomKey));
					} else {
						return new ModelAndView(page, "model", model);
					}
				}
			} else if (mode == 2 ){ //vidyo connect mobile landing pages
				String mobileLink = "join?portal=" + request.getScheme() + "://" + request.getHeader("host");

				mobileLink += appendDirectDialOrRoomKey("&", roomKey, model);
				mobileLink += "&pin=" + (theRoom != null && StringUtils.hasText(theRoom.getRoomPIN()));
						
				if(epicFlag) {
					mobileLink = mobileLink.concat("&extData=")
							.concat(model.get("extData").toString())
							.concat("&extDataType=")
							.concat(validateExtDataType(model.get("extDataType")).toString())
							.concat("&dispName=")
							.concat(model.get("dispName").toString());
				}
				//model.put("hostUrl", mobileLink);
				String iOSAppLink = tenantConfiguration.getiOSAppLink() != null
						&& StringUtils.hasText(tenantConfiguration.getiOSAppLink())
								? tenantConfiguration.getiOSAppLink()
								: appstoreConfig.getAppleStoreNeomobile();
				String iOSAppId = (tenantConfiguration.getiOSAppId() != null
						&& StringUtils.hasText(tenantConfiguration.getiOSAppId()))
						? tenantConfiguration.getiOSAppId() : MobileAppParameters.IOSAPPID.getValue();
				model.put("iOSAppId", iOSAppId);
				model.put("joinLink", tenantConfiguration.getMobileProtocol() + "://" + mobileLink);
				if (UserAgentUtils.isIphone(userAgent)) {
					model.put("neoMobileDownloadLink",
							(tenantConfiguration.getiOSAppLink() != null
									&& StringUtils.hasText(tenantConfiguration.getiOSAppLink()))
											? tenantConfiguration.getiOSAppLink()
											: appstoreConfig.getAppleStoreNeomobile());
					page = "neo_mobile_landing_html";
				} else if (UserAgentUtils.isIPad(userAgent)) {
					model.put("neoMobileDownloadLink",
							(tenantConfiguration.getiOSAppLink() != null
									&& StringUtils.hasText(tenantConfiguration.getiOSAppLink()))
											? tenantConfiguration.getiOSAppLink()
											: appstoreConfig.getAppleStoreNeomobile());
					page = "neo_tablet_landing_html";
				} else if (UserAgentUtils.isAndroid(userAgent) && UserAgentUtils.isMobile(userAgent)) {
					model.put("neoMobileDownloadLink",
							(tenantConfiguration.getAndroidAppLink() != null
									&& StringUtils.hasText(tenantConfiguration.getAndroidAppLink()))
											? tenantConfiguration.getAndroidAppLink()
											: appstoreConfig.getGoogleStoreNeomobile());
					page = "neo_mobile_landing_html";
				} else if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) {
					model.put("neoMobileDownloadLink",
							(tenantConfiguration.getAndroidAppLink() != null
									&& StringUtils.hasText(tenantConfiguration.getAndroidAppLink()))
											? tenantConfiguration.getAndroidAppLink()
											: appstoreConfig.getGoogleStoreNeomobile());
					page = "neo_tablet_landing_html";
				}
				if ((UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent))) {
					model.put("joinLink", getJoinLinkForNeoOnAndroid(request, roomKey, theRoom, model));
				}
			} else if (mode == 0){
				page = "neo_mobile_disabled_html";
			}
			return new ModelAndView(page, "model", model);
		}

		// following should never get executed, but you never know!
		if (isZincEnabled()) {
			return new ModelAndView("redirect:" + getZincUrl(portalUrl, roomKey));
		} else {
			return new ModelAndView(page, "model", model);
		}
	}

	private boolean isZincEnabled() {
		TenantConfiguration config =  tenantService.getTenantConfiguration(TenantContext.getTenantId());
		if (config != null) {
			return (1 == config.getZincEnabled());
		}
		return false;
	}

	private String getZincServer() {
		if (isZincEnabled()) {
			TenantConfiguration config =  tenantService.getTenantConfiguration(TenantContext.getTenantId());
			if (config != null) {
				return config.getZincUrl();
			}
		}
		return "";
	}

	private String getZincUrl(String portalUrl, String roomKey) {
		if (isZincEnabled()) {
			return getZincServer() + "/zincadmin/conf.htm?portalUri=" + portalUrl + "&roomKey=" + roomKey;
		} else {
			return "";
		}
	}

	private String getNeoWebRTCServer() {
		Tenant t = tenantService.getTenant(TenantContext.getTenantId());
		if (t != null) {
			return t.getTenantWebRTCURL();
		}
		return "";
	}

	private String getNeoWebRTCUrl(String portalFqdn, String roomKey, Map<String,String[]> paramMap, Map<String, Object> model) {
		StringBuilder queryStr = new StringBuilder();
		//added the logic to append the query parameters while constructing the url.
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			if(!entry.getKey().equalsIgnoreCase("key")) {
				//Assumption here is that for every key there will be only one value in the array.
				queryStr.append("&"+entry.getKey() + "=" + entry.getValue()[0]);
			}
		}
		
		String directDial = (String) model.get("directDial");
		String directDialUserDisplayName = (String) model.get("ddDisplayName");
		if(!StringUtils.isEmpty(directDial)) {
			queryStr.append("&ddDisplayName=" + directDialUserDisplayName);
		} else {
			queryStr.append("&roomKey=" + roomKey);
		}
		//VPTL-7796 - appending the portal feature request parameter
		queryStr.append("&f="+portalService.getEncodedPortalFeatures());
		if(model.get("dispName") != null) {
			queryStr.append("&dispName="+model.get("dispName"));
		}
		
		return getNeoWebRTCServer() + "/webrtc/conf.htm?portal=" + portalFqdn + queryStr;
	}

	@RequestMapping(value = "/controlmeeting.html", method = RequestMethod.GET)
	public ModelAndView getControlMeetingHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isAuthenticated = false;
        Member member = null;
        String userRole = "";

        String bak = getStringParameter(request, "bak", "");
        String currentBAK = bak;
        if (!bak.equalsIgnoreCase("")) {
            try {
                member = this.user.getMemberForBak(bak, MemberBAKType.MorderatorURL);
                if(member == null) {
                	// try url decoding bak
                	currentBAK = URLDecoder.decode(bak, "UTF-8");
                	member = this.user.getMemberForBak(currentBAK, MemberBAKType.MorderatorURL);
                }
            } catch (Exception e) {
                logger.error("Exception while decoding bak"+ e.getMessage());
            }
            if (member != null) {
            	user.deleteMemberBAK(currentBAK);

                // one-time usage of BAK - change it after usage
                // Retaining the earlier BAK workflow (within Member table) so
                // as to keep the authentication workflows intact
                String newBAK = this.user.generateBAKforMember(member.getMemberID());
                String password = newBAK;

                HttpSession session = request.getSession(true);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(member.getUsername(), password);
                AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                org.springframework.security.core.Authentication authentication;
				try {
					authentication = authenticationManager.authenticate(authRequest);
	                SecurityContext securityContext = SecurityContextHolder.getContext();
	                securityContext.setAuthentication(authentication);

	                if (authentication != null) {
	                    Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
	                    for (GrantedAuthority role : roles) {
	                        userRole = role.getAuthority(); // hope user has only one role. in case of multiple - return last one
	                    }
	                }
	                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	                isAuthenticated = true;
				} catch (Exception e) {
					logger.warn("Authentication for control meeting did not go through - memberId", member.getMemberID());
					SecurityContextHolder.getContext().setAuthentication(null); // logout
				}
            }
        }

        if (member == null) {
             SecurityContextHolder.getContext().setAuthentication(null); // logout
        }

        Map<String, Object> model = new HashMap<String, Object>();
        String roomModeratorKey = getStringParameter(request, "key");

		model.put("authenticated", "false");
		model.put("roomAdmin", "false");
		//model.put("roomName", "unknown");
		model.put("roomHost",  request.getHeader("host"));
		model.put("roomID", "");
		model.put("lectureMode", false);
        model.put("waitingRoom", false);

        if (roomModeratorKey != null) {
            Room room = this.room.getRoomForModeratorKey(roomModeratorKey);
            if (room != null) {
        		String roomName = conference.generateConferenceName(room, tenantService.getTenant(TenantContext.getTenantId()).getTenantURL());
        		logger.warn("roomName ->"+ roomName);
        		logger.warn("room.getDisplayName() ->"+ room.getDisplayName());
        		model.put("roomName", roomName);
				model.put("roomDisplayName", room.getDisplayName());
                model.put("roomID", room.getRoomID());
                model.put("authenticated", (isAuthenticated ? "true" : "false"));
				model.put("lectureMode", room.getLectureMode() == 1);

                TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
                model.put("waitingRoom", tenantConfiguration.getWaitingRoomsEnabled() == 1);
				model.put("lectureModeFeature", tenantConfiguration.getLectureModeAllowed() == 1);

                if (isAuthenticated) {
                    User currentUser = this.user.getLoginUser();
                    if (currentUser != null && currentUser.getTenantID() == room.getTenantID()) {
                        model.put("roomAdmin", (("ROLE_ADMIN".equals(userRole) || "ROLE_OPERATOR".equals(userRole)) ? "true" : "false"));
                    }
                }
            }
        }

		prepareLogo(model);
        return new ModelAndView("controlmeeting_html", "model", model);
    }

	
    @RequestMapping(value = "/linkendpoint.ajax", method = RequestMethod.GET)
	public ModelAndView linkEndpointToUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession sess = request.getSession();
		String endpointGUID = getStringParameter(request, "id", "");
		String vrIP = getStringParameter(request, "vrIP", "");

		String version = getStringParameter(request, "version", "");
		sess.setAttribute("version", version);

		String clientType = getStringParameter(request, "clientType", "");
		sess.setAttribute("clientType", clientType);

		String skipUpdate = getStringParameter(request, "skipUpdate", "");

		String supportPak2 = getStringParameter(request, "pak2", "");

		boolean usePak2 = Boolean.valueOf(supportPak2);

		String activeVersion = "";

		EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(clientType);
		if (endpoint != null) {
			String filename = endpoint.getEndpointUploadFile();
			try {
				activeVersion = filename.substring(filename.indexOf("TAG"), filename.lastIndexOf("."));
			} catch (Exception e) {
				//There is a possibility of runtime exception, so handle the error and do not set activeVersion
				logger.error("Error during validating the active version of Endpoint - ", filename);
			}
		}

		Integer tenantId = TenantContext.getTenantId();
		// do not link user to endpoint if local version is not the same as active on server
		com.vidyo.bo.authentication.Authentication authType = system.getAuthenticationConfig(tenantId).toAuthentication();
        boolean isSamlAuth = authType instanceof SamlAuthentication;

		if (isSamlAuth) {
            skipUpdate = "y"; // TODO facebook hack for now, they use a custom vidyo desktop
        }
		if (version.equalsIgnoreCase(activeVersion) || skipUpdate.equalsIgnoreCase("y")) {
			if (!endpointGUID.equalsIgnoreCase("")) {
				Integer guestID = (Integer)sess.getAttribute("guestID");
				if (guestID != null) { // guest user
					this.user.linkEndpointToGuest(guestID, endpointGUID, usePak2);
				} else { // regular user
					this.user.linkEndpointToUser(endpointGUID, clientType, usePak2);
				}

				sess.setAttribute("endpointGUID", endpointGUID);
			}
		}
		else
		{
			/* On slower links VidyoRoom Client takes a long time to download installer
			and client doesn't go online and message box popup to restart.  While a download
			is in progress and somebody answers "yes" to restart it brings VidyoRoom to unbootable
			state.  This is an exception added just for VidyoRooms on Desktop teams request
			 */

			Integer guestID = (Integer)sess.getAttribute("guestID");
			if (guestID == null)
			{
				// for regular user and endpoint type as VidyoRoom V/R linkendpoint to user
				// irrespective of client version match
				if (clientType.equalsIgnoreCase("V") || clientType.equalsIgnoreCase("R") ||
						clientType.equalsIgnoreCase("N") || clientType.equalsIgnoreCase("O") || clientType.equalsIgnoreCase("P") ||
						clientType.equalsIgnoreCase("Q") || clientType.equalsIgnoreCase("Y") || clientType.equalsIgnoreCase("Z") ||
						clientType.equalsIgnoreCase("B") || clientType.equalsIgnoreCase("C") || clientType.equalsIgnoreCase("D") ||
						clientType.equalsIgnoreCase("E") || clientType.equalsIgnoreCase("F") )
				{
					  this.user.linkEndpointToUser(endpointGUID, clientType, usePak2);
					  sess.setAttribute("endpointGUID", endpointGUID);
				}
			}

		}

		if (!vrIP.equalsIgnoreCase("")) {
			this.user.updateEndpointIPaddress(endpointGUID, vrIP);
		}

		String noimage = getStringParameter(request, "noimage", "redirect");
		if (noimage.equalsIgnoreCase("redirect")) {
			response.sendRedirect("green.png");
		}

		return null;
	}

	// remove quotes for display purposes (VP-5533), e.g. "HD" -> HD
	private String cleanseRecorderProfileName(String actualRecorderName) {
		if (actualRecorderName != null &&
				actualRecorderName.length() >= 2 &&
				actualRecorderName.charAt(0) == '"' &&
				actualRecorderName.charAt(actualRecorderName.length()-1) == '"' ) {
			return actualRecorderName.substring(1,actualRecorderName.length()-1);
		}
		return actualRecorderName;
	}

	@RequestMapping(value = "/getvmconnect.ajax", method = RequestMethod.GET)
	@Deprecated
	public ModelAndView getVMconnectAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info(" [Deprecated marker] - Marker output, check whether this API is really user ");
		Map<String, Object> model = new HashMap<String, Object>();

		// Special step - remove EID and guestID from the session
		HttpSession sess = request.getSession();
		sess.removeAttribute("endpointGUID");
		sess.removeAttribute("guestID");

		User user = this.user.getLoginUser();

		String guestName = getStringParameter(request, "guestName", "");
		int guestID = 0;
		PortalAccessKeys portalAccessKeys = null;
		if (!guestName.equalsIgnoreCase("")) {
			String username = SecureRandomUtils.generateHashKey(16);
			guestID = this.user.addGuestUser(guestName, username);
			portalAccessKeys = this.user.generatePAKforGuest(guestID); // generate PAK before pass it to Endpoint

			model.put("guestID", guestID);
			sess.setAttribute("guestID", guestID);
		} else {
			this.user.setLoginUser(model, response);

			if (user != null && user.getMemberID() != 0) { // not an ANONIMOUS user
				portalAccessKeys = this.user.generatePAKforMember(user.getMemberID()); // generate PAK before pass it to Endpoint
			}
		}

		StringBuffer vmconnect = new StringBuffer();
		vmconnect.append("url=");
		vmconnect.append(request.getScheme()).append("://").append(request.getHeader("host")).append(request.getContextPath());
		vmconnect.append("/linkendpoint.ajax?vm=");

		String vm = "";
		try {
			vm = this.conference.getVMConnectAddress();
			vmconnect.append(vm);
		} catch (Exception ignored) {
		}

		// additional info for VUI
		if (vm.equalsIgnoreCase("")) {
			model.put("novm", true);
		}

		// User name and PAK
		if (guestID != 0) { // guest user
			// Guest name
			User guestUser = this.user.getUserForGuestID(guestID);
			vmconnect.append("&un=");
			vmconnect.append(guestUser.getUsername());
			// PAK for guest
			if(portalAccessKeys != null) {
				vmconnect.append("&pak=");
				vmconnect.append(portalAccessKeys.getPak());
				vmconnect.append("&pak2=");
				vmconnect.append(portalAccessKeys.getPak2());
			}
		} else { // regular user
			// User name
			vmconnect.append("&un=");
			vmconnect.append(user.getUsername());
			// PAK for regular user
			if(portalAccessKeys != null) {
				vmconnect.append("&pak=");
				vmconnect.append(portalAccessKeys.getPak());
				vmconnect.append("&pak2=");
				vmconnect.append(portalAccessKeys.getPak2());
			}
		}

		// VidyoProxy
		String proxies = "";
		Guestconf guestconf = null;
		if (guestID != 0) { // guest user
			// Common settings for All guests
			guestconf = this.system.getGuestConf();
			if (guestconf != null && guestconf.getProxyID() != 0) {
				proxies = this.service.getVP(guestconf.getProxyID()).getUrl();
			}
		} else  {
			proxies = this.service.getProxyCSVList(user.getMemberID());
		}
		if (proxies != null && !proxies.trim().equalsIgnoreCase("")) {
			vmconnect.append("&proxy=");
			vmconnect.append(proxies);
		}

		// Guest or not
		if (guestID != 0) { // guest user
			// Guest indicator in URL
			vmconnect.append("&guest=yes");
		} else {
			Integer tenantId = TenantContext.getTenantId();
			Authentication auth = this.system.getAuthenticationConfig(tenantId).toAuthentication();
			if(auth instanceof LdapAuthentication){
				vmconnect.append("&showdialpad=yes");
				vmconnect.append("&showstartmeeting=yes");
			}
			else{
				// No Guest add local menu in URL
				vmconnect.append("&showdialpad=yes");
				vmconnect.append("&showstartmeeting=yes");

			}
		}

		// Portal WS endpoint
		vmconnect.append("&portal=");
		vmconnect.append(request.getScheme()).append("://").append(request.getHeader("host")).append(request.getContextPath()).append("/services/");

		// Location Tag
		String locTag = "";
		if (guestID != 0) { // guest user
			if (guestconf != null && guestconf.getLocationID() != 0) {
				locTag = this.service.getLocation(guestconf.getLocationID()).getLocationTag();
			}
		} else  {
			locTag = this.service.getLocationTagForMember(user.getMemberID());
		}
		if (locTag != null && !locTag.equalsIgnoreCase("")) {
			vmconnect.append("&loctag=");
			vmconnect.append(locTag);
		} else {
			vmconnect.append("&loctag=Default");
		}

		// Portal version
		String portalVersion = "";
		portalVersion = this.system.getPortalVersion();
		if (portalVersion != null && !portalVersion.equalsIgnoreCase("")) {
			vmconnect.append("&portalVersion=");
			vmconnect.append(portalVersion);
		}

		model.put("vmconnect", vmconnect.toString());

		String key = getStringParameter(request, "roomLink", "");
		if (!key.equalsIgnoreCase("")) {
			Room room = this.room.getRoomForKey(key);
			if(room == null) {
				// Check if it is a scheduled room
				String[] extPin = key.split(":");
				if(extPin.length == 2){
					ScheduledRoomResponse scheduledRoomResponse = conferenceAppService.validateScheduledRoom(extPin[0], extPin[1]);
					if(scheduledRoomResponse.getStatus() == 0) {
						room = scheduledRoomResponse.getRoom();
						room.setRoomPIN(String.valueOf(scheduledRoomResponse.getPin()));
					}
				}
			}
			if (room == null) {
				model.put("entityID", "");
			} else {
				model.put("entityID", room.getRoomID());
				model.put("room", room);
			}
		}

		return new ModelAndView("ajax/flex_vmconnect_ajax", "model", model);
	}

	@RequestMapping(value = "/guestjointheconference.ajax", method = RequestMethod.POST)
	@Deprecated
	public ModelAndView guestJoinTheConference(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info(" [Deprecated marker] - Marker output, check whether this API is really user ");
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();
		int guestId = getIntParameter(request, "guestID", 0);
		int roomId = getIntParameter(request, "entityID", 0);
		String pin = getStringParameter(request, "pin", "");
		Integer tenantId = TenantContext.getTenantId();
		GuestJoinConfRequest guestJoinConfRequest = new GuestJoinConfRequest();
		guestJoinConfRequest.setGuestId(guestId);
		guestJoinConfRequest.setPin(pin);
		guestJoinConfRequest.setRoomId(roomId);
		guestJoinConfRequest.setTenantId(tenantId);
		JoinConferenceResponse joinConferenceResponse = conferenceAppService
				.joinConferenceAsGuest(guestJoinConfRequest);
		if (joinConferenceResponse.getStatus() != JoinConferenceResponse.SUCCESS) {
			FieldError fe = new FieldError(joinConferenceResponse.getMessageId(),
					joinConferenceResponse.getMessageId(),
					ms.getMessage(joinConferenceResponse.getMessage(), null, "", loc));
			errors.add(fe);
			model.put("fields", errors);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

/**
 * refer the WebRTC.java junit for more detailed understanding
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/samlindex.html", method = RequestMethod.GET)
	public ModelAndView getSamlIndexHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {

		/*
		 * Since Java5 brought a new implementation of the Map designed for
		 * multi-threaded access, you can perform efficient map reads without
		 * blocking other threads.
		 */
		final Map<String, Object> viewModel = new HashMap<String, Object>();

		final String userAgent = request.getHeader("User-Agent");
		final Locale loc = LocaleContextHolder.getLocale();
		final Integer tenantId = TenantContext.getTenantId();
		final EndpointUpload endpoint = this.endpoint.getActiveEndpointForType(request);
		boolean isUVD = false;// neo should have high priority from 3.4.5
		boolean webRTCMode = false;
		boolean isMobile = UserAgentUtils.isSupportedTouchDevice(userAgent);
		String relayStateURL = null;
		String roomKey = null;
		String directDial = null;
		String directDialUserDisplayName = null;

		Tenant tenant = tenantService.getTenant(tenantId);
		String webrtcURL = tenant.getTenantWebRTCURL();
		boolean urlExists = org.apache.commons.lang.StringUtils.isNotBlank(webrtcURL);
		// high priorirty to webrtc if roomlink is sent from client
		// Getting relayState from saml credential...if it is there, looking for
		// the mode=webrtc
		final org.springframework.security.core.Authentication authentication = (org.springframework.security.core.Authentication) SecurityContextHolder
				.getContext().getAuthentication();
		Object credentials = authentication.getCredentials();
		if (credentials instanceof SAMLCredential) {
			SAMLCredential samlCredential = (SAMLCredential) credentials;
			relayStateURL = samlCredential.getRelayState();

		}
		if (relayStateURL != null){
			SamlRelayStateParam relayStateObj = PortalUtils.JsonStringtoObject(PortalUtils.decodeBase64(relayStateURL), SamlRelayStateParam.class);
			if(relayStateObj != null) {
				roomKey = relayStateObj.getRoomKey();
				viewModel.put("roomKey", roomKey);
				directDial = relayStateObj.getDirectDial();

				populateDirectDialParams(viewModel, directDial);

				if("webrtc".equalsIgnoreCase(relayStateObj.getMode())){
					webRTCMode = true;
				}
				if (org.apache.commons.lang.StringUtils.isNotBlank(relayStateObj.getToken())) {
					TempAuthToken tempAuthToken = tempAuthTokenService.getTokenDetails(relayStateObj.getToken());
					if(tempAuthToken != null) {
						TokenCreationRequest tokenCreationRequest = new TokenCreationRequest();
						tokenCreationRequest.setTenantId(((VidyoUserDetails) authentication.getPrincipal()).getTenantID());
			            tokenCreationRequest.setMemberId(((VidyoUserDetails) authentication.getPrincipal()).getMemberId());
			            tokenCreationRequest.setUsername(((VidyoUserDetails) authentication.getPrincipal()).getUsername());
						TokenCreationResponse tokenCreationResponse = persistentTokenService.createPersistentToken(tokenCreationRequest);
						// Update the member table with the token from SAML
						tempAuthTokenService.updateTokenWithUser(relayStateObj.getToken(),
								((VidyoUserDetails) authentication.getPrincipal()).getMemberId(),
								Base64.encodeBase64String((tokenCreationResponse.getToken()).getBytes()));
					}
				}
			}
		}

		boolean webRTCForUsers = false;
		Configuration vidyoNeoWebRTUserConfiguration = system.getConfiguration("VIDYONEO_FOR_WEBRTC_FOR_USERS");
		if (vidyoNeoWebRTUserConfiguration != null
				&& org.apache.commons.lang.StringUtils
						.isNotBlank(vidyoNeoWebRTUserConfiguration.getConfigurationValue())
				&& vidyoNeoWebRTUserConfiguration.getConfigurationValue().equalsIgnoreCase("1")) {
			TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(tenantId);
			webRTCForUsers = tenantConfiguration.getVidyoNeoWebRTCUserEnabled() == 1;
		}
		//incase user directly accessing the saml login url with mode=webrtc, and if they are not allowed to use webrtc by admin
		if (webRTCMode && webRTCForUsers) {
			viewModel.put("webRTCUrl", urlExists?(webrtcURL+"/webrtc/conf.htm?portal="+request.getScheme()+"://" + request.getHeader("host")):"");
			viewModel.put("key", new String(Base64.encodeBase64(getKeyNoBase64(request).getBytes())));
			viewModel.put("privacyUrl", TCAndPrivacyPolicyUtils.getPrivacyPolicyUrl(system, tenantService));
			viewModel.put("termsUrl", TCAndPrivacyPolicyUtils.getTermsAndConditionsUrl(system, tenantService));
			return new ModelAndView("saml_webrtc_index_html", "model", viewModel);
		}

		// normal scenarios where it can be endpoint, neo or even webrtc
		String installer = null;

		if (endpoint == null) {
			viewModel.put("installer", "no_installer");

		} else {
			String installerName = endpoint.getEndpointUploadFile();
			if (installerName != null && installerName.contains("TAG_VD_3")) {
				isUVD = true;
			}
			// Construct the download URL with the portal address pre-populated for NEO
			if(this.endpoint.isUploadModeExternal(TenantContext.getTenantId())) {
				// Override the installer url with the CDN
				installer = this.endpoint.getCDNInstallerURLWithParam(endpoint.getEndpointUploadFile(), request.getScheme(), request.getHeader("host"), roomKey);
			} else {
				UriBuilder uriBuilder = UriBuilder.fromPath("")
						.path(upload_path).path(endpoint.getEndpointUploadFile());
				if(!StringUtils.isEmpty(roomKey)) {
					uriBuilder.queryParam("r", roomKey);
				}
				if(!StringUtils.isEmpty(directDial)) {
					uriBuilder.queryParam("directDial", directDial);
					uriBuilder.queryParam("ddDisplayName", directDialUserDisplayName);
				}
				installer = uriBuilder.build().toString();
			}
		}

		if(isUVD && installer.contains("?")) {
			//Remove the response-content-disposition to avoid the uvd download page breaking.
			installer = installer.substring(0, installer.indexOf("?"));
		}

		viewModel.put("installer", installer);
		boolean isNeo = VendorUtils.isForceNeoFlow() || !isUVD;
		// existing logic
		//refer the WebRTC.java junit for more detailed understanding
		if ( !isMobile && webRTCForUsers && urlExists
				&& ((UserAgentUtils.browserSupportsWebRTC(userAgent)
						&& ((UserAgentUtils.isMac(userAgent) || UserAgentUtils.isWindows(userAgent)) && !isNeo))
						|| (UserAgentUtils.browserSupportsWebRTC(userAgent)
								&& (!(UserAgentUtils.isMac(userAgent) || UserAgentUtils.isWindows(userAgent)))))) {
			webRTCMode=true;
		}

		prepareLogo(viewModel);
		// model.put("installer", filename);
		StringBuilder installerAHref = new StringBuilder();
		installerAHref.append("<a href=\"");
		installerAHref.append(installer);
		installerAHref.append("\">");
		installerAHref.append(ms.getMessage("here", null, "", loc));
		installerAHref.append("</a>");
		String step1Txt = MessageFormat.format(ms.getMessage("vd.is.not.running.verbiage1", null, "", loc),
				installerAHref.toString());
		viewModel.put("step1Txt", step1Txt);
		String indexUrl = response.encodeURL("/index.html");
		StringBuilder indexAHref = new StringBuilder();
		indexAHref.append("<a href=\"");
		indexAHref.append(indexUrl);
		indexAHref.append("\">");
		indexAHref.append(ms.getMessage("here", null, "", loc));
		indexAHref.append("</a>");
		viewModel.put("step2Txt", ms.getMessage("vd.is.not.running.verbiage2", null, "", loc));
		String step3Txt = MessageFormat.format(ms.getMessage("vd.is.not.running.verbiage3", null, "", loc),
				indexAHref.toString());
		viewModel.put("step3Txt", step3Txt);
		String infoMsg = ms.getMessage("login.complete", null, "", loc);
		viewModel.put("infoMsg", infoMsg);
		viewModel.put("isInfoCheck", true);
		viewModel.put("title", infoMsg);

		// model.put("errorMessage", "");
		request.setAttribute("roomKey", roomKey);
		request.setAttribute("directDial", directDial);
		request.setAttribute("ddDisplayName", directDialUserDisplayName);
		
		if (!isMobile && isNeo) {
			viewModel.put("url", getSamlVMconnectLinkForNeo(request, viewModel));
			// Check if the guest login is disabled and webrtc is enabled and configured and roomkey is available
			if(tenant.getGuestLogin() == 0 && webRTCForUsers && urlExists && StringUtils.hasText(roomKey)) {
				// If guest is disabled and webrtc is available for users, set the portal url with mode as webrtc and append room key
				UriBuilder uriBuilder = UriBuilder.fromPath("")
						.path("saml/login/").queryParam("mode", "webrtc")
						.queryParam("roomKey", roomKey)
						.queryParam("directDial", directDial)
						.queryParam("ddDisplayName", directDialUserDisplayName);
				String webRTCRedirectURL = uriBuilder.build().toString();
				viewModel.put("neoWebRTCUrl", webRTCRedirectURL);
			}

			viewModel.put("privacyUrl", TCAndPrivacyPolicyUtils.getPrivacyPolicyUrl(system, tenantService));
			viewModel.put("termsUrl", TCAndPrivacyPolicyUtils.getTermsAndConditionsUrl(system, tenantService));
			return new ModelAndView("saml_neo_index_html", "model", viewModel);
		}
		if (!isMobile && webRTCMode) {
			viewModel.put("webRTCUrl", webrtcURL+"/webrtc/conf.htm?portal=" + request.getHeader("host"));
			viewModel.put("roomKey", "");//no room link in this case
			viewModel.put("key", new String(Base64.encodeBase64(getKeyNoBase64(request).getBytes())));
			viewModel.put("privacyUrl", TCAndPrivacyPolicyUtils.getPrivacyPolicyUrl(system, tenantService));
			viewModel.put("termsUrl", TCAndPrivacyPolicyUtils.getTermsAndConditionsUrl(system, tenantService));
			return new ModelAndView("saml_webrtc_index_html", "model", viewModel);
		}
		// set vmconnect
		viewModel.put("url", getSamlVMconnectLink(request));
		if (isMobile) {
			// https://jira.vidyo.com/browse/VPTL-7607 - Implement Saml meo mobile html screen for ios and Android devices
			//check if the tenant is a vidyo Mobile, neo Mobile
			int mode = tenant.getMobileLogin();
			//if its 0, its disabled, if 1 its VidyoMobile and if its 2 then its neoMobile
			if (mode == 1){
				return new ModelAndView("saml_mobile_index_html", "model", viewModel);
			} else if (mode == 2){ // neoMobile mode

				if (UserAgentUtils.isIOS(userAgent)) {
					viewModel.put("neoMobileDownloadLink", appstoreConfig.getAppleStoreNeomobile());
				} else if (UserAgentUtils.isAndroid(userAgent)) {
					viewModel.put("neoMobileDownloadLink", appstoreConfig.getGoogleStoreNeomobile());
				}

				// If the UserAgent is Android, then use Android Intents
				if (UserAgentUtils.isAndroid(userAgent) || UserAgentUtils.isKindle(userAgent)) {
					viewModel.put("joinLink", getSamlVMconnectLinkForNeoMobile(request, viewModel));
					return new ModelAndView("saml_neo_default_index_html", "model", viewModel);
				} else {
					viewModel.put("joinLink", getSamlVMconnectLinkForNeo(request, viewModel));
					return new ModelAndView("saml_neo_default_index_html", "model", viewModel);
				}
			} else if (mode == 0){
				return new ModelAndView("neo_mobile_disabled_html", "model", viewModel);
			}
		}

		//this one for the uvd
		return new ModelAndView("saml_index_html", "model", viewModel);
	}

	private Room populateDirectDialParams(Map<String, Object> viewModel, String directDial) {
		Room room = null;
		if (!StringUtils.isEmpty(directDial)) {
			try {
				int roomId = Integer.parseInt(directDial);
				room = this.room.getRoom(roomId);
				if(room != null) {
					String directDialUserDisplayName = this.member.getMember(room.getMemberID()).getMemberName();
					viewModel.put("directDial", directDial);
					viewModel.put("ddDisplayName", directDialUserDisplayName);
				} else {
					logInvalidRoomAccess(directDial);
				}
			} catch (NumberFormatException e) {
				logInvalidRoomAccess(directDial);
			}
		}
		return room;
	}

	private void logInvalidRoomAccess(String directDial) {
		logFailRoomAccessAttenot(format(LOG_INVALID_ROOM_ID_ATTEMPT, directDial));
		logger.error("Room with id - " + directDial + " is not found.");
	}

	private String getSamlVMconnectLinkForNeoMobile(HttpServletRequest request, Map<String, Object> model) {

		final StringBuilder protocolHndlrUrl = new StringBuilder(34);

		String portalUrl = request.getScheme().concat("://").concat(request.getHeader("host"))
				.concat(request.getContextPath());
		String sKey = new String(Base64.encodeBase64(getKeyNoBase64(request).getBytes()));
		protocolHndlrUrl.append("intent://loginSaml/?portal=").append(portalUrl)
				.append("&key=").append(sKey)
                .append(appendDirectDialOrReturnEmptyString(model))
				.append("#Intent;scheme=vidyo;package=com.vidyo.neomobile;action=loginSaml;S.portal=")
				.append(portalUrl).append(";S.key=").append(sKey).append(";end");

		return protocolHndlrUrl.toString();

	}

    private String getSamlVMconnectLinkForNeo(HttpServletRequest request, Map<String, Object> model) {

		final StringBuilder protocolHndlrUrl = new StringBuilder(34);
		protocolHndlrUrl.append(tenantService.getTenantConfiguration(TenantContext.getTenantId()).getDesktopProtocol());
		protocolHndlrUrl.append("://loginSaml/?portal=").append(request.getScheme()).append("://").append(request.getHeader("host"))
				.append(request.getContextPath()).append("&key=")
				.append(new String(Base64.encodeBase64(getKeyNoBase64(request).getBytes())))
                .append(appendDirectDialOrReturnEmptyString(model));
		return protocolHndlrUrl.toString();

	}

	private String appendDirectDialOrRoomKey(String firstChar, String roomKey, Map<String, Object> model) {
		String result;
		String directDialPart = appendDirectDialIfPresentNoLeadingJoinParam(model);
		if(!StringUtils.isEmpty(directDialPart)) {
			result = directDialPart;
		} else {
			result = "roomKey=" + roomKey;
		}
		return firstChar + result;
	}

	private String appendDirectDialIfPresentNoLeadingJoinParam(Map<String, Object> model) {
		String result = "";
		if(null != model.get("directDial")) {
			String directDialValue = (String) model.get("directDial");
			String directDialDisplayName = (String) model.get("ddDisplayName");
			result = "directDial=" + directDialValue + "&ddDisplayName=" + directDialDisplayName;
		}
		logger.debug("following directDial data will be added to protocolHandler" + result);
		return result;
	}

    private String appendDirectDialOrReturnEmptyString(Map<String, Object> model) {
        String directDialResult = appendDirectDialIfPresentNoLeadingJoinParam(model);
        if (!StringUtils.isEmpty(directDialResult)) {
            return "&" + directDialResult;
        } else {
            return "";
        }
    }

	private String getJoinLinkForNeoOnAndroid(HttpServletRequest request, String roomKey, Room theRoom,
			 Map<String, Object> model) {
		final StringBuilder protocolHndlrUrl = new StringBuilder(34);
		String portalUrl = request.getScheme().concat("://").concat(request.getHeader("host"))
				.concat(request.getContextPath());
		TenantConfiguration tenantConfiguration = tenantService.getTenantConfiguration(TenantContext.getTenantId());
		protocolHndlrUrl.append("intent://join/?portal=").append(portalUrl);
		protocolHndlrUrl.append(appendDirectDialOrRoomKey("&", roomKey, model));
		protocolHndlrUrl.append("&pin=")
                .append(theRoom != null && StringUtils.hasText(theRoom.getRoomPIN()))
				.append("#Intent;scheme=");
		protocolHndlrUrl.append(tenantConfiguration.getMobileProtocol());
		protocolHndlrUrl.append(";");
		protocolHndlrUrl.append("package=");
		protocolHndlrUrl.append(tenantConfiguration.getAndroidPackageName());
		protocolHndlrUrl.append(";action=join;S.portal=")
				.append(portalUrl).append(";S.roomKey=").append(roomKey).append(";S.pin=").append(theRoom != null && StringUtils.hasText(theRoom.getRoomPIN()));
		// If Epic support is enabled and the data is valid
		if(model.get("epicFlag") != null && Boolean.valueOf(model.get("epicFlag").toString())) {
			protocolHndlrUrl.append(";S.extData=")
					.append(model.get("extData"))
					.append(";S.extDataType=")
					.append(validateExtDataType(model.get("extDataType")).toString())
					.append(";S.dispName=")
					.append(model.get("dispName"));
		}
		protocolHndlrUrl.append(";end");
		return protocolHndlrUrl.toString();
	}

	private String getKeyNoBase64(HttpServletRequest request) {
		final User user = this.user.getLoginUser();
		if (user == null || user.getMemberID() == 0) {
			throw new WebException("User is null or member id is 0");
		}
		PortalAccessKeys portalAccessKeys = null;
		portalAccessKeys = this.user.generatePAKforMember(user.getMemberID());
		final EndpointSettings endpointSettings = system.getAdminEndpointSettings(TenantContext.getTenantId());
		final ProtocolHandlerParam protocolHndlrKey = new ProtocolHandlerParam();
		protocolHndlrKey.setVmAddr(getVm());
		protocolHndlrKey.setTlsProxy(getTlsProxy());
		protocolHndlrKey.setUsername(user.getUsername());
		protocolHndlrKey.setAccessKey(portalAccessKeys.getPak2());
		protocolHndlrKey.setProxyAddr(getProxyAddress(user.getMemberID()));
		protocolHndlrKey.setPortalURL(getPortalUrl(request));
		protocolHndlrKey.setLoctag(getLocTag(user.getMemberID()));
		protocolHndlrKey.setPortalVersion(getPortalVersion());
		protocolHndlrKey.setMinPinLen(system.getMinPINLengthForTenant(TenantContext.getTenantId()));
		protocolHndlrKey.setMaxPinLen(SystemServiceImpl.PIN_MAX);
		protocolHndlrKey.setVrProxyConfig(endpointSettings.isAlwaysUseVidyoProxy() ? "ALWAYS" : "AUTO");
		protocolHndlrKey.setMinMediaPort(endpointSettings.getMinMediaPort());
		protocolHndlrKey.setMaxMediaPort(endpointSettings.getMaxMediaPort());
		protocolHndlrKey.setEndpointExternalIPAddress(request.getRemoteAddr());
		protocolHndlrKey.setRoomKey((String)request.getAttribute("roomKey"));
		final ObjectMapper mapper = new ObjectMapper();
		String keyNoBase64 = null;
		try {
			keyNoBase64 = mapper.writeValueAsString(protocolHndlrKey);
		} catch (JsonProcessingException e) {
			throw new WebException("Unable to parse to json " + protocolHndlrKey.toString(), e);
		}
		return keyNoBase64;
	}


	private String getPortalVersion() {
		return this.system.getPortalVersion();
	}

	// Location Tag
	private String getLocTag(int memberId) {
		String locTag = this.service.getLocationTagForMember(memberId);
		if (locTag == null || locTag.equalsIgnoreCase("")) {
			locTag = "Default";
		}
		return locTag;
	}

	private String getPortalUrl(HttpServletRequest request) {

		return request.getScheme() + "://" + request.getHeader("host") + request.getContextPath();
	}

	private String getProxyAddress(int memberId) {
		return this.service.getProxyCSVList(memberId);

	}

	private String getTlsProxy() {

		return this.system.getTLSProxyConfiguration().toString();

	}

	private String getVm() {
		String vm = "";
		try {
			vm = this.conference.getVMConnectAddress();

		} catch (Exception e) {
			logger.error("Not able to get VM", e);
		}
		return vm;
	}

	@RequestMapping(value = "/samlloginfail.html", method = RequestMethod.GET)
	public ModelAndView getSamlLoginFailedHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();
		String logo = StringUtils.trimWhitespace(system.getCustomizedImageLogoName());
		// if no tenant one set, use super one, if if no super one, jsp will use default vidyo logo
		if (StringUtils.isEmpty(logo)) {
			logo = StringUtils.trimWhitespace(system.getCustomizedSuperPortalImageLogoName());
		}
		if(logo == null) {
			logo = "";
		}
		model.put("logoUrl", logo);

		AuthenticationException exception = (AuthenticationException)request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(exception == null) {
			exception = (AuthenticationException)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		if(exception != null && exception.getMessage()!=null) {
			logger.error("Login exception ",exception);
		}
		model.put("errorMessage", ms.getMessage("unable.to.login", null, "", loc));
		return new ModelAndView("saml_login_fail_html", "model", model);
	}

    /**
     * Returns the URL that when followed by the browser connects the vidyodesktop client to the portal (basically
     * logs the user into vidyodesktop).
     * @param request
     * @return
     * @throws Exception
     */
    private String getSamlVMconnectLink(HttpServletRequest request) throws Exception {
        // Special step - remove EID and guestID from the session
        HttpSession sess = request.getSession();
        sess.removeAttribute("endpointGUID");
        sess.removeAttribute("guestID");

        User user = this.user.getLoginUser();

        String guestName = getStringParameter(request, "guestName", "");
        int guestID = 0;
        PortalAccessKeys portalAccessKeys = null;
        if (!guestName.equalsIgnoreCase("")) {
            String username = SecureRandomUtils.generateHashKey(16);
            guestID = this.user.addGuestUser(guestName, username);
            portalAccessKeys = this.user.generatePAKforGuest(guestID); // generate PAK before pass it to Endpoint

            //          model.put("guestID", guestID);
            sess.setAttribute("guestID", guestID);
        } else {
            //        this.user.setLoginUser(model, response);

            if (user != null && user.getMemberID() != 0) { // not an ANONIMOUS user
            	portalAccessKeys = this.user.generatePAKforMember(user.getMemberID()); // generate PAK before pass it to Endpoint
            }
        }

        StringBuffer vmconnect = new StringBuffer();
        vmconnect.append("url=");
        vmconnect.append(request.getScheme()).append("://").append(request.getHeader("host")).append(request.getContextPath());
        vmconnect.append("/linkendpoint.ajax?vm=");

        String vm = "";
        try {
            vm = this.conference.getVMConnectAddress();
            vmconnect.append(vm);
        } catch (Exception ignored) {
        }

        // additional info for VUI
//        if (vm.equalsIgnoreCase("")) {
            //      model.put("novm", true);
//        }

        // User name and PAK
        if (guestID != 0) { // guest user
            // Guest name
            User guestUser = this.user.getUserForGuestID(guestID);
            vmconnect.append("&un=");
            vmconnect.append(guestUser.getUsername());
            // PAK for guest
            if(portalAccessKeys != null) {
                vmconnect.append("&pak=");
                vmconnect.append(portalAccessKeys.getPak());
                vmconnect.append("&pak2=");
                vmconnect.append(portalAccessKeys.getPak2());
            }
        } else { // regular user
            // User name
            vmconnect.append("&un=");
            vmconnect.append(user.getUsername());
            // PAK for regular user
            if(portalAccessKeys != null) {
                vmconnect.append("&pak=");
                vmconnect.append(portalAccessKeys.getPak());
                vmconnect.append("&pak2=");
                vmconnect.append(portalAccessKeys.getPak2());
            }
        }

        // VidyoProxy
        String proxies = "";
        Guestconf guestconf = null;
        if (guestID != 0) { // guest user
            // Common settings for All guests
            guestconf = this.system.getGuestConf();
            if (guestconf != null && guestconf.getProxyID() != 0) {
                proxies = this.service.getVP(guestconf.getProxyID()).getUrl();
            }
        } else  {
            proxies = this.service.getProxyCSVList(user.getMemberID());
        }
        if (proxies != null && !proxies.trim().equalsIgnoreCase("")) {
            vmconnect.append("&proxy=");
            vmconnect.append(proxies);
        }

        // Guest or not
        if (guestID != 0) { // guest user
            // Guest indicator in URL
            vmconnect.append("&guest=yes");
        } else {
        	Integer tenantId = TenantContext.getTenantId();
            Authentication authService = this.system.getAuthenticationConfig(tenantId).toAuthentication();
            if(authService instanceof LdapAuthentication){
                vmconnect.append("&showdialpad=yes");
                vmconnect.append("&showstartmeeting=yes");
            }
            else{
                // No Guest add local menu in URL
                vmconnect.append("&showdialpad=yes");
                vmconnect.append("&showstartmeeting=yes");

            }
        }

        // Portal WS endpoint
        vmconnect.append("&portal=");
        vmconnect.append(request.getScheme()).append("://").append(request.getHeader("host")).append(request.getContextPath()).append("/services/");

        // Location Tag
        String locTag = "";
        if (guestID != 0) { // guest user
            if (guestconf != null && guestconf.getLocationID() != 0) {
                locTag = this.service.getLocation(guestconf.getLocationID()).getLocationTag();
            }
        } else  {
            locTag = this.service.getLocationTagForMember(user.getMemberID());
        }
        if (locTag != null && !locTag.equalsIgnoreCase("")) {
            vmconnect.append("&loctag=");
            vmconnect.append(locTag);
        } else {
            vmconnect.append("&loctag=Default");
        }

        // Portal version
        String portalVersion = "";
        portalVersion = this.system.getPortalVersion();
        if (portalVersion != null && !portalVersion.equalsIgnoreCase("")) {
            vmconnect.append("&portalVersion=");
            vmconnect.append(portalVersion);
        }


        return vmconnect.toString();
    }

	/**
	 * @deprecated  - There is a similar functionality in Abstract Login Controller(for Admin/Super apps),
	 * This is a code duplicate, so it should be refactored
	 */
    @RequestMapping(value = "/changepassword.html", method = RequestMethod.GET)
    @Deprecated
    public ModelAndView getChangePasswordHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isAuthenticated = false;
        Member member = null;
        String bak = getStringParameter(request, "bak", "");
        String currentBAK = bak;
        if (!bak.equalsIgnoreCase("")) {
            try {
                member = this.user.getMemberForBak(bak, MemberBAKType.ChangePassword);
                if(member == null) {
                	currentBAK = URLDecoder.decode(bak, "UTF-8");
                	member = this.user.getMemberForBak(currentBAK, MemberBAKType.ChangePassword);
                }
            } catch (Exception e) {
                logger.warn("Exception while getting member by bak", e.getMessage());
            }
            if (member != null) {
            	user.deleteMemberBAK(currentBAK);

                // one-time usage of BAK - change it after usage
                // Retaining the earlier BAK workflow (within Member table) so
                // as to keep the authentication workflows intact
                String newBAK = this.user.generateBAKforMember(member.getMemberID());
                String password = newBAK;

                HttpSession session = request.getSession(true);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(member.getUsername(), password);
                AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(authRequest);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

                isAuthenticated = true;
            }
        }

        if (member == null) {
             SecurityContextHolder.getContext().setAuthentication(null); // logout
        }

        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", -1);

        Map<String, Object> model = new HashMap<String, Object>();

		model.put("authenticated", (isAuthenticated ? "true" : "false"));
		prepareLogo(model);

        return new ModelAndView("changepassword_html", "model", model);
    }

	/**
	 * @deprecated  - There is a similar functionality in Abstract Login Controller,
	 * This is a code duplicate, so it should be refactored
	 */
    @RequestMapping(value = "/ui/changepassword.ajax", method = RequestMethod.POST)
	@Deprecated
    public ModelAndView changePasswordAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String currentPassword = getStringParameter(request, "currentpassword", null);
        String newPassword = getStringParameter(request, "newpassword", null);
        String reenterNewPassword = getStringParameter(request, "reenternewpassword", null);

        Locale loc = LocaleContextHolder.getLocale();

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        if(currentPassword == null || currentPassword.trim().isEmpty()) {
            FieldError fe = new FieldError("changePassword", "changePassword",
                    ms.getMessage("please.provide.current.password", null, "", loc));
            errors.add(fe);
        }

        if(newPassword == null || newPassword.trim().isEmpty()) {
            FieldError fe = new FieldError("changePassword", "changePassword",
                    ms.getMessage("please.provide.new.password", null, "", loc));
            errors.add(fe);
        }

        if(reenterNewPassword == null || reenterNewPassword.trim().isEmpty()) {
            FieldError fe = new FieldError("changePassword", "changePassword",
                    ms.getMessage("please.reenter.new.password", null, "", loc));
            errors.add(fe);
        }

        if(!newPassword.equals(reenterNewPassword)) {
            FieldError fe = new FieldError("changePassword", "changePassword",
                    ms.getMessage("new.password.is.not.equal.to.reentered.password", null, "", loc));
            errors.add(fe);
        }

        if(newPassword.equals(currentPassword)) {
            FieldError fe = new FieldError("changePassword", "changePassword",
                    ms.getMessage("password.cannot.be.same", null, "", loc));
            errors.add(fe);
        }

        if(errors.size() == 0) {
            User user = this.user.getLoginUser();

            String pwd = this.member.getMemberPassword(user.getMemberID());
            if(SHA1.enc(currentPassword).equals(pwd) || (pwd.contains(":") && PasswordHash.validatePassword(currentPassword, pwd))) {
                Member memb = this.member.getMember(user.getMemberID());
                memb.setPassword(newPassword.trim());
                int resp = this.member.updateMemberPassword(memb);

                FieldError fe = null;
                if(resp == MemberManagementResponse.PASSWORD_DOES_NOT_MEET_REQUIREMENTS){
                    String errMsg = ms.getMessage("password.change.did.not.meet.requirements", null, "", loc);
                    errMsg = errMsg.replaceAll("<br/>", "\n");
                    fe = new FieldError("changePassword", "changePassword", errMsg);
                } else if (resp == MemberManagementResponse.PASSWORD_OF_IMPORTED_USER_CAN_NOT_BE_UPDATED){
                    fe = new FieldError("changePassword", "changePassword", ms.getMessage("the.password.of.imported.user.cannot.be.updated", null, "", loc));
                } else if(resp < 0) {
                    fe = new FieldError("changePassword", "changePassword", ms.getMessage("failed.to.update.password", null, "", loc));
                }
                if(fe != null) {
                    errors.add(fe);
                }
            } else {
                FieldError fe = new FieldError("changePassword", "changePassword",
                        ms.getMessage("incorrect.current.password", null, "", loc));
                errors.add(fe);
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            };
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/closelogouttab.html", method = RequestMethod.GET)
    public ModelAndView getLogoutCloseTabHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();

    	String infoMsg = ms.getMessage("logout.complete", null, "", loc);
        model.put("infoMsg", infoMsg);
		model.put("isInfoCheck", true);
		model.put("title", infoMsg);

		String logo = org.apache.commons.lang.StringUtils.trimToEmpty(this.system.getCustomizedImageLogoName());
		// if no tenant one set, use super one, if if no super one, jsp will use default vidyo logo
		if (org.apache.commons.lang.StringUtils.isEmpty(logo)) {
			logo = org.apache.commons.lang.StringUtils.trimToEmpty(this.system.getCustomizedSuperPortalImageLogoName());
		}
		model.put("logoUrl", logo);

        return new ModelAndView("closelogouttab_html", "model", model);
    }

	/**
	 * @deprecated  - There is a similar functionality in Abstract Login Controller,
	 * This is a code duplicate, so it should be refactored
	 */
	@RequestMapping(value = "/forgotpassword.html", method = RequestMethod.GET)
	@Deprecated
	public ModelAndView getForgotPasswordHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();

		Boolean isShownForgotPassword = showForgotPassword();

		String logo = PortalUtils.prepareLogo(this.system);
		model.put("logoUrl", logo);

		if(isShownForgotPassword.equals(Boolean.FALSE)) {
			String infoMsg = ms.getMessage("the.forgot.password.page.cannot.be.shown.due.to.system.configuration.or.tenant.authentication.type", null, "", loc);
			model.put("infoMsg", infoMsg);
			model.put("isInfoCheck", false);
			model.put("title", infoMsg);

			return new ModelAndView("closetab_html", "model", model);
		}


		return new ModelAndView("forgotpassword_html", "model", model);
	}

	/**
	 * @deprecated  - There is a similar functionality in Abstract Login Controller,
	 * This is a code duplicate, so it should be refactored
	 */
	@RequestMapping(value = "/ui/forgotpassword.ajax", method = RequestMethod.POST)
	@Deprecated
	public ModelAndView forgotPasswordAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int rc = 0;
		String email = getStringParameter(request, "email", "");

		Locale loc = LocaleContextHolder.getLocale();

		if (!email.equalsIgnoreCase("")) {
			rc = this.login.forgotFlexPassword(email, request);
		}

		Map<String, Object> model = new HashMap<String, Object>();

		if (rc > 0) {
			model.put("success", Boolean.TRUE);
		} else {
			model.put("success", Boolean.FALSE);
			if(rc == -100) {
				//Add error message - not configured
				FieldError fe = new FieldError("Configuration", "Configuration", ms.getMessage("email.notifications.not.configured", null, "", loc));
				List<FieldError> errors = new ArrayList<FieldError>();
				errors.add(fe);
				model.put("fields", errors);
			}
			if(rc == -101){
				FieldError fe = new FieldError("Configuration", "Configuration",  ms.getMessage("password.reset.invalid.email", null, "", loc));
				List<FieldError> errors = new ArrayList<FieldError>();
				errors.add(fe);
				model.put("fields", errors);
			}
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/closetab.html", method = RequestMethod.GET)
    public ModelAndView getCloseTabHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();

    	String infoMsg = ms.getMessage("login.complete", null, "", loc);
        model.put("infoMsg", infoMsg);
		model.put("isInfoCheck", true);
		model.put("title", infoMsg);

		String logo = PortalUtils.prepareLogo(this.system);
		model.put("logoUrl", logo);

        return new ModelAndView("closetab_html", "model", model);
    }
    
	@RequestMapping(value = "/closechangepassword.html", method = RequestMethod.GET)
	public ModelAndView getCloseChangePasswordHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();
		Boolean isUpdated = ServletRequestUtils.getBooleanParameter(request, "isUpdated", false);
		String updateMsg = ms.getMessage("user.authentication.failed", null, "", loc);

		if(isUpdated) {
			updateMsg = ms.getMessage("password.is.updated", null, "", loc);
		}

		model.put("infoMsg", updateMsg);
		model.put("isInfoCheck", isUpdated);
		model.put("title", updateMsg);

		String logo = PortalUtils.prepareLogo(this.system);
		model.put("logoUrl", logo);
		return new ModelAndView("closetab_html", "model", model);
	}

	private Boolean showForgotPassword() {
		String showforgotpassword = "";
		Configuration config = system.getConfiguration("SHOW_FORGOT_PASSWORD_LINK");
		Boolean retVal = Boolean.TRUE;
		if(null != config){
			showforgotpassword = config.getConfigurationValue();
		}
		if (showforgotpassword == null || showforgotpassword.equalsIgnoreCase("NEVER")) {
			retVal = Boolean.FALSE;
		} else {
			Integer tenantId = TenantContext.getTenantId();
			Authentication auth = this.system.getAuthenticationConfig(tenantId).toAuthentication();
			if (auth != null && (auth instanceof LdapAuthentication || auth instanceof SamlAuthentication)) { // Authentication using LDAP or SAML provided by third party
				retVal = Boolean.FALSE;
			}
		}

		return retVal;
	}

	@RequestMapping(value = "/closeforgotpassword.html", method = RequestMethod.GET)
	public ModelAndView getCloseForgotPasswordTabHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Locale loc = LocaleContextHolder.getLocale();

		String infoMsg = ms.getMessage("please.check.your.e.mail.account.for.instructions.on.completing.the.password.reset.process", null, "", loc);
		model.put("infoMsg", infoMsg);
		model.put("isInfoCheck", true);
		model.put("title", infoMsg);

		String logo = org.apache.commons.lang.StringUtils.trimToEmpty(this.system.getCustomizedImageLogoName());
		// if no tenant one set, use super one, if if no super one, jsp will use default vidyo logo
		if (org.apache.commons.lang.StringUtils.isEmpty(logo)) {
			logo = org.apache.commons.lang.StringUtils.trimToEmpty(this.system.getCustomizedSuperPortalImageLogoName());
		}
		model.put("logoUrl", logo);

		return new ModelAndView("closetab_html", "model", model);
	}
}
