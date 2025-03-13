package com.tmdigital.gestiondestock.Utils;

import java.security.SecureRandom;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class PasswordUtils {
    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{}[]<>?/\\|";
    private static final int PASSWORD_LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int ALLOWED_CHARS_LENGTH = ALLOWED_CHARS.length();

    public static String generatePassword() {
        char[] password = new char[PASSWORD_LENGTH];

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password[i] = ALLOWED_CHARS.charAt(RANDOM.nextInt(ALLOWED_CHARS_LENGTH));
        }

        return new String(password);
    }

    public static String encodePassword(String randomPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(randomPassword).replace("{bcrypt}", "");
    }

    public static boolean matches(String password, String encodedPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(password, "{bcrypt}" + encodedPassword);
    }
}
