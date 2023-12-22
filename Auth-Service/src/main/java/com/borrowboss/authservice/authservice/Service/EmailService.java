package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Entity.User;

public interface EmailService {

    String sendEmail(String toEmail, String body, String subject);
    String generateEmail(User user);
}
