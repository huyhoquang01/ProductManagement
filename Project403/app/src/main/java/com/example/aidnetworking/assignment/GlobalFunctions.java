package com.example.aidnetworking.assignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalFunctions {
    public static boolean isGmailAddress(String emailAddress) {
        String expression = "^[\\w.+\\-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

}
