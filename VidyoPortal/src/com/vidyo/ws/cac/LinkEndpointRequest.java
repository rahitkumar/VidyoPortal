
/**
 * LinkEndpointRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.ws.cac;
            

            /**
            *  LinkEndpointRequest bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class LinkEndpointRequest
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://ws.vidyo.com/cac",
                "LinkEndpointRequest",
                "ns1");

            

                        /**
                        * field for EID
                        */

                        
                                    protected java.lang.String localEID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEID(){
                               return localEID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EID
                               */
                               public void setEID(java.lang.String param){
                            
                                            this.localEID=param;
                                    

                               }
                            

                        /**
                        * field for VrIP
                        */

                        
                                    protected java.lang.String localVrIP ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVrIPTracker = false ;

                           public boolean isVrIPSpecified(){
                               return localVrIPTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVrIP(){
                               return localVrIP;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VrIP
                               */
                               public void setVrIP(java.lang.String param){
                            localVrIPTracker = param != null;
                                   
                                            this.localVrIP=param;
                                    

                               }
                            

                        /**
                        * field for ClientType
                        */

                        
                                    protected java.lang.String localClientType ;
                                
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
                           * @return java.lang.String
                           */
                           public  java.lang.String getClientType(){
                               return localClientType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClientType
                               */
                               public void setClientType(java.lang.String param){
                            localClientTypeTracker = param != null;
                                   
                                            this.localClientType=param;
                                    

                               }
                            

                        /**
                        * field for ApplicationName
                        */

                        
                                    protected java.lang.String localApplicationName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localApplicationNameTracker = false ;

                           public boolean isApplicationNameSpecified(){
                               return localApplicationNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getApplicationName(){
                               return localApplicationName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ApplicationName
                               */
                               public void setApplicationName(java.lang.String param){
                            localApplicationNameTracker = param != null;
                                   
                                            this.localApplicationName=param;
                                    

                               }
                            

                        /**
                        * field for ApplicationVersion
                        */

                        
                                    protected java.lang.String localApplicationVersion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localApplicationVersionTracker = false ;

                           public boolean isApplicationVersionSpecified(){
                               return localApplicationVersionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getApplicationVersion(){
                               return localApplicationVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ApplicationVersion
                               */
                               public void setApplicationVersion(java.lang.String param){
                            localApplicationVersionTracker = param != null;
                                   
                                            this.localApplicationVersion=param;
                                    

                               }
                            

                        /**
                        * field for ApplicationOs
                        */

                        
                                    protected java.lang.String localApplicationOs ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localApplicationOsTracker = false ;

                           public boolean isApplicationOsSpecified(){
                               return localApplicationOsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getApplicationOs(){
                               return localApplicationOs;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ApplicationOs
                               */
                               public void setApplicationOs(java.lang.String param){
                            localApplicationOsTracker = param != null;
                                   
                                            this.localApplicationOs=param;
                                    

                               }
                            

                        /**
                        * field for DeviceModel
                        */

                        
                                    protected java.lang.String localDeviceModel ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDeviceModelTracker = false ;

                           public boolean isDeviceModelSpecified(){
                               return localDeviceModelTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDeviceModel(){
                               return localDeviceModel;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DeviceModel
                               */
                               public void setDeviceModel(java.lang.String param){
                            localDeviceModelTracker = param != null;
                                   
                                            this.localDeviceModel=param;
                                    

                               }
                            

                        /**
                        * field for Pak2
                        */

                        
                                    protected boolean localPak2 ;
                                
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
                           * @return boolean
                           */
                           public  boolean getPak2(){
                               return localPak2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Pak2
                               */
                               public void setPak2(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localPak2Tracker =
                                       true;
                                   
                                            this.localPak2=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ws.vidyo.com/cac");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":LinkEndpointRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "LinkEndpointRequest",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "EID", xmlWriter);
                             

                                          if (localEID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("EID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localVrIPTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "vrIP", xmlWriter);
                             

                                          if (localVrIP==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("vrIP cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVrIP);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClientTypeTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "clientType", xmlWriter);
                             

                                          if (localClientType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("clientType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localClientType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localApplicationNameTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "applicationName", xmlWriter);
                             

                                          if (localApplicationName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("applicationName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localApplicationName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localApplicationVersionTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "applicationVersion", xmlWriter);
                             

                                          if (localApplicationVersion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("applicationVersion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localApplicationVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localApplicationOsTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "applicationOs", xmlWriter);
                             

                                          if (localApplicationOs==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("applicationOs cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localApplicationOs);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDeviceModelTracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "deviceModel", xmlWriter);
                             

                                          if (localDeviceModel==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("deviceModel cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDeviceModel);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPak2Tracker){
                                    namespace = "http://ws.vidyo.com/cac";
                                    writeStartElement(null, namespace, "pak2", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("pak2 cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak2));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://ws.vidyo.com/cac")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "EID"));
                                 
                                        if (localEID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("EID cannot be null!!");
                                        }
                                     if (localVrIPTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "vrIP"));
                                 
                                        if (localVrIP != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVrIP));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("vrIP cannot be null!!");
                                        }
                                    } if (localClientTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "clientType"));
                                 
                                        if (localClientType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClientType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("clientType cannot be null!!");
                                        }
                                    } if (localApplicationNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "applicationName"));
                                 
                                        if (localApplicationName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localApplicationName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("applicationName cannot be null!!");
                                        }
                                    } if (localApplicationVersionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "applicationVersion"));
                                 
                                        if (localApplicationVersion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localApplicationVersion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("applicationVersion cannot be null!!");
                                        }
                                    } if (localApplicationOsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "applicationOs"));
                                 
                                        if (localApplicationOs != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localApplicationOs));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("applicationOs cannot be null!!");
                                        }
                                    } if (localDeviceModelTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "deviceModel"));
                                 
                                        if (localDeviceModel != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeviceModel));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("deviceModel cannot be null!!");
                                        }
                                    } if (localPak2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/cac",
                                                                      "pak2"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPak2));
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
        public static LinkEndpointRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            LinkEndpointRequest object =
                new LinkEndpointRequest();

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
                    
                            if (!"LinkEndpointRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (LinkEndpointRequest)com.vidyo.ws.cac.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","EID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"EID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","vrIP").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"vrIP" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVrIP(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","clientType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"clientType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setClientType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","applicationName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"applicationName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setApplicationName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","applicationVersion").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"applicationVersion" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setApplicationVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","applicationOs").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"applicationOs" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setApplicationOs(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","deviceModel").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"deviceModel" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDeviceModel(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/cac","pak2").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"pak2" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPak2(
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
           
    