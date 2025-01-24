/*
 * Demoiselle Framework
 * Copyright (C) 2016 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 *
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 *
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 *
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */

package com.assinador;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSigProperties;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDVisibleSignDesigner;
import org.bouncycastle.cms.CMSSignedData;
import org.demoiselle.signer.core.keystore.loader.KeyStoreLoader;
import org.demoiselle.signer.core.keystore.loader.factory.KeyStoreLoaderFactory;
import org.demoiselle.signer.cryptography.DigestAlgorithmEnum;
import org.demoiselle.signer.policy.engine.factory.PolicyFactory;
import org.demoiselle.signer.policy.impl.cades.SignerAlgorithmEnum;
import org.demoiselle.signer.policy.impl.pades.pkcs7.impl.PAdESSigner;
import org.demoiselle.signer.timestamp.configuration.TimeStampConfig;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureOptions;
//import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.KeyStore.Builder;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

//import  org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public class PDFSignerTwoStepsTest {
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

    PDFSignerTwoStepsTest(assinacertificado observer) {
        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;

    }

    /**
     * 
     * @param certificateAlias
     */
    public void testComFile(String certificateAlias) {

        // INFORMAR o arquivo de entrada
        //
        String fileDirName = "C:\\Temp\\entrada.pdf";
        PDDocument document;

        try {
            document = PDDocument.load(new File(fileDirName));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            byte[] fileToSign = Base64.encodeBase64(byteArrayOutputStream.toByteArray());
            // INFORMAR o nome do arquivo de saida assinado
            String filePDFAssinado = "C:\\Temp\\entrada.p7s";

            try {
                doSigner(certificateAlias, document, fileToSign, filePDFAssinado);
                document.close();

            } catch (Throwable e) {
                e.printStackTrace();
                notifyObservers(
                        "ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Ocorreu uma exceção ao tentar assinar o arquivo usando" +
                                "a política de assinatura : PaDES ");
                document.close();
            }
            notifyObservers(
                    "ROTINA ASSINATURA:: [INFO ⚠] -Arquivo assinado com sucesso! Política de" +
                            " assinatura : PaDES ");
        } catch (IOException e) {
            e.printStackTrace();
            notifyObservers(
                    "ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Ocorreu uma exceção ao tentar assinar o" +
                            "aequivo usando a política de assinatura : PaDES ");
        }

    }

    private void doSigner(String certificateAlias, PDDocument original, byte[] toSign, final String signedFile)
            throws Throwable {

        FileOutputStream fos = new FileOutputStream(signedFile);
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hashOriginal = md.digest(toSign);
        String hashOriginalToHex = org.bouncycastle.util.encoders.Hex.toHexString(hashOriginal);
        BigInteger bigId = new BigInteger(hashOriginalToHex.toUpperCase(), 16);
        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED); // (PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        signature.setReason("Procurador");
        signature.setLocation("Endereço IP: 192.168.0.1");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        signature.setSignDate(calendar);

        Calendar.getInstance();
        // --- PDDocument original = docOriginal; // PDDocument.load(toSign);
        original.setDocumentId(bigId.longValue());
        original.addSignature(signature, new SignatureInterface() {
            public byte[] sign(InputStream contentToSign) throws IOException {

                byte[] byteContentToSign = IOUtils.toByteArray(contentToSign); // toSign; //
                try {
                    java.security.MessageDigest md = java.security.MessageDigest
                            .getInstance(DigestAlgorithmEnum.SHA_256.getAlgorithm());
                    byte[] hashToSign = md.digest(byteContentToSign);
                    String hashToSignHex = org.bouncycastle.util.encoders.Hex.toHexString(hashToSign);
                    System.out.println("hashPDFtoSign: " + hashToSignHex);

                    notifyObservers("ROTINA ASSINATURA:: [INFO ⚠] -HASH da assinatura : " + hashToSignHex);

                    // windows e NeoID
                    KeyStore ks = getKeyStoreTokenBySigner();
                    KeyStore ksToTS = getKeyStoreTokenBySigner();
                    PAdESSigner signer = new PAdESSigner();
                    char[] pswString = null;

                    PrivateKey _prvKey = LoadCertificates.getPrivateKey(certificateAlias, pswString);
                    char[] spK = _prvKey.toString().toCharArray();
                    signer.setPrivateKey((PrivateKey) ks.getKey(certificateAlias, spK));
                    signer.setPrivateKeyForTimeStamp((PrivateKey) ksToTS.getKey(certificateAlias, null));
                    ;
                    signer.setSignaturePolicy(PolicyFactory.Policies.AD_RB_PADES_1_1);
                    signer.setAlgorithm(SignerAlgorithmEnum.SHA256withRSA.getAlgorithm());
                    signer.setCertificates(ks.getCertificateChain(certificateAlias));
                    signer.setCertificatesForTimeStamp(ksToTS.getCertificateChain(certificateAlias));

                    notifyObservers("ROTINA ASSINATURA:: [INFO ⚠] -política de assinatura : PaDES");

                    byte[] assinatura = signer.doHashSign(hashToSign);

                    notifyObservers("ROTINA ASSINATURA:: [SUCESSO ✅]  Documento assinado com sucesso, certificado: "
                            + certificateAlias);

                    return assinatura;
                } catch (Throwable error) {
                    error.printStackTrace();
                    notifyObservers(
                            "ROTINA ASSINATURA:: [EXCEÇÃO ❌] -Ocorreu uma exceção ao tentar assinar o" +
                                    "aequivo usando a política de assinatura : PaDES , certificado: "
                                    + certificateAlias);
                    return null;
                }
            }
        });
        original.saveIncremental(fos);
        original.close();
    }

    // Usa o Signer para leitura, funciona para windows e NeoID
    private KeyStore getKeyStoreTokenBySigner() {

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
     * @param toSign
     * @param signature
     * @param signedFile
     * @throws InvalidPasswordException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */

    private void addSignature(byte[] toSign, byte[] signature, final String signedFile)
            throws InvalidPasswordException, IOException, NoSuchAlgorithmException {
        final byte[] varSignature = signature;
        FileOutputStream fileOut = new FileOutputStream(signedFile);
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        byte[] hashOriginal = md.digest(toSign);
        String hashOriginalToHex = org.bouncycastle.util.encoders.Hex.toHexString(hashOriginal);
        BigInteger bigId = new BigInteger(hashOriginalToHex.toUpperCase(), 16);
        PDSignature pdfSignature = new PDSignature();
        pdfSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        pdfSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
        PDDocument original;
        original = PDDocument.load(toSign);
        original.setDocumentId(bigId.longValue());
        original.addSignature(pdfSignature);
        ExternalSigningSupport externalSigningSupport = original.saveIncrementalForExternalSigning(fileOut);
        externalSigningSupport.setSignature(signature);
        byte[] bytePdfSigned = Files.readAllBytes(Paths.get(signedFile));
        String pdfSignedEncoded = new String(Base64.encodeBase64(bytePdfSigned));
        original.close();
        fileOut.flush();
        fileOut.close();

    }

}