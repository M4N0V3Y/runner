package com.assinador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

public class LoadFileNInfo {

    private static byte[] fileContent;
    private static String filePath;
    private static String fileExtention;
    private static List<BackEndObserver> observers;
    private static String status;

    LoadFileNInfo(assinacertificado observer) {
        observers = new ArrayList<>();
        observers.add(observer);
    }

    private static void fileExtention() {
        // extract the filename from the path
        fileExtention = "";
        String filename;
        try {
            filename = filePath.substring(filePath.lastIndexOf('\\') + 1);
            // find the last occurrence of '.' in the filename
            int dotIndex = filename.lastIndexOf('.');
            fileExtention = (dotIndex > 0) ? filename.substring(dotIndex + 1) : ""; // extention

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private static void loadFile() throws IOException {

        // ...
        byte[] fc = Files.readAllBytes(Paths.get(filePath));
        String str = new String(fc, StandardCharsets.UTF_8);
        fileContent = str.getBytes(StandardCharsets.UTF_8);

    }

    public void setFileInfoAndContent(String fullFilePath) throws Exception {

        filePath = fullFilePath;
        fileExtention();
        loadFile();

    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public String getFileExtention() {
        return fileExtention;
    }

}
