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


import com.vidyo.parser.xml.vmconfig.*;
import com.vidyo.parser.xml.vmconfig.VMConfigType.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class VMConfig { 
	
	// factory class to create sub types
	private ObjectFactory objFactory = null;
	
	//vmtype holds configuration data
	private VMConfigType vmtype = null;

    private Jaxb2Marshaller vmMarshaller;
	
	public VMConfig() {
        objFactory = new ObjectFactory();
        vmtype = objFactory.createVMConfigType();
        vmtype.setNumberOfThreads(3l);
        
        //OK to comment following section as default value, those are obsoleted elements
        DatabaseConfig dbCfg = objFactory.createVMConfigTypeDatabaseConfig();
        dbCfg.setDBAddress("");
        dbCfg.setOdbcDSN("");
        dbCfg.setOdbcPwd(new byte[0]);
        dbCfg.setOdbcType("");
        dbCfg.setOdbcUID("");
        vmtype.setDatabaseConfig(dbCfg);
        vmtype.setStat("");
	}

    /**
	 * @return the vmtype
	 */
	public VMConfigType getVmtype() {
		return vmtype;
	}

	public VMConfig(Jaxb2Marshaller vmMarshaller) {
        this();
        this.vmMarshaller = vmMarshaller;
    }
	
	public void setDocumentVersion(int ver) {
		vmtype.setDocumentVersion(""+ver);
	}

    public int getDocumentVersion() {
        return Integer.parseInt(vmtype.getDocumentVersion());
    }

    public void setIPAddress(String ip) {
        if(ip!=null)
            vmtype.setIPAddress(ip);
    }

	public void addSOAPAddress(String ipAndPort) {
		SOAPAddressList soapList;
		if((soapList=vmtype.getSOAPAddressList()) == null) {
			soapList = objFactory.createVMConfigTypeSOAPAddressList();
		}
        soapList.getSOAPListenAddress().add(ipAndPort);
        vmtype.setSOAPAddressList(soapList);
	}
	
	public void addEMCPAddress(String ipAndport) {
		EMCPAddressList emcpList;
		if((emcpList=vmtype.getEMCPAddressList()) == null) {
			emcpList = objFactory.createVMConfigTypeEMCPAddressList();
		}
        emcpList.getEMCPListenAddress().add(ipAndport);
        vmtype.setEMCPAddressList(emcpList);
	}

    public void resetEmcpAddressPort(String dnsName, int port) {
		EMCPAddressList emcpList;
		if((emcpList=vmtype.getEMCPAddressList()) == null) {
			emcpList = objFactory.createVMConfigTypeEMCPAddressList();
		}
        List<String> emcps = emcpList.getEMCPListenAddress();
        for(int x=0; x<emcps.size(); x++) {
            String originalDnsPort = emcps.get(x);
            String originalDns = originalDnsPort.substring(0,originalDnsPort.lastIndexOf(":"));
            if(dnsName == null) {
                emcps.set(x, originalDns+":"+port);
            }
            else {
                emcps.set(x, dnsName+":"+port);
            }
        }
    }
    
    public void resetSOAPAddress(String dnsName) {
		SOAPAddressList soapAddressList;
		if((soapAddressList = vmtype.getSOAPAddressList()) == null) {
			soapAddressList = objFactory.createVMConfigTypeSOAPAddressList();
		}
        List<String> soapAddresses = soapAddressList.getSOAPListenAddress();
        for(int x = 0; x < soapAddresses.size(); x++) {
            String originalDnsPort = soapAddresses.get(x);
            String[] originalDnsPortArr = originalDnsPort.split(":");
            if(dnsName == null) {
            	soapAddresses.set(x, originalDnsPort);
            }
            else {
            	soapAddresses.set(x, dnsName+":"+originalDnsPortArr[1]);
            }
        }
    }    
	
	public void addRMCPAddress(String ipAndPort) {
		RMCPAddressList rmcpList;
		if((rmcpList=vmtype.getRMCPAddressList()) == null) {
			rmcpList = objFactory.createVMConfigTypeRMCPAddressList();
		}
        rmcpList.getRMCPListenAddress().add(ipAndPort);
        vmtype.setRMCPAddressList(rmcpList);
	}

    public void resetRmcpAddress(String ip) {
        if(ip == null) {
            return;
        }
		RMCPAddressList rmcpList;
		if((rmcpList=vmtype.getRMCPAddressList()) == null) {
			rmcpList = objFactory.createVMConfigTypeRMCPAddressList();
		}
        List<String> rmcps = rmcpList.getRMCPListenAddress();
        for(int x=0; x<rmcps.size(); x++) {
            String originalIpPort = rmcps.get(x);
            String originalPort   = originalIpPort.substring(originalIpPort.lastIndexOf(":")+1);
            rmcps.set(x, ip+":"+originalPort);
        }
    }
	
	public void addVidyoPortalUri(String uri) {
		VpURI vpUri = objFactory.createVpURI();
        vpUri.setUri(uri);
        //vpUri.setPriority(4);
        VidyoPortalAddressList portalList;
        if((portalList=vmtype.getVidyoPortalAddressList()) == null) {
        	portalList = objFactory.createVMConfigTypeVidyoPortalAddressList();
        }
        portalList.getVidyoPortalConnectAddress().add(vpUri);
        vmtype.setVidyoPortalAddressList(portalList);
	}

    public void resetVidyoPortalUri(String uriWithoutProtocol) {
        if(uriWithoutProtocol == null)
            return;

        VidyoPortalAddressList portalList;
        if((portalList=vmtype.getVidyoPortalAddressList()) == null) {
			portalList = objFactory.createVMConfigTypeVidyoPortalAddressList();
		}

        List<VpURI> vpURIList = portalList.getVidyoPortalConnectAddress();
        for(int x=0; x<vpURIList.size(); x++) {
            VpURI aVpUri = vpURIList.get(x);
            String protocol;
            if(aVpUri.getUri().toLowerCase().startsWith("https://")){
                protocol = "https://";
            }
            else{
                protocol = "http://";
            }
            aVpUri.setUri(protocol + uriWithoutProtocol);
        }
    }
	
	public void setSecurityEnabled(boolean secured) {
		vmtype.setSecurityEnabled(secured);
	}
	
	public void setLogFileName(String logFn) {
		vmtype.setLogFileName(logFn);
	}
	
	public void setLogLevel(String level) {
		vmtype.setLogLevel(level);
	}
	
	public void setLogFileHistoryDays(long days) {
		vmtype.setLogFileHistoryDays(days);
	}
	
	public void setMaxLogFileSizeKB(long kb) {
		vmtype.setMaxLogFileSizeKB(kb);
	}

    public void setSignalingPrecedence(long signaling) {
        vmtype.setSignalingPrecedence(signaling);
    }

	//marshalling
	public String toXml() throws JAXBException {
		String xmlStr = "";
		try{
            JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vmconfig" );
	        Marshaller m = jc.createMarshaller();
	        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            JAXBElement<VMConfigType> cloud = objFactory.createConfig(vmtype);
	        StringWriter sw = new StringWriter();
	        m.marshal( cloud, sw);
	        xmlStr = sw.toString();
	    }
	    catch(JAXBException anyEx){
	    	throw anyEx;
	    }
	    return xmlStr;
	}

    //unmarshalling
    public void fromXml(String xml) throws JAXBException {
        //JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vmconfig" );
        //Unmarshaller u = jc.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        JAXBElement<VMConfigType> jEl = (JAXBElement<VMConfigType>) this.vmMarshaller.unmarshal(new StreamSource(sr));
        vmtype = jEl.getValue();
    }

	//unmarshalling
	public static VMConfigType read(String filename) throws JAXBException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.vmconfig" );
        Unmarshaller u = jc.createUnmarshaller(); 
        JAXBElement<VMConfigType> jEl = (JAXBElement<VMConfigType>) u.unmarshal(new FileInputStream( filename ));
        VMConfigType type = jEl.getValue();
        return type;
	}


	
	public static void main(String[] args) {
		VMConfig test = new VMConfig();
		
		test.setDocumentVersion(12);
		test.addSOAPAddress("127.0.0.1:17995");
		test.addEMCPAddress("0.0.0.0:17992");
		test.addRMCPAddress("0.0.0.0:17991");
		test.addVidyoPortalUri("http://localhost");
		
		test.setSecurityEnabled(true);
		test.setLogFileName("vm.log");
		test.setLogLevel("Debug Warning@localhost");
		test.setLogFileHistoryDays(3l);
		test.setMaxLogFileSizeKB(2000l);
        test.setSignalingPrecedence(5);
		try {
			System.out.println(test.toXml());
		}
		catch(Exception anyEx){
			anyEx.printStackTrace();
		}
		
    }
}


