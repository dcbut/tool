package com.dcxuexi.util.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: HttpsClient请求类</p>
 * <p>Description: 完成一些对HttpsClient请求的工作</p>
 *
 * @version 1.0.0
 */
public class HttpsClientUtil {
	private static Log log   = LogFactory.getLog(HttpClientUtil.class);
	private static final int TIMEOUT =  180000;
	public static final String UTF8_CHARSET = "UTF-8";
	public static final String GBK_CHARSET = "GBK";

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

	public static String sendGet(String url,String charset) {
        HttpsURLConnection conn = null;
        InputStream in = null;
        try{
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(url);
            conn = (HttpsURLConnection)console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setDoOutput(true);
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            conn.connect();
            in = conn.getInputStream();
            return IOUtils.toString(in, charset);
        }catch(Exception e){
            log.error("HTTPSClient请求URL地址:" + url, e);
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
	}

	public static String sendPostEntity(String url, String content,	String contentType, String charset) {
	    DataOutputStream out = null;
	    InputStream in = null;
		try {
			 SSLContext sc = SSLContext.getInstance("SSL");
		        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
		                new java.security.SecureRandom());
		        @SuppressWarnings("restriction")
				URL console = new URL(null,url,new sun.net.www.protocol.https.Handler());
		        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		        conn.setSSLSocketFactory(sc.getSocketFactory());
		        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		        conn.setDoOutput(true);
		        conn.setConnectTimeout(TIMEOUT);
		        conn.setReadTimeout(TIMEOUT);
//		        conn.setRequestMethod("post");
		        conn.setRequestProperty("Content-Type", contentType+"; charset="+charset);
		        conn.connect();
		        out = new DataOutputStream(conn.getOutputStream());
		        out.write(content.getBytes(charset));
		        // 刷新、关闭
		        out.flush();
		        in = conn.getInputStream();
		       return IOUtils.toString(in, charset);
		} catch (Exception e) {
			log.error("HTTPSClient请求URL地址:"+url+",请求参数["+content+"]", e);
		} finally{
		    try{
                if(in != null){
                    in.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
		    try{
                if(out != null){
                    out.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
		}
		return null;
	}

	
	
	 /**
     * 创建SSL Content对象，并使用指定信任管理器初始化
     *
     * @param sslKeyStorePath     　证书存放地址
     * @param sslKeyStorePassword 　证书密码
     * @param isTest          1是    是否为测试环境
     * @return
     * @throws Exception
     */
    public static SSLContext getSSLContext(String sslKeyStorePath, String sslKeyStorePassword, int isTest) throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");

        if (isTest != 1) {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            KeyStore keyStore = KeyStore.getInstance("JKS");
            FileInputStream fileInputStream = new FileInputStream(sslKeyStorePath);
            keyStore.load(fileInputStream, sslKeyStorePassword.toCharArray());
            fileInputStream.close();
            keyManagerFactory.init(keyStore, sslKeyStorePassword.toCharArray());
            TrustManager[] trustManagers = {new MyX509TrustManager(sslKeyStorePath, sslKeyStorePassword, isTest)};
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);
        } else {
            TrustManager[] trustManagers = {new MyX509TrustManager("", "", isTest)};
            sslContext.init(null, trustManagers, null);
        }
        return sslContext;
    }

    /**
     * 发送https请求
     *
     * @param url           请求URL地址
     * @param requestString 请求的字符串信息
     * @param requestMethod 请求类型 "GET","POST"
     * @return 服务器响应结果
     * @throws CmopException 网络故障时抛出异常
     */
    public static Map<String,String> httpsRequest(String url, String requestString,Map<String,String> map, String requestMethod,String charset,Integer isTest) {
        InputStream returnStream = null;
        SSLContext sslContext = null;
        Map<String,String> result = new HashMap<String, String>();
        result.put("code", "0");
        result.put("result", "");
        try {
            // 创建URL对象
            @SuppressWarnings("restriction")
			URL serverUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());
            // 创建HttpsURLConnection对象
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) serverUrl.openConnection();

            try {
                sslContext = getSSLContext("", "", isTest);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            //设置其SSLSocketFactory对象
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            httpsURLConnection.setSSLSocketFactory(socketFactory);
            //设置建立超时时间
            httpsURLConnection.setConnectTimeout(TIMEOUT);
            //设置读取数据超时时间
            httpsURLConnection.setReadTimeout(TIMEOUT);
            httpsURLConnection.setRequestMethod(requestMethod);
            httpsURLConnection.setDoOutput(true);
            if(map != null && map.size()>0 ){
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	            	httpsURLConnection.addRequestProperty(key, map.get(key));
	            }
	        }
            httpsURLConnection.connect();
            if(StringUtils.isNotBlank(requestString)){
                DataOutputStream out = null;
                try{
                    out = new DataOutputStream(httpsURLConnection.getOutputStream());
                    out.write(requestString.toString().getBytes(charset));
                    // 刷新、关闭
                    out.flush();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(out != null){
                        try{
                            out.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            int code = httpsURLConnection.getResponseCode();
            result.put("code", code+"");
            if(code == 200){
            	  returnStream = httpsURLConnection.getInputStream();
                  String resultString = IOUtils.toString(returnStream, charset);
                  result.put("result", resultString);
            }
        } catch (Exception e) {
        	log.error("HTTPSClient请求URL地址:"+url+",请求参数["+requestString+"]", e);
            e.printStackTrace();
        }finally{
            if(returnStream != null){
                try{
                    returnStream.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    
    /**
     * 发送https请求
     *
     * @param url           请求URL地址
     * @param requestString 请求的字符串信息
     * @param requestMethod 请求类型 "GET","POST"
     * @return 服务器响应结果
     * @throws CmopException 网络故障时抛出异常
     */
    public static String httpsRequest(String url, String requestString,String contentType, String requestMethod,
    		String charset,String keyStorePath,String keyStorePassword,int isTest) {
        InputStream returnStream = null;
        SSLContext sslContext = null;
        DataOutputStream out  = null;
        try {
            // 创建URL对象
            @SuppressWarnings("restriction")
			URL serverUrl = new URL(null, url, new sun.net.www.protocol.https.Handler());
            // 创建HttpsURLConnection对象
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) serverUrl.openConnection();

            try {
                sslContext = getSSLContext(keyStorePath, keyStorePassword, isTest);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            //设置其SSLSocketFactory对象
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            httpsURLConnection.setSSLSocketFactory(socketFactory);
            //设置建立超时时间
            httpsURLConnection.setConnectTimeout(TIMEOUT);
            //设置读取数据超时时间
            httpsURLConnection.setReadTimeout(TIMEOUT);
            httpsURLConnection.setRequestMethod(requestMethod);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.connect();
            if(StringUtils.isNotBlank(contentType)){
              httpsURLConnection.setRequestProperty("Content-Type", contentType+"; charset="+charset);
            }
            out = new DataOutputStream(httpsURLConnection.getOutputStream());
            out.write(requestString.toString().getBytes(charset));
            // 刷新、关闭
            out.flush();
            out.close();
            if (httpsURLConnection.getResponseCode() != 200) {
                returnStream = httpsURLConnection.getErrorStream();
            } else {
                returnStream = httpsURLConnection.getInputStream();
            }
            return IOUtils.toString(returnStream, charset);
        } catch (Exception e) {
        	log.error("HTTPSClient请求URL地址:"+url+",请求参数["+requestString+"]", e);
            e.printStackTrace();
        } finally {
            try{
                if(out != null){
                    out.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            try{
                if(returnStream!=null){
                    returnStream.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}