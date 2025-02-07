
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
 *         &lt;element name="GetDocumentoResult" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
        "getDocumentoResult"
})
@XmlRootElement(name = "GetDocumentoResponse")
public class GetDocumentoResponse {

    @XmlElementRef(name = "GetDocumentoResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> getDocumentoResult;

    /**
     * Gets the value of the getDocumentoResult property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public JAXBElement<byte[]> getGetDocumentoResult() {
        return getDocumentoResult;
    }

    /**
     * Sets the value of the getDocumentoResult property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public void setGetDocumentoResult(JAXBElement<byte[]> value) {
        this.getDocumentoResult = value;
    }

}
