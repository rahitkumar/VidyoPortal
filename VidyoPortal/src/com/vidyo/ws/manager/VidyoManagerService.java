

/**
 * VidyoManagerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.vidyo.ws.manager;

    /*
     *  VidyoManagerService java interface
     */

    public interface VidyoManagerService {
          

        /**
          * Auto generated method signature
          * Make a 2-party call between given endpoints using the given conference
                    * @param inviteEndpointRequest2
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.InviteEndpointResponse inviteEndpoint(

                        com.vidyo.ws.manager.InviteEndpointRequest inviteEndpointRequest2)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Make a 2-party call between given endpoints using the given conference
                * @param inviteEndpointRequest2
            
          */
        public void startinviteEndpoint(

            com.vidyo.ws.manager.InviteEndpointRequest inviteEndpointRequest2,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Make a 2-party call between given endpoints using the given conference
                    * @param makeCallRequest4
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.MakeCallResponse makeCall(

                        com.vidyo.ws.manager.MakeCallRequest makeCallRequest4)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Make a 2-party call between given endpoints using the given conference
                * @param makeCallRequest4
            
          */
        public void startmakeCall(

            com.vidyo.ws.manager.MakeCallRequest makeCallRequest4,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Deletes a Conference
                    * @param disconnectAllRequest6
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.DisconnectAllResponse disconnectAll(

                        com.vidyo.ws.manager.DisconnectAllRequest disconnectAllRequest6)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Deletes a Conference
                * @param disconnectAllRequest6
            
          */
        public void startdisconnectAll(

            com.vidyo.ws.manager.DisconnectAllRequest disconnectAllRequest6,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Send Invitation to second party in call
                    * @param startAlertRequest8
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.StartAlertResponse startAlert(

                        com.vidyo.ws.manager.StartAlertRequest startAlertRequest8)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Send Invitation to second party in call
                * @param startAlertRequest8
            
          */
        public void startstartAlert(

            com.vidyo.ws.manager.StartAlertRequest startAlertRequest8,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Send Invitation to second party in call
                    * @param startRingRequest10
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.StartRingResponse startRing(

                        com.vidyo.ws.manager.StartRingRequest startRingRequest10)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Send Invitation to second party in call
                * @param startRingRequest10
            
          */
        public void startstartRing(

            com.vidyo.ws.manager.StartRingRequest startRingRequest10,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Deletes a Conference
                    * @param deleteConferenceRequest12
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.DeleteConferenceResponse deleteConference(

                        com.vidyo.ws.manager.DeleteConferenceRequest deleteConferenceRequest12)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Deletes a Conference
                * @param deleteConferenceRequest12
            
          */
        public void startdeleteConference(

            com.vidyo.ws.manager.DeleteConferenceRequest deleteConferenceRequest12,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Allow a link to an external conference
                    * @param allowExternalLinkRequest14
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.AllowExternalLinkResponse allowExternalLink(

                        com.vidyo.ws.manager.AllowExternalLinkRequest allowExternalLinkRequest14)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Allow a link to an external conference
                * @param allowExternalLinkRequest14
            
          */
        public void startallowExternalLink(

            com.vidyo.ws.manager.AllowExternalLinkRequest allowExternalLinkRequest14,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Remove an Endpoint from Conference
                    * @param removeEndpointRequest16
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.RemoveEndpointResponse removeEndpoint(

                        com.vidyo.ws.manager.RemoveEndpointRequest removeEndpointRequest16)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Remove an Endpoint from Conference
                * @param removeEndpointRequest16
            
          */
        public void startremoveEndpoint(

            com.vidyo.ws.manager.RemoveEndpointRequest removeEndpointRequest16,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Change a Conference
                    * @param changeConferenceRequest18
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.ChangeConferenceResponse changeConference(

                        com.vidyo.ws.manager.ChangeConferenceRequest changeConferenceRequest18)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Change a Conference
                * @param changeConferenceRequest18
            
          */
        public void startchangeConference(

            com.vidyo.ws.manager.ChangeConferenceRequest changeConferenceRequest18,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * create a link to an external conference
                    * @param createExternalLinkRequest20
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.CreateExternalLinkResponse createExternalLink(

                        com.vidyo.ws.manager.CreateExternalLinkRequest createExternalLinkRequest20)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * create a link to an external conference
                * @param createExternalLinkRequest20
            
          */
        public void startcreateExternalLink(

            com.vidyo.ws.manager.CreateExternalLinkRequest createExternalLinkRequest20,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Request to apply a new license
                    * @param setLicenseRequest22
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.SetLicenseResponse setLicense(

                        com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest22)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Request to apply a new license
                * @param setLicenseRequest22
            
          */
        public void startsetLicense(

            com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest22,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Remove an endpoint bypathing EMCP.
                    * @param removeSpontaneousEndpointRequest24
                
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse removeSpontaneousEndpoint(

                        com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest removeSpontaneousEndpointRequest24)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Remove an endpoint bypathing EMCP.
                * @param removeSpontaneousEndpointRequest24
            
          */
        public void startremoveSpontaneousEndpoint(

            com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest removeSpontaneousEndpointRequest24,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get SOAP Configuration Infomation
                    * @param getSOAPConfigRequest26
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetSOAPConfigResponse getSOAPConfig(

                        com.vidyo.ws.manager.GetSOAPConfigRequest getSOAPConfigRequest26)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get SOAP Configuration Infomation
                * @param getSOAPConfigRequest26
            
          */
        public void startgetSOAPConfig(

            com.vidyo.ws.manager.GetSOAPConfigRequest getSOAPConfigRequest26,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Request to clear the current system license
                    * @param clearLicenseRequest28
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.ClearLicenseResponse clearLicense(

                        com.vidyo.ws.manager.ClearLicenseRequest clearLicenseRequest28)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Request to clear the current system license
                * @param clearLicenseRequest28
            
          */
        public void startclearLicense(

            com.vidyo.ws.manager.ClearLicenseRequest clearLicenseRequest28,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get Vidyo Manager System Identifier
                    * @param getVidyoManagerSystemIDRequest30
                
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse getVidyoManagerSystemID(

                        com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest getVidyoManagerSystemIDRequest30)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get Vidyo Manager System Identifier
                * @param getVidyoManagerSystemIDRequest30
            
          */
        public void startgetVidyoManagerSystemID(

            com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest getVidyoManagerSystemIDRequest30,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Request to remove a licensed endpoint
                    * @param removeLicensedEndpointRequest32
                
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.RemoveLicensedEndpointResponse removeLicensedEndpoint(

                        com.vidyo.ws.manager.RemoveLicensedEndpointRequest removeLicensedEndpointRequest32)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Request to remove a licensed endpoint
                * @param removeLicensedEndpointRequest32
            
          */
        public void startremoveLicensedEndpoint(

            com.vidyo.ws.manager.RemoveLicensedEndpointRequest removeLicensedEndpointRequest32,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Stop send Invitation to second party in call.
                    * @param stopRingRequest34
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.StopRingResponse stopRing(

                        com.vidyo.ws.manager.StopRingRequest stopRingRequest34)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Stop send Invitation to second party in call.
                * @param stopRingRequest34
            
          */
        public void startstopRing(

            com.vidyo.ws.manager.StopRingRequest stopRingRequest34,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Stop send Invitation to second party in call.
                    * @param stopAlertRequest36
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.StopAlertResponse stopAlert(

                        com.vidyo.ws.manager.StopAlertRequest stopAlertRequest36)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Stop send Invitation to second party in call.
                * @param stopAlertRequest36
            
          */
        public void startstopAlert(

            com.vidyo.ws.manager.StopAlertRequest stopAlertRequest36,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Creates a Conference
                    * @param createConferenceRequest38
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.CreateConferenceResponse createConference(

                        com.vidyo.ws.manager.CreateConferenceRequest createConferenceRequest38)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Creates a Conference
                * @param createConferenceRequest38
            
          */
        public void startcreateConference(

            com.vidyo.ws.manager.CreateConferenceRequest createConferenceRequest38,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Add an Endpoint to Conference
                    * @param addEndpointRequest40
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.AddEndpointResponse addEndpoint(

                        com.vidyo.ws.manager.AddEndpointRequest addEndpointRequest40)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Add an Endpoint to Conference
                * @param addEndpointRequest40
            
          */
        public void startaddEndpoint(

            com.vidyo.ws.manager.AddEndpointRequest addEndpointRequest40,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Control for an active conference
                    * @param infoForEndpointRequest42
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.InfoForEndpointResponse infoForEndpoint(

                        com.vidyo.ws.manager.InfoForEndpointRequest infoForEndpointRequest42)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Control for an active conference
                * @param infoForEndpointRequest42
            
          */
        public void startinfoForEndpoint(

            com.vidyo.ws.manager.InfoForEndpointRequest infoForEndpointRequest42,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get EMCP Configuration Infomation
                    * @param getEMCPConfigRequest44
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetEMCPConfigResponse getEMCPConfig(

                        com.vidyo.ws.manager.GetEMCPConfigRequest getEMCPConfigRequest44)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get EMCP Configuration Infomation
                * @param getEMCPConfigRequest44
            
          */
        public void startgetEMCPConfig(

            com.vidyo.ws.manager.GetEMCPConfigRequest getEMCPConfigRequest44,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Make a 2-party call between given endpoints using the given conference
                    * @param cancelInviteEndpointRequest46
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.CancelInviteEndpointResponse cancelInviteEndpoint(

                        com.vidyo.ws.manager.CancelInviteEndpointRequest cancelInviteEndpointRequest46)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Make a 2-party call between given endpoints using the given conference
                * @param cancelInviteEndpointRequest46
            
          */
        public void startcancelInviteEndpoint(

            com.vidyo.ws.manager.CancelInviteEndpointRequest cancelInviteEndpointRequest46,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get current Effective License data
                    * @param getLicenseDataRequest48
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetLicenseDataResponse getLicenseData(

                        com.vidyo.ws.manager.GetLicenseDataRequest getLicenseDataRequest48)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get current Effective License data
                * @param getLicenseDataRequest48
            
          */
        public void startgetLicenseData(

            com.vidyo.ws.manager.GetLicenseDataRequest getLicenseDataRequest48,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Request to add a licensed endpoint
                    * @param addLicensedEndpointRequest50
                
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.AddLicensedEndpointResponse addLicensedEndpoint(

                        com.vidyo.ws.manager.AddLicensedEndpointRequest addLicensedEndpointRequest50)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Request to add a licensed endpoint
                * @param addLicensedEndpointRequest50
            
          */
        public void startaddLicensedEndpoint(

            com.vidyo.ws.manager.AddLicensedEndpointRequest addLicensedEndpointRequest50,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get Portal Configuration Infomation
                    * @param getPortalConfigRequest52
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetPortalConfigResponse getPortalConfig(

                        com.vidyo.ws.manager.GetPortalConfigRequest getPortalConfigRequest52)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get Portal Configuration Infomation
                * @param getPortalConfigRequest52
            
          */
        public void startgetPortalConfig(

            com.vidyo.ws.manager.GetPortalConfigRequest getPortalConfigRequest52,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Add an endpoint bypathing EMCP.
                    * @param addSpontaneousEndpointRequest54
                
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.AddSpontaneousEndpointResponse addSpontaneousEndpoint(

                        com.vidyo.ws.manager.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest54)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ConferenceNotExistFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Add an endpoint bypathing EMCP.
                * @param addSpontaneousEndpointRequest54
            
          */
        public void startaddSpontaneousEndpoint(

            com.vidyo.ws.manager.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest54,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * remove a link to an external conference
                    * @param removeExternalLinkRequest56
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.RemoveExternalLinkResponse removeExternalLink(

                        com.vidyo.ws.manager.RemoveExternalLinkRequest removeExternalLinkRequest56)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * remove a link to an external conference
                * @param removeExternalLinkRequest56
            
          */
        public void startremoveExternalLink(

            com.vidyo.ws.manager.RemoveExternalLinkRequest removeExternalLinkRequest56,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get current Effective License data
                    * @param getGroupsRequest58
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetGroupsResponse getGroups(

                        com.vidyo.ws.manager.GetGroupsRequest getGroupsRequest58)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get current Effective License data
                * @param getGroupsRequest58
            
          */
        public void startgetGroups(

            com.vidyo.ws.manager.GetGroupsRequest getGroupsRequest58,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Get currently available VidyoRouters
                    * @param getVidyoRoutersRequest60
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.GetVidyoRoutersResponse getVidyoRouters(

                        com.vidyo.ws.manager.GetVidyoRoutersRequest getVidyoRoutersRequest60)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Get currently available VidyoRouters
                * @param getVidyoRoutersRequest60
            
          */
        public void startgetVidyoRouters(

            com.vidyo.ws.manager.GetVidyoRoutersRequest getVidyoRoutersRequest60,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Request to apply a new VM license
                    * @param setLicenseRequest62
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.SetLicenseResponse setVidyoManagerLicense(

                        com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest62)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Request to apply a new VM license
                * @param setLicenseRequest62
            
          */
        public void startsetVidyoManagerLicense(

            com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest62,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * connect a link to an external conference
                    * @param connectExternalLinkRequest64
                
             * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
             * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
             * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
             * @throws com.vidyo.ws.manager.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.manager.ConnectExternalLinkResponse connectExternalLink(

                        com.vidyo.ws.manager.ConnectExternalLinkRequest connectExternalLinkRequest64)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.manager.NotLicensedFaultException
          ,com.vidyo.ws.manager.EndpointNotExistFaultException
          ,com.vidyo.ws.manager.InvalidArgumentFaultException
          ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
          ,com.vidyo.ws.manager.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * connect a link to an external conference
                * @param connectExternalLinkRequest64
            
          */
        public void startconnectExternalLink(

            com.vidyo.ws.manager.ConnectExternalLinkRequest connectExternalLinkRequest64,

            final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    