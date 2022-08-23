package com.dcxuexi.util.excel;

import com.dcxuexi.util.pojo.User;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/***
 * @Title ReadExcel
 * @Description TOTD 常规POI读取
 * @Auter DongChuang
 * @Date 2022/8/23 20:14
 * @Version 1.0.0
 */
public class ReadExcel {


    public static List<User> excel(File file) throws Exception {
        InputStream in = new FileInputStream(file);
        // 读取整个Excel
        XSSFWorkbook sheets = new XSSFWorkbook(in);
        // 获取第一个表单Sheet
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        //默认第一行为标题行，i = 0
        XSSFRow titleRow = sheetAt.getRow(0);

        // 导入数据实体类
        List<User> userList = new LinkedList<>();

        // 读取每一格内容
        StringBuilder sb = new StringBuilder();
        XSSFCell cell = null;
        // 循环获取每一行数据
        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheetAt.getRow(i);
            // 用户信息
            User user = new User();
            cell = row.getCell(0);
            cell.setCellType(CellType.STRING);
            if (cell.getStringCellValue().isEmpty()) {
                user.setUserId(null);
            } else {
                sb.delete(0, sb.length());
                sb.append(cell);
                user.setUserId(Integer.parseInt(String.valueOf(sb)));
            }

            cell = row.getCell(1);
            if (cell.getStringCellValue().isEmpty()) {
                user.setUserName("");
            } else {
//                cell.setCellType(CellType.STRING);
                sb.delete(0, sb.length());
                sb.append(cell);
                user.setUserName(String.valueOf(sb));
            }
            cell = row.getCell(2);
            if (cell.getStringCellValue().isEmpty()) {
                user.setEmail("");
            } else {
//                cell.setCellType(CellType.STRING);
                sb.delete(0, sb.length());
                sb.append(cell);
                user.setEmail(String.valueOf(sb));
            }
            cell = row.getCell(3);
            if (cell.getStringCellValue().isEmpty()) {
                user.setBranchName("");
            } else {
//                cell.setCellType(CellType.STRING);
                sb.delete(0, sb.length());
                sb.append(cell);
                user.setBranchName(String.valueOf(sb));
            }
            userList.add(user); //对象方法list中

//            int lie = row.getPhysicalNumberOfCells();
//            for (int index = 0; index < lie; index++) {
//                XSSFCell titleCell = titleRow.getCell(index);
//                cell = row.getCell(index);
//                cell.setCellType(CellType.STRING);
//                if (cell.getStringCellValue().isEmpty()) {
//                    continue;
//                }
//                sb.append(cell);
//            }
//            System.out.println(i + "\t" + sb);
        }

        return userList;
    }


    public static void main(String[] args) {
        try {
            long t1 = new Date().getTime();
//            File file = new File("C:\\java\\test.xlsx");
            File file = new File("C:\\java\\test2.xls");
            if (!file.exists()) {
                throw new Exception("文件不存在!");
            }
            List<User> userlist = excel(file);
            long t2 = new Date().getTime();
            System.out.println((t2 - t1) / 1000 + "秒");
            System.out.println(userlist);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
