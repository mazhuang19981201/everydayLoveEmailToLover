package com.mazhuang.schedulemail.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * 主动生成发送内容
 */
@Component
@Slf4j
public class SendMessage {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${she.mail}")
    private String sheMail;

    /**
     * 发送邮件
     * @param subject   主题
     * @param message   内容
     */
    public void sendMessage(String subject,String message){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimeMessage);
        StringBuffer theMessage = new StringBuffer();
        try {
            mimemessageHelper.setFrom(username); // 发送者邮箱
            mimemessageHelper.setTo(sheMail); // 接受者邮箱
            mimemessageHelper.setSubject(subject); // 发送主题
            mimemessageHelper.setText(message); // 发送内容

            javaMailSender.send(mimemessageHelper.getMimeMessage()); // 发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * @param subject   主题
     * @param message   内容
     */
    public void sendHtmlMessage(String subject,String message){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimeMessage);
        StringBuffer theMessage = new StringBuffer();
        theMessage.append("<h2><font color=pink>双向奔赴</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<i>"+ message +"</i>");
        try {
            mimemessageHelper.setFrom(username); // 发送者邮箱
            mimemessageHelper.setTo(sheMail); // 接受者邮箱
            mimemessageHelper.setSubject(subject); // 发送主题
            mimemessageHelper.setText(theMessage.toString(),true); // 发送内容

            javaMailSender.send(mimemessageHelper.getMimeMessage()); // 发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static String getOneS(){

        try {
            // 创建客户端对象
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://chp.shadiao.app/api.php");

            HttpResponse response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();
            String responseInfo = EntityUtils.toString(entity);

            return responseInfo;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("远程获取信息失败");
        }

    }


}
