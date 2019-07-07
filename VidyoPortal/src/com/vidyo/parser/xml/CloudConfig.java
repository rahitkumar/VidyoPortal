package com.vidyo.parser.xml;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.vidyo.parser.xml.cloudconfig.*;
import com.vidyo.parser.xml.cloudconfig.BandwidthMapType.BandwidthMapElement;
import com.vidyo.parser.xml.cloudconfig.LocationType.PrioritizedGroupLists;
import com.vidyo.parser.xml.cloudconfig.LocationType.PrioritizedGroupLists.GroupList;
import com.vidyo.parser.xml.cloudconfig.NetworkElement.SCIPAddressList;
import net.sf.ehcache.search.expression.EqualTo;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class CloudConfig {
	
	public final static int BANDWIDTH_HIGH = 100; 
	public final static int BANDWIDTH_MED  = 50;
	public final static int BANDWIDTH_LOW  = 25;
	public final static int BANDWIDTH_NONE = 0;
	
    // factory class to create sub types
	private ObjectFactory objFactory = null;
	
	//cloudtype holds configuration data
	private NetworkConfigType cloudtype = null;

    private Jaxb2Marshaller cloudMarshaller = null;
	

	public CloudConfig(){
        objFactory = new ObjectFactory();
        cloudtype = objFactory.createNetworkConfigType();

        //initial settings
		cloudtype.setCategories(objFactory.createCategoriesType());
		Candidates cads = objFactory.createCandidates();
		cads.getCandidate().add("EID");
		cads.getCandidate().add("LocalIP");
		cads.getCandidate().add("ExternalIP");
		cads.getCandidate().add("LocationTag");
		cloudtype.setLocationCandidates(cads);
	}

    public CloudConfig(Jaxb2Marshaller cloudMarshaller) {
        this();
        this.cloudMarshaller = cloudMarshaller;
    }
	
	public boolean addNetworkElement(String neID, String name, String type, String scipAddress, boolean requireValidation) {
		List<NetworkElement> nelist = this.cloudtype.getNetworkElement();
		if(requireValidation) {
			Iterator<NetworkElement> itr = nelist.iterator();
			while (itr.hasNext()) {
				NetworkElement el = itr.next();
				if(el.getName().equals(name)|| el.getIdentifier().equals(neID)) {
					return false;
				}
			}
		}
		NetworkElement ne = objFactory.createNetworkElement();
		ne.setIdentifier(neID);
		ne.setName(name);
		ne.setType(type);
		SCIPAddressList scipList = objFactory.createNetworkElementSCIPAddressList();
		scipList.getSCIPListenAddress().add(scipAddress);
		ne.setSCIPAddressList(scipList);
		nelist.add(ne);
		return true;
	}

    /**
     * In NetworkConfig.xml, locate the VR by id, and reset correcponding SCIPListenAddress element
     * For example: <SCIPListenAddress>scip:0.0.0.0:17990;transport=TCP</SCIPListenAddress>   maybe replaced by
     *              <SCIPListenAddress>scip:fqdn.com:17990;transport=TCP</SCIPListenAddress>
     * @param neID
     * @param newIpOrFqdn   null for keeping original ip
     * @param newPort       -1 for keeping original port
     * @param newTransport  null for keeping original value of TCP/TLS
     */
    public void setNetworkElementScipById(String neID, String newIpOrFqdn, int newPort, String newTransport) {
        if(neID == null) {
            return;     // do nothing to NetworkConfig
        }
        List<NetworkElement> nelist = this.cloudtype.getNetworkElement();
        NetworkElement el = null;
        Iterator<NetworkElement> itr = nelist.iterator();
        while (itr.hasNext()) {
            el = itr.next();
            //NetworkElementConfiguration ID is unique, so this happens once
            if(el.getIdentifier().equals(neID)) {
                List<String> scipAddrs = el.getSCIPAddressList().getSCIPListenAddress();
                String scip = scipAddrs.get(0);
                String ip = (newIpOrFqdn==null)? scip.substring(scip.indexOf(":")+1, scip.lastIndexOf(":")) : newIpOrFqdn;
                String port = (newPort==-1)? scip.substring(scip.lastIndexOf(":")+1, scip.indexOf(";")) : (""+newPort);
                String transport = (newTransport==null)? scip.substring(scip.lastIndexOf("=")+1) : newTransport;
                String newScip = "scip:"+ip+":"+port+";transport="+transport;
                scipAddrs.set(0, newScip);
                return;
            }
        }
    }
	
	public boolean addPool(String poolID, String name, boolean requireValidation) {
		List<GroupType> poolList = this.cloudtype.getGroup();
		if(requireValidation) {
			Iterator<GroupType> itr = poolList.iterator();
			while(itr.hasNext()) {
				GroupType t = itr.next();
				if( t.getID().equals(poolID) || t.getName().equals(name) ){
					return false;
				}
			}
		}
		GroupType gt = objFactory.createGroupType();
		gt.setID(poolID);
		gt.setName(name);
		gt.setCategories(objFactory.createCategoriesType()); //blank <Categories>
		
		poolList.add(gt);
		return true;
	}
	
	public boolean assignVrToPool(String neID, String poolID, boolean requireValidation) {
		List<NetworkElement> nelist = this.cloudtype.getNetworkElement();
		List<GroupType> poolList = this.cloudtype.getGroup();
		if(requireValidation) {
			// check if neID is exist
			boolean found = false;
			Iterator<NetworkElement> itr = nelist.iterator();
			while (itr.hasNext()) {
				NetworkElement element = itr.next();
				if(element.getIdentifier().equals(neID)) {
					found = true;
				}
			}
			if(!found) {
				return false;
			}
			// check if poolID is exist
			found = false;
			Iterator<GroupType> gitr = poolList.iterator();
			while(gitr.hasNext()) {
				GroupType t = gitr.next();
				if( t.getID().equals(poolID)){
					found = true;
				}
			}
			if(!found) {
				return false;
			}
		}
		
		Iterator<GroupType> gitr = poolList.iterator();
		while(gitr.hasNext()) {
			GroupType t = gitr.next();
			if( t.getID().equals(poolID)){
				GroupType.NetworkElements nes = t.getNetworkElements();
				if(nes == null) {
					t.setNetworkElements(nes = objFactory.createGroupTypeNetworkElements());
				}
					
				nes.getIdentifier().add(neID);
			}
		}
		
		return true;
	}
	
	public boolean addLocation(String locID, String name, boolean requireValidation) {
		List<LocationType> locations = cloudtype.getLocation();
		if(requireValidation) {
			for(int i = 0; i<locations.size(); i++) {
				LocationType lt = locations.get(i);
				if(lt.getID().equals(locID) || lt.getName().equals(name) ) {
					return false;
				}
			}
		}
		
		LocationType locType = objFactory.createLocationType();
		locType.setID(locID);
		locType.setName(name);
		
		locations.add(locType);
		return true;
	}
	
	public boolean insertLocationAt(int index, String locID, String name, boolean requireValidation) {
		List<LocationType> locations = cloudtype.getLocation();
		if(requireValidation) {
			for(int i = 0; i<locations.size(); i++) {
				LocationType lt = locations.get(i);
				if(lt.getID().equals(locID) || lt.getName().equals(name) ) {
					return false;
				}
			}
		}
		
		LocationType locType = objFactory.createLocationType();
		locType.setID(locID);
		locType.setName(name);
		
		if( index >= locations.size( )) {
			locations.add(locType);
		}
		else {
			locations.add(index, locType);
		}
		return true;
	}

    public void replaceLocationTag(String oldLocTag, String newLocTag) {
        List<LocationType> locations = cloudtype.getLocation();
        for(int i=0; i<locations.size(); i++) {
            LocationType lt = locations.get(i);
            RuleSetType rst = lt.getRuleSet();
            if(rst == null) {
                continue;
            }
            else {
                OrType ort = rst.getOr();
                if(ort == null) {
                    continue;
                }

                List<BasicRuleSet> ruleList = ort.getArg();
                for(int j=0; j<ruleList.size(); j++) {
                    BasicRuleSet basicRuleSet = ruleList.get(j);
                    BasicAndType bat = basicRuleSet.getBasicAnd();
                    BasicRuleType brt = bat.getArg().get(0);
                    EqualToType et = brt.getEqualTo();
                    if(et.getCandidate().equalsIgnoreCase("LocationTag") &&
                        et.getValue().equalsIgnoreCase(oldLocTag) ) {
                        et.setValue(newLocTag);
                    }
                }
            }
        }
    }
	
	public boolean addBandwidthMap(String fromPoolID, String toPoolID, int weight, boolean requireValidation) {
		List<GroupType> poolList = this.cloudtype.getGroup();
		if(requireValidation) {
			// check if poolID is exist
			int found = 0;
			Iterator<GroupType> gitr = poolList.iterator();
			while(gitr.hasNext()) {
				GroupType t = gitr.next();
				String poolID = t.getID();
				if( poolID.equals(fromPoolID)) {
					found++;
				}
				if( poolID.equals(toPoolID)) {
					found++;
				}
			}
			if(found != 2) {
				return false;
			}
		}
		
		BandwidthMapType bm = cloudtype.getBandwidthMap();
		if(bm == null) {
			cloudtype.setBandwidthMap(bm = objFactory.createBandwidthMapType());
		}
		List<BandwidthMapElement> mapList = bm.getBandwidthMapElement();
		
		//check
		BandwidthMapElement oldBme = null;
		boolean addNew = true;
		for(int i = mapList.size()-1; i>=0; i--){
			oldBme = mapList.get(i);
			if(oldBme.getFromRouterGroupID().equals(fromPoolID) && oldBme.getToRouterGroupID().equals(toPoolID)) {
				addNew = false;
				break;
			}
		}
		if(!addNew) {
			oldBme.setWeightedBandwidth(weight);
		}
		else {
			BandwidthMapElement bme = objFactory.createBandwidthMapTypeBandwidthMapElement();
			bme.setFromRouterGroupID(fromPoolID);
			bme.setToRouterGroupID(toPoolID);
			bme.setWeightedBandwidth(weight);
			mapList.add(bme);
		}
		
		return true;
	}
	
	public boolean insertRuleToLocationAt(int index, String locID, BasicRuleSet brs, boolean requireValidation) {
		List<LocationType> locations = cloudtype.getLocation();
		LocationType lt = null;
		if(requireValidation) {
			for(int i = 0; i<locations.size(); i++) {
				lt = locations.get(i);
				if(lt.getID().equals(locID)) {
					break;
				}
			}
		}
		if(lt==null) {
			return false; //could not find this location
		}
		
		RuleSetType rst = lt.getRuleSet();
		if (rst == null) {
			lt.setRuleSet(rst=objFactory.createRuleSetType());
		}
	
		OrType ort = rst.getOr();
		if(ort == null) {
			rst.setOr(ort=objFactory.createOrType());
		}
		
		List<BasicRuleSet> ruleList = ort.getArg();
		if( index >= ruleList.size( )) {
			ruleList.add(brs);
		}
		else {
			ruleList.add(index, brs);
		}
		
		return true;
	}
	
	public BasicRuleType buildBasicRuleType_EqualTo(String cand, String value) {
		EqualToType et =  objFactory.createEqualToType();
		et.setCandidate(cand);
		et.setValue(value);
		BasicRuleType brt = objFactory.createBasicRuleType();
		brt.setEqualTo(et);
		return brt;
	}
	
	public BasicRuleType buildBasicRuleType_IpSubnet(String cand, String ipAddr, int cidr) {
		IPV4SubnetType ip4Subnet = objFactory.createIPV4SubnetType();
		ip4Subnet.setCandidate(cand);
		ip4Subnet.setIPAddr(ipAddr);
		ip4Subnet.setCIDRBits(new BigInteger(""+cidr));
		BasicRuleType brt = objFactory.createBasicRuleType();
		brt.setIPSubnet(ip4Subnet);
		return brt;
	}
	
	public BasicAndType buildBasicAnd(BasicRuleType param1, BasicRuleType param2) {
		BasicAndType bat = objFactory.createBasicAndType();
		
		if( param1 != null ) {
			bat.getArg().add(param1);
		}
		
		if( param2 != null ) {
			bat.getArg().add(param2);
		}
		return bat;
	}
	
	public BasicRuleSet buildBasicRuleSet(Object obj) {
		BasicRuleSet brs = objFactory.createBasicRuleSet();
		if(obj instanceof BasicAndType)
			brs.setBasicAnd((BasicAndType)obj);
		else if(obj instanceof BasicNotType)
			brs.setBasicNot((BasicNotType)obj);
		else if(obj instanceof BasicOrType)
			brs.setBasicOr((BasicOrType)obj);
		else if(obj instanceof BasicRuleType)
			brs.setBasicRule((BasicRuleType)obj);
		
		return brs;
	}
	
	public boolean insertPrioritizedPoolToLocationAt(int index, String locID, String poolID, boolean requireValidation) {
		List<LocationType> locations = cloudtype.getLocation();
		LocationType lt = null;
		if(requireValidation) {
			for(int i = 0; i<locations.size(); i++) {
				lt = locations.get(i);
				if(lt.getID().equals(locID)) {
					break;
				}
			}
		}
		if(lt==null) {
			return false; //could not find this location
		}
		
		List<GroupType> poolList = this.cloudtype.getGroup();
		if(requireValidation) {
			// check if poolID is exist
			boolean found = false;
			for(int i=0; i<poolList.size(); i++) {
				GroupType t = poolList.get(i);
				if( t.getID().equals(poolID)){
					found = true;
					break;
				}
			}
			if(!found) {
				return false;
			}
		}
		
		PrioritizedGroupLists pgl = lt.getPrioritizedGroupLists();
		if(pgl == null) {
			lt.setPrioritizedGroupLists( pgl=objFactory.createLocationTypePrioritizedGroupLists() );
		}
		List<GroupList> glList = pgl.getGroupList();
		
		GroupList gl = objFactory.createLocationTypePrioritizedGroupListsGroupList();
		gl.getGroupID().add(poolID);
		
		if(index >= glList.size()) {
			glList.add(gl);
		}
		else {
			glList.add(index, gl);
		}
		return true;
	}

    //marshalling
	public String toXml() throws JAXBException {
		String xmlStr = "";
		try{
            JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.cloudconfig" );
	        Marshaller m = jc.createMarshaller();
	        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            JAXBElement<NetworkConfigType> cloud = objFactory.createNetworkConfig(cloudtype);
	        StringWriter sw = new StringWriter();
	        m.marshal(cloud, sw);
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
        JAXBElement<NetworkConfigType> jEl = (JAXBElement<NetworkConfigType>) this.cloudMarshaller.unmarshal(new StreamSource(sr));
        cloudtype = jEl.getValue();
    }

	/*
	public String toXml() throws JAXBException {
		String xmlStr = "";
		try{
            JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.cloudconfig" );
	        Marshaller m = jc.createMarshaller();
	        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            JAXBElement<NetworkConfigType> cloud = objFactory.createNetworkConfig(cloudtype);
	        StringWriter sw = new StringWriter();
	        m.marshal( cloud, sw);
	        xmlStr = sw.toString();
	    }
	    catch(JAXBException anyEx){
	    	throw anyEx;
	    }
	    return xmlStr;
	}
	public NetworkConfigType read(String filename) throws JAXBException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance( "com.vidyo.parser.xml.cloudconfig" );
        Unmarshaller u = jc.createUnmarshaller(); 
        JAXBElement<NetworkConfigType> jEl = (JAXBElement<NetworkConfigType>)u.unmarshal(new FileInputStream( filename ));
        NetworkConfigType ccType = jEl.getValue();
        return ccType;
	}
	*/
	
}


