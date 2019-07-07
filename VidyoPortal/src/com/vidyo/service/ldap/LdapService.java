package com.vidyo.service.ldap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.utils.ImageUtils;


public class LdapService {
	protected static final Logger logger = LoggerFactory.getLogger(LdapService.class);
	
	
	@Value("${upload.thumbnail.dir}")
	private String thumbNailLocation;
	// /opt/vidyo/portal2/thumbnail/	
	
	@Autowired 	private ISystemService system;
	@Autowired private ITenantService tenantService;
	@Autowired private IMemberService memberService;
	
	public Attributes getLdapUsersAttributes(String name, LdapAuthentication ldapAuth, DirContext ctx,
			String[] returnedAttrs) throws Exception {
		Attributes rc = null;
		String dn = null;
		LdapName baseName = null;
		if (!ldapAuth.getLdapbase().equalsIgnoreCase("")) {
			baseName = LdapUtils.newLdapName(ldapAuth.getLdapbase());
		}
		String filter = ldapAuth.getLdapfilter();
		if (filter.contains("<>")) {
			filter = filter.replace("<>".subSequence(0, "<>".length()), name);
		}
		logger.debug("the filter used for ldap query " + filter);
		logger.debug("Attributes passed to the ldap query " + Arrays.toString(returnedAttrs));
		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(Integer.valueOf(ldapAuth.getLdapscope()));
		ctls.setReturningAttributes(returnedAttrs);
		NamingEnumeration<SearchResult> result = ctx.search(baseName, filter, ctls);
		// copying the existing code. not sure why there is a loop.
		while (result !=null && result.hasMoreElements()) {
			SearchResult searchResult = result.nextElement();
			if(searchResult!=null){
				dn = searchResult.getNameInNamespace();
				rc = searchResult.getAttributes();
			}

		}
		if (dn == null || dn.trim().isEmpty()) {
			throw new BadCredentialsException(
					"LDAP didn't return the user dn -" + name + " TenantId -" + TenantContext.getTenantId());
		}
		return rc;

	}

	/**
	 * This is an asynchronous call. If there is an image already exist in the file system, it will compare the size,and if there is any discrepancy ,then method will write the image to the FS.
	 * 
	 * 
	 * @param userName
	 * @param tenantId
	 * @param member
	 */
	@Async
	public void writeImagetoFileSystemAsync(String userId, Integer tenantId, Member member) {
		try {
			if (!member.isUserImageUploaded()) {
				TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(tenantId);
				// keeping expensive call as second condition
				if (tenantConfiguration.getUserImage() == 1) {

					Path theExpectedFileName = Paths.get(thumbNailLocation + "/" + tenantId + "/" + userId);
					Path thumbNailFolder = Paths.get(thumbNailLocation + "/" + tenantId);
					Configuration conf = system.getConfiguration("MAX_USER_IMAGE_SIZE_KB");

					int maxAllowedImageSize = Integer.valueOf(conf.getConfigurationValue());
					if (member.getThumbNailImage().length <= maxAllowedImageSize * 1024) {
						ImageUtils.writeImageOperation(thumbNailLocation, member.getThumbNailImage(), thumbNailFolder,
								theExpectedFileName);
						memberService.updateMemberThumbnailTimeStamp(Integer.parseInt(userId), Calendar.getInstance().getTime());
					} else {
						logger.error(
								"Thumbnail image size is bigger than what it is allowed by super. Allowed size in KB is "
										+ maxAllowedImageSize + " and the file size coming from ldap in KB is "
										+ (member.getThumbNailImage().length / 1024));
					}
				} else {
					logger.debug("Thumbnail fn is not enabled by admin/super for the tenant id " + tenantId);
				}
			}else{
				logger.debug("Member "+member.getMemberName() +" already uploaded the image so skipping image from external system.");
			}
			
		} catch (Exception e) {
			logger.error("Error occurred durring writing image to the file system for the user " + userId,e);
		}
	}
	
		
	

}
