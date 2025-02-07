
package com.assinador;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;

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
 *         &lt;element name="requisicaoCertificado" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any processContents='lax' minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
        "requisicaoCertificado"
})
@XmlRootElement(name = "UploadContratoNovo")
public class UploadContratoNovo {

    @XmlElementRef(name = "requisicaoCertificado", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<UploadContratoNovo.RequisicaoCertificado> requisicaoCertificado;

    /**
     * Gets the value of the requisicaoCertificado property.
     * 
     * @return
     *         possible object is
     *         {@link JAXBElement
     *         }{@code <}{@link UploadContratoNovo.RequisicaoCertificado }{@code >}
     * 
     */
    public JAXBElement<UploadContratoNovo.RequisicaoCertificado> getRequisicaoCertificado() {
        return requisicaoCertificado;
    }

    /**
     * Sets the value of the requisicaoCertificado property.
     * 
     * @param value
     *              allowed object is
     *              {@link JAXBElement
     *              }{@code <}{@link UploadContratoNovo.RequisicaoCertificado }{@code >}
     * 
     */
    public void setRequisicaoCertificado(JAXBElement<UploadContratoNovo.RequisicaoCertificado> value) {
        this.requisicaoCertificado = value;
    }

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
     *         &lt;any processContents='lax' minOccurs="0"/>
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
            "any"
    })
    public static class RequisicaoCertificado {

        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Gets the value of the any property.
         * 
         * @return
         *         possible object is
         *         {@link Element }
         *         {@link Object }
         * 
         */
        public Object getAny() {
            return any;
        }

        /**
         * Sets the value of the any property.
         * 
         * @param value
         *              allowed object is
         *              {@link Element }
         *              {@link Object }
         * 
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}
