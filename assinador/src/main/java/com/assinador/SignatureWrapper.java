package com.assinador;

import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.demoiselle.signer.policy.engine.factory.PolicyFactory;
import org.demoiselle.signer.policy.engine.factory.PolicyFactory.Policies;
import org.demoiselle.signer.policy.impl.cades.SignerAlgorithmEnum;
import org.demoiselle.signer.policy.impl.cades.factory.PKCS7Factory;
import org.demoiselle.signer.policy.impl.cades.pkcs7.PKCS7Signer;

public class SignatureWrapper {

    public void SignnWithPolicy(Certificate[] certificateChain, PrivateKey prvKey, String fullFilePath)
            throws Exception {

        PKCS7Signer signer = PKCS7Factory.getInstance().factoryDefault();

        try {

            signer.setCertificates(certificateChain);
            signer.setPrivateKey(prvKey);
            signer.setAlgorithm(SignerAlgorithmEnum.SHA256withRSA);

            LoadFileNInfo ldFileNInfo = new LoadFileNInfo();
            ldFileNInfo.setFileInfoAndContent(fullFilePath);

            Policies SIGN_POLICY;

            if (fileIsPDF(ldFileNInfo.getFileExtention())) {
                // PAdES policy ---
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_PADES_1_1; // AD_RA_PADES_1_1 AD_RB

            } else if (fileIsXML(ldFileNInfo.getFileExtention())) {
                // XAdES policy ----
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_XADES_2_2; // AD_RA_XADES_2_3

            } else {
                // CAdES policy ---
                SIGN_POLICY = PolicyFactory.Policies.AD_RB_CADES_2_2; // ...others

            }
            signer.setSignaturePolicy(SIGN_POLICY);

            byte[] content = ldFileNInfo.getFileContent();

            byte[] signed = signer.doAttachedSign(content);

            FileOutputStream outputStream = new FileOutputStream(fullFilePath + ".p7s");
            outputStream.write(signed);

            // ByteArrayInputStream bis = new ByteArrayInputStream(signed);
            // Files.copy(bis, fullFilePath+".p7s",CopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }

    }

    private boolean fileIsXML(String fileExtention) {
        // check if XML
        return fileExtention.toUpperCase().equals("XML".toUpperCase());

    }

    private boolean fileIsPDF(String fileExtention) {
        // Check if PDF
        return fileExtention.toUpperCase().equals("PDF".toUpperCase());
    }

}
