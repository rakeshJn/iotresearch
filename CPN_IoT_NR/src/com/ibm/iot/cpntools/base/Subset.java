//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.06 at 08:59:15 AM PDT 
//


package com.ibm.iot.cpntools.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{cpntools.dtd}id" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{cpntools.dtd}with"/>
 *           &lt;element ref="{cpntools.dtd}by"/>
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
@XmlType(name = "", propOrder = {
    "id",
    "with",
    "by"
})
@XmlRootElement(name = "subset")
public class Subset {

    protected java.lang.String id;
    protected With with;
    protected By by;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setId(java.lang.String value) {
        this.id = value;
    }

    /**
     * Gets the value of the with property.
     * 
     * @return
     *     possible object is
     *     {@link With }
     *     
     */
    public With getWith() {
        return with;
    }

    /**
     * Sets the value of the with property.
     * 
     * @param value
     *     allowed object is
     *     {@link With }
     *     
     */
    public void setWith(With value) {
        this.with = value;
    }

    /**
     * Gets the value of the by property.
     * 
     * @return
     *     possible object is
     *     {@link By }
     *     
     */
    public By getBy() {
        return by;
    }

    /**
     * Sets the value of the by property.
     * 
     * @param value
     *     allowed object is
     *     {@link By }
     *     
     */
    public void setBy(By value) {
        this.by = value;
    }

}
