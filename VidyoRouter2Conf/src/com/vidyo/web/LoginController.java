package com.vidyo.web;

import com.vidyo.bo.ILoginHolder;
import com.vidyo.bo.MemberLoginHistory;
import com.vidyo.framework.security.RouterConfigAuthenticationProvider;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
    protected final Logger logger = LoggerFactory.getLogger(LoginController.class.getName());

    private String bannerFilePath="/opt/vidyo/conf.d/";
    private String showLoginBanner = "NO";
    private String showWelcomeBanner = "NO";
    private String loginBannertext = "";
    private String wlcomeBannertext = "";

    private ILoginHolder loginHolder;

    private RouterConfigAuthenticationProvider routerConfigAuthenticationProvider;

    public void setRouterConfigAuthenticationProvider(RouterConfigAuthenticationProvider routerConfigAuthenticationProvider) {
        this.routerConfigAuthenticationProvider = routerConfigAuthenticationProvider;
    }

    public void setLoginHolder(ILoginHolder loginHolder){
        this.loginHolder = loginHolder;
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView getLoginHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            loadLoginBannerValues();
            Boolean showBanner = false;
            if(null !=showLoginBanner) {
                if(showLoginBanner.equalsIgnoreCase("YES")) {
                    showBanner = true;
                }
            }
            model.put("banner", StringEscapeUtils.escapeJavaScript(loginBannertext));
            model.put("showLoginBanner", showBanner);
            model.put("accountLocked", ! routerConfigAuthenticationProvider.canLogin());
        } catch (Exception ex) {
            logger.error("getLoginHtml process failed: "+ ex);
        }

        return new ModelAndView("login_html", "model", model);
    }

    @RequestMapping(value = "/loginhistory.html", method = RequestMethod.GET)
    public ModelAndView getLoginHistoryDetailsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            loadWelcomeBannerValues();
            Boolean showBanner = false;
            if (null !=showWelcomeBanner) {
                if(showWelcomeBanner.equalsIgnoreCase("YES")) {
                    showBanner = true;
                }
            }
            if (!showBanner) {
                sendRedirect(request, response, "maintenance.html");
                return null;
            }
            model.put("banner", StringEscapeUtils.escapeJavaScript(wlcomeBannertext));
        } catch (Exception ex) {
            logger.error("getLoginHistoryDetailsHtml process failed: " + ex);
        }
        String user = this.loginHolder.getUsername();
        if (StringUtils.isBlank(user)) {
            user = "unknown";
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null) {
                Authentication authentication = securityContext.getAuthentication();
                user = authentication.getName();
            }
            this.loginHolder.setUsername(user);
        }
        model.put("userName", this.loginHolder.getUsername());
        int daysExpiry = getDaysUntilPasswordExpires(user);
        if (daysExpiry > 0) {
            if (daysExpiry >= 99999) {
                model.put("expiryStatement", "");
            }  else {
                model.put("expiryStatement", "Your password will expire in " + daysExpiry + " days.");
            }
        } else {
            model.put("expiryStatement", "Your password has expired. Please log into the System Console to change your password.");
        }

        return new ModelAndView("loginhistory_html", "model", model);
    }


    private int getDaysUntilPasswordExpires(String user) {
        String[] cmd = {"sudo", "-n", "/opt/vidyo/bin/vidyo_server.sh", "LAST_PASSWORD_CHANGED", user};
        try {
            ShellCapture capture = ShellExecutor.execute(cmd);
            String dateStr = capture.getStdOut();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date createDate = sdf.parse(dateStr);
            long createMilliseconds = createDate.getTime();
            long todayMilliseconds = (new Date()).getTime();
            long diffMilliseconds = todayMilliseconds - createMilliseconds;
            int passwordExpiryDays = getPasswordExpiryDaysForUser(user);
            if(passwordExpiryDays >=99999) return passwordExpiryDays;
            long daysLeft = passwordExpiryDays - (diffMilliseconds / 24 / 60 / 60 / 1000);
            return (int) daysLeft;
        } catch (ShellExecutorException e) {
            e.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return 0;
    }

    private int getPasswordExpiryDaysForUser(String user) {

        String[] cmd = {"sudo", "-n", "/opt/vidyo/bin/vidyo_server.sh", "PASSWORD_EXPIRY_DAYS", user};
        try {
            ShellCapture capture = ShellExecutor.execute(cmd);
            String passwordExpiryDays = capture.getStdOut();
            return  Integer.parseInt(passwordExpiryDays);
        } catch (ShellExecutorException e) {
            e.printStackTrace();
        } catch (Exception pe) {
            pe.printStackTrace();
        }

        return 99999;
    }


    public List<String> getconfigRouterLast5Logins(){
        List<String> ret = new ArrayList<String>();
        String command = "/opt/vidyo/bin/last5login.sh";
        try {
            ShellCapture capture = ShellExecutor.execute(command);
            return capture.getStdOutLines();
        } catch (ShellExecutorException e) {
            logger.debug("Exception while getconfigRouterLast5Logins: " + e.getMessage());
        }
        return ret;
    }

    @RequestMapping(value = "/loginhistory.ajax", method = RequestMethod.POST)
    public ModelAndView getLoginHistoryAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<MemberLoginHistory> loginHistories = new ArrayList <MemberLoginHistory>() ;

        try{
            List<String> lastlogins = this.getconfigRouterLast5Logins();
            int size = lastlogins.size();
            for (int i=0; i<size; i++)
            {
                String thisItem = lastlogins.get(i);
                if(null!=thisItem) {
                    String delimiterPattern = "\\|";
                    String[] columns = thisItem.split(delimiterPattern);
                    //EX: 2013-04-04 17:33:54 | admin | 172.16.4.188 | Login with correct userid/password
                    if(columns.length == 4){
                        MemberLoginHistory loginHistory = new MemberLoginHistory();
                        loginHistory.setID(i+1);
                        if(columns[3].contains("incorrect")){
                            loginHistory.setTransactionResult("FAILURE : " + columns[1].trim());
                        }
                        else{
                            loginHistory.setTransactionResult("SUCCESS : " + columns[1].trim());
                        }
                        loginHistory.setSourceIP(columns[2].trim());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dtStr= columns[0].trim();
                        Date dt = sdf.parse(dtStr);
                        loginHistory.setTransactionTime(dtStr);
                        loginHistory.setTransactionName("Login");
                        loginHistories.add(loginHistory);
                    }


                }
            }
            Collections.reverse(loginHistories);

        }
        catch(Exception ex){
            logger.error("getLoginHistoryAjax process failed: "+ ex);
        }
        model.put("userName", this.loginHolder.getUsername());
        model.put("loginHistories", loginHistories);
        return new ModelAndView("ajax/loginhistory_ajax", "model", model);
    }

    protected boolean loadLoginBannerValues() throws Exception {
        InputStream is = null;
        String showforgotpassword = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            File bannerfile = new File(bannerFilePath+"loginbanner");
            if(bannerfile.exists()){
                if(bannerfile.length() !=0)  {
                    showLoginBanner =  "Yes";
                    loginBannertext =  readFile(bannerFilePath+"loginbanner") ;
                }
            }

        } catch (IOException ignored) {
            logger.error("Exception loading CONFIG_PROPERTIES: " + ignored);
        }
        return true;
    }

    protected boolean loadWelcomeBannerValues() throws Exception {
        InputStream is = null;
        String showforgotpassword = "";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {

            File bannerfile = new File(bannerFilePath+"welcomebanner");
            if(bannerfile.exists()){
                if(bannerfile.length() !=0)  {
                    showWelcomeBanner =  "Yes";
                    wlcomeBannertext =  readFile(bannerFilePath+"welcomebanner");
                }
            }

        } catch (IOException ignored) {
            logger.error("Exception loading CONFIG_PROPERTIES: " + ignored);
        }
        return true;
    }


    private String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        //String lineSeparator = System.getProperty("line.separator");
        String banner = "";
        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine());
            }
            banner =  fileContents.toString();
            banner = banner.replace("& #40;".subSequence(0, "& #40;".length()), "(")
                    .replace("& #41;".subSequence(0, "& #41;".length()), ")")
                    .replace("&lt;".subSequence(0, "&lt;".length()), "<")
                    .replace("&gt;".subSequence(0, "&gt;".length()), ">")
                    .replace("& lt;".subSequence(0, "& lt;".length()), "<")
                    .replace("& gt;".subSequence(0, "& gt;".length()), ">")
                    .replace("&nbsp;".subSequence(0, "&nbsp;".length()), " ")
                    .replace("& #39;".subSequence(0, "& #39;".length()), "'");
            return banner;
        } finally {
            scanner.close();
        }
    }

    protected void sendRedirect(HttpServletRequest request,
                                HttpServletResponse response, String url) throws IOException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            if(request.getScheme().startsWith("https")){
                url = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath()+ "/" + url;
            } else {
                url = request.getScheme() + "://" + request.getServerName() + request.getContextPath()+ "/" + url;
            }
        }
        logger.info("Redirect URL ->" + response.encodeRedirectURL(url));
        response.sendRedirect(response.encodeRedirectURL(url));
    }
}
