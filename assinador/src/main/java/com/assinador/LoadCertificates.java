package com.assinador;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.demoiselle.signer.core.keystore.loader.KeyStoreLoader;
import org.demoiselle.signer.core.keystore.loader.implementation.MSKeyStoreLoader;
import org.demoiselle.signer.policy.impl.cades.pkcs7.impl.CAdESSigner;

public class LoadCertificates {
    private static KeyStore ksMy;
    private static KeyStore ksRoot;
    private static CAdESSigner signer;
    private static KeyStoreLoader keyStoreLoader;

    private static List<String> certNames;
    private static List<Certificate> certList;

    public static void CAdESSigner() {

        signer = new CAdESSigner();

        keyStoreLoader = new MSKeyStoreLoader();
        try {
            ksMy = KeyStore.getInstance("Windows-MY");

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        KeyStore kStore = keyStoreLoader.getKeyStore("Windows-MY");
        Enumeration<String> aliases;
        try {
            aliases = kStore.aliases();
            List<Certificate> certObj = new ArrayList<>();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate cert = ksMy.getCertificate(alias);

                certObj.add(cert);
            }
            Certificate[] certList = certObj.toArray(Certificate[]::new);
            signer.setCertificates(certList);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

    }

    public static List<Certificate> getCertificates() throws  Exception {

        return certList;
    }
    public static List<String> getCertificateNames() throws Exception {

        return certNames;
    }

    public static void load() {
        try {
            // Load the Windows-MY keystore
            ksMy = KeyStore.getInstance("Windows-MY");
            ksMy.load(null, null);
            System.out.println("Certificates in Windows-MY:");
            printCertificates(ksMy);

            // Load the Windows-ROOT keystore
            ksRoot = KeyStore.getInstance("Windows-ROOT");
            ksRoot.load(null, null);
            System.out.println("Certificates in Windows-ROOT:");
            printCertificates(ksRoot);

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


        } catch (Exception e) {
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