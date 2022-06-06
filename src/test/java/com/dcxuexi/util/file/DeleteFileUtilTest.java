package com.dcxuexi.util.file;


public class DeleteFileUtilTest {

    public static void main(String[] args) {
        // 删除单个文件
//        String file = "C:\\java\\idea-workspace\\tools\\test\\test.txt";
//        DeleteFileUtil.deleteFile(file);
//        System.out.println("===================");
        // 删除一个目录
        String dir = "C:\\java\\idea-workspace\\tools\\test";
        DeleteFileUtil.deleteDirectory(dir);
        System.out.println("===================");
        // 删除文件
//        dir = "c:/test/test0";
//        DeleteFileUtil.delete(dir);
//        System.out.println("===================");

    }


}
