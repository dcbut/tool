package com.dcxuexi.util.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dcxuexi.util.pojo.EasyExcelUser;

import java.util.Date;
import java.util.List;

/***
 * @Title WriteEasyExcel
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/3 21:35
 * @Version 1.0.0
 */
public class WriteEasyExcel {

    /**
     * @Author DongChuang
     * @Description //TODO
     * @Date 2022/9/3 21:39
     * @Param
     * @param null
     * @return
     * @return null
     */
    private static   void  writeEasyExcel(String filePath){
        List<EasyExcelUser> data = data();
        //写法一：
        //EasyExcel.write(filePath, EasyExcelUser.class).sheet("模板").doWrite(data);

        // 写法二
//        ExcelWriterBuilder workBook = EasyExcel.write(filePath, EasyExcelUser.class);
//        ExcelWriterSheetBuilder sheet = workBook.sheet("用户表");
//        sheet.doWrite(data);

        // 写法三
        try (ExcelWriter excelWriter = EasyExcel.write(filePath, EasyExcelUser.class).build()) {
            WriteSheet sheet2 = EasyExcel.writerSheet("用户表2").build();
            excelWriter.write(data,sheet2);


        }


    }





    private static List<EasyExcelUser> data() {
        List<EasyExcelUser> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            EasyExcelUser data = new EasyExcelUser();
            data.setUserId(i+100);
            data.setUserName("王先生"+i);
            data.setEmail("10000"+i+"@qq.com");
            data.setBranchName("上海公司"+i);
            list.add(data);
        }
        return list;
    }

    public static   void main(String[] args) {
        long t1 = new Date().getTime();
        String filePath = "C:\\java\\test20220903.xlsx";
        writeEasyExcel(filePath);
        //readExcel2(filePath);
        long t2 = new Date().getTime();
        System.out.println((t2 - t1) / 1000 + "秒");
    }

}
