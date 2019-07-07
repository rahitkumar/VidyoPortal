

/**
 * RecordingWebcastService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package com.vidyo.recordings.webcast;

    /*
     *  RecordingWebcastService java interface
     */

    public interface RecordingWebcastService {
          

        /**
          * Auto generated method signature
          * 
                    * @param deleteWebcastURLRequest0
                
             * @throws com.vidyo.recordings.webcast.InvalidArgumentFaultException : 
             * @throws com.vidyo.recordings.webcast.GeneralFaultException : 
         */

         
                     public com.vidyo.recordings.webcast.DeleteWebcastURLResponse deleteWebcastURL(

                        com.vidyo.recordings.webcast.DeleteWebcastURLRequest deleteWebcastURLRequest0)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.recordings.webcast.InvalidArgumentFaultException
          ,com.vidyo.recordings.webcast.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param deleteWebcastURLRequest0
            
          */
        public void startdeleteWebcastURL(

            com.vidyo.recordings.webcast.DeleteWebcastURLRequest deleteWebcastURLRequest0,

            final com.vidyo.recordings.webcast.RecordingWebcastServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param createWebcastURLRequest2
                
             * @throws com.vidyo.recordings.webcast.InvalidArgumentFaultException : 
             * @throws com.vidyo.recordings.webcast.GeneralFaultException : 
         */

         
                     public com.vidyo.recordings.webcast.CreateWebcastURLResponse createWebcastURL(

                        com.vidyo.recordings.webcast.CreateWebcastURLRequest createWebcastURLRequest2)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.recordings.webcast.InvalidArgumentFaultException
          ,com.vidyo.recordings.webcast.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param createWebcastURLRequest2
            
          */
        public void startcreateWebcastURL(

            com.vidyo.recordings.webcast.CreateWebcastURLRequest createWebcastURLRequest2,

            final com.vidyo.recordings.webcast.RecordingWebcastServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param updateWebcastPINRequest4
                
             * @throws com.vidyo.recordings.webcast.InvalidArgumentFaultException : 
             * @throws com.vidyo.recordings.webcast.GeneralFaultException : 
         */

         
                     public com.vidyo.recordings.webcast.UpdateWebcastPINResponse updateWebcastPIN(

                        com.vidyo.recordings.webcast.UpdateWebcastPINRequest updateWebcastPINRequest4)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.recordings.webcast.InvalidArgumentFaultException
          ,com.vidyo.recordings.webcast.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param updateWebcastPINRequest4
            
          */
        public void startupdateWebcastPIN(

            com.vidyo.recordings.webcast.UpdateWebcastPINRequest updateWebcastPINRequest4,

            final com.vidyo.recordings.webcast.RecordingWebcastServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getWebcastURLRequest6
                
             * @throws com.vidyo.recordings.webcast.InvalidArgumentFaultException : 
             * @throws com.vidyo.recordings.webcast.GeneralFaultException : 
         */

         
                     public com.vidyo.recordings.webcast.GetWebcastURLResponse getWebcastURL(

                        com.vidyo.recordings.webcast.GetWebcastURLRequest getWebcastURLRequest6)
                        throws java.rmi.RemoteException
             
          ,com.vidyo.recordings.webcast.InvalidArgumentFaultException
          ,com.vidyo.recordings.webcast.GeneralFaultException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getWebcastURLRequest6
            
          */
        public void startgetWebcastURL(

            com.vidyo.recordings.webcast.GetWebcastURLRequest getWebcastURLRequest6,

            final com.vidyo.recordings.webcast.RecordingWebcastServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    