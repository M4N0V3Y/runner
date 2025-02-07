
package com.assinador;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for MarshalByRefObject complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="MarshalByRefObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="__identity" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MarshalByRefObject", namespace = "http://schemas.datacontract.org/2004/07/System", propOrder = {
        "identity"
})
@XmlSeeAlso({
        Stream.class
})
public class MarshalByRefObject {

    @XmlElement(name = "__identity", required = true, nillable = true)
    protected Object identity;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *         possible object is
     *         {@link Object }
     * 
     */
    public Object getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *              allowed object is
     *              {@link Object }
     * 
     */
    public void setIdentity(Object value) {
        this.identity = value;
    }

}
