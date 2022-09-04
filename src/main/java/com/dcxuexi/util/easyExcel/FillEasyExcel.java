package com.dcxuexi.util.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.dcxuexi.util.date.DateUtils;
import com.dcxuexi.util.excel.ExcelUtils;
import com.dcxuexi.util.pojo.EasyExcelUser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Title FillEasyExcel
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/3 22:17
 * @Version 1.0.0
 */
public class FillEasyExcel {

    /**
     * @Author DongChuang
     * @Description //TODO  最简单的填充
     * @Date 2022/9/3 22:50
     * @Param
     * @param null
     * @return
     * @return null
     */
    private static void fillEasyExcelSample(String filePath){
        // 模板
        String templateFile = "C:\\java\\simple.xlsx";
        // 方案1 根据对象填充
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
//        EasyExcelUser easyExcelUser = new EasyExcelUser();
//        easyExcelUser.setUserId(999);
//        easyExcelUser.setUserName("小朋友");
//        easyExcelUser.setEmail("1111@qq.com");
//        easyExcelUser.setBranchName("幼儿园");
//        EasyExcel.write(filePath).withTemplate(templateFile).sheet().doFill(easyExcelUser);

        // 方案2 根据Map填充
        Map<String,Object> map = MapUtils.newHashMap();
        map.put("userId",999);
        map.put("userName","小朋友");
        map.put("email","1122@qq.com");
        map.put("branchName","幼儿园");

        EasyExcel.write(filePath).withTemplate(templateFile).sheet().doFill(map);

    }
    
    /**
     * @Author DongChuang
     * @Description //TODO  填充列表
     * @Date 2022/9/3 23:35
     * @Param 
     * @param null
     * @return 
     * @return null
     */
    private static void listFill(String filePath){
        // 模板
        String templateFile = "C:\\java\\list.xlsx";

        List<EasyExcelUser> data = data();

        // 方案1 一下子全部放到内存里面 并填充
        //EasyExcel.write(filePath).withTemplate(templateFile).sheet().doFill(data);

        // 方案3 分多次 填充 会使用文件缓存（省内存）
        try(ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate(templateFile).build()){
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data,writeSheet);

        }



    }

    /**
     * @Author DongChuang
     * @Description //TODO 复杂的填充
     * @Date 2022/9/3 23:34
     * @Param
     * @param null
     * @return
     * @return null
     */
    public static void complexFill(String filePath){
        // 模板
        String templateFile = "C:\\java\\list.xlsx";

        List<EasyExcelUser> data = data();
        Integer count = data.size();
        String time = DateUtils.getToday();


        try (ExcelWriter excelWriter = EasyExcel.write(filePath).withTemplate(templateFile).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(data,fillConfig,writeSheet);
            HashMap<String, Object> map = MapUtils.newHashMap();
            map.put("total",count);
            map.put("date",time);
            excelWriter.fill(map,fillConfig,writeSheet);


        }



    }










    private static List<EasyExcelUser> data() {
        List<EasyExcelUser> list = ListUtils.newArrayList();
        for (int i = 0; i < 10000; i++) {
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
        String filePath = "C:\\java\\testListFill20220903.xlsx";
        complexFill(filePath);
        //listFill(filePath);
        //fillEasyExcelSample(filePath);
        //templateFilePath(filePath);
        //readExcel2(filePath);
        long t2 = new Date().getTime();
        System.out.println((t2 - t1) / 1000 + "秒");
    }
}
