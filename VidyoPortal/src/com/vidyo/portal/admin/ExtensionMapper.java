
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:21:18 IST)
 */

        
            package com.vidyo.portal.admin;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Filter_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Filter_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "NotLicensedException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.NotLicensedException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "RoomMode_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.RoomMode_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Room".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Room.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "MemberNotFoundException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.MemberNotFoundException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "EntityID".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.EntityID.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "GroupNotFoundException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.GroupNotFoundException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "RoomAlreadyExistsException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.RoomAlreadyExistsException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Exception".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Exception.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Member".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Member.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "MemberAlreadyExistsException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.MemberAlreadyExistsException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "RoomNotFoundException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.RoomNotFoundException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "sortDir".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.SortDir.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "LicenseFeatureData".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.LicenseFeatureData.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Entity_type0".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Entity_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "GroupAlreadyExistsException".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.GroupAlreadyExistsException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://portal.vidyo.com/admin".equals(namespaceURI) &&
                  "Group".equals(typeName)){
                   
                            return  com.vidyo.portal.admin.Group.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    