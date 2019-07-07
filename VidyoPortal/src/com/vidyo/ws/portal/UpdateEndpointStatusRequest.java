
/**
 * UpdateEndpointStatusRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.ws.portal;
            

            /**
            *  UpdateEndpointStatusRequest bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class UpdateEndpointStatusRequest
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://ws.vidyo.com/portal",
                "UpdateEndpointStatusRequest",
                "ns1");

            

                        /**
                        * field for EndpointGUID
                        */

                        
                                    protected com.vidyo.ws.portal.EndpointGUID_type0 localEndpointGUID ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.portal.EndpointGUID_type0
                           */
                           public  com.vidyo.ws.portal.EndpointGUID_type0 getEndpointGUID(){
                               return localEndpointGUID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndpointGUID
                               */
                               public void setEndpointGUID(com.vidyo.ws.portal.EndpointGUID_type0 param){
                            
                                            this.localEndpointGUID=param;
                                    

                               }
                            

                        /**
                        * field for State
                        */

                        
                                    protected com.vidyo.ws.portal.State_type1 localState ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.portal.State_type1
                           */
                           public  com.vidyo.ws.portal.State_type1 getState(){
                               return localState;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param State
                               */
                               public void setState(com.vidyo.ws.portal.State_type1 param){
                            
                                            this.localState=param;
                                    

                               }
                            

                        /**
                        * field for UpdateReason
                        */

                        
                                    protected com.vidyo.ws.portal.UpdateReason_type1 localUpdateReason ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.portal.UpdateReason_type1
                           */
                           public  com.vidyo.ws.portal.UpdateReason_type1 getUpdateReason(){
                               return localUpdateReason;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UpdateReason
                               */
                               public void setUpdateReason(com.vidyo.ws.portal.UpdateReason_type1 param){
                            
                                            this.localUpdateReason=param;
                                    

                               }
                            

                        /**
                        * field for ConferenceInfo
                        */

                        
                                    protected com.vidyo.ws.portal.ConferenceInfoType localConferenceInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localConferenceInfoTracker = false ;

                           public boolean isConferenceInfoSpecified(){
                               return localConferenceInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.ws.portal.ConferenceInfoType
                           */
                           public  com.vidyo.ws.portal.ConferenceInfoType getConferenceInfo(){
                               return localConferenceInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ConferenceInfo
                               */
                               public void setConferenceInfo(com.vidyo.ws.portal.ConferenceInfoType param){
                            localConferenceInfoTracker = param != null;
                                   
                                            this.localConferenceInfo=param;
                                    

                               }
                            

                        /**
                        * field for ParticipantID
                        */

                        
                                    protected java.lang.String localParticipantID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localParticipantIDTracker = false ;

                           public boolean isParticipantIDSpecified(){
                               return localParticipantIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getParticipantID(){
                               return localParticipantID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ParticipantID
                               */
                               public void setParticipantID(java.lang.String param){
                            localParticipantIDTracker = param != null;
                                   
                                            this.localParticipantID=param;
                                    

                               }
                            

                        /**
                        * field for SequenceNum
                        */

                        
                                    protected org.apache.axis2.databinding.types.UnsignedLong localSequenceNum ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSequenceNumTracker = false ;

                           public boolean isSequenceNumSpecified(){
                               return localSequenceNumTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.UnsignedLong
                           */
                           public  org.apache.axis2.databinding.types.UnsignedLong getSequenceNum(){
                               return localSequenceNum;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SequenceNum
                               */
                               public void setSequenceNum(org.apache.axis2.databinding.types.UnsignedLong param){
                            localSequenceNumTracker = true;
                                   
                                            this.localSequenceNum=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ws.vidyo.com/portal");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":UpdateEndpointStatusRequest",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "UpdateEndpointStatusRequest",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localEndpointGUID==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EndpointGUID cannot be null!!");
                                            }
                                           localEndpointGUID.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/portal","EndpointGUID"),
                                               xmlWriter);
                                        
                                            if (localState==null){
                                                 throw new org.apache.axis2.databinding.ADBException("State cannot be null!!");
                                            }
                                           localState.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/portal","State"),
                                               xmlWriter);
                                        
                                            if (localUpdateReason==null){
                                                 throw new org.apache.axis2.databinding.ADBException("UpdateReason cannot be null!!");
                                            }
                                           localUpdateReason.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/portal","UpdateReason"),
                                               xmlWriter);
                                         if (localConferenceInfoTracker){
                                            if (localConferenceInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("ConferenceInfo cannot be null!!");
                                            }
                                           localConferenceInfo.serialize(new javax.xml.namespace.QName("http://ws.vidyo.com/portal","ConferenceInfo"),
                                               xmlWriter);
                                        } if (localParticipantIDTracker){
                                    namespace = "http://ws.vidyo.com/portal";
                                    writeStartElement(null, namespace, "ParticipantID", xmlWriter);
                             

                                          if (localParticipantID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ParticipantID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localParticipantID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSequenceNumTracker){
                                    namespace = "http://ws.vidyo.com/portal";
                                    writeStartElement(null, namespace, "SequenceNum", xmlWriter);
                             

                                          if (localSequenceNum==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSequenceNum));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://ws.vidyo.com/portal")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "EndpointGUID"));
                            
                            
                                    if (localEndpointGUID==null){
                                         throw new org.apache.axis2.databinding.ADBException("EndpointGUID cannot be null!!");
                                    }
                                    elementList.add(localEndpointGUID);
                                
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "State"));
                            
                            
                                    if (localState==null){
                                         throw new org.apache.axis2.databinding.ADBException("State cannot be null!!");
                                    }
                                    elementList.add(localState);
                                
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "UpdateReason"));
                            
                            
                                    if (localUpdateReason==null){
                                         throw new org.apache.axis2.databinding.ADBException("UpdateReason cannot be null!!");
                                    }
                                    elementList.add(localUpdateReason);
                                 if (localConferenceInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "ConferenceInfo"));
                            
                            
                                    if (localConferenceInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("ConferenceInfo cannot be null!!");
                                    }
                                    elementList.add(localConferenceInfo);
                                } if (localParticipantIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "ParticipantID"));
                                 
                                        if (localParticipantID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localParticipantID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ParticipantID cannot be null!!");
                                        }
                                    } if (localSequenceNumTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://ws.vidyo.com/portal",
                                                                      "SequenceNum"));
                                 
                                         elementList.add(localSequenceNum==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSequenceNum));
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
        public static UpdateEndpointStatusRequest parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            UpdateEndpointStatusRequest object =
                new UpdateEndpointStatusRequest();

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
                    
                            if (!"UpdateEndpointStatusRequest".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (UpdateEndpointStatusRequest)com.vidyo.ws.portal.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","EndpointGUID").equals(reader.getName())){
                                
                                                object.setEndpointGUID(com.vidyo.ws.portal.EndpointGUID_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","State").equals(reader.getName())){
                                
                                                object.setState(com.vidyo.ws.portal.State_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","UpdateReason").equals(reader.getName())){
                                
                                                object.setUpdateReason(com.vidyo.ws.portal.UpdateReason_type1.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","ConferenceInfo").equals(reader.getName())){
                                
                                                object.setConferenceInfo(com.vidyo.ws.portal.ConferenceInfoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","ParticipantID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ParticipantID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setParticipantID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://ws.vidyo.com/portal","SequenceNum").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSequenceNum(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToUnsignedLong(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
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
           
    