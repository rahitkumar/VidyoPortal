
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package com.vidyo.ws.configuration;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://ws.vidyo.com/configuration".equals(namespaceURI) &&
                  "ConfigVersion".equals(typeName)){
                   
                            return  com.vidyo.ws.configuration.ConfigVersion.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/configuration".equals(namespaceURI) &&
                  "Configuration".equals(typeName)){
                   
                            return  com.vidyo.ws.configuration.Configuration.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/configuration".equals(namespaceURI) &&
                  "NetworkElementType".equals(typeName)){
                   
                            return  com.vidyo.ws.configuration.NetworkElementType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/configuration".equals(namespaceURI) &&
                  "ConfigType".equals(typeName)){
                   
                            return  com.vidyo.ws.configuration.ConfigType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/configuration".equals(namespaceURI) &&
                  "NonLoopbackIPAddresses_type0".equals(typeName)){
                   
                            return  com.vidyo.ws.configuration.NonLoopbackIPAddresses_type0.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    