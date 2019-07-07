
/**
 * ConfigurationServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.configuration;
    /**
     *  ConfigurationServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface ConfigurationServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * register a new/existing network element
                                    * @param registerRequest
             * @throws NotEnabledFaultException : 
             * @throws NotConfiguredFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
             * @throws ResourceNotAvailableFaultException : 
             * @throws NotLicensedFaultException : 
         */

        
                public com.vidyo.ws.configuration.RegisterResponse register
                (
                  com.vidyo.ws.configuration.RegisterRequest registerRequest
                 )
            throws NotEnabledFaultException,NotConfiguredFaultException,InvalidArgumentFaultException,GeneralFaultException,ResourceNotAvailableFaultException,NotLicensedFaultException;
        
         }
    