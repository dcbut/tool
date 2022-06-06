package com.dcxuexi.util.commons;


public class CommonsTest {

    public static void main(String[] args) {
        // generate
        System.out.println("产生一个给定长度的数字字串:"+ Commons.generate(20));
        //generateMixed
        System.out.println("产生一个给定长度的数字及字母字符串:"+ Commons.generateMixed(20));
        //genImageName
        System.out.println("文件名生成:"+ Commons.genImageName());
        //getIdentifier
        System.out.println("根据毫秒时间戳和随机数生成唯一ID:"+ Commons.getIdentifier());
        //getTaskId
        System.out.println("根据毫秒时间戳和随机数生成唯一任务ID:"+ Commons.getTaskId());


    }


}
