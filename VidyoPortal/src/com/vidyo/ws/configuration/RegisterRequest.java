
/**
 * RegisterRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
 */

            
                package com.vidyo.ws.configuration;
            

            /**
            *  RegisterRequest bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class RegisterRequest
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://ws.vidyo.com/configuration",
                "RegisterRequest",
                "ns1");

            

                        /**
                        * field for Identifier
                        */

                        
                                    protected java.lang.String localIdentifier ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdentifier(){
                               return localIdentifier;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Identifier
                               */
                               public void setIdentifier(java.lang.String param){
                            
                                            this.localIdentifier=param;
                                    

                               }
                            

                        /**
                        * field for NetworkElementType
                        */

                        
                                    protected com.vidyo.ws.configuration.NetworkElementType localNetworkElementType ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.configuration.NetworkElementType
                           */
                           public  com.vidyo.ws.configuration.NetworkElementType getNetworkElementType(){
                               return localNetworkElementType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NetworkElementType
                               */
                               public void setNetworkElementType(com.vidyo.ws.configuration.NetworkElementType param){
                            
                                            this.localNetworkElementType=param;
                                    

                               }
                            

                        /**
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;

                           public boolean isNameSpecified(){
                               return localNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getName(){
                               return localName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(java.lang.String param){
                            localNameTracker = param != null;
                                   
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for SoftwareVersion
                        */

                        
                                    protected java.lang.String localSoftwareVersion ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSoftwareVersion(){
                               return localSoftwareVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SoftwareVersion
                               */
                               public void setSoftwareVersion(java.lang.String param){
                            
                                            this.localSoftwareVersion=param;
                                    

                               }
                            

                        /**
                        * field for NonLoopbackIPAddresses
                        */

                        
                                    protected com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0 localNonLoopbackIPAddresses ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0
                           */
                           public  com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0 getNonLoopbackIPAddresses(){
                               return localNonLoopbackIPAddresses;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NonLoopbackIPAddresses
                               */
                               public void setNonLoopbackIPAddresses(com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0 param){
                            
                                            this.localNonLoopbackIPAddresses=param;
                                    

                               }

                        /**
                        * field for WebApplicationURL
                        * This was a String!
                        */
                        private java.lang.String webApplicationURL;
                         /**
                         * Gets the webApplicationURL value for this RegisterRequest.
                         *
                         * @return webApplicationURL
                         */
                        public java.lang.String getWebApplicationURL() {
                            return webApplicationURL;
                        }


                        /**
                         * Sets the webApplicationURL value for this RegisterRequest.
                         *
                         * @param webApplicationURL
                         */
                        public void setWebApplicationURL(java.lang.String webApplicationURL) {
                            this.webApplicationURL = webApplicationURL;
                        }

                        /**
                        * field for Alarm
                        * This was an Array!
                        */

                        
                                    protected java.lang.String[] localAlarm ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAlarmTracker = false ;

                           public boolean isAlarmSpecified(){
                               return localAlarmTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String[]
                           */
                           public  java.lang.String[] getAlarm(){
                               return localAlarm;
                           }

                           
                        


                               
                              /**
                               * validate the array for Alarm
                               */
                              protected void validateAlarm(java.lang.String[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Alarm
                              */
                              public void setAlarm(java.lang.String[] param){
                              
                                   validateAlarm(param);

                               localAlarmTracker = param != null;
                                      
                                      this.localAlarm=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param java.lang.String
                             */
                             public void addAlarm(java.lang.String param){
                                   if (localAlarm == null){
                                   localAlarm = new java.lang.String[]{};
                                   }

                            
                                 //update the setting tracker
                                localAlarmTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localAlarm);
                               list.add(param);
                               this.localAlarm =
                             (java.lang.String[])list.toArray(
                            new java.lang.String[list.size()]);

                             }
                             

                        /**
                        * field for CurrentConfigVersion
                        * This was an Array!
                        */

                        
                                    protected com.vidyo.ws.configuration.ConfigVersion[] localCurrentConfigVersion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCurrentConfigVersionTracker = false ;

                           public boolean isCurrentConfigVersionSpecified(){
                               return localCurrentConfigVersionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.configuration.ConfigVersion[]
                           */
                           public  com.vidyo.ws.configuration.ConfigVersion[] getCurrentConfigVersion(){
                               return localCurrentConfigVersion;
                           }

                           
                        


                               
                              /**
                               * validate the array for CurrentConfigVersion
                               */
                              protected void validateCurrentConfigVersion(com.vidyo.ws.configuration.ConfigVersion[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param CurrentConfigVersion
                              */
                              public void setCurrentConfigVersion(com.vidyo.ws.configuration.ConfigVersion[] param){
                              
                                   validateCurrentConfigVersion(param);

                               localCurrentConfigVersionTracker = param != null;
                                      
                                      this.localCurrentConfigVersion=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param com.vidyo.ws.configuration.ConfigVersion
                             */
                             public void addCurrentConfigVersion(com.vidyo.ws.configuration.ConfigVersion param){
                                   if (localCurrentConfigVersion == null){
                                   localCurrentConfigVersion = new com.vidyo.ws.configuration.ConfigVersion[]{};
                                   }

                            
                                 //update the setting tracker
                                localCurrentConfigVersionTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localCurrentConfigVersion);
                               list.add(param);
                               this.localCurrentConfigVersion =
                             (com.vidyo.ws.configuration.ConfigVersion[])list.toArray(
                            new com.vidyo.ws.configuration.ConfigVersion[list.size()]);

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ws.vidyo.com/configuration");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":RegisterRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "RegisterRequest",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://ws.vidyo.com/configuration";
                                    writeStartElement(null, namespace, "Identifier", xmlWriter);
                             

                                          if (localIdentifier==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Identifier cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdentifier);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                            if (localNetworkElementType==null){
                                                 throw new org.apache.axis2.databinding.ADBException("NetworkElementType cannot be null!!");
                                            }
                                           localNetworkElementType.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","NetworkElementType"),
                                               xmlWriter);
                                         if (localNameTracker){
                                    namespace = "http://ws.vidyo.com/configuration";
                                    writeStartElement(null, namespace, "Name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("Name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://ws.vidyo.com/configuration";
                                    writeStartElement(null, namespace, "SoftwareVersion", xmlWriter);
                             

                                          if (localSoftwareVersion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("SoftwareVersion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSoftwareVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();

                                    namespace = "http://ws.vidyo.com/configuration";
                                    writeStartElement(null, namespace, "WebApplicationURL", xmlWriter);
                                    if (webApplicationURL==null){
                                          xmlWriter.writeCharacters("");

                                    }else{
                                          xmlWriter.writeCharacters(webApplicationURL);
                                    }
                                   xmlWriter.writeEndElement();
                             
                                            if (localNonLoopbackIPAddresses==null){
                                                 throw new org.apache.axis2.databinding.ADBException("NonLoopbackIPAddresses cannot be null!!");
                                            }
                                           localNonLoopbackIPAddresses.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","NonLoopbackIPAddresses"),
                                               xmlWriter);
                                         if (localAlarmTracker){
                             if (localAlarm!=null) {
                                   namespace = "http://ws.vidyo.com/configuration";
                                   for (int i = 0;i < localAlarm.length;i++){
                                        
                                            if (localAlarm[i] != null){
                                        
                                                writeStartElement(null, namespace, "Alarm", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlarm[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("Alarm cannot be null!!");
                                    
                             }

                        } if (localCurrentConfigVersionTracker){
                                       if (localCurrentConfigVersion!=null){
                                            for (int i = 0;i < localCurrentConfigVersion.length;i++){
                                                if (localCurrentConfigVersion[i] != null){
                                                 localCurrentConfigVersion[i].serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","CurrentConfigVersion"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("CurrentConfigVersion cannot be null!!");
                                        
                                    }
                                 }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://ws.vidyo.com/configuration")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                      "Identifier"));
                                 
                                        if (localIdentifier != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdentifier));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Identifier cannot be null!!");
                                        }
                                    
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                      "NetworkElementType"));
                            
                            
                                    if (localNetworkElementType==null){
                                         throw new org.apache.axis2.databinding.ADBException("NetworkElementType cannot be null!!");
                                    }
                                    elementList.add(localNetworkElementType);
                                 if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                      "Name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("Name cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","SoftwareVersion"));
                                 
                                        if (localSoftwareVersion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSoftwareVersion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("SoftwareVersion cannot be null!!");
                                        }

                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","WebApplicationURL"));
                                        if (webApplicationURL != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(webApplicationURL));
                                        } else {
                                           elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(""));
                                        }
                                    
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                      "NonLoopbackIPAddresses"));
                            
                            
                                    if (localNonLoopbackIPAddresses==null){
                                         throw new org.apache.axis2.databinding.ADBException("NonLoopbackIPAddresses cannot be null!!");
                                    }
                                    elementList.add(localNonLoopbackIPAddresses);
                                 if (localAlarmTracker){
                            if (localAlarm!=null){
                                  for (int i = 0;i < localAlarm.length;i++){
                                      
                                         if (localAlarm[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                              "Alarm"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAlarm[i]));
                                          } else {
                                             
                                                    // have to do nothing
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("Alarm cannot be null!!");
                                
                            }

                        } if (localCurrentConfigVersionTracker){
                             if (localCurrentConfigVersion!=null) {
                                 for (int i = 0;i < localCurrentConfigVersion.length;i++){

                                    if (localCurrentConfigVersion[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/configuration",
                                                                          "CurrentConfigVersion"));
                                         elementList.add(localCurrentConfigVersion[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("CurrentConfigVersion cannot be null!!");
                                    
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
        public static RegisterRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            RegisterRequest object =
                new RegisterRequest();

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
                    
                            if (!"RegisterRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (RegisterRequest)com.vidyo.ws.configuration.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list6 = new java.util.ArrayList();
                    
                        java.util.ArrayList list7 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","Identifier").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdentifier(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","NetworkElementType").equals(reader.getName())){
                                
                                                object.setNetworkElementType(com.vidyo.ws.configuration.NetworkElementType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","Name").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","SoftwareVersion").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSoftwareVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }
                              // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","NonLoopbackIPAddresses").equals(reader.getName())){
                                
                                                object.setNonLoopbackIPAddresses(com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }

                                while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","WebApplicationURL").equals(reader.getName())){

                                    java.lang.String content = reader.getElementText();

                                              object.setWebApplicationURL(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

                                        reader.next();

                                }else{

                                }

                                while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","Alarm").equals(reader.getName())){
                                    // Process the array and step past its final element's end.
                                    list6.add(reader.getElementText());
                                        //loop until we find a start element that is not part of this array
                                        boolean loopDone6 = false;
                                        while(!loopDone6){
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
                                                loopDone6 = true;
                                            } else {
                                                if (new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","Alarm").equals(reader.getName())){
                                                     list6.add(reader.getElementText());

                                                }else{
                                                    loopDone6 = true;
                                                }
                                            }
                                        }
                                        // call the converter utility  to convert and set the array

                                                object.setAlarm((java.lang.String[])
                                                    list6.toArray(new java.lang.String[list6.size()]));

                                }  // End of if for expected property start element
                                else {

                                }
                                    
                                while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                 if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","CurrentConfigVersion").equals(reader.getName())){
                                    // Process the array and step past its final element's end.
                                    list7.add(com.vidyo.ws.configuration.ConfigVersion.Factory.parse(reader));
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone7 = false;
                                                        while(!loopDone7){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone7 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://ws.vidyo.com/configuration","CurrentConfigVersion").equals(reader.getName())){
                                                                    list7.add(com.vidyo.ws.configuration.ConfigVersion.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone7 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setCurrentConfigVersion((com.vidyo.ws.configuration.ConfigVersion[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                com.vidyo.ws.configuration.ConfigVersion.class,
                                                                list7));
                                                            
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
           
    