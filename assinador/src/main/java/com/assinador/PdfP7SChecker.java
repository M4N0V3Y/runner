package com.assinador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

// [DCR]
// classe responsável por verificar se os bytes do arquivo lido correspondem a 
// um arquivo PDF ou um arquivo P7S
public class PdfP7SChecker {

    private static final String LOG_DIR;
    private static final String LOG_FILE_PATTERN = "verificado_do_servidor_";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        String tempDir = System.getProperty("java.io.tmpdir");

        if (osName.contains("win")) {
            // Running on Windows, using C:\temp\
            LOG_DIR = "C:" + File.separator + "temp" + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Running on Linux, Unix, or AIX, using /tmp/
            LOG_DIR = File.separator + "tmp" + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        } else {
            // Default to the system's temporary directory
            LOG_DIR = tempDir + File.separator + LOG_FILE_PATTERN + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        }
    }

    // [ DCR ]
    // este método foi usada nos testes e permite gravar localmente os arquivos
    // assinados
    // este método pode ser suprimido no modo de produção
    //
    private static String fullFlePath(String extensao) {
        String timestamp = DATE_FORMAT.format(new Date()).replace(":", "");
        String sFileName = LOG_DIR + LOG_FILE_PATTERN + timestamp + "." + extensao;

        return sFileName;
    }

    /**
     * 
     * @param fileBytes
     * @return
     */
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

                document.save(fullFlePath("pdf"));
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
            try (FileOutputStream fos = new FileOutputStream(fullFlePath("p7s"))) { // File path
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