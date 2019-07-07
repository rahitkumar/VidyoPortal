package com.vidyo.parser.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.vidyo.parser.xml.vrconfig.*;
import com.vidyo.parser.xml.vrconfig.VRConfigType.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class VRConfig {
	
	// factory class to create sub types
	private ObjectFactory objFactory = null;
	
	//vmtype holds configuration data
	private VRConfigType vrtype = null;

    private Jaxb2Marshaller vrMarshaller;
	
	public VRConfig() {
        objFactory = new ObjectFactory();
        vrtype = objFactory.createVRConfigType();
        vrtype.setNumberOfThreads(3l);
        vrtype.setStat("");
	}

    public VRConfigType getVrtype() {
		return vrtype;
	}

	public VRConfig(Jaxb2Marshaller vrMarshaller) {
        this();
        this.vrMarshaller = vrMarshaller;
    }
	
	public void setDocumentVersion(int ver) {
		vrtype.setDocumentVersion(""+ver);
	}	
	
	public void addCMCPAddress(String ipAndPort) {
		CMCPAddressList scipList;
		if((scipList=vrtype.getCMCPAddressList())==null) {
			scipList = objFactory.createVRConfigTypeCMCPAddressList();
		}
		scipList.getCMCPListenAddress().add(ipAndPort);
        vrtype.setCMCPAddressList(scipList);
	}

    public void resetCMCPAddressPort(String ip, int port) {
		CMCPAddressList scipList;
		if((scipList=vrtype.getCMCPAddressList())==null) {
			scipList = objFactory.createVRConfigTypeCMCPAddressList();
		}
        List<String> scips = scipList.getCMCPListenAddress();
        for(int x=0; x<scips.size(); x++) {
            String originalIpPort = scips.get(x);
            String originalIp = originalIpPort.substring(0, originalIpPort.lastIndexOf(":"));
            if(ip!=null && ip.trim().length()>0) {
                scips.set(x, ip+":"+port);
            }
            else {
                scips.set(x, originalIp+":"+port);
            }
        }
    }
	
	public void setVidyoManagerAccessType(VMAccessType value) {
		vrtype.setVidyoManagerAccess(value);
	}
	
	public void addListenVMUri(String uri) {
        ListenVMList list = objFactory.createVRConfigTypeListenVMList();
        list.getListenVM().add(uri);
        vrtype.setListenVMList(list);
	}
	
	public void addConnectVMUri(String uri) {
		VrURI vmUri = objFactory.createVrURI();
        vmUri.setUri(uri);
        ConnectVMList list = objFactory.createVRConfigTypeConnectVMList();
        list.getConnectVM().add(vmUri);
        vrtype.setConnectVMList(list);
	}

    public void resetConnectVMUri(String vmId, String hostname) {
        ConnectVMList connVMList;
        VrURI vmUri;

        if((connVMList = vrtype.getConnectVMList()) == null) {
            connVMList = objFactory.createVRConfigTypeConnectVMList();
        }

        List<VrURI> list = connVMList.getConnectVM();
        for(int x=0; x<list.size(); x++) {
            VrURI vr = list.get(x);
            String uriStr = vr.getUri();

            String dstVMId = null;
            String dstHostname = null;
            if(uriStr.indexOf("@") != -1) {
                dstVMId = uriStr.substring(0, uriStr.indexOf("@"));
                dstHostname = uriStr.substring(uriStr.indexOf("@")+1, uriStr.lastIndexOf(":"));
            }
            else {
                dstVMId = null;
                dstHostname = uriStr.substring(0, uriStr.lastIndexOf(":"));
            }
            String originalPort = uriStr.substring(uriStr.lastIndexOf(":")+1);

            if(vmId != null && vmId.trim().length()>0) {
                dstVMId = vmId;
            }
            if(hostname != null && hostname.trim().length()>0) {
                dstHostname = hostname;
            }

            if(dstVMId != null && dstVMId.trim().length()>0) {
                vr.setUri(dstVMId+"@"+dstHostname+":"+originalPort);
            }
            else {
                vr.setUri(dstHostname+":"+originalPort);
            }
        }
    }
	
	public void addMediaAddress(String localIP, String remoteIP) {
        MediaAddressMap mediaAddress;
        mediaAddress = objFactory.createVRConfigTypeMediaAddressMap();
        mediaAddress.setLocalAddress(localIP);
        mediaAddress.setRemoteAddress(remoteIP);
        vrtype.getMediaAddressMap().add(mediaAddress);
	}
	
	public void clearMediaAddressMap() {
		vrtype.getMediaAddressMap().clear();
	}
	
	public void setSecurityEnabled(boolean secured) {
		vrtype.setSecurityEnabled(secured);
	}
	
	public void setStunServerAddress(String ip) {
		vrtype.setStunServerAddress(ip);
	}
	
	public void setVideoPrecedence(int low, int med, int high) {
        MediaStreamPrecedenceType msPrec;
        msPrec = objFactory.createMediaStreamPrecedenceType();
        msPrec.setHighPrioDSCPBits(high);
        msPrec.setMedPrioDSCPBits(med);
        msPrec.setLowPrioDSCPBits(low);
        vrtype.setVideoPrecedence(msPrec);
	}
	
	public void setAudioPrecedence(int low, int med, int high) {
        MediaStreamPrecedenceType msPrec;
        msPrec = objFactory.createMediaStreamPrecedenceType();
        msPrec.setHighPrioDSCPBits(high);
        msPrec.setMedPrioDSCPBits(med);
        msPrec.setLowPrioDSCPBits(low);
        vrtype.setAudioPrecedence(msPrec);
	}
	
	public void setAppPrecedence(int low, int med, int high) {
        MediaStreamPrecedenceType msPrec;
        msPrec = objFactory.createMediaStreamPrecedenceType();
        msPrec.setHighPrioDSCPBits(high);
        msPrec.setMedPrioDSCPBits(med);
        msPrec.setLowPrioDSCPBits(low);
        vrtype.setAppPrecedence(msPrec);
	}
	
	public void setLogFileName(String logFn) {
		vrtype.setLogFileName(logFn);
	}
	
	public void setLogLevel(String level) {
		vrtype.setLogLevel(level);
	}
	
	public void setLogFileHistoryDays(long days) {
		vrtype.setLogFileHistoryDays(days);
	}
	
	public void setMaxLogFileSizeKB(long kb) {
		vrtype.setMaxLogFileSizeKB(kb);
	}
	
	//marshalling
	public String toXml() throws JAXBException {
		String xmlStr = "";
		try{
            JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vrconfig" );
	        Marshaller m = jc.createMarshaller();
	        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            JAXBElement<VRConfigType> vr = objFactory.createConfig(vrtype);
	        StringWriter sw = new StringWriter();
	        m.marshal( vr, sw);
	        xmlStr = sw.toString();
	    }
	    catch(JAXBException anyEx){
	    	throw anyEx;
	    }
	    return xmlStr;
	}

    //unmarshalling
    public void fromXml(String xml) throws JAXBException {
        //JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vrconfig" );
        //Unmarshaller u = jc.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        JAXBElement<VRConfigType> jEl = (JAXBElement<VRConfigType>) this.vrMarshaller.unmarshal(new StreamSource(sr));
        vrtype = jEl.getValue();
    }
	
	//unmarshalling
	public static VRConfigType read(String filename) throws JAXBException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vrconfig" );
        Unmarshaller u = jc.createUnmarshaller(); 
        JAXBElement<VRConfigType> jEl = (JAXBElement<VRConfigType>)u.unmarshal(new FileInputStream( filename ));
        VRConfigType type = jEl.getValue();
        return type;
	}
	
    public void addVmIdToConnectVMUri(String vmId) {
        ConnectVMList connVMList;
        
        if((connVMList = vrtype.getConnectVMList()) == null) {
            connVMList = objFactory.createVRConfigTypeConnectVMList();
        }

        List<VrURI> list = connVMList.getConnectVM();
        for(int x=0; x<list.size(); x++) {
            VrURI vr = list.get(x);
            String uriStr = vr.getUri();
            String dstVMId = null;
            if(uriStr.indexOf("@") == -1) {
                dstVMId = vmId+"@"+uriStr;
                vr.setUri(dstVMId);                
            }else {
            	//If VM id is present
            	String oldVMId = uriStr.substring(0, uriStr.indexOf("@"));
            	String uri = uriStr.substring(uriStr.indexOf("@")+1);
            	//If the VM is is not the same, replace it
            	if(!oldVMId.equalsIgnoreCase(vmId)) {
            		vr.setUri(vmId+"@"+uri);
            	}
            }
        }
    }		


	public static void main(String[] args) {
		VRConfig test = new VRConfig();
		
		test.setDocumentVersion(17);
		test.addCMCPAddress("0.0.0.0:17990");
		test.setVidyoManagerAccessType(VMAccessType.CONNECT);
		test.addConnectVMUri("OIUEWRNDKFLJLJOWEJRLKDJFLSA00VM0001"+"@"+"localhost"+":"+"17991");
		
		test.clearMediaAddressMap();
		test.addMediaAddress("local1.2.3.4", "remote2.3.4.5");
		test.addMediaAddress("local11.22.33.66", "remote88.99.32.20");
		
		test.setSecurityEnabled(false);
		test.setStunServerAddress("stun8.7.6.9");
		test.setLogFileName("vr.log");
		test.setLogLevel("Debug Warning@localhost");
		test.setLogFileHistoryDays(3l);
		test.setMaxLogFileSizeKB(2000l);
		
		test.setVideoPrecedence(2,5,15);
		test.setAudioPrecedence(12, 22, 30);
		test.setAppPrecedence(1, 12, 29);
		
		try {
			System.out.println(test.toXml());
		}
		catch(Exception anyEx){
			anyEx.printStackTrace();
		}
	}

}


