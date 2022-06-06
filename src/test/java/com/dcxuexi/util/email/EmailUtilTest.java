package com.dcxuexi.util.email;


public class EmailUtilTest {

    public static void main(String[] args) {
        boolean sendFlag = EmailUtil.sendEmail("1981165631@qq.com", "【邮件发送类】测试", "<b>尊敬的用户</b>:<br>&nbsp;&nbsp;该邮件为测试邮件，收到邮件后可以忽略。谢谢<br> &nbsp;&nbsp;<b>【邮件发送类】测试</b>");
        if(sendFlag){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }


    }






}
