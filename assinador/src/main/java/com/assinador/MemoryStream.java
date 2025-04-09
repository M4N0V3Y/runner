
package com.assinador;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for MemoryStream complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="MemoryStream">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.datacontract.org/2004/07/System.IO}Stream">
 *       &lt;sequence>
 *         &lt;element name="_buffer" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="_capacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_expandable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="_exposable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="_isOpen" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="_length" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_origin" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_position" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="_writable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MemoryStream", namespace = "http://schemas.datacontract.org/2004/07/System.IO", propOrder = {
        "buffer",
        "capacity",
        "expandable",
        "exposable",
        "isOpen",
        "length",
        "origin",
        "position",
        "writable"
})
// [DCR]
// classe gerada pela API do servidor (WSDL)
public class MemoryStream
        extends Stream {

    @XmlElement(name = "_buffer", required = true, nillable = true)
    protected byte[] buffer;
    @XmlElement(name = "_capacity")
    protected int capacity;
    @XmlElement(name = "_expandable")
    protected boolean expandable;
    @XmlElement(name = "_exposable")
    protected boolean exposable;
    @XmlElement(name = "_isOpen")
    protected boolean isOpen;
    @XmlElement(name = "_length")
    protected int length;
    @XmlElement(name = "_origin")
    protected int origin;
    @XmlElement(name = "_position")
    protected int position;
    @XmlElement(name = "_writable")
    protected boolean writable;

    /**
     * Gets the value of the buffer property.
     * 
     * @return
     *         possible object is
     *         byte[]
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * Sets the value of the buffer property.
     * 
     * @param value
     *              allowed object is
     *              byte[]
     */
    public void setBuffer(byte[] value) {
        this.buffer = value;
    }

    /**
     * Gets the value of the capacity property.
     * 
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     * 
     */
    public void setCapacity(int value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the expandable property.
     * 
     */
    public boolean isExpandable() {
        return expandable;
    }

    /**
     * Sets the value of the expandable property.
     * 
     */
    public void setExpandable(boolean value) {
        this.expandable = value;
    }

    /**
     * Gets the value of the exposable property.
     * 
     */
    public boolean isExposable() {
        return exposable;
    }

    /**
     * Sets the value of the exposable property.
     * 
     */
    public void setExposable(boolean value) {
        this.exposable = value;
    }

    /**
     * Gets the value of the isOpen property.
     * 
     */
    public boolean isIsOpen() {
        return isOpen;
    }

    /**
     * Sets the value of the isOpen property.
     * 
     */
    public void setIsOpen(boolean value) {
        this.isOpen = value;
    }

    /**
     * Gets the value of the length property.
     * 
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     */
    public void setLength(int value) {
        this.length = value;
    }

    /**
     * Gets the value of the origin property.
     * 
     */
    public int getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     */
    public void setOrigin(int value) {
        this.origin = value;
    }

    /**
     * Gets the value of the position property.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Gets the value of the writable property.
     * 
     */
    public boolean isWritable() {
        return writable;
    }

    /**
     * Sets the value of the writable property.
     * 
     */
    public void setWritable(boolean value) {
        this.writable = value;
    }

}
