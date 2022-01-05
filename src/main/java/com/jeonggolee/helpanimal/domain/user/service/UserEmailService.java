package com.jeonggolee.helpanimal.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserEmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String to, String sub, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(sub);
        message.setText(text);
        emailSender.send(message);
    }

    public String createKey() {
        String code = "";
        for(int i=0;i<6;i++){
            code += (int)(Math.random()*10);
        }
        return code;
    }
}
