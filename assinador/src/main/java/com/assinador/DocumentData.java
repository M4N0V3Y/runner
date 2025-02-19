package com.assinador;

import java.io.ByteArrayInputStream;

public class DocumentData {

    private byte[] pdf;
    private String idDoc;

    // Constructor
    public DocumentData(byte[] pdf, String idDoc) {
        this.pdf = pdf;
        this.idDoc = idDoc;
    }

    // Getters (Important!)
    public byte[] getPdf() {
        return pdf;
    }

    public String getIdDoc() {
        return idDoc;
    }

    // Setters (If you need to modify the data later)
    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    // Optional: Override toString() for easy debugging/printing
    @Override
    public String toString() {
        return "DocumentData{" +
                "idDoc=" + idDoc +
                ", pdf.length=" + (pdf == null ? 0 : pdf.length) + // Show length for byte array
                '}';
    }

    /*
     * // Optional: Override equals() and hashCode() if you need to compare
     * // DocumentData objects
     * 
     * @Override
     * public boolean equals(Object obj) {
     * if (this == obj)
     * return true;
     * if (obj == null || getClass() != obj.getClass())
     * return false;
     * DocumentData that = (DocumentData) obj;
     * return idDoc == that.idDoc && java.util.Arrays.equals(pdf, that.pdf);
     * }
     * 
     * @Override
     * public int hashCode() {
     * int result = Integer.parseInt(idDoc);
     * result = 31 * result + java.util.Arrays.hashCode(pdf);
     * return result;
     * }
     */

}