//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.06 at 08:59:15 AM PDT 
//


package com.ibm.iot.cpntools.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element ref="{cpntools.dtd}posattr"/>
 *         &lt;element ref="{cpntools.dtd}fillattr"/>
 *         &lt;element ref="{cpntools.dtd}lineattr"/>
 *         &lt;element ref="{cpntools.dtd}textattr"/>
 *         &lt;element ref="{cpntools.dtd}text"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "posattr",
    "fillattr",
    "lineattr",
    "textattr",
    "text"
})
@XmlRootElement(name = "code-key")
public class CodeKey {

    @XmlElement(required = true)
    protected Posattr posattr;
    @XmlElement(required = true)
    protected Fillattr fillattr;
    @XmlElement(required = true)
    protected Lineattr lineattr;
    @XmlElement(required = true)
    protected Textattr textattr;
    @XmlElement(required = true)
    protected Text text;
    @XmlAttribute(name = "id")
    protected java.lang.String id;

    /**
     * Gets the value of the posattr property.
     * 
     * @return
     *     possible object is
     *     {@link Posattr }
     *     
     */
    public Posattr getPosattr() {
        return posattr;
    }

    /**
     * Sets the value of the posattr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Posattr }
     *     
     */
    public void setPosattr(Posattr value) {
        this.posattr = value;
    }

    /**
     * Gets the value of the fillattr property.
     * 
     * @return
     *     possible object is
     *     {@link Fillattr }
     *     
     */
    public Fillattr getFillattr() {
        return fillattr;
    }

    /**
     * Sets the value of the fillattr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fillattr }
     *     
     */
    public void setFillattr(Fillattr value) {
        this.fillattr = value;
    }

    /**
     * Gets the value of the lineattr property.
     * 
     * @return
     *     possible object is
     *     {@link Lineattr }
     *     
     */
    public Lineattr getLineattr() {
        return lineattr;
    }

    /**
     * Sets the value of the lineattr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lineattr }
     *     
     */
    public void setLineattr(Lineattr value) {
        this.lineattr = value;
    }

    /**
     * Gets the value of the textattr property.
     * 
     * @return
     *     possible object is
     *     {@link Textattr }
     *     
     */
    public Textattr getTextattr() {
        return textattr;
    }

    /**
     * Sets the value of the textattr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Textattr }
     *     
     */
    public void setTextattr(Textattr value) {
        this.textattr = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link Text }
     *     
     */
    public Text getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link Text }
     *     
     */
    public void setText(Text value) {
        this.text = value;
    }

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

}
