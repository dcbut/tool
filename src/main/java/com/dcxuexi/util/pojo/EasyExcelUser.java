package com.dcxuexi.util.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/***
 * @Title EasyExcelUser
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/3 18:29
 * @Version 1.0.0
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EasyExcelUser {

    @ExcelProperty(value = "用户ID", index = 0)
    private Integer userId;

    @ExcelProperty(value = "用户名称", index = 1)
    private String userName;

    @ExcelProperty(value = "用户邮箱", index = 2)
    private String email;

    @ExcelProperty(value = "用户公司", index = 3)
    private String branchName;

}
