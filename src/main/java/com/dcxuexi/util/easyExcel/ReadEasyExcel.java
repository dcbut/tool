package com.dcxuexi.util.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.dcxuexi.util.easyExcel.listener.UserListener;
import com.dcxuexi.util.pojo.EasyExcelUser;
import com.dcxuexi.util.pojo.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

/***
 * @Title ReadEasyExcel
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/3 18:20
 * @Version 1.0.0
 */
public class ReadEasyExcel {


    private static UserListener userListener = new UserListener();

    public static void  readExcel(String filePath){
        // 获取一个工作簿对象
        ExcelReaderBuilder readWorkBook = EasyExcel.read(filePath, EasyExcelUser.class, userListener);
        // 获取一个工作表对象
        ExcelReaderSheetBuilder sheet = readWorkBook.sheet();
        // 去取工作表内容
//        sheet.doRead();
        sheet.headRowNumber(2).doRead();

        List<EasyExcelUser> userList = userListener.userList;
        System.out.println(userList);


    }

    public static void  readExcel2(String filePath){

        // 写法1
        try (ExcelReader excelReader = EasyExcel.read(filePath).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(EasyExcelUser.class).registerReadListener(userListener).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(EasyExcelUser.class).registerReadListener(userListener).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }
        List<EasyExcelUser> userList = userListener.userList;
        System.out.println(userList);


    }





    public static void main(String[] args) {
        long t1 = new Date().getTime();
        String filePath = "C:\\java\\test.xlsx";
        readExcel(filePath);
        //readExcel2(filePath);
        long t2 = new Date().getTime();
        System.out.println((t2 - t1) / 1000 + "秒");
    }






}
