package com.assinador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// [DCR]
// classe  de Logs de mensagens do aplicativo
public class Logger {
    private static Logger instance;
    private BufferedWriter writer;
    private BufferedWriter mem_writer;
    private boolean isAtivo;
    private static final String LOG_DIR;
    private static final String LOG_FILE_PATTERN = "assina_certificado_";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HH:mm:ss");
    private StringWriter memoryWriter;
    private List<String> memoryLogs;

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
            LOG_DIR = tempDir + File.separator + "assina_certificado_logs" + File.separator;
            File dir = new File(LOG_DIR);
            if (!dir.exists()) {
                dir.mkdirs(); // Create directory if it doesn't exist
            }
        }
    }

    // [ DCR ]
    // ativo = true - modo DEBUG
    // quando ativo = true -- logs serão gravados no arquivo e na memória
    // quando ativo = false -- logs serão gravados somente na memória
    private Logger(boolean ativo) {
        this.isAtivo = ativo;
        if (isAtivo) {
            try {
                String timestamp = DATE_FORMAT.format(new Date()).replace(":", "");
                String logFileName = LOG_DIR + LOG_FILE_PATTERN + timestamp + ".log";
                writer = new BufferedWriter(new FileWriter(logFileName, true));
                // [ DCR ]
                memoryWriter = new StringWriter();
                mem_writer = new BufferedWriter(memoryWriter);
                memoryLogs = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            memoryWriter = new StringWriter();
            mem_writer = new BufferedWriter(memoryWriter);
            memoryLogs = new ArrayList<>();
        }
    }

    public static Logger getInstance(boolean ativo) {
        if (instance == null || instance.isAtivo() != ativo) {
            synchronized (Logger.class) {
                if (instance == null || instance.isAtivo() != ativo) {
                    if (instance != null) {
                        instance.close(); // Close any existing writer
                        instance = null;
                    }
                    instance = new Logger(ativo);
                }
            }
        }
        return instance;
    }

    public synchronized void log(String message) {
        try {

            if (!isAtivo) {
                memoryLogs.add(new Date() + " - " + message);
            } else {
                writer.write(new Date() + " - " + message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
                writer = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ativar ou desativar o Logger (after instance creation - consider using
     * getInstance(boolean))
     *
     * @param status
     */
    // [DCR]
    @Deprecated
    public void Ativar(boolean status) {
        this.isAtivo = status;
    }

    /**
     * Verifica se o Log está ativo (salvando em arquivo).
     *
     * @return true se estiver salvando em arquivo, false se em memória.
     */
    public boolean isAtivo() {
        return this.isAtivo;
    }

    /**
     * Obtém os logs armazenados em memória (somente se isAtivo() for false).
     *
     * @return Lista de strings contendo os logs em memória, ou null se estiver
     *         salvando em arquivo.
     */
    public List<String> getMemoryLogs() {
        if (!isAtivo) {
            return new ArrayList<>(memoryLogs);
        }
        return null;
    }
}