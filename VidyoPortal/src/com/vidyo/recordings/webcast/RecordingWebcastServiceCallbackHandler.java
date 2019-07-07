
/**
 * RecordingWebcastServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package com.vidyo.recordings.webcast;

    /**
     *  RecordingWebcastServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class RecordingWebcastServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public RecordingWebcastServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public RecordingWebcastServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for deleteWebcastURL method
            * override this method for handling normal response from deleteWebcastURL operation
            */
           public void receiveResultdeleteWebcastURL(
                    com.vidyo.recordings.webcast.DeleteWebcastURLResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteWebcastURL operation
           */
            public void receiveErrordeleteWebcastURL(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createWebcastURL method
            * override this method for handling normal response from createWebcastURL operation
            */
           public void receiveResultcreateWebcastURL(
                    com.vidyo.recordings.webcast.CreateWebcastURLResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createWebcastURL operation
           */
            public void receiveErrorcreateWebcastURL(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateWebcastPIN method
            * override this method for handling normal response from updateWebcastPIN operation
            */
           public void receiveResultupdateWebcastPIN(
                    com.vidyo.recordings.webcast.UpdateWebcastPINResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateWebcastPIN operation
           */
            public void receiveErrorupdateWebcastPIN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getWebcastURL method
            * override this method for handling normal response from getWebcastURL operation
            */
           public void receiveResultgetWebcastURL(
                    com.vidyo.recordings.webcast.GetWebcastURLResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getWebcastURL operation
           */
            public void receiveErrorgetWebcastURL(java.lang.Exception e) {
            }
                


    }
    