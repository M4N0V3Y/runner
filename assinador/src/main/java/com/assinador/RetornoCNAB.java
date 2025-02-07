
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
 *         &lt;element name="LOTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "lote"
})
@XmlRootElement(name = "retornoCNAB")
public class RetornoCNAB {

    @XmlElement(name = "CODASSINAEMPRESA")
    protected Integer codassinaempresa;
    @XmlElementRef(name = "Senha", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> senha;
    @XmlElementRef(name = "LOTE", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lote;

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
     * Gets the value of the lote property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getLOTE() {
        return lote;
    }

    /**
     * Sets the value of the lote property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setLOTE(JAXBElement<String> value) {
        this.lote = value;
    }

}
