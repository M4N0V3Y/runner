
package com.assinador;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="retornoNFEINFResult" type="{http://schemas.datacontract.org/2004/07/System.IO}MemoryStream" minOccurs="0"/>
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
        "retornoNFEINFResult"
})
@XmlRootElement(name = "retornoNFEINFResponse")
public class RetornoNFEINFResponse {

    @XmlElementRef(name = "retornoNFEINFResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<MemoryStream> retornoNFEINFResult;

    /**
     * Gets the value of the retornoNFEINFResult property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link MemoryStream }{@code >}
     * 
     */
    public JAXBElement<MemoryStream> getRetornoNFEINFResult() {
        return retornoNFEINFResult;
    }

    /**
     * Sets the value of the retornoNFEINFResult property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link MemoryStream }{@code >}
     * 
     */
    public void setRetornoNFEINFResult(JAXBElement<MemoryStream> value) {
        this.retornoNFEINFResult = value;
    }

}
