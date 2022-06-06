package com.dcxuexi.util.http;


import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpClient请求工具
 */
public class HttpClientUtil2 {
    private static Log log = LogFactory.getLog(HttpClientUtil.class);
    private static final int TIMEOUT = 360000;
    private static final String POST = "post";
    private static final String GET = "get";
    public static final String UTF8_CHARSET = "UTF-8";
    public static final String GBK_CHARSET = "GBK";
    public static final String GB2312_CHARSET = "GB2312";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> param, String charset) {
        GetMethod method = new GetMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, url, param, charset, GET);
    }

    public static String sendGet(String url, Map<String, String> header, Map<String, String> param, String charset) {
        GetMethod method = new GetMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, header, url, param, charset, GET, TIMEOUT);
    }

    public static Map<String, String> sendGet(String url, Map<String, String> header, Map<String, String> param, String contentType, String charset) {
        GetMethod method = new GetMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, header, url, param, contentType, charset, GET, TIMEOUT);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param, String charset) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, url, param, charset, POST);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static Map<String, String> sendPost(Map<String, String> param, String url, String charset) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(url, method, param, charset, POST);
    }

    /**
     * 向指定 URL 发送POST方法的请求，壹钱包回调使用，可调timeOut
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static Map<String, String> sendPost(Map<String, String> param, String url, String charset, int timeOut) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(url, method, null, param, charset, POST, timeOut);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param header 请求头部
     * @param param  请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> header, Map<String, String> param, String charset) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, header, url, param, charset, POST, TIMEOUT);
    }

    ///  类天下淘渠道请求
    public static String sendPost(String url, Map<String, String> header, Map<String, String> param, String contentType, String charset) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute2(method, header, url, param, contentType, charset, POST, TIMEOUT);
    }
    ///////

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> param, String charset, int timer) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(method, null, url, param, charset, POST, timer);
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param *   param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostEntity(String url, Map<String, String> header, String content, String contentType, String charset) {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(TIMEOUT);
            PostMethod method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
            method.setRequestHeader("Content-Type", contentType + ";charset=" + charset);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            //设置请求参数头
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }

            RequestEntity se = new StringRequestEntity(content, contentType, charset);
            method.setRequestEntity(se);

            int count = httpClient.executeMethod(method);
            log.info("请求URL地址:" + url + ":code:" + count);
            String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
            return submitResult;
        } catch (Exception e) {
            log.error("HTTPClient请求URL地址:" + url + ",请求参数[" + content + "]", e);
            log.info("HTTPClient请求错误：Exception：" + IBUUtil.getErrorInfoFromException(e));
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param *   param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static Map<String, String> sendPostEntity(String url, String content, Map<String, String> header, String contentType, String charset) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "-1");
        map.put("result", "");
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(TIMEOUT);
            PostMethod method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            //设置请求参数头
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }

            RequestEntity se = new StringRequestEntity(content, contentType, charset);
            method.setRequestEntity(se);

            int count = httpClient.executeMethod(method);
            map.put("code", count + "");
            if (count == 200) {
                String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
                map.put("result", submitResult);
            }
            log.info("请求URL地址:" + url + ":code:" + count);
            return map;
        } catch (Exception e) {
            log.error("HTTPClient请求URL地址:" + url + ",请求参数[" + content + "]", e);
            log.info("HTTPClient请求错误：Exception：" + IBUUtil.getErrorInfoFromException(e));
        }
        return map;
    }


    public static String sendPostEntity(String url, String content, String contentType, String charset) {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(TIMEOUT);
            PostMethod method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIMEOUT);
            method.setRequestHeader("Content-Type", contentType + ";charset=" + charset);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

            RequestEntity se = new StringRequestEntity(content, contentType, charset);
            method.setRequestEntity(se);

            httpClient.executeMethod(method);
            String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
            return submitResult;
        } catch (Exception e) {
            log.error("HTTPClient请求URL地址:" + url + ",请求参数[" + content + "]", e);
            log.info("HTTPClient请求错误：Exception：" + IBUUtil.getErrorInfoFromException(e));
        }
        return null;
    }

    /**
     * 执行HttpClient请求
     *
     * @return
     */
    private static String execute(HttpMethodBase method, String url, Map<String, String> param, String charset, String type) {
        return execute(method, null, url, param, charset, type, TIMEOUT);
    }

    /**
     * 执行HttpClient请求
     *
     * @return
     */
    private static Map<String, String> execute(String url, HttpMethodBase method, Map<String, String> param, String charset, String type) {
        return execute(url, method, null, param, charset, type, TIMEOUT);
    }

    private static String execute(HttpMethodBase method, Map<String, String> header, String url, Map<String, String> param, String charset, String type, Integer timer) {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(timer);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timer);
            method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=" + charset);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

            //增加头参数
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            if (param != null && param.size() > 0) {
                //请求信息封装
                NameValuePair[] data = new NameValuePair[param.size()];
                Iterator<Entry<String, String>> iter = param.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    Entry<String, String> entry = iter.next();
                    data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                    i++;
                }
                if (type.equals(GET)) {
                    method.setQueryString(data);
                } else {
                    PostMethod postMethod = (PostMethod) method;
                    postMethod.setRequestBody(data);
                }

            }
            int count = httpClient.executeMethod(method);
            String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
            return submitResult;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("错误了", e);
            log.info("错误了" + getRequestString(url, param), e);
            log.info("HTTPClient请求错误：Exception：" + IBUUtil.getErrorInfoFromException(e));
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    ////  新增请求方法   ////
    private static String execute2(HttpMethodBase method, Map<String, String> header, String url, Map<String, String> param, String ContentType, String charset, String type, Integer timer) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        // 参数 map 转 json 格式
        String reqParam = JSONObject.fromObject(param).toString();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            // 设置通用的请求属性
            conn.setRequestMethod("POST");//  设置请求方式
            conn.setRequestProperty("Accept", ContentType);// 设置接收数据的格式
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 添加请求协议
            conn.setRequestProperty("Content-Type", ContentType); // 设置发送数据的格式
            conn.setRequestProperty("Accept-Charset", charset);// utf-8编码

            //增加头参数
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(reqParam);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
            result = e.getMessage();
        }

        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            log.info("请求日志：[" + url + "][" + reqParam + "][" + result + "]");
        }
        return result;

//	    try {
//            HttpClient httpClient = new HttpClient();
//            httpClient.getParams().setContentCharset(charset);
//            httpClient.getParams().setConnectionManagerTimeout(timer);
//            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timer);
//            method.setRequestHeader("ContentType",ContentType+";charset="+charset);
//            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
//
//            //增加头参数
//            if(header!=null && header.size()>0){
//                for (Entry<String, String> entry : header.entrySet()) {
//                    method.setRequestHeader(entry.getKey(), entry.getValue());
//                }
//            }
//            if(param!=null && param.size()>0){
//                //请求信息封装
//                NameValuePair[] data = new NameValuePair[param.size()];
//                Iterator<Entry<String, String>> iter = param.entrySet().iterator();
//                int i=0;
//                while(iter.hasNext()){
//                    Entry<String, String> entry = iter.next();
//                    data[i] = new NameValuePair(entry.getKey(), entry.getValue());
//                    i++;
//                }
//                log.info("********"+data.toString()+"******");
//                if(type.equals(GET)){
//                    method.setQueryString(data);
//                }else{
//                    PostMethod postMethod = (PostMethod)method;
//                    postMethod.setRequestBody(data);
//                }
//
//            }
//            int count = httpClient.executeMethod(method);
//            String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
//            return submitResult;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("错误了", e);
//            log.info("错误了"+getRequestString(url,param), e);
//            log.info("HTTPClient请求错误：Exception："+IBUUtil.getErrorInfoFromException(e));
//        } finally{
//            method.releaseConnection();
//        }
//        return null;
    }

    ///////


    private static Map<String, String> execute(String url, HttpMethodBase method, Map<String, String> header, Map<String, String> param, String charset, String type, Integer timer) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "-1");
        map.put("result", "");
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(timer);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timer);
            method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=" + charset);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

            //增加头参数
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            if (param != null && param.size() > 0) {
                //请求信息封装
                NameValuePair[] data = new NameValuePair[param.size()];
                Iterator<Entry<String, String>> iter = param.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    Entry<String, String> entry = iter.next();
                    data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                    i++;
                }
                if (type.equals(GET)) {
                    method.setQueryString(data);
                } else {
                    PostMethod postMethod = (PostMethod) method;
                    postMethod.setRequestBody(data);
                }
            }
            int code = httpClient.executeMethod(method);
            map.put("code", code + "");
            if (code == 200) {
                String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
                map.put("result", submitResult);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("错误了", e);
            log.info("错误了" + getRequestString(url, param), e);
        } finally {
            method.releaseConnection();
        }
        return map;
    }

    private static Map<String, String> execute(HttpMethodBase method, Map<String, String> header, String url, Map<String, String> param, String contentType, String charset, String type, Integer timer) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "-1");
        map.put("result", "");
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setContentCharset(charset);
            httpClient.getParams().setConnectionManagerTimeout(timer);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timer);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

            //增加头参数
            if (header != null && header.size() > 0) {
                for (Entry<String, String> entry : header.entrySet()) {
                    method.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            if (param != null && param.size() > 0) {
                //请求信息封装
                NameValuePair[] data = new NameValuePair[param.size()];
                Iterator<Entry<String, String>> iter = param.entrySet().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    Entry<String, String> entry = iter.next();
                    data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                    i++;
                }
                if (type.equals(GET)) {
                    method.setQueryString(data);
                } else {
                    PostMethod postMethod = (PostMethod) method;
                    postMethod.setRequestBody(data);
                }
            }
            int code = httpClient.executeMethod(method);
            map.put("code", code + "");
            if (code == 200) {
                String submitResult = IOUtils.toString(method.getResponseBodyAsStream(), charset);
                map.put("result", submitResult);
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getRequestString(url, param), e);
            log.info("HTTPClient请求错误：Exception：" + IBUUtil.getErrorInfoFromException(e));
        } finally {
            method.releaseConnection();
        }
        return map;
    }

    /**
     * 拼装请求参数信息
     */
    private static String getRequestString(String url, Map<String, String> param) {
        Iterator<Entry<String, String>> iter = param.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("HTTPClient请求URL地址:").append(url).append(",请求参数:");
        sb.append("[");
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            if (iter.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param charset 编码
     * @param header  HTTP请求消息头部信息
     * @param param   HTTP请求消息体
     * @return
     */
    public static Map<String, String> sendPost(String url, String charset, Map<String, String> header, Map<String, String> param) {
        PostMethod method = new PostMethod(url);
        if (param == null) {
            param = new HashMap<String, String>();
        }
        return execute(url, method, header, param, charset, POST, TIMEOUT);
    }
}
