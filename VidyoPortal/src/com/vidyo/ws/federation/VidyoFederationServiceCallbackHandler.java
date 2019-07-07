
/**
 * VidyoFederationServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.vidyo.ws.federation;

    /**
     *  VidyoFederationServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class VidyoFederationServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public VidyoFederationServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public VidyoFederationServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for joinRemoteConference method
            * override this method for handling normal response from joinRemoteConference operation
            */
           public void receiveResultjoinRemoteConference(
                    com.vidyo.ws.federation.JoinRemoteConferenceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from joinRemoteConference operation
           */
            public void receiveErrorjoinRemoteConference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for silenceVideo method
            * override this method for handling normal response from silenceVideo operation
            */
           public void receiveResultsilenceVideo(
                    com.vidyo.ws.federation.SilenceVideoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from silenceVideo operation
           */
            public void receiveErrorsilenceVideo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for startVideo method
            * override this method for handling normal response from startVideo operation
            */
           public void receiveResultstartVideo(
                    com.vidyo.ws.federation.StartVideoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from startVideo operation
           */
            public void receiveErrorstartVideo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for exchangeMediaInfo method
            * override this method for handling normal response from exchangeMediaInfo operation
            */
           public void receiveResultexchangeMediaInfo(
                    com.vidyo.ws.federation.ExchangeMediaInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from exchangeMediaInfo operation
           */
            public void receiveErrorexchangeMediaInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for muteAudio method
            * override this method for handling normal response from muteAudio operation
            */
           public void receiveResultmuteAudio(
                    com.vidyo.ws.federation.MuteAudioResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from muteAudio operation
           */
            public void receiveErrormuteAudio(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeEndpointInfoFromHost method
            * override this method for handling normal response from removeEndpointInfoFromHost operation
            */
           public void receiveResultremoveEndpointInfoFromHost(
                    com.vidyo.ws.federation.RemoveEndpointInfoFromHostResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeEndpointInfoFromHost operation
           */
            public void receiveErrorremoveEndpointInfoFromHost(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for dropRemoteConference method
            * override this method for handling normal response from dropRemoteConference operation
            */
           public void receiveResultdropRemoteConference(
                    com.vidyo.ws.federation.DropRemoteConferenceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from dropRemoteConference operation
           */
            public void receiveErrordropRemoteConference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendEndpointInfoToHost method
            * override this method for handling normal response from sendEndpointInfoToHost operation
            */
           public void receiveResultsendEndpointInfoToHost(
                    com.vidyo.ws.federation.SendEndpointInfoToHostResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendEndpointInfoToHost operation
           */
            public void receiveErrorsendEndpointInfoToHost(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unmuteAudio method
            * override this method for handling normal response from unmuteAudio operation
            */
           public void receiveResultunmuteAudio(
                    com.vidyo.ws.federation.UnmuteAudioResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unmuteAudio operation
           */
            public void receiveErrorunmuteAudio(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for disconnectEndpointFromHost method
            * override this method for handling normal response from disconnectEndpointFromHost operation
            */
           public void receiveResultdisconnectEndpointFromHost(
                    com.vidyo.ws.federation.DisconnectEndpointFromHostResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from disconnectEndpointFromHost operation
           */
            public void receiveErrordisconnectEndpointFromHost(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for silenceAudio method
            * override this method for handling normal response from silenceAudio operation
            */
           public void receiveResultsilenceAudio(
                    com.vidyo.ws.federation.SilenceAudioResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from silenceAudio operation
           */
            public void receiveErrorsilenceAudio(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stopVideo method
            * override this method for handling normal response from stopVideo operation
            */
           public void receiveResultstopVideo(
                    com.vidyo.ws.federation.StopVideoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stopVideo operation
           */
            public void receiveErrorstopVideo(java.lang.Exception e) {
            }
                


    }
    