
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package com.vidyo.portal.guest;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "PortalFeature_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.PortalFeature_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "EndpointFeature_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.EndpointFeature_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "EndpointFeatureName".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.EndpointFeatureName.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "EntityID".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.EntityID.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "extension_type1".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.Extension_type1.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "PortalFeatureName".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.PortalFeatureName.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/guest".equals(namespaceURI) &&
                  "EndpointBehaviorDataType".equals(typeName)){
                   
                            return  com.vidyo.portal.guest.EndpointBehaviorDataType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    