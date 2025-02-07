
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
 *         &lt;element name="CODASSINARESPONSAVEL" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CHAVE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RESULTADO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "codassinaresponsavel",
        "chave",
        "resultado"
})
@XmlRootElement(name = "GetTesteVersao")
public class GetTesteVersao {

    @XmlElement(name = "CODASSINARESPONSAVEL")
    protected Integer codassinaresponsavel;
    @XmlElementRef(name = "CHAVE", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> chave;
    @XmlElementRef(name = "RESULTADO", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resultado;

    /**
     * Gets the value of the codassinaresponsavel property.
     * 
     * @return
     *         possible object is
     *         {@link Integer }
     * 
     */
    public Integer getCODASSINARESPONSAVEL() {
        return codassinaresponsavel;
    }

    /**
     * Sets the value of the codassinaresponsavel property.
     * 
     * @param value
     *              allowed object is
     *              {@link Integer }
     * 
     */
    public void setCODASSINARESPONSAVEL(Integer value) {
        this.codassinaresponsavel = value;
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

    /**
     * Gets the value of the resultado property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getRESULTADO() {
        return resultado;
    }

    /**
     * Sets the value of the resultado property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setRESULTADO(JAXBElement<String> value) {
        this.resultado = value;
    }

}
