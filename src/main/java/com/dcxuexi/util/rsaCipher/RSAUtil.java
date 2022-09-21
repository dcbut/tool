package com.dcxuexi.util.rsaCipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/***
 * @Title RSAUtil
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/21 21:09
 * @Version 1.0.0
 */
public class RSAUtil {
    public static BASE64Encoder base64Encoder = new BASE64Encoder();
    public static BASE64Decoder base64Decoder = new BASE64Decoder();

    private final static Integer keySize  = 2048;  // 默认密钥字节数
    private static final String algorithm = "RSA"; // 算法类型 RSA算法
    private static final String RSAPUBLIC_KEY = "publicKey";  // 公钥
    private static final String RSAPRIVATE_KEY = "privateKey"; // 私钥


    // 生成密钥对
    public static  Map<String,String> generateKeyPair() throws NoSuchAlgorithmException {
        Map<String,String> keyPairMap = new HashMap<>();
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = base64Encoder.encode(publicKey.getEncoded());
        keyPairMap.put(RSAPUBLIC_KEY,publicKeyStr);
        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = base64Encoder.encode(privateKey.getEncoded());
        keyPairMap.put(RSAPRIVATE_KEY,privateKeyStr);
        return keyPairMap;
    }



    // 将base64编码后的公钥字符串转成PublicKey实例
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(keySpec);
    }

    // 将base64编码后的私钥字符串转成PrivateKey实例
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = base64Decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }


    // 私钥加密
    public static String encryptByPrivateKey(String content,String privateKeyStr) throws Exception {
        // 获取私钥
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] cipherText = cipher.doFinal(content.getBytes());
        String cipherStr = base64Encoder.encode(cipherText);
        return cipherStr;
    }

    // 私钥解密
    public static String decryptByPrivateKey(String content,String privateKeyStr) throws Exception {
        // 获取私钥
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherText = base64Decoder.decodeBuffer(content);
        byte[] decryptText = cipher.doFinal(cipherText);
        return new String(decryptText);
    }

    // 公钥加密
    public static String encryptByPublicKey(String content,String publicKeyStr) throws Exception {
        // 获取公钥
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(content.getBytes());
        String cipherStr = base64Encoder.encode(cipherText);
        return cipherStr;
    }
    // 公钥解密
    public static String decryptByPublicKey(String content,String publicKeyStr) throws Exception {
        // 获取公钥
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] cipherText = base64Decoder.decodeBuffer(content);
        byte[] decryptText = cipher.doFinal(cipherText);
        return new String(decryptText);
    }


    public static void main(String[] args) throws Exception {
        Map<String, String> keyPairMap = RSAUtil.generateKeyPair();
        String publicKey=  keyPairMap.get("publicKey");
        String privateKey=  keyPairMap.get("privateKey");
        System.out.println("publicKey ===> " + publicKey);
        System.out.println(publicKey.length());
        System.out.println("privateKey ===> " + privateKey);
        System.out.println(privateKey.length());

        System.out.println("=======================================================");

        String data = "url=http://127.0.0.1/ba/fanruan?op=view&usernem=admin&uuid=2839832837829";

        // 数字签名 加密
        String encryptStr = RSAUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("私钥加密后 ===> " + encryptStr);
        // 数字签名 解密
        String result = RSAUtil.decryptByPublicKey(encryptStr, publicKey);
        System.out.println("公钥解密后 ===> " + result);


        System.out.println("=======================================================");
        String encryptStr2 = RSAUtil.encryptByPublicKey(data, publicKey);
        System.out.println("公钥加密后 ===> " + encryptStr2);
        String result2 = RSAUtil.decryptByPrivateKey(encryptStr2, privateKey);
        System.out.println("私钥解密后 ===> " + result2);

        System.out.println("=======================================================");
        // 生成一个 uuid  根据毫秒时间戳和随机数生成唯一ID
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int frist = random.nextInt(999);
        int end = random.nextInt(999);
        //如果不足三位前面补0
        String uuidStr =String.format("%03d", frist) + millis + String.format("%03d", end);
        System.out.println("uuid ===> " + uuidStr);


    }




}
