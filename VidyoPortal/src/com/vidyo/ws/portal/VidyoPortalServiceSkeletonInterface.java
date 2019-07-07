
/**
 * VidyoPortalServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.portal;
    /**
     *  VidyoPortalServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoPortalServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Updates the external link status to Vidyo Portal
                                    * @param updateExternalLinkStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.UpdateExternalLinkStatusResponse updateExternalLinkStatus
                (
                  com.vidyo.ws.portal.UpdateExternalLinkStatusRequest updateExternalLinkStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Event about starting of VidyoManager
                                    * @param startVidyoManagerRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.StartVidyoManagerResponse startVidyoManager
                (
                  com.vidyo.ws.portal.StartVidyoManagerRequest startVidyoManagerRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Event about adding a spontaneous endpoint
                                    * @param addSpontaneousEndpointRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.AddSpontaneousEndpointResponse addSpontaneousEndpoint
                (
                  com.vidyo.ws.portal.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Request from Endpoint to Vidyo Portal using VCAProtocol
                                    * @param infoFromEndpointRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.InfoFromEndpointResponse infoFromEndpoint
                (
                  com.vidyo.ws.portal.InfoFromEndpointRequest infoFromEndpointRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Updates the VidyoRouter endpoint status to Vidyo Portal
                                    * @param updateRouterEndpointStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.UpdateRouterEndpointStatusResponse updateRouterEndpointStatus
                (
                  com.vidyo.ws.portal.UpdateRouterEndpointStatusRequest updateRouterEndpointStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Updates the endpoint status to Vidyo Portal
                                    * @param updateEndpointStatusRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.portal.UpdateEndpointStatusResponse updateEndpointStatus
                (
                  com.vidyo.ws.portal.UpdateEndpointStatusRequest updateEndpointStatusRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         }
    