package com.assinador;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

// [DCR]
// classe responsável por verificar se os bytes do arquivo lido correspondem a 
// um arquivo PDF ou um arquivo P7S
public class PdfP7SChecker {

    public static FileType checkFileType(byte[] fileBytes) {
        if (isPDF(fileBytes)) {
            return FileType.PDF;
        } else if (isP7S(fileBytes)) {
            return FileType.P7S;
        } else {
            return FileType.UNKNOWN;
        }
    }

    public enum FileType {
        PDF,
        P7S,
        UNKNOWN
    }

    // [DCR]
    // verifica se os bytes lidos correspondem a um PDF
    public static boolean isPDF(byte[] inputFBytes) {

        ByteArrayInputStream fileBytes = new ByteArrayInputStream(inputFBytes);
        // fileBytes.mark(0); // Mark the beginning
        // fileBytes.reset();
        try {
            CMSSignedData cmsData = new CMSSignedData(fileBytes);
            return false; // It's a P7S file
        } catch (Exception e) {
            // Valid PDF
            try (RandomAccessBuffer rab = new RandomAccessBuffer(fileBytes)) {
                // PDFBox's PDFParser is quite lenient. It will often parse even slightly
                // corrupted PDFs.
                // For more strict checking, you might need to look at the PDF's internal
                // structure.

                //
                PDFParser parser = new PDFParser(rab);
                parser.parse();

                PDDocument document = parser.getPDDocument();

                document.save("C:\\temp\\lido_do_servidor.pdf");
                document.close(); // Important: Close the document to release resources
                return true; // If no exception, it's likely a PDF
            } catch (Exception ex) {
                return false; // Not a valid PDF
            }

        }

    }

    // [DCR]
    // verifica se os bytes lidos correspondem a um arquivo P7S
    public static boolean isP7S(byte[] inputFBytes) {

        ByteArrayInputStream fileBytes = new ByteArrayInputStream(inputFBytes);
        try {
            CMSSignedData cmsData = new CMSSignedData(fileBytes);
            try (FileOutputStream fos = new FileOutputStream("C:\\temp\\lido_do_servidor.p7s")) { // File path
                fos.write(inputFBytes); // Write the entire byte array to the file

            } catch (IOException e) {

                e.printStackTrace(); // Or your logging mechanism

            }

            return true; // fileBytes[0] == 0x30; // Check for ASN.1 sequence header

        } catch (CMSException e) {

            e.printStackTrace();
            return false;
        }

    }

}