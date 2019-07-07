package com.vidyo.framework.security.authentication;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


public class VidyoAccessDeniedHandler implements AccessDeniedHandler {

    public void handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, AccessDeniedException e)
            throws IOException, ServletException
    {
    	servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
