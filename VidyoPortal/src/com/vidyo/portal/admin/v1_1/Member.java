
/**
 * Member.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.vidyo.portal.admin.v1_1;
            

            /**
            *  Member bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Member
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Member
                Namespace URI = http://portal.vidyo.com/admin/v1_1
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for MemberID
                        */

                        
                                    protected com.vidyo.portal.admin.v1_1.EntityID localMemberID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMemberIDTracker = false ;

                           public boolean isMemberIDSpecified(){
                               return localMemberIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.admin.v1_1.EntityID
                           */
                           public  com.vidyo.portal.admin.v1_1.EntityID getMemberID(){
                               return localMemberID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MemberID
                               */
                               public void setMemberID(com.vidyo.portal.admin.v1_1.EntityID param){
                            localMemberIDTracker = param != null;
                                   
                                            this.localMemberID=param;
                                    

                               }
                            

                        /**
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                

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
                            
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for Password
                        */

                        
                                    protected java.lang.String localPassword ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPasswordTracker = false ;

                           public boolean isPasswordSpecified(){
                               return localPasswordTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassword(){
                               return localPassword;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Password
                               */
                               public void setPassword(java.lang.String param){
                            localPasswordTracker = true;
                                   
                                            this.localPassword=param;
                                    

                               }
                            

                        /**
                        * field for DisplayName
                        */

                        
                                    protected java.lang.String localDisplayName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDisplayName(){
                               return localDisplayName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DisplayName
                               */
                               public void setDisplayName(java.lang.String param){
                            
                                            this.localDisplayName=param;
                                    

                               }
                            

                        /**
                        * field for Extension
                        */

                        
                                    protected java.lang.String localExtension ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExtension(){
                               return localExtension;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Extension
                               */
                               public void setExtension(java.lang.String param){
                            
                                            this.localExtension=param;
                                    

                               }
                            

                        /**
                        * field for Language
                        */

                        
                                    protected com.vidyo.portal.admin.v1_1.Language_type0 localLanguage ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.admin.v1_1.Language_type0
                           */
                           public  com.vidyo.portal.admin.v1_1.Language_type0 getLanguage(){
                               return localLanguage;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Language
                               */
                               public void setLanguage(com.vidyo.portal.admin.v1_1.Language_type0 param){
                            
                                            this.localLanguage=param;
                                    

                               }
                            

                        /**
                        * field for RoleName
                        */

                        
                                    protected com.vidyo.portal.admin.v1_1.RoleName_type0 localRoleName ;
                                

                           /**
                           * Auto generated getter method
                           * @return com.vidyo.portal.admin.v1_1.RoleName_type0
                           */
                           public  com.vidyo.portal.admin.v1_1.RoleName_type0 getRoleName(){
                               return localRoleName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RoleName
                               */
                               public void setRoleName(com.vidyo.portal.admin.v1_1.RoleName_type0 param){
                            
                                            this.localRoleName=param;
                                    

                               }
                            

                        /**
                        * field for GroupName
                        */

                        
                                    protected java.lang.String localGroupName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGroupName(){
                               return localGroupName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GroupName
                               */
                               public void setGroupName(java.lang.String param){
                            
                                            this.localGroupName=param;
                                    

                               }
                            

                        /**
                        * field for ProxyName
                        */

                        
                                    protected java.lang.String localProxyName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProxyNameTracker = false ;

                           public boolean isProxyNameSpecified(){
                               return localProxyNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProxyName(){
                               return localProxyName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProxyName
                               */
                               public void setProxyName(java.lang.String param){
                            localProxyNameTracker = param != null;
                                   
                                            this.localProxyName=param;
                                    

                               }
                            

                        /**
                        * field for EmailAddress
                        */

                        
                                    protected java.lang.String localEmailAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmailAddress(){
                               return localEmailAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EmailAddress
                               */
                               public void setEmailAddress(java.lang.String param){
                            
                                            this.localEmailAddress=param;
                                    

                               }
                            

                        /**
                        * field for Created
                        */

                        
                                    protected java.util.Date localCreated ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatedTracker = false ;

                           public boolean isCreatedSpecified(){
                               return localCreatedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Date
                           */
                           public  java.util.Date getCreated(){
                               return localCreated;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Created
                               */
                               public void setCreated(java.util.Date param){
                            localCreatedTracker = param != null;
                                   
                                            this.localCreated=param;
                                    

                               }
                            

                        /**
                        * field for Description
                        */

                        
                                    protected java.lang.String localDescription ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDescriptionTracker = false ;

                           public boolean isDescriptionSpecified(){
                               return localDescriptionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDescription(){
                               return localDescription;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Description
                               */
                               public void setDescription(java.lang.String param){
                            localDescriptionTracker = param != null;
                                   
                                            this.localDescription=param;
                                    

                               }
                            

                        /**
                        * field for AllowCallDirect
                        */

                        
                                    protected boolean localAllowCallDirect ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowCallDirectTracker = false ;

                           public boolean isAllowCallDirectSpecified(){
                               return localAllowCallDirectTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAllowCallDirect(){
                               return localAllowCallDirect;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AllowCallDirect
                               */
                               public void setAllowCallDirect(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAllowCallDirectTracker =
                                       true;
                                   
                                            this.localAllowCallDirect=param;
                                    

                               }
                            

                        /**
                        * field for AllowPersonalMeeting
                        */

                        
                                    protected boolean localAllowPersonalMeeting ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllowPersonalMeetingTracker = false ;

                           public boolean isAllowPersonalMeetingSpecified(){
                               return localAllowPersonalMeetingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAllowPersonalMeeting(){
                               return localAllowPersonalMeeting;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AllowPersonalMeeting
                               */
                               public void setAllowPersonalMeeting(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAllowPersonalMeetingTracker =
                                       true;
                                   
                                            this.localAllowPersonalMeeting=param;
                                    

                               }
                            

                        /**
                        * field for LocationTag
                        */

                        
                                    protected java.lang.String localLocationTag ;
                                

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
                            
                                            this.localLocationTag=param;
                                    

                               }
                            

                        /**
                        * field for CreationTime
                        */

                        
                                    protected java.util.Calendar localCreationTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreationTimeTracker = false ;

                           public boolean isCreationTimeSpecified(){
                               return localCreationTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getCreationTime(){
                               return localCreationTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreationTime
                               */
                               public void setCreationTime(java.util.Calendar param){
                            localCreationTimeTracker = param != null;
                                   
                                            this.localCreationTime=param;
                                    

                               }
                            

                        /**
                        * field for ModificationTime
                        */

                        
                                    protected java.util.Calendar localModificationTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModificationTimeTracker = false ;

                           public boolean isModificationTimeSpecified(){
                               return localModificationTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getModificationTime(){
                               return localModificationTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModificationTime
                               */
                               public void setModificationTime(java.util.Calendar param){
                            localModificationTimeTracker = param != null;
                                   
                                            this.localModificationTime=param;
                                    

                               }
                            

                        /**
                        * field for Phone1
                        */

                        
                                    protected java.lang.String localPhone1 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhone1Tracker = false ;

                           public boolean isPhone1Specified(){
                               return localPhone1Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone1(){
                               return localPhone1;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone1
                               */
                               public void setPhone1(java.lang.String param){
                            localPhone1Tracker = true;
                                   
                                            this.localPhone1=param;
                                    

                               }
                            

                        /**
                        * field for Phone2
                        */

                        
                                    protected java.lang.String localPhone2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhone2Tracker = false ;

                           public boolean isPhone2Specified(){
                               return localPhone2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone2(){
                               return localPhone2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone2
                               */
                               public void setPhone2(java.lang.String param){
                            localPhone2Tracker = true;
                                   
                                            this.localPhone2=param;
                                    

                               }
                            

                        /**
                        * field for Phone3
                        */

                        
                                    protected java.lang.String localPhone3 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhone3Tracker = false ;

                           public boolean isPhone3Specified(){
                               return localPhone3Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhone3(){
                               return localPhone3;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Phone3
                               */
                               public void setPhone3(java.lang.String param){
                            localPhone3Tracker = true;
                                   
                                            this.localPhone3=param;
                                    

                               }
                            

                        /**
                        * field for Department
                        */

                        
                                    protected java.lang.String localDepartment ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDepartmentTracker = false ;

                           public boolean isDepartmentSpecified(){
                               return localDepartmentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDepartment(){
                               return localDepartment;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Department
                               */
                               public void setDepartment(java.lang.String param){
                            localDepartmentTracker = true;
                                   
                                            this.localDepartment=param;
                                    

                               }
                            

                        /**
                        * field for Title
                        */

                        
                                    protected java.lang.String localTitle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTitleTracker = false ;

                           public boolean isTitleSpecified(){
                               return localTitleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTitle(){
                               return localTitle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Title
                               */
                               public void setTitle(java.lang.String param){
                            localTitleTracker = true;
                                   
                                            this.localTitle=param;
                                    

                               }
                            

                        /**
                        * field for InstantMessagerID
                        */

                        
                                    protected java.lang.String localInstantMessagerID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInstantMessagerIDTracker = false ;

                           public boolean isInstantMessagerIDSpecified(){
                               return localInstantMessagerIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInstantMessagerID(){
                               return localInstantMessagerID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InstantMessagerID
                               */
                               public void setInstantMessagerID(java.lang.String param){
                            localInstantMessagerIDTracker = true;
                                   
                                            this.localInstantMessagerID=param;
                                    

                               }
                            

                        /**
                        * field for Location
                        */

                        
                                    protected java.lang.String localLocation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLocationTracker = false ;

                           public boolean isLocationSpecified(){
                               return localLocationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLocation(){
                               return localLocation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Location
                               */
                               public void setLocation(java.lang.String param){
                            localLocationTracker = true;
                                   
                                            this.localLocation=param;
                                    

                               }
                            

                        /**
                        * field for ThumbnailUpdateTime
                        */

                        
                                    protected java.util.Calendar localThumbnailUpdateTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localThumbnailUpdateTimeTracker = false ;

                           public boolean isThumbnailUpdateTimeSpecified(){
                               return localThumbnailUpdateTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getThumbnailUpdateTime(){
                               return localThumbnailUpdateTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ThumbnailUpdateTime
                               */
                               public void setThumbnailUpdateTime(java.util.Calendar param){
                            localThumbnailUpdateTimeTracker = true;
                                   
                                            this.localThumbnailUpdateTime=param;
                                    

                               }
                            

                        /**
                        * field for NeoRoomPermanentPairingDeviceUser
                        */

                        
                                    protected boolean localNeoRoomPermanentPairingDeviceUser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNeoRoomPermanentPairingDeviceUserTracker = false ;

                           public boolean isNeoRoomPermanentPairingDeviceUserSpecified(){
                               return localNeoRoomPermanentPairingDeviceUserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getNeoRoomPermanentPairingDeviceUser(){
                               return localNeoRoomPermanentPairingDeviceUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NeoRoomPermanentPairingDeviceUser
                               */
                               public void setNeoRoomPermanentPairingDeviceUser(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localNeoRoomPermanentPairingDeviceUserTracker =
                                       true;
                                   
                                            this.localNeoRoomPermanentPairingDeviceUser=param;
                                    

                               }
                            

                        /**
                        * field for Enabled
                        */

                        
                                    protected boolean localEnabled =
                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean("true");
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEnabledTracker = false ;

                           public boolean isEnabledSpecified(){
                               return localEnabledTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getEnabled(){
                               return localEnabled;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Enabled
                               */
                               public void setEnabled(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localEnabledTracker =
                                       true;
                                   
                                            this.localEnabled=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://portal.vidyo.com/admin/v1_1");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Member",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Member",
                           xmlWriter);
                   }

               
                   }
                if (localMemberIDTracker){
                                            if (localMemberID==null){
                                                 throw new org.apache.axis2.databinding.ADBException("memberID cannot be null!!");
                                            }
                                           localMemberID.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","memberID"),
                                               xmlWriter);
                                        }
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localPasswordTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "password", xmlWriter);
                             

                                          if (localPassword==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassword);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "displayName", xmlWriter);
                             

                                          if (localDisplayName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("displayName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDisplayName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "extension", xmlWriter);
                             

                                          if (localExtension==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("extension cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExtension);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                            if (localLanguage==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Language cannot be null!!");
                                            }
                                           localLanguage.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","Language"),
                                               xmlWriter);
                                        
                                            if (localRoleName==null){
                                                 throw new org.apache.axis2.databinding.ADBException("RoleName cannot be null!!");
                                            }
                                           localRoleName.serialize(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","RoleName"),
                                               xmlWriter);
                                        
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "groupName", xmlWriter);
                             

                                          if (localGroupName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("groupName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGroupName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localProxyNameTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "proxyName", xmlWriter);
                             

                                          if (localProxyName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("proxyName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProxyName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "emailAddress", xmlWriter);
                             

                                          if (localEmailAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("emailAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmailAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCreatedTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "created", xmlWriter);
                             

                                          if (localCreated==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("created cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreated));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDescriptionTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "description", xmlWriter);
                             

                                          if (localDescription==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("description cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDescription);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAllowCallDirectTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "allowCallDirect", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("allowCallDirect cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowCallDirect));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAllowPersonalMeetingTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "allowPersonalMeeting", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("allowPersonalMeeting cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowPersonalMeeting));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "locationTag", xmlWriter);
                             

                                          if (localLocationTag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("locationTag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocationTag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localCreationTimeTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "creationTime", xmlWriter);
                             

                                          if (localCreationTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("creationTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreationTime));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModificationTimeTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "modificationTime", xmlWriter);
                             

                                          if (localModificationTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("modificationTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModificationTime));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhone1Tracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "phone1", xmlWriter);
                             

                                          if (localPhone1==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone1);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhone2Tracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "phone2", xmlWriter);
                             

                                          if (localPhone2==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone2);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPhone3Tracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "phone3", xmlWriter);
                             

                                          if (localPhone3==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhone3);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDepartmentTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "department", xmlWriter);
                             

                                          if (localDepartment==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDepartment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTitleTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "title", xmlWriter);
                             

                                          if (localTitle==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localInstantMessagerIDTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "instantMessagerID", xmlWriter);
                             

                                          if (localInstantMessagerID==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInstantMessagerID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLocationTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "location", xmlWriter);
                             

                                          if (localLocation==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLocation);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localThumbnailUpdateTimeTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "thumbnailUpdateTime", xmlWriter);
                             

                                          if (localThumbnailUpdateTime==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localThumbnailUpdateTime));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNeoRoomPermanentPairingDeviceUserTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "neoRoomPermanentPairingDeviceUser", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("neoRoomPermanentPairingDeviceUser cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNeoRoomPermanentPairingDeviceUser));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEnabledTracker){
                                    namespace = "http://portal.vidyo.com/admin/v1_1";
                                    writeStartElement(null, namespace, "enabled", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("enabled cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnabled));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://portal.vidyo.com/admin/v1_1")){
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

                 if (localMemberIDTracker){
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "memberID"));
                            
                            
                                    if (localMemberID==null){
                                         throw new org.apache.axis2.databinding.ADBException("memberID cannot be null!!");
                                    }
                                    elementList.add(localMemberID);
                                }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                        }
                                     if (localPasswordTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "password"));
                                 
                                         elementList.add(localPassword==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "displayName"));
                                 
                                        if (localDisplayName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDisplayName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("displayName cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "extension"));
                                 
                                        if (localExtension != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExtension));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("extension cannot be null!!");
                                        }
                                    
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "Language"));
                            
                            
                                    if (localLanguage==null){
                                         throw new org.apache.axis2.databinding.ADBException("Language cannot be null!!");
                                    }
                                    elementList.add(localLanguage);
                                
                            elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "RoleName"));
                            
                            
                                    if (localRoleName==null){
                                         throw new org.apache.axis2.databinding.ADBException("RoleName cannot be null!!");
                                    }
                                    elementList.add(localRoleName);
                                
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "groupName"));
                                 
                                        if (localGroupName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGroupName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("groupName cannot be null!!");
                                        }
                                     if (localProxyNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "proxyName"));
                                 
                                        if (localProxyName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProxyName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("proxyName cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "emailAddress"));
                                 
                                        if (localEmailAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmailAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("emailAddress cannot be null!!");
                                        }
                                     if (localCreatedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "created"));
                                 
                                        if (localCreated != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreated));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("created cannot be null!!");
                                        }
                                    } if (localDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "description"));
                                 
                                        if (localDescription != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescription));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("description cannot be null!!");
                                        }
                                    } if (localAllowCallDirectTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "allowCallDirect"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowCallDirect));
                            } if (localAllowPersonalMeetingTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "allowPersonalMeeting"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllowPersonalMeeting));
                            }
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "locationTag"));
                                 
                                        if (localLocationTag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocationTag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("locationTag cannot be null!!");
                                        }
                                     if (localCreationTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "creationTime"));
                                 
                                        if (localCreationTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreationTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("creationTime cannot be null!!");
                                        }
                                    } if (localModificationTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "modificationTime"));
                                 
                                        if (localModificationTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModificationTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("modificationTime cannot be null!!");
                                        }
                                    } if (localPhone1Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "phone1"));
                                 
                                         elementList.add(localPhone1==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone1));
                                    } if (localPhone2Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "phone2"));
                                 
                                         elementList.add(localPhone2==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone2));
                                    } if (localPhone3Tracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "phone3"));
                                 
                                         elementList.add(localPhone3==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone3));
                                    } if (localDepartmentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "department"));
                                 
                                         elementList.add(localDepartment==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDepartment));
                                    } if (localTitleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "title"));
                                 
                                         elementList.add(localTitle==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitle));
                                    } if (localInstantMessagerIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "instantMessagerID"));
                                 
                                         elementList.add(localInstantMessagerID==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstantMessagerID));
                                    } if (localLocationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "location"));
                                 
                                         elementList.add(localLocation==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLocation));
                                    } if (localThumbnailUpdateTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "thumbnailUpdateTime"));
                                 
                                         elementList.add(localThumbnailUpdateTime==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localThumbnailUpdateTime));
                                    } if (localNeoRoomPermanentPairingDeviceUserTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "neoRoomPermanentPairingDeviceUser"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNeoRoomPermanentPairingDeviceUser));
                            } if (localEnabledTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1",
                                                                      "enabled"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEnabled));
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
        public static Member parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Member object =
                new Member();

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
                    
                            if (!"Member".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Member)com.vidyo.portal.admin.v1_1.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","memberID").equals(reader.getName())){
                                
                                                object.setMemberID(com.vidyo.portal.admin.v1_1.EntityID.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","name").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"name" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","password").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassword(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","displayName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"displayName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDisplayName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","extension").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"extension" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExtension(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","Language").equals(reader.getName())){
                                
                                                object.setLanguage(com.vidyo.portal.admin.v1_1.Language_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","RoleName").equals(reader.getName())){
                                
                                                object.setRoleName(com.vidyo.portal.admin.v1_1.RoleName_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","groupName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"groupName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGroupName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","proxyName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"proxyName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProxyName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","emailAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"emailAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmailAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","created").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"created" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreated(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDate(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","description").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"description" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","allowCallDirect").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"allowCallDirect" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAllowCallDirect(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","allowPersonalMeeting").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"allowPersonalMeeting" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAllowPersonalMeeting(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","locationTag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"locationTag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocationTag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","creationTime").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"creationTime" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreationTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","modificationTime").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"modificationTime" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModificationTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","phone1").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone1(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","phone2").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone2(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","phone3").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhone3(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","department").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDepartment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","title").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","instantMessagerID").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInstantMessagerID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","location").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLocation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","thumbnailUpdateTime").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setThumbnailUpdateTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","neoRoomPermanentPairingDeviceUser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"neoRoomPermanentPairingDeviceUser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNeoRoomPermanentPairingDeviceUser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://portal.vidyo.com/admin/v1_1","enabled").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"enabled" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEnabled(
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
           
    