//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.06 at 08:59:15 AM PDT 
//


package com.ibm.iot.cpntools.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *         &lt;element ref="{cpntools.dtd}ellipse"/>
 *         &lt;element ref="{cpntools.dtd}token" maxOccurs="unbounded"/>
 *         &lt;element ref="{cpntools.dtd}marking" maxOccurs="unbounded"/>
 *         &lt;element ref="{cpntools.dtd}fusioninfo" maxOccurs="unbounded"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{cpntools.dtd}port"/>
 *           &lt;element ref="{cpntools.dtd}type"/>
 *           &lt;element ref="{cpntools.dtd}initmark"/>
 *         &lt;/choice>
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
    "text",
    "ellipse",
    "token",
    "marking",
    "fusioninfo",
    "portOrTypeOrInitmark"
})
@XmlRootElement(name = "place")
public class Place {

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
    @XmlElement(required = true)
    protected Ellipse ellipse;
    @XmlElement(required = true)
    protected List<Token> token;
    @XmlElement(required = true)
    protected List<Marking> marking;
    @XmlElement(required = true)
    protected List<Fusioninfo> fusioninfo;
    @XmlElements({
        @XmlElement(name = "port", type = Port.class),
        @XmlElement(name = "type", type = Type.class),
        @XmlElement(name = "initmark", type = Initmark.class)
    })
    protected List<Object> portOrTypeOrInitmark;
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
     * Gets the value of the ellipse property.
     * 
     * @return
     *     possible object is
     *     {@link Ellipse }
     *     
     */
    public Ellipse getEllipse() {
        return ellipse;
    }

    /**
     * Sets the value of the ellipse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ellipse }
     *     
     */
    public void setEllipse(Ellipse value) {
        this.ellipse = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the token property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToken().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Token }
     * 
     * 
     */
    public List<Token> getToken() {
        if (token == null) {
            token = new ArrayList<Token>();
        }
        return this.token;
    }

    /**
     * Gets the value of the marking property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the marking property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMarking().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Marking }
     * 
     * 
     */
    public List<Marking> getMarking() {
        if (marking == null) {
            marking = new ArrayList<Marking>();
        }
        return this.marking;
    }

    /**
     * Gets the value of the fusioninfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fusioninfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFusioninfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fusioninfo }
     * 
     * 
     */
    public List<Fusioninfo> getFusioninfo() {
        if (fusioninfo == null) {
            fusioninfo = new ArrayList<Fusioninfo>();
        }
        return this.fusioninfo;
    }

    /**
     * Gets the value of the portOrTypeOrInitmark property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portOrTypeOrInitmark property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortOrTypeOrInitmark().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Port }
     * {@link Type }
     * {@link Initmark }
     * 
     * 
     */
    public List<Object> getPortOrTypeOrInitmark() {
        if (portOrTypeOrInitmark == null) {
            portOrTypeOrInitmark = new ArrayList<Object>();
        }
        return this.portOrTypeOrInitmark;
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
