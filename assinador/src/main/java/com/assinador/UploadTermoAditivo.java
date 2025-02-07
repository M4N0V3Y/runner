
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
 *         &lt;element name="CODASSINAEMPRESA" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Senha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CNPJCPFRESPONSAVEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CNPJCPFCEDENTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="f" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
        "codassinaempresa",
        "senha",
        "cnpjcpfresponsavel",
        "cnpjcpfcedente",
        "sFileName",
        "f"
})
@XmlRootElement(name = "UploadTermoAditivo")
public class UploadTermoAditivo {

    @XmlElement(name = "CODASSINAEMPRESA")
    protected Integer codassinaempresa;
    @XmlElementRef(name = "Senha", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> senha;
    @XmlElementRef(name = "CNPJCPFRESPONSAVEL", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cnpjcpfresponsavel;
    @XmlElementRef(name = "CNPJCPFCEDENTE", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cnpjcpfcedente;
    @XmlElementRef(name = "sFileName", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sFileName;
    @XmlElementRef(name = "f", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> f;

    /**
     * Gets the value of the codassinaempresa property.
     * 
     * @return
     *         possible object is
     *         {@link Integer }
     * 
     */
    public Integer getCODASSINAEMPRESA() {
        return codassinaempresa;
    }

    /**
     * Sets the value of the codassinaempresa property.
     * 
     * @param value
     *              allowed object is
     *              {@link Integer }
     * 
     */
    public void setCODASSINAEMPRESA(Integer value) {
        this.codassinaempresa = value;
    }

    /**
     * Gets the value of the senha property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getSenha() {
        return senha;
    }

    /**
     * Sets the value of the senha property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setSenha(JAXBElement<String> value) {
        this.senha = value;
    }

    /**
     * Gets the value of the cnpjcpfresponsavel property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCNPJCPFRESPONSAVEL() {
        return cnpjcpfresponsavel;
    }

    /**
     * Sets the value of the cnpjcpfresponsavel property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCNPJCPFRESPONSAVEL(JAXBElement<String> value) {
        this.cnpjcpfresponsavel = value;
    }

    /**
     * Gets the value of the cnpjcpfcedente property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getCNPJCPFCEDENTE() {
        return cnpjcpfcedente;
    }

    /**
     * Sets the value of the cnpjcpfcedente property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setCNPJCPFCEDENTE(JAXBElement<String> value) {
        this.cnpjcpfcedente = value;
    }

    /**
     * Gets the value of the sFileName property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getSFileName() {
        return sFileName;
    }

    /**
     * Sets the value of the sFileName property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setSFileName(JAXBElement<String> value) {
        this.sFileName = value;
    }

    /**
     * Gets the value of the f property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public JAXBElement<byte[]> getF() {
        return f;
    }

    /**
     * Sets the value of the f property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     */
    public void setF(JAXBElement<byte[]> value) {
        this.f = value;
    }

}
