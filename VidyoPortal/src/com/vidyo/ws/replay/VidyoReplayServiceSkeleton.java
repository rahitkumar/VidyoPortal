/**
 * VidyoReplayServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package com.vidyo.ws.replay;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.DaoAuthenticationProvider;
import com.vidyo.framework.security.authentication.UserDetailsService;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.framework.security.utils.SHA1;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.utils.LangUtils;

/**
 * VidyoReplayServiceSkeleton java skeleton for the axisService
 */
public class VidyoReplayServiceSkeleton implements
		VidyoReplayServiceSkeletonInterface {

	/**
	 * Logger for this class and subclasses
	 */
	protected final Logger logger = LoggerFactory.getLogger(VidyoReplayServiceSkeleton.class.getName());

	private UserDetailsService userDetails;
	private IUserService userService;
	private ITenantService tenantService;
	private IMemberService member;
	private ISystemService system;
	private ReloadableResourceBundleMessageSource ms;
	private String webappsDir;

	// loc is a non IoC field
	private Locale loc = Locale.getDefault();

	public void setUserDetails(UserDetailsService userDetails) {
		this.userDetails = userDetails;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	public void setMember(IMemberService member) {
		this.member = member;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	public void setMs(ReloadableResourceBundleMessageSource msgSrc) {
		this.ms = msgSrc;
	}

	/**
	 * @param webappsDir the webappsDir to set
	 */
	public void setWebappsDir(String webappsDir) {
		this.webappsDir = webappsDir;
	}

	/**
	 * Auto generated method signature Retrive user details
	 *
	 * @param getUserByUsernameRequest :
	 * @throws InvalidArgumentFaultException :
	 * @throws UserNotFoundFaultException	:
	 * @throws GeneralFaultException		 :
	 */
	public com.vidyo.ws.replay.GetUserByUsernameResponse getUserByUsername(com.vidyo.ws.replay.GetUserByUsernameRequest getUserByUsernameRequest)
			throws InvalidArgumentFaultException, UserNotFoundFaultException, GeneralFaultException
	{
		GetUserByUsernameResponse resp = new GetUserByUsernameResponse();

		String username = getUserByUsernameRequest.getUsername();
		if (username == null || username.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong username");
		}

		String password = getUserByUsernameRequest.getPassword();
		if (password == null || password.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong password");
		}

		String tenantReplayHost = getUserByUsernameRequest.getTenanthost();
		if (tenantReplayHost == null || tenantReplayHost.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong tenanthost");
		}

		Tenant tenant;
		try {
			tenant = this.tenantService.getTenantByReplayURL(tenantReplayHost);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Wrong tenanthost");
		}
		if (tenant == null) {
			throw new InvalidArgumentFaultException("Wrong tenanthost");
		}

		// Hack for setting the Tenant Id passed from the request - This will
		// replace the original tenantId set by the TenantFilter.
		TenantContext.setTenantId(tenant.getTenantID());
		
		VidyoUserDetails ud;
		String encPassword;
		try {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
			String idForEncode = "pbkdf2";
//			Map<String,PasswordEncoder> encoders = new HashMap<>();
//			encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//			PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
			PasswordEncoder passwordEncoder =  new MessageDigestPasswordEncoder("SHA-1");
			DaoAuthenticationProvider authService = new DaoAuthenticationProvider();			
			authService.setUserDetailsService(this.userDetails);
			authService.setPasswordEncoder(passwordEncoder);
			authService.setSystem(this.system);
			// try to authenticate
			authService.authenticate(auth);
			// if OK - do encription of provided password and return back to client
			encPassword = passwordEncoder.encode(password);
			ud = (VidyoUserDetails) this.userDetails.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new UserNotFoundFaultException(e.getMessage());
		} catch (AuthenticationException e) {
			throw new UserNotFoundFaultException(e.getMessage());
		} catch (DataAccessException e) {
			throw new GeneralFaultException(e.getMessage());
		}

		resp.setDisplayname(ud.getMemberName());
		resp.setUsername(ud.getUsername());
		resp.setPassword(encPassword);
		resp.setEnable(ud.isEnabled());
		Collection<? extends GrantedAuthority> roles = ud.getAuthorities();
		resp.setUserrole(roles.toArray(new GrantedAuthority[roles.size()])[0].getAuthority());

		if (ud.getLangId() == 1) {
			resp.setLanguage(Language_type0.en);
		} else if (ud.getLangId() == 2) {
			resp.setLanguage(Language_type0.fr);
		} else if (ud.getLangId() == 3) {
			resp.setLanguage(Language_type0.ja);
		} else if (ud.getLangId() == 4) {
			resp.setLanguage(Language_type0.zh_CN);
		} else if (ud.getLangId() == 5) {
			resp.setLanguage(Language_type0.es);
		} else if (ud.getLangId() == 6) {
			resp.setLanguage(Language_type0.it);
		} else if (ud.getLangId() == 7) {
			resp.setLanguage(Language_type0.de);
		} else if (ud.getLangId() == 8) {
			resp.setLanguage(Language_type0.ko);
		} else if (ud.getLangId() == 9) {
			resp.setLanguage(Language_type0.pt);
		} else if (ud.getLangId() == 10) {
			resp.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(ud.getTenantID()).getLangCode()));
		} else if (ud.getLangId() == 11) {
			resp.setLanguage(Language_type0.fi);
		} else if (ud.getLangId() == 12) {
			resp.setLanguage(Language_type0.pl);
		} else if (ud.getLangId() == 13) {
			resp.setLanguage(Language_type0.zh_TW);
		} else if (ud.getLangId() == 14) {
			resp.setLanguage(Language_type0.th);
		} else if (ud.getLangId() == 15) {
			resp.setLanguage(Language_type0.ru);
		} else if (ud.getLangId() == 16) {
            resp.setLanguage(Language_type0.tr);
        }
		resp.setEmailAddress(ud.getEmailAddress());
		resp.setTenantName(tenantService.getTenant(ud.getTenantID()).getTenantName());

		resp.setTenantName(tenant.getTenantName());

		return resp;
	}

	/**
	 * Auto generated method signature Retrive user details
	 *
	 * @param getUserByTokenRequest :
	 * @throws InvalidArgumentFaultException :
	 * @throws UserNotFoundFaultException	:
	 * @throws GeneralFaultException		 :
	 */
	public com.vidyo.ws.replay.GetUserByTokenResponse getUserByToken(com.vidyo.ws.replay.GetUserByTokenRequest getUserByTokenRequest)
			throws InvalidArgumentFaultException, UserNotFoundFaultException, GeneralFaultException
	{
		GetUserByTokenResponse resp = new GetUserByTokenResponse();

		//String PAK = getUserByTokenRequest.getToken();
		//if (PAK == null || PAK.equalsIgnoreCase("")) {
		//	throw new InvalidArgumentFaultException("Wrong token");
		//}
		String BAK = getUserByTokenRequest.getToken();
		if (BAK == null || BAK.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong token");
		}

		//int memberID = this.userService.getMemberIDForPAK(PAK);
		Integer memberID = this.userService.getMemberIDForBAK(BAK, MemberBAKType.VidyoReplayLibrary);
		if (memberID == null) {
			throw new InvalidArgumentFaultException("Wrong token");
		}

		// delete BAK after usage
		userService.deleteMemberBAK(BAK);

		Member member = this.member.getMember(memberID);

		UserDetails ud;
		String encPassword;
		try {
/*
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
			ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
			com.vidyo.framework.security.authentication.DaoAuthenticationProvider authService = new com.vidyo.framework.security.authentication.DaoAuthenticationProvider();
			authService.setUserDetailsService(this.userDetails);
			authService.setPasswordEncoder(shaPasswordEncoder);
			authService.setSystem(this.system);
			// try to authenticate
			authService.authenticate(auth);
			// if OK - do encription of provided password and return back to client
			encPassword = shaPasswordEncoder.encodePassword(password, null);
*/
			ud = this.userDetails.loadUserByUsername(member.getUsername(), member.getTenantID());
		} catch (UsernameNotFoundException e) {
			throw new UserNotFoundFaultException(e.getMessage());
		} catch (DataAccessException e) {
			throw new GeneralFaultException(e.getMessage());
		}
		resp.setDisplayname(member.getMemberName());
		resp.setEmailAddress(member.getEmailAddress());
		resp.setUsername(ud.getUsername());
		resp.setPassword(ud.getPassword());
		resp.setEnable(ud.isEnabled());
		Collection<? extends GrantedAuthority> roles = ud.getAuthorities();
		resp.setUserrole(roles.toArray(new GrantedAuthority[roles.size()])[0].getAuthority());

		if (member.getLangID() == 1) {
			resp.setLanguage(Language_type0.en);
		} else if (member.getLangID() == 2) {
			resp.setLanguage(Language_type0.fr);
		} else if (member.getLangID() == 3) {
			resp.setLanguage(Language_type0.ja);
		} else if (member.getLangID() == 4) {
			resp.setLanguage(Language_type0.zh_CN);
		} else if (member.getLangID() == 5) {
			resp.setLanguage(Language_type0.es);
		} else if (member.getLangID() == 6) {
			resp.setLanguage(Language_type0.it);
		} else if (member.getLangID() == 7) {
			resp.setLanguage(Language_type0.de);
		} else if (member.getLangID() == 8) {
			resp.setLanguage(Language_type0.ko);
		} else if (member.getLangID() == 9) {
			resp.setLanguage(Language_type0.pt);
		} else if (member.getLangID() == 10) {
			resp.setLanguage(Language_type0.Factory.fromValue(this.system.getSystemLang(member.getTenantID()).getLangCode()));
		} else if (member.getLangID() == 11) {
			resp.setLanguage(Language_type0.fi);
		} else if (member.getLangID() == 12) {
			resp.setLanguage(Language_type0.pl);
		} else if (member.getLangID() == 13) {
			resp.setLanguage(Language_type0.zh_TW);
		} else if (member.getLangID() == 14) {
			resp.setLanguage(Language_type0.th);
		} else if (member.getLangID() == 15) {
			resp.setLanguage(Language_type0.ru);
		} else if (member.getLangID() == 16) {
            resp.setLanguage(Language_type0.tr);
        }

		resp.setTenantName(member.getTenantName());

		return resp;
	}

	/**
	 * Auto generated method signature Retrive tenant name
	 *
	 * @param getTenantByHostRequest :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException		 :
	 * @throws TenantNotFoundFaultException  :
	 */
	public com.vidyo.ws.replay.GetTenantByHostResponse getTenantByHost(com.vidyo.ws.replay.GetTenantByHostRequest getTenantByHostRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, TenantNotFoundFaultException
	{
		GetTenantByHostResponse resp = new GetTenantByHostResponse();

		String tenantReplayHost = getTenantByHostRequest.getTenanthost();
		if (tenantReplayHost == null || tenantReplayHost.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong tenanthost");
		}

		Tenant tenant;
		try {
			tenant = this.tenantService.getTenantByReplayURL(tenantReplayHost);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Wrong tenanthost");
		}
		if (tenant == null) {
			throw new TenantNotFoundFaultException("Tenant for tenanthost = " + tenantReplayHost + " not found");
		}

		resp.setTenantName(tenant.getTenantName());

		Language lang = this.system.getSystemLang(tenant.getTenantID());
		resp.setLanguage(Language_type0.Factory.fromValue(lang.getLangCode()));

		return resp;
	}

	/**
	 * Auto generated method signature Retrive tenant's Terms Of Service content
	 *
	 * @param getTermsOfServiceForTenantRequest
	 *         :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException		 :
	 * @throws TenantNotFoundFaultException  :
	 */
	public com.vidyo.ws.replay.GetTermsOfServiceForTenantResponse getTermsOfServiceForTenant(com.vidyo.ws.replay.GetTermsOfServiceForTenantRequest getTermsOfServiceForTenantRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, TenantNotFoundFaultException
	{
		GetTermsOfServiceForTenantResponse resp = new GetTermsOfServiceForTenantResponse();

		String tenantName = getTermsOfServiceForTenantRequest.getTenantName();
		if (tenantName == null || tenantName.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}

		Tenant tenant;
		try {
			tenant = this.tenantService.getTenant(tenantName);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}
		if (tenant == null) {
			throw new TenantNotFoundFaultException("Tenant for tenantName = "
					+ tenantName + " not found");
		}

		Language_type0 lang = getTermsOfServiceForTenantRequest.getLanguage();
		loc = LangUtils.getLocaleByLangID(LangUtils.getLangId(lang.getValue()));

		String terms = ms.getMessage("eula.full", null, "", loc);

		DataHandler content = new DataHandler(terms,"text/plain; charset=UTF-8");

		resp.setContent(content);
		return resp;
	}

	/**
	 * Auto generated method signature Retrive tenant's Contact Us content
	 *
	 * @param getContactUsForTenantRequest :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException		 :
	 * @throws TenantNotFoundFaultException  :
	 */
	public com.vidyo.ws.replay.GetContactUsForTenantResponse getContactUsForTenant(com.vidyo.ws.replay.GetContactUsForTenantRequest getContactUsForTenantRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, TenantNotFoundFaultException
	{
		GetContactUsForTenantResponse resp = new GetContactUsForTenantResponse();

		String tenantName = getContactUsForTenantRequest.getTenantName();
		if (tenantName == null || tenantName.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}

		Tenant tenant;
		try {
			tenant = this.tenantService.getTenant(tenantName);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}
		if (tenant == null) {
			throw new TenantNotFoundFaultException("Tenant for tenantName = "
					+ tenantName + " not found");
		}

		Language_type0 lang = getContactUsForTenantRequest.getLanguage();
		loc = LangUtils.getLocaleByLangID(LangUtils.getLangId(lang.getValue()));

		String contact_info = this.system.getConfigValue(tenant.getTenantID(), "ContactInfo");

		if (contact_info.length() == 0) {
			contact_info = this.system.getSuperContactInfo();
		}

		if (contact_info.length() == 0) {
			StringBuffer text = new StringBuffer();
			text.append("<h1>")
					.append(ms.getMessage("contact_us", null, "", loc))
					.append("</h1>").append("<br/><br/>");
			text.append("<p>");
			text.append(ms.getMessage("address_1", null, "", loc)).append(
					"<br/>");
			text.append(ms.getMessage("address_2", null, "", loc)).append(
					"<br/>");
			text.append(ms.getMessage("address_3", null, "", loc)).append(
					"<br/>");
			text.append(ms.getMessage("address_4", null, "", loc)).append(
					"<br/>");
			text.append("<br/>");
			text.append(ms.getMessage("phone", null, "", loc)).append("<br/>");
			text.append(ms.getMessage("fax", null, "", loc)).append("<br/>");
			text.append(ms.getMessage("email_info", null, "", loc)).append(
					"<br/>");
			text.append(ms.getMessage("email_support", null, "", loc)).append(
					"<br/>");
			text.append("</p>");

			contact_info = text.toString();
		}

		DataHandler content = new DataHandler(contact_info, "text/plain; charset=UTF-8");

		resp.setContent(content);
		return resp;
	}

	/**
	 * Auto generated method signature Retrive tenant's About Us content
	 *
	 * @param getAboutUsForTenantRequest :
	 * @throws InvalidArgumentFaultException :
	 * @throws GeneralFaultException		 :
	 * @throws TenantNotFoundFaultException  :
	 */
	public com.vidyo.ws.replay.GetAboutUsForTenantResponse getAboutUsForTenant(com.vidyo.ws.replay.GetAboutUsForTenantRequest getAboutUsForTenantRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, TenantNotFoundFaultException
	{
		GetAboutUsForTenantResponse resp = new GetAboutUsForTenantResponse();

		String tenantName = getAboutUsForTenantRequest.getTenantName();
		if (tenantName == null || tenantName.equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}

		Tenant tenant;
		try {
			tenant = this.tenantService.getTenant(tenantName);
		} catch (Exception e) {
			throw new InvalidArgumentFaultException("Wrong tenantName");
		}
		if (tenant == null) {
			throw new TenantNotFoundFaultException("Tenant for tenantName = " + tenantName + " not found");
		}

		Language_type0 lang = getAboutUsForTenantRequest.getLanguage();

		loc = LangUtils.getLocaleByLangID(LangUtils.getLangId(lang.getValue()));

		String about_us = this.system.getConfigValue(tenant.getTenantID(), "AboutInfo");
		if (about_us.length() == 0) {
			about_us = this.system.getSuperAboutInfo();
		}

		if (about_us.length() == 0) {
			StringBuffer text = new StringBuffer();
			text.append("<h1>")
					.append(ms.getMessage("about_us", null, "", loc))
					.append("</h1>").append("<br/><br/>");
			text.append(ms.getMessage("about.us.full", null, "", loc));

			about_us = text.toString();
		}

		DataHandler content = new DataHandler(about_us, "text/plain; charset=UTF-8");

		resp.setContent(content);
		return resp;
	}

	@Override
	public GetLogoForTenantResponse getLogoForTenant(GetLogoForTenantRequest getLogoForTenantRequest)
			throws InvalidArgumentFaultException, GeneralFaultException, TenantNotFoundFaultException
	{
		GetLogoForTenantResponse getLogoForTenantResponse = new GetLogoForTenantResponse();
		if (getLogoForTenantRequest.getReplayTenantName() == null
				|| getLogoForTenantRequest.getReplayTenantName().equalsIgnoreCase("")) {
			throw new InvalidArgumentFaultException("Invalid ReplayTenantName");
		}

		Tenant tenant = tenantService.getTenantByReplayURL(getLogoForTenantRequest.getReplayTenantName());

		if (tenant == null) {
			throw new InvalidArgumentFaultException("Invalid ReplayTenantName");
		}

		String logoName = system.getCustomizedLogoName(tenant.getTenantID());
		DataHandler content = null;

		if (logoName == null || logoName.equalsIgnoreCase("")) {
			logoName = system.getCustomizedDefaultUserPortalLogoName();
		}

		if (logoName == null || logoName.equalsIgnoreCase("")) {
			content = new DataHandler("Default", "text/plain; charset=UTF-8");
			getLogoForTenantResponse.setContent(content);
			return getLogoForTenantResponse;
		}

		logoName = logoName.replace("..", webappsDir);
		File file = new File(logoName);
		if (!file.exists()) {
			logger.error(logoName + " File is not present, returning the Default file");
			content = new DataHandler("Default", "text/plain; charset=UTF-8");
			getLogoForTenantResponse.setContent(content);
			return getLogoForTenantResponse;
		}

		DataSource dataSource = new FileDataSource(file);
		content = new DataHandler(dataSource);
		getLogoForTenantResponse.setContent(content);
		return getLogoForTenantResponse;
	}

}