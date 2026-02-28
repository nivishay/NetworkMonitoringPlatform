package com.nivishay.nmp.common.utils;

public class EmailUtils {
    public static String normalize(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return email.trim().toLowerCase();
    }
}
