package com.wherewasi.backend.util;

public class StringUtil {
    public static boolean isEnglishOnly(String text) {
        if (text == null) {
            return false;
        }

        return text.chars().allMatch(c -> c >= 32 && c <= 126);
    }
}
