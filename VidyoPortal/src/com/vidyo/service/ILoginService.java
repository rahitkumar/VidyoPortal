package com.vidyo.service;

import javax.servlet.http.HttpServletRequest;

import com.vidyo.service.exceptions.EmailAddressNotFoundException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.NotificationEmailAddressNotConfiguredException;

public interface ILoginService {
    public int forgotPassword(String email, HttpServletRequest request);
    public int resetPassword(String passKey, HttpServletRequest request);
    public int forgotFlexPassword(String email, HttpServletRequest request);
    public int resetFlexPassword(String passKey, HttpServletRequest request);
    public void forgotAPIPassword(int tenantId, String emailAddress, String scheme, String host, String contextPath) 
    		throws NotificationEmailAddressNotConfiguredException, EmailAddressNotFoundException, GeneralException;
}