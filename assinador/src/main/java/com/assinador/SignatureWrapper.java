package com.assinador;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.security.*;
import java.security.KeyStore.Builder;
import java.security.cert.Certificate;
import java.util.Calendar;
import java.util.Enumeration;

import org.demoiselle.signer.core.keystore.loader.KeyStoreLoader;
import org.demoiselle.signer.core.keystore.loader.factory.KeyStoreLoaderFactory;
import org.demoiselle.signer.cryptography.DigestAlgorithmEnum;
import org.demoiselle.signer.policy.engine.factory.PolicyFactory;
import org.demoiselle.signer.policy.engine.factory.PolicyFactory.Policies;
import org.demoiselle.signer.policy.impl.cades.SignerAlgorithmEnum;
import org.demoiselle.signer.policy.impl.cades.factory.PKCS7Factory;
import org.demoiselle.signer.policy.impl.cades.pkcs7.PKCS7Signer;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESSigner;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESTimeStampSigner;
import org.demoiselle.signer.policy.impl.pades.pkcs7.impl.PAdESSigner;
import org.demoiselle.signer.policy.impl.pades.pkcs7.impl.PAdESTimeStampSigner;

import org.demoiselle.signer.policy.impl.cades.pkcs7.PKCS7Checker;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
//import org.demoiselle.signer.policy.impl.cades.pkcs7.PKCS7SignerFactory;
//import org.demoiselle.signer.policy.impl.cades.pkcs7.PKCS7SignerParameters;

import com.RunnerUtils;

public class SignatureWrapper {

    private static List<BackEndObserver> observers;
    private static String status;
    private static String _certName;
    private static assinacertificado _observer;
    private static Certificate[] _certificateChain;
    private static PrivateKey _prvKey;

    //
    private static String _ROUTN_ASSINA_DOC_NM = "ROTINA ASSINATURA:: [INFO ⚠] - Assinando o(s) documentos(s):  [   %s   ]";
    private static String _INIT_ROUTN_ASSINA = "ROTINA ASSINATURA:: [INFO ⚠] - Iniciando a assinatura do(s) documentos(s)";
    private static String _ROTINA_ASSINA_SUCCESS = "ROTINA ASSINATURA:: [INFO ✅] - Documentos(s) assinado(s) com sucesso";
    private static String _ROTINA_ASSINA_FAIL = "ROTINA ASSINATURA:: [ERRO ❌] - Falha ao tentar assinar o(s) documentos(s)";
    private static String _ROTINA_ASSINA_XML = "ROTINA ASSINATURA:: [INFO ⚠] - Assinando documento XML";
    private static String _ROTINA_ASSINA_PDF = "ROTINA ASSINATURA:: [INFO ⚠] - Assinando documento PDF";

    /**
     * 
     * @param observer
     */
    public static void addObserver(BackEndObserver observer) {
        observers.add(observer);
    }

    /**
     * 
     * @param observer
     */
    public static void removeObserver(BackEndObserver observer) {
        observers.remove(observer);
    }

    /**
     * 
     */
    private static void notifyObservers(String newStatus) {
        for (BackEndObserver observer : observers) {
            status = newStatus;
            observer.update(status);
        }
    }

    SignatureWrapper(assinacertificado observer) {
        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;

    }

    /**
     * 
     * @param certificateChain
     * @param prvKey
     * @param fullFilePath
     * @throws Exception
     */
    public static void SignnWithPolicy(String certName, Certificate cert, Certificate[] certificateChain,
            PrivateKey prvKey,
            String fullFilePath)
            throws Exception {

        // PKCS7Signer signer = PKCS7Factory.getInstance().factoryDefault();
        PAdESSigner padSigner = null;
        CAdESTimeStampSigner cadSigner = null;
        PKCS7Signer reSigner = null;

        _certificateChain = certificateChain;
        _certName = certName;
        _prvKey = prvKey;

        notifyObservers(_INIT_ROUTN_ASSINA);
        notifyObservers(String.format(_ROUTN_ASSINA_DOC_NM, fullFilePath));

        try {

            // signer.setCertificates(certificateChain);
            // signer.setPrivateKey(prvKey);
            // signer.setPrivateKeyForTimeStamp(prvKey); // Timestamp
            // signer.setAlgorithm(SignerAlgorithmEnum.SHA256withRSA.getAlgorithm());

            LoadFileNInfo ldFileNInfo = new LoadFileNInfo(_observer);
            ldFileNInfo.setFileInfoAndContent(fullFilePath);

            Policies SIGN_POLICY;

            if (fileIsPDF(ldFileNInfo.getFileExtention())) {
                // PAdES policy ---
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_PADES_1_1; // AD_RA_PADES_1_1 AD_RB
                padSigner = new PAdESSigner(SignerAlgorithmEnum.SHA256withRSA.getAlgorithm(), SIGN_POLICY);
                // SignerAlgorithmEnum.SHA256withRSA.getAlgorithm(), SIGN_POLICY
                padSigner.setCertificates(_certificateChain);
                padSigner.setCertificatesForTimeStamp(_certificateChain);
                padSigner.setPrivateKey(_prvKey);
                padSigner.setPrivateKeyForTimeStamp(_prvKey); // Timestamp

            } else if (fileIsXML(ldFileNInfo.getFileExtention())) {
                // XAdES policy ----
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_XADES_2_4; // AD_RA_XADES_2_3

            } else if (fileIsP7S(ldFileNInfo.getFileExtention())) {
                // PKCS7SignerParameters reSignerParams = new PKCS7SignerParameters();
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_CADES_2_3;
                //
                reSigner = PKCS7Factory.getInstance().factoryDefault();
                reSigner.setAlgorithm(SignerAlgorithmEnum.SHA256withRSA.getAlgorithm());
                reSigner.setSignaturePolicy(SIGN_POLICY);
                reSigner.setCertificates(certificateChain);
                reSigner.setCertificatesForTimeStamp(certificateChain);
                reSigner.setPrivateKey(prvKey);
                reSigner.setPrivateKeyForTimeStamp(prvKey);

            } else {
                // CAdES policy ---
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_CADES_2_3; // ...others
                cadSigner = new CAdESTimeStampSigner();
                // (SignerAlgorithmEnum.SHA256withRSA.getAlgorithm(), SIGN_POLICY);
                cadSigner.setCertificateChain(certificateChain);
                cadSigner.setCertificates(certificateChain);
                cadSigner.setPrivateKey(prvKey);
                // cadSigner.setPrivateKeyForTimeStamp(prvKey); // Timestamp

            }
            // signer.setSignaturePolicy(SIGN_POLICY);

            byte[] content = ldFileNInfo.getFileContent();
            if (reSigner != null) {
                // byte[] signed = reSigner.doDetachedSign(content); //
                // .doAttachedSign(content);
                byte[] signed = reSigner.doAttachedSign(content);
                FileOutputStream outputStream = new FileOutputStream(fullFilePath);
                outputStream.write(signed);
                notifyObservers(_ROTINA_ASSINA_SUCCESS);
            } else if (padSigner != null) {
                // byte[] signed = padSigner.doDetachedSign(content); //
                // .doAttachedSign(content);
                // FileOutputStream outputStream = new FileOutputStream(fullFilePath + ".p7s");
                // outputStream.write(signed);
                try {
                    doPDFSigner(certName, content, fullFilePath + ".p7s");
                } catch (Throwable e) {

                    e.printStackTrace();
                }
                notifyObservers(_ROTINA_ASSINA_SUCCESS);
            } else {
                byte[] signed = cadSigner.doTimeStampForSignature(content); //
                // doAttachedSign(content);
                FileOutputStream outputStream = new FileOutputStream(fullFilePath + ".p7s");
                outputStream.write(signed);
                notifyObservers(_ROTINA_ASSINA_SUCCESS);

            }
            // ByteArrayInputStream bis = new ByteArrayInputStream(signed);
            // Files.copy(bis, fullFilePath+".p7s",CopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

            String detailMessage = RunnerUtils.extractDetailedMessage(e);
            notifyObservers(_ROTINA_ASSINA_FAIL);
            notifyObservers(detailMessage);
            e.printStackTrace();
            throw e;
        }

    }

    private static boolean fileIsXML(String fileExtention) {
        // check if XML
        notifyObservers(_ROTINA_ASSINA_XML);
        return fileExtention.toUpperCase().equals("XML".toUpperCase());

    }

    private static boolean fileIsPDF(String fileExtention) {
        // Check if PDF
        notifyObservers(_ROTINA_ASSINA_PDF);
        return fileExtention.toUpperCase().equals("PDF".toUpperCase());
    }

    private static boolean fileIsP7S(String fileExtention) {
        // Check if PDF
        notifyObservers(_ROTINA_ASSINA_PDF);
        return fileExtention.toUpperCase().equals("P7S".toUpperCase());
    }

    private static void doPDFSigner(String certName, byte[] toSign, final String signedFile) throws Throwable {

        FileOutputStream fos = new FileOutputStream(signedFile);
        java.security.MessageDigest md1 = java.security.MessageDigest
                .getInstance(DigestAlgorithmEnum.SHA_256.getAlgorithm());
        byte[] hashOriginal = md1.digest(toSign);
        String hashOriginalToHex = org.bouncycastle.util.encoders.Hex.toHexString(hashOriginal);
        BigInteger bigId = new BigInteger(hashOriginalToHex.toUpperCase(), 16);
        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_SHA1);
        // signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

        //

        Calendar.getInstance();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(toSign);

        PDDocument original = PDDocument.load(inputStream);
        original.setDocumentId(bigId.longValue());
        original.addSignature(signature, new SignatureInterface() {
            public byte[] sign(InputStream contentToSign) throws IOException {

                byte[] byteContentToSign = toSign; // IOUtils.toByteArray(contentToSign);
                try {
                    // java.security.MessageDigest md =
                    // java.security.MessageDigest.getInstance("SHA-512"); // SHA-512
                    java.security.MessageDigest md = java.security.MessageDigest
                            .getInstance(DigestAlgorithmEnum.SHA_256.getAlgorithm());
                    // gera o hash do arquivo
                    // devido a uma restrição do token branco, no windws só funciona com 256
                    // if
                    // (org.demoiselle.signer.core.keystore.loader.configuration.Configuration.getInstance().getSO()
                    // .toLowerCase().indexOf("Windows") > 0) {
                    // md =
                    // java.security.MessageDigest.getInstance(DigestAlgorithmEnum.SHA_512.getAlgorithm());
                    // }
                    byte[] hashToSign = md.digest(byteContentToSign);
                    String hashToSignHex = org.bouncycastle.util.encoders.Hex.toHexString(hashToSign);
                    System.out.println("hashPDFtoSign: " + hashToSignHex);

                    notifyObservers("ROTINA ASSINATURA:: [INFO ⚠] -HASH da assinatura : " + hashToSignHex);

                    // windows e NeoID
                    KeyStore ks = getKeyStoreTokenBySigner();

                    // KeyStore ks = getKeyStoreToken();

                    // para arquivo
                    // KeyStore ks = getKeyStoreFileBySigner();

                    // para timeStamp

                    KeyStore ksToTS = getKeyStoreTokenBySigner();

                    // String alias = getAlias(ks);

                    // String aliasTS = getAlias(ksToTS);

                    PAdESSigner signer = new PAdESSigner();

                    // cert

                    // signer.setCertificates(_certificateChain);
                    // signer.setCertificatesForTimeStamp(_certificateChain);
                    // signer.setPrivateKey(_prvKey);
                    char[] spK = _prvKey.toString().toCharArray();
                    signer.setPrivateKey((PrivateKey) ks.getKey(certName, spK));
                    signer.setPrivateKeyForTimeStamp((PrivateKey) ksToTS.getKey(certName, null));
                    // signer.setPrivateKeyForTimeStamp(_prvKey);
                    signer.setSignaturePolicy(PolicyFactory.Policies.AD_RB_PADES_1_1);
                    signer.setAlgorithm(SignerAlgorithmEnum.SHA256withRSA.getAlgorithm());
                    signer.setCertificates(ks.getCertificateChain(certName));
                    signer.setCertificatesForTimeStamp(ksToTS.getCertificateChain(certName));

                    notifyObservers("ROTINA ASSINATURA:: [INFO ⚠] -política de assinatura : PaDES ");

                    byte[] assinatura = signer.doHashSign(hashToSign);

                    return assinatura;
                } catch (Throwable error) {
                    error.printStackTrace();
                    return null;
                }
            }
        });
        original.saveIncremental(fos);
        original.close();
    }

    // Usa o Signer para leitura, funciona para windows e NeoID
    private static KeyStore getKeyStoreTokenBySigner() {

        try {

            KeyStoreLoader keyStoreLoader = KeyStoreLoaderFactory.factoryKeyStoreLoader();
            KeyStore keyStore = keyStoreLoader.getKeyStore();

            return keyStore;

        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
        }

    }

    /**
     * Faz a leitura do token em LINUX, precisa setar a lib (.SO) e a senha do
     * token.
     */
    @SuppressWarnings("restriction")
    private static KeyStore getKeyStoreToken() {

        try {
            // ATENÇÃO ALTERAR CONFIGURAÇÃO ABAIXO CONFORME O TOKEN USADO

            // Para TOKEN Branco a linha abaixo
            // String pkcs11LibraryPath =
            // "/usr/lib/watchdata/ICP/lib/libwdpkcs_icp.so";

            // Para TOKEN Azul a linha abaixo
            String pkcs11LibraryPath = "/usr/lib/libeToken.so";

            StringBuilder buf = new StringBuilder();
            buf.append("library = ").append(pkcs11LibraryPath).append("\nname = Provedor\n");
            Provider p = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(buf.toString().getBytes()));
            Security.addProvider(p);
            // ATENÇÃO ALTERAR "SENHA" ABAIXO
            Builder builder = KeyStore.Builder.newInstance("PKCS11", p,
                    new KeyStore.PasswordProtection("senha".toCharArray()));
            KeyStore ks;
            ks = builder.getKeyStore();

            return ks;

        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        } finally {
        }

    }

    private static String getAlias(KeyStore ks) {
        Certificate[] certificates = null;
        String alias = "";
        Enumeration<String> e;
        try {
            e = ks.aliases();
            while (e.hasMoreElements()) {
                alias = e.nextElement();
                System.out.println("alias..............: " + alias);
                System.out.println("iskeyEntry" + ks.isKeyEntry(alias));
                System.out.println("containsAlias" + ks.containsAlias(alias));
                // System.out.println(""+ks.getKey(alias, null));
                certificates = ks.getCertificateChain(alias);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alias;
    }

}
