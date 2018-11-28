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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{cpntools.dtd}block"/>
 *         &lt;element ref="{cpntools.dtd}color"/>
 *         &lt;element ref="{cpntools.dtd}var"/>
 *         &lt;element ref="{cpntools.dtd}ml"/>
 *         &lt;element ref="{cpntools.dtd}globref"/>
 *         &lt;element ref="{cpntools.dtd}use"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "blockOrColorOrVar"
})
@XmlRootElement(name = "globbox")
public class Globbox {

    @XmlElements({
        @XmlElement(name = "block", type = Block.class),
        @XmlElement(name = "color", type = Color.class),
        @XmlElement(name = "var", type = Var.class),
        @XmlElement(name = "ml", type = Ml.class),
        @XmlElement(name = "globref", type = Globref.class),
        @XmlElement(name = "use", type = Use.class)
    })
    protected List<Object> blockOrColorOrVar;

    /**
     * Gets the value of the blockOrColorOrVar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the blockOrColorOrVar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBlockOrColorOrVar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Block }
     * {@link Color }
     * {@link Var }
     * {@link Ml }
     * {@link Globref }
     * {@link Use }
     * 
     * 
     */
    public List<Object> getBlockOrColorOrVar() {
        if (blockOrColorOrVar == null) {
            blockOrColorOrVar = new ArrayList<Object>();
        }
        return this.blockOrColorOrVar;
    }

}