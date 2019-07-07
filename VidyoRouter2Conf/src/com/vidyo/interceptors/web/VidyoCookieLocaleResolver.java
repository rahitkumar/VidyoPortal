package com.vidyo.interceptors.web;

import org.springframework.util.Assert;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class VidyoCookieLocaleResolver extends CookieLocaleResolver {

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        if(locale != null) {
            request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
            this.addCookie(request, response, locale.toString());
        } else {
            request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, this.determineDefaultLocale(request));
            this.removeCookie(response);
        }
    }

    public void addCookie(HttpServletRequest request, HttpServletResponse response, String cookieValue) {
        Assert.notNull(response, "HttpServletResponse must not be null");
        Cookie cookie = this.createCookie(cookieValue);
        Integer maxAge = this.getCookieMaxAge();
        if(maxAge != null) {
            cookie.setMaxAge(maxAge.intValue());
        }

        if(this.isCookieSecure() && request.isSecure()) {
            cookie.setSecure(true);
        }

        if(this.isCookieHttpOnly()) {
            cookie.setHttpOnly(true);
        }

        response.addCookie(cookie);
        if(this.logger.isDebugEnabled()) {
            this.logger.debug("Added cookie with name [" + this.getCookieName() + "] and value [" + cookieValue + "]");
        }

    }

}
