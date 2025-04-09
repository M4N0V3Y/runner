package com.assinador;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAttributeTableGenerationException;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

// [ DCR ]
//  classe para contruir o vetor customizado de atributos da assinatura 
//      *** candidata a ser usada para assinatura 
//      ***     ou candidata a ser depreciada e removida do projeto
//      - precisa ser avaliada para ver se será utlizada
//      - não está em uso na presente versão
//      - esta classe foi construída para implementar o padrão PADES - ICP-Brasil na assinatura    
/**
 * 
 
 */
public final class CustomAttributeTableGenerator implements CMSAttributeTableGenerator {

        private ASN1EncodableVector attributes;

        private static final byte[] bytepolicy = {
                        0x30, 0x3b, 0x06, 0x08, 0x60, 0x4c, 0x01, 0x07, 0x01, 0x01, 0x02, 0x02, 0x30, 0x2f, 0x30,
                        0x0b, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x04, 0x20, 0x0f,
                        0x6f,
                        (byte) 0xa2, (byte) 0xc6, 0x28, 0x19, (byte) 0x81, 0x71, 0x6c, (byte) 0x95, (byte) 0xc7,
                        (byte) 0x98,
                        (byte) 0x99, 0x03, (byte) 0x98, 0x44, 0x52, 0x3b, 0x1c, 0x61, (byte) 0xc2, (byte) 0xc9, 0x62,
                        0x28,
                        (byte) 0x9c, (byte) 0xda, (byte) 0xc7, (byte) 0x81, 0x1f, (byte) 0xee, (byte) 0xe2,
                        (byte) 0x9e };

        public CustomAttributeTableGenerator(X509Certificate certificate, byte[] dataToSign) throws Exception {
                this.attributes = createSignatureAttributesEx(certificate, dataToSign);
        }

        public AttributeTable getAttributesEx() {
                return new AttributeTable(attributes);
        }

        /**
         * This method should meet your requirements and fit within your Maven project
         * constraints. Here are the OIDs used:
         * 
         * ContentType: 1.2.840.113549.1.9.3
         * 
         * Signing Time: 1.2.840.113549.1.9.5
         * 
         * Message Digest: 1.2.840.113549.1.9.4
         * 
         * PAdES Policy
         * 
         * 1.2.840.113549.1.9.16.2.15: OID for the "Signature Policy Identifier"
         * attribute.
         * 
         * 2.16.76.1.7.1.1.2.3 OID for the specific ICP-Brasil PAdES policy.
         * 
         * Signing Certificate V2: 1.2.840.113549.1.9.16.2.47
         * 
         * Content Description (example): 1.2.840.113549.1.9.16.2.12
         * 
         * 
         */

        private ASN1EncodableVector createSignatureAttributesEx(X509Certificate certificate, byte[] dataToSign)
                        throws Exception {
                ASN1EncodableVector attributeVector = new ASN1EncodableVector();

                // 1) Attribute ContentType
                attributeVector.add(new Attribute(
                                PKCSObjectIdentifiers.pkcs_9_at_contentType,
                                new DERSet(PKCSObjectIdentifiers.data)));

                // 3) Attribute Message Digest
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256",
                                (Provider) new BouncyCastleProvider());
                byte[] messageDigestBytes = messageDigest.digest(dataToSign);
                attributeVector.add(new Attribute(
                                PKCSObjectIdentifiers.pkcs_9_at_messageDigest,
                                new DERSet(new DEROctetString(messageDigestBytes))));

                // 2) Attribute Signing Time
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", Locale.getDefault());
                String signingTime = dateFormat.format(new Date());
                attributeVector.add(new Attribute(
                                PKCSObjectIdentifiers.pkcs_9_at_signingTime,
                                new DERSet(new DERUTCTime(signingTime))));

                // 4) Attribute PAdES Policy for ICP-Brasil (Corrected)
                ASN1EncodableVector policyVector = new ASN1EncodableVector();

                // Add the policy hash
                policyVector.add(new DEROctetString(bytepolicy));

                // Add the policy OID
                policyVector.add(new ASN1ObjectIdentifier("2.16.76.1.7.1.1.1")); // ICP-Brasil PAdES policy OID
                                                                                 // 2.16.76.1.7.1

                // Add the hash algorithm
                AlgorithmIdentifier hashAlgorithm = new AlgorithmIdentifier(
                                new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1")); // SHA-256 OID
                policyVector.add(hashAlgorithm);

                // Add the policy hash again (as per the ICP-Brasil PAdES profile)
                // policyVector.add(new DEROctetString(bytepolicy));

                // 5. Policy OID (specific)
                policyVector.add(new ASN1ObjectIdentifier("2.16.76.1.7.1.1.2.1")); // 2.16.76.1.7.1.1.2.3

                // Add the policy hash again (as per the ICP-Brasil PAdES profile)
                policyVector.add(new DEROctetString(bytepolicy));
                // Add the hash algorithm
                // AlgorithmIdentifier hashAlgorithm = new AlgorithmIdentifier(
                // new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1")); // SHA-256 OID
                policyVector.add(hashAlgorithm);
                // Add the policy description (optional, but recommended)
                policyVector.add(new DERUTF8String("ICP-Brasil PAdES Policy Description"));

                AlgorithmIdentifier algId = new AlgorithmIdentifier(new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1")); // SHA-256
                byte[] certHash = MessageDigest.getInstance("SHA-256").digest(certificate.getEncoded());
                ESSCertIDv2 essCertIDv2 = new ESSCertIDv2(algId, certHash);
                SigningCertificateV2 signingCertificateV2 = new SigningCertificateV2(essCertIDv2);
                // 4) PaDES
                attributeVector.add(new Attribute(
                                new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.15"),
                                // new DERSet(new DEROctetString(bytepolicy)) ));
                                new DERSet(new DERSequence(policyVector))));

                // 5) Attribute Signing Certificate V2
                attributeVector.add(new Attribute(
                                new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.47"),
                                new DERSet(signingCertificateV2)));

                // 6) Attribute Document Description (optional)
                String documentDescription = "Example Document Description";
                attributeVector.add(new Attribute(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.12"),
                                new DERSet(new DERUTF8String(documentDescription))));

                return attributeVector;
        }

        @Override
        public AttributeTable getAttributes(Map arg0) throws CMSAttributeTableGenerationException {

                return new AttributeTable(attributes);
        }

}