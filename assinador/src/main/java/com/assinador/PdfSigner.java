package com.assinador;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

public class PdfSigner {

    private final KeyStore keyStore;
    //
    private List<BackEndObserver> observers;
    private String status;
    private assinacertificado _observer;

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

    public PdfSigner(assinacertificado observer) throws Exception {

        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;
        // mostra todos os certificados
        keyStore = KeyStore.getInstance("Windows-MY");

        keyStore.load(null, null);
    }

    public byte[] genP7s(final byte[] pdfToSign, final String alias) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

        CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();

        Store certs = new JcaCertStore(Arrays.asList(certificate));

        ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(
                "SunMSCAPI").build((PrivateKey) keyStore.getKey(alias, null));

        // "BC" is the name of the BouncyCastle provider
        signGen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(
                        sha1Signer, certificate));

        signGen.addCertificates(certs);

        // --------------------------------------------------------
        CMSTypedData msg = null;
        // boolean bSegunda = false;
        try {

            // ------------------------------

            CMSSignedData signedData = null;
            try {
                signedData = new CMSSignedData(pdfToSign);
            } catch (Exception e) {
                e.printStackTrace();
                signedData = null;
            }

            if (signedData != null) {
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
                    throw new Exception("addSigners: " + e.getMessage());
                }

                try {
                    Store storeCerts = signedData.getCertificates();
                    signGen.addCertificates(storeCerts);
                } catch (Exception e) {
                    throw new Exception("addCertificates: " + e.getMessage());
                }

                try {
                    Store storeCRLs = signedData.getCRLs();
                    signGen.addCRLs(storeCRLs);
                } catch (Exception e) {
                    throw new Exception("addCRLs: " + e.getMessage());
                }

                try {
                    Store storeAttrCerts = signedData.getAttributeCertificates();
                    signGen.addAttributeCertificates(storeAttrCerts);
                } catch (Exception e) {
                    throw new Exception("addAttributeCertificates: " + e.getMessage());
                }
            } else {
                // primeira assinatura.
                msg = new CMSProcessableByteArray(pdfToSign);
            }

            notifyObservers(
                    "ROTINA ASSINATURA:: [INFO ⚠] -Arquivo P7S gerado com sucesso!");

        } catch (Exception e) {
            notifyObservers(
                    "ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Arquivo P7S não foi gerado");
            throw new Exception("genP7s: " + e.getMessage());

        }
        // --------------------------------------------------------

        CMSSignedData sigData = signGen.generate(msg, true);

        return sigData.getEncoded();
    }

    public String[] keyAliases() throws KeyStoreException {
        return Collections.list(keyStore.aliases()).toArray(new String[] {});
    }

}
