package com.dcxuexi.util.commons;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * <p>Title: 常用字符串操作类</p>
 * <p>Description: 完成一些对字符串的计算工作</p>
 *
 * @version 1.0.0
 */
public class Commons {

    private static char[] ca = new char[] { '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 产生一个给定长度的数字字串
     *
     * @param n
     * @return
     */
    public static String generate(int n) {
        Random random = new Random();
        char[] cr = new char[n];
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(10);
            cr[i] = Integer.toString(x).charAt(0);
        }
        return (new String(cr));
    }

    /**
     * 产生一个给定长度的数字及字母字符串
     *
     * @param n
     * @return
     */
    public static String generateMixed(int n) {
        Random random = new Random();
        char[] cr = new char[n];
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(ca.length);
            cr[i] = ca[x];
        }
        return (new String(cr));
    }

    /**
     * 文件名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }


    /**
     * 根据毫秒时间戳和随机数生成唯一ID
     * @return
     */
    public static String getIdentifier(){
        long curr = System.currentTimeMillis();
        String random = generateMixed(3)+curr+generateMixed(3);
        return random.toLowerCase();
    }


    /**
     * 根据毫秒时间戳和随机数生成唯一任务ID
     * @return
     */
    public static String getTaskId(){
        long curr = System.currentTimeMillis();
        String random = curr+generate(4);
        return random;
    }

    /**
     * 通过文件路径直接修改文件名
     * @param filePath 需要修改的文件的完整路径
     * @param newFileName 需要修改的文件的名称
     * @return
     */
    public static String newFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        // 判断原文件是否存在
        if (!f.exists()) {
            return null;
        }

        newFileName = newFileName.trim();
        // 文件名不能为空
        if ("".equals(newFileName) || newFileName == null)
            return null;

        String newFilePath = null;
        // 判断是否为文件夹
        if (f.isDirectory()) {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/" + newFileName;
        } else {
            newFilePath = filePath.substring(0, filePath.lastIndexOf("/"))+ "/"  + newFileName + filePath.substring(filePath.lastIndexOf("."));
        }
        File nf = new File(newFilePath);
        // 判断需要修改为的文件是否存在（防止文件名冲突）
        if (!f.exists()) {
            return null;
        }

        try {
            // 修改文件名
            f.renameTo(nf);
        } catch(Exception err) {
            err.printStackTrace();
            return null;
        }

        return newFilePath;
    }

    /**
     * 获取图片分辨率及文件大小
     * @param path
     * @return
     */
    public static Map<String,Object> getImage(String path){
        Map<String,Object> map = new HashMap<String, Object>();
        URL url = null;
        try {
            url = new URL(path);
//            url = new URL("http://127.0.0.1:18080/2018/8/29/EVB3721535533706682.jpg");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            long fileSize = connection.getContentLength();
            BufferedImage image = ImageIO.read(connection.getInputStream());
            int srcWidth = image .getWidth();      // 源图宽度
            int srcHeight = image .getHeight();    // 源图高度
            String size = getNetFileSizeDescription(fileSize);
            map.put("width",srcWidth);
            map.put("height",srcHeight);
            map.put("size",size);
            System.out.println("srcWidth = " + srcWidth);
            System.out.println("=============size = " + size);
            System.out.println("srcHeight = " + srcHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }
    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        }
        else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        }
        else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        }
        else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            }
            else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }



}
