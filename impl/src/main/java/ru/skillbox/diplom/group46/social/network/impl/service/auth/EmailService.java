package ru.skillbox.diplom.group46.social.network.impl.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.sender.email}")
    private String senderEmail;

    @Value("${spring.mail.sender.text}")
    private String senderText;

    public void sendRecoveryEmail(String email, String recoveryToken) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(email);
        message.setSubject("Password Recovery");
        message.setText("Hello,\n\nYou requested to reset your password. Click the link below to reset it:\n\n"
                + "http://yourwebsite.com/reset-password?token=" + recoveryToken + "\n\n"
                + "If you did not request this, please ignore this email.\n\nThank you.");

        emailSender.send(message);
    }
}
