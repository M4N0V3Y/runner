package com.assinador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

// [DCR]
// classe  responsável pela assinatura dos arquivos lidos do servidor
public class PdfSigner {
    private static final String LOG_DIR;
    private static final String LOG_FILE_PATTERN = "assinado_local_";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");

    private final KeyStore keyStore;
    private List<BackEndObserver> observers;
    private String status;

    private static byte[] bytepolicy = {
            0x30, 0x3b, 0x06, 0x08, 0x60, 0x4c, 0x01, 0x07, 0x01, 0x01, 0x02, 0x02, 0x30, 0x2f, 0x30,
            0x0b, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x04, 0x20, 0x0f, 0x6f,
            (byte) 0xa2, (byte) 0xc6, 0x28, 0x19, (byte) 0x81, 0x71, 0x6c, (byte) 0x95, (byte) 0xc7, (byte) 0x98,
            (byte) 0x99, 0x03, (byte) 0x98, 0x44, 0x52, 0x3b, 0x1c, 0x61, (byte) 0xc2, (byte) 0xc9, 0x62, 0x28,
            (byte) 0x9c, (byte) 0xda, (byte) 0xc7, (byte) 0x81, 0x1f, (byte) 0xee, (byte) 0xe2, (byte) 0x9e };

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        String tempDir = System.getProperty("java.io.tmpdir");

        if (osName.contains("win")) {
            // Running on Windows, using C:\temp\
            LOG_DIR = "C:" + File.separator + "temp" + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Running on Linux, Unix, or AIX, using /tmp/
            LOG_DIR = File.separator + "tmp" + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        } else {
            // Default to the system's temporary directory
            LOG_DIR = tempDir + File.separator + LOG_FILE_PATTERN + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        }
    }

    // [ DCR ]
    // este método foi usada nos testes e permite gravar localmente os arquivos
    // assinados
    // este método pode ser suprimido no modo de produção
    //
    private String fullFlePath(String instancia) {
        String timestamp = DATE_FORMAT.format(new Date()).replace(":", "");
        String sFileName = LOG_DIR + LOG_FILE_PATTERN + instancia + timestamp + ".p7s";

        return sFileName;
    }

    /**
     * 
     * @param observer
     */
    private void addObserver(BackEndObserver observer) {
        observers.add(observer);
    }

    /**
     * 
     * @param observer
     */
    private void removeObserver(BackEndObserver observer) {
        observers.remove(observer);
    }

    /**
     * 
     */
    private void notifyObservers(String newStatus) {
        for (BackEndObserver observer : observers) {
            status = newStatus;
            observer.update(status);
        }
    }

    /**
     * 
     * @param observer
     * @throws Exception
     */
    // [DCR]
    // metodo de instancição da classe
    public PdfSigner(assinacertificado observer) throws Exception {
        observers = new ArrayList<>();
        addObserver(observer);
        keyStore = KeyStore.getInstance("Windows-MY");
        keyStore.load(null, null);
    }

    public static X509CertificateHolder convertToX509CertificateHolder(X509Certificate certificate)
            throws CertificateEncodingException {
        byte[] encoded = certificate.getEncoded();
        try {
            return new X509CertificateHolder(encoded);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 
     * @param certificate
     * @return
     * @throws Exception
     */
    /*
     * public static X509AttributeCertificateHolder
     * createAttributeCertificate(X509Certificate certificate, PrivateKey
     * privatekey) throws Exception {
     * // Generate a new key pair for the holder (you can modify this as needed)
     * KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
     * keyGen.initialize(2048);
     * //KeyPair holderKeyPair = keyGen.generateKeyPair();
     * 
     * // Derive issuer and holder information from the provided X509Certificate
     * X500Name issuerName = new JcaX509CertificateHolder(certificate).getIssuer();
     * // Issuer from the existing certificate
     * X500Name holderName = new JcaX509CertificateHolder(certificate).getSubject();
     * // Holder details from the existing certificate
     * 
     * // Create the AttributeCertificateHolder using the holder's details
     * AttributeCertificateHolder attributeHolder = new
     * AttributeCertificateHolder(holderName);
     * 
     * // Create the AttributeCertificateIssuer using the issuer's details
     * AttributeCertificateIssuer attributeIssuer = new
     * AttributeCertificateIssuer(issuerName);
     * 
     * 
     * // Validity period
     * Date notBefore =certificate.getNotBefore(); // new
     * Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24); // 1 day ago
     * Date notAfter = certificate.getNotAfter(); //new
     * Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365); // 1 year
     * ahead
     * 
     * // Create the attribute certificate builder
     * X509v2AttributeCertificateBuilder certBuilder = new
     * X509v2AttributeCertificateBuilder(
     * attributeHolder, // Holder as AttributeCertificateHolder
     * attributeIssuer, // Issuer as AttributeCertificateIssuer
     * certificate.getSerialNumber(), // Serial Number
     * notBefore, // Valid from
     * notAfter // Valid to
     * );
     * 
     * // Sign the certificate using the private key of the X509Certificate issuer
     * // Note: If you don't have access to the issuer's private key, you'll need a
     * substitute
     * ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
     * .build(privatekey); // Replace with the private key if available
     * 
     * // Build and return the attribute certificate
     * return certBuilder.build(signer);
     * }
     */

    /**
     * 
     * @param pdfToSign
     * @param alias
     * @param originalPdf
     * @return
     * @throws Exception
     */
    /*
     * @SuppressWarnings("rawtypes")
     * public byte[] genP7s(final byte[] pdfToSign, final String alias, byte[]
     * originalPdf) throws Exception
     * {
     * notifyObservers("PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo de assinatura."
     * );
     * String testeFilename = fullFlePath("primeira_assinatura_");
     * Security.addProvider(new BouncyCastleProvider());
     * 
     * X509Certificate certificate = (X509Certificate)
     * keyStore.getCertificate(alias);
     * 
     * CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
     * 
     * Store certs = new JcaCertStore(Arrays.asList(certificate));
     * X509CertificateHolder certHolder =
     * convertToX509CertificateHolder(certificate);
     * ContentSigner sha1Signer = new
     * JcaContentSignerBuilder("SHA256withRSA").setProvider(
     * "SunMSCAPI").build((PrivateKey) keyStore.getKey(alias, null));
     * 
     * // "BC" is the name of the BouncyCastle provider
     * signGen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
     * new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(
     * sha1Signer, certificate));
     * // Added -
     * //signGen.addSignerInfoGenerator(new SignerInfoGeneratorBuilder(
     * // new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
     * // .setSignedAttributeGenerator( new CustomAttributeTableGenerator(
     * certificate, originalPdf))
     * // .build(sha1Signer, certHolder));
     * 
     * X509AttributeCertificateHolder attributeCertificateHolder =
     * createAttributeCertificate(certificate, (PrivateKey) keyStore.getKey(alias,
     * null));
     * signGen.addAttributeCertificate(attributeCertificateHolder);
     * signGen.addCertificates(certs);
     * 
     * //--------------------------------------------------------
     * CMSTypedData msg = null;
     * //boolean bSegunda = false;
     * try
     * {
     * CMSSignedData signedData = null;
     * try
     * {
     * signedData = new CMSSignedData(pdfToSign);
     * }
     * catch(Exception e)
     * {
     * signedData = null;
     * }
     * 
     * if (signedData != null)
     * {
     * // segunda assinatura.
     * //bSegunda = true;
     * notifyObservers("PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo da segunda assinatura."
     * );
     * testeFilename = fullFlePath("segunda_assinatura_");
     * //signedData = new CMSSignedData(Base64.decode(pdfToSign));
     * 
     * msg = signedData.getSignedContent(); // o pdf aparece no p7s.
     * //msg = new CMSProcessableByteArray(pdfToSign); // erro: o pdf n�o aparece no
     * p7s.
     * 
     * try
     * {
     * SignerInformationStore signers = signedData.getSignerInfos();
     * signGen.addSigners(signers);
     * }
     * catch(Exception e)
     * {
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado."
     * );
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addSigners]."
     * );
     * throw new Exception("addSigners: " + e.getMessage());
     * }
     * 
     * try
     * {
     * Store storeCerts = signedData.getCertificates();
     * signGen.addCertificates(storeCerts);
     * }
     * catch(Exception ex)
     * {
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X ] -Arquivo P7S não foi gerado."
     * );
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addCertificates]."
     * );
     * throw new Exception("addCertificates: " + ex.getMessage());
     * }
     * 
     * try
     * {
     * Store storeCRLs = signedData.getCRLs();
     * signGen.addCRLs(storeCRLs);
     * }
     * catch(Exception ey)
     * {
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X ] -Arquivo P7S não foi gerado."
     * );
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addCRLs]."
     * );
     * throw new Exception("addCRLs: " + ey.getMessage());
     * }
     * 
     * try
     * {
     * Store storeAttrCerts = signedData.getAttributeCertificates();
     * signGen.addAttributeCertificates(storeAttrCerts);
     * }
     * catch(Exception ez)
     * {
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado."
     * );
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addAttributeCertificates]."
     * );
     * throw new Exception("addAttributeCertificates: " + ez.getMessage());
     * }
     * }
     * else
     * {
     * // primeira assinatura.
     * msg = new CMSProcessableByteArray(pdfToSign);
     * notifyObservers("PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo da primeira assinatura."
     * );
     * }
     * }
     * catch (Exception e)
     * {
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado."
     * );
     * throw new Exception("genP7s: " + e.getMessage());
     * }
     * //--------------------------------------------------------
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] -Arquivo P7S gerado com sucesso!."
     * );
     * 
     * 
     * CMSSignedData sigData = signGen.generate(msg, true);
     * 
     * // Test
     * 
     * try (FileOutputStream fos = new FileOutputStream(testeFilename)) { // File
     * 
     * byte[] conteudo = sigData.getEncoded();// path
     * fos.write(conteudo); // Write the entire byte array to the file
     * 
     * } catch (IOException e) {
     * 
     * e.printStackTrace(); // Or your logging mechanism
     * notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo de teste P7S não foi gravado."
     * );
     * 
     * }
     * //
     * 
     * return sigData.getEncoded();
     * }
     */

    /**
     * 
     * @param pdfToSign
     * @param alias
     * @param originalPdf
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes" })
    // genP7S_old
    // [DCR]
    // assina o conteúdo de um arquivo no formato PDF
    // recebe os bytes do arquivo, o alias do certificado e o pdfOriginal para
    // garantir a
    // integridade do cálculo do message digest quando o PDF for lido pela segunda
    // vez
    public byte[] genP7s(byte[] pdfToSign, final String alias, byte[] originalPdf) throws Exception {

        notifyObservers("PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo de assinatura.");
        String testeFilename = fullFlePath("primeira_assinatura_");

        Security.addProvider(new BouncyCastleProvider());

        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

        CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);

        Store certs = new JcaCertStore(Arrays.asList(certificate));
        X509CertificateHolder certHolder = convertToX509CertificateHolder(certificate);
        ContentSigner sha1Signer = null;
        try {
            sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(
                    "BC").build(privateKey);
        } catch (Exception exp) {
            if (exp.getCause() != null)
                System.out.print(exp.getCause().getMessage());
            System.out.print(exp.getMessage());
            //
            sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(
                    "SunMSCAPI").build(privateKey);
        }

        /*
         * try {
         * signGen.addSignerInfoGenerator(new SignerInfoGeneratorBuilder(
         * new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
         * .setSignedAttributeGenerator( new CustomAttributeTableGenerator( certificate,
         * originalPdf))
         * .build(sha1Signer, certHolder));
         * } catch (Exception exp) {
         * if(exp.getCause()!=null) System.out.print(exp.getCause().getMessage());
         * System.out.print(exp.getMessage());
         * signGen.addSignerInfoGenerator(new SignerInfoGeneratorBuilder(
         * new JcaDigestCalculatorProviderBuilder().setProvider("SunMSCAPI").build())
         * .setSignedAttributeGenerator( new CustomAttributeTableGenerator( certificate,
         * originalPdf))
         * .build(sha1Signer, certHolder));
         * }
         * 
         */

        // Add PAdES policy
        notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Implementar política PaDES.");
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();

        // Add Signature Policy Identifier attribute (2.16.76.1.7.1.1.1)
        signedAttrs.add(new Attribute(PKCSObjectIdentifiers.id_aa_ets_sigPolicyId, // id_aa_ets_sigPolicyHash,
                new DERSet(new DERSequence(new ASN1Encodable[] {
                        new DERSet(new DEROctetString(bytepolicy)),
                        new ASN1ObjectIdentifier("2.16.76.1.7.1.1.1"), // OID for ICP-Brasil PAdES policy
                        new DERUTF8String("ICP-Brasil PAdES Policy Description") }))));

        // Add Signature Policy Hash attribute (1.2.840.113549.1.9.16.2.15)
        signedAttrs.add(new Attribute(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.15"),
                new DERSet(new DEROctetString(bytepolicy))));

        // Add Signing Certificate attribute (1.2.840.113549.1.9.16.2.12)
        ESSCertID essCertID = new ESSCertID(certificate.getEncoded()); // certHolder.getEncoded());
        SigningCertificate signingCertificate = new SigningCertificate(essCertID);
        signedAttrs.add(new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificate, new DERSet(signingCertificate)));

        // Add Signing Time attribute (1.2.840.113549.1.9.5)
        signedAttrs.add(
                new Attribute(PKCSObjectIdentifiers.pkcs_9_at_signingTime,
                        new DERSet(new DERUTCTime(new Date()))));
        // Add Commitment Type Indication attribute (1.2.840.113549.1.9.16.2.16)
        ASN1EncodableVector commitmentTypeVector = new ASN1EncodableVector();
        commitmentTypeVector.add(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.6.1")); // Example OID for proof of
                                                                                         // approval
        signedAttrs.add(new Attribute(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.16"),
                new DERSet(new DERSequence(commitmentTypeVector))));

        signGen.addSignerInfoGenerator(new SignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
                .setSignedAttributeGenerator(new DefaultSignedAttributeTableGenerator(new AttributeTable(signedAttrs)))
                .build(sha1Signer, certHolder));

        // dcr
        // ContentSigner sha1Signer = new
        // JcaContentSignerBuilder("SHA1withRSA").setProvider(
        // "SunMSCAPI").build((PrivateKey) keyStore.getKey(alias, null));

        // --------------------------------------------------------
        CMSTypedData msg = null;
        // boolean bSegunda = false;
        try {
            CMSSignedData signedData = null;
            try {
                signedData = new CMSSignedData(pdfToSign);
            } catch (Exception e) {
                signedData = null;
            }
            notifyObservers("\nPdfSigner::genP7s - Iniciando a construção do assinador.");
            if (signedData != null) {
                notifyObservers(
                        "PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo da segunda assinatura.");
                testeFilename = fullFlePath("segunda_assinatura_");
                // segunda assinatura.
                // bSegunda = true;

                // signedData = new CMSSignedData(Base64.decode(pdfToSign));

                msg = signedData.getSignedContent(); // o pdf aparece no p7s.
                // msg = new CMSProcessableByteArray(pdfToSign); // erro: o pdf n�o aparece no
                // p7s.

                try {

                    SignerInformationStore signers = signedData.getSignerInfos();
                    signGen.addSigners(signers);
                } catch (Exception e) {
                    notifyObservers(
                            "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado.");
                    notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addSigners].");
                    throw new Exception("addSigners: " + e.getMessage());
                }

                try {
                    Store storeCerts = signedData.getCertificates();
                    signGen.addCertificates(storeCerts);
                } catch (Exception ex) {
                    notifyObservers(
                            "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado.");
                    notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addCertificates].");
                    throw new Exception("addCertificates: " + ex.getMessage());
                }

                try {
                    Store storeCRLs = signedData.getCRLs();
                    signGen.addCRLs(storeCRLs);
                } catch (Exception ey) {
                    notifyObservers(
                            "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado.");
                    notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addCRLs].");
                    throw new Exception("addCRLs: " + ey.getMessage());
                }

                try {
                    Store storeAttrCerts = signedData.getAttributeCertificates();
                    signGen.addAttributeCertificates(storeAttrCerts);
                } catch (Exception ez) {
                    notifyObservers(
                            "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado.");
                    notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [ERRO : addAttributeCertificates].");
                    throw new Exception("addAttributeCertificates: " + ez.getMessage());
                }
            } else {
                // primeira assinatura.
                msg = new CMSProcessableByteArray(pdfToSign);
                notifyObservers(
                        "PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo da primeira assinatura.");
            }
        } catch (Exception e) {
            notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo P7S não foi gerado.");
            throw new Exception("genP7s: " + e.getMessage());
        }
        // --------------------------------------------------------

        signGen.addCertificates(certs);

        notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] -Arquivo P7S gerado com sucesso!.");
        CMSSignedData sigData = signGen.generate(msg, true);
        // Test

        try (FileOutputStream fos = new FileOutputStream(testeFilename)) { // File

            byte[] conteudo = sigData.getEncoded();// path
            fos.write(conteudo); // Write the entire byte array to the file

        } catch (IOException e) {

            e.printStackTrace(); // Or your logging mechanism
            notifyObservers(
                    "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEPTION X] -Arquivo de teste P7S não foi gravado.");

        }
        //

        return sigData.getEncoded();

    }

    /**
     * 
     * @return
     * @throws KeyStoreException
     */
    public String[] keyAliases() throws KeyStoreException {
        return Collections.list(keyStore.aliases()).toArray(new String[] {});
    }

}