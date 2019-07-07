

/**
 * VidyoFederationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.vidyo.ws.federation;

    /*
     *  VidyoFederationService java interface
     */

    public interface VidyoFederationService {
          

        /**
          * Auto generated method signature
          * Join to remote conference on federation of Vidyo Portals
                    * @param joinRemoteConferenceRequest0
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.UserNotFoundFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
             * @throws com.vidyo.ws.federation.ConferenceLockedFaultException : 
             * @throws com.vidyo.ws.federation.TenantNotFoundFaultException : 
             * @throws com.vidyo.ws.federation.FederationNotAllowedFaultException : 
             * @throws com.vidyo.ws.federation.WrongPinFaultException : 
         */

         
                     public com.vidyo.ws.federation.JoinRemoteConferenceResponse joinRemoteConference(

                        com.vidyo.ws.federation.JoinRemoteConferenceRequest joinRemoteConferenceRequest0)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.UserNotFoundFaultException
          ,com.vidyo.ws.federation.GeneralFaultException
          ,com.vidyo.ws.federation.ConferenceLockedFaultException
          ,com.vidyo.ws.federation.TenantNotFoundFaultException
          ,com.vidyo.ws.federation.FederationNotAllowedFaultException
          ,com.vidyo.ws.federation.WrongPinFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Join to remote conference on federation of Vidyo Portals
                * @param joinRemoteConferenceRequest0
            
          */
        public void startjoinRemoteConference(

            com.vidyo.ws.federation.JoinRemoteConferenceRequest joinRemoteConferenceRequest0,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Silence the video for a given Endpoint GUID in the conference.
                    * @param silenceVideoRequest2
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.SilenceVideoResponse silenceVideo(

                        com.vidyo.ws.federation.SilenceVideoRequest silenceVideoRequest2)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Silence the video for a given Endpoint GUID in the conference.
                * @param silenceVideoRequest2
            
          */
        public void startsilenceVideo(

            com.vidyo.ws.federation.SilenceVideoRequest silenceVideoRequest2,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Start the video for a given Endpoint GUID in the conference.
                    * @param startVideoRequest4
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.StartVideoResponse startVideo(

                        com.vidyo.ws.federation.StartVideoRequest startVideoRequest4)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Start the video for a given Endpoint GUID in the conference.
                * @param startVideoRequest4
            
          */
        public void startstartVideo(

            com.vidyo.ws.federation.StartVideoRequest startVideoRequest4,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Exchange information about Cascaded Vidyo Routers
                    * @param exchangeMediaInfoRequest6
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.ExchangeMediaInfoResponse exchangeMediaInfo(

                        com.vidyo.ws.federation.ExchangeMediaInfoRequest exchangeMediaInfoRequest6)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Exchange information about Cascaded Vidyo Routers
                * @param exchangeMediaInfoRequest6
            
          */
        public void startexchangeMediaInfo(

            com.vidyo.ws.federation.ExchangeMediaInfoRequest exchangeMediaInfoRequest6,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Mute the audio for a given Endpoint GUID in the conference.
                    * @param muteAudioRequest8
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.MuteAudioResponse muteAudio(

                        com.vidyo.ws.federation.MuteAudioRequest muteAudioRequest8)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Mute the audio for a given Endpoint GUID in the conference.
                * @param muteAudioRequest8
            
          */
        public void startmuteAudio(

            com.vidyo.ws.federation.MuteAudioRequest muteAudioRequest8,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Remove info about remote Endpoint from Host portal
                    * @param removeEndpointInfoFromHostRequest10
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.RemoveEndpointInfoFromHostResponse removeEndpointInfoFromHost(

                        com.vidyo.ws.federation.RemoveEndpointInfoFromHostRequest removeEndpointInfoFromHostRequest10)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Remove info about remote Endpoint from Host portal
                * @param removeEndpointInfoFromHostRequest10
            
          */
        public void startremoveEndpointInfoFromHost(

            com.vidyo.ws.federation.RemoveEndpointInfoFromHostRequest removeEndpointInfoFromHostRequest10,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Disconnect from remote conference on federation of Vidyo Portals
                    * @param dropRemoteConferenceRequest12
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.DropRemoteConferenceResponse dropRemoteConference(

                        com.vidyo.ws.federation.DropRemoteConferenceRequest dropRemoteConferenceRequest12)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Disconnect from remote conference on federation of Vidyo Portals
                * @param dropRemoteConferenceRequest12
            
          */
        public void startdropRemoteConference(

            com.vidyo.ws.federation.DropRemoteConferenceRequest dropRemoteConferenceRequest12,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Send info about remote Endpoint to Host portal
                    * @param sendEndpointInfoToHostRequest14
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.SendEndpointInfoToHostResponse sendEndpointInfoToHost(

                        com.vidyo.ws.federation.SendEndpointInfoToHostRequest sendEndpointInfoToHostRequest14)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Send info about remote Endpoint to Host portal
                * @param sendEndpointInfoToHostRequest14
            
          */
        public void startsendEndpointInfoToHost(

            com.vidyo.ws.federation.SendEndpointInfoToHostRequest sendEndpointInfoToHostRequest14,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Unmute the audio for a given Endpoint GUID in the conference.
                    * @param unmuteAudioRequest16
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.UnmuteAudioResponse unmuteAudio(

                        com.vidyo.ws.federation.UnmuteAudioRequest unmuteAudioRequest16)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Unmute the audio for a given Endpoint GUID in the conference.
                * @param unmuteAudioRequest16
            
          */
        public void startunmuteAudio(

            com.vidyo.ws.federation.UnmuteAudioRequest unmuteAudioRequest16,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Disconnect remote participant from the conference
                    * @param disconnectEndpointFromHostRequest18
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.DisconnectEndpointFromHostResponse disconnectEndpointFromHost(

                        com.vidyo.ws.federation.DisconnectEndpointFromHostRequest disconnectEndpointFromHostRequest18)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Disconnect remote participant from the conference
                * @param disconnectEndpointFromHostRequest18
            
          */
        public void startdisconnectEndpointFromHost(

            com.vidyo.ws.federation.DisconnectEndpointFromHostRequest disconnectEndpointFromHostRequest18,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Silence the audio for a given Endpoint GUID in the conference.
                    * @param silenceAudioRequest20
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.SilenceAudioResponse silenceAudio(

                        com.vidyo.ws.federation.SilenceAudioRequest silenceAudioRequest20)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Silence the audio for a given Endpoint GUID in the conference.
                * @param silenceAudioRequest20
            
          */
        public void startsilenceAudio(

            com.vidyo.ws.federation.SilenceAudioRequest silenceAudioRequest20,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * Stop the video for a given Endpoint GUID in the conference.
                    * @param stopVideoRequest22
                
             * @throws com.vidyo.ws.federation.InvalidArgumentFaultException : 
             * @throws com.vidyo.ws.federation.GeneralFaultException : 
         */

         
                     public com.vidyo.ws.federation.StopVideoResponse stopVideo(

                        com.vidyo.ws.federation.StopVideoRequest stopVideoRequest22)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.ws.federation.InvalidArgumentFaultException
          ,com.vidyo.ws.federation.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * Stop the video for a given Endpoint GUID in the conference.
                * @param stopVideoRequest22
            
          */
        public void startstopVideo(

            com.vidyo.ws.federation.StopVideoRequest stopVideoRequest22,

            final com.vidyo.ws.federation.VidyoFederationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    