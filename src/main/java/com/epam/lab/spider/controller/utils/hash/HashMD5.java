package com.epam.lab.spider.controller.utils.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dmytro on 13.06.2015.
 */
public class HashMD5 {

    public String hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
