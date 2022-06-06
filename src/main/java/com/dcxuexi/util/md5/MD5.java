package com.dcxuexi.util.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * <p>Title: MD5加密类</p>
 * <p>Description: 完成一些对MD5加密的工作</p>
 *
 * @version 1.0.0
 */
public class MD5 {
    public static String digest(String inString) {
        String outString = null;
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.update(inString.getBytes());
            byte[] digest = alg.digest();
            outString = byte2hex(digest);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return outString.toLowerCase();
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n >= b.length - 1) continue;
            hs = hs;
        }
        return hs.toUpperCase();
    }

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0L;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25.0D + 65.0D);
                    sb.append(String.valueOf((char) (int) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25.0D + 97.0D);
                    sb.append(String.valueOf((char) (int) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
            }
        }

        return sb.toString();
    }
}