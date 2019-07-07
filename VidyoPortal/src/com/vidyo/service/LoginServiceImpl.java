package com.vidyo.service;

import com.vidyo.bo.ForgotPassword;
import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.db.ILoginDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.email.EmailService;
import com.vidyo.service.exceptions.EmailAddressNotFoundException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.NotificationEmailAddressNotConfiguredException;
import com.vidyo.utils.LangUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vidyo.utils.SecureRandomUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;

public class LoginServiceImpl implements ILoginService {

    private ILoginDao dao;
    private IMemberDao memberDao;
    private EmailService emailService;
    private ISystemService system;
    private ReloadableResourceBundleMessageSource ms;

    private Locale loc = Locale.getDefault();
    
    public void setDao(ILoginDao dao) {
        this.dao = dao;
    }
    
    public void setMemberDao(IMemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void setSystem(ISystemService system) {
        this.system = system;
    }
    
    public void setMs(ReloadableResourceBundleMessageSource msgSrc){
        this.ms = msgSrc;
    }

	public int forgotPassword(String email, HttpServletRequest request) {
        int rc = 0;

        String fromEmail = null;
        
        List<String> userTypes = new ArrayList<String>();
        
        List<ForgotPassword> list = null;
        if(request.getContextPath().equalsIgnoreCase("/super")) {
        	fromEmail = this.system.getSuperNotificationFromEmailAddress();
        	
        	if(fromEmail == null || fromEmail.equals("")) {
            	//Return error code
            	return -100;
            }
        	
        	userTypes.add("Super");
        	
        	list = this.dao.getMembersForEmail(email, 1, userTypes);
        } else {
        	fromEmail = this.system.getNotificationFromEmailAddress();
        	
        	if(fromEmail == null || fromEmail.equals("")) {
            	//Return error code
            	return -100;
            }
        	
        	if(request.getContextPath().equalsIgnoreCase("/admin")) {
        		userTypes.add("Admin");
            	userTypes.add("Operator");
        	} else {
        		//This condition wont happen
        		userTypes.add("Normal");
        	}
        	
        	list = this.dao.getMembersForEmail(email, this.system.getTenantId(), userTypes);
        }
        
        if(list.isEmpty()) {
        	return -101;
        }
        
        for (ForgotPassword member : list) {
        	int langID = member.getLangID();
        	loc = LangUtils.getLocaleByLangID(langID);
        	
            // generate a new passKey for reference
            String passKey = SecureRandomUtils.generateHashKey(12);
            try {
                passKey = PasswordHash.createHash(passKey);
            } catch (Exception e) {
                // just ignore it
            }
            this.dao.updateMemberPassKey(member.getMemberID(), passKey);

            SimpleMailMessage message = new SimpleMailMessage();

            StringBuffer text = new StringBuffer();
            text.append(MessageFormat.format(ms.getMessage("dear.member", null, "", loc), member.getUsername()));
            text.append("\r");
            text.append(ms.getMessage("click.on.link.to.reset.password", null, "", loc));
            text.append("\r");
            String link = request.getScheme()+"://"+request.getHeader("host")+request.getContextPath()+"/resetpassword.html?key="+passKey;
            text.append(link);
            text.append("\r\r\r-\r");
            text.append(ms.getMessage("support.team", null, "", loc));

            if (fromEmail != null && !fromEmail.equals("")) {
                message.setFrom(fromEmail );
            } else {
                message.setFrom("admin@vidyo.com");
            }
            message.setTo(member.getEmailAddress());
            message.setSubject(ms.getMessage("your.password.reset.key", null, "", loc));
            message.setText(text.toString());

	        this.emailService.sendEmailAsynchronous(message);
            rc++;
        }

        return rc;
    }
    
    private String normalizeEmailAddressDisplayName(String emailAddressDisplayName) {
    	if(emailAddressDisplayName == null || emailAddressDisplayName.trim().length() == 0) {
    		return emailAddressDisplayName;
    	}
    	
    	return emailAddressDisplayName.
    			replace("[", "\"[\"").
    			replace("]", "\"]\"").
    			replace(":", "\":\"").
    			replace(";", "\";\"").
    			replace(",", "\",\"");
    }
    
    /*public static void main(String[] args) {
		LoginServiceImpl impl = new LoginServiceImpl();
		String val = impl.normalizeEmailAddressDisplayName("test thirteen, adm (GOBHI )," + " <a@g.com>");

		 JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		 SimpleMailMessage message = new SimpleMailMessage();

         StringBuffer text = new StringBuffer();
       
             message.setFrom("admin@vidyo.com");

         message.setTo("test thirteen adm (GOBHI) <a@g.com>");
         message.setSubject("sub");
         message.setText(text.toString());
		 javaMailSenderImpl.send(message);
	    
	}*/

    public int resetPassword(String passKey, HttpServletRequest request) {
        int rc = 0;

//        String fromEmail = this.system.getNotificationFromEmailAddress();
        String fromEmail = null;
        
        if(request.getContextPath().equalsIgnoreCase("/super")) {
            fromEmail = this.system.getSuperNotificationFromEmailAddress();
        } else {
            fromEmail = this.system.getNotificationFromEmailAddress();
        }
        
        if(fromEmail == null || fromEmail.equals("")) {
        	//Return error code
        	return -100;
        }

        List<ForgotPassword> list = this.dao.getMemberForPassKey(passKey);
        for (ForgotPassword member : list) {
        	int langID = member.getLangID();
        	loc = LangUtils.getLocaleByLangID(langID);
        	
            // generate a new password for member
            String newPassword = SecureRandomUtils.generateHashKey(7);
            String encNewPassword = newPassword;
            try {
                encNewPassword = PasswordHash.createHash(newPassword);
            } catch (Exception e) {
                // just ignore it
            }
            this.dao.updateMemberPassword(member.getMemberID(), encNewPassword);
            this.memberDao.updateMember(TenantContext.getTenantId(), member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);

            SimpleMailMessage message = new SimpleMailMessage();

            StringBuffer text = new StringBuffer();
            text.append(MessageFormat.format(ms.getMessage("dear.member", null, "", loc), member.getUsername()));
            text.append("\r");
            text.append(ms.getMessage("your.password.was.reset", null, "", loc));
            text.append(MessageFormat.format(ms.getMessage("your.new.password.is", null, "", loc), newPassword));
            String link = request.getScheme()+"://"+request.getHeader("host")+request.getContextPath()+"/login.html";
            text.append(ms.getMessage("you.may.login.at.admin", null, "", loc)).append(" " + link);
            text.append("\r\r\r-\r");
            text.append(ms.getMessage("support.team", null, "", loc));

            if (fromEmail != null && !fromEmail.equals("")) {
                message.setFrom(fromEmail);
            } else {
                message.setFrom("admin@vidyo.com");
            }
            message.setTo(member.getEmailAddress());
            message.setSubject(ms.getMessage("your.new.password", null, "", loc));
            message.setText(text.toString());

	        this.emailService.sendEmailAsynchronous(message);
            rc++;
        }

        return rc;
    }

    public int forgotFlexPassword(String email, HttpServletRequest request) {
        int rc = 0;

        String fromEmail = this.system.getNotificationFromEmailAddress();
        
        if(fromEmail == null || fromEmail.equals("")) {
        	fromEmail = this.system.getSuperNotificationFromEmailAddress();
        	if(fromEmail == null || fromEmail.equals("")) {
        		//Return error code
        		return -100;
        	}
        }
        
        List<String> userTypes = new ArrayList<String>();
        userTypes.add("Normal");
        userTypes.add("Executive");
        List<ForgotPassword> list = this.dao.getMembersForEmail(email, this.system.getTenantId(), userTypes);
        
        if(list.isEmpty()) {
        	return -101;
        }
        
        for (ForgotPassword member : list) {
        	int langID = member.getLangID();
        	loc = LangUtils.getLocaleByLangID(langID);
        	
            // generate a new passKey for reference
            String passKey = SecureRandomUtils.generateHashKey(12);
            try {
                passKey = PasswordHash.createHash(passKey);
            } catch (Exception e) {
                // just ignore it
            }
            this.dao.updateMemberPassKey(member.getMemberID(), passKey);

            SimpleMailMessage message = new SimpleMailMessage();

            StringBuffer text = new StringBuffer();
            text.append(MessageFormat.format(ms.getMessage("dear.member", null, "", loc), member.getUsername()));
            text.append("\r");
            text.append(ms.getMessage("click.on.link.to.reset.password", null, "", loc));
            text.append("\r");
            String link = request.getScheme()+"://"+request.getHeader("host")+request.getContextPath()+"/flex.html?pwd="+passKey;
            text.append(link);
            text.append("\r\r\r-\r");
            text.append(ms.getMessage("support.team", null, "", loc));

            if (fromEmail != null && !fromEmail.equals("")) {
                message.setFrom(fromEmail);
            } else {
                message.setFrom("admin@vidyo.com");
            }
            message.setTo(member.getEmailAddress());
            message.setSubject(ms.getMessage("your.password.reset.key", null, "", loc));
            message.setText(text.toString());

	        this.emailService.sendEmailAsynchronous(message);
            rc++;
        }

        return rc;
    }

    public int resetFlexPassword(String passKey, HttpServletRequest request) {
        int rc = 0;

        String fromEmail = this.system.getNotificationFromEmailAddress();
        
        if(fromEmail == null || fromEmail.equals("")) {
        	//Return error code
        	return -100;
        }        

        List<ForgotPassword> list = this.dao.getMemberForPassKey(passKey);
        
        if(list.isEmpty()) {
        	// return error code
        	return -101;
        }
        
        for (ForgotPassword member : list) {
        	int langID = member.getLangID();
        	loc = LangUtils.getLocaleByLangID(langID);
        	
            // generate a new password for member
            String newPassword = SecureRandomUtils.generateHashKey(7);
            String encNewPassword = newPassword;
            try {
                encNewPassword = PasswordHash.createHash(newPassword);
            } catch (Exception e) {
                // just ignore it
            }
            this.dao.updateMemberPassword(member.getMemberID(), encNewPassword);
            this.memberDao.updateMember(TenantContext.getTenantId(), member.getUsername() != null ? member.getUsername().toLowerCase(Locale.ENGLISH) : null);

            SimpleMailMessage message = new SimpleMailMessage();

            StringBuffer text = new StringBuffer();
            text.append(MessageFormat.format(ms.getMessage("dear.member", null, "", loc), member.getUsername()));
            text.append("\r");
            text.append(ms.getMessage("your.password.was.reset", null, "", loc));
            text.append(MessageFormat.format(ms.getMessage("your.new.password.is", null, "", loc), newPassword));
            text.append(ms.getMessage("you.may.login.at", null, "", loc));            
            text.append("\r\r\r-\r");
            text.append(ms.getMessage("support.team", null, "", loc));

            if (fromEmail != null && !fromEmail.equals("")) {
                message.setFrom(fromEmail);
            } else {
                message.setFrom("admin@vidyo.com");
            }
            message.setTo(member.getEmailAddress());
            message.setSubject(ms.getMessage("your.new.password", null, "", loc));
            message.setText(text.toString());

	        this.emailService.sendEmailAsynchronous(message);
            rc++;
        }

        return rc;
    }
    
    public void forgotAPIPassword(int tenantId, String emailAddress, String scheme, String host, String contextPath)
    		throws NotificationEmailAddressNotConfiguredException, EmailAddressNotFoundException, GeneralException {
        String fromEmail = this.system.getNotificationFromEmailAddress();
        
        if(fromEmail == null || fromEmail.isEmpty()) {
        	throw new NotificationEmailAddressNotConfiguredException(); 
        }
        
        List<String> userTypes = new ArrayList<String>();
        userTypes.add(MemberRoleEnum.ADMIN.getMemberRole());
        userTypes.add(MemberRoleEnum.EXECUTIVE.getMemberRole());
        userTypes.add(MemberRoleEnum.NORMAL.getMemberRole());
        userTypes.add(MemberRoleEnum.OPERATOR.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_PANORAMA.getMemberRole());
        userTypes.add(MemberRoleEnum.VIDYO_ROOM.getMemberRole());
        
        List<ForgotPassword> list = this.dao.getMembersForEmail(emailAddress, tenantId, userTypes);
        
        if(list.size() < 1) {
            throw new EmailAddressNotFoundException();
        }
        
        for (ForgotPassword member : list) {
            int langID = member.getLangID();
            loc = LangUtils.getLocaleByLangID(langID);

            // generate a new passKey for reference
            String passKey = SecureRandomUtils.generateHashKey(12);
            try {
                passKey = PasswordHash.createHash(passKey);
            } catch (Exception e) {
                throw new GeneralException("passKey is not generated", e.getCause());
            }
        
            this.dao.updateMemberPassKey(member.getMemberID(), passKey);

            SimpleMailMessage message = new SimpleMailMessage();

            StringBuffer text = new StringBuffer();
            text.append(MessageFormat.format(ms.getMessage("dear.member", null, "", loc), member.getUsername()));
            text.append("\r");
            text.append(ms.getMessage("click.on.link.to.reset.password", null, "", loc));
            text.append("\r");
            String link = scheme + "://" + host + contextPath + "/flex.html?pwd=" + passKey;
            text.append(link);
            text.append("\r\r\r-\r");
            text.append(ms.getMessage("support.team", null, "", loc));


            message.setFrom(fromEmail);
        
            message.setTo(member.getEmailAddress());
            message.setSubject(ms.getMessage("your.password.reset.key", null, "", loc));
            message.setText(text.toString());

            this.emailService.sendEmailAsynchronous(message);
        }
    }
}