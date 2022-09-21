package com.dcxuexi.util.rsa;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;


/***
 * @Title RSAUtil2
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/21 21:48
 * @Version 1.0.0
 */
public class RSAUtil2 {

    public static final String SIGN_ALGORITHMS = "SHA256WithRSA";
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }


    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            byte[] signed = signature.sign();
            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verify(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 公钥分段加密
     *
     * @param content
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String publicEncrpyt(String content, String publicKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, getPublicKey(publicKeyStr));
        byte[] bytes = content.getBytes(DEFAULT_CHARSET);

        int inputLen = bytes.length;
        int offSet = 0;
        byte[] cache;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.getEncoder().encodeToString(encryptedData);
    }


    /**
     * 私钥分段加密
     *
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String privateEncrpyt(String content, String privateKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, getPrivateKey(privateKeyStr));
        byte[] bytes = content.getBytes(DEFAULT_CHARSET);

        int inputLen = bytes.length;
        int offSet = 0;
        byte[] cache;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.getEncoder().encodeToString(encryptedData);
    }


    /**
     * 私钥分段解密
     *
     * @param content
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String privateDecrypt(String content, String privateKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, getPrivateKey(privateKeyStr));
        byte[] bytes = Base64.getDecoder().decode(content);
        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }


    /**
     * 公钥分段解密
     *
     * @param content
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String publicDecrypt(String content, String publicKeyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, getPublicKey(publicKeyStr));
        byte[] bytes = Base64.getDecoder().decode(content);
        int inputLen = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(bytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }


    public static void main(String[] args) throws Exception {
        String content = "asdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsadadiugtiutujjjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkka";
        // 公钥216
        String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCaGn5+Yq9o7kxmDS2zwIREIEkcpKbx44uPT/PX22XOjgzkMvbCEamu7KyatNhkrrSH6E14l1F739WztXffJ/OCXUmgF4egMedyGCA8ymZWbzyhwnZcywa8me8S2rjb3gkCD6VFq8BSPJx9vaMvlR7eZxe1O5lllaV6tE6SlNY7QIDAQAB";
        //私钥844
        String privateKey =
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJoafn5ir2juTGYNLbPAhEQgSRy" +
                        "kpvHji49P89fbZc6ODOQy9sIRqa7srJq02GSutIfoTXiXUXvf1bO1d98n84JdSaAXh6Ax53IYIDz" +
                        "KZlZvPKHCdlzLBryZ7xLauNveCQIPpUWrwFI8nH29oy+VHt5nF7U7mWWVpXq0TpKU1jtAgMBAAEC" +
                        "gYBOzKsk+s4EM5dnSXKo+ENmblOq43SFnzrh4+7X7vD4zZxCRH96NfEDNS4Qs45RSmmLKMOwHL2B" +
                        "0etfWBJSPisNZomhOBgTSfvHDSIFNbvVFqQBH5zEtxIB3bPrkW5X6EDF2BYCatC70Al21kmzHr2d" +
                        "BaL/c2p/4ueTdna/zKSKwQJBAMZLvFeyLi9jiIXXUzv7AkxF4SICtOOL56mKMkVlAXOWQgQe9FVs" +
                        "O8lTTK6GqqLrYe5cqgTiQqcLCP8/5n33oF0CQQCoW0ospTg8JEqStTmwemF8HfgxLU6YGj5kegD2" +
                        "Fq6HfCTq/z59THohbq6l+yoSxO3mjZb+7Zv1zu6oDR7YXFHRAkB66dEaDtFAAJNMWxc107Yt7xbI" +
                        "zSKw9TSoy4ezqhNHQXk0MrfDB27bsS2T9NdqWzr91CRzGIi2IEn4ZfSKWmblAkB2S1bOEfV2hMWF" +
                        "WiND9mnDDUfUPhKIW4BVl0hPodZWSouiN2DQJ8l07lF3PQjuEUNcCUb8rzYzvIgCut1eh1fRAkEA" +
                        "qBOEqBuRq9W7vrBlHbqEk1EXu2MfeRLZWljZn6uoyedJCdSh+6neUUvqHDGi3DNdss/ByWHI9e+9" +
                        "zOXTURmMaQ==";



        String s = publicEncrpyt(content, publicKey);
        System.out.println("公钥加密后"+s);






        String s1 = privateDecrypt(s, privateKey);
        System.out.println("解密后"+s1 );


        //公钥加密-->私钥签名-->公钥验签-->私钥解密
       /* String s = publicEncrpyt(content, publicKey);
        System.out.println("公钥加密后"+s);

        //签名     Authorization
        String sign = sign(s, privateKey);
        System.out.println("私钥签名后："+sign);

        //验签
        boolean verify = verify(s, sign, publicKey);
        System.out.println("用公钥验签后"+verify);

        String s1 = privateDecrypt(s, privateKey);
        System.out.println("解密后"+s1 );*/


        /*String s = privateEncrpyt(content, privateKey);
        System.out.println("公钥加密后" + s);

        String sign = sign(s, privateKey);
        System.out.println("私钥签名后：" + sign);

        boolean verify = verify(s, sign, publicKey);
        System.out.println("用公钥验签后" + verify);

        String s1 = publicDecrypt(s, publicKey);
        System.out.println("解密后" + s1);*/
    }

}
