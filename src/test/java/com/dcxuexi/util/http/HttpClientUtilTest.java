package com.dcxuexi.util.http;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilTest {
    public static void main(String[] args) {

        HttpClientUtil hcu = HttpClientUtil.getInstance();
        String huarenmaCallBack = "";//华人码的链接  taskId,transcodingUrl
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("taskid", "039a741900bc409b90732177dcf5e2de");
        maps.put("transcodingUrl", "http://mgcdn.vod.migucloud.com/vi1/222.0Ko0xpwUJaA80jiNDrImB.4.VxGzqA.mp4");
        String map = "taskid=039a741900bc409b90732177dcf5e2de&transcodingUrl=http://mgcdn.vod.migucloud.com/vi1/222.0Ko0xpwUJaA80jiNDrImB.4.VxGzqA.mp4";
        System.out.println(maps + "请求入参");
        String string = hcu.sendHttpPost(huarenmaCallBack, maps);
        System.out.println("请求出参:" + string);
    }

    public static void uploadFile(String fileName) {
        try {

            // 换行符
            final String newLine = "\r\n";      //文件的换行符
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL("http://localhost:8080?heheheh");
//            URL url2 =new URL(null,"http://localhost:8080?heheheh",new sun.net.www.protocol.http.Handler());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            File file = new File(fileName);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + fileName
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(
                    file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }


    public void initClass() {
        //1:创建一个httpclient对象
        HttpClient httpclient = new DefaultHttpClient();
        Charset charset = Charset.forName("UTF-8");//设置编码
        try {
            //2：创建http的发送方式对象，是GET还是post
            HttpPost httppost = new HttpPost("http://localhost:8080?/advertisement/file/test?get=get");

            //3：创建要发送的实体，就是key-value的这种结构，借助于这个类，可以实现文件和参数同时上传，很简单的。
            MultipartEntity reqEntity = new MultipartEntity();

            FileBody bin = new FileBody(new File("/var/root/Desktop/logs/error.log"));
//            FileBody bin1 = new FileBody(new File("C:/Users/kin.liufu.2GOTECH/Desktop/资料/Go.XML Message Protocol Specification (V2.88h).doc"));
            StringBody comment = new StringBody("房子类型为三房一厅", charset);
            ArrayList<FileBody> fileBodys = new ArrayList<FileBody>();
            fileBodys.add(bin);
//            fileBodys.add(bin1);

            addFileBodyPart("upLoadImage", fileBodys, reqEntity);
            reqEntity.addPart("typeOfHourseRoom", comment);
            httppost.setEntity(reqEntity);

            //4：执行httppost对象，从而获得信息
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();

            //获得返回来的信息，转化为字符串string
            String resString = EntityUtils.toString(resEntity);
            System.out.println(resString);

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            }
        }
    }

    //当多个文件需要上传时，对文件进行组装
    public void addFileBodyPart(String paramName, ArrayList<FileBody> fileBodys, MultipartEntity reqEntity) {
        if (fileBodys == null || fileBodys.size() < 1 || reqEntity == null || paramName == null) {
            return;
        }
        for (FileBody iteam : fileBodys) {
            reqEntity.addPart(paramName, iteam);
        }
    }
}
