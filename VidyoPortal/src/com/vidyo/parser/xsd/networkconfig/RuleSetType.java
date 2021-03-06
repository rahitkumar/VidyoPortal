//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.30 at 11:24:41 AM IST 
//


package com.vidyo.parser.xsd.networkconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RuleSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RuleSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="And" type="{http://www.vidyo.com/NetConfigParser.xsd}AndType"/>
 *           &lt;element name="Or" type="{http://www.vidyo.com/NetConfigParser.xsd}OrType"/>
 *           &lt;element name="Not" type="{http://www.vidyo.com/NetConfigParser.xsd}NotType"/>
 *           &lt;element name="BasicRule" type="{http://www.vidyo.com/NetConfigParser.xsd}BasicRuleType"/>
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
@XmlType(name = "RuleSetType", propOrder = {
    "and",
    "or",
    "not",
    "basicRule"
})
public class RuleSetType {

    @XmlElement(name = "And")
    protected AndType and;
    @XmlElement(name = "Or")
    protected OrType or;
    @XmlElement(name = "Not")
    protected NotType not;
    @XmlElement(name = "BasicRule")
    protected BasicRuleType basicRule;

    /**
     * Gets the value of the and property.
     * 
     * @return
     *     possible object is
     *     {@link AndType }
     *     
     */
    public AndType getAnd() {
        return and;
    }

    /**
     * Sets the value of the and property.
     * 
     * @param value
     *     allowed object is
     *     {@link AndType }
     *     
     */
    public void setAnd(AndType value) {
        this.and = value;
    }

    /**
     * Gets the value of the or property.
     * 
     * @return
     *     possible object is
     *     {@link OrType }
     *     
     */
    public OrType getOr() {
        return or;
    }

    /**
     * Sets the value of the or property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrType }
     *     
     */
    public void setOr(OrType value) {
        this.or = value;
    }

    /**
     * Gets the value of the not property.
     * 
     * @return
     *     possible object is
     *     {@link NotType }
     *     
     */
    public NotType getNot() {
        return not;
    }

    /**
     * Sets the value of the not property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotType }
     *     
     */
    public void setNot(NotType value) {
        this.not = value;
    }

    /**
     * Gets the value of the basicRule property.
     * 
     * @return
     *     possible object is
     *     {@link BasicRuleType }
     *     
     */
    public BasicRuleType getBasicRule() {
        return basicRule;
    }

    /**
     * Sets the value of the basicRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicRuleType }
     *     
     */
    public void setBasicRule(BasicRuleType value) {
        this.basicRule = value;
    }

}
