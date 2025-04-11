package com.assinador;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class LoadCertificates {

    private static List<BackEndObserver> observers = new ArrayList<>();
    private static KeyStore ksPersonal = null;
    private static KeyStore ksTrusted = null;
    private static List<String> certNames = new ArrayList<>();
    private static List<Certificate> certList = new ArrayList<>();
    private static String status;

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String WINDOWS = "win";
    private static final String LINUX = "linux";

    private static final String _LOAD_CERT_PK = "CARREGA CERTIFICADOS:: [INFO ⚠] - Obteve chave primária do certificado";
    private static final String _LOAD_CERT_OK = "CARREGA CERTIFICADOS:: [INFO ✅] - Obteve os certificados com sucesso";
    private static final String _LOAD_CERT_INIT = "CARREGA CERTIFICADOS:: [INFO ⚠] - Iniciando o processo de carregamento dos Cetificados";
    private static final String _LOAD_CERT_DONE_OK = "CARREGA CERTIFICADOS:: [INFO ✅] - Cetificados carregados";
    private static final String _LOAD_CERT_DONE_FAIL = "CARREGA CERTIFICADO:: [INFO X] - Falha ao carregar os certificados";
    private static final String _LOAD_CERT_PK_ERR = "CARREGA CERTIFICADOS:: [ERRO X] - Falha ao obter a  chave primária do certificado";
    private static final String _LOAD_CERT_ERR = "CARREGA CERTIFICADOS:: [ERRO X] - Falha ao obter os certificados";

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
     * @param newStatus
     */
    private static void notifyObservers(String newStatus) {
        for (BackEndObserver observer : observers) {
            status = newStatus;
            observer.update(status);
        }
    }

    /**
     * 
     * @param observer
     */
    LoadCertificates(assinacertificado observer) {
        observers.add(observer);
        load();
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
            thisKelclKey = (PrivateKey) ksPersonal.getKey(alias, pswString);
            if (thisKelclKey == null)
                throw new KeyStoreException();
            notifyObservers(_LOAD_CERT_PK);
            return thisKelclKey;
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {

            try {
                thisKelclKey = (PrivateKey) ksTrusted.getKey(alias, pswString);
                return thisKelclKey;
            } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e2) {

                e.printStackTrace();
            }

            e.printStackTrace();
        }
        notifyObservers(_LOAD_CERT_PK_ERR);
        return null;

    }

    /**
     * 
     * @param alias
     * @return
     */
    public static Certificate[] getCertificateChain(String alias) {
        Certificate[] certificateChain = null;
        try {
            if (ksPersonal != null && ksPersonal.containsAlias(alias)) {
                certificateChain = ksPersonal.getCertificateChain(alias);
            } else if (ksTrusted != null && ksTrusted.containsAlias(alias)) {
                certificateChain = ksTrusted.getCertificateChain(alias);
            }
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
            notifyObservers(_LOAD_CERT_ERR);
            return null;
        }
        notifyObservers(_LOAD_CERT_OK);
        return certificateChain;
    }

    /**
     * 
     */
    public static void load() {
        notifyObservers(_LOAD_CERT_INIT);
        try {
            if (OS_NAME.contains(WINDOWS)) {
                loadWindowsCertificates();
            } else if (OS_NAME.contains(LINUX)) {
                loadLinuxCertificates();
            } else {
                notifyObservers(
                        "CARREGA CERTIFICADOS:: [INFO ⚠] - Sistema operacional não reconhecido. Carregamento padrão (apenas certificados confiáveis).");
                loadTrustedCertificates();
            }
            notifyObservers(_LOAD_CERT_DONE_OK);
        } catch (Exception e) {
            notifyObservers(_LOAD_CERT_DONE_FAIL);
            e.printStackTrace();
        }
    }

    /**
     * 
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void loadWindowsCertificates() throws KeyStoreException, NoSuchAlgorithmException, IOException {
        ksPersonal = KeyStore.getInstance("Windows-MY");
        try {
            ksPersonal.load(null, null);
            System.out.println("Certificates in Windows-MY:");
            populateCertificateLists(ksPersonal);

            ksTrusted = KeyStore.getInstance("Windows-ROOT");
            ksTrusted.load(null, null);
            System.out.println("Certificates in Windows-ROOT:");
            populateCertificateLists(ksTrusted);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        } catch (CertificateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    /**
     * 
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void loadLinuxCertificates() throws KeyStoreException, NoSuchAlgorithmException, IOException {
        // Focus on loading trusted certificates available without passwords
        ksTrusted = KeyStore.getInstance("JKS"); // Default Java truststore type
        try {
            ksTrusted.load(null, null); // Try loading the default truststore
            System.out.println("Loaded default Java truststore on Linux.");
            populateCertificateLists(ksTrusted);
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException e) {
            System.err.println("Error loading default Java truststore on Linux: " + e.getMessage());
            // Consider other standard locations for CA certificates if needed
            // e.g., /etc/ssl/certs/ (may require parsing and conversion)
        }

        // For personal certificates, without password access, the application
        // might need to rely on certificates already loaded by the system
        // (e.g., in the browser's trust store) or require the user to import
        // a password-less certificate into a specific location/format.
        System.out.println(
                "On Linux, loading personal certificates without passwords requires platform-specific solutions or user-provided files.");
        // Further implementation for Linux personal certificates would depend on
        // the specific requirements and how the A1 certificate is intended to be used.
    }

    /**
     * 
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private static void loadTrustedCertificates() throws KeyStoreException, NoSuchAlgorithmException, IOException {
        ksTrusted = KeyStore.getInstance("JKS");
        try {
            ksTrusted.load(null, null);
            System.out.println("Loaded default Java truststore.");
            populateCertificateLists(ksTrusted);
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException e) {
            System.err.println("Error loading default Java truststore: " + e.getMessage());
        }
    }

    /**
     * 
     * @param keystore
     * @throws KeyStoreException
     */
    private static void populateCertificateLists(KeyStore keystore) throws KeyStoreException {
        if (keystore != null) {
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate cert = keystore.getCertificate(alias);
                if (cert != null && isCertificateValid(cert)) { // Add validity check here
                    certList.add(cert);
                    certNames.add(alias);
                }
            }
        }
    }

    /**
     * 
     * @param cert
     * @return
     */
    private static boolean isCertificateValid(Certificate cert) {
        if (cert instanceof java.security.cert.X509Certificate) {
            try {
                ((java.security.cert.X509Certificate) cert).checkValidity(new Date());
                return true;
            } catch (java.security.cert.CertificateExpiredException
                    | java.security.cert.CertificateNotYetValidException e) {
                return false;
            }
        }
        return true; // Assume other certificate types are valid for simplicity
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public static List<Certificate> getCertificates() throws Exception {

        return certList;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public static List<String> getCertificateNames() throws Exception {

        return certNames;
    }

    /**
     * @param keystore
     * @throws Exception
     */
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