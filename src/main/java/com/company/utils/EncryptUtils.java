package com.company.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;


public class EncryptUtils {

    private static final int logRounds = 8;

    public static String hashBCrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public static boolean verifyHashBCrypt(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
