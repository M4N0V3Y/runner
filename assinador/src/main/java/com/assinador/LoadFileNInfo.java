package com.assinador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

        fileContent = Files.readAllBytes(Paths.get(filePath));
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
