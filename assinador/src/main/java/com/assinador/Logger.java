package com.assinador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// [DCR]
// classe  de Logs de mensagens do aplicativo
public class Logger {
    //
    private static Logger instance;
    private BufferedWriter writer;
    private boolean isAtivo;
    private static final String LOG_DIR = "C:\\temp\\";
    private static final String LOG_FILE_PATTERN = "assina_certificado_";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");

    private Logger() {
        try {
            String timestamp = DATE_FORMAT.format(new Date()).replace(":", "");
            String logFileName = LOG_DIR + LOG_FILE_PATTERN + timestamp + ".log";
            writer = new BufferedWriter(new FileWriter(logFileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public synchronized void log(String message) {
        try {
            writer.write(new Date() + " - " + message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ativar ou desativar o Logger
     * 
     * @param status
     */
    public void Ativar(boolean status) {
        // [ DCR ]
        this.isAtivo = status;
    }

    /**
     * Verifica se o Log está ativo e se as mensagens serão exibidas no console da
     * aplicação
     * 
     * @return
     */
    public boolean isAtivo() {
        // [ DCR ]
        return this.isAtivo;
    }
}
