
/**
 * VidyoFederationServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
    package com.vidyo.ws.federation;
    /**
     *  VidyoFederationServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface VidyoFederationServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * Join to remote conference on federation of Vidyo Portals
                                    * @param joinRemoteConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws UserNotFoundFaultException : 
             * @throws GeneralFaultException : 
             * @throws ConferenceLockedFaultException : 
             * @throws TenantNotFoundFaultException : 
             * @throws FederationNotAllowedFaultException : 
             * @throws WrongPinFaultException : 
         */

        
                public com.vidyo.ws.federation.JoinRemoteConferenceResponse joinRemoteConference
                (
                  com.vidyo.ws.federation.JoinRemoteConferenceRequest joinRemoteConferenceRequest
                 )
            throws InvalidArgumentFaultException,UserNotFoundFaultException,GeneralFaultException,ConferenceLockedFaultException,TenantNotFoundFaultException,FederationNotAllowedFaultException,WrongPinFaultException;
        
         
        /**
         * Auto generated method signature
         * Silence the video for a given Endpoint GUID in the conference.
                                    * @param silenceVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.SilenceVideoResponse silenceVideo
                (
                  com.vidyo.ws.federation.SilenceVideoRequest silenceVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Start the video for a given Endpoint GUID in the conference.
                                    * @param startVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.StartVideoResponse startVideo
                (
                  com.vidyo.ws.federation.StartVideoRequest startVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Exchange information about Cascaded Vidyo Routers
                                    * @param exchangeMediaInfoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.ExchangeMediaInfoResponse exchangeMediaInfo
                (
                  com.vidyo.ws.federation.ExchangeMediaInfoRequest exchangeMediaInfoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Mute the audio for a given Endpoint GUID in the conference.
                                    * @param muteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.MuteAudioResponse muteAudio
                (
                  com.vidyo.ws.federation.MuteAudioRequest muteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Remove info about remote Endpoint from Host portal
                                    * @param removeEndpointInfoFromHostRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.RemoveEndpointInfoFromHostResponse removeEndpointInfoFromHost
                (
                  com.vidyo.ws.federation.RemoveEndpointInfoFromHostRequest removeEndpointInfoFromHostRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Disconnect from remote conference on federation of Vidyo Portals
                                    * @param dropRemoteConferenceRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.DropRemoteConferenceResponse dropRemoteConference
                (
                  com.vidyo.ws.federation.DropRemoteConferenceRequest dropRemoteConferenceRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Send info about remote Endpoint to Host portal
                                    * @param sendEndpointInfoToHostRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.SendEndpointInfoToHostResponse sendEndpointInfoToHost
                (
                  com.vidyo.ws.federation.SendEndpointInfoToHostRequest sendEndpointInfoToHostRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Unmute the audio for a given Endpoint GUID in the conference.
                                    * @param unmuteAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.UnmuteAudioResponse unmuteAudio
                (
                  com.vidyo.ws.federation.UnmuteAudioRequest unmuteAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Disconnect remote participant from the conference
                                    * @param disconnectEndpointFromHostRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.DisconnectEndpointFromHostResponse disconnectEndpointFromHost
                (
                  com.vidyo.ws.federation.DisconnectEndpointFromHostRequest disconnectEndpointFromHostRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Silence the audio for a given Endpoint GUID in the conference.
                                    * @param silenceAudioRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.SilenceAudioResponse silenceAudio
                (
                  com.vidyo.ws.federation.SilenceAudioRequest silenceAudioRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         
        /**
         * Auto generated method signature
         * Stop the video for a given Endpoint GUID in the conference.
                                    * @param stopVideoRequest
             * @throws InvalidArgumentFaultException : 
             * @throws GeneralFaultException : 
         */

        
                public com.vidyo.ws.federation.StopVideoResponse stopVideo
                (
                  com.vidyo.ws.federation.StopVideoRequest stopVideoRequest
                 )
            throws InvalidArgumentFaultException,GeneralFaultException;
        
         }
    