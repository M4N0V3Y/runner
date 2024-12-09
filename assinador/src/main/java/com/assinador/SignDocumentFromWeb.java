package com.assinador;

import java.util.ArrayList;
import java.util.List;

import com.RunnerUtils;

public class SignDocumentFromWeb {
    // address parameters in the argument string
    private static int _DOC_OPERATION = 0; // Parameter 1
    private static int _WORK_WEB_ADDR = 1; // Parameter 2
    private static int _DOC_GET_ACTION = 2; // Parameter 3
    private static int _SRV_DATE_TIME = 3; // Parameter 4
    private static int _AN_AZ_FLAG = 4; // Parameter 5
    private static int _APP_VERSION = 5; // Parameter 6
    private static int _URL_API_CPP = 6; // Parameter 7
    private static int _A_N_S_FLAG = 7; // Parameter 8
    private static int _CPF_CNPJ = 8; // Parameter 9
    private static int _OPT_SHOWONLY_CERT = 9; // Parameter 10

    //
    private static String _PREP_ASSINA_ACCESS_KEY = "ROTINA ASSINATURA:: [INFO ⚠] - Preparando para assinar ocumentos(s) com a chave de acesso [ %s ]";

    // Action selection
    private static int _INSTALL_TEST = 0;
    private static int _SIGN_4_TEST = 1; // o número 12768392 é um codigo aleatório e não é fixo
                                         // esta é a ChaveAcessoJava

    public static final String[] _signOperation = { "TesteInstalacao",
            "AssinaturaTeste" };

    private static List<BackEndObserver> observers;
    private static String status;

    /**
     * 
     * @param observer
     */
    public static void addObserver(BackEndObserver observer) {
        observers.add(observer);
    }

    /**
     * 
     * @param observer
     */
    public void removeObserver(BackEndObserver observer) {
        observers.remove(observer);
    }

    /**
     * 
     */
    private static void notifyObservers(String newStatus) {
        if (observers.size() > 1) {
            for (BackEndObserver observer : observers) {
                status = newStatus;
                observer.update(status);
            }
        } else {

        }

    }

    /**
     * 
     * @param concatParams
     */
    public SignDocumentFromWeb(assinacertificado observer, String concatParams) {
        observers = new ArrayList<>();
        observers.add(observer);

        String[] parameters = concatParams.split("#");
        /*
         * Request DOC for siggn
         */
        String firstParam = parameters[_DOC_OPERATION];

        String[] signOperation = firstParam.split(":");

        if (signOperation[1] == _signOperation[_INSTALL_TEST]) {

            installTest(parameters);
        } else if (signOperation[1] == _signOperation[_SIGN_4_TEST]) {

            sign4Test(parameters);
        } else {

            if (RunnerUtils.isNumeric(signOperation[1]) == true) {
                String _accessKey = signOperation[1];
                requestDocs4Sign(parameters, _accessKey);
            } else {
                throw new UnsupportedOperationException("Chave de acesso inválida " + signOperation[1]);

            }

        }

    }

    /**
     * 
     * @param parameters
     */
    private static void requestDocs4Sign(String[] parameters, String _accessKey) {
        //
        // notifyObservers("ROTINA ASSINATURA:: [INFO ⚠] - Preparando para assinar
        // ocumentos(s) com a chave de acesso [ "
        // + _accessKey + " ]");

        notifyObservers(String.format(_PREP_ASSINA_ACCESS_KEY, _accessKey));

        boolean allMandatory = false;
        try {
            String accessK = _accessKey; // parameters[_DOC_OPERATION];
            String workWebAddr = parameters[_WORK_WEB_ADDR];
            String docGetAction = parameters[_DOC_GET_ACTION];
            String dateTimeFromServer = parameters[_SRV_DATE_TIME];
            String compressedFlag = parameters[_AN_AZ_FLAG]; // por enquanto tratar só normal - ignorar flag Z
            String appVersion = parameters[_APP_VERSION];
            String urlAPI = parameters[_URL_API_CPP];
            allMandatory = true;
            String updateCheckNoCheck = parameters[_A_N_S_FLAG];
            String _signerID = parameters[_CPF_CNPJ];
            String optShowOnlyCert = parameters[_OPT_SHOWONLY_CERT];

            //
            notifyObservers("ROTINA ASSINATURA:: [SUCESSO ✅] - Documentos(s) assinados com a chave de acesso [ "
                    + _accessKey + " ]");

        } catch (Exception ex) {
            notifyObservers(
                    "ROTINA ASSINATURA:: [ERRO ❌] - Ocorreu um erro ao tentar assinar documentos(s) com a chave de acesso [ "
                            + _accessKey + " ]");
            if (allMandatory == false)
                throw ex;
        }

    }

    /**
     * 
     * @param parameters
     */
    private static void sign4Test(String[] parameters) {

        throw new UnsupportedOperationException("Unimplemented method 'sign4Test'");
    }

    /**
     * 
     * @param parameters
     */
    private static void installTest(String[] parameters) {

        throw new UnsupportedOperationException("Unimplemented method 'installTest'");
    }

}
