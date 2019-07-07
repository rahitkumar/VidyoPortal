
/**
 * EndpointCallPref.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.ws.manager;
            

            /**
            *  EndpointCallPref bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class EndpointCallPref
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = EndpointCallPref
                Namespace URI = http://ws.vidyo.com/manager
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for EndpointGUID
                        */

                        
                                    protected com.vidyo.ws.manager.EndpointGUID_type0 localEndpointGUID ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.manager.EndpointGUID_type0
                           */
                           public  com.vidyo.ws.manager.EndpointGUID_type0 getEndpointGUID(){
                               return localEndpointGUID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointGUID
                               */
                               public void setEndpointGUID(com.vidyo.ws.manager.EndpointGUID_type0 param){
                            
                                            this.localEndpointGUID=param;
                                    

                               }
                            

                        /**
                        * field for User
                        */

                        
                                    protected java.lang.String localUser ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUser(){
                               return localUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param User
                               */
                               public void setUser(java.lang.String param){
                            
                                            this.localUser=param;
                                    

                               }
                            

                        /**
                        * field for MaxUplinkBw
                        */

                        
                                    protected java.math.BigInteger localMaxUplinkBw ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxUplinkBwTracker = false ;

                           public boolean isMaxUplinkBwSpecified(){
                               return localMaxUplinkBwTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigInteger
                           */
                           public  java.math.BigInteger getMaxUplinkBw(){
                               return localMaxUplinkBw;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxUplinkBw
                               */
                               public void setMaxUplinkBw(java.math.BigInteger param){
                            localMaxUplinkBwTracker = param != null;
                                   
                                            this.localMaxUplinkBw=param;
                                    

                               }
                            

                        /**
                        * field for MaxDownlinkBw
                        */

                        
                                    protected java.math.BigInteger localMaxDownlinkBw ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaxDownlinkBwTracker = false ;

                           public boolean isMaxDownlinkBwSpecified(){
                               return localMaxDownlinkBwTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigInteger
                           */
                           public  java.math.BigInteger getMaxDownlinkBw(){
                               return localMaxDownlinkBw;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaxDownlinkBw
                               */
                               public void setMaxDownlinkBw(java.math.BigInteger param){
                            localMaxDownlinkBwTracker = param != null;
                                   
                                            this.localMaxDownlinkBw=param;
                                    

                               }
                            

                        /**
                        * field for GroupId
                        * This was an Array!
                        */

                        
                                    protected java.lang.String[] localGroupId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGroupIdTracker = false ;

                           public boolean isGroupIdSpecified(){
                               return localGroupIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String[]
                           */
                           public  java.lang.String[] getGroupId(){
                               return localGroupId;
                           }

                           
                        


                               
                              /**
                               * validate the array for GroupId
                               */
                              protected void validateGroupId(java.lang.String[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param GroupId
                              */
                              public void setGroupId(java.lang.String[] param){
                              
                                   validateGroupId(param);

                               localGroupIdTracker = param != null;
                                      
                                      this.localGroupId=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param java.lang.String
                             */
                             public void addGroupId(java.lang.String param){
                                   if (localGroupId == null){
                                   localGroupId = new java.lang.String[]{};
                                   }

                            
                                 //update the setting tracker
                                localGroupIdTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localGroupId);
                               list.add(param);
                               this.localGroupId =
                             (java.lang.String[])list.toArray(
                            new java.lang.String[list.size()]);

                             }
                             

                        /**
                        * field for ApplicationData
                        */

                        
                                    protected javax.activation.DataHandler localApplicationData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localApplicationDataTracker = false ;

                           public boolean isApplicationDataSpecified(){
                               return localApplicationDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return javax.activation.DataHandler
                           */
                           public  javax.activation.DataHandler getApplicationData(){
                               return localApplicationData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ApplicationData
                               */
                               public void setApplicationData(javax.activation.DataHandler param){
                            localApplicationDataTracker = param != null;
                                   
                                            this.localApplicationData=param;
                                    

                               }
                            

                        /**
                        * field for Clearance
                        */

                        
                                    protected com.vidyo.ws.manager.Clearance_type0 localClearance ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClearanceTracker = false ;

                           public boolean isClearanceSpecified(){
                               return localClearanceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.manager.Clearance_type0
                           */
                           public  com.vidyo.ws.manager.Clearance_type0 getClearance(){
                               return localClearance;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Clearance
                               */
                               public void setClearance(com.vidyo.ws.manager.Clearance_type0 param){
                            localClearanceTracker = param != null;
                                   
                                            this.localClearance=param;
                                    

                               }
                            

                        /**
                        * field for ClientType
                        */

                        
                                    protected com.vidyo.ws.manager.ClientType_type0 localClientType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClientTypeTracker = false ;

                           public boolean isClientTypeSpecified(){
                               return localClientTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.manager.ClientType_type0
                           */
                           public  com.vidyo.ws.manager.ClientType_type0 getClientType(){
                               return localClientType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClientType
                               */
                               public void setClientType(com.vidyo.ws.manager.ClientType_type0 param){
                            localClientTypeTracker = param != null;
                                   
                                            this.localClientType=param;
                                    

                               }
                            

                        /**
                        * field for UserId
                        */

                        
                                    protected java.lang.String localUserId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUserIdTracker = false ;

                           public boolean isUserIdSpecified(){
                               return localUserIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUserId(){
                               return localUserId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UserId
                               */
                               public void setUserId(java.lang.String param){
                            localUserIdTracker = param != null;
                                   
                                            this.localUserId=param;
                                    

                               }
                            

                        /**
                        * field for RouterPools
                        */

                        
                                    protected com.vidyo.ws.manager.RouterPoolPriorityList localRouterPools ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRouterPoolsTracker = false ;

                           public boolean isRouterPoolsSpecified(){
                               return localRouterPoolsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.manager.RouterPoolPriorityList
                           */
                           public  com.vidyo.ws.manager.RouterPoolPriorityList getRouterPools(){
                               return localRouterPools;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RouterPools
                               */
                               public void setRouterPools(com.vidyo.ws.manager.RouterPoolPriorityList param){
                            localRouterPoolsTracker = param != null;
                                   
                                            this.localRouterPools=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ws.vidyo.com/manager");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":EndpointCallPref",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "EndpointCallPref",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localEndpointGUID==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EndpointGUID cannot be null!!");
                                            }
                                           localEndpointGUID.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointGUID"),
                                               xmlWriter);
                                        
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "User", xmlWriter);
                             

                                          if (localUser==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("User cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localMaxUplinkBwTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "MaxUplinkBw", xmlWriter);
                             

                                          if (localMaxUplinkBw==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MaxUplinkBw cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxUplinkBw));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaxDownlinkBwTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "MaxDownlinkBw", xmlWriter);
                             

                                          if (localMaxDownlinkBw==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MaxDownlinkBw cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxDownlinkBw));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGroupIdTracker){
                             if (localGroupId!=null) {
                                   namespace = "http://ws.vidyo.com/manager";
                                   for (int i = 0;i < localGroupId.length;i++){
                                        
                                            if (localGroupId[i] != null){
                                        
                                                writeStartElement(null, namespace, "GroupId", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGroupId[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("GroupId cannot be null!!");
                                    
                             }

                        } if (localApplicationDataTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "ApplicationData", xmlWriter);
                             
                                        
                                    if (localApplicationData!=null)  {
                                       try {
                                           org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter, localApplicationData, null, true);
                                       } catch (java.io.IOException ex) {
                                           throw new javax.xml.stream.XMLStreamException("Unable to read data handler for ApplicationData", ex);
                                       }
                                    } else {
                                         
                                    }
                                 
                                   xmlWriter.writeEndElement();
                             } if (localClearanceTracker){
                                            if (localClearance==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Clearance cannot be null!!");
                                            }
                                           localClearance.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","Clearance"),
                                               xmlWriter);
                                        } if (localClientTypeTracker){
                                            if (localClientType==null){
                                                 throw new org.apache.axis2.databinding.ADBException("ClientType cannot be null!!");
                                            }
                                           localClientType.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ClientType"),
                                               xmlWriter);
                                        } if (localUserIdTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "UserId", xmlWriter);
                             

                                          if (localUserId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("UserId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUserId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRouterPoolsTracker){
                                            if (localRouterPools==null){
                                                 throw new org.apache.axis2.databinding.ADBException("RouterPools cannot be null!!");
                                            }
                                           localRouterPools.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","RouterPools"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://ws.vidyo.com/manager")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "EndpointGUID"));
                            
                            
                                    if (localEndpointGUID==null){
                                         throw new org.apache.axis2.databinding.ADBException("EndpointGUID cannot be null!!");
                                    }
                                    elementList.add(localEndpointGUID);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "User"));
                                 
                                        if (localUser != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUser));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("User cannot be null!!");
                                        }
                                     if (localMaxUplinkBwTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "MaxUplinkBw"));
                                 
                                        if (localMaxUplinkBw != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxUplinkBw));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MaxUplinkBw cannot be null!!");
                                        }
                                    } if (localMaxDownlinkBwTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "MaxDownlinkBw"));
                                 
                                        if (localMaxDownlinkBw != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxDownlinkBw));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MaxDownlinkBw cannot be null!!");
                                        }
                                    } if (localGroupIdTracker){
                            if (localGroupId!=null){
                                  for (int i = 0;i < localGroupId.length;i++){
                                      
                                         if (localGroupId[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                              "GroupId"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGroupId[i]));
                                          } else {
                                             
                                                    // have to do nothing
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("GroupId cannot be null!!");
                                
                            }

                        } if (localApplicationDataTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                        "ApplicationData"));
                                
                            elementList.add(localApplicationData);
                        } if (localClearanceTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "Clearance"));
                            
                            
                                    if (localClearance==null){
                                         throw new org.apache.axis2.databinding.ADBException("Clearance cannot be null!!");
                                    }
                                    elementList.add(localClearance);
                                } if (localClientTypeTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "ClientType"));
                            
                            
                                    if (localClientType==null){
                                         throw new org.apache.axis2.databinding.ADBException("ClientType cannot be null!!");
                                    }
                                    elementList.add(localClientType);
                                } if (localUserIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "UserId"));
                                 
                                        if (localUserId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUserId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("UserId cannot be null!!");
                                        }
                                    } if (localRouterPoolsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "RouterPools"));
                            
                            
                                    if (localRouterPools==null){
                                         throw new org.apache.axis2.databinding.ADBException("RouterPools cannot be null!!");
                                    }
                                    elementList.add(localRouterPools);
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
        public static EndpointCallPref parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            EndpointCallPref object =
                new EndpointCallPref();

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
                    
                            if (!"EndpointCallPref".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (EndpointCallPref)com.vidyo.ws.manager.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list5 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointGUID").equals(reader.getName())){
                                
                                                object.setEndpointGUID(com.vidyo.ws.manager.EndpointGUID_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","User").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"User" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","MaxUplinkBw").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MaxUplinkBw" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxUplinkBw(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","MaxDownlinkBw").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MaxDownlinkBw" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaxDownlinkBw(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GroupId").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list5.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone5 = false;
                                            while(!loopDone5){
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
                                                    loopDone5 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GroupId").equals(reader.getName())){
                                                         list5.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone5 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                                    object.setGroupId((java.lang.String[])
                                                        list5.toArray(new java.lang.String[list5.size()]));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ApplicationData").equals(reader.getName())){
                                
                                            object.setApplicationData(org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","Clearance").equals(reader.getName())){
                                
                                                object.setClearance(com.vidyo.ws.manager.Clearance_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ClientType").equals(reader.getName())){
                                
                                                object.setClientType(com.vidyo.ws.manager.ClientType_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","UserId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"UserId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUserId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","RouterPools").equals(reader.getName())){
                                
                                                object.setRouterPools(com.vidyo.ws.manager.RouterPoolPriorityList.Factory.parse(reader));
                                              
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
           
    