package com.vidyo.framework.security;

import com.vidyo.service.IRouterConfigService;
import com.vidyo.framework.executors.ShellCapture;
import com.vidyo.framework.executors.ShellExecutor;
import com.vidyo.framework.executors.exception.ShellExecutorException;
import com.vidyo.service.exceptions.AccountLockedException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouterConfigAuthenticationProvider implements AuthenticationProvider {
    protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(RouterConfigAuthenticationProvider.class.getName());

    private IRouterConfigService dao;

    public void setDao (IRouterConfigService dao) {
        this.dao = dao;
    }

    private String tmpDirectory = "/opt/vidyo/temp/tomcat/";

    public String getTmpDirectory() {
        return tmpDirectory;
    }

    public void setTmpDirectory(String tmpDirectory) {
        this.tmpDirectory = tmpDirectory;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());
        String ip = "?";

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            ip = request.getRemoteAddr();
        }


        if (username == null || password == null) {
            List<GrantedAuthority> anonRole = new ArrayList<GrantedAuthority>(1) {{
                add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
            }};
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), anonRole);
        } else {
            boolean loginSuccess = checkCredentials(username, password);

            if (this.canLogin() && loginSuccess){
                // continue
            } else {
                try {
                    if (!this.canLogin()) {
                        dao.writeAuditHistory(authentication.getName(), ip, "Attempting to login while account locked.");
                    } else {
                        dao.writeAuditHistory(authentication.getName(), ip, "Attempting to login with incorrect userid/password");
                    }
                    this.recordLoginFailedAttempt();
                } catch (AccountLockedException ale) {
                    dao.writeAuditHistory(ale.getMessage());
                }
                List<GrantedAuthority> anonRole = new ArrayList<GrantedAuthority>(1) {{
                    add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
                }};
                throw new BadCredentialsException("Username/Password does not match for " + authentication.getPrincipal());
            }

            List<GrantedAuthority> userRole = new ArrayList<GrantedAuthority>(1) {{
                add(new SimpleGrantedAuthority("ROLE_USER"));
            }};
            this.recordLoginSuccess();
            dao.writeAuditHistory(authentication.getName(), ip, "Login with correct userid/password");
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), userRole);
        }
    }

    public static boolean checkCredentials(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        if (password == null) {
            return false;
        }
        try {
            ShellCapture passcheckOutput;
            String[] cmd = new String[]{"sudo", "/opt/vidyo/bin/authuser", username, "admin"};
            List<String> stdIn = new ArrayList<String>();
            stdIn.add(password);
            passcheckOutput = ShellExecutor.execute(cmd, stdIn);
            if (passcheckOutput.isErrorExitCode()) {
                logger.error("authuser error code: " + passcheckOutput.getExitCode() + ", error msg: " + passcheckOutput.getStdErr());
            }
            return passcheckOutput.isSuccessExitCode();
        } catch (ShellExecutorException see) {
            logger.error("Error executing shell commands for authentication.");
            logger.error(ExceptionUtils.getStackTrace(see));
        }

        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }

    private void recordLoginFailedAttempt() throws AccountLockedException {
        File lockFile = new File(getTmpDirectory() + "vr2conf.lock");
        if (!lockFile.exists()) {
            long now = System.currentTimeMillis() / 1000L;
            List<String> lines = new ArrayList<String>(2);
            lines.add(""+now);
            lines.add("1");
            try {
                FileUtils.writeLines(lockFile, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                List<String> contents = FileUtils.readLines(lockFile);
                long failedTime = Long.parseLong(contents.get(0));
                int attempts = Integer.parseInt(contents.get(1));
                long now = System.currentTimeMillis() / 1000L;

                long seconds = now - failedTime;
                if (seconds > 60) {
                    attempts = 0;
                }

                attempts = attempts + 1;
                contents.clear();
                contents.add(""+now);
                contents.add(""+attempts);

                FileUtils.writeLines(lockFile, contents, false);

                if (attempts >= 3) {
                    throw new AccountLockedException("Account locked for 60 seconds, too many failed login attempts");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void recordLoginSuccess() {
        File lockFile = new File(getTmpDirectory() + "vr2conf.lock");
        if (lockFile.exists()) {
            FileUtils.deleteQuietly(lockFile);
        }
    }

    public boolean canLogin() {
        File lockFile = new File(getTmpDirectory() + "vr2conf.lock");
        if (!lockFile.exists()) {
            return true;
        }

        try {
            List<String> contents = FileUtils.readLines(lockFile);
            long unixTime = Long.parseLong(contents.get(0));
            int attempts = Integer.parseInt(contents.get(1));
            long now = System.currentTimeMillis() / 1000L;

            if (attempts < 3) {
                return true;
            }

            long seconds = now - unixTime;
            if (seconds > 60) {
                return true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return false;

    }


}
