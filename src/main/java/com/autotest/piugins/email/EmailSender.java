package com.autotest.piugins.email;

/**
 * Created by youyong.li on 10/24/2017.
 */

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**

 * 创建并发送一封包含文本、图片、附件的复杂邮件

 *

 * @author xietansheng

 */
public class EmailSender {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    public static String myEmailUsername = "xxx";
    public static String myEmailAccount = "xxx@163.com";
    public static String myEmailPassword = "xxx";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "xxx@qq.com";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.163.com";

    public void send(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", myEmailSMTPHost);
        props.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try{
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myEmailUsername, myEmailPassword);
                }
            });
            session.setDebug(true);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmailAccount));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount));
            message.setSubject("激活邮件");
            String content = "<html><head></head><body><h1>请点击连接激活</h1></html>";
            message.setContent(content, "text/html;charset=UTF-8");
//        Transport.send(message);
            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        EmailSender emailSender = new EmailSender();
        emailSender.send();

    }

}
