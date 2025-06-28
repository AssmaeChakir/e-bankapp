package com.bankapp.bank_management.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender sender) {
        this.mailSender = sender;
    }

    public void sendCredentialsEmail(String to, String login, String password) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject("Vos identifiants de connexion");
            msg.setText("Bonjour,\n\nVoici vos identifiants:\nLogin: " + login + "\nMot de passe: " + password);
            mailSender.send(msg);
            System.out.println("✅ Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
