
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package com.vidyo.portal.user;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "Filter_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.user.Filter_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "Exception".equals(typeName)){
                   
                            return  com.vidyo.portal.user.Exception.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "RoomMode_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.user.RoomMode_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "EntityID".equals(typeName)){
                   
                            return  com.vidyo.portal.user.EntityID.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "sortDir".equals(typeName)){
                   
                            return  com.vidyo.portal.user.SortDir.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/user".equals(namespaceURI) &&
                  "Entity_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.user.Entity_type0.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    