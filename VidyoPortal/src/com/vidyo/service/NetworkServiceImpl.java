package com.vidyo.service;

import com.vidyo.bo.Network;

import java.util.*;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServiceImpl implements INetworkService {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(NetworkServiceImpl.class.getName());

    public Network getNetworkSettings() {
        Network rc = new Network();

        try {
            logger.debug("Run GetNetwork.sh");

            Process p = Runtime.getRuntime().exec("/opt/vidyo/bin/GetNetwork.sh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ( (line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    Stack<String> stack = new Stack<String>();
                    StringTokenizer tokenizer = new StringTokenizer(line, "=");
                    while (tokenizer.hasMoreTokens()) {
                        stack.push((String)tokenizer.nextElement());
                    }
                    //while(!stack.empty()) {  // not empty is not strong enough to prevent exception thrown because of following 2 stack.pop() calls
                    while(stack.size() >= 2) {
                        String value = stack.pop();
                        String key = stack.pop();
                        if (key.equalsIgnoreCase("Ip Address")) rc.setIpAddress(value);
                        if (key.equalsIgnoreCase("Ip Address2")) rc.setIpAddress2(value);
                        if (key.equalsIgnoreCase("Gateway")) rc.setGateway(value);
                        if (key.equalsIgnoreCase("Mac Address")) rc.setMACAddress(value);
                        if (key.equalsIgnoreCase("Netmask")) rc.setSubnetMask(value);
                        if (key.equalsIgnoreCase("Netmask2")) rc.setSubnetMask2(value);
                        if (key.equalsIgnoreCase("Nameserver")) {
                            if (rc.getDns1() != null) {
                                rc.setDns2(value);
                            } else {
                                rc.setDns1(value);
                            }
                        }
                        if (key.equalsIgnoreCase("FQDN")) rc.setFqdn(value);
                        if (key.equalsIgnoreCase("FQDN2")) rc.setFqdn2(value);
                        if (key.equalsIgnoreCase("IP1_443_IN_USE")) rc.setIp1Port443InUse(value);
                    }
                    logger.debug(line);
                }
            }

        } catch(Exception e) {
            logger.error(e.getMessage());
        }

        return rc;
    }

    public HashMap<String, String> getSystemNetworkData() {
        HashMap<String, String> map = new HashMap<String, String>();
        Network nt = this.getNetworkSettings();
        map.put("macAddress",  nt.getMACAddress());
        map.put("ipAddress",   nt.getIpAddress());
        map.put("ipAddress2",  nt.getIpAddress2());
        map.put("subnetMask",  nt.getSubnetMask());
        map.put("subnetMask2", nt.getSubnetMask2());
        map.put("gateway",     nt.getGateway());
        map.put("fqdn",        nt.getFqdn());
        map.put("fqdn2",       nt.getFqdn2());
        map.put("ip1Port443InUse", nt.getIp1Port443InUse());
        return map;
    }

}
