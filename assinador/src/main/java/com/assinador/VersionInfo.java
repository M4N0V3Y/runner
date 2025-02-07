package com.assinador;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class VersionInfo {

    public static String getVersion() {
        try {
            InputStream inputStream = VersionInfo.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
            if (inputStream != null) {
                Manifest manifest = new Manifest(inputStream);
                Attributes attributes = manifest.getMainAttributes();
                String version = attributes.getValue("Implementation-Version"); // Or "Bundle-Version" or
                                                                                // "Specification-Version"
                if (version != null) {
                    return version;
                }
            }
        } catch (IOException e) {
            // Handle exception (e.g., log it)
            e.printStackTrace(); // Or your preferred logging
        }
        return "Unknown Version"; // Default if version info not found
    }

}