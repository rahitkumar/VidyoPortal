

/**
 * ReplayUpdateParamService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package com.vidyo.replay.update;

    /*
     *  ReplayUpdateParamService java interface
     */

    public interface ReplayUpdateParamService {
          

        /**
          * Auto generated method signature
          * 
                    * @param updateTenantRequest0
                
         */

         
                     public com.vidyo.replay.update.UpdateStatusResponse updateTenant(

                        com.vidyo.replay.update.UpdateTenantRequest updateTenantRequest0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param updateTenantRequest0
            
          */
        public void startupdateTenant(

            com.vidyo.replay.update.UpdateTenantRequest updateTenantRequest0,

            final com.vidyo.replay.update.ReplayUpdateParamServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    