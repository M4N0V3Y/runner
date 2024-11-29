package com.assinador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class LoadFileNInfo {

    private static  byte[] fileContent;
    private static  String filePath;
    private static  String fileExtention;
    
    private static void fileExtention() {      
        // extract the filename from the path
        fileExtention= "";
       String filename;
        try {
             filename = filePath.substring(filePath.lastIndexOf('\\') + 1);
            // find the last occurrence of '.' in the filename
            int dotIndex = filename.lastIndexOf('.'); 
            fileExtention =   (dotIndex > 0) ? filename.substring(dotIndex + 1) : ""; //extention

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }        
    }

    private static void loadFile() throws IOException{

        fileContent= Files.readAllBytes(Paths.get(filePath));
    }

    public void setFileInfoAndContent(String fullFilePath) throws Exception{

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
