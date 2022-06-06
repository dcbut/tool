package com.dcxuexi.util.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * MD5加密类
 */
public class Md5SaltTool {


    public static String encode(final String password) {
        if (password == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());

            final byte[] digest = messageDigest.digest();
            StringBuffer hexString = new StringBuffer();

            synchronized (hexString) {
                for (int i = 0; i < digest.length; i++) {
                    final String plainText = Integer.toHexString(0xFF & digest[i]);

                    if (plainText.length() < 2) {
                        hexString.append("0");
                    }
                    hexString.append(plainText);
                }

            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e.getMessage());
        }
    }

    /**
     * 生成含有随机盐的md5密码
     */
    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        int len = sb.length();
        int a = 16;
        if (len < a) {
            for (int i = 0; i < a - len; i++) {
                sb.append("0");
            }
        }
        String salt = sb.toString();
        password = encode(password + salt);
        char[] cs = new char[48];
        int b = 48;
        int b1 = 3;
        for (int i = 0; i < b; i += b1) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        int b = 48;
        int b1 = 3;
        for (int i = 0; i < b; i += b1) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return encode(password + salt).equals(new String(cs1));
    }
}
