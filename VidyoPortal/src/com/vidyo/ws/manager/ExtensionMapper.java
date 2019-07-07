
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package com.vidyo.ws.manager;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "EndpointCallPref".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.EndpointCallPref.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "NEGroupType".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.NEGroupType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "AddressesPerVM".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.AddressesPerVM.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "Address".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.Address.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "RouterPoolPriorityList".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.RouterPoolPriorityList.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "RouterPoolList".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.RouterPoolList.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "VidyoRouterInfo".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.VidyoRouterInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "SpontaneousEndpointInfo".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.SpontaneousEndpointInfo.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://ws.vidyo.com/manager".equals(namespaceURI) &&
                  "LicenseFeatureData".equals(typeName)){
                   
                            return  com.vidyo.ws.manager.LicenseFeatureData.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    