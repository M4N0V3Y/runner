
package com.assinador;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="CODASSINADOCUMENTORESPONSAVEL" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DOCUMENTOBIN" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="CHAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "codassinadocumentoresponsavel",
        "documentobin",
        "chave"
})
@XmlRootElement(name = "SetDocumento")
public class SetDocumento {

    @XmlElement(name = "CODASSINADOCUMENTORESPONSAVEL")
    protected Integer codassinadocumentoresponsavel;
    @XmlElementRef(name = "DOCUMENTOBIN", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> documentobin;
    @XmlElementRef(name = "CHAVE", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> chave;

    /**
     * Gets the value of the codassinadocumentoresponsavel property.
     * 
     * @return
     *         possible object is
     *         {@link Integer }
     * 
     */
    public Integer getCODASSINADOCUMENTORESPONSAVEL() {
        return codassinadocumentoresponsavel;
    }

    /**
     * Sets the value of the codassinadocumentoresponsavel property.
     * 
     * @param value
     *              allowed object is
     *              {@link Integer }
     * 
     */
    public void setCODASSINADOCUMENTORESPONSAVEL(Integer value) {
        this.codassinadocumentoresponsavel = value;
    }

    /**
     * Gets the value of the documentobin property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public JAXBElement<byte[]> getDOCUMENTOBIN() {
        return documentobin;
    }

    /**
     * Sets the value of the documentobin property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public void setDOCUMENTOBIN(JAXBElement<byte[]> value) {
        this.documentobin = value;
    }

    /**
     * Gets the value of the chave property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCHAVE() {
        return chave;
    }

    /**
     * Sets the value of the chave property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCHAVE(JAXBElement<String> value) {
        this.chave = value;
    }

}
