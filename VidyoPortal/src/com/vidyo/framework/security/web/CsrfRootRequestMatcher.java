package com.vidyo.framework.security.web;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class CsrfRootRequestMatcher implements RequestMatcher {

    private RegexRequestMatcher loginPath = new RegexRequestMatcher("/j_spring_security_check", "POST");
    private RegexRequestMatcher uiPath = new RegexRequestMatcher("/ui/.*", "POST");

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        return uiPath.matches(httpServletRequest) || loginPath.matches(httpServletRequest);
    }
}
