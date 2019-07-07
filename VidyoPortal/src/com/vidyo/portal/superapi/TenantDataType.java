
/**
 * TenantDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.portal.superapi;
            

            /**
            *  TenantDataType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class TenantDataType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = TenantDataType
                Namespace URI = http://portal.vidyo.com/superapi/
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for TenantName
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantNamePattern localTenantName ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantNamePattern
                           */
                           public  com.vidyo.portal.superapi.TenantNamePattern getTenantName(){
                               return localTenantName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TenantName
                               */
                               public void setTenantName(com.vidyo.portal.superapi.TenantNamePattern param){
                            
                                            this.localTenantName=param;
                                    

                               }
                            

                        /**
                        * field for TenantUrl
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantUrlPattern localTenantUrl ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantUrlPattern
                           */
                           public  com.vidyo.portal.superapi.TenantUrlPattern getTenantUrl(){
                               return localTenantUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TenantUrl
                               */
                               public void setTenantUrl(com.vidyo.portal.superapi.TenantUrlPattern param){
                            
                                            this.localTenantUrl=param;
                                    

                               }
                            

                        /**
                        * field for ExtensionPrefix
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantExtensionPrefixPattern localExtensionPrefix ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantExtensionPrefixPattern
                           */
                           public  com.vidyo.portal.superapi.TenantExtensionPrefixPattern getExtensionPrefix(){
                               return localExtensionPrefix;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExtensionPrefix
                               */
                               public void setExtensionPrefix(com.vidyo.portal.superapi.TenantExtensionPrefixPattern param){
                            
                                            this.localExtensionPrefix=param;
                                    

                               }
                            

                        /**
                        * field for DialinNumber
                        */

                        
                                    protected com.vidyo.portal.superapi.String20 localDialinNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDialinNumberTracker = false ;

                           public boolean isDialinNumberSpecified(){
                               return localDialinNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.String20
                           */
                           public  com.vidyo.portal.superapi.String20 getDialinNumber(){
                               return localDialinNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DialinNumber
                               */
                               public void setDialinNumber(com.vidyo.portal.superapi.String20 param){
                            localDialinNumberTracker = true;
                                   
                                            this.localDialinNumber=param;
                                    

                               }
                            

                        /**
                        * field for VidyoReplayUrl
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantUrlPattern localVidyoReplayUrl ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVidyoReplayUrlTracker = false ;

                           public boolean isVidyoReplayUrlSpecified(){
                               return localVidyoReplayUrlTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantUrlPattern
                           */
                           public  com.vidyo.portal.superapi.TenantUrlPattern getVidyoReplayUrl(){
                               return localVidyoReplayUrl;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VidyoReplayUrl
                               */
                               public void setVidyoReplayUrl(com.vidyo.portal.superapi.TenantUrlPattern param){
                            localVidyoReplayUrlTracker = true;
                                   
                                            this.localVidyoReplayUrl=param;
                                    

                               }
                            

                        /**
                        * field for Description
                        */

                        
                                    protected java.lang.String localDescription ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDescriptionTracker = false ;

                           public boolean isDescriptionSpecified(){
                               return localDescriptionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDescription(){
                               return localDescription;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Description
                               */
                               public void setDescription(java.lang.String param){
                            localDescriptionTracker = true;
                                   
                                            this.localDescription=param;
                                    

                               }
                            

                        /**
                        * field for VidyoGatewayControllerDns
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantUrlPattern localVidyoGatewayControllerDns ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVidyoGatewayControllerDnsTracker = false ;

                           public boolean isVidyoGatewayControllerDnsSpecified(){
                               return localVidyoGatewayControllerDnsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantUrlPattern
                           */
                           public  com.vidyo.portal.superapi.TenantUrlPattern getVidyoGatewayControllerDns(){
                               return localVidyoGatewayControllerDns;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VidyoGatewayControllerDns
                               */
                               public void setVidyoGatewayControllerDns(com.vidyo.portal.superapi.TenantUrlPattern param){
                            localVidyoGatewayControllerDnsTracker = true;
                                   
                                            this.localVidyoGatewayControllerDns=param;
                                    

                               }
                            

                        /**
                        * field for NumOfInstalls
                        */

                        
                                    protected com.vidyo.portal.superapi.NonNegativeInt localNumOfInstalls ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.NonNegativeInt
                           */
                           public  com.vidyo.portal.superapi.NonNegativeInt getNumOfInstalls(){
                               return localNumOfInstalls;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfInstalls
                               */
                               public void setNumOfInstalls(com.vidyo.portal.superapi.NonNegativeInt param){
                            
                                            this.localNumOfInstalls=param;
                                    

                               }
                            

                        /**
                        * field for NumOfSeats
                        */

                        
                                    protected com.vidyo.portal.superapi.NonNegativeInt localNumOfSeats ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.NonNegativeInt
                           */
                           public  com.vidyo.portal.superapi.NonNegativeInt getNumOfSeats(){
                               return localNumOfSeats;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfSeats
                               */
                               public void setNumOfSeats(com.vidyo.portal.superapi.NonNegativeInt param){
                            
                                            this.localNumOfSeats=param;
                                    

                               }
                            

                        /**
                        * field for NumOfLines
                        */

                        
                                    protected com.vidyo.portal.superapi.NonNegativeInt localNumOfLines ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.NonNegativeInt
                           */
                           public  com.vidyo.portal.superapi.NonNegativeInt getNumOfLines(){
                               return localNumOfLines;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfLines
                               */
                               public void setNumOfLines(com.vidyo.portal.superapi.NonNegativeInt param){
                            
                                            this.localNumOfLines=param;
                                    

                               }
                            

                        /**
                        * field for NumOfExecutives
                        */

                        
                                    protected com.vidyo.portal.superapi.NonNegativeInt localNumOfExecutives ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.NonNegativeInt
                           */
                           public  com.vidyo.portal.superapi.NonNegativeInt getNumOfExecutives(){
                               return localNumOfExecutives;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfExecutives
                               */
                               public void setNumOfExecutives(com.vidyo.portal.superapi.NonNegativeInt param){
                            
                                            this.localNumOfExecutives=param;
                                    

                               }
                            

                        /**
                        * field for NumOfPanoramas
                        */

                        
                                    protected com.vidyo.portal.superapi.NonNegativeInt localNumOfPanoramas ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.NonNegativeInt
                           */
                           public  com.vidyo.portal.superapi.NonNegativeInt getNumOfPanoramas(){
                               return localNumOfPanoramas;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfPanoramas
                               */
                               public void setNumOfPanoramas(com.vidyo.portal.superapi.NonNegativeInt param){
                            
                                            this.localNumOfPanoramas=param;
                                    

                               }
                            

                        /**
                        * field for EnableGuestLogin
                        */

                        
                                    protected boolean localEnableGuestLogin =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("1");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEnableGuestLoginTracker = false ;

                           public boolean isEnableGuestLoginSpecified(){
                               return localEnableGuestLoginTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEnableGuestLogin(){
                               return localEnableGuestLogin;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EnableGuestLogin
                               */
                               public void setEnableGuestLogin(boolean param){
                            localEnableGuestLoginTracker = true;
                                   
                                            this.localEnableGuestLogin=param;
                                    

                               }
                            

                        /**
                        * field for AllowedTenantList
                        * This was an Array!
                        */

                        
                                    protected int[] localAllowedTenantList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowedTenantListTracker = false ;

                           public boolean isAllowedTenantListSpecified(){
                               return localAllowedTenantListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getAllowedTenantList(){
                               return localAllowedTenantList;
                           }

                           
                        


                               
                              /**
                               * validate the array for AllowedTenantList
                               */
                              protected void validateAllowedTenantList(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AllowedTenantList
                              */
                              public void setAllowedTenantList(int[] param){
                              
                                   validateAllowedTenantList(param);

                               localAllowedTenantListTracker = true;
                                      
                                      this.localAllowedTenantList=param;
                              }

                               
                             

                        /**
                        * field for VidyoManager
                        */

                        
                                    protected int localVidyoManager ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getVidyoManager(){
                               return localVidyoManager;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VidyoManager
                               */
                               public void setVidyoManager(int param){
                            
                                            this.localVidyoManager=param;
                                    

                               }
                            

                        /**
                        * field for VidyoProxyList
                        * This was an Array!
                        */

                        
                                    protected int[] localVidyoProxyList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVidyoProxyListTracker = false ;

                           public boolean isVidyoProxyListSpecified(){
                               return localVidyoProxyListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getVidyoProxyList(){
                               return localVidyoProxyList;
                           }

                           
                        


                               
                              /**
                               * validate the array for VidyoProxyList
                               */
                              protected void validateVidyoProxyList(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param VidyoProxyList
                              */
                              public void setVidyoProxyList(int[] param){
                              
                                   validateVidyoProxyList(param);

                               localVidyoProxyListTracker = true;
                                      
                                      this.localVidyoProxyList=param;
                              }

                               
                             

                        /**
                        * field for DefaultGuestProxy
                        */

                        
                                    protected org.apache.axis2.databinding.types.NonNegativeInteger localDefaultGuestProxy ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDefaultGuestProxyTracker = false ;

                           public boolean isDefaultGuestProxySpecified(){
                               return localDefaultGuestProxyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.NonNegativeInteger
                           */
                           public  org.apache.axis2.databinding.types.NonNegativeInteger getDefaultGuestProxy(){
                               return localDefaultGuestProxy;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DefaultGuestProxy
                               */
                               public void setDefaultGuestProxy(org.apache.axis2.databinding.types.NonNegativeInteger param){
                            localDefaultGuestProxyTracker = param != null;
                                   
                                            this.localDefaultGuestProxy=param;
                                    

                               }
                            

                        /**
                        * field for AllowedVidyoGatewayList
                        * This was an Array!
                        */

                        
                                    protected int[] localAllowedVidyoGatewayList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowedVidyoGatewayListTracker = false ;

                           public boolean isAllowedVidyoGatewayListSpecified(){
                               return localAllowedVidyoGatewayListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getAllowedVidyoGatewayList(){
                               return localAllowedVidyoGatewayList;
                           }

                           
                        


                               
                              /**
                               * validate the array for AllowedVidyoGatewayList
                               */
                              protected void validateAllowedVidyoGatewayList(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AllowedVidyoGatewayList
                              */
                              public void setAllowedVidyoGatewayList(int[] param){
                              
                                   validateAllowedVidyoGatewayList(param);

                               localAllowedVidyoGatewayListTracker = true;
                                      
                                      this.localAllowedVidyoGatewayList=param;
                              }

                               
                             

                        /**
                        * field for AllowedVidyoReplayRecorderList
                        * This was an Array!
                        */

                        
                                    protected int[] localAllowedVidyoReplayRecorderList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowedVidyoReplayRecorderListTracker = false ;

                           public boolean isAllowedVidyoReplayRecorderListSpecified(){
                               return localAllowedVidyoReplayRecorderListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getAllowedVidyoReplayRecorderList(){
                               return localAllowedVidyoReplayRecorderList;
                           }

                           
                        


                               
                              /**
                               * validate the array for AllowedVidyoReplayRecorderList
                               */
                              protected void validateAllowedVidyoReplayRecorderList(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AllowedVidyoReplayRecorderList
                              */
                              public void setAllowedVidyoReplayRecorderList(int[] param){
                              
                                   validateAllowedVidyoReplayRecorderList(param);

                               localAllowedVidyoReplayRecorderListTracker = true;
                                      
                                      this.localAllowedVidyoReplayRecorderList=param;
                              }

                               
                             

                        /**
                        * field for AllowedVidyoRepalyList
                        * This was an Array!
                        */

                        
                                    protected int[] localAllowedVidyoRepalyList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowedVidyoRepalyListTracker = false ;

                           public boolean isAllowedVidyoRepalyListSpecified(){
                               return localAllowedVidyoRepalyListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getAllowedVidyoRepalyList(){
                               return localAllowedVidyoRepalyList;
                           }

                           
                        


                               
                              /**
                               * validate the array for AllowedVidyoRepalyList
                               */
                              protected void validateAllowedVidyoRepalyList(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param AllowedVidyoRepalyList
                              */
                              public void setAllowedVidyoRepalyList(int[] param){
                              
                                   validateAllowedVidyoRepalyList(param);

                               localAllowedVidyoRepalyListTracker = true;
                                      
                                      this.localAllowedVidyoRepalyList=param;
                              }

                               
                             

                        /**
                        * field for AllowedLocationTagList
                        * This was an Array!
                        */

                        
                                    protected int[] localAllowedLocationTagList ;
                                

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getAllowedLocationTagList(){
                               return localAllowedLocationTagList;
                           }

                           
                        


                               
                              /**
                               * validate the array for AllowedLocationTagList
                               */
                              protected void validateAllowedLocationTagList(int[] param){
                             
                              if ((param != null) && (param.length < 1)){
                                throw new java.lang.RuntimeException();
                              }
                              
                              }


                             /**
                              * Auto generated setter method
                              * @param param AllowedLocationTagList
                              */
                              public void setAllowedLocationTagList(int[] param){
                              
                                   validateAllowedLocationTagList(param);

                               
                                      this.localAllowedLocationTagList=param;
                              }

                               
                             

                        /**
                        * field for DefaultGuestLocationTag
                        */

                        
                                    protected org.apache.axis2.databinding.types.NonNegativeInteger localDefaultGuestLocationTag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDefaultGuestLocationTagTracker = false ;

                           public boolean isDefaultGuestLocationTagSpecified(){
                               return localDefaultGuestLocationTagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.NonNegativeInteger
                           */
                           public  org.apache.axis2.databinding.types.NonNegativeInteger getDefaultGuestLocationTag(){
                               return localDefaultGuestLocationTag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DefaultGuestLocationTag
                               */
                               public void setDefaultGuestLocationTag(org.apache.axis2.databinding.types.NonNegativeInteger param){
                            localDefaultGuestLocationTagTracker = param != null;
                                   
                                            this.localDefaultGuestLocationTag=param;
                                    

                               }
                            

                        /**
                        * field for VidyoMobileAllowed
                        */

                        
                                    protected boolean localVidyoMobileAllowed =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("1");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVidyoMobileAllowedTracker = false ;

                           public boolean isVidyoMobileAllowedSpecified(){
                               return localVidyoMobileAllowedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getVidyoMobileAllowed(){
                               return localVidyoMobileAllowed;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VidyoMobileAllowed
                               */
                               public void setVidyoMobileAllowed(boolean param){
                            localVidyoMobileAllowedTracker = true;
                                   
                                            this.localVidyoMobileAllowed=param;
                                    

                               }
                            

                        /**
                        * field for IpcAllowOutbound
                        */

                        
                                    protected boolean localIpcAllowOutbound =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("1");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIpcAllowOutboundTracker = false ;

                           public boolean isIpcAllowOutboundSpecified(){
                               return localIpcAllowOutboundTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIpcAllowOutbound(){
                               return localIpcAllowOutbound;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IpcAllowOutbound
                               */
                               public void setIpcAllowOutbound(boolean param){
                            localIpcAllowOutboundTracker = true;
                                   
                                            this.localIpcAllowOutbound=param;
                                    

                               }
                            

                        /**
                        * field for IpcAllowInbound
                        */

                        
                                    protected boolean localIpcAllowInbound =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("1");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIpcAllowInboundTracker = false ;

                           public boolean isIpcAllowInboundSpecified(){
                               return localIpcAllowInboundTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIpcAllowInbound(){
                               return localIpcAllowInbound;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IpcAllowInbound
                               */
                               public void setIpcAllowInbound(boolean param){
                            localIpcAllowInboundTracker = true;
                                   
                                            this.localIpcAllowInbound=param;
                                    

                               }
                            

                        /**
                        * field for NumOfPublicRooms
                        */

                        
                                    protected org.apache.axis2.databinding.types.NonNegativeInteger localNumOfPublicRooms ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNumOfPublicRoomsTracker = false ;

                           public boolean isNumOfPublicRoomsSpecified(){
                               return localNumOfPublicRoomsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.NonNegativeInteger
                           */
                           public  org.apache.axis2.databinding.types.NonNegativeInteger getNumOfPublicRooms(){
                               return localNumOfPublicRooms;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NumOfPublicRooms
                               */
                               public void setNumOfPublicRooms(org.apache.axis2.databinding.types.NonNegativeInteger param){
                            localNumOfPublicRoomsTracker = true;
                                   
                                            this.localNumOfPublicRooms=param;
                                    

                               }
                            

                        /**
                        * field for TenantWebRTCURL
                        */

                        
                                    protected com.vidyo.portal.superapi.TenantUrlPattern localTenantWebRTCURL ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTenantWebRTCURLTracker = false ;

                           public boolean isTenantWebRTCURLSpecified(){
                               return localTenantWebRTCURLTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.TenantUrlPattern
                           */
                           public  com.vidyo.portal.superapi.TenantUrlPattern getTenantWebRTCURL(){
                               return localTenantWebRTCURL;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TenantWebRTCURL
                               */
                               public void setTenantWebRTCURL(com.vidyo.portal.superapi.TenantUrlPattern param){
                            localTenantWebRTCURLTracker = true;
                                   
                                            this.localTenantWebRTCURL=param;
                                    

                               }
                            

                        /**
                        * field for AdminUser
                        */

                        
                                    protected com.vidyo.portal.superapi.AdminMember localAdminUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAdminUserTracker = false ;

                           public boolean isAdminUserSpecified(){
                               return localAdminUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.AdminMember
                           */
                           public  com.vidyo.portal.superapi.AdminMember getAdminUser(){
                               return localAdminUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AdminUser
                               */
                               public void setAdminUser(com.vidyo.portal.superapi.AdminMember param){
                            localAdminUserTracker = true;
                                   
                                            this.localAdminUser=param;
                                    

                               }
                            

                        /**
                        * field for ExternalEndpointSoftwareFileserver
                        */

                        
                                    protected com.vidyo.portal.superapi.ExternalEndpointSoftwareFileserver localExternalEndpointSoftwareFileserver ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExternalEndpointSoftwareFileserverTracker = false ;

                           public boolean isExternalEndpointSoftwareFileserverSpecified(){
                               return localExternalEndpointSoftwareFileserverTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.ExternalEndpointSoftwareFileserver
                           */
                           public  com.vidyo.portal.superapi.ExternalEndpointSoftwareFileserver getExternalEndpointSoftwareFileserver(){
                               return localExternalEndpointSoftwareFileserver;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExternalEndpointSoftwareFileserver
                               */
                               public void setExternalEndpointSoftwareFileserver(com.vidyo.portal.superapi.ExternalEndpointSoftwareFileserver param){
                            localExternalEndpointSoftwareFileserverTracker = param != null;
                                   
                                            this.localExternalEndpointSoftwareFileserver=param;
                                    

                               }
                            

                        /**
                        * field for EnableCustomRole
                        */

                        
                                    protected boolean localEnableCustomRole ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEnableCustomRoleTracker = false ;

                           public boolean isEnableCustomRoleSpecified(){
                               return localEnableCustomRoleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEnableCustomRole(){
                               return localEnableCustomRole;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EnableCustomRole
                               */
                               public void setEnableCustomRole(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localEnableCustomRoleTracker =
                                       true;
                                   
                                            this.localEnableCustomRole=param;
                                    

                               }
                            

                        /**
                        * field for EnableEndpointLogAggregation
                        */

                        
                                    protected boolean localEnableEndpointLogAggregation =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("0");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEnableEndpointLogAggregationTracker = false ;

                           public boolean isEnableEndpointLogAggregationSpecified(){
                               return localEnableEndpointLogAggregationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEnableEndpointLogAggregation(){
                               return localEnableEndpointLogAggregation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EnableEndpointLogAggregation
                               */
                               public void setEnableEndpointLogAggregation(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localEnableEndpointLogAggregationTracker =
                                       true;
                                   
                                            this.localEnableEndpointLogAggregation=param;
                                    

                               }
                            

                        /**
                        * field for MobileLoginMode
                        */

                        
                                    protected com.vidyo.portal.superapi.MobileLoginMode localMobileLoginMode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobileLoginModeTracker = false ;

                           public boolean isMobileLoginModeSpecified(){
                               return localMobileLoginModeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.superapi.MobileLoginMode
                           */
                           public  com.vidyo.portal.superapi.MobileLoginMode getMobileLoginMode(){
                               return localMobileLoginMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MobileLoginMode
                               */
                               public void setMobileLoginMode(com.vidyo.portal.superapi.MobileLoginMode param){
                            localMobileLoginModeTracker = param != null;
                                   
                                            this.localMobileLoginMode=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://portal.vidyo.com/superapi/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":TenantDataType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "TenantDataType",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localTenantName==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tenantName cannot be null!!");
                                            }
                                           localTenantName.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantName"),
                                               xmlWriter);
                                        
                                            if (localTenantUrl==null){
                                                 throw new org.apache.axis2.databinding.ADBException("tenantUrl cannot be null!!");
                                            }
                                           localTenantUrl.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantUrl"),
                                               xmlWriter);
                                        
                                            if (localExtensionPrefix==null){
                                                 throw new org.apache.axis2.databinding.ADBException("extensionPrefix cannot be null!!");
                                            }
                                           localExtensionPrefix.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","extensionPrefix"),
                                               xmlWriter);
                                         if (localDialinNumberTracker){
                                    if (localDialinNumber==null){

                                        writeStartElement(null, "http://portal.vidyo.com/superapi/", "dialinNumber", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localDialinNumber.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","dialinNumber"),
                                        xmlWriter);
                                    }
                                } if (localVidyoReplayUrlTracker){
                                    if (localVidyoReplayUrl==null){

                                        writeStartElement(null, "http://portal.vidyo.com/superapi/", "vidyoReplayUrl", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localVidyoReplayUrl.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoReplayUrl"),
                                        xmlWriter);
                                    }
                                } if (localDescriptionTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "description", xmlWriter);
                             

                                          if (localDescription==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDescription);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVidyoGatewayControllerDnsTracker){
                                    if (localVidyoGatewayControllerDns==null){

                                        writeStartElement(null, "http://portal.vidyo.com/superapi/", "vidyoGatewayControllerDns", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localVidyoGatewayControllerDns.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoGatewayControllerDns"),
                                        xmlWriter);
                                    }
                                }
                                            if (localNumOfInstalls==null){
                                                 throw new org.apache.axis2.databinding.ADBException("numOfInstalls cannot be null!!");
                                            }
                                           localNumOfInstalls.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfInstalls"),
                                               xmlWriter);
                                        
                                            if (localNumOfSeats==null){
                                                 throw new org.apache.axis2.databinding.ADBException("numOfSeats cannot be null!!");
                                            }
                                           localNumOfSeats.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfSeats"),
                                               xmlWriter);
                                        
                                            if (localNumOfLines==null){
                                                 throw new org.apache.axis2.databinding.ADBException("numOfLines cannot be null!!");
                                            }
                                           localNumOfLines.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfLines"),
                                               xmlWriter);
                                        
                                            if (localNumOfExecutives==null){
                                                 throw new org.apache.axis2.databinding.ADBException("numOfExecutives cannot be null!!");
                                            }
                                           localNumOfExecutives.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfExecutives"),
                                               xmlWriter);
                                        
                                            if (localNumOfPanoramas==null){
                                                 throw new org.apache.axis2.databinding.ADBException("numOfPanoramas cannot be null!!");
                                            }
                                           localNumOfPanoramas.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfPanoramas"),
                                               xmlWriter);
                                         if (localEnableGuestLoginTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "enableGuestLogin", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableGuestLogin));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAllowedTenantListTracker){
                             if (localAllowedTenantList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localAllowedTenantList.length;i++){
                                        
                                                   if (localAllowedTenantList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "allowedTenantList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedTenantList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://portal.vidyo.com/superapi/";
                                                            writeStartElement(null, namespace, "allowedTenantList", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://portal.vidyo.com/superapi/", "allowedTenantList", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        }
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "vidyoManager", xmlWriter);
                             
                                               if (localVidyoManager==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("vidyoManager cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoManager));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localVidyoProxyListTracker){
                             if (localVidyoProxyList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localVidyoProxyList.length;i++){
                                        
                                                   if (localVidyoProxyList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "vidyoProxyList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoProxyList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://portal.vidyo.com/superapi/";
                                                            writeStartElement(null, namespace, "vidyoProxyList", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://portal.vidyo.com/superapi/", "vidyoProxyList", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        } if (localDefaultGuestProxyTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "defaultGuestProxy", xmlWriter);
                             

                                          if (localDefaultGuestProxy==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("defaultGuestProxy cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultGuestProxy));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAllowedVidyoGatewayListTracker){
                             if (localAllowedVidyoGatewayList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localAllowedVidyoGatewayList.length;i++){
                                        
                                                   if (localAllowedVidyoGatewayList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "allowedVidyoGatewayList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoGatewayList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://portal.vidyo.com/superapi/";
                                                            writeStartElement(null, namespace, "allowedVidyoGatewayList", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://portal.vidyo.com/superapi/", "allowedVidyoGatewayList", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        } if (localAllowedVidyoReplayRecorderListTracker){
                             if (localAllowedVidyoReplayRecorderList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localAllowedVidyoReplayRecorderList.length;i++){
                                        
                                                   if (localAllowedVidyoReplayRecorderList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "allowedVidyoReplayRecorderList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoReplayRecorderList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://portal.vidyo.com/superapi/";
                                                            writeStartElement(null, namespace, "allowedVidyoReplayRecorderList", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://portal.vidyo.com/superapi/", "allowedVidyoReplayRecorderList", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        } if (localAllowedVidyoRepalyListTracker){
                             if (localAllowedVidyoRepalyList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localAllowedVidyoRepalyList.length;i++){
                                        
                                                   if (localAllowedVidyoRepalyList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "allowedVidyoRepalyList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoRepalyList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://portal.vidyo.com/superapi/";
                                                            writeStartElement(null, namespace, "allowedVidyoRepalyList", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://portal.vidyo.com/superapi/", "allowedVidyoRepalyList", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        }
                             if (localAllowedLocationTagList!=null) {
                                   namespace = "http://portal.vidyo.com/superapi/";
                                   for (int i = 0;i < localAllowedLocationTagList.length;i++){
                                        
                                                   if (localAllowedLocationTagList[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "allowedLocationTagList", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedLocationTagList[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           throw new org.apache.axis2.databinding.ADBException("allowedLocationTagList cannot be null!!");
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("allowedLocationTagList cannot be null!!");
                                    
                             }

                         if (localDefaultGuestLocationTagTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "defaultGuestLocationTag", xmlWriter);
                             

                                          if (localDefaultGuestLocationTag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("defaultGuestLocationTag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultGuestLocationTag));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVidyoMobileAllowedTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "vidyoMobileAllowed", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoMobileAllowed));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIpcAllowOutboundTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "ipcAllowOutbound", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpcAllowOutbound));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIpcAllowInboundTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "ipcAllowInbound", xmlWriter);
                             
                                               if (false) {
                                           
                                                         writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpcAllowInbound));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNumOfPublicRoomsTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "numOfPublicRooms", xmlWriter);
                             

                                          if (localNumOfPublicRooms==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOfPublicRooms));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTenantWebRTCURLTracker){
                                    if (localTenantWebRTCURL==null){

                                        writeStartElement(null, "http://portal.vidyo.com/superapi/", "tenantWebRTCURL", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localTenantWebRTCURL.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantWebRTCURL"),
                                        xmlWriter);
                                    }
                                } if (localAdminUserTracker){
                                    if (localAdminUser==null){

                                        writeStartElement(null, "http://portal.vidyo.com/superapi/", "adminUser", xmlWriter);

                                       // write the nil attribute
                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                      xmlWriter.writeEndElement();
                                    }else{
                                     localAdminUser.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","adminUser"),
                                        xmlWriter);
                                    }
                                } if (localExternalEndpointSoftwareFileserverTracker){
                                            if (localExternalEndpointSoftwareFileserver==null){
                                                 throw new org.apache.axis2.databinding.ADBException("externalEndpointSoftwareFileserver cannot be null!!");
                                            }
                                           localExternalEndpointSoftwareFileserver.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","externalEndpointSoftwareFileserver"),
                                               xmlWriter);
                                        } if (localEnableCustomRoleTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "enableCustomRole", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("enableCustomRole cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableCustomRole));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEnableEndpointLogAggregationTracker){
                                    namespace = "http://portal.vidyo.com/superapi/";
                                    writeStartElement(null, namespace, "enableEndpointLogAggregation", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("enableEndpointLogAggregation cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableEndpointLogAggregation));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobileLoginModeTracker){
                                            if (localMobileLoginMode==null){
                                                 throw new org.apache.axis2.databinding.ADBException("mobileLoginMode cannot be null!!");
                                            }
                                           localMobileLoginMode.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","mobileLoginMode"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://portal.vidyo.com/superapi/")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "tenantName"));
                            
                            
                                    if (localTenantName==null){
                                         throw new org.apache.axis2.databinding.ADBException("tenantName cannot be null!!");
                                    }
                                    elementList.add(localTenantName);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "tenantUrl"));
                            
                            
                                    if (localTenantUrl==null){
                                         throw new org.apache.axis2.databinding.ADBException("tenantUrl cannot be null!!");
                                    }
                                    elementList.add(localTenantUrl);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "extensionPrefix"));
                            
                            
                                    if (localExtensionPrefix==null){
                                         throw new org.apache.axis2.databinding.ADBException("extensionPrefix cannot be null!!");
                                    }
                                    elementList.add(localExtensionPrefix);
                                 if (localDialinNumberTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "dialinNumber"));
                            
                            
                                    elementList.add(localDialinNumber==null?null:
                                    localDialinNumber);
                                } if (localVidyoReplayUrlTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "vidyoReplayUrl"));
                            
                            
                                    elementList.add(localVidyoReplayUrl==null?null:
                                    localVidyoReplayUrl);
                                } if (localDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "description"));
                                 
                                         elementList.add(localDescription==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescription));
                                    } if (localVidyoGatewayControllerDnsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "vidyoGatewayControllerDns"));
                            
                            
                                    elementList.add(localVidyoGatewayControllerDns==null?null:
                                    localVidyoGatewayControllerDns);
                                }
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfInstalls"));
                            
                            
                                    if (localNumOfInstalls==null){
                                         throw new org.apache.axis2.databinding.ADBException("numOfInstalls cannot be null!!");
                                    }
                                    elementList.add(localNumOfInstalls);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfSeats"));
                            
                            
                                    if (localNumOfSeats==null){
                                         throw new org.apache.axis2.databinding.ADBException("numOfSeats cannot be null!!");
                                    }
                                    elementList.add(localNumOfSeats);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfLines"));
                            
                            
                                    if (localNumOfLines==null){
                                         throw new org.apache.axis2.databinding.ADBException("numOfLines cannot be null!!");
                                    }
                                    elementList.add(localNumOfLines);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfExecutives"));
                            
                            
                                    if (localNumOfExecutives==null){
                                         throw new org.apache.axis2.databinding.ADBException("numOfExecutives cannot be null!!");
                                    }
                                    elementList.add(localNumOfExecutives);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfPanoramas"));
                            
                            
                                    if (localNumOfPanoramas==null){
                                         throw new org.apache.axis2.databinding.ADBException("numOfPanoramas cannot be null!!");
                                    }
                                    elementList.add(localNumOfPanoramas);
                                 if (localEnableGuestLoginTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "enableGuestLogin"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableGuestLogin));
                            } if (localAllowedTenantListTracker){
                            if (localAllowedTenantList!=null){
                                  for (int i = 0;i < localAllowedTenantList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "allowedTenantList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedTenantList[i]));

                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                              "allowedTenantList"));
                                    elementList.add(null);
                                
                            }

                        }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "vidyoManager"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoManager));
                             if (localVidyoProxyListTracker){
                            if (localVidyoProxyList!=null){
                                  for (int i = 0;i < localVidyoProxyList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "vidyoProxyList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoProxyList[i]));

                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                              "vidyoProxyList"));
                                    elementList.add(null);
                                
                            }

                        } if (localDefaultGuestProxyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "defaultGuestProxy"));
                                 
                                        if (localDefaultGuestProxy != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultGuestProxy));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("defaultGuestProxy cannot be null!!");
                                        }
                                    } if (localAllowedVidyoGatewayListTracker){
                            if (localAllowedVidyoGatewayList!=null){
                                  for (int i = 0;i < localAllowedVidyoGatewayList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "allowedVidyoGatewayList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoGatewayList[i]));

                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                              "allowedVidyoGatewayList"));
                                    elementList.add(null);
                                
                            }

                        } if (localAllowedVidyoReplayRecorderListTracker){
                            if (localAllowedVidyoReplayRecorderList!=null){
                                  for (int i = 0;i < localAllowedVidyoReplayRecorderList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "allowedVidyoReplayRecorderList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoReplayRecorderList[i]));

                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                              "allowedVidyoReplayRecorderList"));
                                    elementList.add(null);
                                
                            }

                        } if (localAllowedVidyoRepalyListTracker){
                            if (localAllowedVidyoRepalyList!=null){
                                  for (int i = 0;i < localAllowedVidyoRepalyList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "allowedVidyoRepalyList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedVidyoRepalyList[i]));

                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                              "allowedVidyoRepalyList"));
                                    elementList.add(null);
                                
                            }

                        }
                            if (localAllowedLocationTagList!=null){
                                  for (int i = 0;i < localAllowedLocationTagList.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                                                                       "allowedLocationTagList"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowedLocationTagList[i]));

                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("allowedLocationTagList cannot be null!!");
                                
                            }

                         if (localDefaultGuestLocationTagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "defaultGuestLocationTag"));
                                 
                                        if (localDefaultGuestLocationTag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDefaultGuestLocationTag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("defaultGuestLocationTag cannot be null!!");
                                        }
                                    } if (localVidyoMobileAllowedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "vidyoMobileAllowed"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVidyoMobileAllowed));
                            } if (localIpcAllowOutboundTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "ipcAllowOutbound"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpcAllowOutbound));
                            } if (localIpcAllowInboundTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "ipcAllowInbound"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIpcAllowInbound));
                            } if (localNumOfPublicRoomsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "numOfPublicRooms"));
                                 
                                         elementList.add(localNumOfPublicRooms==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNumOfPublicRooms));
                                    } if (localTenantWebRTCURLTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "tenantWebRTCURL"));
                            
                            
                                    elementList.add(localTenantWebRTCURL==null?null:
                                    localTenantWebRTCURL);
                                } if (localAdminUserTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "adminUser"));
                            
                            
                                    elementList.add(localAdminUser==null?null:
                                    localAdminUser);
                                } if (localExternalEndpointSoftwareFileserverTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "externalEndpointSoftwareFileserver"));
                            
                            
                                    if (localExternalEndpointSoftwareFileserver==null){
                                         throw new org.apache.axis2.databinding.ADBException("externalEndpointSoftwareFileserver cannot be null!!");
                                    }
                                    elementList.add(localExternalEndpointSoftwareFileserver);
                                } if (localEnableCustomRoleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "enableCustomRole"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableCustomRole));
                            } if (localEnableEndpointLogAggregationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "enableEndpointLogAggregation"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnableEndpointLogAggregation));
                            } if (localMobileLoginModeTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/",
                                                                      "mobileLoginMode"));
                            
                            
                                    if (localMobileLoginMode==null){
                                         throw new org.apache.axis2.databinding.ADBException("mobileLoginMode cannot be null!!");
                                    }
                                    elementList.add(localMobileLoginMode);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static TenantDataType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            TenantDataType object =
                new TenantDataType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"TenantDataType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (TenantDataType)com.vidyo.portal.superapi.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list14 = new java.util.ArrayList();
                    
                        java.util.ArrayList list16 = new java.util.ArrayList();
                    
                        java.util.ArrayList list18 = new java.util.ArrayList();
                    
                        java.util.ArrayList list19 = new java.util.ArrayList();
                    
                        java.util.ArrayList list20 = new java.util.ArrayList();
                    
                        java.util.ArrayList list21 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantName").equals(reader.getName())){
                                
                                                object.setTenantName(com.vidyo.portal.superapi.TenantNamePattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantUrl").equals(reader.getName())){
                                
                                                object.setTenantUrl(com.vidyo.portal.superapi.TenantUrlPattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","extensionPrefix").equals(reader.getName())){
                                
                                                object.setExtensionPrefix(com.vidyo.portal.superapi.TenantExtensionPrefixPattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","dialinNumber").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setDialinNumber(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setDialinNumber(com.vidyo.portal.superapi.String20.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoReplayUrl").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setVidyoReplayUrl(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setVidyoReplayUrl(com.vidyo.portal.superapi.TenantUrlPattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","description").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoGatewayControllerDns").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setVidyoGatewayControllerDns(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setVidyoGatewayControllerDns(com.vidyo.portal.superapi.TenantUrlPattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfInstalls").equals(reader.getName())){
                                
                                                object.setNumOfInstalls(com.vidyo.portal.superapi.NonNegativeInt.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfSeats").equals(reader.getName())){
                                
                                                object.setNumOfSeats(com.vidyo.portal.superapi.NonNegativeInt.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfLines").equals(reader.getName())){
                                
                                                object.setNumOfLines(com.vidyo.portal.superapi.NonNegativeInt.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfExecutives").equals(reader.getName())){
                                
                                                object.setNumOfExecutives(com.vidyo.portal.superapi.NonNegativeInt.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfPanoramas").equals(reader.getName())){
                                
                                                object.setNumOfPanoramas(com.vidyo.portal.superapi.NonNegativeInt.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","enableGuestLogin").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEnableGuestLogin(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedTenantList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list14.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                       
                                                  reader.next();
                                              } else {
                                            list14.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone14 = false;
                                            while(!loopDone14){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone14 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedTenantList").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list14.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list14.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone14 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setAllowedTenantList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list14));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoManager").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"vidyoManager" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVidyoManager(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoProxyList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list16.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                       
                                                  reader.next();
                                              } else {
                                            list16.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone16 = false;
                                            while(!loopDone16){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone16 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoProxyList").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list16.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list16.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone16 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setVidyoProxyList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list16));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","defaultGuestProxy").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"defaultGuestProxy" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDefaultGuestProxy(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToNonNegativeInteger(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoGatewayList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list18.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                       
                                                  reader.next();
                                              } else {
                                            list18.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone18 = false;
                                            while(!loopDone18){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone18 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoGatewayList").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list18.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list18.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone18 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setAllowedVidyoGatewayList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list18));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoReplayRecorderList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list19.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                       
                                                  reader.next();
                                              } else {
                                            list19.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone19 = false;
                                            while(!loopDone19){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone19 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoReplayRecorderList").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list19.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list19.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone19 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setAllowedVidyoReplayRecorderList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list19));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoRepalyList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list20.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                       
                                                  reader.next();
                                              } else {
                                            list20.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone20 = false;
                                            while(!loopDone20){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone20 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedVidyoRepalyList").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list20.add(String.valueOf(java.lang.Integer.MIN_VALUE));
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list20.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone20 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setAllowedVidyoRepalyList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list20));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedLocationTagList").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list21.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone21 = false;
                                            while(!loopDone21){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone21 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","allowedLocationTagList").equals(reader.getName())){
                                                         list21.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone21 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setAllowedLocationTagList((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list21));
                                                
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","defaultGuestLocationTag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"defaultGuestLocationTag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDefaultGuestLocationTag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToNonNegativeInteger(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","vidyoMobileAllowed").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVidyoMobileAllowed(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","ipcAllowOutbound").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIpcAllowOutbound(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","ipcAllowInbound").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIpcAllowInbound(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","numOfPublicRooms").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNumOfPublicRooms(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToNonNegativeInteger(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","tenantWebRTCURL").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setTenantWebRTCURL(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setTenantWebRTCURL(com.vidyo.portal.superapi.TenantUrlPattern.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","adminUser").equals(reader.getName())){
                                
                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                          object.setAdminUser(null);
                                          reader.next();
                                            
                                            reader.next();
                                          
                                      }else{
                                    
                                                object.setAdminUser(com.vidyo.portal.superapi.AdminMember.Factory.parse(reader));
                                              
                                        reader.next();
                                    }
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","externalEndpointSoftwareFileserver").equals(reader.getName())){
                                
                                                object.setExternalEndpointSoftwareFileserver(com.vidyo.portal.superapi.ExternalEndpointSoftwareFileserver.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","enableCustomRole").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"enableCustomRole" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEnableCustomRole(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","enableEndpointLogAggregation").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"enableEndpointLogAggregation" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEnableEndpointLogAggregation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/superapi/","mobileLoginMode").equals(reader.getName())){
                                
                                                object.setMobileLoginMode(com.vidyo.portal.superapi.MobileLoginMode.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    