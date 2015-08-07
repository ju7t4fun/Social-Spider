package com.epam.lab.spider.controller.utils.hash;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Dzyuba Orest
 */
public class HashMD5 {
    private static final Logger LOG = Logger.getLogger(HashMD5.class);

    public String hash(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getLocalizedMessage(), e);
        }
        return generatedPassword;
    }
}
