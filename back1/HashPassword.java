package org.example.back1;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ApplicationScope
@Component
public class HashPassword {
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update((password).getBytes());
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return  stringBuilder.toString();
    }
}