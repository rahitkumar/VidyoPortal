
/**
 * VidyoLicenseServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.license;
    /**
     *  VidyoLicenseServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoLicenseServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * License Registration during installation process
                                    * @param registerLicenseRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.license.RegisterLicenseResponse registerLicense
                (
                  com.vidyo.ws.license.RegisterLicenseRequest registerLicenseRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Updates the Line consumption scheme for Endpoint
                                    * @param updateLineConsumptionRequest
             * @throws EndpointNotBoundFaultException : 
             * @throws InvalidArgumentFaultException : 
             * @throws EndpointOfflineFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.license.UpdateLineConsumptionResponse updateLineConsumptionForEndpoint
                (
                  com.vidyo.ws.license.UpdateLineConsumptionRequest updateLineConsumptionRequest
                 )
            throws EndpointNotBoundFaultException,InvalidArgumentFaultException,EndpointOfflineFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Get the current active client version
                                    * @param clientVersionRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.license.ClientVersionResponse getClientVersion
                (
                  com.vidyo.ws.license.ClientVersionRequest clientVersionRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         }
    