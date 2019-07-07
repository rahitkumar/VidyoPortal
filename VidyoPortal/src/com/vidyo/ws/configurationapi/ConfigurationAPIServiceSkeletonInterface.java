
/**
 * ConfigurationAPIServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
    package com.vidyo.ws.configurationapi;
    /**
     *  ConfigurationAPIServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface ConfigurationAPIServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Enable or Disable a network component
                                    * @param enableNetworkComponentRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.configurationapi.EnableNetworkComponentResponse enableNetworkComponent
                (
                  com.vidyo.ws.configurationapi.EnableNetworkComponentRequest enableNetworkComponentRequest
                 )
            throws GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * List all network components
                                    * @param listNetworkComponentsRequest
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.configurationapi.ListNetworkComponentsResponse listNetworkComponents
                (
                  com.vidyo.ws.configurationapi.ListNetworkComponentsRequest listNetworkComponentsRequest
                 )
            throws GeneralFaultException;
        
         }
    