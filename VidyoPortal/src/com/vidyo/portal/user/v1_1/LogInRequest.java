
/**
 * LogInRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.portal.user.v1_1;
            

            /**
            *  LogInRequest bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LogInRequest
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://portal.vidyo.com/user/v1_1",
                "LogInRequest",
                "ns1");

            

                        /**
                        * field for ClientType
                        */

                        
                                    protected com.vidyo.portal.user.v1_1.ClientType_type0 localClientType ;
                                
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
                           * @return com.vidyo.portal.user.v1_1.ClientType_type0
                           */
                           public  com.vidyo.portal.user.v1_1.ClientType_type0 getClientType(){
                               return localClientType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClientType
                               */
                               public void setClientType(com.vidyo.portal.user.v1_1.ClientType_type0 param){
                            localClientTypeTracker = param != null;
                                   
                                            this.localClientType=param;
                                    

                               }
                            

                        /**
                        * field for ReturnEndpointBehavior
                        */

                        
                                    protected boolean localReturnEndpointBehavior =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnEndpointBehaviorTracker = false ;

                           public boolean isReturnEndpointBehaviorSpecified(){
                               return localReturnEndpointBehaviorTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnEndpointBehavior(){
                               return localReturnEndpointBehavior;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnEndpointBehavior
                               */
                               public void setReturnEndpointBehavior(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnEndpointBehaviorTracker =
                                       true;
                                   
                                            this.localReturnEndpointBehavior=param;
                                    

                               }
                            

                        /**
                        * field for ReturnAuthToken
                        */

                        
                                    protected boolean localReturnAuthToken =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnAuthTokenTracker = false ;

                           public boolean isReturnAuthTokenSpecified(){
                               return localReturnAuthTokenTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnAuthToken(){
                               return localReturnAuthToken;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnAuthToken
                               */
                               public void setReturnAuthToken(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnAuthTokenTracker =
                                       true;
                                   
                                            this.localReturnAuthToken=param;
                                    

                               }
                            

                        /**
                        * field for ReturnPortalVersion
                        */

                        
                                    protected boolean localReturnPortalVersion =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnPortalVersionTracker = false ;

                           public boolean isReturnPortalVersionSpecified(){
                               return localReturnPortalVersionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnPortalVersion(){
                               return localReturnPortalVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnPortalVersion
                               */
                               public void setReturnPortalVersion(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnPortalVersionTracker =
                                       true;
                                   
                                            this.localReturnPortalVersion=param;
                                    

                               }
                            

                        /**
                        * field for ReturnServiceAddress
                        */

                        
                                    protected boolean localReturnServiceAddress =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnServiceAddressTracker = false ;

                           public boolean isReturnServiceAddressSpecified(){
                               return localReturnServiceAddressTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnServiceAddress(){
                               return localReturnServiceAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnServiceAddress
                               */
                               public void setReturnServiceAddress(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnServiceAddressTracker =
                                       true;
                                   
                                            this.localReturnServiceAddress=param;
                                    

                               }
                            

                        /**
                        * field for ReturnNeoRoomPermanentPairingDeviceUserAttribute
                        */

                        
                                    protected boolean localReturnNeoRoomPermanentPairingDeviceUserAttribute =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnNeoRoomPermanentPairingDeviceUserAttributeTracker = false ;

                           public boolean isReturnNeoRoomPermanentPairingDeviceUserAttributeSpecified(){
                               return localReturnNeoRoomPermanentPairingDeviceUserAttributeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnNeoRoomPermanentPairingDeviceUserAttribute(){
                               return localReturnNeoRoomPermanentPairingDeviceUserAttribute;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnNeoRoomPermanentPairingDeviceUserAttribute
                               */
                               public void setReturnNeoRoomPermanentPairingDeviceUserAttribute(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnNeoRoomPermanentPairingDeviceUserAttributeTracker =
                                       true;
                                   
                                            this.localReturnNeoRoomPermanentPairingDeviceUserAttribute=param;
                                    

                               }
                            

                        /**
                        * field for ReturnUserRole
                        */

                        
                                    protected boolean localReturnUserRole =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("false");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnUserRoleTracker = false ;

                           public boolean isReturnUserRoleSpecified(){
                               return localReturnUserRoleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getReturnUserRole(){
                               return localReturnUserRole;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnUserRole
                               */
                               public void setReturnUserRole(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localReturnUserRoleTracker =
                                       true;
                                   
                                            this.localReturnUserRole=param;
                                    

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
                           namespacePrefix+":LogInRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "LogInRequest",
                           xmlWriter);
                   }

               
                   }
                if (localClientTypeTracker){
                                            if (localClientType==null){
                                                 throw new org.apache.axis2.databinding.ADBException("ClientType cannot be null!!");
                                            }
                                           localClientType.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","ClientType"),
                                               xmlWriter);
                                        } if (localReturnEndpointBehaviorTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnEndpointBehavior", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnEndpointBehavior cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnEndpointBehavior));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnAuthTokenTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnAuthToken", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnAuthToken cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnAuthToken));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnPortalVersionTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnPortalVersion", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnPortalVersion cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnPortalVersion));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnServiceAddressTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnServiceAddress", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnServiceAddress cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnServiceAddress));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnNeoRoomPermanentPairingDeviceUserAttributeTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnNeoRoomPermanentPairingDeviceUserAttribute", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnNeoRoomPermanentPairingDeviceUserAttribute cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnNeoRoomPermanentPairingDeviceUserAttribute));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnUserRoleTracker){
                                    namespace = "http://portal.vidyo.com/user/v1_1";
                                    writeStartElement(null, namespace, "returnUserRole", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("returnUserRole cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnUserRole));
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

                 if (localClientTypeTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "ClientType"));
                            
                            
                                    if (localClientType==null){
                                         throw new org.apache.axis2.databinding.ADBException("ClientType cannot be null!!");
                                    }
                                    elementList.add(localClientType);
                                } if (localReturnEndpointBehaviorTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnEndpointBehavior"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnEndpointBehavior));
                            } if (localReturnAuthTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnAuthToken"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnAuthToken));
                            } if (localReturnPortalVersionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnPortalVersion"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnPortalVersion));
                            } if (localReturnServiceAddressTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnServiceAddress"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnServiceAddress));
                            } if (localReturnNeoRoomPermanentPairingDeviceUserAttributeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnNeoRoomPermanentPairingDeviceUserAttribute"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnNeoRoomPermanentPairingDeviceUserAttribute));
                            } if (localReturnUserRoleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1",
                                                                      "returnUserRole"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnUserRole));
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
        public static LogInRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LogInRequest object =
                new LogInRequest();

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
                    
                            if (!"LogInRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LogInRequest)com.vidyo.portal.user.v1_1.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","ClientType").equals(reader.getName())){
                                
                                                object.setClientType(com.vidyo.portal.user.v1_1.ClientType_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnEndpointBehavior").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnEndpointBehavior" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnEndpointBehavior(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnAuthToken").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnAuthToken" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnAuthToken(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnPortalVersion").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnPortalVersion" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnPortalVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnServiceAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnServiceAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnServiceAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnNeoRoomPermanentPairingDeviceUserAttribute").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnNeoRoomPermanentPairingDeviceUserAttribute" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnNeoRoomPermanentPairingDeviceUserAttribute(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/user/v1_1","returnUserRole").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnUserRole" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnUserRole(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
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
           
    