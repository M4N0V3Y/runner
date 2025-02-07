package com.assinador;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AssinaCertificadoAPIClienteB {

    private assinacertificado _observer;
    private List<BackEndObserver> observers;
    private String status;

    private static final String WSDL_URL = "http://wcfassinanetsuporte.assina.net.br/Arquivo.svc?wsdl";
    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "Arquivo");
    private Arquivo arquivo;

    /**
     * 
     * @param observer
     */
    private void addObserver(BackEndObserver observer) {
        observers.add(observer);
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

    public AssinaCertificadoAPIClienteB(assinacertificado observer) throws Exception {
        observers = new ArrayList<>();
        addObserver(observer);
        _observer = observer;
        try {

            arquivo = new Arquivo();

            notifyObservers(
                    "AssinaCertificadoAPIClienteB::AssinaCertificadoAPIClienteB - [INFO ⚠] Servidor:  "
                            + WSDL_URL +
                            "  nome do serviço " + SERVICE_NAME + ". Inicializa cliente da API.");

        } catch (Exception ex) {

            notifyObservers(
                    "AssinaCertificadoAPIClienteB::AssinaCertificadoAPIClienteB - [EXCEÇÃO ] Erro ao iniciar o serviço: Servidor:  "
                            + WSDL_URL +
                            "  nome do serviço " + SERVICE_NAME + ". Inicializa cliente da API.");
            notifyObservers(ex.getMessage());

        }

    }

    /**
     * 
     * @param codResponsavel
     * @param documentoBin
     * @param chave
     * @return
     */
    public String callSetDocumentoService(Integer codResponsavel, byte[] documentoBin, String chave) {
        notifyObservers(
                "AssinaCertificadoAPIClienteB::callSetDocumentoService - [INFO ⚠] Enviando PDF  para  " + WSDL_URL +
                        "  nome do serviço " + SERVICE_NAME + ". Id do documento" + codResponsavel +
                        " chave: " + chave);

        //
        String response = arquivo.getIArquivo().setDocumento(codResponsavel, documentoBin, chave);

        return response; // arquivo.setDocumento(codResponsavel, documentoBin, chave);
    }

    /**
     * 
     * @param codResponsavel
     * @param chave
     * @return
     */
    public byte[] callGetDocumentoService(Integer codResponsavel, String chave) {
        notifyObservers(
                "AssinaCertificadoAPIClienteB::callGetDocumentoService - [Info ⚠ ] Buscando PDF  do " + WSDL_URL +
                        "  nome do serviço " + SERVICE_NAME + ". Id do documento" + codResponsavel +
                        " chave: " + chave);

        //
        byte[] response = arquivo.getIArquivo().getDocumento(codResponsavel, chave); // getDocumento(request);
        return response; // arquivo.getDocumento(codResponsavel, chave);
    }

    /**
     * 
     * @param codResponsavel
     * @param chave
     * @return
     */
    public String callGetDocumentoAssinarService(String senha, Integer codResponsavel, String chave) {

        notifyObservers(
                "AssinaCertificadoAPIClienteB::callGetDocumentoAssinarService - [Info ⚠ ] Buscando PDF  do " + WSDL_URL
                        +
                        "  nome do serviço " + SERVICE_NAME + ". Id do documento" + codResponsavel +
                        " chave: " + chave + "com senha");

        //
        String response = arquivo.getIArquivo().getDocumentoAssinar(senha, codResponsavel, chave); // getDocumento(request);

        notifyObservers(
                "AssinaCertificadoAPIClienteB::callGetDocumentoAssinarService - [Info ⚠ ] getDocumentoAssinar retornou a seguinte resposta: [  "
                        +
                        response + " ].");

        return response; // arquivo.getDocumento(codResponsavel, chave);
    }

}
