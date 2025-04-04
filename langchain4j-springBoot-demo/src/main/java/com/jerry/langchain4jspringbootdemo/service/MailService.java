package com.jerry.langchain4jspringbootdemo.service;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @version 1.0
 * @Author jerryLau
 * @Date 2025/3/28 11:18
 * @注释 邮件服务
 */
@Service
public class MailService {

    public boolean sendEmail(
                             String addr, String password, // 此处 password 实际是授权码
                             String toAddress, String subject, String message)
            throws MessagingException {


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // 开启ssl 验证
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.debug", "true"); // 启用调试日志

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(addr, password); // 密码参数传入授权码
            }
        };

        Session session = Session.getInstance(props, authenticator);

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(addr));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("邮件发送成功！");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
