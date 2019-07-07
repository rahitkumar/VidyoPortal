
/**
 * LogInAsGuestResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.portal.guest;
            

            /**
            *  LogInAsGuestResponse bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LogInAsGuestResponse
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://portal.vidyo.com/guest",
                "LogInAsGuestResponse",
                "ns1");

            

                        /**
                        * field for GuestID
                        */

                        
                                    protected int localGuestID ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getGuestID(){
                               return localGuestID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GuestID
                               */
                               public void setGuestID(int param){
                            
                                            this.localGuestID=param;
                                    

                               }
                            

                        /**
                        * field for IsLocked
                        */

                        
                                    protected boolean localIsLocked ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIsLocked(){
                               return localIsLocked;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IsLocked
                               */
                               public void setIsLocked(boolean param){
                            
                                            this.localIsLocked=param;
                                    

                               }
                            

                        /**
                        * field for HasPin
                        */

                        
                                    protected boolean localHasPin ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getHasPin(){
                               return localHasPin;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HasPin
                               */
                               public void setHasPin(boolean param){
                            
                                            this.localHasPin=param;
                                    

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
                        * field for Un
                        */

                        
                                    protected java.lang.String localUn ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUn(){
                               return localUn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Un
                               */
                               public void setUn(java.lang.String param){
                            
                                            this.localUn=param;
                                    

                               }
                            

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
                        * field for Portal
                        */

                        
                                    protected java.lang.String localPortal ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPortal(){
                               return localPortal;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Portal
                               */
                               public void setPortal(java.lang.String param){
                            
                                            this.localPortal=param;
                                    

                               }
                            

                        /**
                        * field for PortalVersion
                        */

                        
                                    protected java.lang.String localPortalVersion ;
                                

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
                            
                                            this.localPortalVersion=param;
                                    

                               }
                            

                        /**
                        * field for ExtraElement
                        */

                        
                                    protected org.apache.axiom.om.OMElement localExtraElement ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExtraElementTracker = false ;

                           public boolean isExtraElementSpecified(){
                               return localExtraElementTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axiom.om.OMElement
                           */
                           public  org.apache.axiom.om.OMElement getExtraElement(){
                               return localExtraElement;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExtraElement
                               */
                               public void setExtraElement(org.apache.axiom.om.OMElement param){
                            localExtraElementTracker = param != null;
                                   
                                            this.localExtraElement=param;
                                    

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
                            localPak2Tracker = param != null;
                                   
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
                        * field for RoomName
                        */

                        
                                    protected java.lang.String localRoomName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRoomNameTracker = false ;

                           public boolean isRoomNameSpecified(){
                               return localRoomNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRoomName(){
                               return localRoomName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RoomName
                               */
                               public void setRoomName(java.lang.String param){
                            localRoomNameTracker = param != null;
                                   
                                            this.localRoomName=param;
                                    

                               }
                            

                        /**
                        * field for RoomDisplayName
                        */

                        
                                    protected java.lang.String localRoomDisplayName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRoomDisplayNameTracker = false ;

                           public boolean isRoomDisplayNameSpecified(){
                               return localRoomDisplayNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRoomDisplayName(){
                               return localRoomDisplayName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RoomDisplayName
                               */
                               public void setRoomDisplayName(java.lang.String param){
                            localRoomDisplayNameTracker = param != null;
                                   
                                            this.localRoomDisplayName=param;
                                    

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

                        
                                    protected com.vidyo.portal.guest.EndpointBehaviorDataType localEndpointBehavior ;
                                
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
                           * @return com.vidyo.portal.guest.EndpointBehaviorDataType
                           */
                           public  com.vidyo.portal.guest.EndpointBehaviorDataType getEndpointBehavior(){
                               return localEndpointBehavior;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointBehavior
                               */
                               public void setEndpointBehavior(com.vidyo.portal.guest.EndpointBehaviorDataType param){
                            localEndpointBehaviorTracker = param != null;
                                   
                                            this.localEndpointBehavior=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://portal.vidyo.com/guest");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":LogInAsGuestResponse",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "LogInAsGuestResponse",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "guestID", xmlWriter);
                             
                                               if (localGuestID==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("guestID cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGuestID));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "isLocked", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("isLocked cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsLocked));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "hasPin", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("hasPin cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHasPin));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localVmaddressTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "vmaddress", xmlWriter);
                             

                                          if (localVmaddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVmaddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProxyaddressTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "proxyaddress", xmlWriter);
                             

                                          if (localProxyaddress==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProxyaddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoctagTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "loctag", xmlWriter);
                             

                                          if (localLoctag==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoctag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "un", xmlWriter);
                             

                                          if (localUn==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("un cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUn);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "pak", xmlWriter);
                             

                                          if (localPak==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pak cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPak);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "portal", xmlWriter);
                             

                                          if (localPortal==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("portal cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPortal);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "portalVersion", xmlWriter);
                             

                                          if (localPortalVersion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("portalVersion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPortalVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localExtraElementTracker){
                            
                            if (localExtraElement != null) {
                                localExtraElement.serialize(xmlWriter);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
                            }
                        } if (localPak2Tracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "pak2", xmlWriter);
                             

                                          if (localPak2==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("pak2 cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPak2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEndpointExternalIPAddressTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "endpointExternalIPAddress", xmlWriter);
                             

                                          if (localEndpointExternalIPAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("endpointExternalIPAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEndpointExternalIPAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRoomNameTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "roomName", xmlWriter);
                             

                                          if (localRoomName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("roomName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRoomName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRoomDisplayNameTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "roomDisplayName", xmlWriter);
                             

                                          if (localRoomDisplayName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("roomDisplayName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRoomDisplayName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMinMediaPortTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "minMediaPort", xmlWriter);
                             
                                               if (localMinMediaPort==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("minMediaPort cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinMediaPort));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaxMediaPortTracker){
                                    namespace = "http://portal.vidyo.com/guest";
                                    writeStartElement(null, namespace, "maxMediaPort", xmlWriter);
                             
                                               if (localMaxMediaPort==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("maxMediaPort cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxMediaPort));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVrProxyConfigTracker){
                                    namespace = "http://portal.vidyo.com/guest";
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
                                           localEndpointBehavior.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/guest","endpointBehavior"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://portal.vidyo.com/guest")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "guestID"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGuestID));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "isLocked"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsLocked));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "hasPin"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHasPin));
                             if (localVmaddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "vmaddress"));
                                 
                                         elementList.add(localVmaddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVmaddress));
                                    } if (localProxyaddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "proxyaddress"));
                                 
                                         elementList.add(localProxyaddress==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProxyaddress));
                                    } if (localLoctagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "loctag"));
                                 
                                         elementList.add(localLoctag==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoctag));
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "un"));
                                 
                                        if (localUn != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUn));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("un cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "pak"));
                                 
                                        if (localPak != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pak cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "portal"));
                                 
                                        if (localPortal != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPortal));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("portal cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "portalVersion"));
                                 
                                        if (localPortalVersion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPortalVersion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("portalVersion cannot be null!!");
                                        }
                                     if (localExtraElementTracker){
                            if (localExtraElement != null){
                                elementList.add(org.apache.axis2.databinding.utils.Constants.OM_ELEMENT_KEY);
                                elementList.add(localExtraElement);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
                            }
                        } if (localPak2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "pak2"));
                                 
                                        if (localPak2 != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak2));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("pak2 cannot be null!!");
                                        }
                                    } if (localEndpointExternalIPAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "endpointExternalIPAddress"));
                                 
                                        if (localEndpointExternalIPAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndpointExternalIPAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("endpointExternalIPAddress cannot be null!!");
                                        }
                                    } if (localRoomNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "roomName"));
                                 
                                        if (localRoomName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRoomName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("roomName cannot be null!!");
                                        }
                                    } if (localRoomDisplayNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "roomDisplayName"));
                                 
                                        if (localRoomDisplayName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRoomDisplayName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("roomDisplayName cannot be null!!");
                                        }
                                    } if (localMinMediaPortTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "minMediaPort"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinMediaPort));
                            } if (localMaxMediaPortTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "maxMediaPort"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxMediaPort));
                            } if (localVrProxyConfigTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "vrProxyConfig"));
                                 
                                        if (localVrProxyConfig != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVrProxyConfig));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("vrProxyConfig cannot be null!!");
                                        }
                                    } if (localEndpointBehaviorTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/guest",
                                                                      "endpointBehavior"));
                            
                            
                                    if (localEndpointBehavior==null){
                                         throw new org.apache.axis2.databinding.ADBException("endpointBehavior cannot be null!!");
                                    }
                                    elementList.add(localEndpointBehavior);
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
        public static LogInAsGuestResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LogInAsGuestResponse object =
                new LogInAsGuestResponse();

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
                    
                            if (!"LogInAsGuestResponse".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LogInAsGuestResponse)com.vidyo.portal.guest.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","guestID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"guestID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGuestID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","isLocked").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"isLocked" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIsLocked(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","hasPin").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"hasPin" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHasPin(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","vmaddress").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","proxyaddress").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","loctag").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","un").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"un" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","pak").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","portal").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"portal" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPortal(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","portalVersion").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"portalVersion" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPortalVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                   if (reader.isStartElement()){
                                
                                    
                                     
                                     //use the QName from the parser as the name for the builder
                                     javax.xml.namespace.QName startQname11 = reader.getName();

                                     // We need to wrap the reader so that it produces a fake START_DOCUMENT event
                                     // this is needed by the builder classes
                                     org.apache.axis2.databinding.utils.NamedStaxOMBuilder builder11 =
                                         new org.apache.axis2.databinding.utils.NamedStaxOMBuilder(
                                             new org.apache.axis2.util.StreamWrapper(reader),startQname11);
                                     object.setExtraElement(builder11.getOMElement());
                                       
                                         reader.next();
                                     
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","pak2").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"pak2" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPak2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","endpointExternalIPAddress").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","roomName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"roomName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRoomName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","roomDisplayName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"roomDisplayName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRoomDisplayName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","minMediaPort").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","maxMediaPort").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","vrProxyConfig").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/guest","endpointBehavior").equals(reader.getName())){
                                
                                                object.setEndpointBehavior(com.vidyo.portal.guest.EndpointBehaviorDataType.Factory.parse(reader));
                                              
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
           
    