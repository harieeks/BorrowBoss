package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Entity.Otp;
import com.borrowboss.authservice.authservice.Entity.User;
import com.borrowboss.authservice.authservice.Repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;
    private final OtpService otpService;


    @Override
    public String sendEmail(String toEmail, String body, String subject) {
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("demo9207692551@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("mail send");
            return "mail send successfully...";
        }catch (Exception e){
            return "Error while sending email";
        }
    }

    @Override
    public String generateEmail(User user) {
        String otp=otpService.generatePassword(user);
         return sendEmail(
                 user.getEmail(),
                 "This is your one time password "+otp+" use this...",
                 "One time password for Borrow Boss"
         );
    }


}
