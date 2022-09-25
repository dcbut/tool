package com.dcxuexi.util.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/***
 * @Title RSAUtilTest
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/22 20:57
 * @Version 1.0.0
 */
public class RSAUtil2Test {

    public static void main(String[] args) throws Exception {

        Map<String, Object> keyMap = RSAUtil3.genKeyPair();
        String  publicKey = RSAUtil3.getPublicKey(keyMap);
        String privateKey = RSAUtil3.getPrivateKey(keyMap);
        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);
        System.out.println("=====================1111111111111==================================");

        System.out.println("私钥加密——公钥解密");
        String  content = "daosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdas";
        String source = "sdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadasdhaoddddddaosdjosadosaudosaudaosdusadadsadsadssdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsadadiugtiutujjjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkka";

        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil3.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtil3.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);

        System.out.println("=================2222222222222======================================");
        System.out.println("私钥签名——公钥验证签名");
        String sign = RSAUtil3.sign(encodedData, privateKey);
        System.out.println("签名:\r" + sign);
        boolean status = RSAUtil3.verify(encodedData, publicKey, sign);
        System.out.println("验证结果:\r" + status);

        System.out.println("=================3333333333333333======================================");
        System.out.println("公钥加密——私钥解密");
        byte[] encodedData2 = RSAUtil3.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData2));
        byte[] decodedData2 = RSAUtil3.decryptByPrivateKey(encodedData2, privateKey);
        String target2 = new String(decodedData2);
        System.out.println("解密后文字: \r\n" + target2);

    }




    public static final void test2() throws Exception{

        KeyPair keyPair = RSAUtil2.getKeyPair();
        String publicKey=  "publicKey";
        String privateKey=  "privateKey";
        System.out.println("publicKey ===> " + publicKey);
        System.out.println(publicKey.length());
        System.out.println("privateKey ===> " + privateKey);
        System.out.println(privateKey.length());

        System.out.println("=======================================================");

        String content = "asdhaoddddddaosdjosadosaudosausdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsdhaoddddddaosdjosadosaudosaudaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsaddaosdusadadsadsadsadasdasdasdasdsadsadsadasdasdasdsadasdsadasdsadsadadsadsadadiugtiutujjjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkka";

        // 私钥加密
        String encryptStr = RSAUtil2.publicEncrpyt(content, publicKey);
        System.out.println("私钥加密后 ===> " + encryptStr);
        // 数字签名 解密
        String result = RSAUtil2.privateDecrypt(encryptStr, privateKey);
        System.out.println("公钥解密后 ===> " + result);


    }



}
