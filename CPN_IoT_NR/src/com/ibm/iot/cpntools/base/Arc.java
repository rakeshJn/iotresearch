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
 *         &lt;element ref="{cpntools.dtd}arrowattr"/>
 *         &lt;element ref="{cpntools.dtd}transend"/>
 *         &lt;element ref="{cpntools.dtd}placeend"/>
 *         &lt;element ref="{cpntools.dtd}bendpoint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{cpntools.dtd}annot" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="order" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "arrowattr",
    "transend",
    "placeend",
    "bendpoint",
    "annot"
})
@XmlRootElement(name = "arc")
public class Arc {

    @XmlElement(required = true)
    protected Posattr posattr;
    @XmlElement(required = true)
    protected Fillattr fillattr;
    @XmlElement(required = true)
    protected Lineattr lineattr;
    @XmlElement(required = true)
    protected Textattr textattr;
    @XmlElement(required = true)
    protected Arrowattr arrowattr;
    @XmlElement(required = true)
    protected Transend transend;
    @XmlElement(required = true)
    protected Placeend placeend;
    protected List<Bendpoint> bendpoint;
    protected Annot annot;
    @XmlAttribute(name = "id")
    protected java.lang.String id;
    @XmlAttribute(name = "orientation")
    protected java.lang.String orientation;
    @XmlAttribute(name = "order")
    protected java.lang.String order;

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
     * Gets the value of the arrowattr property.
     * 
     * @return
     *     possible object is
     *     {@link Arrowattr }
     *     
     */
    public Arrowattr getArrowattr() {
        return arrowattr;
    }

    /**
     * Sets the value of the arrowattr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Arrowattr }
     *     
     */
    public void setArrowattr(Arrowattr value) {
        this.arrowattr = value;
    }

    /**
     * Gets the value of the transend property.
     * 
     * @return
     *     possible object is
     *     {@link Transend }
     *     
     */
    public Transend getTransend() {
        return transend;
    }

    /**
     * Sets the value of the transend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transend }
     *     
     */
    public void setTransend(Transend value) {
        this.transend = value;
    }

    /**
     * Gets the value of the placeend property.
     * 
     * @return
     *     possible object is
     *     {@link Placeend }
     *     
     */
    public Placeend getPlaceend() {
        return placeend;
    }

    /**
     * Sets the value of the placeend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Placeend }
     *     
     */
    public void setPlaceend(Placeend value) {
        this.placeend = value;
    }

    /**
     * Gets the value of the bendpoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bendpoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBendpoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bendpoint }
     * 
     * 
     */
    public List<Bendpoint> getBendpoint() {
        if (bendpoint == null) {
            bendpoint = new ArrayList<Bendpoint>();
        }
        return this.bendpoint;
    }

    /**
     * Gets the value of the annot property.
     * 
     * @return
     *     possible object is
     *     {@link Annot }
     *     
     */
    public Annot getAnnot() {
        return annot;
    }

    /**
     * Sets the value of the annot property.
     * 
     * @param value
     *     allowed object is
     *     {@link Annot }
     *     
     */
    public void setAnnot(Annot value) {
        this.annot = value;
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

    /**
     * Gets the value of the orientation property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setOrientation(java.lang.String value) {
        this.orientation = value;
    }

    /**
     * Gets the value of the order property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setOrder(java.lang.String value) {
        this.order = value;
    }

}
