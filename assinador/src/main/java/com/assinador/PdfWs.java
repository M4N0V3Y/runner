package com.assinador;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class PdfWs {

    private final String servidor;
    private final String chave;
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

    public PdfWs(assinacertificado observer, final String servidor, final String chave) {
        this.servidor = servidor;
        this.chave = chave;
        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;
    }

    public byte[] getPdfBytes(final String id) {
        try {

            notifyObservers("PdfWs::getPdfBytes - WEBSERVICE:: [INFO ⚠] buscar arquivo  " + id + "  no servidoe.");
            String answer = request(getPdfRequest(id), "GetDocumento");

            return DatatypeConverter.parseBase64Binary(answer.replaceAll(".*?<GetDocumentoResult>([^<]+)<.*", "$1"));
        } catch (Exception e) {
            notifyObservers("PdfWs::getPdfBytes - WEBSERVICE:: [EXCEÇÃO] não conseguiu buscar o  arquivo  " + id
                    + "  no servidoe.");
            throw new RuntimeException("getPdfBytes:" + e);
        }
    }

    private String request(final String soapRequest, final String service)
            throws MalformedURLException, IOException, ProtocolException {
        try {
            URL obj = new URL(servidor);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [INFO ⚠]  Início.");

            // con.setConnectTimeout(5000); //set timeout to 5 seconds
            // deixar 30 segundos. n�o vai demorar muito se houver erro de transmiss�o nesse
            // documento.
            con.setConnectTimeout(30000);

            con.setReadTimeout(30000);

            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("charset", "utf-8");

            con.setRequestProperty("SOAPAction", "http://tempuri.org/IArquivo/" + service);

            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(soapRequest);
            wr.flush();
            wr.close();

            Scanner scanner = new Scanner(con.getInputStream());
            String answer = scanner.useDelimiter("$$").next();
            scanner.close();
            con.disconnect();
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [INFO ⚠]  Arquivo lido do servidor.");
            return answer;
        } catch (ProtocolException e) {
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [ERRO DE PROTOCOLO] Erro no protocolo: ");
            throw new RuntimeException("Erro de protocolo: " + e);
        } catch (MalformedURLException e) {
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [ERRO DE URL] URL malformada: ");
            throw new RuntimeException("URL malformada: " + e);
        } catch (IOException e) {
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [ERRO DE I/O] Falha na comunicação: ");
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [EXCEÇÃO X]  Arquivo não foi  lido do servidor.");
            throw new RuntimeException("Erro de I/O: " + e);
        } catch (Exception e) {
            notifyObservers("PdfWs::request - WEBSERVICE::REQUEST [EXCEÇÃO X]  Arquivo não foi  lido do servidor.");
            throw new RuntimeException("request: " + e);
        }
    }

    private String getPdfRequest(final String id) {
        try {

            notifyObservers("PdfWs::getPdfRequest - WEBSERVICE:: [INFO ⚠] buscar arquivo  " + id
                    + "- montando o comando de requisição ao servidor.");

            return "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"><Body>"
                    + "<GetDocumento xmlns=\"http://tempuri.org/\">"
                    + "<CODASSINADOCUMENTORESPONSAVEL>" + id + "</CODASSINADOCUMENTORESPONSAVEL>"
                    + "<CHAVE>" + chave + "</CHAVE>" + "</GetDocumento></Body></Envelope>";
        } catch (Exception e) {
            throw new RuntimeException("getPdfRequest: " + e);
        }
    }

    private String setPdfRequest(final String id, final byte[] pdf) {
        try {
            return "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\"><Body><SetDocumento xmlns=\"http://tempuri.org/\">"
                    + "<CODASSINADOCUMENTORESPONSAVEL>" + id + "</CODASSINADOCUMENTORESPONSAVEL>"
                    + "<DOCUMENTOBIN>" + DatatypeConverter.printBase64Binary(pdf) + "</DOCUMENTOBIN>"
                    + "<CHAVE>" + chave + "</CHAVE></SetDocumento></Body></Envelope>";
        } catch (Exception e) {
            throw new RuntimeException("setPdfRequest: " + e);
        }
    }

    public String sendSignedPdf(final String id, final byte[] signedPdf) {
        try {

            notifyObservers("PdfWs::sendSignedPdf - WEBSERVICE:: [INFO ⚠] Eviar arquivo  " + id
                    + "  assinado para o servidoe.");

            String answer = request(setPdfRequest(id, signedPdf), "SetDocumento");

            return DatatypeConverter.parseString(answer.replaceAll(".*?<SetDocumentoResult>([^<]+)<.*", "$1"));
        } catch (Exception e) {
            notifyObservers("PdfWs::sendSignedPdf - WEBSERVICE:: [EXCEÇÃO ❌ ] Erro ao enviar arquivo  " + id
                    + "  assinado para o servidoe.");
            throw new RuntimeException("sendSignedPdf: " + e);
        }
    }

}
