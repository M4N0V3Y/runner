package com.assinador;

import java.awt.RenderingHints.Key;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.demoiselle.signer.core.keystore.loader.KeyStoreLoader;
import org.demoiselle.signer.core.keystore.loader.implementation.MSKeyStoreLoader;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESSigner;

public class LoadCertificates {

    private static List<BackEndObserver> observers;
    private static KeyStore ksMy;
    private static KeyStore ksRoot;
    private static CAdESSigner signer;
    private static KeyStoreLoader keyStoreLoader;

    private static List<String> certNames;
    private static List<Certificate> certList;
    private static String status;
    //
    private static String _LOAD_CERT_PK = "CARREGA CERTIFICADOS:: [INFO ⚠] - Obteve chave primária do certificado";
    private static String _LOAD_CERT_OK = "CARREGA CERTIFICADOS:: [INFO ✅] - Obteve os certificados com sucesso";
    private static String _LOAD_CERT_INIT = "CARREGA CERTIFICADOS:: [INFO ⚠] - Iniciando o processo de carregamento dos Cetificados";
    private static String _LOAD_CERT_DONE_OK = "CARREGA CERTIFICADOS:: [INFO ✅] - Cetificados carregados";
    private static String _LOAD_CERT_DONE_FAIL = "CARREGA CERTIFICADO:: [INFO ❌] - Falha ao carregar os certificados";
    private static String _LOAD_CERT_PK_ERR = "CARREGA CERTIFICADOS:: [ERRO ❌] - Falha ao obter a  chave primária do certificado";
    private static String _LOAD_CERT_ERR = "CARREGA CERTIFICADOS:: [ERRO ❌] - Falha ao obter os certificados";

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
        try {

            for (BackEndObserver observer : observers) {
                status = newStatus;
                observer.update(status);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Constructor
     */
    LoadCertificates(assinacertificado observer) {

        signer = new CAdESSigner();
        observers = new ArrayList<>();
        observers.add(observer);

        load();
        signer.setCertificates(certList.toArray(new Certificate[0]));
    }

    /**
     * 
     * @param alias
     * @param pswString
     * @return
     */
    public static PrivateKey getPrivateKey(String alias, char[] pswString) {

        // private static KeyStore ksMy;
        // private static KeyStore ksRoot;

        PrivateKey thisKelclKey;
        try {
            thisKelclKey = (PrivateKey) ksMy.getKey(alias, pswString);
            if (thisKelclKey == null)
                throw new KeyStoreException();
            notifyObservers(_LOAD_CERT_PK);
            return thisKelclKey;
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {

            try {
                thisKelclKey = (PrivateKey) ksRoot.getKey(alias, pswString);
                return thisKelclKey;
            } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e2) {

                e.printStackTrace();
            }

            e.printStackTrace();
        }
        notifyObservers(_LOAD_CERT_PK_ERR);
        return null;

    }

    public static Certificate[] getCertificateChain(String alias) {

        Certificate[] certificateChain;

        try {
            certificateChain = ksMy.getCertificateChain(alias);
        } catch (KeyStoreException ex) {
            try {
                certificateChain = ksRoot.getCertificateChain(alias);
            } catch (final KeyStoreException ex2) {
                notifyObservers(_LOAD_CERT_ERR);
                return null;
            }

        }
        notifyObservers(_LOAD_CERT_OK);
        return certificateChain;

    }

    public static List<Certificate> getCertificates() throws Exception {

        return certList;
    }

    public static List<String> getCertificateNames() throws Exception {

        return certNames;
    }

    public static void load() {
        try {
            notifyObservers(_LOAD_CERT_INIT);
            // Load the Windows-MY keystore
            ksMy = KeyStore.getInstance("Windows-MY");
            ksMy.load(null, null);
            System.out.println("Certificates in Windows-MY:");
            // printCertificates(ksMy);

            // Load the Windows-ROOT keystore
            ksRoot = KeyStore.getInstance("Windows-ROOT");
            ksRoot.load(null, null);
            System.out.println("Certificates in Windows-ROOT:");
            // printCertificates(ksRoot);

            certNames = new ArrayList<String>();
            certList = new ArrayList<Certificate>();
            Enumeration<String> aliases = ksRoot.aliases();
            Enumeration<String> aliases2 = ksMy.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate cert = ksMy.getCertificate(alias);
                certList.add(cert);
                certNames.add(alias);
            }
            while (aliases2.hasMoreElements()) {
                String alias = aliases2.nextElement();
                Certificate cert = ksMy.getCertificate(alias);
                certList.add(cert);
                certNames.add(alias);
            }

            // criar um certstore à partir da lista de certificados.
            CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList));

            // Apenas certificados válidos - não expirados
            X509CertSelector selector = new X509CertSelector();
            selector.setCertificateValid(new Date()); // o selector vai agir como um filtro

            // Retorna apenas os certificados válidos
            Collection<? extends Certificate> certs = certStore.getCertificates(selector);

            // Certificate[] certList = certObj.toArray(new Certificate[]);
            Certificate[] f_certList = certs.toArray(new Certificate[0]); // (Certificate[]) certs.toArray();
            // signer.setCertificates(f_certList);
            List<String> fileteredNames = new ArrayList<>();

            String[] arrKeys = certNames.toArray(new String[0]);
            Certificate[] arrValues = certList.toArray(new Certificate[0]);
            for (int i = 0; i < arrKeys.length; i++) {

                for (Certificate cert_ : f_certList) {
                    if (arrValues[i] == cert_)
                        fileteredNames.add(arrKeys[i]);
                }

            }
            certNames.clear();
            certList.clear();
            certNames = fileteredNames;
            certList = Arrays.asList(f_certList);
            notifyObservers(_LOAD_CERT_DONE_OK);

        } catch (Exception e) {
            notifyObservers(_LOAD_CERT_DONE_FAIL);
            e.printStackTrace();
        }
    }

    private static void printCertificates(KeyStore keystore) throws Exception {
        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Certificate cert = keystore.getCertificate(alias);
            System.out.println("Alias: " + alias);
            System.out.println(cert);
        }
    }

}
