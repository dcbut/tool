package com.dcxuexi.util.commons;

import java.util.Map;

/***
 * @Title CommonsTest
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/5 22:27
 * @Version 1.0.0
 */
public class CommonsTest {

    /**
     * @Author DongChuang
     * @Description //TODO
     * @Date 2022/9/5 22:28
     * @Param
     * @param null
     * @return
     * @return null
     */
    public static void main(String[] args) {

        // 方法一：产生一个给定长度的数字字串
        System.out.println("数字字符串=" + Commons.generate(50));

        // 方法二：产生一个给定长度的数字及字母字符串
        System.out.println("数字及字母字符串=" + Commons.generateMixed(8));

        // 方法二：文件名生成
        System.out.println("文件名生成=" + Commons.genImageName());

        // 方法二：根据毫秒时间戳和随机数生成唯一ID
        System.out.println("唯一ID=" + Commons.getIdentifier());

        // 方法二：根据毫秒时间戳和随机数生成唯一任务ID
        System.out.println("唯一任务ID=" + Commons.getTaskId());

        // 方法二：获取图片分辨率及文件大小
        //String imagePath = "C:\\java\\data\\diugai.com165312754993654.png";
        //String imagePath = "https://img2022.cnblogs.com/blog/35695/202209/35695-20220901164517964-141149333.png";
        String imagePath = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
        Map<String, Object> image = Commons.getImage(imagePath);
        System.out.println("width = " + image.get("width"));
        System.out.println("height = " + image.get("height"));
        System.out.println("size = " + image.get("size"));


    }







}
