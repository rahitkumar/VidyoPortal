//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.13 at 12:07:58 PM EDT 
//


package com.vidyo.parser.xml.vmconfig;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.vidyo.parser.xml.vmconfig package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Config_QNAME = new QName("", "Config");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.vidyo.parser.xml.vmconfig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VMConfigType.VidyoPortalAddressList }
     * 
     */
    public VMConfigType.VidyoPortalAddressList createVMConfigTypeVidyoPortalAddressList() {
        return new VMConfigType.VidyoPortalAddressList();
    }

    /**
     * Create an instance of {@link VMConfigType.DatabaseConfig }
     * 
     */
    public VMConfigType.DatabaseConfig createVMConfigTypeDatabaseConfig() {
        return new VMConfigType.DatabaseConfig();
    }

    /**
     * Create an instance of {@link VMConfigType }
     * 
     */
    public VMConfigType createVMConfigType() {
        return new VMConfigType();
    }

    /**
     * Create an instance of {@link VMConfigType.RMCPAddressList }
     * 
     */
    public VMConfigType.RMCPAddressList createVMConfigTypeRMCPAddressList() {
        return new VMConfigType.RMCPAddressList();
    }

    /**
     * Create an instance of {@link VMConfigType.SOAPAddressList }
     * 
     */
    public VMConfigType.SOAPAddressList createVMConfigTypeSOAPAddressList() {
        return new VMConfigType.SOAPAddressList();
    }

    /**
     * Create an instance of {@link VpURI }
     * 
     */
    public VpURI createVpURI() {
        return new VpURI();
    }

    /**
     * Create an instance of {@link VMConfigType.EMCPAddressList }
     * 
     */
    public VMConfigType.EMCPAddressList createVMConfigTypeEMCPAddressList() {
        return new VMConfigType.EMCPAddressList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VMConfigType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Config")
    public JAXBElement<VMConfigType> createConfig(VMConfigType value) {
        return new JAXBElement<VMConfigType>(_Config_QNAME, VMConfigType.class, null, value);
    }

}
