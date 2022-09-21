package com.dcxuexi.util.rsaCipher;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

/***
 * @Title RSAUtilTest
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/21 21:21
 * @Version 1.0.0
 */
public class RSAUtilTest {

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
