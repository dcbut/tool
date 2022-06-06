package com.dcxuexi.util.http;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 自定义签名证书管理类 (接受任意来源证书)
 *
 * @author Creator
 * @version 2.0
 * @since jdk 1.6
 */
public class MyX509TrustManager implements X509TrustManager {
    /*
     * The default X509TrustManager returned by SunX509. We'll delegate
     * decisions to it, and fall back to the logic in this class if the
     * default X509TrustManager doesn't trust it.
     */
    X509TrustManager sunJSSEX509TrustManager;

    MyX509TrustManager(String sslKeyStorePath, String sslKeyStorePassword, int isTest) throws Exception {
        // create a "default" JSSE X509TrustManager.

        KeyStore keyStore = KeyStore.getInstance("JKS");

        if (isTest != 1) {
            FileInputStream fileInputStream = null;
            try{
                fileInputStream = new FileInputStream(sslKeyStorePath);
                keyStore.load(fileInputStream, sslKeyStorePassword.toCharArray());
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }
        }


        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance("SunX509", "SunJSSE");

        trustManagerFactory.init(keyStore);

        TrustManager trustManagers[] = trustManagerFactory.getTrustManagers();

         /*
          * Iterate over the returned trustmanagers, look
          * for an instance of X509TrustManager. If found,
          * use that as our "default" trust manager.
          */
        for (int i = 0; i < trustManagers.length; i++) {
            if (trustManagers[i] instanceof X509TrustManager) {
                sunJSSEX509TrustManager = (X509TrustManager) trustManagers[i];
                return;
            }
        }

         /*
          * Find some other way to initialize, or else we have to fail the
          * constructor.
          */
        throw new Exception("Couldn't initialize");
    }

    /*
     * Delegate to the default trust manager.
     */
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            // do any special handling here, or rethrow exception.
        }
    }

    /*
     * Delegate to the default trust manager.
     */
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        try {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
             /*
              * Possibly pop up a dialog box asking whether to trust the
              * cert chain.
              */
        }
    }

    /*
     * Merely pass this through.
     */
    public X509Certificate[] getAcceptedIssuers() {
        return sunJSSEX509TrustManager.getAcceptedIssuers();
    }
}