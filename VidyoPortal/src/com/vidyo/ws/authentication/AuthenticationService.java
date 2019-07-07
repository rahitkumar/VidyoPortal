

/**
 * AuthenticationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package com.vidyo.ws.authentication;

    /*
     *  AuthenticationService java interface
     */

    public interface AuthenticationService {
          

        /**
          * Auto generated method signature
          * 
                    * @param authenticationRequest0
                
         */

         
                     public com.vidyo.ws.authentication.AuthenticationResponse authentication(

                        com.vidyo.ws.authentication.AuthenticationRequest authenticationRequest0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param authenticationRequest0
            
          */
        public void startauthentication(

            com.vidyo.ws.authentication.AuthenticationRequest authenticationRequest0,

            final com.vidyo.ws.authentication.AuthenticationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    