package com.vidyo.bo;

import java.io.Serializable;

public class Network  implements Serializable {
    String ipAddress;
    String subnetMask;
    String gateway;
    String dns1;
    String dns2;
    String MACAddress;
    String fqdn;
    String fqdn2;
    String ip1Port443InUse;

    String ipAddress2;
    String subnetMask2;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public String getMACAddress() {
        return MACAddress;
    }

    public void setMACAddress(String MACAddress) {
        this.MACAddress = MACAddress;
    }

    public String getIpAddress2() {
        return ipAddress2;
    }

    public void setIpAddress2(String ipAddress) {
        this.ipAddress2 = ipAddress;
    }

    public String getSubnetMask2() {
        return subnetMask2;
    }

    public void setSubnetMask2(String subnetMask) {
        this.subnetMask2 = subnetMask;
    }

    public String getFqdn() {
        return this.fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getFqdn2() {
        return this.fqdn2;
    }

    public void setFqdn2(String fqdn2) {
        this.fqdn2 = fqdn2;
    }

    public String getIp1Port443InUse() {
        return this.ip1Port443InUse;
    }

    public void setIp1Port443InUse(String inUse) {
        this.ip1Port443InUse = inUse;
    }

}
