//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.30 at 11:22:11 AM IST 
//


package com.vidyo.parser.xsd.vmconfig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VidyoPortalConnectAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VidyoPortalConnectAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VidyoPortalConnectAddress" type="{http://www.vidyo.com/VMConfigParser.xsd}vpURI" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VidyoPortalConnectAddressType", propOrder = {
    "vidyoPortalConnectAddress"
})
public class VidyoPortalConnectAddressType {

    @XmlElement(name = "VidyoPortalConnectAddress")
    protected List<VpURI> vidyoPortalConnectAddress;

    /**
     * Gets the value of the vidyoPortalConnectAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vidyoPortalConnectAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVidyoPortalConnectAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VpURI }
     * 
     * 
     */
    public List<VpURI> getVidyoPortalConnectAddress() {
        if (vidyoPortalConnectAddress == null) {
            vidyoPortalConnectAddress = new ArrayList<VpURI>();
        }
        return this.vidyoPortalConnectAddress;
    }

}
