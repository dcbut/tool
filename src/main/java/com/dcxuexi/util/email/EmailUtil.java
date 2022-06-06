package com.dcxuexi.util.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;


/**
 * <p>Title: 邮件发送类</p>
 * <p>Description: 完成一些对邮件发送的工作</p>
 *
 * @version 1.0.0
 */
public class EmailUtil {

    /**
     * 邮箱服务器
     */
    public static final String SMTP_SERVER = "smtp.mxhichina.com";
    /**
     * 发件人邮箱
     */
    public static final String ACCOUT = "xxxxx@dc1g.com";
    /**
     * 发件人邮箱授权码
     */
    public static final String PWD = "xxxxxxxxx";

    /**
     * 邮件发送
     *
     * @param to      收件人的邮箱地址
     * @param titel   邮件的标题
     * @param content 邮件的内容
     * @return hgbccfqwhpiqbfbi
     */
    @SuppressWarnings("deprecation")
    public static boolean sendEmail(String to, String titel, String content) {
        boolean flag;
        try {
            SimpleEmail email = new SimpleEmail();
            //设置邮件服务器
            email.setHostName(SMTP_SERVER);
            //设置发件人用户和授权密码
            email.setAuthenticator(new DefaultAuthenticator(ACCOUT, PWD));
            //打印debug信息
            email.setDebug(true);
            //设置权限校验
            email.setTLS(true);
            //收件人地址
            /** email.setSSL(true);设置权限校验*/
            email.addTo(to);
            //发件人地址
            email.setFrom(ACCOUT, "【邮件发送类】系统消息");
            //设置标题
            email.setSubject(titel);
            //设置编码
            email.setCharset("utf-8");
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            //设置发送内容
            email.setContent((MimeMultipart) mainPart);
            email.send(); //发送
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

}
