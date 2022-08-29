package com.dcxuexi.util.excel;

import com.alibaba.fastjson.JSONObject;
import com.dcxuexi.util.pojo.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellAlignment;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/***
 * @Title WriteExcel
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/8/23 21:43
 * @Version 1.0.0
 */
public class WriteExcel {

    private static void write(List<User> list,String filePath) throws IOException {
        // 创建一个工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建一个工作表
        XSSFSheet sheet = workbook.createSheet("用户表");
        //设置样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置边框 左右上下
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION); //水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中


        XSSFCell cell = null;
        XSSFRow row = null;
        /////////////////////// 表头 ///////
        // 1.初始化带边框的表头
        for (int i = 0; i < 3; i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < 5; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
            }
        }
        // 2.指定单元格填充数据
        sheet.getRow(0).getCell(0).setCellValue("序号");
        sheet.getRow(0).getCell(1).setCellValue("用户表");
        sheet.getRow(1).getCell(1).setCellValue("用户");
        sheet.getRow(1).getCell(3).setCellValue("公司");
        sheet.getRow(2).getCell(1).setCellValue("用户ID");
        sheet.getRow(2).getCell(2).setCellValue("用户名称");
        sheet.getRow(2).getCell(3).setCellValue("用户邮箱");
        sheet.getRow(2).getCell(4).setCellValue("用户公司");

        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));


        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 3);
            cell =row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(cellStyle);
            cell =row.createCell(1);
            cell.setCellValue(list.get(i).getUserId());
            cell.setCellStyle(cellStyle);
            cell =row.createCell(2);
            cell.setCellValue(list.get(i).getUserName());
            cell.setCellStyle(cellStyle);
            cell =row.createCell(3);
            cell.setCellValue(list.get(i).getEmail());
            cell.setCellStyle(cellStyle);
            cell =row.createCell(4);
            cell.setCellValue(list.get(i).getBranchName());
            cell.setCellStyle(cellStyle);
        }
        // 设置一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        // 工作簿里的内容 写入输出流中
        workbook.write(fileOutputStream);
        // 刷新一下输出流
        fileOutputStream.flush();
        // 关闭资源
        fileOutputStream.close();
        workbook.close();
        System.out.println("写入成功");
    }







    public static void main(String[] args) throws IOException {

        String  filePath = "C:\\java\\test20220829.xlsx";
        User user1 = new User(101, "成先生", "cheng@qq.com", "成先生公司");
        User user2 = new User(102, "张三", "", "上海张扬");
        User user3 = new User(103, "李四", "111@163.com", "上海正德");
        User user4 = new User(104, "王二", "", "");
        List<User> list = new LinkedList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        write(list,filePath);

    }





}
