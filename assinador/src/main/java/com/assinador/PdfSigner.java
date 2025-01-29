package com.assinador;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERObjectIdentifier;
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
import java.security.cert.CertificateEncodingException;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGeneratorBuilder;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

public class PdfSigner {

    private final KeyStore keyStore;
    private List<BackEndObserver> observers;
    private String status;
    private assinacertificado _observer;

    private static final ASN1ObjectIdentifier id_aa_ets_sigPolicyHash = new ASN1ObjectIdentifier(
            "1.2.840.113549.1.9.16.2.15");
    private static final ASN1ObjectIdentifier id_aa_ets_commitmentType = new ASN1ObjectIdentifier(
            "1.2.840.113549.1.9.16.6.1");

    private static byte[] bytepolicy = {
            0x30, 0x3b, 0x06, 0x08, 0x60, 0x4c, 0x01, 0x07, 0x01, 0x01, 0x02, 0x02, 0x30, 0x2f, 0x30,
            0x0b, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x04, 0x20, 0x0f, 0x6f,
            (byte) 0xa2, (byte) 0xc6, 0x28, 0x19, (byte) 0x81, 0x71, 0x6c, (byte) 0x95, (byte) 0xc7, (byte) 0x98,
            (byte) 0x99, 0x03, (byte) 0x98, 0x44, 0x52, 0x3b, 0x1c, 0x61, (byte) 0xc2, (byte) 0xc9, 0x62, 0x28,
            (byte) 0x9c, (byte) 0xda, (byte) 0xc7, (byte) 0x81, 0x1f, (byte) 0xee, (byte) 0xe2, (byte) 0x9e };

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
    public PdfSigner(assinacertificado observer) throws Exception {
        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;
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

    @SuppressWarnings({ "rawtypes", "deprecation" })
    public byte[] genP7s(final byte[] pdfToSign, final String alias) throws Exception {

        notifyObservers("PdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Início do processo de assinatura.");

        Security.addProvider(new BouncyCastleProvider());

        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

        CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();

        Store certs = new JcaCertStore(Arrays.asList(certificate));

        ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(
                "SunMSCAPI").build((PrivateKey) keyStore.getKey(alias, null));

        signGen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(
                        sha1Signer, certificate));

        signGen.addCertificates(certs);

        CMSTypedData msg = null;
        notifyObservers("PdfSigner::genP7s - Iniciando a assinatura do documento.");
        try {
            CMSSignedData signedData = null;
            try {
                signedData = new CMSSignedData(pdfToSign);
            } catch (Exception e) {
                e.printStackTrace();
                signedData = null;
            }
            notifyObservers("\nPdfSigner::genP7s - Iniciando a construção do assinador.");
            if (signedData != null) {
                msg = signedData.getSignedContent();
                try {
                    SignerInformationStore signers = signedData.getSignerInfos();
                    signGen.addSigners(signers);
                    Store storeCerts = signedData.getCertificates();
                    signGen.addCertificates(storeCerts);
                    Store storeCRLs = signedData.getCRLs();
                    signGen.addCRLs(storeCRLs);
                    Store storeAttrCerts = signedData.getAttributeCertificates();
                    signGen.addAttributeCertificates(storeAttrCerts);

                } catch (Exception e) {
                    throw new Exception("addAttributeCertificates: " + e.getMessage());
                }
            } else {
                msg = new CMSProcessableByteArray(pdfToSign);
            }
            notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] -Arquivo P7S gerado com sucesso!.");
            notifyObservers(
                    "ROTINA ASSINATURA:: [INFO ⚠] -Arquivo P7S gerado com sucesso!");

        } catch (Exception e) {
            notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Arquivo P7S não foi gerado.");
            notifyObservers(
                    "ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Arquivo P7S não foi gerado");
            throw new Exception("genP7s: " + e.getMessage());

        }

        // Add PAdES policy
        notifyObservers("\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Implementar política PaDES.");
        ASN1EncodableVector signedAttrs = new ASN1EncodableVector();
        X509CertificateHolder certHolder = convertToX509CertificateHolder(certificate);

        // Add Signature Policy Identifier attribute (2.16.76.1.7.1.1.1)
        signedAttrs.add(new Attribute(PKCSObjectIdentifiers.id_aa_ets_sigPolicyId, // id_aa_ets_sigPolicyHash,
                new DERSet(new DERSequence(new ASN1Encodable[] {
                        new DERSet(new DEROctetString(bytepolicy)),
                        new DERObjectIdentifier("2.16.76.1.7.1.1.1"), // OID for ICP-Brasil PAdES policy
                        new DERUTF8String("ICP-Brasil PAdES Policy Description") }))));

        // Add Signature Policy Hash attribute (1.2.840.113549.1.9.16.2.15)
        signedAttrs.add(new Attribute(id_aa_ets_sigPolicyHash, new DERSet(new DEROctetString(bytepolicy))));

        // Add Signing Certificate attribute (1.2.840.113549.1.9.16.2.12)
        ESSCertID essCertID = new ESSCertID(certHolder.getEncoded());
        SigningCertificate signingCertificate = new SigningCertificate(essCertID);
        signedAttrs.add(new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificate, new DERSet(signingCertificate)));

        // Add Signing Time attribute (1.2.840.113549.1.9.5)
        signedAttrs.add(
                new Attribute(PKCSObjectIdentifiers.pkcs_9_at_signingTime,
                        new DERSet(new DERUTCTime(new Date()))));
        // Add Commitment Type Indication attribute (1.2.840.113549.1.9.16.2.16)
        ASN1EncodableVector commitmentTypeVector = new ASN1EncodableVector();
        commitmentTypeVector.add(new DERObjectIdentifier("1.2.840.113549.1.9.16.6.1")); // Example OID for proof of
                                                                                        // approval
        signedAttrs.add(new Attribute(id_aa_ets_commitmentType, new DERSet(new DERSequence(commitmentTypeVector))));

        signGen.addSignerInfoGenerator(new SignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
                .setSignedAttributeGenerator(new DefaultSignedAttributeTableGenerator(new AttributeTable(signedAttrs)))
                .build(sha1Signer, certHolder));

        notifyObservers(
                "\nPdfSigner::genP7s - ROTINA ASSINATURA:: [INFO ⚠] Implementada política ICP-Brasil PAdES para assinatura P7S");
        notifyObservers(
                "ROTINA ASSINATURA:: [INFO ⚠] - Implementada política ICP-Brasil PAdES para assinatura P7S");

        CMSSignedData sigData = signGen.generate(msg, true);

        return sigData.getEncoded();
    }

    public String[] keyAliases() throws KeyStoreException {
        return Collections.list(keyStore.aliases()).toArray(new String[] {});
    }
}