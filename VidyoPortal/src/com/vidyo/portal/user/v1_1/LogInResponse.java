
/**
 * LogInResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.portal.user.v1_1;
            

            /**
            *  LogInResponse bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LogInResponse
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://portal.vidyo.com/user/v1_1",
                "LogInResponse",
                "ns1");

            

                        /**
                        * field for Pak
                        */

                        
                                    protected java.lang.String localPak ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPak(){
                               return localPak;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Pak
                               */
                               public void setPak(java.lang.String param){
                            
                                            this.localPak=param;
                                    

                               }
                            

                        /**
                        * field for Vmaddress
                        */

                        
                                    protected java.lang.String localVmaddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVmaddressTracker = false ;

                           public boolean isVmaddressSpecified(){
                               return localVmaddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVmaddress(){
                               return localVmaddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Vmaddress
                               */
                               public void setVmaddress(java.lang.String param){
                            localVmaddressTracker = true;
                                   
                                            this.localVmaddress=param;
                                    

                               }
                            

                        /**
                        * field for Proxyaddress
                        */

                        
                                    protected java.lang.String localProxyaddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProxyaddressTracker = false ;

                           public boolean isProxyaddressSpecified(){
                               return localProxyaddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProxyaddress(){
                               return localProxyaddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Proxyaddress
                               */
                               public void setProxyaddress(java.lang.String param){
                            localProxyaddressTracker = true;
                                   
                                            this.localProxyaddress=param;
                                    

                               }
                            

                        /**
                        * field for Loctag
                        */

                        
                                    protected java.lang.String localLoctag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoctagTracker = false ;

                           public boolean isLoctagSpecified(){
                               return localLoctagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoctag(){
                               return localLoctag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Loctag
                               */
                               public void setLoctag(java.lang.String param){
                            localLoctagTracker = true;
                                   
                                            this.localLoctag=param;
                                    

                               }
                            

                        /**
                        * field for Pak2
                        */

                        
                                    protected java.lang.String localPak2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPak2Tracker = false ;

                           public boolean isPak2Specified(){
                               return localPak2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPak2(){
                               return localPak2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Pak2
                               */
                               public void setPak2(java.lang.String param){
                            localPak2Tracker = true;
                                   
                                            this.localPak2=param;
                                    

                               }
                            

                        /**
                        * field for EndpointExternalIPAddress
                        */

                        
                                    protected java.lang.String localEndpointExternalIPAddress ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEndpointExternalIPAddressTracker = false ;

                           public boolean isEndpointExternalIPAddressSpecified(){
                               return localEndpointExternalIPAddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEndpointExternalIPAddress(){
                               return localEndpointExternalIPAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointExternalIPAddress
                               */
                               public void setEndpointExternalIPAddress(java.lang.String param){
                            localEndpointExternalIPAddressTracker = param != null;
                                   
                                            this.localEndpointExternalIPAddress=param;
                                    

                               }
                            

                        /**
                        * field for MinimumPINLength
                        */

                        
                                    protected int localMinimumPINLength ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMinimumPINLengthTracker = false ;

                           public boolean isMinimumPINLengthSpecified(){
                               return localMinimumPINLengthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getMinimumPINLength(){
                               return localMinimumPINLength;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MinimumPINLength
                               */
                               public void setMinimumPINLength(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localMinimumPINLengthTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localMinimumPINLength=param;
                                    

                               }
                            

                        /**
                        * field for MaximumPINLength
                        */

                        
                                    protected int localMaximumPINLength ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaximumPINLengthTracker = false ;

                           public boolean isMaximumPINLengthSpecified(){
                               return localMaximumPINLengthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getMaximumPINLength(){
                               return localMaximumPINLength;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaximumPINLength
                               */
                               public void setMaximumPINLength(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localMaximumPINLengthTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localMaximumPINLength=param;
                                    

                               }
                            

                        /**
                        * field for MinMediaPort
                        */

                        
                                    protected int localMinMediaPort ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMinMediaPortTracker = false ;

                           public boolean isMinMediaPortSpecified(){
                               return localMinMediaPortTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getMinMediaPort(){
                               return localMinMediaPort;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MinMediaPort
                               */
                               public void setMinMediaPort(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localMinMediaPortTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localMinMediaPort=param;
                                    

                               }
                            

                        /**
                        * field for MaxMediaPort
                        */

                        
                                    protected int localMaxMediaPort ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxMediaPortTracker = false ;

                           public boolean isMaxMediaPortSpecified(){
                               return localMaxMediaPortTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getMaxMediaPort(){
                               return localMaxMediaPort;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxMediaPort
                               */
                               public void setMaxMediaPort(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localMaxMediaPortTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localMaxMediaPort=param;
                                    

                               }
                            

                        /**
                        * field for VrProxyConfig
                        */

                        
                                    protected java.lang.String localVrProxyConfig ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVrProxyConfigTracker = false ;

                           public boolean isVrProxyConfigSpecified(){
                               return localVrProxyConfigTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVrProxyConfig(){
                               return localVrProxyConfig;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VrProxyConfig
                               */
                               public void setVrProxyConfig(java.lang.String param){
                            localVrProxyConfigTracker = param != null;
                                   
                                            this.localVrProxyConfig=param;
                                    

                               }
                            

                        /**
                        * field for EndpointBehavior
                        */

                        
                                    protected com.vidyo.portal.user.v1_1.EndpointBehaviorDataType localEndpointBehavior ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEndpointBehaviorTracker = false ;

                           public boolean isEndpointBehaviorSpecified(){
                               return localEndpointBehaviorTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.user.v1_1.EndpointBehaviorDataType
                           */
                           public  com.vidyo.portal.user.v1_1.EndpointBehaviorDataType getEndpointBehavior(){
                               return localEndpointBehavior;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointBehavior
                               */
                               public void setEndpointBehavior(com.vidyo.portal.user.v1_1.EndpointBehaviorDataType param){
                            localEndpointBehaviorTracker = param != null;
                                   
                                            this.localEndpointBehavior=param;
                                    

                               }
                            

                        /**
                        * field for AuthToken
                        */

                        
                                    protected java.lang.String localAuthToken ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthTokenTracker = false ;

                           public boolean isAuthTokenSpecified(){
                               return localAuthTokenTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthToken(){
                               return localAuthToken;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthToken
                               */
                               public void setAuthToken(java.lang.String param){
                            localAuthTokenTracker = param != null;
                                   
                                            this.localAuthToken=param;
                                    

                               }
                            

                        /**
                        * field for PortalVersion
                        */

                        
                                    protected java.lang.String localPortalVersion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPortalVersionTracker = false ;

                           public boolean isPortalVersionSpecified(){
                               return localPortalVersionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPortalVersion(){
                               return localPortalVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PortalVersion
                               */
                               public void setPortalVersion(java.lang.String param){
                            localPortalVersionTracker = param != null;
                                   
                                            this.localPortalVersion=param;
                                    

                               }
                            

                        /**
                        * field for PairingService
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localPairingService ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPairingServiceTracker = false ;

                           public boolean isPairingServiceSpecified(){
                               return localPairingServiceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getPairingService(){
                               return localPairingService;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PairingService
                               */
                               public void setPairingService(org.apache.axis2.databinding.types.URI param){
                            localPairingServiceTracker = param != null;
                                   
                                            this.localPairingService=param;
                                    

                               }
                            

                        /**
                        * field for RegistrationService
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localRegistrationService ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRegistrationServiceTracker = false ;

                           public boolean isRegistrationServiceSpecified(){
                               return localRegistrationServiceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getRegistrationService(){
                               return localRegistrationService;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RegistrationService
                               */
                               public void setRegistrationService(org.apache.axis2.databinding.types.URI param){
                            localRegistrationServiceTracker = param != null;
                                   
                                            this.localRegistrationService=param;
                                    

                               }
                            

                        /**
                        * field for NeoRoomPermanentPairingDeviceUser
                        */

                        
                                    protected boolean localNeoRoomPermanentPairingDeviceUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNeoRoomPermanentPairingDeviceUserTracker = false ;

                           public boolean isNeoRoomPermanentPairingDeviceUserSpecified(){
                               return localNeoRoomPermanentPairingDeviceUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getNeoRoomPermanentPairingDeviceUser(){
                               return localNeoRoomPermanentPairingDeviceUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NeoRoomPermanentPairingDeviceUser
                               */
                               public void setNeoRoomPermanentPairingDeviceUser(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localNeoRoomPermanentPairingDeviceUserTracker =
                                       true;
                                   
                                            this.localNeoRoomPermanentPairingDeviceUser=param;
                                    

                               }
                            

                        /**
                        * field for UserRole
                        */

                        
                                    protected java.lang.String localUserRole ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUserRoleTracker = false ;

                           public boolean isUserRoleSpecified(){
                               return localUserRoleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUserRole(){
                               return localUserRole;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UserRole
                               */
                               public void setUserRole(java.lang.String param){
                            localUserRoleTracker = param != null;
                                   
                                            this.localUserRole=param;
                                    

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
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://portal.vidyo.com/user/v1_1");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":LogInResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "LogInResponse",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "pak", xmlWriter);
                             

                                          if (localPak==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pak cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPak);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localVmaddressTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "vmaddress", xmlWriter);
                             

                                          if (localVmaddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVmaddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProxyaddressTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "proxyaddress", xmlWriter);
                             

                                          if (localProxyaddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProxyaddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoctagTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "loctag", xmlWriter);
                             

                                          if (localLoctag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoctag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPak2Tracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "pak2", xmlWriter);
                             

                                          if (localPak2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPak2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEndpointExternalIPAddressTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "endpointExternalIPAddress", xmlWriter);
                             

                                          if (localEndpointExternalIPAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("endpointExternalIPAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEndpointExternalIPAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMinimumPINLengthTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "minimumPINLength", xmlWriter);
                             
                                               if (localMinimumPINLength==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("minimumPINLength cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinimumPINLength));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaximumPINLengthTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "maximumPINLength", xmlWriter);
                             
                                               if (localMaximumPINLength==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maximumPINLength cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaximumPINLength));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMinMediaPortTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "minMediaPort", xmlWriter);
                             
                                               if (localMinMediaPort==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("minMediaPort cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinMediaPort));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaxMediaPortTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "maxMediaPort", xmlWriter);
                             
                                               if (localMaxMediaPort==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maxMediaPort cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxMediaPort));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVrProxyConfigTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "vrProxyConfig", xmlWriter);
                             

                                          if (localVrProxyConfig==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("vrProxyConfig cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVrProxyConfig);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEndpointBehaviorTracker){
                                            if (localEndpointBehavior==null){
                                                 throw new org.apache.axis2.databinding.ADBException("endpointBehavior cannot be null!!");
                                            }
                                           localEndpointBehavior.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","endpointBehavior"),
                                               xmlWriter);
                                        } if (localAuthTokenTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "authToken", xmlWriter);
                             

                                          if (localAuthToken==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authToken cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthToken);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPortalVersionTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "portalVersion", xmlWriter);
                             

                                          if (localPortalVersion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("portalVersion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPortalVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPairingServiceTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "pairingService", xmlWriter);
                             

                                          if (localPairingService==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pairingService cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPairingService));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRegistrationServiceTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "registrationService", xmlWriter);
                             

                                          if (localRegistrationService==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("registrationService cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRegistrationService));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNeoRoomPermanentPairingDeviceUserTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "neoRoomPermanentPairingDeviceUser", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("neoRoomPermanentPairingDeviceUser cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNeoRoomPermanentPairingDeviceUser));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUserRoleTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "userRole", xmlWriter);
                             

                                          if (localUserRole==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("userRole cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUserRole);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://portal.vidyo.com/user/v1_1")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "pak"));
                                 
                                        if (localPak != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pak cannot be null!!");
                                        }
                                     if (localVmaddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "vmaddress"));
                                 
                                         elementList.add(localVmaddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVmaddress));
                                    } if (localProxyaddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "proxyaddress"));
                                 
                                         elementList.add(localProxyaddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProxyaddress));
                                    } if (localLoctagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "loctag"));
                                 
                                         elementList.add(localLoctag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoctag));
                                    } if (localPak2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "pak2"));
                                 
                                         elementList.add(localPak2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak2));
                                    } if (localEndpointExternalIPAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "endpointExternalIPAddress"));
                                 
                                        if (localEndpointExternalIPAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndpointExternalIPAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("endpointExternalIPAddress cannot be null!!");
                                        }
                                    } if (localMinimumPINLengthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "minimumPINLength"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinimumPINLength));
                            } if (localMaximumPINLengthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "maximumPINLength"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaximumPINLength));
                            } if (localMinMediaPortTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "minMediaPort"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinMediaPort));
                            } if (localMaxMediaPortTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "maxMediaPort"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxMediaPort));
                            } if (localVrProxyConfigTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "vrProxyConfig"));
                                 
                                        if (localVrProxyConfig != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVrProxyConfig));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("vrProxyConfig cannot be null!!");
                                        }
                                    } if (localEndpointBehaviorTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "endpointBehavior"));
                            
                            
                                    if (localEndpointBehavior==null){
                                         throw new org.apache.axis2.databinding.ADBException("endpointBehavior cannot be null!!");
                                    }
                                    elementList.add(localEndpointBehavior);
                                } if (localAuthTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "authToken"));
                                 
                                        if (localAuthToken != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthToken));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authToken cannot be null!!");
                                        }
                                    } if (localPortalVersionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "portalVersion"));
                                 
                                        if (localPortalVersion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPortalVersion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("portalVersion cannot be null!!");
                                        }
                                    } if (localPairingServiceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "pairingService"));
                                 
                                        if (localPairingService != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPairingService));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pairingService cannot be null!!");
                                        }
                                    } if (localRegistrationServiceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "registrationService"));
                                 
                                        if (localRegistrationService != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRegistrationService));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("registrationService cannot be null!!");
                                        }
                                    } if (localNeoRoomPermanentPairingDeviceUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "neoRoomPermanentPairingDeviceUser"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNeoRoomPermanentPairingDeviceUser));
                            } if (localUserRoleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "userRole"));
                                 
                                        if (localUserRole != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUserRole));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("userRole cannot be null!!");
                                        }
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
        public static LogInResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LogInResponse object =
                new LogInResponse();

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
                    
                            if (!"LogInResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LogInResponse)com.vidyo.portal.user.v1_1.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","pak").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"pak" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPak(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","vmaddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVmaddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","proxyaddress").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProxyaddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","loctag").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoctag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","pak2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPak2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","endpointExternalIPAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"endpointExternalIPAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEndpointExternalIPAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","minimumPINLength").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"minimumPINLength" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMinimumPINLength(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMinimumPINLength(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","maximumPINLength").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maximumPINLength" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaximumPINLength(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMaximumPINLength(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","minMediaPort").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"minMediaPort" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMinMediaPort(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMinMediaPort(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","maxMediaPort").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maxMediaPort" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxMediaPort(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setMaxMediaPort(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","vrProxyConfig").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"vrProxyConfig" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVrProxyConfig(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","endpointBehavior").equals(reader.getName())){
                                
                                                object.setEndpointBehavior(com.vidyo.portal.user.v1_1.EndpointBehaviorDataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","authToken").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authToken" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthToken(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","portalVersion").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"portalVersion" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPortalVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","pairingService").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"pairingService" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPairingService(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","registrationService").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"registrationService" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRegistrationService(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","neoRoomPermanentPairingDeviceUser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"neoRoomPermanentPairingDeviceUser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNeoRoomPermanentPairingDeviceUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","userRole").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"userRole" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUserRole(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
           
    