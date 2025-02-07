
package com.assinador;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.assinador package.
 * <p>
 * An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups. Factory methods for each of these are
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "unsignedLong");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "unsignedInt");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "unsignedShort");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "decimal");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "boolean");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "duration");
    private final static QName _MarshalByRefObject_QNAME = new QName("http://schemas.datacontract.org/2004/07/System",
            "MarshalByRefObject");
    private final static QName _Stream_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.IO", "Stream");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "base64Binary");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "anyURI");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _MemoryStream_QNAME = new QName("http://schemas.datacontract.org/2004/07/System.IO",
            "MemoryStream");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "dateTime");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "double");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "anyType");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/",
            "string");
    private final static QName _GetTesteVersaoCHAVE_QNAME = new QName("http://tempuri.org/", "CHAVE");
    private final static QName _GetTesteVersaoRESULTADO_QNAME = new QName("http://tempuri.org/", "RESULTADO");
    private final static QName _RetornoNFEINFSenha_QNAME = new QName("http://tempuri.org/", "Senha");
    private final static QName _RetornoNFEINFCHNFE_QNAME = new QName("http://tempuri.org/", "CHNFE");
    private final static QName _GetTesteVersaoResponseGetTesteVersaoResult_QNAME = new QName("http://tempuri.org/",
            "GetTesteVersaoResult");
    private final static QName _RetornoNFEINFResponseRetornoNFEINFResult_QNAME = new QName("http://tempuri.org/",
            "retornoNFEINFResult");
    private final static QName _GetComunicacaoResponseGetComunicacaoResult_QNAME = new QName("http://tempuri.org/",
            "GetComunicacaoResult");
    private final static QName _GetDocumentoAssinarResponseGetDocumentoAssinarResult_QNAME = new QName(
            "http://tempuri.org/", "GetDocumentoAssinarResult");
    private final static QName _UploadTermoAditivoF_QNAME = new QName("http://tempuri.org/", "f");
    private final static QName _UploadTermoAditivoSFileName_QNAME = new QName("http://tempuri.org/", "sFileName");
    private final static QName _UploadTermoAditivoCNPJCPFCEDENTE_QNAME = new QName("http://tempuri.org/",
            "CNPJCPFCEDENTE");
    private final static QName _UploadTermoAditivoCNPJCPFRESPONSAVEL_QNAME = new QName("http://tempuri.org/",
            "CNPJCPFRESPONSAVEL");
    private final static QName _UploadTermoAditivoContratoResponseUploadTermoAditivoContratoResult_QNAME = new QName(
            "http://tempuri.org/", "UploadTermoAditivoContratoResult");
    private final static QName _UploadCadastroResponseUploadCadastroResult_QNAME = new QName("http://tempuri.org/",
            "UploadCadastroResult");
    private final static QName _RetornoCNABLOTE_QNAME = new QName("http://tempuri.org/", "LOTE");
    private final static QName _UploadCadastroCNPJCPF_QNAME = new QName("http://tempuri.org/", "CNPJCPF");
    private final static QName _RetornoCNABResponseRetornoCNABResult_QNAME = new QName("http://tempuri.org/",
            "retornoCNABResult");
    private final static QName _UploadNFEINFResponseUploadNFEINFResult_QNAME = new QName("http://tempuri.org/",
            "UploadNFEINFResult");
    private final static QName _SetDocumentoDOCUMENTOBIN_QNAME = new QName("http://tempuri.org/", "DOCUMENTOBIN");
    private final static QName _GetComunicacaoDESCRICAO_QNAME = new QName("http://tempuri.org/", "DESCRICAO");
    private final static QName _UploadContratoNovoResponseUploadContratoNovoResult_QNAME = new QName(
            "http://tempuri.org/", "UploadContratoNovoResult");
    private final static QName _UploadTermoAditivoResponseUploadTermoAditivoResult_QNAME = new QName(
            "http://tempuri.org/", "UploadTermoAditivoResult");
    private final static QName _UploadCNABResponseUploadCNABResult_QNAME = new QName("http://tempuri.org/",
            "UploadCNABResult");
    private final static QName _UploadContratoNovoRequisicaoCertificado_QNAME = new QName("http://tempuri.org/",
            "requisicaoCertificado");
    private final static QName _SetDocumentoResponseSetDocumentoResult_QNAME = new QName("http://tempuri.org/",
            "SetDocumentoResult");
    private final static QName _GetDocumentoResponseGetDocumentoResult_QNAME = new QName("http://tempuri.org/",
            "GetDocumentoResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema
     * derived classes for package: com.assinador
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UploadContratoNovo }
     * 
     */
    public UploadContratoNovo createUploadContratoNovo() {
        return new UploadContratoNovo();
    }

    /**
     * Create an instance of {@link GetComunicacao }
     * 
     */
    public GetComunicacao createGetComunicacao() {
        return new GetComunicacao();
    }

    /**
     * Create an instance of {@link GetDocumentoAssinar }
     * 
     */
    public GetDocumentoAssinar createGetDocumentoAssinar() {
        return new GetDocumentoAssinar();
    }

    /**
     * Create an instance of {@link UploadCadastro }
     * 
     */
    public UploadCadastro createUploadCadastro() {
        return new UploadCadastro();
    }

    /**
     * Create an instance of {@link UploadTermoAditivoResponse }
     * 
     */
    public UploadTermoAditivoResponse createUploadTermoAditivoResponse() {
        return new UploadTermoAditivoResponse();
    }

    /**
     * Create an instance of {@link UploadCadastroResponse }
     * 
     */
    public UploadCadastroResponse createUploadCadastroResponse() {
        return new UploadCadastroResponse();
    }

    /**
     * Create an instance of {@link UploadNFEINFResponse }
     * 
     */
    public UploadNFEINFResponse createUploadNFEINFResponse() {
        return new UploadNFEINFResponse();
    }

    /**
     * Create an instance of {@link GetDocumentoResponse }
     * 
     */
    public GetDocumentoResponse createGetDocumentoResponse() {
        return new GetDocumentoResponse();
    }

    /**
     * Create an instance of {@link UploadTermoAditivoContratoResponse }
     * 
     */
    public UploadTermoAditivoContratoResponse createUploadTermoAditivoContratoResponse() {
        return new UploadTermoAditivoContratoResponse();
    }

    /**
     * Create an instance of {@link RetornoCNABResponse }
     * 
     */
    public RetornoCNABResponse createRetornoCNABResponse() {
        return new RetornoCNABResponse();
    }

    /**
     * Create an instance of {@link MemoryStream }
     * 
     */
    public MemoryStream createMemoryStream() {
        return new MemoryStream();
    }

    /**
     * Create an instance of {@link RetornoNFEINF }
     * 
     */
    public RetornoNFEINF createRetornoNFEINF() {
        return new RetornoNFEINF();
    }

    /**
     * Create an instance of {@link UploadContratoNovoResponse }
     * 
     */
    public UploadContratoNovoResponse createUploadContratoNovoResponse() {
        return new UploadContratoNovoResponse();
    }

    /**
     * Create an instance of {@link GetTesteVersaoResponse }
     * 
     */
    public GetTesteVersaoResponse createGetTesteVersaoResponse() {
        return new GetTesteVersaoResponse();
    }

    /**
     * Create an instance of {@link GetComunicacaoResponse }
     * 
     */
    public GetComunicacaoResponse createGetComunicacaoResponse() {
        return new GetComunicacaoResponse();
    }

    /**
     * Create an instance of {@link GetDocumentoAssinarResponse }
     * 
     */
    public GetDocumentoAssinarResponse createGetDocumentoAssinarResponse() {
        return new GetDocumentoAssinarResponse();
    }

    /**
     * Create an instance of {@link SetDocumento }
     * 
     */
    public SetDocumento createSetDocumento() {
        return new SetDocumento();
    }

    /**
     * Create an instance of {@link UploadCNABResponse }
     * 
     */
    public UploadCNABResponse createUploadCNABResponse() {
        return new UploadCNABResponse();
    }

    /**
     * Create an instance of {@link UploadCNAB }
     * 
     */
    public UploadCNAB createUploadCNAB() {
        return new UploadCNAB();
    }

    /**
     * Create an instance of {@link UploadContratoNovo.RequisicaoCertificado }
     * 
     */
    public UploadContratoNovo.RequisicaoCertificado createUploadContratoNovoRequisicaoCertificado() {
        return new UploadContratoNovo.RequisicaoCertificado();
    }

    /**
     * Create an instance of {@link GetTesteVersao }
     * 
     */
    public GetTesteVersao createGetTesteVersao() {
        return new GetTesteVersao();
    }

    /**
     * Create an instance of {@link GetDocumento }
     * 
     */
    public GetDocumento createGetDocumento() {
        return new GetDocumento();
    }

    /**
     * Create an instance of {@link UploadTermoAditivo }
     * 
     */
    public UploadTermoAditivo createUploadTermoAditivo() {
        return new UploadTermoAditivo();
    }

    /**
     * Create an instance of {@link RetornoNFEINFResponse }
     * 
     */
    public RetornoNFEINFResponse createRetornoNFEINFResponse() {
        return new RetornoNFEINFResponse();
    }

    /**
     * Create an instance of {@link SetDocumentoResponse }
     * 
     */
    public SetDocumentoResponse createSetDocumentoResponse() {
        return new SetDocumentoResponse();
    }

    /**
     * Create an instance of {@link UploadTermoAditivoContrato }
     * 
     */
    public UploadTermoAditivoContrato createUploadTermoAditivoContrato() {
        return new UploadTermoAditivoContrato();
    }

    /**
     * Create an instance of {@link RetornoCNAB }
     * 
     */
    public RetornoCNAB createRetornoCNAB() {
        return new RetornoCNAB();
    }

    /**
     * Create an instance of {@link UploadNFEINF }
     * 
     */
    public UploadNFEINF createUploadNFEINF() {
        return new UploadNFEINF();
    }

    /**
     * Create an instance of {@link Stream }
     * 
     */
    public Stream createStream() {
        return new Stream();
    }

    /**
     * Create an instance of {@link MarshalByRefObject }
     * 
     */
    public MarshalByRefObject createMarshalByRefObject() {
        return new MarshalByRefObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarshalByRefObject
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System", name = "MarshalByRefObject")
    public JAXBElement<MarshalByRefObject> createMarshalByRefObject(MarshalByRefObject value) {
        return new JAXBElement<MarshalByRefObject>(_MarshalByRefObject_QNAME, MarshalByRefObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Stream }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.IO", name = "Stream")
    public JAXBElement<Stream> createStream(Stream value) {
        return new JAXBElement<Stream>(_Stream_QNAME, Stream.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemoryStream
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/System.IO", name = "MemoryStream")
    public JAXBElement<MemoryStream> createMemoryStream(MemoryStream value) {
        return new JAXBElement<MemoryStream>(_MemoryStream_QNAME, MemoryStream.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement
     * }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CHAVE", scope = GetTesteVersao.class)
    public JAXBElement<String> createGetTesteVersaoCHAVE(String value) {
        return new JAXBElement<String>(_GetTesteVersaoCHAVE_QNAME, String.class, GetTesteVersao.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "RESULTADO", scope = GetTesteVersao.class)
    public JAXBElement<String> createGetTesteVersaoRESULTADO(String value) {
        return new JAXBElement<String>(_GetTesteVersaoRESULTADO_QNAME, String.class, GetTesteVersao.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = RetornoNFEINF.class)
    public JAXBElement<String> createRetornoNFEINFSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, RetornoNFEINF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CHNFE", scope = RetornoNFEINF.class)
    public JAXBElement<String> createRetornoNFEINFCHNFE(String value) {
        return new JAXBElement<String>(_RetornoNFEINFCHNFE_QNAME, String.class, RetornoNFEINF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetTesteVersaoResult", scope = GetTesteVersaoResponse.class)
    public JAXBElement<String> createGetTesteVersaoResponseGetTesteVersaoResult(String value) {
        return new JAXBElement<String>(_GetTesteVersaoResponseGetTesteVersaoResult_QNAME, String.class,
                GetTesteVersaoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemoryStream
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "retornoNFEINFResult", scope = RetornoNFEINFResponse.class)
    public JAXBElement<MemoryStream> createRetornoNFEINFResponseRetornoNFEINFResult(MemoryStream value) {
        return new JAXBElement<MemoryStream>(_RetornoNFEINFResponseRetornoNFEINFResult_QNAME, MemoryStream.class,
                RetornoNFEINFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetComunicacaoResult", scope = GetComunicacaoResponse.class)
    public JAXBElement<String> createGetComunicacaoResponseGetComunicacaoResult(String value) {
        return new JAXBElement<String>(_GetComunicacaoResponseGetComunicacaoResult_QNAME, String.class,
                GetComunicacaoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetDocumentoAssinarResult", scope = GetDocumentoAssinarResponse.class)
    public JAXBElement<String> createGetDocumentoAssinarResponseGetDocumentoAssinarResult(String value) {
        return new JAXBElement<String>(_GetDocumentoAssinarResponseGetDocumentoAssinarResult_QNAME, String.class,
                GetDocumentoAssinarResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "f", scope = UploadTermoAditivo.class)
    public JAXBElement<byte[]> createUploadTermoAditivoF(byte[] value) {
        return new JAXBElement<byte[]>(_UploadTermoAditivoF_QNAME, byte[].class, UploadTermoAditivo.class,
                ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = UploadTermoAditivo.class)
    public JAXBElement<String> createUploadTermoAditivoSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, UploadTermoAditivo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "sFileName", scope = UploadTermoAditivo.class)
    public JAXBElement<String> createUploadTermoAditivoSFileName(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoSFileName_QNAME, String.class, UploadTermoAditivo.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFCEDENTE", scope = UploadTermoAditivo.class)
    public JAXBElement<String> createUploadTermoAditivoCNPJCPFCEDENTE(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFCEDENTE_QNAME, String.class, UploadTermoAditivo.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFRESPONSAVEL", scope = UploadTermoAditivo.class)
    public JAXBElement<String> createUploadTermoAditivoCNPJCPFRESPONSAVEL(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFRESPONSAVEL_QNAME, String.class,
                UploadTermoAditivo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "f", scope = UploadNFEINF.class)
    public JAXBElement<byte[]> createUploadNFEINFF(byte[] value) {
        return new JAXBElement<byte[]>(_UploadTermoAditivoF_QNAME, byte[].class, UploadNFEINF.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = UploadNFEINF.class)
    public JAXBElement<String> createUploadNFEINFSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, UploadNFEINF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "sFileName", scope = UploadNFEINF.class)
    public JAXBElement<String> createUploadNFEINFSFileName(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoSFileName_QNAME, String.class, UploadNFEINF.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFCEDENTE", scope = UploadNFEINF.class)
    public JAXBElement<String> createUploadNFEINFCNPJCPFCEDENTE(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFCEDENTE_QNAME, String.class, UploadNFEINF.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFRESPONSAVEL", scope = UploadNFEINF.class)
    public JAXBElement<String> createUploadNFEINFCNPJCPFRESPONSAVEL(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFRESPONSAVEL_QNAME, String.class, UploadNFEINF.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadTermoAditivoContratoResult", scope = UploadTermoAditivoContratoResponse.class)
    public JAXBElement<String> createUploadTermoAditivoContratoResponseUploadTermoAditivoContratoResult(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoContratoResponseUploadTermoAditivoContratoResult_QNAME,
                String.class, UploadTermoAditivoContratoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadCadastroResult", scope = UploadCadastroResponse.class)
    public JAXBElement<String> createUploadCadastroResponseUploadCadastroResult(String value) {
        return new JAXBElement<String>(_UploadCadastroResponseUploadCadastroResult_QNAME, String.class,
                UploadCadastroResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "LOTE", scope = RetornoCNAB.class)
    public JAXBElement<String> createRetornoCNABLOTE(String value) {
        return new JAXBElement<String>(_RetornoCNABLOTE_QNAME, String.class, RetornoCNAB.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = RetornoCNAB.class)
    public JAXBElement<String> createRetornoCNABSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, RetornoCNAB.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPF", scope = UploadCadastro.class)
    public JAXBElement<String> createUploadCadastroCNPJCPF(String value) {
        return new JAXBElement<String>(_UploadCadastroCNPJCPF_QNAME, String.class, UploadCadastro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "f", scope = UploadCadastro.class)
    public JAXBElement<byte[]> createUploadCadastroF(byte[] value) {
        return new JAXBElement<byte[]>(_UploadTermoAditivoF_QNAME, byte[].class, UploadCadastro.class,
                ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = UploadCadastro.class)
    public JAXBElement<String> createUploadCadastroSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, UploadCadastro.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "f", scope = UploadTermoAditivoContrato.class)
    public JAXBElement<byte[]> createUploadTermoAditivoContratoF(byte[] value) {
        return new JAXBElement<byte[]>(_UploadTermoAditivoF_QNAME, byte[].class, UploadTermoAditivoContrato.class,
                ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = UploadTermoAditivoContrato.class)
    public JAXBElement<String> createUploadTermoAditivoContratoSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, UploadTermoAditivoContrato.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "sFileName", scope = UploadTermoAditivoContrato.class)
    public JAXBElement<String> createUploadTermoAditivoContratoSFileName(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoSFileName_QNAME, String.class,
                UploadTermoAditivoContrato.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFCEDENTE", scope = UploadTermoAditivoContrato.class)
    public JAXBElement<String> createUploadTermoAditivoContratoCNPJCPFCEDENTE(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFCEDENTE_QNAME, String.class,
                UploadTermoAditivoContrato.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFRESPONSAVEL", scope = UploadTermoAditivoContrato.class)
    public JAXBElement<String> createUploadTermoAditivoContratoCNPJCPFRESPONSAVEL(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFRESPONSAVEL_QNAME, String.class,
                UploadTermoAditivoContrato.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemoryStream
     * }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "retornoCNABResult", scope = RetornoCNABResponse.class)
    public JAXBElement<MemoryStream> createRetornoCNABResponseRetornoCNABResult(MemoryStream value) {
        return new JAXBElement<MemoryStream>(_RetornoCNABResponseRetornoCNABResult_QNAME, MemoryStream.class,
                RetornoCNABResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadNFEINFResult", scope = UploadNFEINFResponse.class)
    public JAXBElement<String> createUploadNFEINFResponseUploadNFEINFResult(String value) {
        return new JAXBElement<String>(_UploadNFEINFResponseUploadNFEINFResult_QNAME, String.class,
                UploadNFEINFResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = GetDocumentoAssinar.class)
    public JAXBElement<String> createGetDocumentoAssinarSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, GetDocumentoAssinar.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CHAVE", scope = GetDocumentoAssinar.class)
    public JAXBElement<String> createGetDocumentoAssinarCHAVE(String value) {
        return new JAXBElement<String>(_GetTesteVersaoCHAVE_QNAME, String.class, GetDocumentoAssinar.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CHAVE", scope = SetDocumento.class)
    public JAXBElement<String> createSetDocumentoCHAVE(String value) {
        return new JAXBElement<String>(_GetTesteVersaoCHAVE_QNAME, String.class, SetDocumento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "DOCUMENTOBIN", scope = SetDocumento.class)
    public JAXBElement<byte[]> createSetDocumentoDOCUMENTOBIN(byte[] value) {
        return new JAXBElement<byte[]>(_SetDocumentoDOCUMENTOBIN_QNAME, byte[].class, SetDocumento.class,
                ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "DESCRICAO", scope = GetComunicacao.class)
    public JAXBElement<String> createGetComunicacaoDESCRICAO(String value) {
        return new JAXBElement<String>(_GetComunicacaoDESCRICAO_QNAME, String.class, GetComunicacao.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadContratoNovoResult", scope = UploadContratoNovoResponse.class)
    public JAXBElement<String> createUploadContratoNovoResponseUploadContratoNovoResult(String value) {
        return new JAXBElement<String>(_UploadContratoNovoResponseUploadContratoNovoResult_QNAME, String.class,
                UploadContratoNovoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadTermoAditivoResult", scope = UploadTermoAditivoResponse.class)
    public JAXBElement<String> createUploadTermoAditivoResponseUploadTermoAditivoResult(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoResponseUploadTermoAditivoResult_QNAME, String.class,
                UploadTermoAditivoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UploadCNABResult", scope = UploadCNABResponse.class)
    public JAXBElement<String> createUploadCNABResponseUploadCNABResult(String value) {
        return new JAXBElement<String>(_UploadCNABResponseUploadCNABResult_QNAME, String.class,
                UploadCNABResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CHAVE", scope = GetDocumento.class)
    public JAXBElement<String> createGetDocumentoCHAVE(String value) {
        return new JAXBElement<String>(_GetTesteVersaoCHAVE_QNAME, String.class, GetDocumento.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "f", scope = UploadCNAB.class)
    public JAXBElement<byte[]> createUploadCNABF(byte[] value) {
        return new JAXBElement<byte[]>(_UploadTermoAditivoF_QNAME, byte[].class, UploadCNAB.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "Senha", scope = UploadCNAB.class)
    public JAXBElement<String> createUploadCNABSenha(String value) {
        return new JAXBElement<String>(_RetornoNFEINFSenha_QNAME, String.class, UploadCNAB.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "sFileName", scope = UploadCNAB.class)
    public JAXBElement<String> createUploadCNABSFileName(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoSFileName_QNAME, String.class, UploadCNAB.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFCEDENTE", scope = UploadCNAB.class)
    public JAXBElement<String> createUploadCNABCNPJCPFCEDENTE(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFCEDENTE_QNAME, String.class, UploadCNAB.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CNPJCPFRESPONSAVEL", scope = UploadCNAB.class)
    public JAXBElement<String> createUploadCNABCNPJCPFRESPONSAVEL(String value) {
        return new JAXBElement<String>(_UploadTermoAditivoCNPJCPFRESPONSAVEL_QNAME, String.class, UploadCNAB.class,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement
     * }{@code <}{@link UploadContratoNovo.RequisicaoCertificado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "requisicaoCertificado", scope = UploadContratoNovo.class)
    public JAXBElement<UploadContratoNovo.RequisicaoCertificado> createUploadContratoNovoRequisicaoCertificado(
            UploadContratoNovo.RequisicaoCertificado value) {
        return new JAXBElement<UploadContratoNovo.RequisicaoCertificado>(_UploadContratoNovoRequisicaoCertificado_QNAME,
                UploadContratoNovo.RequisicaoCertificado.class, UploadContratoNovo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "SetDocumentoResult", scope = SetDocumentoResponse.class)
    public JAXBElement<String> createSetDocumentoResponseSetDocumentoResult(String value) {
        return new JAXBElement<String>(_SetDocumentoResponseSetDocumentoResult_QNAME, String.class,
                SetDocumentoResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetDocumentoResult", scope = GetDocumentoResponse.class)
    public JAXBElement<byte[]> createGetDocumentoResponseGetDocumentoResult(byte[] value) {
        return new JAXBElement<byte[]>(_GetDocumentoResponseGetDocumentoResult_QNAME, byte[].class,
                GetDocumentoResponse.class, ((byte[]) value));
    }

}
