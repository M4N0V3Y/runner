package com.assinador;

import java.util.ArrayList;
import java.util.List;

// [DCR]
// classe  FACADE assinador dos documentos
public class PdfWs {

    private final String servidor;
    private final String chave;
    private List<BackEndObserver> observers;
    private String status;
    private assinacertificado _observer;
    private AssinaCertificadoAPIClienteB assinaCertificadoAPICliente;

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

        try {
            notifyObservers(
                    "PdfWs::PdfWs - Inicializa:: [Info ⚠ ]  - Iniciando o componente AssinaCertificadoAPIClienteB.");

            // [ DCR ]
            // Instancia a class Arquivo
            // ** Esta classe é a implementação da interface IArquivo
            // *** por sua vez a classe IArquivo é a implementação da WSDL
            //
            assinaCertificadoAPICliente = new AssinaCertificadoAPIClienteB(servidor, observer);
            notifyObservers(
                    "PdfWs::PdfWs - Inicializa:: [Info ✔ ]  - Componente AssinaCertificadoAPIClienteB inicializado.");
        } catch (Exception e) {
            notifyObservers(
                    "PdfWs::PdfWs - Inicializa:: [EXCEÇÃO X ]  - Ocorreu uma exceção ao tentar iniciar o componente AssinaCertificadoAPIClienteB.");

            StackTraceElement[] stacktrace = e.getStackTrace();
            notifyObservers(e.getMessage());
            for (StackTraceElement element : stacktrace) {
                notifyObservers("[EXCEÇÃO X] " + element.getFileName() +
                        " Linha " + element.getLineNumber() + " " + element.toString());
            }
            notifyObservers(" Componente :: AssinaCertificadoAPICliente não foi inicializado ");

            throw new RuntimeException("getPdfBytes:" + e);
        }
    }

    public String getDocumentoAssinar(String senha, Integer codResponsavel, String chave) {

        String retorno = "";
        try {
            notifyObservers("PdfWs::getDocumentoAssinar - WEBSERVICE:: [INFO ⚠] buscar arquivo(s) para assinar  "
                    + codResponsavel + "  no servidoe.");
            retorno = assinaCertificadoAPICliente.callGetDocumentoAssinarService(senha, codResponsavel, chave);

        } catch (Exception e) {
            notifyObservers(
                    "PdfWs::getDocumentoAssinar - WEBSERVICE:: [EXCEÇÃO] não conseguiu buscar o(s)  arquivo(s)  "
                            + codResponsavel
                            + "  no servidoe.");
            throw new RuntimeException("getDocumentoAssinar:" + e);
        }
        return retorno;
    }

    public byte[] getPdfBytes(final String id, final String key) {
        try {

            notifyObservers("PdfWs::getPdfBytes - WEBSERVICE:: [INFO ⚠] buscar arquivo  " + id + "  no servidoe.");
            byte[] result = assinaCertificadoAPICliente.callGetDocumentoService(Integer.parseInt(id),
                    key);
            return result;
            // return DatatypeConverter.printBase64Binary(result).getBytes();

        } catch (Exception e) {
            notifyObservers("PdfWs::getPdfBytes - WEBSERVICE:: [EXCEÇÃO] não conseguiu buscar o  arquivo  " + id
                    + "  no servidoe.");
            throw new RuntimeException("getPdfBytes:" + e);
        }
    }

    public String sendSignedPdf(final String id, final byte[] signedPdf, final String key) {
        try {

            notifyObservers("PdfWs::sendSignedPdf - WEBSERVICE:: [INFO ⚠] Eviar arquivo  " + id
                    + "  assinado para o servidoe.");
            return assinaCertificadoAPICliente.callSetDocumentoService(Integer.parseInt(id), signedPdf, key);

        } catch (Exception e) {
            notifyObservers("PdfWs::sendSignedPdf - WEBSERVICE:: [EXCEÇÃO X ] Erro ao enviar arquivo  " + id
                    + "  assinado para o servidoe.");
            throw new RuntimeException("sendSignedPdf: " + e);
        }
    }

}