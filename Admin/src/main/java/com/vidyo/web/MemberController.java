package com.vidyo.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.WebUtils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Group;
import com.vidyo.bo.Location;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.room.RoomTypes;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.MemberServiceImpl;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.utils.CompressionUtils;
import com.vidyo.utils.LangUtils;
import com.vidyo.utils.PortalUtils;
import com.vidyo.utils.SecurityUtils;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.VendorUtils;
import com.vidyo.utils.room.RoomUtils;

@Controller
public class MemberController {

	protected static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private final static String MEMBER_ROLE_EXECUTIVE = "Executive";
	private final static String MEMBER_ROLE_VIDYO_PANORAMA = "VidyoPanorama";
	private final static String MEMBER_ROLE_VIDYO_ROOM = "VidyoRoom";
	private final static String[] MEMBER_EXPORT_CSV_HEADER_HASHED_PASSWORDS = { "UserType", "Username",
			"Hashed Password", "Fullname", "Email", "Extension", "Group", "Language", "Description", "Proxy",
			"LocationTag", "PhoneNumber1", "PhoneNumber2", "PhoneNumber3", "Department", "Title", "IM", "Location" };
	private final static String[] MEMBER_EXPORT_CSV_HEADER_TEXT_PASSWORDS = { "UserType", "Username", "Password",
			"Fullname", "Email", "Extension", "Group", "Language", "Description", "Proxy", "LocationTag",
			"PhoneNumber1", "PhoneNumber2", "PhoneNumber3", "Department", "Title", "IM", "Location" };

	@Autowired
	private IMemberService service;

	@Autowired
	private IUserService user;

	@Autowired
	private IRoomService room;

	@Autowired
	private ISystemService system;

	@Autowired
	private IGroupService group;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LicensingService license;

	@Autowired
	private ITenantService tenantService;

	@Autowired
	private CookieLocaleResolver lr;

	@Autowired
	private ReloadableResourceBundleMessageSource ms;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Value("${upload.temp.dir}")
	private String uploadTempDir;


	@RequestMapping(value = "/members.html", method = GET)
	public ModelAndView getMembersHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		model.put("nav", "members");

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		return new ModelAndView("admin/members_html", "model", model);
	}

	@RequestMapping(value = "/members.ajax", method = GET)
	public ModelAndView getMembersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

		MemberFilter filter = new MemberFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			filter = null;
		}

		List<Member> list = this.service.searchMembers(TenantContext.getTenantId(), filter);
		int num = this.service.countMembers(TenantContext.getTenantId(), filter);

		/*if (roomID != 0) {
			int roomOwnerID = this.room.getRoom(roomID).getMemberID();
			Member roomOwner = this.service.getMember(roomOwnerID);
			if (roomOwner != null && !list.contains(roomOwner)) {
				list.add(roomOwner);
			}
		}

		for (Member member : list) {
			member.setMemberName(member.getMemberName());
			member.setGroupName(member.getGroupName());
		}*/
		
		logger.warn("Total num ->" + num);
		
		User user = this.user.getLoginUser();
		int excludedCount = 0;
		if ((user != null) && ("ROLE_OPERATOR".equals(user.getUserRole()))) {
			List<Member> members = new ArrayList<Member>();
			for (Member member : list) {
				// for Operator user skip ADMIN users
				if (!"Admin".equals(member.getRoleName())) {
					members.add(member);
				} else {
					excludedCount++;
				}
			}
			list = members;
			//num = members.size();
			num = num - excludedCount;
			logger.warn("Total num after exclusion ->" + num);
		}
			
		model.put("list", list);
		model.put("num", num);

		return new ModelAndView("ajax/members_ajax", "model", model);
	}

	@RequestMapping(value = "/member.html", method = GET)
	public ModelAndView getMemberHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		model.put("nav", "members");

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		model.put("memberID", memberID);
		model.put("tenantPrefix", this.service.getTenantPrefix());

		int roleID;
		if (memberID != 0) {
			Member member = this.service.getMember(memberID);

			int tenantId = TenantContext.getTenantId();
			// Cross tenant check
			if (member == null || tenantId != member.getTenantID()) {
				logger.error("Unauthorized access to Member Id -{} from Tenant -{}", memberID, tenantId);
				return new ModelAndView("admin/members_html", "model", model);
			}

			model.put("memberName", ESAPI.encoder().encodeForJavaScript(member.getMemberName()));
			model.put("roleID", member.getRoleID());
			model.put("roomExtNumber", member.getRoomExtNumber());
			roleID = member.getRoleID();
			model.put("importedUsed", member.getImportedUsed());
		} else {
			model.put("memberName", "");
			model.put("roleID", 3);
			roleID = 3;
			model.put("importedUsed", "0");
		}

		model.put("ldap", false);
		if (this.tenantService.isTenantAuthenticatedWithLDAP(TenantContext.getTenantId())) {
			List<MemberRoles> roles = this.system.getToRoles();

			for (MemberRoles role : roles) {
				if (role.getRoleID() == roleID) {
					model.put("ldap", true);
					break;
				}
			}
		}

		// check if default group exists
		Group defaultGroup = this.group.getDefaultGroup();
		model.put("defaultGroupExists", defaultGroup != null);

		return new ModelAndView("admin/member_html", "model", model);
	}

	@RequestMapping(value = "/member.ajax", method = GET)
	public ModelAndView getMemberAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberID", memberID);

		Member member = null;
		if (memberID == 0) {
			member = new Member();
			member.setLangID(10); // Language 10 = Default System Language
			member.setTenantPrefix(this.service.getTenantPrefix());
			member.setGroupID(this.group.getDefaultGroup().getGroupID());
			member.setProxyID(0);
			member.setLocationID(this.system.getTenantDefaultLocationTagID());
			member.setRoleID(3);
			member.setEnable("on");
			member.setAllowedToParticipateHtml("on");

			model.put("importedUsed", "0");

		} else {
			member = this.service.getMember(memberID);

			int tenantId = TenantContext.getTenantId();
			// Cross tenant check
			if (member == null || tenantId != member.getTenantID()) {
				logger.error("Unauthorized access to Member -{} from Tenant - {}", memberID, tenantId);
				member = new Member();
				member.setLangID(10); // Language 10 = Default System Language
				member.setTenantPrefix(this.service.getTenantPrefix());
				member.setGroupID(this.group.getDefaultGroup().getGroupID());
				member.setProxyID(0);
				member.setLocationID(this.system.getTenantDefaultLocationTagID());
				member.setRoleID(3);
				member.setEnable("on");
				member.setAllowedToParticipateHtml("on");
			} else {
				String stripTenantPrefix = member.getRoomExtNumber().replaceFirst(member.getTenantPrefix(), "");
				member.setRoomExtNumber(stripTenantPrefix);
				member.setMemberName(member.getMemberName());

				model.put("roomExtNumber", member.getRoomExtNumber());
				model.put("importedUsed", member.getImportedUsed());
			}
		}
		model.put("tenantPrefix", this.service.getTenantPrefix());
		model.put("ldap", false);
		if (this.tenantService.isTenantAuthenticatedWithLDAP(TenantContext.getTenantId())) {
			List<MemberRoles> roles = this.system.getToRoles();

			for (MemberRoles role : roles) {
				if (role.getRoleID() == member.getRoleID()) {
					model.put("ldap", true);
					break;
				}
			}
		}

		model.put("member", member);
		TenantConfiguration tenantConfiguration = this.tenantService
				.getTenantConfiguration(TenantContext.getTenantId());
		
	
		model.put("isUsrImgEnbledAdmin", tenantConfiguration.getUserImage() == 1);
		model.put("isUsrImgUpldEnbledAdm", tenantConfiguration.getUploadUserImage() == 1);
		model.put("hideImageDelete", false);
		if (tenantConfiguration.getUserImage() == 1) {
			String image = this.service.getThumbNailImage(TenantContext.getTenantId(), memberID);
			if (image == null) {
				//since user cant delete the default image.
				model.put("hideImageDelete", true);
				//getting the avatar
				image = this.service.getDefaultThumbNailImage(TenantContext.getTenantId(), memberID);
			}

			model.put("thumbNailImage", image == null ? "" : image);
		} else {
			model.put("thumbNailImage", "");
		}
		
		model.put("neoRoomPermanentPairingDeviceUser", member.isNeoRoomPermanentPairingDeviceUser());
		return new ModelAndView("ajax/member_ajax", "model", model);
	}

	@RequestMapping(value = "/savemember.ajax", method = POST)
	public ModelAndView saveMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();

		Locale loc = LocaleContextHolder.getLocale();

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
	
		Member member = new Member();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(member);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();

		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		String tenantPrefix = StringUtils.trimToEmpty(tenant.getTenantPrefix());
		String roomExtNumber = request.getParameter("roomExtNumber");

		// Hunter will only accept numerical room extension numbers
		if(!NumberUtils.isDigits(roomExtNumber)) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", MessageFormat
					.format(ms.getMessage("import.invalid.ext.number", null, "", loc), 
							HtmlUtils.htmlEscape(member.getRoomExtNumber())));
			results.addError(fe);
		}

		String fullRoomExt = tenantPrefix + roomExtNumber;
		member.setRoomExtNumber(fullRoomExt);
		/*
		 * START Security role check: Tenant Admins and Operators Can Not Create
		 * Higher Privileged User Types
		 */
		FieldError sfe = checkSecurityRoleForUserCreateUpdate(user, member);
		if (sfe != null) {
			results.addError(sfe);
		}

		/*
		 * END Security role check: Tenant Admins and Operators Can Not Create
		 * Higher Privileged User Types
		 */

		// validations
		if (this.service.isMemberExistForUserName(member.getUsername(), memberID)) {
			FieldError fe = new FieldError("username", "username",
					MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc), member.getUsername()));
			results.addError(fe);
		}
		if (this.room.isRoomExistForRoomExtNumber(member.getRoomExtNumber(), member.getRoomID())) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", MessageFormat
					.format(ms.getMessage("duplicate.room.number", null, "", loc), member.getRoomExtNumber()));
			results.addError(fe);
		}
		if (member.getRoomExtNumber().length() > 64) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber",
					"Extension (with tenant prefix) must be 64 characters or less.");
			results.addError(fe);
		}

		int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, member.getRoomExtNumber());
		if (extExists > 0) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", MessageFormat.format(
					ms.getMessage("extn.matches.scheduleroom.prefix", null, "", loc), 
						HtmlUtils.htmlEscape(member.getRoomExtNumber())));
			results.addError(fe);
		}

		if (member.getUsername().length() > 80) {
			FieldError fe = new FieldError("AddMember", "username", MessageFormat
					.format(ms.getMessage("invalid.username.length", null, "", loc), 
							HtmlUtils.htmlEscape(member.getUsername())));
			results.addError(fe);
		}

		if (!member.getUsername()
				.matches(ValidationUtils.USERNAME_REGEX)) { // printabled unicoded username

			FieldError fe = new FieldError("AddMember", "username",
			MessageFormat.format(ms.getMessage("invalid.username.match", null, "", loc)
							.replace("&quot;", "\""), HtmlUtils.htmlEscape(member.getUsername())));
			results.addError(fe);
		}

		if (!member.getEmailAddress().matches(ValidationUtils.EMAIL_REGEX)) {
			FieldError fe = new FieldError("AddMember", "email", MessageFormat
					.format(ms.getMessage("invalid.email.match", null, "", loc), 
							HtmlUtils.htmlEscape(member.getEmailAddress())));
			results.addError(fe);
		}
		if (member.getPhone1().length() > 64) {
			FieldError fe = new FieldError("AddMember", "Phone 1", MessageFormat
					.format(ms.getMessage("import.invalid.ph1.length", null, "", loc), member.getPhone1().length()));
			results.addError(fe);

		}
		if (member.getPhone2().length() > 64) {
			FieldError fe = new FieldError("AddMember", "Phone 2", MessageFormat
					.format(ms.getMessage("import.invalid.ph2.length", null, "", loc), member.getPhone2().length()));
			results.addError(fe);
		}
		if (member.getPhone3().length() > 64) {
			FieldError fe = new FieldError("AddMember", "Phone 3", MessageFormat
					.format(ms.getMessage("import.invalid.ph3.length", null, "", loc), member.getPhone3().length()));
			results.addError(fe);
		}
		if (member.getTitle().length() > 128) {
			FieldError fe = new FieldError("AddMember", "Title", MessageFormat
					.format(ms.getMessage("import.invalid.title.length", null, "", loc), member.getTitle().length()));
			results.addError(fe);
		}
		if (member.getDepartment().length() > 128) {
			FieldError fe = new FieldError("AddMember", "Department", MessageFormat.format(
					ms.getMessage("import.invalid.department.length", null, "", loc), member.getDepartment().length()));
			results.addError(fe);
		}
		if (member.getInstantMessagerID().length() > 128) {
			FieldError fe = new FieldError("ImportMembers", "IM", MessageFormat.format(
					ms.getMessage("import.invalid.im.length", null, "", loc), member.getInstantMessagerID().length()));
			results.addError(fe);
		}
		if (member.getLocation().length() > 128) {
			FieldError fe = new FieldError("ImportMembers", "Location", MessageFormat.format(
					ms.getMessage("import.invalid.location.length", null, "", loc), member.getLocation().length()));
			results.addError(fe);
		}

		if (memberID == 0 && this.room.isRoomExistForRoomName(member.getUsername(), 0)) {
	       	 FieldError fe = new FieldError("roomName", "roomName", 
	       			 MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc), 
	       					HtmlUtils.htmlEscape(member.getUsername())));
	            results.addError(fe);
	       }
		
		// check if lines license is still valid
		List<FieldError> seatErrors = checkLicenses(memberID, member);

		if (seatErrors != null) {
			for (FieldError seatError : seatErrors) {
				results.addError(seatError);
			}
		}

		// ------------------------------------------------------------------------------------------------------------------------------------------

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		int tenantId = TenantContext.getTenantId();
		
		// Validate the GroupId, if the groupId doesn't exist for Tenant, replace it with default group
		Group group = null;
		try {
			group = this.group.getGroup(member.getGroupID());
		} catch (Exception e1) {
			logger.error("Invalid GroupId {}", member.getGroupID());
		}
		
		if(group == null || (group.getTenantID() != TenantContext.getTenantId())) {
			Group defaultGroup = this.group.getDefaultGroup();
			member.setGroupID(defaultGroup.getGroupID());
		}

		if (results.hasErrors()) {
			errors.addAll(results.getFieldErrors());
			model.put("success", Boolean.FALSE);

			model.put("fields", errors);
		} else {
			if (memberID == 0) {
				// create a new member
				String password = ServletRequestUtils.getStringParameter(request, "password1", "password");
				if (this.service.isValidMemberPassword(password, memberID)) {
					member.setPassword(PasswordHash.createHash(password)); // encode
																			// the
																			// password
					memberID = this.service.insertMember(member);

					// create a Personal Room for the member
					Room room = new Room();
					room.setMemberID(memberID);
					room.setRoomDescription(member.getUsername() + "-Personal Room");
					room.setGroupID(member.getGroupID());
					room.setRoomEnabled(member.getActive());
					room.setRoomMuted(0);
					room.setRoomExtNumber(member.getRoomExtNumber());
					room.setRoomName(member.getUsername());
					room.setRoomTypeID(RoomTypes.PERSONAL.getId());
					room.setDisplayName(member.getMemberName());
					if (VendorUtils.isRoomsLockedByDefault()) {
						room.setRoomLocked(1);
					}
					this.room.insertRoom(room);

					// Send New Account Notification email out
					if (this.system.getEnableNewAccountNotification()
							&& this.system.isAuthenticationLocalForMemberRole(tenantId, member.getRoleID())
							&& (this.emailService != null)) {
						try {
							sendNewAccountNotificationEmail(member.getEmailAddress(), member.getMemberName(),
									member.getUsername(), password, request, member.getLangID());
							// request.getScheme()+"://"+request.getHeader("host")+request.getContextPath());
						} catch (Exception e) {
							FieldError fe = new FieldError("CannotSendEmail", "CannotSendEmail",
									ms.getMessage(
											"user.created.successfully.br.failed.to.send.email.notification.to.this.user",
											null, "", loc));
							errors.add(fe);
						}
					}
				} else {
					logger.error("Password for Member did not passed requirements check");
					FieldError fe = new FieldError("password1", ms.getMessage("error", null, "", loc),
							ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));

					errors.add(fe);
				}
			} else {
				String password = ServletRequestUtils.getStringParameter(request, "password1", "do_not_update");
				try {
					if (password.equalsIgnoreCase("do_not_update")) {
						member.setPassword(null);
						memberID = this.service.updateMember(tenantId, memberID, member);
					} else {
						if (this.service.isValidMemberPassword(password, member.getMemberID())) {
							member.setPassword(PasswordHash.createHash(password));
							memberID = this.service.updateMember(tenantId, memberID, member);
						} else {
							logger.error("Password for Member did not passed requirements check");
							FieldError fe = new FieldError("password1", ms.getMessage("error", null, "", loc),
									ms.getMessage("password.change.did.not.meet.requirements", null, "", loc));
							errors.add(fe);
						}
					}
				} catch (AccessRestrictedException e) {
					FieldError fe = new FieldError("UpdateMember", "UpdateMember",
							ms.getMessage("invalid.member", null, "", loc));
					errors.add(fe);
					model.put("fields", errors);
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}
			}

			if (errors.size() > 0) {
				model.put("success", Boolean.FALSE);
			} else {
				model.put("success", Boolean.TRUE);
			}
			model.put("fields", errors);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/enableprovisioneduser.ajax", method = POST)
	public ModelAndView enableProvisionedUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.user.getLoginUser();

		Locale loc = LocaleContextHolder.getLocale();

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
		int active = ServletRequestUtils.getIntParameter(request, "active", 0);
		int userImageAllowed = ServletRequestUtils.getIntParameter(request, "userImageAllowed", 0);


		List<FieldError> errors = new ArrayList<>();
		if (memberID < 1) {
			FieldError fe = new FieldError("UpdateMember", "UpdateMember",
					ms.getMessage("invalid.member", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() == 0) {
			Member member = service.getMember(TenantContext.getTenantId(), memberID);
			if (member == null) {
				FieldError fe = new FieldError("UpdateMember", "UpdateMember",
						ms.getMessage("invalid.member", null, "", loc));
				errors.add(fe);
			}

			if (errors.size() == 0) {
				FieldError fe = checkSecurityRoleForUserCreateUpdate(user, member);
				if (fe != null) {
					errors.add(fe);
				}

				List<FieldError> err = checkLicenses(memberID, member);
				if (err.size() > 0) {
					errors.addAll(err);
				} else {
					int affected = this.service.enableUser(TenantContext.getTenantId(), memberID, member.getUsername(),
							active);
					if (affected == 0) {
						fe = new FieldError("UpdateMember", "UpdateMember",
								ms.getMessage("invalid.member", null, "", loc));
						errors.add(fe);
					}
						
						int affectedUserImageOp = this.service.updateUserImageAlwdFlag(TenantContext.getTenantId(), memberID, member.getUsername(),
								userImageAllowed);
						if (affectedUserImageOp == 0) {
							fe = new FieldError("UpdateMember", "UpdateMember",
									ms.getMessage("invalid.member-Failed updating Disable Upload User Image", null, "", loc));
							errors.add(fe);
					}
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		if (errors.size() > 0) {
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		model.put("fields", errors);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	private List<FieldError> checkLicenses(int memberID, Member member) {
		Locale loc = LocaleContextHolder.getLocale();
		// check if lines license is still valid
		List<FieldError> errors = new ArrayList<>();
		if (this.license.lineLicenseExpired()) {
			FieldError fe = new FieldError("AddMember", "AddMember",
					MessageFormat.format(ms.getMessage("line.license.expired", null, "", loc), member.getUsername()));
			errors.add(fe);
		}

		int roleID = member.getRoleID();

		if (roleID == MemberRoleEnum.EXECUTIVE.getMemberRoleID()) {
			long allowedExecutives = this.license.getAllowedExecutives();
			final String msg = "cannot.add.member.executive.license.exceeded";
			errors.addAll(checkAllowedSeatsExececutivesPanoramas(memberID, member, allowedExecutives, msg));
		} else if (roleID == MemberRoleEnum.VIDYO_PANORAMA.getMemberRoleID()) {
			long allowedPanoramas = this.license.getAllowedPanoramas();
			final String msg = "cannot.add.member.panorama.license.exceeded";
			errors.addAll(checkAllowedSeatsExececutivesPanoramas(memberID, member, allowedPanoramas, msg));
		} else
			if (roleID == MemberRoleEnum.ADMIN.getMemberRoleID() || roleID == MemberRoleEnum.OPERATOR.getMemberRoleID()
					|| roleID == MemberRoleEnum.NORMAL.getMemberRoleID()) {
			long allowedSeats = this.license.getAllowedSeats();
			final String msg = "cannot.add.member.seat.license.exceeded";
			errors.addAll(checkAllowedSeatsExececutivesPanoramas(memberID, member, allowedSeats, msg));
		}

		return errors;
	}

	private FieldError checkSecurityRoleForUserCreateUpdate(User user, Member member) {
		/*
		 * START Security role check: Tenant Admins and Operators Can Not Create
		 * Higher Privileged User Types
		 */

		Locale loc = LocaleContextHolder.getLocale();
		FieldError fe = null;
		if (!service.isUserEligibleToCreateUpdateMember(user, member)) {
			fe = new FieldError("AddMember", "AddMember", MessageFormat
					.format(ms.getMessage("import.member.insert.error", null, "", loc), member.getUsername()));
		}

		return fe;
	}

	private List<FieldError> checkAllowedSeatsExececutivesPanoramas(int memberID, Member member, long allowedSeats,
			String msg) {
		Locale loc = LocaleContextHolder.getLocale();
		FieldError fe = null;
		List<FieldError> fes = new ArrayList<>();
		if (memberID == 0) {
			if (member.getActive() == 1 && allowedSeats < 1) {
				fe = new FieldError("enable", "enable",
						MessageFormat.format(ms.getMessage(msg, null, "", loc), member.getUsername()));
				fes.add(fe);
			} else if (member.getAllowedToParticipate() == 1 && allowedSeats < 1) {
				fe = new FieldError("allowedToParticipateHtml", "allowedToParticipateHtml",
						MessageFormat.format(ms.getMessage(msg, null, "", loc), member.getUsername()));
				fes.add(fe);
			}
		} else {
			Member oldMember = service.getMember(TenantContext.getTenantId(), memberID);

			boolean rolesAreEqual = false;
			int oldRole = oldMember.getRoleID();
			int newRole = member.getRoleID();

			if (oldRole == newRole || (oldRole == MemberRoleEnum.ADMIN.getMemberRoleID()
					|| oldRole == MemberRoleEnum.OPERATOR.getMemberRoleID()
					|| oldRole == MemberRoleEnum.NORMAL.getMemberRoleID())
					&& (newRole == MemberRoleEnum.ADMIN.getMemberRoleID()
							|| newRole == MemberRoleEnum.OPERATOR.getMemberRoleID()
							|| newRole == MemberRoleEnum.NORMAL.getMemberRoleID())) {
				rolesAreEqual = true;
			}

			if (oldMember.getActive() == 0 && member.getActive() == 1 && allowedSeats < 1
					|| oldMember.getActive() == 1 && member.getActive() == 1 && !rolesAreEqual && allowedSeats < 1) {
				fe = new FieldError("enable", "enable",
						MessageFormat.format(ms.getMessage(msg, null, "", loc), member.getUsername()));
				fes.add(fe);
			} else if (oldMember.getAllowedToParticipate() == 0 && member.getAllowedToParticipate() == 1
					&& allowedSeats < 1
					|| oldMember.getAllowedToParticipate() == 1 && member.getAllowedToParticipate() == 1
							&& !rolesAreEqual && allowedSeats < 1) {
				fe = new FieldError("allowedToParticipateHtml", "allowedToParticipateHtml",
						MessageFormat.format(ms.getMessage(msg, null, "", loc), member.getUsername()));
				fes.add(fe);
			}
		}

		return fes;
	}

	// Attempt to send out notification email. There is no error returned if
	// sending failed on whatever reason.
	// "void org.springframework.mail.MailSender.send()" method won't give any
	// error if sending failed
	private void sendNewAccountNotificationEmail(String emailAddress, String fullName, String userName,
			String plainPassword, HttpServletRequest request, int langID) {
		Locale tmploc;

		if (langID == 10)
			tmploc = LangUtils.getLocaleByLangID(system.getSystemLang().getLangID());
		else
			tmploc = LangUtils.getLocaleByLangID(langID);

		SimpleMailMessage message = new SimpleMailMessage();
		String subject = ms.getMessage("NewAccount.Notification.Email.Subject", null, "", tmploc);
		String content = ms.getMessage("NewAccount.Notification.Email.Content", null, "", tmploc);
		content = content.replaceAll("\\[FULLNAME\\]", Matcher.quoteReplacement(fullName));
		content = content.replaceAll("\\[USERNAME\\]", Matcher.quoteReplacement(userName));
		content = content.replaceAll("\\[PASSWORD\\]", Matcher.quoteReplacement(plainPassword));
		content = content.replaceAll("\\[PORTAL\\]", request.getScheme() + "://" + request.getHeader("host"));

		String fromAddr = this.system.getNotificationFromEmailAddress();
		message.setFrom(fromAddr);
		message.setTo(emailAddress);
		message.setSubject(subject);
		message.setText(content);

		this.emailService.sendEmailAsynchronous(message);
	}

	@RequestMapping(value = "/deletemember.ajax", method = POST)
	public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
		Locale loc = LocaleContextHolder.getLocale();

		Map<String, Object> model = new HashMap<String, Object>();

		int deleteCount = 0;
		Member deletingMember = this.service.getMember(TenantContext.getTenantId(), memberID);
		List<SessionInformation> sessionInfoList = null;
		if (deletingMember != null) {
			List<Object> principals = sessionRegistry.getAllPrincipals();
			for (Object principal : principals) {
				if (((VidyoUserDetails) principal).getUsername().equals(deletingMember.getUsername())
						&& (deletingMember.getRoleID() == MemberRoleEnum.ADMIN.getMemberRoleID()
								|| deletingMember.getRoleID() == MemberRoleEnum.OPERATOR.getMemberRoleID())) {
					sessionInfoList = sessionRegistry.getAllSessions(principal, false);
					break;
				}
			}
			try {
				deleteCount = this.service.deleteMember(TenantContext.getTenantId(), memberID);
			} catch (AccessRestrictedException e) {
				FieldError fe = new FieldError("DeleteMember", "DeleteMember",
						ms.getMessage("invalid.member", null, "", loc));
				List<FieldError> errors = new ArrayList<FieldError>();
				errors.add(fe);
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
				return new ModelAndView("ajax/result_ajax", "model", model);
			}
		}

		if (deleteCount > 0) {
			if (sessionInfoList != null) {
				for (SessionInformation sessionInfo : sessionInfoList) {
					sessionInfo.expireNow();
				}
			}
			model.put("success", Boolean.TRUE);
		} else {
			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/roles.ajax", method = GET)
	public ModelAndView getMemberRolesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Long num = this.service.getCountMemberRoles();
		model.put("num", num);
		List<MemberRoles> list = this.service.getMemberRoles();
		model.put("list", list);

		return new ModelAndView("ajax/roles_ajax", "model", model);
	}

	@RequestMapping(value = "/samlroles.ajax", method = GET)
	public ModelAndView getSamlMemberRolesAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Long num = this.service.getCountSamlMemberRoles();
		model.put("num", num);
		List<MemberRoles> list = this.service.getSamlMemberRoles();
		model.put("list", list);

		return new ModelAndView("ajax/roles_ajax", "model", model);
	}

	@RequestMapping(value = "/legacy.html", method = GET)
	public ModelAndView getLegacyHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		model.put("nav", "members");

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		model.put("memberID", memberID);

		int tenantId = TenantContext.getTenantId();

		if (memberID != 0) {
			Member member = this.service.getMember(memberID);

			// Cross tenant check
			if (member != null && tenantId == member.getTenantID()) {
				model.put("memberName", member.getMemberName());
			} else {
				model.put("memberName", "");
			}

		} else {
			model.put("memberName", "");
		}

		return new ModelAndView("admin/legacy_html", "model", model);
	}

	@RequestMapping(value = "/legacy.ajax", method = GET)
	public ModelAndView getLegacyAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberID", memberID);
		int tenantId = TenantContext.getTenantId();
		Member member = null;
		if (memberID == 0) {
			member = new Member();
		} else {
			member = this.service.getMember(memberID);

			// Cross tenant check
			if (member == null || member.getTenantID() != tenantId) {
				logger.error("Member does not exist/Unauthorized access MemberId - {}, from tenantId - {}", memberID,
						tenantId);
				member = new Member();
			}
		}
		model.put("member", member);

		return new ModelAndView("ajax/legacy_ajax", "model", model);
	}

	@RequestMapping(value = "/savelegacy.ajax", method = POST)
	public ModelAndView saveLegacy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);

		Member member = new Member();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(member);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();

		// validations
		if (this.service.isMemberExistForUserName(member.getUsername(), memberID)) {
			FieldError fe = new FieldError("username", "username",
					MessageFormat.format(ms.getMessage("duplicate.user.name", null, "", loc),
							ESAPI.encoder().encodeForXML(member.getUsername())));
			results.addError(fe);
		}

		if (this.room.isExtensionExistForLegacyExtNumber(member.getRoomExtNumber(), member.getRoomID())) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber",
					MessageFormat.format(ms.getMessage("duplicate.room.number", null, "", loc),
							ESAPI.encoder().encodeForXML(member.getRoomExtNumber())));
			results.addError(fe);
		}
		if (member.getUsername().length() > 80) {
			FieldError fe = new FieldError("AddMember", "username",
					MessageFormat.format(ms.getMessage("invalid.username.length", null, "", loc),
							ESAPI.encoder().encodeForXML(member.getUsername())));
			results.addError(fe);
		}
		//
		if (!MemberServiceImpl.isValidLegacyUsername(member.getUsername())) { // printabled
																				// unicoded
																				// username..
			FieldError fe = new FieldError("AddMember", "username",
					MessageFormat.format(ms.getMessage("invalid.legacy.username.match", null, "", loc),
							ESAPI.encoder().encodeForXML(member.getUsername())));
			results.addError(fe);
		}

		Map<String, Object> model = new HashMap<String, Object>();

		int tenantId = TenantContext.getTenantId();

		if (results.hasErrors()) {
			List<FieldError> fields = results.getFieldErrors();
			model.put("success", Boolean.FALSE);

			model.put("fields", fields);
		} else {
			if (memberID == 0) {
				// create a new member
				member.setMemberName(member.getUsername());
				member.setPassword("");
				member.setActive(1);
				member.setAllowedToParticipate(1);
				member.setDescription("Legacy Device");
				member.setEmailAddress("");
				Group defGroup = null;
				// Get default group for the Tenant
				try {
					defGroup = group.getDefaultGroup();
				} catch (Exception e) {
					logger.error("Default Group does not exist for Tenant Id - {}", tenantId);
				}

				if (defGroup == null) {
					logger.error("There is no Default group available for the legacy device creation");
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}

				member.setGroupID(defGroup.getGroupID());
				member.setLocation("");
				member.setProfileID(1);
				member.setLangID(1);

				memberID = this.service.addLegacy(member);

				if (memberID <= 0) {
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}

				// create a Personal Room for the member
				Room room = new Room();
				room.setMemberID(memberID);
				room.setRoomDescription(member.getUsername() + "-Legacy Device");
				room.setGroupID(member.getGroupID());
				room.setRoomEnabled(member.getActive());
				room.setRoomMuted(0);
				room.setRoomExtNumber(member.getRoomExtNumber());
				room.setRoomName(member.getUsername());
				room.setRoomTypeID(RoomTypes.LEGACY.getId());
				int roomId = this.room.insertRoom(room);
				if (roomId <= 0) {
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}
			} else {
				Member existingMember = this.service.getMember(memberID);

				if (existingMember == null || existingMember.getTenantID() != tenantId) {
					logger.error("Member doesnot exist/Unauthorized access to member id -{}", memberID);
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}
				int updateCount = this.service.updateLegacy(tenantId, memberID, member);

				if (updateCount <= 0) {
					logger.error("Update legacy failed -{}", memberID);
					model.put("success", Boolean.FALSE);
					return new ModelAndView("ajax/result_ajax", "model", model);
				}

			}
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/importmembers.html", method = RequestMethod.GET)
	public ModelAndView importMembersHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		model.put("nav", "members");

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		return new ModelAndView("admin/import_members_html", "model", model);
	}

	private ModelAndView failedToUploadImportMemberFile(List<FieldError> errors, Map<String, Object> model) {
		logger.error("User import failed.");
		Locale loc = LocaleContextHolder.getLocale();
		errors.add(new FieldError("uploadedfile", ms.getMessage("import.read.csv.error", null, "", loc),
				ms.getMessage("import.read.csv.error", null, "", loc)));

		model.put("userCreated", 0);
		model.put("fields", errors);
		model.put("success", Boolean.FALSE);

		return new ModelAndView("ajax/import_members_result_ajax", "model", model);
	}

	@RequestMapping(value = "/importmembers.ajax", method = RequestMethod.POST)
	public ModelAndView doImportMembersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<FieldError> errors = new ArrayList<FieldError>();
		Map<String, Object> model = new HashMap<String, Object>();
		int userCreated = 0;
		Locale loc = LocaleContextHolder.getLocale();
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
				MultipartHttpServletRequest.class);
		MultipartFile multipartFile = multipartRequest.getFile("client-path");
		File uploadedFile = new File(getUploadTempDir() + RandomStringUtils.randomAlphabetic(16));
		multipartFile.transferTo(uploadedFile);
		File decryptedFile = null;
		String filePwd = ServletRequestUtils.getStringParameter(request, "password", "test");

		if (multipartFile.getOriginalFilename().endsWith(".vidyo")) {
			logger.error("User import failed, unsupported .vidyo file.");
			String errorMsg = "This format is no longer supported. Please upload a .csv file and try again.";
			errors.add(new FieldError("uploadedfile", errorMsg, errorMsg));
			model.put("userCreated", 0);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			FileUtils.deleteQuietly(uploadedFile);
			return new ModelAndView("ajax/import_members_result_ajax", "model", model);
		} else if (multipartFile.getOriginalFilename().endsWith(".csv")) {
			String mimeType = PortalUtils.determineFileMimeType(new File(uploadedFile.getAbsolutePath()));
			if (!"text/plain".equals(mimeType)) {
				String errorMsg = "Invalid .csv file uploaded.";
				errors.add(new FieldError("uploadedfile", errorMsg, errorMsg));
				model.put("userCreated", 0);
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
				FileUtils.deleteQuietly(uploadedFile);
				return new ModelAndView("ajax/import_members_result_ajax", "model", model);
			}
		} else {
			String outputDir = getUploadTempDir() + System.currentTimeMillis();
			File newDir = new File(outputDir);
			newDir.mkdir();
			try {
				SecurityUtils.extractSecureArchiveUsingPassword(filePwd, uploadedFile.getAbsolutePath(), outputDir);
				decryptedFile = new File(getUploadTempDir() + "/" + "UsersExport.csv");
				FileUtils.moveFile(new File(outputDir + "/UsersExport.csv"), decryptedFile);
			} catch (Exception e) {
				logger.error("Error decrypting user import file: " + e.getMessage());
				String errorMsg = "Invalid file uploaded and/or password.";
				errors.add(new FieldError("uploadedfile", errorMsg, errorMsg));
				model.put("userCreated", 0);
				model.put("fields", errors);
				model.put("success", Boolean.FALSE);
				FileUtils.deleteQuietly(uploadedFile);
				return new ModelAndView("ajax/import_members_result_ajax", "model", model);
			} finally {
				FileUtils.deleteQuietly(new File(outputDir));
			}
		}

		boolean newAccountEnabled = this.system.getEnableNewAccountNotification();
		long allowedSeats = this.license.getAllowedSeats();
		long totalExecSeats = this.service.getLicensedExecutive(TenantContext.getTenantId());
		long usedExecSeats = this.service.getCountExecutives(TenantContext.getTenantId());
		long allowedExecSeats = (totalExecSeats - usedExecSeats);
		long totalPanoramaSeats = this.service.getLicensedPanorama(TenantContext.getTenantId());
		long usedPanoramaSeats = this.service.getCountPanoramas(TenantContext.getTenantId());
		long allowedPanoramaSeats = totalPanoramaSeats - usedPanoramaSeats;
		// Get the MemberRoles for RoleName/UserType Validation
		List<MemberRoles> memberRolesList = this.service.getMemberRoles();
		List<String> roles = new ArrayList<String>();
		Map<String, Integer> roleNameIdMap = new HashMap<String, Integer>();
		for (MemberRoles memberRoles : memberRolesList) {
			roles.add(memberRoles.getRoleName().toLowerCase());
			roleNameIdMap.put(memberRoles.getRoleName().toLowerCase(), memberRoles.getRoleID());
		}

		// parsing the uploaded csv file and insert into DB
		CsvReader reader = null;
		int lineNum;
		try {
			if (decryptedFile == null) {
				decryptedFile = uploadedFile;
			}
			reader = new CsvReader(decryptedFile.getAbsolutePath(), ',', Charset.forName("utf-8")); // delimiter
																									// is
																									// ','
			reader.setSkipEmptyRecords(false);
			reader.setTrimWhitespace(true);
			reader.readHeaders(); // Bypass the first line.
			// Here it's doesn't matter if csv has header or not. First line
			// maybe have
			// 3 hidden-bytes which specified the file encoding.
			lineNum = 1;
			while (reader.readRecord()) {
				lineNum++;

				// Checks errors: if too many errors displayed
				if (errors.size() > 10) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.too.many.errors", null, "", loc), lineNum));
					errors.add(fe);
					break;
				}

				if (reader.getColumnCount() == 1) {
					// reader.skipRecord();
					continue;
				}
				if (reader.getColumnCount() != 18) {
					errors.add(new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							ms.getMessage("import.line.error", null, "", loc)));
					// reader.skipLine();
					continue;
				}
				String userType = reader.get(0); // reader.get("UserType");
				String username = reader.get(1); // reader.get("Username");
				String password = reader.get(2); // reader.get("Password");
				String name = reader.get(3); // reader.get("Fullname");
				String email = reader.get(4); // reader.get("Email");
				String ext = StringUtils.trimToEmpty(reader.get(5)); // reader.get("Extension");
				String groupName = reader.get(6); // reader.get("Group");
				String lang = reader.get(7); // reader.get("Language");
				String desc = reader.get(8); // reader.get("Description");
				String proxyName = reader.get(9); // reader.get("Proxy");
				String locationTag = reader.get(10); // reader.get("LocationTag");
				String ph1 = reader.get(11);// PhoneNumber1
				String ph2 = reader.get(12);// PhoneNumber2
				String ph3 = reader.get(13);// PhoneNumber3
				String department = reader.get(14);// Department
				String title = reader.get(15);// Title
				String im = reader.get(16);// IM
				String location = reader.get(17);// Location
				// String thumbNail= reader.get(17);

				// Check License: if Num of Seats is met
				if (!userType.equalsIgnoreCase(MEMBER_ROLE_EXECUTIVE)
						&& !userType.equalsIgnoreCase(MEMBER_ROLE_VIDYO_PANORAMA)
						&& !userType.equalsIgnoreCase(MEMBER_ROLE_VIDYO_ROOM) && allowedSeats <= 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.exceed.seats.num", null, "", loc), lineNum));
					errors.add(fe);
					break;
				}

				// validation
				if (userType.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.invalid.usertype.is.required", null, "", loc), 
									HtmlUtils.htmlEscape(username)));
					errors.add(fe);
					continue;
				}
				if (username.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.invalid.username.is.required", null, "", loc), username));
					errors.add(fe);
					continue;
				}
				if (username.length() > 80) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("import.invalid.username.length", null, "", loc), 
											HtmlUtils.htmlEscape(username)));
					errors.add(fe);
					continue;
				}
				if (userType.equalsIgnoreCase("Legacy")) {
					if (!MemberServiceImpl.isValidLegacyUsername(username)) { // printabled
																				// unicoded
																				// username..
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.invalid.username.match", null, "", loc),
										HtmlUtils.htmlEscape(username)));
						errors.add(fe);
						continue;
					}
				} else {
					if (!username.matches(ValidationUtils.USERNAME_REGEX)) { // printabled unicoded username
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.invalid.username.match", null, "", loc),
										HtmlUtils.htmlEscape(username)));
						errors.add(fe);
						continue;
					}
				}
				if ((ext.length() == 0) || StringUtils.isEmpty(ext)) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ext.is.required", null, "", loc), ext));
					errors.add(fe);
					continue;
				}
				if (ext.length() > 64) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ext.size", null, "", loc), ext));
					errors.add(fe);
					continue;
				}
				
				// If not legacy and not if its not numeric digits return error
				if(!userType.equalsIgnoreCase("Legacy") && !NumberUtils.isDigits(ext)) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ext.number", null, "", loc), 
									HtmlUtils.htmlEscape(ext)));
					errors.add(fe);
					continue;
				}

				int extExists = 0;

				// Handle Legacy Devices here
				if (userType.equalsIgnoreCase("Legacy")) {
					// validations
					if (this.service.isMemberExistForUserName(username, 0)) {
						FieldError fe = new FieldError("username",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.legacy.in.use", null, "", loc), 
										HtmlUtils.htmlEscape(username)));
						errors.add(fe);
						continue;
					}
					if (this.room.isExtensionExistForLegacyExtNumber(ext, 0)) {
						FieldError fe = new FieldError("roomExtNumber",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.extension.in.use", null, "", loc), ext));
						errors.add(fe);
						continue;
					}

					extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, ext);
					if (extExists > 0) {
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
										.format(ms.getMessage("extn.matches.scheduleroom.prefix", null, "", loc), 
												HtmlUtils.htmlEscape(ext)));
						errors.add(fe);
						continue;
					}

					// License is not counted against Legacy Devices

					// create a new member
					Member member = new Member();
					member.setUsername(username);
					member.setMemberName(username);
					member.setPassword("");
					member.setActive(1);
					member.setAllowedToParticipate(1);
					member.setDescription("Legacy Device");
					member.setEmailAddress("");
					member.setGroupID(1);
					member.setLocation("");
					member.setProfileID(1);
					member.setLangID(1);
					member.setRoleID(6);
					int memberID = this.service.addLegacy(member);

					// create a Personal Room for the member
					Room room = new Room();
					room.setMemberID(memberID);
					room.setRoomDescription(member.getUsername() + "-Legacy Device");
					room.setGroupID(member.getGroupID());
					room.setRoomEnabled(member.getActive());
					room.setRoomMuted(0);
					room.setRoomExtNumber(ext);
					room.setRoomName(member.getUsername());
					// TODO Get the RoomType from Database
					room.setRoomTypeID(3);
					int roomID = this.room.insertRoom(room);
					// Increase the user count
					userCreated++;
					continue;
				}

				// Validate the UserType values after legacy, as legacy type is
				// not returned in the roles
				if (!roles.contains(userType.toLowerCase())) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("import.invalid.usertype.value", null, "", loc), 
											HtmlUtils.htmlEscape(password)));
					errors.add(fe);
					continue;
				}
				if (password.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.invalid.password.is.required", null, "", loc), 
									HtmlUtils.htmlEscape(password)));
					errors.add(fe);
					continue;
				}
				if (name.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("import.invalid.fullname.is.required", null, "", loc), 
											HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (name.length() > 80) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.fullname.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (ph1.length() > 64) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ph1.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (ph2.length() > 64) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ph2.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (ph3.length() > 64) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.ph3.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (title.length() > 128) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.title.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (department.length() > 128) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("import.invalid.department.length", null, "", loc), 
											HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (im.length() > 128) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.im.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (location.length() > 128) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.location.length", null, "", loc), 
									HtmlUtils.htmlEscape(name)));
					errors.add(fe);
					continue;
				}
				if (email.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("import.invalid.email.is.required", null, "", loc), 
											HtmlUtils.htmlEscape(email)));
					errors.add(fe);
					continue;
				}
				if (!email.matches("^([\\w_\\-\\.])+@([\\w_\\-]+([\\.]))+([A-Za-z]{2,})$")) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.email.match", null, "", loc), 
									HtmlUtils.htmlEscape(email)));
					errors.add(fe);
					continue;
				}
				if (groupName.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.invalid.groupName.is.required", null, "", loc), groupName));
					errors.add(fe);
					continue;
				}
				if (locationTag.length() == 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.locationTag.is.required", null, "", loc),
									locationTag));
					errors.add(fe);
					continue;
				}
				if (userType.equalsIgnoreCase(MEMBER_ROLE_EXECUTIVE) && allowedExecSeats <= 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.exceed.seats.num", null, "", loc), lineNum));
					errors.add(fe);
					continue;
				}
				if (userType.equalsIgnoreCase(MEMBER_ROLE_VIDYO_PANORAMA) && allowedPanoramaSeats <= 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.exceed.seats.num", null, "", loc), lineNum));
					errors.add(fe);
					continue;
				}

				extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system,
						ext.startsWith(this.service.getTenantPrefix()) ? ext : this.service.getTenantPrefix() + ext);
				if (extExists > 0) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat
									.format(ms.getMessage("extn.matches.scheduleroom.prefix", null, "", loc), 
											HtmlUtils.htmlEscape(ext)));
					errors.add(fe);
					continue;
				}
				
				int langId = LangUtils.getLangId(lang, false); // don't use default language 
				if (langId == -1) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.invalid.language", null, "", loc), 
									HtmlUtils.htmlEscape(ext)));
					errors.add(fe);
					continue;
				}						

				Member m = new Member();
				m.setActive(1); // Active
				// Get roleId from the Map
				int roleID = roleNameIdMap.get(userType.toLowerCase());
				m.setRoleID(roleID);
				m.setRoomTypeID(1); // Personal Room
				m.setUsername(username);
				if (reader.getHeader(2).toLowerCase().trim().equals("hashed password")) {
					m.setPassword(password); // password in file already encoded
				} else {
					m.setPassword(PasswordHash.createHash(password));
				}
				m.setMemberName(name);
				m.setEmailAddress(email);
				m.setLangID(langId);
				m.setDescription(desc);
				m.setRoomExtNumber(ext);
				m.setAllowedToParticipate(1);
				m.setDepartment(department);
				m.setLocation(location);
				m.setInstantMessagerID(im);
				m.setPhone1(ph1);
				m.setPhone2(ph2);
				m.setPhone3(ph3);
				m.setTitle(title);

				if (this.service.isMemberExistForUserName(m.getUsername(), 0)) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.user.in.use", null, "", loc), 
									HtmlUtils.htmlEscape(m.getUsername())));
					errors.add(fe);
					continue;
				}
				if (m.getRoomExtNumber().startsWith(this.service.getTenantPrefix())
						&& this.room.isRoomExistForRoomExtNumber(m.getRoomExtNumber(), 0)
						|| !m.getRoomExtNumber().startsWith(this.service.getTenantPrefix())
								&& this.room.isRoomExistForRoomExtNumber(
										this.service.getTenantPrefix() + m.getRoomExtNumber(), 0)) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.extension.in.use", null, "", loc), 
									HtmlUtils.htmlEscape(m.getRoomExtNumber())));
					errors.add(fe);
					continue;
				}

				// get GroupID from GroupName
				m.setGroupName(groupName);
				Group g;
				try {
					g = this.group.getGroupByName(groupName);
				} catch (Exception ex) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.group.name.not.exist", null, "", loc),
									HtmlUtils.htmlEscape(groupName)));
					errors.add(fe);
					continue;
				}
				m.setGroupID(g.getGroupID());

				// get locationID from locationTag
				Location loca;
				try {
					loca = this.service.getLocationInTenantByTag(locationTag);
					if (loca == null) {
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.locationTag.not.reachable", null, "", loc),
										HtmlUtils.htmlEscape(locationTag)));
						errors.add(fe);
						continue;
					}
				} catch (Exception ex) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							MessageFormat.format(ms.getMessage("import.locationTag.not.reachable", null, "", loc),
									HtmlUtils.htmlEscape(locationTag)));
					errors.add(fe);
					continue;
				}
				m.setLocationID(loca.getLocationID());

				// get proxyID by proxyName
				// User can leave proxyName blank or set it as 'No Proxy'
				int proxyID = -1;
				if (proxyName.trim().length() == 0 || proxyName.trim().equalsIgnoreCase("No Proxy")) {
					proxyID = 0;
				} else {
					List<Proxy> proxyList = this.service.getProxies();
					for (Proxy p : proxyList) {
						if (p.getProxyName().equalsIgnoreCase(proxyName))
							proxyID = p.getProxyID();
					}
				}

				if (proxyID != -1) {
					m.setProxyID(proxyID);
				} else {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
							"Incorrect Proxy Name");
					errors.add(fe);
					continue;
				}

				// Set the ProxyID as zero, as this column is not used anymore
				// m.setProxyID(0);

				// Insert Member
				int autoMemberId = 0;
				try {
					autoMemberId = this.service.insertMember(m);
					if (autoMemberId <= 0) {
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.member.insert.error", null, "", loc),
										HtmlUtils.htmlEscape(m.getMemberName())));
						errors.add(fe);
						continue;
					}
				} catch (Exception e) {
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.member.insert.error", null, "", loc), 
									HtmlUtils.htmlEscape(m.getMemberName())));
					errors.add(fe);
					continue;
				}

				Room room = new Room();
				room.setMemberID(autoMemberId);
				room.setRoomDescription(m.getUsername() + "-Personal Room");
				room.setGroupID(g.getGroupID());
				room.setRoomEnabled(m.getActive());
				if (m.getRoomExtNumber().startsWith(this.service.getTenantPrefix())) {
					room.setRoomExtNumber(m.getRoomExtNumber());
				} else {
					room.setRoomExtNumber(this.service.getTenantPrefix() + m.getRoomExtNumber());
				}
				room.setRoomName(m.getUsername());
				room.setRoomTypeID(m.getRoomTypeID());
				room.setDisplayName(m.getMemberName());
				if (VendorUtils.isRoomsLockedByDefault()) {
					room.setRoomLocked(1);
				}
				// insert Room
				int autoRoomId;
				int tenantID = TenantContext.getTenantId();
				try {
					autoRoomId = this.room.insertRoom(room);
					if (autoRoomId <= 0) {
						try {
							this.service.deleteMember(tenantID, autoMemberId);
						} catch (AccessRestrictedException e) {
							// It is not necessary to do anything. Error will be
							// added by next line.
						}
						FieldError fe = new FieldError("ImportMembers",
								MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum),
								MessageFormat.format(ms.getMessage("import.room.insert.error", null, "", loc),
										room.getRoomName()));
						errors.add(fe);
						continue;
					}
				} catch (Exception ex) {
					try {
						this.service.deleteMember(tenantID, autoMemberId);
					} catch (AccessRestrictedException e) {
						// It is not necessary to do anything. Error will be
						// added by next line.
					}
					FieldError fe = new FieldError("ImportMembers",
							MessageFormat.format(ms.getMessage("line.0", null, "", loc), lineNum), MessageFormat.format(
									ms.getMessage("import.room.insert.error", null, "", loc), room.getRoomName()));
					errors.add(fe);
					continue;
				}

				// counting allowedSeats
				allowedSeats--;
				if (userType.equalsIgnoreCase(MEMBER_ROLE_EXECUTIVE)) {
					allowedExecSeats--;
				}
				if (userType.equalsIgnoreCase(MEMBER_ROLE_VIDYO_PANORAMA)) {
					allowedPanoramaSeats--;
				}

				userCreated++;
				// Don't send account creation email for imports.
				// Send New Account Notification email out
				/*
				 * if (newAccountEnabled &&
				 * system.isAuthenticationLocalForMemberRole(tenantID, roleID)
				 * && (this.emailService != null)) { try {
				 * sendNewAccountNotificationEmail(m.getEmailAddress(),
				 * m.getMemberName(), m.getUsername(), password, request,
				 * m.getLangID()); } catch (Exception anExp) { logger.error(
				 * "Exception whiel sending email "); } }
				 */
			}
			// reader.close();
		} catch (FileNotFoundException ex) {
			logger.error("User import failed.", ex);
			errors.add(new FieldError("csvparser", ms.getMessage("import.read.csv.error", null, "", loc),
					ex.getStackTrace().toString()));
		} finally {
			if (reader != null) {
				reader.close();
			}
			FileUtils.deleteQuietly(uploadedFile);
			FileUtils.deleteQuietly(decryptedFile);
		}

		model.put("userCreated", userCreated);

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/import_members_result_ajax", "model", model);
	}

	@RequestMapping(value = "/exportmembers.html", method = GET)
	public ModelAndView exportMembersHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

		model.put("nav", "members");

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		return new ModelAndView("admin/export_members_html", "model", model);
	}

	@RequestMapping(value = "/exportmembers.ajax", method = POST)
	public ModelAndView doExportMembersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String format = ServletRequestUtils.getStringParameter(request, "fileformat", "csv");
		String password = ServletRequestUtils.getStringParameter(request, "password", "test");

		response.reset();
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setHeader("Content-Description", "File Transfer");
		response.setCharacterEncoding("UTF-8");

		MemberFilter filter = new MemberFilter();
		filter.setLimit(Integer.MAX_VALUE); // Set to retrieve all users in the tenant.

		List<Member> list = this.service.getMembers(filter);

		final String csvFile = getUploadTempDir() + "UsersExport.csv";
		File file = new File(csvFile);
		FileOutputStream fileStream = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
		byte[] bom = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF };
		fileStream.write(bom); // BOM for UTF-*
		
		CsvWriter csvwriter = new CsvWriter(writer, ',');
		csvwriter.setTextQualifier('"');
		csvwriter.setUseTextQualifier(true);
		csvwriter.setEscapeMode(CsvWriter.ESCAPE_MODE_BACKSLASH);

		//writer.write("\ufeef");
		//writer.write("\ufebb");
		//writer.write("\ufebf");

		try {

			if (format.equalsIgnoreCase("veb")) {
				csvwriter.writeRecord(MEMBER_EXPORT_CSV_HEADER_HASHED_PASSWORDS);
			} else {
				csvwriter.writeRecord(MEMBER_EXPORT_CSV_HEADER_TEXT_PASSWORDS);
			}
			for (Member record : list) {
				if (format.equalsIgnoreCase("csv")) {
					record.setPassword("");
				}
				logger.info("Member export record: " + Arrays.toString(record.toArray()));
				csvwriter.writeRecord(record.toArray());
			}
			writer.flush();
		} catch (IOException ioe) {
			logger.error("IOException during member csv export: " + ioe.getMessage());
		} finally {
			writer.flush();
			writer.close();
			csvwriter.flush();
			csvwriter.close();
		}

		if (format.equalsIgnoreCase("veb")) {

			try {
				String tarGzCsv = csvFile + ".tar.gz";
				CompressionUtils.targzip(csvFile, tarGzCsv, true);
				SecurityUtils.createSecureArchive(password, getUploadTempDir(), tarGzCsv,
						getUploadTempDir() + "UsersExport.veb");
				File exportFile = new File(getUploadTempDir() + "UsersExport.veb");
				if (exportFile.exists()) {
					response.setHeader("Content-Disposition", "attachment; filename=\"UsersExport.veb\"");
					response.addHeader("Content-Length", Long.toString(exportFile.length()));
					response.setContentType("application/octet-stream");

					try (InputStream in = new FileInputStream(exportFile.getAbsolutePath());
							ServletOutputStream out = response.getOutputStream();) {
						IOUtils.copy(in, out);
						out.flush();
					}
				}
				FileUtils.deleteQuietly(exportFile);
			} catch (Exception e) {
				logger.error("Exception while creating .veb of UserExports.csv: " + e.getMessage());
			}

		} else if (format.equalsIgnoreCase("csv")) {
			File exportFile = new File(csvFile);
			if (exportFile.exists()) {
				response.setHeader("Content-Disposition", "attachment; filename=\"UsersExport.csv\"");
				response.addHeader("Content-Length", Long.toString(exportFile.length()));
				response.setContentType("text/csv; charset=UTF-8");

				logger.info("Export members content: " + Files.toString(exportFile, StandardCharsets.UTF_8));
				
				try (InputStream in = new FileInputStream(csvFile);
						ServletOutputStream out = response.getOutputStream();) {
					IOUtils.copy(in, out);
					out.flush();
				}
			}
		}

		FileUtils.deleteQuietly(new File(csvFile));

		return null;
	}

	@RequestMapping(value = "/proxies.ajax", method = GET)
	public ModelAndView getProxiesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Long num = this.service.getCountProxies();
		model.put("num", num);
		List<Proxy> list = this.service.getProxies();
		model.put("list", list);

		return new ModelAndView("ajax/proxies_ajax", "model", model);
	}

	@RequestMapping(value = "/locationtags.ajax", method = GET)
	public ModelAndView getLocationTagsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		Long num = this.service.getCountLocationTags();
		model.put("num", num);
		List<Location> list = this.service.getLocationTags();
		model.put("list", list);

		return new ModelAndView("ajax/locations_ajax", "model", model);
	}

	@RequestMapping(value = "/checkLicensesQuantity.ajax", method = RequestMethod.GET)
	public ModelAndView validateLicenseAjax(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Locale loc = LocaleContextHolder.getLocale();

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

		int memberID = ServletRequestUtils.getIntParameter(request, "memberID", 0);
		int futureRoleId = ServletRequestUtils.getIntParameter(request, "role", 3);
		MemberRoleEnum futureRole = MemberRoleEnum.get(futureRoleId);

		Member member = null;

		if (memberID != 0) {
			member = getMember(memberID);
			if (member == null) {
				return buildInvalidMemberResponse(loc, model, errors);
			}
		}

		if (futureRole == MemberRoleEnum.VIDYO_PANORAMA) {
			MemberFilter filter = new MemberFilter();
			filter.setLimit(Integer.MAX_VALUE);
			filter.setType(MEMBER_ROLE_VIDYO_PANORAMA);
			Long numPanorama = this.service.getCountMembers(filter);

			if (numPanorama >= this.service.getLicensedPanorama()) {
				FieldError fe = new FieldError("roleID", "roleID",
						ms.getMessage("cannot.add.member.panorama.license.exceeded", null, "", loc));
				errors.add(fe);
			}
		} else if(futureRole == MemberRoleEnum.EXECUTIVE) {
			MemberFilter filter = new MemberFilter();
			filter.setLimit(Integer.MAX_VALUE);
			filter.setType(MEMBER_ROLE_EXECUTIVE);
			Long numExecutive = this.service.getCountMembers(filter);

			if (numExecutive >= this.service.getLicensedExecutive()) {
				FieldError fe = new FieldError("roleID", "roleID",
						ms.getMessage("cannot.add.member.executive.license.exceeded", null, "", loc));
				errors.add(fe);
			}
		} else {
			if (memberID == 0 ||  member.getRoleID() != MemberRoleEnum.ADMIN.getMemberRoleID()
					&& member.getRoleID() != MemberRoleEnum.OPERATOR.getMemberRoleID()
					&& member.getRoleID() != MemberRoleEnum.NORMAL.getMemberRoleID()) {
				// licensing
				long allowedSeats = this.license.getAllowedSeats();
				model.put("canAddUser", allowedSeats > 0);

				if (allowedSeats < 1) {
					FieldError fe = new FieldError("roleID", "roleID",
							ms.getMessage("cannot.add.member.seat.license.exceeded", null, "", loc));
					errors.add(fe);
				}
			}
		}
		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/json_result_ajax", "model", model);
	}

	private ModelAndView buildInvalidMemberResponse(Locale loc, Map<String, Object> model, List<FieldError> errors) {
		FieldError fe = new FieldError("roleID", "roleID", ms.getMessage("invalid.member", null, "", loc));
		errors.add(fe);
		model.put("fields", errors);
		model.put("success", Boolean.FALSE);
		return new ModelAndView("ajax/json_result_ajax", "model", model);
	}

	@RequestMapping(value = "/uploadUserImage.ajax", method = RequestMethod.POST)
	public ModelAndView uploadUserImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
				MultipartHttpServletRequest.class);
		MultipartFile multipartFile = multipartRequest.getFile("thumbnailImagePreview");
		int memberId = ServletRequestUtils.getIntParameter(request, "memberId", 0);
		if(memberId!=0){
		if (multipartFile.getContentType() != null && (multipartFile.getContentType().equalsIgnoreCase("image/jpg")
				|| multipartFile.getContentType().equalsIgnoreCase("image/jpeg")
				|| multipartFile.getContentType().equalsIgnoreCase("image/png"))) {
			try {
				TenantConfiguration tenantConfiguration = this.tenantService
						.getTenantConfiguration(TenantContext.getTenantId());
				if (tenantConfiguration.getUserImage() == 1) {
					Configuration conf = system.getConfiguration("MAX_USER_IMAGE_SIZE_KB");

					int maxAllowedImageSize = Integer.valueOf(conf.getConfigurationValue());
					if (multipartFile.getSize() <= (maxAllowedImageSize * 1024)) {
						byte thumbNail[] = multipartFile.getBytes();
						model.put("thumbnailImageB64", new String(Base64.getEncoder().encode(thumbNail)));
						service.uploadUserImage(thumbNail, memberId, TenantContext.getTenantId());
					} else {
						FieldError fe = new FieldError("image", "image",
								"Thumbnail image size is bigger than what it is allowed by super. Allowed size in KB - "
										+ maxAllowedImageSize);
						errors.add(fe);
					}

				} else {
					FieldError fe = new FieldError("image", "image", "Thumbnail image is not enabled by Admin");
					errors.add(fe);
				}
			} catch (Exception e) {

				logger.error("Uploading user image failed for tenantId " + TenantContext.getTenantId()
						+ "and member Id " + memberId + " due to ", e);
				FieldError fe = new FieldError("image", "image", "Uploading user image failed");
				errors.add(fe);
			}

		} else {
			FieldError fe = new FieldError("image", "image", "Invalid Image type. Allowed types are jpg,jpeg and png");
			errors.add(fe);

		}
		}else{
			FieldError fe = new FieldError("image", "image", "Member Id is coming as 0");
			errors.add(fe);
		}
		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/member_user_image_ajax", "model", model);

	}

	@RequestMapping(value = "/deleteUserImage.ajax", method = POST)
	public ModelAndView deleteUserImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Locale loc = LocaleContextHolder.getLocale();
		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		int memberId = ServletRequestUtils.getIntParameter(request, "memberId", 0);
		if (memberId != 0) {
			try{				
				this.service.deleteUserImage(TenantContext.getTenantId(), memberId);
			}catch(Exception e){
				FieldError fe = new FieldError("image", "image", "Delete User image failed");
				errors.add(fe);
			}
			
			model.put("thumbnailImageB64",
					this.service.getDefaultThumbNailImage(TenantContext.getTenantId(), memberId));
		} else {
			FieldError fe = new FieldError("image", "image", "Member Id is 0");
			errors.add(fe);
		}
		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/member_user_image_ajax", "model", model);

	}

	private Member getMember(int memberID) {
		Member member = this.service.getMember(memberID);

		int tenantId = TenantContext.getTenantId();

		if (member != null && member.getTenantID() != tenantId) {
			member = null;
		}

		return member;
	}

	@RequestMapping(value = "/checkmembername.ajax", method = RequestMethod.POST)
	public ModelAndView checkMemberNameAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		String username = ServletRequestUtils.getStringParameter(request, "username", null);

		if (username == null) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		boolean memberExists = service.isMemberExistForUserName(username, 0);
		model.put("success", memberExists);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	public ModelAndView checkRoomExtAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		String roomExt = ServletRequestUtils.getStringParameter(request, "roomext", null);

		if (roomExt == null) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		boolean extExists = room.isRoomExistForRoomExtNumber(roomExt, 0);
		model.put("success", extExists);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	public void setUploadTempDir(String uploadTempDir) {
		this.uploadTempDir = uploadTempDir;
	}

	public String getUploadTempDir() {
		return uploadTempDir;
	}
}