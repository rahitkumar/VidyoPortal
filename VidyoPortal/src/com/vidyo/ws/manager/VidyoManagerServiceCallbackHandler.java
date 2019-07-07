
/**
 * VidyoManagerServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.vidyo.ws.manager;

    /**
     *  VidyoManagerServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class VidyoManagerServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public VidyoManagerServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public VidyoManagerServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inviteEndpoint method
            * override this method for handling normal response from inviteEndpoint operation
            */
           public void receiveResultinviteEndpoint(
                    com.vidyo.ws.manager.InviteEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inviteEndpoint operation
           */
            public void receiveErrorinviteEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for makeCall method
            * override this method for handling normal response from makeCall operation
            */
           public void receiveResultmakeCall(
                    com.vidyo.ws.manager.MakeCallResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from makeCall operation
           */
            public void receiveErrormakeCall(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for disconnectAll method
            * override this method for handling normal response from disconnectAll operation
            */
           public void receiveResultdisconnectAll(
                    com.vidyo.ws.manager.DisconnectAllResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from disconnectAll operation
           */
            public void receiveErrordisconnectAll(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for startAlert method
            * override this method for handling normal response from startAlert operation
            */
           public void receiveResultstartAlert(
                    com.vidyo.ws.manager.StartAlertResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from startAlert operation
           */
            public void receiveErrorstartAlert(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for startRing method
            * override this method for handling normal response from startRing operation
            */
           public void receiveResultstartRing(
                    com.vidyo.ws.manager.StartRingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from startRing operation
           */
            public void receiveErrorstartRing(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteConference method
            * override this method for handling normal response from deleteConference operation
            */
           public void receiveResultdeleteConference(
                    com.vidyo.ws.manager.DeleteConferenceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteConference operation
           */
            public void receiveErrordeleteConference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allowExternalLink method
            * override this method for handling normal response from allowExternalLink operation
            */
           public void receiveResultallowExternalLink(
                    com.vidyo.ws.manager.AllowExternalLinkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allowExternalLink operation
           */
            public void receiveErrorallowExternalLink(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeEndpoint method
            * override this method for handling normal response from removeEndpoint operation
            */
           public void receiveResultremoveEndpoint(
                    com.vidyo.ws.manager.RemoveEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeEndpoint operation
           */
            public void receiveErrorremoveEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changeConference method
            * override this method for handling normal response from changeConference operation
            */
           public void receiveResultchangeConference(
                    com.vidyo.ws.manager.ChangeConferenceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changeConference operation
           */
            public void receiveErrorchangeConference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createExternalLink method
            * override this method for handling normal response from createExternalLink operation
            */
           public void receiveResultcreateExternalLink(
                    com.vidyo.ws.manager.CreateExternalLinkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createExternalLink operation
           */
            public void receiveErrorcreateExternalLink(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setLicense method
            * override this method for handling normal response from setLicense operation
            */
           public void receiveResultsetLicense(
                    com.vidyo.ws.manager.SetLicenseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setLicense operation
           */
            public void receiveErrorsetLicense(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeSpontaneousEndpoint method
            * override this method for handling normal response from removeSpontaneousEndpoint operation
            */
           public void receiveResultremoveSpontaneousEndpoint(
                    com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeSpontaneousEndpoint operation
           */
            public void receiveErrorremoveSpontaneousEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSOAPConfig method
            * override this method for handling normal response from getSOAPConfig operation
            */
           public void receiveResultgetSOAPConfig(
                    com.vidyo.ws.manager.GetSOAPConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSOAPConfig operation
           */
            public void receiveErrorgetSOAPConfig(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for clearLicense method
            * override this method for handling normal response from clearLicense operation
            */
           public void receiveResultclearLicense(
                    com.vidyo.ws.manager.ClearLicenseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from clearLicense operation
           */
            public void receiveErrorclearLicense(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVidyoManagerSystemID method
            * override this method for handling normal response from getVidyoManagerSystemID operation
            */
           public void receiveResultgetVidyoManagerSystemID(
                    com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVidyoManagerSystemID operation
           */
            public void receiveErrorgetVidyoManagerSystemID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeLicensedEndpoint method
            * override this method for handling normal response from removeLicensedEndpoint operation
            */
           public void receiveResultremoveLicensedEndpoint(
                    com.vidyo.ws.manager.RemoveLicensedEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeLicensedEndpoint operation
           */
            public void receiveErrorremoveLicensedEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stopRing method
            * override this method for handling normal response from stopRing operation
            */
           public void receiveResultstopRing(
                    com.vidyo.ws.manager.StopRingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stopRing operation
           */
            public void receiveErrorstopRing(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stopAlert method
            * override this method for handling normal response from stopAlert operation
            */
           public void receiveResultstopAlert(
                    com.vidyo.ws.manager.StopAlertResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stopAlert operation
           */
            public void receiveErrorstopAlert(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createConference method
            * override this method for handling normal response from createConference operation
            */
           public void receiveResultcreateConference(
                    com.vidyo.ws.manager.CreateConferenceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createConference operation
           */
            public void receiveErrorcreateConference(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addEndpoint method
            * override this method for handling normal response from addEndpoint operation
            */
           public void receiveResultaddEndpoint(
                    com.vidyo.ws.manager.AddEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addEndpoint operation
           */
            public void receiveErroraddEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for infoForEndpoint method
            * override this method for handling normal response from infoForEndpoint operation
            */
           public void receiveResultinfoForEndpoint(
                    com.vidyo.ws.manager.InfoForEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from infoForEndpoint operation
           */
            public void receiveErrorinfoForEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getEMCPConfig method
            * override this method for handling normal response from getEMCPConfig operation
            */
           public void receiveResultgetEMCPConfig(
                    com.vidyo.ws.manager.GetEMCPConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEMCPConfig operation
           */
            public void receiveErrorgetEMCPConfig(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelInviteEndpoint method
            * override this method for handling normal response from cancelInviteEndpoint operation
            */
           public void receiveResultcancelInviteEndpoint(
                    com.vidyo.ws.manager.CancelInviteEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelInviteEndpoint operation
           */
            public void receiveErrorcancelInviteEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getLicenseData method
            * override this method for handling normal response from getLicenseData operation
            */
           public void receiveResultgetLicenseData(
                    com.vidyo.ws.manager.GetLicenseDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getLicenseData operation
           */
            public void receiveErrorgetLicenseData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addLicensedEndpoint method
            * override this method for handling normal response from addLicensedEndpoint operation
            */
           public void receiveResultaddLicensedEndpoint(
                    com.vidyo.ws.manager.AddLicensedEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addLicensedEndpoint operation
           */
            public void receiveErroraddLicensedEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPortalConfig method
            * override this method for handling normal response from getPortalConfig operation
            */
           public void receiveResultgetPortalConfig(
                    com.vidyo.ws.manager.GetPortalConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPortalConfig operation
           */
            public void receiveErrorgetPortalConfig(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSpontaneousEndpoint method
            * override this method for handling normal response from addSpontaneousEndpoint operation
            */
           public void receiveResultaddSpontaneousEndpoint(
                    com.vidyo.ws.manager.AddSpontaneousEndpointResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSpontaneousEndpoint operation
           */
            public void receiveErroraddSpontaneousEndpoint(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeExternalLink method
            * override this method for handling normal response from removeExternalLink operation
            */
           public void receiveResultremoveExternalLink(
                    com.vidyo.ws.manager.RemoveExternalLinkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeExternalLink operation
           */
            public void receiveErrorremoveExternalLink(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGroups method
            * override this method for handling normal response from getGroups operation
            */
           public void receiveResultgetGroups(
                    com.vidyo.ws.manager.GetGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGroups operation
           */
            public void receiveErrorgetGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVidyoRouters method
            * override this method for handling normal response from getVidyoRouters operation
            */
           public void receiveResultgetVidyoRouters(
                    com.vidyo.ws.manager.GetVidyoRoutersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVidyoRouters operation
           */
            public void receiveErrorgetVidyoRouters(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setVidyoManagerLicense method
            * override this method for handling normal response from setVidyoManagerLicense operation
            */
           public void receiveResultsetVidyoManagerLicense(
                    com.vidyo.ws.manager.SetLicenseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setVidyoManagerLicense operation
           */
            public void receiveErrorsetVidyoManagerLicense(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for connectExternalLink method
            * override this method for handling normal response from connectExternalLink operation
            */
           public void receiveResultconnectExternalLink(
                    com.vidyo.ws.manager.ConnectExternalLinkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from connectExternalLink operation
           */
            public void receiveErrorconnectExternalLink(java.lang.Exception e) {
            }
                


    }
    