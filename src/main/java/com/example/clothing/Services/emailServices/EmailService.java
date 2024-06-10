package com.example.clothing.Services.emailServices;

import java.util.Properties;

import org.springframework.stereotype.Component;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Authenticator;

@Component
public class EmailService {
    public boolean sendEmail(String to, String subject, String text) {
        System.out.println("I AM IN SEND EMAIL METHODO");
        boolean flag = false;

        String from = "technicalangadsingh@gmail.com";

        // logic building
        // smtp properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        String user = "technicalangadsingh";
        String password = "tlid knpc rxbp bgrz";
        // session create
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password); // Generated from
                                                                   // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
        });

        // composing the message
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("subject");
            // Setting the HTML content
            message.setContent(text, "text/html");

            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("EMAIL IS SENT : " + flag);
        return flag;
    }

}
