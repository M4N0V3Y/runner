package com;

import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;

public class RunnerUtils {

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static String extractDetailedMessage(Throwable e) {
        final String message = e.getMessage();
        String[] messageParts = message.split(";");
        if (message == null) {
            return "";
        }

        return messageParts[0];
    }

}
