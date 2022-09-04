package com.dcxuexi.util.easyExcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.Listener;
import com.alibaba.excel.util.ListUtils;
import com.dcxuexi.util.pojo.EasyExcelUser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/***
 * @Title UserListener
 * @Description TOTD 读取 USER 文档的监听器
 * @Auter DongChuang
 * @Date 2022/9/3 19:31
 * @Version 1.0.0
 */
@Slf4j
public class UserListener extends AnalysisEventListener<EasyExcelUser> {


    public   List<EasyExcelUser> userList = ListUtils.newArrayList();

    /*/**
     * @Author DongChuang
     * @Description //TODO  每读一行内容，都会调用一次invoke，在invoke 中可以操作使用读取的数据
     * @Date 2022/9/3 19:35
     * @Param
     * @param easyExcelUser
     * @param analysisContext
     * @return
     */
    @Override
    public void invoke(EasyExcelUser easyExcelUser, AnalysisContext analysisContext) {
        userList.add(easyExcelUser);
        System.out.println("easyExcelUser = "+easyExcelUser);


    }

    /**
     * @Author DongChuang
     * @Description //TODO  读取完整个文件后操作的方法
     * @Date 2022/9/3 19:41
     * @Param 
     * @param null
     * @return 
     * @return null
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取文件结束");
        System.out.println("读取文件结束");

    }
}
