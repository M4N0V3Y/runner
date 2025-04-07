package com.assinador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.ExternalSigningSupport;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.util.StreamUtils;

public class PdfSignatureHelper {

    public static ByteArrayInputStream signPdfWithHiddenSignature(ByteBuffer pdfBuffer, String certAlias)
            throws Exception {
        // Obtain the KeyStore and KeyStore password (example assumes a PKCS12 file)
        // String keyStorePath = "path/to/your/keystore.p12"; // Replace with the actual
        // KeyStore path
        // String keyStorePassword = "your-keystore-password"; // Replace with your
        // KeyStore password
        KeyStore keyStore = KeyStore.getInstance("Windows-MY");
        keyStore.load(null, null);

        // Convert ByteBuffer to InputStream
        InputStream pdfInputStream = new ByteArrayInputStream(pdfBuffer.array());

        // Load the PDF document
        PDDocument document = PDDocument.load(pdfInputStream);
        PDDocumentCatalog catalog = document.getDocumentCatalog();
        PDAcroForm acroForm = catalog.getAcroForm();
        if (acroForm == null) {
            acroForm = new PDAcroForm(document);
            catalog.setAcroForm(acroForm);
        }

        // Prepare signing components
        // rivateKey privateKey = (PrivateKey) keyStore.getKey(certAlias,
        // keyStorePassword.toCharArray());

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(certAlias, null); // Get private key
        Certificate[] certChain = keyStore.getCertificateChain(certAlias);
        X509Certificate x509Cert = (X509Certificate) certChain[0];

        // Signature options
        PDSignature signature = new PDSignature();
        signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
        signature.setSubFilter(PDSignature.SUBFILTER_ETSI_CADES_DETACHED);
        signature.setName("Hidden Signature");
        signature.setSignDate(Calendar.getInstance());
        document.addSignature(signature);

        // Sign the document
        ByteArrayOutputStream signedPdfOutputStream = new ByteArrayOutputStream();
        ExternalSigningSupport externalSigning = document.saveIncrementalForExternalSigning(signedPdfOutputStream);

        try (InputStream inputStream = externalSigning.getContent();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            StreamUtils.copy(inputStream, outputStream);
            signedPdfOutputStream = signDetached(x509Cert, privateKey, certChain, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save to a P7S file
        try (FileOutputStream fos = new FileOutputStream("C:\\temp\\assinado_local_segunda_assina.p7s")) {
            fos.write(signedPdfOutputStream.toByteArray());
        }

        // Close the document
        document.close();

        // Return the signed PDF as a byte array

        return new ByteArrayInputStream(signedPdfOutputStream.toByteArray());
    }

    private static ByteArrayOutputStream signDetached(X509Certificate certificate, PrivateKey privateKey,
            Certificate[] certChain, OutputStream contentToSign)
            throws Exception {
        // BouncyCastle for CMS Signing
        List<Certificate> certList = Arrays.asList(certChain);
        JcaCertStore certStore = new JcaCertStore(certList);

        CMSSignedDataGenerator cmsGen = new CMSSignedDataGenerator();
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(privateKey);

        // Generate X509CertificateHolder for attributes
        X509CertificateHolder certHolder = new X509CertificateHolder(certificate.getEncoded());

        // Generate attributes using the CustomAttributeTableGenerator
        CustomAttributeTableGenerator attributeTableGenerator = new CustomAttributeTableGenerator(certificate,
                contentToSign.toString().getBytes());

        // Add signer with custom attributes
        cmsGen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().build())
                .setSignedAttributeGenerator(attributeTableGenerator)
                .build(contentSigner, certificate));

        cmsGen.addCertificates(certStore);

        CMSTypedData data = new CMSProcessableByteArray(contentToSign.toString().getBytes());
        CMSSignedData signedData = cmsGen.generate(data, false);

        contentToSign.write(signedData.getEncoded());

        return (ByteArrayOutputStream) contentToSign;
    }
}
