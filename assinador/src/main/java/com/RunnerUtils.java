package com;

import java.util.regex.Pattern;

public class RunnerUtils {

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static int divideAndRoundUp(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        int quotient = dividend / divisor; // Integer division gives the whole number part
        int remainder = dividend % divisor; // Modulo operator gives the remainder

        if (remainder > 0) {
            return quotient + 1; // If there's a remainder, round up
        } else {
            return quotient; // If no remainder, the quotient is the result
        }
    }
    
    public static final boolean isUTF8(final byte[] pText) {
        int expectedLength = 0;
        for (int i = 0; i < pText.length; i++) {
            if ((pText[i] & 0b10000000) == 0b00000000) {
                expectedLength = 1;
            } else if ((pText[i] & 0b11100000) == 0b11000000) {
                expectedLength = 2;
            } else if ((pText[i] & 0b11110000) == 0b11100000) {
                expectedLength = 3;
            } else if ((pText[i] & 0b11111000) == 0b11110000) {
                expectedLength = 4;
            } else if ((pText[i] & 0b11111100) == 0b11111000) {
                expectedLength = 5;
            } else if ((pText[i] & 0b11111110) == 0b11111100) {
                expectedLength = 6;
            } else {
                return false;
            }
            while (--expectedLength > 0) {
                if (++i >= pText.length) {
                    return false;
                }
                if ((pText[i] & 0b11000000) != 0b10000000) {
                    return false;
                }
            }
        }
        return true;
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
