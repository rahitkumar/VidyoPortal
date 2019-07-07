
/**
 * VidyoManagerServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.vidyo.ws.manager;

        

        /*
        *  VidyoManagerServiceStub java implementation
        */

        
        public class VidyoManagerServiceStub extends org.apache.axis2.client.Stub
        implements VidyoManagerService{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("VidyoManagerService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[32];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "inviteEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "makeCall"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "disconnectAll"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "startAlert"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "startRing"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "deleteConference"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "allowExternalLink"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[6]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "removeEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[7]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "changeConference"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[8]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "createExternalLink"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[9]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "setLicense"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[10]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "removeSpontaneousEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[11]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getSOAPConfig"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[12]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "clearLicense"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[13]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getVidyoManagerSystemID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[14]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "removeLicensedEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[15]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "stopRing"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[16]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "stopAlert"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[17]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "createConference"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[18]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "addEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[19]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "infoForEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[20]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getEMCPConfig"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[21]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "cancelInviteEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[22]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getLicenseData"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[23]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "addLicensedEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[24]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getPortalConfig"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[25]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "addSpontaneousEndpoint"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[26]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "removeExternalLink"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[27]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getGroups"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[28]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "getVidyoRouters"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[29]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "setVidyoManagerLicense"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[30]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://ws.vidyo.com/manager", "connectExternalLink"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[31]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "inviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "inviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "inviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "inviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "inviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "inviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "inviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "inviteEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "inviteEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "inviteEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "makeCall"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "makeCall"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "makeCall"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "makeCall"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "makeCall"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "makeCall"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "makeCall"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "makeCall"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "makeCall"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "makeCall"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "makeCall"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "makeCall"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "makeCall"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "makeCall"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "makeCall"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "makeCall"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "makeCall"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "makeCall"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "disconnectAll"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "disconnectAll"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "disconnectAll"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "disconnectAll"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "disconnectAll"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "disconnectAll"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "disconnectAll"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "disconnectAll"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "disconnectAll"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "disconnectAll"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "disconnectAll"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "disconnectAll"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "disconnectAll"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "disconnectAll"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "disconnectAll"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startAlert"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startAlert"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startAlert"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startAlert"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startAlert"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startAlert"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startAlert"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startAlert"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startAlert"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startAlert"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startAlert"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startAlert"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startAlert"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startAlert"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startAlert"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startRing"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startRing"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "startRing"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startRing"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startRing"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "startRing"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startRing"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startRing"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "startRing"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startRing"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startRing"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "startRing"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startRing"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startRing"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "startRing"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startRing"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startRing"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "startRing"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "deleteConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "deleteConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "deleteConference"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "deleteConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "deleteConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "deleteConference"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "deleteConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "deleteConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "deleteConference"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "deleteConference"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "deleteConference"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "deleteConference"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "deleteConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "deleteConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "deleteConference"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "allowExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "allowExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "allowExternalLink"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "allowExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "allowExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "allowExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "allowExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "allowExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "allowExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "allowExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "allowExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "allowExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "allowExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "allowExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "allowExternalLink"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeEndpoint"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "changeConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "changeConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "changeConference"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "changeConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "changeConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "changeConference"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "changeConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "changeConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "changeConference"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "changeConference"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "changeConference"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "changeConference"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "changeConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "changeConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "changeConference"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createExternalLink"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "createExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "createExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "createExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createExternalLink"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setLicense"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setLicense"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setLicense"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getSOAPConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getSOAPConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getSOAPConfig"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getSOAPConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getSOAPConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getSOAPConfig"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getSOAPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getSOAPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getSOAPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getSOAPConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getSOAPConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getSOAPConfig"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "clearLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "clearLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "clearLicense"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "clearLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "clearLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "clearLicense"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "clearLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "clearLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "clearLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "clearLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "clearLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "clearLicense"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoManagerSystemID"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopRing"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopRing"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopRing"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopRing"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopRing"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopRing"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopRing"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopRing"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopRing"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopRing"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopRing"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopRing"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopRing"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopRing"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopRing"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopRing"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopRing"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopRing"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopAlert"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopAlert"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "stopAlert"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopAlert"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopAlert"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "stopAlert"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "stopAlert"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "stopAlert"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopAlert"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopAlert"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "stopAlert"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createConference"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "createConference"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createConference"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "createConference"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createConference"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "createConference"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createConference"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "createConference"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "addEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "addEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "addEndpoint"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "infoForEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "infoForEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "infoForEndpoint"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "infoForEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "infoForEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "infoForEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "infoForEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "infoForEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "infoForEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "infoForEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "infoForEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "infoForEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "infoForEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "infoForEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "infoForEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getEMCPConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getEMCPConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getEMCPConfig"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getEMCPConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getEMCPConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getEMCPConfig"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getEMCPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getEMCPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getEMCPConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getEMCPConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getEMCPConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getEMCPConfig"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "cancelInviteEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getLicenseData"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getLicenseData"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getLicenseData"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getLicenseData"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getLicenseData"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getLicenseData"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getLicenseData"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getLicenseData"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getLicenseData"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getLicenseData"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getLicenseData"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getLicenseData"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addLicensedEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getPortalConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getPortalConfig"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getPortalConfig"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getPortalConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getPortalConfig"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getPortalConfig"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getPortalConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getPortalConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getPortalConfig"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getPortalConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getPortalConfig"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getPortalConfig"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ConferenceNotExistFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.ConferenceNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "addSpontaneousEndpoint"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "removeExternalLink"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "removeExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "removeExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "removeExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "removeExternalLink"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getGroups"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getGroups"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getGroups"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getGroups"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getGroups"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getGroups"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getGroups"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getGroups"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getGroups"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getGroups"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getGroups"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getGroups"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getVidyoRouters"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getVidyoRouters"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "getVidyoRouters"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getVidyoRouters"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getVidyoRouters"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "getVidyoRouters"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoRouters"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoRouters"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "getVidyoRouters"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoRouters"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoRouters"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "getVidyoRouters"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "setVidyoManagerLicense"),"com.vidyo.ws.manager.GeneralFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "connectExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "connectExternalLink"),"com.vidyo.ws.manager.NotLicensedFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","NotLicensedFault"), "connectExternalLink"),"com.vidyo.ws.manager.NotLicensedFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "connectExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "connectExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","EndpointNotExistFault"), "connectExternalLink"),"com.vidyo.ws.manager.EndpointNotExistFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "connectExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "connectExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","InvalidArgumentFault"), "connectExternalLink"),"com.vidyo.ws.manager.InvalidArgumentFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "connectExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "connectExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","ResourceNotAvailableFault"), "connectExternalLink"),"com.vidyo.ws.manager.ResourceNotAvailableFault");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "connectExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "connectExternalLink"),"com.vidyo.ws.manager.GeneralFaultException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://ws.vidyo.com/manager","GeneralFault"), "connectExternalLink"),"com.vidyo.ws.manager.GeneralFault");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public VidyoManagerServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public VidyoManagerServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public VidyoManagerServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://localhost:VM_SOAP_PORT/vidyo/services/VidyoManagerService" );
                
    }

    /**
     * Default Constructor
     */
    public VidyoManagerServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("http://localhost:VM_SOAP_PORT/vidyo/services/VidyoManagerService" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public VidyoManagerServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * Make a 2-party call between given endpoints using the given conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#inviteEndpoint
                     * @param inviteEndpointRequest66
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.InviteEndpointResponse inviteEndpoint(

                            com.vidyo.ws.manager.InviteEndpointRequest inviteEndpointRequest66)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("inviteEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inviteEndpointRequest66,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "inviteEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "inviteEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.InviteEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.InviteEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Make a 2-party call between given endpoints using the given conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startinviteEndpoint
                    * @param inviteEndpointRequest66
                
                */
                public  void startinviteEndpoint(

                 com.vidyo.ws.manager.InviteEndpointRequest inviteEndpointRequest66,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("inviteEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inviteEndpointRequest66,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "inviteEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "inviteEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.InviteEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinviteEndpoint(
                                        (com.vidyo.ws.manager.InviteEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinviteEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"inviteEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorinviteEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorinviteEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinviteEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorinviteEndpoint(f);
									    }
									} else {
									    callback.receiveErrorinviteEndpoint(f);
									}
								} else {
								    callback.receiveErrorinviteEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorinviteEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Make a 2-party call between given endpoints using the given conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#makeCall
                     * @param makeCallRequest68
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.MakeCallResponse makeCall(

                            com.vidyo.ws.manager.MakeCallRequest makeCallRequest68)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("makeCall");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    makeCallRequest68,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "makeCall")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "makeCall"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.MakeCallResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.MakeCallResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Make a 2-party call between given endpoints using the given conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startmakeCall
                    * @param makeCallRequest68
                
                */
                public  void startmakeCall(

                 com.vidyo.ws.manager.MakeCallRequest makeCallRequest68,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("makeCall");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    makeCallRequest68,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "makeCall")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "makeCall"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.MakeCallResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultmakeCall(
                                        (com.vidyo.ws.manager.MakeCallResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrormakeCall(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"makeCall"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrormakeCall((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrormakeCall(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrormakeCall(f);
                                            }
									    } else {
										    callback.receiveErrormakeCall(f);
									    }
									} else {
									    callback.receiveErrormakeCall(f);
									}
								} else {
								    callback.receiveErrormakeCall(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrormakeCall(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Deletes a Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#disconnectAll
                     * @param disconnectAllRequest70
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.DisconnectAllResponse disconnectAll(

                            com.vidyo.ws.manager.DisconnectAllRequest disconnectAllRequest70)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("disconnectAll");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    disconnectAllRequest70,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "disconnectAll")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "disconnectAll"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.DisconnectAllResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.DisconnectAllResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Deletes a Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startdisconnectAll
                    * @param disconnectAllRequest70
                
                */
                public  void startdisconnectAll(

                 com.vidyo.ws.manager.DisconnectAllRequest disconnectAllRequest70,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("disconnectAll");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    disconnectAllRequest70,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "disconnectAll")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "disconnectAll"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.DisconnectAllResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultdisconnectAll(
                                        (com.vidyo.ws.manager.DisconnectAllResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrordisconnectAll(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"disconnectAll"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrordisconnectAll((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrordisconnectAll((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrordisconnectAll((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrordisconnectAll((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrordisconnectAll((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrordisconnectAll(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordisconnectAll(f);
                                            }
									    } else {
										    callback.receiveErrordisconnectAll(f);
									    }
									} else {
									    callback.receiveErrordisconnectAll(f);
									}
								} else {
								    callback.receiveErrordisconnectAll(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrordisconnectAll(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[2].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[2].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Send Invitation to second party in call
                     * @see com.vidyo.ws.manager.VidyoManagerService#startAlert
                     * @param startAlertRequest72
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.StartAlertResponse startAlert(

                            com.vidyo.ws.manager.StartAlertRequest startAlertRequest72)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("startAlert");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    startAlertRequest72,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startAlert")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startAlert"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.StartAlertResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.StartAlertResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Send Invitation to second party in call
                * @see com.vidyo.ws.manager.VidyoManagerService#startstartAlert
                    * @param startAlertRequest72
                
                */
                public  void startstartAlert(

                 com.vidyo.ws.manager.StartAlertRequest startAlertRequest72,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
             _operationClient.getOptions().setAction("startAlert");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    startAlertRequest72,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startAlert")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startAlert"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.StartAlertResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultstartAlert(
                                        (com.vidyo.ws.manager.StartAlertResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorstartAlert(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startAlert"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorstartAlert((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorstartAlert(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartAlert(f);
                                            }
									    } else {
										    callback.receiveErrorstartAlert(f);
									    }
									} else {
									    callback.receiveErrorstartAlert(f);
									}
								} else {
								    callback.receiveErrorstartAlert(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorstartAlert(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[3].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[3].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Send Invitation to second party in call
                     * @see com.vidyo.ws.manager.VidyoManagerService#startRing
                     * @param startRingRequest74
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.StartRingResponse startRing(

                            com.vidyo.ws.manager.StartRingRequest startRingRequest74)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("startRing");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    startRingRequest74,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startRing")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startRing"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.StartRingResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.StartRingResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Send Invitation to second party in call
                * @see com.vidyo.ws.manager.VidyoManagerService#startstartRing
                    * @param startRingRequest74
                
                */
                public  void startstartRing(

                 com.vidyo.ws.manager.StartRingRequest startRingRequest74,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
             _operationClient.getOptions().setAction("startRing");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    startRingRequest74,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startRing")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "startRing"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.StartRingResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultstartRing(
                                        (com.vidyo.ws.manager.StartRingResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorstartRing(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"startRing"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorstartRing((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorstartRing(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstartRing(f);
                                            }
									    } else {
										    callback.receiveErrorstartRing(f);
									    }
									} else {
									    callback.receiveErrorstartRing(f);
									}
								} else {
								    callback.receiveErrorstartRing(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorstartRing(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[4].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[4].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Deletes a Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#deleteConference
                     * @param deleteConferenceRequest76
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.DeleteConferenceResponse deleteConference(

                            com.vidyo.ws.manager.DeleteConferenceRequest deleteConferenceRequest76)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("deleteConference");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteConferenceRequest76,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "deleteConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "deleteConference"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.DeleteConferenceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.DeleteConferenceResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Deletes a Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startdeleteConference
                    * @param deleteConferenceRequest76
                
                */
                public  void startdeleteConference(

                 com.vidyo.ws.manager.DeleteConferenceRequest deleteConferenceRequest76,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
             _operationClient.getOptions().setAction("deleteConference");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteConferenceRequest76,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "deleteConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "deleteConference"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.DeleteConferenceResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultdeleteConference(
                                        (com.vidyo.ws.manager.DeleteConferenceResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrordeleteConference(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"deleteConference"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrordeleteConference((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrordeleteConference((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrordeleteConference((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrordeleteConference((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrordeleteConference((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrordeleteConference(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrordeleteConference(f);
                                            }
									    } else {
										    callback.receiveErrordeleteConference(f);
									    }
									} else {
									    callback.receiveErrordeleteConference(f);
									}
								} else {
								    callback.receiveErrordeleteConference(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrordeleteConference(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[5].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[5].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Allow a link to an external conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#allowExternalLink
                     * @param allowExternalLinkRequest78
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.AllowExternalLinkResponse allowExternalLink(

                            com.vidyo.ws.manager.AllowExternalLinkRequest allowExternalLinkRequest78)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
              _operationClient.getOptions().setAction("allowExternalLink");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    allowExternalLinkRequest78,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "allowExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "allowExternalLink"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.AllowExternalLinkResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.AllowExternalLinkResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Allow a link to an external conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startallowExternalLink
                    * @param allowExternalLinkRequest78
                
                */
                public  void startallowExternalLink(

                 com.vidyo.ws.manager.AllowExternalLinkRequest allowExternalLinkRequest78,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
             _operationClient.getOptions().setAction("allowExternalLink");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    allowExternalLinkRequest78,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "allowExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "allowExternalLink"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.AllowExternalLinkResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultallowExternalLink(
                                        (com.vidyo.ws.manager.AllowExternalLinkResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorallowExternalLink(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"allowExternalLink"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorallowExternalLink((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorallowExternalLink((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorallowExternalLink((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorallowExternalLink((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorallowExternalLink((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorallowExternalLink(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorallowExternalLink(f);
                                            }
									    } else {
										    callback.receiveErrorallowExternalLink(f);
									    }
									} else {
									    callback.receiveErrorallowExternalLink(f);
									}
								} else {
								    callback.receiveErrorallowExternalLink(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorallowExternalLink(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[6].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[6].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Remove an Endpoint from Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#removeEndpoint
                     * @param removeEndpointRequest80
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.RemoveEndpointResponse removeEndpoint(

                            com.vidyo.ws.manager.RemoveEndpointRequest removeEndpointRequest80)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
              _operationClient.getOptions().setAction("removeEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeEndpointRequest80,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.RemoveEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.RemoveEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Remove an Endpoint from Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startremoveEndpoint
                    * @param removeEndpointRequest80
                
                */
                public  void startremoveEndpoint(

                 com.vidyo.ws.manager.RemoveEndpointRequest removeEndpointRequest80,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
             _operationClient.getOptions().setAction("removeEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeEndpointRequest80,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.RemoveEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultremoveEndpoint(
                                        (com.vidyo.ws.manager.RemoveEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorremoveEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorremoveEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorremoveEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorremoveEndpoint(f);
									    }
									} else {
									    callback.receiveErrorremoveEndpoint(f);
									}
								} else {
								    callback.receiveErrorremoveEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorremoveEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[7].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[7].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Change a Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#changeConference
                     * @param changeConferenceRequest82
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.ChangeConferenceResponse changeConference(

                            com.vidyo.ws.manager.ChangeConferenceRequest changeConferenceRequest82)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
              _operationClient.getOptions().setAction("changeConference");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    changeConferenceRequest82,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "changeConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "changeConference"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.ChangeConferenceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.ChangeConferenceResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Change a Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startchangeConference
                    * @param changeConferenceRequest82
                
                */
                public  void startchangeConference(

                 com.vidyo.ws.manager.ChangeConferenceRequest changeConferenceRequest82,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
             _operationClient.getOptions().setAction("changeConference");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    changeConferenceRequest82,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "changeConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "changeConference"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.ChangeConferenceResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultchangeConference(
                                        (com.vidyo.ws.manager.ChangeConferenceResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorchangeConference(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"changeConference"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorchangeConference((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorchangeConference((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorchangeConference((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorchangeConference((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorchangeConference((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorchangeConference(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorchangeConference(f);
                                            }
									    } else {
										    callback.receiveErrorchangeConference(f);
									    }
									} else {
									    callback.receiveErrorchangeConference(f);
									}
								} else {
								    callback.receiveErrorchangeConference(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorchangeConference(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[8].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[8].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * create a link to an external conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#createExternalLink
                     * @param createExternalLinkRequest84
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.CreateExternalLinkResponse createExternalLink(

                            com.vidyo.ws.manager.CreateExternalLinkRequest createExternalLinkRequest84)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
              _operationClient.getOptions().setAction("createExternalLink");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createExternalLinkRequest84,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createExternalLink"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.CreateExternalLinkResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.CreateExternalLinkResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * create a link to an external conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startcreateExternalLink
                    * @param createExternalLinkRequest84
                
                */
                public  void startcreateExternalLink(

                 com.vidyo.ws.manager.CreateExternalLinkRequest createExternalLinkRequest84,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
             _operationClient.getOptions().setAction("createExternalLink");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createExternalLinkRequest84,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createExternalLink"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.CreateExternalLinkResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultcreateExternalLink(
                                        (com.vidyo.ws.manager.CreateExternalLinkResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorcreateExternalLink(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createExternalLink"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorcreateExternalLink((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorcreateExternalLink((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorcreateExternalLink((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorcreateExternalLink((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorcreateExternalLink((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorcreateExternalLink(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateExternalLink(f);
                                            }
									    } else {
										    callback.receiveErrorcreateExternalLink(f);
									    }
									} else {
									    callback.receiveErrorcreateExternalLink(f);
									}
								} else {
								    callback.receiveErrorcreateExternalLink(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorcreateExternalLink(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[9].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[9].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Request to apply a new license
                     * @see com.vidyo.ws.manager.VidyoManagerService#setLicense
                     * @param setLicenseRequest86
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.SetLicenseResponse setLicense(

                            com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest86)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
              _operationClient.getOptions().setAction("setLicense");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setLicenseRequest86,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setLicense"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.SetLicenseResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.SetLicenseResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Request to apply a new license
                * @see com.vidyo.ws.manager.VidyoManagerService#startsetLicense
                    * @param setLicenseRequest86
                
                */
                public  void startsetLicense(

                 com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest86,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
             _operationClient.getOptions().setAction("setLicense");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setLicenseRequest86,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setLicense"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.SetLicenseResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultsetLicense(
                                        (com.vidyo.ws.manager.SetLicenseResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorsetLicense(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setLicense"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorsetLicense((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorsetLicense((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorsetLicense((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorsetLicense((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorsetLicense(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetLicense(f);
                                            }
									    } else {
										    callback.receiveErrorsetLicense(f);
									    }
									} else {
									    callback.receiveErrorsetLicense(f);
									}
								} else {
								    callback.receiveErrorsetLicense(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorsetLicense(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[10].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[10].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Remove an endpoint bypathing EMCP.
                     * @see com.vidyo.ws.manager.VidyoManagerService#removeSpontaneousEndpoint
                     * @param removeSpontaneousEndpointRequest88
                    
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse removeSpontaneousEndpoint(

                            com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest removeSpontaneousEndpointRequest88)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
              _operationClient.getOptions().setAction("removeSpontaneousEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeSpontaneousEndpointRequest88,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeSpontaneousEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeSpontaneousEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Remove an endpoint bypathing EMCP.
                * @see com.vidyo.ws.manager.VidyoManagerService#startremoveSpontaneousEndpoint
                    * @param removeSpontaneousEndpointRequest88
                
                */
                public  void startremoveSpontaneousEndpoint(

                 com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest removeSpontaneousEndpointRequest88,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
             _operationClient.getOptions().setAction("removeSpontaneousEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeSpontaneousEndpointRequest88,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeSpontaneousEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeSpontaneousEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultremoveSpontaneousEndpoint(
                                        (com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorremoveSpontaneousEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeSpontaneousEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorremoveSpontaneousEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorremoveSpontaneousEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorremoveSpontaneousEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorremoveSpontaneousEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorremoveSpontaneousEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveSpontaneousEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorremoveSpontaneousEndpoint(f);
									    }
									} else {
									    callback.receiveErrorremoveSpontaneousEndpoint(f);
									}
								} else {
								    callback.receiveErrorremoveSpontaneousEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorremoveSpontaneousEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[11].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[11].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get SOAP Configuration Infomation
                     * @see com.vidyo.ws.manager.VidyoManagerService#getSOAPConfig
                     * @param getSOAPConfigRequest90
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetSOAPConfigResponse getSOAPConfig(

                            com.vidyo.ws.manager.GetSOAPConfigRequest getSOAPConfigRequest90)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
              _operationClient.getOptions().setAction("getSOAPConfig");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getSOAPConfigRequest90,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getSOAPConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getSOAPConfig"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetSOAPConfigResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetSOAPConfigResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get SOAP Configuration Infomation
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetSOAPConfig
                    * @param getSOAPConfigRequest90
                
                */
                public  void startgetSOAPConfig(

                 com.vidyo.ws.manager.GetSOAPConfigRequest getSOAPConfigRequest90,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
             _operationClient.getOptions().setAction("getSOAPConfig");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getSOAPConfigRequest90,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getSOAPConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getSOAPConfig"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetSOAPConfigResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetSOAPConfig(
                                        (com.vidyo.ws.manager.GetSOAPConfigResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetSOAPConfig(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getSOAPConfig"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetSOAPConfig((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetSOAPConfig((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetSOAPConfig((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetSOAPConfig((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetSOAPConfig(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetSOAPConfig(f);
                                            }
									    } else {
										    callback.receiveErrorgetSOAPConfig(f);
									    }
									} else {
									    callback.receiveErrorgetSOAPConfig(f);
									}
								} else {
								    callback.receiveErrorgetSOAPConfig(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetSOAPConfig(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[12].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[12].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Request to clear the current system license
                     * @see com.vidyo.ws.manager.VidyoManagerService#clearLicense
                     * @param clearLicenseRequest92
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.ClearLicenseResponse clearLicense(

                            com.vidyo.ws.manager.ClearLicenseRequest clearLicenseRequest92)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
              _operationClient.getOptions().setAction("clearLicense");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    clearLicenseRequest92,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "clearLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "clearLicense"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.ClearLicenseResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.ClearLicenseResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Request to clear the current system license
                * @see com.vidyo.ws.manager.VidyoManagerService#startclearLicense
                    * @param clearLicenseRequest92
                
                */
                public  void startclearLicense(

                 com.vidyo.ws.manager.ClearLicenseRequest clearLicenseRequest92,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
             _operationClient.getOptions().setAction("clearLicense");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    clearLicenseRequest92,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "clearLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "clearLicense"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.ClearLicenseResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultclearLicense(
                                        (com.vidyo.ws.manager.ClearLicenseResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorclearLicense(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"clearLicense"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorclearLicense((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorclearLicense((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorclearLicense((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorclearLicense((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorclearLicense(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorclearLicense(f);
                                            }
									    } else {
										    callback.receiveErrorclearLicense(f);
									    }
									} else {
									    callback.receiveErrorclearLicense(f);
									}
								} else {
								    callback.receiveErrorclearLicense(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorclearLicense(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[13].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[13].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get Vidyo Manager System Identifier
                     * @see com.vidyo.ws.manager.VidyoManagerService#getVidyoManagerSystemID
                     * @param getVidyoManagerSystemIDRequest94
                    
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse getVidyoManagerSystemID(

                            com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest getVidyoManagerSystemIDRequest94)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
              _operationClient.getOptions().setAction("getVidyoManagerSystemID");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getVidyoManagerSystemIDRequest94,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoManagerSystemID")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoManagerSystemID"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get Vidyo Manager System Identifier
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetVidyoManagerSystemID
                    * @param getVidyoManagerSystemIDRequest94
                
                */
                public  void startgetVidyoManagerSystemID(

                 com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest getVidyoManagerSystemIDRequest94,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
             _operationClient.getOptions().setAction("getVidyoManagerSystemID");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getVidyoManagerSystemIDRequest94,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoManagerSystemID")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoManagerSystemID"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetVidyoManagerSystemID(
                                        (com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetVidyoManagerSystemID(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoManagerSystemID"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetVidyoManagerSystemID((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetVidyoManagerSystemID((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetVidyoManagerSystemID(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoManagerSystemID(f);
                                            }
									    } else {
										    callback.receiveErrorgetVidyoManagerSystemID(f);
									    }
									} else {
									    callback.receiveErrorgetVidyoManagerSystemID(f);
									}
								} else {
								    callback.receiveErrorgetVidyoManagerSystemID(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetVidyoManagerSystemID(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[14].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[14].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Request to remove a licensed endpoint
                     * @see com.vidyo.ws.manager.VidyoManagerService#removeLicensedEndpoint
                     * @param removeLicensedEndpointRequest96
                    
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.RemoveLicensedEndpointResponse removeLicensedEndpoint(

                            com.vidyo.ws.manager.RemoveLicensedEndpointRequest removeLicensedEndpointRequest96)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[15].getName());
              _operationClient.getOptions().setAction("removeLicensedEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeLicensedEndpointRequest96,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeLicensedEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeLicensedEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.RemoveLicensedEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.RemoveLicensedEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Request to remove a licensed endpoint
                * @see com.vidyo.ws.manager.VidyoManagerService#startremoveLicensedEndpoint
                    * @param removeLicensedEndpointRequest96
                
                */
                public  void startremoveLicensedEndpoint(

                 com.vidyo.ws.manager.RemoveLicensedEndpointRequest removeLicensedEndpointRequest96,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[15].getName());
             _operationClient.getOptions().setAction("removeLicensedEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeLicensedEndpointRequest96,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeLicensedEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeLicensedEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.RemoveLicensedEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultremoveLicensedEndpoint(
                                        (com.vidyo.ws.manager.RemoveLicensedEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorremoveLicensedEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeLicensedEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorremoveLicensedEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorremoveLicensedEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorremoveLicensedEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorremoveLicensedEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveLicensedEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorremoveLicensedEndpoint(f);
									    }
									} else {
									    callback.receiveErrorremoveLicensedEndpoint(f);
									}
								} else {
								    callback.receiveErrorremoveLicensedEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorremoveLicensedEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[15].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[15].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Stop send Invitation to second party in call.
                     * @see com.vidyo.ws.manager.VidyoManagerService#stopRing
                     * @param stopRingRequest98
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.StopRingResponse stopRing(

                            com.vidyo.ws.manager.StopRingRequest stopRingRequest98)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[16].getName());
              _operationClient.getOptions().setAction("stopRing");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    stopRingRequest98,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopRing")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopRing"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.StopRingResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.StopRingResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Stop send Invitation to second party in call.
                * @see com.vidyo.ws.manager.VidyoManagerService#startstopRing
                    * @param stopRingRequest98
                
                */
                public  void startstopRing(

                 com.vidyo.ws.manager.StopRingRequest stopRingRequest98,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[16].getName());
             _operationClient.getOptions().setAction("stopRing");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    stopRingRequest98,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopRing")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopRing"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.StopRingResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultstopRing(
                                        (com.vidyo.ws.manager.StopRingResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorstopRing(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopRing"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorstopRing((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorstopRing(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopRing(f);
                                            }
									    } else {
										    callback.receiveErrorstopRing(f);
									    }
									} else {
									    callback.receiveErrorstopRing(f);
									}
								} else {
								    callback.receiveErrorstopRing(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorstopRing(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[16].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[16].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Stop send Invitation to second party in call.
                     * @see com.vidyo.ws.manager.VidyoManagerService#stopAlert
                     * @param stopAlertRequest100
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.StopAlertResponse stopAlert(

                            com.vidyo.ws.manager.StopAlertRequest stopAlertRequest100)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[17].getName());
              _operationClient.getOptions().setAction("stopAlert");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    stopAlertRequest100,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopAlert")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopAlert"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.StopAlertResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.StopAlertResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Stop send Invitation to second party in call.
                * @see com.vidyo.ws.manager.VidyoManagerService#startstopAlert
                    * @param stopAlertRequest100
                
                */
                public  void startstopAlert(

                 com.vidyo.ws.manager.StopAlertRequest stopAlertRequest100,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[17].getName());
             _operationClient.getOptions().setAction("stopAlert");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    stopAlertRequest100,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopAlert")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "stopAlert"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.StopAlertResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultstopAlert(
                                        (com.vidyo.ws.manager.StopAlertResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorstopAlert(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"stopAlert"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorstopAlert((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorstopAlert(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorstopAlert(f);
                                            }
									    } else {
										    callback.receiveErrorstopAlert(f);
									    }
									} else {
									    callback.receiveErrorstopAlert(f);
									}
								} else {
								    callback.receiveErrorstopAlert(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorstopAlert(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[17].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[17].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Creates a Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#createConference
                     * @param createConferenceRequest102
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.CreateConferenceResponse createConference(

                            com.vidyo.ws.manager.CreateConferenceRequest createConferenceRequest102)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[18].getName());
              _operationClient.getOptions().setAction("createConference");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createConferenceRequest102,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createConference"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.CreateConferenceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.CreateConferenceResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Creates a Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startcreateConference
                    * @param createConferenceRequest102
                
                */
                public  void startcreateConference(

                 com.vidyo.ws.manager.CreateConferenceRequest createConferenceRequest102,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[18].getName());
             _operationClient.getOptions().setAction("createConference");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createConferenceRequest102,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createConference")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "createConference"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.CreateConferenceResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultcreateConference(
                                        (com.vidyo.ws.manager.CreateConferenceResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorcreateConference(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"createConference"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorcreateConference((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorcreateConference((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorcreateConference((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorcreateConference((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorcreateConference(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcreateConference(f);
                                            }
									    } else {
										    callback.receiveErrorcreateConference(f);
									    }
									} else {
									    callback.receiveErrorcreateConference(f);
									}
								} else {
								    callback.receiveErrorcreateConference(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorcreateConference(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[18].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[18].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Add an Endpoint to Conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#addEndpoint
                     * @param addEndpointRequest104
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.AddEndpointResponse addEndpoint(

                            com.vidyo.ws.manager.AddEndpointRequest addEndpointRequest104)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[19].getName());
              _operationClient.getOptions().setAction("addEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addEndpointRequest104,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.AddEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.AddEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Add an Endpoint to Conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startaddEndpoint
                    * @param addEndpointRequest104
                
                */
                public  void startaddEndpoint(

                 com.vidyo.ws.manager.AddEndpointRequest addEndpointRequest104,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[19].getName());
             _operationClient.getOptions().setAction("addEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addEndpointRequest104,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.AddEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultaddEndpoint(
                                        (com.vidyo.ws.manager.AddEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErroraddEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErroraddEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErroraddEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddEndpoint(f);
                                            }
									    } else {
										    callback.receiveErroraddEndpoint(f);
									    }
									} else {
									    callback.receiveErroraddEndpoint(f);
									}
								} else {
								    callback.receiveErroraddEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErroraddEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[19].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[19].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Control for an active conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#infoForEndpoint
                     * @param infoForEndpointRequest106
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.InfoForEndpointResponse infoForEndpoint(

                            com.vidyo.ws.manager.InfoForEndpointRequest infoForEndpointRequest106)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[20].getName());
              _operationClient.getOptions().setAction("infoForEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    infoForEndpointRequest106,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "infoForEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "infoForEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.InfoForEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.InfoForEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Control for an active conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startinfoForEndpoint
                    * @param infoForEndpointRequest106
                
                */
                public  void startinfoForEndpoint(

                 com.vidyo.ws.manager.InfoForEndpointRequest infoForEndpointRequest106,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[20].getName());
             _operationClient.getOptions().setAction("infoForEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    infoForEndpointRequest106,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "infoForEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "infoForEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.InfoForEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinfoForEndpoint(
                                        (com.vidyo.ws.manager.InfoForEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinfoForEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"infoForEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorinfoForEndpoint((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorinfoForEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorinfoForEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorinfoForEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorinfoForEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorinfoForEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinfoForEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorinfoForEndpoint(f);
									    }
									} else {
									    callback.receiveErrorinfoForEndpoint(f);
									}
								} else {
								    callback.receiveErrorinfoForEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorinfoForEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[20].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[20].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get EMCP Configuration Infomation
                     * @see com.vidyo.ws.manager.VidyoManagerService#getEMCPConfig
                     * @param getEMCPConfigRequest108
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetEMCPConfigResponse getEMCPConfig(

                            com.vidyo.ws.manager.GetEMCPConfigRequest getEMCPConfigRequest108)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[21].getName());
              _operationClient.getOptions().setAction("getEMCPConfig");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getEMCPConfigRequest108,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getEMCPConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getEMCPConfig"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetEMCPConfigResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetEMCPConfigResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get EMCP Configuration Infomation
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetEMCPConfig
                    * @param getEMCPConfigRequest108
                
                */
                public  void startgetEMCPConfig(

                 com.vidyo.ws.manager.GetEMCPConfigRequest getEMCPConfigRequest108,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[21].getName());
             _operationClient.getOptions().setAction("getEMCPConfig");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getEMCPConfigRequest108,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getEMCPConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getEMCPConfig"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetEMCPConfigResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetEMCPConfig(
                                        (com.vidyo.ws.manager.GetEMCPConfigResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetEMCPConfig(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getEMCPConfig"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetEMCPConfig((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetEMCPConfig((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetEMCPConfig((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetEMCPConfig((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetEMCPConfig(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetEMCPConfig(f);
                                            }
									    } else {
										    callback.receiveErrorgetEMCPConfig(f);
									    }
									} else {
									    callback.receiveErrorgetEMCPConfig(f);
									}
								} else {
								    callback.receiveErrorgetEMCPConfig(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetEMCPConfig(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[21].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[21].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Make a 2-party call between given endpoints using the given conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#cancelInviteEndpoint
                     * @param cancelInviteEndpointRequest110
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.CancelInviteEndpointResponse cancelInviteEndpoint(

                            com.vidyo.ws.manager.CancelInviteEndpointRequest cancelInviteEndpointRequest110)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[22].getName());
              _operationClient.getOptions().setAction("cancelInviteEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    cancelInviteEndpointRequest110,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "cancelInviteEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "cancelInviteEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.CancelInviteEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.CancelInviteEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Make a 2-party call between given endpoints using the given conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startcancelInviteEndpoint
                    * @param cancelInviteEndpointRequest110
                
                */
                public  void startcancelInviteEndpoint(

                 com.vidyo.ws.manager.CancelInviteEndpointRequest cancelInviteEndpointRequest110,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[22].getName());
             _operationClient.getOptions().setAction("cancelInviteEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    cancelInviteEndpointRequest110,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "cancelInviteEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "cancelInviteEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.CancelInviteEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultcancelInviteEndpoint(
                                        (com.vidyo.ws.manager.CancelInviteEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorcancelInviteEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"cancelInviteEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorcancelInviteEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorcancelInviteEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorcancelInviteEndpoint(f);
                                            }
									    } else {
										    callback.receiveErrorcancelInviteEndpoint(f);
									    }
									} else {
									    callback.receiveErrorcancelInviteEndpoint(f);
									}
								} else {
								    callback.receiveErrorcancelInviteEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorcancelInviteEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[22].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[22].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get current Effective License data
                     * @see com.vidyo.ws.manager.VidyoManagerService#getLicenseData
                     * @param getLicenseDataRequest112
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetLicenseDataResponse getLicenseData(

                            com.vidyo.ws.manager.GetLicenseDataRequest getLicenseDataRequest112)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[23].getName());
              _operationClient.getOptions().setAction("getLicenseData");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLicenseDataRequest112,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getLicenseData")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getLicenseData"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetLicenseDataResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetLicenseDataResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get current Effective License data
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetLicenseData
                    * @param getLicenseDataRequest112
                
                */
                public  void startgetLicenseData(

                 com.vidyo.ws.manager.GetLicenseDataRequest getLicenseDataRequest112,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[23].getName());
             _operationClient.getOptions().setAction("getLicenseData");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLicenseDataRequest112,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getLicenseData")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getLicenseData"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetLicenseDataResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetLicenseData(
                                        (com.vidyo.ws.manager.GetLicenseDataResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetLicenseData(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getLicenseData"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetLicenseData((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetLicenseData((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetLicenseData((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetLicenseData((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetLicenseData(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetLicenseData(f);
                                            }
									    } else {
										    callback.receiveErrorgetLicenseData(f);
									    }
									} else {
									    callback.receiveErrorgetLicenseData(f);
									}
								} else {
								    callback.receiveErrorgetLicenseData(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetLicenseData(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[23].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[23].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Request to add a licensed endpoint
                     * @see com.vidyo.ws.manager.VidyoManagerService#addLicensedEndpoint
                     * @param addLicensedEndpointRequest114
                    
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.AddLicensedEndpointResponse addLicensedEndpoint(

                            com.vidyo.ws.manager.AddLicensedEndpointRequest addLicensedEndpointRequest114)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[24].getName());
              _operationClient.getOptions().setAction("addLicensedEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addLicensedEndpointRequest114,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addLicensedEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addLicensedEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.AddLicensedEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.AddLicensedEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Request to add a licensed endpoint
                * @see com.vidyo.ws.manager.VidyoManagerService#startaddLicensedEndpoint
                    * @param addLicensedEndpointRequest114
                
                */
                public  void startaddLicensedEndpoint(

                 com.vidyo.ws.manager.AddLicensedEndpointRequest addLicensedEndpointRequest114,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[24].getName());
             _operationClient.getOptions().setAction("addLicensedEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addLicensedEndpointRequest114,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addLicensedEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addLicensedEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.AddLicensedEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultaddLicensedEndpoint(
                                        (com.vidyo.ws.manager.AddLicensedEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErroraddLicensedEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addLicensedEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErroraddLicensedEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErroraddLicensedEndpoint((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErroraddLicensedEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErroraddLicensedEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddLicensedEndpoint(f);
                                            }
									    } else {
										    callback.receiveErroraddLicensedEndpoint(f);
									    }
									} else {
									    callback.receiveErroraddLicensedEndpoint(f);
									}
								} else {
								    callback.receiveErroraddLicensedEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErroraddLicensedEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[24].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[24].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get Portal Configuration Infomation
                     * @see com.vidyo.ws.manager.VidyoManagerService#getPortalConfig
                     * @param getPortalConfigRequest116
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetPortalConfigResponse getPortalConfig(

                            com.vidyo.ws.manager.GetPortalConfigRequest getPortalConfigRequest116)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[25].getName());
              _operationClient.getOptions().setAction("getPortalConfig");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getPortalConfigRequest116,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getPortalConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getPortalConfig"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetPortalConfigResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetPortalConfigResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get Portal Configuration Infomation
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetPortalConfig
                    * @param getPortalConfigRequest116
                
                */
                public  void startgetPortalConfig(

                 com.vidyo.ws.manager.GetPortalConfigRequest getPortalConfigRequest116,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[25].getName());
             _operationClient.getOptions().setAction("getPortalConfig");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getPortalConfigRequest116,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getPortalConfig")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getPortalConfig"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetPortalConfigResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetPortalConfig(
                                        (com.vidyo.ws.manager.GetPortalConfigResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetPortalConfig(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getPortalConfig"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetPortalConfig((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetPortalConfig((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetPortalConfig((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetPortalConfig((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetPortalConfig(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetPortalConfig(f);
                                            }
									    } else {
										    callback.receiveErrorgetPortalConfig(f);
									    }
									} else {
									    callback.receiveErrorgetPortalConfig(f);
									}
								} else {
								    callback.receiveErrorgetPortalConfig(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetPortalConfig(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[25].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[25].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Add an endpoint bypathing EMCP.
                     * @see com.vidyo.ws.manager.VidyoManagerService#addSpontaneousEndpoint
                     * @param addSpontaneousEndpointRequest118
                    
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ConferenceNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.AddSpontaneousEndpointResponse addSpontaneousEndpoint(

                            com.vidyo.ws.manager.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest118)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ConferenceNotExistFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[26].getName());
              _operationClient.getOptions().setAction("addSpontaneousEndpoint");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addSpontaneousEndpointRequest118,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addSpontaneousEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addSpontaneousEndpoint"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.AddSpontaneousEndpointResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.AddSpontaneousEndpointResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
                          throw (com.vidyo.ws.manager.ConferenceNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Add an endpoint bypathing EMCP.
                * @see com.vidyo.ws.manager.VidyoManagerService#startaddSpontaneousEndpoint
                    * @param addSpontaneousEndpointRequest118
                
                */
                public  void startaddSpontaneousEndpoint(

                 com.vidyo.ws.manager.AddSpontaneousEndpointRequest addSpontaneousEndpointRequest118,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[26].getName());
             _operationClient.getOptions().setAction("addSpontaneousEndpoint");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addSpontaneousEndpointRequest118,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addSpontaneousEndpoint")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "addSpontaneousEndpoint"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.AddSpontaneousEndpointResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultaddSpontaneousEndpoint(
                                        (com.vidyo.ws.manager.AddSpontaneousEndpointResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErroraddSpontaneousEndpoint(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addSpontaneousEndpoint"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErroraddSpontaneousEndpoint((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ConferenceNotExistFaultException){
														callback.receiveErroraddSpontaneousEndpoint((com.vidyo.ws.manager.ConferenceNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErroraddSpontaneousEndpoint((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErroraddSpontaneousEndpoint(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddSpontaneousEndpoint(f);
                                            }
									    } else {
										    callback.receiveErroraddSpontaneousEndpoint(f);
									    }
									} else {
									    callback.receiveErroraddSpontaneousEndpoint(f);
									}
								} else {
								    callback.receiveErroraddSpontaneousEndpoint(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErroraddSpontaneousEndpoint(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[26].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[26].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * remove a link to an external conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#removeExternalLink
                     * @param removeExternalLinkRequest120
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.RemoveExternalLinkResponse removeExternalLink(

                            com.vidyo.ws.manager.RemoveExternalLinkRequest removeExternalLinkRequest120)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[27].getName());
              _operationClient.getOptions().setAction("removeExternalLink");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeExternalLinkRequest120,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeExternalLink"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.RemoveExternalLinkResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.RemoveExternalLinkResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * remove a link to an external conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startremoveExternalLink
                    * @param removeExternalLinkRequest120
                
                */
                public  void startremoveExternalLink(

                 com.vidyo.ws.manager.RemoveExternalLinkRequest removeExternalLinkRequest120,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[27].getName());
             _operationClient.getOptions().setAction("removeExternalLink");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeExternalLinkRequest120,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "removeExternalLink"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.RemoveExternalLinkResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultremoveExternalLink(
                                        (com.vidyo.ws.manager.RemoveExternalLinkResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorremoveExternalLink(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeExternalLink"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorremoveExternalLink((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorremoveExternalLink((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorremoveExternalLink((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorremoveExternalLink((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorremoveExternalLink((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorremoveExternalLink(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorremoveExternalLink(f);
                                            }
									    } else {
										    callback.receiveErrorremoveExternalLink(f);
									    }
									} else {
									    callback.receiveErrorremoveExternalLink(f);
									}
								} else {
								    callback.receiveErrorremoveExternalLink(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorremoveExternalLink(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[27].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[27].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get current Effective License data
                     * @see com.vidyo.ws.manager.VidyoManagerService#getGroups
                     * @param getGroupsRequest122
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetGroupsResponse getGroups(

                            com.vidyo.ws.manager.GetGroupsRequest getGroupsRequest122)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[28].getName());
              _operationClient.getOptions().setAction("getGroups");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getGroupsRequest122,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getGroups")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getGroups"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetGroupsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetGroupsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get current Effective License data
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetGroups
                    * @param getGroupsRequest122
                
                */
                public  void startgetGroups(

                 com.vidyo.ws.manager.GetGroupsRequest getGroupsRequest122,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[28].getName());
             _operationClient.getOptions().setAction("getGroups");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getGroupsRequest122,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getGroups")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getGroups"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetGroupsResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetGroups(
                                        (com.vidyo.ws.manager.GetGroupsResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetGroups(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getGroups"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetGroups((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetGroups((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetGroups((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetGroups((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetGroups(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetGroups(f);
                                            }
									    } else {
										    callback.receiveErrorgetGroups(f);
									    }
									} else {
									    callback.receiveErrorgetGroups(f);
									}
								} else {
								    callback.receiveErrorgetGroups(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetGroups(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[28].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[28].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Get currently available VidyoRouters
                     * @see com.vidyo.ws.manager.VidyoManagerService#getVidyoRouters
                     * @param getVidyoRoutersRequest124
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.GetVidyoRoutersResponse getVidyoRouters(

                            com.vidyo.ws.manager.GetVidyoRoutersRequest getVidyoRoutersRequest124)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[29].getName());
              _operationClient.getOptions().setAction("getVidyoRouters");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getVidyoRoutersRequest124,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoRouters")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoRouters"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.GetVidyoRoutersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.GetVidyoRoutersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Get currently available VidyoRouters
                * @see com.vidyo.ws.manager.VidyoManagerService#startgetVidyoRouters
                    * @param getVidyoRoutersRequest124
                
                */
                public  void startgetVidyoRouters(

                 com.vidyo.ws.manager.GetVidyoRoutersRequest getVidyoRoutersRequest124,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[29].getName());
             _operationClient.getOptions().setAction("getVidyoRouters");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getVidyoRoutersRequest124,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoRouters")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "getVidyoRouters"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.GetVidyoRoutersResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetVidyoRouters(
                                        (com.vidyo.ws.manager.GetVidyoRoutersResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetVidyoRouters(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getVidyoRouters"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorgetVidyoRouters((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorgetVidyoRouters((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorgetVidyoRouters((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorgetVidyoRouters((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetVidyoRouters(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetVidyoRouters(f);
                                            }
									    } else {
										    callback.receiveErrorgetVidyoRouters(f);
									    }
									} else {
									    callback.receiveErrorgetVidyoRouters(f);
									}
								} else {
								    callback.receiveErrorgetVidyoRouters(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetVidyoRouters(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[29].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[29].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * Request to apply a new VM license
                     * @see com.vidyo.ws.manager.VidyoManagerService#setVidyoManagerLicense
                     * @param setLicenseRequest126
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.SetLicenseResponse setVidyoManagerLicense(

                            com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest126)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[30].getName());
              _operationClient.getOptions().setAction("setVidyoManagerLicense");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setLicenseRequest126,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setVidyoManagerLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setVidyoManagerLicense"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.SetLicenseResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.SetLicenseResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * Request to apply a new VM license
                * @see com.vidyo.ws.manager.VidyoManagerService#startsetVidyoManagerLicense
                    * @param setLicenseRequest126
                
                */
                public  void startsetVidyoManagerLicense(

                 com.vidyo.ws.manager.SetLicenseRequest setLicenseRequest126,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[30].getName());
             _operationClient.getOptions().setAction("setVidyoManagerLicense");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setLicenseRequest126,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setVidyoManagerLicense")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "setVidyoManagerLicense"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.SetLicenseResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultsetVidyoManagerLicense(
                                        (com.vidyo.ws.manager.SetLicenseResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorsetVidyoManagerLicense(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"setVidyoManagerLicense"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorsetVidyoManagerLicense((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorsetVidyoManagerLicense((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorsetVidyoManagerLicense((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorsetVidyoManagerLicense((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorsetVidyoManagerLicense(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorsetVidyoManagerLicense(f);
                                            }
									    } else {
										    callback.receiveErrorsetVidyoManagerLicense(f);
									    }
									} else {
									    callback.receiveErrorsetVidyoManagerLicense(f);
									}
								} else {
								    callback.receiveErrorsetVidyoManagerLicense(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorsetVidyoManagerLicense(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[30].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[30].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * connect a link to an external conference
                     * @see com.vidyo.ws.manager.VidyoManagerService#connectExternalLink
                     * @param connectExternalLinkRequest128
                    
                     * @throws com.vidyo.ws.manager.NotLicensedFaultException : 
                     * @throws com.vidyo.ws.manager.EndpointNotExistFaultException : 
                     * @throws com.vidyo.ws.manager.InvalidArgumentFaultException : 
                     * @throws com.vidyo.ws.manager.ResourceNotAvailableFaultException : 
                     * @throws com.vidyo.ws.manager.GeneralFaultException : 
                     */

                    

                            public  com.vidyo.ws.manager.ConnectExternalLinkResponse connectExternalLink(

                            com.vidyo.ws.manager.ConnectExternalLinkRequest connectExternalLinkRequest128)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.vidyo.ws.manager.NotLicensedFaultException
                        ,com.vidyo.ws.manager.EndpointNotExistFaultException
                        ,com.vidyo.ws.manager.InvalidArgumentFaultException
                        ,com.vidyo.ws.manager.ResourceNotAvailableFaultException
                        ,com.vidyo.ws.manager.GeneralFaultException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[31].getName());
              _operationClient.getOptions().setAction("connectExternalLink");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    connectExternalLinkRequest128,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "connectExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "connectExternalLink"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.vidyo.ws.manager.ConnectExternalLinkResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.vidyo.ws.manager.ConnectExternalLinkResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
                          throw (com.vidyo.ws.manager.NotLicensedFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
                          throw (com.vidyo.ws.manager.EndpointNotExistFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
                          throw (com.vidyo.ws.manager.InvalidArgumentFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
                          throw (com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex;
                        }
                        
                        if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
                          throw (com.vidyo.ws.manager.GeneralFaultException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * connect a link to an external conference
                * @see com.vidyo.ws.manager.VidyoManagerService#startconnectExternalLink
                    * @param connectExternalLinkRequest128
                
                */
                public  void startconnectExternalLink(

                 com.vidyo.ws.manager.ConnectExternalLinkRequest connectExternalLinkRequest128,

                  final com.vidyo.ws.manager.VidyoManagerServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[31].getName());
             _operationClient.getOptions().setAction("connectExternalLink");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    connectExternalLinkRequest128,
                                                    optimizeContent(new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "connectExternalLink")), new javax.xml.namespace.QName("http://ws.vidyo.com/manager",
                                                    "connectExternalLink"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.vidyo.ws.manager.ConnectExternalLinkResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultconnectExternalLink(
                                        (com.vidyo.ws.manager.ConnectExternalLinkResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorconnectExternalLink(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"connectExternalLink"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof com.vidyo.ws.manager.NotLicensedFaultException){
														callback.receiveErrorconnectExternalLink((com.vidyo.ws.manager.NotLicensedFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.EndpointNotExistFaultException){
														callback.receiveErrorconnectExternalLink((com.vidyo.ws.manager.EndpointNotExistFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.InvalidArgumentFaultException){
														callback.receiveErrorconnectExternalLink((com.vidyo.ws.manager.InvalidArgumentFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.ResourceNotAvailableFaultException){
														callback.receiveErrorconnectExternalLink((com.vidyo.ws.manager.ResourceNotAvailableFaultException)ex);
											            return;
										            }
										            
													if (ex instanceof com.vidyo.ws.manager.GeneralFaultException){
														callback.receiveErrorconnectExternalLink((com.vidyo.ws.manager.GeneralFaultException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorconnectExternalLink(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorconnectExternalLink(f);
                                            }
									    } else {
										    callback.receiveErrorconnectExternalLink(f);
									    }
									} else {
									    callback.receiveErrorconnectExternalLink(f);
									}
								} else {
								    callback.receiveErrorconnectExternalLink(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorconnectExternalLink(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[31].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[31].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //http://localhost:VM_SOAP_PORT/vidyo/services/VidyoManagerService
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.InviteEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.InviteEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.InviteEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.InviteEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.NotLicensedFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.NotLicensedFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.EndpointNotExistFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.EndpointNotExistFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.InvalidArgumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.InvalidArgumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ResourceNotAvailableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ResourceNotAvailableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ConferenceNotExistFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ConferenceNotExistFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GeneralFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GeneralFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.MakeCallRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.MakeCallRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.MakeCallResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.MakeCallResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.DisconnectAllRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.DisconnectAllRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.DisconnectAllResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.DisconnectAllResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StartAlertRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StartAlertRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StartAlertResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StartAlertResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StartRingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StartRingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StartRingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StartRingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.DeleteConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.DeleteConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.DeleteConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.DeleteConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AllowExternalLinkRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AllowExternalLinkRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AllowExternalLinkResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AllowExternalLinkResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ChangeConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ChangeConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ChangeConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ChangeConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CreateExternalLinkRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CreateExternalLinkRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CreateExternalLinkResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CreateExternalLinkResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.SetLicenseRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.SetLicenseRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.SetLicenseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.SetLicenseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetSOAPConfigRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetSOAPConfigRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetSOAPConfigResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetSOAPConfigResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ClearLicenseRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ClearLicenseRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ClearLicenseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ClearLicenseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveLicensedEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveLicensedEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveLicensedEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveLicensedEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StopRingRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StopRingRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StopRingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StopRingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StopAlertRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StopAlertRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.StopAlertResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.StopAlertResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CreateConferenceRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CreateConferenceRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CreateConferenceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CreateConferenceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.InfoForEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.InfoForEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.InfoForEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.InfoForEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetEMCPConfigRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetEMCPConfigRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetEMCPConfigResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetEMCPConfigResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CancelInviteEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CancelInviteEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.CancelInviteEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.CancelInviteEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetLicenseDataRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetLicenseDataRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetLicenseDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetLicenseDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddLicensedEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddLicensedEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddLicensedEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddLicensedEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetPortalConfigRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetPortalConfigRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetPortalConfigResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetPortalConfigResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddSpontaneousEndpointRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddSpontaneousEndpointRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.AddSpontaneousEndpointResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.AddSpontaneousEndpointResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveExternalLinkRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveExternalLinkRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.RemoveExternalLinkResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.RemoveExternalLinkResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetGroupsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetGroupsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetGroupsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetGroupsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetVidyoRoutersRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetVidyoRoutersRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.GetVidyoRoutersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.GetVidyoRoutersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ConnectExternalLinkRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ConnectExternalLinkRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.vidyo.ws.manager.ConnectExternalLinkResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.vidyo.ws.manager.ConnectExternalLinkResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.InviteEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.InviteEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.MakeCallRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.MakeCallRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.DisconnectAllRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.DisconnectAllRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.StartAlertRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.StartAlertRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.StartRingRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.StartRingRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.DeleteConferenceRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.DeleteConferenceRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.AllowExternalLinkRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.AllowExternalLinkRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.RemoveEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.RemoveEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.ChangeConferenceRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.ChangeConferenceRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.CreateExternalLinkRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.CreateExternalLinkRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.SetLicenseRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.SetLicenseRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetSOAPConfigRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetSOAPConfigRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.ClearLicenseRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.ClearLicenseRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.RemoveLicensedEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.RemoveLicensedEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.StopRingRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.StopRingRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.StopAlertRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.StopAlertRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.CreateConferenceRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.CreateConferenceRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.AddEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.AddEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.InfoForEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.InfoForEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetEMCPConfigRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetEMCPConfigRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.CancelInviteEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.CancelInviteEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetLicenseDataRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetLicenseDataRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.AddLicensedEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.AddLicensedEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetPortalConfigRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetPortalConfigRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.AddSpontaneousEndpointRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.AddSpontaneousEndpointRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.RemoveExternalLinkRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.RemoveExternalLinkRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetGroupsRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetGroupsRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.GetVidyoRoutersRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.GetVidyoRoutersRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.vidyo.ws.manager.ConnectExternalLinkRequest param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.vidyo.ws.manager.ConnectExternalLinkRequest.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.vidyo.ws.manager.InviteEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.InviteEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InviteEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.InviteEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.MakeCallRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.MakeCallRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.MakeCallResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.MakeCallResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.DisconnectAllRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.DisconnectAllRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.DisconnectAllResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.DisconnectAllResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StartAlertRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.StartAlertRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StartAlertResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.StartAlertResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StartRingRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.StartRingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StartRingResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.StartRingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.DeleteConferenceRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.DeleteConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.DeleteConferenceResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.DeleteConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AllowExternalLinkRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.AllowExternalLinkRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AllowExternalLinkResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.AllowExternalLinkResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ChangeConferenceRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.ChangeConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ChangeConferenceResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.ChangeConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CreateExternalLinkRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.CreateExternalLinkRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CreateExternalLinkResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.CreateExternalLinkResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.SetLicenseRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.SetLicenseRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.SetLicenseResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.SetLicenseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveSpontaneousEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveSpontaneousEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetSOAPConfigRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetSOAPConfigRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetSOAPConfigResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetSOAPConfigResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ClearLicenseRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.ClearLicenseRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ClearLicenseResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.ClearLicenseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetVidyoManagerSystemIDRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetVidyoManagerSystemIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveLicensedEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveLicensedEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveLicensedEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveLicensedEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StopRingRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.StopRingRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StopRingResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.StopRingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StopAlertRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.StopAlertRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.StopAlertResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.StopAlertResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CreateConferenceRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.CreateConferenceRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CreateConferenceResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.CreateConferenceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InfoForEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.InfoForEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InfoForEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.InfoForEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetEMCPConfigRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetEMCPConfigRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetEMCPConfigResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetEMCPConfigResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CancelInviteEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.CancelInviteEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.CancelInviteEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.CancelInviteEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetLicenseDataRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetLicenseDataRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetLicenseDataResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetLicenseDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddLicensedEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddLicensedEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddLicensedEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddLicensedEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetPortalConfigRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetPortalConfigRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetPortalConfigResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetPortalConfigResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddSpontaneousEndpointRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddSpontaneousEndpointRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.AddSpontaneousEndpointResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.AddSpontaneousEndpointResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConferenceNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConferenceNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveExternalLinkRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveExternalLinkRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.RemoveExternalLinkResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.RemoveExternalLinkResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetGroupsRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetGroupsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetGroupsResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetGroupsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetVidyoRoutersRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetVidyoRoutersRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GetVidyoRoutersResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.GetVidyoRoutersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.SetLicenseRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.SetLicenseRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.SetLicenseResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.SetLicenseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConnectExternalLinkRequest.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConnectExternalLinkRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ConnectExternalLinkResponse.class.equals(type)){
                
                           return com.vidyo.ws.manager.ConnectExternalLinkResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.NotLicensedFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.NotLicensedFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.EndpointNotExistFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.EndpointNotExistFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.InvalidArgumentFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.InvalidArgumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.ResourceNotAvailableFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.ResourceNotAvailableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.vidyo.ws.manager.GeneralFault.class.equals(type)){
                
                           return com.vidyo.ws.manager.GeneralFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   