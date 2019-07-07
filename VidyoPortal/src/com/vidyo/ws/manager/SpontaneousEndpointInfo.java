
/**
 * SpontaneousEndpointInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.ws.manager;
            

            /**
            *  SpontaneousEndpointInfo bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class SpontaneousEndpointInfo
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = SpontaneousEndpointInfo
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
                        * field for IsSecure
                        */

                        
                                    protected boolean localIsSecure ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIsSecure(){
                               return localIsSecure;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IsSecure
                               */
                               public void setIsSecure(boolean param){
                            
                                            this.localIsSecure=param;
                                    

                               }
                            

                        /**
                        * field for LocalAddress
                        */

                        
                                    protected java.lang.String localLocalAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocalAddress(){
                               return localLocalAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocalAddress
                               */
                               public void setLocalAddress(java.lang.String param){
                            
                                            this.localLocalAddress=param;
                                    

                               }
                            

                        /**
                        * field for ExternalAddress
                        */

                        
                                    protected java.lang.String localExternalAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExternalAddress(){
                               return localExternalAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExternalAddress
                               */
                               public void setExternalAddress(java.lang.String param){
                            
                                            this.localExternalAddress=param;
                                    

                               }
                            

                        /**
                        * field for EndpointName
                        */

                        
                                    protected java.lang.String localEndpointName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEndpointNameTracker = false ;

                           public boolean isEndpointNameSpecified(){
                               return localEndpointNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEndpointName(){
                               return localEndpointName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointName
                               */
                               public void setEndpointName(java.lang.String param){
                            localEndpointNameTracker = param != null;
                                   
                                            this.localEndpointName=param;
                                    

                               }
                            

                        /**
                        * field for LocationTag
                        */

                        
                                    protected java.lang.String localLocationTag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocationTagTracker = false ;

                           public boolean isLocationTagSpecified(){
                               return localLocationTagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocationTag(){
                               return localLocationTag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LocationTag
                               */
                               public void setLocationTag(java.lang.String param){
                            localLocationTagTracker = param != null;
                                   
                                            this.localLocationTag=param;
                                    

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
                           namespacePrefix+":SpontaneousEndpointInfo",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "SpontaneousEndpointInfo",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localEndpointGUID==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EndpointGUID cannot be null!!");
                                            }
                                           localEndpointGUID.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointGUID"),
                                               xmlWriter);
                                        
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "IsSecure", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("IsSecure cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsSecure));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "LocalAddress", xmlWriter);
                             

                                          if (localLocalAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("LocalAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocalAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "ExternalAddress", xmlWriter);
                             

                                          if (localExternalAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ExternalAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExternalAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localEndpointNameTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "EndpointName", xmlWriter);
                             

                                          if (localEndpointName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("EndpointName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEndpointName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLocationTagTracker){
                                    namespace = "http://ws.vidyo.com/manager";
                                    writeStartElement(null, namespace, "LocationTag", xmlWriter);
                             

                                          if (localLocationTag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("LocationTag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocationTag);
                                            
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
                                                                      "IsSecure"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIsSecure));
                            
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "LocalAddress"));
                                 
                                        if (localLocalAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocalAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("LocalAddress cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "ExternalAddress"));
                                 
                                        if (localExternalAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ExternalAddress cannot be null!!");
                                        }
                                     if (localEndpointNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "EndpointName"));
                                 
                                        if (localEndpointName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndpointName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("EndpointName cannot be null!!");
                                        }
                                    } if (localLocationTagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                                      "LocationTag"));
                                 
                                        if (localLocationTag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocationTag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("LocationTag cannot be null!!");
                                        }
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
        public static SpontaneousEndpointInfo parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            SpontaneousEndpointInfo object =
                new SpontaneousEndpointInfo();

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
                    
                            if (!"SpontaneousEndpointInfo".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SpontaneousEndpointInfo)com.vidyo.ws.manager.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","IsSecure").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IsSecure" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIsSecure(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","LocalAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LocalAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocalAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ExternalAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ExternalAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExternalAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"EndpointName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEndpointName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/manager","LocationTag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LocationTag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocationTag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
           
    