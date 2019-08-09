package com.apploidxxx.api.util;

import org.apache.commons.codec.digest.Md5Crypt;

import java.util.Base64;

/**
 * @author Arthur Kupriyanov
 */
public class Password {
    private static final String salt = Base64.getEncoder().encodeToString("ITMO".getBytes());
    public static String hash(String password){
        return Md5Crypt.md5Crypt((password + salt).getBytes());
    }
    private static String hash(String password, String anotherEncryptedPassword){
        return Md5Crypt.md5Crypt((password + salt).getBytes(), anotherEncryptedPassword);
    }

    public static boolean isEqual(String rawPassword, String hashedPassword){
        return hashedPassword.equals(hash(rawPassword, hashedPassword));
    }
}
