//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.18 at 02:20:00 PM EDT 
//


package com.vidyo.parser.xml.cloudconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BasicRuleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BasicRuleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="EqualTo" type="{}EqualToType"/>
 *           &lt;element name="GreaterThan" type="{}GreaterThanType"/>
 *           &lt;element name="LessThan" type="{}LessThanType"/>
 *           &lt;element name="GreaterThanOrEqualTo" type="{}GreaterThanOrEqualToType"/>
 *           &lt;element name="LessThanOrEqualTo" type="{}LessThanOrEqualToType"/>
 *           &lt;element name="ListMember" type="{}ListMemberType"/>
 *           &lt;element name="IPAddrRange" type="{}IPV4AddressRangeType"/>
 *           &lt;element name="IPSubnet" type="{}IPV4SubnetType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasicRuleType", propOrder = {
    "equalTo",
    "greaterThan",
    "lessThan",
    "greaterThanOrEqualTo",
    "lessThanOrEqualTo",
    "listMember",
    "ipAddrRange",
    "ipSubnet"
})
public class BasicRuleType {

    @XmlElement(name = "EqualTo")
    protected EqualToType equalTo;
    @XmlElement(name = "GreaterThan")
    protected GreaterThanType greaterThan;
    @XmlElement(name = "LessThan")
    protected LessThanType lessThan;
    @XmlElement(name = "GreaterThanOrEqualTo")
    protected GreaterThanOrEqualToType greaterThanOrEqualTo;
    @XmlElement(name = "LessThanOrEqualTo")
    protected LessThanOrEqualToType lessThanOrEqualTo;
    @XmlElement(name = "ListMember")
    protected ListMemberType listMember;
    @XmlElement(name = "IPAddrRange")
    protected IPV4AddressRangeType ipAddrRange;
    @XmlElement(name = "IPSubnet")
    protected IPV4SubnetType ipSubnet;

    /**
     * Gets the value of the equalTo property.
     * 
     * @return
     *     possible object is
     *     {@link EqualToType }
     *     
     */
    public EqualToType getEqualTo() {
        return equalTo;
    }

    /**
     * Sets the value of the equalTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EqualToType }
     *     
     */
    public void setEqualTo(EqualToType value) {
        this.equalTo = value;
    }

    /**
     * Gets the value of the greaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link GreaterThanType }
     *     
     */
    public GreaterThanType getGreaterThan() {
        return greaterThan;
    }

    /**
     * Sets the value of the greaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link GreaterThanType }
     *     
     */
    public void setGreaterThan(GreaterThanType value) {
        this.greaterThan = value;
    }

    /**
     * Gets the value of the lessThan property.
     * 
     * @return
     *     possible object is
     *     {@link LessThanType }
     *     
     */
    public LessThanType getLessThan() {
        return lessThan;
    }

    /**
     * Sets the value of the lessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link LessThanType }
     *     
     */
    public void setLessThan(LessThanType value) {
        this.lessThan = value;
    }

    /**
     * Gets the value of the greaterThanOrEqualTo property.
     * 
     * @return
     *     possible object is
     *     {@link GreaterThanOrEqualToType }
     *     
     */
    public GreaterThanOrEqualToType getGreaterThanOrEqualTo() {
        return greaterThanOrEqualTo;
    }

    /**
     * Sets the value of the greaterThanOrEqualTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GreaterThanOrEqualToType }
     *     
     */
    public void setGreaterThanOrEqualTo(GreaterThanOrEqualToType value) {
        this.greaterThanOrEqualTo = value;
    }

    /**
     * Gets the value of the lessThanOrEqualTo property.
     * 
     * @return
     *     possible object is
     *     {@link LessThanOrEqualToType }
     *     
     */
    public LessThanOrEqualToType getLessThanOrEqualTo() {
        return lessThanOrEqualTo;
    }

    /**
     * Sets the value of the lessThanOrEqualTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LessThanOrEqualToType }
     *     
     */
    public void setLessThanOrEqualTo(LessThanOrEqualToType value) {
        this.lessThanOrEqualTo = value;
    }

    /**
     * Gets the value of the listMember property.
     * 
     * @return
     *     possible object is
     *     {@link ListMemberType }
     *     
     */
    public ListMemberType getListMember() {
        return listMember;
    }

    /**
     * Sets the value of the listMember property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListMemberType }
     *     
     */
    public void setListMember(ListMemberType value) {
        this.listMember = value;
    }

    /**
     * Gets the value of the ipAddrRange property.
     * 
     * @return
     *     possible object is
     *     {@link IPV4AddressRangeType }
     *     
     */
    public IPV4AddressRangeType getIPAddrRange() {
        return ipAddrRange;
    }

    /**
     * Sets the value of the ipAddrRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPV4AddressRangeType }
     *     
     */
    public void setIPAddrRange(IPV4AddressRangeType value) {
        this.ipAddrRange = value;
    }

    /**
     * Gets the value of the ipSubnet property.
     * 
     * @return
     *     possible object is
     *     {@link IPV4SubnetType }
     *     
     */
    public IPV4SubnetType getIPSubnet() {
        return ipSubnet;
    }

    /**
     * Sets the value of the ipSubnet property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPV4SubnetType }
     *     
     */
    public void setIPSubnet(IPV4SubnetType value) {
        this.ipSubnet = value;
    }

}
