
/**
 * VidyoRecorderServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
    package com.vidyo.ws.recorder;
    /**
     *  VidyoRecorderServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoRecorderServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Bring the Recorder Endpoint to Vidyo Portal
                                    * @param registerRecorderEndpointRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.recorder.RegisterRecorderEndpointResponse registerRecorderEndpoint
                (
                  com.vidyo.ws.recorder.RegisterRecorderEndpointRequest registerRecorderEndpointRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         }
    