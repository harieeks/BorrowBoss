package com.borrowboss.authservice.authservice.Service;

import com.borrowboss.authservice.authservice.Entity.Otp;
import com.borrowboss.authservice.authservice.Entity.User;

public interface OtpService {
    String generatePassword(User user);
    String verifyOtp(Long id, String otp);
}
