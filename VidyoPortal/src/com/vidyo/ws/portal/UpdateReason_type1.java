
/**
 * UpdateReason_type1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.ws.portal;
            

            /**
            *  UpdateReason_type1 bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class UpdateReason_type1
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://ws.vidyo.com/portal",
                "UpdateReason_type1",
                "ns1");

            

                        /**
                        * field for UpdateReason_type1
                        */

                        
                                    protected java.lang.String localUpdateReason_type1 ;
                                
                            private static java.util.HashMap _table_ = new java.util.HashMap();

                            // Constructor
                            
                                protected UpdateReason_type1(java.lang.String value, boolean isRegisterValue) {
                                    localUpdateReason_type1 = value;
                                    if (isRegisterValue){
                                        
                                               _table_.put(localUpdateReason_type1, this);
                                           
                                    }

                                }
                            
                                    public static final java.lang.String _EpLost =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpLost");
                                
                                    public static final java.lang.String _EpDetected =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpDetected");
                                
                                    public static final java.lang.String _EpLeftConf =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpLeftConf");
                                
                                    public static final java.lang.String _EpLeaveConfOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpLeaveConfOk");
                                
                                    public static final java.lang.String _EpStopRingOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpStopRingOk");
                                
                                    public static final java.lang.String _EpStopAlertOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpStopAlertOk");
                                
                                    public static final java.lang.String _EpJoinConfOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpJoinConfOk");
                                
                                    public static final java.lang.String _JoinedRouter =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("JoinedRouter");
                                
                                    public static final java.lang.String _EpInConf =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpInConf");
                                
                                    public static final java.lang.String _EpStartRingOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpStartRingOk");
                                
                                    public static final java.lang.String _EpRinging =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpRinging");
                                
                                    public static final java.lang.String _EpRingAccepted =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpRingAccepted");
                                
                                    public static final java.lang.String _EpRingRejected =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpRingRejected");
                                
                                    public static final java.lang.String _EpRingNoAnswer =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpRingNoAnswer");
                                
                                    public static final java.lang.String _EpStartAlertOk =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpStartAlertOk");
                                
                                    public static final java.lang.String _EpAlerting =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpAlerting");
                                
                                    public static final java.lang.String _EpAlertingCanceled =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpAlertingCanceled");
                                
                                    public static final java.lang.String _EpRingBusy =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpRingBusy");
                                
                                    public static final java.lang.String _EpJoinConfRejected =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpJoinConfRejected");
                                
                                    public static final java.lang.String _EpStartRingRejected =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EpStartRingRejected");
                                
                                public static final UpdateReason_type1 EpLost =
                                    new UpdateReason_type1(_EpLost,true);
                            
                                public static final UpdateReason_type1 EpDetected =
                                    new UpdateReason_type1(_EpDetected,true);
                            
                                public static final UpdateReason_type1 EpLeftConf =
                                    new UpdateReason_type1(_EpLeftConf,true);
                            
                                public static final UpdateReason_type1 EpLeaveConfOk =
                                    new UpdateReason_type1(_EpLeaveConfOk,true);
                            
                                public static final UpdateReason_type1 EpStopRingOk =
                                    new UpdateReason_type1(_EpStopRingOk,true);
                            
                                public static final UpdateReason_type1 EpStopAlertOk =
                                    new UpdateReason_type1(_EpStopAlertOk,true);
                            
                                public static final UpdateReason_type1 EpJoinConfOk =
                                    new UpdateReason_type1(_EpJoinConfOk,true);
                            
                                public static final UpdateReason_type1 JoinedRouter =
                                    new UpdateReason_type1(_JoinedRouter,true);
                            
                                public static final UpdateReason_type1 EpInConf =
                                    new UpdateReason_type1(_EpInConf,true);
                            
                                public static final UpdateReason_type1 EpStartRingOk =
                                    new UpdateReason_type1(_EpStartRingOk,true);
                            
                                public static final UpdateReason_type1 EpRinging =
                                    new UpdateReason_type1(_EpRinging,true);
                            
                                public static final UpdateReason_type1 EpRingAccepted =
                                    new UpdateReason_type1(_EpRingAccepted,true);
                            
                                public static final UpdateReason_type1 EpRingRejected =
                                    new UpdateReason_type1(_EpRingRejected,true);
                            
                                public static final UpdateReason_type1 EpRingNoAnswer =
                                    new UpdateReason_type1(_EpRingNoAnswer,true);
                            
                                public static final UpdateReason_type1 EpStartAlertOk =
                                    new UpdateReason_type1(_EpStartAlertOk,true);
                            
                                public static final UpdateReason_type1 EpAlerting =
                                    new UpdateReason_type1(_EpAlerting,true);
                            
                                public static final UpdateReason_type1 EpAlertingCanceled =
                                    new UpdateReason_type1(_EpAlertingCanceled,true);
                            
                                public static final UpdateReason_type1 EpRingBusy =
                                    new UpdateReason_type1(_EpRingBusy,true);
                            
                                public static final UpdateReason_type1 EpJoinConfRejected =
                                    new UpdateReason_type1(_EpJoinConfRejected,true);
                            
                                public static final UpdateReason_type1 EpStartRingRejected =
                                    new UpdateReason_type1(_EpStartRingRejected,true);
                            

                                public java.lang.String getValue() { return localUpdateReason_type1;}

                                public boolean equals(java.lang.Object obj) {return (obj == this);}
                                public int hashCode() { return toString().hashCode();}
                                public java.lang.String toString() {
                                
                                        return localUpdateReason_type1.toString();
                                    

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
            
                
                //We can safely assume an element has only one type associated with it
                
                            java.lang.String namespace = parentQName.getNamespaceURI();
                            java.lang.String _localName = parentQName.getLocalPart();
                        
                            writeStartElement(null, namespace, _localName, xmlWriter);

                            // add the type details if this is used in a simple type
                               if (serializeType){
                                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://ws.vidyo.com/portal");
                                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           namespacePrefix+":UpdateReason_type1",
                                           xmlWriter);
                                   } else {
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           "UpdateReason_type1",
                                           xmlWriter);
                                   }
                               }
                            
                                          if (localUpdateReason_type1==null){
                                            
                                                     throw new org.apache.axis2.databinding.ADBException("UpdateReason_type1 cannot be null !!");
                                                
                                         }else{
                                        
                                                       xmlWriter.writeCharacters(localUpdateReason_type1);
                                            
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


        
                
                //We can safely assume an element has only one type associated with it
                 return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME,
                            new java.lang.Object[]{
                            org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
                            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUpdateReason_type1)
                            },
                            null);

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        
                public static UpdateReason_type1 fromValue(java.lang.String value)
                      throws java.lang.IllegalArgumentException {
                    UpdateReason_type1 enumeration = (UpdateReason_type1)
                       
                               _table_.get(value);
                           

                    if ((enumeration == null) && !((value == null) || (value.equals("")))) {
                        throw new java.lang.IllegalArgumentException();
                    }
                    return enumeration;
                }
                public static UpdateReason_type1 fromString(java.lang.String value,java.lang.String namespaceURI)
                      throws java.lang.IllegalArgumentException {
                    try {
                       
                                       return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
                                   

                    } catch (java.lang.Exception e) {
                        throw new java.lang.IllegalArgumentException();
                    }
                }

                public static UpdateReason_type1 fromString(javax.xml.stream.XMLStreamReader xmlStreamReader,
                                                                    java.lang.String content) {
                    if (content.indexOf(":") > -1){
                        java.lang.String prefix = content.substring(0,content.indexOf(":"));
                        java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
                        return UpdateReason_type1.Factory.fromString(content,namespaceUri);
                    } else {
                       return UpdateReason_type1.Factory.fromString(content,"");
                    }
                }
            

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static UpdateReason_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            UpdateReason_type1 object = null;
                // initialize a hash map to keep values
                java.util.Map attributeMap = new java.util.HashMap();
                java.util.List extraAttributeList = new java.util.ArrayList<org.apache.axiom.om.OMAttribute>();
            

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                   
                while(!reader.isEndElement()) {
                    if (reader.isStartElement()  || reader.hasText()){
                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"UpdateReason_type1" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                        if (content.indexOf(":") > 0) {
                                            // this seems to be a Qname so find the namespace and send
                                            prefix = content.substring(0, content.indexOf(":"));
                                            namespaceuri = reader.getNamespaceURI(prefix);
                                            object = UpdateReason_type1.Factory.fromString(content,namespaceuri);
                                        } else {
                                            // this seems to be not a qname send and empty namespace incase of it is
                                            // check is done in fromString method
                                            object = UpdateReason_type1.Factory.fromString(content,"");
                                        }
                                        
                                        
                             } else {
                                reader.next();
                             }  
                           }  // end of while loop
                        



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    