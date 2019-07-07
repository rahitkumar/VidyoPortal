package com.vidyo.framework.security.saml;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.websso.WebSSOProfileOptions;

import com.vidyo.bo.authentication.SamlRelayStateParam;
import com.vidyo.utils.PortalUtils;


public class SAMLEntryPoint extends org.springframework.security.saml.SAMLEntryPoint {

    protected final static Logger logger = LoggerFactory.getLogger(SAMLEntryPoint.class);


    /**override commence method from parent class in order to set request attributes

     *
     * @param request  request
     * @param response response
     * @param e        exception causing this entry point to be invoked or null when EntryPoint is invoked directly
     * @throws IOException      error sending response
     * @throws ServletException error initializing SAML protocol
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

    	//TODO initial changes only...we need dont need to set attribute if roomkey and mode are null..TODO
           	request.setAttribute("roomKey", request.getParameter("roomKey"));
        	request.setAttribute("mode", request.getParameter("mode"));
        	request.setAttribute("token", request.getParameter("token"));
        	request.setAttribute("directDial", request.getParameter("directDial"));
        	super.commence(request, response, e);
    }

      /**
     * Method is supposed to populate preferences used to construct the SAML message. Method can be overridden to provide
     * logic appropriate for given application. In case defaultOptions object was set it will be used as basis for construction
     * and request specific values will be update (idp field).
     *
     * @param context   containing local entity
     * @param exception exception causing invocation of this entry point (can be null)
     * @return populated webSSOprofile
     * @throws MetadataProviderException in case metadata loading fails
     */
    @Override
    protected WebSSOProfileOptions getProfileOptions(SAMLMessageContext context, AuthenticationException exception) throws MetadataProviderException {

        WebSSOProfileOptions ssoProfileOptions;
        if (defaultOptions != null) {
            ssoProfileOptions = defaultOptions.clone();
        } else {
            ssoProfileOptions = new WebSSOProfileOptions();
        }

        Object roomKey=context.getInboundMessageTransport().getAttribute("roomKey");
        Object mode =context.getInboundMessageTransport().getAttribute("mode");
        Object token = context.getInboundMessageTransport().getAttribute("token");
        Object directDial = context.getInboundMessageTransport().getAttribute("directDial");

		if (roomKey != null || mode != null || token != null || directDial != null) {
			SamlRelayStateParam relayStateParam = new SamlRelayStateParam();
			relayStateParam.setMode(mode!=null?mode.toString():null);
			relayStateParam.setRoomKey(roomKey!=null?roomKey.toString():null);
			relayStateParam.setToken(token != null ? token.toString() : null);
			relayStateParam.setDirectDial(directDial != null ? directDial.toString() : null);
			String relayState = PortalUtils.encodeBase64(PortalUtils.objectToJsonString(relayStateParam));
			if (relayState != null) {
				ssoProfileOptions.setRelayState(relayState);
			}
		}
        //we only set relay state if both fields are exist.As per the current requirement,both should come together.
        return ssoProfileOptions;

    }
}