package com.modelsapp.models_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private static JavaMailSender emailSender;

    public static void enviarEmailConfirmacao(String para, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(para);
        email.setSubject(assunto);
        email.setText(mensagem);
        email.setFrom("seuemail@gmail.com"); // Email configurado no application.properties
        
        emailSender.send(email);
    }
}